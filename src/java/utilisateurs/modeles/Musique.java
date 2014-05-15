package utilisateurs.modeles;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 *
 * @author Bigbru
 */
@Entity
public class Musique implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    private String titre;
    private String auteur;
    private double prix;
    
    public Musique() {
    }

    public Musique(String Titre, String Auteur, double prix) {
        this.titre = Titre;
        this.auteur = Auteur;
        this.prix = prix;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String Titre) {
        this.titre = Titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String Auteur) {
        this.auteur = Auteur;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }
    
    
    
}
