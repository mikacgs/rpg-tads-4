/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entidades;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Michael
 */
public class CampoDeVisao {

    ArrayList<Personagem> entidades;

    public CampoDeVisao(ArrayList<Personagem> entidades) {
        this.entidades = entidades;
    }

    public void update(int currentTick) {
        for (Personagem o : entidades) {
            for (int i = 0; i < 4; i++) {
                o.alvos[i] = null;
            }
        }
        for (int i1 = 0; i1 < entidades.size() - 1; i1++) {
            Personagem objetoUm = entidades.get(i1);
            for (int i2 = i1 + 1; i2 < entidades.size(); i2++) {
                Personagem objetoDois = entidades.get(i2);
                if (objetoUm.pos.intersects(objetoDois.pos)) {
                    Rectangle2D intersecao = objetoUm.pos.createIntersection(objetoDois.pos);
                    if (intersecao.getWidth() > intersecao.getHeight()) {
                        if (objetoUm.pos.getCenterY() < objetoDois.pos.getCenterY()) {
                            objetoUm.alvos[Entidade.COLIDINDO_ABAIXO] = objetoDois;
                            objetoDois.alvos[Entidade.COLIDINDO_ACIMA] = objetoUm;
                        } else {
                            objetoUm.alvos[Entidade.COLIDINDO_ACIMA] = objetoDois;
                            objetoDois.alvos[Entidade.COLIDINDO_ABAIXO] = objetoUm;
                        }
                    } else {
                        if (objetoUm.pos.getCenterX() < objetoDois.pos.getCenterX()) {
                            objetoUm.alvos[Entidade.COLIDINDO_DIREITA] = objetoDois;
                            objetoDois.alvos[Entidade.COLIDINDO_ESQUERDA] = objetoUm;
                        } else {
                            objetoUm.alvos[Entidade.COLIDINDO_ESQUERDA] = objetoDois;
                            objetoDois.alvos[Entidade.COLIDINDO_DIREITA] = objetoUm;
                        }
                    }
                }
            }
        }
    }

}
