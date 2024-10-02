import java.awt.*;

public abstract class AccionesSemantica {
    //protected String Token;
    protected LectorDeTexto lector;

    public AccionesSemantica(LectorDeTexto lector) {
        this.lector = lector;
    }

    public abstract void ejecutarAccionSemantica(int accionSemantica, StringBuilder cadena, Character caracter);

    public static class AS1 extends AccionesSemantica {
        private static AS1 instancia;

        private AS1(LectorDeTexto lector) {
            super(lector);
        }

        public static AS1 obtenerInstancia(LectorDeTexto lector) {
            if (instancia == null) {
                instancia = new AS1(lector);
            }
            return instancia;
        }

        public void ejecutarAccionSemantica(int accionSemantica, StringBuilder cadena, Character caracter) {
            cadena.append(caracter);
            //System.out.println("Letra que inserta "+"'"+cadena+"'");
        }
    }

    public static class AS2 extends AccionesSemantica {
        private static AS2 instancia;

        private AS2(LectorDeTexto lector) {
            super(lector);
        }

        public static AS2 obtenerInstancia(LectorDeTexto lector) {
            if (instancia == null) {
                instancia = new AS2(lector);
            }
            return instancia;
        }

        public void ejecutarAccionSemantica(int accionSemantica, StringBuilder cadena, Character caracter) {
            if (cadena.length() > 15) {
                System.out.println("ERROR: Supera el limite indicado para los id en la linea: " + lector.getNroLinea() + " en la columna: " + lector.getColumna());
                // Truncar el StringBuilder a 15 caracteres
                cadena.setLength(15); // Establece la longitud máxima de la cadena a 15
            }
            if(!caracter.equals('?')) {
                lector.retrocederCaracter();

            }
        }
    }

    public static class AS3 extends AccionesSemantica {
        private static AS3 instancia;

        private AS3(LectorDeTexto lector) {
            super(lector);
        }

        public static AS3 obtenerInstancia(LectorDeTexto lector) {
            if (instancia == null) {
                instancia = new AS3(lector);
            }
            return instancia;
        }

        public void ejecutarAccionSemantica(int accionSemantica, StringBuilder cadena, Character caracter) {
            //informar linea se esperaba un igual y llego otro caracter
            System.out.println("ERROR: Se esperaba un simbolo '=' en la linea: " + lector.getNroLinea() + " en la columna: " + lector.getColumna() +" y se obtuvo el simbolo: "+ caracter);

            if(!caracter.equals('?')) {
                lector.retrocederCaracter();

            }
        }
    }

    //TODO: SE OCUPA CON EL ? creo
    public static class AS4 extends AccionesSemantica {
        private static AS4 instancia;

        private AS4(LectorDeTexto lector) {
            super(lector);
        }

        public static AS4 obtenerInstancia(LectorDeTexto lector) {
            if (instancia == null) {
                instancia = new AS4(lector);
            }
            return instancia;
        }

        public void ejecutarAccionSemantica(int accionSemantica, StringBuilder cadena, Character caracter) {
            System.out.println(cadena);
            System.out.println("");
            cadena.setLength(0);
        }
    }

    public static class AS5 extends AccionesSemantica {
        private static AS5 instancia;

        private AS5(LectorDeTexto lector) {
            super(lector);
        }

        public static AS5 obtenerInstancia(LectorDeTexto lector) {
            if (instancia == null) {
                instancia = new AS5(lector);
            }
            return instancia;
        }

        public void ejecutarAccionSemantica(int accionSemantica, StringBuilder cadena, Character caracter) {
            /*
            // Convertir el StringBuilder a String
            String numeroEnCadena = cadena.toString();
            // Convertir la cadena a un número entero
            int numero = Integer.parseInt(numeroEnCadena);

            if (numero >= 2147483647) { //TODO: como hacemos con el negativo?
                System.out.println("ERROR: Supera el limite indicado para los longint en la linea: "
                        + lector.getNroLinea() + " en la columna: " + lector.getColumna());
            }

            try {
                // Convertir la cadena a un número entero
                int numero = Integer.parseInt(numeroEnCadena);

                // Verificar si está dentro del rango de un int (-2^31 a 2^31 - 1)
                //TODO: CREO QUE EL VERIFICAR NO ES ASI PORQUE NO TENEMOS EN CUENTA EL SIMBOLO A ESTA ALTURA, NUNCA VA A SER NEGATIVO
                if (numero < Integer.MIN_VALUE || numero > Integer.MAX_VALUE) {
                    System.out.println("ERROR: Supera el limite indicado para los longint en la linea: "
                            + lector.getNroLinea() + " en la columna: " + lector.getColumna());
                }
            } catch (NumberFormatException e) {
                // Capturar la excepción si la conversión no es válida
                System.out.println("ERROR: El número no es válido en la linea: " + lector.getNroLinea()
                        + " en la columna: " + lector.getColumna());
            }
             */

            if(!caracter.equals('?')) {
                lector.retrocederCaracter();

            }
        }
    }

