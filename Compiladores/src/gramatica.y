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
							    | ID cuerpo END {System.out.println("ERROR,falta begin programa principal");}
                                | BEGIN END {System.out.println("ERROR,falta el ID del programa principal");}
                                | ID BEGIN cuerpo {System.out.println("ERROR,falta END del programa principal");}
                                | ID cuerpo {System.out.println("ERROR,falta BEGIN,END del programa principal");}
                                | BEGIN cuerpo  {System.out.println("ERROR,falta ID,END del programa principal");}
                                ;

cuerpo							: cuerpo sentencia
                                | sentencia
                                ;

sentencia						: sentencia_declaracion
                                | sentencia_ejecucion
                                | ETIQUETA
                                ;

sentencia_declaracion			: tipo lista_variables ";" {System.out.println($2);}
							    | tipo lista_variables {System.out.println("ERROR, Falta ; en la sentencia de declaracion");}
						    	| declaracion_funcion
						    	| TYPEDEF ID ASIGNACION tipo "{" subrango "}" ";" {System.out.println("Declaracion de Subtipo");}
						    	| TYPEDEF ID ASIGNACION tipo "{" subrango ";" {System.out.println("Error, Falta de llaves '{}'");}
						    	| TYPEDEF ID ASIGNACION tipo "" subrango  "}" ";" {System.out.println("Error, Falta de llaves '{}'");}
						    	| TYPEDEF ID ASIGNACION tipo  subrango  ";" {System.out.println("Error, Falta de llaves '{}'");}
						    	| TYPEDEF ID ASIGNACION tipo "{" "}" ";" {System.out.println("ERROR, Falta de rango");}
						    	| TYPEDEF ASIGNACION tipo "{" subrango "}" ";" {System.out.println("ERROR, Falta nombre del tipo definido");}
						    	| TYPEDEF ID ASIGNACION "{" subrango "}" ";" {System.out.println("ERROR, Falta el tipo base");}
						    	| TYPEDEF STRUCT "<" lista_tipos ">" "{" lista_variables "}" ID ";" {System.out.println("Declaracion de Struct");}
						    	| TYPEDEF STRUCT lista_tipos  "{" lista_variables "}" ID ";" {System.out.println("ERROR, Falta <>.");}
						    	| TYPEDEF "<" lista_tipos ">" "{" lista_variables "}" ID ";" {System.out.println("ERROR, Falta la palabra STRUCT.");}
                                | TYPEDEF STRUCT "<" lista_tipos ">"  "{" lista_variables "}"  ";" {System.out.println("ERROR,Falta  ID al final de la declaracion");}
                                ;

subrango 						: LONGINT "," LONGINT	/TODO: CHEQUERA RANGO VALIDO V1< V2/
                                | LONGINT LONGINT {System.out.println("ERROR,Falta ','entre los digitos del subrango");}
                                | DOUBLE "," DOUBLE	/TODO: A.S CHEQUEAR VALOR CON TIPO/
                                | DOUBLE DOUBLE {System.out.println("ERROR,Falta ','entre los digitos del subrango");}
                                | HEXA "," HEXA
                                | HEXA HEXA {System.out.println("ERROR,Falta ','entre los digitos del subrango");}
                                ;

declaracion_funcion				: tipo FUN ID "(" parametro ")" BEGIN cuerpo_funcion END {System.out.println("Declaracion de Funcion");}
                                | FUN ID "(" parametro ")" BEGIN cuerpo_funcion END {System.out.println("ERROR,Falta la declaracion del tipo de la FUN ");}
                                | tipo  ID "(" parametro ")" BEGIN cuerpo_funcion END {System.out.println("ERROR,Falta la declaracion de la palabra reservada FUN");}
                                | tipo FUN "(" parametro ")" BEGIN cuerpo_funcion END {System.out.println("ERROR,Falta el ID de la funcion");}
                                | tipo FUN ID parametro  BEGIN cuerpo_funcion END {System.out.println("ERROR,Falta de () a la hora de los parametros");}
                                | tipo FUN ID "("  ")" BEGIN cuerpo_funcion END {System.out.println("ERROR,Falta de parametros en la FUN");}
                                | tipo FUN ID "(" parametro ")"  cuerpo_funcion END {System.out.println("ERROR,Falta de BEGIN en la FUN");}
                                | tipo FUN ID "(" parametro ")" BEGIN cuerpo_funcion error {System.out.println("ERROR,Falta de END en la FUN");}
                                | tipo FUN ID "(" parametro ")" BEGIN  END {System.out.println("ERROR,Falsa cuerpo de funcion ");}
                                ;

