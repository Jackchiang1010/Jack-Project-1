package com.example.jackstylish.service;

import com.example.jackstylish.controller.ProductController;
import com.example.jackstylish.dao.ProductListDao;
import com.example.jackstylish.dto.sendproduct.SendProductDetailsDto;
import com.example.jackstylish.dto.sendproduct.SendProductDto;
import com.example.jackstylish.dto.sendproduct.SendProductListDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductListService {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductListDao productListDao;

    public List<Object> getProduct(int paging, String category) {

        int pageList = 6;

        List<Object> productList = new ArrayList<>();

        int nextPaging = 0;

        int sumPage = productListDao.countProduct(category);

        if (Math.ceil((double) sumPage / pageList) - paging == 0) {
            if (sumPage % pageList == 0) {
                pageList = 6;
            } else {
                pageList = sumPage % pageList;
            }

            nextPaging = 0;
        } else {
            nextPaging = paging + 1;
        }

        List<SendProductDto> sendProductDto = new ArrayList<>();

        SendProductListDto sendProductListDto = new SendProductListDto();

        sendProductDto = productListDao.getProduct(category, paging - 1);

        for (int i = 0; i < pageList; i++) {

            sendProductDto.get(i).setColors(productListDao.getColor(sendProductDto.get(i)));
            sendProductDto.get(i).setSizes(productListDao.getSize(sendProductDto.get(i)));
            sendProductDto.get(i).setVariants(productListDao.getVariants(sendProductDto.get(i)));
            sendProductDto.get(i).setMainImage(productListDao.getMainImage(sendProductDto.get(i)));
            sendProductDto.get(i).setImages(productListDao.getImages(sendProductDto.get(i)));

        }

        sendProductListDto.setData(sendProductDto);

        sendProductListDto.setNextPage(nextPaging);

        productList.add(sendProductListDto);

        return productList;
    }

    public List<Object> searchProduct(String keyword, int paging) {
        int pageList = 6;

        List<Object> productList = new ArrayList<>();

        int nextPaging = 0;

        int sumPage = productListDao.countSearchProduct(keyword);

        if (Math.ceil((double) sumPage / pageList) - paging == 0) {
            if (sumPage % pageList == 0) {
                pageList = 6;
            } else {
                pageList = sumPage % pageList;
            }

            nextPaging = 0;
        } else {
            nextPaging = paging + 1;
        }

        List<SendProductDto> sendProductDto = new ArrayList<>();

        SendProductListDto sendProductListDto = new SendProductListDto();

        sendProductDto = productListDao.getSearchProduct(keyword, paging - 1);

        for (int i = 0; i < pageList; i++) {

            sendProductDto.get(i).setColors(productListDao.getColor(sendProductDto.get(i)));
            sendProductDto.get(i).setSizes(productListDao.getSize(sendProductDto.get(i)));
            sendProductDto.get(i).setVariants(productListDao.getVariants(sendProductDto.get(i)));
            sendProductDto.get(i).setMainImage(productListDao.getMainImage(sendProductDto.get(i)));
            sendProductDto.get(i).setImages(productListDao.getImages(sendProductDto.get(i)));

        }

        sendProductListDto.setData(sendProductDto);

        sendProductListDto.setNextPage(nextPaging);

        productList.add(sendProductListDto);

        return productList;
    }

    public List<Object> getProductDetails(int id) throws DataAccessException {

        List<Object> productList = new ArrayList<>();

        SendProductDto sendProductDto = new SendProductDto();

        SendProductDetailsDto sendProductDetailsDto = new SendProductDetailsDto();

        sendProductDto = productListDao.getProductDetails(id);

        sendProductDto.setColors(productListDao.getColor(sendProductDto));
        sendProductDto.setSizes(productListDao.getSize(sendProductDto));
        sendProductDto.setVariants(productListDao.getVariants(sendProductDto));
        sendProductDto.setMainImage(productListDao.getMainImage(sendProductDto));
        sendProductDto.setImages(productListDao.getImages(sendProductDto));

        sendProductDetailsDto.setData(sendProductDto);

        productList.add(sendProductDetailsDto);

        return productList;
    }

}
