/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.RecordDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import utils.DBUtils;

/**
 *
 * @author nguye
 */
public class RecordDAO {
    //checked
    public void insert(RecordDTO dto) throws SQLException{
        Connection cn=null;
        PreparedStatement pst=null;
        try {
            cn=DBUtils.getConnection();
            if(cn!=null){
                String sql="INSERT tblRecord(userName,foodId,action,actionDate) values(?,?,?,?)";
                pst=cn.prepareStatement(sql);
                pst.setString(1, dto.getUserName());
                pst.setString(2, dto.getFoodId());
                pst.setString(3, dto.getAction());
                pst.setDate(4, dto.getActionDate());
                pst.executeUpdate();
            }
        } finally{
            if(pst!=null){
                pst.close();
            }
            if(cn!=null){
                cn.close();
            }
        }
    }
}
