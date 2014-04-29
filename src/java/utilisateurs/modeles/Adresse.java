/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilisateurs.modeles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author Dam
 */
@Entity
public class Adresse implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @NotNull
    @Size(min = 1)
    private String ville;
    
    @Pattern(regexp = "[0-9]{5}")
    private String codePostal;

    @Pattern(regexp = "[0-9]+")
    private String numRue;
    
    private String rue;
    
    
    @OneToMany(mappedBy = "adresse")
    private List<Utilisateur> utilisateurs = new ArrayList<>();

    public Adresse() {
    }

    public Adresse(String ville, String codePostal, String num, String rue) {
        this.ville = ville;
        this.codePostal = codePostal;
        this.numRue = num;
        this.rue = rue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getNumRue() {
        return numRue;
    }

    public void setNumRue(String num) {
        this.numRue = num;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public void addUtilisateur(Utilisateur u) {
        utilisateurs.add(u);
    }

    public void removeUtilisateur(Utilisateur u) {
        utilisateurs.remove(u);
    }

    public List<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

}
