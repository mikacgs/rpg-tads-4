package model.entidades;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class CollisionDetector {

    // Lista de objetos que deverão ser testados.
    ArrayList<Entidade> entidades;

    public CollisionDetector(ArrayList<Entidade> entidades) {
        // Recebe a lista de objetos ao ser criado.
        this.entidades = entidades;
    }

    /**
     * Chamado para atualizar a lista de objetos em colisão
     *
     * @param currentTick
     */
    public void update(int currentTick) {
        for (Entidade o : entidades) {
            for (int i = 0; i < 4; i++) {
                o.entidadesColidindo[i] = null;
            }
        }
        for (int i1 = 0; i1 < entidades.size() - 1; i1++) {
            Entidade objetoUm = entidades.get(i1);
            for (int i2 = i1 + 1; i2 < entidades.size(); i2++) {
                Entidade objetoDois = entidades.get(i2);
                if (objetoUm.pos.intersects(objetoDois.pos)) {
                    Rectangle2D intersecao = objetoUm.pos.createIntersection(objetoDois.pos);
                    if (intersecao.getWidth() > intersecao.getHeight()) {
                        if (objetoUm.pos.getCenterY() < objetoDois.pos.getCenterY()) {
                            objetoUm.entidadesColidindo[Entidade.COLIDINDO_ABAIXO] = objetoDois;
                            objetoDois.entidadesColidindo[Entidade.COLIDINDO_ACIMA] = objetoUm;
                        } else {
                            objetoUm.entidadesColidindo[Entidade.COLIDINDO_ACIMA] = objetoDois;
                            objetoDois.entidadesColidindo[Entidade.COLIDINDO_ABAIXO] = objetoUm;
                        }
                    } else {
                        if (objetoUm.pos.getCenterX() < objetoDois.pos.getCenterX()) {
                            objetoUm.entidadesColidindo[Entidade.COLIDINDO_DIREITA] = objetoDois;
                            objetoDois.entidadesColidindo[Entidade.COLIDINDO_ESQUERDA] = objetoUm;
                        } else {
                            objetoUm.entidadesColidindo[Entidade.COLIDINDO_ESQUERDA] = objetoDois;
                            objetoDois.entidadesColidindo[Entidade.COLIDINDO_DIREITA] = objetoUm;
                        }
                    }
                }
            }
        }
    }
}
