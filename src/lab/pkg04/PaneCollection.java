/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab.pkg04;

import java.util.ArrayList;
import javafx.geometry.Point2D;

/**
 *
 * @author Shaikh
 */
public class PaneCollection {
    
        public static boolean containsPoint(ArrayList<Point2D> points, double x, double y)
        {
            for (Point2D p : points) {
                if(p.getX()==x && p.getY()==y)
                    return true;
            }
            return false;
           
        }
    
        public static boolean removePoint(ArrayList<Point2D> points, double x, double y)
        {
            int index=0;
             for (Point2D p : points) {
                if(p.getX()==x && p.getY()==y){
                    points.remove(index);
                    return true;
             
                }
                index ++;
             }
            return false;
        }
    
}
