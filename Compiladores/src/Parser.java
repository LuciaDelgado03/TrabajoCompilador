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
//#line 19 "Parser.java"




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
    4,    4,    4,    4,    4,    4,    4,    4,    4,    4,
   14,   14,   14,   14,   14,   14,   14,   11,   11,   11,
   11,   11,   11,   11,   11,   11,   11,   10,   10,   18,
   15,   15,   13,   13,   13,   13,   13,   13,   13,   13,
   13,   13,   13,   13,   16,   16,   16,   12,   17,   17,
   17,   17,   17,   17,   17,   17,   17,   17,   21,   21,
   21,   21,   21,   21,   21,   21,   21,   21,   21,   22,
   22,   22,   22,   22,   22,   22,   22,    6,    6,    6,
    6,   23,   20,   20,    9,    9,    9,    5,    5,    5,
    5,   19,   19,   19,   19,   19,
};
final static short yylen[] = {                            2,
    4,    3,    2,    3,    2,    2,    2,    1,    1,    1,
    1,    3,    2,    1,    8,    7,    8,    6,    7,    7,
    7,   10,    8,    9,    9,    3,    2,    3,    2,    3,
    2,    9,    8,    8,    8,    7,    8,    8,    8,    1,
    1,    1,    7,    6,    6,    6,    3,    2,    2,    2,
    5,    5,    4,    4,    4,    3,    3,    5,    6,    4,
    4,    3,    4,    4,    4,    5,    5,    2,    1,    4,
    4,    3,    8,   10,    7,    7,    4,    4,    4,    9,
    9,    9,    9,    9,    5,    2,    4,    4,    3,    3,
    4,    4,    2,    2,    2,    4,    3,    1,    3,    3,
    4,    4,    4,    4,    2,    2,    2,    2,    1,    1,
    1,    1,    2,    1,    2,    1,    2,    3,    3,    1,
    1,    3,    3,    1,    3,    2,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    3,    0,    0,    0,    0,    0,
    0,  129,  130,  128,    0,    0,    8,    9,   10,    0,
    0,   14,   40,   41,   42,  120,    0,    0,    0,    0,
    0,  112,  114,    0,  116,    0,    0,    0,    0,    0,
    0,    0,  110,    0,  109,    0,    0,    0,    0,    0,
    0,    0,    0,   49,    0,   50,    7,    0,    0,    0,
    0,    0,    0,    2,    0,    0,    0,    0,    0,    0,
    0,   56,    0,    0,  113,  115,  117,  108,  107,    0,
    0,    0,    0,    0,   57,    0,    0,    0,  131,    0,
  127,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   47,  122,    0,    0,    0,   12,    0,    0,    0,
  119,    1,   77,   79,   78,   86,    0,    0,    0,  132,
  133,  134,  135,  136,    0,    0,   97,    0,    0,   53,
    0,    0,    0,    0,    0,    0,    0,   99,    0,    0,
  100,    0,    0,    0,    0,    0,  126,    0,    0,    0,
    0,   72,    0,    0,    0,    0,    0,    0,    0,   88,
    0,    0,    0,    0,    0,    0,   70,   96,   51,   52,
    0,    0,  101,  102,  104,  103,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  125,    0,    0,   68,    0,
   71,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   87,   27,    0,   31,    0,
   29,    0,    0,    0,    0,    0,   18,    0,    0,    0,
    0,    0,   46,    0,   44,    0,    0,    0,    0,    0,
    0,    0,    0,   85,   76,    0,    0,    0,    0,   75,
   26,   30,   28,   21,   19,   16,    0,    0,    0,    0,
   20,    0,    0,   43,    0,    0,    0,    0,    0,    0,
    0,   36,    0,    0,    0,    0,    0,   73,    0,   15,
   17,    0,   23,    0,   33,   37,   39,    0,   38,    0,
   62,    0,    0,    0,    0,   35,   34,   83,   82,    0,
   84,   80,    0,   25,   24,   32,   63,    0,   64,   61,
    0,   65,    0,   74,   22,   58,   66,    0,   67,   59,
};
final static short yydgoto[] = {                          3,
  230,   17,   18,   19,   20,   21,   22,  180,   92,  151,
  231,   23,   24,   25,   52,   69,   70,   43,  125,  109,
   44,   45,   26,
};
final static short yysindex[] = {                      -220,
  492,  568,    0,   22,    0,  397,  378, -236, -216,  -12,
   39,    0,    0,    0,   -3,  648,    0,    0,    0, -127,
  -44,    0,    0,    0,    0,    0,  648,  588, -108,  450,
   52,    0,    0,   45,    0,   60,  471,  193,  343,  343,
  525,  105,    0,  120,    0, -156,  391,  375,  375,   91,
  447,  -38,   94,    0, -106,    0,    0,  -20,  126,   -7,
  551,  -97,  608,    0,  129,   -9,  532,  -66,  157,  176,
  551,    0,  539,  120,    0,    0,    0,    0,    0,  164,
  143,  144,  348,  348,    0,  403,  403,  -89,    0,  375,
    0,  333,   92,  355,  375,   39,   -3,  -51,  354,  180,
  506,    0,    0,  317,  375,  375,    0,  152,    5,   39,
    0,    0,    0,    0,    0,    0,  176, -216,   50,    0,
    0,    0,    0,    0,  551,  440,    0,  554,  167,    0,
  269,  471,  120,  471,  120,  343,  343,    0,  343,  343,
    0,  337, -104,  366,   59,  375,    0,  337,  212,   77,
  309,    0,  109,  500,  314,  -32,  108,  334,  338,    0,
  551,  551,  119,  274,  135,  616,    0,    0,    0,    0,
  120,  120,    0,    0,    0,    0,  -18,   -5,  -16,  275,
  -72,  337,  342,  285,  -17,    0,  305,   59,    0,  186,
    0,  393,  416,  410,  214,  435,  257,  224,  236,  152,
  636,  445, -216,  392,  452,    0,    0,  234,    0,  242,
    0,  249,  458,  467,  -23,  406,    0,   59,  266,  479,
    2,  257,    0,  485,    0,  257,  207,  512,  551,  628,
  289,  257,  257,    0,    0,  294,   55,  503,  302,    0,
    0,    0,    0,    0,    0,    0,  510,  517,    4,  519,
    0,  300,  330,    0,  339,  237,  346,  551,   87,  639,
  518,    0,  352,  368,  572,  574,   28,    0,  575,    0,
    0,   24,    0,  581,    0,    0,    0,  384,    0,  104,
    0,  594,  601,  551,  114,    0,    0,    0,    0,  606,
    0,    0,  627,    0,    0,    0,    0,  629,    0,    0,
  542,    0,  632,    0,    0,    0,    0,  633,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   10,    0,    0,    0,   41,  667,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  693,    0,    0,
  -37,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   14,    0,    0,    0,    0,    0,    0,
    0,    0,   61,    0,    0,    0,    0,    0,   21,   81,
    0,    0,  694,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   34,    0,    0,    0,    0,    0,    0,
    0,    0,   54,   74,    0,  -30,  134,    0,    0,    0,
    0,    0,    0,    0,    0,  -41,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   23,    0,    1,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  101,    0,
  121,    0,  163,    0,  168,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   -8,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  173,  201,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   44,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  141,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  161,
    0,    0,    0,    0,    0,    0,    0,  434,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  110,  107,    0,  -27,  595,   11,    0,  -57,   17,  -36,
  476,    0,    0,    0,  -13,  -83,  374,    0,  579,    0,
   26,    3,  635,
};
final static int YYTABLESIZE=930;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         62,
  118,  101,  121,  111,  111,  111,  111,  111,  195,  111,
  106,  106,  106,  106,  106,   66,  106,  155,  181,  105,
  121,  111,  111,   98,  111,  208,   62,  212,  106,  106,
   60,  106,   69,  142,   50,  246,   62,   99,  210,    1,
   11,   78,   79,   51,  118,   62,   54,   62,  161,  115,
    2,  107,  214,  121,   98,   56,   98,   98,   98,  118,
   48,   30,   74,  160,  121,   94,  124,  157,  158,  159,
  193,  153,   98,   98,   93,   98,   93,   93,   93,  121,
   13,  124,  294,  121,   55,  183,  291,  123,  138,  141,
  187,   71,   93,   93,   94,   93,   94,   94,   94,   73,
   55,  247,  123,   72,  163,  165,  144,  219,  133,  135,
   16,   28,   94,   94,   95,   94,   95,   95,   95,  196,
   54,   88,   57,  215,  216,  118,  252,  282,  272,   83,
   95,   84,   95,   95,   57,   95,   63,   58,  173,  174,
   45,  175,  176,   59,  298,  281,   83,   83,   84,   84,
  204,   51,  102,   65,  303,  185,   83,  171,   84,  172,
   81,   86,  297,   85,  103,  106,   87,  177,  178,   57,
  179,   55,  302,  110,  105,  105,  105,  105,  105,  182,
  105,   89,   12,   13,  131,   14,   83,  113,   84,  236,
  239,  118,  105,  105,   83,  105,   84,  119,  221,  177,
  178,  130,  179,   89,  129,   89,   89,   89,   90,  152,
   90,   90,   90,   91,  148,   91,   91,   91,   83,  154,
   84,   89,   89,  267,   89,  169,   90,   90,  249,   90,
  100,   91,   91,   61,   91,  120,  121,  121,   89,   12,
   13,   92,   14,   92,   92,   92,  229,  111,  111,  111,
  104,   69,  114,  207,  106,  106,  106,  118,  211,   92,
   92,  118,   92,  118,  118,  118,  118,  209,  118,   53,
  118,  118,  118,  118,  131,  118,  229,  121,  118,   29,
  131,  121,  118,  121,  121,  121,  121,  121,  121,  290,
  121,  121,  121,  121,  293,  121,  229,   11,   98,   98,
   98,   11,  121,   11,   11,   11,   11,  164,   11,   51,
   11,   11,   11,   11,   51,   11,  266,   48,   93,   93,
   93,   48,   11,   48,   48,   48,   48,  170,   48,   96,
   48,   48,   48,   48,  188,   48,   57,   13,   94,   94,
   94,   13,   48,   13,   13,   13,   13,  189,   13,  190,
   13,   13,   13,   13,  194,   13,  156,   55,   95,   95,
   95,   55,   13,   55,   55,   55,   55,  197,   55,  191,
   55,   55,   55,   55,  198,   55,  146,   54,  199,   42,
  202,   54,   55,   54,   54,   54,   54,   38,   54,   39,
   54,   54,   54,   54,   40,   54,  205,   45,  146,  213,
  217,   45,   54,   45,   45,   45,   45,  218,   45,  146,
   45,   45,   45,   45,   82,   45,  149,   81,  105,  105,
  105,   81,   45,   81,   81,   81,   81,  184,   81,  220,
   81,   81,   81,   81,  108,   81,   41,   49,   39,   37,
  117,   38,   81,   40,  126,  222,  128,   89,   89,   89,
   90,  223,   90,   90,   90,  145,  224,   91,   91,   91,
  122,  123,  124,    4,   75,   76,  256,   77,  225,    6,
    7,    8,  228,  226,    9,  227,   10,   11,   12,   13,
  167,   14,   83,  232,   84,   92,   92,   92,   15,   67,
   68,   39,   37,    4,   38,  233,   40,  277,  166,    6,
    7,    8,  228,  235,    9,  241,   10,   11,   12,   13,
  240,   14,   39,    4,  242,   38,  244,   40,   15,    6,
    7,    8,  228,  243,    9,  245,   10,   11,   12,   13,
  248,   14,  203,   51,  200,  201,  250,  251,   15,   67,
  192,   39,   37,  254,   38,   67,   40,   39,   37,  262,
   38,  258,   40,   39,   37,  265,   38,  284,   40,   39,
   37,  268,   38,  269,   40,   81,   39,   37,  270,   38,
  274,   40,  116,   39,   37,  271,   38,  273,   40,  127,
   39,   37,  308,   38,   83,   40,   84,   89,   12,   13,
  275,   14,   39,   37,  168,   38,   83,   40,   84,  276,
  307,  259,  260,   89,   12,   13,  279,   14,  177,  178,
    4,  179,  286,   31,   32,   33,    6,   35,   31,   32,
   33,    9,   35,   10,   96,   89,   12,   13,  287,   14,
  288,  280,  289,  292,  285,   97,   89,   12,   13,  295,
   14,   91,   93,   91,  296,   89,   12,   13,   46,   14,
  237,   51,  299,  238,   47,   48,  206,  301,   83,  300,
   84,   89,   12,   13,  304,   14,    6,   31,   32,   33,
   34,   35,   36,   31,   32,   33,  234,   35,   83,  283,
   84,   83,  143,   84,   91,  305,  147,  306,  147,  150,
  309,  310,    5,    4,   60,  162,  111,  253,  150,  150,
  150,  255,  257,    4,    0,    0,   51,  263,  264,    6,
    0,    0,    0,    0,    9,    0,   10,   96,    0,    0,
   31,   32,   33,    0,   35,   36,    0,    0,   97,    0,
    0,  278,    0,    0,    0,    0,    0,    0,  147,    0,
  186,   31,   32,   33,    0,   35,    0,    0,    4,    0,
  150,    0,    5,    0,    6,    7,    8,    0,    0,    9,
    0,   10,   11,   12,   13,    0,   14,    0,    0,    0,
   31,   32,   33,   15,   35,   36,   31,   32,   33,    0,
   35,   36,   31,   32,   33,    0,   35,   36,   31,   32,
   33,    0,   35,   36,    0,   31,   32,   33,   80,   35,
   36,    0,   31,   32,   33,    0,   35,   36,    0,   31,
   32,   33,    0,   35,   36,    0,    0,    0,    0,    0,
    0,   31,   32,   33,    4,   35,   36,   27,    0,    0,
    6,    7,    8,    0,    0,    9,    0,   10,   11,   12,
   13,    0,   14,    0,    4,    0,    0,    0,   64,   15,
    6,    7,    8,    0,    0,    9,    0,   10,   11,   12,
   13,    0,   14,    0,    4,    0,    0,    0,  112,   15,
    6,    7,    8,    0,    0,    9,    0,   10,   11,   12,
   13,    0,   14,    0,    4,    0,    0,    0,    0,   15,
    6,    7,    8,  261,    0,    9,    0,   10,   11,   12,
   13,    0,   14,    0,    4,    0,    0,    0,    0,   15,
    6,    7,    8,    0,    0,    9,    0,   10,   11,   12,
   13,    0,   14,    0,    0,    0,    0,    0,    0,   15,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         44,
    0,   40,   44,   41,   42,   43,   44,   45,   41,   47,
   41,   42,   43,   44,   45,   29,   47,  101,  123,   40,
    0,   59,   60,   51,   62,   44,   44,   44,   59,   60,
   20,   62,   41,  123,  271,   59,   44,   51,   44,  260,
    0,   39,   40,  260,   44,   44,   59,   44,   44,   59,
  271,   59,  125,   44,   41,   59,   43,   44,   45,   59,
    0,   40,   37,   59,   44,   49,   44,  104,  105,  106,
  154,   99,   59,   60,   41,   62,   43,   44,   45,   59,
    0,   59,   59,  125,   46,  143,   59,   44,   86,   87,
  148,   40,   59,   60,   41,   62,   43,   44,   45,   40,
    0,  125,   59,   59,  118,  119,   90,  125,   83,   84,
    1,    2,   59,   60,   41,   62,   43,   44,   45,  156,
    0,  278,   16,  181,  182,  125,  125,   41,  125,   43,
   40,   45,   59,   60,   28,   62,   27,  265,  136,  137,
    0,  139,  140,  271,   41,   59,   43,   43,   45,   45,
  164,  260,   59,  262,   41,  145,   43,  132,   45,  134,
    0,   42,   59,   59,  271,   40,   47,  272,  273,   63,
  275,   46,   59,  271,   41,   42,   43,   44,   45,  284,
   47,  271,  272,  273,   41,  275,   43,   59,   45,  203,
  204,  258,   59,   60,   43,   62,   45,   41,  188,  272,
  273,   59,  275,   41,   41,   43,   44,   45,   41,  261,
   43,   44,   45,   41,  123,   43,   44,   45,   43,   40,
   45,   59,   60,  237,   62,   59,   59,   60,  218,   62,
  269,   59,   60,  278,   62,   60,  278,   62,  271,  272,
  273,   41,  275,   43,   44,   45,   40,  285,  286,  287,
  271,  260,  262,  272,  285,  286,  287,  257,  275,   59,
   60,  261,   62,  263,  264,  265,  266,  273,  268,  282,
  270,  271,  272,  273,  265,  275,   40,  257,  278,  258,
  271,  261,  282,  263,  264,  265,  266,  278,  268,  262,
  270,  271,  272,  273,  271,  275,   40,  257,  285,  286,
  287,  261,  282,  263,  264,  265,  266,  258,  268,  260,
  270,  271,  272,  273,  260,  275,  262,  257,  285,  286,
  287,  261,  282,  263,  264,  265,  266,   59,  268,  271,
  270,  271,  272,  273,  123,  275,  230,  257,  285,  286,
  287,  261,  282,  263,  264,  265,  266,  271,  268,   41,
  270,  271,  272,  273,   41,  275,   40,  257,  285,  286,
  287,  261,  282,  263,  264,  265,  266,  260,  268,  261,
  270,  271,  272,  273,   41,  275,   44,  257,   41,    6,
  262,  261,  282,  263,  264,  265,  266,   45,  268,   42,
  270,  271,  272,  273,   47,  275,  262,  257,   44,  125,
   59,  261,  282,  263,  264,  265,  266,  123,  268,   44,
  270,  271,  272,  273,   41,  275,   62,  257,  285,  286,
  287,  261,  282,  263,  264,  265,  266,   62,  268,  125,
  270,  271,  272,  273,   61,  275,   40,   60,   42,   43,
   67,   45,  282,   47,   71,  260,   73,  285,  286,  287,
   60,   59,  285,  286,  287,  123,   41,  285,  286,  287,
  285,  286,  287,  257,  272,  273,  260,  275,   59,  263,
  264,  265,  266,  260,  268,   41,  270,  271,  272,  273,
   41,  275,   43,  260,   45,  285,  286,  287,  282,   40,
   41,   42,   43,  257,   45,  260,   47,  261,  125,  263,
  264,  265,  266,   59,  268,  272,  270,  271,  272,  273,
   59,  275,   42,  257,  273,   45,   59,   47,  282,  263,
  264,  265,  266,  275,  268,   59,  270,  271,  272,  273,
  125,  275,  259,  260,  161,  162,  271,   59,  282,   40,
   41,   42,   43,   59,   45,   40,   47,   42,   43,  261,
   45,   40,   47,   42,   43,  262,   45,   40,   47,   42,
   43,   59,   45,  262,   47,   41,   42,   43,   59,   45,
  271,   47,   41,   42,   43,   59,   45,   59,   47,   41,
   42,   43,   41,   45,   43,   47,   45,  271,  272,  273,
  261,  275,   42,   43,   41,   45,   43,   47,   45,  261,
   59,  228,  229,  271,  272,  273,  261,  275,  272,  273,
  257,  275,  261,  271,  272,  273,  263,  275,  271,  272,
  273,  268,  275,  270,  271,  271,  272,  273,  261,  275,
   59,  258,   59,   59,  261,  282,  271,  272,  273,   59,
  275,   47,   48,   49,  261,  271,  272,  273,  271,  275,
  259,  260,   59,  262,  277,  278,   41,  284,   43,   59,
   45,  271,  272,  273,   59,  275,    0,  271,  272,  273,
  274,  275,  276,  271,  272,  273,   41,  275,   43,   41,
   45,   43,   88,   45,   90,   59,   92,   59,   94,   95,
   59,   59,    0,    0,  261,  117,   62,  222,  104,  105,
  106,  226,  227,  257,   -1,   -1,  260,  232,  233,  263,
   -1,   -1,   -1,   -1,  268,   -1,  270,  271,   -1,   -1,
  271,  272,  273,   -1,  275,  276,   -1,   -1,  282,   -1,
   -1,  256,   -1,   -1,   -1,   -1,   -1,   -1,  144,   -1,
  146,  271,  272,  273,   -1,  275,   -1,   -1,  257,   -1,
  156,   -1,  261,   -1,  263,  264,  265,   -1,   -1,  268,
   -1,  270,  271,  272,  273,   -1,  275,   -1,   -1,   -1,
  271,  272,  273,  282,  275,  276,  271,  272,  273,   -1,
  275,  276,  271,  272,  273,   -1,  275,  276,  271,  272,
  273,   -1,  275,  276,   -1,  271,  272,  273,  274,  275,
  276,   -1,  271,  272,  273,   -1,  275,  276,   -1,  271,
  272,  273,   -1,  275,  276,   -1,   -1,   -1,   -1,   -1,
   -1,  271,  272,  273,  257,  275,  276,  260,   -1,   -1,
  263,  264,  265,   -1,   -1,  268,   -1,  270,  271,  272,
  273,   -1,  275,   -1,  257,   -1,   -1,   -1,  261,  282,
  263,  264,  265,   -1,   -1,  268,   -1,  270,  271,  272,
  273,   -1,  275,   -1,  257,   -1,   -1,   -1,  261,  282,
  263,  264,  265,   -1,   -1,  268,   -1,  270,  271,  272,
  273,   -1,  275,   -1,  257,   -1,   -1,   -1,   -1,  282,
  263,  264,  265,  266,   -1,  268,   -1,  270,  271,  272,
  273,   -1,  275,   -1,  257,   -1,   -1,   -1,   -1,  282,
  263,  264,  265,   -1,   -1,  268,   -1,  270,  271,  272,
  273,   -1,  275,   -1,   -1,   -1,   -1,   -1,   -1,  282,
};
}
final static short YYFINAL=3;
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
"OUTF","TYPEDEF","FUN","RET","STRING","REPEAT","WHILE","GOTO","ID","LONGINT",
"HEXA","CML","DOUBLE","TOD","STRUCT","ASIGNACION","DISTINTO","MENOR_IGUAL",
"MAYOR_IGUAL","ETIQUETA","LOWER_THAN_ELSE","\"\"","\"MAYOR_IGUAL\"",
"\"MENOR_IGUAL\"","\"DISTINTO\"",
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
"sentencia_ejecucion : REPEAT bloque_sentencia_ejecutable WHILE '(' condicion ')' ';'",
"sentencia_ejecucion : REPEAT bloque_sentencia_ejecutable '(' condicion ')' ';'",
"sentencia_ejecucion : REPEAT bloque_sentencia_ejecutable WHILE '(' condicion ')'",
"sentencia_ejecucion : REPEAT bloque_sentencia_ejecutable WHILE '(' ')' ';'",
"sentencia_ejecucion : GOTO ETIQUETA ';'",
"sentencia_ejecucion : GOTO ETIQUETA",
"sentencia_ejecucion : GOTO ';'",
"sentencia_ejecucion : ETIQUETA ';'",
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
"bloque_sentencia_ejecutable : BEGIN bloque_sentencia_ejecutable sentencia_ejecucion END",
"bloque_sentencia_ejecutable : BEGIN sentencia_ejecucion END",
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
"condicion : '(' expresion comparador expresion ')'",
"condicion : '(' ')'",
"condicion : expresion comparador expresion ')'",
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
"comparador : \"MAYOR_IGUAL\"",
"comparador : \"MENOR_IGUAL\"",
"comparador : \"DISTINTO\"",
};

