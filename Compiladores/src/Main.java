//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    static AnalizadorLexico lex = null;
    static Parser par = null;

    public static void main(String[] args) {
        try {
            String path;
            path = "out/production/TrabajoCompilador/Ejecutable.txt";

            int seleccion = 0;
            Scanner scanner = new Scanner(System.in);
            System.out.println("Elija una de las opciones para codigo ejecutable");
            System.out.println("1) Ejecutable 1");
            System.out.println("2) Ejecutable 2");
            System.out.println("3) Ingresar el path de un ejecutable");


            while (seleccion == 0) {
                seleccion = scanner.nextInt();
            }
            if (seleccion == 1) {
                path = "Ejecutable.txt";
            }
            if (seleccion == 2) {
                path = "out/production/TrabajoCompilador/Ejecutable.txt";
            }
            if (seleccion == 3) {
                path = scanner.nextLine();
            }

            Parser par = new Parser(false);
            AnalizadorLexico lex = new AnalizadorLexico(par, path);
            par.run(lex);
            lex.tablaSimbolos.imprimir();
            System.out.println("Fin de compilación");


        } catch (Exception var2) {
            System.err.println("Error durante la ejecución: " + var2.getMessage());
        }

    }
}