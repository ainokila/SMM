package sm.cvr.graficos;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * Clase Border,
 * esta clase hace referencia a la propiedad Borde de una figura, la cual en sus propiedades,
 * puede contar con el tipo de borde que tiene, su grosor, el color del borde del objeto y si
 * en este caso tiene activado que tenga un suavizado.
 * 
 * @author Cristian Vélez Ruiz
 */
public class Border {
    //Atributos : Alisado borde, Color, Tipo trazo.
    //Por defecto sin alisado, negro, continuo.
    
    /*
    * Pone el borde de manera continua
    */
    public static final int CONTINUOUS = 0;
    
    /*
    * Borde discontinuo 50% linea 50% espacio
    */
    public static final int DISCONTINUOUS_1_1 = 1;
    
    /*
    * Borde discontinuo 66% linea , 33% espacio
    */
    public static final int DISCONTINUOUS_2_1 = 2;
    
    /*
    * Borde discontinuo 75% linea y 25% espacio
    */
    public static final int DISCONTINUOUS_3_1 = 3;
    
    /*
    * Grosor del borde
    */
    private float grosor;
    
    /*
    * Tipo de basic stroke a aplicar al borde
    */
    private BasicStroke stroke;
    
    /*
    * Color del borde
    */
    private Color color;
    
    /*
    * Suavizado del borde
    */
    private RenderingHints renderHint;
    
    /**
     * Constructor por defecto del Borde, nos pone una linea continua de 1 de grosor negra y sin suavizado
     */
    public Border(){
        this.stroke = new BasicStroke(1);
        this.color = EnumColor.NEGRO.getColor();
        this.setSmoothed(false);
    }
    
    /**
     * Modificador del tipo de borde, 
     * CONTINUOUS para linea continua
     * DISCONTINUOUS_1_1 para linea discontinua tipo - - - 
     * DISCONTINUOUS_2_1 para linea discontinua tipo con mas linea y menos espacio
     * DISCONTINUOUS_3_1 para linea discontinua tipo con mas linea y menos espacio que 2_1
     * @param type Tipo de linea a usar
     */
    public void setType(int type){
        switch(type){
            case 0:
                stroke = new BasicStroke(grosor);
                break;

            case 1:
                float patronDiscontinuidad1[] = {15.0f , 15.0f};
                stroke = new BasicStroke(grosor, BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER, 1.0f,patronDiscontinuidad1, 0.0f);
                break;

            case 2:
                float patronDiscontinuidad2[] = {30.0f , 15.0f};
                stroke = new BasicStroke(grosor, BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER, 1.0f,patronDiscontinuidad2, 0.0f);
                break;

            case 3:
                float patronDiscontinuidad3[] = {45.0f , 15.0f};
                stroke = new BasicStroke(grosor, BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER, 1.0f,patronDiscontinuidad3, 0.0f);
                break;

            default:
                stroke = new BasicStroke(grosor);
                break;
        }
    }
    
    /**
     * Modificador del suavizado, si es true se activa y si es false esta desactivado
     * @param hints Estado del suavizado
     */
    public void setSmoothed(boolean hints){
        Object result;
        if(hints){
            result = RenderingHints.VALUE_ANTIALIAS_ON;
        }else{
            result = RenderingHints.VALUE_ANTIALIAS_OFF;
        }
        this.renderHint = new RenderingHints(RenderingHints.KEY_ANTIALIASING,result);
    }
    
    /**
     * Modificador del tipo de borde a aplicar
     * @param type Tipo del grosor a aplicar
     * @param grosor Tamañao del grosor
     * @param color Tipo de color 
     * @param hints Estado del suavizado
     */
    public void setStroke(int type,int grosor,EnumColor color,boolean hints){
        
        this.grosor = grosor;
        this.color = color.getColor();
        
        this.setSmoothed(hints);
        
        this.setType(type);
        
    }

    /**
     * Modificador del grosor de manera ancestral, acepta un basicStroke
     * @param stroke Tipo de grosor a configurar
     */
    public void setStroke(BasicStroke stroke) {
        this.stroke = stroke;
    }
    
    /**
     * Modificador del color del borde
     * @param color Color a modificar en el borde
     */
    public void setColor(EnumColor color){
        this.color= color.getColor();
    }
    
    /**
     * Modificador del grosor del borde
     * @param thic Grosor del borde
     */
    public void setThickness(int thic){
        this.grosor=thic;
    }
    
    /**
     * Consultor del valor actual del grosor del borde
     * @return Grosor del borde
     */
    public float getThickness(){
        return grosor;
    }
    
    /**
     * Metodo que nos hara aplicar las propiedades de borde en el Graphics2D,
     * sin este metodo no se podrían visualizar estas propiedades
     * @param g2d Graphics2D donde configurar el borde 
     */
    public void applyBorder(Graphics2D g2d){
        g2d.setRenderingHints(renderHint);
        g2d.setColor(color);
        g2d.setStroke(stroke);
        
    }

}
