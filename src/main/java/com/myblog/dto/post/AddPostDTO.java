package com.myblog.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddPostDTO {
    private String title;
    private String text;
    private String tags;
    private MultipartFile image;
}
