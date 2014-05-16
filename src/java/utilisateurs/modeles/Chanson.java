/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilisateurs.modeles;

import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Dam
 */
@Entity
public class Chanson implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
 
    @OneToOne
    private Artiste artiste;
    
    @OneToMany
    private ArrayList<Piste> pistes;
    
    private String titre;
    private double prix;

    public Chanson() {
    }

    public Chanson(String titre, Artiste artiste, double prix) {
        this.titre = titre;
        this.artiste = artiste;
        this.prix = prix;
    }

    public Long getId() {
        return id;
    }

    public Artiste getArtiste() {
        return artiste;
    }

    public double getPrix() {
        return prix;
    }
    
    public String getTitre() {
        return titre;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public void setArtiste(String Artiste) {
        this.artiste = artiste;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public ArrayList<Piste> getPistes() {
        return pistes;
    }

    public void setPistes(ArrayList<Piste> pistes) {
        this.pistes = pistes;
    }
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (Long) id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Chanson)) {
            return false;
        }
        Chanson other = (Chanson) object;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "utilisateurs.modeles.Musique[ id=" + id + " ]";
    }
    
}
