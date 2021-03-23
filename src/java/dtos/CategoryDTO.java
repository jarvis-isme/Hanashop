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
public class CategoryDTO implements  Serializable{
    private String categoryName, categoryId;
    private boolean isDeleted;

    public CategoryDTO() {
    }

    public CategoryDTO(String categoryName, String categoryId, boolean isDeleted) {
        this.categoryName = categoryName;
        this.categoryId = categoryId;
        this.isDeleted = isDeleted;
    }
    
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDelted) {
        this.isDeleted = isDelted;
    }
    
}
