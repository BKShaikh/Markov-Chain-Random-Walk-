/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab.pkg04;

import java.util.ArrayList;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Shaikh
 */
public class Lab04 extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        RandomWalkPane pane = new RandomWalkPane();

        Button btStart = new Button("Start");
        HBox hButtons = new HBox(btStart);
        hButtons.setAlignment(Pos.CENTER);
        hButtons.setPadding(new Insets(10, 10, 10, 10));
        btStart.setOnAction(e -> pane.play());             // ye kyon nhi chl rha 
        BorderPane borderPane = new BorderPane(pane, null, null, hButtons, null);
        borderPane.setMinHeight(400);
        borderPane.setMinWidth(400);
        primaryStage.setScene(new Scene(borderPane));
        primaryStage.setTitle("Markov bhai ki tun hone k bad bgri hui chal :P");
        pane.setTranslateX(100);
        pane.setTranslateY(100);
        primaryStage.show();
//        btn.setText("Say 'Hello World'");
//        btn.setOnAction(new EventHandler<ActionEvent>() {
//            
//            @Override
//            public void handle(ActionEvent event) {
//                System.out.println("Hello World!");
//            }
//        });

//        StackPane root = new StackPane();
//        root.getChildren().add(btn);
//        
//        Scene scene = new Scene(root, 300, 250);
//        
//        primaryStage.setTitle("Hello World!");
//        primaryStage.setScene(scene);
//        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private class RandomWalkPane extends Pane {

        private double size;
        private double squareCount;
        private double w;
        private double h;

        ArrayList<Point2D> points;
        private Point2D centerP;

        private Point2D currentP;
        ArrayList<Line> lines = new ArrayList<>(); // pure path ko is main save kr rhe 
        private int lineIndex = 0;

        Timeline timeline;

        private boolean hasHitBorder= false;

        private void nextLine() {
            getChildren().add(lines.get(lineIndex++));
        }

