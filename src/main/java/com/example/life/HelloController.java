package com.example.life;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;



import java.awt.Transparency;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class HelloController {
    Random randNum = new Random();
    int x = 20;
    int y = 30;
    Button[][] btn = new Button[x][y];
    int[][] gameGrid = new int[x][y];
    int reproduceCounter = 0;
    ArrayList<Ant> ants = new ArrayList<>();

    @FXML
    private AnchorPane aPane;

    @FXML
    private GridPane gPane;


    @FXML
    private void handleStart(ActionEvent event) {
        System.out.println(gameGrid.length + "lengthrows");
        System.out.println(gameGrid[0].length + "columns");

        for(int i=0; i<btn.length; i++){
            for(int j=0; j<btn[0].length;j++){

                //Initializing 2D buttons with values i,j
                btn[i][j] = new Button();
                btn[i][j].setStyle("-fx-background-color:#d3d3d3");

                btn[i][j].setPrefWidth(25);

//                btn[i][j].setPrefSize(25, 5);
                //Paramters:  object, columns, rows
                gPane.add(btn[i][j], j, i);
                gameGrid[i][j]=0;


            }
        }

        gPane.setGridLinesVisible(true);

        gPane.setVisible(true);

        EventHandler z = new EventHandler<MouseEvent>()
        {

            @Override
            public void handle(MouseEvent t)
            {

            }

        };
        for(int i=0; i<btn.length; i++){
            for(int j=0; j<btn[0].length;j++){
                btn[i][j].setOnMouseClicked(z);

            }
        }
        start();
    }
    @FXML
    public void handleAddAnt()
    {
        ants.add(new Ant(7,8));
        gameGrid[ants.get(ants.size()-1).getX()][ants.get(ants.size()-1).getY()]=1;
        updateScreen();
    }

    public int chooseRandomAntForReproduction(){
        int randomIndex = (int) (Math.random()*ants.size());
        return randomIndex;
    }

    ArrayList<Locations> tempLocs = new ArrayList<>();
    public void reproduce(int currentAnt){

        tempLocs.clear();
        int x;
        int y;

        x = ants.get(currentAnt).getX();
        y = ants.get(currentAnt).getY();
        for(int i = x-1; i < x+2 && x<19 && x>0; i++){
            for(int j = y-1; j<y+2 && y < 29 && y >0;j++){
                if(checkEmptyAroundMe(i,j)){
                    tempLocs.add(new Locations(i,j));
                }
            }
        }

        int randomIndex = (int) (Math.random() * (tempLocs.size()-1));

        System.out.println(randomIndex + "random number");
        System.out.println(tempLocs.size() + "size of array");
        if(tempLocs.size()>0){
            ants.add(new Ant(tempLocs.get(randomIndex).getX(),tempLocs.get(randomIndex).getY()));
            gameGrid[tempLocs.get(randomIndex).getX()][tempLocs.get(randomIndex).getY()] = 1;
        }
    }

    public boolean checkEmptyAroundMe(int i, int j){
        return gameGrid[i][j] == 0;
    }


    public void updateScreen(){

        for(int i=0; i<btn.length; i++) {
            for (int j = 0; j < btn[0].length; j++) {
                if (gameGrid[i][j]==0){
                    btn[i][j].setStyle("-fx-background-color:#d3d3d3");
                }
                else if (gameGrid[i][j]==1){
                    btn[i][j].setStyle("-fx-background-color:#fc6a6a");
                }
                else if(gameGrid[i][j]==2){
                    btn[i][j].setStyle("-fx-background-color: #960202");
                }
                else if(gameGrid[i][j]==3){
                    btn[i][j].setStyle("-fx-background-color: #380000");
                }
            }
        }
    }



    public void start() {

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(ants.size()>0){
                    for (int i = 0; i<ants.size();i++){//doesn't update size in for each loop
                        if(now - ants.get(i).getReproduceTime() > 3000000000.0){
                            reproduce(i);
                            ants.get(i).resetReproduceTime();
                        }

                        if(now - ants.get(i).getStartTime() > 1000000000.0){
                            ants.get(i).changeLoc(gameGrid, ants);
                            ants.get(i).resetStartTime();
                        }

                    }
                    for (int i = 0;i<ants.size();i++) {
                        if (now - ants.get(i).getBirthTime() > 2000000000.0) {
                            int age = 2;
                            ants.get(i).changeAdult(age);

                        }
                        if (now - ants.get(i).getBirthTime() > 3000000000.0) {
                            int age = 3;
                            ants.get(i).changeAdult(age);
                        }
                    }

                    for(int i = ants.size()-1; i>=0;i--){
//                        System.out.println(now - ants.get(i).getBirthTime() + "time");
                        if(now - ants.get(i).getBirthTime()>10000000000.0){
                            if(randNum.nextInt(100000)>10000){
                                if(decideIfHappens(9)){
                                    System.out.println("ant has died after 10 seconds");
                                    gameGrid[ants.get(i).getX()][ants.get(i).getY()] =0;
                                    ants.remove(i);
//                                break;
                                }
                            }

                        }else if((now - ants.get(i).getBirthTime() > 7000000000.0)){
                            if(randNum.nextInt(100000)>75000){
                                if(decideIfHappens(3)){
                                    System.out.println("Ant has died after 7 seconds");
                                    gameGrid[ants.get(i).getX()][ants.get(i).getY()]=0;
                                    ants.remove(i);
//                                break;
                                }
                            }

                        }
                        else if((now - ants.get(i).getBirthTime() > 2000000000.0)){
                            if(randNum.nextInt(100000)>99998){
                                System.out.println("this happens");
                                if(decideIfHappens(1)){
                                    System.out.println("ant has died after 2 seconds");
                                    gameGrid[ants.get(i).getX()][ants.get(i).getY()]=0;
                                    ants.remove(i);
//                                break;
                                }
                            }

                        }
//                        else{
//                            if(decideIfHappens(0)){
//                                System.out.println("DIes upon birth");
//                                gameGrid[ants.get(i).getX()][ants.get(i).getY()]=0;
//                                ants.remove(i);
////                                break;
//                            }
//                        }

                    }

                }
                updateScreen();
            }
        }.start();
    }

    public boolean decideIfHappens(int chance){
        //can add as many cases as you would like with whatever probabilities you want
        //I made the numbers large, but that is because I am running the code every nanosecond.
        //If you run the code less often then change the numbers.  Or you can add a variable
        //multiplier to make it more universal.
        switch (chance){
            case 0://Almost no chance of happening
                return randNum.nextInt(10000000)>99990;
            case 1://slight chance of happening

                System.out.println("blah2");
                return randNum.nextInt(100000000)>95000;


            case 2://slightly greater chance of happening etc...
                return randNum.nextInt(10000000)>85000;
            case 3:
                return randNum.nextInt(10000000)>60000;
            case 4:
                return randNum.nextInt(10000000)>40000;
            case 5:
                return randNum.nextInt(10000000)>35000;
            case 6:
                return randNum.nextInt(10000000)>30000;
            case 7:
                return randNum.nextInt(10000000)>20000;
            case 8:
                return randNum.nextInt(10000000)>10000;
            case 9://95% chance of happening
                return randNum.nextInt(100000)>50;
            default://always happens
                return randNum.nextInt(1000)>0;
        }


    }
}



