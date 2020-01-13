package com.treehole.onlineCourse.service;

import com.treehole.framework.domain.onlineCourse.CourseBase;
import com.treehole.framework.domain.onlineCourse.CoursePub;
import com.treehole.framework.domain.onlineCourse.CourseSearchParam;
import com.treehole.framework.domain.onlineCourse.response.CourseBaseResult;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.onlineCourse.dao.CourseBaseRepository;
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
import java.util.*;

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
}
