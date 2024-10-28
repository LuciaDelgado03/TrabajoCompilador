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
import java.util.Stack;
import java.math.BigDecimal;
import java.util.ArrayList;
//#line 22 "Parser.java"




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
    3,    3,    3,    3,    8,    8,   11,    7,    7,    7,
    7,    7,    7,    7,    7,    7,    7,    7,    4,    4,
    4,    4,    4,    4,    4,    4,    4,    4,   17,   17,
   17,   17,   17,   17,   17,   17,   16,   16,   16,   16,
   16,   16,   16,   16,   12,   12,   18,   18,   19,   21,
   21,   15,   15,   15,   15,   15,   15,   15,   15,   15,
   15,   15,   15,   15,   15,   15,   15,   15,   15,   22,
   22,   23,   23,   20,   20,   14,   13,   13,   13,   13,
   13,   13,   13,   13,   13,   13,   26,   26,   26,   26,
   26,   26,   26,   26,   26,   26,   26,   10,   10,   10,
   10,   10,   10,   10,   10,   10,    6,    6,    6,    6,
   27,   25,   25,    9,    9,    9,    5,    5,    5,    5,
   24,   24,   24,   24,   24,
};
final static short yylen[] = {                            2,
    4,    3,    2,    3,    2,    2,    2,    1,    1,    1,
    3,    2,    1,    8,    7,    7,    6,    7,    7,    7,
   11,    9,   10,   10,    3,    2,    1,   14,    9,   13,
   13,   13,   12,   12,   13,   13,    9,    8,    1,    1,
    1,    1,    2,    3,    2,    2,    1,    1,    7,    6,
    6,    6,    6,    6,    5,    6,    5,    5,    4,    4,
    4,    3,    3,    5,    2,    1,    4,    3,    3,    2,
    1,    8,   10,    7,    7,    8,    7,    7,    5,    9,
    9,    9,    9,    6,    7,    7,    9,    9,    8,    1,
    1,    1,    1,    3,    3,    4,    3,    3,    4,    4,
    2,    2,    2,    4,    3,    1,    3,    3,    4,    4,
    4,    4,    2,    2,    2,    2,    1,    1,    1,    1,
    1,    2,    1,    2,    1,    2,    3,    3,    1,    1,
    3,    3,    1,    3,    2,    1,    1,    1,    1,    2,
    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    3,    0,    0,    0,    0,    0,
    0,  138,   48,  137,   47,    0,    8,    9,   10,    0,
    0,   13,   39,   40,   41,   42,    0,  129,    0,    0,
    0,    0,  121,  123,  125,    0,    0,    0,    0,    0,
    0,  117,    0,  118,    0,    0,  120,    0,    0,    0,
    0,    0,    0,    0,   27,    0,    0,    0,    0,    0,
   46,    0,    0,    7,    0,    0,    0,    0,    0,    0,
   43,    0,    2,    0,   91,   90,    0,    0,    0,  122,
  124,  126,  116,  115,    0,    0,    0,  145,  144,  143,
    0,    0,  141,  142,    0,    0,    0,    0,    0,   62,
    0,    0,    0,    0,   63,    0,    0,  139,    0,  136,
    0,    0,    0,    0,   71,    0,    0,    0,    0,   44,
  131,   68,    0,    0,    0,   11,    0,    0,    0,    0,
  128,    1,    0,  105,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  107,    0,
    0,  108,    0,    0,   59,    0,    0,    0,  140,    0,
    0,    0,  135,    0,    0,    0,    0,   69,   70,    0,
    0,    0,    0,   67,    0,    0,    0,    0,   96,    0,
   79,  104,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  109,  110,  112,  111,   64,   57,   58,    0,    0,
    0,    0,    0,    0,    0,  134,    0,    0,   65,    0,
    0,    0,    0,   55,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   93,
   92,    0,   84,    0,    0,    0,    0,   26,    0,    0,
   17,    0,    0,    0,    0,    0,    0,   56,   52,   54,
    0,   53,   50,    0,    0,    0,    0,    0,   75,    0,
   85,    0,    0,   77,    0,    0,    0,   74,    0,    0,
   86,   20,   25,   18,   15,    0,   16,    0,    0,   19,
    0,    0,   49,    0,    0,    0,    0,    0,    0,    0,
   76,    0,    0,    0,   72,    0,   89,    0,   14,    0,
    0,    0,    0,    0,    0,   38,    0,    0,    0,    0,
   87,   83,   82,    0,   80,   88,    0,   22,    0,    0,
    0,    0,   37,   29,    0,    0,    0,    0,   73,    0,
   24,   23,    0,    0,    0,    0,    0,    0,    0,    0,
   21,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   34,    0,   33,    0,   30,   32,   35,
    0,   36,   31,   28,
};
final static short yydgoto[] = {                          3,
   16,   17,   18,   19,   20,   21,   22,  199,  111,   42,
   56,  167,   43,   23,   24,   25,   26,   44,   76,   45,
  116,   77,  232,   95,  129,   46,   47,
};
final static short yysindex[] = {                      -216,
  884,  900,    0,  499,    0,  655,  238, -255, -220,  -33,
  109,    0,    0,    0,    0, 1100,    0,    0,    0, -169,
  -40,    0,    0,    0,    0,    0,  -41,    0, 1100,  916,
 1129,  109,    0,    0,    0,  -20,   77,  -91,  626,  626,
  686,    0,   25,    0,    7,   74,    0,  -13,  679,   21,
 -222,  736, -160, -160,    0,   36, 1153,   37,  -30,   39,
    0, -206,  693,    0,  -39,   53,   15,   67,  716, -157,
    0,  932,    0,  109,    0,    0, -144,  700,   74,    0,
    0,    0,    0,    0, -135,   12,  716,    0,    0,    0,
  522,  522,    0,    0,  716, 1129, -126,   82,   82,    0,
   95,   99,  114,  122,    0,  -68, -130,    0, -160,    0,
   38,   86,  151, -160,    0, 1144,  716,  661,  716,    0,
    0,    0,  146, -160,   54,    0, -160,  172,   16,   53,
    0,    0,  -98,    0,  234, 1129, 1129, 1120,  172,   77,
   74,   77,   74,  172, -111, 1129,  626,  626,    0,  626,
  626,    0,  124,  134,    0,  139,  626,  590,    0,  516,
 -103, -160,    0,  626,  161,  -69,  165,    0,    0,  169,
  707,  -14,  170,    0,  173,   72,  -55,  181,    0,  716,
    0,    0,  -15, -106,  868,   10,   74,   74, 1129,  210,
  -65,    0,    0,    0,    0,    0,    0,    0,  187,  444,
  723,  -46,  244,   53,  250,    0,  207, -103,    0,   47,
  253,  258,   -8,    0,  264,  280,   81,   85,  305, 1100,
   96,  172,  318, 1129,  320,  121, 1129,  835,  328,    0,
    0,  132,    0, 1129,  336,  337,  626,    0,  338,  -31,
    0,  341, -103,  -19,  350,  374, 1100,    0,    0,    0,
  360,    0,    0, 1100, 1100,  948,  968, 1100,    0,  171,
    0,  372,  175,    0,  819,  381,  179,    0,  384,  193,
    0,    0,    0,    0,    0,  398,    0,  417,  194,    0,
  -17,  988,    0, 1008, 1028, 1044, 1064,  424, 1084,  407,
    0,  408,  420,  219,    0,  426,    0,  431,    0,   -4,
  432,  232,  465,  472,  473,    0,  774,  474,  716,  477,
    0,    0,    0,  462,    0,    0,  -53,    0,  471,  716,
  716,  716,    0,    0,  667,  716,  254,  716,    0,  481,
    0,    0,  292,  327,  579,  716,   24,  618,  484,  621,
    0,  490,  511,  512,  710,  271,  521,  275,  525,  297,
  326,  344,  556,    0,  356,    0,  362,    0,    0,    0,
  364,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   56,    0,    0,    0,    0,  633,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  644,
    0,  547,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  559,    0,    0,    0,    0,
 -105,    0,    0,    0,    0,    0,    0,    0,    0,  142,
    0,    0,    0,    0,    0,  110,  311,    0,    0,    0,
    0,  645,    0,  -35,    0,    0,    0,    0,  567,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  587,  593,    0,    0,    0,    0,    0,   84,  554,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   42,    0,  164,
    0,    0,    0,    0,    0,    0,    0,    0,  -34,    0,
  598,    0,  624,   -7,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  188,    0,  212,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -27,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  629,  634,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  604,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   44,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  236,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  263,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  291,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    6,  -11,    0,  888,  961,   -9,    0, -122,  -25,  907,
   30,  -86,   -6,    0,    0,    0,    0,  104,    3,  -26,
    0,   34,  -49,    0,    0,   17,    1,
};
final static int YYTABLESIZE=1435;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         50,
  124,   28,   28,   70,   64,  331,   95,   30,  130,  119,
   67,   59,  241,   66,   86,   55,   28,   71,   64,   78,
   28,  279,  157,  302,   95,   61,  215,  275,  113,   28,
   28,   28,  251,   94,   72,  202,  317,  175,  177,   57,
  178,  207,  104,    1,  214,  100,  242,   97,   58,   68,
  250,   94,  138,   79,    2,  106,  123,   28,   70,  180,
   64,  276,  128,   91,  121,   92,   91,   91,   92,   92,
  131,  135,   28,  126,  179,  114,  117,  161,  240,  105,
  139,  162,  346,  160,   93,  133,   94,  132,  144,  219,
  170,  172,  173,  176,  125,   65,   28,  120,   62,  130,
  133,   66,  132,  107,   27,   27,  127,  141,  143,  130,
  108,   12,  218,  130,   14,   98,   28,  133,   39,   27,
   99,   38,  136,   40,  114,  114,  114,  114,  114,  145,
  114,  146,   27,   27,   27,  153,   28,   28,   28,  154,
  159,   45,  114,  114,  213,  114,   28,  189,   63,   27,
  190,  205,  224,  130,   62,  225,  187,  181,  188,  140,
   27,   28,  156,  127,   91,  140,   92,  204,  130,  183,
  184,  186,  155,  222,  260,   27,  164,  263,  267,  191,
   80,   81,  196,   82,  270,   28,  174,   61,   91,   28,
   92,  231,  197,  234,  162,  107,  235,  198,  246,   27,
  208,  209,  108,   12,  220,  210,   14,  127,   28,  211,
  216,   60,  165,  217,   91,  294,   92,  330,  228,   27,
   28,  221,  127,   95,   28,  257,  231,   28,   28,  231,
  231,   55,   66,  278,   28,   51,  231,   69,  118,   27,
   27,   27,  130,   28,  131,   64,  223,   28,   60,   27,
   94,  130,  282,  130,   28,   28,   28,   28,   28,  284,
  285,  287,   78,  289,   96,   28,  130,  231,  233,  137,
   64,  229,   64,   64,  182,   64,   91,   64,   92,  236,
   87,  131,   28,  243,   28,   28,   28,   28,   27,   28,
   81,  307,   27,  244,  339,   64,   91,   54,   92,  245,
  131,  107,  327,   88,   89,   90,  247,   28,  108,   12,
   12,  248,   14,  333,  334,  335,  249,  107,  337,  338,
  139,  340,  252,   27,  108,   12,  139,   27,   14,  345,
   27,   27,  342,  130,   91,  107,   92,   27,  253,  114,
  254,  114,  108,   12,  255,  256,   14,   32,   33,   34,
   27,   35,   32,   33,   34,  258,   35,   27,   27,   27,
   27,   27,  114,  114,  114,  130,  130,  343,   27,   91,
  130,   92,  130,  130,  130,  130,  259,  130,  261,  130,
  130,  130,  262,  130,  130,   27,  268,   27,   27,   27,
   27,  130,   27,  269,  271,  272,  274,   45,   45,  277,
   45,   45,   45,   45,   45,   45,   45,   45,  280,   45,
   27,   45,   45,   45,  107,   45,   45,  281,  283,  127,
  127,  108,   12,   45,  127,   14,  127,  127,  127,  127,
  291,  127,  290,  127,  127,  127,  292,  127,  127,  295,
  296,  127,  297,   61,   61,  127,   61,   61,   61,   61,
   61,   61,   61,   61,  298,   61,  299,   61,   61,   61,
  300,   61,   61,  309,  301,  311,  312,   60,   60,   61,
   60,   60,   60,   60,   60,   60,   60,   60,  313,   60,
  314,   60,   60,   60,  315,   60,   60,  237,   38,  316,
  318,   51,   51,   60,   51,   51,   51,   51,   51,   51,
   51,   51,  319,   51,  320,   51,   51,   51,   51,   51,
   51,  321,  322,  326,   52,   53,  328,   51,   78,   78,
  329,   78,   78,   78,   78,   78,   78,   78,   78,  332,
   78,  354,   78,   78,   78,  356,   78,   78,   41,  341,
   39,   37,  348,   38,   78,   40,   81,   81,  350,   81,
   81,   81,   81,   81,   81,   81,   81,  358,   81,  162,
   81,   81,   81,   39,   81,   81,   12,   12,   40,  351,
  352,   12,   81,   12,   12,   12,   12,  203,   12,  355,
   12,   12,   12,  357,   12,   12,  359,  119,  119,  119,
  119,  119,   12,  119,  113,  113,  113,  113,  113,  106,
  113,  106,  106,  106,  360,  119,  119,  101,  119,  101,
  101,  101,  113,  113,  361,  113,  362,  106,  106,  344,
  106,   91,  363,   92,  364,  101,  101,  102,  101,  102,
  102,  102,    6,  103,   38,  103,  103,  103,   97,  119,
   97,   97,   97,    5,    4,  102,  102,  130,  102,    0,
    0,  103,  103,    0,  103,    0,   97,   97,  347,   97,
   91,  349,   92,   91,   98,   92,   98,   98,   98,   99,
   38,   99,   99,   99,  100,    0,  100,  100,  100,    0,
  201,    0,   98,   98,    0,   98,    0,   99,   99,    0,
   99,    0,  100,  100,   49,  100,   39,   37,    0,   38,
  171,   40,   39,   37,    0,   38,  336,   40,   39,   37,
    0,   38,    0,   40,   32,   33,   34,    0,   35,  103,
   39,   37,    0,   38,    0,   40,   85,   39,   37,    0,
   38,    0,   40,  122,   39,   37,    0,   38,    0,   40,
  134,   39,   37,    0,   38,    0,   40,  212,   39,   37,
  353,   38,   91,   40,   92,    0,   31,   39,   37,    0,
   38,    0,   40,    0,    0,    0,    0,   38,    0,   32,
   33,   34,    0,   35,   36,    0,    0,    0,    0,  107,
    0,    0,    0,    0,    0,    0,  108,   12,    0,    0,
   14,    0,   32,   33,   34,  109,   35,    0,    0,    0,
    0,    0,  119,    0,  119,    0,    0,    0,    0,  113,
    0,  113,    0,    0,  106,  239,  106,  119,  119,  119,
    0,  119,  101,    0,  101,  119,  119,  119,    0,    0,
    0,    0,  113,  113,  113,    0,    0,  106,  106,  106,
    0,    0,  102,    0,  102,  101,  101,  101,  103,    0,
  103,    0,    0,   97,    0,   97,    0,    0,    0,    0,
   32,   33,   34,    0,   35,  102,  102,  102,    0,    0,
    0,  103,  103,  103,    0,    0,   97,   97,   97,   98,
    0,   98,    0,    0,   99,    0,   99,    0,    0,  100,
    0,  100,    0,    0,    0,    0,   32,   33,   34,    0,
   35,    0,   98,   98,   98,    0,    0,   99,   99,   99,
    0,    0,  100,  100,  100,    0,    0,    0,   75,    0,
    0,    0,    0,    0,    0,   32,   33,   34,   48,   35,
   36,   32,   33,   34,  101,   35,   36,   32,   33,   34,
    0,   35,   36,    0,  115,   83,   84,    0,    0,   32,
   33,   34,  102,   35,   36,    0,   32,   33,   34,    0,
   35,   36,    0,   32,   33,   34,    0,   35,   36,    0,
   32,   33,   34,    0,   35,   36,    0,   32,   33,   34,
    0,   35,   36,   75,    0,    0,   32,   33,   34,    0,
   35,   36,    0,   32,   33,   34,    0,   35,    0,  107,
    0,    0,    0,  169,  149,  152,  108,   12,    0,    0,
   14,    0,  110,  112,  110,    0,    0,    0,    0,    0,
    0,    0,    0,   75,   75,   75,    0,    0,    0,  323,
    4,    0,    0,   75,  324,    0,    6,    7,    8,  325,
    0,    9,    0,   10,   11,   12,    0,   13,   14,    0,
    0,    0,    0,  192,  193,   15,  194,  195,    0,    0,
    0,    0,    0,  200,  200,    0,  158,    0,    0,  110,
  200,  163,   75,  163,  166,    4,  230,    0,   57,    0,
  293,    6,    0,    0,  166,  166,    9,  166,   10,   74,
  264,    4,   13,  265,   57,    0,  266,    6,    0,    0,
   15,    0,    9,    0,   10,   74,  238,  200,   13,    0,
    0,  230,    0,    0,  230,  230,   15,    0,    0,    0,
  163,  230,  206,  226,    4,    0,  227,   57,    0,    0,
    6,    0,    0,    0,    0,    9,  166,   10,   74,    0,
    4,   13,    0,  273,    5,    0,    6,    7,    8,   15,
    0,    9,  230,   10,   11,   12,    4,   13,   14,   29,
    0,    0,    6,    7,    8,   15,    0,    9,    0,   10,
   11,   12,    4,   13,   14,    0,   73,    0,    6,    7,
    8,   15,    0,    9,    0,   10,   11,   12,    4,   13,
   14,    0,  132,    0,    6,    7,    8,   15,    0,    9,
    0,   10,   11,   12,    4,   13,   14,  286,    0,    0,
    6,    7,    8,   15,    0,    9,    0,   10,   11,   12,
    0,   13,   14,    0,    4,    0,    0,    0,    0,   15,
    6,    7,    8,  288,    0,    9,    0,   10,   11,   12,
    0,   13,   14,    0,    4,    0,    0,    0,    0,   15,
    6,    7,    8,  303,    0,    9,    0,   10,   11,   12,
    0,   13,   14,    0,    4,    0,    0,    0,    0,   15,
    6,    7,    8,  304,    0,    9,    0,   10,   11,   12,
    0,   13,   14,    0,    4,    0,    0,    0,    0,   15,
    6,    7,    8,  305,    0,    9,    0,   10,   11,   12,
    4,   13,   14,    0,  306,    0,    6,    7,    8,   15,
    0,    9,    0,   10,   11,   12,    0,   13,   14,    0,
    4,    0,    0,    0,    0,   15,    6,    7,    8,  308,
    0,    9,    0,   10,   11,   12,    0,   13,   14,    0,
    4,    0,    0,    0,    0,   15,    6,    7,    8,  310,
    0,    9,    0,   10,   11,   12,    4,   13,   14,    0,
    0,    0,    6,    7,    8,   15,    0,    9,    0,   10,
   11,   12,    0,   13,   14,    0,    4,  185,    0,   57,
    0,   15,    6,    0,    0,    4,    0,    9,   57,   10,
   74,    6,    0,   13,    0,    0,    9,    0,   10,   74,
    4,   15,   13,    0,  168,    0,    6,    0,    0,    4,
   15,    9,    0,   10,   74,    6,    0,   13,    0,    0,
    9,    0,   10,   74,    0,   15,   13,    0,    0,    0,
    0,    0,    0,    0,   15,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          6,
   40,    1,    2,   44,   16,   59,   41,    2,   44,   40,
   20,    9,   59,   41,   41,  271,   16,   59,   30,   40,
   20,   41,   91,   41,   59,   59,   41,   59,   54,   29,
   30,   31,   41,   41,   29,  158,   41,  124,  125,  260,
  127,  164,   49,  260,   59,   59,   93,   41,  269,   20,
   59,   59,   41,   37,  271,  278,   63,   57,   44,   44,
   72,   93,   69,   43,  271,   45,   43,   43,   45,   45,
   70,   78,   72,   59,   59,   40,   40,   40,  201,   59,
   87,   44,   59,  109,   60,   44,   62,   44,   95,  176,
  117,  118,  119,   40,   65,  265,   96,   59,   46,   44,
   59,  271,   59,  264,    1,    2,   40,   91,   92,    0,
  271,  272,   41,  271,  275,   42,  116,  262,   42,   16,
   47,   45,  258,   47,   41,   42,   43,   44,   45,   96,
   47,  258,   29,   30,   31,   41,  136,  137,  138,   41,
  271,    0,   59,   60,  171,   62,  146,  259,   40,   40,
  262,  161,  259,   44,   46,  262,  140,  256,  142,  265,
   57,  161,   41,    0,   43,  271,   45,  271,   59,  136,
  137,  138,   59,  180,  224,   72,   91,  227,  228,  146,
  272,  273,   59,  275,  234,  185,   41,    0,   43,  189,
   45,  189,   59,  259,   44,  264,  262,   59,  208,   96,
   40,  271,  271,  272,  260,   41,  275,   44,  208,   41,
   41,    0,   62,   41,   43,  265,   45,  271,  185,  116,
  220,   41,   59,  258,  224,  220,  224,  227,  228,  227,
  228,  271,  260,  243,  234,    0,  234,  278,  269,  136,
  137,  138,  278,  243,  244,  257,  262,  247,  282,  146,
  258,  271,  247,  271,  254,  255,  256,  257,  258,  254,
  255,  256,    0,  258,  258,  265,  271,  265,   59,  258,
  282,  262,  284,  285,   41,  287,   43,  289,   45,   93,
  256,  281,  282,   40,  284,  285,  286,  287,  185,  289,
    0,  286,  189,   44,   41,  307,   43,   60,   45,   93,
  300,  264,  309,  279,  280,  281,  260,  307,  271,  272,
    0,   59,  275,  320,  321,  322,   59,  264,  325,  326,
  265,  328,   59,  220,  271,  272,  271,  224,  275,  336,
  227,  228,   41,  278,   43,  264,   45,  234,   59,  256,
  260,  258,  271,  272,  260,   41,  275,  271,  272,  273,
  247,  275,  271,  272,  273,  260,  275,  254,  255,  256,
  257,  258,  279,  280,  281,  256,  257,   41,  265,   43,
  261,   45,  263,  264,  265,  266,   59,  268,   59,  270,
  271,  272,  262,  274,  275,  282,   59,  284,  285,  286,
  287,  282,  289,  262,   59,   59,   59,  256,  257,   59,
  259,  260,  261,  262,  263,  264,  265,  266,   59,  268,
  307,  270,  271,  272,  264,  274,  275,   44,   59,  256,
  257,  271,  272,  282,  261,  275,  263,  264,  265,  266,
   59,  268,  262,  270,  271,  272,  262,  274,  275,   59,
  262,  278,   59,  256,  257,  282,  259,  260,  261,  262,
  263,  264,  265,  266,  262,  268,   59,  270,  271,  272,
   44,  274,  275,   40,  271,   59,   59,  256,  257,  282,
  259,  260,  261,  262,  263,  264,  265,  266,   59,  268,
  262,  270,  271,  272,   59,  274,  275,   44,   45,   59,
   59,  256,  257,  282,  259,  260,  261,  262,  263,  264,
  265,  266,  271,  268,   40,  270,  271,  272,  271,  274,
  275,   40,   40,   40,  277,  278,   40,  282,  256,  257,
   59,  259,  260,  261,  262,  263,  264,  265,  266,   59,
  268,  261,  270,  271,  272,  261,  274,  275,   40,   59,
   42,   43,   59,   45,  282,   47,  256,  257,   59,  259,
  260,  261,  262,  263,  264,  265,  266,  261,  268,   44,
  270,  271,  272,   42,  274,  275,  256,  257,   47,   59,
   59,  261,  282,  263,  264,  265,  266,   62,  268,   59,
  270,  271,  272,   59,  274,  275,  261,   41,   42,   43,
   44,   45,  282,   47,   41,   42,   43,   44,   45,   41,
   47,   43,   44,   45,  261,   59,   60,   41,   62,   43,
   44,   45,   59,   60,   59,   62,  261,   59,   60,   41,
   62,   43,  261,   45,  261,   59,   60,   41,   62,   43,
   44,   45,    0,   41,   45,   43,   44,   45,   41,   93,
   43,   44,   45,    0,    0,   59,   60,   44,   62,   -1,
   -1,   59,   60,   -1,   62,   -1,   59,   60,   41,   62,
   43,   41,   45,   43,   41,   45,   43,   44,   45,   41,
   45,   43,   44,   45,   41,   -1,   43,   44,   45,   -1,
   91,   -1,   59,   60,   -1,   62,   -1,   59,   60,   -1,
   62,   -1,   59,   60,   40,   62,   42,   43,   -1,   45,
   40,   47,   42,   43,   -1,   45,   40,   47,   42,   43,
   -1,   45,   -1,   47,  271,  272,  273,   -1,  275,   41,
   42,   43,   -1,   45,   -1,   47,   41,   42,   43,   -1,
   45,   -1,   47,   41,   42,   43,   -1,   45,   -1,   47,
   41,   42,   43,   -1,   45,   -1,   47,   41,   42,   43,
   41,   45,   43,   47,   45,   -1,  258,   42,   43,   -1,
   45,   -1,   47,   -1,   -1,   -1,   -1,   45,   -1,  271,
  272,  273,   -1,  275,  276,   -1,   -1,   -1,   -1,  264,
   -1,   -1,   -1,   -1,   -1,   -1,  271,  272,   -1,   -1,
  275,   -1,  271,  272,  273,   60,  275,   -1,   -1,   -1,
   -1,   -1,  256,   -1,  258,   -1,   -1,   -1,   -1,  256,
   -1,  258,   -1,   -1,  256,   93,  258,  271,  272,  273,
   -1,  275,  256,   -1,  258,  279,  280,  281,   -1,   -1,
   -1,   -1,  279,  280,  281,   -1,   -1,  279,  280,  281,
   -1,   -1,  256,   -1,  258,  279,  280,  281,  256,   -1,
  258,   -1,   -1,  256,   -1,  258,   -1,   -1,   -1,   -1,
  271,  272,  273,   -1,  275,  279,  280,  281,   -1,   -1,
   -1,  279,  280,  281,   -1,   -1,  279,  280,  281,  256,
   -1,  258,   -1,   -1,  256,   -1,  258,   -1,   -1,  256,
   -1,  258,   -1,   -1,   -1,   -1,  271,  272,  273,   -1,
  275,   -1,  279,  280,  281,   -1,   -1,  279,  280,  281,
   -1,   -1,  279,  280,  281,   -1,   -1,   -1,   31,   -1,
   -1,   -1,   -1,   -1,   -1,  271,  272,  273,  274,  275,
  276,  271,  272,  273,  256,  275,  276,  271,  272,  273,
   -1,  275,  276,   -1,   57,   39,   40,   -1,   -1,  271,
  272,  273,  274,  275,  276,   -1,  271,  272,  273,   -1,
  275,  276,   -1,  271,  272,  273,   -1,  275,  276,   -1,
  271,  272,  273,   -1,  275,  276,   -1,  271,  272,  273,
   -1,  275,  276,   96,   -1,   -1,  271,  272,  273,   -1,
  275,  276,   -1,  271,  272,  273,   -1,  275,   -1,  264,
   -1,   -1,   -1,  116,   98,   99,  271,  272,   -1,   -1,
  275,   -1,   52,   53,   54,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  136,  137,  138,   -1,   -1,   -1,  256,
  257,   -1,   -1,  146,  261,   -1,  263,  264,  265,  266,
   -1,  268,   -1,  270,  271,  272,   -1,  274,  275,   -1,
   -1,   -1,   -1,  147,  148,  282,  150,  151,   -1,   -1,
   -1,   -1,   -1,  157,  158,   -1,  106,   -1,   -1,  109,
  164,  111,  185,  113,  114,  257,  189,   -1,  260,   -1,
  262,  263,   -1,   -1,  124,  125,  268,  127,  270,  271,
  256,  257,  274,  259,  260,   -1,  262,  263,   -1,   -1,
  282,   -1,  268,   -1,  270,  271,  200,  201,  274,   -1,
   -1,  224,   -1,   -1,  227,  228,  282,   -1,   -1,   -1,
  160,  234,  162,  256,  257,   -1,  259,  260,   -1,   -1,
  263,   -1,   -1,   -1,   -1,  268,  176,  270,  271,   -1,
  257,  274,   -1,  237,  261,   -1,  263,  264,  265,  282,
   -1,  268,  265,  270,  271,  272,  257,  274,  275,  260,
   -1,   -1,  263,  264,  265,  282,   -1,  268,   -1,  270,
  271,  272,  257,  274,  275,   -1,  261,   -1,  263,  264,
  265,  282,   -1,  268,   -1,  270,  271,  272,  257,  274,
  275,   -1,  261,   -1,  263,  264,  265,  282,   -1,  268,
   -1,  270,  271,  272,  257,  274,  275,  260,   -1,   -1,
  263,  264,  265,  282,   -1,  268,   -1,  270,  271,  272,
   -1,  274,  275,   -1,  257,   -1,   -1,   -1,   -1,  282,
  263,  264,  265,  266,   -1,  268,   -1,  270,  271,  272,
   -1,  274,  275,   -1,  257,   -1,   -1,   -1,   -1,  282,
  263,  264,  265,  266,   -1,  268,   -1,  270,  271,  272,
   -1,  274,  275,   -1,  257,   -1,   -1,   -1,   -1,  282,
  263,  264,  265,  266,   -1,  268,   -1,  270,  271,  272,
   -1,  274,  275,   -1,  257,   -1,   -1,   -1,   -1,  282,
  263,  264,  265,  266,   -1,  268,   -1,  270,  271,  272,
  257,  274,  275,   -1,  261,   -1,  263,  264,  265,  282,
   -1,  268,   -1,  270,  271,  272,   -1,  274,  275,   -1,
  257,   -1,   -1,   -1,   -1,  282,  263,  264,  265,  266,
   -1,  268,   -1,  270,  271,  272,   -1,  274,  275,   -1,
  257,   -1,   -1,   -1,   -1,  282,  263,  264,  265,  266,
   -1,  268,   -1,  270,  271,  272,  257,  274,  275,   -1,
   -1,   -1,  263,  264,  265,  282,   -1,  268,   -1,  270,
  271,  272,   -1,  274,  275,   -1,  257,  258,   -1,  260,
   -1,  282,  263,   -1,   -1,  257,   -1,  268,  260,  270,
  271,  263,   -1,  274,   -1,   -1,  268,   -1,  270,  271,
  257,  282,  274,   -1,  261,   -1,  263,   -1,   -1,  257,
  282,  268,   -1,  270,  271,  263,   -1,  274,   -1,   -1,
  268,   -1,  270,  271,   -1,  282,  274,   -1,   -1,   -1,
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
"actualizar_ambito : ID",
"declaracion_funcion : tipo FUN actualizar_ambito '(' parametro ')' BEGIN cuerpo RET '(' expresion ')' ';' END",
"declaracion_funcion : tipo FUN actualizar_ambito '(' parametro ')' BEGIN cuerpo END",
"declaracion_funcion : FUN actualizar_ambito '(' parametro ')' BEGIN cuerpo RET '(' expresion ')' ';' END",
"declaracion_funcion : tipo actualizar_ambito '(' parametro ')' BEGIN cuerpo RET '(' expresion ')' ';' END",
"declaracion_funcion : tipo FUN '(' parametro ')' BEGIN cuerpo RET '(' expresion ')' ';' END",
"declaracion_funcion : tipo FUN actualizar_ambito parametro BEGIN cuerpo RET '(' expresion ')' ';' END",
"declaracion_funcion : tipo FUN actualizar_ambito '(' parametro ')' BEGIN cuerpo RET expresion ';' END",
"declaracion_funcion : tipo FUN actualizar_ambito '(' ')' BEGIN cuerpo RET '(' expresion ')' ';' END",
"declaracion_funcion : tipo FUN actualizar_ambito '(' parametro ')' cuerpo RET '(' expresion ')' ';' END",
"declaracion_funcion : tipo FUN actualizar_ambito '(' parametro ')' BEGIN cuerpo error",
"declaracion_funcion : tipo FUN actualizar_ambito '(' parametro ')' BEGIN END",
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
"condicion_if : IF '(' condicion ')' THEN cuerpo_then END_IF ';'",
"condicion_if : IF '(' condicion ')' THEN cuerpo_then ELSE cuerpo_else END_IF ';'",
"condicion_if : IF '(' condicion ')' cuerpo_then END_IF ';'",
"condicion_if : IF '(' ')' THEN cuerpo_then END_IF ';'",
"condicion_if : IF '(' condicion ')' THEN error END_IF ';'",
"condicion_if : IF '(' condicion ')' THEN cuerpo_then error",
"condicion_if : IF '(' condicion ')' THEN cuerpo_then END_IF",
"condicion_if : IF THEN cuerpo_then END_IF error",
"condicion_if : IF '(' condicion ')' THEN cuerpo_then cuerpo_else END_IF ';'",
"condicion_if : IF '(' condicion ')' THEN cuerpo_then ELSE cuerpo_else END_IF",
"condicion_if : IF '(' condicion ')' THEN cuerpo_then ELSE END_IF ';'",
"condicion_if : IF '(' condicion ')' THEN ELSE cuerpo_else END_IF ';'",
"condicion_if : IF condicion THEN cuerpo_then END_IF ';'",
"condicion_if : IF '(' condicion THEN cuerpo_then END_IF ';'",
"condicion_if : IF condicion ')' THEN cuerpo_then END_IF ';'",
"condicion_if : IF '(' condicion THEN cuerpo_then ELSE cuerpo_else END_IF ';'",
"condicion_if : IF condicion ')' THEN cuerpo_then ELSE cuerpo_else END_IF ';'",
"condicion_if : IF condicion THEN cuerpo_then ELSE cuerpo_else END_IF ';'",
"cuerpo_then : bloque_sentencia_ejecutable",
"cuerpo_then : sentencia_ejecucion",
"cuerpo_else : bloque_sentencia_ejecutable",
"cuerpo_else : sentencia_ejecucion",
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

