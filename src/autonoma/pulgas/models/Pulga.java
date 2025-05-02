/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.pulgas.models;

/**
 *
 * @author Salo
 */
import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

public abstract class Pulga {
    protected int x, y;
    protected int vida;
    protected Image imagen;

    protected static final int ANCHO = 40;
    protected static final int ALTO = 40;

    public Pulga(int x, int y, int vida, Image imagen) {
        this.x = x;
        this.y = y;
        this.vida = vida;
        this.imagen = imagen;
    }

    public void saltar(int anchoCampo, int altoCampo) {
        Random rand = new Random();
        this.x = rand.nextInt(anchoCampo - ANCHO);
        this.y = rand.nextInt(altoCampo - ALTO);
    }

    public abstract void recibirImpacto(CampoBatalla campo);

    public void dibujar(Graphics g) {
        g.drawImage(imagen, x, y, ANCHO, ALTO, null);
    }

    public boolean contiene(int px, int py) {
        return px >= x && px <= x + ANCHO && py >= y && py <= y + ALTO;
    }

    public boolean estaViva() {
        return vida > 0;
    }

    public int getX() { return x; }
    
}