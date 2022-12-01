package testrismenu;

//@author Nevir2002

import java.util.logging.Level;
import java.util.logging.Logger;

public class RepeatThread extends Thread {

    private GameArea game;
    private String action;
    private long DELAY,REPEAT_RATE;
    private GameThread gameThread;
    private boolean isRunning;

    public RepeatThread() {
    }
    public RepeatThread(GameArea game, String action, long DELAY, long REPEAT_RATE) {
        this.game = game;
        this.action = action;
        this.DELAY = DELAY;
        this.REPEAT_RATE = REPEAT_RATE;
        isRunning = true;
    }
    public void startRunning(){
        isRunning = true;
    }
    public void stopRunning(){
        isRunning = false;
    }
    public void update(long DELAY, long REPEAT_RATE){
        this.DELAY = DELAY;
        this.REPEAT_RATE = REPEAT_RATE;
    }    
    
    @Override
    public void run(){
//        while(isRunning){
            try {
                if(!action.equals("Softdrop")) RepeatThread.sleep(DELAY);
                while(isRunning){
//                        System.out.println(DELAY + " " + REPEAT_RATE);
//                    if(!isRunning) break;
                    RepeatThread.sleep(REPEAT_RATE);
                    if(action.equals("Right")) game.moveBlockRight();
                    if(action.equals("Left")) game.moveBlockLeft();
                    if(action.equals("Softdrop")) game.moveBlockDown();
                }
            } catch (InterruptedException ex) {
//                    System.out.println("Interrupted.");
//                    Logger.getLogger(RepeatThread.class.getName()).log(Level.SEVERE, null, ex);
            }
//        }
    }
}