//#line 273 "gramatica.y"


void yyerror(String mensaje) {
  // funcion utilizada para imprimir errores que produce yacc
  System.out.println("Error yacc: " + mensaje);

AnalizadorLexico lex ;
}
//#line 633 "Parser.java"
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
//#line 17 "gramatica.y"
{System.out.println(val_peek(3));}
break;
case 2:
//#line 18 "gramatica.y"
{System.out.println("ERROR,falta begin programa principal");}
break;
case 3:
//#line 19 "gramatica.y"
{System.out.println("ERROR,falta el ID del programa principal");}
break;
case 4:
//#line 20 "gramatica.y"
{System.out.println("ERROR,falta END del programa principal");}
break;
case 5:
//#line 21 "gramatica.y"
{System.out.println("ERROR,falta BEGIN,END del programa principal");}
break;
case 6:
//#line 22 "gramatica.y"
{System.out.println("ERROR,falta ID,END del programa principal");}
break;
case 12:
//#line 34 "gramatica.y"
{System.out.println(val_peek(1));}
break;
case 13:
//#line 35 "gramatica.y"
{System.out.println("ERROR, Falta ; en la sentencia de declaracion");}
break;
case 15:
//#line 37 "gramatica.y"
{System.out.println("Declaracion de Subtipo");}
break;
case 16:
//#line 38 "gramatica.y"
{System.out.println("Error, Falta de llaves '{}'");}
break;
case 17:
//#line 39 "gramatica.y"
{System.out.println("Error, Falta de llaves '{}'");}
break;
case 18:
//#line 40 "gramatica.y"
{System.out.println("Error, Falta de llaves '{}'");}
break;
case 19:
//#line 41 "gramatica.y"
{System.out.println("ERROR, Falta de rango");}
break;
case 20:
//#line 42 "gramatica.y"
{System.out.println("ERROR, Falta nombre del tipo definido");}
break;
case 21:
//#line 43 "gramatica.y"
{System.out.println("ERROR, Falta el tipo base");}
break;
case 22:
//#line 44 "gramatica.y"
{System.out.println("Declaracion de Struct");}
break;
case 23:
//#line 45 "gramatica.y"
{System.out.println("ERROR, Falta <>.");}
break;
case 24:
//#line 46 "gramatica.y"
{System.out.println("ERROR, Falta la palabra STRUCT.");}
break;
case 25:
//#line 47 "gramatica.y"
{System.out.println("ERROR,Falta  ID al final de la declaracion");}
break;
case 27:
//#line 51 "gramatica.y"
{System.out.println("ERROR,Falta ','entre los digitos del subrango");}
break;
case 29:
//#line 53 "gramatica.y"
{System.out.println("ERROR,Falta ','entre los digitos del subrango");}
break;
case 31:
//#line 55 "gramatica.y"
{System.out.println("ERROR,Falta ','entre los digitos del subrango");}
break;
case 32:
//#line 58 "gramatica.y"
{System.out.println("Declaracion de Funcion");}
break;
case 33:
//#line 59 "gramatica.y"
{System.out.println("ERROR,Falta la declaracion del tipo de la FUN ");}
break;
case 34:
//#line 60 "gramatica.y"
{System.out.println("ERROR,Falta la declaracion de la palabra reservada FUN");}
break;
case 35:
//#line 61 "gramatica.y"
{System.out.println("ERROR,Falta el ID de la funcion");}
break;
case 36:
//#line 62 "gramatica.y"
{System.out.println("ERROR,Falta de () a la hora de los parametros");}
break;
case 37:
//#line 63 "gramatica.y"
{System.out.println("ERROR,Falta de parametros en la FUN");}
break;
case 38:
//#line 64 "gramatica.y"
{System.out.println("ERROR,Falta de BEGIN en la FUN");}
break;
case 39:
//#line 66 "gramatica.y"
{System.out.println("ERROR,Falsa cuerpo de funcion ");}
break;
case 43:
//#line 72 "gramatica.y"
{System.out.println("Declaracion REPEAT-WHILE");}
break;
case 44:
//#line 74 "gramatica.y"
{System.out.println("ERROR,falta palabra WHILE");}
break;
case 45:
//#line 75 "gramatica.y"
{System.out.println("ERROR,falta palabra ';' al final de la declaracion ");}
break;
case 46:
//#line 76 "gramatica.y"
{System.out.println("ERROR,falta falta la condicion del WHILE ");}
break;
case 47:
//#line 77 "gramatica.y"
{System.out.println("Declaracion de GOTO ");}
break;
case 48:
//#line 78 "gramatica.y"
{System.out.println("ERROR, Falta ';' al final de la declaracion  ");}
break;
case 49:
//#line 79 "gramatica.y"
{System.out.println("ERROR,falta la ETIQUETA  ");}
break;
case 50:
//#line 80 "gramatica.y"
{System.out.println("ERROR,falta el GOTO  ");}
break;
case 51:
//#line 83 "gramatica.y"
{System.out.println(val_peek(2).sval);}
break;
case 53:
//#line 85 "gramatica.y"
{System.out.println("Falta parámetro en sentencia OUTF");}
break;
case 54:
//#line 86 "gramatica.y"
{System.out.println("Falta ';' en la sentencia OUTF");}
break;
case 55:
//#line 87 "gramatica.y"
{System.out.println("Falta ';' en la sentencia OUTF");}
break;
case 56:
//#line 88 "gramatica.y"
{System.out.println("Faltan los parentesis en la sentencia OUTF");}
break;
case 57:
//#line 89 "gramatica.y"
{System.out.println("Faltan los parentesis en la sentencia OUTF");}
break;
case 58:
//#line 92 "gramatica.y"
{System.out.println("Declaracion del Cuerpo de la funcion");}
break;
case 59:
//#line 93 "gramatica.y"
{System.out.println("Declaracion del Cuerpo de la funcion");}
break;
case 60:
//#line 94 "gramatica.y"
{System.out.println("ERROR, falta ';' al final de la declaracion");}
break;
case 61:
//#line 95 "gramatica.y"
{System.out.println("ERROR, falta RET de la funcion");}
break;
case 62:
//#line 96 "gramatica.y"
{System.out.println("ERROR, falta '()' a la hora de realizar el RET");}
break;
case 63:
//#line 97 "gramatica.y"
{System.out.println("ERROR, falta ')' a la hora de realizar el RET");}
break;
case 64:
//#line 98 "gramatica.y"
{System.out.println("ERROR, falta '(' a la hora de realizar el RET");}
break;
case 65:
//#line 99 "gramatica.y"
{System.out.println("ERROR, falta '()' a la hora de realizar el RET");}
break;
case 66:
//#line 100 "gramatica.y"
{System.out.println("ERROR, falta ')' a la hora de realizar el RET");}
break;
case 67:
//#line 101 "gramatica.y"
{System.out.println("ERROR, falta '(' a la hora de realizar el RET");}
break;
case 69:
//#line 106 "gramatica.y"
{System.out.println("ERROR, falta ID del parametro");}
break;
case 70:
//#line 110 "gramatica.y"
{if (val_peek(3).sval.equals(null)){ System.out.println("No existe una funcion con ese nombre");}}
break;
case 75:
//#line 121 "gramatica.y"
{System.out.println("ERROR, Falta THEN luego de la condicion");}
break;
case 76:
//#line 122 "gramatica.y"
{System.out.println("ERROR,falta de Condicion");}
break;
case 77:
//#line 123 "gramatica.y"
{System.out.println("ERROR,falta el bloque ejecutable");}
break;
case 78:
//#line 124 "gramatica.y"
{System.out.println("ERROR,falta END_IF al final de la declaracion");}
break;
case 79:
//#line 125 "gramatica.y"
{System.out.println("ERROR,falta ';' al final de la declaracion");}
break;
case 80:
//#line 126 "gramatica.y"
{System.out.println("ERROR, falta ELSE luego de la sentencias de ejecucion");}
break;
case 81:
//#line 127 "gramatica.y"
{System.out.println("ERROR,falta ';' al final de la declaracion");}
break;
case 82:
//#line 128 "gramatica.y"
{System.out.println("ERROR, falta el bloque ejecutable en el ELSE");}
break;
case 83:
//#line 129 "gramatica.y"
{System.out.println("ERROR, falta el bloque ejecutable en el IF");}
break;
case 84:
//#line 130 "gramatica.y"
{System.out.println("ERROR,falta END_IF al final de la declaracion");}
break;
case 86:
//#line 135 "gramatica.y"
{System.out.println("ERROR,falta de comparador en condicion");}
break;
case 87:
//#line 137 "gramatica.y"
{System.out.println("ERROR,falta de '(' ");}
break;
case 88:
//#line 140 "gramatica.y"
{}
break;
case 89:
//#line 143 "gramatica.y"
{yyval.ival = val_peek(2).ival + val_peek(0).ival;}
break;
case 90:
//#line 144 "gramatica.y"
{yyval.ival = val_peek(2).ival - val_peek(0).ival;}
break;
case 91:
//#line 145 "gramatica.y"
{System.out.println("ERROR, hay 2 operadores");}
break;
case 92:
//#line 146 "gramatica.y"
{System.out.println("ERROR, hay 2 operadores");}
break;
case 93:
//#line 148 "gramatica.y"
{System.out.println("ERROR, falta de operando");}
break;
case 94:
//#line 149 "gramatica.y"
{System.out.println("ERROR, falta de operando");}
break;
case 95:
//#line 150 "gramatica.y"
{System.out.println("ERROR, falta de operando");}
break;
case 96:
//#line 151 "gramatica.y"
{System.out.println("Declaracion de TOD");}
break;
case 97:
//#line 152 "gramatica.y"
{System.out.println("ERROR, falta de expresion");}
break;
case 98:
//#line 153 "gramatica.y"
{yyval.ival = val_peek(0).ival;}
break;
case 101:
//#line 159 "gramatica.y"
{System.out.println("ERROR, hay 2 operadores");}
break;
case 102:
//#line 160 "gramatica.y"
{System.out.println("ERROR, hay 2 operadores");}
break;
case 103:
//#line 161 "gramatica.y"
{System.out.println("ERROR, hay 2 operadores");}
break;
case 104:
//#line 162 "gramatica.y"
{System.out.println("ERROR, hay 2 operadores");}
break;
case 105:
//#line 163 "gramatica.y"
{System.out.println("ERROR, falta de operando");}
break;
case 106:
//#line 164 "gramatica.y"
{System.out.println("ERROR, falta de operando");}
break;
case 107:
//#line 165 "gramatica.y"
{System.out.println("ERROR, falta de operando");}
break;
case 108:
//#line 166 "gramatica.y"
{System.out.println("ERROR, falta de operando");}
break;
case 109:
//#line 167 "gramatica.y"
{yyval= val_peek(0);}
break;
case 111:
//#line 172 "gramatica.y"
{yyval = val_peek(0);  System.out.println("la variable" + val_peek(0).sval + "tiene valor: " + val_peek(0).ival);}
break;
case 112:
//#line 173 "gramatica.y"
{yyval = val_peek(0);
                                      					Long valor = Long.parseLong(val_peek(0).sval);
                                      					if (valor == 2147483648L){
                                        				yyerror("El número está fuera del rango permitido para un longint positivo.");
                                      					}
                                      					int token =   this.LONGINT;
                                      					lector.tablaSimbolos.addToken(val_peek(0).sval,token,"LONGINT");
                                    					}
break;
case 113:
//#line 181 "gramatica.y"
{
                                             				yyval = val_peek(0); /*TODO: posible error*/
                                             				String lexema = '-'+ val_peek(0).sval;
									int token =   this.LONGINT;
                                                                        lector.tablaSimbolos.addToken(val_peek(0).sval,token,"LONGINT");
                                             				}
break;
case 114:
//#line 188 "gramatica.y"
{ yyval = val_peek(0);
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
case 115:
//#line 201 "gramatica.y"
{yyval = val_peek(0);
									  String lexema = '-'+ val_peek(0).sval;
									  int token =   this.HEXA;
									  lector.tablaSimbolos.addToken(val_peek(0).sval,token,"HEXA");}
break;
case 116:
//#line 206 "gramatica.y"
{/*
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
                                      					*/}
break;
case 117:
//#line 220 "gramatica.y"
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
case 121:
//#line 243 "gramatica.y"
{System.out.println(val_peek(0).sval);}
break;
case 126:
//#line 255 "gramatica.y"
{System.out.println("ERROR, falta de ',' en la lista ");}
break;
//#line 1223 "Parser.java"
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
