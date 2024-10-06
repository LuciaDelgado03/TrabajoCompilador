%{


//import accion_semantica.AccionSemantica;

import java.io.*;
%}
        //declaracion de tokens a recibir del Analizador Lexico
%token IF THEN ELSE BEGIN END END_IF OUTF TYPEDEF FUN RET STRING REPEAT WHILE GOTO ID LONGINT HEXA CML DOUBLE TOD STRUCT ASIGNACION DISTINTO MENOR_IGUAL MAYOR_IGUAL OUTF ETIQUETA

%left '+' '-'
%left '*' '/'
%left ASIGNACION
%nonassoc LOWER_THAN_ELSE
%nonassoc ELSE
%start prog

%%
prog						: ID BEGIN cuerpo END {System.out.println($1);}
							| ID cuerpo END {System.out.println("ERROR,falta begin programa principal");}//TODO: notificar error FALTA BEGIN
                            | BEGIN END {System.out.println("ERROR,falta el ID del programa principal");} /* TODO: Notificar falta ID Y CUERPO*/
                            | ID BEGIN cuerpo {System.out.println("ERROR,falta END del programa principal");} /*TODO: falta end*/
                            | ID cuerpo {System.out.println("ERROR,falta BEGIN,END del programa principal");} /*TODO: falta begin y end*/
                            | BEGIN cuerpo  {System.out.println("ERROR,falta ID,END del programa principal");}
                            ;

cuerpo						: cuerpo sentencia
							| sentencia
							;

sentencia					: sentencia_declaracion
							| sentencia_ejecucion
							| ETIQUETA
							;

sentencia_declaracion		: tipo lista_variables ";" {System.out.println($2);}
							| tipo lista_variables {System.out.println("ERROR, Falta ; en la sentencia de declaracion");}
                            | declaracion_funcion
                            | TYPEDEF ID ASIGNACION tipo "{" subrango "}" ";" {System.out.println("Declaracion de Subtipo");}
                            | TYPEDEF STRUCT "<" lista_tipos ">" "{" lista_variables "}" ID ";" {System.out.println("Declaracion de Struct");}
                            | TYPEDEF STRUCT lista_tipos  "{" lista_variables "}" ID ";" {System.out.println("ERROR,Falta STRUCT. Falta <>.");}
                            | TYPEDEF STRUCT "<" lista_tipos ">"  "{" lista_variables "}"  ";" {System.out.println("ERROR,Falta  ID al final de la declaracion");}
                            ;

subrango 					: DIGITO "," DIGITO	/*TODO: CHEQUERA RANGO VALIDO V1< V2*/
							| DIGITO DIGITO {System.out.println("ERROR,Falta ','entre los digitos del subrango");}
							| DOUBLE "," DOUBLE	/*TODO: A.S CHEQUEAR VALOR CON TIPO*/
							;

declaracion_funcion			: tipo FUN ID "(" parametro ")" BEGIN cuerpo_funcion END {System.out.println("Declaracion de Funcion");}
                            | FUN ID "(" parametro ")" BEGIN cuerpo_funcion END {System.out.println("ERROR,Falta la declaracion del tipo de la FUN ");}
                            | tipo  ID "(" parametro ")" BEGIN cuerpo_funcion END {System.out.println("ERROR,Falta la declaracion de la palabra reservada FUN");}
                            | tipo FUN "(" parametro ")" BEGIN cuerpo_funcion END {System.out.println("ERROR,Falta el ID de la funcion");}
                            | tipo FUN ID parametro  BEGIN cuerpo_funcion END {System.out.println("ERROR,Falta de () a la hora de los parametros");}
                            | tipo FUN ID "("  ")" BEGIN cuerpo_funcion END {System.out.println("ERROR,Falta de parametros en la FUN");}
                            | tipo FUN ID "(" parametro ")"  cuerpo_funcion END {System.out.println("ERROR,Falta de BEGIN en la FUN");}
                            /*| tipo FUN ID "(" parametro ")" BEGIN cuerpo_funcion {System.out.println("ERROR,Falta de END en la FUN");} ESTA GENERA CONFLICTO*/
                            | tipo FUN ID "(" parametro ")" BEGIN  END {System.out.println("ERROR,Falsa cuerpo de funcion ");}
                            ;

