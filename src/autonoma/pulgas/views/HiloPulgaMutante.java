package autonoma.pulgas.views;

import autonoma.pulgas.models.CampoBatalla;

public class HiloPulgaMutante implements Runnable {
    private final CampoBatalla campo;
    private final SimuladorPulgas simulador;
    private volatile boolean running = true;
    
    public HiloPulgaMutante(CampoBatalla campo, SimuladorPulgas simulador) {
        this.campo = campo;
        this.simulador = simulador;
    }
    
    public void detener() {
        running = false;
    }
    
    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep((long) (Math.random() * 5000 + 5000)); // Entre 5 y 10 segundos
                if (running) {
                    campo.agregarPulgaMutante();
                    simulador.repaint();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}