package com.example.life;
import java.util.ArrayList;

public class Ant extends Bug {
    private int x;   //row
    private int y;   //column

    String loc;
    private long startTime;
    private long reproduceTime;
    private long agingTime;
    private final long birthTime;
    private int typeOfAnt;
    private long deathTime;
    public Ant(int x,int y){
        typeOfAnt = 1;
        this.x = x;
        this.y = y;
        startTime = System.nanoTime();
        reproduceTime = System.nanoTime();
        agingTime = System.nanoTime();
        birthTime = System.nanoTime();
        deathTime = System.nanoTime();
    }
    public void changeLoc(int[][] gameGrid, ArrayList<Ant> tempAnt){
        boolean check = false;
        while (!check) {
            int tempx = x;
            int tempy = y;
            if (Math.random() > .5 && tempx <29) {
                tempx++;
            } else if(tempx > 0){
                tempx--;
            }
            else if(tempx == 0){
                tempx ++;
            }
            if (Math.random() > .5 && tempy < 19) {
                tempy++;
            } else if (tempy >0){
                tempy--;
            }
            else if(tempy == 0){
                tempy++;
            }
            if (gameGrid[tempx][tempy] == 0 ) {
                check = true;
                gameGrid[tempx][tempy] = typeOfAnt;
                gameGrid[x][y] = 0;
                x = tempx;
                y = tempy;
            }

        }


        }
    public void changeAdult(int value) {
        typeOfAnt = value;
    }


    public void resetStartTime(){
        startTime = System.nanoTime();
    }

    public void resetDeathTIme(){
        deathTime = System.nanoTime();
    }

    public void resetReproduceTime(){
        reproduceTime = System.nanoTime();
    }

    public void resetAgingTime(){
        agingTime = System.nanoTime();
    }

    public long getStartTime(){
        return startTime;
    }

    public long getReproduceTime(){
        return reproduceTime;
    }

    public long getAgingTime(){
        return agingTime;
    }

    public long getBirthTime(){
        return birthTime;
    }

    public long getDeathTime(){
        return deathTime;
    }

    public int getY() {
        return this.y;
    }

    public boolean closeToAnt(ArrayList<Ant> tempAnt){
        for(int i = 0; i<tempAnt.size();i++){
            if(tempAnt.get(i).getX() >= x-1 && tempAnt.get(i).getX()<=x+1 && tempAnt.get(i).getY()>=x-1 && tempAnt.get(i).getY()<=y+1
                    && tempAnt.get(i).getX()!= x && tempAnt.get(i).getY()!=y){
                return true;
            }
        }
        return false;
    }

    public int getX(){
        return this.x;
    }

    }



