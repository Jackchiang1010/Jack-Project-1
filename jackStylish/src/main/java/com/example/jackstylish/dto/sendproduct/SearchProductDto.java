package com.example.jackstylish.dto.sendproduct;

import com.example.jackstylish.dto.ProductDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"id", "category", "title", "description", "price", "texture", "wash", "place", "note", "story"
        , "main_image", "images", "variants", "colors", "sizes"})
public class SearchProductDto {

    ProductDto product = new ProductDto();

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
    private List<SendColorDto> colors;
    private List<String> sizes;
    private List<SendVariantDto> variants;
    @JsonProperty("main_image")
    private String mainImage;
    private List<String> images;

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

    public List<SendColorDto> getColors() {
        return colors;
    }

    public void setColors(List<SendColorDto> colors) {
        this.colors = colors;
    }

    public List<String> getSizes() {
        return sizes;
    }

    public void setSizes(List<String> sizes) {
        this.sizes = sizes;
    }

    public List<SendVariantDto> getVariants() {
        return variants;
    }

    public void setVariants(List<SendVariantDto> variants) {
        this.variants = variants;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

}
