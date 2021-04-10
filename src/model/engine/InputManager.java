package model.engine;

import model.entidades.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;

public class InputManager implements KeyListener {

    static protected int KEY_RELEASED = 0;
    static protected int KEY_JUST_PRESSED = 1;
    static protected int KEY_PRESSED = 2;
    static private InputManager instance;
    private HashMap<Integer, Integer> keyCache;
    private ArrayList<Integer> pressedKeys;
    private ArrayList<Integer> releasedKeys;

    private InputManager() {
        keyCache = new HashMap<>();
        pressedKeys = new ArrayList<>();
        releasedKeys = new ArrayList<>();
    }

    static public InputManager getInstance() {
        if (instance == null) {
            instance = new InputManager();
        }
        return instance;
    }

    /**
     * Pergunta se a tecla está pressionada
     *
     * @param keyId codigo da tecla usando as constantes da classe
     * {@link KeyEvent}
     * @return
     */
    public boolean isPressed(int keyId) {

        return keyCache.containsKey(keyId)
                && !keyCache.get(keyId).equals(KEY_RELEASED);
    }

    /**
     * Pergunta se a tecla foi pressionada uma vez
     *
     * @param keyId codigo da tecla usando as constantes da classe
     * {@link KeyEvent}
     * @return
     */
    public boolean isJustPressed(int keyId) {
        return keyCache.containsKey(keyId)
                && keyCache.get(keyId).equals(KEY_JUST_PRESSED);
    }

    /**
     * Pergunta se a tecla está solta
     *
     * @param keyId codigo da tecla usando as constantes da classe
     * {@link KeyEvent}
     * @return
     */
    public boolean isReleased(int keyId) {
        return !keyCache.containsKey(keyId)
                || keyCache.get(keyId).equals(KEY_RELEASED);
    }

    public void update() {
        int tiltUm = pressedKeys.size();
        int tiltDois = pressedKeys.size();
        for (Integer keyCode : keyCache.keySet()) {

            if (isJustPressed(keyCode)) {
                keyCache.put(keyCode, KEY_PRESSED);
            }
        }
        for (Integer keyCode : releasedKeys) {
            keyCache.put(keyCode, KEY_RELEASED);
        }
        for (Integer keyCode : pressedKeys) {

            if (isReleased(keyCode)) {
                keyCache.put(keyCode, KEY_JUST_PRESSED);
            } else {
                keyCache.put(keyCode, KEY_PRESSED);
            }
        }
        if (tiltUm == pressedKeys.size()) {
            pressedKeys.clear();
        }
        if (tiltDois == pressedKeys.size()) {
            releasedKeys.clear();
        }

    }

    public void keyTyped(KeyEvent e) {
        // Rotina não utilizada. Evento de tecla teclada.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        pressedKeys.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        releasedKeys.add(e.getKeyCode());
    }
}
