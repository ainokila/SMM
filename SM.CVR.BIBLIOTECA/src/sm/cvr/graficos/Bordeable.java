/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.cvr.graficos;

/**
 * Clase abstracta Bordeable,
 * para los objetos que puedan tener tener en sus propiedades un borde,
 * el cual incluye el tipo de borde, su grosor, el color del borde y el 
 * suavizado, esta clase tiene las propiedades y métodos de MyShape.
 * 
 * @author Cristian Vélez Ruiz
 */
public abstract class Bordeable extends MyShape {
    
    protected Border border;
    
    /**
     * Este metodo es usado por la clase para configurar el tipo de borde, su grosor y el color que tendrá.
     * @param type Tipo de borde a aplicar
     * @param grosor Grosor que tendra el borde
     * @param color Color que tendrá el borde
     */
    public void setBorder(int type,int grosor,EnumColor color){
        this.border.setStroke(type, grosor,color,false);
    }
    
    /**
     * Podemos obtener el Border del bordeable
     * @return Border - Objeto borde de la figura
     */
    public Border getBorder(){
        return this.border;
    }
    
    /**
     * Modificador del tipo de borde
     * @param type - Nuevo tipo de borde
     */
    public void setType(int type){
        this.border.setType(type);
    }
    
    /**
     * Activador/desactivador del suavizado de los bordes.
     * @param hints True si queremos suavizado, false si no queremos
     */
    public void setSmoothed(boolean hints){
        this.border.setSmoothed(hints);
    }
    
    /**
     * Modificador del color del borde
     * @param color - EnumColor con el color seleccionado
     */
    public void setColorBorder(EnumColor color){
        this.border.setColor(color);
    }
    
    /**
     * Modificador del grosor del borde
     * @param thic - Tamaño del grosor del borde 
     */
    public void setThickness(int thic){
        this.border.setThickness(thic);
    }
    
    /**
     * Consultor del tamaño actual del borde
     * @return Grosor del borde
     */
    public float getThickness(){
        return this.border.getThickness();
    }
    
}
