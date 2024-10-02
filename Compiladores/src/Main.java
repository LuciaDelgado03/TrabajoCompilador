//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Main {
    static AnalizadorLexico lex = null;
    static Parser par = null;

    public Main() {
    }

    public static void main(String[] args) {
        try {

            par = new Parser(false);
            lex = new AnalizadorLexico(par);
            par.run(lex);
            System.out.println("Fin de compilación");
        } catch (Exception var2) {
            System.err.println("Error durante la ejecución: " + var2.getMessage());
        }

    }
}