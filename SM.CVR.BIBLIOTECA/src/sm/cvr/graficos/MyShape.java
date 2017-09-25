/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.cvr.graficos;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

/**
 * Clase abstracta MyShape,
 * esta clase abstracta es la clase principal, de donde heredaran todas las 
 * figuras, ya que por defecto toda forma tiene una localización, tambien una 
 * transparencia y un metodo draw para dibujar
 * 
 * @author Cristian Vélez Ruiz
 */
public abstract class MyShape{
    
    /**
     * Transparencia de la forma
     */
    protected AlphaComposite transparency;
    
    /**
     * Metodo abstracto para dibujar
     * @param g2d Parametro donde pintar la figura
     */
    public abstract void draw(Graphics2D g2d);
    
    /**
     * Metodo para modificar la localización de cada forma
     * @param p Nueva localización
     */
    public void setLocation(Point2D p){
        System.out.println("");
    }
    
    /**
     * Metodo para configurar la transparencia de una forma
     * @param transparency Nivel de transparencia 
     */
    public void setTransparency(float transparency){
       this.transparency = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency);

    }
    
    /**
     * Consultor de la transparencia
     * @return Devuelve el objeto AlphaComposite con el nivel de transparencia
     */
    public AlphaComposite getTransparency(){
        return this.transparency;
    }
    
}
