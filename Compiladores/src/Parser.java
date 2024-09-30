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




import java.io.*;
//#line 23 "Parser.java"




public class Parser
{
AnalizadorLexico lexico;
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
    0,    1,    1,    2,    2,    2,    3,    3,    3,    3,
    8,    8,    7,    4,    4,    4,    4,    4,   14,   14,
   11,   11,   10,   18,   18,   19,   15,   15,   13,   13,
   16,   12,   17,   17,   17,   17,   22,   22,   22,   23,
   23,   23,   23,   23,    6,    6,    6,    6,   21,   21,
    9,    9,    5,    5,    5,   20,   20,   20,   20,   20,
};
final static short yylen[] = {                            2,
    4,    2,    1,    1,    1,    1,    2,    1,    8,   10,
    3,    3,    9,    1,    1,    1,    7,    3,    3,    5,
    1,    5,    2,    1,    1,    4,    4,    3,    6,    8,
    5,    4,    3,    3,    4,    1,    3,    3,    1,    1,
    1,    2,    1,    2,    3,    5,    3,    1,    3,    1,
    3,    1,    1,    1,    1,    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   54,
   53,    6,    0,    3,    4,    5,    0,    0,    8,   14,
   15,   16,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    1,    2,    0,    0,    0,    0,    0,    0,
   41,   43,    0,    0,    0,    0,   24,    0,   39,    0,
   19,   40,    0,    0,    0,    0,    0,    0,   18,   47,
    0,   50,    0,    0,    0,    0,   42,   44,    0,    0,
   56,   57,   58,   59,   60,    0,    0,    0,    0,    0,
   55,    0,   52,    0,   28,    0,    0,    0,   32,    0,
    0,    0,    0,    0,    0,    0,   37,   38,    0,    0,
   20,    0,    0,    0,   27,    0,    0,    0,   49,   46,
   26,   35,   31,    0,   29,    0,    0,    0,    0,   51,
    0,   23,    0,    0,    0,    0,    0,    0,   17,    0,
   30,   11,   12,    9,    0,    0,   21,    0,    0,    0,
   13,   10,    0,    0,   22,
};
final static short yydgoto[] = {                          2,
   13,   14,   15,   16,   17,   18,   19,  118,   84,  108,
  138,   20,   21,   22,   30,   24,   45,   46,   47,   76,
   63,   48,   49,
};
final static short yysindex[] = {                      -227,
 -203,    0, -116,   27,  -15, -224, -186, -201,   40,    0,
    0,    0, -195,    0,    0,    0, -213,  -22,    0,    0,
    0,    0,  -34, -165,   47,   39, -166,   60, -146, -143,
   84, -125,    0,    0, -118,   40,  112,  -34, -109,  126,
    0,    0,  128, -145,   62,   32,    0,   36,    0, -186,
    0,    0,   44, -212, -212,  -91,  -99,  135,    0,    0,
  136,    0,    6,  131,   39,   39,    0,    0,   -4,   -4,
    0,    0,    0,    0,    0,  -34,   -4,   -4, -147,  119,
    0,   56,    0,   -2,    0,  -81,   27, -212,    0,  -34,
  -90,   57,   78,   36,   36,  141,    0,    0, -186,  124,
    0, -139,   61, -212,    0,  144,  -85,  146,    0,    0,
    0,    0,    0,  -74,    0,  145,  147,   65,  -79,    0,
  134,    0,  -66,  137,  -77,  -78,  139,   -7,    0, -167,
    0,    0,    0,    0,  -72,  160,    0,  -60,  143,   39,
    0,    0,  120,  148,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,   28,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    9, -126,    0,    0,  -41,
    0,    0,    0,    0,  -24,    0,    0,  -36,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   26,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -31,  -11,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    2,    0,   14,   -9,   10,    0,    0,    0,    0,
    0,    0,    0,    0,   11,  116,  -10,    1,    0,    0,
    0,   70,   96,
};
final static int YYTABLESIZE=319;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         40,
   40,   40,   40,   40,   36,   40,   36,   36,   36,   33,
   44,   33,   33,   33,   34,   53,   25,   40,   40,   25,
   40,   39,   36,   36,   26,   36,   37,   33,   33,   34,
   33,   34,   34,   34,   25,   25,   39,   25,   62,   57,
   44,  104,   56,    1,   82,   83,   27,   34,   34,   90,
   34,   35,   48,   28,   92,   93,    3,   36,   81,  103,
   79,    4,   10,   11,   89,   33,   23,    5,    6,   45,
   86,   48,    7,   29,    8,    9,   96,   77,  107,   10,
   11,   31,   78,   44,   80,   32,   69,   12,   70,    4,
  109,   71,   50,   72,  120,    5,    6,  111,  136,   69,
    7,   70,    8,    9,   69,   51,   70,   10,   11,  114,
    4,   99,   54,   29,  100,   12,    5,  135,  112,   55,
   69,    7,   70,    8,   36,   58,   67,   68,  128,  143,
    7,  137,  116,   48,    7,  117,    7,    7,   94,   95,
    4,    7,   59,    7,    7,   60,    5,    6,    7,    7,
   45,    7,   61,    8,    9,   39,    7,    4,   10,   11,
  144,   64,   69,    5,   70,   65,   12,   66,    7,   85,
    8,   36,   97,   98,   87,   88,   91,  101,  102,  105,
  110,  113,  115,  119,  121,  122,  123,  124,  125,  127,
  126,   36,  129,  130,  132,  131,  133,  134,  139,  140,
  141,  142,  106,    0,    0,    0,  145,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   40,   41,   42,    0,
    0,    0,   43,   40,   40,   40,    0,    0,   36,   36,
   36,    0,    0,   33,   33,   33,   38,    0,   25,    0,
   25,   25,   25,    0,    0,   48,   52,   41,   42,   48,
    0,   48,   48,   34,   34,   34,   48,    0,   48,   48,
    0,    0,   45,   48,   48,    0,   45,   48,   45,   45,
    0,   48,   55,   45,    0,   45,   45,    0,   55,    0,
   45,   45,    0,    0,   45,    0,   48,    0,   45,   52,
   41,   42,    0,    0,    0,   43,   73,   74,   75,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   44,   45,   41,   47,   43,   44,   45,   41,
   45,   43,   44,   45,   13,   26,   41,   59,   60,   44,
   62,   44,   59,   60,   40,   62,   17,   59,   60,   41,
   62,   43,   44,   45,   59,   60,   44,   62,   38,   29,
   45,   44,   29,  271,   54,   55,  271,   59,   60,   44,
   62,  265,   44,  278,   65,   66,  260,  271,  271,   62,
   50,  257,  275,  276,   59,  261,   40,  263,  264,   44,
   57,   44,  268,  260,  270,  271,   76,   42,   88,  275,
  276,  283,   47,   45,   41,   46,   43,  283,   45,  257,
   90,   60,  258,   62,  104,  263,  264,   41,  266,   43,
  268,   45,  270,  271,   43,   59,   45,  275,  276,   99,
  257,  259,  279,  260,  262,  283,  263,  125,   41,   60,
   43,  268,   45,  270,  271,  269,  272,  273,  119,  140,
  257,  130,  272,  125,  261,  275,  263,  264,   69,   70,
  257,  268,   59,  270,  271,  271,  263,  264,  275,  276,
  125,  268,  271,  270,  271,   44,  283,  257,  275,  276,
   41,  271,   43,  263,   45,   40,  283,   40,  268,  261,
  270,  271,   77,   78,   40,   40,   46,   59,  123,  261,
  271,   41,   59,  123,   41,  271,   41,  262,   44,  125,
   44,  271,   59,  260,  272,   59,  275,   59,  271,   40,
  261,   59,   87,   -1,   -1,   -1,   59,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  271,  272,  273,   -1,
   -1,   -1,  277,  285,  286,  287,   -1,   -1,  285,  286,
  287,   -1,   -1,  285,  286,  287,  279,   -1,  274,   -1,
  285,  286,  287,   -1,   -1,  257,  271,  272,  273,  261,
   -1,  263,  264,  285,  286,  287,  268,   -1,  270,  271,
   -1,   -1,  257,  275,  276,   -1,  261,  279,  263,  264,
   -1,  283,  265,  268,   -1,  270,  271,   -1,  271,   -1,
  275,  276,   -1,   -1,  279,   -1,  279,   -1,  283,  271,
  272,  273,   -1,   -1,   -1,  277,  285,  286,  287,
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
"cuerpo : cuerpo sentencia",
"cuerpo : sentencia",
"sentencia : sentencia_declaracion",
"sentencia : sentencia_ejecucion",
"sentencia : ETIQUETA",
"sentencia_declaracion : tipo lista_variables",
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
"cuerpo_funcion : sentencia",
"cuerpo_funcion : RET '(' expresion ')' ';'",
"parametro : tipo ID",
"expresion_aritmetica : invocacion_funcion",
"expresion_aritmetica : expresion",
"invocacion_funcion : ID '(' expresion ')'",
"bloque_sentencia_ejecutable : BEGIN bloque_sentencia_ejecutable sentencia_ejecucion END",
"bloque_sentencia_ejecutable : BEGIN sentencia_ejecucion END",
"condicion_if : IF condicion THEN bloque_sentencia_ejecutable END_IF ';'",
"condicion_if : IF condicion THEN bloque_sentencia_ejecutable ELSE bloque_sentencia_ejecutable END_IF ';'",
"condicion : '(' expresion_aritmetica comparador expresion_aritmetica ')'",
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
"lista_variables : lista_variables ',' ID",
"lista_variables : lista_variables ',' ID '.' ID",
"lista_variables : ID '.' ID",
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
int yylex(){

  return 0;
  }


  int 

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
        yychar = lexico.yylex();  //get next token
          System.out.println("llego: " + yychar);
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
        yychar = lexico.yylex();        //get next character
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
  lexico = lex;
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
