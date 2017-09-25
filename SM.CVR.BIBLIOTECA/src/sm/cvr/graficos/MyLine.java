/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.cvr.graficos;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 * Clase MyLine,
 * esta clase se usa para poder dibujar lineas, una linea tiene como propiedad 
 * el borde, hereda de Bordeable.
 * 
 * @author Cristian Vélez Ruiz
 */
public class MyLine extends Bordeable {
    
    /**
     * Line2D atributo donde trabajar en esta clase
     */
    private Line2D.Double line;
    
    /**
     * Constructor de la clase MyLine, recibe punto inicio y final de la linea
     * @param start Punto inicio de la linea
     * @param end Punto final de la linea
     */
    public MyLine(Point2D start, Point2D end){
        this.line = new Line2D.Double(start, end);
        this.border = new Border();
    }
    
    /**
     * Modificador de la linea, recibe punto inicio y final de la linea
     * @param start Punto inicio de la linea
     * @param end Punto final de la linea
     */
    public void setLine(Point2D start, Point2D end){
        this.line.setLine(start, end);
    }

    /**
     * Metodo sobrescrito de MyShape, este metodo nos pinta la linea y sus 
     * atributos en el Graphics2D que se le pase como parametro.
     * @param g2d Graphics2D donde pintar la linea y sus atributos
     */
    @Override
    public void draw(Graphics2D g2d) {
        this.border.applyBorder(g2d);
        g2d.setComposite(transparency);
        g2d.draw(line);
    }

    
    /**
     * Cambia la localización de la Linea, recibe el punto donde irá situada
     * @param p  Nueva localización
     */
    @Override
    public void setLocation(Point2D p) {
        Point auxPd = (Point) p;
        Point fin = new Point(0,0);
        
        if(((this.line.x2-this.line.x1)>0) && ((this.line.y2-this.line.y1)>0) ){
                fin.x = auxPd.x +(int) Math.abs(this.line.x1-this.line.x2);
                fin.y = auxPd.y +(int) Math.abs(this.line.y1-this.line.y2);
                

        }else if(((this.line.x2-this.line.x1)>0) && ((this.line.y2-this.line.y1)<0) ){
            
                fin.x = auxPd.x +(int) Math.abs(this.line.x1-this.line.x2);
                fin.y = auxPd.y -(int) Math.abs(this.line.y1-this.line.y2);
                
        }else if(((this.line.x2-this.line.x1)<0) && ((this.line.y2-this.line.y1)>0) ){
                fin.x = auxPd.x -(int) Math.abs(this.line.x1-this.line.x2);
                fin.y = auxPd.y +(int) Math.abs(this.line.y1-this.line.y2);
            
        }else{
                fin.x = auxPd.x -(int) Math.abs(this.line.x1-this.line.x2);
                fin.y = auxPd.y -(int) Math.abs(this.line.y1-this.line.y2);
        }
        this.setLine(p, fin);
        
        
    }
}
