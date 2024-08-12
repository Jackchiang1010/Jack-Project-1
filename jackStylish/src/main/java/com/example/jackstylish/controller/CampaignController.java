package com.example.jackstylish.controller;


import com.example.jackstylish.dto.campaign.CampaignDto;
import com.example.jackstylish.dto.campaign.SendCampaignDto;
import com.example.jackstylish.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
public class CampaignController {

    @Autowired
    private CampaignService campaignService;

    //create campaigns
    @RequestMapping(value = "/api/{apiVersion}/campaigns/create", method = RequestMethod.POST)
    public ResponseEntity<?> createCampaign(@PathVariable(value = "apiVersion") String apiVersion,
                                            @ModelAttribute CampaignDto campaignDto) {

        try {
            if (Objects.equals(apiVersion, "1.0")) {
                campaignService.createCampaign(campaignDto);

                return new ResponseEntity<>("create success", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("apiVersion is wrong", HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //query all campaigns
    @RequestMapping(value = "/api/{apiVersion}/marketing/campaigns", method = RequestMethod.GET)
    public ResponseEntity<?> getProducts(@PathVariable(value = "apiVersion") String apiVersion) {

        try {
            if (Objects.equals(apiVersion, "1.0")) {
                List<SendCampaignDto> campaigns = campaignService.getCampaigns();

                if (campaigns == null) {
                    return new ResponseEntity<>("None of information", HttpStatus.OK);
                }
                return ResponseEntity.ok(Map.of("data", campaigns));
            } else {
                return new ResponseEntity<>("apiVersion is wrong", HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
