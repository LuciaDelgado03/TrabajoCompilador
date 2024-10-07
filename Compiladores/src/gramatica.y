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
prog							: ID BEGIN cuerpo END {System.out.println($1);}
							    | ID cuerpo END {System.out.println("ERROR,falta begin programa principal en la linea: " + lector.getNroLinea());}
                                | BEGIN END {System.out.println("ERROR,falta el ID del programa principal en la linea: " + lector.getNroLinea());}
                                | ID BEGIN cuerpo {System.out.println("ERROR,falta END del programa principal en la linea: " + lector.getNroLinea());}
                                | ID cuerpo {System.out.println("ERROR,falta BEGIN,END del programa principal en la linea: " + lector.getNroLinea());}
                                | BEGIN cuerpo  {System.out.println("ERROR,falta ID,END del programa principal en la linea: " + lector.getNroLinea());}
                                ;

cuerpo							: cuerpo sentencia
                                | sentencia
                                ;

sentencia						: sentencia_declaracion
                                | sentencia_ejecucion
                                | ETIQUETA
                                ;

sentencia_declaracion			: tipo lista_variables ";" {System.out.println($2);}
							    | tipo lista_variables {System.out.println("ERROR, Falta ; en la sentencia de declaracion en la linea: " + lector.getNroLinea());}
						    	| declaracion_funcion
						    	| TYPEDEF ID ASIGNACION tipo "(" subrango ")" ";" {System.out.println("Declaracion de Subtipo");}
						    	| TYPEDEF ID ASIGNACION tipo "(" subrango ";" {System.out.println("Error, Falta de parentesis en la linea: " + lector.getNroLinea());}
						    	| TYPEDEF ID ASIGNACION tipo "" subrango  ")" ";" {System.out.println("Error, Falta de parentesis en la linea: " + lector.getNroLinea());}
						    	| TYPEDEF ID ASIGNACION tipo  subrango  ";" {System.out.println("Error, Falta de llaves parentesis en la linea: " + lector.getNroLinea());}
						    	| TYPEDEF ID ASIGNACION tipo "(" ")" ";" {System.out.println("ERROR, Falta de rango en la linea: " + lector.getNroLinea());}
						    	| TYPEDEF ASIGNACION tipo "(" subrango ")" ";" {System.out.println("ERROR, Falta nombre del tipo definido en la linea: " + lector.getNroLinea());}
						    	| TYPEDEF ID ASIGNACION "(" subrango ")" ";" {System.out.println("ERROR, Falta el tipo base en la linea: " + lector.getNroLinea());}
						    	| TYPEDEF STRUCT "<" lista_tipos ">" "(" lista_variables ")" ID ";" {System.out.println("Declaracion de Struct");}
						    	| TYPEDEF STRUCT lista_tipos  "(" lista_variables ")" ID ";" {System.out.println("ERROR, Falta <> en la linea: " + lector.getNroLinea());}
						    	| TYPEDEF "<" lista_tipos ">" "(" lista_variables ")" ID ";" {System.out.println("ERROR, Falta la palabra STRUCT en la linea: " + lector.getNroLinea());}
                                | TYPEDEF STRUCT "<" lista_tipos ">"  "(" lista_variables ")"  ";" {System.out.println("ERROR,Falta  ID al final de la declaracion en la linea: " + lector.getNroLinea());}
                                ;

subrango 						: LONGINT "," LONGINT	/*TODO: CHEQUERA RANGO VALIDO V1< V2*/
                                | LONGINT LONGINT {System.out.println("ERROR,Falta ','entre los digitos del subrango en la linea: " + lector.getNroLinea());}
                                | DOUBLE "," DOUBLE	/*TODO: A.S CHEQUEAR VALOR CON TIPO*/
                                | DOUBLE DOUBLE {System.out.println("ERROR,Falta ','entre los digitos del subrang en la linea: " + lector.getNroLinea());}
                                | HEXA "," HEXA
                                | HEXA HEXA {System.out.println("ERROR,Falta ','entre los digitos del subrango en la linea: " + lector.getNroLinea());}
                                ;

