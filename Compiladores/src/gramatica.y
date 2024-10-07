%{
import java.io.*;

import java.math.BigDecimal;
%}
        //declaracion de tokens a recibir del Analizador Lexico
%token IF THEN ELSE BEGIN END END_IF OUTF TYPEDEF FUN RET STRING REPEAT WHILE GOTO ID LONGINT HEXA CML DOUBLE TOD STRUCT ASIGNACION DISTINTO MENOR_IGUAL MAYOR_IGUAL ETIQUETA

%left '+' '-'
%left '*' '/'
%left '.'
%left ASIGNACION
%nonassoc LOWER_THAN_ELSE
%nonassoc ELSE
%start prog


%%
prog							: ID BEGIN cuerpo END {System.out.println("Fin sentencia prog");}
							    | ID cuerpo END {yyerror("ERROR, falta begin programa principal en la linea: " + lector.getNroLinea());}
                                | BEGIN END {yyerror("ERROR, falta el ID del programa principal en la linea: " + lector.getNroLinea());}
                                | ID BEGIN cuerpo {yyerror("ERROR, falta END del programa principal en la linea: " + lector.getNroLinea());}
                                | ID cuerpo {yyerror("ERROR, falta BEGIN,END del programa principal en la linea: " + lector.getNroLinea());}
                                | BEGIN cuerpo  {yyerror("ERROR, falta ID,END del programa principal en la linea: " + lector.getNroLinea());}
                                ;

cuerpo							: cuerpo sentencia
                                | sentencia
                                ;

sentencia						: sentencia_declaracion
                                | sentencia_ejecucion
                                | ETIQUETA
                                ;

sentencia_declaracion			: tipo lista_variables ";" {/*System.out.println($2);*/}
							    | tipo lista_variables {yyerror("ERROR, Falta ; en la sentencia de declaracion en la linea: " + lector.getNroLinea());}
						    	| declaracion_funcion
						    	| TYPEDEF ID ASIGNACION tipo "[" subrango "]" ";" {System.out.println("Declaracion de Subtipo");}
						    	| TYPEDEF ID ASIGNACION tipo "[" subrango ";" {yyerror("ERROR, Falta de ']' en la linea: " + lector.getNroLinea());}
						    	| TYPEDEF ID ASIGNACION tipo subrango  "]" ";" {yyerror("ERROR, Falta de '[' en la linea: " + lector.getNroLinea());}
						    	| TYPEDEF ID ASIGNACION tipo  subrango  ";" {yyerror("ERROR, Falta de llaves '[]' en la linea: " + lector.getNroLinea());}
						    	| TYPEDEF ID ASIGNACION tipo "[" "]" ";" {yyerror("ERROR, Falta de rango en la linea: " + lector.getNroLinea());}
						    	| TYPEDEF ASIGNACION tipo "[" subrango "]" ";" {yyerror("ERROR, Falta nombre del tipo definido en la linea: " + lector.getNroLinea());}
						    	| TYPEDEF ID ASIGNACION "[" subrango "]" ";" {yyerror("ERROR, Falta el tipo base en la linea: " + lector.getNroLinea());}
						    	| TYPEDEF STRUCT "<" lista_tipos ">" "(" lista_variables "," ")" ID ";" {System.out.println("Declaracion de Struct");}
						    	| TYPEDEF STRUCT lista_tipos  "(" lista_variables "," ")" ID ";" {yyerror("ERROR, Falta <> en la linea: " + lector.getNroLinea());}
						    	| TYPEDEF "<" lista_tipos ">" "(" lista_variables "," ")" ID ";" {yyerror("ERROR, Falta la palabra STRUCT en la linea: " + lector.getNroLinea());}
                                | TYPEDEF STRUCT "<" lista_tipos ">"  "(" lista_variables "," ")"  ";" {yyerror("ERROR, Falta ID al final de la declaracion en la linea: " + lector.getNroLinea());}
                                ;

