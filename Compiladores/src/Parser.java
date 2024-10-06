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

import java.math.BigDecimal;
//#line 21 "Parser.java"




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
public final static short LONGINT=272;
public final static short HEXA=273;
public final static short CML=274;
public final static short DOUBLE=275;
public final static short TOD=276;
public final static short STRUCT=277;
public final static short ASIGNACION=278;
public final static short DISTINTO=279;
public final static short MENOR_IGUAL=280;
public final static short MAYOR_IGUAL=281;
public final static short ETIQUETA=282;
public final static short LOWER_THAN_ELSE=283;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    0,    0,    0,    1,    1,    2,    2,
    2,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    8,    8,    8,    8,    8,
    8,    7,    7,    7,    7,    7,    7,    7,    7,    4,
    4,    4,    4,    4,    4,    4,    4,   15,   15,   15,
   15,   14,   14,   14,   14,   14,   14,   14,   11,   11,
   11,   11,   11,   11,   11,   11,   11,   11,   10,   10,
   19,   16,   20,   20,   13,   13,   13,   13,   13,   13,
   13,   13,   13,   13,   13,   13,   17,   17,   12,   18,
   18,   18,   18,   18,   18,   18,   18,   18,   18,   23,
   23,   23,   23,   23,   23,   23,   23,   23,   23,   23,
   24,   24,   24,   24,   24,   24,   24,   24,    6,    6,
    6,    6,   25,   22,   22,    9,    9,    9,    5,    5,
    5,    5,   21,   21,   21,   21,   21,
};
final static short yylen[] = {                            2,
    4,    3,    2,    3,    2,    2,    2,    1,    1,    1,
    1,    3,    2,    1,    8,    7,    8,    6,    7,    7,
    7,   10,    8,    9,    9,    3,    2,    3,    2,    3,
    2,    9,    8,    8,    8,    7,    8,    8,    8,    1,
    1,    1,    1,    3,    2,    2,    2,    7,    6,    6,
    6,    5,    5,    4,    4,    4,    3,    3,    5,    6,
    4,    4,    3,    4,    4,    4,    5,    5,    2,    1,
    4,    3,    2,    1,    8,   10,    7,    7,    4,    4,
    4,    9,    9,    9,    9,    9,    3,    1,    4,    3,
    3,    4,    4,    2,    2,    2,    4,    3,    1,    3,
    3,    4,    4,    4,    4,    2,    2,    2,    2,    1,
    1,    1,    1,    2,    1,    2,    1,    2,    3,    3,
    1,    1,    3,    3,    1,    3,    2,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    3,    0,    0,    0,    0,    0,
    0,  130,  131,  129,    0,    0,    8,    9,   10,    0,
    0,   14,   40,   41,   42,   43,  121,    0,    0,    0,
    0,    0,  113,  115,    0,  117,    0,    0,    0,    0,
    0,    0,    0,  111,    0,  110,    0,    0,    0,    0,
    0,    0,    0,    0,   46,    0,   47,    7,    0,    0,
    0,    0,    0,    0,    2,    0,    0,    0,    0,    0,
    0,    0,   57,    0,    0,  114,  116,  118,  109,  108,
    0,    0,    0,    0,    0,   58,    0,    0,    0,  132,
    0,  128,    0,    0,    0,    0,    0,    0,   74,    0,
    0,    0,   44,  123,    0,    0,    0,   12,    0,    0,
    0,  120,    1,   79,   81,   80,    0,    0,  137,  136,
  135,  133,  134,    0,    0,    0,   98,    0,    0,   54,
    0,    0,    0,    0,    0,    0,    0,  100,    0,    0,
  101,    0,    0,    0,    0,    0,  127,    0,    0,    0,
    0,   72,   73,    0,    0,    0,    0,    0,    0,   89,
    0,    0,    0,    0,    0,   71,   97,   52,   53,    0,
    0,  102,  103,  105,  104,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  126,    0,    0,   69,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   27,    0,   31,    0,   29,    0,    0,    0,    0,
    0,   18,    0,    0,    0,    0,    0,   51,    0,   49,
    0,    0,    0,    0,    0,    0,    0,    0,   78,    0,
    0,    0,    0,   77,   26,   30,   28,   21,   19,   16,
    0,    0,    0,    0,   20,    0,    0,   48,    0,    0,
    0,    0,    0,    0,    0,   36,    0,    0,    0,    0,
    0,   75,    0,   15,   17,    0,   23,    0,   33,   37,
   39,    0,   38,    0,   63,    0,    0,    0,    0,   35,
   34,   85,   84,    0,   86,   82,    0,   25,   24,   32,
   64,    0,   65,   62,    0,   66,    0,   76,   22,   59,
   67,    0,   68,   60,
};
final static short yydgoto[] = {                          3,
  225,   17,   18,   19,   20,   21,   22,  179,   93,  151,
  226,   23,   24,   25,   26,   53,   69,   70,   44,  100,
  124,   71,   45,   46,   27,
};
final static short yysindex[] = {                      -155,
  416,  443,    0,  -18,    0,  166,  -56, -201, -178,   -2,
   67,    0,    0,    0,   80,  640,    0,    0,    0, -222,
  -41,    0,    0,    0,    0,    0,    0,  640,  580, -110,
  541,  116,    0,    0,  105,    0,  129,  421, -169,  372,
  372,  534,  112,    0,   90,    0,  -85,  389,  -68,  -68,
  157,  -91,  -20,  160,    0,  -37,    0,    0,  -30,   32,
   35,  563,  -32,  600,    0,  170,  -24,  -10,  249,  -26,
  264,  563,    0,  548,   90,    0,    0,    0,    0,    0,
  259,  251,  133,  348,  348,    0,  358,  358,  -77,    0,
  -68,    0,  364,  194,  275,  -68,   67,   80,    0, -128,
  280,  563,    0,    0,  -12,  -68,  -68,    0,  130,   56,
   67,    0,    0,    0,    0,    0, -178,  -72,    0,    0,
    0,    0,    0,  563,  563,  169,    0,  356,  262,    0,
  269,  421,   90,  421,   90,  372,  372,    0,  372,  372,
    0, -105,  -83,  384,   59,  -68,    0, -105,  212,   69,
  298,    0,    0,  557,  300,  340,   88,  309,  314,    0,
   98, -115,  108,  130,  130,    0,    0,    0,    0,   90,
   90,    0,    0,    0,    0,  -15,   -5,  -19,  243,  393,
 -105,  318,  257,   -7,    0,  263,   59,    0,  150,  341,
  413,  371,  196,  420,  207,  205,  214,  408, -178,  234,
  417,    0,  223,    0,  213,    0,  239,  469,  471,  -11,
  424,    0,   59,  284,  497,   -6,  207,    0,  498,    0,
  207,  -40,  521,  563,  620,  297,  207,  207,    0,  303,
  -44,  512,  311,    0,    0,    0,    0,    0,    0,    0,
  519,  526,    3,  528,    0,  321,  319,    0,  333,  187,
  342,  563,   28,  573,  527,    0,  346,  361,  542,  550,
   15,    0,  558,    0,    0,   -1,    0,  565,    0,    0,
    0,  365,    0,  106,    0,  568,  575,  563,  256,    0,
    0,    0,    0,  579,    0,    0,  583,    0,    0,    0,
    0,  587,    0,    0,  316,    0,  589,    0,    0,    0,
    0,  590,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   10,    0,    0,    0,   41,  632,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  650,    0,
    0,  -36,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  440,    0,    0,    0,    0,    0,
    0,    0,    0,   61,    0,    0,    0,    0,    0,   21,
   81,    0,    0,  652,    0,    0,    0,    0,    0,   12,
  612,    0,    0,    0,  447,    0,    0,    0,    0,    0,
    0,    0,    0,  460,  467,    0,  -29,  140,    0,    0,
    0,    0,    0,    0,    0,    0,  -42,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   68,    0,
    1,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  101,    0,
  121,    0,  472,    0,  480,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -14,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  613,   18,    0,    0,    0,    0,  492,
  500,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  141,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  161,    0,    0,    0,    0,    0,    0,
    0,  397,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   74,   70,    0,   -8,    2,  518,    0,  -58,    5,  -39,
  -97,    0,    0,    0,    0,  523,  -70,  373,    0,    0,
    0,  605,    4,   23,  606,
};
final static int YYTABLESIZE=922;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        224,
  119,  122,   63,   50,  112,  112,  112,  112,  112,  106,
  112,  107,  107,  107,  107,  107,   84,  107,   85,  102,
  122,   31,  112,  112,  207,  112,   70,  156,  203,  107,
  107,  155,  107,  122,  116,  123,   63,   63,  205,  180,
   11,   75,   59,   99,  119,  142,   63,  240,   60,   92,
   94,   92,  125,  122,   95,  125,   55,  288,  124,  119,
   45,  124,   79,   80,  122,  157,  158,  159,  276,   51,
   84,  107,   85,  285,   16,   29,  124,   56,   63,  122,
   13,   52,  122,  191,  182,   58,  275,  133,  135,  186,
  143,  153,   92,  108,  147,  144,  147,  150,   58,  125,
   56,   64,   76,   77,    1,   78,  150,  150,  150,  138,
  141,  125,   56,  241,  160,    2,  194,  214,  246,  247,
   55,  210,  211,  249,  251,  119,  125,  266,    4,  257,
  258,   87,  152,   58,    6,  170,   88,  171,   57,    9,
   50,   10,   97,  199,   52,  147,  292,  185,   84,   52,
   85,   66,  272,   98,   84,   72,   85,  150,  172,  173,
   83,  174,  175,   73,  291,    4,  176,  177,   74,  178,
   86,    6,   84,  131,   85,   84,    9,   85,   10,   97,
  106,  106,  106,  106,  106,  162,  106,   52,  176,  177,
   98,  178,   89,   90,   12,   13,   96,   14,  106,  106,
  181,  106,   90,   12,   13,   42,   14,   40,   38,  166,
   39,   84,   41,   85,   47,   52,    4,  260,  103,  250,
   48,   49,    6,    7,    8,  223,  224,    9,  114,   10,
   11,   12,   13,  104,   14,  122,   62,  115,  111,   30,
  105,   15,  112,  112,  112,   70,  224,  117,  101,  107,
  107,  107,  119,  120,  121,  206,  202,  119,   90,   12,
   13,  119,   14,  119,  119,  119,  119,  204,  119,  287,
  119,  119,  119,  119,  132,  119,  284,  122,  119,   54,
  132,  122,  119,  122,  122,  122,  122,  122,  122,  118,
  122,  122,  122,  122,   58,  122,  297,   11,   84,  129,
   85,   11,  122,   11,   11,   11,   11,  125,   11,  130,
   11,   11,   11,   11,  296,   11,  148,   45,  146,  154,
  168,   45,   11,   45,   45,   45,   45,  169,   45,   97,
   45,   45,   45,   45,  187,   45,  149,   13,  189,  188,
  192,   13,   45,   13,   13,   13,   13,  195,   13,  196,
   13,   13,   13,   13,  197,   13,  302,   56,   84,  198,
   85,   56,   13,   56,   56,   56,   56,  208,   56,  201,
   56,   56,   56,   56,  301,   56,  212,   55,   43,  213,
  193,   55,   56,   55,   55,   55,   55,  215,   55,   40,
   55,   55,   55,   55,   41,   55,  167,   50,   84,  218,
   85,   50,   55,   50,   50,   50,   50,  146,   50,  217,
   50,   50,   50,   50,   83,   50,   39,   83,  106,  106,
  106,   83,   50,   83,   83,   83,   83,  146,   83,  220,
   83,   83,   83,   83,  109,   83,   32,   33,   34,   35,
   36,   37,   83,    4,  126,  183,  128,  271,   91,    6,
    7,    8,  223,  219,    9,  221,   10,   11,   12,   13,
  222,   14,   40,    4,  227,   39,  229,   41,   15,    6,
    7,    8,  223,  228,    9,  234,   10,   11,   12,   13,
   99,   14,   99,   99,   99,  236,  145,   94,   15,   94,
   94,   94,  231,   52,  235,  232,  164,  165,   99,   99,
   95,   99,   95,   95,   95,   94,   94,   96,   94,   96,
   96,   96,   90,  237,   90,   90,   90,  209,   95,   95,
   91,   95,   91,   91,   91,   96,   96,  238,   96,  239,
   90,   90,   92,   90,   92,   92,   92,   61,   91,   91,
   93,   91,   93,   93,   93,   90,   12,   13,  242,   14,
   92,   92,   67,   92,  244,  245,  248,  256,   93,   93,
  252,   93,   40,   38,  259,   39,  278,   41,   40,   38,
  262,   39,  263,   41,   82,   40,   38,  264,   39,  269,
   41,   68,   40,   38,  265,   39,  267,   41,  127,   40,
   38,  268,   39,  270,   41,  253,  254,  190,   40,   38,
  282,   39,  273,   41,   40,   38,  280,   39,  283,   41,
   90,   12,   13,  277,   14,   84,  286,   85,   32,   33,
   34,  281,   36,  289,  274,  290,  293,  279,   32,   33,
   34,    6,   36,  294,   90,   12,   13,  298,   14,  161,
  163,  299,   32,   33,   34,  300,   36,  303,  304,    5,
  295,    4,   88,   87,   90,   12,   13,   61,   14,   90,
   12,   13,  184,   14,  176,  177,  110,  178,  112,    0,
    0,    0,    4,    0,    0,    0,    5,    0,    6,    7,
    8,    0,    0,    9,  200,   10,   11,   12,   13,    0,
   14,   32,   33,   34,    0,   36,    0,   15,    0,    4,
    0,    0,   28,    0,  216,    6,    7,    8,    0,    0,
    9,    0,   10,   11,   12,   13,    0,   14,   99,   99,
   99,  230,  233,    0,   15,   94,   94,   94,    0,    0,
  243,    0,    0,    0,    0,    0,    0,    0,   95,   95,
   95,    0,    0,    0,    0,   96,   96,   96,    0,    0,
   90,   90,   90,  261,    0,    0,    0,    0,   91,   91,
   91,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   92,   92,   92,    0,    0,    0,    0,    0,   93,   93,
   93,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   32,   33,   34,    0,   36,   37,   32,   33,   34,
    0,   36,   37,    0,   32,   33,   34,   81,   36,   37,
    0,   32,   33,   34,    0,   36,   37,    0,   32,   33,
   34,    0,   36,   37,    0,    0,    0,   32,   33,   34,
    0,   36,   37,   32,   33,   34,    4,   36,   37,    0,
   65,    0,    6,    7,    8,    0,    0,    9,    0,   10,
   11,   12,   13,    0,   14,    0,    4,    0,    0,    0,
  113,   15,    6,    7,    8,    0,    0,    9,    0,   10,
   11,   12,   13,    0,   14,    0,    4,    0,    0,    0,
    0,   15,    6,    7,    8,  255,    0,    9,    0,   10,
   11,   12,   13,    0,   14,    0,    4,    0,    0,    0,
    0,   15,    6,    7,    8,    0,    0,    9,    0,   10,
   11,   12,   13,    0,   14,    0,    0,    0,    0,    0,
    0,   15,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   44,   44,   60,   41,   42,   43,   44,   45,   40,
   47,   41,   42,   43,   44,   45,   43,   47,   45,   40,
    0,   40,   59,   60,   44,   62,   41,   40,   44,   59,
   60,  102,   62,   60,   59,   62,   44,   44,   44,  123,
    0,   38,  265,   52,   44,  123,   44,   59,  271,   48,
   49,   50,   41,   44,   50,   44,   59,   59,   41,   59,
    0,   44,   40,   41,   44,  105,  106,  107,   41,  271,
   43,   40,   45,   59,    1,    2,   59,   46,   44,   59,
    0,  260,  125,  154,  143,   16,   59,   84,   85,  148,
   89,  100,   91,   59,   93,   91,   95,   96,   29,   44,
    0,   28,  272,  273,  260,  275,  105,  106,  107,   87,
   88,   44,   46,  125,   59,  271,  156,  125,  125,  217,
    0,  180,  181,  221,  222,  125,   59,  125,  257,  227,
  228,   42,  261,   64,  263,  132,   47,  134,   59,  268,
    0,  270,  271,  259,  260,  144,   41,  146,   43,  260,
   45,  262,  250,  282,   43,   40,   45,  156,  136,  137,
    0,  139,  140,   59,   59,  257,  272,  273,   40,  275,
   59,  263,   43,   41,   45,   43,  268,   45,  270,  271,
   41,   42,   43,   44,   45,  258,   47,  260,  272,  273,
  282,  275,  278,  271,  272,  273,   40,  275,   59,   60,
  284,   62,  271,  272,  273,   40,  275,   42,   43,   41,
   45,   43,   47,   45,  271,  260,  257,  262,   59,  260,
  277,  278,  263,  264,  265,  266,   40,  268,   59,  270,
  271,  272,  273,  271,  275,  278,  278,  262,  271,  258,
  271,  282,  279,  280,  281,  260,   40,  258,  269,  279,
  280,  281,  279,  280,  281,  275,  272,  257,  271,  272,
  273,  261,  275,  263,  264,  265,  266,  273,  268,  271,
  270,  271,  272,  273,  265,  275,  262,  257,  278,  282,
  271,  261,  282,  263,  264,  265,  266,  278,  268,   41,
  270,  271,  272,  273,  225,  275,   41,  257,   43,   41,
   45,  261,  282,  263,  264,  265,  266,   44,  268,   59,
  270,  271,  272,  273,   59,  275,  123,  257,   44,   40,
   59,  261,  282,  263,  264,  265,  266,   59,  268,  271,
  270,  271,  272,  273,  123,  275,   62,  257,   41,  271,
   41,  261,  282,  263,  264,  265,  266,  260,  268,   41,
  270,  271,  272,  273,   41,  275,   41,  257,   43,  262,
   45,  261,  282,  263,  264,  265,  266,  125,  268,  262,
  270,  271,  272,  273,   59,  275,   59,  257,    6,  123,
   41,  261,  282,  263,  264,  265,  266,  125,  268,   42,
  270,  271,  272,  273,   47,  275,   41,  257,   43,   59,
   45,  261,  282,  263,  264,  265,  266,   44,  268,  260,
  270,  271,  272,  273,   42,  275,   45,  257,  279,  280,
  281,  261,  282,  263,  264,  265,  266,   44,  268,   59,
  270,  271,  272,  273,   62,  275,  271,  272,  273,  274,
  275,  276,  282,  257,   72,   62,   74,  261,   60,  263,
  264,  265,  266,   41,  268,  260,  270,  271,  272,  273,
   41,  275,   42,  257,  260,   45,   59,   47,  282,  263,
  264,  265,  266,  260,  268,   59,  270,  271,  272,  273,
   41,  275,   43,   44,   45,  273,  123,   41,  282,   43,
   44,   45,  259,  260,  272,  262,  124,  125,   59,   60,
   41,   62,   43,   44,   45,   59,   60,   41,   62,   43,
   44,   45,   41,  275,   43,   44,   45,  125,   59,   60,
   41,   62,   43,   44,   45,   59,   60,   59,   62,   59,
   59,   60,   41,   62,   43,   44,   45,   20,   59,   60,
   41,   62,   43,   44,   45,  271,  272,  273,  125,  275,
   59,   60,   30,   62,  271,   59,   59,  261,   59,   60,
   40,   62,   42,   43,  262,   45,   40,   47,   42,   43,
   59,   45,  262,   47,   41,   42,   43,   59,   45,  261,
   47,   41,   42,   43,   59,   45,   59,   47,   41,   42,
   43,  271,   45,  261,   47,  223,  224,   41,   42,   43,
   59,   45,  261,   47,   42,   43,  261,   45,   59,   47,
  271,  272,  273,   41,  275,   43,   59,   45,  271,  272,
  273,  261,  275,   59,  252,  261,   59,  255,  271,  272,
  273,    0,  275,   59,  271,  272,  273,   59,  275,  117,
  118,   59,  271,  272,  273,   59,  275,   59,   59,    0,
  278,    0,   41,   41,  271,  272,  273,  261,  275,  271,
  272,  273,  145,  275,  272,  273,   62,  275,   63,   -1,
   -1,   -1,  257,   -1,   -1,   -1,  261,   -1,  263,  264,
  265,   -1,   -1,  268,  162,  270,  271,  272,  273,   -1,
  275,  271,  272,  273,   -1,  275,   -1,  282,   -1,  257,
   -1,   -1,  260,   -1,  187,  263,  264,  265,   -1,   -1,
  268,   -1,  270,  271,  272,  273,   -1,  275,  279,  280,
  281,  199,  200,   -1,  282,  279,  280,  281,   -1,   -1,
  213,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  279,  280,
  281,   -1,   -1,   -1,   -1,  279,  280,  281,   -1,   -1,
  279,  280,  281,  231,   -1,   -1,   -1,   -1,  279,  280,
  281,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  279,  280,  281,   -1,   -1,   -1,   -1,   -1,  279,  280,
  281,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  271,  272,  273,   -1,  275,  276,  271,  272,  273,
   -1,  275,  276,   -1,  271,  272,  273,  274,  275,  276,
   -1,  271,  272,  273,   -1,  275,  276,   -1,  271,  272,
  273,   -1,  275,  276,   -1,   -1,   -1,  271,  272,  273,
   -1,  275,  276,  271,  272,  273,  257,  275,  276,   -1,
  261,   -1,  263,  264,  265,   -1,   -1,  268,   -1,  270,
  271,  272,  273,   -1,  275,   -1,  257,   -1,   -1,   -1,
  261,  282,  263,  264,  265,   -1,   -1,  268,   -1,  270,
  271,  272,  273,   -1,  275,   -1,  257,   -1,   -1,   -1,
   -1,  282,  263,  264,  265,  266,   -1,  268,   -1,  270,
  271,  272,  273,   -1,  275,   -1,  257,   -1,   -1,   -1,
   -1,  282,  263,  264,  265,   -1,   -1,  268,   -1,  270,
  271,  272,  273,   -1,  275,   -1,   -1,   -1,   -1,   -1,
   -1,  282,
};
}
final static short YYFINAL=3;
final static short YYMAXTOKEN=284;
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
"OUTF","TYPEDEF","FUN","RET","STRING","REPEAT","WHILE","GOTO","ID","LONGINT",
"HEXA","CML","DOUBLE","TOD","STRUCT","ASIGNACION","DISTINTO","MENOR_IGUAL",
"MAYOR_IGUAL","ETIQUETA","LOWER_THAN_ELSE","\"\"",
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
"sentencia : sentencia_declaracion",
"sentencia : sentencia_ejecucion",
"sentencia : ETIQUETA",
"sentencia_declaracion : tipo lista_variables ';'",
"sentencia_declaracion : tipo lista_variables",
"sentencia_declaracion : declaracion_funcion",
"sentencia_declaracion : TYPEDEF ID ASIGNACION tipo '{' subrango '}' ';'",
"sentencia_declaracion : TYPEDEF ID ASIGNACION tipo '{' subrango ';'",
"sentencia_declaracion : TYPEDEF ID ASIGNACION tipo \"\" subrango '}' ';'",
"sentencia_declaracion : TYPEDEF ID ASIGNACION tipo subrango ';'",
"sentencia_declaracion : TYPEDEF ID ASIGNACION tipo '{' '}' ';'",
"sentencia_declaracion : TYPEDEF ASIGNACION tipo '{' subrango '}' ';'",
"sentencia_declaracion : TYPEDEF ID ASIGNACION '{' subrango '}' ';'",
"sentencia_declaracion : TYPEDEF STRUCT '<' lista_tipos '>' '{' lista_variables '}' ID ';'",
"sentencia_declaracion : TYPEDEF STRUCT lista_tipos '{' lista_variables '}' ID ';'",
"sentencia_declaracion : TYPEDEF '<' lista_tipos '>' '{' lista_variables '}' ID ';'",
"sentencia_declaracion : TYPEDEF STRUCT '<' lista_tipos '>' '{' lista_variables '}' ';'",
"subrango : LONGINT ',' LONGINT",
"subrango : LONGINT LONGINT",
"subrango : DOUBLE ',' DOUBLE",
"subrango : DOUBLE DOUBLE",
"subrango : HEXA ',' HEXA",
"subrango : HEXA HEXA",
"declaracion_funcion : tipo FUN ID '(' parametro ')' BEGIN cuerpo_funcion END",
"declaracion_funcion : FUN ID '(' parametro ')' BEGIN cuerpo_funcion END",
"declaracion_funcion : tipo ID '(' parametro ')' BEGIN cuerpo_funcion END",
"declaracion_funcion : tipo FUN '(' parametro ')' BEGIN cuerpo_funcion END",
"declaracion_funcion : tipo FUN ID parametro BEGIN cuerpo_funcion END",
"declaracion_funcion : tipo FUN ID '(' ')' BEGIN cuerpo_funcion END",
"declaracion_funcion : tipo FUN ID '(' parametro ')' cuerpo_funcion END",
"declaracion_funcion : tipo FUN ID '(' parametro ')' BEGIN END",
"sentencia_ejecucion : asignacion",
"sentencia_ejecucion : condicion_if",
"sentencia_ejecucion : sentencia_print",
"sentencia_ejecucion : sentencia_while",
"sentencia_ejecucion : GOTO ETIQUETA ';'",
"sentencia_ejecucion : GOTO ETIQUETA",
"sentencia_ejecucion : GOTO ';'",
"sentencia_ejecucion : ETIQUETA ';'",
"sentencia_while : REPEAT bloque_sentencia_ejecutable WHILE '(' condicion ')' ';'",
"sentencia_while : REPEAT bloque_sentencia_ejecutable '(' condicion ')' ';'",
"sentencia_while : REPEAT bloque_sentencia_ejecutable WHILE '(' condicion ')'",
"sentencia_while : REPEAT bloque_sentencia_ejecutable WHILE '(' ')' ';'",
"sentencia_print : OUTF '(' CML ')' ';'",
"sentencia_print : OUTF '(' expresion ')' ';'",
"sentencia_print : OUTF '(' ')' ';'",
"sentencia_print : OUTF '(' expresion ')'",
"sentencia_print : OUTF '(' CML ')'",
"sentencia_print : OUTF CML ';'",
"sentencia_print : OUTF expresion ';'",
"cuerpo_funcion : RET '(' expresion ')' ';'",
"cuerpo_funcion : cuerpo RET '(' expresion ')' ';'",
"cuerpo_funcion : RET '(' expresion ')'",
"cuerpo_funcion : '(' expresion ')' ';'",
"cuerpo_funcion : RET expresion ';'",
"cuerpo_funcion : RET '(' expresion ';'",
"cuerpo_funcion : RET expresion ')' ';'",
"cuerpo_funcion : cuerpo RET expresion ';'",
"cuerpo_funcion : cuerpo RET '(' expresion ';'",
"cuerpo_funcion : cuerpo RET expresion ')' ';'",
"parametro : tipo ID",
"parametro : tipo",
"invocacion_funcion : ID '(' expresion ')'",
"bloque_sentencia_ejecutable : BEGIN lista_sentencias END",
"lista_sentencias : lista_sentencias sentencia_ejecucion",
"lista_sentencias : sentencia_ejecucion",
"condicion_if : IF '(' condicion ')' THEN bloque_sentencia_ejecutable END_IF ';'",
"condicion_if : IF '(' condicion ')' THEN bloque_sentencia_ejecutable ELSE bloque_sentencia_ejecutable END_IF ';'",
"condicion_if : IF '(' condicion ')' bloque_sentencia_ejecutable END_IF ';'",
"condicion_if : IF '(' ')' THEN bloque_sentencia_ejecutable END_IF ';'",
"condicion_if : IF THEN END_IF ';'",
"condicion_if : IF THEN bloque_sentencia_ejecutable ';'",
"condicion_if : IF THEN bloque_sentencia_ejecutable END_IF",
"condicion_if : IF '(' condicion ')' THEN bloque_sentencia_ejecutable bloque_sentencia_ejecutable END_IF ';'",
"condicion_if : IF '(' condicion ')' THEN bloque_sentencia_ejecutable ELSE bloque_sentencia_ejecutable END_IF",
"condicion_if : IF '(' condicion ')' THEN bloque_sentencia_ejecutable ELSE END_IF ';'",
"condicion_if : IF '(' condicion ')' THEN ELSE bloque_sentencia_ejecutable END_IF ';'",
"condicion_if : IF '(' condicion ')' THEN bloque_sentencia_ejecutable ELSE bloque_sentencia_ejecutable ';'",
"condicion : expresion comparador expresion",
"condicion : lista_expresiones",
"asignacion : lista_variables ASIGNACION lista_expresiones ';'",
"expresion : expresion '+' termino",
"expresion : expresion '-' termino",
"expresion : expresion '+' '+' termino",
"expresion : expresion '-' '+' termino",
"expresion : '+' termino",
"expresion : expresion '+'",
"expresion : expresion '-'",
"expresion : TOD '(' expresion ')'",
"expresion : TOD '(' ')'",
"expresion : termino",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : termino '*' '*' factor",
"termino : termino '*' '/' factor",
"termino : termino '/' '/' factor",
"termino : termino '/' '*' factor",
"termino : termino '/'",
"termino : termino '*'",
"termino : '/' factor",
"termino : '*' factor",
"termino : factor",
"factor : invocacion_funcion",
"factor : ID",
"factor : LONGINT",
"factor : '-' LONGINT",
"factor : HEXA",
"factor : '-' HEXA",
"factor : DOUBLE",
"factor : '-' DOUBLE",
"lista_variables : lista_variables ',' ID",
"lista_variables : lista_variables ',' id_compuesta",
"lista_variables : id_compuesta",
"lista_variables : ID",
"id_compuesta : ID '.' ID",
"lista_expresiones : lista_expresiones ',' expresion",
"lista_expresiones : expresion",
"lista_tipos : lista_tipos ',' tipo",
"lista_tipos : lista_tipos tipo",
"lista_tipos : tipo",
"tipo : DOUBLE",
"tipo : LONGINT",
"tipo : HEXA",
"tipo : ID",
"comparador : '<'",
"comparador : '>'",
"comparador : MAYOR_IGUAL",
"comparador : MENOR_IGUAL",
"comparador : DISTINTO",
};

