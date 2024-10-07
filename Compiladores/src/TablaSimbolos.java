import javax.tools.ForwardingFileObject;
import java.util.HashMap;
import java.util.Map;
import java.io.*;
import java.util.Scanner;
public class TablaSimbolos {
    private Integer token;
    private String atributo;
    private String archivoSimbolos;
    private static Map<String,DatosTablaSimbolos> tabla;
    private static Integer NO_ENCONTRADO = -1;


    public TablaSimbolos(String archivo) {
        this.archivoSimbolos = archivo;
        this.tabla = addTokenTXT();
    }
    public Map<String,DatosTablaSimbolos> addTokenTXT(){
        Map<String, DatosTablaSimbolos> map = new HashMap<>();
        String filePath = archivoSimbolos; // Cambia esta ruta por la ubicación correcta del archivo

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Usar split con límite -1 para capturar todos los elementos, incluyendo los vacíos
                String[] parts = line.split(",", -1);
                if (parts.length >= 2) {
                    try {
                        // Convertir la primera parte en un Integer (valor)
                        Integer value = Integer.parseInt(parts[0].trim());

                        // Determinar el valor según las partes
                        String key;
                        if (parts.length == 3 && parts[1].isEmpty() && parts[2].isEmpty()) {
                            // Si la línea es como "18,,", asignar "," como clave
                            key = ",";
                        } else {
                            // En cualquier otro caso, tomar la segunda parte como clave
                            key = parts[1].trim();
                        }
                        DatosTablaSimbolos datos = new DatosTablaSimbolos(value);
                        // Agregar el par clave-valor al mapa
                        map.put(key, datos);
                    } catch (NumberFormatException e) {
                        System.out.println("Error al convertir el valor a Integer: " + parts[0]);
                    }
                } else {
                    System.out.println("Línea no válida: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public void imprimir(){
        this.tabla.forEach((key, value) -> System.out.println(key + " : " + value));
    }

    public void addToken(String Lexema,Integer identificador, String tipo) {
        if(!tabla.containsKey(Lexema)){
            DatosTablaSimbolos datos = new DatosTablaSimbolos(identificador);
            datos.setTipo(tipo);
            this.tabla.put(Lexema,datos);
        }
    }

    public int obtenerToken(String lexema) {
        for (Map.Entry<String , DatosTablaSimbolos> entry : tabla.entrySet()) {
            if ((entry.getKey().equals(lexema)) || (entry.getKey().equalsIgnoreCase(lexema))) {
                return entry.getValue().getToken();
            }
        }
        return NO_ENCONTRADO;
    }


    public int determinarTokenValor(String cadena) {
        int valor = this.obtenerToken(cadena);
        if (valor != -1) {
            //System.out.println("encontrado");
            return valor;

        } else if (cadena.matches("^0x[a-zA-Z0-9]+$")) {
            int hexadecimal = obtenerToken("HEXA");
            //addToken(cadena, null, "LONGINT");

            return hexadecimal;

        } else if (cadena.equals("@")){
            return obtenerToken("@");

        } else if (cadena.matches("^^\\d+$")) {
            int digito = obtenerToken("LONGINT");
            return digito;

        } else if (cadena.equals("<=")){
            return obtenerToken("MENOR_IGUAL");

        } else if (cadena.equals(">=")){
            return obtenerToken("MAYOR_IGUAL");

        } else if (cadena.equals("!=")){
            return obtenerToken("DISTINTO");

        } else if (cadena.equals(":=")){
            return obtenerToken("ASIGNACION");

        } else if (cadena.matches("^[a-zA-Z][a-zA-Z0-9_]*$")) {
            int identificador = obtenerToken("ID");
            this.addToken(cadena, identificador, null);
            return identificador;

        } else if (cadena.matches("^[a-zA-Z][a-zA-Z0-9_]*@$")){
            int etiqueta = obtenerToken("ETIQUETA");
            this.addToken(cadena, etiqueta, null);
            return etiqueta;

        } else if (cadena.matches("\\{[\\s\\S]*\\}")){
            int cadenaMultilinea = obtenerToken("CML");
            this.addToken(cadena,cadenaMultilinea, null);
            return cadenaMultilinea;

        } else if (cadena.matches("^\\d+\\.\\d+([dD][+-–]?\\d+)?$")) {
            int identificador = obtenerToken("DOUBLE");
            return identificador;
        }
        return NO_ENCONTRADO;

    }
}
