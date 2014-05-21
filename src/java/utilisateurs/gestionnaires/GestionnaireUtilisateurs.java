package utilisateurs.gestionnaires;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import utilisateurs.modeles.Chanson;
import utilisateurs.modeles.Utilisateur;

@Stateless
public class GestionnaireUtilisateurs {

    // Ici injection de code : on n'initialise pas. L'entity manager sera créé
    // à partir du contenu de persistence.xml
    @PersistenceContext
    private EntityManager em;

    private int currentLine = 0;

    public void generateUsers(int nb) {

        String line;
        int end = currentLine + nb;
        int i = 0;

        InputStream is = null;
        BufferedReader br = null;
        try {
            is = getClass().getClassLoader().getResourceAsStream("data/data.csv");
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                if (i < currentLine) {
                    i++;
                } else if (i >= currentLine) {
                    if (i >= end) {
                        currentLine = i;
                        break;
                    } else {
                        i++;
                        String[] user = line.split(",");
                        creeUtilisateur(user[2], user[1], user[0]);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public void creerUtilisateursDeBase() {
        Utilisateur u = creeUtilisateur("admin", "admin", "admin", "admin");
        
        String untildate = "2014-10-08";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(dateFormat.parse(untildate));
        } catch (ParseException ex) {
            Logger.getLogger(GestionnaireUtilisateurs.class.getName()).log(Level.SEVERE, null, ex);
        }
        cal.add(Calendar.DATE, 1);
        
        suscribeUser("admin", cal);
    }

    public Utilisateur creeUtilisateur(String nom, String prenom, String login) {
        if (!testUser(login)) {
            Utilisateur u = new Utilisateur(login, nom, prenom);
            Collection<Chanson> l = u.getListeChansons();
            l.add(getSong(1));
            l.add(getSong(2));
            l.add(getSong(3));
            u.setListeChansons(l);
            em.persist(u);
            return u;
        }
        return null;
    }

    public Utilisateur creeUtilisateur(String nom, String prenom, String login, String password) {
        if (!testUser(login)) {
            Utilisateur u = new Utilisateur(login, nom, prenom, password);
            Collection<Chanson> l = u.getListeChansons();
            l.add(getSong(1));
            l.add(getSong(2));
            l.add(getSong(3));
            u.setListeChansons(l);
            em.persist(u);
            return u;
        }
        return null;
    }

    public Chanson getSong(int index) {
        // Exécution d'une requête équivalente à un select *
        Query q = em.createQuery("SELECT u FROM Chanson u ORDER BY u.artiste");
        q.setMaxResults(10);
        q.setFirstResult(index * 10);
        Chanson c = (Chanson) q.getResultList().get(0);
        return c;
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
            json += "{\"login\":\"" + u.getLogin() + "\", \"firstname\":\"" + u.getFirstname() + "\", \"lastname\":\"" + u.getLastname() + "\"},";
        }
        json = json.substring(0, json.length() - 1);
        json += "]";

        return json;
    }

    public Utilisateur getUserByLogin(String login) {
        // Exécution d'une requête équivalente à un select *
        Query q = em.createQuery("select u from Utilisateur u where u.login = :login");
        q.setParameter("login", login);
        return (Utilisateur) q.getResultList().get(0);
    }

    public void removeUser(String login) {
        Utilisateur user = getUserByLogin(login);
        em.remove(user);
    }

    public int editUser(String nom, String prenom, String login) {
        Query q = em.createQuery("update Utilisateur u set u.firstname = :prenom, u.lastname = :nom where u.login = :login");
        q.setParameter("prenom", prenom);
        q.setParameter("nom", nom);
        q.setParameter("login", login);
        int num = q.executeUpdate();
        return num;
    }

    public int suscribeUser(String login, Calendar date) {
        Query q = em.createQuery("update Utilisateur u set u.isAbonner = 1, u.finAbonnement = :date where u.login = :login");
        q.setParameter("date", date);
        q.setParameter("login", login);
        int num = q.executeUpdate();
        return num;
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
        Query q = em.createQuery("select u from Utilisateur u where u.login = :login and u.password = :password");
        q.setParameter("password", password);
        q.setParameter("login", login);

        Utilisateur user = null;
        if (q.getResultList().size() > 0) {
            user = (Utilisateur) q.getResultList().get(0);
        }
        return user;
    }

    public Collection<Chanson> getSongByUser(Utilisateur loggedUser) {
        Query q = em.createQuery("SELECT DISTINCT OBJECT(c) FROM Utilisateur u, IN (u.listeChansons) AS c WHERE u.id = :id");
        q.setParameter("id", loggedUser.getId());
        return q.getResultList();
    }

    public String listSongsToJson(Collection<Chanson> listSongs) {
        String json = "[";
        for (Chanson u : listSongs) {
            json += "{\"id\":\"" + u.getId() + "\", \"artiste\":\"" + u.getArtiste().getNom() + "\", \"titre\":\"" + u.getTitre() + "\", \"prix\":\"" + u.getPrix() + "\"},";
        }
        json = json.substring(0, json.length() - 1);
        json += "]";

        return json;
    }
}
