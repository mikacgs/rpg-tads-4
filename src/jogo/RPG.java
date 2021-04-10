package jogo;

import controller.HeroiJpaController;
import controller.InimigoJpaController;
import controller.PersonagemJpaController;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.Atributos;
import model.engine.AudioManager;
import model.engine.DataManager;
import model.engine.Game;
import model.engine.ImageManager;
import model.engine.InputManager;
import model.entidades.CampoDeVisao;
import model.entidades.CollisionDetector;
import model.entidades.Entidade;
import model.entidades.EntidadeCidade;
import model.entidades.EntidadePlataforma;
import model.entidades.Heroi;
import model.entidades.Inimigo;
import model.entidades.Personagem;

public class RPG extends Game {

    CollisionDetector collisionDetector;
    CampoDeVisao cp;
    ArrayList<Entidade> entidades;
    ArrayList<Personagem> entidadesQueMovem;
    boolean gameOver;
    BufferedImage imagem;

    EntityManagerFactory emf;
    EntityManager em;

    public RPG() {
        //  emf = Persistence.createEntityManagerFactory("RPGPU");
        //  em = emf.createEntityManager();
        entidades = new ArrayList<>();
        entidadesQueMovem = new ArrayList<>();
        collisionDetector = new CollisionDetector(entidades);
        cp = new CampoDeVisao(entidadesQueMovem);
        gameOver = false;
    }

    @Override
    public void onLoad() {
        try {
            AudioManager.getInstance().loadAudio("tema.wav").loop();
            imagem = ImageManager.getInstance().loadImage("mapa.jpg");
            carrega();
            // HeroiJpaController hjpa = new HeroiJpaController(emf);
            //Heroi player = hjpa.findHeroi(1);
            Heroi player = null;
            if (player == null) {
                System.out.println("Criando um jogador, não havia no banco");
                System.out.println("Corrigir na linha 62 da classe RPG");
                player = new Heroi(300, 300, entidadesQueMovem, new Atributos("Player", 198, 20, 10, 30, 50));
                //hjpa.create(player);
            }
            entidades.add(player);
            entidadesQueMovem.add(player);
            for (Entidade e : entidades) {
                e.init();
            }

        } catch (IOException ex) {
        }
    }

    @Override
    public void onUnload() {
        // PersonagemJpaController pjpa = new PersonagemJpaController(emf);

        //  try {
        //      AudioManager.getInstance().loadAudio("tema.wav").stop();
        //     for (Personagem e : entidadesQueMovem) {
        //         pjpa.edit(e);
        //     }
        //  } catch (Exception ex) {
        //      Logger.getLogger(RPG.class.getName()).log(Level.SEVERE, null, ex);
        //   }
    }

    @Override
    public void onUpdate(int currentTick) {
        if (!gameOver) {
            for (Entidade e : entidades) {
                e.update(currentTick);
            }
            collisionDetector.update(currentTick);
            cp.update(currentTick);

        }
        if (InputManager.getInstance().isPressed(KeyEvent.VK_ESCAPE)) {
            terminate();
        }
    }

    @Override
    public void onRender(Graphics2D g) {
        g.setColor(Color.blue);
        g.drawImage(imagem, 0, 0, null);
        for (Entidade e : entidades) {
            e.render(g);

        }
    }

    public void carrega() {
        try {
            DataManager dm = new DataManager("oceanos.save");
            entidades.clear();
            int qtd = 0;
            qtd = dm.read("oceanos", qtd);
            for (int i = 0; i < qtd; i++) {
                Entidade e_temp = new EntidadePlataforma(0, 0, 0, 0);
                e_temp.getPos().x = dm.read("oceano." + i + ".x", (int) e_temp.getPos().x);
                e_temp.getPos().y = dm.read("oceano." + i + ".y", (int) e_temp.getPos().y);
                e_temp.getPos().width = dm.read("oceano." + i + ".width", (int) e_temp.getPos().width);
                e_temp.getPos().height = dm.read("oceano." + i + ".height", (int) e_temp.getPos().height);
                e_temp.init();
                entidades.add(e_temp);
            }
            DataManager dmc = new DataManager("cidades.save");
            qtd = 0;
            qtd = dmc.read("cidades", qtd);
            for (int i = 0; i < qtd; i++) {
                Entidade e_temp = new EntidadeCidade(0, 0, 0, 0);
                e_temp.getPos().x = dmc.read("cidade." + i + ".x", (int) e_temp.getPos().x);
                e_temp.getPos().y = dmc.read("cidade." + i + ".y", (int) e_temp.getPos().y);
                e_temp.getPos().width = dmc.read("cidade." + i + ".width", (int) e_temp.getPos().width);
                e_temp.getPos().height = dmc.read("cidade." + i + ".height", (int) e_temp.getPos().height);
                e_temp.init();
                entidades.add(e_temp);
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
        //   InimigoJpaController hjpa = new InimigoJpaController(emf);
        int contInimigos = 0;
        /*  for (Inimigo inimigo : hjpa.findInimigoEntities()) {
            entidades.add(inimigo);
            entidadesQueMovem.add(inimigo);
            contInimigos++;
        }*/
        if (contInimigos == 0) {
            Inimigo inimigo = (new Inimigo(170, 200, 20, new Atributos("Monstro", 200, 20, 10, 30, 50)));
            entidades.add(inimigo);
            entidadesQueMovem.add(inimigo);
           // hjpa.create(inimigo);
            System.out.println("Criar mais inimigos na linha 138 da classe RPG");
        }

    }
}
