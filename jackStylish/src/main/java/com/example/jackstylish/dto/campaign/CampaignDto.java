package com.example.jackstylish.dto.campaign;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CampaignDto {

    private Integer productId;
    private MultipartFile picture;
    private String story;

}
