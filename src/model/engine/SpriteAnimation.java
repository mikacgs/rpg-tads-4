package model.engine;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.Transient;

/**
 * Classe usada para animações
 *
 * @author Michael
 */
public class SpriteAnimation implements Serializable {

    private int currentImageIndex;
    private int indexInc;
    private int speed;
    private boolean loop;
    @Transient
    private ArrayList<BufferedImage> images;

    public SpriteAnimation() {
        currentImageIndex = 0;
        loop = false;
        indexInc = 1;
        images = new ArrayList<BufferedImage>();
        speed = 5;
    }

    /**
     * maneira como a imagem vai ser desenhada, pode ser usada em loop para
     * simular o movimento de asas
     *
     * @param value
     */
    public void setLoop(boolean value) {
        loop = value;
    }

    public boolean isLoop() {
        return loop;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * adiciona uma imagem ao sprite
     *
     * @param img
     */
    public void addImage(BufferedImage img) {
        images.add(img);
    }

    /**
     * Atualiza a posição atual da imagem a ser desenhada No estado de loop ao
     * chegar na ultima imagem, a volta se inverte, por exemplo 1>2>3>2>1 e
     * assim por diante, fora do estado de loop após a ultima posição ele pula
     * para a primeira ex : 1>2>3>1
     *
     * @param currentTick
     */
    public void update(int currentTick) {
        if (currentTick % speed == 0) {
            currentImageIndex += indexInc;
            if (currentImageIndex == images.size() || currentImageIndex == -1) {
                if (loop) {
                    indexInc *= -1;
                    currentImageIndex += indexInc;
                } else {
                    currentImageIndex = 0;
                }
            }
        }
    }

    /**
     * Pegar a imagem para ser desenhada
     *
     * @return a imagem escolida pelo metodo {@link #update(int)}
     */
    public BufferedImage getImage() {
        return images.get(currentImageIndex);
    }

    public boolean finished() {
        return currentImageIndex == images.size() - 1;
    }
}
