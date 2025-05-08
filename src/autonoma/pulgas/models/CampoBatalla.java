package autonoma.pulgas.models;

import autonoma.pulgas.utils.GestorImagenes;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class CampoBatalla {
    private List<Pulga> pulgas;
    private final int ancho;
    private final int alto;
    private int puntaje;
    private final GestorImagenes gestorImagenes;

    public CampoBatalla(int ancho, int alto, GestorImagenes gestorImagenes) {
        this.ancho = ancho;
        this.alto = alto;
        this.pulgas = new ArrayList<>();
        this.puntaje = 0;
        this.gestorImagenes = gestorImagenes;
    }

    public void agregarPulgaNormal() {
        Pulga nueva = crearPulgaSinColision(false);
        if (nueva != null) {
            pulgas.add(nueva);
        }
    }

    public void agregarPulgaMutante() {
        Pulga nueva = crearPulgaSinColision(true);
        if (nueva != null) {
            pulgas.add(nueva);
        }
    }

    private Pulga crearPulgaSinColision(boolean mutante) {
        Random rand = new Random();
        for (int intentos = 0; intentos < 20; intentos++) {
            int x = rand.nextInt(ancho - Pulga.ANCHO);
            int y = rand.nextInt(alto - Pulga.ALTO);
            
            Pulga p = mutante
                ? new PulgaMutante(x, y, gestorImagenes.getImagenMutante(), ancho, alto)
                : new PulgaNormal(x, y, gestorImagenes.getImagenNormal(), ancho, alto);

            if (!hayColision(p)) {
                return p;
            }
        }
        return null;
    }

    private boolean hayColision(Pulga nuevaPulga) {
        for (Pulga otra : pulgas) {
            if (Math.abs(nuevaPulga.getX() - otra.getX()) < Pulga.ANCHO && 
                Math.abs(nuevaPulga.getY() - otra.getY()) < Pulga.ALTO) {
                return true;
            }
        }
        return false;
    }

    public void saltarPulgas() {
        for (Pulga p : pulgas) {
            p.saltar(ancho, alto);
        }
    }

    public void lanzarMisil() {
        int cantidad = pulgas.size() / 2;
        for (int i = 0; i < cantidad && !pulgas.isEmpty(); i++) {
            pulgas.remove(0);
            puntaje++;
        }
    }

    public void impactarEn(int x, int y) {
        Iterator<Pulga> it = pulgas.iterator();
        while (it.hasNext()) {
            Pulga p = it.next();
            if (p.contiene(x, y)) {
                p.recibirImpacto(this);
                if (!p.estaViva()) {
                    it.remove();
                    incrementarPuntaje();
                }
                break;
            }
        }
    }

    public void convertirEnPulgaNormal(PulgaMutante mutante) {
        int x = mutante.getX();
        int y = mutante.getY();
        pulgas.remove(mutante);
        pulgas.add(new PulgaNormal(x, y, gestorImagenes.getImagenNormal(), ancho, alto));
    }

    public void dibujar(Graphics g) {
        for (Pulga p : pulgas) {
            p.dibujar(g);
        }
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void incrementarPuntaje() {
        puntaje++;
    }

    public boolean estaVacio() {
        return false;
    }

    public void actualizarPulgas() {
        for (Pulga pulga : pulgas) {
            pulga.mover();
        }
        
        // Verificar si alguna pulga ha salido del campo
        pulgas.removeIf(pulga -> !pulga.estaDentro(ancho, alto));
    }

    // Métodos auxiliares para obtener dimensiones si son necesarios en otras clases
    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }
}