sentencia_ejecucion			: asignacion
					 		| condicion_if
							| sentencia_print
							| REPEAT bloque_sentencia_ejecutable WHILE "(" condicion ")" ";" {System.out.println("Declaracion REPEAT-WHILE");}
                            /*| bloque_sentencia_ejecutable WHILE "(" condicion ")" ";"  {System.out.println("ERROR,falta palabra REPEAT");} ESTA GENERA CONFLICTO  */
                            | REPEAT bloque_sentencia_ejecutable  "(" condicion ")" ";" {System.out.println("ERROR,falta palabra WHILE");}
                            | REPEAT bloque_sentencia_ejecutable WHILE "(" condicion ")"  {System.out.println("ERROR,falta palabra ';' al final de la declaracion ");}
                            | REPEAT bloque_sentencia_ejecutable WHILE "(" ")" ";" {System.out.println("ERROR,falta falta la condicion del WHILE ");}
                            | REPEAT bloque_sentencia_ejecutable WHILE condicion ";" {System.out.println("ERROR,falta '()' en la declaracion de la condicion ");}
                            | GOTO ETIQUETA ";" {System.out.println("Declaracion de GOTO ");}
                            | GOTO ETIQUETA  {System.out.println("ERROR, Falta ';' al final de la declaracion  ");}
                            | GOTO  ";" {System.out.println("ERROR,falta la ETIQUETA  ");}
                            | ETIQUETA ";" {System.out.println("ERROR,falta el GOTO  ");}
							;

sentencia_print				: OUTF CML ";" {System.out.println($2);}
							| OUTF "(" expresion ")" ";"
							;

cuerpo_funcion              : cuerpo_funcion sentencia RET "(" expresion ")" ";" {System.out.println("Declaracion del Cuerpo de la funcion");} //CHUSMEAR MAÑANA
                            | cuerpo_funcion RET "(" expresion ")" ";" {System.out.println("Declaracion del Cuerpo de la funcion");}
                            | RET "(" expresion ")" ";"
                            | RET "(" expresion ")" {System.out.println("ERROR, falta ';' al final de la declaracion");}
                            | "(" expresion ")" ";" {System.out.println("ERROR, falta RET de la funcion");}
                            | RET expresion ";"	{System.out.println("ERROR, falta '()' a la hora de realizar el RET");}
                            | sentencia
                            ;

parametro					: tipo ID
                            /*| ID {System.out.println("ERROR, falta declaracion de TIPO");}*/
							| tipo {System.out.println("ERROR, falta ID del parametro");}
							;
							;

/*expresion_aritmetica					: invocacion_funcion
                            			| expresion   esta se va probablemente*/

invocacion_funcion          : ID "(" expresion ")"  {if ($1.sval.equals(null)){ System.out.println("No existe una funcion con ese nombre");}}
                            /*| "(" expresion ")" {System.out.println("ERROR, falta ID en la invocacion");}  SHIFT REDUCE CONFLICT*/
                            ;

bloque_sentencia_ejecutable 				: BEGIN bloque_sentencia_ejecutable sentencia_ejecucion END
					  		| BEGIN sentencia_ejecucion END
					  		;

