/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.cvr.graficos;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

/**
 * Clase MyRectangle,
 * esta clase hace referencia a un rectangulo, el cual puede tener un relleno, 
 * asi como un borde, por tanto hereda de Fillerable, para conseguir tener 
 * relleno, borde  y las propiedades de MyShape
 * 
 * @author Cristian Vélez Ruiz
 */
public class MyRectangle  extends Fillerable {
    /**
     * Rectangle al que expandir la clase
     */
    private Rectangle rectangle;
    
    /**
     * Constructor de la clase, se le pasan 2 puntos, punto de inicio y punto de 
     * fin del rectangulo, tambien se le aplica un borde y un relleno por 
     * defecto
     * @param start Punto de inicio del rectangulo
     * @param end Punto de fin del rectangulo
     */
    public MyRectangle(Point2D start, Point2D end){
        this.rectangle = new Rectangle((int)start.getX(),(int)start.getY(),(int)end.getX(),(int)end.getY());  
        this.border = new Border();
        this.filler = new Filler();
    }
    
    /**
     * Modificador de la clase, se le pasan 2 puntos, punto de inicio y punto de 
     * fin del rectangulo
     * @param start Punto de inicio del rectangulo
     * @param end Punto de fin del rectangulo
     */
    public void setDiagonal(Point2D start,Point2D end){
        this.rectangle.setFrameFromDiagonal(start, end);
    }
    
    /**
     * Metodo para dibujar el rectangulo en el Graphics2D, se encarga de configurar 
     * sus propiedades y pintar el rectangulo.
     * @param g2d Graphics2D donde pintar
     */
    @Override
    public void draw(Graphics2D g2d) {
        this.border.applyBorder(g2d);
        g2d.setComposite(transparency);
        if(filler.getFill()){
            this.filler.applyFiller(g2d);
            g2d.fill(rectangle);
        }else{
            g2d.draw(rectangle);
        }
            
        
    }

    /**
     * Modificador de la localización del rectangulo, se le pasa como parametro
     * su nueva posición
     * @param p Punto de localización del rectangulo
     */
    @Override
    public void setLocation(Point2D p) {
        System.out.println("Cambio posicion");
        this.rectangle.setLocation((Point)p);
    }
    
}
