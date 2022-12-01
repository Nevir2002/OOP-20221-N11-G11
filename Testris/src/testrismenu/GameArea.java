package testrismenu;

//@author Nevir2002

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.util.*;


public class GameArea extends JPanel{
    private final int CELL_SIZE = 30, COLUMNS = 10, ROWS = 20;
    public int SCORE = 0;
    public int LINES_COUNT = 0;
    private Color borderColor = new Color(255, 255, 255, (int)(0.2 * 255));
    private Color invis = new Color(0,0,0,0);
    private Color[][] background;
    private Tetrominos block,shadow,hold;
    public boolean canHold = true;
    private LinkedList<Tetrominos> pieceQueue;
    private final int previewSize = 5;
    private Random rd = new Random();
    private ArrayList<String> arr = new ArrayList<>(Arrays.asList("T","O","I","Z","S","L","J"));
    private GameThread gameThread;
    
    private boolean gameOver = false;
    
    public GameArea(JPanel area) {
        
        area.setVisible(false);
        this.setBounds(area.getBounds());
        this.setBackground(area.getBackground());
        this.setBorder(area.getBorder());
        
        background = new Color[ROWS+2][COLUMNS+12];
        for(int i = 0; i < background.length; i++){
            
            for(int j = 0; j < background[0].length; j++){
                
                if(i == background.length-1 || j == 5 || j == background[0].length-6){
                    
                    background[i][j] = invis;
                    
                } else background[i][j] = Color.BLACK;
                
            }
            
        }
        
        pieceQueue = new LinkedList<>();
        for(int i = 0; i < previewSize; i++) pieceQueue.add(getNewBlock());
        
    }
    public void addGameThread(GameThread gameThread){
        this.gameThread = gameThread;
        gameThread.updateSpeed(LINES_COUNT);
    }
    public void resetBackground(){
        
        arr = new ArrayList<>(Arrays.asList("T","O","I","Z","S","L","J"));
        shadow = null;
        block = null;
        hold = null;
        for(int i = 0; i < background.length; i++){
            
            for(int j = 0; j < background[0].length; j++){
                
                if(i == background.length-1 || j == 5 || j == background[0].length-6){
                    
                    background[i][j] = invis;
                    
                } else background[i][j] = Color.BLACK;
                
            }
            
        }
        
        pieceQueue = new LinkedList<>();
        for(int i = 0; i < previewSize; i++) pieceQueue.add(getNewBlock());
        
    }
    private void getShadowPosition(){
        
        if(shadow == null) return;
        shadow.setShape(block.getState());
        shadow.setX(block.getX());
        shadow.setY(block.getY());
        while(moveShadowDown());
        
    }
    private Tetrominos getNewBlock(){
        int randomNum = getSeed(rd);
        int index = randomNum % arr.size();
        
        Tetrominos res = new Tetrominos(arr.get(index),false);
        arr.remove(index);
        if(arr.isEmpty()) arr = new ArrayList<>(Arrays.asList("T","O","I","Z","S","L","J"));
        
        return res;
        
    }
    public void spawnNewBlock(){
        pieceQueue.add(getNewBlock());
//        pieceQueue.add(new Tetrominos("I",false));
        spawnBlock(pieceQueue.pop().getName());
    }
    private void spawnBlock(String s){
        
        block = new Tetrominos(s,false);
        shadow = new Tetrominos(s,true);
        block.spawn(COLUMNS);
        shadow.spawn(COLUMNS);
        getShadowPosition();
        int st = block.getState();
        while(conflict(block.getShape(st),block.getY(),block.getX())) block.setY(block.getY()-1);
        
    }
    public void rotate(Tetrominos block, int d){
        
        if(block.getName().equals("O")) return;
        int st = block.getState();
        int nextSt = (st+d+4)%4;
        int[][] shape = block.getShape(nextSt);
        int x = block.getX();
        int y = block.getY();
        int[][] srsX = block.getSrsX();
        int[][] srsY = block.getSrsY();
        // test cases
        for(int i = 0; i < 5; i++){
            
            int dx = srsX[i][st] - srsX[i][nextSt];
            int dy = srsY[i][st] - srsY[i][nextSt];
            int newX = x+dx;
            int newY = y-dy;
            if(!conflict(shape, newY, newX)){
                
                block.setShape(nextSt);
                block.setX(newX);
                block.setY(newY);
                break;
                
            }
            
        }
        
    }
    private boolean conflict(int[][] shape, int y, int x){
        
        int h = block.getHeight();
        int w = block.getWidth();
        for(int i = 0; i < h; i++){
            
            for(int j = 0; j < w; j++){
                
                if(y+i < 1) continue;
                if(shape[i][j] != 0 && background[y+i][x+j] != Color.BLACK) return true;
                
            }
            
        }
        return false;
        
    }
    public boolean outOfBounds(){
        
        if(block.getY() < 0){
            
//            block = null; // prevent game control after game over
            return true;
            
        }
        return false;
        
    }
    private int getSeed(Random rd){
        
        return Math.abs(rd.nextInt());
        
    }
    public void hold(){
        
        if(canHold){
            canHold = false;
            String name = block.getName();
            if(hold == null) spawnNewBlock();
            else spawnBlock(hold.getName());
            hold = new Tetrominos(name,false);
            repaint();
            
        }
        
    }
    public void rotateR(){
        
        rotate(block,1);
        repaint();
        
    }
    public void rotateL(){
        
        rotate(block,-1);
        repaint();
        
    }
    public void rotateB(){
        
        if(block.getName().equals("O")) return;
        int st = block.getState();
        int[][] reverseTableX = {{0,0,1,-1,1,-1},
                                {0,1,1,1,0,0},
                                {0,0,-1,1,-1,1},
                                {0,-1,-1,-1,0,0}};
        int[][] reverseTableY = {{0,1,1,1,0,0},
                                {0,0,2,1,2,1},
                                {0,-1,-1,-1,0,0},
                                {0,0,2,1,2,1}};
        int nextSt = (st+2+4)%4;
        int[][] shape = block.getShape(nextSt);
        int x = block.getX();
        int y = block.getY();
        // test cases
        for(int i = 0; i < 6; i++){
            
            int dx = reverseTableX[st][i];
            int dy = reverseTableY[st][i];
            int newX = x+dx;
            int newY = y-dy;
            if(!conflict(shape, newY, newX)){
                
                block.setShape(nextSt);
                block.setX(newX);
                block.setY(newY);
                break;
                
            }
            
        }
        repaint();
        
    }
    public boolean moveBlockRight(){
        
        if(canMoveRight()){
            
            block.moveRight();
            repaint();
            return true;
            
        }
        
        return false;
    }
    public boolean moveBlockLeft(){
        
        if(canMoveLeft()){
            
            block.moveLeft();
            repaint();
            return true;
            
        }
        
        return false;
    }
    public boolean moveBlockDown(){
        
        if(!canMoveDown(block)) {
            return false;
        }
        
        block.moveDown();
        repaint();
        return true;
        
    }
    public boolean moveShadowDown(){
        
        if(!canMoveDown(shadow)) return false;
        
        shadow.moveDown();
        repaint();
        return true;
        
    }
    public void dropBlock(){
        
//        while(canMoveDown(block)) block.moveDown();
//        repaint();
        block.setX(shadow.getX());
        block.setY(shadow.getY());
//        saveToBackground();
        repaint();

    }
    public boolean canRotateR(){
        
        int[][] shape = block.getNextShape();
        int c = block.getWidth();
        int r = block.getHeight();
        
        for(int i = 0; i < r; i++){
            
            for(int j = 0; j < c; j++){
                
                if(shape[j][i] == 1){
                    
                    int x = i + block.getX();
                    int y = j + block.getY();
                    if(x < 0 || y < 0) continue;
                    if(background[y][x] != Color.BLACK) return false;
                    
                }
                
            }
            
        }
        return true;
        
    }
    public boolean canRotateL(){
        
        int[][] shape = block.getPrevShape();
        int c = block.getWidth();
        int r = block.getHeight();
        
        for(int i = 0; i < c; i++){
            
            for(int j = 0; j < r; j++){
                
                if(shape[j][i] == 1){
                    
                    int x = i + block.getX();
                    int y = j + block.getY();
                    if(x < 0 || y < 0) continue;
                    if(background[y][x] != Color.BLACK) return false;
                    
                }
                
            }
            
        }
        return true;
        
    }
    public boolean canMoveRight(){
        
        int st = block.getState();
        int[][] shape = block.getShape(st);
        int c = block.getWidth();
        int r = block.getHeight();
        
        for(int i = 0; i < c; i++){
            
            for(int j = r-1; j >= 0; j--){
                
                if(shape[j][i] == 1){
                    
                    int x = i + block.getX() + 1;
                    int y = j + block.getY();
                    if(x > COLUMNS+5) return false;
                    if(y < 0) break;
                    if(background[y][x] != Color.BLACK) return false;
                    
                }
                
            }
            
        }
        return true;
    }
    public boolean canMoveLeft(){
        
        int st = block.getState();
        int[][] shape = block.getShape(st);
        int c = block.getWidth();
        int r = block.getHeight();
        
        for(int i = 0; i < c; i++){
            
            for(int j = 0; j < r; j++){
                
                if(shape[j][i] == 1){
                    
                    int x = i + block.getX() - 1;
                    int y = j + block.getY();
                    if(x <= 5) return false;
                    if(y < 0) break;
                    if(background[y][x] != Color.BLACK) return false;
                    
                }
                
            }
            
        }
        return true;
    }
    public boolean canMoveDown(Tetrominos t){
        
        int st = block.getState();
        int[][] shape = t.getShape(st);
        int c = t.getWidth();
        int r = t.getHeight();
        
        for(int i = 0; i < c; i++){
            
            for(int j = r-1; j >= 0; j--){
                
                if(shape[j][i] == 1){
                    
                    int x = i + t.getX();
                    int y = j + t.getY() + 1;
                    if(y < 1) continue;
                    if(t.isShadow()){
                        
                        if(background[y][x] != Color.BLACK) return false;
                        
                    }else{
                        
                        if(!isBackground(background[y][x])) return false;
                        
                    }
                    
                }
                
            }
            
        }
        
        return true;
    }
    
