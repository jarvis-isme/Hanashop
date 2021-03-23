/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author nguye
 */
public class FoodDTO implements  Serializable {
    private String foodId,categoryId,foodName,description,image;
    private int quantity;
    private float price;
    private boolean isDeleted;
    private Date createdDate,expiredDate;
    public FoodDTO(){
        
    }

    public FoodDTO(String foodId, String categoryId, String foodName, String description, String image, int quantity, float price, boolean isDeleted) {
        this.foodId = foodId;
        this.categoryId = categoryId;
        this.foodName = foodName;
        this.description = description;
        this.image = image;
        this.quantity = quantity;
        this.price = price;
        this.isDeleted = isDeleted;
    }

    
    public FoodDTO(String foodId, String categoryId, String foodName, String description, String image, int quantity, float price, boolean isDeleted, Date createdDate, Date expiredDate) {
        this.foodId = foodId;
        this.categoryId = categoryId;
        this.foodName = foodName;
        this.description = description;
        this.image = image;
        this.quantity = quantity;
        this.price = price;
        this.isDeleted = isDeleted;
        this.createdDate = createdDate;
        this.expiredDate = expiredDate;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

   
}
