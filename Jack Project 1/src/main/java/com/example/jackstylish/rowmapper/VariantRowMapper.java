package com.example.jackstylish.rowmapper;

import com.example.jackstylish.dto.sendproduct.SendVariantDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VariantRowMapper implements RowMapper<SendVariantDto> {

    @Override
    public SendVariantDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        SendVariantDto sendVariantDto = new SendVariantDto();

        sendVariantDto.setColorCode(rs.getString("color_code"));
        sendVariantDto.setVariantSize(rs.getString("size"));
        sendVariantDto.setStock(rs.getInt("stock"));

        return sendVariantDto;
    }

}
