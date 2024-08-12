package com.example.jackstylish.controller;

import com.example.jackstylish.dto.order.OrderDto;
import com.example.jackstylish.dto.order.OrderNumberDto;
import com.example.jackstylish.dto.user.UserDto;
import com.example.jackstylish.service.OrderService;
import com.example.jackstylish.service.UserService;
import com.example.jackstylish.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/api/1.0/order/checkout", method = RequestMethod.POST)
    public ResponseEntity<?> createCampaign(@RequestBody OrderDto orderDto,
                                            @RequestHeader("Authorization") String token) {

        try {

            if (token == null || !token.startsWith("Bearer ")) {
                return new ResponseEntity<>("No token", HttpStatus.UNAUTHORIZED);
            }

            String jwtToken = token.substring(7); // Remove "Bearer " prefix

            String email = jwtUtils.extractUsername(jwtToken);
            UserDto userDtoExist = userService.getUserByEmail(email);

            if (orderService.isProductExist(orderDto.getOrder().getList().get(0).getId()) != null) {

                Integer orderId = orderService.createOrder(orderDto, userDtoExist.getId());

                boolean paymentStatus = orderService.processPaymentRequest(orderId, orderDto);

                if (paymentStatus) {
                    OrderNumberDto orderNumberDto = new OrderNumberDto();
                    orderNumberDto.setNumber(orderId.toString());

                    return new ResponseEntity<>(Map.of("data", orderNumberDto), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Payment failed", HttpStatus.BAD_REQUEST);
                }
            } else {
                log.info("orderDto.getOrder().getList().get(0).getId(): {}", orderDto.getOrder().getList().get(0).getId());
                return new ResponseEntity<>("product doesn't exist", HttpStatus.BAD_REQUEST);
            }

        } catch (DataAccessException e) {
            return new ResponseEntity<>("product doesn't exist", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
