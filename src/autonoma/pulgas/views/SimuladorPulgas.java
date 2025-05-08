package autonoma.pulgas.views;

import autonoma.pulgas.models.*;
import autonoma.pulgas.utils.GestorImagenes;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Graphics;

public class SimuladorPulgas extends JPanel implements KeyListener, MouseListener, Runnable {
    private CampoBatalla campo;
    private final GestorImagenes gestorImagenes;
    private JFrame ventana;
    private int record;
    private final String ARCHIVO_RECORD = "resources/puntaje.txt";
    private volatile boolean running = true;
    private HiloPulgaNormal hiloNormal;
    private HiloPulgaMutante hiloMutante;
    private Thread hiloPrincipal;
    
    public SimuladorPulgas() {
        this.setPreferredSize(new Dimension(800, 600));
        this.setFocusable(true);
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.requestFocusInWindow();

        gestorImagenes = new GestorImagenes();
        campo = new CampoBatalla(800, 600, gestorImagenes);

        cargarRecord();

        iniciarVentana();
        iniciarHilos();
    }
    
    private void iniciarHilos() {
        // Detener hilos anteriores si existen
        detenerHilos();
        
        // Hilo principal de movimiento
        hiloPrincipal = new Thread(this);
        hiloPrincipal.start();
        
        // Hilos de generación de pulgas
        hiloNormal = new HiloPulgaNormal(campo, this);
        hiloMutante = new HiloPulgaMutante(campo, this);
        
        new Thread(hiloNormal).start();
        new Thread(hiloMutante).start();
    }
    
    private void detenerHilos() {
        running = false;
        
        if (hiloNormal != null) {
            hiloNormal.detener();
        }
        
        if (hiloMutante != null) {
            hiloMutante.detener();
        }
        
        if (hiloPrincipal != null) {
            hiloPrincipal.interrupt();
        }
    }
    
    private void iniciarVentana() {
        ventana = new JFrame("Simulador AntiPulgas");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.add(this);
        ventana.pack();
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        campo.dibujar(g);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Puntaje: " + campo.getPuntaje(), 20, 25);
        g.drawString("Récord: " + record, 20, 50);

        if (campo.estaVacio()) {
            g.setFont(new Font("Arial", Font.BOLD, 28));
            g.setColor(Color.RED);
            g.drawString("¡Simulación finalizada!", 250, 300);
            mostrarDialogoFinal();
        }
    }

    private void mostrarDialogoFinal() {
        SwingUtilities.invokeLater(() -> {
            actualizarRecord();
            
            int opcion = JOptionPane.showConfirmDialog(this,
                    "¿Deseas reiniciar la partida?", "Fin del juego",
                    JOptionPane.YES_NO_OPTION);

            if (opcion == JOptionPane.YES_OPTION) {
                reiniciarJuego();
            } else {
                System.exit(0);
            }
        });
    }
    
    private void reiniciarJuego() {
        campo = new CampoBatalla(800, 600, gestorImagenes);
        running = true;
        iniciarHilos();
        repaint();
    }
    
    private void actualizarRecord() {
        if (campo.getPuntaje() > record) {
            guardarRecord(campo.getPuntaje());
        }
    }

    private void cargarRecord() {
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_RECORD))) {
            record = Integer.parseInt(br.readLine());
        } catch (IOException | NumberFormatException e) {
            record = 0;
        }
    }

    private void guardarRecord(int nuevoRecord) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_RECORD))) {
            bw.write(String.valueOf(nuevoRecord));
            record = nuevoRecord;
        } catch (IOException e) {
            System.err.println("Error al guardar record.");
        }
    }
    
    @Override
    public void run() {
        while (running) {
            campo.actualizarPulgas();
            
            // Verificar y actualizar record continuamente
            if (campo.getPuntaje() > record) {
                guardarRecord(campo.getPuntaje());
            }
            
            repaint();
            
            try {
                Thread.sleep(50); // Tiempo de actualización más rápido para mejor respuesta
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'p':
                campo.agregarPulgaNormal();
                break;
            case 'm':
                campo.agregarPulgaMutante();
                break;
            case 's':
                campo.saltarPulgas();
                break;
            case 'q':
                mostrarDialogoFinal();
                break;
            case 'x':
                campo.lanzarMisil();
                if (campo.getPuntaje() > record) {
                    guardarRecord(campo.getPuntaje());
                }
            default:
                break;
        }
        repaint();
    }

    
    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            campo.impactarEn(e.getX(), e.getY());
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SimuladorPulgas::new);
    }
}


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

