/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.cvr.imagen;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.WritableRaster;
import sm.image.BufferedImageOpAdapter;


/**
 * Clase SepiaOp,
 * utilizada para generar la imagen en sepia a partir de otra dada
 * @author Cristian Vélez Ruiz
 */
public class SepiaOp extends BufferedImageOpAdapter{

    /**
     * Filtro sepia de la imagen
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
        
        ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY),null);
        op.filter(src, src);
        
        
                        
        WritableRaster raster = dest.getRaster();
        double rAnt,bAnt,gAnt,rNuevo,gNuevo,bNuevo;
        
        for (int x = 0; x < src.getWidth(); x++) {
            for (int y = 0; y < src.getHeight(); y++) {

                rAnt = raster.getSample(x, y, 0);
                gAnt = raster.getSample(x, y, 1);
                bAnt = raster.getSample(x, y, 2);
                
                rNuevo = Math.min(255, 0.393*rAnt+0.769*gAnt+0.189*bAnt);
                gNuevo = Math.min(255, 0.349*rAnt+0.686*gAnt+0.168*bAnt);
                bNuevo = Math.min(255, 0.272*rAnt+0.534*bAnt+0.131*bAnt);
                
                
                raster.setSample(x, y, 0, rNuevo);
                raster.setSample(x, y, 1,gNuevo);
                raster.setSample(x, y, 2,bNuevo);


            }
            
            System.out.print("\n");
        }  
        
        return dest;

    }
    
}



