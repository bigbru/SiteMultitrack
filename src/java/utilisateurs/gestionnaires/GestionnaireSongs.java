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
import utilisateurs.modeles.Musique;

@Stateless
public class GestionnaireSongs {

    // Ici injection de code : on n'initialise pas. L'entity manager sera créé
    // à partir du contenu de persistence.xml
    @PersistenceContext
    private EntityManager em;

    public Musique creeMusique(String artiste, String titre, double prix) {
        Musique m = new Musique(titre, artiste, prix);
        em.persist(m);
        return m;
    }

    public void chargerMusiquesDeBase() {
        String line;

        //chercher la derniere ligne avec ./
        boolean end = false;

        InputStream is;
        BufferedReader br = null;
        try {
            is = getClass().getClassLoader().getResourceAsStream("data/listeChansons.txt");
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                
                System.out.println(line);
                
                if (end) {
                    break;
                } else {
                    if (line.contains("./")) {
                        end = true;
                    } else {
                        String[] song = line.split("-");
                        creeMusique(song[0], song[1], 1);
                    }
                }
            }
        } catch (Exception e) {
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

    public Collection<Musique> getAllSongs() {
        // Exécution d'une requête équivalente à un select *
        Query q = em.createQuery("select u from Musique u");
        return q.getResultList();
    }

    public Collection<Musique> getSongs(int index) {
        // Exécution d'une requête équivalente à un select *
        Query q = em.createQuery("select u from Musique u");
        q.setMaxResults(10);
        q.setFirstResult(index * 10);
        return q.getResultList();
    }

    public String listSongsToJson(Collection<Musique> listSongs) {
        String json = "[";
        for (Musique u : listSongs) {
            json += "{\"id\":\"" + u.getId() + "\", \"artiste\":\"" + u.getArtiste() + "\", \"titre\":\"" + u.getTitre() + "\", \"prix\":\"" + u.getPrix() + "\"},";
        }
        json = json.substring(0, json.length() - 1);
        json += "]";

        return json;
    }

    public Collection<Musique> getSongsByAuthor(String artiste) {
        // Exécution d'une requête équivalente à un select *
        Query q = em.createQuery("select u from Musique u where u.artiste='" + artiste + "'");
        return q.getResultList();
    }

    public Collection<Musique> getSongById(String id) {
        // Exécution d'une requête équivalente à un select *
        Query q = em.createQuery("select u from Musique u where u.id='" + id + "'");
        return q.getResultList();
    }
    
    public void removeSong(String id) {
        Collection<Musique> liste = getSongById(id);
        for (Musique song : liste) {
            em.remove(song);
        }
    }

    public int editSong(String id, String artiste, String titre, String prix) {
        Query q = em.createQuery("update Musique u set u.artiste='" + artiste + "', u.titre='" + titre + "', u.prix='" + prix + "' where u.id='" + id + "'");
        int num = q.executeUpdate();
        return num;
    }

    public boolean testSong(int id) {
        Collection<Musique> listeTest = getAllSongs();
        boolean exist = false;
        for (Musique u : listeTest) {
            if (u.getId() == id) {
                exist = true;
            }
        }
        return exist;
    }
}
