package utilisateurs.modeles;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import utilisateurs.modeles.Artiste;
import utilisateurs.modeles.Piste;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-05-26T21:23:25")
@StaticMetamodel(Chanson.class)
public class Chanson_ { 

    public static volatile SingularAttribute<Chanson, Long> id;
    public static volatile SingularAttribute<Chanson, Double> prix;
    public static volatile CollectionAttribute<Chanson, Piste> pistes;
    public static volatile SingularAttribute<Chanson, Artiste> artiste;
    public static volatile SingularAttribute<Chanson, String> titre;

}