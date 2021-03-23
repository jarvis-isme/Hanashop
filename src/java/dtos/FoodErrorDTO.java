/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.Serializable;

/**
 *
 * @author nguye
 */
public class FoodErrorDTO  implements  Serializable{
    private String foodIdError,foodNameError,descriptionError,imageError,categoryIdError,quantityError,priceError,dateCreatedError,expiredDatedError;

    public FoodErrorDTO() {
    }

    public FoodErrorDTO(String foodIdError, String foodNameError, String descriptionError, String imageError, String categoryIdError, String quantityError, String priceError, String dateCreatedError, String expiredDatedError) {
        this.foodIdError = foodIdError;
        this.foodNameError = foodNameError;
        this.descriptionError = descriptionError;
        this.imageError = imageError;
        this.categoryIdError = categoryIdError;
        this.quantityError = quantityError;
        this.priceError = priceError;
        this.dateCreatedError = dateCreatedError;
        this.expiredDatedError = expiredDatedError;
    }

    public String getDateCreatedError() {
        return dateCreatedError;
    }

    public void setDateCreatedError(String dateCreatedError) {
        this.dateCreatedError = dateCreatedError;
    }

    public String getExpiredDatedError() {
        return expiredDatedError;
    }

    public void setExpiredDatedError(String expiredDatedError) {
        this.expiredDatedError = expiredDatedError;
    }

 
    public String getFoodIdError() {
        return foodIdError;
    }

    public void setFoodIdError(String foodIdError) {
        this.foodIdError = foodIdError;
    }

    public String getFoodNameError() {
        return foodNameError;
    }

    public void setFoodNameError(String foodNameError) {
        this.foodNameError = foodNameError;
    }

    public String getDescriptionError() {
        return descriptionError;
    }

    public void setDescriptionError(String descriptionError) {
        this.descriptionError = descriptionError;
    }

    public String getImageError() {
        return imageError;
    }

    public void setImageError(String imageError) {
        this.imageError = imageError;
    }

    public String getCategoryIdError() {
        return categoryIdError;
    }

    public void setCategoryIdError(String categoryIdError) {
        this.categoryIdError = categoryIdError;
    }

    public String getQuantityError() {
        return quantityError;
    }

    public void setQuantityError(String quantityError) {
        this.quantityError = quantityError;
    }

    public String getPriceError() {
        return priceError;
    }

    public void setPriceError(String priceError) {
        this.priceError = priceError;
    }

    
    
    
}