    public static class AS6 extends AccionesSemantica {
        private static AS6 instancia;

        private AS6(LectorDeTexto lector) {
            super(lector);
        }

        public static AS6 obtenerInstancia(LectorDeTexto lector) {
            if (instancia == null) {
                instancia = new AS6(lector);
            }
            return instancia;
        }

        public void ejecutarAccionSemantica(int accionSemantica, StringBuilder cadena, Character caracter) {
            //informa error ya que se esperaba un numero, inserta el caracter
            System.out.println("ERROR: Se esperaba un numero en la linea: " + lector.getNroLinea() + " en la columna: " + lector.getColumna() +" y se obtuvo el simbolo: "+"'"+ caracter+"'");
            if(!caracter.equals('?')) {
                lector.retrocederCaracter();

            }
        }

    }

    public static class AS7 extends AccionesSemantica {
        private static AS7 instancia;

        private AS7(LectorDeTexto lector) {
            super(lector);
        }

        public static AS7 obtenerInstancia(LectorDeTexto lector) {
            if (instancia == null) {
                instancia = new AS7(lector);
            }
            return instancia;
        }

        public void ejecutarAccionSemantica(int accionSemantica, StringBuilder cadena, Character caracter) {
            // Convertir StringBuilder a String
            String numeroEnCadena = cadena.toString();

            try {
                // Convertir la cadena a un número double
                double numero = Double.parseDouble(numeroEnCadena);

                // Verificar si está fuera del rango permitido o es 0.0
                //TODO: CREO QUE EL VERIFICAR NO ES ASI PORQUE NO TENEMOS EN CUENTA EL SIMBOLO A ESTA ALTURA, NUNCA VA A SER NEGATIVO
                if (!(numero > 2.2250738585072014e-308 && numero < 1.7976931348623157e308) &&
                        !(numero < -2.2250738585072014e-308 && numero > -1.7976931348623157e308)) {
                    // Fuera del rango o 0.0
                    System.out.println("ERROR: Supera el límite indicado para los valores double en la línea: "
                            + lector.getNroLinea() + " en la columna: " + lector.getColumna());
                }
            } catch (NumberFormatException e) {
                // El número no es válido
                System.out.println("ERROR: El número no es válido en la línea: " + lector.getNroLinea()
                        + " en la columna: " + lector.getColumna());
            }

            // Retroceder un carácter
            if(!caracter.equals('?')) {
                lector.retrocederCaracter();

            }
        }
    }

    public static class AS8 extends AccionesSemantica {
        private static AS8 instancia;

        private AS8(LectorDeTexto lector) {
            super(lector);
        }

        public static AS8 obtenerInstancia(LectorDeTexto lector) {
            if (instancia == null) {
                instancia = new AS8(lector);
            }
            return instancia;
        }

        public void ejecutarAccionSemantica(int accionSemantica, StringBuilder cadena, Character caracter) {
            //se esperaba un numero o una letra A,B,C,D,E,F , y inserto en la linea
            System.out.println("ERROR: Se esperaba un digito o una letra de la A-F en la linea: " + lector.getNroLinea()
                                + " en la columna: " + lector.getColumna() +" y se obtuvo el simbolo: "+"'"+ caracter+"'");
            if(!caracter.equals('?')) {
                lector.retrocederCaracter();
            }
        }
    }

    public static class AS9 extends AccionesSemantica {
        private static AS9 instancia;

        private AS9(LectorDeTexto lector) {
            super(lector);
        }

        public static AS9 obtenerInstancia(LectorDeTexto lector) {
            if (instancia == null) {
                instancia = new AS9(lector);
            }
            return instancia;
        }

        public void ejecutarAccionSemantica(int accionSemantica, StringBuilder cadena, Character caracter) {
            // se esperaba un # para comentario, pero no llego, inserta el ultimo caracter
            System.out.println("ERROR: Se esperaba un simbolo '#' en la linea: " + lector.getNroLinea()
                    + " en la columna: " + lector.getColumna() +" y se obtuvo el simbolo: "+ "'"+ caracter+"'");
            if(!caracter.equals('?')) {
                lector.retrocederCaracter();
            }
        }
    }

