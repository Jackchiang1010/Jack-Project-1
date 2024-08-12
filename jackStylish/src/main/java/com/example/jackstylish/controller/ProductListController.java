package com.example.jackstylish.controller;

import com.example.jackstylish.service.ProductListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Controller
@RequestMapping(value = "/api/{apiVersion}/products", method = RequestMethod.GET)
public class ProductListController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductListService productListService;

    //http://localhost:8080/api/V1/products/men
    @RequestMapping(value = "/{category}", method = RequestMethod.GET)
    public ResponseEntity<?> getProducts(@PathVariable(value = "apiVersion") String apiVersion,
                                         @PathVariable(value = "category") String category,
                                         @RequestParam(value = "paging", defaultValue = "1") int paging) {

        try {
            if (Objects.equals(apiVersion, "1.0")) {
                Object productList = productListService.getProduct(paging, category).get(0);
                return new ResponseEntity<>(productList, HttpStatus.OK);
            }
            return new ResponseEntity<>("apiVersion is wrong", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //http://localhost:8080/api/V1/products/search?keyword=洋裝
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<?> search(@PathVariable(value = "apiVersion") String apiVersion,
                                    @RequestParam(value = "keyword") String keyword,
                                    @RequestParam(value = "paging", defaultValue = "1") int paging) {

        try {
            if (Objects.equals(apiVersion, "1.0")) {
                Object productList = productListService.searchProduct(keyword, paging).get(0);
                return new ResponseEntity<>(productList, HttpStatus.OK);
            }
            return new ResponseEntity<>("apiVersion is wrong", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //http://localhost:8080/api/V1/products/details?id=2
    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public ResponseEntity<?> details(@PathVariable(value = "apiVersion") String apiVersion,
                                     @RequestParam(value = "id", defaultValue = "1") int id) throws Exception {

        try {
            if (Objects.equals(apiVersion, "1.0")) {

                Object productList = productListService.getProductDetails(id).get(0);
                return new ResponseEntity<>(productList, HttpStatus.OK);

            }
            return new ResponseEntity<>("apiVersion is wrong", HttpStatus.OK);

        } catch (DataAccessException e) {
            return new ResponseEntity<>("id is not exist", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
