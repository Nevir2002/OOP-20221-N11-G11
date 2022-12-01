package testrismenu;

//@author Nevir2002
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameThread extends Thread{

    private GameArea game;
    private JPanel ReadyPanel, SettingsPanel;
    private JLabel ReadyLabel, TimerLabel, ScoreLabel, LevelLabel;
    private int mil, sec, min, LEVEL, SCORE;
    private boolean incScore = false; //tăng điểm
    private Timer timer;
    private long speed = 800;
    private boolean isRunning;
    private InputHandler input;
    private ArrayList<Integer> listKeyCode;
    private int[] threshold = {10,20,30,40,50,60,70,80,90};
//    private int[] threshold = {1,2,3,4,5,6,7,8,9}; // for testing purposes
    private int[] speedLevel = {800,700,600,500,400,300,200,150,100,50};
    //solve sound
    private static GameSounds sound =  new GameSounds();
    
    public GameThread(GameArea ga, JPanel ReadyPanel, JLabel ReadyLabel, JLabel TimerLabel, JLabel ScoreLabel, JLabel LevelLabel, JPanel SettingsPanel) {
        game = ga;
        isRunning = true;
        this.ReadyPanel = ReadyPanel;
        this.ReadyLabel = ReadyLabel;
        this.TimerLabel = TimerLabel;
        this.ScoreLabel = ScoreLabel;
        this.LevelLabel = LevelLabel;
        this.SettingsPanel = SettingsPanel;
        mil = 0;
        sec = 0;
        min = 0;
        SCORE = 0;
    }
    
    @Override
    public void run(){
        try {
            
            //reset score when click reset button
            ScoreLabel.setText("Điểm: 0");
            LevelLabel.setText("Cấp độ: 1");
            TimerLabel.setText("Thời gian: 00:00:00");
            ReadyPanel.setVisible(true);
            ReadyLabel.setText("READY");
            sound.playReady();
            this.sleep(1000);
            ReadyLabel.setText("GO");
            sound.playGo();
            this.sleep(1000);
            ReadyPanel.setVisible(false);
            
            
            timer = new Timer(10, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mil++;
                    if(mil == 100){
                        mil = 0;
                        sec++;
                    }
                    if(sec == 60){
                        sec = 0;
                        min++;
                    }
                    TimerLabel.setText(String.format("Thời gian: %02d:%02d:%02d",min,sec,mil));
                }
            });
            enableInput();
            timer.start();
        } catch (InterruptedException ex) {
//            Logger.getLogger(GameThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ReadyPanel.setVisible(false);
        while(isRunning){
            game.spawnNewBlock();
            do{
                try {
                    GameThread.sleep(speed);
                } catch (InterruptedException ex) {
                }
                if(!isRunning) break;

            }while(game.moveBlockDown());
            
            if(!isRunning){
                break;
            }
            if(game.outOfBounds()){
                game.saveToBackground();
                sound.playGameOver();
                
                disableInput();
                stopTimer();
                stopRunning();
                
                game.setGameOver(true);
                game.REPAINT();
                System.out.println("Game over.");

            }else{
                
                sound.playBlockDown();
                game.saveToBackground();
                
                if( game.clearLines() ) {
                    //set score
                    if (game.getGameOver()) SCORE = 0;
                    else SCORE = game.SCORE;
                    ScoreLabel.setText(String.format("Điểm: %d", SCORE));
                    
                    sound.playClearLines();
                }
                
            }
        }
        
    }
    public void updateSpeed(int count){
        int i = 0;
        while(i < 9 && count >= threshold[i]) i++;
        //set level
        LEVEL = i+1;
        LevelLabel.setText(String.format("Cấp độ: %d", LEVEL));

        speed = speedLevel[i];
//        System.out.println(i + " " + speed);
    }
    public void stopRunning(){
        isRunning = false;
    }
    public void startRunnng(){
        isRunning = true;
    }
    public boolean running(){
        return isRunning;
    }
    public void addListKeyCode(ArrayList<Integer> arr){
        listKeyCode = arr;
    }
    public void addInputHandler(InputHandler input){
        this.input = input;
    }
    public void disableInput(){
        input.disableInputAndReset(listKeyCode);
    }
    public void enableInput(){
        input.updateInput(listKeyCode);
    }
    public void stopTimer(){
        timer.stop();
    }
}
