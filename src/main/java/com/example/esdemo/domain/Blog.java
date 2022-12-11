package com.example.esdemo.domain;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

@Data
@Document(indexName = "blog")
public class Blog {
    //此项作为id，不会写到_source里边。
    @Id
    private Long blogId;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text)
    private String content;

    @Field(type = FieldType.Text)
    private String author;

    //博客所属分类。
    @Field(type = FieldType.Keyword)
    private String category;

    //0: 未发布（草稿） 1：已发布 2：已删除
    @Field(type = FieldType.Integer)
    private int status;

    //序列号，用于给外部展示的id
    @Field(type = FieldType.Keyword)
    private String serialNum;

    @JsonFormat(pattern = "uuuu-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "uuuu-MM-dd HH:mm:ss")
    @Field(type= FieldType.Date,  format = {}, pattern = "uuuu-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "uuuu-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "uuuu-MM-dd HH:mm:ss")
    @Field(type= FieldType.Date,  format = {}, pattern = "uuuu-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;


}