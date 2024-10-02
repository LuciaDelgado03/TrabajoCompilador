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
   22,   22,   22,   22,   22,   22,    6,    6,    6,    6,
   20,   20,    9,    9,    5,    5,    5,   19,   19,   19,
   19,   19,
};
final static short yylen[] = {                            2,
    4,    3,    2,    1,    1,    1,    1,    3,    1,    8,
   10,    3,    3,    9,    1,    1,    1,    7,    3,    3,
    5,    2,    6,    5,    1,    2,    4,    4,    3,    6,
    8,    5,    4,    3,    3,    4,    1,    3,    3,    1,
    1,    1,    2,    1,    2,    1,    3,    5,    3,    1,
    3,    1,    3,    1,    1,    1,    1,    1,    1,    1,
    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   56,
   55,    7,    0,    4,    5,    6,    0,    0,    9,   15,
   16,   17,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    2,    3,    0,    0,    0,    0,    0,
    0,   42,   44,    0,    0,    0,   46,    0,   40,    0,
    1,   20,    0,    0,    0,    0,    0,    0,   19,   49,
    0,    8,    0,    0,    0,    0,    0,   43,   45,    0,
    0,   58,   59,   60,   61,   62,    0,    0,    0,    0,
    0,   57,    0,   54,    0,   29,    0,    0,    0,   33,
    0,    0,    0,    0,    0,    0,    0,   38,   39,    0,
    0,   21,    0,    0,    0,   28,    0,    0,    0,    0,
   48,   27,   36,   32,    0,   30,    0,    0,    0,    0,
   53,    0,   26,    0,    0,    0,    0,    0,    0,   18,
    0,   31,   12,   13,   10,    0,    0,   25,    0,    0,
    0,   14,    0,   22,   11,    0,    0,    0,    0,   24,
    0,   23,
};
final static short yydgoto[] = {                          2,
   13,   14,   15,   16,   17,   18,   19,  119,   85,  109,
  139,   20,   21,   22,   31,   24,   46,   47,   77,   64,
   48,   49,
};
final static short yysindex[] = {                      -215,
 -140,    0,   36,  -67,  -13, -219, -174, -193,   48,    0,
    0,    0, -124,    0,    0,    0, -161,  -27,    0,    0,
    0,    0,  -34, -157,  -99,   46,  -34, -145,   72, -172,
 -131,   86, -121,    0,    0, -118,   48,    1,  -34, -117,
  115,    0,    0,  116, -226,  -23,    0,   31,    0, -174,
    0,    0,   68, -150, -150, -100, -168,  126,    0,    0,
  127,    0,   84,   18,  124,  -34,  -34,    0,    0,   -4,
   -4,    0,    0,    0,    0,    0,  -34,   -4,   -4, -147,
  114,    0,   52,    0,    9,    0,  -82,   36, -150,    0,
  -34,  -93,  169,  170,   31,   31,  176,    0,    0, -174,
  123,    0, -153,   63, -150,    0,  148,  -80,  153,   84,
    0,    0,    0,    0,  -64,    0,  151,  155,   77,  -66,
    0,  147,    0,  -53,  165,  -46,  -47,  171,  -28,    0,
  -83,    0,    0,    0,    0,  -42,  191,    0, -196,  173,
  -34,    0,  193,    0,    0,  177,  -34,  175,  182,    0,
  181,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,   -6,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -19,    0,    0,    0,
  -41,    0,    0,    0,    0,    0,    0,  -36,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   22,    0,   -9,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -31,  -11,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   25,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,
};
final static short yygindex[] = {                         0,
  231,   29,    0,    6,    3,   -2,    0,    0,    0,    0,
    0,    0,    0,    0,   14,  154,   16,    0,    0,    0,
   71,   70,
};
final static int YYTABLESIZE=276;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         41,
   41,   41,   41,   41,   37,   41,   37,   37,   37,   34,
   45,   34,   34,   34,   38,   40,   40,   41,   41,   70,
   41,   71,   37,   37,   50,   37,   27,   34,   34,   35,
   34,   35,   35,   35,   47,   56,   72,   50,   73,   50,
   45,   35,   53,   57,   40,   68,   69,   35,   35,   47,
   35,   28,  105,   35,   63,    1,   83,   84,   29,   62,
    3,   91,   87,   80,  142,   52,    5,    6,   51,  143,
  104,    7,   78,    8,    9,   23,   90,   79,   10,   11,
   52,   93,   94,   51,    3,   30,   12,   30,    3,   32,
    5,  108,   97,   33,    5,    7,  136,    8,   37,    7,
   50,    8,   37,   36,   52,   50,  110,  121,   81,   37,
   70,  100,   71,  115,  101,   47,    3,  129,  117,    4,
   82,  118,    5,    6,   10,   11,   70,    7,   71,    8,
    9,   55,    3,   54,   10,   11,   34,   58,    5,    6,
   95,   96,   12,    7,   59,    8,    9,   98,   99,   60,
   10,   11,   61,   65,   66,   67,  146,    3,   12,  138,
   86,   51,  149,    5,    6,   88,   89,  144,    7,   92,
    8,    9,  102,    3,  103,   10,   11,  111,  106,    5,
    6,  116,  137,   12,    7,  120,    8,    9,  122,    3,
  123,   10,   11,  124,  126,    5,    6,  125,  127,   12,
    7,  128,    8,    9,   37,  130,  131,   10,   11,  112,
  113,   70,   70,   71,   71,   12,  114,  148,   70,   70,
   71,   71,  151,  132,   70,  133,   71,  134,  140,  135,
  141,  145,  147,  150,   25,    0,   41,   42,   43,  152,
    0,  107,   44,   41,   41,   41,    0,    0,   37,   37,
   37,   39,    0,   34,   34,   34,    0,    0,   57,   50,
   26,   74,   75,   76,   57,    0,   41,   42,   43,   47,
    0,    0,   50,   35,   35,   35,
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
   45,   13,   27,   30,   44,  272,  273,   59,   60,   59,
   62,  271,   44,   25,   39,  271,   54,   55,  278,   59,
  257,   44,   57,   50,  261,   44,  263,  264,   44,  266,
   62,  268,   42,  270,  271,   40,   59,   47,  275,  276,
   59,   66,   67,   59,  257,  260,  283,  260,  257,  283,
  263,   89,   77,   46,  263,  268,  125,  270,  271,  268,
  258,  270,  271,  265,   59,  125,   91,  105,   41,  271,
   43,  259,   45,  100,  262,  125,  257,  120,  272,  260,
  271,  275,  263,  264,  275,  276,   43,  268,   45,  270,
  271,   60,  257,  279,  275,  276,  261,  269,  263,  264,
   70,   71,  283,  268,   59,  270,  271,   78,   79,  271,
  275,  276,  271,  271,   40,   40,  141,  257,  283,  131,
  261,  261,  147,  263,  264,   40,   40,  139,  268,   46,
  270,  271,   59,  257,  123,  275,  276,  271,  261,  263,
  264,   59,  266,  283,  268,  123,  270,  271,   41,  257,
  271,  275,  276,   41,   44,  263,  264,  262,   44,  283,
  268,  125,  270,  271,  271,   59,  260,  275,  276,   41,
   41,   43,   43,   45,   45,  283,   41,   41,   43,   43,
   45,   45,   41,   59,   43,  272,   45,  275,  271,   59,
   40,   59,   40,   59,    4,   -1,  271,  272,  273,   59,
   -1,   88,  277,  285,  286,  287,   -1,   -1,  285,  286,
  287,  279,   -1,  285,  286,  287,   -1,   -1,  265,  279,
  274,  285,  286,  287,  271,   -1,  271,  272,  273,  279,
   -1,   -1,  279,  285,  286,  287,
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

