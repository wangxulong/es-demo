package com.example.esdemo.service;

import com.example.esdemo.domain.Blog;
import io.netty.util.internal.StringUtil;
import org.apache.commons.codec.binary.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 描述
 *
 * @author wangxulong
 * @version 20221211
 */
@Service
public class BlogService {

    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    public Page<Blog> pageAndSort(Long id ,String title, LocalDateTime startCreateTime, LocalDateTime endCreateTime) {
        // 分页
        PageRequest pageRequest = PageRequest.of(0, 2);
        // 查询 dsl, 构造查询条件
        NativeSearchQueryBuilder query = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if(null !=id ){
            // 不需要分词
            boolQueryBuilder.must(QueryBuilders.matchPhraseQuery("blogId", id));
        }
        if (!StringUtil.isNullOrEmpty(title)) {
            // 分词
            boolQueryBuilder.must(QueryBuilders.matchQuery("title", title));
        }
        if( null != startCreateTime && null != endCreateTime){
            // 时间区间
            boolQueryBuilder.must(QueryBuilders.rangeQuery("createTime")
                            .gt(startCreateTime));
        }

        query.withQuery(boolQueryBuilder);
        // 分页
        query.withPageable(pageRequest);
        // 排序
        query.withSorts(SortBuilders.fieldSort("createTime").order(SortOrder.DESC));

        SearchHits<Blog> searchHits = elasticsearchRestTemplate.search(query.build(), Blog.class);

        List<Blog> blogs = new ArrayList<>();
        for (SearchHit<Blog> searchHit : searchHits) {
            blogs.add(searchHit.getContent());
        }
        return new PageImpl(blogs, pageRequest, searchHits.getTotalHits());
    }
}
