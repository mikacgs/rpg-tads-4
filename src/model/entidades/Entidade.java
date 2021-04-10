package model.entidades;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Entidade implements Serializable {

    @Id
    @GeneratedValue
    private int id;

    public Entidade() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    static final int COLIDINDO_ACIMA = 0;
    static final int COLIDINDO_DIREITA = 1;
    static final int COLIDINDO_ABAIXO = 2;
    static final int COLIDINDO_ESQUERDA = 3;
    protected TipoDeEntidade tipo;
    @Transient
    protected Rectangle2D.Double pos;
    protected Double x;
    protected Double y;
    /**
     * Array de 4 posições com as entidades em que está colidindo, Usando as
     * constantes a seguir:
     * {@link #COLIDINDO_ABAIXO},{@link #COLIDINDO_ACIMA},{@link #COLIDINDO_ESQUERDA}
     * E {@link #COLIDINDO_DIREITA}, podemos acessar a posição coresspondente a
     * cada lado em que ocorre colisão do objeto
     */
    @Transient
    Entidade[] entidadesColidindo;

    public Entidade(int x, int y) {
        pos = new Rectangle2D.Double(x, y, 1, 1);
        entidadesColidindo = new Entidade[4];
    }

    public Entidade(int x, int y, TipoDeEntidade tipo) {
        pos = new Rectangle2D.Double(x, y, 1, 1);
        this.tipo = tipo;
        entidadesColidindo = new Entidade[4];
        this.x = Double.valueOf(x);
        this.y = Double.valueOf(y);
    }

    public Rectangle2D.Double getPos() {
        return pos;
    }

    /**
     * Método que é executado assim que a classe é instanciada
     */
    public abstract void init();

    /**
     * Método que deve ser sobrescrito para cada ação da entidade
     *
     * @param currentTick contador de volta do jogo, utilizado para controlar a
     * velocidade em que as coisas do jogo ocorrem
     */
    public abstract void update(int currentTick);

    /**
     * Método que deve ser sobrescrito para desenhar a entidade no mapa
     *
     * @param g objeto usado para desenhar a classe.
     */
    public abstract void render(Graphics2D g);

}
