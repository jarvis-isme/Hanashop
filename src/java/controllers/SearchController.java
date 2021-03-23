/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import daos.CategoryDAO;
import daos.FoodDAO;
import dtos.CartDTO;
import dtos.CategoryDTO;
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
@WebServlet(name = "SearchController", urlPatterns = {"/SearchController"})
public class SearchController extends HttpServlet {
//checked
    private final static String ERROR = "error.jsp";
    private final static String SUCCESS = "shopping.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String page = request.getParameter("page");
            String txtMin = request.getParameter("min");
            String txtMax = request.getParameter("max");
            String categoryId = request.getParameter("txtCategoryId");
            String foodName = request.getParameter("txtFoodName");
            boolean check = true;
            float min, max;
            if (page == null) {
                page = "1";
            }
            if (txtMin == null) {
                txtMin = "";
            }
            if (txtMax == null) {
                txtMax = "";
            }
            if (categoryId == null) {
                categoryId = "";
            }
            if (foodName == null) {
                foodName = "";
            }
            if (!txtMin.isEmpty()) {
                if (!txtMin.matches("^[0-9]{1,}$")) {
                    check = false;
                }
            }
            if (!txtMax.isEmpty()) {
                if (!txtMax.matches("^[0-9]{1,}$")) {
                    check = false;
                }
            }
            if (page.contains("''") || foodName.contains("''") || categoryId.contains("''") || txtMin.contains("''") || txtMax.contains("''")) {
                check = false;
            }
            if (!page.matches("^[0-9]{1,}$")) {
                check = false;
            }
            HttpSession session = request.getSession();
            List<FoodDTO> list = null;
            int pages = 0;
            if (check) {
                FoodDAO dao = new FoodDAO();
                if (txtMin.trim().isEmpty()) {
                    min = dao.getMinPrice();
                } else {
                    min = Float.parseFloat(txtMin);
                }
                if (txtMax.trim().isEmpty()) {
                    max = dao.getMaxPrice();
                } else {
                    max = Float.parseFloat(txtMax);
                }
                list = dao.getFoodsBySearch(foodName, categoryId, min, max, Integer.parseInt(page));
                pages = dao.getCountPageFoodsBySearch(foodName, categoryId, min, max);
            }

            CategoryDAO cateDao = new CategoryDAO();
            CartDTO cart = (CartDTO) session.getAttribute("CART");
            //set again quantity

            if (cart != null) {
                if (list != null) {
                    for (FoodDTO obj : list) {
                        if (cart.getCart().containsKey(obj.getFoodId())) {
                            int quantity = obj.getQuantity();
                            quantity = quantity - cart.getCart().get(obj.getFoodId()).getQuantity();
                            obj.setQuantity(quantity);
                        }
                    }
                }
            }
            //recomen
            //get categoryId ng du like nhta
            String hobby = "";
            UserDTO user = (UserDTO) session.getAttribute("USER");
            if (user != null) {
                hobby = cateDao.getCategoryUserOftenBuy(user.getUserName());
            }  
            List<FoodDTO> userLike=null;
            FoodDAO foodDao=new FoodDAO();
            userLike=(List<FoodDTO>) foodDao.getFoodByCategoy(hobby);
            session.setAttribute("USER_LIKE_LIST",userLike);
            List<CategoryDTO> listCate = cateDao.getCategories();
            pages = ((pages % 20) == 0) ? (pages / 20) : (pages / 20 + 1);
            session.setAttribute("CATEGORY_LIST_USER", listCate);
            session.setAttribute("FOOD_LIST_USER", list);
            session.setAttribute("COUNT_PAGE", pages);
            url = SUCCESS;
        } catch (Exception e) {
            log("Errort at SearchController" + e.toString());
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
