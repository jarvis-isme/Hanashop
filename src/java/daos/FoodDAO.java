/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.FoodDTO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.DBUtils;

/**
 *
 * @author nguye
 */
public class FoodDAO {
//checked
    public boolean checkIdDuplicate(String id) throws SQLException {
        boolean result = false;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT foodName from tblFood where foodId=?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, id);
                rs = pst.executeQuery();
                if (rs.next()) {
                    result = true;
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (cn != null) {
                cn.close();
            }
        }

        return result;
    }
//check
    public boolean checkIdFoodCanBuy(String id) throws SQLException {
        boolean result = false;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT foodName from tblFood where foodId=? and isDeleted=0 and getDate()<expiredDate";
                pst = cn.prepareStatement(sql);
                pst.setString(1, id);
                rs = pst.executeQuery();
                if (rs.next()) {
                    result = true;
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (cn != null) {
                cn.close();
            }
        }

        return result;
    }
//checked
    public void addFood(FoodDTO dto) throws SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "INSERT tblFood(foodId,foodName,categoryId,createdDate,description,quantity,isDeleted,price,image,expiredDate) values(?,?,?,?,?,?,?,?,?,?)";
                pst = cn.prepareStatement(sql);
                pst.setString(1, dto.getFoodId());
                pst.setString(2, dto.getFoodName());
                pst.setString(3, dto.getCategoryId());
                pst.setDate(4, dto.getCreatedDate());
                pst.setString(5, dto.getDescription());
                pst.setInt(6, dto.getQuantity());
                pst.setBoolean(7, dto.isIsDeleted());
                pst.setFloat(8, dto.getPrice());
                pst.setString(9, dto.getImage());
                pst.setDate(10, dto.getExpiredDate());
                pst.executeUpdate();
            }
        } finally {
            if (pst != null) {
                pst.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
    }
//checked
    public void deleteFood(String foodId) throws SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "UPDATE tblFood SET isDeleted=1 WHERE foodId=?";
                pst = cn.prepareCall(sql);
                pst.setString(1, foodId);
                pst.executeUpdate();
            }
        } finally {
            if (pst != null) {
                pst.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
    }
//cechke

    public List<FoodDTO> getAdminFoods(int index) throws SQLException {
        List<FoodDTO> list = null;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT foodId,expiredDate, foodName ,image ,description ,price ,isDeleted,quantity,createdDate,categoryId \n"
                        + " FROM tblFood \n"
                        + " order by createdDate \n"
                        + " offset ( ? -1 )*20 rows fetch next 20 rows only";
                pst = cn.prepareStatement(sql);
                pst.setInt(1, index);
                rs = pst.executeQuery();
                while (rs.next()) {
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    String foodId = rs.getString("foodId");
                    String foodName = rs.getString("foodName");
                    String categoryId = rs.getString("categoryId");
                    String description = rs.getString("description");
                    String image = rs.getString("image");
                    boolean isDeleted = rs.getBoolean("isDeleted");
                    float price = rs.getFloat("price");
                    int quantity = rs.getInt("quantity");
                    Date expiredDate = rs.getDate("expiredDate");
                    Date createdDate = rs.getDate("createdDate");
                    FoodDTO dto = new FoodDTO(foodId, categoryId, foodName, description, image, quantity, price, isDeleted, createdDate, expiredDate);
                    list.add(dto);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return list;
    }
//checked
    public void update(FoodDTO dto) throws SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "UPDATE tblFood SET foodName=? , image=? , description=? , price=? , quantity=? , isDeleted=? WHERE foodId=? ";
                pst = cn.prepareStatement(sql);
                pst.setString(1, dto.getFoodName());
                pst.setString(2, dto.getImage());
                pst.setString(3, dto.getDescription());
                pst.setFloat(4, dto.getPrice());
                pst.setInt(5, dto.getQuantity());
                pst.setBoolean(6, dto.isIsDeleted());
                pst.setString(7, dto.getFoodId());
                pst.executeUpdate();
            }
        } finally {
            if (pst != null) {
                pst.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
    }

    public List<FoodDTO> getFoodsBySearch(String foodName, String categoryId, float min, float max, int page) throws SQLException {
        List<FoodDTO> list = null;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT foodId,quantity,price,foodName,description,image FROM tblFood\n"
                        + "WHERE getDate()<=expiredDate and isDeleted=0 and categoryId like ? and foodName like ? and price >= ? and price<= ? \n"
                        + "ORDER BY createdDate\n"
                        + "OFFSET (? -1)*20 ROWS FETCH NEXT 20 ROWS ONLY";
                pst = cn.prepareStatement(sql);
                pst.setString(1, "%" + categoryId + "%");
                pst.setString(2, "%" + foodName + "%");
                pst.setFloat(3, min);
                pst.setFloat(4, max);
                pst.setInt(5, page);
                rs = pst.executeQuery();
                while (rs.next()) {
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    String findedFoodName = rs.getString("foodName");
                    String foodId = rs.getString("foodId");
                    int quantity = rs.getInt("quantity");
                    float price = rs.getFloat("price");
                    String description = rs.getString("description");
                    String image = rs.getString("image");
                    boolean isDeleted = false;
                    FoodDTO dto = new FoodDTO(foodId, categoryId, findedFoodName, description, image, quantity, price, isDeleted);
                    list.add(dto);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return list;
    }
//checked
    public float getMaxPrice() throws SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        float price = 0;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT    max(price) as max \n"
                        + "FROM tblFood";
                pst = cn.prepareStatement(sql);
                rs = pst.executeQuery();
                if (rs.next()) {
                    price = rs.getFloat("max");
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (cn != null) {
                cn.close();
            }
        }

        return price;
    }
//checked
    public float getMinPrice() throws SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        float price = 0;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT    min(price) as min \n"
                        + "FROM tblFood";
                pst = cn.prepareStatement(sql);
                rs = pst.executeQuery();
                if (rs.next()) {
                    price = rs.getFloat("min");
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (cn != null) {
                cn.close();
            }
        }

        return price;
    }
