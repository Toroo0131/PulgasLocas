/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.pulgas.models;

import java.awt.Image;

public class PulgaMutante extends Pulga {
    public PulgaMutante(int x, int y, Image imagen, int anchoCampo, int altoCampo) {
        super(x, y, 2, imagen, anchoCampo, altoCampo);
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
}