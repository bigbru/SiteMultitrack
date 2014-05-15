package servlets;

import javax.ejb.EJB;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import utilisateurs.gestionnaires.GestionnaireSongs;
import utilisateurs.gestionnaires.GestionnaireUtilisateurs;

public class Init implements ServletContextListener {

    @EJB
    private GestionnaireSongs gestionnaireSongs;
    @EJB
    private GestionnaireUtilisateurs gestionnaireUtilisateurs;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        gestionnaireUtilisateurs.creerUtilisateursDeBase();
        gestionnaireSongs.chargerMusiquesDeBase();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
