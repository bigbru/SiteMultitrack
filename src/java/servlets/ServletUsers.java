package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utilisateurs.gestionnaires.GestionnaireUtilisateurs;
import utilisateurs.modeles.Chanson;
import utilisateurs.modeles.Utilisateur;

/**
 *
 * @author michel
 */
//@WebServlet(name = "ServletUsers", urlPatterns = {"/ServletUsers"})
public class ServletUsers extends HttpServlet {

    // ici injection de code ! On n'initialise pas !
    @EJB
    private GestionnaireUtilisateurs gestionnaireUtilisateurs;
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
        try (PrintWriter out = response.getWriter()) {
            String action = request.getParameter("action");

            if (action != null) {
                switch (action) {
                    case "loginUtilisateur":
                        Utilisateur userLog = gestionnaireUtilisateurs.testLogin(request.getParameter("loginSession"), request.getParameter("passwordSession"));
                        if (userLog != null) {
                            loggedUser = userLog;
                            out.print(true);
                            //afficher les musiques mais probleme static
                            //out.print(GestionnaireSongs.listSongsToJson(GestionnaireSongs.getSongs(0)));
                        } else {
                            out.print(false);
                        }
                        break;

                    case "deconnexion":
                        loggedUser = null;
                        break;

                    case "signUpUser": {
                        Utilisateur u = gestionnaireUtilisateurs.creeUtilisateur(request.getParameter("nom"), request.getParameter("prenom"), request.getParameter("login"), request.getParameter("pass"));
                        if (u != null) {
                            loggedUser = u;
                            // probleme static affichage songs json static view
                            out.print(true);
                        } else {
                            out.print(false);
                        }
                        break;
                    }

                    case "testForLogin":
                        if (loggedUser != null) {
                            out.print(true);
                        } else {
                            out.print(false);
                        }
                        break;

                    case "getSongs":
                        out.print(gestionnaireUtilisateurs.listSongsToJson(gestionnaireUtilisateurs.getSongByUser(loggedUser)));
                        break;

                    case "suscribe": {
                        Chanson chanson = gestionnaireUtilisateurs.getSong((int) Long.parseLong(request.getParameter("id")));
                        if (loggedUser.getChansonsDispos() > 0) {
                            loggedUser.setChansonsDispos((loggedUser.getChansonsDispos() - 1));
                            gestionnaireUtilisateurs.addSongtoUser(loggedUser, chanson);
                        } else {
                            gestionnaireUtilisateurs.addSongtoUser(loggedUser, chanson);
                        }
                        out.print("true");
                        break;
                    }

                    case "getAbo":
                        String json = "[";
                        if (loggedUser.isIsAbonner()) {
                            if (loggedUser.getFinAbonnement() == null) {
                                json += "{\"abo\":\"life\", \"chansons\":\"" + loggedUser.getChansonsDispos() + "\"}";
                            } else {
                                json += "{\"abo\":\"" + new SimpleDateFormat("dd/MM/yyyy").format(loggedUser.getFinAbonnement().getTime()) + "\", \"chansons\":\"" + loggedUser.getChansonsDispos() + "\"}";
                            }
                        } else {
                            json += "{\"abo\":\"false\", \"chansons\":\"" + loggedUser.getChansonsDispos() + "\"}";
                        }
                        json += "]";
                        out.print(json);
                        break;

                    case "getInfos":
                        out.print("nom : " + loggedUser.getLastname());
                        break;

                    case "putLotSongs":
                        loggedUser.setChansonsDispos(loggedUser.getChansonsDispos() + Integer.parseInt(request.getParameter("nb")));
                        break;

                    case "putSuscribeDay":
                        if (request.getParameter("nb").equals("life")) {
                            loggedUser.setFinAbonnement(null);
                            loggedUser.setIsAbonner(true);
                        } else {
                            loggedUser.setIsAbonner(true);
                            if (loggedUser.getFinAbonnement() != null) {
                                Calendar c = loggedUser.getFinAbonnement();
                                c.add(Calendar.DATE, Integer.parseInt(request.getParameter("nb")));
                                loggedUser.setFinAbonnement(c);
                            } else {
                                Calendar c = Calendar.getInstance();
                                c.add(Calendar.DATE, Integer.parseInt(request.getParameter("nb")));
                                loggedUser.setFinAbonnement(c);
                            }
                        }
                        loggedUser.setChansonsDispos(loggedUser.getChansonsDispos() + Integer.parseInt(request.getParameter("nb")));
                        break;

//                case "chercherParLogin": {
//                    Collection<Utilisateur> liste = gestionnaireUtilisateurs.getUserByLogin(request.getParameter("login"));
//                    currentPagination = 0;
//                    if (liste.size() <= 0) {
//                        out.print(false);
//                    } else {
//                        out.print(gestionnaireUtilisateurs.listUsersToJson(liste));
//                    }
//                    break;
//                }
//                case "testUsers": {
//                    try {
//                        int nbUsers = Integer.parseInt(request.getParameter("nUsers"));
//                        if (nbUsers > 0) {
//                            gestionnaireUtilisateurs.generateUsers(nbUsers);
//                            Collection<Utilisateur> liste = gestionnaireUtilisateurs.getUsers(currentPagination);
//                            out.print(gestionnaireUtilisateurs.listUsersToJson(liste));
//                        } else {
//                            out.print(false);
//                        }
//                    } catch (NumberFormatException e) {
//                        out.print(false);
//                    }
//                    break;
//                }
//                case "creerUnUtilisateur": {
//                    Utilisateur u = gestionnaireUtilisateurs.creeUtilisateur(request.getParameter("nom"), request.getParameter("prenom"), request.getParameter("login"));
//                    if (u != null) {
//                        currentPagination = (int) Math.ceil(gestionnaireUtilisateurs.getAllUsers().size() / 10);
//                        Collection<Utilisateur> liste = gestionnaireUtilisateurs.getUsers(currentPagination);
//                        out.print(gestionnaireUtilisateurs.listUsersToJson(liste));
//                    } else {
//                        out.print(false);
//                    }
//                    break;
//                }
//
//                case "deleteUser": {
//                    gestionnaireUtilisateurs.removeUser(request.getParameter("login"));
//                    currentPagination = 0;
//                    Collection<Utilisateur> liste = gestionnaireUtilisateurs.getUsers(currentPagination);
//                    out.print(gestionnaireUtilisateurs.listUsersToJson(liste));
//                    break;
//                }
//                case "nextPage": {
//                    if (gestionnaireUtilisateurs.getUsers(currentPagination + 1).size() > 0) {
//                        currentPagination++;
//                        Collection<Utilisateur> liste = gestionnaireUtilisateurs.getUsers(currentPagination);
//                        out.print(gestionnaireUtilisateurs.listUsersToJson(liste));
//                    } else {
//                        out.print(false);
//                    }
//                    break;
//                }
//                case "prevPage": {
//                    if (currentPagination > 0) {
//                        currentPagination--;
//                        Collection<Utilisateur> liste = gestionnaireUtilisateurs.getUsers(currentPagination);
//                        out.print(gestionnaireUtilisateurs.listUsersToJson(liste));
//                    } else {
//                        currentPagination = 0;
//                        Collection<Utilisateur> liste = gestionnaireUtilisateurs.getUsers(currentPagination);
//                        out.print(gestionnaireUtilisateurs.listUsersToJson(liste));
//                    }
//                    break;
//                }
//                case "refreshList": {
//                    currentPagination = 0;
//                    Collection<Utilisateur> liste = gestionnaireUtilisateurs.getUsers(currentPagination);
//                    out.print(gestionnaireUtilisateurs.listUsersToJson(liste));
//                    break;
//                }
//                case "editUser": {
//                    gestionnaireUtilisateurs.editUser(request.getParameter("nom"), request.getParameter("prenom"), request.getParameter("login"));
//                    Collection<Utilisateur> liste = gestionnaireUtilisateurs.getUsers(currentPagination);
//                    out.print(gestionnaireUtilisateurs.listUsersToJson(liste));
//                    break;
//                }
//                case "getPages": {
//                    String nbPages = Math.ceil(gestionnaireUtilisateurs.getAllUsers().size() / 10) + "";
//                    out.print("[{\"nb\":\"" + nbPages + "\", \"current\":\"" + currentPagination + "\"}]");
//                    break;
//                }
//                case "goPage": {
//                    currentPagination = Integer.parseInt(request.getParameter("id"));
//                    Collection<Utilisateur> liste = gestionnaireUtilisateurs.getUsers(currentPagination);
//                    out.print(gestionnaireUtilisateurs.listUsersToJson(liste));
//                    break;
//                }
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
