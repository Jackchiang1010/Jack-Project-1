package com.example.jackstylish.rowmapper;

import com.example.jackstylish.dto.campaign.SendCampaignDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CampaignRowMapper implements RowMapper<SendCampaignDto> {

    @Override
    public SendCampaignDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        SendCampaignDto sendCampaignDto = new SendCampaignDto();

        sendCampaignDto.setProductId(rs.getInt("product_id"));
        sendCampaignDto.setPicture(rs.getString("picture"));
        sendCampaignDto.setStory(rs.getString("story"));

        return sendCampaignDto;
    }

}
