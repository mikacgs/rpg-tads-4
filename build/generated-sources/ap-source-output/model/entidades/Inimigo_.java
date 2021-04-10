package model.entidades;

import java.util.ArrayList;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-07-26T16:03:49")
@StaticMetamodel(Inimigo.class)
public class Inimigo_ extends Personagem_ {

    public static volatile SingularAttribute<Inimigo, Integer> atual;
    public static volatile SingularAttribute<Inimigo, Integer> FRENTE;
    public static volatile SingularAttribute<Inimigo, Integer> ESQUERDA;
    public static volatile SingularAttribute<Inimigo, Integer> stepInterval;
    public static volatile SingularAttribute<Inimigo, Integer> DIREITA;
    public static volatile SingularAttribute<Inimigo, Integer> lastStepTick;
    public static volatile SingularAttribute<Inimigo, ArrayList> inimigo;
    public static volatile SingularAttribute<Inimigo, Integer> lastAtackTick;
    public static volatile SingularAttribute<Inimigo, Integer> COSTAS;

}