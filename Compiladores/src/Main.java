//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Main {
    public static int[][] leerArchivoComoMatriz(String rutaArchivo) {
        int[][] matriz = null;

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            // Leer todas las líneas del archivo
            String linea;
            int numFilas = 0;
            int numColumnas = 0;

            // Determinar el número de filas y columnas
            while ((linea = br.readLine()) != null) {
                numFilas++;
                String[] columnas = linea.split("\\s+"); // Separar por espacios
                numColumnas = columnas.length; // Suponer que todas las filas tienen el mismo número de columnas
            }

            // Inicializar la matriz
            matriz = new int[numFilas][numColumnas];

            // Volver al principio del archivo para llenar la matriz
            br.close();
            try (BufferedReader br2 = new BufferedReader(new FileReader(rutaArchivo))) {
                int filaActual = 0;

                while ((linea = br2.readLine()) != null) {
                    String[] columnas = linea.split("\\s+");
                    for (int columnaActual = 0; columnaActual < columnas.length; columnaActual++) {
                        matriz[filaActual][columnaActual] = Integer.parseInt(columnas[columnaActual]);
                    }
                    filaActual++;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return matriz;
    }

    public static void main(String[] args) {
        String Path = "Compiladores/src/TablaSimbolos.txt";
        TablaSimbolos TS = new TablaSimbolos(Path);
        TS.addToken("hola", 31);
        TS.addToken("22", 32);
        //TS.addToken("IF",1);

        int valor = TS.obtenerToken("22");
        System.out.println("el valor es: " + valor);
        TS.imprimir();
        String archEjecutable = "Compiladores/src/Ejecutable.txt";
        LectorDeTexto Lec = new LectorDeTexto(archEjecutable);
        String matrizTransiciones = "Compiladores/src/matrizTransicion.txt";
        String matrizAccionesSemanticas = "Compiladores/src/MatrizDeAccionesSemanticas.txt";
        int[][] matrizAcciones = leerArchivoComoMatriz(matrizAccionesSemanticas);
        int[][] matrizTrans = leerArchivoComoMatriz(matrizTransiciones);


        AccionesSemantica.AS1 instanciaAS1 = AccionesSemantica.AS1.obtenerInstancia(Lec);
        AccionesSemantica.AS2 instanciaAS2 = AccionesSemantica.AS2.obtenerInstancia(Lec);
        AccionesSemantica.AS3 instanciaAS3 = AccionesSemantica.AS3.obtenerInstancia(Lec);
        AccionesSemantica.AS4 instanciaAS4 = AccionesSemantica.AS4.obtenerInstancia(Lec);
        AccionesSemantica.AS5 instanciaAS5 = AccionesSemantica.AS5.obtenerInstancia(Lec);
        ArrayList<AccionesSemantica> acciones = new ArrayList<>();
        acciones.add(instanciaAS1);
        acciones.add(instanciaAS2);
        acciones.add(instanciaAS3);
        acciones.add(instanciaAS4);
        acciones.add(instanciaAS5);

        AnalizadorLexico lex = new AnalizadorLexico(Lec, TS, matrizTrans, matrizAcciones, acciones);
        Integer token = null;
        ArrayList<Integer> tiraTokens = new ArrayList<>();
        token = lex.sigToken();
        System.out.println(token);
        int i = 0;
        while (token != 100) {
            token = lex.sigToken();
            System.out.println("Token: "+token);
            tiraTokens.add(token);
        }
        TS.imprimir();

        /*
        int vvalor = TS.determinarTokenValor("0ola");
        System.out.println("primero" + vvalor);
        vvalor = TS.determinarTokenValor("0x2");
        System.out.println("segundo" + vvalor);
        vvalor = TS.determinarTokenValor("if");
        System.out.println("tercero" + vvalor);
        String prueba = "[hola \n"+  "\n"+ "chau \\n]";
        System.out.println(prueba);
        vvalor = TS.determinarTokenValor(prueba);
        System.out.println(vvalor);
    */

    }
}