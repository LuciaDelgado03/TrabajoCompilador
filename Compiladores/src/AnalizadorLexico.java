import java.util.*;
import java.util.Map.Entry;

public class AnalizadorLexico {
    private LectorDeTexto lector;
    private TablaSimbolos tablaSimbolos;
    private int[][] matrizTransicion;
    private int[][] matrizSemantica;

    public AnalizadorLexico(LectorDeTexto lector, TablaSimbolos tabla, int[][] matriz, int[][] matrizSemantica) {
        this.lector = lector;
        this.tablaSimbolos = tabla;
        this.matrizTransicion = matriz;
        this.matrizSemantica = matrizSemantica;
    }


    /*public int consultarTablaTransicion(int fila, int columna) {
        //el estado es la fila y la columna el valor del caracter
        return
    }*/

    public int asignarValorChar(char caracter) {
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

        switch (accionSemantica) {
            case 1:
                AccionesSemantica.AS1 instanciaAS1 = AccionesSemantica.AS1.obtenerInstancia(lector);
                instanciaAS1.ejecutarAccionSemantica(accionSemantica, cadena, caracter);
                break;
            case 2:
                AccionesSemantica.AS2 instanciaAS2 = AccionesSemantica.AS2.obtenerInstancia(lector);
                instanciaAS2.ejecutarAccionSemantica(accionSemantica, cadena, caracter);
                break;
            case 3:
                AccionesSemantica.AS3 instanciaAS3 = AccionesSemantica.AS3.obtenerInstancia(lector);
                instanciaAS3.ejecutarAccionSemantica(accionSemantica, cadena, caracter);
                break;
            case 4:
                AccionesSemantica.AS4 instanciaAS4 = AccionesSemantica.AS4.obtenerInstancia(lector);
                instanciaAS4.ejecutarAccionSemantica(accionSemantica, cadena, caracter);
                break;
            case 5:
                AccionesSemantica.AS5 instanciaAS5 = AccionesSemantica.AS5.obtenerInstancia(lector);
                instanciaAS5.ejecutarAccionSemantica(accionSemantica, cadena, caracter);
                break;
            case 6:
                AccionesSemantica.AS6 instanciaAS6 = AccionesSemantica.AS6.obtenerInstancia(lector);
                instanciaAS6.ejecutarAccionSemantica(accionSemantica, cadena, caracter);
                break;
            case 7:
                AccionesSemantica.AS7 instanciaAS7 = AccionesSemantica.AS7.obtenerInstancia(lector);
                instanciaAS7.ejecutarAccionSemantica(accionSemantica, cadena, caracter);
                break;
            case 8:
                AccionesSemantica.AS8 instanciaAS8 = AccionesSemantica.AS8.obtenerInstancia(lector);
                instanciaAS8.ejecutarAccionSemantica(accionSemantica, cadena, caracter);
                break;
            case 9:
                AccionesSemantica.AS9 instanciaAS9 = AccionesSemantica.AS9.obtenerInstancia(lector);
                instanciaAS9.ejecutarAccionSemantica(accionSemantica, cadena, caracter);
                break;
            case 10:
                AccionesSemantica.AS10 instanciaAS10 = AccionesSemantica.AS10.obtenerInstancia(lector);
                instanciaAS10.ejecutarAccionSemantica(accionSemantica, cadena, caracter);
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
    public int yylex() {
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