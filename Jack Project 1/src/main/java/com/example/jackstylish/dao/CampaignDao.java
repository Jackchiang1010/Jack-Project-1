package com.example.jackstylish.dao;

import com.example.jackstylish.dto.campaign.CampaignDto;
import com.example.jackstylish.dto.campaign.SendCampaignDto;
import com.example.jackstylish.rowmapper.CampaignRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CampaignDao {


    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @ResponseBody
    public Integer createCampaign(CampaignDto campaignDto, String picturePath) {

        String sql = "INSERT INTO campaign(product_id, picture, story) VALUES (:productId, :picture, :story);";

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("productId", campaignDto.getProductId());
        map.put("picture", picturePath);
        map.put("story", campaignDto.getStory());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int imagesId = keyHolder.getKey().intValue();

        return imagesId;
    }

    @ResponseBody
    public List<SendCampaignDto> getCampaigns() {
        String sql = "SELECT * FROM campaign";

        Map<String, Object> map = new HashMap<String, Object>();

        List<SendCampaignDto> campaigns = namedParameterJdbcTemplate.query(sql, map, new CampaignRowMapper());

        if (!campaigns.isEmpty()) {
            return campaigns;
        } else {
            return null;
        }
    }

}
