package com.example.jackstylish.service;

import com.example.jackstylish.dao.ProductDao;
import com.example.jackstylish.dao.ProductListDao;
import com.example.jackstylish.dto.ProductDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Value("${webpage.url}")
    private String WEBPAGE_URL;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductListDao productListDao;

    public Integer createProduct(ProductDto productDto) {

        uploadMainImage(productDto.getProductMainImage());

        String mainImagePath = uploadMainImage(productDto.getProductMainImage());

        return productDao.createProduct(productDto, mainImagePath);
    }

    public Integer createImages(int productId, MultipartFile imagesFile) throws IOException {

        String images = uploadImages(imagesFile);

        return productDao.createImages(productId, images);
    }

    public String uploadMainImage(MultipartFile mainImageFile) {
        if (mainImageFile.isEmpty()) {
            return "At least one files";
        }

        try {

//            String workingDirectory = System.getProperty("user.dir");
//            System.out.println("Current working directory: " + workingDirectory);

            String uuid = UUID.randomUUID().toString();

            String storeImagePath = "uploads/";

            File directory = new File(storeImagePath);
            if (!directory.exists()) {
                directory.mkdirs();
                directory.setWritable(true);
            }

            //process main_image
            //getOriginalFilename():return file original name
            Path FilePath = Paths.get(storeImagePath, uuid + "_" + mainImageFile.getOriginalFilename());

//            //transferTo():upload file
//            mainImageFile.transferTo(main_image_path);
//            mainImageFile.transferTo(FilePath);

            Files.write(FilePath, mainImageFile.getBytes());

            return FilePath.toString();

        } catch (IOException e) {

            e.printStackTrace();
            return "Fail！";

        }
    }

    public String uploadImages(MultipartFile imagesFiles) throws IOException {

//        //to get current dir position
//        String workingDirectory = System.getProperty("user.dir");

        String uuid = UUID.randomUUID().toString();

        String storeImagePath = "uploads/";

        File directory = new File(storeImagePath);
        if (!directory.exists()) {
            directory.mkdirs();
            directory.setWritable(true);
        }

        Path FilePath = Paths.get(storeImagePath, uuid + "_" + imagesFiles.getOriginalFilename());

        Files.write(FilePath, imagesFiles.getBytes());

        String imagePath = WEBPAGE_URL + FilePath.toString();

        return imagePath;
    }

    List<Integer> colorIdList = new ArrayList<>();

    public void createColor(ProductDto productDto) {

        colorIdList = new ArrayList<>();
        for (int colorNumber = 0; colorNumber < productDto.getCode().size(); colorNumber++) {

//            String colorCode = productDto.getCode().get(colorNumber);
//            Integer colorId = productDao.getColorCode(colorCode);
//
//            if (colorId == null) {
//                colorId = productDao.createColor(productDto, colorNumber);
//            }
//
//            colorIdList.add(colorId);

            Integer colorId = productDao.createColor(productDto, colorNumber);
            colorIdList.add(colorId);
        }
    }

    List<Integer> sizeList = new ArrayList<>();

    public void createSize(ProductDto productDto) {

        sizeList = new ArrayList<>();
        for (int sizeNumber = 0; sizeNumber < productDto.getSize().size(); sizeNumber++) {

//            String sizeName = productDto.getSize().get(sizeNumber);
//
//            Integer sizeId = productDao.getSize(sizeName);
//
//            if (sizeId == null) {
//                sizeId = productDao.createSize(productDto, sizeNumber);
//
//                sizeList.add(sizeId);
//            }

            Integer sizeId = productDao.createSize(productDto, sizeNumber);
            sizeList.add(sizeId);
        }
    }

    List<Integer> variantIdList = new ArrayList<>();

    public void createVariant(int productId, ProductDto productDto) {

        for (int colorId = 0; colorId < colorIdList.size(); colorId++) {

            for (int sizeId = 0; sizeId < sizeList.size(); sizeId++) {

                int variantId = productDao.createVariant(productId, productDto, colorId, sizeId);
                variantIdList.add(variantId);
            }

        }
    }

}
