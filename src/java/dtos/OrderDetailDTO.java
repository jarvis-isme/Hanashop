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
public class OrderDetailDTO  implements Serializable{
    private int orderId,quantity;
    private String foodId;
    private float price;

    public int getOrderId() {
        return orderId;
    }

    public OrderDetailDTO(int orderId, int quantity, String foodId, float price) {
        this.orderId = orderId;
        this.quantity = quantity;
        this.foodId = foodId;
        this.price = price;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
    

}
