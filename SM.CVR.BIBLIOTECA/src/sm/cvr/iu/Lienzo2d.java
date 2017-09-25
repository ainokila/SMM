/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.cvr.iu;

import java.awt.BasicStroke;
import sm.cvr.graficos.EnumHerramienta;
import sm.cvr.graficos.EnumColor;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.LookupOp;
import java.awt.image.LookupTable;
import java.awt.image.RescaleOp;
import java.awt.image.WritableRaster;
import javax.swing.JInternalFrame;
import sm.cvr.graficos.Bordeable;
import sm.cvr.graficos.Border;
import sm.cvr.graficos.Fillerable;
import sm.cvr.graficos.MyCurveLine;
import sm.cvr.graficos.MyElipse;
import sm.cvr.graficos.MyLine;
import sm.cvr.graficos.MyPoint;
import sm.cvr.graficos.MyRectangle;
import sm.cvr.graficos.MyText;
import sm.cvr.graficos.MyShape;
import sm.cvr.imagen.NegativoOp;
import sm.cvr.imagen.RotaColoresOp;
import sm.cvr.imagen.SepiaOp;
import sm.cvr.imagen.UmbralizacionOp;
import sm.image.EqualizationOp;
import sm.image.KernelProducer;
import sm.image.LookupTableProducer;
import sm.image.TintOp;

/**
 * Clase Lienzo2d,
 * podemos añadir imagenes, editarlas, dibujar figuras, aplicar filtros y todo sobre Graphics2D
 * @author Cristian Velez Ruiz
 */
public class Lienzo2d extends javax.swing.JPanel {

    /**
     * Creates new form Lienzo2d
     * 
     * 
     */
    
    
    private Point2D p;
    //Estado de herramientas
    
    /**
     * Ultima forma que no se ha escrito aun en la imagen
     */
    private MyShape forma;
    
    /**
     * Estado de si estamos o no editando
     */
    private boolean editar;
    
    /**
     * Grosor del trazo
     */
    private int grosor;
    
    /**
     * Estado del suavizado
     */
    private boolean estadoAlisado;
    
    /**
     * Estado de si hay que rellenar las figuras o no
     */
    private boolean rellenar;
    
    /**
     * Patron de discontinuidad
     */
    private int discontinuidad;
    
    /**
     * Variable para detectar punto de control de la curva
     */
    private boolean puntoCurva;
    
    
    /**
     * Padre de lienzo2d
     */
    protected JInternalFrame padre;
    
    
    
    /**
     * Herramienta activa
     */
    private EnumHerramienta herramienta;
    
    /**
     * Ultima figura sin escribir en la imagen
     */
    private MyShape ultima;
    
    /**
     * Punto auxiliar para guardar el inicio
     */
    private Point inicio;
    
    
    
    
    //Atributos para estilo
    
    /**
     * Color activo en el lienzo
     */
    private EnumColor color;
    
    /**
     * Transparencias de 0 a 1
     */
    private float transp;
    
    //Practica final
    
    /**
     * Imagen de fondo donde guardar las formas y las imagenes
     */
    private BufferedImage img;
        
    

    /**
     * Constructor de la clase lienzo2d, recibe a su padre como parametro
     * @param padre Padre JInternalFrame para modificar su estado desde el hijo
     */
    public Lienzo2d(JInternalFrame padre) {
        
        this.padre = padre;
        
        initComponents();
        
        this.setHerramienta(EnumHerramienta.LAPIZ);
        
        this.setGrosor(1);
        
        this.setColor(EnumColor.NEGRO);
        this.setRellenar(false);
               
        this.setTransparencia(1.0f);
           
        this.setAlisado(false);
        this.setEditar(false);
        
        //setPreferredSize(new Dimension(400, 400));
        img = new BufferedImage(500,500,BufferedImage.TYPE_INT_RGB);
        setImage(img);
        
        Graphics2D g2 = img.createGraphics();
        g2.setColor(Color.WHITE);  
        
        float patronDiscontinuidad1[] = {5.0f ,5.0f};
        BasicStroke limite = new BasicStroke(2, BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER, 1.0f,patronDiscontinuidad1, 1.0f);
        g2.setStroke(limite);
        g2.fillRect(0, 0, img.getWidth(), img.getHeight());
        paint(g2);
        
        this.puntoCurva=false;
        

        //this.paint(img.getGraphics());
        //this.setImage(img);

    }
    
