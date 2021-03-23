 
package daos;

import dtos.FoodDTO;
import dtos.OrderDTO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import utils.DBUtils;

 
public class OrderDAO {
    //checked
    public int insert(OrderDTO dto) throws SQLException {
        int result = 0;
        Connection cn = null;
        PreparedStatement pst = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "insert tblOrder(payment,boughtDate,totalPrice,address,userName) values(?,?,?,?,?)";
                pst = cn.prepareStatement(sql);
                pst.setString(1, dto.getPayment());
                pst.setDate(2, dto.getBoughtDate());
                pst.setFloat(3, dto.getTotalPrice());
                pst.setString(4, dto.getAddress());
                pst.setString(5, dto.getUserName());
                pst.executeUpdate();
                result = getMax(dto.getBoughtDate(), dto.getUserName());
            }
        } finally {
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
    public int getMax(Date boughtDate, String userName) throws SQLException {
        int result = 0;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "select max(orderId) as Max from tblOrder where boughtDate=? and userName=? ";
                pst = cn.prepareStatement(sql);
                pst.setDate(1, boughtDate);
                pst.setString(2, userName);
                rs = pst.executeQuery();
                if (rs.next()) {
                    result = rs.getInt("Max");
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
    public Map<OrderDTO, List<FoodDTO>> search(String foodName,String userName) throws SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Map<OrderDTO, List<FoodDTO>> map = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT     O.orderId, F.foodName , F.createdDate, F.expiredDate, O.boughtDate, O.address, O.payment, F.image, F.foodId,OD.quantity,OD.price,O.totalPrice,F.categoryId,F.description\n"
                        + "from tblOrder O join tblOrder_Detail  OD on O.orderId=OD.orderId join tblFood F on OD.foodId=F.foodId \n"
                        + "where F.foodName like ? and O.userName=?";
                pst=cn.prepareStatement(sql);
                pst.setString(1, "%"+foodName+"%");
                pst.setString(2, userName);
                rs=pst.executeQuery();
                while(rs.next()){
                    if(map==null){
                        map=new HashMap<>();
                    }
                    List<FoodDTO> list=null;
                    int orderId=rs.getInt("orderId");
                    String address=rs.getString("address");
                    String payment=rs.getString("payment");
                    float totalPrice=rs.getFloat("totalPrice");
                    Date boughtDate=rs.getDate("boughtDate");
                    String foodId=rs.getString("foodId");
                    String newFoodName=rs.getString("foodName");
                    int quantity=rs.getInt("quantity");
                    float price=rs.getFloat("price");
                    String image=rs.getString("image");
                    String categoryId=rs.getString("categoryId");
                    Date createdDate=rs.getDate("createdDate");
                    Date expiredDate=rs.getDate("expiredDate");
                    String description=rs.getString("description");
                    OrderDTO key=new OrderDTO(orderId, boughtDate, "", address, payment, totalPrice);
                    FoodDTO dto=new FoodDTO(foodId, categoryId, newFoodName, description, image, quantity, price, false,createdDate, expiredDate);
                    if(map.containsKey(key)){
                        list=map.get(key);
                    }
                    if(list==null){
                        list=new ArrayList();
                    }
                    list.add(dto);
                    map.put(key, list);
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
        return map;
    }
    //checkd
     public Map<OrderDTO, List<FoodDTO>> searchNameDate(String foodName,Date date) throws SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Map<OrderDTO, List<FoodDTO>> map = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT     O.orderId, F.foodName , F.createdDate, F.expiredDate, O.boughtDate, O.address, O.payment, F.image, F.foodId,OD.quantity,OD.price,O.totalPrice,F.categoryId,F.description\n"
                        + "from tblOrder O join tblOrder_Detail  OD on O.orderId=OD.orderId join tblFood F on OD.foodId=F.foodId \n"
                        + "where F.foodName like ? and boughtDate=?";
                pst=cn.prepareStatement(sql);
                pst.setString(1, "%"+foodName+"%");
                pst.setDate(2, date);
                rs=pst.executeQuery();
                while(rs.next()){
                    if(map==null){
                        map=new HashMap<>();
                    }
                    List<FoodDTO> list=null;
                    int orderId=rs.getInt("orderId");
                    String address=rs.getString("address");
                    String payment=rs.getString("payment");
                    float totalPrice=rs.getFloat("totalPrice");
                    Date boughtDate=rs.getDate("boughtDate");
                    String foodId=rs.getString("foodId");
                    String newFoodName=rs.getString("foodName");
                    int quantity=rs.getInt("quantity");
                    float price=rs.getFloat("price");
                    String image=rs.getString("image");
                    String categoryId=rs.getString("categoryId");
                    Date createdDate=rs.getDate("createdDate");
                    Date expiredDate=rs.getDate("expiredDate");
                    String description=rs.getString("description");
                    OrderDTO key=new OrderDTO(orderId, boughtDate, "", address, payment, totalPrice);
                    FoodDTO dto=new FoodDTO(foodId, categoryId, newFoodName, description, image, quantity, price, false,createdDate, expiredDate);
                    if(map.containsKey(key)){
                        list=map.get(key);
                    }
                    if(list==null){
                        list=new ArrayList();
                    }
                    list.add(dto);
                    map.put(key, list);
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
        return map;
    }
    
}
