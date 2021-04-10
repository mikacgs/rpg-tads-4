package model.entidades;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.persistence.Transient;

public class EntidadeCidade extends Entidade {

    BufferedImage sprite;

    public EntidadeCidade(int x, int y, int width, int height) {
        super(x, y, TipoDeEntidade.CIDADE);
        pos.setRect(x, y, width, height);
        System.out.println(tipo);
    }

    @Override
    public void init() {
        sprite = new BufferedImage((int) pos.width, (int) pos.height, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = sprite.getGraphics();
        g.setColor(Color.yellow);
        g.fillRect(0, 0, sprite.getWidth(), sprite.getHeight());
    }

    @Override
    public void update(int currentTick) {
        // Plataformas não possuem lógica: ficam paradas no lugar.
    }

    @Override
    public void render(Graphics2D g) {
        // Descomentar para vizualizar as cidades
       //   g.drawImage(sprite, (int) pos.x, (int) pos.y, null);
    }
}