    /**
     * Configurador del lienzo para pasar el estado desde el padre
     * @param herr Heramienta activa
     * @param color Color activo
     * @param grosor Grosor 
     * @param relleno Estado relleno
     * @param alisar Estado alisar
     * @param trans Estado transparencia
     * @param editar Estado editar 
     * @param disc Tipo discontinuidad
     */
    public void setLienzo(EnumHerramienta herr,EnumColor color,int grosor,boolean relleno,boolean alisar,float trans,boolean editar,int disc){
        this.setHerramienta(herr);
        
        this.setGrosor(grosor);
        
        this.setColor(color);
        this.setRellenar(relleno);
               
        this.setTransparencia(trans);
           
        this.setAlisado(alisar);
        this.setEditar(editar);
        
        this.setDiscontinuidad(disc);
    }
    
    public Point2D getPos(){
        return p;
    }
    
    //*******************************Discontinuidad****************************//
    /**
     * Modifica el tipo de discontinuidad activa en el lienzo
     * @param dis Tipo de discontinuidad
     */
    public void setDiscontinuidad(int dis){
        this.discontinuidad=dis;
        
        if(editar){
            if(this.forma instanceof MyLine || this.forma instanceof MyPoint ||
                    this.forma instanceof MyCurveLine || this.forma instanceof MyRectangle ||
                    this.forma instanceof MyElipse){
                ((Bordeable)this.forma).setBorder(dis, grosor, color);
            }
        }
        
        this.repaint();
 
    }
    /**
     * Consulta la discontinuidad
     * @return  Tipo de discontinuidad activa en el lienzo
     */
    public int getDiscontinuidad(){
        return this.discontinuidad;
    }
    
    //******************************EDITAR*************************************//
    /**
     * Activa/desactiva el editar en el lienzo
     * @param edicion Estado editar
     */
    public void setEditar(boolean edicion){
        this.editar=edicion;
    }
    
     /**
     * Consulta el estado de editar en el lienzo
     * @return Estado de editar
     */
    public boolean getEditar(){
        return this.editar;
    }
    
    //*************************PRUEBA IMG*************************************//
    
    /**
     * Configura una imagen en el bufferedImage del lienzo2d
     * @param b Imagen a configurar en el lienzo
     */
    public void setImage(BufferedImage b){
            this.img=b;
            if(img!=null) {
                this.padre.setPreferredSize(new Dimension(img.getRaster().getWidth(),img.getRaster().getHeight()));

                setPreferredSize(new Dimension(img.getRaster().getWidth(),img.getRaster().getHeight()));
                this.padre.setSize(new Dimension(img.getRaster().getWidth(),img.getRaster().getHeight()));
            }
    }
    
    /**
     * Devuelve la imagen del lienzo2d, si se le pasa true, pinta tambien las 
     * figuras si no solo devuelve la imagen
     * @param drawVector Si es true, pinta tambien las figuras si no solo
     * devuelve la imagen
     * @return Imagen del lienzo
     */
    public BufferedImage getImage(boolean drawVector){
        BufferedImage m = null;
        if (drawVector) {
            m = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
            paint(m.createGraphics());
        }
        else{
            m = getImage();
        }
        return m;
    }

    /**
     * Sobreescribimos el paintcomponent para poder pintar las figuras
     * @param g Graficos
     */
    @Override
    public void paintComponent(Graphics g){
        
        super.paintComponent(g);
        if(img!=null)
            g.drawImage(img,0,0,this);
        
        
    }
    
    /**
     * Devuelve la imagen del lienzo actual sin la ultima figura
     * @return Imagen lienzo
     */
    public BufferedImage getImage(){
        
        //BufferedImage m;
        //m = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        //paint(m.createGraphics());
        return img;
    }
    
    //*******************************HERRAMIENTAS*********************************//
    
    
    /**
     * Devuelve la herrmienta activa en el lienzo
     * @return Herramienta activa
     */
    public EnumHerramienta getHerramienta(){
        return this.herramienta;
    }
    
    /**
     * Modifica la herramienta activa en el lienzo
     * @param herramienta Herramienta nueva cambiar
     */
    public void setHerramienta(EnumHerramienta herramienta){
        this.herramienta = herramienta;
    }
   
    //****************************GROSOR********************************************//
    
