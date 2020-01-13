package com.treehole.framework.domain.onlineCourse.response;

import com.google.common.collect.ImmutableMap;
import com.treehole.framework.model.response.ResultCode;
import lombok.ToString;


/**
 * Created by admin on 2018/3/5.
 */
@ToString
public enum CoursePicCode implements ResultCode {
    FS_UPLOADFILE_FILEISNULL(false,25001,"上传文件为空！"),
    FS_UPLOADFILE_BUSINESSISNULL(false,25002,"业务Id为空！"),
    FS_UPLOADFILE_SERVERFAIL(false,25003,"上传文件服务器失败！"),
    FS_DELETEFILE_NOTEXISTS(false,25004,"删除的文件不存在！"),
    FS_DELETEFILE_DBFAIL(false,25005,"删除文件信息失败！"),
    FS_DELETEFILE_SERVERFAIL(false,25006,"删除文件失败！"),
    FS_UPLOADFILE_METAERROR(false,25007,"上传文件的元信息请使用json格式！"),
    FS_UPLOADFILE_USERISNULL(false,25008,"上传文件用户为空！"),
    FS_INITFDFSERROR(false,25009,"初始化fastDFS环境出错！");
    //操作代码
    boolean success;

    //操作代码
    int code;
    //提示信息
    String message;
    private CoursePicCode(boolean success, int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }
    private static final ImmutableMap<Integer, CoursePicCode> CACHE;

    static {
        final ImmutableMap.Builder<Integer, CoursePicCode> builder = ImmutableMap.builder();
        for (CoursePicCode commonCode : values()) {
            builder.put(commonCode.code(), commonCode);
        }
        CACHE = builder.build();
    }

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
