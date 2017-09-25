/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica4;

/**
 *
 * @author Ainokila
 */
public enum Herramienta {
     LAPIZ,LINEA,RECTANGULO,CIRCULO,NINGUNO;
    
    @Override
    public String toString(){
        switch(this) {
            case LAPIZ: return "Lapiz";
            case LINEA: return "Linea";
            case RECTANGULO: return "Rectangulo";
            case CIRCULO: return "Circulo";
            case NINGUNO: return "No seleccionado";

            default: throw new IllegalArgumentException();
        }
    }
}