    public static class AS10 extends AccionesSemantica {
        private static AS10 instancia;

        private AS10(LectorDeTexto lector) {
            super(lector);
        }

        public static AS10 obtenerInstancia(LectorDeTexto lector) {
            if (instancia == null) {
                instancia = new AS10(lector);
            }
            return instancia;
        }

        public void ejecutarAccionSemantica(int accionSemantica, StringBuilder cadena, Character caracter) {
            //verificar el rango del hexadecimal y retroceder
            // Convertir StringBuilder a String
            /*String numeroEnHexadecimal = cadena.toString();

            try {
                // Verificar si es un número negativo
                int numero;
                if (numeroEnHexadecimal.startsWith("-")) {
                    // Si es negativo, quitar el prefijo "-0x" y convertir a entero negativo
                    numero = Integer.parseUnsignedInt(numeroEnHexadecimal.substring(3), 16) * -1;
                } else {
                    // Si es positivo, quitar el prefijo "0x" y convertir a entero
                    numero = Integer.parseUnsignedInt(numeroEnHexadecimal.substring(2), 16);
                }

                // Verificar si el número está fuera del rango permitido para un entero con signo de 32 bits
                if (numero <= -2147483648 || numero >= 2147483647) {
                    System.out.println("ERROR: Supera el límite indicado para los valores hexadecimales en la línea: "
                            + lector.getNroLinea() + " en la columna: " + lector.getColumna());
                }
            } catch (NumberFormatException e) {
                // El número no es válido
                System.out.println("ERROR: El número hexadecimal no es válido en la línea: " + lector.getNroLinea()
                        + " en la columna: " + lector.getColumna());
            }

             */

            // Retroceder un carácter
            if(!caracter.equals('?')) {
                lector.retrocederCaracter();

            }
        }
    }

}


/*public abstract class AccionesSemantica {
    public String Token;

    public void insertarCaracter(){

    }

    public abstract void ejecutarAccionSemantica(int accionSemantica, String cadena);

    public class AS1 extends AccionesSemantica {
        public void ejecutarAccionSemantica(int accionSemantica, String cadena) {
            //GUARDA LOS CARACTERES QUE ESTAMOS LEYENDO
            //concatenar
        }
    }

    public class AS2 extends AccionesSemantica {
        public void ejecutarAccionSemantica(int accionSemantica, String cadena) {
            // controlar la longitud y concatenar, ademas verificar el rango
            // verificar si viene algo distinto de un digito, letra o _ y eso insertarlo en la misma linea
        }
    }
    public class AS3 extends AccionesSemantica {
        public void ejecutarAccionSemantica(int accionSemantica, String cadena) {
        //informar linea se esperaba un igual y llego otro caracter
        }
    }
    //es la misma que la AS3
    public class AS4 extends AccionesSemantica {
        public void ejecutarAccionSemantica(int accionSemantica, String cadena) {
            //verificar si viene algo distinto de = y insertarlo en la misma linea
        }
    }

    public class AS5 extends AccionesSemantica {
        public void ejecutarAccionSemantica(int accionSemantica, String cadena) {
            //verifica el rango si es long int imprime por pantall , y lo  inserta el caracter en la linea
        }
    }

    public class AS6 extends AccionesSemantica {
        public void ejecutarAccionSemantica(int accionSemantica, String cadena) {
            //informa error ya que se esperaba un numero, inserta el caracter
        }
    }

    public class AS7 extends AccionesSemantica {
        public void ejecutarAccionSemantica(int accionSemantica, String cadena) {
            //es un punto flotante, corroborar el rango, avisar en caso de error, vuelve a imsertar el digito
        }
    }

    public class AS8 extends AccionesSemantica {
        public void ejecutarAccionSemantica(int accionSemantica, String cadena) {
            //se esperaba un numero o una letra A,B,C,D,E,F , y inserto en la linea
        }
    }

    public class AS9 extends AccionesSemantica {
        public void ejecutarAccionSemantica(int accionSemantica, String cadena) {
            // se eesperaba un # para comentario, pero no llego, inserta el ultimo caracter
        }
    }

    public class AS10 extends AccionesSemantica {
        public void ejecutarAccionSemantica(int accionSemantica, String cadena) {
            // verificar el rango de exa, e insertar el ultimo caracter
        }
    }

    public class AS11 extends AccionesSemantica {
        public void ejecutarAccionSemantica(int accionSemantica, String cadena) {
            //fin del archivo controlar lo q haya q hacer :P
        }
    }


}
*/



