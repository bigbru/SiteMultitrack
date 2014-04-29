package servlets;

import javax.ejb.EJB;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import utilisateurs.gestionnaires.GestionnaireUtilisateurs;

public class Init implements ServletContextListener {

    @EJB
    private GestionnaireUtilisateurs gestionnaireUtilisateurs;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        gestionnaireUtilisateurs.creerUtilisateurDeTest();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
