/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.pulgas.models;

import java.awt.Image;

public class PulgaNormal extends Pulga {
    public PulgaNormal(int x, int y, Image imagen, int anchoCampo, int altoCampo) {
        super(x, y, 1, imagen, anchoCampo, altoCampo);
    }

    @Override
    public void recibirImpacto(CampoBatalla campo) {
        this.vida--;
        if (vida <= 0) {
            campo.incrementarPuntaje();
        }
    }
}
