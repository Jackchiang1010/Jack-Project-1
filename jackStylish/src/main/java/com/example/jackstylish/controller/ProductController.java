package com.example.jackstylish.controller;

import com.example.jackstylish.dto.ProductDto;
import com.example.jackstylish.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

//http://localhost:8080/admin/product.html

@Controller
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String index() {
        return "redirect:http://54.248.138.109/index.html";
    }

    @RequestMapping(value = "/admin/product", method = RequestMethod.POST)
    public ResponseEntity<String> createProduct(@ModelAttribute ProductDto productDto) throws IOException {

        try {

            int productId = productService.createProduct(productDto);

            for (MultipartFile file : productDto.getProductImages()) {
                productService.createImages(productId, file);
            }

            productService.createColor(productDto);
            productService.createSize(productDto);
            productService.createVariant(productId, productDto);

            return ResponseEntity.ok("created product");
        } catch (IOException e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }
}
