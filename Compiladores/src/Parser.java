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


/*import accion_semantica.AccionSemantica;*/

import java.io.*;
//#line 23 "Parser.java"




public class Parser
{
AnalizadorLexico lector;
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
public final static short IF=257;
public final static short THEN=258;
public final static short ELSE=259;
public final static short BEGIN=260;
public final static short END=261;
public final static short END_IF=262;
public final static short OUTF=263;
public final static short TYPEDEF=264;
public final static short FUN=265;
public final static short RET=266;
public final static short STRING=267;
public final static short REPEAT=268;
public final static short WHILE=269;
public final static short GOTO=270;
public final static short ID=271;
public final static short DIGITO=272;
public final static short HEXA=273;
public final static short CML=274;
public final static short DOUBLE=275;
public final static short LONGINT=276;
public final static short TOD=277;
public final static short STRUCT=278;
public final static short ASIGNACION=279;
public final static short DISTINTO=280;
public final static short MENOR_IGUAL=281;
public final static short MAYOR_IGUAL=282;
public final static short ETIQUETA=283;
public final static short LOWER_THAN_ELSE=284;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    1,    1,    2,    2,    2,    3,    3,    3,
    3,    8,    8,    7,    4,    4,    4,    4,    4,   14,
   14,   11,   11,   11,   11,   10,   18,   15,   15,   13,
   13,   16,   12,   17,   17,   17,   17,   21,   21,   21,
   22,   22,   22,   22,   22,   22,   22,   22,    6,    6,
    6,    6,   20,   20,    9,    9,    5,    5,    5,   19,
   19,   19,   19,   19,
};
final static short yylen[] = {                            2,
    4,    3,    2,    1,    1,    1,    1,    3,    1,    8,
   10,    3,    3,    9,    1,    1,    1,    7,    3,    3,
    5,    2,    6,    5,    1,    2,    4,    4,    3,    6,
    8,    5,    4,    3,    3,    4,    1,    3,    3,    1,
    1,    1,    2,    1,    2,    1,    1,    2,    3,    5,
    3,    1,    3,    1,    3,    1,    1,    1,    1,    1,
    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   58,
   57,    7,    0,    4,    5,    6,    0,    0,    9,   15,
   16,   17,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    2,    3,    0,    0,    0,    0,    0,
    0,   42,   44,   47,    0,    0,    0,   46,    0,   40,
    0,    1,   20,    0,    0,    0,    0,    0,    0,   19,
   51,    0,    8,    0,    0,    0,    0,    0,   43,   45,
   48,    0,    0,   60,   61,   62,   63,   64,    0,    0,
    0,    0,    0,   59,    0,   56,    0,   29,    0,    0,
    0,   33,    0,    0,    0,    0,    0,    0,    0,   38,
   39,    0,    0,   21,    0,    0,    0,   28,    0,    0,
    0,    0,   50,   27,   36,   32,    0,   30,    0,    0,
    0,    0,   55,    0,   26,    0,    0,    0,    0,    0,
    0,   18,    0,   31,   12,   13,   10,    0,    0,   25,
    0,    0,    0,   14,    0,   22,   11,    0,    0,    0,
    0,   24,    0,   23,
};
final static short yydgoto[] = {                          2,
   13,   14,   15,   16,   17,   18,   19,  121,   87,  111,
  141,   20,   21,   22,   31,   24,   47,   48,   79,   65,
   49,   50,
};
final static short yysindex[] = {                      -226,
 -146,    0,    7,  -75,  -13, -198, -192, -205,   45,    0,
    0,    0, -130,    0,    0,    0, -186,  -27,    0,    0,
    0,    0,  -34, -162, -107,   43,  -34, -176,   55, -170,
 -133,   48, -129,    0,    0, -123,   45,   17,  -34, -113,
  120,    0,    0,    0,  127,  -57,  -23,    0,   47,    0,
 -192,    0,    0,   78, -132, -132,  -90, -158,  134,    0,
    0,  138,    0,   83,   22,  135,  -34,  -34,    0,    0,
    0,   -4,   -4,    0,    0,    0,    0,    0,  -34,   -4,
   -4, -127,  124,    0,   63,    0,    2,    0,  -74,    7,
 -132,    0,  -34,  -77,  106,  161,   47,   47,  162,    0,
    0, -192,  139,    0, -120,   74, -132,    0,  158,  -54,
  178,   83,    0,    0,    0,    0,  -42,    0,  177,  179,
   97,  -47,    0,  166,    0,  -33,  167,  -44,  -46,  171,
  -28,    0,  -91,    0,    0,    0,    0,  -40,  192,    0,
 -201,  174,  -34,    0,  194,    0,    0,  168,  -34,  176,
  169,    0,  181,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,   -6,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -19,    0,    0,    0,
  -41,    0,    0,    0,    0,    0,    0,    0,  -36,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   27,    0,   -9,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -31,  -11,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   33,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  232,   29,    0,   14,   -3,   -2,    0,    0,    0,    0,
    0,    0,    0,    0,    6,  152,   16,    0,    0,    0,
  -14,  110,
};
final static int YYTABLESIZE=276;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         41,
   41,   41,   41,   41,   37,   41,   37,   37,   37,   34,
   46,   34,   34,   34,   38,   40,   40,   41,   41,   72,
   41,   73,   37,   37,   52,   37,   27,   34,   34,   35,
   34,   35,   35,   35,   49,   58,   74,   52,   75,   52,
   46,   35,   54,   57,    1,  107,   23,   35,   35,   49,
   35,   85,   86,   35,   64,    3,   82,   97,   98,  144,
   40,    5,    6,  106,  145,   93,    7,   30,    8,    9,
   54,   89,   28,   10,   11,   63,   53,   32,   36,   29,
   92,   12,   95,   96,   37,   54,    3,  110,   80,   30,
   33,   53,    5,   81,   99,   51,  138,    7,    3,    8,
   37,   53,   55,  123,    5,   52,   60,  117,  112,    7,
    3,    8,   37,    4,   56,   49,    5,    6,   83,  131,
   72,    7,   73,    8,    9,   72,    3,   73,   10,   11,
   34,  102,    5,    6,  103,   59,   12,    7,   84,    8,
    9,   61,   10,   11,   10,   11,  114,   62,   72,    3,
   73,  119,   12,   52,  120,    5,    6,   66,  148,   67,
    7,  140,    8,    9,  151,    3,   68,   10,   11,  146,
   88,    5,    6,   90,  139,   12,    7,   91,    8,    9,
   94,    3,  104,   10,   11,  105,  108,    5,    6,  100,
  101,   12,    7,  113,    8,    9,  122,  118,  124,   10,
   11,  115,  116,   72,   72,   73,   73,   12,  150,  153,
   72,   72,   73,   73,   69,   70,  125,   71,  126,  127,
  128,  130,  129,   37,  132,  134,  133,  135,  136,  137,
  142,  143,  147,  149,  152,   25,   41,   42,   43,  154,
   44,  109,   45,   41,   41,   41,    0,    0,   37,   37,
   37,   39,    0,   34,   34,   34,    0,    0,   59,   52,
   26,   76,   77,   78,   59,    0,   41,   42,   43,   49,
   44,    0,   52,   35,   35,   35,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   44,   45,   41,   47,   43,   44,   45,   41,
   45,   43,   44,   45,   17,   44,   44,   59,   60,   43,
   62,   45,   59,   60,   44,   62,   40,   59,   60,   41,
   62,   43,   44,   45,   44,   30,   60,   44,   62,   59,
   45,   13,   27,   30,  271,   44,   40,   59,   60,   59,
   62,   55,   56,   25,   39,  257,   51,   72,   73,  261,
   44,  263,  264,   62,  266,   44,  268,  260,  270,  271,
   44,   58,  271,  275,  276,   59,   44,  283,  265,  278,
   59,  283,   67,   68,  271,   59,  257,   91,   42,  260,
   46,   59,  263,   47,   79,  258,  125,  268,  257,  270,
  271,   59,  279,  107,  263,  125,   59,  102,   93,  268,
  257,  270,  271,  260,   60,  125,  263,  264,   41,  122,
   43,  268,   45,  270,  271,   43,  257,   45,  275,  276,
  261,  259,  263,  264,  262,  269,  283,  268,  271,  270,
  271,  271,  275,  276,  275,  276,   41,  271,   43,  257,
   45,  272,  283,  261,  275,  263,  264,  271,  143,   40,
  268,  133,  270,  271,  149,  257,   40,  275,  276,  141,
  261,  263,  264,   40,  266,  283,  268,   40,  270,  271,
   46,  257,   59,  275,  276,  123,  261,  263,  264,   80,
   81,  283,  268,  271,  270,  271,  123,   59,   41,  275,
  276,   41,   41,   43,   43,   45,   45,  283,   41,   41,
   43,   43,   45,   45,  272,  273,  271,  275,   41,  262,
   44,  125,   44,  271,   59,   59,  260,  272,  275,   59,
  271,   40,   59,   40,   59,    4,  271,  272,  273,   59,
  275,   90,  277,  285,  286,  287,   -1,   -1,  285,  286,
  287,  279,   -1,  285,  286,  287,   -1,   -1,  265,  279,
  274,  285,  286,  287,  271,   -1,  271,  272,  273,  279,
  275,   -1,  279,  285,  286,  287,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=287;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,null,"';'",