//#line 309 "gramatica.y"


void yyerror(String mensaje) {
  // funcion utilizada para imprimir errores que produce yacc
  System.out.println("Error yacc: " + mensaje);

}
//#line 631 "Parser.java"
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
case 1:
//#line 19 "gramatica.y"
{System.out.println(val_peek(3));}
break;
case 2:
//#line 20 "gramatica.y"
{System.out.println("ERROR,falta begin programa principal");}
break;
case 3:
//#line 21 "gramatica.y"
{System.out.println("ERROR,falta el ID del programa principal");}
break;
case 4:
//#line 22 "gramatica.y"
{System.out.println("ERROR,falta END del programa principal");}
break;
case 5:
//#line 23 "gramatica.y"
{System.out.println("ERROR,falta BEGIN,END del programa principal");}
break;
case 6:
//#line 24 "gramatica.y"
{System.out.println("ERROR,falta ID,END del programa principal");}
break;
case 12:
//#line 36 "gramatica.y"
{System.out.println(val_peek(1));}
break;
case 13:
//#line 37 "gramatica.y"
{System.out.println("ERROR, Falta ; en la sentencia de declaracion");}
break;
case 15:
//#line 39 "gramatica.y"
{System.out.println("Declaracion de Subtipo");}
break;
case 16:
//#line 40 "gramatica.y"
{System.out.println("Error, Falta de llaves '{}'");}
break;
case 17:
//#line 41 "gramatica.y"
{System.out.println("Error, Falta de llaves '{}'");}
break;
case 18:
//#line 42 "gramatica.y"
{System.out.println("Error, Falta de llaves '{}'");}
break;
case 19:
//#line 43 "gramatica.y"
{System.out.println("ERROR, Falta de rango");}
break;
case 20:
//#line 44 "gramatica.y"
{System.out.println("ERROR, Falta nombre del tipo definido");}
break;
case 21:
//#line 45 "gramatica.y"
{System.out.println("ERROR, Falta el tipo base");}
break;
case 22:
//#line 46 "gramatica.y"
{System.out.println("Declaracion de Struct");}
break;
case 23:
//#line 47 "gramatica.y"
{System.out.println("ERROR, Falta <>.");}
break;
case 24:
//#line 48 "gramatica.y"
{System.out.println("ERROR, Falta la palabra STRUCT.");}
break;
case 25:
//#line 49 "gramatica.y"
{System.out.println("ERROR,Falta  ID al final de la declaracion");}
break;
case 27:
//#line 53 "gramatica.y"
{System.out.println("ERROR,Falta ','entre los digitos del subrango");}
break;
case 29:
//#line 55 "gramatica.y"
{System.out.println("ERROR,Falta ','entre los digitos del subrango");}
break;
case 31:
//#line 57 "gramatica.y"
{System.out.println("ERROR,Falta ','entre los digitos del subrango");}
break;
case 32:
//#line 60 "gramatica.y"
{System.out.println("Declaracion de Funcion");}
break;
case 33:
//#line 61 "gramatica.y"
{System.out.println("ERROR,Falta la declaracion del tipo de la FUN ");}
break;
case 34:
//#line 62 "gramatica.y"
{System.out.println("ERROR,Falta la declaracion de la palabra reservada FUN");}
break;
case 35:
//#line 63 "gramatica.y"
{System.out.println("ERROR,Falta el ID de la funcion");}
break;
case 36:
//#line 64 "gramatica.y"
{System.out.println("ERROR,Falta de () a la hora de los parametros");}
break;
case 37:
//#line 65 "gramatica.y"
{System.out.println("ERROR,Falta de parametros en la FUN");}
break;
case 38:
//#line 66 "gramatica.y"
{System.out.println("ERROR,Falta de BEGIN en la FUN");}
break;
case 39:
//#line 68 "gramatica.y"
{System.out.println("ERROR,Falsa cuerpo de funcion ");}
break;
case 44:
//#line 75 "gramatica.y"
{System.out.println("Declaracion de GOTO ");}
break;
case 45:
//#line 76 "gramatica.y"
{System.out.println("ERROR, Falta ';' al final de la declaracion  ");}
break;
case 46:
//#line 77 "gramatica.y"
{System.out.println("ERROR,falta la ETIQUETA  ");}
break;
case 47:
//#line 78 "gramatica.y"
{System.out.println("ERROR,falta el GOTO  ");}
break;
case 48:
//#line 80 "gramatica.y"
{System.out.println("Declaracion REPEAT-WHILE");}
break;
case 49:
//#line 82 "gramatica.y"
{System.out.println("ERROR,falta palabra WHILE");}
break;
case 50:
//#line 83 "gramatica.y"
{System.out.println("ERROR,falta palabra ';' al final de la declaracion ");}
break;
case 51:
//#line 84 "gramatica.y"
{System.out.println("ERROR,falta falta la condicion del WHILE ");}
break;
case 52:
//#line 87 "gramatica.y"
{System.out.println(val_peek(2).sval);}
break;
case 54:
//#line 89 "gramatica.y"
{System.out.println("Falta parámetro en sentencia OUTF");}
break;
case 55:
//#line 90 "gramatica.y"
{System.out.println("Falta ';' en la sentencia OUTF");}
break;
case 56:
//#line 91 "gramatica.y"
{System.out.println("Falta ';' en la sentencia OUTF");}
break;
case 57:
//#line 92 "gramatica.y"
{System.out.println("Faltan los parentesis en la sentencia OUTF");}
break;
case 58:
//#line 93 "gramatica.y"
{System.out.println("Faltan los parentesis en la sentencia OUTF");}
break;
case 59:
//#line 98 "gramatica.y"
{System.out.println("Declaracion del Cuerpo de la funcion");}
break;
case 60:
//#line 99 "gramatica.y"
{System.out.println("Declaracion del Cuerpo de la funcion");}
break;
case 61:
//#line 100 "gramatica.y"
{System.out.println("ERROR, falta ';' al final de la declaracion");}
break;
case 62:
//#line 101 "gramatica.y"
{System.out.println("ERROR, falta RET de la funcion");}
break;
case 63:
//#line 102 "gramatica.y"
{System.out.println("ERROR, falta '()' a la hora de realizar el RET");}
break;
case 64:
//#line 103 "gramatica.y"
{System.out.println("ERROR, falta ')' a la hora de realizar el RET");}
break;
case 65:
//#line 104 "gramatica.y"
{System.out.println("ERROR, falta '(' a la hora de realizar el RET");}
break;
case 66:
//#line 105 "gramatica.y"
{System.out.println("ERROR, falta '()' a la hora de realizar el RET");}
break;
case 67:
//#line 106 "gramatica.y"
{System.out.println("ERROR, falta ')' a la hora de realizar el RET");}
break;
case 68:
//#line 107 "gramatica.y"
{System.out.println("ERROR, falta '(' a la hora de realizar el RET");}
break;
case 70:
//#line 112 "gramatica.y"
{System.out.println("ERROR, falta ID del parametro");}
break;
case 71:
//#line 116 "gramatica.y"
{if (val_peek(3).sval.equals(null)){ System.out.println("No existe una funcion con ese nombre");}}
break;
case 77:
//#line 130 "gramatica.y"
{System.out.println("ERROR, Falta THEN luego de la condicion");}
break;
case 78:
//#line 131 "gramatica.y"
{System.out.println("ERROR,falta de Condicion");}
break;
case 79:
//#line 132 "gramatica.y"
{System.out.println("ERROR,falta el bloque ejecutable");}
break;
case 80:
//#line 133 "gramatica.y"
{System.out.println("ERROR,falta END_IF al final de la declaracion");}
break;
case 81:
//#line 134 "gramatica.y"
{System.out.println("ERROR,falta ';' al final de la declaracion");}
break;
case 82:
//#line 135 "gramatica.y"
{System.out.println("ERROR, falta ELSE luego de la sentencias de ejecucion");}
break;
case 83:
//#line 136 "gramatica.y"
{System.out.println("ERROR,falta ';' al final de la declaracion");}
break;
case 84:
//#line 137 "gramatica.y"
{System.out.println("ERROR, falta el bloque ejecutable en el ELSE");}
break;
case 85:
//#line 138 "gramatica.y"
{System.out.println("ERROR, falta el bloque ejecutable en el IF");}
break;
case 86:
//#line 139 "gramatica.y"
{System.out.println("ERROR,falta END_IF al final de la declaracion");}
break;
case 88:
//#line 144 "gramatica.y"
{System.out.println("ERROR, falta comparador en comparacion");}
break;
case 89:
//#line 147 "gramatica.y"
{yyval= val_peek(1);}
break;
case 90:
//#line 150 "gramatica.y"
{yyval.ival = val_peek(2).ival + val_peek(0).ival;}
break;
case 91:
//#line 151 "gramatica.y"
{yyval.ival = val_peek(2).ival - val_peek(0).ival;}
break;
case 92:
//#line 152 "gramatica.y"
{System.out.println("ERROR, hay 2 operadores");}
break;
case 93:
//#line 153 "gramatica.y"
{System.out.println("ERROR, hay 2 operadores");}
break;
case 94:
//#line 155 "gramatica.y"
{System.out.println("ERROR, falta de operando");}
break;
case 95:
//#line 156 "gramatica.y"
{System.out.println("ERROR, falta de operando");}
break;
case 96:
//#line 157 "gramatica.y"
{System.out.println("ERROR, falta de operando");}
break;
case 97:
//#line 158 "gramatica.y"
{System.out.println("Declaracion de TOD");}
break;
case 98:
//#line 159 "gramatica.y"
{System.out.println("ERROR, falta de expresion");}
break;
case 99:
//#line 160 "gramatica.y"
{yyval.ival = val_peek(0).ival;}
break;
case 102:
//#line 166 "gramatica.y"
{System.out.println("ERROR, hay 2 operadores");}
break;
case 103:
//#line 167 "gramatica.y"
{System.out.println("ERROR, hay 2 operadores");}
break;
case 104:
//#line 168 "gramatica.y"
{System.out.println("ERROR, hay 2 operadores");}
break;
case 105:
//#line 169 "gramatica.y"
{System.out.println("ERROR, hay 2 operadores");}
break;
case 106:
//#line 170 "gramatica.y"
{System.out.println("ERROR, falta de operando");}
break;
case 107:
//#line 171 "gramatica.y"
{System.out.println("ERROR, falta de operando");}
break;
case 108:
//#line 172 "gramatica.y"
{System.out.println("ERROR, falta de operando");}
break;
case 109:
//#line 173 "gramatica.y"
{System.out.println("ERROR, falta de operando");}
break;
case 110:
//#line 174 "gramatica.y"
{yyval= val_peek(0);}
break;
case 112:
//#line 180 "gramatica.y"
{yyval = val_peek(0);  System.out.println("la variable" + val_peek(0).sval + "tiene valor: " + val_peek(0).ival);}
break;
case 113:
//#line 181 "gramatica.y"
{
                                                    yyval = val_peek(0);
                                                    Long valor = Long.parseLong(val_peek(0).sval);
                                                    if (valor == 2147483648L){
                                                        yyerror("El número está fuera del rango permitido para un longint positivo.");
                                                    }
                                                    int token =   this.LONGINT;
                                                    lector.tablaSimbolos.addToken(val_peek(0).sval,token,"LONGINT");
                                                }
