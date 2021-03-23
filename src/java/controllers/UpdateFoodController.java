/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import daos.CategoryDAO;
import daos.FoodDAO;
import daos.RecordDAO;
import dtos.FoodDTO;
import dtos.FoodErrorDTO;
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

/**
 *
 * @author nguye
 */
@WebServlet(name = "UpdateFoodController", urlPatterns = {"/UpdateFoodController"})
public class UpdateFoodController extends HttpServlet {

    private final static String ERROR = "admin.jsp";
    private final static String SUCCESS = "ShowManageFoodPageController";
    private final static String ADMIN = "AD";
    private final static String HOME_PAGE = "home.jsp";
    private final static String LOGIN_PAGE = "index.jsp";

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
                String foodId = request.getParameter("txtFoodId");
                String foodName = request.getParameter("txtFoodName");
                String image = request.getParameter("txtImage");
                String isDeleted = request.getParameter("txtIsDeleted");
                String categoryId = request.getParameter("txtCategory");
                String quantity = request.getParameter("txtQuantity");
                String price = request.getParameter("txtPrice");
                String description = request.getParameter("txtDescription");
                FoodErrorDTO foodError = new FoodErrorDTO("", "", "", "", "", "", "", "", "");
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
                } else if (!dao.checkIdDuplicate(foodId)) {
                    check = false;
                    foodError.setFoodIdError("foodId was not exist");
                }
                if (!categoryDao.checkIdExist(categoryId)) {
                    foodError.setCategoryIdError("Category Id you choose not exist");
                    check = false;
                }
                if (image.trim().isEmpty()) {
                    foodError.setImageError("Image can not empty");
                    check = false;
                } else if (!(image.contains(".jpg") || image.contains(".png") || image.contains(".jpeg") || image.contains(".jfif"))) {
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
                if (check) {
                    FoodDTO dto = new FoodDTO(foodId, categoryId, foodName, description, image, Integer.parseInt(quantity), Float.parseFloat(price), Boolean.parseBoolean(isDeleted));
                    dao.update(dto);
                    long date = System.currentTimeMillis();
                    Date actionDate = new Date(date);
                    RecordDTO record = new RecordDTO(user.getUserName(), foodId, "Update", actionDate);
                    RecordDAO recordDao =new RecordDAO();
                    recordDao.insert(record);
                    url=SUCCESS;
                } else {
                    request.setAttribute("UPDATE_ERROR", foodError);
                }
            }
        } catch (Exception e) {
            log("Error at UpdateFoodController:" + e.toString());
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
