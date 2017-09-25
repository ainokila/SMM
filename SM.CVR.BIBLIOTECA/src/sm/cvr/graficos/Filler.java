/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.cvr.graficos;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Clase Filler,
 * hace referencia a una propiedad de una figura que sea rellenable, con eso me 
 * refuiero a que esa figura pueda tener un color de relleno.
 * @author Cristian Vélez Ruiz
 */
public class Filler{
    
    //Atributos : Color y transparencia
    //Por defecto: Negro y sin transparencia
   
    /*
    * Color del relleno
    */
    private Color color;
    
    /**
     * Si el relleno esta activo
     */
    private boolean fill;

           
    /**
     * Constructor por defecto del relleno, nos crea un relleno de color negro y 
     * no esta activo
     */
    public Filler(){
        this.color = EnumColor.NEGRO.getColor();
        this.fill = false;
    }
    
    /**
    * Modificador del color de relleno del filler
    * @param color Color a asignar al relleno
    */
    public void setColorFill(EnumColor color){
        this.color = color.getColor();
    }
    
    /**
     * Modificador del estado del relleno, si es true se rellena, en caso contrario
     * no
     * @param fill Estado del relleno
     */
    public void setFill(boolean fill){
        this.fill=fill;
    }
    
    /**
     * Consultor del estado del relleno del filler
     * @return Estado del relleno
     */
    public boolean getFill(){
        return this.fill;
    }
    
    /**
     * Metodo importante para configurar las propiedades del filler en el
     * Graphics2D, sin este metodo las propiedades no se pueden visualizar.
     * @param g2d Graphics2D donde aplicar los atributos
     */
    public void applyFiller(Graphics2D g2d){
        //g2d.setComposite(transparency);
        g2d.setColor(color);
               
    }

}
