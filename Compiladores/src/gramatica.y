%{


//import accion_semantica.AccionSemantica;

import java.io.*;
%}
        //declaracion de tokens a recibir del Analizador Lexico
%token IF THEN ELSE BEGIN END END_IF OUTF TYPEDEF FUN RET STRING REPEAT WHILE GOTO ID DIGITO HEXA CML DOUBLE LONGINT TOD STRUCT ASIGNACION DISTINTO MENOR_IGUAL MAYOR_IGUAL OUTF ETIQUETA

%left '+' '-'
%left '*' '/'
%left ASIGNACION
%nonassoc LOWER_THAN_ELSE
%nonassoc ELSE
%start prog

%%
prog							: ID BEGIN cuerpo END
							| ID cuerpo END /*{agregarError("falta un begin");}*/ //TODO: notificar error FALTA BEGIN
							/*| BEGIN END {} /* TODO: Notificar falta ID Y CUERPO*/
							/*| ID BEGIN cuerpo {} /*TODO: falta end*/
							/*| ID cuerpo {} /*TODO: falta begin y end*/
							/*| BEGIN cuerpo  {}*/
							;

cuerpo							: cuerpo sentencia
							| sentencia
							;

sentencia					: sentencia_declaracion
							| sentencia_ejecucion
							| ETIQUETA
							;

sentencia_declaracion		: tipo lista_variables ";"
							| declaracion_funcion
							| TYPEDEF ID ASIGNACION tipo "{" subrango "}" ";"
							| TYPEDEF STRUCT "<" lista_tipos ">" "{" lista_variables "}" ID ";"
							;

subrango 					: DIGITO "," DIGITO	/*TODO: CHEQUERA RANGO VALIDO V1< V2*/
							| DOUBLE "," DOUBLE	/*TODO: A.S CHEQUEAR VALOR CON TIPO*/
							;

declaracion_funcion			: tipo FUN ID "(" parametro ")" BEGIN cuerpo_funcion END
							;

sentencia_ejecucion			: asignacion
					 		| condicion_if
							| sentencia_print
							| REPEAT bloque_sentencia_ejecutable WHILE "(" condicion ")" ";"
							| GOTO ETIQUETA ";"
							;

sentencia_print				: OUTF CML ";"
							| OUTF "(" expresion ")" ";" /*{System.out.println(recuperar_lexema($3));}*/
							;

cuerpo_funcion              : cuerpo_funcion sentencia
							| cuerpo_funcion RET "(" expresion ")" ";"
							| RET "(" expresion ")" ";"
							| sentencia
							;

parametro						: tipo ID
							;

/*expresion_aritmetica					: invocacion_funcion
                            			| expresion   esta se va probablemente*/

invocacion_funcion          				: ID "(" expresion ")"
							;

bloque_sentencia_ejecutable 				: BEGIN bloque_sentencia_ejecutable sentencia_ejecucion END
					  		| BEGIN sentencia_ejecucion END
					  		;

condicion_if 						: IF condicion THEN bloque_sentencia_ejecutable END_IF ";"
              						| IF condicion THEN bloque_sentencia_ejecutable ELSE bloque_sentencia_ejecutable END_IF ";"
              						%prec LOWER_THAN_ELSE;

condicion						: "(" expresion comparador expresion ")"
							;

asignacion : lista_variables ASIGNACION lista_expresiones ';' /*TODO: verificar que ambos lados tengan la misma cantidad de componentes*/
           /*| estructura_asignacion ';'
           ;

estructura_asignacion : ID '.' ID ASIGNACION expresion_aritmetica
					  ;*/

expresion					: expresion "+" termino		/*{$$ = $1 + $3;}*/
							| expresion "-" termino		/*{$$ = $1 - $3;}*/
							| TOD "(" expresion ")"
							| termino
							;

termino						: termino "*" factor	/*{$$ = $1 * $3;}*/
							| termino "/" factor	/*{$$ = $1 / $3;}*/
							| factor
							;

factor						: ID	/*{$$ = $1;}*/
							| DIGITO	{
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
                                        }
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
							            ParserVal valor = val_peek(1);
                                        System.out.println("llegue para DOUBLE positivo" + valor.lval);
                                        BigDecimal numero = valor.lval;
                                        BigDecimal min = 2.2250738585072014e-308;
                                        BigDecimal max = 1.7976931348623157e+308;;
                                        if (numero > max || (numero < min && numero != 0.0)) {
                                            yyerror("El número está fuera del rango permitido para un double positivo.");
                                        }
                                        else{
                                            valor.ival = numero.intValue();
                                            lector.tablaSimbolos.addToken(numero.toString(),275);
                                            }
                                      }
							| "-" DOUBLE {
                                              ParserVal valor = val_peek(1);
                                              System.out.println("llegue para DOUBLE negativo " + valor.lval);
                                              BigDecimal numero = valor.lval;
                                              numero = -numero;
                                              BigDecimal min = -1.7976931348623157e+308;
                                              BigDecimal max = -2.2250738585072014e-308;
                                              if (numero > max || (numero < min) {
                                                yyerror("El número está fuera del rango permitido para un longint negativo.");
                                              } else{
                                                valor.ival = numero.intValue();
                                                lector.tablaSimbolos.addToken(numero.toString(),275);
                                              }
                                         }
							;

lista_variables				: lista_variables "," ID
							| lista_variables "," ID "." ID
							| ID "." ID
							| ID
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
}