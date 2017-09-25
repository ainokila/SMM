/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.cvr.graficos;

/**
 * Herramientas disponibles para poder elegir, y asi facilitar la escalabilidad de la aplicación.
 * @author Cristian Vélez Ruiz
 */
public enum EnumHerramienta {
     LAPIZ,LINEA,RECTANGULO,CIRCULO,CURVA,TEXTO,NINGUNO;
    
     
     /**
     * Metodo para obtener el valor en string del enum
     * @return String - Herramienta del enum seleccionada
     */
    @Override
    public String toString(){
        switch(this) {
            case LAPIZ: return "Lapiz";
            case LINEA: return "Linea";
            case RECTANGULO: return "Rectangulo";
            case CIRCULO: return "Circulo";
            case CURVA: return "Curva";
            case TEXTO: return "Texto";
            case NINGUNO: return "No seleccionado";

            default: throw new IllegalArgumentException();
        }
    }
}
