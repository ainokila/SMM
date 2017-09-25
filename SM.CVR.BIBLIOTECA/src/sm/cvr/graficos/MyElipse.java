/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.cvr.graficos;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

/**
 * Clase MyElipse,
 * clase que hace referencia a una elipse, una elipse tiene un relleno por tanto
 * extiende a Fillerable, con la que se podrá rellena y usar las propiedades de
 * Bordeable y MyShape
 * 
 * @author Cristian Vélez Ruiz
 */
public class MyElipse  extends Fillerable {
    
    /**
     * Objeto de tipo Ellipse2D el cual usaremos para crear la clase
     */
    private Ellipse2D elipse;
    
    /**
     * Constructor de MyElipse, el cual acepta dos puntos, punto inicio y punto 
     * final, se le crea un borde y un relleno por defecto.
     * 
     * @param start Punto de inicio de la elipse
     * @param end  Punto final de la elipse
     */
    public MyElipse(Point2D start, Point2D end){
        this.elipse = new Ellipse2D.Double(start.getX(),start.getY(),end.getX(),end.getY());  
        this.border = new Border();
        this.filler = new Filler();
    }
    
    /**
     * Modifica el punto de inicio y final de la elipse
     * @param start Punto donde comienza la diagonal
     * @param end Punto donde termina la diagonal
     */
    public void setDiagonal(Point2D start,Point2D end){
        this.elipse.setFrameFromDiagonal(start, end);
    }
    
    
    /**
     * Metodo que sobrescribe el draw de MyShape, si tiene relleno pinta con 
     * relleno y si no sin relleno, se encarga tambien de aplicar el borde y su 
     * trasnparencia
     * 
     * @param g2d  Graphics2D donde aplicar los atributos
     */
    @Override
    public void draw(Graphics2D g2d) {
        this.border.applyBorder(g2d);
        g2d.setComposite(transparency);
        if(filler.getFill()){
            this.filler.applyFiller(g2d);
            g2d.fill(elipse);
        }else{
            g2d.draw(elipse);
        }
            
        
    }
//
//    @Override
//    public void setLocation(Point2D p) {

//    }
    
    @Override
    public void setLocation(Point2D p) {
        System.out.println("Cambio posicion");
        
        double anchura = this.elipse.getHeight();
        double altura = this.elipse.getWidth();

        Point aux = new Point();
        aux.x = (int)p.getX() +(int) altura;
        aux.y = (int)p.getY() +(int) anchura;


        this.elipse.setFrameFromDiagonal(p, aux);
    }
    
}