break;
case 114:
//#line 190 "gramatica.y"
{
                                                    yyval = val_peek(0); /*TODO: posible error*/
                                                    String lexema = '-'+ val_peek(0).sval;
                                                    int token =   this.LONGINT;
                                                    lector.tablaSimbolos.addToken(val_peek(0).sval,token,"LONGINT");
                                                }
break;
case 115:
//#line 197 "gramatica.y"
{
                                                    yyval = val_peek(0);
                                                    String hexa = val_peek(0).sval;
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
break;
case 116:
//#line 211 "gramatica.y"
{
                                                    yyval = val_peek(0);
                                                    String lexema = '-'+ val_peek(0).sval;
                                                    int token =   this.HEXA;
                                                    lector.tablaSimbolos.addToken(val_peek(0).sval,token,"HEXA");
                                                }
break;
case 117:
//#line 217 "gramatica.y"
{
                                                    yyval = val_peek(0) ; /* valor del número*/
                                                    String valor = val_peek(0).sval;
                                                    System.out.println("Llegué para DOUBLE positivo: " + valor);
                                                    String valorConvertido = valor.replace("d", "E");
                                                    System.out.println("Llegué para DOUBLE positivo Replace: " + valorConvertido);

                                                    try {
                                                        /* Convertir la cadena a un tipo double para evitar problemas de formato con BigDecimal.*/
                                                        double numero = Double.parseDouble(valorConvertido);
                                                        double min = 2.2250738585072014E-308;
                                                        double max = 1.7976931348623157E+308;

                                                        /* Comparamos el número contra los límites permitidos.*/
                                                        if (numero > max || (numero < min && numero != 0.0)) {
                                                            yyerror("El número está fuera del rango permitido para un double positivo.");
                                                        } else {
                                                            String valorC = valor.replace("E", "d");
                                                            double numeroC = -Double.parseDouble(valorC);
                                                            int token = this.DOUBLE;
                                                            lector.tablaSimbolos.addToken(Double.toString(numeroC), token, "DOUBLE");
                                                        }
                                                    } catch (NumberFormatException e) {
                                                        /* Control de error en caso de que la conversión a double falle.*/
                                                        yyerror("Formato de número inválido: " + valorConvertido);
                                                    }

                                                }
break;
case 118:
//#line 245 "gramatica.y"
{
                                                    yyval = val_peek(0);
                                                    String valor = val_peek(0).sval;
                                                    System.out.println("llegue para DOUBLE negativo: " + valor);
                                                    String valorConvertido = valor.replace("d", "E");

                                                    try {
                                                        /* Convertimos el valor a double y lo negamos.*/
                                                        double numero = -Double.parseDouble(valorConvertido); /* Negamos el valor.*/
                                                        double min = -1.7976931348623157E+308;
                                                        double max = -2.2250738585072014E-308;

                                                        /* Comparamos el número contra los límites permitidos.*/
                                                        if (numero > max || numero < min) {
                                                            yyerror("El número está fuera del rango permitido para un double negativo.");
                                                        } else {
                                                            String valorC = valor.replace("E", "d");
                                                            double numeroC = -Double.parseDouble(valorC);
                                                            int token = this.DOUBLE;
                                                            lector.tablaSimbolos.addToken(Double.toString(numeroC), token, "DOUBLE");

                                                        }
                                                    } catch (NumberFormatException e) {
                                                        /* Control de error en caso de que la conversión a double falle.*/
                                                        yyerror("Formato de número inválido: " + valorConvertido);
                                                    }
                                                }
break;
case 122:
//#line 280 "gramatica.y"
{System.out.println(val_peek(0).sval);}
break;
case 127:
//#line 292 "gramatica.y"
{System.out.println("ERROR, falta de ',' en la lista ");}
break;
//#line 1248 "Parser.java"
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
