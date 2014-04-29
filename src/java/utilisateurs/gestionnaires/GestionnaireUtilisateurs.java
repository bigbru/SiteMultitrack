package utilisateurs.gestionnaires;

import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import utilisateurs.modeles.Adresse;
import utilisateurs.modeles.Utilisateur;

@Stateless
public class GestionnaireUtilisateurs {

    // Ici injection de code : on n'initialise pas. L'entity manager sera créé
    // à partir du contenu de persistence.xml
    @PersistenceContext
    private EntityManager em;

    public void creerUtilisateurDeTest() {
        // On cree des adresses et on les insère dans la base
        Adresse biot = creeAdresse("Biot", "06410", "12", "rue des lilas");
        creeUtilisateur("admin", "admin", "admin", "admin", "contact@admin.fr", "0899857485", biot);
    }

    public Adresse creeAdresse(String Ville, String cp, String numR, String rue) {
        Adresse a = new Adresse(Ville, cp, numR, rue);
        em.persist(a);
        return a;
    }

    public Utilisateur creeUtilisateur(String nom, String prenom, String login, String mail, String tel, Adresse a) {
        if (!testUser(login)) {
            Utilisateur u = new Utilisateur(nom, prenom, login, mail, tel);
            // On met à jour la relation, elle est déjà en base  
            u.setAdresse(a);

            // a est déjà en base et connectée, donc la ligne suivante modifie les   
            // données pour relier l'adresse à l'utilisateur  
            a.addUtilisateur(u);

            // On persiste l'utilisateur, la relation est déjà en base, cela va donc  
            // ajouter une ligne dans la table des utilisateur avec une clé étrangère  
            // correspondant à l'adresse  
            em.persist(u);
            
            return u;
        }
        return null;
    }

    public Utilisateur creeUtilisateur(String nom, String prenom, String login, String mail, String tel, String password, Adresse a) {
        if (!testUser(login)) {
            Utilisateur u = new Utilisateur(nom, prenom, login, mail, tel, password);
            // On met à jour la relation, elle est déjà en base  
            u.setAdresse(a);

            // a est déjà en base et connectée, donc la ligne suivante modifie les   
            // données pour relier l'adresse à l'utilisateur  
            a.addUtilisateur(u);

            // On persiste l'utilisateur, la relation est déjà en base, cela va donc  
            // ajouter une ligne dans la table des utilisateur avec une clé étrangère  
            // correspondant à l'adresse  
            em.persist(u);
            return u;
        }
        return null;
    }

    public Collection<Utilisateur> getAllUsers() {
        // Exécution d'une requête équivalente à un select *
        Query q = em.createQuery("select u from Utilisateur u");
        return q.getResultList();
    }

    public Collection<Utilisateur> getUsers(int index) {
        // Exécution d'une requête équivalente à un select *
        Query q = em.createQuery("select u from Utilisateur u");
        q.setMaxResults(10);
        q.setFirstResult(index * 10);
        return q.getResultList();
    }

    public String listUsersToJson(Collection<Utilisateur> listUsers) {
        String json = "[";
        for (Utilisateur u : listUsers) {
            json += "{\"login\":\"" + u.getLogin() + "\", \"firstname\":\"" + u.getFirstname() + "\", \"lastname\":\"" + u.getLastname() + "\", \"cp\":\"" + u.getAdresse().getCodePostal() + "\", \"ville\":\"" + u.getAdresse().getVille() + "\", \"idville\":\"" + u.getAdresse().getId() + "\"},";
        }
        json = json.substring(0, json.length() - 1);
        json += "]";

        return json;
    }

    public Collection<Utilisateur> getUserByLogin(String login) {
        // Exécution d'une requête équivalente à un select *
        Query q = em.createQuery("select u from Utilisateur u where u.login='" + login + "'");
        return q.getResultList();
    }

    public void removeUser(String login) {
        Collection<Utilisateur> liste = getUserByLogin(login);
        for (Utilisateur utilisateur : liste) {
            em.remove(utilisateur);
        }
    }

    public boolean testUser(String login) {
        Collection<Utilisateur> listeTest = getAllUsers();
        boolean exist = false;
        for (Utilisateur u : listeTest) {
            if (u.getLogin().equals(login)) {
                exist = true;
            }
        }
        return exist;
    }

    public Utilisateur testLogin(String login, String password) {
        Query q = em.createQuery("select u from Utilisateur u where u.login='" + login + "' and u.password='" + password + "'");
        Utilisateur user = null;
        if (q.getResultList().size() > 0) {
            user = (Utilisateur) q.getResultList().get(0);
        }
        return user;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public Collection<Utilisateur> getUsersParVille(int idVille) {
         Adresse a = em.find(Adresse.class, idVille);
        // a est connecté, le get va déclencher un select
        return a.getUtilisateurs();
    }
}
