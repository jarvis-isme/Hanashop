/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import daos.CategoryDAO;
import daos.FoodDAO;
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

@WebServlet(name = "ShowManageFoodPageController", urlPatterns = {"/ShowManageFoodPageController"})
public class ShowManageFoodPageController extends HttpServlet {

    private final static String ERORR = "error.jsp";
    private final static String SUCCESS = "admin.jsp";
    private final static String LOGIN_PAGE = "index.jsp";
    private final static String HOME_PAGE = "home.jsp";
    private final static String ADMIN="AD";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERORR;
        try {
            HttpSession session = request.getSession();
            UserDTO user = (UserDTO) session.getAttribute("USER");
            if (user == null) {
                url = LOGIN_PAGE;
            } else if (!ADMIN.equals(user.getRoleId())) {
                url=HOME_PAGE;
            } else {
                FoodDAO dao = new FoodDAO();
                String index = request.getParameter("index");
                List<FoodDTO> list=null;
                if (index == null) {
                    index = "1";
                }
                if(index.matches("^[0-9]{1,}$")) {
                    list=dao.getAdminFoods(Integer.parseInt(index));
                }
                session.setAttribute("FOOD_LIST_ADMIN", list);
                List<CategoryDTO> listCate=(List<CategoryDTO>) session.getAttribute("CATEGORY_LIST");
                if(listCate==null){
                    CategoryDAO cateDao= new CategoryDAO();
                    listCate=cateDao.getCategories();
                }
                int pages=dao.getCountPageFoodAdmin();
                session.setAttribute("COUNT_PAGE_ADMIN", ((pages%20)==0)? ( pages/20) :(pages/20 +1));
                session.setAttribute("CATEGORY_LIST", listCate);
                url=SUCCESS;
            }
        } catch (Exception e) {
            log("Error at ShowManageFoodPageController:" + e.toString());
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
