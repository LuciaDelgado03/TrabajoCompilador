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
    8,    7,    7,    7,    7,    7,    7,    7,    7,    7,
    4,    4,    4,    4,    4,    4,    4,    4,   15,   15,
   15,   15,   15,   15,   15,   15,   14,   14,   14,   14,
   14,   14,   14,   14,   10,   10,   18,   18,   16,   19,
   19,   13,   13,   13,   13,   13,   13,   13,   13,   13,
   13,   13,   13,   13,   13,   13,   13,   13,   13,   13,
   13,   13,   13,   13,   17,   17,   12,   11,   11,   11,
   11,   11,   11,   11,   11,   11,   11,   11,   22,   22,
   22,   22,   22,   22,   22,   22,   22,   22,   22,   23,
   23,   23,   23,   23,   23,   23,   23,    6,    6,    6,
    6,    6,    6,   24,   21,   21,    9,    9,    9,    5,
    5,    5,    5,   20,   20,   20,   20,   20,
};
final static short yylen[] = {                            2,
    4,    3,    2,    3,    2,    2,    2,    1,    1,    1,
    1,    3,    2,    1,    8,    7,    8,    6,    7,    7,
    7,   10,    8,    9,    9,    3,    2,    3,    2,    3,
    2,   14,    9,   13,   13,   13,   12,   13,   13,    8,
    1,    1,    1,    1,    3,    2,    2,    2,    7,    6,
    6,    6,    6,    6,    5,    6,    5,    5,    4,    4,
    4,    3,    3,    5,    2,    1,    4,    3,    3,    2,
    1,    8,   10,    7,    7,    8,    7,    7,    4,    9,
    9,    9,    9,    9,    6,    7,    7,    9,    9,    8,
    8,   10,   10,   10,    3,    1,    4,    3,    3,    4,
    4,    3,    2,    2,    2,    4,    3,    1,    3,    3,
    4,    4,    4,    4,    2,    2,    2,    2,    1,    1,
    1,    1,    2,    1,    2,    1,    2,    3,    3,    1,
    1,    3,    3,    3,    3,    1,    3,    2,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    3,    0,    0,    0,    0,    0,
    0,  141,  142,  140,    0,    0,    8,    9,   10,    0,
    0,   14,   41,   42,   43,   44,  130,    0,    0,    0,
    0,  122,  124,  126,    0,    0,    0,    0,    0,    0,
    0,    0,  120,    0,    0,  119,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   47,    0,
   48,    7,    0,    0,    0,    0,    0,    0,    0,    2,
    0,    0,    0,    0,  123,  125,  127,  118,  117,    0,
    0,    0,  148,  147,  146,    0,    0,  144,  145,    0,
    0,    0,    0,    0,    0,   62,    0,    0,    0,    0,
   63,    0,  143,    0,  139,    0,    0,    0,    0,    0,
    0,   71,    0,    0,    0,    0,   45,  134,    0,    0,
    0,   12,    0,  133,    0,    0,    0,  129,    1,   79,
   68,    0,  107,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  109,    0,
    0,  110,    0,    0,   59,    0,    0,    0,    0,    0,
    0,  138,    0,    0,    0,    0,    0,   69,   70,    0,
    0,    0,    0,    0,    0,    0,    0,   97,   67,  106,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  111,
  112,  114,  113,   64,   57,   58,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  137,    0,    0,   65,    0,
    0,    0,    0,   55,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   85,    0,    0,   27,    0,   31,    0,   29,    0,    0,
    0,    0,    0,   18,    0,    0,    0,    0,    0,   56,
   52,   54,    0,   53,   50,    0,    0,    0,    0,    0,
   75,    0,   86,    0,    0,    0,    0,   78,    0,    0,
   77,    0,   74,    0,    0,   87,   26,   30,   28,   21,
   19,   16,    0,    0,    0,    0,   20,    0,    0,   49,
    0,    0,    0,    0,    0,    0,    0,   76,    0,    0,
    0,   91,    0,    0,    0,   72,    0,   90,    0,   15,
   17,    0,   23,    0,    0,    0,   40,    0,    0,    0,
    0,    0,   88,   83,    0,    0,   82,    0,    0,   84,
   80,   89,    0,   25,   24,    0,    0,   33,    0,    0,
    0,    0,    0,   94,   92,   93,   73,   22,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   37,    0,    0,   34,
   38,    0,   39,   36,   35,   32,
};
final static short yydgoto[] = {                          3,
   16,   17,   18,   19,   20,   21,   22,  200,  106,  167,
   41,   23,   24,   25,   26,   57,   42,   43,  113,   90,
   44,   45,   46,   27,
};
final static short yysindex[] = {                      -232,
  732,  752,    0,   13,    0,   49,   55, -221, -229,  -42,
   30,    0,    0,    0,   22,  976,    0,    0,    0, -160,
  -31,    0,    0,    0,    0,    0,    0,  976,  776, -174,
   70,    0,    0,    0,   82,  646, -139,  335,  335,  613,
  -27,  -26,    0,   53,    5,    0,   42,  606,   86, -157,
  435,  424,  424,  111,  557,  121,  -10,  104,    0,  -99,
    0,    0,  -29,   92,  114,  -95,  640,  -93,  796,    0,
  -82,  620,  627,    5,    0,    0,    0,    0,    0,  -67,
    8,  646,    0,    0,    0,  442,  442,    0,    0,  640,
 -174,  -61,  640,  569,  569,    0,  161,  166,  152,   61,
    0,   -3,    0,  424,    0,   -8,  173,  429,  592,   30,
   22,    0,  701,  640,  599,  640,    0,    0,  154,  592,
  592,    0,   30,    0,  101,  -18,   30,    0,    0,    0,
    0,   85,    0,   94, -174, -174, -201,    5,  646,    5,
  646,    5,  101,  -72, -174,  101,  335,  335,    0,  335,
  335,    0,  155,  157,    0,  159, -119,  -36,  449,  -52,
  424,    0, -119,  182,    0,  -45,  208,    0,    0,  234,
  634,   10,  241,  176,   32,  260,  267,    0,    0,    0,
   56,  -70,  480,   65,    5,    5, -174,  237,  -63,    0,
    0,    0,    0,    0,    0,    0,  -39,    4,  -24,  278,
   38, -119,  269,  289,   27,    0,  297,  -52,    0,  102,
  284,  318,   23,    0,  324,  342,  134,  364,  976,  158,
  178,  350, -174,  381,  183, -174,  -53,  546,  401,  200,
    0, -174,  408,    0,  197,    0,  198,    0,  195,  443,
  445,   31,  453,    0,  -52,  238,  454,   79,  976,    0,
    0,    0,  455,    0,    0,  976,  816,  836,  976,  976,
    0,  253,    0,  463,  283,  993,  490,    0,  669,  500,
    0,  299,    0,  505,  304,    0,    0,    0,    0,    0,
    0,    0,  510,  518,  116,  525,    0,  308,  856,    0,
  876,  896,  916,  548,  936,  956,  533,    0,  535,  333,
  336,    0,  541,  341,  -38,    0,  556,    0,  561,    0,
    0,   81,    0,  565,  585,  586,    0,  684,  589,  640,
  591,  594,    0,    0,  577,  584,    0,  593,  598,    0,
    0,    0,  605,    0,    0,  640,  640,    0,  610,  640,
  107,  640,  640,    0,    0,    0,    0,    0,  136,  143,
  640,  160,  607,  167,  682,  612,  614,  688,  619,  384,
  625,  630,  425,  431,  631,  437,    0,  444,  450,    0,
    0,  451,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  438,    0,    0,    0,  235,  718,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  719,    0,
  -35,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   21,    0,    0,   36,  498,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  125,    0,    0,
    0,    0,    0,  103,  255,    0,    0,    0,  726,    0,
    0,    0,    0,  503,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  511,  531,    0,    0,    0,
    0,    0,    0,  123,  491,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -22,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   34,    0,   41,    0,   83,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  537,    0,  542,
    0,  568,   57,    0,    0,   29,    0,    0,    0,    0,
    0,    0,    0,  149,    0,  171,    0,    0,    0,    0,
    0,    0,    0,    0,    2,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  573,  578,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  193,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  215,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    1,  -15,    0,  476,  871,  520,    0,  366,  -30,  -75,
   -6,    0,    0,    0,    0,  -28,   -2,    0,    0,    0,
  665,  694,   80,   75,
};
final static int YYTABLESIZE=1275;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         49,
   62,   71,   29,  201,  235,  121,  121,  121,  121,  121,
  120,  121,   68,   62,   92,   86,   59,   87,  131,  239,
  330,  131,  108,  121,  121,   93,  121,    1,   69,  116,
   55,  160,   88,  132,   89,  161,  157,   81,    2,   56,
  178,  100,   66,  175,  176,  177,   94,  237,  137,   54,
  215,   95,   40,   62,   38,   36,  183,   37,   55,   39,
  125,  136,  144,  253,  136,  132,  134,  246,  214,  135,
   68,  283,  135,  159,  132,   60,   96,  132,  241,  136,
   61,  252,  128,  143,  136,   55,  146,  135,   48,  282,
   38,   36,  132,   37,   96,   39,   93,   95,  218,  136,
   96,  156,  131,   86,   63,   87,  181,  182,  184,   72,
   64,  170,  172,  173,   53,   95,  189,   78,   79,  288,
  102,   73,   68,  128,   46,  179,  128,   86,   86,   87,
   87,  121,   75,   76,  180,   77,   86,   60,   87,  334,
  124,  128,  128,   86,  101,   87,  131,  353,   61,   86,
  109,   87,  197,  198,  228,  199,  312,   68,  230,   68,
  114,  131,  117,  116,  116,  116,  116,  116,  213,  116,
   60,  118,  122,  149,  152,  123,  356,  127,   86,  130,
   87,  116,  116,  357,  116,   86,  187,   87,  223,  188,
  135,  224,   51,  174,  262,  232,  145,  265,  233,  272,
  359,  153,   86,  275,   87,  266,  154,  361,  267,   86,
  155,   87,  163,  194,   81,  195,  217,  196,  110,  258,
  121,  208,  121,  329,   66,  209,  190,  191,   82,  192,
  193,   91,  234,  131,   11,  197,  198,  301,  199,   58,
  305,  119,   62,  121,  121,  121,   67,  202,  210,  289,
  238,   83,   84,   85,   13,  131,  291,  293,  115,  295,
  296,   66,  103,   12,   13,  136,   14,  103,   12,   13,
   30,   14,  143,   62,  211,   62,  236,   62,  136,   62,
   62,  216,   66,   31,   32,   33,  135,   34,   35,  132,
  132,  219,  318,   96,  132,  231,  132,  132,  132,  132,
  220,  132,   62,  132,  132,  132,  132,  221,  132,  197,
  198,  132,  199,  341,   95,  132,   82,  222,  240,   31,
   32,   33,   47,   34,   35,   50,  229,  244,  245,  349,
  350,   51,   52,  352,   66,  354,  355,  247,  128,  128,
   82,   82,  250,  128,  358,  128,  128,  128,  128,   82,
  128,  333,  128,  128,  128,  128,   82,  128,  131,  131,
  128,  249,   82,  131,  128,  131,  131,  131,  131,   66,
  131,   66,  131,  131,  131,  131,  251,  131,  116,   37,
  116,   46,  254,   46,  131,   46,   46,   46,   46,   46,
   46,   82,   46,  256,   46,   46,   46,   46,   82,   46,
  255,  116,  116,  116,  257,   61,   46,   61,  261,   61,
   61,   61,   61,   61,   61,   82,   61,  259,   61,   61,
   61,   61,   82,   61,  165,   12,   13,   60,   14,   60,
   61,   60,   60,   60,   60,   60,   60,  260,   60,  263,
   60,   60,   60,   60,  264,   60,  165,   12,   13,   51,
   14,   51,   60,   51,   51,   51,   51,   51,   51,  273,
   51,  274,   51,   51,   51,   51,  276,   51,  277,  279,
  278,   81,  161,   81,   51,   81,   81,   81,   81,   81,
   81,  131,   81,   38,   81,   81,   81,   81,   39,   81,
  164,   11,  161,  284,  104,   11,   81,   11,   11,   11,
   11,  280,   11,  281,   11,   11,   11,   11,  286,   11,
  204,   13,  287,  290,  297,   13,   11,   13,   13,   13,
   13,  298,   13,  203,   13,   13,   13,   13,  207,   13,
  112,  115,  115,  115,  115,  115,   13,  115,  108,   65,
  108,  108,  108,  103,  299,  103,  103,  103,  302,  115,
  115,  104,  115,  104,  104,  104,  108,  108,  306,  108,
  307,  103,  103,  308,  103,  309,  242,  243,  310,  104,
  104,  105,  104,  105,  105,  105,  311,  102,  314,  102,
  102,  102,   98,  313,   98,   98,   98,  320,  169,  105,
  105,  323,  105,  324,  325,  102,  102,  326,  102,  327,
   98,   98,  328,   98,  271,   31,   32,   33,   99,   34,
   99,   99,   99,  100,  331,  100,  100,  100,  101,  332,
  101,  101,  101,  335,  336,  337,   99,   99,  340,   99,
  342,  100,  100,  343,  100,  344,  101,  101,  171,  101,
   38,   36,  345,   37,  367,   39,   99,   38,   36,  351,
   37,  346,   39,   80,   38,   36,  347,   37,  227,   39,
  131,   38,   36,  348,   37,  360,   39,  133,   38,   36,
  363,   37,  364,   39,  212,   38,   36,  366,   37,  205,
   39,   38,   36,  368,   37,  370,   39,   38,  369,  372,
   37,  371,   39,  131,  103,   12,   13,  373,   14,  103,
   12,   13,  143,   14,  374,  103,   12,   13,  143,   14,
  375,  376,   31,   32,   33,  131,   34,    6,    5,  103,
   12,   13,  362,   14,   86,    4,   87,  248,  365,   74,
   86,  126,   87,    0,    0,  225,    4,    0,  226,   55,
    0,  300,    6,    0,  304,    0,  115,    9,  115,   10,
  110,    0,    0,  108,    0,  108,    0,    0,  103,    0,
  103,  111,    0,    0,  285,    0,  104,    0,  104,  115,
  115,  115,    0,    0,    0,  138,  108,  108,  108,  140,
  142,  103,  103,  103,    0,    0,  105,    0,  105,  104,
  104,  104,  102,    0,  102,    0,    0,   98,    0,   98,
    0,  268,    0,    0,  269,   55,    0,  270,    0,  105,
  105,  105,    0,    4,    0,  102,  102,  102,    0,    6,
   98,   98,   98,   99,    9,   99,   10,  110,  100,    0,
  100,    0,  185,  101,  186,  101,    0,    0,  111,   31,
   32,   33,    0,   34,    0,    0,   99,   99,   99,    0,
    0,  100,  100,  100,    0,    0,  101,  101,  101,    0,
    0,   97,  165,   12,   13,    0,   14,    0,    0,   31,
   32,   33,    0,   34,   35,    0,   31,   32,   33,   98,
   34,   35,    0,   31,   32,   33,    0,   34,   35,    0,
   31,   32,   33,    0,   34,   35,    0,   31,   32,   33,
    0,   34,   35,    0,   31,   32,   33,    0,   34,   35,
   31,   32,   33,    0,   34,   35,   31,   32,   33,    0,
   34,  105,  107,  105,    0,    4,    0,    0,   55,    0,
  303,    6,    0,    0,    0,    0,    9,   82,   10,  110,
    4,    0,    0,   82,  338,    0,    6,    7,    8,  339,
  111,    9,    0,   10,   11,   12,   13,    4,   14,    0,
    0,  168,    0,    6,    0,   15,    0,    0,    9,    0,
   10,  110,  158,    0,  105,    0,  162,    0,  162,  166,
    0,    0,  111,    0,    0,    0,    0,    0,    4,  166,
  166,  166,    5,    0,    6,    7,    8,    0,    0,    9,
    0,   10,   11,   12,   13,    0,   14,    0,    4,    0,
    0,   28,    0,   15,    6,    7,    8,    0,    0,    9,
    0,   10,   11,   12,   13,    0,   14,    0,    0,  162,
    0,  206,    4,   15,    0,    0,   70,    0,    6,    7,
    8,    0,    0,    9,  166,   10,   11,   12,   13,    0,
   14,    0,    4,    0,    0,    0,  129,   15,    6,    7,
    8,    0,    0,    9,    0,   10,   11,   12,   13,    0,
   14,    0,    4,    0,    0,  292,    0,   15,    6,    7,
    8,    0,    0,    9,    0,   10,   11,   12,   13,    0,
   14,    0,    4,    0,    0,    0,    0,   15,    6,    7,
    8,  294,    0,    9,    0,   10,   11,   12,   13,    0,
   14,    0,    4,    0,    0,    0,    0,   15,    6,    7,
    8,  315,    0,    9,    0,   10,   11,   12,   13,    0,
   14,    0,    4,    0,    0,    0,    0,   15,    6,    7,
    8,  316,    0,    9,    0,   10,   11,   12,   13,    0,
   14,    0,    4,    0,    0,    0,  317,   15,    6,    7,
    8,    0,    0,    9,    0,   10,   11,   12,   13,    0,
   14,    0,    4,    0,    0,    0,    0,   15,    6,    7,
    8,  319,    0,    9,    0,   10,   11,   12,   13,    0,
   14,    0,    4,    0,    0,    0,    0,   15,    6,    7,
    8,  321,    0,    9,    0,   10,   11,   12,   13,    0,
   14,    0,    4,    0,    0,    0,    0,   15,    6,    7,
    8,  322,    0,    9,    0,   10,   11,   12,   13,    0,
   14,    0,    4,    0,    0,    0,    0,   15,    6,    7,
    8,    0,    0,    9,    0,   10,   11,   12,   13,    4,
   14,    0,   55,    0,    0,    6,    0,   15,    0,    0,
    9,    0,   10,  110,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  111,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          6,
   16,   30,    2,   40,   44,   41,   42,   43,   44,   45,
   40,   47,   44,   29,   41,   43,   59,   45,   41,   44,
   59,   44,   53,   59,   60,   44,   62,  260,   28,   40,
  260,   40,   60,    0,   62,   44,   40,   40,  271,  269,
   59,   48,   41,  119,  120,  121,   42,   44,   41,  271,
   41,   47,   40,   69,   42,   43,  258,   45,  260,   47,
   67,   41,   91,   41,   44,   72,   73,   41,   59,   41,
   44,   41,   44,  104,   41,   46,   41,   44,   41,   59,
   59,   59,    0,   90,   44,  260,   93,   59,   40,   59,
   42,   43,   59,   45,   59,   47,   44,   41,  174,   59,
   59,   41,    0,   43,  265,   45,  135,  136,  137,   40,
  271,  114,  115,  116,   60,   59,  145,   38,   39,   41,
  278,   40,   44,   41,    0,   41,   44,   43,   43,   45,
   45,   40,  272,  273,   41,  275,   43,   46,   45,   59,
   66,   59,   68,   43,   59,   45,   44,   41,    0,   43,
   40,   45,  272,  273,  183,  275,   41,   44,  187,   44,
   40,   59,   59,   41,   42,   43,   44,   45,  171,   47,
    0,  271,   59,   94,   95,  271,   41,  271,   43,  262,
   45,   59,   60,   41,   62,   43,  259,   45,  259,  262,
  258,  262,    0,   40,  223,  259,  258,  226,  262,  228,
   41,   41,   43,  232,   45,  259,   41,   41,  262,   43,
   59,   45,   40,   59,    0,   59,   41,   59,  271,  219,
  256,   40,  258,  262,  256,  271,  147,  148,  256,  150,
  151,  258,  272,  256,    0,  272,  273,  266,  275,  282,
  269,  271,  258,  279,  280,  281,  278,  284,   41,  249,
  275,  279,  280,  281,    0,  278,  256,  257,  269,  259,
  260,  260,  271,  272,  273,  258,  275,  271,  272,  273,
  258,  275,  271,  289,   41,  291,  273,  293,  258,  295,
  296,   41,  256,  271,  272,  273,  258,  275,  276,  256,
  257,  260,  292,  258,  261,   59,  263,  264,  265,  266,
   41,  268,  318,  270,  271,  272,  273,   41,  275,  272,
  273,  278,  275,  320,  258,  282,  256,  262,   41,  271,
  272,  273,  274,  275,  276,  271,  262,   59,   40,  336,
  337,  277,  278,  340,  256,  342,  343,   41,  256,  257,
  256,  256,   59,  261,  351,  263,  264,  265,  266,  256,
  268,  271,  270,  271,  272,  273,  256,  275,  256,  257,
  278,  260,  256,  261,  282,  263,  264,  265,  266,  256,
  268,  256,  270,  271,  272,  273,   59,  275,  256,   45,
  258,  257,   59,  259,  282,  261,  262,  263,  264,  265,
  266,  256,  268,  260,  270,  271,  272,  273,  256,  275,
   59,  279,  280,  281,   41,  257,  282,  259,   59,  261,
  262,  263,  264,  265,  266,  256,  268,  260,  270,  271,
  272,  273,  256,  275,  271,  272,  273,  257,  275,  259,
  282,  261,  262,  263,  264,  265,  266,  260,  268,   59,
  270,  271,  272,  273,  262,  275,  271,  272,  273,  257,
  275,  259,  282,  261,  262,  263,  264,  265,  266,   59,
  268,  262,  270,  271,  272,  273,   59,  275,  272,  275,
  273,  257,   44,  259,  282,  261,  262,  263,  264,  265,
  266,   44,  268,   42,  270,  271,  272,  273,   47,  275,
   62,  257,   44,   41,   60,  261,  282,  263,  264,  265,
  266,   59,  268,   59,  270,  271,  272,  273,  271,  275,
   62,  257,   59,   59,  262,  261,  282,  263,  264,  265,
  266,   59,  268,  158,  270,  271,  272,  273,  163,  275,
   55,   41,   42,   43,   44,   45,  282,   47,   41,   20,
   43,   44,   45,   41,  262,   43,   44,   45,   59,   59,
   60,   41,   62,   43,   44,   45,   59,   60,   59,   62,
  262,   59,   60,   59,   62,  262,  201,  202,   59,   59,
   60,   41,   62,   43,   44,   45,   59,   41,  271,   43,
   44,   45,   41,   59,   43,   44,   45,   40,  113,   59,
   60,   59,   62,   59,  262,   59,   60,  262,   62,   59,
   59,   60,  262,   62,   59,  271,  272,  273,   41,  275,
   43,   44,   45,   41,   59,   43,   44,   45,   41,   59,
   43,   44,   45,   59,   40,   40,   59,   60,   40,   62,
   40,   59,   60,   40,   62,   59,   59,   60,   40,   62,
   42,   43,   59,   45,  261,   47,   41,   42,   43,   40,
   45,   59,   47,   41,   42,   43,   59,   45,  183,   47,
   41,   42,   43,   59,   45,   59,   47,   41,   42,   43,
   59,   45,   59,   47,   41,   42,   43,   59,   45,  160,
   47,   42,   43,   59,   45,  261,   47,   42,   59,   59,
   45,  261,   47,  256,  271,  272,  273,  261,  275,  271,
  272,  273,  265,  275,  261,  271,  272,  273,  271,  275,
  261,  261,  271,  272,  273,  278,  275,    0,    0,  271,
  272,  273,   41,  275,   43,    0,   45,  208,   41,   36,
   43,   67,   45,   -1,   -1,  256,  257,   -1,  259,  260,
   -1,  266,  263,   -1,  269,   -1,  256,  268,  258,  270,
  271,   -1,   -1,  256,   -1,  258,   -1,   -1,  256,   -1,
  258,  282,   -1,   -1,  245,   -1,  256,   -1,  258,  279,
  280,  281,   -1,   -1,   -1,   82,  279,  280,  281,   86,
   87,  279,  280,  281,   -1,   -1,  256,   -1,  258,  279,
  280,  281,  256,   -1,  258,   -1,   -1,  256,   -1,  258,
   -1,  256,   -1,   -1,  259,  260,   -1,  262,   -1,  279,
  280,  281,   -1,  257,   -1,  279,  280,  281,   -1,  263,
  279,  280,  281,  256,  268,  258,  270,  271,  256,   -1,
  258,   -1,  139,  256,  141,  258,   -1,   -1,  282,  271,
  272,  273,   -1,  275,   -1,   -1,  279,  280,  281,   -1,
   -1,  279,  280,  281,   -1,   -1,  279,  280,  281,   -1,
   -1,  256,  271,  272,  273,   -1,  275,   -1,   -1,  271,
  272,  273,   -1,  275,  276,   -1,  271,  272,  273,  274,
  275,  276,   -1,  271,  272,  273,   -1,  275,  276,   -1,
  271,  272,  273,   -1,  275,  276,   -1,  271,  272,  273,
   -1,  275,  276,   -1,  271,  272,  273,   -1,  275,  276,
  271,  272,  273,   -1,  275,  276,  271,  272,  273,   -1,
  275,   51,   52,   53,   -1,  257,   -1,   -1,  260,   -1,
  262,  263,   -1,   -1,   -1,   -1,  268,  256,  270,  271,
  257,   -1,   -1,  256,  261,   -1,  263,  264,  265,  266,
  282,  268,   -1,  270,  271,  272,  273,  257,  275,   -1,
   -1,  261,   -1,  263,   -1,  282,   -1,   -1,  268,   -1,
  270,  271,  102,   -1,  104,   -1,  106,   -1,  108,  109,
   -1,   -1,  282,   -1,   -1,   -1,   -1,   -1,  257,  119,
  120,  121,  261,   -1,  263,  264,  265,   -1,   -1,  268,
   -1,  270,  271,  272,  273,   -1,  275,   -1,  257,   -1,
   -1,  260,   -1,  282,  263,  264,  265,   -1,   -1,  268,
   -1,  270,  271,  272,  273,   -1,  275,   -1,   -1,  159,
   -1,  161,  257,  282,   -1,   -1,  261,   -1,  263,  264,
  265,   -1,   -1,  268,  174,  270,  271,  272,  273,   -1,
  275,   -1,  257,   -1,   -1,   -1,  261,  282,  263,  264,
  265,   -1,   -1,  268,   -1,  270,  271,  272,  273,   -1,
  275,   -1,  257,   -1,   -1,  260,   -1,  282,  263,  264,
  265,   -1,   -1,  268,   -1,  270,  271,  272,  273,   -1,
  275,   -1,  257,   -1,   -1,   -1,   -1,  282,  263,  264,
  265,  266,   -1,  268,   -1,  270,  271,  272,  273,   -1,
  275,   -1,  257,   -1,   -1,   -1,   -1,  282,  263,  264,
  265,  266,   -1,  268,   -1,  270,  271,  272,  273,   -1,
  275,   -1,  257,   -1,   -1,   -1,   -1,  282,  263,  264,
  265,  266,   -1,  268,   -1,  270,  271,  272,  273,   -1,
  275,   -1,  257,   -1,   -1,   -1,  261,  282,  263,  264,
  265,   -1,   -1,  268,   -1,  270,  271,  272,  273,   -1,
  275,   -1,  257,   -1,   -1,   -1,   -1,  282,  263,  264,
  265,  266,   -1,  268,   -1,  270,  271,  272,  273,   -1,
  275,   -1,  257,   -1,   -1,   -1,   -1,  282,  263,  264,
  265,  266,   -1,  268,   -1,  270,  271,  272,  273,   -1,
  275,   -1,  257,   -1,   -1,   -1,   -1,  282,  263,  264,
  265,  266,   -1,  268,   -1,  270,  271,  272,  273,   -1,
  275,   -1,  257,   -1,   -1,   -1,   -1,  282,  263,  264,
  265,   -1,   -1,  268,   -1,  270,  271,  272,  273,  257,
  275,   -1,  260,   -1,   -1,  263,   -1,  282,   -1,   -1,
  268,   -1,  270,  271,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  282,
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
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,"IF","THEN","ELSE","BEGIN","END","END_IF","OUTF",
"TYPEDEF","FUN","RET","STRING","REPEAT","WHILE","GOTO","ID","LONGINT","HEXA",
"CML","DOUBLE","TOD","STRUCT","ASIGNACION","DISTINTO","MENOR_IGUAL",
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
"sentencia_declaracion : TYPEDEF ID ASIGNACION tipo '(' subrango ')' ';'",
"sentencia_declaracion : TYPEDEF ID ASIGNACION tipo '(' subrango ';'",
"sentencia_declaracion : TYPEDEF ID ASIGNACION tipo \"\" subrango ')' ';'",
"sentencia_declaracion : TYPEDEF ID ASIGNACION tipo subrango ';'",
"sentencia_declaracion : TYPEDEF ID ASIGNACION tipo '(' ')' ';'",
"sentencia_declaracion : TYPEDEF ASIGNACION tipo '(' subrango ')' ';'",
"sentencia_declaracion : TYPEDEF ID ASIGNACION '(' subrango ')' ';'",
"sentencia_declaracion : TYPEDEF STRUCT '<' lista_tipos '>' '(' lista_variables ')' ID ';'",
"sentencia_declaracion : TYPEDEF STRUCT lista_tipos '(' lista_variables ')' ID ';'",
"sentencia_declaracion : TYPEDEF '<' lista_tipos '>' '(' lista_variables ')' ID ';'",
"sentencia_declaracion : TYPEDEF STRUCT '<' lista_tipos '>' '(' lista_variables ')' ';'",
"subrango : LONGINT ',' LONGINT",
"subrango : LONGINT LONGINT",
"subrango : DOUBLE ',' DOUBLE",
"subrango : DOUBLE DOUBLE",
"subrango : HEXA ',' HEXA",
"subrango : HEXA HEXA",
"declaracion_funcion : tipo FUN ID '(' parametro ')' BEGIN cuerpo RET '(' expresion ')' ';' END",
"declaracion_funcion : tipo FUN ID '(' parametro ')' BEGIN cuerpo END",
"declaracion_funcion : FUN ID '(' parametro ')' BEGIN cuerpo RET '(' expresion ')' ';' END",
"declaracion_funcion : tipo ID '(' parametro ')' BEGIN cuerpo RET '(' expresion ')' ';' END",
"declaracion_funcion : tipo FUN '(' parametro ')' BEGIN cuerpo RET '(' expresion ')' ';' END",
"declaracion_funcion : tipo FUN ID parametro BEGIN cuerpo RET '(' expresion ')' ';' END",
"declaracion_funcion : tipo FUN ID '(' ')' BEGIN cuerpo RET '(' expresion ')' ';' END",
"declaracion_funcion : tipo FUN ID '(' parametro ')' cuerpo RET '(' expresion ')' ';' END",
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
"sentencia_while : REPEAT bloque_sentencia_ejecutable WHILE condicion ')' ';'",
"sentencia_while : REPEAT bloque_sentencia_ejecutable WHILE '(' condicion ';'",
"sentencia_while : REPEAT bloque_sentencia_ejecutable WHILE condicion ';'",
"sentencia_while : REPEAT WHILE '(' condicion ')' ';'",
"sentencia_print : OUTF '(' CML ')' ';'",
"sentencia_print : OUTF '(' expresion ')' ';'",
"sentencia_print : OUTF '(' ')' ';'",
"sentencia_print : OUTF '(' expresion ')'",
"sentencia_print : OUTF '(' CML ')'",
"sentencia_print : OUTF CML ';'",
"sentencia_print : OUTF expresion ';'",
"sentencia_print : OUTF '(' error ')' ';'",
"parametro : tipo ID",
"parametro : ID",
"invocacion_funcion : ID '(' expresion ')'",
"invocacion_funcion : ID '(' ')'",
"bloque_sentencia_ejecutable : BEGIN lista_sentencias END",
"lista_sentencias : lista_sentencias sentencia_ejecucion",
"lista_sentencias : sentencia_ejecucion",
"condicion_if : IF '(' condicion ')' THEN bloque_sentencia_ejecutable END_IF ';'",
"condicion_if : IF '(' condicion ')' THEN bloque_sentencia_ejecutable ELSE bloque_sentencia_ejecutable END_IF ';'",
"condicion_if : IF '(' condicion ')' bloque_sentencia_ejecutable END_IF ';'",
"condicion_if : IF '(' ')' THEN bloque_sentencia_ejecutable END_IF ';'",
"condicion_if : IF '(' condicion ')' THEN error END_IF ';'",
"condicion_if : IF '(' condicion ')' THEN bloque_sentencia_ejecutable ';'",
"condicion_if : IF '(' condicion ')' THEN bloque_sentencia_ejecutable error",
"condicion_if : IF THEN bloque_sentencia_ejecutable END_IF",
"condicion_if : IF '(' condicion ')' THEN bloque_sentencia_ejecutable bloque_sentencia_ejecutable END_IF ';'",
"condicion_if : IF '(' condicion ')' THEN bloque_sentencia_ejecutable ELSE bloque_sentencia_ejecutable END_IF",
"condicion_if : IF '(' condicion ')' THEN bloque_sentencia_ejecutable ELSE END_IF ';'",
"condicion_if : IF '(' condicion ')' THEN ELSE bloque_sentencia_ejecutable END_IF ';'",
"condicion_if : IF '(' condicion ')' THEN bloque_sentencia_ejecutable ELSE bloque_sentencia_ejecutable ';'",
"condicion_if : IF condicion THEN bloque_sentencia_ejecutable END_IF ';'",
"condicion_if : IF '(' condicion THEN bloque_sentencia_ejecutable END_IF ';'",
"condicion_if : IF condicion ')' THEN bloque_sentencia_ejecutable END_IF ';'",
"condicion_if : IF '(' condicion THEN bloque_sentencia_ejecutable ELSE bloque_sentencia_ejecutable END_IF ';'",
"condicion_if : IF condicion ')' THEN bloque_sentencia_ejecutable ELSE bloque_sentencia_ejecutable END_IF ';'",
"condicion_if : IF condicion THEN bloque_sentencia_ejecutable ELSE bloque_sentencia_ejecutable END_IF ';'",
"condicion_if : IF '(' condicion ')' THEN sentencia_ejecucion END_IF ';'",
"condicion_if : IF '(' condicion ')' THEN sentencia_ejecucion ELSE bloque_sentencia_ejecutable END_IF ';'",
"condicion_if : IF '(' condicion ')' THEN bloque_sentencia_ejecutable ELSE sentencia_ejecucion END_IF ';'",
"condicion_if : IF '(' condicion ')' THEN sentencia_ejecucion ELSE sentencia_ejecucion END_IF ';'",
"condicion : expresion comparador expresion",
"condicion : lista_expresiones",
"asignacion : lista_variables ASIGNACION lista_expresiones ';'",
"expresion : expresion '+' termino",
"expresion : expresion '-' termino",
"expresion : expresion '+' '+' termino",
"expresion : expresion '-' '+' termino",
"expresion : expresion error termino",
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
"lista_variables : lista_variables error ID",
"lista_variables : lista_variables error id_compuesta",
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

