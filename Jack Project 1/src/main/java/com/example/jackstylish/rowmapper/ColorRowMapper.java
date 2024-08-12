package com.example.jackstylish.rowmapper;

import com.example.jackstylish.dto.sendproduct.SendColorDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ColorRowMapper implements RowMapper<SendColorDto> {

    @Override
    public SendColorDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        SendColorDto sendColorDto = new SendColorDto();

        sendColorDto.setCode(rs.getString("code"));
        sendColorDto.setName(rs.getString("name"));

        return sendColorDto;
    }

}
