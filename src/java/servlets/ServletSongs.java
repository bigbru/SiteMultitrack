/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utilisateurs.gestionnaires.GestionnaireSongs;
import utilisateurs.modeles.Chanson;

/**
 *
 * @author Dam
 */
public class ServletSongs extends HttpServlet {

    // ici injection de code ! On n'initialise pas !
    @EJB
    private GestionnaireSongs gestionnaireSongs;
    private int currentPagination = 0;

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
                case "getSongs":
                    out.print(gestionnaireSongs.listSongsToJson(gestionnaireSongs.getSongs(currentPagination)));
                    break;

                case "chercherChanson": {
                    String str = request.getParameter("str");
                    Collection<Chanson> liste = gestionnaireSongs.getSongsByStr(str);
                    currentPagination = 0;
                    if (liste.size() <= 0) {
                        out.print(false);
                    } else {
                        out.print(gestionnaireSongs.listSongsToJson(liste));
                    }
                    break;
                }

                case "nextPage": {
                    if (gestionnaireSongs.getSongs(currentPagination + 1).size() > 0) {
                        currentPagination++;
                        Collection<Chanson> liste = gestionnaireSongs.getSongs(currentPagination);
                        out.print(gestionnaireSongs.listSongsToJson(liste));
                    } else {
                        out.print(false);
                    }
                    break;
                }
                
                case "prevPage": {
                    if (currentPagination > 0) {
                        currentPagination--;
                        Collection<Chanson> liste = gestionnaireSongs.getSongs(currentPagination);
                        out.print(gestionnaireSongs.listSongsToJson(liste));
                    } else {
                        currentPagination = 0;
                        Collection<Chanson> liste = gestionnaireSongs.getSongs(currentPagination);
                        out.print(gestionnaireSongs.listSongsToJson(liste));
                    }
                    break;
                }

                case "refreshList": {
                    currentPagination = 0;
                    Collection<Chanson> liste = gestionnaireSongs.getSongs(currentPagination);
                    out.print(gestionnaireSongs.listSongsToJson(liste));
                    break;
                }
                
                case "suscribe": {
                    System.out.println("suscribe !!!");
                    Collection<Chanson> liste = gestionnaireSongs.getSongById(Long.parseLong(request.getParameter("id")));
                    out.print("Abonner !");
                    break;
                }
                
                case "playSong": {
                    currentPagination = 0;
                    Collection<Chanson> liste = gestionnaireSongs.getSongById(Long.parseLong(request.getParameter("id")));
                    out.print("Jouer !");
                    break;
                }
                
                case "getASong": {
                    out.print(gestionnaireSongs.listSongsToJson(gestionnaireSongs.getSongById(Long.parseLong(request.getParameter("id")))));
                    break;
                }
                        
                case "getPages": {
                    String nbPages = Math.ceil(gestionnaireSongs.getAllSongs().size() / 10) + "";
                    out.print("[{\"nb\":\"" + nbPages + "\", \"current\":\"" + currentPagination + "\"}]");
                    break;
                }
                
                case "getPistes": {
                    out.print(gestionnaireSongs.listPistesToJson(gestionnaireSongs.getPistesOfSong(Long.parseLong(request.getParameter("id")))));
                    break;
                }
            }
        }
        out.close();
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
