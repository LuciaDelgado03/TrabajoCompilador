//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "gramatica.y"


import accion_semantica.AccionSemantica;

import java.io*;
//#line 23 "Parser.java"




public class Parser
             implements ParserTokens
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    0,    0,    0,    1,    1,    2,    2,
    2,    2,    2,    2,    2,    3,    3,    3,    3,    8,
    8,    4,    4,    5,    5,    5,    5,   14,   14,   11,
   11,   10,   16,   16,   17,   18,   18,   13,   13,   19,
   21,   12,   12,   22,   15,   15,   15,   15,   24,   24,
   24,   25,   25,   25,   25,   25,   25,    7,    7,   23,
   23,    9,    9,    6,    6,    6,   20,   20,   20,   20,
   20,
};
final static short yylen[] = {                            2,
    4,    3,    2,    3,    2,    2,    2,    1,    2,    2,
    2,    1,    1,    1,    1,    2,    1,    8,   10,    3,
    3,    9,    9,    1,    1,    1,    4,    3,    5,    1,
    5,    2,    1,    1,    4,    4,    3,    8,   10,    3,
    7,    4,    6,    4,    3,    3,    4,    1,    3,    3,
    1,    1,    1,    3,    2,    1,    2,    3,    1,    3,
    1,    3,    1,    1,    1,    1,    1,    1,    1,    1,
    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    3,    0,    0,    0,    0,   65,
   64,   15,    0,    0,   13,   12,   14,    0,   24,   25,
   26,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    9,   10,   11,    0,   59,    0,    0,    2,
    0,   53,   56,    0,    0,    0,    0,   33,    0,    0,
   51,   28,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    1,    0,    0,    0,   55,   57,    0,    0,   67,
   68,   69,   70,   71,    0,    0,    0,    0,    0,   66,
    0,   63,    0,   27,   42,    0,    0,   58,    0,   54,
    0,    0,    0,   40,    0,   49,   50,   29,    0,    0,
    0,    0,    0,    0,   35,   47,    0,    0,    0,    0,
    0,    0,   62,   43,   32,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   37,    0,    0,   38,
   20,   21,   18,    0,    0,    0,    0,   36,    0,    0,
    0,   22,   39,   19,    0,    0,   31,
};
final static short yydgoto[] = {                          3,
   13,   14,   15,   16,   17,   18,   38,  111,   83,  104,
  137,   19,   20,   21,   46,   47,   48,  108,   49,   75,
    0,    0,    0,   50,   51,
};
final static short yysindex[] = {                      -207,
 -184, -167,    0,   32,    0,  -40, -196, -274,  -43,    0,
    0,    0,  -93,  -91,    0,    0,    0, -182,    0,    0,
    0,  -93, -151,  -31,   17,   -4, -198,   42,   47,  -31,
 -149,  -91,    0,    0,    0, -144,    0,   91, -124,    0,
   54,    0,    0,   98, -188,   62,    9,    0,  112,    3,
    0,    0,  108,  152,  -84,  -84,  101,  102, -122,  125,
 -102,    0,   -4,  -97,   -4,    0,    0,  -41,  -41,    0,
    0,    0,    0,    0,  -31,  -82,  -41,  -41,  122,    0,
   63,    0,    8,    0,    0,  -31,  -84,    0,  153,    0,
  158,    3,    3,    0,  -72,    0,    0,    0, -213,   66,
  -84,  146,  -65,  166,    0,    0, -142, -199,  164,  165,
   85,  -60,    0,    0,    0,  -48,  -43,  -47, -140,  -72,
  154,  -57,  -58,  157,  -27, -108,    0,  -42,  -44,    0,
    0,    0,    0,  -51,  181,  -91,  -39,    0,  167,  168,
   -4,    0,    0,    0,  159,  169,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0, -170,    0,
    0,    0,  223,    2,    0,    0,    0,    0,    0,    0,
    0,    0,  224,    0,    0,    0,    0,    0,    0,    0,
    0,    5,    0,    0,    0,    0,    0,    1,  225,    0,
  -35,    0,    0,    0,    0,    6,    0,    0,    0,  -30,
    0,    0,  -35,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -25,   -5,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -32,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   29,   10,   12,   14,    7,  -13,  121,    0,    0,    0,
    0,    0,    0,    0,   -7,   -8,    0,  -59,    0,    0,
    0,    0,    0,   73,   67,
};
final static int YYTABLESIZE=295;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         26,
   16,    8,   31,   45,    7,   52,   52,   52,   29,   52,
   48,   52,   48,   45,   48,   45,   61,   45,   54,   45,
   35,   58,   32,   52,   52,   33,   52,   34,   48,   48,
   23,   48,   32,   45,   45,   46,   45,   46,   35,   46,
   45,   81,   82,   33,   77,   34,   34,  119,   32,   78,
   39,  101,    1,   46,   46,   89,   46,   91,  109,  120,
  129,  110,  121,    2,   34,   34,   94,   34,   70,  100,
   71,   24,    4,  103,   27,   52,    5,  102,    6,    7,
   55,   28,   36,   66,   67,    8,    9,  113,   37,    4,
   10,   11,   22,   63,   66,    6,    7,  134,   12,   64,
   66,   56,    8,    9,   68,    4,   69,   10,   11,   40,
   57,    6,    7,  118,    4,   12,    4,  107,    8,    9,
    6,   59,    6,   10,   11,  128,   60,    8,  117,    8,
  117,   12,    4,  145,   61,  136,   62,   65,    6,    7,
   92,   93,   35,   96,   97,    8,    9,   33,    4,   34,
   10,   11,   76,   64,    6,    7,   86,  135,   12,   84,
   85,    8,    9,    4,   87,    4,   10,   11,   88,    6,
    7,    6,    7,   90,   12,   95,    8,    9,    8,    9,
   98,   10,   11,   10,   11,   99,   80,  107,  112,   12,
   10,   11,   79,  105,   68,   68,   69,   69,  106,  146,
   68,   68,   69,   69,  114,  115,  116,  122,  123,  124,
   37,  126,  130,  127,  131,  133,  132,  139,  138,  140,
  141,  142,    6,    5,    4,  143,  144,  147,   30,   53,
   42,   43,  125,   25,    0,   30,    0,    0,    0,   41,
   42,   43,    0,    0,    0,   44,    0,    0,   52,   52,
   52,    0,    0,   48,   48,   48,    0,   16,   45,   45,
   45,   16,    8,   16,   16,    7,   53,   42,   43,    0,
   16,   16,   44,    0,    0,   16,   16,    0,   46,   46,
   46,    0,    0,   16,    8,    0,    0,    7,    0,   34,
   34,   34,   72,   73,   74,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,    0,   46,   45,    0,   41,   42,   43,  283,   45,
   41,   47,   43,   45,   45,   41,   44,   43,   26,   45,
   14,   30,   13,   59,   60,   14,   62,   14,   59,   60,
    2,   62,   23,   59,   60,   41,   62,   43,   32,   45,
   45,   55,   56,   32,   42,   32,   41,  107,   39,   47,
   22,   44,  260,   59,   60,   63,   62,   65,  272,  259,
  120,  275,  262,  271,   59,   60,   75,   62,   60,   62,
   62,   40,  257,   87,  271,   59,  261,   86,  263,  264,
  279,  278,  265,  272,  273,  270,  271,  101,  271,  257,
  275,  276,  260,   40,  265,  263,  264,  125,  283,   46,
  271,   60,  270,  271,   43,  257,   45,  275,  276,  261,
   64,  263,  264,  107,  257,  283,  257,  260,  270,  271,
  263,  271,  263,  275,  276,  119,  271,  270,  271,  270,
  271,  283,  257,  141,   44,  126,  261,   40,  263,  264,
   68,   69,  136,   77,   78,  270,  271,  136,  257,  136,
  275,  276,   41,   46,  263,  264,  279,  266,  283,   59,
   59,  270,  271,  257,   40,  257,  275,  276,  271,  263,
  264,  263,  264,  271,  283,  258,  270,  271,  270,  271,
   59,  275,  276,  275,  276,  123,  271,  260,  123,  283,
  275,  276,   41,   41,   43,   43,   45,   45,   41,   41,
   43,   43,   45,   45,   59,  271,   41,   44,   44,  125,
  271,  260,   59,  261,  272,   59,  275,  262,  261,  271,
   40,  261,    0,    0,    0,   59,   59,   59,  261,  271,
  272,  273,  112,  274,   -1,  279,   -1,   -1,   -1,  271,
  272,  273,   -1,   -1,   -1,  277,   -1,   -1,  284,  285,
  286,   -1,   -1,  284,  285,  286,   -1,  257,  284,  285,
  286,  261,  261,  263,  264,  261,  271,  272,  273,   -1,
  270,  271,  277,   -1,   -1,  275,  276,   -1,  284,  285,
  286,   -1,   -1,  283,  283,   -1,   -1,  283,   -1,  284,
  285,  286,  284,  285,  286,
};
}
final static short YYFINAL=3;
final static short YYMAXTOKEN=286;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,null,"';'",
"'<'",null,"'>'",null,"'@'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"IF","THEN","ELSE","BEGIN","END","END_IF",
"OUTF","TYPEDEF","FUN","RET","STRING","REPEAT","WHILE","GOTO","ID","DIGITO",
"HEXA","CML","DOUBLE","LONGINT","TOD","STRUCT","ASIGNACION","DISTINTO",
"MENOR_IGUAL","MAYOR_IGUAL","ETIQUETA","\"MAYOR_IGUAL\"","\"MENOR_IGUAL\"",
"\"DISTINTO\"",
};
final static String yyrule[] = {
"$accept : prog",
"prog : ID BEGIN cuerpo END",
"prog : ID cuerpo END",
"prog : BEGIN END",
"prog : ID BEGIN cuerpo",
"prog : ID cuerpo",
"prog : BEGIN cuerpo",
"cuerpo : cuerpo sentencia",
"cuerpo : sentencia",
"sentencia : sentencia sentencia_declaracion",
"sentencia : sentencia sentencia_funcion",
"sentencia : sentencia sentencia_ejecucion",
"sentencia : sentencia_funcion",
"sentencia : sentencia_declaracion",
"sentencia : sentencia_ejecucion",
"sentencia : ETIQUETA",
"sentencia_declaracion : tipo lista_variables",
"sentencia_declaracion : sentencia_funcion",
"sentencia_declaracion : TYPEDEF ID ASIGNACION tipo '{' subrango '}' ';'",
"sentencia_declaracion : TYPEDEF STRUCT '<' lista_tipos '>' '{' lista_variables '}' ID ';'",
"subrango : DIGITO ',' DIGITO",
"subrango : DOUBLE ',' DOUBLE",
"sentencia_funcion : tipo FUN ID '(' parametro ')' BEGIN cuerpo_funcion END",
"sentencia_funcion : tipo FUN ID '(' parametro ')' BEGIN cuerpo_funcion END",
"sentencia_ejecucion : asignacion",
"sentencia_ejecucion : condicion_if",
"sentencia_ejecucion : sentencia_print",
"sentencia_ejecucion : GOTO ETIQUETA '@' ';'",
"sentencia_print : OUTF CML ';'",
"sentencia_print : OUTF '(' expresion ')' ';'",
"cuerpo_funcion : sentencia",
"cuerpo_funcion : RET '(' expresion ')' ';'",
"parametro : tipo ID",
"expresion_aritmetica : invocacion_funcion",
"expresion_aritmetica : expresion",
"invocacion_funcion : ID '(' expresion ')'",
"bloque_sentencia_ejecutable : BEGIN bloque_sentencia_ejecutable sentencia_ejecucion END",
"bloque_sentencia_ejecutable : BEGIN sentencia_ejecucion END",
"condicion_if : IF '(' condicion ')' THEN bloque_sentencia_ejecutable END_IF ';'",
"condicion_if : IF '(' condicion ')' THEN bloque_sentencia_ejecutable ELSE bloque_sentencia_ejecutable END_IF ';'",
"condicion : expresion_aritmetica comparador expresion_aritmetica",
"repeat_while : REPEAT bloque_sentencia_ejecutable WHILE '(' condicion ')' ';'",
"asignacion : ID ASIGNACION expresion_aritmetica ';'",
"asignacion : ID '.' ID ASIGNACION expresion_aritmetica ';'",
"asignacion_multiple : lista_variables ASIGNACION lista_expresiones ';'",
"expresion : expresion '+' termino",
"expresion : expresion '-' termino",
"expresion : TOD '(' expresion ')'",
"expresion : termino",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : ID",
"factor : DIGITO",
"factor : ID '.' ID",
"factor : '-' DIGITO",
"factor : HEXA",
"factor : '-' HEXA",
"lista_variables : lista_variables ',' ID",
"lista_variables : ID",
"lista_expresiones : lista_expresiones ',' expresion_aritmetica",
"lista_expresiones : expresion_aritmetica",
"lista_tipos : lista_tipos ',' tipo",
"lista_tipos : tipo",
"tipo : LONGINT",
"tipo : DOUBLE",
"tipo : ID",
"comparador : '<'",
"comparador : '>'",
"comparador : \"MAYOR_IGUAL\"",
"comparador : \"MENOR_IGUAL\"",
"comparador : \"DISTINTO\"",
};

//#line 140 "gramatica.y"
/*Soluciones:

  Revisa las reglas ambiguas. Los conflictos shift/reduce son comunes en estructuras como las condiciones if-then-else, donde el parser no sabe si debe continuar analizando (shift) o reducir la parte ya analizada.

  Para if-then-else, una solución común es usar la directiva %prec (precedencia) para especificar la prioridad de las reducciones, de esta forma:

  yacc
  Copiar código
  %nonassoc LOWER_THAN_ELSE
  %nonassoc ELSE

  condicion_if : IF "(" condicion ")" THEN bloque_sentencia_ejecutable END_IF ";"
              | IF "(" condicion ")" THEN bloque_sentencia_ejecutable ELSE bloque_sentencia_ejecutable END_IF ";"
              %prec LOWER_THAN_ELSE;
*/
//#line 393 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 2:
//#line 18 "gramatica.y"
{}
break;
case 3:
//#line 19 "gramatica.y"
{}
break;
case 4:
//#line 20 "gramatica.y"
{}
break;
case 5:
//#line 21 "gramatica.y"
{}
break;
case 6:
//#line 22 "gramatica.y"
{}
break;
//#line 562 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