    /**
     * Modifica el grosor de las figuras
     * @param grosor Grosor de la figura a pintar o editar
     */
    public void setGrosor(int grosor){
        this.grosor = grosor;
        
        if(editar){
            if(this.forma instanceof MyLine || this.forma instanceof MyPoint ||
                    this.forma instanceof MyCurveLine || this.forma instanceof MyRectangle ||
                    this.forma instanceof MyElipse){
                ((Bordeable)this.forma).setBorder(discontinuidad, grosor, color);
                this.repaint();
            }
        }
    }
    
    /**
     * Devuelve el grosor activo
     * @return Grosor activo en lienzo
     */
    public int getGrosor(){
        return this.grosor;
    }
    
    //*******************************COLOR Y RELLENO********************************//
    
    /**
     * Consulta el estado de rellenar de lienzo
     * @return Estado de rellenar
     */
    public boolean getRellenar(){
        return this.rellenar;
    }
    
    /**
     * Modifica el estado de rellenar del lienzo
     * @param estado True si rellena, false si no
     */
    public void setRellenar(boolean  estado){
        this.rellenar = estado;
        if(editar){
            if(forma instanceof MyRectangle || forma instanceof MyElipse){
                ((Fillerable)forma).setFiller(color, estado);
            }
        }
        this.repaint();
    }
    /**
     * Modifica el color activo del lienzo
     * @param color Color activo
     */
    public void setColor(EnumColor color){
        this.color = color;
        
        if(editar){
            if(this.forma instanceof MyLine || this.forma instanceof MyPoint ||
                    this.forma instanceof MyCurveLine || this.forma instanceof MyRectangle ||
                    this.forma instanceof MyElipse){
                ((Bordeable)this.forma).setBorder(discontinuidad, grosor, color);
                this.repaint();
            }
        }
    }
    
    /**
     * Consulta el color activo del lienzo
     * @return Color del lienzo
     */
    public EnumColor getColor(){
        return this.color;
    }
    
    //***********************************TRANSPARENCIA**************************************//
    
    /**
     * Configura la transparencia en el lienzo
     * @param value Valor de la transparencia
     */
    public void setTransparencia(float value){
     
        this.transp = value;
        if(editar){
            this.forma.setTransparency(value);
        }

    }
    
    /**
     * Consulta el estado actual de la transparencia del lienzo
     * @return Valor de la transparencia
     */
    public float getTransparencia(){
        return this.transp;
    }
    
    //***********************************ALISADO***************************************//
    
    /**
     * Modifica si se aplica el suavizado en las figuras
     * @param estate Estado del suavizado
     */
    public void setAlisado(boolean estate){
        this.estadoAlisado = estate;
        
        if(editar){
            if(this.forma instanceof MyLine || this.forma instanceof MyPoint ||
                    this.forma instanceof MyCurveLine || this.forma instanceof MyRectangle ||
                    this.forma instanceof MyElipse){
                ((Bordeable)this.forma).getBorder().setSmoothed(estate);
                this.repaint();
            }
        }

        
    }
    
    /**
     * Consulta el estado del suavizado del lienzo
     * @return Estado del suavizado de las figuras
     */
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
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
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

