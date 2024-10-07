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
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    8,    8,    7,    7,    7,    7,
    7,    7,    7,    7,    7,    7,    7,    4,    4,    4,
    4,    4,    4,    4,    4,    4,    4,   16,   16,   16,
   16,   16,   16,   16,   16,   15,   15,   15,   15,   15,
   15,   15,   15,   11,   11,   17,   17,   18,   20,   20,
   14,   14,   14,   14,   14,   14,   14,   14,   14,   14,
   14,   14,   14,   14,   14,   14,   14,   14,   14,   14,
   14,   14,   14,   19,   19,   13,   12,   12,   12,   12,
   12,   12,   12,   12,   12,   12,   23,   23,   23,   23,
   23,   23,   23,   23,   23,   23,   23,   10,   10,   10,
   10,   10,   10,   10,   10,   10,    6,    6,    6,    6,
   24,   22,   22,    9,    9,    9,    5,    5,    5,    5,
   21,   21,   21,   21,   21,
};
final static short yylen[] = {                            2,
    4,    3,    2,    3,    2,    2,    2,    1,    1,    1,
    3,    2,    1,    8,    7,    7,    6,    7,    7,    7,
   11,    9,   10,   10,    3,    2,   14,    9,   13,   13,
   13,   12,   12,   13,   13,    9,    8,    1,    1,    1,
    1,    2,    3,    2,    2,    1,    1,    7,    6,    6,
    6,    6,    6,    5,    6,    5,    5,    4,    4,    4,
    3,    3,    5,    2,    1,    4,    3,    3,    2,    1,
    8,   10,    7,    7,    8,    7,    7,    5,    9,    9,
    9,    9,    9,    6,    7,    7,    9,    9,    8,    8,
   10,   10,   10,    3,    3,    4,    3,    3,    4,    4,
    2,    2,    2,    4,    3,    1,    3,    3,    4,    4,
    4,    4,    2,    2,    2,    2,    1,    1,    1,    1,
    1,    2,    1,    2,    1,    2,    3,    3,    1,    1,
    3,    3,    1,    3,    2,    1,    1,    1,    1,    2,
    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    3,    0,    0,    0,    0,    0,
    0,  138,   47,  137,   46,    0,    8,    9,   10,    0,
    0,   13,   38,   39,   40,   41,    0,  129,    0,    0,
    0,    0,  121,  123,  125,    0,    0,    0,    0,    0,
    0,  117,    0,  118,    0,    0,  120,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   45,
    0,    0,    7,    0,    0,    0,    0,    0,   42,    0,
    2,    0,    0,    0,  122,  124,  126,  116,  115,    0,
    0,    0,  145,  144,  143,    0,    0,  141,  142,    0,
    0,    0,    0,    0,   61,    0,    0,    0,    0,   62,
    0,    0,  139,    0,  136,    0,    0,    0,    0,    0,
   70,    0,    0,    0,    0,   43,  131,   67,    0,    0,
    0,    0,   11,    0,    0,    0,  128,    1,    0,  105,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  107,    0,    0,  108,    0,    0,
   58,    0,    0,    0,  140,    0,    0,    0,  135,    0,
    0,    0,    0,   68,   69,    0,    0,    0,    0,   66,
    0,    0,    0,    0,   96,    0,   78,  104,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  109,  110,  112,
  111,   63,   56,   57,    0,    0,    0,    0,    0,    0,
    0,  134,    0,    0,   64,    0,    0,    0,    0,   54,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   84,    0,    0,
    0,    0,   26,    0,    0,   17,    0,    0,    0,    0,
    0,    0,   55,   51,   53,    0,   52,   49,    0,    0,
    0,    0,    0,   74,    0,   85,    0,    0,    0,    0,
   77,    0,    0,   76,    0,   73,    0,    0,   86,   20,
   25,   18,   15,    0,   16,    0,    0,   19,    0,    0,
   48,    0,    0,    0,    0,    0,    0,    0,   75,    0,
    0,    0,   90,    0,    0,    0,   71,    0,   89,    0,
   14,    0,    0,    0,    0,    0,   37,    0,    0,    0,
    0,    0,   87,   82,    0,    0,   81,    0,    0,   83,
   79,   88,    0,   22,    0,    0,    0,   36,   28,    0,
    0,    0,    0,    0,   93,   91,   92,   72,    0,   24,
   23,    0,    0,    0,    0,    0,    0,    0,    0,   21,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   33,    0,   32,    0,    0,   29,   34,    0,   35,
   31,   30,   27,
};
final static short yydgoto[] = {                          3,
   16,   17,   18,   19,   20,   21,   22,  195,  106,   42,
  163,   43,   23,   24,   25,   26,   44,   58,   45,  112,
   90,  125,   46,   47,
};
final static short yysindex[] = {                      -194,
  -76,  710,    0,  613,    0,    2,  307, -242, -236,  -54,
   47,    0,    0,    0,    0, 1003,    0,    0,    0, -140,
  -21,    0,    0,    0,    0,    0,  -18,    0, 1003,  809,
 -201,   47,    0,    0,    0,   20,   75,  160,  437,  437,
  633,    0,  -34,    0,   12,   69,    0,    9,  626,   55,
 -179,  475, -135, -135,   63, 1013,   67,   -3,   79,    0,
 -129,  640,    0,  -30,   87,   42,  660, -128,    0,  831,
    0, -108,  647,   69,    0,    0,    0,    0,    0,  -96,
   32,  660,    0,    0,    0,   68,   68,    0,    0,  660,
 -201,  -83,  -99,  -99,    0,  136,  138,  125,  110,    0,
  546,  -85,    0, -135,    0,   41,   99,  121, -135,   47,
    0,  742,  660,  579,  660,    0,    0,    0,  123,   43,
 -135, -135,    0,   85,   50,  145,    0,    0,  -48,    0,
  168, -201, -201,  -91,   85,   75,   69,   75,   69,   85,
  -59, -201,  437,  437,    0,  437,  437,    0,  151,  170,
    0,  173,  437,  446,    0,  367,   11, -135,    0,  437,
  197,   15,  227,    0,    0,  254,  654,   -5,  256,    0,
  -37,   46,  269,  285,    0,  660,    0,    0,   72,   60,
 -111,   80,   69,   69, -201,  278,  102,    0,    0,    0,
    0,    0,    0,    0,  262,  458,  452,  -11,  325,  145,
  328,    0,  286,   11,    0,  128,  322,  330,    5,    0,
  342,  344,  154,  382, 1003,  165,  174,   85,  381, -201,
  388,  187, -201,  148,  618,  394,  194,    0, -201,  398,
  401,  437,    0,  410,    4,    0,  412,   11,  -29,  416,
  434, 1003,    0,    0,    0,  420,    0,    0, 1003,  847,
  871, 1003, 1003,    0,  231,    0,  445,  249,  -56,  454,
    0,  769,  461,    0,  255,    0,  474,  274,    0,    0,
    0,    0,    0,  479,    0,  497,  272,    0,  -22,  891,
    0,  911,  927,  947,  504,  967,  987,  494,    0,  495,
  293,  314,    0,  522,  321,  -39,    0,  534,    0,  536,
    0,   -9,  541,  333,  565,  568,    0,  694,  570,  660,
  571,  574,    0,    0,  557,  564,    0,  576,  577,    0,
    0,    0,  -52,    0,  581,  660,  660,    0,    0,  619,
  660,  251,  660,  660,    0,    0,    0,    0,  582,    0,
    0,  609,  670,  660,   59,  699,  586,  713,  729,    0,
  588,  598,  734,  402,  606,  409,  620,  625,  411,  425,
  632,    0,  432,    0,  439,  443,    0,    0,  453,    0,
    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   52,    0,    0,    0,    0,  698,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  706,
    0,  480,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  507,    0,    0,    0,    0,
 -130,    0,    0,    0,    0,    0,    0,    0,  112,    0,
    0,    0,    0,    0,  134,  244,    0,    0,    0,  716,
    0,    0,    0,  520,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  527,  547,    0,    0,    0,
    0,    0,  487,  515,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -17,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   62,    0,   88,    0,    0,    0,    0,
    0,    0,    0,    0,  -25,    0,  553,    0,  558,   21,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  156,
    0,  180,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -35,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  584,  589,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  676,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   64,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  202,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  224,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
    6,  -15,    0,  -42,  900,   -7,    0, -102,  -32,  901,
  -82,   -6,    0,    0,    0,    0,   49,  805,  -23,    0,
    0,    0,  -12,    1,
};
final static int YYTABLESIZE=1295;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         50,
   63,   28,   28,  213,   60,   65,  340,   30,   86,  121,
   87,  277,   66,  111,   63,   95,   28,   81,  304,  320,
   28,  108,   68,   56,   74,   88,  130,   89,   55,   28,
   28,  323,   57,   95,   70,  211,  115,  172,  173,  174,
   69,   49,   99,   39,   37,  246,   38,  236,   40,   27,
   27,  198,   92,  210,   63,  119,   28,  203,   56,   73,
  124,   94,  273,  245,   27,    1,  131,   95,  127,  165,
   28,  156,  134,  137,  139,  135,    2,   27,   27,   94,
  157,  237,  171,  140,  158,   68,   62,  127,  214,  166,
  168,  169,   61,  176,  235,  130,  274,   86,  101,   87,
  123,   86,  109,   87,   27,  133,  113,  132,  175,   39,
   93,   44,   28,  100,   40,   94,   39,  354,   27,   38,
  133,   40,  132,  183,   64,  184,  122,   86,  102,   87,
   65,  127,   61,  130,  140,  103,   12,  116,  224,   14,
  140,  117,  126,  209,  222,    4,  127,  223,   56,  201,
  152,    6,   86,  129,   87,   60,    9,   28,   10,  110,
   27,  132,   13,  170,  158,   86,  181,   87,   56,  218,
   15,   32,   33,   34,  142,   35,  149,  130,  150,   59,
    4,   28,  161,  151,    5,  155,    6,    7,    8,  160,
   61,    9,  130,   10,   11,   12,  241,   13,   14,  185,
    4,   50,  186,   56,   28,   15,    6,  177,  178,  192,
   86,    9,   87,   10,  110,   28,  291,   13,  339,  295,
  251,   82,  319,   80,   65,   15,  102,   59,  193,   27,
  276,  194,   95,  103,   12,   63,  204,   14,   28,  127,
  120,  126,   28,   12,   83,   84,   85,  280,  126,   28,
   28,   28,   28,   28,  282,  284,   67,  286,  287,   28,
  130,  126,   28,   27,   63,  114,   63,  206,   63,   91,
   63,   63,   32,   33,   34,   48,   35,   36,   94,  127,
   28,  200,   28,   28,   28,  205,   28,   28,  308,  133,
   27,  347,   63,   86,  207,   87,  212,   27,   27,   27,
   27,   27,  127,  332,  102,  215,  102,   27,   28,  216,
   27,  103,   12,  103,   12,   14,  139,   14,  220,  342,
  343,  221,  139,  345,  346,  217,  348,  349,   27,  130,
   27,   27,   27,  219,   27,   27,  228,  353,   32,   33,
   34,  226,   35,  127,  127,   32,   33,   34,  127,   35,
  127,  127,  127,  127,  231,  127,   27,  127,  127,  127,
  229,  127,  127,  230,  238,  127,   54,   44,   44,  127,
   44,  239,   44,   44,   44,   44,   44,   44,  240,   44,
  243,   44,   44,   44,  102,   44,   44,  242,  244,  130,
  130,  103,   12,   44,  130,   14,  130,  130,  130,  130,
  247,  130,  248,  130,  130,  130,  259,  130,  130,  260,
  158,   60,   60,  249,   60,  130,   60,   60,   60,   60,
   60,   60,  250,   60,  252,   60,   60,   60,  199,   60,
   60,   75,   76,  253,   77,   59,   59,   60,   59,  254,
   59,   59,   59,   59,   59,   59,  256,   59,  257,   59,
   59,   59,  266,   59,   59,  267,  269,   50,   50,  270,
   50,   59,   50,   50,   50,   50,   50,   50,  272,   50,
  275,   50,   50,   50,  278,   50,   50,  279,  281,   80,
   80,   38,   80,   50,   80,   80,   80,   80,   80,   80,
   38,   80,  288,   80,   80,   80,   38,   80,   80,   12,
   12,  232,   38,  289,   12,   80,   12,   12,   12,   12,
  290,   12,  293,   12,   12,   12,  298,   12,   12,  297,
  119,  119,  119,  119,  119,   12,  119,  114,  114,  114,
  114,  114,  299,  114,  104,  300,  197,  301,  119,  119,
  302,  119,  303,  310,  234,  114,  114,  106,  114,  106,
  106,  106,  313,  314,  315,  113,  113,  113,  113,  113,
  101,  113,  101,  101,  101,  106,  106,  102,  106,  102,
  102,  102,  119,  113,  113,  316,  113,   51,  101,  101,
  317,  101,  318,   52,   53,  102,  102,  103,  102,  103,
  103,  103,  321,   97,  322,   97,   97,   97,   98,  324,
   98,   98,   98,  325,  326,  103,  103,  327,  103,  331,
  333,   97,   97,  334,   97,  335,   98,   98,  167,   98,
   39,   37,  336,   38,   99,   40,   99,   99,   99,  100,
  102,  100,  100,  100,  337,  338,  153,  103,   12,  341,
  350,   14,   99,   99,  356,   99,  359,  100,  100,  351,
  100,   86,   41,   87,   39,   37,  360,   38,  344,   40,
   39,   37,  362,   38,  363,   40,   98,   39,   37,  364,
   38,  367,   40,   80,   39,   37,  264,   38,  365,   40,
  118,   39,   37,  366,   38,  368,   40,  130,   39,   37,
  369,   38,  370,   40,  208,   39,   37,    6,   38,  371,
   40,   39,   37,  372,   38,    5,   40,   32,   33,   34,
  352,   35,   86,  373,   87,    4,   32,   33,   34,  130,
   35,    0,   32,   33,   34,    0,   35,    0,   32,   33,
   34,    0,   35,    0,    0,  119,    0,  119,  102,  355,
    0,   86,  114,   87,  114,  103,   12,    0,    0,   14,
  119,  119,  119,  357,  119,   86,    0,   87,  119,  119,
  119,    0,  106,    0,  106,  114,  114,  114,    0,  358,
  113,   86,  113,   87,  361,  101,   86,  101,   87,    0,
    0,    0,  102,    0,  102,  106,  106,  106,    0,    0,
    0,    0,    0,  113,  113,  113,    0,    0,  101,  101,
  101,    0,  103,    0,  103,  102,  102,  102,   97,  102,
   97,    0,    0,   98,    0,   98,  103,   12,    0,    0,
   14,    0,    0,    0,    0,  103,  103,  103,    0,    0,
    0,   97,   97,   97,    0,   72,   98,   98,   98,   99,
    0,   99,    0,    0,  100,    0,  100,    0,    0,   32,
   33,   34,    0,   35,   36,    0,    0,    0,    0,    0,
    0,    0,   99,   99,   99,    0,    0,  100,  100,  100,
   31,    0,    0,  261,    0,    0,  262,   56,    0,  263,
    0,   96,    0,   32,   33,   34,    0,   35,   36,   32,
   33,   34,    0,   35,   36,  141,   32,   33,   34,   97,
   35,   36,    0,   32,   33,   34,    0,   35,   36,    0,
   32,   33,   34,    0,   35,   36,    0,   32,   33,   34,
    0,   35,   36,    0,   32,   33,   34,    0,   35,   36,
   32,   33,   34,    0,   35,   36,  179,  180,  182,   78,
   79,    0,    0,    0,    0,    0,  187,    0,    0,  328,
    4,  105,  107,  105,  329,    0,    6,    7,    8,  330,
    0,    9,    0,   10,   11,   12,    4,   13,   14,   29,
    0,    0,    6,    7,    8,   15,    0,    9,    0,   10,
   11,   12,    0,   13,   14,  225,    0,    0,    0,  227,
    0,   15,    0,  145,  148,    0,    0,    0,    4,    0,
  154,    0,  164,  105,    6,  159,    0,  159,  162,    9,
    0,   10,  110,    0,    0,   13,    0,    0,    0,  162,
  162,  162,    0,   15,  255,    4,    0,  258,   56,  265,
  294,    6,    0,  268,    0,    0,    9,    0,   10,  110,
    0,    0,   13,  188,  189,    0,  190,  191,    0,    0,
   15,    0,    0,  196,  196,  159,    0,  202,    0,    0,
  196,    0,    0,  292,    0,    4,  296,    0,    0,   71,
  162,    6,    7,    8,    0,    0,    9,    0,   10,   11,
   12,    0,   13,   14,    0,    0,    0,    4,    0,    0,
   15,  128,    0,    6,    7,    8,  233,  196,    9,    0,
   10,   11,   12,    4,   13,   14,  283,    0,    0,    6,
    7,    8,   15,    0,    9,    0,   10,   11,   12,    0,
   13,   14,    0,    0,    0,    0,    0,    4,   15,    0,
    0,    0,  271,    6,    7,    8,  285,    0,    9,    0,
   10,   11,   12,    0,   13,   14,    0,    4,    0,    0,
    0,    0,   15,    6,    7,    8,  305,    0,    9,    0,
   10,   11,   12,    0,   13,   14,    0,    4,    0,    0,
    0,    0,   15,    6,    7,    8,  306,    0,    9,    0,
   10,   11,   12,    4,   13,   14,    0,  307,    0,    6,
    7,    8,   15,    0,    9,    0,   10,   11,   12,    0,
   13,   14,    0,    4,    0,    0,    0,    0,   15,    6,
    7,    8,  309,    0,    9,    0,   10,   11,   12,    0,
   13,   14,    0,    4,    0,    0,    0,    0,   15,    6,
    7,    8,  311,    0,    9,    0,   10,   11,   12,    0,
   13,   14,    0,    4,    0,    0,    0,    0,   15,    6,
    7,    8,  312,    0,    9,    0,   10,   11,   12,    4,
   13,   14,    0,    0,    0,    6,    7,    8,   15,    4,
    9,    0,   10,   11,   12,    6,   13,   14,    0,    0,
    9,    0,   10,  110,   15,    0,   13,    0,    0,    0,
    0,    0,    0,    0,   15,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          6,
   16,    1,    2,   41,   59,   41,   59,    2,   43,   40,
   45,   41,   20,   56,   30,   41,   16,   41,   41,   59,
   20,   54,   44,  260,   37,   60,   44,   62,  271,   29,
   30,   41,  269,   59,   29,   41,   40,  120,  121,  122,
   59,   40,   49,   42,   43,   41,   45,   59,   47,    1,
    2,  154,   41,   59,   70,   62,   56,  160,  260,   40,
   67,   41,   59,   59,   16,  260,   73,   59,   68,  112,
   70,  104,   41,   86,   87,   82,  271,   29,   30,   59,
   40,   93,   40,   90,   44,   44,   40,    0,  171,  113,
  114,  115,   46,   44,  197,   44,   93,   43,  278,   45,
   59,   43,   40,   45,   56,   44,   40,   44,   59,   42,
   42,    0,  112,   59,   47,   47,   42,   59,   70,   45,
   59,   47,   59,  136,  265,  138,   40,   43,  264,   45,
  271,   44,   46,    0,  265,  271,  272,   59,  181,  275,
  271,  271,  271,  167,  256,  257,   59,  259,  260,  157,
   41,  263,   43,  262,   45,    0,  268,  157,  270,  271,
  112,  258,  274,   41,   44,   43,  258,   45,  260,  176,
  282,  271,  272,  273,  258,  275,   41,   44,   41,    0,
  257,  181,   62,   59,  261,  271,  263,  264,  265,   91,
   46,  268,   59,  270,  271,  272,  204,  274,  275,  259,
  257,    0,  262,  260,  204,  282,  263,  256,   41,   59,
   43,  268,   45,  270,  271,  215,  259,  274,  271,  262,
  215,  256,  262,    0,  260,  282,  264,  282,   59,  181,
  238,   59,  258,  271,  272,  251,   40,  275,  238,  239,
  271,  271,  242,    0,  279,  280,  281,  242,  271,  249,
  250,  251,  252,  253,  249,  250,  278,  252,  253,  259,
  278,  271,  262,  215,  280,  269,  282,   41,  284,  258,
  286,  287,  271,  272,  273,  274,  275,  276,  258,  279,
  280,  271,  282,  283,  284,  271,  286,  287,  283,  258,
  242,   41,  308,   43,   41,   45,   41,  249,  250,  251,
  252,  253,  302,  310,  264,  260,  264,  259,  308,   41,
  262,  271,  272,  271,  272,  275,  265,  275,  259,  326,
  327,  262,  271,  330,  331,   41,  333,  334,  280,  278,
  282,  283,  284,  262,  286,  287,   59,  344,  271,  272,
  273,  262,  275,  256,  257,  271,  272,  273,  261,  275,
  263,  264,  265,  266,   93,  268,  308,  270,  271,  272,
  259,  274,  275,  262,   40,  278,   60,  256,  257,  282,
  259,   44,  261,  262,  263,  264,  265,  266,   93,  268,
   59,  270,  271,  272,  264,  274,  275,  260,   59,  256,
  257,  271,  272,  282,  261,  275,  263,  264,  265,  266,
   59,  268,   59,  270,  271,  272,  259,  274,  275,  262,
   44,  256,  257,  260,  259,  282,  261,  262,  263,  264,
  265,  266,   41,  268,  260,  270,  271,  272,   62,  274,
  275,  272,  273,  260,  275,  256,  257,  282,  259,   59,
  261,  262,  263,  264,  265,  266,   59,  268,  262,  270,
  271,  272,   59,  274,  275,  262,   59,  256,  257,   59,
  259,  282,  261,  262,  263,  264,  265,  266,   59,  268,
   59,  270,  271,  272,   59,  274,  275,   44,   59,  256,
  257,   45,  259,  282,  261,  262,  263,  264,  265,  266,
   45,  268,  262,  270,  271,  272,   45,  274,  275,  256,
  257,   44,   45,   59,  261,  282,  263,  264,  265,  266,
  262,  268,   59,  270,  271,  272,  262,  274,  275,   59,
   41,   42,   43,   44,   45,  282,   47,   41,   42,   43,
   44,   45,   59,   47,   60,  262,   91,   59,   59,   60,
   44,   62,  271,   40,   93,   59,   60,   41,   62,   43,
   44,   45,   59,   59,  262,   41,   42,   43,   44,   45,
   41,   47,   43,   44,   45,   59,   60,   41,   62,   43,
   44,   45,   93,   59,   60,  262,   62,  271,   59,   60,
   59,   62,  262,  277,  278,   59,   60,   41,   62,   43,
   44,   45,   59,   41,   59,   43,   44,   45,   41,   59,
   43,   44,   45,  271,   40,   59,   60,   40,   62,   40,
   40,   59,   60,   40,   62,   59,   59,   60,   40,   62,
   42,   43,   59,   45,   41,   47,   43,   44,   45,   41,
  264,   43,   44,   45,   59,   59,   91,  271,  272,   59,
   59,  275,   59,   60,   59,   62,   59,   59,   60,   41,
   62,   43,   40,   45,   42,   43,   59,   45,   40,   47,
   42,   43,  261,   45,   59,   47,   41,   42,   43,  261,
   45,  261,   47,   41,   42,   43,   59,   45,   59,   47,
   41,   42,   43,   59,   45,  261,   47,   41,   42,   43,
   59,   45,  261,   47,   41,   42,   43,    0,   45,  261,
   47,   42,   43,  261,   45,    0,   47,  271,  272,  273,
   41,  275,   43,  261,   45,    0,  271,  272,  273,   44,
  275,   -1,  271,  272,  273,   -1,  275,   -1,  271,  272,
  273,   -1,  275,   -1,   -1,  256,   -1,  258,  264,   41,
   -1,   43,  256,   45,  258,  271,  272,   -1,   -1,  275,
  271,  272,  273,   41,  275,   43,   -1,   45,  279,  280,
  281,   -1,  256,   -1,  258,  279,  280,  281,   -1,   41,
  256,   43,  258,   45,   41,  256,   43,  258,   45,   -1,
   -1,   -1,  256,   -1,  258,  279,  280,  281,   -1,   -1,
   -1,   -1,   -1,  279,  280,  281,   -1,   -1,  279,  280,
  281,   -1,  256,   -1,  258,  279,  280,  281,  256,  264,
  258,   -1,   -1,  256,   -1,  258,  271,  272,   -1,   -1,
  275,   -1,   -1,   -1,   -1,  279,  280,  281,   -1,   -1,
   -1,  279,  280,  281,   -1,   31,  279,  280,  281,  256,
   -1,  258,   -1,   -1,  256,   -1,  258,   -1,   -1,  271,
  272,  273,   -1,  275,  276,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  279,  280,  281,   -1,   -1,  279,  280,  281,
  258,   -1,   -1,  256,   -1,   -1,  259,  260,   -1,  262,
   -1,  256,   -1,  271,  272,  273,   -1,  275,  276,  271,
  272,  273,   -1,  275,  276,   91,  271,  272,  273,  274,
  275,  276,   -1,  271,  272,  273,   -1,  275,  276,   -1,
  271,  272,  273,   -1,  275,  276,   -1,  271,  272,  273,
   -1,  275,  276,   -1,  271,  272,  273,   -1,  275,  276,
  271,  272,  273,   -1,  275,  276,  132,  133,  134,   39,
   40,   -1,   -1,   -1,   -1,   -1,  142,   -1,   -1,  256,
  257,   52,   53,   54,  261,   -1,  263,  264,  265,  266,
   -1,  268,   -1,  270,  271,  272,  257,  274,  275,  260,
   -1,   -1,  263,  264,  265,  282,   -1,  268,   -1,  270,
  271,  272,   -1,  274,  275,  181,   -1,   -1,   -1,  185,
   -1,  282,   -1,   93,   94,   -1,   -1,   -1,  257,   -1,
  101,   -1,  261,  104,  263,  106,   -1,  108,  109,  268,
   -1,  270,  271,   -1,   -1,  274,   -1,   -1,   -1,  120,
  121,  122,   -1,  282,  220,  257,   -1,  223,  260,  225,
  262,  263,   -1,  229,   -1,   -1,  268,   -1,  270,  271,
   -1,   -1,  274,  143,  144,   -1,  146,  147,   -1,   -1,
  282,   -1,   -1,  153,  154,  156,   -1,  158,   -1,   -1,
  160,   -1,   -1,  259,   -1,  257,  262,   -1,   -1,  261,
  171,  263,  264,  265,   -1,   -1,  268,   -1,  270,  271,
  272,   -1,  274,  275,   -1,   -1,   -1,  257,   -1,   -1,
  282,  261,   -1,  263,  264,  265,  196,  197,  268,   -1,
  270,  271,  272,  257,  274,  275,  260,   -1,   -1,  263,
  264,  265,  282,   -1,  268,   -1,  270,  271,  272,   -1,
  274,  275,   -1,   -1,   -1,   -1,   -1,  257,  282,   -1,
   -1,   -1,  232,  263,  264,  265,  266,   -1,  268,   -1,
  270,  271,  272,   -1,  274,  275,   -1,  257,   -1,   -1,
   -1,   -1,  282,  263,  264,  265,  266,   -1,  268,   -1,
  270,  271,  272,   -1,  274,  275,   -1,  257,   -1,   -1,
   -1,   -1,  282,  263,  264,  265,  266,   -1,  268,   -1,
  270,  271,  272,  257,  274,  275,   -1,  261,   -1,  263,
  264,  265,  282,   -1,  268,   -1,  270,  271,  272,   -1,
  274,  275,   -1,  257,   -1,   -1,   -1,   -1,  282,  263,
  264,  265,  266,   -1,  268,   -1,  270,  271,  272,   -1,
  274,  275,   -1,  257,   -1,   -1,   -1,   -1,  282,  263,
  264,  265,  266,   -1,  268,   -1,  270,  271,  272,   -1,
  274,  275,   -1,  257,   -1,   -1,   -1,   -1,  282,  263,
  264,  265,  266,   -1,  268,   -1,  270,  271,  272,  257,
  274,  275,   -1,   -1,   -1,  263,  264,  265,  282,  257,
  268,   -1,  270,  271,  272,  263,  274,  275,   -1,   -1,
  268,   -1,  270,  271,  282,   -1,  274,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  282,
};
}
final static short YYFINAL=3;
final static short YYMAXTOKEN=283;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,null,"';'",
"'<'",null,"'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
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
"MAYOR_IGUAL","ETIQUETA","LOWER_THAN_ELSE",
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
"sentencia_declaracion : tipo lista_variables ';'",
"sentencia_declaracion : tipo lista_variables",
"sentencia_declaracion : declaracion_funcion",
"sentencia_declaracion : TYPEDEF ID ASIGNACION tipo '[' subrango ']' ';'",
"sentencia_declaracion : TYPEDEF ID ASIGNACION tipo '[' subrango ';'",
"sentencia_declaracion : TYPEDEF ID ASIGNACION tipo subrango ']' ';'",
"sentencia_declaracion : TYPEDEF ID ASIGNACION tipo subrango ';'",
"sentencia_declaracion : TYPEDEF ID ASIGNACION tipo '[' ']' ';'",
"sentencia_declaracion : TYPEDEF ASIGNACION tipo '[' subrango ']' ';'",
"sentencia_declaracion : TYPEDEF ID ASIGNACION '[' subrango ']' ';'",
"sentencia_declaracion : TYPEDEF STRUCT '<' lista_tipos '>' '(' lista_variables ',' ')' ID ';'",
"sentencia_declaracion : TYPEDEF STRUCT lista_tipos '(' lista_variables ',' ')' ID ';'",
"sentencia_declaracion : TYPEDEF '<' lista_tipos '>' '(' lista_variables ',' ')' ID ';'",
"sentencia_declaracion : TYPEDEF STRUCT '<' lista_tipos '>' '(' lista_variables ',' ')' ';'",
"subrango : factor ',' factor",
"subrango : factor factor",
"declaracion_funcion : tipo FUN ID '(' parametro ')' BEGIN cuerpo RET '(' expresion ')' ';' END",
"declaracion_funcion : tipo FUN ID '(' parametro ')' BEGIN cuerpo END",
"declaracion_funcion : FUN ID '(' parametro ')' BEGIN cuerpo RET '(' expresion ')' ';' END",
"declaracion_funcion : tipo ID '(' parametro ')' BEGIN cuerpo RET '(' expresion ')' ';' END",
"declaracion_funcion : tipo FUN '(' parametro ')' BEGIN cuerpo RET '(' expresion ')' ';' END",
"declaracion_funcion : tipo FUN ID parametro BEGIN cuerpo RET '(' expresion ')' ';' END",
"declaracion_funcion : tipo FUN ID '(' parametro ')' BEGIN cuerpo RET expresion ';' END",
"declaracion_funcion : tipo FUN ID '(' ')' BEGIN cuerpo RET '(' expresion ')' ';' END",
"declaracion_funcion : tipo FUN ID '(' parametro ')' cuerpo RET '(' expresion ')' ';' END",
"declaracion_funcion : tipo FUN ID '(' parametro ')' BEGIN cuerpo error",
"declaracion_funcion : tipo FUN ID '(' parametro ')' BEGIN END",
"sentencia_ejecucion : asignacion",
"sentencia_ejecucion : condicion_if",
"sentencia_ejecucion : sentencia_print",
"sentencia_ejecucion : sentencia_while",
"sentencia_ejecucion : invocacion_funcion ';'",
"sentencia_ejecucion : GOTO ETIQUETA ';'",
"sentencia_ejecucion : GOTO ETIQUETA",
"sentencia_ejecucion : GOTO ';'",
"sentencia_ejecucion : ETIQUETA",
"sentencia_ejecucion : CML",
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
"parametro : tipo",
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
"condicion_if : IF THEN bloque_sentencia_ejecutable END_IF error",
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
"condicion : expresion error expresion",
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
"factor : id_compuesta",
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
"tipo : ID",
"tipo : TYPEDEF ID",
"comparador : '<'",
"comparador : '>'",
"comparador : MAYOR_IGUAL",
"comparador : MENOR_IGUAL",
"comparador : DISTINTO",
};

