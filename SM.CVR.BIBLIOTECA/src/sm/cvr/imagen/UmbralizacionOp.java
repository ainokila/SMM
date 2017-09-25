/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.cvr.imagen;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import sm.image.BufferedImageOpAdapter;

/**
 * Clase UmbralizacionOp,
 * utilizada para generar la imagen umbralizada a partir de otra dada
 * @author Cristian Vélez Ruiz
 */
public class UmbralizacionOp extends BufferedImageOpAdapter{
    private int umbral;
    
    public UmbralizacionOp (int umbral) {
        this.umbral = umbral;
               
    }
    
     /**
     * Umbralización de la imagen
     * @param src Imagen origen
     * @param dest Imagen destino
     * @return Imagen destino
     */
    @Override
    public BufferedImage filter(BufferedImage src, BufferedImage dest) {
        if (src == null) {
                    throw new NullPointerException("src image is null");
        }
        if (dest == null) {
            dest = createCompatibleDestImage(src, null);
        }
        
                        
        WritableRaster raster = dest.getRaster();
        double rAnt,bAnt,gAnt,rNuevo,gNuevo,bNuevo;
        
        for (int x = 0; x < src.getWidth(); x++) {
            for (int y = 0; y < src.getHeight(); y++) {
                

                Color color = new Color(src.getRGB(x, y));
                
                int rojo = color.getRed();
                int verde = color.getBlue();
                int azul = color.getBlue();
                
                int intensidadMedia = (rojo+verde+azul)/3;
                
                Color c;
                
                if(intensidadMedia<umbral){
                    c = new Color(0,0,0);
                }else{
                    c = new Color(255,255,255);
                }
                
                //System.out.println(rNuevo + " "+ gNuevo + " "+ bNuevo);

//                raster.setSample(x, y, 0, rNuevo);
//                raster.setSample(x, y, 1,gNuevo);
//                raster.setSample(x, y, 2,bNuevo);
                
                dest.setRGB(x, y, c.getRGB());


            }
        }  
        
        return dest;

    }
}
