/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilisateurs.modeles;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 *
 * @author Dam
 */
@Entity
public class Utilisateur implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String firstname;
    private String lastname;
    private String login;
    private String password;
    @OneToMany
    private Collection<Chanson> listeChansons;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date finAbonnement = null;
    
    private boolean isAbonner = false;
    
    
    
    public Utilisateur() {}

    public Utilisateur(final String login, final String lastname, final String firstname) {
        this.listeChansons = new LinkedList<>();
        this.login = login;
        this.lastname = lastname;
        this.firstname = firstname;
    }

    public Utilisateur(final String login, final String lastname, final String firstname, String password) {
        this.listeChansons = new LinkedList<>();
        this.login = login;
        this.lastname = lastname;
        this.firstname = firstname;
        this.password = password;
    }

    public Collection<Chanson> getListeChansons() {
        return listeChansons;
    }

    public void setListeChansons(Collection<Chanson> listeChansons) {
        this.listeChansons = listeChansons;
    }

    public Date getFinAbonnement() {
        return finAbonnement;
    }

    public void setFinAbonnement(Date finAbonnement) {
        this.finAbonnement = finAbonnement;
    }

    public boolean isIsAbonner() {
        return isAbonner;
    }

    public void setIsAbonner(boolean isAbonner) {
        this.isAbonner = isAbonner;
    }
    
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Utilisateur)) {
            return false;
        }
        Utilisateur other = (Utilisateur) object;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "utilisateurs.modeles.Utilisateur[ id=" + id + " ]";
    }

}
