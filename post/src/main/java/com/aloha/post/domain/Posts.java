package com.aloha.post.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@Validated
public class Posts implements Serializable {

    private Long no;
    private String id;
    @NotBlank(message = "제목을 입력해 주세요.")
    private String title;
    @NotBlank(message = "작성자명을 입력해 주세요.")
    private String writer;
    @Size(min=10, max=500, message = "최소 10자 이상으로 입력해주세요.")
    private String content;
    private Date createdAt;
    private Date updatedAt;

    public Posts() {
        this.id = UUID.randomUUID().toString();
    }
}