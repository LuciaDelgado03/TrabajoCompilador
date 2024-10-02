import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LectorDeTexto {
    private BufferedReader br;
    int nroLinea;
    private String linea;
    private int columna;
    private boolean saltoDeLineaPendiente;

    public LectorDeTexto(String nombreArchivo) {
        try {
            br = new BufferedReader(new FileReader(nombreArchivo));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.nroLinea = 0;
        this.getNuevaLinea();
        columna = 0;
    }

    public int getNroLinea() {
        return nroLinea;
    }

    public int getColumna() {
        return this.columna;
    }

    public void getNuevaLinea() {
        try {
            this.linea = br.readLine();
            nroLinea++;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void cerrarArchivo() {
        try {
            if (br != null) {
                br.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public char nuevoCaracter() {
            // Si hay un salto de línea pendiente, devolverlo primero
        if (saltoDeLineaPendiente) {
            saltoDeLineaPendiente = false;
            return '\n'; // Devolvemos el salto de línea explícitamente
            }
        if ((linea == null) || (columna >= linea.length())) {
            if (this.hayLineas()) {
                this.getNuevaLinea();
                this.columna = 0;
                return '\t';
            } else {
                System.out.println("fin de archivo");
                return '?'; //caracter de fin
            }
        }
        //System.out.println("linea:" + this.linea);
        char caracter = linea.charAt(columna);

        columna++;
        //System.out.println("lectorLexico:"+ caracter);
        return caracter;
    }

    public void retrocederCaracter(){
        //TODO: si estoy en la linea y retrocede en la primera tengo que volver a la linea anterior?
        if(columna > 0)
        this.columna--;
    }

    public boolean hayLineas() {
        try {
            br.mark(100); // Marcamos la posición actual
            if (br.readLine() == null) {
                return false; // Si no hay más líneas, retornamos false
            }
            br.reset(); // Volvemos a la posición marcada
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true; // Si hay más líneas, retornamos true
    }
}