sentencia_ejecucion				: asignacion
                                | condicion_if
                                | sentencia_print
                                | REPEAT bloque_sentencia_ejecutable WHILE "(" condicion ")" ";" {System.out.println("Declaracion REPEAT-WHILE");}
						    	//| bloque_sentencia_ejecutable WHILE "(" condicion ")" ";"  {System.out.println("ERROR,falta palabra REPEAT");}
						    	| REPEAT bloque_sentencia_ejecutable  "(" condicion ")" ";" {System.out.println("ERROR,falta palabra WHILE");}
						    	| REPEAT bloque_sentencia_ejecutable WHILE "(" condicion ")"  {System.out.println("ERROR,falta palabra ';' al final de la declaracion ");}
						    	| REPEAT bloque_sentencia_ejecutable WHILE "(" ")" ";" {System.out.println("ERROR,falta falta la condicion del WHILE ");}
						    	| GOTO ETIQUETA ";" {System.out.println("Declaracion de GOTO ");}
						    	| GOTO ETIQUETA  {System.out.println("ERROR, Falta ';' al final de la declaracion  ");}
						    	| GOTO  ";" {System.out.println("ERROR,falta la ETIQUETA  ");}
						    	| ETIQUETA ";" {System.out.println("ERROR,falta el GOTO  ");}
							    ;

sentencia_print					: OUTF "(" CML ")" ";" {System.out.println($3.sval);}
                                | OUTF "(" expresion ")" ";"
                                | OUTF "(" ")" ";"      {System.out.println("Falta parámetro en sentencia OUTF");}
                                | OUTF "(" expresion ")" {System.out.println("Falta ';' en la sentencia OUTF");}
                                | OUTF "(" CML ")" {System.out.println("Falta ';' en la sentencia OUTF");}
                                | OUTF CML ";" {System.out.println("Faltan los parentesis en la sentencia OUTF");}
                                | OUTF expresion ";"{System.out.println("Faltan los parentesis en la sentencia OUTF");}
                                ;



cuerpo_funcion              	: RET "(" expresion ")" ";" {System.out.println("Declaracion del Cuerpo de la funcion");}
						    	| cuerpo RET "(" expresion ")" ";"  {System.out.println("Declaracion del Cuerpo de la funcion");}
						    	| RET "(" expresion ")" {System.out.println("ERROR, falta ';' al final de la declaracion");}
							    | "(" expresion ")" ";" {System.out.println("ERROR, falta RET de la funcion");}
						    	| RET expresion ";"	{System.out.println("ERROR, falta '()' a la hora de realizar el RET");}
							    | RET "(" expresion ";"	{System.out.println("ERROR, falta ')' a la hora de realizar el RET");}
						    	| RET expresion ")" ";"	{System.out.println("ERROR, falta '(' a la hora de realizar el RET");}
						    	| cuerpo RET expresion ";"  {System.out.println("ERROR, falta '()' a la hora de realizar el RET");}
						    	| cuerpo RET "(" expresion ";"  {System.out.println("ERROR, falta ')' a la hora de realizar el RET");}
						    	| cuerpo RET expresion ")" ";"  {System.out.println("ERROR, falta '(' a la hora de realizar el RET");}
							    ;

parametro						: tipo ID
                            	| ID {System.out.println("ERROR, falta declaracion de TIPO en el parametro");}
                                //| tipo {System.out.println("ERROR, falta ID del parametro");}
                                ;


invocacion_funcion          	: ID "(" expresion ")"  {if ($1.sval.equals(null)){ System.out.println("No existe una funcion con ese nombre");}}
                            	//| "(" expresion ")" {System.out.println("ERROR, falta ID en la invocacion");} //TODO: algunos casos este choca con el de cuerpo_funcion
                            	| ID "(" ")" {System.out.println("ERROR, falta parametro en la invocacion de la funcion");}
                            	;

bloque_sentencia_ejecutable     : BEGIN bloque_sentencia_ejecutable sentencia_ejecucion END
					  		    | BEGIN sentencia_ejecucion END
					  		    ;