"'<'",null,"'>'",null,null,null,null,null,null,null,null,null,null,null,null,
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
"MENOR_IGUAL","MAYOR_IGUAL","ETIQUETA","LOWER_THAN_ELSE","\"MAYOR_IGUAL\"",
"\"MENOR_IGUAL\"","\"DISTINTO\"",
};
final static String yyrule[] = {
"$accept : prog",
"prog : ID BEGIN cuerpo END",
"prog : ID cuerpo END",
"cuerpo : cuerpo sentencia",
"cuerpo : sentencia",
"sentencia : sentencia_declaracion",
"sentencia : sentencia_ejecucion",
"sentencia : ETIQUETA",
"sentencia_declaracion : tipo lista_variables ';'",
"sentencia_declaracion : declaracion_funcion",
"sentencia_declaracion : TYPEDEF ID ASIGNACION tipo '{' subrango '}' ';'",
"sentencia_declaracion : TYPEDEF STRUCT '<' lista_tipos '>' '{' lista_variables '}' ID ';'",
"subrango : DIGITO ',' DIGITO",
"subrango : DOUBLE ',' DOUBLE",
"declaracion_funcion : tipo FUN ID '(' parametro ')' BEGIN cuerpo_funcion END",
"sentencia_ejecucion : asignacion",
"sentencia_ejecucion : condicion_if",
"sentencia_ejecucion : sentencia_print",
"sentencia_ejecucion : REPEAT bloque_sentencia_ejecutable WHILE '(' condicion ')' ';'",
"sentencia_ejecucion : GOTO ETIQUETA ';'",
"sentencia_print : OUTF CML ';'",
"sentencia_print : OUTF '(' expresion ')' ';'",
"cuerpo_funcion : cuerpo_funcion sentencia",
"cuerpo_funcion : cuerpo_funcion RET '(' expresion ')' ';'",
"cuerpo_funcion : RET '(' expresion ')' ';'",
"cuerpo_funcion : sentencia",
"parametro : tipo ID",
"invocacion_funcion : ID '(' expresion ')'",
"bloque_sentencia_ejecutable : BEGIN bloque_sentencia_ejecutable sentencia_ejecucion END",
"bloque_sentencia_ejecutable : BEGIN sentencia_ejecucion END",
"condicion_if : IF condicion THEN bloque_sentencia_ejecutable END_IF ';'",
"condicion_if : IF condicion THEN bloque_sentencia_ejecutable ELSE bloque_sentencia_ejecutable END_IF ';'",
"condicion : '(' expresion comparador expresion ')'",
"asignacion : lista_variables ASIGNACION lista_expresiones ';'",
"expresion : expresion '+' termino",
"expresion : expresion '-' termino",
"expresion : TOD '(' expresion ')'",
"expresion : termino",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : ID",
"factor : DIGITO",
"factor : '-' DIGITO",
"factor : HEXA",
"factor : '-' HEXA",
"factor : invocacion_funcion",
"factor : DOUBLE",
"factor : '-' DOUBLE",
"lista_variables : lista_variables ',' ID",
"lista_variables : lista_variables ',' ID '.' ID",
"lista_variables : ID '.' ID",
"lista_variables : ID",
"lista_expresiones : lista_expresiones ',' expresion",
"lista_expresiones : expresion",
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

//#line 213 "gramatica.y"


void yyerror(String mensaje) {
  // funcion utilizada para imprimir errores que produce yacc
  System.out.println("Error yacc: " + mensaje);
}
//#line 375 "Parser.java"
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
        yychar = lector.yylex();  //get next token
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
case 42:
//#line 105 "gramatica.y"
{
  ParserVal valor = val_peek(1);
  System.out.println("llegue para LOGNINT positivo" + valor.lval);
  Long numero = valor.lval;
  if (numero > 2147483647L) {
    yyerror("El número está fuera del rango permitido para un longint positivo.");
  }
  else{
    valor.ival = numero.intValue();
    lector.tablaSimbolos.addToken(numero.toString(),272);
  }
}
break;
case 43:
//#line 117 "gramatica.y"
{
  ParserVal valor = val_peek(1);
  System.out.println("llegue para LONGINT negativo " + valor.lval);
  Long numero = valor.lval;
  numero = -numero;
  Long min = -2147483648L;
  if (numero < min) {
    yyerror("El número está fuera del rango permitido para un longint negativo.");
  } else{
    valor.ival = numero.intValue();
    lector.tablaSimbolos.addToken(numero.toString(),272);
  }
}
break;
case 44:
//#line 131 "gramatica.y"
{
                                      ParserVal valor = val_peek(1);
                                      System.out.println("llegue para HEXA positivo" + valor.sval);
                                      String numero = valor.sval;
                                      //String hexadecimal = "0x7FFFFFFF"; // Ejemplo de número hexadecimal
                                      // Convertir de hexadecimal a decimal
                                      Long numeroL = Long.decode(numero); // Conversión de cadena hexadecimal a número long

                                      long longIntMax = 2147483647L;  // 0x7FFFFFFF

                                      if (numeroL > longIntMax) {
                                        yyerror("El número está fuera del rango permitido para un HEXA positivo.");
                                      }
                                      else{
                                        valor.sval = numero;
                                        lector.tablaSimbolos.addToken(numero,273);
                                      }


                                    }
break;
case 45:
//#line 143 "gramatica.y"
{
  ParserVal valor = val_peek(1);
  System.out.println("llegue para HEXA negativo" + valor.sval);
  String numero = valor.sval;
  String hexadecimal = "0x7FFFFFFF"; // Ejemplo de número hexadecimal
  // Convertir de hexadecimal a decimal
  Long numeroL = Long.decode(numero); // Conversión de cadena hexadecimal a número long
  numeroL = -numeroL;
  long min = -2147483648L; // -0x80000000

  if (numeroL < min) {
    yyerror("El número está fuera del rango permitido para un HEXA negativo.");
  }
  else{
    numero = Long.toHexString(numeroL);
    valor.sval = numero;
    lector.tablaSimbolos.addToken(numero,273);
  }

}
break;
case 47:
//#line 157 "gramatica.y"
{
							           /* ParserVal valor = val_peek(1);
                                        System.out.println("llegue para DOUBLE positivo" + valor.lval);
                                        BigDecimal numero = valor.lval.BigDecimal.value();
                                        BigDecimal min = 2.2250738585072014e-308;
                                        BigDecimal max = 1.7976931348623157e+308;;
                                        if (numero > max || (numero < min && numero != 0.0)) {
                                            yyerror("El número está fuera del rango permitido para un double positivo.");
                                        }
                                        else{
                                            valor.ival = numero.intValue();
                                            lector.tablaSimbolos.addToken(numero.toString(),275);
                                            */
}
break;
case 48:
//#line 171 "gramatica.y"
{/*
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
      }*/
 }
break;
//#line 620 "Parser.java"
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
        yychar = lector.yylex();        //get next character
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
public void run(AnalizadorLexico lex)
{
  lector = lex;
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
