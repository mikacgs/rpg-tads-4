package model.entidades;

import java.awt.geom.Rectangle2D.Double;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.entidades.Entidade;
import model.entidades.TipoDeEntidade;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-07-26T16:03:49")
@StaticMetamodel(Entidade.class)
public abstract class Entidade_ { 

    public static volatile SingularAttribute<Entidade, TipoDeEntidade> tipo;
    public static volatile SingularAttribute<Entidade, Double> pos;
    public static volatile SingularAttribute<Entidade, Entidade[]> entidadesColidindo;
    public static volatile SingularAttribute<Entidade, Integer> id;

}