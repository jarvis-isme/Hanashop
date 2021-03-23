/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import daos.FoodDAO;
import dtos.CartDTO;
import dtos.FoodDTO;
import dtos.UserDTO;
import java.io.IOException;
import java.util.List;
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
@WebServlet(name = "DeleteCartController", urlPatterns = {"/DeleteCartController"})
public class DeleteCartController extends HttpServlet {
//checked
    private final static String ERROR = "error.jsp";
    private final static String SUCCESS = "cart.jsp";
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
                String foodId = request.getParameter("txtFoodId");
                CartDTO cart = (CartDTO) session.getAttribute("CART");
                List<FoodDTO> list = (List<FoodDTO>) session.getAttribute("FOOD_LIST_USER");
                FoodDAO dao = new FoodDAO();
                if (cart != null) {
                    if (cart.getCart().containsKey(foodId)) {
                        cart.delete(foodId);
                    }
                    for (FoodDTO obj : list) {
                        if (obj.getFoodId().equals(foodId)) {
                            int newQuantity = dao.getQuantity(foodId);
                            obj.setQuantity(newQuantity);
                        }
                    }
                }
                if (cart.getCart() == null) {
                    cart = null;
                }
                session.setAttribute("CART", cart);
                session.setAttribute("FOOD_LIST_USER", list);
                url = SUCCESS;
            }
        } catch (Exception e) {
            log("Error at DeleteCartController:" + e.toString());
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
