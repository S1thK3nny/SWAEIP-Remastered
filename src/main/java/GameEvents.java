import javax.swing.*;

public class GameEvents extends Thread {

    Thread gameThread;
    JTextArea chatArea;

    public GameEvents(JTextArea chatArea) {
        gameThread = new Thread(this);
        gameThread.setDaemon(true);
        this.chatArea = chatArea;
    }

    public void startGameThread() {
        gameThread.start();
    }

    public void stopGameThread() {
        gameThread = null;
    }

    @Override
    public void run() {
        while(gameThread != null) {
            System.out.println("Hello there!");
        }
    }
}