subrango 						: factor "," factor
                                | factor factor {yyerror("ERROR, Falta ',' entre los digitos del subrango en la linea: " + lector.getNroLinea());}
                                ;

declaracion_funcion				: tipo FUN ID "(" parametro ")" BEGIN cuerpo RET "(" expresion ")" ";" END {System.out.println("Declaracion de Funcion");}
                                | tipo FUN ID "(" parametro ")" BEGIN cuerpo END {yyerror("ERROR, Falta sentencia return en la linea: " + lector.getNroLinea());}
                                | FUN ID "(" parametro ")" BEGIN cuerpo RET "(" expresion ")" ";" END {yyerror("ERROR, Falta la declaracion del tipo de la FUN en la linea: " + lector.getNroLinea());}
                                | tipo  ID "(" parametro ")" BEGIN cuerpo RET "(" expresion ")" ";" END {yyerror("ERROR, Falta la declaracion de la palabra reservada FUN en la linea: " + lector.getNroLinea());}
                                | tipo FUN "(" parametro ")" BEGIN cuerpo RET "(" expresion ")" ";" END {yyerror("ERROR, Falta el ID de la funcion en la linea: " + lector.getNroLinea());}
                                | tipo FUN ID parametro  BEGIN cuerpo RET "(" expresion ")" ";" END {yyerror("ERROR, Falta de () a la hora de los parametros en la linea: " + lector.getNroLinea());}
                                | tipo FUN ID "(" parametro ")"  BEGIN cuerpo RET expresion ";" END {yyerror("ERROR, Falta de () a la hora de la expresion en la linea: " + lector.getNroLinea());}
                                | tipo FUN ID "("  ")" BEGIN cuerpo RET "(" expresion ")" ";" END {yyerror("ERROR, Falta de parametros en la FUN en la linea: " + lector.getNroLinea());}
                                | tipo FUN ID "(" parametro ")"  cuerpo RET "(" expresion ")" ";" END {yyerror("ERROR, Falta de BEGIN en la FUN en la linea: " + lector.getNroLinea());}
                                | tipo FUN ID "(" parametro ")" BEGIN cuerpo error {System.out.println("ERROR,Falta de END en la FUN en la linea: " + lector.getNroLinea());}
                                | tipo FUN ID "(" parametro ")" BEGIN  END {yyerror("ERROR, Falsa cuerpo de funcion en la linea: " + lector.getNroLinea());}
                                ;

sentencia_ejecucion				: asignacion
                                | condicion_if
                                | sentencia_print
                                | sentencia_while
                                | invocacion_funcion ";"
						    	| GOTO ETIQUETA ";" {System.out.println("Declaracion de GOTO");}
						    	| GOTO ETIQUETA  {yyerror("ERROR, Falta ';' al final de la declaracion en la linea: " + lector.getNroLinea());}
						    	| GOTO  ";" {yyerror("ERROR, falta la ETIQUETA en la linea: " + lector.getNroLinea());}
						    	//| ETIQUETA ";" {yyerror("ERROR, falta el GOTO en la linea: " + lector.getNroLinea());}
                                | ETIQUETA
                                | CML
							    ;

sentencia_while                 : REPEAT bloque_sentencia_ejecutable WHILE "(" condicion ")" ";" {System.out.println("Declaracion REPEAT-WHILE");}
                                | REPEAT bloque_sentencia_ejecutable  "(" condicion ")" ";" {yyerror("ERROR, falta palabra WHILE en la linea: " + lector.getNroLinea());}
                                | REPEAT bloque_sentencia_ejecutable WHILE "(" condicion ")"  {yyerror("ERROR, falta palabra ';' al final de la declaracion en la linea: " + lector.getNroLinea());}
                                | REPEAT bloque_sentencia_ejecutable WHILE "(" ")" ";" {yyerror("ERROR, falta la condicion del WHILE en la linea: " + lector.getNroLinea());}
                                | REPEAT bloque_sentencia_ejecutable WHILE  condicion ")" ";" {yyerror("ERROR, falta parentesis '(' en la declaracion de la condicion de WHILE en la linea: " + lector.getNroLinea());}
                                | REPEAT bloque_sentencia_ejecutable WHILE "(" condicion ";" {yyerror("ERROR, falta parentesis ')' en la declaracion de la condicion de WHILE en la linea: " + lector.getNroLinea());}
                                | REPEAT bloque_sentencia_ejecutable WHILE  condicion ";" {yyerror("ERROR, falta parentesis en la declaracion de la condicion de WHILE en la linea: " + lector.getNroLinea());}
                                | REPEAT WHILE "(" condicion ")" ";" {yyerror("ERROR, falta el cuerpo de la iteracion repeat en la linea: " + lector.getNroLinea());}
                                ;

