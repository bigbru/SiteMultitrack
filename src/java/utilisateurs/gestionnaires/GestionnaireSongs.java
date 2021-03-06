package utilisateurs.gestionnaires;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.LinkedList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import utilisateurs.modeles.Artiste;
import utilisateurs.modeles.Chanson;
import utilisateurs.modeles.Piste;

@Stateless
public class GestionnaireSongs {

    @PersistenceContext(unitName = "Tp2PU")
    private EntityManager em;

    public Chanson creeMusique(Artiste artiste, String titre, double prix) {
        Chanson m = new Chanson(titre, artiste, prix);
        em.persist(m);
        return m;
    }

    public Chanson creeMusique(Artiste artiste, String titre, double prix, Collection<Piste> pistes) {
        Chanson m = new Chanson(titre, artiste, prix, pistes);
        em.persist(m);
        return m;
    }

    public Artiste creeArtiste(String nom) {
        Artiste m = new Artiste(nom);
        em.persist(m);
        return m;
    }

    public void chargerMusiquesDeBase() {
        String line;
        InputStream is;
        BufferedReader br = null;
        Collection<Piste> pistes = new LinkedList<>();
        String[] curSong = null;
        Artiste curArtiste = null;
        try {
            is = getClass().getClassLoader().getResourceAsStream("data/listeChansons.txt");
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                if (line.contains("./")) {
                    if (pistes.size() > 0) {
                        creeMusique(curArtiste, curSong[1], 3.99, pistes);
                        pistes = new LinkedList<>();
                    }
                    String strSong = line.replace("./", "");
                    strSong = strSong.replace(":", "");
                    if (strSong.split("-").length == 2) {
                        curSong = strSong.split("-");
                        curArtiste = creeArtiste(curSong[0]);
                    }
                } else {
                    Piste p = null;
                    if (line.contains(".mp3")) {
                        p = new Piste(line.replace(".mp3", ""));
                        pistes.add(p);
                    } else if (line.contains(".ogg")) {
                        p = new Piste(line.replace(".ogg", ""));
                        pistes.add(p);
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

    public Collection<Chanson> getAllSongs() {
        // Exécution d'une requête équivalente à un select *
        Query q = em.createQuery("select u from Chanson u ORDER BY u.artiste");
        return q.getResultList();
    }

    public Collection<Chanson> getSongs(int index) {
        // Exécution d'une requête équivalente à un select *
        Query q = em.createQuery("SELECT u FROM Chanson u ORDER BY u.artiste");
        q.setMaxResults(10);
        q.setFirstResult(index * 10);
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

    public Collection<Chanson> getSongsByStr(String str) {
        // Exécution d'une requête équivalente à un select *
        Query q = em.createQuery("select u from Chanson u where UPPER(u.artiste.nom) LIKE :str OR UPPER(u.titre) LIKE :str");
        q.setParameter("str", "%" + str.toUpperCase() + "%");
        return q.getResultList();
    }

    public Chanson getSongById(Long id) {
        // Exécution d'une requête équivalente à un select *
        Query q = em.createQuery("select u from Chanson u where u.id=:id");
        q.setParameter("id", id);
        return (Chanson) q.getResultList().get(0);
    }

    public void removeSong(Long id) {
        Chanson c = getSongById(id);
        em.remove(c);
    }

    public int editSong(String id, String artiste, String titre, String prix) {
        Query q = em.createQuery("update Chanson u set u.artiste = :artiste, u.titre = :titre, u.prix = :prix where u.id = :id");
        q.setParameter("artiste", artiste);
        q.setParameter("titre", titre);
        q.setParameter("prix", prix);
        q.setParameter("id", id);
        int num = q.executeUpdate();
        return num;
    }

    public boolean testSong(int id) {
        Collection<Chanson> listeTest = getAllSongs();
        boolean exist = false;
        for (Chanson u : listeTest) {
            if (u.getId() == id) {
                exist = true;
            }
        }
        return exist;
    }

    public void persist(Object object) {
        em.persist(object);
    }

    public void persist1(Object object) {
        em.persist(object);
    }

    public void persist2(Object object) {
        em.persist(object);
    }

    public Collection<Piste> getPistesOfSong(Long id) {
        Query q = em.createQuery("SELECT DISTINCT OBJECT(c) FROM Chanson u, IN (u.pistes) AS c WHERE u.id = :id");
        q.setParameter("id", id);
        return q.getResultList();
    }

    public String listPistesToJson(Collection<Piste> pistesOfSong) {
        String json = "[";
        for (Piste u : pistesOfSong) {
            json += "{\"id\":\"" + u.getId() + "\", \"nom\":\"" + u.getNom() + "\"},";
        }
        json = json.substring(0, json.length() - 1);
        json += "]";
        return json;
    }
}
