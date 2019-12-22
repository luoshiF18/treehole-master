package com.treehole.manage_media_process.mq;

import com.alibaba.fastjson.JSON;
import com.treehole.framework.domain.onlineCourse.MediaFile;
import com.treehole.framework.domain.onlineCourse.MediaFileProcess_m3u8;
import com.treehole.framework.utils.HlsVideoUtil;
import com.treehole.framework.utils.Mp4VideoUtil;
import com.treehole.manage_media_process.dao.MediaFileRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 监听视频上传小心，处理上传的视频
 * @author cc
 * @date 2019/11/12 14:47
 */
@Component
public class MediaProcessTask {

    //ffmpeg绝对路径
    @Value("${xc-service-manage-media.ffmpeg-path}")
    String ffmpeg_path;
    //上传文件根目录
    @Value("${xc-service-manage-media.video-location}")
    String serverPath;

    @Autowired
    private MediaFileRepository mediaFileRepository;

    @RabbitListener(queues = "${xc-service-manage-media.mq.queue-media-video-processor}",containerFactory="customContainerFactory")
    public void receiveMediaProcessTask(String msg){
        //1，解析获得的数据,转换为map对象
        Map msgMap = JSON.parseObject(msg, Map.class);
        //获取媒资id
        String mediaId = (String) msgMap.get("mediaId");
        //先查询数据库，看数据是否存在
        Optional<MediaFile> optional = mediaFileRepository.findById(mediaId);
        //若为空，直接结束方法
        if(!optional.isPresent()){
            return;
        }
        //否则拿到media数据
        MediaFile mediaFile = optional.get();
        //获取媒资文件类型
        String fileType = mediaFile.getFileType();
        //判断是否为avi类型，当前只支持avi类型视频的处理
        if(fileType == null || fileType.equals("mp4")){
            //设置视频状态为无需处理
            mediaFile.setProcessStatus("303004");
            //保存到数据库中
            mediaFileRepository.save(mediaFile);
            return;
        }else{
            //设置视频状态为处理中
            mediaFile.setProcessStatus("303001");
            mediaFileRepository.save(mediaFile);
        }
        //转换成MP4需要数据
        String video_path = serverPath + mediaFile.getFilePath() + mediaFile.getFileName();
        String mp4_name = mediaId + ".mp4";
        String mp4folder_path = serverPath + mediaFile.getFilePath();
        //先将视频转为mp4格式
        Mp4VideoUtil mp4VideoUtil = new Mp4VideoUtil(ffmpeg_path,video_path,mp4_name,mp4folder_path);
        String result = mp4VideoUtil.generateMp4();
        if(result == null && !result.equals("success")){
            //操作失败写入处理日志
            //修改处理状态为处理失败
            mediaFile.setProcessStatus("303003");
            //将错入信息存储到数据库中
//            MediaFileProcess_m3u8 mediaFileProcess_m3u8 = new MediaFileProcess_m3u8();
//            mediaFileProcess_m3u8.setErrormsg(result);
//            mediaFile.setMediaFileProcess_m3u8(mediaFileProcess_m3u8);
            //保存到数据库中
            mediaFileRepository.save(mediaFile);
            return;
        }
        //生成m3u8
        //生成m3u8需要的数据
        String mp4video_path = serverPath + mediaFile.getFilePath() + mp4_name;
        String m3u8_name = mediaId + ".m3u8";
        String m3u8folder_path = serverPath + mediaFile.getFilePath() + "hls/";
        HlsVideoUtil hlsVideoUtil = new HlsVideoUtil(ffmpeg_path,mp4video_path,m3u8_name,m3u8folder_path);
        result = hlsVideoUtil.generateM3u8();
        if(result == null && !result.equals("success")){
            //操作失败写入处理日志
            //修改处理状态为处理失败
            mediaFile.setProcessStatus("303003");
            //将错入信息存储到数据库中
//            MediaFileProcess_m3u8 mediaFileProcess_m3u8 = new MediaFileProcess_m3u8();
//            mediaFileProcess_m3u8.setErrormsg(result);
//            mediaFile.setMediaFileProcess_m3u8(mediaFileProcess_m3u8);
            //保存到数据库中
            mediaFileRepository.save(mediaFile);
            return;
        }
        //转换成功
        //获取m3u8列表
//        List<String> ts_list = hlsVideoUtil.get_ts_list();
//        MediaFileProcess_m3u8 mediaFileProcess_m3u8 = new MediaFileProcess_m3u8();
//        mediaFileProcess_m3u8.setTslist(ts_list);
//        mediaFile.setMediaFileProcess_m3u8(mediaFileProcess_m3u8);
        //设置状态为成功
        mediaFile.setProcessStatus("303002");
        //设置最后用于播放的url
        mediaFile.setFileUrl(mediaFile.getFilePath()+"hls/"+m3u8_name);
        //保存
        mediaFileRepository.save(mediaFile);

    }
}
