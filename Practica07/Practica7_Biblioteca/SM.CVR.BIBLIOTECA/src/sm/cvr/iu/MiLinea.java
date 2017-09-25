/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.cvr.iu;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 *
 * @author Ainokila
 */
public class MiLinea extends Line2D.Double{
    
    public MiLinea(Point inicio,Point fin){
        super(inicio,fin);
    }
    
    @Override
    public boolean contains(Point2D pd){
        if(this.ptLineDist(pd)<30){
            return true;
        }
        return false;
    }
    
    public void setLocation(Point2D pd){
        Point auxPd = (Point) pd;
        Point fin = new Point(0,0);
        fin.x = auxPd.x +(int) Math.abs(this.x1-this.x2);
        fin.y = auxPd.y +(int) Math.abs(this.y1-this.y2);
        
        

        this.setLine(pd, fin);
    }
   
    
    
}
