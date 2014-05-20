package utilisateurs.modeles;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import utilisateurs.modeles.Instrument;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-05-20T23:48:34")
@StaticMetamodel(Piste.class)
public class Piste_ { 

    public static volatile SingularAttribute<Piste, Long> id;
    public static volatile SingularAttribute<Piste, Instrument> instrument;
    public static volatile SingularAttribute<Piste, String> nom;

}