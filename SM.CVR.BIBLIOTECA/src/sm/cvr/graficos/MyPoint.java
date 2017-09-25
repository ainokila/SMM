/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.cvr.graficos;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 * Clase MyPoint,
 * hace referencia a un punto con su grosor y tipo, se usa una linea con mismo 
 * punto de inicio y fin.
 * 
 * @author Cristian Vélez Ruiz
 */
public class MyPoint extends Bordeable{

    /**
     * Linea para crea el punto
     */
    private Line2D.Double line;
    
    /**
     * Constructor de MyPoint, recibe el punto donde ira ubicado y se crea un 
     * grosor por defecto
     * @param location Ubicación del punto
     */
    public MyPoint(Point2D location){
        
        this.border = new Border();
        this.border.setStroke(Border.CONTINUOUS, 1,EnumColor.NEGRO,false);        
        this.line = new Line2D.Double(location, location);
        
    }
    
    /**
     * Metodo para dibujar el punto en el Graphics2D, se encarga de configurar 
     * sus propiedades y pintar el punto.
     * @param g2d Graphics2D donde pintar
     */
    @Override
    public void draw(Graphics2D g2d) {
        this.border.applyBorder(g2d);
        g2d.setComposite(transparency);
        g2d.draw(line);
    }
    
    /**
     * Modificador de la localización del punto
     * @param p Nueva localización
     */
    @Override
    public void setLocation(Point2D p) {
        this.line.setLine(p, p);
    }
    
}