//#line 138 "gramatica.y"


//#line 369 "Parser.java"
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

void yyerror(String mensaje) {
  // funcion utilizada para imprimir errores que produce yacc
  System.out.println("Error yacc: " + mensaje);
}
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
case 2:
//#line 20 "gramatica.y"
//{agregarError("falta un begin");}
break;
case 21:
//#line 57 "gramatica.y"
//{System.out.println(recuperar_lexema(val_peek(2)));}
break;
case 34:
//#line 93 "gramatica.y"
//{yyval = val_peek(2).ival + val_peek(0).ival;}
break;
case 35:
//#line 94 "gramatica.y"
//{yyval = val_peek(2) - val_peek(0);}
break;
case 38:
//#line 99 "gramatica.y"
//{yyval = val_peek(2) * val_peek(0);}
break;
case 39:
//#line 100 "gramatica.y"
//{yyval = val_peek(2) / val_peek(0);}
break;
case 41:
//#line 104 "gramatica.y"
{yyval = val_peek(0);}
break;
case 42:
//#line 105 "gramatica.y"
{yyval = val_peek(0);}
break;
case 43:
//#line 106 "gramatica.y"
//{yyval = -val_peek(0);}
break;
case 44:
//#line 107 "gramatica.y"
{yyval = val_peek(0);}
break;
case 45:
//#line 108 "gramatica.y"
//{yyval = -val_peek(0);}
break;
case 46:
//#line 109 "gramatica.y"
{yyval = val_peek(0);}
break;
//#line 566 "Parser.java"
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
  this.lector = lex;
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
