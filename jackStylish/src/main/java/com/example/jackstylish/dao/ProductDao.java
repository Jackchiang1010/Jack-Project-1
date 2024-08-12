package com.example.jackstylish.dao;

import com.example.jackstylish.controller.ProductController;
import com.example.jackstylish.dto.ProductDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ProductDao {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Value("${webpage.url}")
    private String WEBPAGE_URL;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @ResponseBody
    public Integer createProduct(ProductDto productDto, String main_image) {

        String sql = "INSERT INTO product(category,title,description,price,texture,wash,place,note,story,main_image) " +
                "VALUES (:category,:title,:description,:price,:texture,:wash,:place,:note,:story,:main_image);";

        productDto.setId(productDto.getId());

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("category", productDto.getCategory());
        map.put("title", productDto.getTitle());
        map.put("description", productDto.getDescription());
        map.put("price", productDto.getPrice());
        map.put("texture", productDto.getTexture());
        map.put("wash", productDto.getWash());
        map.put("place", productDto.getPlace());
        map.put("note", productDto.getNote());
        map.put("story", productDto.getStory());
        map.put("main_image", WEBPAGE_URL + main_image);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int productId = keyHolder.getKey().intValue();

        return productId;
    }

    @ResponseBody
    public Integer createImages(int productId, String images) {


        String sql = "INSERT INTO product_images(images,product_id) VALUES (:images,:product_id);";

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("images", images);
        map.put("product_id", productId);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int imagesId = keyHolder.getKey().intValue();

        return imagesId;
    }

    @ResponseBody
    public Integer createColor(ProductDto productDto, int colorCode) {

        String sql = "INSERT INTO color(code,name) VALUES (:code,:name);";

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", productDto.getCode().get(colorCode));
        map.put("name", productDto.getName().get(colorCode));

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int colorId = keyHolder.getKey().intValue();

        return colorId;
    }

    @ResponseBody
    public Integer createSize(ProductDto productDto, int sizeNumber) {


        String sql = "INSERT INTO size(size) VALUES (:insertSize);";

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("insertSize", productDto.getSize().get(sizeNumber));

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int sizeId = keyHolder.getKey().intValue();

        return sizeId;
    }

    @ResponseBody
    public int createVariant(int productId, ProductDto productDto, int colorId, int sizeId) {

        String sql = "INSERT INTO variant(color_code,size,stock,product_id) VALUES (:color_code,:size,:stock,:product_id);";

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("color_code", productDto.getCode().get(colorId));
        map.put("size", productDto.getSize().get(sizeId));
        map.put("stock", productDto.getStock().get(colorId));
        map.put("product_id", productId);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
        int variantId = keyHolder.getKey().intValue();

        return variantId;
    }

    @ResponseBody
    public Integer getColorCode(String colorCode) {

        String sql = "SELECT DISTINCT " +
                "color.id " +
                "FROM color " +
                "WHERE color.code = :colorCode;";

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("colorCode", colorCode);

        SqlParameterSource paramSource = new MapSqlParameterSource(map);

        try {
            Integer colorId = namedParameterJdbcTemplate.queryForObject(sql, paramSource, Integer.class);
            return colorId;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @ResponseBody
    public Integer getSize(String sizeName) {

        String sql = "SELECT DISTINCT " +
                "size.id " +
                "FROM size " +
                "WHERE size.size = :sizeName;";

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sizeName", sizeName);

        SqlParameterSource paramSource = new MapSqlParameterSource(map);

        try {
            Integer sizeID = namedParameterJdbcTemplate.queryForObject(sql, paramSource, Integer.class);
            return sizeID;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

}
