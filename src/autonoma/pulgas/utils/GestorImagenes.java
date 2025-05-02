
package autonoma.pulgas.utils;

/**
 *
 * @author SalomeC
 */


import javax.swing.ImageIcon;
import java.awt.Image;
import java.net.URL;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

public class GestorImagenes {
    private Image imagenNormal;
    private Image imagenMutante;

    public GestorImagenes() {
        this.imagenNormal = cargarImagen("/pulgaNormal.png");  // Ajusta el nombre si es necesario
        this.imagenMutante = cargarImagen("/pulgaMutante.png");

        if (imagenNormal == null || imagenMutante == null) {
            System.err.println("Error: No se cargaron las imagenes. Verifica la carpeta 'resources'.");
            System.exit(1);
        }
    }

    private Image cargarImagen(String ruta) {
        URL recurso = getClass().getResource(ruta);
        if (recurso != null) {
            return new ImageIcon(recurso).getImage();
        } else {
            System.err.println("No se encontro: " + ruta);
            return null;
        }
    }

    public Image getImagenNormal() {
        return imagenNormal;
    }

    public Image getImagenMutante() {
        return imagenMutante;
    }
}