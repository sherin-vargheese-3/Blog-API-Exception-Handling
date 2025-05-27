package com.exercise.exceptionblog.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDTO {
    @NotBlank(message = "Content is required")
    private String content;
}