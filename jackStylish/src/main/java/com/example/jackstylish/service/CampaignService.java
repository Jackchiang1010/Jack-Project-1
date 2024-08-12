package com.example.jackstylish.service;

import com.example.jackstylish.dao.CampaignDao;
import com.example.jackstylish.dto.campaign.CampaignDto;
import com.example.jackstylish.dto.campaign.SendCampaignDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class CampaignService {

    @Value("${webpage.url}")
    private String WEBPAGE_URL;

    @Autowired
    private CampaignDao campaignDao;

    public void createCampaign(CampaignDto campaignDto) {

        String picturePath = uploadPicture(campaignDto.getPicture());

        if (campaignDto.getProductId().equals(null)) {
            throw new IllegalArgumentException("missing product_id");
        } else if (campaignDto.getStory().isEmpty()) {
            throw new IllegalArgumentException("missing story");
        } else {
            campaignDao.createCampaign(campaignDto, picturePath.replace("\\", "/"));
        }

    }

    public String uploadPicture(MultipartFile picture) {
        if (picture.isEmpty()) {
            return "At least one files";
        }

        try {

            String uuid = UUID.randomUUID().toString();

            String storePicture = "uploads/";

            File directory = new File(storePicture);
            if (!directory.exists()) {
                directory.mkdirs();
                directory.setWritable(true);
            }

            Path FilePath = Paths.get(storePicture, uuid + "_" + picture.getOriginalFilename());

            Files.write(FilePath, picture.getBytes());

            String imagePath = WEBPAGE_URL + FilePath.toString();

            log.info("imagePath : {}", imagePath);

            return imagePath;

        } catch (IOException e) {

            e.printStackTrace();
            return "Fail！";

        }
    }

    public List<SendCampaignDto> getCampaigns() {
        return campaignDao.getCampaigns();
    }

}
