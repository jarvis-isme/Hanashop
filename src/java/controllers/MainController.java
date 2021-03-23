/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Nguyen Dan
 */
@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
//Nguyeenx Ddnagw 
public class MainController extends HttpServlet {
//checked
    private static final String ERORR = "error.jsp";
    private static final String LOGIN = "LoginController";
    private static final String LOGIN_PAGE = "index.jsp";
    private static final String HOME_PAGE = "home.jsp";
    private static final String LOGOUT = "LogoutController";
    private static final String ADD_FOOD_PAGE = "ShowAddFoodPageControlller";
    private static final String ADD_FOOD = "AddFoodController";
    private static final String MANAGE_PAGE = "ShowManageFoodPageController";
    private static final String UPDATE_FOOD = "UpdateFoodController";
    private static final String DELETE_FOOD = "DeleteFoodController";
    private static final String SHOPPING = "SearchController";
    private static final String ADD_CART = "AddCartController";
    private static final String CART_PAGE = "ShowCartPageController";
    private static final String UPDATE_CART = "UpdateCartController";
    private static final String DELETE_CART = "DeleteCartController";
    private static final String CHECK_OUT = "CheckOutController";
    private static final String SEARCH_ORDER="SearchOrderController";
    private static final String LOGIN_GOOGLE="LoginGoogleController";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERORR;
        try {
            String action = request.getParameter("btnAction");
            if (action == null) {
                action = "ShowHomePage";
            }
            if ("Login".equals(action)) {
                url = LOGIN;
            } else if ("Logout".equals(action)) {
                url = LOGOUT;
            } else if ("ShowLoginPage".equals(action)) {
                url = LOGIN_PAGE;
            } else if ("ShowHomePage".equals(action)) {
                url = HOME_PAGE;
            } else if ("ShowAddFoodPage".equals(action)) {
                url = ADD_FOOD_PAGE;
            } else if ("Add".equals(action)) {
                url = ADD_FOOD;
            } else if ("ShowManageFoodPage".equals(action)) {
                url = MANAGE_PAGE;
            } else if ("Update Food".equals(action)) {
                url = UPDATE_FOOD;
            } else if ("Delete Selected".equals(action)) {
                url = DELETE_FOOD;
            } else if ("search".equals(action)) {
                url = SHOPPING;
            } else if ("AddToCart".equals(action)) {
                url = ADD_CART;
            } else if ("ShowCartPage".equals(action)) {
                url = CART_PAGE;
            } else if ("Update".equals(action)) {
                url = UPDATE_CART;
            } else if ("Delete Cart".equals(action)) {
                url = DELETE_CART;
            } else if ("Buy".equals(action)) {
                url = CHECK_OUT;
            }else if("SearchOrder".equals(action)){
                url=SEARCH_ORDER;
            }else if("LoginGoogle".equals(action)){
                url=LOGIN_GOOGLE;
            }
        } catch (Exception e) {
            log("Error at MainController:" + e.toString());
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
