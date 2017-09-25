/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.cvr.graficos;

/**
 * Clase abstracta Fillerable,
 * para las figuras que puedan tener en su propiedad relleno, esta clase tiene
 * tambien las propiedades y atributos de la clase bordeable, ya que una figura
 * que tiene relleno siempre tendra borde.
 * 
 * @author Cristian Vélez Ruiz
 */
public abstract class Fillerable extends Bordeable {
    
    /**
     * Propiedad del relleno que tiene Fillerable, donde se aplicarán las 
     * operaciones
     */
    protected Filler filler;
    
    
    /**
     * Modificador del tipo de relleno, acepta un color y el estado del relleno
     * @param color Color con el que rellenar la figura
     * @param fill Estado del relleno de la figura
     */
    public void setFiller(EnumColor color,boolean fill){
        //this.border.setStroke(type, grosor);
        this.filler.setColorFill(color);
        this.filler.setFill(fill);
    }
    
    
    /**
     * Consultor del filler, nos devuelve la propiedade Filler
     * @return Filler- Donde se encuentra la configuración del relleno
     */
    public Filler getFiller(){
        return this.filler;
    }
}