condicion_if 						: IF condicion THEN bloque_sentencia_ejecutable END_IF ";" /*{System.out.println("Declaracion de IF");}*/
                                    | IF condicion THEN bloque_sentencia_ejecutable ELSE bloque_sentencia_ejecutable END_IF ";"  /*{System.out.println(Declaracion de IF,ELSE );}*/
                                    | IF condicion bloque_sentencia_ejecutable END_IF ";" {System.out.println("ERROR, Falta THEN luego de la condicion");}
                                    | IF THEN bloque_sentencia_ejecutable END_IF ";" {System.out.println("ERROR,falta de Condicion");}
                                    | IF THEN END_IF ";" {System.out.println("ERROR,falta el bloque ejecutable");}
                                    | IF THEN bloque_sentencia_ejecutable ";" {System.out.println("ERROR,falta END_IF al final de la declaracion");}
                                    | IF THEN bloque_sentencia_ejecutable END_IF  {System.out.println("ERROR,falta ';' al final de la declaracion");}
                                    | IF condicion THEN bloque_sentencia_ejecutable  bloque_sentencia_ejecutable END_IF ";"  {System.out.println("ERROR, falta ELSE luego de la sentencias de ejecucion");}
                                    | IF condicion THEN bloque_sentencia_ejecutable ELSE bloque_sentencia_ejecutable END_IF {System.out.println("ERROR,falta ';' al final de la declaracion");}
                                    | IF condicion THEN bloque_sentencia_ejecutable ELSE  END_IF ";" {System.out.println("ERROR, falta el bloque ejecutable en el ELSE");}
                                    | IF condicion THEN ELSE bloque_sentencia_ejecutable END_IF ";" {System.out.println("ERROR, falta el bloque ejecutable en el IF");}
                                    | IF condicion THEN bloque_sentencia_ejecutable ELSE bloque_sentencia_ejecutable ";" {System.out.println("ERROR,falta END_IF al final de la declaracion");}
                                    %prec LOWER_THAN_ELSE;

condicion						: "(" expresion comparador expresion ")"
							;

asignacion : lista_variables ASIGNACION lista_expresiones ';'{$1.ival = $2.ival;} /*TODO: verificar que ambos lados tengan la misma cantidad de componentes*/

expresion					: expresion "+" termino		{$$ = $1.ival + $3.ival;}
                            | expresion "-" termino		/*{$$ = $1 - $3;}*/
                            /*| expresion "+" "-" termino {System.out.println("ERROR, hay 2 operandos");} REDUCE REDUCE CONFLICT*/
                            | TOD "(" expresion ")" {System.out.println("Declaracion de TOD");}
                            /*| "(" expresion ")" {System.out.println("ERROR, falta declaracion de TOD");} SHIFT REDUCE*/
                            | termino
                            ;

termino						: termino "*" factor	/*{$$ = $1 * $3;}*/
							| termino "/" factor	/*{$$ = $1 / $3;}*/
							| factor
							;