    public void saveToBackground(){
        
        int st = block.getState();
        int[][] shape = block.getShape(st);
        int h = block.getHeight();
        int w = block.getWidth();
        int x = block.getX();
        int y = block.getY();
        Color color = block.getColor();
        
        
        
        for(int i = 0; i < h; i++){
            
            for(int j = 0; j < w; j++){
                
                
                if(shape[i][j] == 1){
                    if( i+y < 1 ) continue;
                    background[i+y][j+x] = color;
                    
                }
                
            }
            
        }
        canHold = true;
        
    }
    private boolean isBackground(Color c){
        
        return c == Color.BLACK || c == new Color(175,41,138,128) || 
               c == new Color(227,159,2,128) || c == new Color(15,155,215,128) || 
               c == new Color(227,91,2,128) || c == new Color(33,65,198,128) || 
               c == new Color(89,177,1,128) || c == new Color(215,15,55,128);
        
    }
    public boolean clearLines(){
        
        // check line clear
        boolean check;
        int count = 0;
            
        // check if bottom line is full
        int r = ROWS;
        while(r > 0){
            check = true;
            for(int i = 6; i < 16; i++){
                
                if(isBackground(background[r][i])){
                    check = false;
                    break;
                }
            }
            // clear bottom line by moving all background downwards by 1 block
            if(check){
                count++;
                clearLine(r);
                shiftLineDown(r);
                clearLine(0);
                r++;
            }
            r--;
        }
        this.LINES_COUNT += count;
        gameThread.updateSpeed(LINES_COUNT);
        this.SCORE += 100 * count;
        return count > 0;
    }
    private void shiftLineDown(int r){
        
        for(int row = r; row > 0; row--){

            for(int col = 6; col < 16; col++) background[row][col] = background[row-1][col];

        }
        
    }
    private void clearLine(int r){
        
            for(int col = 6; col < 16; col++) background[r][col] = Color.BLACK;
        
    }
    private void drawShape(Graphics g, Tetrominos block){
        
        if(block == null) return;
        Color color = block.getColor();
        int st = block.getState();
        
        for(int i = 0; i < block.getHeight(); i++){
            
            for(int j = 0; j < block.getWidth(); j++){
                
                int x = block.getX();
                int y = block.getY();
                if(block.getShape(st)[i][j] == 1){
                    if(y+i > 0) drawBlockCell(g,color,(x+j)*CELL_SIZE,(y+i)*CELL_SIZE);
                }
                
            }
            
        }
        
    }
    private void drawPreviewShape(Graphics g, Tetrominos block, int offsetX, int offsetY){
        
        Color color = block.getColor();
        int st = block.getState();
        
        for(int i = 0; i < block.getHeight(); i++){
            
            for(int j = 0; j < block.getWidth(); j++){
                
                int x = offsetX;
                int y = offsetY;
                if(block.getName().equals("I")){
                    
                    y--;
                    x--;
                    
                }
                if(block.getName().equals("O")) x++;
                if(block.getShape(st)[i][j] == 1){
                    if(y+i > 0) drawBlockCell(g,color,(x+j)*CELL_SIZE,(y+i)*CELL_SIZE);
                }
                
            }
            
        }
        
    }
    private void drawBackground(Graphics g){
        
        for(int i = 1; i < background.length; i++){
            
            for(int j = 5; j < COLUMNS+7; j++){
                
                if(background[i][j] != null){
                    
                    drawBackgroundCell(g, background[i][j], j*CELL_SIZE, i*CELL_SIZE);
                    
                }
                
            }
            
        }
        
    }
    private void drawSide(Graphics g){
        
        for(int i = 1; i < background.length; i++){
            
            for(int j = 0; j < 5; j++){
                
                if(background[i][j] != null){
                    
                    drawBlockCell(g, background[i][j], j*CELL_SIZE, i*CELL_SIZE);
                    
                }
                
            }
            
            for(int j = COLUMNS+7; j < background[0].length; j++){
                
                if(background[i][j] != null){
                    
                    drawBlockCell(g, background[i][j], j*CELL_SIZE, i*CELL_SIZE);
                }
                
            }
            
        }
        
    }
    private void drawBlockCell(Graphics g, Color color, int x, int y){
        
        g.setColor(color);
        g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
        
    }
    private void drawBackgroundCell(Graphics g, Color color, int x, int y){
        
        g.setColor(color);
        g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
        g.setColor(color == Color.BLACK ? borderColor : color);
        g.drawRect(x, y, CELL_SIZE, CELL_SIZE);
        
    }
    private void drawHold(Graphics g){
        
        if(hold == null) return;
        int offsetX = 2;
        int offsetY = 2;
        if(hold.getName().equals("I")){
            offsetX -= 2;
            offsetY -= 1;
        }
        int st = hold.getState();
        int[][] shape = hold.getShape(st);
        int h = hold.getHeight();
        int w = hold.getWidth();
        Color color = hold.getColor();
        for(int i = 0; i < h; i++){
            
            for(int j = 0; j < w; j++){
                
                if(shape[i][j] != 0){
                    
                    drawBlockCell(g, color, (j+offsetX)*CELL_SIZE, (i+offsetY)*CELL_SIZE);
                    
                }
                
            }
           
        }
        
    }
    private void drawPreview(Graphics g){
        
        int offsetX = 17;
        int offsetY = 2;
        
        for(int i = 0; i < previewSize; i++){
            
            int j = i*3;
            drawPreviewShape(g, pieceQueue.get(i), offsetX, j+offsetY);
            
        }
        
    }
    
    public void drawBackGroundGameOver(Graphics g) {
        for(int i = 1; i < background.length - 1; i++){
            
            for(int j = 6; j < COLUMNS+6; j++){
                
                if(background[i][j] != Color.BLACK) {
                    
                    drawBackgroundCell(g, Color.GRAY, j*CELL_SIZE, i*CELL_SIZE);
                }
            }
        }
    }
    
    public void REPAINT() {
        repaint();
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
    
    public boolean getGameOver() {
        return this.gameOver;
    }
    
    public void reset(){
        gameOver = false;
        this.SCORE = 0;
        this.LINES_COUNT = 0;
        this.resetBackground();
        this.canHold = true;
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g){
        
        super.paintComponent(g);
        
        drawSide(g);
        drawBackground(g);
        drawHold(g);
        drawPreview(g);
        getShadowPosition();
        drawShape(g,shadow); // draw shadow first so that the actual block is in front of shadow
        drawShape(g,block);
        
        if (gameOver) drawBackGroundGameOver(g);
    }
    
}