//#line 426 "gramatica.y"
void yyerror(String mensaje) {
  String ANSI_RESET = "\u001B[0m";
  String ANSI_RED = "\u001B[31m";
  // funcion utilizada para imprimir errores que produce yacc
  System.out.println(ANSI_RED + mensaje + ANSI_RESET);

}
String ambitoActual = "main";
AnalizadorLexico lector;
public static ArrayList<String> polaca = new ArrayList<>();
public static ArrayList<String> posicionDePolaca = new ArrayList<>();
public static Stack<Integer> pila = new Stack<>(); //para bifuraciones en while/if algun otro
int yylex(){
    return lector.yylex();
}
public static void agregarTokenPolaca(String lexema){
	System.out.println(lexema);
	polaca.add(lexema);
}

public static void agregarCheckpoint(){
	pila.push(polaca.size()); //guardamos el checkpoint en caso de una difurcacion
}

public void cargarTablaSimbolos (String lexema,String tipo, String uso){
    DatosTablaSimbolos datos = lector.tablaSimbolos.getDato(lexema);
    datos.setTipo(tipo);
    datos.setUso(uso);
    datos.setAmbito(lexema+":"+ambitoActual);
    lector.tablaSimbolos.setDato(datos, lexema);
}
public static void imprimirPolaca() {
        // Imprimo la polaca inversa generada en el programa
        if (!polaca.isEmpty()) {
                System.out.println();
                System.out.println("Polaca:");

                for (int i = 0; i < polaca.size(); ++i) {
                        System.out.println(i + " " + polaca.get(i));
                }
        }
}
//#line 797 "Parser.java"
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
    switch(yyn) {
//########## USER-SUPPLIED ACTIONS ##########
      case 1:
//#line 20 "gramatica.y"
      {
        System.out.println("Fin sentencia prog");
      }
      break;
      case 2:
//#line 21 "gramatica.y"
      {
        yyerror("ERROR, falta begin programa principal en la linea: " + lector.getNroLinea());
      }
      break;
      case 3:
//#line 22 "gramatica.y"
      {
        yyerror("ERROR, falta el ID del programa principal en la linea: " + lector.getNroLinea());
      }
      break;
      case 4:
//#line 23 "gramatica.y"
      {
        yyerror("ERROR, falta END del programa principal en la linea: " + lector.getNroLinea());
      }
      break;
      case 5:
//#line 24 "gramatica.y"
      {
        yyerror("ERROR, falta BEGIN,END del programa principal en la linea: " + lector.getNroLinea());
      }
      break;
      case 6:
//#line 25 "gramatica.y"
      {
        yyerror("ERROR, falta ID,END del programa principal en la linea: " + lector.getNroLinea());
      }
      break;
      case 11:
//#line 36 "gramatica.y"
      {/*System.out.println($2);*/}
      break;
      case 12:
//#line 37 "gramatica.y"
      {
        yyerror("ERROR, Falta ; en la sentencia de declaracion en la linea: " + lector.getNroLinea());
      }
      break;
      case 14:
//#line 39 "gramatica.y"
      {
        System.out.println("Declaracion de Subtipo");
        cargarTablaSimbolos(val_peek(6).sval, val_peek(4).sval, "Nombre de Subtipo");
      }
      break;
      case 15:
//#line 42 "gramatica.y"
      {
        yyerror("ERROR, Falta de ']' en la linea: " + lector.getNroLinea());
      }
      break;
      case 16:
//#line 43 "gramatica.y"
      {
        yyerror("ERROR, Falta de '[' en la linea: " + lector.getNroLinea());
      }
      break;
      case 17:
//#line 44 "gramatica.y"
      {
        yyerror("ERROR, Falta de llaves '[]' en la linea: " + lector.getNroLinea());
      }
      break;
      case 18:
//#line 45 "gramatica.y"
      {
        yyerror("ERROR, Falta de rango en la linea: " + lector.getNroLinea());
      }
      break;
      case 19:
//#line 46 "gramatica.y"
      {
        yyerror("ERROR, Falta nombre del tipo definido en la linea: " + lector.getNroLinea());
      }
      break;
      case 20:
//#line 47 "gramatica.y"
      {
        yyerror("ERROR, Falta el tipo base en la linea: " + lector.getNroLinea());
      }
      break;
      case 21:
//#line 48 "gramatica.y"
      {
        System.out.println("Declaracion de Struct");
      }
      break;
      case 22:
//#line 54 "gramatica.y"
      {
        yyerror("ERROR, Falta <> en la linea: " + lector.getNroLinea());
      }
      break;
      case 23:
//#line 55 "gramatica.y"
      {
        yyerror("ERROR, Falta la palabra STRUCT en la linea: " + lector.getNroLinea());
      }
      break;
      case 24:
//#line 56 "gramatica.y"
      {
        yyerror("ERROR, Falta ID al final de la declaracion en la linea: " + lector.getNroLinea());
      }
      break;
      case 26:
//#line 60 "gramatica.y"
      {
        yyerror("ERROR, Falta ',' entre los digitos del subrango en la linea: " + lector.getNroLinea());
      }
      break;
      case 27:
//#line 63 "gramatica.y"
      {
        ambitoActual = ambitoActual + ":" + val_peek(0).sval;
      }
      break;
      case 28:
//#line 67 "gramatica.y"
      {
        System.out.println("Declaracion de Funcion");
        cargarTablaSimbolos(val_peek(11).sval, val_peek(13).sval, "Nombre de Funcion");
        /* Restaurar el ámbito al anterior*/
        ambitoActual = ambitoActual.substring(0, ambitoActual.lastIndexOf(":"));
      }
      break;
      case 29:
//#line 74 "gramatica.y"
      {
        yyerror("ERROR, Falta sentencia return en la linea: " + lector.getNroLinea());
        cargarTablaSimbolos(val_peek(6).sval, val_peek(8).sval, "Nombre de Funcion");
        ambitoActual = ambitoActual.substring(0, ambitoActual.lastIndexOf(":"));
      }
      break;
      case 30:
//#line 79 "gramatica.y"
      {
        yyerror("ERROR, Falta la declaracion del tipo de la FUN en la linea: " + lector.getNroLinea());
        cargarTablaSimbolos(val_peek(11).sval, null, "Nombre de Funcion");
        ambitoActual = ambitoActual.substring(0, ambitoActual.lastIndexOf(":"));
      }
      break;
      case 31:
//#line 84 "gramatica.y"
      {
        yyerror("ERROR, Falta la declaracion de la palabra reservada FUN en la linea: " + lector.getNroLinea());
        cargarTablaSimbolos(val_peek(11).sval, val_peek(12).sval, "Nombre de Funcion");
        ambitoActual = ambitoActual.substring(0, ambitoActual.lastIndexOf(":"));
      }
      break;
      case 32:
//#line 89 "gramatica.y"
      {
        yyerror("ERROR, Falta el ID de la funcion en la linea: " + lector.getNroLinea());

      }
      break;
      case 33:
//#line 93 "gramatica.y"
      {
        yyerror("ERROR, Falta de () a la hora de los parametros en la linea: " + lector.getNroLinea());
        cargarTablaSimbolos(val_peek(9).sval, val_peek(11).sval, "Nombre de Funcion");
        ambitoActual = ambitoActual.substring(0, ambitoActual.lastIndexOf(":"));
      }
      break;
      case 34:
//#line 98 "gramatica.y"
      {
        yyerror("ERROR, Falta de () a la hora de la expresion en la linea: " + lector.getNroLinea());
        cargarTablaSimbolos(val_peek(9).sval, val_peek(11).sval, "Nombre de Funcion");
        ambitoActual = ambitoActual.substring(0, ambitoActual.lastIndexOf(":"));
      }
      break;
      case 35:
//#line 103 "gramatica.y"
      {
        yyerror("ERROR, Falta de parametros en la FUN en la linea: " + lector.getNroLinea());
        cargarTablaSimbolos(val_peek(10).sval, val_peek(12).sval, "Nombre de Funcion");
        ambitoActual = ambitoActual.substring(0, ambitoActual.lastIndexOf(":"));
      }
      break;
      case 36:
//#line 108 "gramatica.y"
      {
        yyerror("ERROR, Falta de BEGIN en la FUN en la linea: " + lector.getNroLinea());
        cargarTablaSimbolos(val_peek(10).sval, val_peek(12).sval, "Nombre de Funcion");
        ambitoActual = ambitoActual.substring(0, ambitoActual.lastIndexOf(":"));
      }
      break;
      case 37:
//#line 113 "gramatica.y"
      {
        System.out.println("ERROR,Falta de END en la FUN en la linea: " + lector.getNroLinea());
        cargarTablaSimbolos(val_peek(6).sval, val_peek(8).sval, "Nombre de Funcion");
        ambitoActual = ambitoActual.substring(0, ambitoActual.lastIndexOf(":"));
      }
      break;
      case 38:
//#line 118 "gramatica.y"
      {
        yyerror("ERROR, Falsa cuerpo de funcion en la linea: " + lector.getNroLinea());
        cargarTablaSimbolos(val_peek(5).sval, val_peek(7).sval, "Nombre de Funcion");
        ambitoActual = ambitoActual.substring(0, ambitoActual.lastIndexOf(":"));
      }
      break;
      case 44:
//#line 129 "gramatica.y"
      {
        System.out.println("Declaracion de GOTO");
      }
      break;
      case 45:
//#line 130 "gramatica.y"
      {
        yyerror("ERROR, Falta ';' al final de la sentencia en la linea: " + lector.getNroLinea());
      }
      break;
      case 46:
//#line 131 "gramatica.y"
      {
        yyerror("ERROR, falta la ETIQUETA en la linea: " + lector.getNroLinea());
      }
      break;
      case 49:
//#line 137 "gramatica.y"
      {
        System.out.println("Declaracion REPEAT-WHILE");
      }
      break;
      case 50:
//#line 138 "gramatica.y"
      {
        yyerror("ERROR, falta palabra WHILE en la linea: " + lector.getNroLinea());
      }
      break;
      case 51:
//#line 139 "gramatica.y"
      {
        yyerror("ERROR, falta palabra ';' al final de la declaracion en la linea: " + lector.getNroLinea());
      }
      break;
      case 52:
//#line 140 "gramatica.y"
      {
        yyerror("ERROR, falta la condicion del WHILE en la linea: " + lector.getNroLinea());
      }
      break;
      case 53:
//#line 141 "gramatica.y"
      {
        yyerror("ERROR, falta parentesis '(' en la declaracion de la condicion de WHILE en la linea: " + lector.getNroLinea());
      }
      break;
      case 54:
//#line 142 "gramatica.y"
      {
        yyerror("ERROR, falta parentesis ')' en la declaracion de la condicion de WHILE en la linea: " + lector.getNroLinea());
      }
      break;
      case 55:
//#line 143 "gramatica.y"
      {
        yyerror("ERROR, falta parentesis en la declaracion de la condicion de WHILE en la linea: " + lector.getNroLinea());
      }
      break;
      case 56:
//#line 144 "gramatica.y"
      {
        yyerror("ERROR, falta el cuerpo de la iteracion repeat en la linea: " + lector.getNroLinea());
      }
      break;
      case 59:
//#line 149 "gramatica.y"
      {
        yyerror("ERROR, Falta parámetro en sentencia OUTF en la linea: " + lector.getNroLinea());
      }
      break;
      case 60:
//#line 150 "gramatica.y"
      {
        yyerror("ERROR, Falta ';' en la sentencia OUTF en la linea: " + lector.getNroLinea());
      }
      break;
      case 61:
//#line 151 "gramatica.y"
      {
        yyerror("ERROR, Falta ';' en la sentencia OUTF en la linea: " + lector.getNroLinea());
      }
      break;
      case 62:
//#line 152 "gramatica.y"
      {
        yyerror("ERROR, Faltan los parentesis en la sentencia OUTF en la linea: " + lector.getNroLinea());
      }
      break;
      case 63:
//#line 153 "gramatica.y"
      {
        yyerror("ERROR, Faltan los parentesis en la sentencia OUTF en la linea: " + lector.getNroLinea());
      }
      break;
      case 64:
//#line 154 "gramatica.y"
      {
        yyerror("ERROR, tipo invalido como parametro para la sentencia OUTF en la linea: " + lector.getNroLinea());
      }
      break;
      case 65:
//#line 157 "gramatica.y"
      {
        cargarTablaSimbolos(val_peek(0).sval, val_peek(1).sval, "Nombre de Parametro");
      }
      break;
      case 66:
//#line 158 "gramatica.y"
      {
        yyerror("ERROR, falta declaracion de TIPO o NOMBRE en el parametro de la linea: " + lector.getNroLinea());
      }
      break;
      case 67:
//#line 161 "gramatica.y"
      {
        if (val_peek(3).sval.equals(null)) {
          yyerror("No existe una funcion con ese nombre en la linea: " + lector.getNroLinea());
        }
      }
      break;
      case 68:
//#line 163 "gramatica.y"
      {
        yyerror("ERROR, falta parametro en la invocacion de la funcion en la linea: " + lector.getNroLinea());
      }
      break;
      case 72:
//#line 175 "gramatica.y"
      {
        System.out.println("Declaracion de IF");
        System.out.println("Declaracion de IF");
        /* Backpatching del BF (completa el salto hacia el final del IF).*/
        int posicionBF = pila.pop();
        polaca.set(posicionBF, ":L" + polaca.size());

      }
      break;
      case 73:
//#line 183 "gramatica.y"
      {
        System.out.println("Declaracion de IF,ELSE");
        /* Backpatching del BF (completa el salto al bloque ELSE).*/
        int posicionBF = pila.pop();
        polaca.set(posicionBF, ":L" + polaca.size());  /* Inicio del ELSE.*/

        /* Backpatching del BI (completa el salto al final del IF-ELSE).*/
        int posicionBI = pila.pop();
        polaca.set(posicionBI, ":L" + polaca.size());  /* Final del IF-ELSE.*/
      }
      break;
      case 74:
//#line 192 "gramatica.y"
      {
        yyerror("ERROR, Falta THEN luego de la condicion en la linea: " + lector.getNroLinea());
      }
      break;
      case 75:
//#line 193 "gramatica.y"
      {
        yyerror("ERROR,falta de Condicion en la linea: " + lector.getNroLinea());
      }
      break;
      case 76:
//#line 194 "gramatica.y"
      {
        yyerror("ERROR,falta el bloque ejecutable en la linea: " + lector.getNroLinea());
      }
      break;
      case 77:
//#line 195 "gramatica.y"
      {
        yyerror("ERROR,falta END_IF; al final de la declaracion en la linea: " + lector.getNroLinea());
      }
      break;
      case 78:
//#line 196 "gramatica.y"
      {
        yyerror("ERROR,falta ; al final de la declaracion del bloque IF en la linea: " + lector.getNroLinea());
      }
      break;
      case 79:
//#line 197 "gramatica.y"
      {
        yyerror("ERROR,falta ';' al final de la declaracion en la linea: " + lector.getNroLinea());
      }
      break;
      case 80:
//#line 198 "gramatica.y"
      {
        yyerror("ERROR, falta ELSE luego de la sentencias de ejecucion en la linea: " + lector.getNroLinea());
      }
      break;
      case 81:
//#line 199 "gramatica.y"
      {
        yyerror("ERROR,falta ';' al final de la declaracion en la linea: " + lector.getNroLinea());
      }
      break;
      case 82:
//#line 200 "gramatica.y"
      {
        yyerror("ERROR, falta el bloque ejecutable en el ELSE en la linea: " + lector.getNroLinea());
      }
      break;
      case 83:
//#line 201 "gramatica.y"
      {
        yyerror("ERROR, falta el bloque ejecutable en el IF en la linea: " + lector.getNroLinea());
      }
      break;
      case 84:
//#line 203 "gramatica.y"
      {
        yyerror("ERROR,falta de parentesis en la linea: " + lector.getNroLinea());
      }
      break;
      case 85:
//#line 204 "gramatica.y"
      {
        yyerror("ERROR,falta un parentesis ')' en la linea: " + lector.getNroLinea());
      }
      break;
      case 86:
//#line 205 "gramatica.y"
      {
        yyerror("ERROR,falta un parentesis '(' en la linea: " + lector.getNroLinea());
      }
      break;
      case 87:
//#line 206 "gramatica.y"
      {
        yyerror("ERROR,falta un parentesis ')' ");
      }
      break;
      case 88:
//#line 207 "gramatica.y"
      {
        yyerror("ERROR,falta un parentesis '(' ");
      }
      break;
      case 89:
//#line 208 "gramatica.y"
      {
        yyerror("ERROR,falta un parentesis '()' ");
      }
      break;
      case 90:
//#line 213 "gramatica.y"
      {
        /* Backpatching del BF (completar el salto hacia ELSE o final del IF).*/
        int posicionBF = pila.pop();
        polaca.set(posicionBF, ":L" + polaca.size());  /* Marca el inicio del ELSE (o final del IF).*/

        /* Generar salto incondicional (BI) al final del IF-ELSE.*/
        agregarCheckpoint();  /* Guardamos la posición para el BI.*/
        agregarTokenPolaca("");  /* Espacio reservado para BI.*/
        agregarTokenPolaca("BI");  /* Marcador del BI.*/
        imprimirPolaca();
      }
      break;
      case 92:
//#line 228 "gramatica.y"
      {
        /* Backpatching del BI (completar el salto al final del IF-ELSE).*/
        int posicionBI = pila.pop();
        polaca.set(posicionBI, ":L" + polaca.size());  /* Marca el final del IF-ELSE.*/
        imprimirPolaca();
      }
      break;
      case 94:
//#line 243 "gramatica.y"
      {

        agregarCheckpoint();  /* Guardamos la posición del BF en la pila para backpatching.*/
        agregarTokenPolaca("");  /* Espacio reservado para BF.*/
        agregarTokenPolaca("#BF");  /* Branch False si la condición es falsa.*/


      }
      break;
      case 95:
//#line 251 "gramatica.y"
      {
        yyerror("ERROR, falta comparador en comparacion en la linea: " + lector.getNroLinea());
      }
      break;
      case 96:
//#line 254 "gramatica.y"
      {
        yyval = val_peek(1);
      }
      break;
      case 97:
//#line 257 "gramatica.y"
      {
        yyval.ival = val_peek(2).ival + val_peek(0).ival;
        //agregarTokenPolaca("+");
      }
      break;
      case 98:
//#line 259 "gramatica.y"
      {
        yyval.ival = val_peek(2).ival - val_peek(0).ival;
//						    								 agregarTokenPolaca("-");
      }
      break;
      case 99:
//#line 261 "gramatica.y"
      {
        yyerror("ERROR, hay 2 operadores en la linea: " + lector.getNroLinea());
//						    								 agregarTokenPolaca("+");
      }
      break;
      case 100:
//#line 263 "gramatica.y"
      {
        yyerror("ERROR, hay 2 operadores en la linea: " + lector.getNroLinea());
//						    								 agregarTokenPolaca("-");}
      }break;
case 101:
//#line 266 "gramatica.y"
{yyerror("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
break;
case 102:
//#line 267 "gramatica.y"
{yyerror("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
break;
case 103:
//#line 268 "gramatica.y"
{yyerror("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
break;
case 104:
//#line 269 "gramatica.y"
{System.out.println("Declaracion de TOD");}
break;
case 105:
//#line 270 "gramatica.y"
{yyerror("ERROR, falta de expresion en la linea: " + lector.getNroLinea());}
break;
case 106:
//#line 271 "gramatica.y"
{yyval = val_peek(0);}
break;
case 107:
//#line 275 "gramatica.y"
{//agregarTokenPolaca("*");
   }
break;
case 108:
//#line 276 "gramatica.y"
{//agregarTokenPolaca("/");
}
break;
case 109:
//#line 277 "gramatica.y"
{yyerror("ERROR, hay 2 operadores en la linea: " + lector.getNroLinea());}
break;
case 110:
//#line 278 "gramatica.y"
{yyerror("ERROR, hay 2 operadores en la linea: " + lector.getNroLinea());}
break;
case 111:
//#line 279 "gramatica.y"
{yyerror("ERROR, hay 2 operadores en la linea: " + lector.getNroLinea());}
break;
case 112:
//#line 280 "gramatica.y"
{yyerror("ERROR, hay 2 operadores en la linea: " + lector.getNroLinea());}
break;
case 113:
//#line 281 "gramatica.y"
{yyerror("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
break;
case 114:
//#line 282 "gramatica.y"
{yyerror("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
break;
case 115:
//#line 283 "gramatica.y"
{yyerror("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
break;
case 116:
//#line 284 "gramatica.y"
{yyerror("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
break;
case 117:
//#line 285 "gramatica.y"
{yyval = val_peek(0);}
break;
case 119:
//#line 291 "gramatica.y"
{yyval = val_peek(0);}
break;
case 121:
//#line 293 "gramatica.y"
{
                                                    yyval = val_peek(0);
                                                    Long valor = Long.parseLong(val_peek(0).sval);
                                                    if (valor == 2147483648L){
                                                        yyerror("ERROR, El número está fuera del rango permitido para un longint positivo en la linea: " + lector.getNroLinea());
                                                    }
                                                    int token =   this.LONGINT;
                                                    lector.tablaSimbolos.addToken(val_peek(0).sval,token,"LONGINT");
                                                    agregarTokenPolaca(val_peek(0).sval);
                                                }
break;
case 122:
//#line 303 "gramatica.y"
{
                                                    yyval = val_peek(0); /*TODO: posible error*/
                                                    String lexema = '-'+ val_peek(0).sval;
                                                    int token =   this.LONGINT;
                                                    lector.tablaSimbolos.addToken(lexema,token,"LONGINT");
                                                    agregarTokenPolaca(lexema);
                                                }
break;
case 123:
//#line 311 "gramatica.y"
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
//#line 325 "gramatica.y"
{
                                                    yyval = val_peek(0);
                                                    String lexema = '-'+ val_peek(0).sval;
                                                    int token =   this.HEXA;
                                                    lector.tablaSimbolos.addToken(val_peek(0).sval,token,"HEXA");
                                                }
break;
case 125:
//#line 331 "gramatica.y"
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
													agregarTokenPolaca(val_peek(0).sval);
                                                }
break;
case 126:
//#line 360 "gramatica.y"
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
case 135:
//#line 409 "gramatica.y"
{yyerror("ERROR, falta de ',' en la lista en la linea: " + lector.getNroLinea());}
break;
//#line 1530 "Parser.java"
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
