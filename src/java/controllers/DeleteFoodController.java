/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import daos.FoodDAO;
import daos.RecordDAO;
import dtos.RecordDTO;
import dtos.UserDTO;
import java.io.IOException;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "DeleteFoodController", urlPatterns = {"/DeleteFoodController"})
public class DeleteFoodController extends HttpServlet {
//checked
    private final static String ERROR = "error.jsp";
    private final static String SUCCESS = "ShowManageFoodPageController";
    private final static String LOGIN_PAGE = "index.jsp";
    private final static String HOME_PAGE = "home.jsp";
    private final static String ADMIN = "AD";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            UserDTO user = (UserDTO) session.getAttribute("USER");
            if (user == null) {
                url = LOGIN_PAGE;
            } else if (!ADMIN.equals(user.getRoleId())) {
                url = HOME_PAGE;
            } else {
                String foodIds = request.getParameter("listFoodId");

                FoodDAO dao = new FoodDAO();
                if (foodIds != null) {
                    String[] listFoodId = foodIds.split(";");
                    RecordDAO recordDao = new RecordDAO();
                    for (String foodId : listFoodId) {
                        if (!foodId.trim().isEmpty()) {
                            dao.deleteFood(foodId);
                            long date = System.currentTimeMillis();
                            Date actionDate = new Date(date);
                            RecordDTO record = new RecordDTO(user.getUserName(), foodId, "Delete", actionDate);
                            recordDao.insert(record);
                        }
                    }
                }
                url = SUCCESS;
            }
        } catch (Exception e) {
            log("Error at DeleteFoodController:" + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
