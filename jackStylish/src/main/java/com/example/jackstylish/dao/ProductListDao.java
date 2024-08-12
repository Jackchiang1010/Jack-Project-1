package com.example.jackstylish.dao;

import com.example.jackstylish.controller.ProductController;
import com.example.jackstylish.dto.sendproduct.SendColorDto;
import com.example.jackstylish.dto.sendproduct.SendProductDto;
import com.example.jackstylish.dto.sendproduct.SendVariantDto;
import com.example.jackstylish.rowmapper.ColorRowMapper;
import com.example.jackstylish.rowmapper.ProductRowMapper;
import com.example.jackstylish.rowmapper.VariantRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductListDao {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private int limit = 6;

    @ResponseBody
    public int countProduct(String category) {

        String sql = "";

        if (category.equals("all")) {
            sql = "SELECT " +
                    "COUNT(id) " +
                    "FROM product;";
        } else {
            sql = "SELECT " +
                    "COUNT(id) " +
                    "FROM product " +
                    "WHERE product.category = :category;";
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("category", category);

        int count = namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);

        return count;
    }

    @ResponseBody
    public int countSearchProduct(String keyword) {

        String sql = "";

        sql = "SELECT " +
                "COUNT(id) " +
                "FROM product " +
                "WHERE product.title LIKE :keyword;";

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("keyword", "%" + keyword + "%");

        int count = namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);

        return count;
    }

    @ResponseBody
    public List<SendProductDto> getSearchProduct(String keyword, int paging) {

        String sql = "";

        sql = "SELECT " +
                "product.id," +
                "product.category," +
                "product.title," +
                "product.description," +
                "product.price," +
                "product.texture," +
                "product.wash," +
                "product.place," +
                "product.note," +
                "product.story " +
                "FROM product " +
                "WHERE product.title LIKE :keyword " +
                "LIMIT :limit " +
                "OFFSET :offset;";

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("keyword", "%" + keyword + "%");
        map.put("limit", limit);
        map.put("offset", limit * paging);

        List<SendProductDto> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        if (productList.size() > 0) {
            return productList;
        } else {
            return null;
        }
    }

    @ResponseBody
    public SendProductDto getProductDetails(int id) throws DataAccessException {

        String sql = "";

        sql = "SELECT " +
                "product.id," +
                "product.category," +
                "product.title," +
                "product.description," +
                "product.price," +
                "product.texture," +
                "product.wash," +
                "product.place," +
                "product.note," +
                "product.story " +
                "FROM product " +
                "WHERE product.id = :id;";

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);

        SendProductDto productList = namedParameterJdbcTemplate.queryForObject(sql, map, new ProductRowMapper());

        if (!productList.equals(null)) {
            return productList;
        } else {
            return null;
        }
    }

    @ResponseBody
    public List<SendProductDto> getProduct(String category, int paging) {

        String sql = "";

        if (category.equals("all")) {
            sql = "SELECT " +
                    "product.id," +
                    "product.category," +
                    "product.title," +
                    "product.description," +
                    "product.price," +
                    "product.texture," +
                    "product.wash," +
                    "product.place," +
                    "product.note," +
                    "product.story " +
                    "FROM product " +
                    "LIMIT :limit " +
                    "OFFSET :offset;";
        } else {
            sql = "SELECT " +
                    "product.id," +
                    "product.category," +
                    "product.title," +
                    "product.description," +
                    "product.price," +
                    "product.texture," +
                    "product.wash," +
                    "product.place," +
                    "product.note," +
                    "product.story " +
                    "FROM product " +
                    "WHERE product.category = :category " +
                    "LIMIT :limit " +
                    "OFFSET :offset;";
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("category", category);
        map.put("limit", limit);
        map.put("offset", limit * paging);

        List<SendProductDto> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        if (!productList.isEmpty()) {
            return productList;
        } else {
            return null;
        }
    }

    @ResponseBody
    public List<SendColorDto> getColor(SendProductDto sendProductDto) {

        String sql = "SELECT DISTINCT " +
                "color.code," +
                "color.name " +
                "FROM color " +
                "INNER JOIN variant ON variant.color_code = color.code " +
                "WHERE variant.product_id = :productId;";

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("productId", sendProductDto.getId());

        List<SendColorDto> productList = namedParameterJdbcTemplate.query(sql, map, new ColorRowMapper());

        if (!productList.isEmpty()) {
            return productList;
        } else {
            return null;
        }
    }

    @ResponseBody
    public List<String> getSize(SendProductDto sendProductDto) {

        String sql = "SELECT DISTINCT " +
                "size.size " +
                "FROM size " +
                "INNER JOIN variant ON variant.size = size.size " +
                "WHERE variant.product_id = :productId;";

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("productId", sendProductDto.getId());

        List<String> productList = namedParameterJdbcTemplate.query(sql, map, (ResultSet rs, int rowNum) -> rs.getString("size"));

        if (!productList.isEmpty()) {
            return productList;
        } else {
            return null;
        }
    }

    @ResponseBody
    public List<SendVariantDto> getVariants(SendProductDto sendProductDto) {

        String sql = "SELECT DISTINCT " +
                "variant.color_code," +
                "variant.size," +
                "variant.stock " +
                "FROM variant " +
                "WHERE variant.product_id = :productId;";

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("productId", sendProductDto.getId());

        List<SendVariantDto> productList = namedParameterJdbcTemplate.query(sql, map, new VariantRowMapper());

        if (productList.size() > 0) {
            return productList;
        } else {
            return null;
        }
    }

    @ResponseBody
    public String getMainImage(SendProductDto sendProductDto) {

        String sql = "SELECT " +
                "product.main_image " +
                "FROM product " +
                "WHERE product.id = :productId;";

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("productId", sendProductDto.getId());

        String productList = namedParameterJdbcTemplate.queryForObject(sql, map, (ResultSet rs, int rowNum) -> rs.getString("main_image"));

        if (!productList.equals(null)) {
            return productList;
        } else {
            return null;
        }
    }

    @ResponseBody
    public List<String> getImages(SendProductDto sendProductDto) {

        String sql = "SELECT " +
                "images " +
                "FROM product_images " +
                "WHERE product_images.product_id = :productId;";

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("productId", sendProductDto.getId());

        List<String> productList = namedParameterJdbcTemplate.query(sql, map, (ResultSet rs, int rowNum) -> rs.getString("images"));

        logger.info("image_productList : {}", productList);

        if (!productList.isEmpty()) {
            return productList;
        } else {
            return null;
        }
    }

}
