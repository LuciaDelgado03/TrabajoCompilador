%{


import accion_semantica.AccionSemantica;

import java.io*;
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
							/*| ID cuerpo END {} /*TODO: notificar error FALTA BEGIN*/
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

sentencia_declaracion		: tipo lista_variables
							| declaracion_funcion
							| TYPEDEF ID ASIGNACION tipo "{" subrango "}" ";"
							| TYPEDEF STRUCT "<" lista_tipos ">" "{" lista_variables "}" ID ";"
							;

subrango 						: DIGITO "," DIGITO	/*TODO: CHEQUERA RANGO VALIDO V1< V2*/
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

sentencia_print						: OUTF CML ";"
							| OUTF "(" expresion ")" ";"
							;

cuerpo_funcion              				: sentencia
							| RET "(" expresion ")" ";"
							;

parametro						: tipo ID
							;

expresion_aritmetica					: invocacion_funcion
                            				| expresion
							;

invocacion_funcion          				: ID "(" expresion ")"
							;

bloque_sentencia_ejecutable 				: BEGIN bloque_sentencia_ejecutable sentencia_ejecucion END
					  		| BEGIN sentencia_ejecucion END
					  		;

condicion_if 						: IF condicion THEN bloque_sentencia_ejecutable END_IF ";"
              						| IF condicion THEN bloque_sentencia_ejecutable ELSE bloque_sentencia_ejecutable END_IF ";"
              						%prec LOWER_THAN_ELSE;

condicion						: "(" expresion_aritmetica comparador expresion_aritmetica ")"
							;

asignacion : lista_variables ASIGNACION lista_expresiones ';' /*TODO: verificar que ambos lados tengan la misma cantidad de componentes*/
           /*| estructura_asignacion ';'
           ;

estructura_asignacion : ID '.' ID ASIGNACION expresion_aritmetica
					  ;*/

expresion						: expresion "+" termino
							| expresion "-" termino
							| TOD "(" expresion ")"
							| termino
							;

termino							: termino "*" factor
							| termino "/" factor
							| factor
							;

factor							: ID
							| DIGITO
							//| ID "." ID /*TODO: que sea solo de un tipo definido como struct*/
							| "-" DIGITO /*HAY QUE MULTIPLICARLO POR -1?*/
							| HEXA
							| "-" HEXA
							;

lista_variables				: lista_variables "," ID
							| lista_variables "," ID "." ID
							| ID "." ID
							| ID
							;

lista_expresiones					: lista_expresiones "," expresion_aritmetica
							| expresion_aritmetica
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