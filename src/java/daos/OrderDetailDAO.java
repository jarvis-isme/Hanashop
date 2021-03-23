/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.OrderDetailDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import utils.DBUtils;

/**
 *
 * @author nguye
 */
public class OrderDetailDAO {
    //checked
    public void insert(OrderDetailDTO dto) throws SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "insert tblOrder_Detail(orderId,quantity,price,foodId) values(?,?,?,?)";
                pst = cn.prepareStatement(sql);
                pst.setInt(1, dto.getOrderId());
                pst.setInt(2, dto.getQuantity());
                pst.setFloat(3, dto.getPrice());
                pst.setString(4, dto.getFoodId());
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

 
}