sentencia_print					: OUTF "(" CML ")" ";" //{System.out.println($3.sval);}
                                | OUTF "(" expresion ")" ";"
                                | OUTF "(" ")" ";" {yyerror("ERROR, Falta parámetro en sentencia OUTF en la linea: " + lector.getNroLinea());}
                                | OUTF "(" expresion ")" {yyerror("ERROR, Falta ';' en la sentencia OUTF en la linea: " + lector.getNroLinea());}
                                | OUTF "(" CML ")" {yyerror("ERROR, Falta ';' en la sentencia OUTF en la linea: " + lector.getNroLinea());}
                                | OUTF CML ";" {yyerror("ERROR, Faltan los parentesis en la sentencia OUTF en la linea: " + lector.getNroLinea());}
                                | OUTF expresion ";" {yyerror("ERROR, Faltan los parentesis en la sentencia OUTF en la linea: " + lector.getNroLinea());}
                                | OUTF "(" error ")" ";" {yyerror("ERROR, tipo invalido como parametro para la sentencia OUTF en la linea: " + lector.getNroLinea());}
                                ;

parametro						: tipo ID
                            	| tipo {yyerror("ERROR, falta declaracion de TIPO o NOMBRE en el parametro de la linea: " + lector.getNroLinea());}
                                ;

invocacion_funcion          	: ID "(" expresion ")" {if ($1.sval.equals(null)){ yyerror("No existe una funcion con ese nombre en la linea: " + lector.getNroLinea());}}
                            	//| "(" expresion ")" {yyerror("ERROR, falta ID en la invocacion en la linea: " + lector.getNroLinea());}
                            	| ID "(" ")"{yyerror("ERROR, falta parametro en la invocacion de la funcion en la linea: " + lector.getNroLinea());}
                            	;

bloque_sentencia_ejecutable     : BEGIN lista_sentencias END
					  		    ;

lista_sentencias                : lista_sentencias sentencia_ejecucion
                                | sentencia_ejecucion
                                ;

