package model.entidades;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class EntidadePlataforma extends Entidade {

    BufferedImage sprite;

    public EntidadePlataforma(int x, int y, int width, int height) {
        super(x, y, TipoDeEntidade.BARREIRA);
        pos.setRect(x, y, width, height);
    }

    @Override
    public void init() {
        sprite = new BufferedImage((int) pos.width, (int) pos.height, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = sprite.getGraphics();
        g.setColor(Color.lightGray);
        g.fillRect(0, 0, sprite.getWidth(), sprite.getHeight());
    }

    @Override
    public void update(int currentTick) {
    }

    @Override
    public void render(Graphics2D g) {
        // descomentar para visualizar barreiras da cidade
        //      g.drawImage(sprite, (int) pos.x, (int) pos.y, null);
    }
}
