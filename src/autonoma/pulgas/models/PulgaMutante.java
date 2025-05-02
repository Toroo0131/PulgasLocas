/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.pulgas.models;
/**
 *
 * @author Salo
 */
import java.awt.Image;

public class PulgaMutante extends Pulga {
    public PulgaMutante(int x, int y, Image imagen) {
        super(x, y, 2, imagen);
    }

    @Override
    public void recibirImpacto(CampoBatalla campo) {
        this.vida--;
        if (vida == 1) {
            campo.convertirEnPulgaNormal(this);
        } else if (vida <= 0) {
            campo.incrementarPuntaje();
      }
   }

    int getY() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