declaracion_funcion				: tipo FUN ID "(" parametro ")" BEGIN cuerpo RET "(" expresion ")" ";" END
                                | tipo FUN ID "(" parametro ")" BEGIN cuerpo END {System.out.println("Falta sentencia return en la linea: " + lector.getNroLinea());}
                                | FUN ID "(" parametro ")" BEGIN cuerpo RET "(" expresion ")" ";" END {System.out.println("ERROR,Falta la declaracion del tipo de la FUN en la linea: " + lector.getNroLinea());}
                                | tipo  ID "(" parametro ")" BEGIN cuerpo RET "(" expresion ")" ";" END {System.out.println("ERROR,Falta la declaracion de la palabra reservada FUN en la linea: " + lector.getNroLinea());}
                                | tipo FUN "(" parametro ")" BEGIN cuerpo RET "(" expresion ")" ";" END {System.out.println("ERROR,Falta el ID de la funcion en la linea: " + lector.getNroLinea());}
                                | tipo FUN ID parametro  BEGIN cuerpo RET "(" expresion ")" ";" END {System.out.println("ERROR,Falta de () a la hora de los parametros en la linea: " + lector.getNroLinea());}
                                | tipo FUN ID "("  ")" BEGIN cuerpo RET "(" expresion ")" ";" END {System.out.println("ERROR,Falta de parametros en la FUN en la linea: " + lector.getNroLinea());}
                                | tipo FUN ID "(" parametro ")"  cuerpo RET "(" expresion ")" ";" END {System.out.println("ERROR,Falta de BEGIN en la FUN en la linea: " + lector.getNroLinea());}
                                //| tipo FUN ID "(" parametro ")" BEGIN cuerpo error {System.out.println("ERROR,Falta de END en la FUN en la linea: " + lector.getNroLinea());}
                                | tipo FUN ID "(" parametro ")" BEGIN  END {System.out.println("ERROR,Falsa cuerpo de funcion en la linea: " + lector.getNroLinea());}
                                ;

sentencia_ejecucion				: asignacion
                                | condicion_if
                                | sentencia_print
                                | sentencia_while
						    	| GOTO ETIQUETA ";" {System.out.println("Declaracion de GOTO");}
						    	| GOTO ETIQUETA  {System.out.println("ERROR, Falta ';' al final de la declaracion en la linea: " + lector.getNroLinea());}
						    	| GOTO  ";" {System.out.println("ERROR,falta la ETIQUETA en la linea: " + lector.getNroLinea());}
						    	| ETIQUETA ";" {System.out.println("ERROR,falta el GOTO en la linea: " + lector.getNroLinea());}
							    ;

sentencia_while                 : REPEAT bloque_sentencia_ejecutable WHILE "(" condicion ")" ";" {System.out.println("Declaracion REPEAT-WHILE");}
                                //| bloque_sentencia_ejecutable WHILE "(" condicion ")" ";"  {System.out.println("ERROR,falta palabra REPEAT en la linea: " + lector.getNroLinea());}
                                | REPEAT bloque_sentencia_ejecutable  "(" condicion ")" ";" {System.out.println("ERROR,falta palabra WHILE en la linea: " + lector.getNroLinea());}
                                | REPEAT bloque_sentencia_ejecutable WHILE "(" condicion ")"  {System.out.println("ERROR,falta palabra ';' al final de la declaracion en la linea: " + lector.getNroLinea());}
                                | REPEAT bloque_sentencia_ejecutable WHILE "(" ")" ";" {System.out.println("ERROR,falta falta la condicion del WHILE en la linea: " + lector.getNroLinea());}
                                | REPEAT bloque_sentencia_ejecutable WHILE  condicion ")" ";" {System.out.println("ERRROR, falta parentesis '(' en la declaracion de la condicion de  WHILE en la linea: " + lector.getNroLinea());}
                                | REPEAT bloque_sentencia_ejecutable WHILE "(" condicion ";" {System.out.println("ERRROR, falta parentesis ')' en la declaracion de la condicion de  WHILE en la linea: " + lector.getNroLinea());}
                                | REPEAT bloque_sentencia_ejecutable WHILE  condicion ";" {System.out.println("ERRROR, falta parentesis  en la declaracion de la condicion de  WHILE en la linea: " + lector.getNroLinea());}
                                | REPEAT WHILE "(" condicion ")" ";" {System.out.println("ERRROR, falta el cuerpo de la iteracion repeat en la linea: " + lector.getNroLinea());}
                                ;

