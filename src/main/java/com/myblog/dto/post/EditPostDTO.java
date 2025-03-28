package com.myblog.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Validated
public class EditPostDTO {
    private Long id;
    private String title;
    private String text;
    private MultipartFile image;
    private String tags = "";
}
