/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import daos.FoodDAO;
import daos.OrderDAO;
import daos.OrderDetailDAO;
import dtos.CartDTO;
import dtos.FoodDTO;
import dtos.OrderDTO;
import dtos.OrderDetailDTO;
import dtos.UserDTO;
import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author nguye
 */
@WebServlet(name = "CheckOutController", urlPatterns = {"/CheckOutController"})
public class CheckOutController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCESS = "cart.jsp";
    private static final String USER = "US";
    private static final String LOGIN_PAGE = "index.jsp";
    private static final String HOME_PAGE = "home.jsp";
//checked

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            UserDTO user = (UserDTO) session.getAttribute("USER");
            if (user == null) {
                url = LOGIN_PAGE;
            } else if (!user.getRoleId().equals(USER)) {
                url = HOME_PAGE;
            } else {
                boolean check = true;
                Map<String, String> mapError = new HashMap<>();
                CartDTO cart = (CartDTO) session.getAttribute("CART");
                FoodDAO dao = new FoodDAO();
                String address = request.getParameter("txtAddress");
                float totalPrice = 0;
                if (address.trim().isEmpty()) {
                    request.setAttribute("ADDRESS_ERROR", "can not empty");
                    check = false;
                }
                //check Expired and isDelete
                for (String key : cart.getCart().keySet()) {
                    FoodDTO obj = cart.getCart().get(key);
                    boolean result = dao.checkExpiredDate(obj);
                    int currentQuantity = dao.getQuantity(obj.getFoodId());
                    totalPrice += obj.getPrice() * obj.getQuantity();
                    String error = "";
                    if (!result) {
                        check = false;
                        error = "Product can not buy you must delete";
                    } else {
                        if (currentQuantity < obj.getQuantity()) {
                            check = false;
                            error += "Product's quantity have" + currentQuantity;
                        }
                    }
                    if (!error.isEmpty()) {
                        mapError.put(obj.getFoodId(), error);
                    }
                }
                if (check) {
                    long time = System.currentTimeMillis();
                    Date currentDate = new Date(time);
                    String payment = "Cash On upon delivery";
                    OrderDTO order = new OrderDTO(0, currentDate, user.getUserName(), address, payment, totalPrice);
                    OrderDAO orderDAO = new OrderDAO();
                    int orderId = orderDAO.insert(order);
                    for (String key : cart.getCart().keySet()) {
                        FoodDTO food = cart.getCart().get(key);
                        OrderDetailDTO orderDetail = new OrderDetailDTO(orderId, food.getQuantity(), food.getFoodId(), food.getPrice());
                        OrderDetailDAO detailDao = new OrderDetailDAO();
                        detailDao.insert(orderDetail);
                    }
                    session.setAttribute("CART", null);
                } else {
                    request.setAttribute("CHECKOUT_ERROR", mapError);
                }
                url = SUCESS;
            }
        } catch (Exception e) {
            log("Error at CheckOut" + e.toString());
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
