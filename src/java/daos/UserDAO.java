package daos;

import dtos.UserDTO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.DBUtils;

public class UserDAO {
//check
    public UserDTO getUser(String userName, String password) throws SQLException, ClassNotFoundException {
        UserDTO dto = null;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String url = "SELECT fullName,dob,roleId,address,phoneNumber FROM tblUser WHERE userName=? AND password=?";
                pst = cn.prepareStatement(url);
                pst.setString(1, userName);
                pst.setString(2, password);
                rs = pst.executeQuery();
                if (rs.next()) {
                    String fullName = rs.getString("fullName");
                    String roleId = rs.getString("roleId");
                    String phoneNumber = rs.getString("phoneNumber");
                    String address = rs.getString("address");
                    Date dob = rs.getDate("dob");
                    dto = new UserDTO(fullName, userName, "***", phoneNumber, address, roleId, dob);
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
//check
    public boolean checkLogin(String userName, String password) throws SQLException, ClassNotFoundException {
        boolean result = false;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String url = "SELECT fullName FROM tblUser WHERE userName=? AND password=?";
                pst = cn.prepareStatement(url);
                pst.setString(1, userName);
                pst.setString(2, password);
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
    public boolean checkUserNameDuplicate(String userName) throws SQLException, ClassNotFoundException {
        boolean result = false;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String url = "SELECT fullName FROM tblUser WHERE userName=? ";
                pst = cn.prepareStatement(url);
                pst.setString(1, userName);
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
    public void insert(UserDTO dto) throws SQLException, ClassNotFoundException {
        Connection cn = null;
        PreparedStatement pst = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String url = "insert tblUser(userName,fullName,password,address,roleId,dob) values(?,?,?,?,?,?)";
                pst = cn.prepareStatement(url);
                pst.setString(1, dto.getUserName());
                pst.setString(2, dto.getFullName());
                pst.setString(3, dto.getPassword());
                pst.setString(4, dto.getAddress());
                pst.setString(5, dto.getRoleId());
                pst.setDate(6, dto.getDob());
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
