package testrismenu;

//@author Nevir2002

import java.awt.Color;

public class Tetrominos {

    private String name;
    private int[][] shape;
    private int[][][] shapes;
    private int[][] srsX;
    private int[][] srsY;
    private Color color;
    private int offsetX = 0,offsetY = 0;
    private int state;
    private boolean isShadow;
    
    public Tetrominos(String s, boolean f){
        
        isShadow = f;
        shapes = new int[4][][];
        name = s.toUpperCase();
        switch(name){
            
            case "T" -> {
                
                int[][] x = {{0,1,0},
                             {1,1,1},
                             {0,0,0}}; 
                shapes[0] = x;
                int[][] y = {{0,1,0},
                             {0,1,1},
                             {0,1,0}};  
                shapes[1] = y;
                int[][] z = {{0,0,0},
                             {1,1,1},
                             {0,1,0}};  
                shapes[2] = z;
                int[][] t = {{0,1,0},
                             {1,1,0},
                             {0,1,0}};  
                shapes[3] = t;
                color = isShadow ? new Color(87,20,69,255): new Color(175,41,138,255);
            }
            case "O" -> {
                int[][] x = {{1,1},
                             {1,1}};
                shapes[0] = shapes[1] = shapes[2] = shapes[3] = x;
                color = isShadow ? new Color(113,79,1,255) : new Color(227,159,2,255);
            }
            case "I" -> {
                int[][] x = {{0,0,0,0,0},
                             {0,0,0,0,0},
                             {0,1,1,1,1},
                             {0,0,0,0,0},
                             {0,0,0,0,0}}; 
                shapes[0] = x;
                int[][] y = {{0,0,0,0,0},
                             {0,0,1,0,0},
                             {0,0,1,0,0},
                             {0,0,1,0,0},
                             {0,0,1,0,0}}; 
                shapes[1] = y;
                int[][] z = {{0,0,0,0,0},
                             {0,0,0,0,0},
                             {1,1,1,1,0},
                             {0,0,0,0,0},
                             {0,0,0,0,0}}; 
                shapes[2] = z;
                int[][] t = {{0,0,1,0,0},
                             {0,0,1,0,0},
                             {0,0,1,0,0},
                             {0,0,1,0,0},
                             {0,0,0,0,0}};  
                shapes[3] = t;
                color = isShadow ? new Color(7,77,107,255) : new Color(15,155,215,255);
            }
            case "L" -> {
                int[][] x = {{0,0,1},
                             {1,1,1},
                             {0,0,0}}; 
                shapes[0] = x;
                int[][] y = {{0,1,0},
                             {0,1,0},
                             {0,1,1}};  
                shapes[1] = y;
                int[][] z = {{0,0,0},
                             {1,1,1},
                             {1,0,0}};  
                shapes[2] = z;
                int[][] t = {{1,1,0},
                             {0,1,0},
                             {0,1,0}};  
                shapes[3] = t;
                color = isShadow ? new Color(113,45,1,255) : new Color(227,91,2,255);
            }
            case "J" -> {
                int[][] x = {{1,0,0},
                             {1,1,1},
                             {0,0,0}}; 
                shapes[0] = x;
                int[][] y = {{0,1,1},
                             {0,1,0},
                             {0,1,0}};  
                shapes[1] = y;
                int[][] z = {{0,0,0},
                             {1,1,1},
                             {0,0,1}};  
                shapes[2] = z;
                int[][] t = {{0,1,0},
                             {0,1,0},
                             {1,1,0}};  
                shapes[3] = t;
                color = isShadow ? new Color(16,32,99,255) : new Color(33,65,198,255);
//                color.
            }
            case "S" -> {
                int[][] x = {{0,1,1},
                             {1,1,0},
                             {0,0,0}}; 
                shapes[0] = x;
                int[][] y = {{0,1,0},
                             {0,1,1},
                             {0,0,1}};  
                shapes[1] = y;
                int[][] z = {{0,0,0},
                             {0,1,1},
                             {1,1,0}};  
                shapes[2] = z;
                int[][] t = {{1,0,0},
                             {1,1,0},
                             {0,1,0}};  
                shapes[3] = t;
                color = isShadow ? new Color(44,88,0,255) : new Color(89,177,1,255);
            }
            case "Z" -> {
                int[][] x = {{1,1,0},
                             {0,1,1},
                             {0,0,0}}; 
                shapes[0] = x;
                int[][] y = {{0,0,1},
                             {0,1,1},
                             {0,1,0}};  
                shapes[1] = y;
                int[][] z = {{0,0,0},
                             {1,1,0},
                             {0,1,1}};  
                shapes[2] = z;
                int[][] t = {{0,1,0},
                             {1,1,0},
                             {1,0,0}};  
                shapes[3] = t;
                color = isShadow ? new Color(107,7,27,255) : new Color(215,15,55,255);
            }
            
        }
        if(name.equals("I")){
            
            int[][] x = {{0,-1,-1,0},
                        {-1,0,1,0},
                        {2,0,-2,0},
                        {-1,0,1,0},
                        {2,0,-2,0}};
            int[][] y = {{0,0,1,1},
                        {0,0,1,1},
                        {0,0,1,1},
                        {0,1,0,-1},
                        {0,-2,0,2}};
            srsX = x;
            srsY = y;
            
        }else if(!name.equals("O")){
            
            int[][] x = {{0, 0, 0, 0},
                        {0, 1, 0, -1},
                        {0, 1, 0, -1},
                        {0, 0, 0, 0},
                        {0, 1, 0, -1}};
            int[][] y = {{0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {0, -1, 0, -1},
                        {0, 2, 0, 2},
                        {0, 2, 0, 2}};
            srsX = x;
            srsY = y;
            
        }
        state = 0;
        shape = shapes[state];
        
    }
    
    public void spawn(int gridWidth){
        
        offsetX = (gridWidth - this.getWidth())/2 + 1;
        offsetY = -this.getHeight()+3;
        if(name.equals("I")) offsetY++;
        if(name.equals("O")) offsetY--;
        
    }
    
    public String getName(){return name;}
    public int getState(){return state;}
    public int[][] getSrsX(){return srsX;}
    public int[][] getSrsY(){return srsY;}
    public int[][] getShape(int st){return shapes[st];}
    public int[][] getNextShape(){return shapes[(state+1)%4];}
    public int[][] getPrevShape(){return shapes[(state+3)%4];}
    public void setShape(int st){
        state = st;
        shape = shapes[state];
    }
    public Color getColor(){return color;}
    public boolean isShadow(){return isShadow;}
    public int getHeight(){return shape.length;}
    public int getWidth(){return shape[0].length;}
    public int getX(){return offsetX+5;}
    public void setX(int x){offsetX = x-5;}
    public int getY(){return offsetY;}
    public void setY(int y){offsetY = y;}
    public void moveDown(){offsetY++;}
    public void moveLeft(){offsetX--;}
    public void moveRight(){offsetX++;}
    public int totalHeight(){return getY()+getHeight();}
    public void rotateRight(){
        
        state++;
        state%=4;
        shape = shapes[state];
    
    }
    public void rotateLeft(){
        
        state--;
        if(state < 0) state = 3;
        shape = shapes[(state%4)];
    
    }
    
}
