package model.engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;

abstract public class Game implements WindowListener {

    protected JFrame mainWindow;
    private boolean active;
    private BufferStrategy bufferStrategy;
    private GameSpeedTracker speedTracker;
    private int expectedTPS;
    private double expectedNanosPerTick;
    private int maxFrameSkip;

    public Game() {
        mainWindow = new JFrame("RPG");
        mainWindow.setSize(800, 600);
        mainWindow.addWindowListener(this);
        mainWindow.addKeyListener(InputManager.getInstance());
        active = false;
    }

    public void terminate() {
        active = false;
    }

    public void run() {
        active = true;
        load();
        expectedTPS = 60;
        expectedNanosPerTick = GameSpeedTracker.NANOS_IN_ONE_SECOND / expectedTPS;
        maxFrameSkip = 10;
        long nanoTimeAtNextTick = System.nanoTime();
        int skippedFrames = 0;
        while (active) {
            speedTracker.update();
            if (System.nanoTime() > nanoTimeAtNextTick && skippedFrames < maxFrameSkip) {
                nanoTimeAtNextTick += expectedNanosPerTick;
                InputManager.getInstance().update();
                update();
                skippedFrames++;
            } else {
                render();
                skippedFrames = 0;
            }
        }
        unload();
    }

    protected void load() {
        mainWindow.setUndecorated(true);
        mainWindow.setIgnoreRepaint(true);
        mainWindow.setLocation(100, 100);
        mainWindow.setVisible(true);
        mainWindow.createBufferStrategy(2);
        bufferStrategy = mainWindow.getBufferStrategy();
        speedTracker = new GameSpeedTracker();
        onLoad();
        speedTracker.start();
    }

    protected void unload() {
        onUnload();
        bufferStrategy.dispose();
        mainWindow.dispose();
    }

    protected void update() {
        speedTracker.countTick();
        onUpdate(speedTracker.totalTicks);
        Thread.yield();
    }

    protected void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
         onRender(g);
        g.setColor(Color.black);
      //  g.fillRect(0, 0, mainWindow.getWidth(), mainWindow.getHeight());

      

        g.setColor(Color.black);
        g.fillRect(0, 0, 200, 16);
        g.setColor(Color.white);
        g.setFont(new Font("", Font.BOLD, 12));
        g.drawString(speedTracker.getTPS() + " tps", 1, 12);
        g.dispose();
        bufferStrategy.show();
    }

    abstract public void onLoad();

    abstract public void onUnload();

    abstract public void onUpdate(int currentTick);

    abstract public void onRender(Graphics2D g);

    public int getWidth() {
        return mainWindow.getWidth();
    }

    public int getHeight() {
        return mainWindow.getHeight();
    }

    public void windowClosing(WindowEvent e) {
        terminate();
    }

    public void windowOpened(WindowEvent e) {
    }

    public void windowClosed(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }
}
