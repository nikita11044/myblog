package com.myblog.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlainPostDTO {
    private Long id;
    private String title;
    private String text;
    private String tagsAsString;
    private MultipartFile image;
}
