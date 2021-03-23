/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nguye
 */
public class CartDTO implements Serializable{

     
    private Map<String, FoodDTO> cart;

    public CartDTO() {
        cart = new HashMap<>();
       
    }

    public CartDTO( Map<String, FoodDTO> cart) {
         
        this.cart = cart;
    }

 

    public Map<String, FoodDTO> getCart() {
        return cart;
    }

    public void setCart(Map<String, FoodDTO> cart) {
        this.cart = cart;
    }

    public void add(FoodDTO dto) {
        if (cart == null) {
            cart = new HashMap<>();
        }
        if (this.cart.containsKey(dto.getFoodId())) {
            int quantity = cart.get(dto.getFoodId()).getQuantity() + 1;
            dto.setQuantity(quantity);
        }
        this.cart.put(dto.getFoodId(), dto);
       
    }

    public void delete(String foodId) {
        if (cart == null) {
            return;
        }
        if (cart.containsKey(foodId)) {
            cart.remove(foodId);
            if (cart.isEmpty()) {
                cart = null;
            }
        }
    }

    public void update(String foodId, FoodDTO dto) {
        if (cart != null) {
            if (cart.containsKey(foodId)) {
                cart.replace(foodId, dto);
            }
        }
    }
}
