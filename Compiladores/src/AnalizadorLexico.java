import java.util.*;
import java.util.Map.Entry;

public class AnalizadorLexico {
    private LectorDeTexto lector;
    private TablaSimbolos tablaSimbolos;
    private int[][] matrizTransicion;
    private int[][] matrizSemantica;
    private ArrayList<AccionesSemantica> acciones;
    public AnalizadorLexico(LectorDeTexto lector, TablaSimbolos tabla, int[][] matriz, int[][] matrizSemantica, ArrayList<AccionesSemantica> acciones) {
        this.lector = lector;
        this.tablaSimbolos = tabla;
        this.matrizTransicion = matriz;
        this.matrizSemantica = matrizSemantica;
        this.acciones = acciones;
    }


    /*public int consultarTablaTransicion(int fila, int columna) {
        //el estado es la fila y la columna el valor del caracter
        return
    }*/

    public int asignarValorChar(char caracter) {
        //asignamos valores a los caracteres segun la codigo ASCII
        switch (caracter) {
            case '+':
            case '-':
            case '*':
            case '/':
            case '(':
            case ')':
            case ',':
            case ';':
                return 0;
            case ':':
            case '!':
                return 1;
            case '>':
            case '<':
                return 2;
            case '[':
                return 3;
            case '#':
                return 4;
            case '_':
                return 5;
            case '=':
                return 6;
            case ']':
                return 7;
            case ' ':
            case '\t':
                return 8;
            case '\n':
                return 9;
            case '.':
                return 10;
            case '0':
                return 11;
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                return 12;
            case 'x':
            case 'X':
                return 13;
            case 'd':
            case 'D':
                return 14;
            case 'a':
            case 'A':
            case 'b':
            case 'B':
            case 'c':
            case 'C':
            case 'e':
            case 'E':
            case 'f':
            case 'F':
                return 15;
            case '?':
                return 16;
            default:
                if (Character.isLetter(caracter)) {
                    return 17;
                } else {
                    throw new IllegalArgumentException("Caracter no válido: " + caracter);
                }
        }
    }

        public void ejecutarAccionSemantica(int accionSemantica, StringBuilder cadena,  Character caracter) {
        AccionesSemantica accion; // Declaración de la variable

        switch (accionSemantica) {
            case 1:
                //accion = new AccionesSemantica.AS1(); // Crear nueva instancia de AS1

                acciones.get(0).ejecutarAccionSemantica(accionSemantica, cadena, caracter);
                break;
            case 2:
                acciones.get(1).ejecutarAccionSemantica(accionSemantica, cadena, caracter);
                break;
            case 3:
                acciones.get(2).ejecutarAccionSemantica(accionSemantica, cadena, caracter);
                break;
            case 4:
                acciones.get(3).ejecutarAccionSemantica(accionSemantica, cadena, caracter);
                break;
            case 5:
                acciones.get(4).ejecutarAccionSemantica(accionSemantica, cadena, caracter);
                break;
            case 6:

                break;
            case 7:

                break;
            case 8:

                break;
            case 9:

                break;
            case 10:

                break;
            case 11:

                break;
            case 15:
                System.out.println("hola no hago nd");
                break;
            default:
                //System.out.println("soy la accion: "+accionSemantica);
                System.out.println("Acción no válida. El número debe estar entre 1 y 10.");
                break;
        }
    }

    //ver el tema que no se quede en el while si nos quedamos sin caracteres
    public int sigToken() {
        int valorToken = -1;
        int estado = 0;
        int estadoAnt = 0;
        StringBuilder cadena = new StringBuilder();
        Character caracter = null;
        while (estado != -1) { //estado final es -1 y error es -2
            estadoAnt = estado;
            caracter = lector.nuevoCaracter();
            System.out.println("caracter:" +caracter);
            int valorChar = asignarValorChar(caracter);

            //consulta la tabla de transicion para saber a que estado movernos

            estado = matrizTransicion[estado][valorChar];

            System.out.println(cadena); //TODO: ver como conectar el cadena con el token de las acciones semanticas para que se guarde cuando se corta
            if (estado == -2) {
                estado = estadoAnt; //TODO: estado anterior si se ejecuta alguna accion semantica que retroceda la columna en el lector
            }
            //System.out.println("EstadoNuevo:" + estado);
            int accionSemantica = matrizSemantica[estadoAnt][valorChar];
            ejecutarAccionSemantica(accionSemantica, cadena, caracter);
            //VER COMO HACER ESTO, deberia comprobar si hay un error y informalo
            //}
            /*if (estado == -2) { //estado de error
                //controlarError(estadoAnt, caracter);
                estado = 0; //si encuentro un error vuelvo a empezar, chequear despues para salvarlo
                cadena = "";
            }*/

        }
         //pasar a Acciones semanticas
        System.out.println("cadena: " + cadena);
        return tablaSimbolos.determinarTokenValor(cadena.toString());
    }
}