factor						: ID	{$$ = $1;
                                    System.out.println("la variable" + $1.sval + "tiene valor: " + $1.ival);}
                            | DIGITO {$$ = $1;
                                      System.out.println("la variable" + $1.sval + "tiene valor: " + $1.ival);
                            /*
                                      ParserVal valor = val_peek(1);
                                      System.out.println("llegue para LOGNINT positivo" + valor.lval);
                                      Long numero = valor.lval;
                                      if (numero > 2147483647) {
                                        yyerror("El número está fuera del rango permitido para un longint positivo.");
                                      }
                                      else{
                                        valor.ival = numero.intValue();
                                        lector.tablaSimbolos.addToken(numero.toString(),272);
                                      }
                                     */}
                            | "-" DIGITO	{
                                              ParserVal valor = val_peek(1);
                                              System.out.println("llegue para LONGINT negativo " + valor.lval);
                                              Long numero = valor.lval;
                                              numero = -numero;
                                              int max = -2147483648;
                                              if (numero < -2147483648) {
                                                yyerror("El número está fuera del rango permitido para un longint negativo.");
                                              } else{
                                                valor.ival = numero.intValue();
                                                lector.tablaSimbolos.addToken(numero.toString(),272);
                                              }
                                             }

                            | HEXA	{
                                      ParserVal valor = val_peek(1);
                                      System.out.println("llegue para LOGNINT positivo" + valor.lval);
                                      Long numero = valor.lval;
                                      if (numero > 0x7FFFFFFF) {
                                        yyerror("El número está fuera del rango permitido para un HEXA positivo.");
                                      }
                                      else{
                                        valor.ival = numero.intValue();
                                        lector.tablaSimbolos.addToken(numero.toString(),273);
                                      }
                                                                            }
                            | "-" HEXA	{
                                          ParserVal valor = val_peek(1);
                                          System.out.println("llegue para LONGINT negativo " + valor.lval);
                                          Long numero = valor.lval;
                                          numero = -numero;
                                          int max = -2147483648;
                                          if (numero < -2147483648) {
                                            yyerror("El número está fuera del rango permitido para un longint negativo.");
                                          } else{
                                            valor.ival = numero.intValue();
                                            lector.tablaSimbolos.addToken(numero.toString(),273);
                                          }
                                     }
                            | invocacion_funcion	/*{$$ = $1;}*/
                            | DOUBLE {
                                          $$ = $1 ; // valor del número
                                          String valor = $1.sval;
                                          System.out.println("Llegué para DOUBLE positivo: " + valor);
                                          String valorConvertido = valor.replace("d", "E");
                                          System.out.println("Llegué para DOUBLE positivo Replace: " + valorConvertido);

                                          try {
                                               // Convertir la cadena a un tipo double para evitar problemas de formato con BigDecimal.
                                               double numero = Double.parseDouble(valorConvertido);
                                               double min = 2.2250738585072014E-308;
                                               double max = 1.7976931348623157E+308;

                                               // Comparamos el número contra los límites permitidos.
                                               if (numero > max || (numero < min && numero != 0.0)) {
                                                   yyerror("El número está fuera del rango permitido para un double positivo.");
                                               } else {
                                                   String valorC = valor.replace("E", "d");
                                                   double numeroC = -Double.parseDouble(valorC);
                                                   int token = this.DOUBLE;
                                                   lector.tablaSimbolos.addToken(Double.toString(numeroC), token, "DOUBLE");
                                               }
                                          } catch (NumberFormatException e) {
                                               // Control de error en caso de que la conversión a double falle.
                                               yyerror("Formato de número inválido: " + valorConvertido);
                                          }
                                      }
                            | "-" DOUBLE {
                                            $$ = $2;
                                            String valor = $2.sval;
                                            System.out.println("llegue para DOUBLE negativo: " + valor);
                                            String valorConvertido = valor.replace("d", "E");

                                            try {
                                                // Convertimos el valor a double y lo negamos.
                                                double numero = -Double.parseDouble(valorConvertido); // Negamos el valor.
                                                double min = -1.7976931348623157E+308;
                                                double max = -2.2250738585072014E-308;

                                                // Comparamos el número contra los límites permitidos.
                                                if (numero > max || numero < min) {
                                                    yyerror("El número está fuera del rango permitido para un double negativo.");
                                                } else {
                                                    String valorC = valor.replace("E", "d");
                                                    double numeroC = -Double.parseDouble(valorC);
                                                    int token = this.DOUBLE;
                                                    lector.tablaSimbolos.addToken(Double.toString(numeroC), token, "DOUBLE");

                                                }
                                            } catch (NumberFormatException e) {

                                                     // Control de error en caso de que la conversión a double falle.
                                                     yyerror("Formato de número inválido: " + valorConvertido);
                                            }

                                          }
                            ;

lista_variables				: lista_variables "," ID
							| lista_variables "," ID "." ID
							| ID "." ID
							| ID {System.out.println($1);}
							;

lista_expresiones					: lista_expresiones "," expresion
							| expresion
							;

lista_tipos						: lista_tipos "," tipo
							| tipo
							;

tipo		 					: LONGINT
							| DOUBLE
							| ID  /*TODO: verificar que el use un tipo creado*/
							;

comparador 					 	: "<"
							| ">"
							| "MAYOR_IGUAL"
							| "MENOR_IGUAL"
							| "DISTINTO"
							;
%%


void yyerror(String mensaje) {
  // funcion utilizada para imprimir errores que produce yacc
  System.out.println("Error yacc: " + mensaje);

AnalizadorLexico lex ;
}