sentencia_print					: OUTF "(" CML ")" ";" {System.out.println($3.sval);}
                                | OUTF "(" expresion ")" ";"
                                | OUTF "(" ")" ";"      {System.out.println("ERROR, Falta parámetro en sentencia OUTF en la linea: " + lector.getNroLinea());}
                                | OUTF "(" expresion ")" {System.out.println("ERROR, Falta ';' en la sentencia OUTF en la linea: " + lector.getNroLinea());}
                                | OUTF "(" CML ")" {System.out.println("ERROR, Falta ';' en la sentencia OUTF en la linea: " + lector.getNroLinea());}
                                | OUTF CML ";" {System.out.println("ERROR, Faltan los parentesis en la sentencia OUTF en la linea: " + lector.getNroLinea());}
                                | OUTF expresion ";"{System.out.println("ERROR, Faltan los parentesis en la sentencia OUTF en la linea: " + lector.getNroLinea());}
                                | OUTF "(" error ")" ";" {System.out.println("ERROR, tipo invalido como parametro para la sentencia OUTF en la linea: " + lector.getNroLinea());}
                                ;






parametro						: tipo ID
                            	| ID {System.out.println("ERROR, falta declaracion de TIPO en la linea: " + lector.getNroLinea());}
                                //| tipo {System.out.println("ERROR, falta ID del parametro en la linea: " + lector.getNroLinea());}
                                ;


invocacion_funcion          	: ID "(" expresion ")"  {if ($1.sval.equals(null)){ System.out.println("No existe una funcion con ese nombre en la linea: " + lector.getNroLinea());}}
                            	//| "(" expresion ")" {System.out.println("ERROR, falta ID en la invocacion en la linea: " + lector.getNroLinea());} //TODO: algunos casos este choca con el de cuerpo_funcion
                            	| ID "(" ")" {System.out.println("ERROR, falta parametro en la invocacion de la funcion en la linea: " + lector.getNroLinea());}
                            	;

bloque_sentencia_ejecutable     : BEGIN lista_sentencias END
					  		    ;

lista_sentencias                : lista_sentencias sentencia_ejecucion
                                | sentencia_ejecucion
                                ;