//checked
    public int getCountPageFoodsBySearch(String foodName, String categoryId, float min, float max) throws SQLException {
        int pages = 0;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT  count(foodId) as count from tblFood "
                        + "WHERE getDate() <= expiredDate and isDeleted=0 and categoryId like ? and foodName like ? and price>=? and price<= ? \n";
                pst = cn.prepareStatement(sql);
                pst.setString(1, "%" + categoryId + "%");
                pst.setString(2, "%" + foodName + "%");
                pst.setFloat(3, min);
                pst.setFloat(4, max);

                rs = pst.executeQuery();
                if (rs.next()) {
                    pages = rs.getInt("count");
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return pages;
    }
//checked
    public FoodDTO getFood(String foodId) throws SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        FoodDTO dto = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT foodName,categoryId,description,image,quantity,price,createdDate,expiredDate,createdDate from tblFood where isDeleted=0 and getDate()<= expiredDate and quantity > 0 and foodId=?";
                pst = cn.prepareCall(sql);
                pst.setString(1, foodId);
                rs = pst.executeQuery();
                if (rs.next()) {
                    String foodName = rs.getString("foodName");
                    String description = rs.getString("description");
                    String image = rs.getString("image");
                    int quantity = rs.getInt("quantity");
                    float price = rs.getFloat("price");
                    Date expiredDate = rs.getDate("expiredDate");
                    Date createdDate = rs.getDate("createdDate");
                    String categoryId = rs.getString("categoryId");
                    dto = new FoodDTO(foodId, categoryId, foodName, description, image, quantity, price, false, createdDate, expiredDate);

                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return dto;
    }
//checked
    public int getQuantity(String foodId) throws SQLException {
        int quantity = 0;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT quantity from tblFood where isDeleted=0 and quantity >0 and  getDate()<= expiredDate and foodId=?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, foodId);
                rs = pst.executeQuery();
                if (rs.next()) {
                    quantity = rs.getInt("quantity");
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return quantity;
    }
//checked
    public int getCountPageFoodAdmin() throws SQLException {
        int pages = 0;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT  count(foodId) as count from tblFood ";
                pst = cn.prepareStatement(sql);
                rs = pst.executeQuery();
                if (rs.next()) {
                    pages = rs.getInt("count");
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return pages;
    }
//checked
    public boolean checkExpiredDate(FoodDTO dto) throws SQLException {
        boolean result = false;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "select foodName from tblFood where foodId=? and isDeleted=0 and quantity >0 and getDate()<= ?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, dto.getFoodId());
                pst.setDate(2, dto.getExpiredDate());
                rs = pst.executeQuery();
                if (rs.next()) {
                    result = true;
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return result;
    }

    
    //checked
    public List<FoodDTO> getFoodByCategoy(String categoryId) throws SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<FoodDTO> list = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT top 5 quantity,foodId, foodName,categoryId,description,image,price,createdDate,expiredDate,createdDate from tblFood where isDeleted=0 and getDate()<= expiredDate and quantity > 0 and categoryId like ? ";
                pst = cn.prepareCall(sql);
                pst.setString(1, "%" + categoryId + "%");
                rs = pst.executeQuery();
                while (rs.next()) {
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    String foodName = rs.getString("foodName");
                    String description = rs.getString("description");
                    String image = rs.getString("image");
                    int quantity = rs.getInt("quantity");
                    float price = rs.getFloat("price");
                    Date expiredDate = rs.getDate("expiredDate");
                    Date createdDate = rs.getDate("createdDate");
                    String foodId = rs.getString("foodId");
                    FoodDTO dto = new FoodDTO(foodId, categoryId, foodName, description, image, quantity, price, false, createdDate, expiredDate);
                    list.add(dto);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return list;
    }

}
