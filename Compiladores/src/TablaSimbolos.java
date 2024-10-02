import javax.tools.ForwardingFileObject;
import java.util.HashMap;
import java.util.Map;
import java.io.*;
import java.util.Scanner;
public class TablaSimbolos {
    private Integer token;
    private String atributo;
    private String archivoSimbolos;
    private static Map<String,Integer> tabla;
    private static Integer NO_ENCONTRADO = -1;


    public TablaSimbolos(String archivo) {
        this.archivoSimbolos = archivo;
        this.tabla = addTokenTXT();
    }
    public Map<String,Integer> addTokenTXT(){
        Map<String, Integer> map = new HashMap<>();
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

                        // Agregar el par clave-valor al mapa
                        map.put(key, value);
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
    public void addToken(String Lexema,Integer identificador) {
        if(!tabla.containsKey(Lexema))
            this.tabla.put(Lexema,identificador);
    }

    public int obtenerToken(String lexema) {
        for (Map.Entry<String , Integer> entry : tabla.entrySet()) {
            if ((entry.getKey().equals(lexema)) || (entry.getKey().equalsIgnoreCase(lexema))) {
                return entry.getValue();
            }
        }
        return NO_ENCONTRADO;
    }
    public int determinarTokenValor(String caracter) {
        int valor = this.obtenerToken(caracter);
        if (valor != -1) {
            return valor;
        } else if (caracter.matches("^0x[a-zA-Z0-9]+$")) {
            int hexadecimal = obtenerToken("HEXA");
            String val = caracter.toString();
            //System.out.println(val);
            //System.out.println(numero);
            ParserVal yyval= new ParserVal(val);
            //System.out.println(yyval.lval);
            parser.val_push(yyval);
            System.out.println("Valor almacenado en ParserVal: " + yyval.sval);
            //this.addToken(caracter, hexadecimal);
            return hexadecimal;
        } else if (caracter.equals("@")){
            return obtenerToken("@");
        } else if (caracter.matches("^^\\d+$")) {
            int digito = obtenerToken("DIGITO");
            this.addToken(caracter, digito);
            return digito;
        } else if (caracter.equals("<=")){
            return obtenerToken("MENOR_IGUAL");
        } else if (caracter.equals(">=")){
            return obtenerToken("MAYOR_IGUAL");
        } else if (caracter.equals("!=")){
            return obtenerToken("DISTINTO");
        } else if (caracter.equals(":=")){
            return obtenerToken("ASIGNACION");
        } else if (caracter.matches("^[a-zA-Z][a-zA-Z0-9_]*$")) {
            int identificador = obtenerToken("ID");
            this.addToken(caracter, identificador);
            return identificador;
        } else if (caracter.matches("^[a-zA-Z][a-zA-Z0-9_]*@$")){
            int identificador = obtenerToken("ETIQUETA");
            this.addToken(caracter, identificador);
            return identificador;
        } else if (caracter.matches("\\{[\\s\\S]*\\}")){
            caracter = caracter.replaceAll("\\n", "");
            int cadenaMultilinea = obtenerToken("CML");
            this.addToken(caracter,cadenaMultilinea);
            return cadenaMultilinea;
        }
        return NO_ENCONTRADO;

    }
}
