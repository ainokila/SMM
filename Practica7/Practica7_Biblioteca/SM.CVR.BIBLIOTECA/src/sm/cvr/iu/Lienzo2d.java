/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.cvr.iu;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JInternalFrame;

/**
 *
 * @author Ainokila
 */
public class Lienzo2d extends javax.swing.JPanel {

    /**
     * Creates new form Lienzo2d
     * 
     * 
     */
    
    private JInternalFrame padre;
    
    //Vector de figuras para pintar
    private List<Shape> figuras = new ArrayList();
    private EnumHerramienta herramienta;
    
    private Shape ultima;
    private Point inicio;
    
    private boolean editar;
    
    
    //Atributos para estilo
    private Stroke trazo;
    private int grosor;
    
    private Paint relleno;
    private EnumColor color;
    private boolean rellenar;
    
    private Composite composite;
    private boolean transp;
    
    private RenderingHints alisado;
    private boolean estadoAlisado;
    

    public Lienzo2d(JInternalFrame padre) {
        
        this.padre = padre;
        
        initComponents();
        
        this.setHerramienta(EnumHerramienta.LAPIZ);
        
        this.setGrosor(1);
        
        this.setColor(EnumColor.NEGRO);
        this.setRellenar(false);
               
        this.setTransparencia(false);
           
        this.setAlisado(false);
        this.setEditar(false);

    }
    
    public void setLienzo(EnumHerramienta herr,EnumColor color,int grosor,boolean relleno,boolean alisar,boolean trans,boolean editar){
        this.setHerramienta(herr);
        
        this.setGrosor(grosor);
        
        this.setColor(color);
        this.setRellenar(relleno);
               
        this.setTransparencia(trans);
           
        this.setAlisado(alisar);
        this.setEditar(editar);
    }
    
    //******************************EDITAR*************************************//
    
    public void setEditar(boolean edicion){
        this.editar=edicion;
    }
    
    public boolean getEditar(){
        return this.editar;
    }
    
    //*******************************HERRAMIENTAS*********************************//
    public EnumHerramienta getHerramienta(){
        return this.herramienta;
    }
    
    public void setHerramienta(EnumHerramienta herramienta){
        this.herramienta = herramienta;
    }
   
    //****************************GROSOR********************************************//
    public void setGrosor(int grosor){
        this.grosor = grosor;
        this.trazo =  new BasicStroke(this.grosor);
        this.repaint();
    }
    
    public int getGrosor(){
        return this.grosor;
    }
    
    //*******************************COLOR Y RELLENO********************************//
    public boolean getRellenar(){
        return this.rellenar;
    }
    
    public void setRellenar(boolean  estado){
        this.rellenar = estado;
        this.repaint();
    }
    
    public void setColor(EnumColor color){
      
        this.color = color;
        
        switch(color){
            case NEGRO:
                this.relleno = new Color(0,0,0);                        
                break;
                
            case ROJO: 
                this.relleno = new Color(255, 60, 30);
                break;
                
            case AZUL:
                this.relleno = new Color(33, 103, 242);
                break;
                
            case BLANCO:
                this.relleno = new Color(255, 255, 255);
                break;
                
            case AMARILLO:
                this.relleno = new Color(255, 242, 61);
                break;
            case VERDE:
                this.relleno = new Color(99, 249, 114);
                break;
            case NINGUNO:
                this.relleno = new Color(255, 255, 255);
                break;
        }
        
        this.repaint();
                
    }
    
    public EnumColor getColor(){
        return this.color;
    }
    
    //***********************************TRANSPARENCIA**************************************//
    
    public void setTransparencia(boolean state){
        float result;
        if(state){
            result = 0.5f;
        }else{
            result = 1.0f;
        }
        this.composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, result);
        this.transp = state;
        