//         private boolean nextPath()
//         {
//             checkBorder(currentP);
//             return false;
//         }
        RandomWalkPane() {

            size = 20;
            squareCount = 11;
            w = size * squareCount;
            h = w;

            setPrefWidth(w);
            setPrefHeight(h);

            if (squareCount % 2 == 0) {
                centerP = new Point2D(w / 2, h / 2);
            } else {
                centerP = new Point2D(w / 2 - (size / 2), h / 2 - (size / 2));
            }

            points = new ArrayList<>();
            drawLayout();
            getChildren().add(new Circle(centerP.getX(), centerP.getY(), 2));
            PaneCollection.removePoint(points,centerP.getX(),centerP.getY());
            
          
//            // path as list of lines
            currentP = centerP;
//            Point2D rp = new Point2D(currentP.getX() + size, currentP.getY()); // right movement 
//            Line line = new Line(currentP.getX(), currentP.getY(), rp.getX(), rp.getY());
//            currentP = rp; // updation of current point
//            lines.add(line);
//            getChildren().add(lines.get(lineIndex++));
//
//            //up movement
//            Point2D rp2 = new Point2D(currentP.getX(), currentP.getY() - size); // up movement 
//            Line line2 = new Line(currentP.getX(), currentP.getY(), rp2.getX(), rp2.getY());
//            currentP = rp2; // updation of current point
//            lines.add(line2);
//            getChildren().add(lines.get(lineIndex++));

            while (nextPath());

            timeline = new Timeline(new KeyFrame(Duration.seconds(0.8), e -> getChildren().add(lines.get(lineIndex++))));
            timeline.setCycleCount(lines.size());

        }

        void play() {
            timeline.play();

        }

        private void drawLayout() {

            //horizontal line bn rhi hain aur possible points add ho rhe hain 
            for (int i = 0; i <= squareCount; i++) {

                Line line = new Line(0, i * size, w, i * size);
                line.setOpacity(0.1);
                getChildren().add(line);

                //adding all possible line 
                for (int j = 0; j <= squareCount; j++) {

                    double x = j * size;
                    double y = i * size;

                    points.add(new Point2D(x, y));

                }

            }

            //vertical lines bn rhi hain 
            for (int i = 0; i <= squareCount; i++) {

                Line line = new Line(i * size, 0, i * size, h);
                line.setOpacity(0.1);
                getChildren().add(line);

            }

        }

        private boolean nextPath() {
            //get possible path 
            points.add(centerP);
            Point2D[] possiblePs = new Point2D[4];
             Random rand = new Random();
            
            //origin is from top left
            possiblePs[0] = new Point2D(currentP.getX(), currentP.getY() - size); //up
            possiblePs[1] = new Point2D(currentP.getX(), currentP.getY() + size); //down
            possiblePs[2] = new Point2D(currentP.getX() + size, currentP.getY()); //right
            possiblePs[3] = new Point2D(currentP.getX() - size, currentP.getY()); //left

            boolean hasmoved = false;
            boolean[] isUsed = new boolean[possiblePs.length];
            Point2D p = null;
            
            for (int i = 0; i < 4; i++) {
                //= (int) Math.random() * 4 ;
                int randIndex;
              //  double randIndex1 = rand.nextInt(0);
               
                double r = Math.random();
                if (r < 0.25) {
                    randIndex = 2;
                 
                } else if (r < 0.50) {
                    randIndex = 3;
               
                } else if (r < 0.65) {
                    randIndex = 0;
             
                } else {
                    randIndex = 1;
             
                }
//                 if (randIndex1 < 0.25) {
//                    randIndex = 2;
//                 
//                } else if (randIndex1 < 0.50) {
//                    randIndex = 3;
//               
//                } else if (randIndex1 < 0.65) {
//                    randIndex = 0;
//             
//                } else {
//                    randIndex = 1;
//             
//                }
            
                if (!isUsed[randIndex]) {

                    //i++;
                    p = possiblePs[randIndex];
                    System.out.println("avcc " + p);
                    System.out.println(isUsed[randIndex]);
                    if (PaneCollection.containsPoint(points, p.getX(), p.getY()) && !hasHitBorder) {
                        isUsed[randIndex] = true;
                    //    System.out.println(p);
                        Line line = new Line(currentP.getX(), currentP.getY(), p.getX(), p.getY());
                        currentP = p;
                        lines.add(line);

                        PaneCollection.removePoint(points, p.getX(), p.getY());

                        hasmoved = true;
                        checkBorder(currentP);
                        break;
                    }
                }
            }
//            for (int i = 0; i < 4;) {
//
//                int ranIndex = (int) (Math.random() * 4);
//
//                if (!isUsed[ranIndex]) {
//
//                    isUsed[ranIndex] = true;
//                    i++;
//                    Point2D p = possiblePs[ranIndex];
//
//                    //check for valid point
//                    if (PaneCollection.containsPoint(points, p.getX(), p.getY())) {
//
//                        Line line = new Line(currentP.getX(), currentP.getY(), p.getX(), p.getY());
//                        currentP = p;   //updation of current point
//                        lines.add(line);
//                        //remove point from point tracker to visit it once 
//                        PaneCollection.removePoint(points, p.getX(), p.getY());
//
//                        //update hasMoved
//                        hasmoved = true;
//
//                        //collision detection or boundry check
//                        checkBorder(currentP);
//                        break;
//
//                    }
//
//                }
//
//            }
                if (!hasmoved && !hasHitBorder) {
                Line line = new Line(currentP.getX(), currentP.getY(), p.getX(), p.getY());
                currentP = p;
                lines.add(line);
               
            }

      //      System.out.println(lines.size());
            return hasmoved;   //return moved status
        }

        private void checkBorder(Point2D p) {

            double x = p.getX();
            double y = p.getY();

            if (x <= 0 || x >= w) {
                hasHitBorder = true;
            }
            if (y <= 0 || y >= h) {
                hasHitBorder = true;
            }

        }

    }

}
