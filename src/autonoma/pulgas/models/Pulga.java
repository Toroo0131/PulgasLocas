package autonoma.pulgas.models;

import java.awt.Image;
import java.awt.Graphics;
import java.util.Random;

public abstract class Pulga {
    protected int x, y;
    protected int vida;
    protected Image imagen;
    protected int velocidadX;
    protected int velocidadY;
    protected int anchoCampo;  // Nuevo campo para almacenar dimensiones del campo
    protected int altoCampo;   // Nuevo campo para almacenar dimensiones del campo

    protected static final int ANCHO = 40;
    protected static final int ALTO = 40;

    public Pulga(int x, int y, int vida, Image imagen, int anchoCampo, int altoCampo) {
        this.x = x;
        this.y = y;
        this.vida = vida;
        this.imagen = imagen;
        this.anchoCampo = anchoCampo;
        this.altoCampo = altoCampo;
        Random rand = new Random();
        this.velocidadX = rand.nextInt(5) + 1; // Velocidad aleatoria entre 1 y 5
        this.velocidadY = rand.nextInt(5) + 1;
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

    public int getY() { return y; }

    public void mover() {
        // Actualizar posición
        this.x += this.velocidadX;
        this.y += this.velocidadY;
        
        // Rebote en bordes horizontales
        if (this.x <= 0 || this.x >= anchoCampo - ANCHO) {
            this.velocidadX *= -1;
            // Asegurarse de que no se salga del campo
            this.x = Math.max(0, Math.min(this.x, anchoCampo - ANCHO));
        }
        
        // Rebote en bordes verticales
        if (this.y <= 0 || this.y >= altoCampo - ALTO) {
            this.velocidadY *= -1;
            // Asegurarse de que no se salga del campo
            this.y = Math.max(0, Math.min(this.y, altoCampo - ALTO));
        }
    }

    public boolean estaDentro(int anchoCampo, int altoCampo) {
        return this.x >= 0 && this.x <= anchoCampo - ANCHO && 
               this.y >= 0 && this.y <= altoCampo - ALTO;
    }
}