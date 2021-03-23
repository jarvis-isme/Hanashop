package controllers;

import daos.UserDAO;
import dtos.UserDTO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {
//checked
    private final static String ERROR = "index.jsp";
    private final static String US = "home.jsp";
    private final static String AD = "ShowManageFoodPageController";
    private final static String USER = "US";
    private final static String ADMIN = "AD";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String userName = request.getParameter("txtUserName");
            String password = request.getParameter("txtPassword");
            UserDTO dto=null;
            UserDAO dao = new UserDAO();
            if ("".equals(userName.trim()) || "".equals(password.trim())) {
                request.setAttribute("LOGIN_ERROR", "Username/Password can not empty");
            } else {
                boolean result = dao.checkLogin(userName, password);
                if (result) {
                                HttpSession session = request.getSession();
                    dto = dao.getUser(userName, password);
                    if ((!dto.getRoleId().equals(ADMIN) && (!dto.getRoleId().equals(USER)))) {
                        request.setAttribute("LOGIN_ERROR", "Your acount's role is not valid");
                    } else {

                        session.setAttribute("USER", dto);
                        if (dto.getRoleId().equals(ADMIN)) {
                            url = AD;

                        } else {
                            url = US;
                        }
                    }
                } else {
                    request.setAttribute("LOGIN_ERROR", "Username/Password is wrong");
                }
            }
        } catch (Exception e) {
            log("Error at LoginController:" + e.toString());
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