condicion_if 					: IF "(" condicion ")" THEN bloque_sentencia_ejecutable END_IF ";" {System.out.println("Declaracion de IF");}
                                | IF "(" condicion ")" THEN bloque_sentencia_ejecutable ELSE bloque_sentencia_ejecutable END_IF ";"  {System.out.println("Declaracion de IF,ELSE");}
                                | IF "(" condicion ")" bloque_sentencia_ejecutable END_IF ";" {System.out.println("ERROR, Falta THEN luego de la condicion en la linea: " + lector.getNroLinea());}
                                | IF "(" ")"THEN bloque_sentencia_ejecutable END_IF ";" {System.out.println("ERROR,falta de Condicion en la linea: " + lector.getNroLinea());}
                                | IF "(" condicion ")" THEN error END_IF ";" {System.out.println("ERROR,falta el bloque ejecutable en la linea: " + lector.getNroLinea());}
                                | IF "(" condicion ")" THEN bloque_sentencia_ejecutable ";" {System.out.println("ERROR,falta END_IF al final de la declaracion en la linea: " + lector.getNroLinea());}
                                | IF "(" condicion ")" THEN bloque_sentencia_ejecutable error {System.out.println("ERROR,falta END_IF; al final de la declaracion en la linea: " + lector.getNroLinea());}
                                | IF THEN bloque_sentencia_ejecutable END_IF  {System.out.println("ERROR,falta ';' al final de la declaracion en la linea: " + lector.getNroLinea());}
                                | IF "(" condicion ")" THEN bloque_sentencia_ejecutable  bloque_sentencia_ejecutable END_IF ";"  {System.out.println("ERROR, falta ELSE luego de la sentencias de ejecucion en la linea: " + lector.getNroLinea());}
                                | IF "(" condicion ")" THEN bloque_sentencia_ejecutable ELSE bloque_sentencia_ejecutable END_IF {System.out.println("ERROR,falta ';' al final de la declaracion en la linea: " + lector.getNroLinea());}
                                | IF "(" condicion ")" THEN bloque_sentencia_ejecutable ELSE  END_IF ";" {System.out.println("ERROR, falta el bloque ejecutable en el ELSE en la linea: " + lector.getNroLinea());}
                                | IF "(" condicion ")" THEN ELSE bloque_sentencia_ejecutable END_IF ";" {System.out.println("ERROR, falta el bloque ejecutable en el IF en la linea: " + lector.getNroLinea());}
                                | IF "(" condicion ")" THEN bloque_sentencia_ejecutable ELSE bloque_sentencia_ejecutable ";" {System.out.println("ERROR,falta END_IF al final de la declaracion en la linea: " + lector.getNroLinea());}
                                | IF condicion THEN bloque_sentencia_ejecutable END_IF ";" {System.out.println("ERROR,falta de parentesis en la linea: " + lector.getNroLinea());}
                                | IF "(" condicion  THEN bloque_sentencia_ejecutable END_IF ";" {System.out.println("ERROR,falta un parentesis ')' en la linea: " + lector.getNroLinea());}
                                | IF  condicion ")" THEN bloque_sentencia_ejecutable END_IF ";" {System.out.println("ERROR,falta un parentesis '(' en la linea: " + lector.getNroLinea());}
                                | IF "(" condicion  THEN bloque_sentencia_ejecutable ELSE bloque_sentencia_ejecutable END_IF ";" {System.out.println("ERROR,falta un parentesis ')' ");}
                                | IF condicion ")" THEN bloque_sentencia_ejecutable ELSE bloque_sentencia_ejecutable END_IF ";" {System.out.println("ERROR,falta un parentesis '(' ");}
                                | IF condicion THEN bloque_sentencia_ejecutable ELSE bloque_sentencia_ejecutable END_IF ";" {System.out.println("ERROR,falta un parentesis '()' ");}
                                | IF "(" condicion ")" THEN sentencia_ejecucion END_IF ";"
                                | IF "(" condicion ")" THEN sentencia_ejecucion ELSE bloque_sentencia_ejecutable END_IF ";"  {System.out.println("Declaracion de IF,ELSE");}
                                | IF "(" condicion ")" THEN bloque_sentencia_ejecutable ELSE sentencia_ejecucion END_IF ";"  {System.out.println("Declaracion de IF,ELSE");}
                                | IF "(" condicion ")" THEN sentencia_ejecucion ELSE sentencia_ejecucion END_IF ";"  {System.out.println("Declaracion de IF,ELSE");}
                                %prec LOWER_THAN_ELSE
                                ;

condicion						:  expresion comparador expresion
                                |  lista_expresiones {System.out.println("ERROR, falta comparador en comparacion en la linea: " + lector.getNroLinea());}
                                ;