//#line 313 "gramatica.y"
void yyerror(String mensaje) {
  String ANSI_RESET = "\u001B[0m";
  String ANSI_RED = "\u001B[31m";
  // funcion utilizada para imprimir errores que produce yacc
  System.out.println(ANSI_RED + mensaje + ANSI_RESET);

}

AnalizadorLexico lector;

int yylex(){
    return lector.yylex();
}
//#line 742 "Parser.java"
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
case 1:
//#line 19 "gramatica.y"
{System.out.println("Fin sentencia prog");}
break;
case 2:
//#line 20 "gramatica.y"
{yyerror("ERROR, falta begin programa principal en la linea: " + lector.getNroLinea());}
break;
case 3:
//#line 21 "gramatica.y"
{yyerror("ERROR, falta el ID del programa principal en la linea: " + lector.getNroLinea());}
break;
case 4:
//#line 22 "gramatica.y"
{yyerror("ERROR, falta END del programa principal en la linea: " + lector.getNroLinea());}
break;
case 5:
//#line 23 "gramatica.y"
{yyerror("ERROR, falta BEGIN,END del programa principal en la linea: " + lector.getNroLinea());}
break;
case 6:
//#line 24 "gramatica.y"
{yyerror("ERROR, falta ID,END del programa principal en la linea: " + lector.getNroLinea());}
break;
case 11:
//#line 35 "gramatica.y"
{/*System.out.println($2);*/}
break;
case 12:
//#line 36 "gramatica.y"
{yyerror("ERROR, Falta ; en la sentencia de declaracion en la linea: " + lector.getNroLinea());}
break;
case 14:
//#line 38 "gramatica.y"
{System.out.println("Declaracion de Subtipo");}
break;
case 15:
//#line 39 "gramatica.y"
{yyerror("ERROR, Falta de ']' en la linea: " + lector.getNroLinea());}
break;
case 16:
//#line 40 "gramatica.y"
{yyerror("ERROR, Falta de '[' en la linea: " + lector.getNroLinea());}
break;
case 17:
//#line 41 "gramatica.y"
{yyerror("ERROR, Falta de llaves '[]' en la linea: " + lector.getNroLinea());}
break;
case 18:
//#line 42 "gramatica.y"
{yyerror("ERROR, Falta de rango en la linea: " + lector.getNroLinea());}
break;
case 19:
//#line 43 "gramatica.y"
{yyerror("ERROR, Falta nombre del tipo definido en la linea: " + lector.getNroLinea());}
break;
case 20:
//#line 44 "gramatica.y"
{yyerror("ERROR, Falta el tipo base en la linea: " + lector.getNroLinea());}
break;
case 21:
//#line 45 "gramatica.y"
{System.out.println("Declaracion de Struct");}
break;
case 22:
//#line 46 "gramatica.y"
{yyerror("ERROR, Falta <> en la linea: " + lector.getNroLinea());}
break;
case 23:
//#line 47 "gramatica.y"
{yyerror("ERROR, Falta la palabra STRUCT en la linea: " + lector.getNroLinea());}
break;
case 24:
//#line 48 "gramatica.y"
{yyerror("ERROR, Falta ID al final de la declaracion en la linea: " + lector.getNroLinea());}
break;
case 26:
//#line 52 "gramatica.y"
{yyerror("ERROR, Falta ',' entre los digitos del subrango en la linea: " + lector.getNroLinea());}
break;
case 27:
//#line 55 "gramatica.y"
{System.out.println("Declaracion de Funcion");}
break;
case 28:
//#line 56 "gramatica.y"
{yyerror("ERROR, Falta sentencia return en la linea: " + lector.getNroLinea());}
break;
case 29:
//#line 57 "gramatica.y"
{yyerror("ERROR, Falta la declaracion del tipo de la FUN en la linea: " + lector.getNroLinea());}
break;
case 30:
//#line 58 "gramatica.y"
{yyerror("ERROR, Falta la declaracion de la palabra reservada FUN en la linea: " + lector.getNroLinea());}
break;
case 31:
//#line 59 "gramatica.y"
{yyerror("ERROR, Falta el ID de la funcion en la linea: " + lector.getNroLinea());}
break;
case 32:
//#line 60 "gramatica.y"
{yyerror("ERROR, Falta de () a la hora de los parametros en la linea: " + lector.getNroLinea());}
break;
case 33:
//#line 61 "gramatica.y"
{yyerror("ERROR, Falta de () a la hora de la expresion en la linea: " + lector.getNroLinea());}
break;
case 34:
//#line 62 "gramatica.y"
{yyerror("ERROR, Falta de parametros en la FUN en la linea: " + lector.getNroLinea());}
break;
case 35:
//#line 63 "gramatica.y"
{yyerror("ERROR, Falta de BEGIN en la FUN en la linea: " + lector.getNroLinea());}
break;
case 36:
//#line 64 "gramatica.y"
{System.out.println("ERROR,Falta de END en la FUN en la linea: " + lector.getNroLinea());}
break;
case 37:
//#line 65 "gramatica.y"
{yyerror("ERROR, Falsa cuerpo de funcion en la linea: " + lector.getNroLinea());}
break;
case 43:
//#line 73 "gramatica.y"
{System.out.println("Declaracion de GOTO");}
break;
case 44:
//#line 74 "gramatica.y"
{yyerror("ERROR, Falta ';' al final de la sentencia en la linea: " + lector.getNroLinea());}
break;
case 45:
//#line 75 "gramatica.y"
{yyerror("ERROR, falta la ETIQUETA en la linea: " + lector.getNroLinea());}
break;
case 48:
//#line 81 "gramatica.y"
{System.out.println("Declaracion REPEAT-WHILE");}
break;
case 49:
//#line 82 "gramatica.y"
{yyerror("ERROR, falta palabra WHILE en la linea: " + lector.getNroLinea());}
break;
case 50:
//#line 83 "gramatica.y"
{yyerror("ERROR, falta palabra ';' al final de la declaracion en la linea: " + lector.getNroLinea());}
break;
case 51:
//#line 84 "gramatica.y"
{yyerror("ERROR, falta la condicion del WHILE en la linea: " + lector.getNroLinea());}
break;
case 52:
//#line 85 "gramatica.y"
{yyerror("ERROR, falta parentesis '(' en la declaracion de la condicion de WHILE en la linea: " + lector.getNroLinea());}
break;
case 53:
//#line 86 "gramatica.y"
{yyerror("ERROR, falta parentesis ')' en la declaracion de la condicion de WHILE en la linea: " + lector.getNroLinea());}
break;
case 54:
//#line 87 "gramatica.y"
{yyerror("ERROR, falta parentesis en la declaracion de la condicion de WHILE en la linea: " + lector.getNroLinea());}
break;
case 55:
//#line 88 "gramatica.y"
{yyerror("ERROR, falta el cuerpo de la iteracion repeat en la linea: " + lector.getNroLinea());}
break;
case 58:
//#line 93 "gramatica.y"
{yyerror("ERROR, Falta parámetro en sentencia OUTF en la linea: " + lector.getNroLinea());}
break;
case 59:
//#line 94 "gramatica.y"
{yyerror("ERROR, Falta ';' en la sentencia OUTF en la linea: " + lector.getNroLinea());}
break;
case 60:
//#line 95 "gramatica.y"
{yyerror("ERROR, Falta ';' en la sentencia OUTF en la linea: " + lector.getNroLinea());}
break;
case 61:
//#line 96 "gramatica.y"
{yyerror("ERROR, Faltan los parentesis en la sentencia OUTF en la linea: " + lector.getNroLinea());}
break;
case 62:
//#line 97 "gramatica.y"
{yyerror("ERROR, Faltan los parentesis en la sentencia OUTF en la linea: " + lector.getNroLinea());}
break;
case 63:
//#line 98 "gramatica.y"
{yyerror("ERROR, tipo invalido como parametro para la sentencia OUTF en la linea: " + lector.getNroLinea());}
break;
case 65:
//#line 102 "gramatica.y"
{yyerror("ERROR, falta declaracion de TIPO o NOMBRE en el parametro de la linea: " + lector.getNroLinea());}
break;
case 66:
//#line 105 "gramatica.y"
{if (val_peek(3).sval.equals(null)){ yyerror("No existe una funcion con ese nombre en la linea: " + lector.getNroLinea());}}
break;
case 67:
//#line 107 "gramatica.y"
{yyerror("ERROR, falta parametro en la invocacion de la funcion en la linea: " + lector.getNroLinea());}
break;
case 71:
//#line 117 "gramatica.y"
{System.out.println("Declaracion de IF");}
break;
case 72:
//#line 118 "gramatica.y"
{System.out.println("Declaracion de IF,ELSE");}
break;
case 73:
//#line 119 "gramatica.y"
{yyerror("ERROR, Falta THEN luego de la condicion en la linea: " + lector.getNroLinea());}
break;
case 74:
//#line 120 "gramatica.y"
{yyerror("ERROR,falta de Condicion en la linea: " + lector.getNroLinea());}
break;
case 75:
//#line 121 "gramatica.y"
{yyerror("ERROR,falta el bloque ejecutable en la linea: " + lector.getNroLinea());}
break;
case 76:
//#line 122 "gramatica.y"
{yyerror("ERROR,falta END_IF al final de la declaracion en la linea: " + lector.getNroLinea());}
break;
case 77:
//#line 123 "gramatica.y"
{yyerror("ERROR,falta ; al final de la declaracion del bloque IF en la linea: " + lector.getNroLinea());}
break;
case 78:
//#line 124 "gramatica.y"
{yyerror("ERROR,falta ';' al final de la declaracion en la linea: " + lector.getNroLinea());}
break;
case 79:
//#line 125 "gramatica.y"
{yyerror("ERROR, falta ELSE luego de la sentencias de ejecucion en la linea: " + lector.getNroLinea());}
break;
case 80:
//#line 126 "gramatica.y"
{yyerror("ERROR,falta ';' al final de la declaracion en la linea: " + lector.getNroLinea());}
break;
case 81:
//#line 127 "gramatica.y"
{yyerror("ERROR, falta el bloque ejecutable en el ELSE en la linea: " + lector.getNroLinea());}
break;
case 82:
//#line 128 "gramatica.y"
{yyerror("ERROR, falta el bloque ejecutable en el IF en la linea: " + lector.getNroLinea());}
break;
case 83:
//#line 129 "gramatica.y"
{yyerror("ERROR,falta END_IF al final de la declaracion en la linea: " + lector.getNroLinea());}
break;
case 84:
//#line 130 "gramatica.y"
{yyerror("ERROR,falta de parentesis en la linea: " + lector.getNroLinea());}
break;
case 85:
//#line 131 "gramatica.y"
{yyerror("ERROR,falta un parentesis ')' en la linea: " + lector.getNroLinea());}
break;
case 86:
//#line 132 "gramatica.y"
{yyerror("ERROR,falta un parentesis '(' en la linea: " + lector.getNroLinea());}
break;
case 87:
//#line 133 "gramatica.y"
{yyerror("ERROR,falta un parentesis ')' "); }
break;
case 88:
//#line 134 "gramatica.y"
{yyerror("ERROR,falta un parentesis '(' "); }
break;
case 89:
//#line 135 "gramatica.y"
{yyerror("ERROR,falta un parentesis '()' "); }
break;
case 91:
//#line 137 "gramatica.y"
{System.out.println("Declaracion de IF,ELSE");}
break;
case 92:
//#line 138 "gramatica.y"
{System.out.println("Declaracion de IF,ELSE");}
break;
case 93:
//#line 139 "gramatica.y"
{System.out.println("Declaracion de IF,ELSE");}
break;
case 95:
//#line 144 "gramatica.y"
{yyerror("ERROR, falta comparador en comparacion en la linea: " + lector.getNroLinea());}
break;
case 96:
//#line 147 "gramatica.y"
{yyval = val_peek(1);}
break;
case 97:
//#line 150 "gramatica.y"
{yyval.ival = val_peek(2).ival + val_peek(0).ival;}
break;
case 98:
//#line 151 "gramatica.y"
{yyval.ival = val_peek(2).ival - val_peek(0).ival;}
break;
case 99:
//#line 152 "gramatica.y"
{yyerror("ERROR, hay 2 operadores en la linea: " + lector.getNroLinea());}
break;
case 100:
//#line 153 "gramatica.y"
{yyerror("ERROR, hay 2 operadores en la linea: " + lector.getNroLinea());}
break;
case 101:
//#line 155 "gramatica.y"
{yyerror("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
break;
case 102:
//#line 156 "gramatica.y"
{yyerror("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
break;
case 103:
//#line 157 "gramatica.y"
{yyerror("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
break;
case 104:
//#line 158 "gramatica.y"
{System.out.println("Declaracion de TOD");}
break;
case 105:
//#line 159 "gramatica.y"
{yyerror("ERROR, falta de expresion en la linea: " + lector.getNroLinea());}
break;
case 106:
//#line 160 "gramatica.y"
{yyval = val_peek(0);}
break;
case 109:
//#line 166 "gramatica.y"
{yyerror("ERROR, hay 2 operadores en la linea: " + lector.getNroLinea());}
break;
case 110:
//#line 167 "gramatica.y"
{yyerror("ERROR, hay 2 operadores en la linea: " + lector.getNroLinea());}
break;
case 111:
//#line 168 "gramatica.y"
{yyerror("ERROR, hay 2 operadores en la linea: " + lector.getNroLinea());}
break;
case 112:
//#line 169 "gramatica.y"
{yyerror("ERROR, hay 2 operadores en la linea: " + lector.getNroLinea());}
break;
case 113:
//#line 170 "gramatica.y"
{yyerror("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
break;
case 114:
//#line 171 "gramatica.y"
{yyerror("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
break;
case 115:
//#line 172 "gramatica.y"
{yyerror("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
break;
case 116:
//#line 173 "gramatica.y"
{yyerror("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
break;
case 117:
//#line 174 "gramatica.y"
{yyval = val_peek(0);}
break;
case 119:
//#line 180 "gramatica.y"
{yyval = val_peek(0);}
break;
case 121:
//#line 182 "gramatica.y"
{
                                                    yyval = val_peek(0);
                                                    Long valor = Long.parseLong(val_peek(0).sval);
                                                    if (valor == 2147483648L){
                                                        yyerror("ERROR, El número está fuera del rango permitido para un longint positivo en la linea: " + lector.getNroLinea());
                                                    }
                                                    int token =   this.LONGINT;
                                                    lector.tablaSimbolos.addToken(val_peek(0).sval,token,"LONGINT");
                                                }
break;
case 122:
//#line 191 "gramatica.y"
{
                                                    yyval = val_peek(0); /*TODO: posible error*/
                                                    String lexema = '-'+ val_peek(0).sval;
                                                    int token =   this.LONGINT;
                                                    lector.tablaSimbolos.addToken(val_peek(0).sval,token,"LONGINT");
                                                }
break;
case 123:
//#line 198 "gramatica.y"
{
                                                    yyval = val_peek(0);
                                                    String hexa = val_peek(0).sval;
                                                    if (hexa.startsWith("0x")) {
                                                        hexa = hexa.substring(2);
                                                    }
                                                    long num = Long.parseLong(hexa, 16);
                                                    long maxValorAbsoluto = 2147483648L;
                                                    if (num == maxValorAbsoluto){
                                                        yyerror("ERROR, El número está fuera del rango permitido para un HEXA positivo en la linea: " + lector.getNroLinea());
                                                    }
                                                    int token =   this.HEXA;
                                                    lector.tablaSimbolos.addToken(val_peek(0).sval,token,"HEXA");
                                                }
break;
case 124:
//#line 212 "gramatica.y"
{
                                                    yyval = val_peek(0);
                                                    String lexema = '-'+ val_peek(0).sval;
                                                    int token =   this.HEXA;
                                                    lector.tablaSimbolos.addToken(val_peek(0).sval,token,"HEXA");
                                                }
break;
case 125:
//#line 218 "gramatica.y"
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
                                                          yyerror("ERROR, El número está fuera del rango permitido para un double positivo en la linea: " + lector.getNroLinea());
                                                        } else {
                                                          int token = DOUBLE;
                                                          lector.tablaSimbolos.addToken(valor, token, "DOUBLE");  /* Añade el token*/

                                                        }

                                                      } catch (NumberFormatException e) {
                                                        yyerror("Formato de número inválido.");
                                                      }

                                                }
break;
case 126:
//#line 247 "gramatica.y"
{
                                                    yyval = val_peek(0);
                                                    String valor = val_peek(0).sval;
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
                                                              yyerror("ERROR, El número está fuera del rango permitido para un double negativo en la linea: " + lector.getNroLinea());
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
case 130:
//#line 280 "gramatica.y"
{/*System.out.println($1.sval);*/}
break;
case 135:
//#line 296 "gramatica.y"
{yyerror("ERROR, falta de ',' en la lista en la linea: " + lector.getNroLinea());}
break;
//#line 1389 "Parser.java"
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
