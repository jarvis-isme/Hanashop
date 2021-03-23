/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import daos.OrderDAO;
import dtos.FoodDTO;
import dtos.OrderDTO;
import dtos.UserDTO;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utils.Validation;

/**
 *
 * @author nguye
 */
@WebServlet(name = "SearchOrderController", urlPatterns = {"/SearchOrderController"})
public class SearchOrderController extends HttpServlet {

    private final static String ERROR = "error.jsp";
    private final static String SUCESS = "history.jsp";
    private final static String USER = "US";
    private final static String LOGIN_PAGE = "index.jsp";
    private final static String HOME_PAGE = "home.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            UserDTO user = (UserDTO) session.getAttribute("USER");
            if (user == null) {
                url = LOGIN_PAGE;
            } else if (!USER.equals(user.getRoleId())) {
                url = HOME_PAGE;
            } else {
                String foodName = request.getParameter("txtFoodName");
                String day = request.getParameter("txtDay");
                String month = request.getParameter("txtMonth");
                String year = request.getParameter("txtYear");
                String date = "";
                boolean check = true;
                Map<OrderDTO, List<FoodDTO>> map = null;
                if (year == null) {
                    year = "";
                }
                if (month == null) {
                    month = "";
                }
                if (day == null) {
                    day = "";
                }
                if (foodName == null) {
                    foodName = "";
                }
                if(foodName.contains("'")){
                    check=false;
                }
                date = year + "-" + month + "-" + day;
                if (!date.matches("^--$")) {
                    if (!Validation.checkFormatdate(date)) {
                        check = false;
                    } else if (!Validation.checkValidDate(date)) {
                        check = false;
                    }
                }
                if (check) {
                    OrderDAO dao = new OrderDAO();
                    if (date.matches("^--$")) {
                        map = dao.search(foodName,user.getUserName()    );

                    } else {
                        Date searchedDate = new Date(Validation.convertStringToDate(date).getTime());
                        map = dao.searchNameDate(foodName, searchedDate);
                    }
                }
                session.setAttribute("ORDER_HISTORY_LIST", map);
                url = SUCESS;
            }
        } catch (Exception e) {
            log("SearchOrderController:" + e.toString());
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
