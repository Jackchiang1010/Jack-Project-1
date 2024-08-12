package com.example.jackstylish.service;

import com.example.jackstylish.dao.OrderDao;
import com.example.jackstylish.dto.order.OrderDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    @Autowired
    private OrderDao orderDao;

    public Integer isProductExist(String productId) throws DataAccessException {
        return orderDao.isProductExist(productId);
    }

    public Integer createOrder(OrderDto order, Integer userId) {
        return orderDao.createOrders(order, userId);
    }

    private final String TAP_PAY_API_URL_SANDBOX = "https://sandbox.tappaysdk.com/tpc/payment/pay-by-prime";
    private final String TAP_PAY_PARTNER_KEY = "partner_PHgswvYEk4QY6oy3n8X3CwiQCVQmv91ZcFoD5VrkGFXo8N7BFiLUxzeG";

    public boolean processPaymentRequest(Integer orderId, OrderDto orderDto) throws DataAccessException {

        Integer quantity = orderDto.getOrder().getList().get(0).getQty();
        String prime = orderDto.getPrime();
        Integer amount = orderDto.getOrder().getTotal();

        String colorCode = orderDto.getOrder().getList().get(0).getColor().getCode();
        String size = orderDto.getOrder().getList().get(0).getSize();

        String result = sendPaymentRequest(prime, amount);

        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode responseJson = mapper.readTree(result);

            String status = responseJson.path("status").asText();

            if (status.equals("0")) {
                // Pay Success
                log.info("Payment successful!");
                String transactionId = responseJson.path("transaction_id").asText();
                log.info("Transaction ID: " + transactionId);

                orderDao.updateOrder(orderId, 1);

                orderDao.updateVariantStock(quantity, orderDto.getOrder().getList().get(0).getId(), colorCode, size);

                return true;
            } else {
                // Pay Fail
                String errorMessage = responseJson.path("msg").asText();
                log.info("Payment failed: " + errorMessage);

                orderDao.updateOrder(orderId, -1);

                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public String sendPaymentRequest(String prime, int amount) {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();

        // request headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("x-api-key", TAP_PAY_PARTNER_KEY);

        // build cardholder infomation
        Map<String, Object> cardholder = new HashMap<>();
        cardholder.put("phone_number", "+886923456789");
        cardholder.put("name", "Jane Doe");
        cardholder.put("email", "Jane@Doe.com");
        cardholder.put("zip_code", "12345");
        cardholder.put("address", "123 1st Avenue, City, Country");
        cardholder.put("national_id", "A123456789");

        // build request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("prime", prime);
        requestBody.put("partner_key", TAP_PAY_PARTNER_KEY);
        requestBody.put("merchant_id", "AppWorksSchool_CTBC");
        requestBody.put("details", "TapPay Test");
        requestBody.put("amount", amount);
        requestBody.put("cardholder", cardholder);
        requestBody.put("remember", true);

        // parse to JSON
        String jsonRequestBody = null;
        try {
            jsonRequestBody = mapper.writeValueAsString(requestBody);
        } catch (Exception e) {
            e.printStackTrace();
        }

        HttpEntity<String> requestEntity = new HttpEntity<>(jsonRequestBody, headers);

        // send POST to Sandbox
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                TAP_PAY_API_URL_SANDBOX,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        // TapPay server response
        return responseEntity.getBody();
    }

}
