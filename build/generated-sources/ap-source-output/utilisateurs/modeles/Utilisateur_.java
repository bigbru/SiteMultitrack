package utilisateurs.modeles;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import utilisateurs.modeles.Chanson;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-05-26T21:23:25")
@StaticMetamodel(Utilisateur.class)
public class Utilisateur_ { 

    public static volatile SingularAttribute<Utilisateur, Integer> id;
    public static volatile SingularAttribute<Utilisateur, Calendar> finAbonnement;
    public static volatile SingularAttribute<Utilisateur, Boolean> isAbonner;
    public static volatile SingularAttribute<Utilisateur, Integer> chansonsDispos;
    public static volatile SingularAttribute<Utilisateur, String> lastname;
    public static volatile SingularAttribute<Utilisateur, String> login;
    public static volatile SingularAttribute<Utilisateur, String> firstname;
    public static volatile SingularAttribute<Utilisateur, String> password;
    public static volatile CollectionAttribute<Utilisateur, Chanson> listeChansons;

}