/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.CategoryDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import utils.DBUtils;

/**
 *
 * @author nguye
 */
public class CategoryDAO {

    public ArrayList<CategoryDTO> getCategories() throws SQLException {
        ArrayList<CategoryDTO> list = null;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT categoryId,categoryName from tblCategory where isDeleted=0";
                pst = cn.prepareStatement(sql);
                rs = pst.executeQuery();
                while (rs.next()) {
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    String categoryId = rs.getString("categoryId");
                    String categoryName = rs.getString("categoryName");
                    CategoryDTO obj = new CategoryDTO(categoryName, categoryId, false);
                    list.add(obj);
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
    public boolean checkIdExist(String categoryId) throws SQLException {
        boolean result = false;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "Select categoryName from tblCategory where categoryId=? and isDeleted=0";
                pst = cn.prepareStatement(sql);
                pst.setString(1, categoryId);
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
    public String getCategoryUserOftenBuy(String userName) throws SQLException {
        String result = "";
        int max = -1;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "select F.categoryId, sum(OD.quantity) as totalMax,O.userName\n"
                        + "from tblFood F join tblOrder_Detail OD on F.foodId=OD.foodId join tblOrder O on OD.orderId=O.orderId\n"
                        + "group by F.categoryId ,O.userName\n"
                        + " having O.userName= ? ";
                pst = cn.prepareStatement(sql);
                pst.setString(1, userName);
                rs = pst.executeQuery();
                while (rs.next()) {
                    int totalMax = rs.getInt("totalMax");
                    if (totalMax > max) {
                        result = rs.getString("categoryId");
                    }
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
}
