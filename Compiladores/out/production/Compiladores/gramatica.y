%{
package compilador;

import accion_semantica.AccionSemantica;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
%}
        //declaracion de tokens a recibir del Analizador Lexico
%token ID CTE CADENA IF THEN ELSE
%left '+' '-'
%left '*' '/'

%start program

%%
program: