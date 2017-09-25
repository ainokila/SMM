/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.cvr.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

/**
 * Clase MyText,
 * esta clase se usa para poder definir un texto, asi como su localización, su 
 * tipo de fuente, y el color con el que pintarla
 * @author Cristian Vélez Ruiz
 */
public class MyText extends MyShape{

    /**
     * Tipo de fuente a usar
     */
    private Font font;
    
    /**
     * Localizacion del texto
     */
    private Point2D location;
    
    /**
     * Contenido del texto
     */
    private String text;
    
    /**
     * Color del texto
     */
    private Color color;
    
    /**
     * Constructor del Mytext, recibe el texto que contiene, su fuente, el 
     * tamaño y su localización.
     * @param text Texto del MyText
     * @param font Estilo de la fuente
     * @param size Tamaño de la fuente
     * @param location Localizacion de la fuente
     */
    public MyText(String text,String font,int size,Point2D location){
        
        this.location = location;
        this.font = new Font(font, Font.BOLD, size);
        this.text=text;      

    }
    
    /**
     * Utilizado para pintar el MyText, asi como sus atributos
     * @param g2d Graphics2D donde pintar el MyText
     */
    @Override
    public void draw(Graphics2D g2d) {
        g2d.setFont(font);
        g2d.setComposite(transparency);
        g2d.setColor(color);
        g2d.drawString(text,(int)location.getX(),(int)location.getY());
    }

    /**
     * Modificador de la localización del texto
     * @param p Punto de localización del texto
     */
    @Override
    public void setLocation(Point2D p) {
        this.location = p;
    }
    
    /**
     * Modificador del color del texto
     * @param color Color del texto
     */
    public void setColor(EnumColor color){
        this.color = color.getColor();
    }
    
    /**
     * Consultor del color del texto
     * @return color actual del texto
     */
    public Color getColor(){
        return this.color;
    }
    
}
