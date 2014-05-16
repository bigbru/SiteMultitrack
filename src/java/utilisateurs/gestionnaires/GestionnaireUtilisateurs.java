package utilisateurs.gestionnaires;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
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
        u.getListeChansons().add(getSong(1));
        u.getListeChansons().add(getSong(2));
        u.getListeChansons().add(getSong(3));
    }

    public Utilisateur creeUtilisateur(String nom, String prenom, String login) {
        if (!testUser(login)) {
            Utilisateur u = new Utilisateur(login, nom, prenom);
            u.getListeChansons().add(getSong(1));
            u.getListeChansons().add(getSong(2));
            u.getListeChansons().add(getSong(3));
            em.persist(u);
            return u;
        }
        return null;
    }

    public Utilisateur creeUtilisateur(String nom, String prenom, String login, String password) {
        if (!testUser(login)) {
            Utilisateur u = new Utilisateur(login, nom, prenom, password);
            u.getListeChansons().add(getSong(1));
            u.getListeChansons().add(getSong(2));
            u.getListeChansons().add(getSong(3));
            em.persist(u);
            return u;
        }
        return null;
    }

    public Chanson getSong(int index) {
        // Exécution d'une requête équivalente à un select *
        Query q = em.createQuery("select u from Chanson u");
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

    public Collection<Utilisateur> getUserByLogin(String login) {
        // Exécution d'une requête équivalente à un select *
        Query q = em.createQuery("select u from Utilisateur u where u.login = :login");
        q.setParameter("login", login);
        return q.getResultList();
    }

    public void removeUser(String login) {
        Collection<Utilisateur> liste = getUserByLogin(login);
        for (Utilisateur utilisateur : liste) {
            em.remove(utilisateur);
        }
    }

    public int editUser(String nom, String prenom, String login) {
        Query q = em.createQuery("update Utilisateur u set u.firstname = :prenom, u.lastname = :nom where u.login = :login");
        q.setParameter("prenom", prenom);
        q.setParameter("nom", nom);
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

}
