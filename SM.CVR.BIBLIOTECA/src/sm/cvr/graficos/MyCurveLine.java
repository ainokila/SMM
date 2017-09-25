/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.cvr.graficos;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;

/**
 * Clase MyCurveLine,
 * esta clase hace referencia a una figura de una linea curva con un punto de 
 * control, esta clase tiene las propiedades de la clase abstracta Bordeable,
 * ya que tiene un borde.
 * 
 * @author Cristian Vélez Ruiz
 */
public class MyCurveLine extends Bordeable {

    public MyCurveLine() {
    }
    /**
     * Objeto de tipo linea-curva el cual usaremos para implementar la clase
     */
    private QuadCurve2D.Double curve;
    
    /**
     * Constructor de la clase curva, recibe punto inicio y punto fin, el punto 
     * de control se usa el punto medio entre los dos, tambien se le crea un 
     * borde por defecto
     * 
     * @param start Punto inicio de la linea-curva
     * @param end Punto fin de la linea-curva
     */
    public MyCurveLine(Point2D start, Point2D end){
        //this.line = new Line2D.Double(start, end);
        this.curve = new QuadCurve2D.Double(start.getX(), start.getY(), (start.getX()+end.getX())/2.0, (start.getY()+end.getY())/2.0, end.getX(), end.getY());
        this.border = new Border();
    }
    
    /**
     * Modificador del punto de inicio y punto final
     * @param start Punto inicio de la linea-curva
     * @param end Punto fin de la linea-curva
     */
    public void setLine(Point2D start, Point2D end){
        this.curve.setCurve(start.getX(), start.getY(), (start.getX()+end.getX())/2.0, (start.getY()+end.getY())/2.0, end.getX(), end.getY());
    }
    
    /**
     * Modificador del punto de control de la linea-curva
     * @param control Punto de control
     */
    public void setControl(Point2D control){
        this.curve.setCurve(this.curve.getP1(), control, this.curve.getP2());
    }
    
    /**
     * Metodo para dibujar la linea heredado de MyShape
     * @param g2d Graphics2D donde pintar la linea-curva y sus propiedades
     */
    @Override
    public void draw(Graphics2D g2d) {
        this.border.applyBorder(g2d);
        g2d.setComposite(transparency);
        g2d.draw(curve);
    }

    /**
     * Modificador de la localización de la linea-curva
     * @param p Nueva posición de la figura
     */
    @Override
    public void setLocation(Point2D p) {
        System.out.println("Cambio posicion");
    }

    
}
