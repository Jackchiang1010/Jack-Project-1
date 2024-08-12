package com.example.jackstylish.dao;

import com.example.jackstylish.dto.order.ListDto;
import com.example.jackstylish.dto.order.OrderDto;
import com.example.jackstylish.dto.order.RecipientDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @ResponseBody
    public Integer isProductExist(String productId) throws DataAccessException {

        String sql = "SELECT id FROM product WHERE id = :productId;";

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("productId", productId);

        Integer productExistId = namedParameterJdbcTemplate.queryForObject(sql, map, (ResultSet rs, int rowNum) -> rs.getInt("id"));

        return productExistId;
    }

    public Integer createOrders(OrderDto order, Integer userId) {

        String sql = "INSERT INTO orders(shipping,payment,subtotal,freight,total,recipient_id,user_id,payment_status) " +
                "VALUES (:shipping,:payment,:subtotal,:freight,:total,:recipientId,:userId,:paymentStatus);";

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("shipping", order.getOrder().getShipping());
        map.put("payment", order.getOrder().getPayment());
        map.put("subtotal", order.getOrder().getSubtotal());
        map.put("freight", order.getOrder().getFreight());
        map.put("total", order.getOrder().getTotal());

        int recipientId = createRecipient(order.getOrder().getRecipient());
        map.put("recipientId", recipientId);

        map.put("userId", userId);
        map.put("paymentStatus", 0);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int orderId = keyHolder.getKey().intValue();

        for (int index = 0; index < order.getOrder().getList().size(); index++) {
            createList(orderId, order.getOrder().getList(), index);
        }

        return orderId;

    }

    public Integer createRecipient(RecipientDto recipientDto) {

        String sql = "INSERT INTO recipient(name,phone,email,address,time) VALUES (:name,:phone,:email,:address,:time);";

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", recipientDto.getName());
        map.put("phone", recipientDto.getPhone());
        map.put("email", recipientDto.getEmail());
        map.put("address", recipientDto.getAddress());
        map.put("time", recipientDto.getTime());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int recipientId = keyHolder.getKey().intValue();

        return recipientId;

    }

    public void createList(Integer orderId, List<ListDto> ListDto, Integer index) {

        String sql = "INSERT INTO list(order_id, variant_id, qty) VALUES (:order_id, :variant_id, :qty);";

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("order_id", orderId);
        map.put("variant_id", getVariantId(ListDto.get(index).getId(), ListDto.get(index).getColor().getCode(), ListDto.get(index).getSize()));
        map.put("qty", ListDto.get(index).getQty());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

    }

    @ResponseBody
    public Integer getVariantId(String productId, String color, String size) {

        String sql = "SELECT " +
                "id " +
                "FROM variant " +
                "WHERE product_id = :productId " +
                "AND color_code = :colorCode " +
                "AND size = :size;";

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("productId", productId);
        map.put("colorCode", color);
        map.put("size", size);

        Integer variantId = namedParameterJdbcTemplate.queryForObject(sql, map, (ResultSet rs, int rowNum) -> rs.getInt("id"));

        return variantId;
    }

    public void updateOrder(Integer orderId, Integer paymentStatus) {
        String sql = "UPDATE orders " +
                "SET payment_status = :paymentStatus " +
                "WHERE id = :orderId;";

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderId", orderId);
        map.put("paymentStatus", paymentStatus);

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map));

    }

    public void updateVariantStock(Integer quantity, String productId, String colorCode, String size) throws DataAccessException {
        String sql = "UPDATE variant " +
                "SET stock = stock - :quantity " +
                "WHERE product_id = :productId " +
                "AND color_code = :colorCode " +
                "AND size = :size;";

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("quantity", quantity);
        map.put("productId", productId);
        map.put("colorCode", colorCode);
        map.put("size", size);

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map));

    }

}