condicion_if 					: IF "(" condicion ")" THEN bloque_sentencia_ejecutable END_IF ";" /{System.out.println("Declaracion de IF");}/
                                | IF "(" condicion ")" THEN bloque_sentencia_ejecutable ELSE bloque_sentencia_ejecutable END_IF ";"  /{System.out.println(Declaracion de IF,ELSE );}/
                                | IF "(" condicion ")" bloque_sentencia_ejecutable END_IF ";" {System.out.println("ERROR, Falta THEN luego de la condicion");}
                                | IF "(" ")"THEN bloque_sentencia_ejecutable END_IF ";" {System.out.println("ERROR,falta de Condicion");}
                                | IF THEN END_IF ";" {System.out.println("ERROR,falta el bloque ejecutable");}
                                | IF THEN bloque_sentencia_ejecutable ";" {System.out.println("ERROR,falta END_IF al final de la declaracion");}
                                | IF THEN bloque_sentencia_ejecutable END_IF  {System.out.println("ERROR,falta ';' al final de la declaracion");}
                                | IF "(" condicion ")" THEN bloque_sentencia_ejecutable  bloque_sentencia_ejecutable END_IF ";"  {System.out.println("ERROR, falta ELSE luego de la sentencias de ejecucion");}
                                | IF "(" condicion ")" THEN bloque_sentencia_ejecutable ELSE bloque_sentencia_ejecutable END_IF {System.out.println("ERROR,falta ';' al final de la declaracion");}
                                | IF "(" condicion ")" THEN bloque_sentencia_ejecutable ELSE  END_IF ";" {System.out.println("ERROR, falta el bloque ejecutable en el ELSE");}
                                | IF "(" condicion ")" THEN ELSE bloque_sentencia_ejecutable END_IF ";" {System.out.println("ERROR, falta el bloque ejecutable en el IF");}
                                | IF "(" condicion ")" THEN bloque_sentencia_ejecutable ELSE bloque_sentencia_ejecutable ";" {System.out.println("ERROR,falta END_IF al final de la declaracion");}
                                %prec LOWER_THAN_ELSE
                                ;

condicion						:  expresion comparador expresion
                                |  lista_expresiones {System.out.println("ERROR, falta comparador en comparacion");}
                                ;

asignacion                      : lista_variables ASIGNACION lista_expresiones ";"{$$= $3;} /TODO: verificar que ambos lados tengan la misma cantidad de componentes/


expresion						: expresion "+" termino		{$$.ival = $1.ival + $3.ival;}
						    	| expresion "-" termino		{$$.ival = $1.ival - $3.ival;}
						    	| expresion "+" "+" termino {System.out.println("ERROR, hay 2 operadores");}
						    	| expresion "-" "+" termino {System.out.println("ERROR, hay 2 operadores");}
						    	| expresion error termino	{System.out.println("ERROR, falta de operador");}
						    	| "+" termino {System.out.println("ERROR, falta de operando");}
						    	| expresion "+" {System.out.println("ERROR, falta de operando");}
						    	| expresion "-" {System.out.println("ERROR, falta de operando");}
						    	| TOD "(" expresion ")" {System.out.println("Declaracion de TOD");}
						    	| TOD "("  ")" {System.out.println("ERROR, falta de expresion");}
						    	| termino {$$.ival = $1.ival;}
						    	;


termino							: termino "*" factor
                                | termino "/" factor
                                | termino "" "" factor {System.out.println("ERROR, hay 2 operadores");}
                                | termino "*" "/" factor {System.out.println("ERROR, hay 2 operadores");}
                                | termino "/" "/" factor {System.out.println("ERROR, hay 2 operadores");}
                                | termino "/" "*" factor {System.out.println("ERROR, hay 2 operadores");}
                                | termino "/" {System.out.println("ERROR, falta de operando");}
                                | termino "*" {System.out.println("ERROR, falta de operando");}
                                | "/" factor {System.out.println("ERROR, falta de operando");}
                                | "*" factor {System.out.println("ERROR, falta de operando");}
                                //| termino error factor	{System.out.println("ERROR, falta operador");}
                                | factor {$$= $1;}
                                ;


factor							: invocacion_funcion	//{$$ = $1;}
                                |ID	%prec '('  /* Precedencia menor que la de invocación de función */ {$$ = $1;  System.out.println("la variable" + $1.sval + "tiene valor: " + $1.ival);}
                                    | LONGINT 	{
                                                    $$ = $1;
                                                    Long valor = Long.parseLong($1.sval);
                                                    if (valor == 2147483648L){
                                                        yyerror("El número está fuera del rango permitido para un longint positivo.");
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
                                                        yyerror("El número está fuera del rango permitido para un HEXA positivo.");
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
                                |"-" DOUBLE     {
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


lista_variables					: lista_variables "," ID
                                | lista_variables "," id_compuesta
                                | lista_variables error ID {System.out.println("ERROR, falta "," en la lista ");}
                                | lista_variables error id_compuesta {System.out.println("ERROR, falta "," en la lista ");}
                                | id_compuesta
                                | ID {System.out.println($1.sval);}
                                ;

id_compuesta                	: ID "." ID
                                ;

lista_expresiones				: lista_expresiones ',' expresion
                                //| lista_expresiones expresion {System.out.println("ERROR, falta de ',' en la lista");}
                                | expresion
                                ;



lista_tipos						: lista_tipos "," tipo
                                | lista_tipos tipo  {System.out.println("ERROR, falta de ',' en la lista ");}
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