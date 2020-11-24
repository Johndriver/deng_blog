package com.just.model;

import lombok.Data;

@Data
public class Question {
    private Integer id;
    private String description;
    private String tag;
    private String title;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
}
