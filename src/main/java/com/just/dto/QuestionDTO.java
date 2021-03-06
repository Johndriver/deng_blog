package com.just.dto;

import com.just.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private Long id;
    private String description;
    private String tag;
    private String title;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private User user;
}
