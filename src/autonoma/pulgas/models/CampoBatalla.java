/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.pulgas.models;

/**
 *
 * @author Estudiante
 */
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CampoBatalla {
    private List<Pulga> pulgas;
    private int ancho, alto;
    private int puntaje;
    private GestorImagenes gestorImagenes;

    public CampoBatalla(int ancho, int alto, GestorImagenes gestorImagenes) {
        this.ancho = ancho;
        this.alto = alto;
        this.pulgas = new ArrayList<>();
        this.puntaje = 0;
        this.gestorImagenes = gestorImagenes;
    }

    public void agregarPulgaNormal() {
        Pulga nueva = crearPulgaSinColision(false);
        if (nueva != null) pulgas.add(nueva);
    }

    public void agregarPulgaMutante() {
        Pulga nueva = crearPulgaSinColision(true);
        if (nueva != null) pulgas.add(nueva);
    }

    private Pulga crearPulgaSinColision(boolean mutante) {
        for (int intentos = 0; intentos < 20; intentos++) {
            int x = (int) (Math.random() * (ancho - 40));
            int y = (int) (Math.random() * (alto - 40));
            Pulga p = mutante
                ? new PulgaMutante(x, y, gestorImagenes.getImagenMutante())
                : new PulgaNormal(x, y, gestorImagenes.getImagenNormal());

            boolean colisiona = false;
            for (Pulga otra : pulgas) {
                if (Math.abs(p.getX() - otra.getX()) < 40 && Math.abs(p.getY() - otra.getY()) < 40) {
                    colisiona = true;
                    break;
                }
            }
            if (!colisiona) return p;
        }
        return null;
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
                if (!p.estaViva()) it.remove();
                break;
            }
        }
    }

    public void convertirEnPulgaNormal(PulgaMutante mutante) {
        int x = mutante.getX();
        int y = mutante.getY();
        pulgas.remove(mutante);
        pulgas.add(new PulgaNormal(x, y, gestorImagenes.getImagenNormal()));
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
        return pulgas.isEmpty();
    }
}