condicion_if 					: IF "(" condicion ")" THEN bloque_sentencia_ejecutable END_IF ";" {System.out.println("Declaracion de IF");}
                                | IF "(" condicion ")" THEN bloque_sentencia_ejecutable ELSE bloque_sentencia_ejecutable END_IF ";"  {System.out.println("Declaracion de IF,ELSE");}
                                | IF "(" condicion ")" bloque_sentencia_ejecutable END_IF ";" {yyerror("ERROR, Falta THEN luego de la condicion en la linea: " + lector.getNroLinea());}
                                | IF "(" ")" THEN bloque_sentencia_ejecutable END_IF ";" {yyerror("ERROR,falta de Condicion en la linea: " + lector.getNroLinea());}
                                | IF "(" condicion ")" THEN error END_IF ";" {yyerror("ERROR,falta el bloque ejecutable en la linea: " + lector.getNroLinea());}
                                | IF "(" condicion ")" THEN bloque_sentencia_ejecutable ";" {yyerror("ERROR,falta END_IF al final de la declaracion en la linea: " + lector.getNroLinea());}
                                | IF "(" condicion ")" THEN bloque_sentencia_ejecutable error {yyerror("ERROR,falta ; al final de la declaracion del bloque IF en la linea: " + lector.getNroLinea());}
                                | IF THEN bloque_sentencia_ejecutable END_IF error {yyerror("ERROR,falta ';' al final de la declaracion en la linea: " + lector.getNroLinea());}
                                | IF "(" condicion ")" THEN bloque_sentencia_ejecutable  bloque_sentencia_ejecutable END_IF ";"  {yyerror("ERROR, falta ELSE luego de la sentencias de ejecucion en la linea: " + lector.getNroLinea());}
                                | IF "(" condicion ")" THEN bloque_sentencia_ejecutable ELSE bloque_sentencia_ejecutable END_IF {yyerror("ERROR,falta ';' al final de la declaracion en la linea: " + lector.getNroLinea());}
                                | IF "(" condicion ")" THEN bloque_sentencia_ejecutable ELSE  END_IF ";" {yyerror("ERROR, falta el bloque ejecutable en el ELSE en la linea: " + lector.getNroLinea());}
                                | IF "(" condicion ")" THEN ELSE bloque_sentencia_ejecutable END_IF ";" {yyerror("ERROR, falta el bloque ejecutable en el IF en la linea: " + lector.getNroLinea());}
                                | IF "(" condicion ")" THEN bloque_sentencia_ejecutable ELSE bloque_sentencia_ejecutable ";" {yyerror("ERROR,falta END_IF al final de la declaracion en la linea: " + lector.getNroLinea());}
                                | IF condicion THEN bloque_sentencia_ejecutable END_IF ";" {yyerror("ERROR,falta de parentesis en la linea: " + lector.getNroLinea());}
                                | IF "(" condicion  THEN bloque_sentencia_ejecutable END_IF ";" {yyerror("ERROR,falta un parentesis ')' en la linea: " + lector.getNroLinea());}
                                | IF  condicion ")" THEN bloque_sentencia_ejecutable END_IF ";" {yyerror("ERROR,falta un parentesis '(' en la linea: " + lector.getNroLinea());}
                                | IF "(" condicion  THEN bloque_sentencia_ejecutable ELSE bloque_sentencia_ejecutable END_IF ";" {yyerror("ERROR,falta un parentesis ')' "); }
                                | IF condicion ")" THEN bloque_sentencia_ejecutable ELSE bloque_sentencia_ejecutable END_IF ";" {yyerror("ERROR,falta un parentesis '(' "); }
                                | IF condicion THEN bloque_sentencia_ejecutable ELSE bloque_sentencia_ejecutable END_IF ";" {yyerror("ERROR,falta un parentesis '()' "); }
                                | IF "(" condicion ")" THEN sentencia_ejecucion END_IF ";"
                                | IF "(" condicion ")" THEN sentencia_ejecucion ELSE bloque_sentencia_ejecutable END_IF ";"  {System.out.println("Declaracion de IF,ELSE");}
                                | IF "(" condicion ")" THEN bloque_sentencia_ejecutable ELSE sentencia_ejecucion END_IF ";"  {System.out.println("Declaracion de IF,ELSE");}
                                | IF "(" condicion ")" THEN sentencia_ejecucion ELSE sentencia_ejecucion END_IF ";"  {System.out.println("Declaracion de IF,ELSE");}
                                %prec LOWER_THAN_ELSE
                                ;

condicion						:  expresion comparador expresion
                                |  expresion error expresion {yyerror("ERROR, falta comparador en comparacion en la linea: " + lector.getNroLinea());}
                                ;

asignacion                      : lista_variables ASIGNACION lista_expresiones ';' {$$ = $3;} /*TODO: verificar que ambos lados tengan la misma cantidad de componentes*/
                                ;

