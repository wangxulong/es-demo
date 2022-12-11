package com.example.esdemo.service;

import com.example.esdemo.domain.Blog;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 描述
 *
 * @author wangxulong
 * @version 20221211
 */
@SpringBootTest
class BlogServiceTest {
    @Autowired
    private BlogService blogService;
    @Test
    public void testSearch(){
        String beginDate = "2021-10-10 11:52:01";
        String endDate = "2021-10-10 11:52:04";
        final LocalDateTime begin = LocalDateTime.parse(beginDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        final LocalDateTime end = LocalDateTime.parse(endDate,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        final Page<Blog> result = blogService.pageAndSort(null,null, begin,end);
        final long totalElements = result.getTotalElements();
        Assertions.assertTrue(totalElements > 0);
    }
}
