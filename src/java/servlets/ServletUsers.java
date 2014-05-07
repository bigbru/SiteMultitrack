package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utilisateurs.gestionnaires.GestionnaireUtilisateurs;
import utilisateurs.modeles.Adresse;
import utilisateurs.modeles.Utilisateur;

/**
 *
 * @author michel
 */
@WebServlet(name = "ServletUsers", urlPatterns = {"/ServletUsers"})
public class ServletUsers extends HttpServlet {

    // ici injection de code ! On n'initialise pas !
    @EJB
    private GestionnaireUtilisateurs gestionnaireUtilisateurs;
    private int currentPagination = 0;
    Utilisateur loggedUser = null;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "testForLogin":
                    if (loggedUser != null) {
                        out.print(gestionnaireUtilisateurs.listUsersToJson(gestionnaireUtilisateurs.getUsers(currentPagination)));
                    } else {
                        out.print(false);
                    }
                    break;

                case "loginUtilisateur":
                    Utilisateur userLog = gestionnaireUtilisateurs.testLogin(request.getParameter("loginSession"), request.getParameter("passwordSession"));
                    if (userLog != null) {
                        loggedUser = userLog;
                        out.print(gestionnaireUtilisateurs.listUsersToJson(gestionnaireUtilisateurs.getUsers(currentPagination)));
                    } else {
                        out.print(false);
                    }
                    break;

                case "deconnexion":
                    loggedUser = null;
                    break;
                    
                case "signUpUser": {
                    Adresse a = gestionnaireUtilisateurs.creeAdresse(request.getParameter("ville"), request.getParameter("cp"), request.getParameter("num"), request.getParameter("rue"));
                    Utilisateur u = gestionnaireUtilisateurs.creeUtilisateur(request.getParameter("nom"), request.getParameter("prenom"), request.getParameter("login"), request.getParameter("mail"), request.getParameter("tel"), request.getParameter("pass") , a);
                    if (u != null) {
                        loggedUser = u;
                        out.print(gestionnaireUtilisateurs.listUsersToJson(gestionnaireUtilisateurs.getUsers(currentPagination)));
                    } else {
                        out.print(false);
                    }
                    break;
                }
            }
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