//#line 322 "gramatica.y"


void yyerror(String mensaje) {
  // funcion utilizada para imprimir errores que produce yacc
  System.out.println("Error yacc: " + mensaje);

}
//#line 735 "Parser.java"
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
{System.out.println("ERROR,falta begin programa principal en la linea: " + lector.getNroLinea());}
break;
case 3:
//#line 21 "gramatica.y"
{System.out.println("ERROR,falta el ID del programa principal en la linea: " + lector.getNroLinea());}
break;
case 4:
//#line 22 "gramatica.y"
{System.out.println("ERROR,falta END del programa principal en la linea: " + lector.getNroLinea());}
break;
case 5:
//#line 23 "gramatica.y"
{System.out.println("ERROR,falta BEGIN,END del programa principal en la linea: " + lector.getNroLinea());}
break;
case 6:
//#line 24 "gramatica.y"
{System.out.println("ERROR,falta ID,END del programa principal en la linea: " + lector.getNroLinea());}
break;
case 12:
//#line 36 "gramatica.y"
{System.out.println(val_peek(1));}
break;
case 13:
//#line 37 "gramatica.y"
{System.out.println("ERROR, Falta ; en la sentencia de declaracion en la linea: " + lector.getNroLinea());}
break;
case 15:
//#line 39 "gramatica.y"
{System.out.println("Declaracion de Subtipo");}
break;
case 16:
//#line 40 "gramatica.y"
{System.out.println("Error, Falta de parentesis en la linea: " + lector.getNroLinea());}
break;
case 17:
//#line 41 "gramatica.y"
{System.out.println("Error, Falta de parentesis en la linea: " + lector.getNroLinea());}
break;
case 18:
//#line 42 "gramatica.y"
{System.out.println("Error, Falta de llaves parentesis en la linea: " + lector.getNroLinea());}
break;
case 19:
//#line 43 "gramatica.y"
{System.out.println("ERROR, Falta de rango en la linea: " + lector.getNroLinea());}
break;
case 20:
//#line 44 "gramatica.y"
{System.out.println("ERROR, Falta nombre del tipo definido en la linea: " + lector.getNroLinea());}
break;
case 21:
//#line 45 "gramatica.y"
{System.out.println("ERROR, Falta el tipo base en la linea: " + lector.getNroLinea());}
break;
case 22:
//#line 46 "gramatica.y"
{System.out.println("Declaracion de Struct");}
break;
case 23:
//#line 47 "gramatica.y"
{System.out.println("ERROR, Falta <> en la linea: " + lector.getNroLinea());}
break;
case 24:
//#line 48 "gramatica.y"
{System.out.println("ERROR, Falta la palabra STRUCT en la linea: " + lector.getNroLinea());}
break;
case 25:
//#line 49 "gramatica.y"
{System.out.println("ERROR,Falta  ID al final de la declaracion en la linea: " + lector.getNroLinea());}
break;
case 27:
//#line 53 "gramatica.y"
{System.out.println("ERROR,Falta ','entre los digitos del subrango en la linea: " + lector.getNroLinea());}
break;
case 29:
//#line 55 "gramatica.y"
{System.out.println("ERROR,Falta ','entre los digitos del subrang en la linea: " + lector.getNroLinea());}
break;
case 31:
//#line 57 "gramatica.y"
{System.out.println("ERROR,Falta ','entre los digitos del subrango en la linea: " + lector.getNroLinea());}
break;
case 33:
//#line 61 "gramatica.y"
{System.out.println("Falta sentencia return en la linea: " + lector.getNroLinea());}
break;
case 34:
//#line 62 "gramatica.y"
{System.out.println("ERROR,Falta la declaracion del tipo de la FUN en la linea: " + lector.getNroLinea());}
break;
case 35:
//#line 63 "gramatica.y"
{System.out.println("ERROR,Falta la declaracion de la palabra reservada FUN en la linea: " + lector.getNroLinea());}
break;
case 36:
//#line 64 "gramatica.y"
{System.out.println("ERROR,Falta el ID de la funcion en la linea: " + lector.getNroLinea());}
break;
case 37:
//#line 65 "gramatica.y"
{System.out.println("ERROR,Falta de () a la hora de los parametros en la linea: " + lector.getNroLinea());}
break;
case 38:
//#line 66 "gramatica.y"
{System.out.println("ERROR,Falta de parametros en la FUN en la linea: " + lector.getNroLinea());}
break;
case 39:
//#line 67 "gramatica.y"
{System.out.println("ERROR,Falta de BEGIN en la FUN en la linea: " + lector.getNroLinea());}
break;
case 40:
//#line 69 "gramatica.y"
{System.out.println("ERROR,Falsa cuerpo de funcion en la linea: " + lector.getNroLinea());}
break;
case 45:
//#line 76 "gramatica.y"
{System.out.println("Declaracion de GOTO");}
break;
case 46:
//#line 77 "gramatica.y"
{System.out.println("ERROR, Falta ';' al final de la declaracion en la linea: " + lector.getNroLinea());}
break;
case 47:
//#line 78 "gramatica.y"
{System.out.println("ERROR,falta la ETIQUETA en la linea: " + lector.getNroLinea());}
break;
case 48:
//#line 79 "gramatica.y"
{System.out.println("ERROR,falta el GOTO en la linea: " + lector.getNroLinea());}
break;
case 49:
//#line 82 "gramatica.y"
{System.out.println("Declaracion REPEAT-WHILE");}
break;
case 50:
//#line 84 "gramatica.y"
{System.out.println("ERROR,falta palabra WHILE en la linea: " + lector.getNroLinea());}
break;
case 51:
//#line 85 "gramatica.y"
{System.out.println("ERROR,falta palabra ';' al final de la declaracion en la linea: " + lector.getNroLinea());}
break;
case 52:
//#line 86 "gramatica.y"
{System.out.println("ERROR,falta falta la condicion del WHILE en la linea: " + lector.getNroLinea());}
break;
case 53:
//#line 87 "gramatica.y"
{System.out.println("ERRROR, falta parentesis '(' en la declaracion de la condicion de  WHILE en la linea: " + lector.getNroLinea());}
break;
case 54:
//#line 88 "gramatica.y"
{System.out.println("ERRROR, falta parentesis ')' en la declaracion de la condicion de  WHILE en la linea: " + lector.getNroLinea());}
break;
case 55:
//#line 89 "gramatica.y"
{System.out.println("ERRROR, falta parentesis  en la declaracion de la condicion de  WHILE en la linea: " + lector.getNroLinea());}
break;
case 56:
//#line 90 "gramatica.y"
{System.out.println("ERRROR, falta el cuerpo de la iteracion repeat en la linea: " + lector.getNroLinea());}
break;
case 57:
//#line 93 "gramatica.y"
{System.out.println(val_peek(2).sval);}
break;
case 59:
//#line 95 "gramatica.y"
{System.out.println("ERROR, Falta parámetro en sentencia OUTF en la linea: " + lector.getNroLinea());}
break;
case 60:
//#line 96 "gramatica.y"
{System.out.println("ERROR, Falta ';' en la sentencia OUTF en la linea: " + lector.getNroLinea());}
break;
case 61:
//#line 97 "gramatica.y"
{System.out.println("ERROR, Falta ';' en la sentencia OUTF en la linea: " + lector.getNroLinea());}
break;
case 62:
//#line 98 "gramatica.y"
{System.out.println("ERROR, Faltan los parentesis en la sentencia OUTF en la linea: " + lector.getNroLinea());}
break;
case 63:
//#line 99 "gramatica.y"
{System.out.println("ERROR, Faltan los parentesis en la sentencia OUTF en la linea: " + lector.getNroLinea());}
break;
case 64:
//#line 100 "gramatica.y"
{System.out.println("ERROR, tipo invalido como parametro para la sentencia OUTF en la linea: " + lector.getNroLinea());}
break;
case 66:
//#line 109 "gramatica.y"
{System.out.println("ERROR, falta declaracion de TIPO en la linea: " + lector.getNroLinea());}
break;
case 67:
//#line 114 "gramatica.y"
{if (val_peek(3).sval.equals(null)){ System.out.println("No existe una funcion con ese nombre en la linea: " + lector.getNroLinea());}}
break;
case 68:
//#line 116 "gramatica.y"
{System.out.println("ERROR, falta parametro en la invocacion de la funcion en la linea: " + lector.getNroLinea());}
break;
case 72:
//#line 127 "gramatica.y"
{System.out.println("Declaracion de IF");}
break;
case 73:
//#line 128 "gramatica.y"
{System.out.println("Declaracion de IF,ELSE");}
break;
case 74:
//#line 129 "gramatica.y"
{System.out.println("ERROR, Falta THEN luego de la condicion en la linea: " + lector.getNroLinea());}
break;
case 75:
//#line 130 "gramatica.y"
{System.out.println("ERROR,falta de Condicion en la linea: " + lector.getNroLinea());}
break;
case 76:
//#line 131 "gramatica.y"
{System.out.println("ERROR,falta el bloque ejecutable en la linea: " + lector.getNroLinea());}
break;
case 77:
//#line 132 "gramatica.y"
{System.out.println("ERROR,falta END_IF al final de la declaracion en la linea: " + lector.getNroLinea());}
break;
case 78:
//#line 133 "gramatica.y"
{System.out.println("ERROR,falta END_IF; al final de la declaracion en la linea: " + lector.getNroLinea());}
break;
case 79:
//#line 134 "gramatica.y"
{System.out.println("ERROR,falta ';' al final de la declaracion en la linea: " + lector.getNroLinea());}
break;
case 80:
//#line 135 "gramatica.y"
{System.out.println("ERROR, falta ELSE luego de la sentencias de ejecucion en la linea: " + lector.getNroLinea());}
break;
case 81:
//#line 136 "gramatica.y"
{System.out.println("ERROR,falta ';' al final de la declaracion en la linea: " + lector.getNroLinea());}
break;
case 82:
//#line 137 "gramatica.y"
{System.out.println("ERROR, falta el bloque ejecutable en el ELSE en la linea: " + lector.getNroLinea());}
break;
case 83:
//#line 138 "gramatica.y"
{System.out.println("ERROR, falta el bloque ejecutable en el IF en la linea: " + lector.getNroLinea());}
break;
case 84:
//#line 139 "gramatica.y"
{System.out.println("ERROR,falta END_IF al final de la declaracion en la linea: " + lector.getNroLinea());}
break;
case 85:
//#line 140 "gramatica.y"
{System.out.println("ERROR,falta de parentesis en la linea: " + lector.getNroLinea());}
break;
case 86:
//#line 141 "gramatica.y"
{System.out.println("ERROR,falta un parentesis ')' en la linea: " + lector.getNroLinea());}
break;
case 87:
//#line 142 "gramatica.y"
{System.out.println("ERROR,falta un parentesis '(' en la linea: " + lector.getNroLinea());}
break;
case 88:
//#line 143 "gramatica.y"
{System.out.println("ERROR,falta un parentesis ')' ");}
break;
case 89:
//#line 144 "gramatica.y"
{System.out.println("ERROR,falta un parentesis '(' ");}
break;
case 90:
//#line 145 "gramatica.y"
{System.out.println("ERROR,falta un parentesis '()' ");}
break;
case 92:
//#line 147 "gramatica.y"
{System.out.println("Declaracion de IF,ELSE");}
break;
case 93:
//#line 148 "gramatica.y"
{System.out.println("Declaracion de IF,ELSE");}
break;
case 94:
//#line 149 "gramatica.y"
{System.out.println("Declaracion de IF,ELSE");}
break;
case 96:
//#line 154 "gramatica.y"
{System.out.println("ERROR, falta comparador en comparacion en la linea: " + lector.getNroLinea());}
break;
case 97:
//#line 157 "gramatica.y"
{yyval= val_peek(1);}
break;
case 98:
//#line 160 "gramatica.y"
{yyval.ival = val_peek(2).ival + val_peek(0).ival;}
break;
case 99:
//#line 161 "gramatica.y"
{yyval.ival = val_peek(2).ival - val_peek(0).ival;}
break;
case 100:
//#line 162 "gramatica.y"
{System.out.println("ERROR, hay 2 operadores en la linea: " + lector.getNroLinea());}
break;
case 101:
//#line 163 "gramatica.y"
{System.out.println("ERROR, hay 2 operadores en la linea: " + lector.getNroLinea());}
break;
case 102:
//#line 164 "gramatica.y"
{System.out.println("ERROR, falta de operador en la linea: " + lector.getNroLinea());}
break;
case 103:
//#line 165 "gramatica.y"
{System.out.println("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
break;
case 104:
//#line 166 "gramatica.y"
{System.out.println("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
break;
case 105:
//#line 167 "gramatica.y"
{System.out.println("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
break;
case 106:
//#line 168 "gramatica.y"
{System.out.println("Declaracion de TOD");}
break;
case 107:
//#line 169 "gramatica.y"
{System.out.println("ERROR, falta de expresion en la linea: " + lector.getNroLinea());}
break;
case 108:
//#line 170 "gramatica.y"
{yyval.ival = val_peek(0).ival;}
break;
case 111:
//#line 176 "gramatica.y"
{System.out.println("ERROR, hay 2 operadores en la linea: " + lector.getNroLinea());}
break;
case 112:
//#line 177 "gramatica.y"
{System.out.println("ERROR, hay 2 operadores en la linea: " + lector.getNroLinea());}
break;
case 113:
//#line 178 "gramatica.y"
{System.out.println("ERROR, hay 2 operadores en la linea: " + lector.getNroLinea());}
break;
case 114:
//#line 179 "gramatica.y"
{System.out.println("ERROR, hay 2 operadores en la linea: " + lector.getNroLinea());}
break;
case 115:
//#line 180 "gramatica.y"
{System.out.println("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
break;
case 116:
//#line 181 "gramatica.y"
{System.out.println("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
break;
case 117:
//#line 182 "gramatica.y"
{System.out.println("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
break;
case 118:
//#line 183 "gramatica.y"
{System.out.println("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
break;
case 119:
//#line 184 "gramatica.y"
{yyval= val_peek(0);}
break;
case 121:
//#line 190 "gramatica.y"
{yyval = val_peek(0);}
break;
case 122:
//#line 191 "gramatica.y"
{
                                                    yyval = val_peek(0);
                                                    Long valor = Long.parseLong(val_peek(0).sval);
                                                    if (valor == 2147483648L){
                                                        yyerror("El número está fuera del rango permitido para un longint positivo en la linea: " + lector.getNroLinea());
                                                    }
                                                    int token =   this.LONGINT;
                                                    lector.tablaSimbolos.addToken(val_peek(0).sval,token,"LONGINT");
                                                }
break;
case 123:
//#line 200 "gramatica.y"
{
                                                    yyval = val_peek(0); /*TODO: posible error*/
                                                    String lexema = '-'+ val_peek(0).sval;
                                                    int token =   this.LONGINT;
                                                    lector.tablaSimbolos.addToken(val_peek(0).sval,token,"LONGINT");
                                                }
break;
case 124:
//#line 207 "gramatica.y"
{
                                                    yyval = val_peek(0);
                                                    String hexa = val_peek(0).sval;
                                                    if (hexa.startsWith("0x")) {
                                                        hexa = hexa.substring(2);
                                                    }
                                                    long num = Long.parseLong(hexa, 16);
                                                    long maxValorAbsoluto = 2147483648L;
                                                    if (num == maxValorAbsoluto){
                                                        yyerror("El número está fuera del rango permitido para un HEXA positivo en la linea: " + lector.getNroLinea());
                                                    }
                                                    int token =   this.HEXA;
                                                    lector.tablaSimbolos.addToken(val_peek(0).sval,token,"HEXA");
                                                }
break;
case 125:
//#line 221 "gramatica.y"
{
                                                    yyval = val_peek(0);
                                                    String lexema = '-'+ val_peek(0).sval;
                                                    int token =   this.HEXA;
                                                    lector.tablaSimbolos.addToken(val_peek(0).sval,token,"HEXA");
                                                }
break;
case 126:
//#line 227 "gramatica.y"
{
                                                    yyval = val_peek(0) ; /* valor del número*/
                                                    String valor = val_peek(0).sval;
                                                    try {
                                                        String numeroStr = valor;
                                                        /* Reemplazamos la 'd' por 'E' para que BigDecimal pueda procesarlo correctamente*/
                                                        if (numeroStr.contains("d") || numeroStr.contains("D")) {
                                                          numeroStr = numeroStr.replace('d', 'E').replace('D', 'E');
                                                        }

                                                        /* Convertimos el valor a BigDecimal*/
                                                        BigDecimal numero = new BigDecimal(numeroStr);
                                                        BigDecimal min = new BigDecimal("2.2250738585072014E-308");
                                                        BigDecimal max = new BigDecimal("1.7976931348623157E+308");

                                                        /* Comparamos el número con los límites permitidos*/
                                                        if ((numero.compareTo(max) > 0) || (numero.compareTo(min) < 0 && numero.compareTo(BigDecimal.ZERO) != 0)){
                                                          yyerror("El número está fuera del rango permitido para un double positivo en la linea: " + lector.getNroLinea());
                                                        } else {
                                                          int token = DOUBLE;
                                                          lector.tablaSimbolos.addToken(valor, token, "DOUBLE");  /* Añade el token*/

                                                        }

                                                      } catch (NumberFormatException e) {
                                                        yyerror("Formato de número inválido.");
                                                      }

                                                }
break;
case 127:
//#line 256 "gramatica.y"
{
                                                    yyval = val_peek(0);
                                                    String valor = val_peek(0).sval;
                                                    System.out.println("llegue para DOUBLE negativo: " + valor);
                                                    try {
                                                          String numeroStr = valor;

                                                          /* Reemplazamos la 'd' por 'E' para notación científica*/
                                                          if (numeroStr.contains("d") || numeroStr.contains("D")) {
                                                            numeroStr = numeroStr.replace('d', 'E').replace('D', 'E');
                                                          }

                                                            /* Convertimos el valor a BigDecimal y lo negamos*/
                                                            BigDecimal numero = new BigDecimal(numeroStr).negate();
                                                            BigDecimal min = new BigDecimal("-1.7976931348623157E+308");
                                                            BigDecimal max = new BigDecimal("-2.2250738585072014E-308");

                                                            /* Comparamos el número con los límites permitidos*/
                                                            if (numero.compareTo(max) > 0 || numero.compareTo(min) < 0) {
                                                              yyerror("El número está fuera del rango permitido para un double negativo en la linea: " + lector.getNroLinea());
                                                            } else {
                                                              int token = this.DOUBLE;
                                                              String negativo = "-" + valor;
                                                              lector.tablaSimbolos.addToken(negativo, token,"DOUBLE");
                                                            }
                                                          } catch (NumberFormatException e) {
                                                            yyerror("Formato de número inválido.");
                                                          }
                                                }
break;
case 131:
//#line 290 "gramatica.y"
{System.out.println(val_peek(0).sval);}
break;
case 132:
//#line 291 "gramatica.y"
{System.out.println("ERROR, falta ',' en la lista ");}
break;
case 133:
//#line 292 "gramatica.y"
{System.out.println("ERROR, falta,' en la lista ");}
break;
case 138:
//#line 305 "gramatica.y"
{System.out.println("ERROR, falta de ',' en la lista en la linea: " + lector.getNroLinea());}
break;
//#line 1399 "Parser.java"
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
