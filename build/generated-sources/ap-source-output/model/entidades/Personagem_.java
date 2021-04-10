package model.entidades;

import java.awt.geom.Point2D.Double;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Atributos;
import model.entidades.Personagem;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-07-26T16:03:49")
@StaticMetamodel(Personagem.class)
public abstract class Personagem_ extends Entidade_ {

    public static volatile SingularAttribute<Personagem, Atributos> atributosPersonagem;
    public static volatile SingularAttribute<Personagem, Personagem[]> alvos;
    public static volatile SingularAttribute<Personagem, Double> maxSpeed;
    public static volatile SingularAttribute<Personagem, Integer> nivel;
    public static volatile SingularAttribute<Personagem, Integer> tipoPersonagem;
    public static volatile SingularAttribute<Personagem, Double> accel;
    public static volatile SingularAttribute<Personagem, Double> speed;
    public static volatile SingularAttribute<Personagem, Integer> alcance;

}