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
@WebServlet(name = "AddCartController", urlPatterns = {"/AddCartController"})
public class AddCartController extends HttpServlet {
//checked 
    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "shopping.jsp";
    private static final String USER = "US";
    private static final String HOME_PAGE = "home.jsp";
    private static final String LOGIN_PAGE = "index.jsp";

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
                CartDTO cart = (CartDTO) session.getAttribute("CART");
                if (cart == null) {
                    cart = new CartDTO();
                }

                List<FoodDTO> listFood = (List<FoodDTO>) session.getAttribute("FOOD_LIST_USER");
                String foodId = request.getParameter("txtFoodId");
                FoodDAO dao = new FoodDAO();
                FoodDTO dto = null;
                boolean check = true;
                if (dao.checkIdFoodCanBuy(foodId)) {
                    dto = dao.getFood(foodId);
                    dto.setQuantity(1);
                } else {
                    check = false;
                }
                for (FoodDTO obj : listFood) {
                    if (obj.getFoodId().equals(foodId)) {
                        if (obj.getQuantity() > 0) {
                            int quantity = obj.getQuantity() - 1;
                            obj.setQuantity(quantity);
                        } else {
                            check = false;
                        }
                    }
                }
                session.setAttribute("FOOD_LIST_USER", listFood);
                if (check) {
                    cart.add(dto);
                }
                session.setAttribute("CART", cart);
                url = SUCCESS;
            }
        } catch (Exception e) {
            log("Error at AddCartController" + e.toString());
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
