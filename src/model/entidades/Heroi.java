package model.entidades;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import model.AcoesPersonagens;
import model.Atributos;
import model.engine.AudioManager;
import model.engine.ImageManager;
import model.engine.InputManager;
import model.engine.SpriteAnimation;

@Entity
public class Heroi extends Personagem implements Serializable, AcoesPersonagens {
    
    @Id
    @GeneratedValue
    private int id;
    
    public Heroi() {
        super();
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    int stepInterval;
    int lastStepTick;
    final int FRENTE = 0;
    final int ESQUERDA = 1;
    final int DIREITA = 2;
    final int COSTAS = 3;
    int atual;
    @Transient
    ArrayList<SpriteAnimation> personagem;
    
    public Heroi(int x, int y, ArrayList<Personagem> entidadesQueMovem, Atributos atributos) {
        super(x, y, 10, entidadesQueMovem, atributos);
        stepInterval = 20;
        personagem = new ArrayList<>();
        atual = FRENTE;
    }
    
    @Override
    public void init() {
        try {
            personagem.add(ImageManager.getInstance().loadSpriteAnimation("Frente.png", 4));
            personagem.add(ImageManager.getInstance().loadSpriteAnimation("Esquerda.png", 4));
            personagem.add(ImageManager.getInstance().loadSpriteAnimation("right.png", 4));
            personagem.add(ImageManager.getInstance().loadSpriteAnimation("back.png", 4));
            
        } catch (IOException ex) {
            Logger.getLogger(Heroi.class.getName()).log(Level.SEVERE, null, ex);
        }
        getAtributosPersonagem().initAtributos();
        pos = new Rectangle2D.Double(x, y, 1, 1);
        pos.width = personagem.get(atual).getImage().getWidth();
        pos.height = personagem.get(atual).getImage().getHeight();
    }
    
    @Override
    public void update(int currentTick) {
        if (InputManager.getInstance().isPressed(KeyEvent.VK_RIGHT)) {
            accel.x = 0.4;
            atual = DIREITA;
        } else if (InputManager.getInstance().isPressed(KeyEvent.VK_LEFT)) {
            accel.x = -0.4;
            atual = ESQUERDA;
        }
        if (InputManager.getInstance().isPressed(KeyEvent.VK_UP)) {
            accel.y = -0.4;
            atual = COSTAS;
        } else if (InputManager.getInstance().isPressed(KeyEvent.VK_DOWN)) {
            accel.y = 0.4;
            atual = FRENTE;
        } else if (InputManager.getInstance().isJustPressed(KeyEvent.VK_A)) {
            atacar(getAlvo());
        } else if (InputManager.getInstance().isJustPressed(KeyEvent.VK_V)) {
            System.out.println("Aguardando classes para implementar item de vida, classe heroi linha 77");
        } else if (InputManager.getInstance().isJustPressed(KeyEvent.VK_F)) {
            System.out.println("Aguardando classes para implementar item de força, classe heroi linha 80");
        } else if (InputManager.getInstance().isJustPressed(KeyEvent.VK_R)) {
            System.out.println("Aguardando classes para implementar algum item, classe heroi linha 83");
        } else if (InputManager.getInstance().isJustPressed(KeyEvent.VK_G)) {
            System.out.println("Aguardando classes para implementar algum tem, classe heroi linha 86");
            
        }
        super.update(currentTick);
        
        if (speed.x != 0 || speed.y != 0) {
            personagem.get(atual).update(currentTick);
            if (currentTick - lastStepTick > stepInterval) {
                playSound("step.wav");
                lastStepTick = currentTick;
            }
        }
        speed.x = 0;
        speed.y = 0;
    }
    
    @Override
    public void render(Graphics2D g) {
        int y = (int) (pos.y + pos.height) - personagem.get(atual).getImage().getHeight();
        int x = (int) (pos.x + pos.width / 2) - personagem.get(atual).getImage().getWidth() / 2;
        g.drawImage(personagem.get(atual).getImage(), x, y, null);
        Graphics2D g2d = (Graphics2D) g.create();
       
        Paint paint = Color.BLACK;
        
        paint = new RadialGradientPaint(
                new Point((int) pos.x + 10, (int) pos.y + 10),
                50,
                new float[]{0, 1f},
                new Color[]{new Color(0, 0, 0, 0), new Color(0, 0, 0, 255)});
        
        g2d.setPaint(paint);
        g2d.fillRect(0, 0, 800, 600);
        g2d.dispose();
        
        Rectangle2D vidaPersonagem = new Rectangle(1, 497, 799, 20);
        g.setColor(Color.RED);
        g.fill(vidaPersonagem);
        g.setColor(Color.GREEN);
        int pVida = getAtributosPersonagem().getPontosVida() * 100 / getAtributosPersonagem().getMaxVida();
        int barra = (int) vidaPersonagem.getWidth() * pVida / 100;
        g.fillRect((int) vidaPersonagem.getX(), (int) vidaPersonagem.getY(), barra, (int) vidaPersonagem.getHeight());
        if (getAlvo() != null) {
            Rectangle2D vidaInimigo = new Rectangle(1, (int) vidaPersonagem.getY() + 40, 799, 20);
            g.setColor(Color.RED);
            g.fill(vidaInimigo);
            g.setColor(Color.GREEN);
            pVida = getAlvo().getAtributosPersonagem().getPontosVida() * 100 / getAlvo().getAtributosPersonagem().getMaxVida();
            barra = (int) vidaInimigo.getWidth() * pVida / 100;
            g.fillRect((int) vidaInimigo.getX(), (int) vidaInimigo.getY(), barra, (int) vidaInimigo.getHeight());
            g.setColor(Color.black);
            g.setFont(new Font("", Font.BOLD, 12));
            g.drawString("Vida do inimigo", (int) vidaInimigo.getX() + (int) vidaInimigo.getWidth() / 2 + 2, (int) vidaInimigo.getY() + (int) vidaInimigo.getHeight() / 2 + 2);
        }
        g.setColor(Color.black);
        g.setFont(new Font("", Font.BOLD, 12));
        g.drawString("Sua vida", (int) vidaPersonagem.getX() + (int) vidaPersonagem.getWidth() / 2 + 2, (int) vidaPersonagem.getY() + (int) vidaPersonagem.getHeight() / 2 + 2);
        
    }

    /**
     * Metodo para simplificar a chamada de sons a serem tocados
     *
     * @param fileName
     */
    public void playSound(String fileName) {
        try {
            AudioManager.getInstance().loadAudio(fileName).play();
        } catch (IOException ioe) {
        }
    }
    
    @Override
    public void atacar(Personagem alvo) {
        if (alvo != null) {
            alvo.getAtributosPersonagem().setPontosVida(alvo.getAtributosPersonagem().getPontosVida() - this.getAtributosPersonagem().getAtaque());
        }
    }
    
    @Override
    public void defender(Personagem alvo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void usarMagia(Personagem alvo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
