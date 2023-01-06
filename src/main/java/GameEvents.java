import javax.swing.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GameEvents extends Thread {

    Condition conditionObject;
    JTextArea chatArea;
    Lock lock;
    Thread gameThread;

    public GameEvents(JTextArea chatArea) {
        gameThread = new Thread(this);
        gameThread.setDaemon(true);
        this.chatArea = chatArea;
        this.lock = new ReentrantLock();
        this.conditionObject = lock.newCondition();
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
            if(GameWindow.player1IsCurrent) {
                lock.lock();
                try {
                    while(GameWindow.player1IsCurrent) {
                        conditionObject.await();
                    }
                } catch (InterruptedException e) {
                } finally {
                    lock.unlock();
                    //chatArea.append("Lets go");
                }
                }
            else {
                System.out.println("Hello there!");
            }
        }
    }

    public void setCondition(boolean condition) {
        GameWindow.player1IsCurrent = condition;
        lock.lock();
        try {
            // Signal the condition object
            conditionObject.signal();
        } finally {
            lock.unlock();
        }
    }
}