asignacion : 					lista_variables ASIGNACION lista_expresiones ";"{$$= $3;} /*TODO: verificar que ambos lados tengan la misma cantidad de componentes*/


expresion						: expresion "+" termino		{$$.ival = $1.ival + $3.ival;}
						    	| expresion "-" termino		{$$.ival = $1.ival - $3.ival;}
						    	| expresion "+" "+" termino {System.out.println("ERROR, hay 2 operadores en la linea: " + lector.getNroLinea());}
						    	| expresion "-" "+" termino {System.out.println("ERROR, hay 2 operadores en la linea: " + lector.getNroLinea());}
						    	//| expresion error termino		{System.out.println("ERROR, falta de operador en la linea: " + lector.getNroLinea());}
						    	| "+" termino {System.out.println("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
						    	| expresion "+" {System.out.println("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
						    	| expresion "-" {System.out.println("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
						    	| TOD "(" expresion ")" {System.out.println("Declaracion de TOD");}
						    	| TOD "("  ")" {System.out.println("ERROR, falta de expresion en la linea: " + lector.getNroLinea());}
						    	| termino {$$.ival = $1.ival;}
						    	;


termino							: termino "*" factor
                                | termino "/" factor
                                | termino "*" "*" factor {System.out.println("ERROR, hay 2 operadores en la linea: " + lector.getNroLinea());}
                                | termino "*" "/" factor {System.out.println("ERROR, hay 2 operadores en la linea: " + lector.getNroLinea());}
                                | termino "/" "/" factor {System.out.println("ERROR, hay 2 operadores en la linea: " + lector.getNroLinea());}
                                | termino "/" "*" factor {System.out.println("ERROR, hay 2 operadores en la linea: " + lector.getNroLinea());}
                                | termino "/" {System.out.println("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
                                | termino "*" {System.out.println("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
                                | "/" factor {System.out.println("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
                                | "*" factor {System.out.println("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
                                | factor {$$= $1;}
                                //| termino error factor	{System.out.println("ERROR, falta operador en la linea: " + lector.getNroLinea());}
                                ;


factor							: invocacion_funcion	//{$$ = $1;}
                                |ID	%prec '('  /* Precedencia menor que la de invocación de función */ {$$ = $1;}
                                | id_compuesta
                                | LONGINT 	{
                                                    $$ = $1;
                                                    Long valor = Long.parseLong($1.sval);
                                                    if (valor == 2147483648L){
                                                        yyerror("El número está fuera del rango permitido para un longint positivo en la linea: " + lector.getNroLinea());
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
                                                        yyerror("El número está fuera del rango permitido para un HEXA positivo en la linea: " + lector.getNroLinea());
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
                                                          yyerror("El número está fuera del rango permitido para un double positivo en la linea: " + lector.getNroLinea());
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
                                                    System.out.println("llegue para DOUBLE negativo: " + valor);
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
                                                              yyerror("El número está fuera del rango permitido para un double negativo en la linea: " + lector.getNroLinea());
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
                                | ID {System.out.println($1.sval);}
                                | lista_variables error ID {System.out.println("ERROR, falta ',' en la lista ");}
                                | lista_variables error id_compuesta {System.out.println("ERROR, falta ',' en la lista ");}
                                ;

id_compuesta                	: ID "." ID
                                ;


lista_expresiones				: lista_expresiones ',' expresion
                                //| lista_expresiones  expresion {System.out.println("ERROR, falta de ',' en la lista en la linea: " + lector.getNroLinea());}
                                | expresion
                                ;

lista_tipos						: lista_tipos "," tipo
                                | lista_tipos tipo  {System.out.println("ERROR, falta de ',' en la lista en la linea: " + lector.getNroLinea());}
                                | tipo
                                ;

tipo		 					: DOUBLE
                                | LONGINT
                                | HEXA
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
  // funcion utilizada para imprimir errores que produce yacc
  System.out.println("Error yacc: " + mensaje);

}
