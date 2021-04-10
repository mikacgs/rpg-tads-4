package model.entidades;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import model.AcoesPersonagens;
import model.Atributos;
import model.engine.ImageManager;
import model.engine.SpriteAnimation;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Tiarles
 */
@Entity
public class Inimigo extends Personagem implements AcoesPersonagens, Serializable {

    @Id
    @GeneratedValue
    private int id;

    public Inimigo() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    int stepInterval = 10;
    int lastStepTick;
    int lastAtackTick;
    @Transient
    int lastRessurgeTick;
    final int FRENTE = 0;
    final int ESQUERDA = 1;
    final int DIREITA = 3;
    final int COSTAS = 2;
    int atual;
    @Transient
    private boolean isAlive;
    @Transient
    ArrayList<SpriteAnimation> inimigo;

    public Inimigo(int x, int y, double speedValue, Atributos atributos) {
        super(x, y, speedValue, atributos);
        inimigo = new ArrayList<>();
        atual = 0;
        lastStepTick = 0;
        lastAtackTick = 0;
        isAlive = true;

    }

    @Override
    public void init() {
        try {
            inimigo.add(ImageManager.getInstance().loadSpriteAnimation("inimigoFrente.png", 3));
            inimigo.add(ImageManager.getInstance().loadSpriteAnimation("inimigoEsquerda.png", 3));
            inimigo.add(ImageManager.getInstance().loadSpriteAnimation("inimigoCostas.png", 3));
            inimigo.add(ImageManager.getInstance().loadSpriteAnimation("inimigoDireita.png", 3));

        } catch (IOException ex) {
            Logger.getLogger(Heroi.class.getName()).log(Level.SEVERE, null, ex);
        }

        getAtributosPersonagem().initAtributos();
        if (getAtributosPersonagem().getPontosVida() == 0) {
            getAtributosPersonagem().setPontosVida(getAtributosPersonagem().getMaxVida());

            isAlive = true;
        }
        pos = new Rectangle2D.Double(x, y, 1, 1);
        pos.width = inimigo.get(atual).getImage().getWidth();
        pos.height = inimigo.get(atual).getImage().getHeight();

    }

    @Override
    public void update(int currentTick) {

        if (isAlive) {
            if (getAtributosPersonagem().getPontosVida() <= 0) {
                isAlive = false;
                return;
            }
            if (currentTick - lastStepTick > stepInterval * 5) {
                if (atual == 3) {
                    atual = 0;
                } else {
                    atual++;
                }
                lastStepTick = currentTick;
            }
            if (atual == DIREITA) {
                accel.x = 0.4;
            }
            if (atual == ESQUERDA) {
                accel.x = -0.4;
            }
            if (atual == COSTAS) {
                accel.y = -0.4;
            }
            if (atual == FRENTE) {
                accel.y = 0.4;
            }
            super.update(currentTick);
            inimigo.get(atual).update(currentTick);
            if (currentTick - lastAtackTick > stepInterval * 10) {
                Random random = new Random();
                int x = random.nextInt(10);
                Personagem alvo = (Personagem) getAlvo();
                if (x % 2 == 0) {
                    atacar(alvo);
                } else {
                    defender(this);
                }
                lastAtackTick = currentTick;
            }
        } else {
            if (currentTick - lastRessurgeTick > stepInterval * 100) {
                init();
                lastRessurgeTick = currentTick;
            }
        }
    }

    @Override
    public void render(Graphics2D g
    ) {
        if (isAlive) {
            int y = (int) (pos.y + pos.height) - inimigo.get(atual).getImage().getHeight();
            int x = (int) (pos.x + pos.width / 2) - inimigo.get(atual).getImage().getWidth() / 2;
            g.drawImage(inimigo.get(atual).getImage(), x, y, null);
        }
        //Arc2D campoVisao = new Arc2D.Double(x, y, 100, 100, 360, 360, Arc2D.CHORD);
    }

    @Override
    public void atacar(Personagem alvo
    ) {
        if (alvo != null) {
            alvo.getAtributosPersonagem().setPontosVida(alvo.getAtributosPersonagem().getPontosVida() - this.getAtributosPersonagem().getAtaque());
        }
    }

    @Override
    public void defender(Personagem alvo
    ) {

        // if (alvo != null) {
        //     int ataque = (int) (alvo.getAtributosPersonagem().getAtaque() * 0.25);
        //     this.getAtributosInimigos().setPontosVida(this.getAtributosInimigos().getPontosVida() - ataque);
        // }
        //}
    }

    @Override
    public void usarMagia(Personagem alvo
    ) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
