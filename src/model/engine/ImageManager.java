package model.engine;

import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import javax.imageio.ImageIO;

public class ImageManager {

    static private ImageManager instance;
    private HashMap<String, BufferedImage> images;

    private ImageManager() {
        images = new HashMap<String, BufferedImage>();
    }

    static public ImageManager getInstance() {
        if (instance == null) {
            instance = new ImageManager();
        }
        return instance;
    }

    /**
     * Carrega uma imagem
     *
     * @param fileName nome do arquivo que contem a imagem
     * @return a imagem
     * @throws IOException
     */
    public BufferedImage loadImage(String fileName) throws IOException {
        URL url = getClass().getResource("/" + fileName);
        if (url == null) {
            throw new RuntimeException("A imagem /" + fileName
                    + " não foi encontrada.");
        } else {
            String path = url.getPath();
            if (images.containsKey(path)) {
                return images.get(path);
            } else {
                BufferedImage img = ImageIO.read(url);
                images.put(path, img);
                return img;
            }
        }
    }

    /**
     * Carrega o pedaço de uma imagem, usado quando apenas uma parte da imagem
     * será desenhada
     *
     * @param fileName arquivo com a imagem
     * @param x começo da imagem a partir da posição no eixo horizontal
     * @param y começo da imagem a partir da posição no eixo vertical
     * @param w altura da imagem
     * @param h largura da imagem
     * @return a imagem dimensionada
     * @throws IOException
     */
    public BufferedImage loadImage(String fileName, int x, int y, int w, int h)
            throws IOException {
        BufferedImage sheet = loadImage(fileName);
        BufferedImage img = sheet.getSubimage(x, y, w, h);
        return img;
    }

    /**
     * Inverte uma imagem causando o efeito de espelho
     *
     * @param image imagem a ser espelhada
     * @param flipHorizontal inverter a imagem na horizontal
     * @param flipVertical inverter a imagem na vertical
     * @return
     */
    public BufferedImage flipImage(BufferedImage image, boolean flipHorizontal, boolean flipVertical) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage img = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(w, h, BufferedImage.BITMASK);
        if (flipHorizontal) {
            if (flipVertical) {
                img.getGraphics().drawImage(image, w, h, -w, -h, null);
            } else {
                img.getGraphics().drawImage(image, w, 0, -w, h, null);
            }
        } else if (flipVertical) {
            img.getGraphics().drawImage(image, 0, h, w, -h, null);
        } else {
            img.getGraphics().drawImage(image, 0, 0, w, h, null);
        }
        return img;
    }

    /**
     * Carrega uma imagem para ser usada como animação, ou seja, na mesma imagem
     * possuem os seus diferentes estados
     *
     * @param fileName nome do arquivo que contem a imagem a ser animada
     * @param qtdFrames quantidade de estados da imagem.
     * @return
     * @throws IOException caso a imagem nao exista
     * @throws RuntimeException caso a imagem não possa ser dividida, uma imagem
     * que contenha 3 estados, deve ter uma quantidade de pixels divisivel por
     * tres para ser possivel o recorte
     */
    public SpriteAnimation loadSpriteAnimation(String fileName, int qtdFrames) throws
            IOException {
        BufferedImage sheet = loadImage(fileName);
        if (sheet.getWidth() % qtdFrames != 0) {
            throw new RuntimeException("A imagem /" + fileName
                    + " não possui " + qtdFrames + " sprites de mesmo tamanho.");
        } else {
            SpriteAnimation anim = new SpriteAnimation();
            int w = sheet.getWidth() / qtdFrames;
            int h = sheet.getHeight();
            for (int i = 0; i < qtdFrames; i++) {
                anim.addImage(sheet.getSubimage(i * w, 0, w, h));
            }
            return anim;

        }
    }

 
}
