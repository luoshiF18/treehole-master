package com.treehole.search.service;

import com.treehole.framework.domain.onlineCourse.CoursePub;
import com.treehole.framework.domain.onlineCourse.CourseSearchParam;
import com.treehole.framework.domain.onlineCourse.TeachplanMediaPub;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cc
 * @date 2019/10/29 19:33
 */
@Service
public class CourseSearchService {

    @Value("${treehole.elasticsearch.course.index}")
    private String es_index;
    @Value("${treehole.elasticsearch.course.type}")
    private String es_type;
    @Value("${treehole.elasticsearch.course.source_field}")
    private String source_field;

    @Value("${treehole.elasticsearch.media.index}")
    private String media_index;
    @Value("${treehole.elasticsearch.media.type}")
    private String media_type;
    @Value("${treehole.elasticsearch.media.source_field}")
    private String media_source_field;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    //搜索课程
    public QueryResponseResult<CoursePub> searchCourse(int page, int size, CourseSearchParam courseSearchParam) {
        //判断条件
        if(courseSearchParam == null){
            courseSearchParam = new CourseSearchParam();
        }
        //设置索引
        SearchRequest searchRequest = new SearchRequest(es_index);
        //设置类型
        searchRequest.types(es_type);
        //创建搜索源对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //设置过滤条件
        String[] source_fields = source_field.split(",");
        searchSourceBuilder.fetchSource(source_fields,new String[]{});
        //创建bool查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //匹配关键字
        if(StringUtils.isNotEmpty(courseSearchParam.getKeyword())){
            MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders
                    .multiMatchQuery(courseSearchParam.getKeyword(),"name","teachplan","description")
                    .minimumShouldMatch("70%")
                    .field("name",10);
            boolQueryBuilder.must(multiMatchQueryBuilder);
        }
        //设置过滤器
        if(StringUtils.isNotEmpty(courseSearchParam.getMt())){
            //设置根据一级分类查询
            boolQueryBuilder.filter(QueryBuilders.termQuery("mt",courseSearchParam.getMt()));
        }
        if(StringUtils.isNotEmpty(courseSearchParam.getSt())){
            //设置根据二级分类查询
            boolQueryBuilder.filter(QueryBuilders.termQuery("st",courseSearchParam.getSt()));
        }
        if(StringUtils.isNotEmpty(courseSearchParam.getGrade())){
            //设置难度等级查询
            boolQueryBuilder.filter(QueryBuilders.termQuery("grade",courseSearchParam.getGrade()));
        }
        searchSourceBuilder.query(boolQueryBuilder);
        //设置分页参数
        //判断
        if(page <= 0){
            page = 1;
        }
        if(size <= 0){
            size = 12;
        }
        //计算起始下标
        int from = (page-1)*size;
        searchSourceBuilder.from(from);
        searchSourceBuilder.size(size);
        //设置高亮显示
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<font class='eslight'>");
        highlightBuilder.postTags("</font>");
        highlightBuilder.fields().add(new HighlightBuilder.Field("name"));
        searchSourceBuilder.highlighter(highlightBuilder);
        //设置搜索对象
        searchRequest.source(searchSourceBuilder);
        try {
            //restHighLevelClient执行搜索
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest);
            //处理结果
            SearchHits searchHits = searchResponse.getHits();
            //总记录数
            long totalHits = searchHits.getTotalHits();
            //获得匹配度高的数据
            SearchHit[] hits = searchHits.getHits();
            //创建数据列表
            List<CoursePub> list = new ArrayList<CoursePub>();
            //遍历数据
            for(SearchHit hit : hits){
                //创建coursePub
                CoursePub coursePub = new CoursePub();
                //取出单条数据
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                //取出课程id
                String id = (String) sourceAsMap.get("id");
                coursePub.setId(id);
                //取出名称
                String name = (String) sourceAsMap.get("name");
                //取出高亮字段
                Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                if(highlightFields.get("name") != null){
                    HighlightField highlightField = highlightFields.get("name");
                    Text[] fragments = highlightField.fragments();
                    StringBuffer stringBuffer = new StringBuffer();
                    for (Text text:fragments){
                        stringBuffer.append(text);
                    }
                    name = stringBuffer.toString();
                }
                coursePub.setName(name);
                //图片
                String pic = (String) sourceAsMap.get("pic");
                coursePub.setPic(pic);
                //价格
                Double price = null;
                try {
                    if(sourceAsMap.get("price")!=null ){
                        price = (Double) sourceAsMap.get("price");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                coursePub.setPrice(price);
                Double price_old = null;
                try {
                    if(sourceAsMap.get("price_old")!=null ){
                        price_old = (Double) sourceAsMap.get("price_old");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                coursePub.setPrice_old(price_old);
                list.add(coursePub);
            }
            //创建queryResult
            QueryResult queryResult = new QueryResult();
            queryResult.setTotal(totalHits);
            queryResult.setList(list);
            return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //根据课程id查询所有课程信息
    public Map<String, CoursePub> getall(String id) {
        //设置索引库
        SearchRequest searchRequest = new SearchRequest(es_index);
        //设置类型
        searchRequest.types(es_type);
        //创建searchSourceBuilders
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //设置查询条件
        searchSourceBuilder.query(QueryBuilders.termQuery("id",id));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取搜索结果
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        Map<String,CoursePub> map = new HashMap<>();
        for (SearchHit hit : searchHits) {
            //String courseId = hit.getId();
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            String courseId = (String) sourceAsMap.get("id");
            String name = (String) sourceAsMap.get("name");
            String grade = (String) sourceAsMap.get("grade");
            String charge = (String) sourceAsMap.get("charge");
            String pic = (String) sourceAsMap.get("pic");
            String description = (String) sourceAsMap.get("description");
            String teachplan = (String) sourceAsMap.get("teachplan");
            CoursePub coursePub = new CoursePub();
            coursePub.setId(courseId);
            coursePub.setName(name);
            coursePub.setPic(pic);
            coursePub.setGrade(grade);
            coursePub.setTeachplan(teachplan);
            coursePub.setDescription(description);
            map.put(courseId,coursePub);
        }
        return map;
    }

    //根据课程计划查询课程媒资信息
    public QueryResponseResult<TeachplanMediaPub> getmedia(String[] teachplanIds) {
        //设置索引
        SearchRequest searchRequest = new SearchRequest(media_index);
        //设置类型
        searchRequest.types(media_type);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //source源字段过虑
        String[] source_fields = media_source_field.split(",");
        searchSourceBuilder.fetchSource(source_fields, new String[]{});
        //查询条件，根据课程计划id查询(可传入多个id)
        searchSourceBuilder.query(QueryBuilders.termsQuery("teachplan_id", teachplanIds));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
        //执行搜索
            searchResponse = restHighLevelClient.search(searchRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取搜索结果
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        Map<String,CoursePub> map = new HashMap<>();
        //数据列表
        List<TeachplanMediaPub> teachplanMediaPubList = new ArrayList<>();
        for (SearchHit hit : searchHits) {
            TeachplanMediaPub teachplanMediaPub =new TeachplanMediaPub();
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
//取出课程计划媒资信息
            String courseid = (String) sourceAsMap.get("courseid");
            String media_id = (String) sourceAsMap.get("media_id");
            String media_url = (String) sourceAsMap.get("media_url");
            String teachplan_id = (String) sourceAsMap.get("teachplan_id");
            String media_fileoriginalname = (String) sourceAsMap.get("media_fileoriginalname");
            teachplanMediaPub.setCourseId(courseid);
            teachplanMediaPub.setMediaUrl(media_url);
            teachplanMediaPub.setMediaFileOriginalName(media_fileoriginalname);
            teachplanMediaPub.setMediaId(media_id);
            teachplanMediaPub.setTeachplanId(teachplan_id);
            //将数据加入列表
            teachplanMediaPubList.add(teachplanMediaPub);
        }
        //构建返回课程媒资信息对象
        QueryResult<TeachplanMediaPub> queryResult = new QueryResult<>();
        queryResult.setList(teachplanMediaPubList);
        QueryResponseResult<TeachplanMediaPub> queryResponseResult = new
                QueryResponseResult<TeachplanMediaPub>(CommonCode.SUCCESS,queryResult);
        return queryResponseResult;
    }
}
