package model.entidades;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import model.Atributos;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Personagem extends Entidade implements Serializable {

    @Id
    @GeneratedValue
    private int id;
    private int nivel;
    private int tipoPersonagem;
    @OneToOne(cascade=CascadeType.PERSIST)
    private Atributos atributosPersonagem;

    public Personagem() {
        super();
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getTipoPersonagem() {
        return tipoPersonagem;
    }

    public void setTipoPersonagem(int tipoPersonagem) {
        this.tipoPersonagem = tipoPersonagem;
    }

    public Atributos getAtributosPersonagem() {
        return atributosPersonagem;
    }

    public void setAtributosPersonagem(Atributos atributosPersonagem) {
        this.atributosPersonagem = atributosPersonagem;
    }

    Point2D.Double speed;
    Point2D.Double accel;
    Point2D.Double maxSpeed;
    @Transient
    Personagem[] alvos;
    int alcance;

    public Personagem(int x, int y, double speedValue, ArrayList<Personagem> entidadesQueMovem, Atributos atributos) {
        super(x, y, TipoDeEntidade.PERSONAGEM);
        speed = new Point2D.Double(speedValue, speedValue);
        accel = new Point2D.Double();
        maxSpeed = new Point2D.Double(5, 15);
        alvos = new Personagem[4];
        this.atributosPersonagem = atributos;
    }

    public Personagem getAlvo() {
        for (Personagem e : alvos) {
            if (e != null) {
                return e;
            }
        }
        return null;
    }

    public Personagem(int x, int y, double speedValue, Atributos atributos) {
        super(x, y, TipoDeEntidade.PERSONAGEM);
        speed = new Point2D.Double(speedValue, speedValue);
        accel = new Point2D.Double();
        maxSpeed = new Point2D.Double(5, 15);
        alvos = new Personagem[4];
        this.atributosPersonagem = atributos;
    }

    @Override
    public void update(int currentTick) {
        speed.x = accel.x;
        speed.y = accel.y;

        if (entidadesColidindo[COLIDINDO_ACIMA] != null) {
            if (entidadesColidindo[COLIDINDO_ACIMA].tipo == TipoDeEntidade.BARREIRA) {
                pos.y = entidadesColidindo[COLIDINDO_ACIMA].pos.y + entidadesColidindo[COLIDINDO_ACIMA].pos.height;
                speed.y = 0;
                accel.y = 0;
            } else if (entidadesColidindo[COLIDINDO_ACIMA].tipo == TipoDeEntidade.CIDADE) {
            }
        }
        if (entidadesColidindo[COLIDINDO_ABAIXO] != null) {
            if (entidadesColidindo[COLIDINDO_ABAIXO].tipo == TipoDeEntidade.BARREIRA) {
                pos.y = entidadesColidindo[COLIDINDO_ABAIXO].pos.y - pos.height;
                speed.y = 0;
                accel.y = 0;
            } else if (entidadesColidindo[COLIDINDO_ABAIXO].tipo == TipoDeEntidade.CIDADE) {

            }
        }
        if (entidadesColidindo[COLIDINDO_ESQUERDA] != null) {
            if (entidadesColidindo[COLIDINDO_ESQUERDA].tipo == TipoDeEntidade.BARREIRA) {
                pos.x = entidadesColidindo[COLIDINDO_ESQUERDA].pos.x + entidadesColidindo[COLIDINDO_ESQUERDA].pos.width;
                speed.x = 0;
                accel.x = 0;
            } else if (entidadesColidindo[COLIDINDO_ESQUERDA].tipo == TipoDeEntidade.CIDADE) {
            }
        }
        if (entidadesColidindo[COLIDINDO_DIREITA] != null) {
            if (entidadesColidindo[COLIDINDO_DIREITA].tipo == TipoDeEntidade.BARREIRA) {
                pos.x = entidadesColidindo[COLIDINDO_DIREITA].pos.x - pos.width;
                speed.x = 0;
                accel.x = 0;
            } else if (entidadesColidindo[COLIDINDO_DIREITA].tipo == TipoDeEntidade.CIDADE) {

            }
        }
        pos.x += speed.x;
        pos.y += speed.y;
        accel.x = 0;
        accel.y = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
