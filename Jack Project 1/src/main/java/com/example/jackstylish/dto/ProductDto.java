package com.example.jackstylish.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ProductDto {

    private int id;

    private String category;
    private String title;
    private String description;
    private int price;
    private String texture;
    private String wash;
    private String place;
    private String note;
    private String story;

    private List<String> code;
    private List<String> name;
    private List<String> size;
    private List<Integer> stock;

    private MultipartFile productMainImage;
    private List<MultipartFile> productImages;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    public String getWash() {
        return wash;
    }

    public void setWash(String wash) {
        this.wash = wash;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public MultipartFile getProductMainImage() {
        return productMainImage;
    }

    public void setProductMainImage(MultipartFile productMainImage) {
        this.productMainImage = productMainImage;
    }

    public List<MultipartFile> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<MultipartFile> productImages) {
        this.productImages = productImages;
    }

    public List<String> getCode() {
        return code;
    }

    public void setCode(List<String> code) {
        this.code = code;
    }

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public List<String> getSize() {
        return size;
    }

    public void setSize(List<String> size) {
        this.size = size;
    }

    public List<Integer> getStock() {
        return stock;
    }

    public void setStock(List<Integer> stock) {
        this.stock = stock;
    }

}