        this.repaint();
    }
    public boolean getTransparencia(){
        return this.transp;
    }
    
    //***********************************ALISADO***************************************//
    
    public void setAlisado(boolean estate){
        Object result;
        if(estate){
            result = RenderingHints.VALUE_ANTIALIAS_ON;
        }else{
            result = RenderingHints.VALUE_ANTIALIAS_OFF;
        }
        this.alisado = new RenderingHints(RenderingHints.KEY_ANTIALIASING,result);
        this.estadoAlisado = estate;
        this.repaint();
    }
    
    public boolean getAlisado(){
        return estadoAlisado;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        if(this.editar){
            ultima = getSelectedShape(evt.getPoint());
        }else{
            this.inicio = evt.getPoint();
            switch(this.herramienta){
                case LAPIZ:
                    inicio = evt.getPoint();
                    //ultima = new Line2D.Double(inicio, inicio);
                    ultima = new MiLinea(inicio, inicio);
                    figuras.add(ultima);
                    break;
                case LINEA:
                    inicio = evt.getPoint();
                    //ultima = new Line2D.Double(inicio, inicio);
                    ultima = new MiLinea(inicio, inicio);
                    figuras.add(ultima);
                    break;
                case RECTANGULO:
                    inicio = evt.getPoint();
                    ultima = new Rectangle(inicio);
                    figuras.add(ultima);
                    break;
                case CIRCULO:
                    inicio = evt.getPoint();
                    ultima = new Ellipse2D.Double(inicio.x,inicio.y,inicio.x,inicio.y);
                    figuras.add(ultima);
                    break;   
                case NINGUNO:
                    break;
            }
        }
        
        
        this.repaint();
            }//GEN-LAST:event_formMousePressed

    private Shape getSelectedShape(Point2D p){
        for(Shape s:figuras)
           
            if(s.contains(p)) return s;
                
        return null;
    }
        
    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        if(!editar){
            switch(this.herramienta){
                case LAPIZ:
                    
                    break;
                case LINEA:
                    ((MiLinea)(this.ultima)).setLine(inicio, evt.getPoint());
                    break;
                case RECTANGULO:
                    ((Rectangle)(this.ultima)).setFrameFromDiagonal(inicio, evt.getPoint());
                    break;
                case CIRCULO:
                    ((Ellipse2D.Double)(this.ultima)).setFrameFromDiagonal(inicio, evt.getPoint());
                    break;   
                case NINGUNO:
                    break;
            }
        }else{
          
            if(ultima!=null){
                if(ultima instanceof MiLinea ){
                    ((MiLinea)(this.ultima)).setLocation(evt.getPoint());                    
                }else if( ultima instanceof Rectangle){
                    ((Rectangle)(this.ultima)).setLocation(evt.getPoint());
                }else if(ultima instanceof Ellipse2D){
                    double anchura = ((Ellipse2D.Double)(this.ultima)).height;
                    double altura = ((Ellipse2D.Double)(this.ultima)).width;
                    
                    Point aux = new Point();
                    aux.x = evt.getPoint().x +(int) altura;
                    aux.y = evt.getPoint().y +(int) anchura;
                    
                    
                    ((Ellipse2D.Double)(this.ultima)).setFrameFromDiagonal(evt.getPoint(), aux);
                }
                
            }
        
        }
        this.repaint();
    }//GEN-LAST:event_formMouseDragged

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        this.formMouseDragged(evt);
        this.repaint();
        
    }//GEN-LAST:event_formMouseReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    @Override
    public void paint(Graphics g){
       super.paint(g);
       Graphics2D g2d = (Graphics2D)g;
       actualizaPintar(g2d);
       for(Shape s:figuras){
           if(rellenar) g2d.fill(s);
           g2d.draw(s);
       }
       
       //this.padre.repaint();
    }
    
    private void actualizaPintar(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor((Color)relleno);
        g2d.setStroke(trazo);
        g2d.setComposite(composite);
        g2d.setRenderingHints(alisado);
    }

}
