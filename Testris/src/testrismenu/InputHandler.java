package testrismenu;

//@author Nevir2002

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;

public class InputHandler{

    private GameArea game;
    private GameThread gameThread;
    private InputMap im;
    private ActionMap am;
    private RepeatThread rightThread,leftThread,downThread;
    private long DELAY,REPEAT_RATE;
    private boolean movingRight,movingLeft,movingDown;

    public InputHandler() {
    }

    public InputHandler(GameArea game, GameThread gameThread, ArrayList<Integer> listKeyCode, InputMap im, ActionMap am) {
        this.game = game;
        this.gameThread = gameThread;
        this.im = im;
        movingRight = movingLeft = false;
        updateInput(listKeyCode);
        
        am.put("Right",new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(!movingRight){
                    if(movingLeft) leftThread.stopRunning();
                    movingRight = true;
                    game.moveBlockRight();
                    rightThread = new RepeatThread(game,"Right",DELAY,REPEAT_RATE);
                    rightThread.start();
                }
            }
        });
        am.put("sRight",new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(movingLeft && !leftThread.isAlive()){
                    leftThread = new RepeatThread(game,"Left",DELAY,REPEAT_RATE);
                    leftThread.start();
                }
                if(rightThread != null) rightThread.stopRunning();
                movingRight = false;
            }
        });
        am.put("Left",new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(!movingLeft){
                    if(movingRight) rightThread.stopRunning();
                    movingLeft = true;
                    game.moveBlockLeft();
                    leftThread = new RepeatThread(game,"Left",DELAY,REPEAT_RATE);
                    leftThread.start();
                }
            }
        });
        am.put("sLeft",new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(movingRight && !rightThread.isAlive()){
                    rightThread = new RepeatThread(game,"Right",DELAY,REPEAT_RATE);
                    rightThread.start();
                }
                if(leftThread != null) leftThread.stopRunning();
                movingLeft = false;
            }
        });
        am.put("Softdrop",new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(!movingDown){
                    movingDown = true;
                    downThread = new RepeatThread(game,"Softdrop",DELAY,REPEAT_RATE);
                    downThread.start();
                    game.moveBlockDown();
                    downThread.startRunning();
                    interruptThread();
                }
            }
        });
        am.put("sSoftdrop",new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(downThread != null) downThread.stopRunning();
                movingDown = false;
            }
        });
        am.put("Harddrop",new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e){
                game.dropBlock();
                interruptThread();
                
            }
        });
        am.put("RotateR",new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e){
                game.rotateR();
            }
        });
        am.put("RotateL",new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e){
                game.rotateL();
            }
        });
        am.put("RotateB",new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e){
                game.rotateB();
            }
        });
        am.put("Hold",new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e){
                game.hold();
            }
        });
        am.put("Exit",new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });
    }
    
    public void updateInput(ArrayList<Integer> arr){
        
        im.clear();
        im.put(KeyStroke.getKeyStroke(arr.get(0),0,false), "Left");
        im.put(KeyStroke.getKeyStroke(arr.get(0),0,true), "sLeft");
        im.put(KeyStroke.getKeyStroke(arr.get(1),0,false), "Right");
        im.put(KeyStroke.getKeyStroke(arr.get(1),0,true), "sRight"); // on release key
        im.put(KeyStroke.getKeyStroke(arr.get(2),0,false), "Softdrop");
        im.put(KeyStroke.getKeyStroke(arr.get(2),0,true), "sSoftdrop");
        im.put(KeyStroke.getKeyStroke(arr.get(3),0,false), "Harddrop");
        im.put(KeyStroke.getKeyStroke(arr.get(4),0,false), "RotateL");
        im.put(KeyStroke.getKeyStroke(arr.get(5),0,false), "RotateR");
        im.put(KeyStroke.getKeyStroke(arr.get(6),0,false), "RotateB");
        im.put(KeyStroke.getKeyStroke(arr.get(7),0,false), "Hold");
        im.put(KeyStroke.getKeyStroke(arr.get(8),0,false), "Reset");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0,false), "Exit");
        DELAY = arr.get(9);
        REPEAT_RATE = arr.get(10);
        if(rightThread != null) rightThread.update(DELAY,REPEAT_RATE);
        if(leftThread != null) leftThread.update(DELAY,REPEAT_RATE);
        if(downThread != null) downThread.update(DELAY,REPEAT_RATE);
        
    }
    private void interruptThread(){
        this.gameThread.interrupt();
    }
    public void replaceGameThread(GameThread gameThread){
        this.gameThread = gameThread;
    }
    public void disableInputAndReset(ArrayList<Integer> arr){
        
        im.clear();
        im.put(KeyStroke.getKeyStroke(arr.get(8),0,false), "Reset");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0,false), "Exit");
        
    }
    public void disableInput(ArrayList<Integer> arr){
        
        im.clear();
//        im.put(KeyStroke.getKeyStroke(arr.get(8),0,false), "Reset");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0,false), "Exit");
        
    }
    
}