expresion						: expresion "+" termino 		{$$.ival = $1.ival + $3.ival;}
						    	| expresion "-" termino		{$$.ival = $1.ival - $3.ival;}
						    	| expresion "+" "+" termino {yyerror("ERROR, hay 2 operadores en la linea: " + lector.getNroLinea());}
						    	| expresion "-" "+" termino {yyerror("ERROR, hay 2 operadores en la linea: " + lector.getNroLinea());}
						    	//| expresion termino		{yyerror("ERROR, falta de operador en la linea: " + lector.getNroLinea());}
						    	| "+" termino {yyerror("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
						    	| expresion "+" {yyerror("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
						    	| expresion "-" {yyerror("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
						    	| TOD "(" expresion ")" {System.out.println("Declaracion de TOD");}
						    	| TOD "("  ")" {yyerror("ERROR, falta de expresion en la linea: " + lector.getNroLinea());}
						    	| termino {$$ = $1;}
						    	;


termino							: termino "*" factor
                                | termino "/" factor
                                | termino "*" "*" factor {yyerror("ERROR, hay 2 operadores en la linea: " + lector.getNroLinea());}
                                | termino "*" "/" factor {yyerror("ERROR, hay 2 operadores en la linea: " + lector.getNroLinea());}
                                | termino "/" "/" factor {yyerror("ERROR, hay 2 operadores en la linea: " + lector.getNroLinea());}
                                | termino "/" "*" factor {yyerror("ERROR, hay 2 operadores en la linea: " + lector.getNroLinea());}
                                | termino "/" {yyerror("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
                                | termino "*" {yyerror("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
                                | "/" factor {yyerror("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
                                | "*" factor {yyerror("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
                                | factor {$$ = $1;}
                                //| error {yyerror("ERROR, mal escrita la expresion en la linea: " + lector.getNroLinea());}
                                ;


factor							: invocacion_funcion	//{$$ = $1;}
                                |ID	%prec '('  /* Precedencia menor que la de invocación de función */ {$$ = $1;}
                                | id_compuesta
                                | LONGINT 	    {
                                                    $$ = $1;
                                                    Long valor = Long.parseLong($1.sval);
                                                    if (valor == 2147483648L){
                                                        yyerror("ERROR, El número está fuera del rango permitido para un longint positivo en la linea: " + lector.getNroLinea());
                                                    }
                                                    int token =   this.LONGINT;
                                                    lector.tablaSimbolos.addToken(val_peek(0).sval,token,"LONGINT");
                                                }
                                | "-" LONGINT	{
                                                    $$ = $2; //TODO: posible error
                                                    String lexema = '-'+ $2.sval;
                                                    int token =   this.LONGINT;
                                                    lector.tablaSimbolos.addToken(val_peek(0).sval,token,"LONGINT");
                                                }

                                | HEXA		    {
                                                    $$ = $1;
                                                    String hexa = $1.sval;
                                                    if (hexa.startsWith("0x")) {
                                                        hexa = hexa.substring(2);
                                                    }
                                                    long num = Long.parseLong(hexa, 16);
                                                    long maxValorAbsoluto = 2147483648L;
                                                    if (num == maxValorAbsoluto){
                                                        yyerror("ERROR, El número está fuera del rango permitido para un HEXA positivo en la linea: " + lector.getNroLinea());
                                                    }
                                                    int token =   this.HEXA;
                                                    lector.tablaSimbolos.addToken(val_peek(0).sval,token,"HEXA");
                                                }
                                | "-" HEXA	    {
                                                    yyval = val_peek(0);
                                                    String lexema = '-'+ val_peek(0).sval;
                                                    int token =   this.HEXA;
                                                    lector.tablaSimbolos.addToken(val_peek(0).sval,token,"HEXA");
                                                }
                                | DOUBLE 	    {
                                                    $$ = $1 ; // valor del número
                                                    String valor = $1.sval;
                                                    try {
                                                        String numeroStr = valor;
                                                        // Reemplazamos la 'd' por 'E' para que BigDecimal pueda procesarlo correctamente
                                                        if (numeroStr.contains("d") || numeroStr.contains("D")) {
                                                          numeroStr = numeroStr.replace('d', 'E').replace('D', 'E');
                                                        }

                                                        // Convertimos el valor a BigDecimal
                                                        BigDecimal numero = new BigDecimal(numeroStr);
                                                        BigDecimal min = new BigDecimal("2.2250738585072014E-308");
                                                        BigDecimal max = new BigDecimal("1.7976931348623157E+308");

                                                        // Comparamos el número con los límites permitidos
                                                        if ((numero.compareTo(max) > 0) || (numero.compareTo(min) < 0 && numero.compareTo(BigDecimal.ZERO) != 0)){
                                                          yyerror("ERROR, El número está fuera del rango permitido para un double positivo en la linea: " + lector.getNroLinea());
                                                        } else {
                                                          int token = DOUBLE;
                                                          lector.tablaSimbolos.addToken(valor, token, "DOUBLE");  // Añade el token

                                                        }

                                                      } catch (NumberFormatException e) {
                                                        yyerror("Formato de número inválido.");
                                                      }

                                                }
                                |"-" DOUBLE     {
                                                    $$ = $2;
                                                    String valor = $2.sval;
                                                    try {
                                                          String numeroStr = valor;

                                                          // Reemplazamos la 'd' por 'E' para notación científica
                                                          if (numeroStr.contains("d") || numeroStr.contains("D")) {
                                                            numeroStr = numeroStr.replace('d', 'E').replace('D', 'E');
                                                          }

                                                            // Convertimos el valor a BigDecimal y lo negamos
                                                            BigDecimal numero = new BigDecimal(numeroStr).negate();
                                                            BigDecimal min = new BigDecimal("-1.7976931348623157E+308");
                                                            BigDecimal max = new BigDecimal("-2.2250738585072014E-308");

                                                            // Comparamos el número con los límites permitidos
                                                            if (numero.compareTo(max) > 0 || numero.compareTo(min) < 0) {
                                                              yyerror("ERROR, El número está fuera del rango permitido para un double negativo en la linea: " + lector.getNroLinea());
                                                            } else {
                                                              int token = this.DOUBLE;
                                                              String negativo = "-" + valor;
                                                              lector.tablaSimbolos.addToken(negativo, token,"DOUBLE");
                                                            }
                                                          } catch (NumberFormatException e) {
                                                            yyerror("Formato de número inválido.");
                                                          }
                                                }
                                ;

lista_variables					: lista_variables "," ID
                                | lista_variables "," id_compuesta
                                | id_compuesta
                                | ID {/*System.out.println($1.sval);*/}
                                //| lista_variables  ID {yyerror("ERROR, falta ',' en la lista ");}
                                //| lista_variables  id_compuesta {yyerror("ERROR, falta ',' en la lista ");}
                                //| error {yyerror("ERROR, mal escrita la lista de variables en la linea: "+ lector.getNroLinea());}
                                ;

id_compuesta                	: ID "." ID
                                ;

lista_expresiones				: lista_expresiones ',' expresion
                                //| lista_expresiones expresion {yyerror("ERROR, falta de ',' en la lista en la linea: " + lector.getNroLinea());}
                                | expresion
                                //| expresion error ";"
                                ;

lista_tipos						: lista_tipos "," tipo
                                | lista_tipos tipo  {yyerror("ERROR, falta de ',' en la lista en la linea: " + lector.getNroLinea());}
                                | tipo
                                ;

tipo		 					: DOUBLE
                                | LONGINT
                                | ID    %prec ID
                                ;

comparador 					    : "<"
                                | ">"
                                | MAYOR_IGUAL
                                | MENOR_IGUAL
                                | DISTINTO
                                ;
%%
void yyerror(String mensaje) {
  String ANSI_RESET = "\u001B[0m";
  String ANSI_RED = "\u001B[31m";
  // funcion utilizada para imprimir errores que produce yacc
  System.out.println(ANSI_RED + mensaje + ANSI_RESET);

}

AnalizadorLexico lector;

int yylex(){
    return lector.yylex();
}
