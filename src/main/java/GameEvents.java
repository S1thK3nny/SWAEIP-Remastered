import javax.swing.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GameEvents extends Thread {

    Condition conditionObject;
    static int player1Attack = -1;
    static int player2Attack = 0;
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
            if(GameWindow.chooseAttackPhase) {
                lock.lock();
                try {
                    while(GameWindow.chooseAttackPhase) {
                        conditionObject.await();
                    }
                }
                catch (InterruptedException e) {}
                finally {
                    lock.unlock();
                    //chatArea.append("Lets go");
                }
            }
            else {
                System.out.println("Hello there!");
                System.out.println(player1Attack);
                if(GameWindow.getPlayer1().getAttacks().get(player1Attack).getAttackSpeed() == GameWindow.getPlayer2().getAttacks().get(player2Attack).getAttackSpeed()) {
                    System.out.println("Equal");
                }
                else if(GameWindow.getPlayer1().getAttacks().get(player1Attack).getAttackSpeed() > GameWindow.getPlayer2().getAttacks().get(player2Attack).getAttackSpeed()) {
                    System.out.println("player 1 turn");
                }
                else {
                    System.out.println("player 2 turn");
                }

                setCondition(true);
            }
        }
    }

    public void setCondition(boolean condition) {
        GameWindow.chooseAttackPhase = condition;
        lock.lock();
        try {
            // Signal the condition object
            conditionObject.signal();
        }
        finally {
            lock.unlock();
        }
    }
}