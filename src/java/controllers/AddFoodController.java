/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import daos.CategoryDAO;
import daos.FoodDAO;
import dtos.FoodDTO;
import dtos.FoodErrorDTO;
import dtos.UserDTO;
import java.io.IOException;
import java.sql.Date;
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
//checked
@WebServlet(name = "AddFoodController", urlPatterns = {"/AddFoodController"})
public class AddFoodController extends HttpServlet {

    private final static String ERROR = "addfoodpage.jsp";
    private final static String SUCCESS = "ShowManageFoodPageController";
    private final static String ADMIN = "AD";
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
                request.setAttribute("LOGIN_ERROR", "You must to login !");
            } else if (!user.getRoleId().equals(ADMIN)) {
                url = HOME_PAGE;
            } else {
                String foodName = request.getParameter("txtFoodName");
                String foodId = request.getParameter("txtFoodId");
                String quantity = request.getParameter("txtQuantity");
                String description = request.getParameter("txtDescription");
                String price = request.getParameter("txtPrice");
                String categoryId = request.getParameter("txtCategory");
                String image = request.getParameter("txtImage");
                String textExpiredDate = request.getParameter("txtExpiredDate");
                FoodErrorDTO foodError = new FoodErrorDTO("", "", "", "", "", "", "","", "");
                FoodDAO dao = new FoodDAO();
                CategoryDAO categoryDao = new CategoryDAO();
                boolean check = true;
                if (foodName.trim().isEmpty() || foodName.length() > 50) {
                    foodError.setFoodNameError("foodName's length 0-50 charaters");
                    check = false;
                }
                if (foodId.trim().isEmpty() || foodId.length() > 50) {
                    check = false;
                    foodError.setFoodIdError("foodId's length 0-50 characters");
                } else if (dao.checkIdDuplicate(foodId)) {
                    check = false;
                    foodError.setFoodIdError("foodId was duplicated");
                }
                if (!categoryDao.checkIdExist(categoryId)) {
                    foodError.setCategoryIdError("Category Id you choose not exist");
                    check = false;
                }
                if (image.trim().isEmpty()) {
                    foodError.setImageError("Image can not empty");
                    check = false;
                } else if (!(image.contains(".jpg") || image.contains(".png") || image.contains(".jpeg")  || image.contains(".jfif") )) {
                    foodError.setImageError("Image is not valid");
                    check = false;
                }
                if (description.trim().isEmpty() || description.length() > 50) {
                    foodError.setDescriptionError("Description's length :0-50");
                    check = false;
                }
                if (price.trim().isEmpty()) {
                    foodError.setPriceError("Price can not empty");
                    check = false;
                } else if (!price.matches("^([-]{0,1}[0-9]{1,}|[-]{0,1}[0-9]{1,}.[0-9]{1,2})$")) {
                    foodError.setPriceError("Price must be number");
                    check = false;
                } else if (Float.parseFloat(price) <= 0) {
                    foodError.setPriceError("Price must be more than 0");
                    check = false;
                }

                if (quantity.trim().isEmpty()) {
                    foodError.setQuantityError("Quantity can not empty");
                    check = false;
                } else if (!quantity.matches("^[-]{0,1}[0-9]{1,}$")) {
                    foodError.setQuantityError("Quantity must be number");
                    check = false;
                } else if (Integer.parseInt(quantity) <= 0) {
                    foodError.setQuantityError("Quantity must be more than 0");
                    check = false;
                }
                if (textExpiredDate.trim().isEmpty()) {
                    foodError.setExpiredDatedError("ExpiredDate can not empty");
                    check = false;
                } else if (!textExpiredDate.matches("^[-]{0,1}[0-9]{1,}$")) {
                    foodError.setExpiredDatedError("Expired Date must be number");
                    check = false;
                } else if (Integer.parseInt(textExpiredDate) <= 0) {
                    foodError.setExpiredDatedError("Expired must be more than 0");
                    check = false;
                }
                if (check) {
                    long current = System.currentTimeMillis();
                    Date date = new Date(current);
                    Date expiredDate = new Date(current + 1000*24*3600*Integer.parseInt(textExpiredDate));
                    FoodDAO foodDao = new FoodDAO();
                    FoodDTO dto = new FoodDTO(foodId, categoryId, foodName, description, image, Integer.parseInt(quantity), Float.parseFloat(price), false, date, expiredDate);
                    foodDao.addFood(dto);
                    url = SUCCESS;
                } else {
                    request.setAttribute("FOOD_ERROR", foodError);
                }

            }
        } catch (Exception e) {
            log("Error at AddFoodController:" + e.toString());
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
