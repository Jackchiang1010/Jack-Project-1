package com.example.jackstylish.rowmapper;

import com.example.jackstylish.dto.sendproduct.SendProductDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<SendProductDto> {

    @Override
    public SendProductDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        SendProductDto sendProductDto = new SendProductDto();

        sendProductDto.setId(rs.getInt("id"));
        sendProductDto.setCategory(rs.getString("category"));
        sendProductDto.setTitle(rs.getString("title"));
        sendProductDto.setDescription(rs.getString("description"));
        sendProductDto.setPrice(rs.getInt("price"));
        sendProductDto.setTexture(rs.getString("texture"));
        sendProductDto.setWash(rs.getString("wash"));
        sendProductDto.setPlace(rs.getString("place"));
        sendProductDto.setNote(rs.getString("note"));
        sendProductDto.setStory(rs.getString("story"));

        return sendProductDto;
    }

}