    /**
     * Procesador del evento pressed
     * @param evt Evento pressed
     */
    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        if(this.editar){
            //ultima = getSelectedShape(evt.getPoint());
        }else if(puntoCurva){
            
            MyCurveLine c = (MyCurveLine)(forma);
            c.setControl(evt.getPoint());
            puntoCurva=false;
            System.out.println("Poniendo punto control");
            
        }else{
            this.inicio = evt.getPoint();
            
            //Si la forma es distinta de null,ahi que guardarla en la imagen 

            
            if(forma!=null){
                this.shapeToImg(img);
            }
            
            switch(this.herramienta){
                case LAPIZ:

                    inicio = evt.getPoint();
                    
                    MyPoint p = new MyPoint(inicio);
                    
                    p.setThickness(grosor);
                    //p.setColor(color);
                    p.setBorder(Border.CONTINUOUS, grosor,color);

                    p.setTransparency(transp);
                    
                    forma = p;
                    
                    /*
                    forma = new MyPoint(inicio);
                    
                    if(forma instanceof MyPoint){
                        ((MyPoint)forma).setThickness(grosor);
                        forma.setColor(color);
                    }*/
                    
                    //figuras.add(ultima);
                    break;
                case LINEA:
                    
                    inicio = evt.getPoint();
                    
                    MyLine l = new MyLine(inicio,(Point2D)inicio.clone());
                    l.setBorder(discontinuidad, grosor,color);
                    //l.setColor(color);
                    l.setSmoothed(estadoAlisado);
                    l.setTransparency(transp);
                    
                    forma = l;
                    
                    
                    /*
                    forma = new MyLine(inicio,inicio);
                    if(forma instanceof MyLine){
                        ((MyLine)forma).setBorder(discontinuidad, grosor);
                        System.out.println("Discontinuidad tipo " + discontinuidad);
                        forma.setColor(color);
                    }*/
                    //ultima = new Line2D.Double(inicio, inicio);
                    //ultima = new MiLinea(inicio, inicio);
                    //figuras.add(ultima);
                    break;
                case RECTANGULO:
                    inicio = evt.getPoint();
                    //ultima = new Rectangle(inicio);
                    //figuras.add(ultima);
                    
                    MyRectangle r = new MyRectangle(inicio,(Point2D)inicio.clone());
                    r.setBorder(discontinuidad, grosor,color);
                    r.setTransparency(transp);
                    r.setFiller(color, this.rellenar);
                    r.setSmoothed(estadoAlisado);
                    
                    forma = r;

                    break;
                case CIRCULO:
                    inicio = evt.getPoint();

                    MyElipse e = new MyElipse(inicio,(Point2D)inicio.clone());
                    e.setBorder(discontinuidad, grosor,color);
                    e.setTransparency(transp);
                    e.setFiller(color, this.rellenar);
                    e.setSmoothed(estadoAlisado);
                    
                    forma = e;
                    break;   
                    
                case CURVA:
                    
                        MyCurveLine c = new MyCurveLine(inicio,(Point2D)inicio.clone());
                        c.setBorder(discontinuidad, grosor,color);
                        c.setSmoothed(estadoAlisado);
                        c.setTransparency(transp);
                    
                        forma = c;
                        
                    break;
                    
                case TEXTO:
                        AskText preg = new AskText(new Frame(), true);
                        preg.setVisible(true);
                        String texto = preg.getText();
                        String fuente = preg.getFuente();
                        int tam = preg.getTamanio();

                        MyText t = new MyText(texto,fuente,tam, inicio);
                        t.setColor(color);
                        t.setTransparency(transp);
                        
                        forma = t;
                        
                    break;
                    
                case NINGUNO:
                    break;
            }
        }
        
        
        this.repaint();
            }//GEN-LAST:event_formMousePressed

     /**
     * Procesador del evento dragged
     * @param evt Evento dragged
     */
    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        
        if(!editar){
            switch(this.herramienta){
                case LAPIZ:
                    
                    break;
                case LINEA:
                    //((MiLinea)(this.ultima)).setLine(inicio, evt.getPoint());
                    MyLine l = (MyLine)(forma);
                    l.setLine(inicio, evt.getPoint());
                    break;
                    
                case RECTANGULO:
                    MyRectangle r = (MyRectangle)(forma);
                    r.setDiagonal(inicio, evt.getPoint());
                    break;
                    
                case CIRCULO:
                    MyElipse e = (MyElipse)(forma);
                    e.setDiagonal(inicio, evt.getPoint());
                    break;
                    
                case CURVA:
                    //((MiLinea)(this.ultima)).setLine(inicio, evt.getPoint());
                    
                    MyCurveLine c = (MyCurveLine)(forma);
                    c.setLine(inicio, evt.getPoint());
                    puntoCurva=true;
                   
                    break;
            }
            
        }else{
          
            if(forma!=null){
                
                //Manera mas rapida de cambiar localizacion
                forma.setLocation(evt.getPoint());
                
            }
        
        }
        this.repaint();
    }//GEN-LAST:event_formMouseDragged

    /**
     * Procesador del evento released
     * @param evt Evento released
     */
    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        //this.formMouseDragged(evt);
        this.repaint();
        
    }//GEN-LAST:event_formMouseReleased

    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        p = evt.getPoint();
    }//GEN-LAST:event_formMouseMoved


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

     /**
     * Metodo paint para pintar el lienzo 
     * @param g Graphics donde pintar el lienzo
     */
    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        g.drawImage(img,0,0,this);
        //img = getImage();
        if(forma!=null)
            forma.draw(g2d);
        
    }


    /**
     * Guarda la ultima figura en la imagen
     */
    public void saveAll(){

        
        if(forma!=null){
            this.shapeToImg(img);
            inicio = new Point(0,0);
            forma = new MyLine(inicio, inicio);
            forma.setTransparency(0.0f);
        }
    }
    
    /**
     * Se encarga de pasar el shape al bufferedImage que se le pase como 
     * parametro
     * @param b Imagen donde guardar la última figura
     */
    private void shapeToImg(BufferedImage b){
        
        BufferedImage m = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        paint(m.createGraphics());
        
        this.img=m;
        
        //forma=null;
    }
    
    
    
    /********************************** IMAGENES******************************/
    
    /**
     * Funcion para aplicarle un determinado tipo de filtro a la imagen
     * @param filtro Filtro seleccionado
     */
    public void aplicaFiltro(String filtro){
        saveAll();
        Kernel k ;
        
        switch(filtro){
            case "Media":
                k = KernelProducer.createKernel(KernelProducer.TYPE_MEDIA_3x3);
                break;
            case "Enfoque":
                k = KernelProducer.createKernel(KernelProducer.TYPE_ENFOQUE_3x3);
                break;
            case "Binomial":
                k = KernelProducer.createKernel(KernelProducer.TYPE_BINOMIAL_3x3);
                break;
            case "Relieve":
                k = KernelProducer.createKernel(KernelProducer.TYPE_RELIEVE_3x3);
                break; 
            case "Laplaciano":
                k = KernelProducer.createKernel(KernelProducer.TYPE_LAPLACIANA_3x3);
                break;
            default:
                k = KernelProducer.createKernel(KernelProducer.TYPE_MEDIA_3x3);
                break;
        }
        
        try{
              
            ConvolveOp cop = new ConvolveOp( k );
            BufferedImage imgOut = cop.filter(getImage(),null);
            setImage(imgOut);
            repaint();
           
        }catch(Exception e){
            System.err.println(e.toString());
        }
    }
    
    /**
     * Función para hacer sepia una imagen
     */
    public void sepiaImagen(){
        saveAll();

        try{
            
            SepiaOp sepia = new SepiaOp();
            sepia.filter(img, img);
            repaint();
            
        }catch(Exception e){
            System.err.println("Error "+e.toString());
        }  
    }
    
    /**
     * Hace en negativo la imagen
     */
    public void negativoImagen(){
        saveAll();

        try{
            
            NegativoOp negativo = new NegativoOp();
            negativo.filter(img, img);
            repaint();
            
        }catch(Exception e){
            System.err.println("Error "+e.toString());
        }  
    }
    
    
    /**
     * Rota los colores de la imagen en el sentido R-G-B
     */
    public void rotaColores(){
        saveAll();

        try{
            
            RotaColoresOp rota = new RotaColoresOp();
            rota.filter(img, img);
            repaint();
            
        }catch(Exception e){
            System.err.println("Error "+e.toString());
        }  
    }
    
    /**
     * Hace el tintado de la imagen con el color activo
     */
    public void tintaImagen(){
        saveAll();

        try{
            
            TintOp tintado = new TintOp(getColor().getColor(),0.5f);
            tintado.filter(img, img);
            repaint();
        }catch(Exception e){
            System.err.println("Error "+e.toString());
        }  
    }
    
    /**
     * Ecualiza la imagen
     */
    public void ecualizaImagen(){
        saveAll();

        try{
            EqualizationOp ecualizacion = new EqualizationOp();
            ecualizacion.filter(img,img);
            repaint();
        }catch(Exception e){
            System.err.println("Error "+e.getMessage());
        }
    }
    
    /**
     * Escala la imagen en x e y
     * @param x Escala en x
     * @param y Escala en y
     */
    public void escala(float x,float y){
        saveAll();

        AffineTransform at = AffineTransform.getScaleInstance(x, y);
        try{
            AffineTransformOp atop = new AffineTransformOp(at,
            AffineTransformOp.TYPE_BILINEAR);
            BufferedImage imgdest = atop.filter( getImage(), null);
            this.img=imgdest;
            if(img!=null) {
                setPreferredSize(new Dimension(img.getWidth(),img.getHeight()));
                //this.padre.setSize(img.getWidth(),img.getHeight());
                repaint();
            }
        }catch(Exception e){
            System.err.println("Error");
        }
    }
    
    /**
     * Aplica un valor de brillo a la imagen que se le pase como parametro
     * @param value Valor del brillo
     * @param img Imagen a la que aplicar el brillo
     */
    public void brillo(int value,BufferedImage img){
        //repaint();
        saveAll();
        //repaint();
        try{
            RescaleOp rop = new RescaleOp(1.0F, value, null);
            rop.filter(img,this.img);
            repaint();
        } catch(IllegalArgumentException e){
            System.err.println(e.getLocalizedMessage());
        }
    }
    
    
    /**
     * Rota la imagen de parametro el numero de grados
     * @param grados Grados a rotar
     * @param img Imagen origen
     */
    public void rota(int grados,BufferedImage img){
        saveAll();

        double r = Math.toRadians(grados);
        Point p = new Point(img.getWidth()/2, img.getHeight()/2);
        AffineTransform at = AffineTransform.getRotateInstance(r,p.x,p.y);
        
        try{
            AffineTransformOp atop = new AffineTransformOp(at,AffineTransformOp.TYPE_BILINEAR);
            BufferedImage imgdest = atop.filter( img, null);
            
            setImage(imgdest);
            repaint();
            

        }catch(Exception e){
            System.err.println("Error "+e.getMessage());
        }        
    }
     
    /**
     * Rotal la imagen src el numero de grados
     * @param grados Grados a rotar la imagen
     */
     public void rota(int grados){
        saveAll();

        double r = Math.toRadians(grados);
        Point p = new Point(getImage().getWidth()/2, getImage().getHeight()/2);
        AffineTransform at = AffineTransform.getRotateInstance(r,p.x,p.y);
        
        try{
            AffineTransformOp atop = new AffineTransformOp(at,AffineTransformOp.TYPE_BILINEAR);
            BufferedImage imgdest = atop.filter( getImage(), null);
            setImage(imgdest);
        }catch(Exception e){
            System.err.println("Error "+e.getMessage());
        }        
    }
     
    /**
     * Aplica contraste a la imagen src
     */
    public void contraste(){
        saveAll();

        try{
            int type = LookupTableProducer.TYPE_SFUNCION;
            LookupTable lt = LookupTableProducer.createLookupTable(type);
            LookupOp lop = new LookupOp(lt, null);
            //Imagen origen y destino iguales
            lop.filter( getImage() , getImage());
            
           //Bug
           //BufferedImage imgdest = lop.filter( getImage() ,null); 
           //this.setImage(imgdest);
            repaint();
        }catch(Exception e){
            System.err.println("Error al aplicar el contraste");
        }
        

    }
    
    /**
     * Ilumina la imagen
     */
    public void iluminar(){
            saveAll();

        try{
            int type = LookupTableProducer.TYPE_LOGARITHM;
            LookupTable lt = LookupTableProducer.createLookupTable(type);
            LookupOp lop = new LookupOp(lt, null);
            //Imagen origen y destino iguales
            lop.filter( getImage() , getImage());
            repaint();
            
        }catch(Exception e){
            System.err.println("Error");
        }
    }
    
    /**
     * Oscurece la imagen
     */
    public void oscurecer(){
        saveAll();

        try{
            int type = LookupTableProducer.TYPE_POWER;
            LookupTable lt = LookupTableProducer.createLookupTable(type);
            LookupOp lop = new LookupOp(lt, null);
            //Imagen origen y destino iguales
            lop.filter( getImage() , getImage());
            repaint();
        }catch(Exception e){
            System.err.println("Error");
        }
    }
    
    
    /**
     * Realiza un clonado profundo de la imagen
     * @return Imagen Clonada
     */
    public BufferedImage cloneImage(){
        saveAll();

        BufferedImage sol;
        ColorModel cm = getImage().getColorModel();
        WritableRaster raster = getImage().copyData(null);
        boolean alfaPre = getImage().isAlphaPremultiplied();
        sol = new BufferedImage(cm,raster,alfaPre,null);
         
         return sol;
    }
    
    /**
     * Umbraliza la imagen de parametro el umbral deseado
     * @param umbral Umbral de umbralizacion
     * @param original Imagen Original
     */
    
    
    public void umbraliza(int umbral,BufferedImage original){
        saveAll();

        try{
            UmbralizacionOp umbra = new UmbralizacionOp(umbral);
            BufferedImage imgdest = umbra.filter(original, null);
            setImage(imgdest);
            repaint();
            
            
        }catch(Exception e){
            System.err.println("Error "+e.getMessage());
        }
    }

}
