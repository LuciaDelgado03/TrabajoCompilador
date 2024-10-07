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
    4,    4,    4,    4,    4,    4,    4,    4,    4,   15,
   15,   15,   15,   15,   15,   15,   15,   14,   14,   14,
   14,   14,   14,   14,   14,   10,   10,   16,   16,   17,
   19,   19,   13,   13,   13,   13,   13,   13,   13,   13,
   13,   13,   13,   13,   13,   13,   13,   13,   13,   13,
   13,   13,   13,   13,   13,   18,   18,   12,   11,   11,
   11,   11,   11,   11,   11,   11,   11,   11,   22,   22,
   22,   22,   22,   22,   22,   22,   22,   22,   22,   23,
   23,   23,   23,   23,   23,   23,   23,   23,    6,    6,
    6,    6,   24,   21,   21,    9,    9,    9,    5,    5,
    5,    5,   20,   20,   20,   20,   20,
};
final static short yylen[] = {                            2,
    4,    3,    2,    3,    2,    2,    2,    1,    1,    1,
    1,    3,    2,    1,    8,    7,    7,    6,    7,    7,
    7,   11,    9,   10,   10,    3,    2,    3,    2,    3,
    2,   14,    9,   13,   13,   13,   12,   13,   13,    8,
    1,    1,    1,    1,    2,    3,    2,    2,    2,    7,
    6,    6,    6,    6,    6,    5,    6,    5,    5,    4,
    4,    4,    3,    3,    5,    2,    1,    4,    3,    3,
    2,    1,    8,   10,    7,    7,    8,    7,    7,    4,
    9,    9,    9,    9,    9,    6,    7,    7,    9,    9,
    8,    8,   10,   10,   10,    3,    1,    4,    3,    3,
    4,    4,    2,    2,    2,    4,    3,    1,    3,    3,
    4,    4,    4,    4,    2,    2,    2,    2,    1,    1,
    1,    1,    1,    2,    1,    2,    1,    2,    3,    3,
    1,    1,    3,    3,    1,    3,    2,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    3,    0,    0,    0,    0,    0,
    0,  140,  141,  139,    0,    0,    8,    9,   10,    0,
    0,   14,   41,   42,   43,   44,    0,  131,    0,    0,
    0,    0,  123,  125,  127,    0,    0,    0,    0,    0,
    0,    0,  120,    0,    0,    0,  119,  122,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   48,    0,    0,   49,    7,    0,    0,    0,    0,    0,
   45,    0,    2,    0,    0,    0,  124,  126,  128,  118,
  117,    0,    0,  147,  146,  145,    0,    0,  143,  144,
    0,    0,    0,    0,    0,    0,   63,    0,    0,    0,
    0,   64,    0,  142,    0,  138,    0,    0,    0,    0,
    0,    0,   72,    0,    0,    0,    0,   46,  133,   69,
    0,    0,    0,    0,   12,    0,    0,    0,  130,    1,
   80,  107,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  109,    0,    0,  110,
    0,    0,   60,    0,    0,    0,    0,    0,    0,  137,
    0,    0,    0,    0,    0,   70,   71,    0,    0,    0,
    0,   68,    0,    0,    0,    0,   98,  106,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  111,  112,  114,
  113,   65,   58,   59,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  136,    0,    0,   66,    0,    0,    0,
    0,   56,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   86,    0,
    0,   27,    0,   31,    0,   29,    0,    0,    0,    0,
   18,    0,    0,    0,    0,    0,    0,   57,   53,   55,
    0,   54,   51,    0,    0,    0,    0,    0,   76,    0,
   87,    0,    0,    0,    0,   79,    0,    0,   78,    0,
   75,    0,    0,   88,   26,   30,   28,   21,   19,   16,
    0,   17,    0,    0,   20,    0,    0,   50,    0,    0,
    0,    0,    0,    0,    0,   77,    0,    0,    0,   92,
    0,    0,    0,   73,    0,   91,    0,   15,    0,    0,
    0,    0,    0,   40,    0,    0,    0,    0,    0,   89,
   84,    0,    0,   83,    0,    0,   85,   81,   90,    0,
   23,    0,    0,    0,   33,    0,    0,    0,    0,    0,
   95,   93,   94,   74,    0,   25,   24,    0,    0,    0,
    0,    0,    0,    0,   22,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   37,    0,    0,   34,
   38,    0,   39,   36,   35,   32,
};
final static short yydgoto[] = {                          3,
   16,   17,   18,   19,   20,   21,   22,  198,  107,  165,
   42,   23,   24,   25,   26,   43,   59,   44,  114,   91,
   45,   46,   47,   48,
};
final static short yysindex[] = {                      -106,
  486,  -70,    0,  469,    0,  595,  -56, -259, -240,    3,
   92,    0,    0,    0,   17,  905,    0,    0,    0, -123,
  -31,    0,    0,    0,    0,    0,   30,    0,  905,  676,
 -233,   92,    0,    0,    0,  -12,  576, -103,  435,  435,
    4,  -38,    0,   13,   -2,  129,    0,    0,   61,  616,
   27, -185,  430,  -87,  -87,   84,  534,   89,  -17,   87,
    0, -116,  623,    0,    0,  -21,  128,   21,  609,  -98,
    0,  718,    0,  -65,  630,  129,    0,    0,    0,    0,
    0,  -66,   23,    0,    0,    0,  418,  418,    0,    0,
  609, -233,  -47,  609,  463,  463,    0,  242,  252,  249,
  448,    0,  -39,    0,  -87,    0,   34,  273,  425,  499,
   92,   17,    0,  512,  609,  603,  609,    0,    0,    0,
  572,   70,  499,  499,    0,   32,   77,  268,    0,    0,
    0,    0,  633, -233, -233,   42,  576,  129,  576,  129,
   32,  -63, -233,   32,  435,  435,    0,  435,  435,    0,
  258,  259,    0,  265,  -92,   78,  458,   59,  -87,    0,
  -92,  295,    0,  101,  329,    0,    0,  336,  639,    2,
  338,    0,   90,  132,  353,  360,    0,    0,  141,  -43,
 -107,  142,  129,  129, -233,  355,  -42,    0,    0,    0,
    0,    0,    0,    0,   -3,  -19,   -5,  378,  -35,   38,
  381,  268,  390,    0,  384,   59,    0,  162,  377,  382,
   46,    0,  388,  392,  183,  417,  905,  207,  208,  412,
 -233,  423,  251, -233,  -33,  280,  445,  253,    0, -233,
  459,    0,  250,    0,  248,    0,  256,  465,  479,   55,
    0,  484,   59,    7,  487,  507,  905,    0,    0,    0,
  494,    0,    0,  905,  744,  765,  905,  905,    0,  296,
    0,  501,  316,  922,  529,    0,  455,  539,    0,  345,
    0,  557,  364,    0,    0,    0,    0,    0,    0,    0,
  570,    0,  586,  365,    0,   15,  785,    0,  805,  825,
  845,  591,  865,  885,  580,    0,  585,  385,  387,    0,
  594,  393,  -48,    0,  601,    0,  608,    0,   18,  610,
  391,  647,  648,    0,  656,  652,  609,  654,  655,    0,
    0,  640,  645,    0,  657,  673,    0,    0,    0,  -41,
    0,  680,  609,  609,    0,  674,  609,  668,  609,  609,
    0,    0,    0,    0,  687,    0,    0,  743,  809,  609,
  812,  689,  815,  818,    0,  694,  696,  889,  701,  502,
  705,  707,  506,  518,  722,  526,    0,  528,  531,    0,
    0,  535,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  134,    0,    0,    0,  262,  793,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  795,
    0,   57,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   82,    0,    0,  -27,   -7,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  125,
    0,    0,    0,    0,    0,    0,  145,  284,    0,    0,
    0,  803,    0,    0,    0,   68,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  165,  532,    0,    0,
    0,    0,    0,    0,  520,  527,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -29,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  107,    0,  103,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  540,    0,  552,
    9,    0,    0,   99,    0,    0,    0,    0,    0,    0,
    0,  167,    0,  191,    0,    0,    0,    0,    0,    0,
    0,    0,  -32,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  560,  565,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  770,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  213,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  235,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    6,   10,    0,  571,  854,  140,    0,  -22,  -45,  -16,
   -6,    0,    0,    0,    0,   65,  498,  -25,    0,    0,
  740,   -4,  681,    1,
};
final static int YYTABLESIZE=1204;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         51,
  155,   28,   28,   55,   87,  239,   88,   30,   67,  109,
  327,   56,   70,   97,  132,   83,   28,  346,  123,   57,
   28,   89,  117,   90,  235,   65,   57,   75,   58,   28,
   28,   97,   76,  108,   72,  108,  108,  108,  237,   65,
  233,   94,  213,  101,   82,   39,   37,  284,   38,   96,
   40,  108,  108,   93,  108,  311,  121,   28,  330,  157,
  212,   61,  126,  136,   70,   27,   27,   96,  133,   87,
  129,   88,   28,  158,   87,   64,   88,  159,  242,  125,
   27,   65,  138,  140,  141,  102,  251,  144,   71,  168,
  170,  171,  103,   27,   27,  281,  241,  121,  121,  121,
  121,  121,  129,  121,  250,  174,  175,  176,  103,  173,
  103,  103,  103,  280,   28,  121,  121,  199,  121,   97,
   94,   27,  135,  110,   47,  135,  103,  103,  115,  103,
  215,   63,  183,  200,  184,  177,   27,   62,  205,  134,
  135,   66,  134,  211,  132,  118,  129,   67,  223,    4,
  135,  224,   57,    1,  119,    6,  216,  134,   28,   68,
    9,  129,   10,  111,    2,  135,   62,  124,   77,   78,
   95,   79,  128,   62,  112,   96,  240,  132,   27,  195,
  196,   28,  197,  104,   12,   13,    4,   14,  132,   29,
   61,  134,    6,    7,    8,  185,  131,    9,  186,   10,
   11,   12,   13,  132,   14,  104,   28,  104,  104,  104,
  143,   15,   52,  326,   52,  221,  230,   28,  222,  231,
   53,   54,  256,  104,  104,  264,  104,   67,  265,  345,
   97,  104,   12,   13,   82,   14,  195,  196,  142,  197,
   84,   85,   86,   28,  129,   27,   69,   28,  132,  122,
  108,  116,  287,  234,   28,   28,   28,   28,   28,  289,
  291,   11,  293,  294,   28,   65,   96,   28,  232,  236,
   92,  108,  108,  108,   32,   33,   34,  128,   35,   36,
  135,   27,  151,   13,   60,  128,  129,   28,  128,   28,
   28,   28,  152,   28,   28,  315,   65,  203,   65,  181,
   65,   57,   65,   65,  104,   12,   13,  153,   14,  129,
  338,   27,  161,   62,  121,   28,  192,  193,   27,   27,
   27,   27,   27,  194,   65,  103,  348,  349,   27,  202,
  351,   27,  353,  354,  206,  121,  121,  121,  269,  135,
  163,   12,   13,  358,   14,  246,  103,  103,  103,  195,
  196,   27,  197,   27,   27,   27,  134,   27,   27,  129,
  163,   12,   13,  129,   14,  129,  129,  129,  129,  208,
  129,  207,  129,  129,  129,  129,  209,  129,  214,   27,
  129,   47,  283,   47,  129,   47,   47,   47,   47,   47,
   47,  217,   47,  218,   47,   47,   47,   47,  142,   47,
  219,  132,  220,  227,  142,  132,   47,  132,  132,  132,
  132,  132,  132,  229,  132,  132,  132,  132,  238,  132,
  243,  247,  104,   62,  245,   62,  132,   62,   62,   62,
   62,   62,   62,  244,   62,  248,   62,   62,   62,   62,
  249,   62,  254,  104,  104,  104,  252,   61,   62,   61,
  253,   61,   61,   61,   61,   61,   61,  255,   61,   39,
   61,   61,   61,   61,   40,   61,  257,  258,  159,   52,
  259,   52,   61,   52,   52,   52,   52,   52,   52,   38,
   52,  261,   52,   52,   52,   52,  162,   52,  154,  105,
   87,   82,   88,   82,   52,   82,   82,   82,   82,   82,
   82,  159,   82,  271,   82,   82,   82,   82,   41,   82,
   39,   37,  262,   38,  272,   40,   82,  274,   11,  201,
  276,  275,   11,  278,   11,   11,   11,   11,   74,   11,
  277,   11,   11,   11,   11,  266,   11,  279,  267,   57,
   13,  268,  282,   11,   13,  285,   13,   13,   13,   13,
  286,   13,  288,   13,   13,   13,   13,  295,   13,  296,
  116,  116,  116,  116,  116,   13,  116,  115,  115,  115,
  115,  115,  105,  115,  105,  105,  105,  297,  116,  116,
   99,  116,   99,   99,   99,  115,  115,  300,  115,  142,
  105,  105,  100,  105,  100,  100,  100,  304,   99,   99,
  101,   99,  101,  101,  101,  102,  305,  102,  102,  102,
  100,  100,  172,  100,   87,  306,   88,   39,  101,  101,
   38,  101,   40,  102,  102,  307,  102,  113,  308,  309,
  317,  179,  180,  182,   50,  310,   39,   37,  320,   38,
  187,   40,  169,  321,   39,   37,  322,   38,  323,   40,
   39,   37,  324,   38,  325,   40,  100,   39,   37,  328,
   38,  332,   40,  120,   39,   37,  329,   38,  331,   40,
  132,   39,   37,  178,   38,   87,   40,   88,  226,  210,
   39,   37,  228,   38,  167,   40,  333,  334,   32,   33,
   34,  337,   35,  339,  340,  104,   12,   13,  341,   14,
  104,   12,   13,  342,   14,   32,   33,   34,  352,   35,
   87,    4,   88,  350,   57,  343,  301,    6,  260,   80,
   81,  263,    9,  270,   10,  111,   31,  273,  104,   12,
   13,  344,   14,   32,   33,   34,  112,   35,  347,   32,
   33,   34,    4,   35,   36,  355,    5,  360,    6,    7,
    8,  225,  363,    9,  364,   10,   11,   12,   13,  366,
   14,  299,  367,  368,  303,  369,  370,   15,    4,  163,
   12,   13,  166,   14,    6,  147,  150,  116,  371,    9,
  372,   10,  111,  356,  115,   87,  373,   88,  374,  105,
    4,  375,    6,  112,    5,  376,    6,   99,  116,  116,
  116,    9,    4,   10,  111,  115,  115,  115,  127,  100,
  105,  105,  105,  132,    0,  112,    0,  101,   99,   99,
   99,    0,  102,    0,    0,  188,  189,    0,  190,  191,
  100,  100,  100,    0,  298,    0,    0,  302,  101,  101,
  101,    0,    0,  102,  102,  102,   32,   33,   34,  357,
   35,   87,  359,   88,   87,  361,   88,   87,  362,   88,
   87,    0,   88,    0,    0,   32,   33,   34,   49,   35,
   36,   98,    0,   32,   33,   34,    0,   35,   36,   32,
   33,   34,    0,   35,   36,    0,   32,   33,   34,   99,
   35,   36,    0,   32,   33,   34,    0,   35,   36,    0,
   32,   33,   34,    0,   35,   36,  106,  108,  106,   32,
   33,   34,    4,   35,   36,    0,  335,    0,    6,    7,
    8,  336,    0,    9,    0,   10,   11,   12,   13,  365,
   14,   87,    4,   88,    0,    0,   73,   15,    6,    7,
    8,    0,    0,    9,    0,   10,   11,   12,   13,    0,
   14,    0,    0,    0,    0,    0,  156,   15,  106,    0,
  160,    0,  160,  164,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    4,  164,  164,  164,  130,    0,
    6,    7,    8,    0,    0,    9,    0,   10,   11,   12,
   13,    0,   14,    0,    0,    0,    0,    0,    0,   15,
    4,    0,    0,  290,    0,    0,    6,    7,    8,    0,
  160,    9,  204,   10,   11,   12,   13,    0,   14,    0,
    0,    4,    0,    0,    0,   15,  164,    6,    7,    8,
  292,    0,    9,    0,   10,   11,   12,   13,    0,   14,
    0,    4,    0,    0,    0,    0,   15,    6,    7,    8,
  312,    0,    9,    0,   10,   11,   12,   13,    0,   14,
    0,    4,    0,    0,    0,    0,   15,    6,    7,    8,
  313,    0,    9,    0,   10,   11,   12,   13,    0,   14,
    0,    4,    0,    0,    0,  314,   15,    6,    7,    8,
    0,    0,    9,    0,   10,   11,   12,   13,    0,   14,
    0,    4,    0,    0,    0,    0,   15,    6,    7,    8,
  316,    0,    9,    0,   10,   11,   12,   13,    0,   14,
    0,    4,    0,    0,    0,    0,   15,    6,    7,    8,
  318,    0,    9,    0,   10,   11,   12,   13,    0,   14,
    0,    4,    0,    0,    0,    0,   15,    6,    7,    8,
  319,    0,    9,    0,   10,   11,   12,   13,    0,   14,
    0,    4,    0,    0,    0,    0,   15,    6,    7,    8,
    0,    0,    9,    0,   10,   11,   12,   13,    4,   14,
    0,   57,    0,    0,    6,    0,   15,    0,    0,    9,
    0,   10,  111,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  112,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          6,
   40,    1,    2,   60,   43,   41,   45,    2,   41,   55,
   59,  271,   44,   41,   44,   41,   16,   59,   40,  260,
   20,   60,   40,   62,   44,   16,  260,   40,  269,   29,
   30,   59,   37,   41,   29,   43,   44,   45,   44,   30,
   44,   44,   41,   50,   41,   42,   43,   41,   45,   41,
   47,   59,   60,   41,   62,   41,   63,   57,   41,  105,
   59,   59,   69,   41,   44,    1,    2,   59,   75,   43,
   70,   45,   72,   40,   43,   59,   45,   44,   41,   59,
   16,   72,   87,   88,   91,   59,   41,   94,   59,  115,
  116,  117,  278,   29,   30,   41,   59,   41,   42,   43,
   44,   45,    0,   47,   59,  122,  123,  124,   41,   40,
   43,   44,   45,   59,  114,   59,   60,   40,   62,   59,
   44,   57,   41,   40,    0,   44,   59,   60,   40,   62,
   41,   40,  137,  156,  139,   59,   72,   46,  161,   41,
   59,  265,   44,  169,    0,   59,   44,  271,  256,  257,
   44,  259,  260,  260,  271,  263,  173,   59,  158,   20,
  268,   59,  270,  271,  271,   59,    0,   40,  272,  273,
   42,  275,  271,   46,  282,   47,  199,   44,  114,  272,
  273,  181,  275,  271,  272,  273,  257,  275,   44,  260,
    0,  258,  263,  264,  265,  259,  262,  268,  262,  270,
  271,  272,  273,   59,  275,   41,  206,   43,   44,   45,
  258,  282,    0,  262,  271,  259,  259,  217,  262,  262,
  277,  278,  217,   59,   60,  259,   62,  260,  262,  271,
  258,  271,  272,  273,    0,  275,  272,  273,  271,  275,
  279,  280,  281,  243,  244,  181,  278,  247,  278,  271,
  258,  269,  247,  273,  254,  255,  256,  257,  258,  254,
  255,    0,  257,  258,  264,  256,  258,  267,  272,  275,
  258,  279,  280,  281,  271,  272,  273,  271,  275,  276,
  258,  217,   41,    0,  282,  271,  286,  287,  271,  289,
  290,  291,   41,  293,  294,  290,  287,  158,  289,  258,
  291,  260,  293,  294,  271,  272,  273,   59,  275,  309,
  317,  247,   40,   46,  258,  315,   59,   59,  254,  255,
  256,  257,  258,   59,  315,  258,  333,  334,  264,  271,
  337,  267,  339,  340,   40,  279,  280,  281,   59,  258,
  271,  272,  273,  350,  275,  206,  279,  280,  281,  272,
  273,  287,  275,  289,  290,  291,  258,  293,  294,  257,
  271,  272,  273,  261,  275,  263,  264,  265,  266,   41,
  268,  271,  270,  271,  272,  273,   41,  275,   41,  315,
  278,  257,  243,  259,  282,  261,  262,  263,  264,  265,
  266,  260,  268,   41,  270,  271,  272,  273,  265,  275,
   41,  257,  262,  262,  271,  261,  282,  263,  264,  265,
  266,  278,  268,   59,  270,  271,  272,  273,   41,  275,
   40,  260,  258,  257,   41,  259,  282,  261,  262,  263,
  264,  265,  266,   44,  268,   59,  270,  271,  272,  273,
   59,  275,  260,  279,  280,  281,   59,  257,  282,  259,
   59,  261,  262,  263,  264,  265,  266,   41,  268,   42,
  270,  271,  272,  273,   47,  275,  260,  260,   44,  257,
   59,  259,  282,  261,  262,  263,  264,  265,  266,   45,
  268,   59,  270,  271,  272,  273,   62,  275,   41,   60,
   43,  257,   45,  259,  282,  261,  262,  263,  264,  265,
  266,   44,  268,   59,  270,  271,  272,  273,   40,  275,
   42,   43,  262,   45,  262,   47,  282,   59,  257,   62,
  273,  272,  261,   59,  263,  264,  265,  266,   31,  268,
  275,  270,  271,  272,  273,  256,  275,   59,  259,  260,
  257,  262,   59,  282,  261,   59,  263,  264,  265,  266,
   44,  268,   59,  270,  271,  272,  273,  262,  275,   59,
   41,   42,   43,   44,   45,  282,   47,   41,   42,   43,
   44,   45,   41,   47,   43,   44,   45,  262,   59,   60,
   41,   62,   43,   44,   45,   59,   60,   59,   62,   92,
   59,   60,   41,   62,   43,   44,   45,   59,   59,   60,
   41,   62,   43,   44,   45,   41,  262,   43,   44,   45,
   59,   60,   41,   62,   43,   59,   45,   42,   59,   60,
   45,   62,   47,   59,   60,  262,   62,   57,   59,   44,
   40,  134,  135,  136,   40,  271,   42,   43,   59,   45,
  143,   47,   40,   59,   42,   43,  262,   45,  262,   47,
   42,   43,   59,   45,  262,   47,   41,   42,   43,   59,
   45,  271,   47,   41,   42,   43,   59,   45,   59,   47,
   41,   42,   43,   41,   45,   43,   47,   45,  181,   41,
   42,   43,  185,   45,  114,   47,   40,   40,  271,  272,
  273,   40,  275,   40,   40,  271,  272,  273,   59,  275,
  271,  272,  273,   59,  275,  271,  272,  273,   41,  275,
   43,  257,   45,   40,  260,   59,  262,  263,  221,   39,
   40,  224,  268,  226,  270,  271,  258,  230,  271,  272,
  273,   59,  275,  271,  272,  273,  282,  275,   59,  271,
  272,  273,  257,  275,  276,   59,  261,   59,  263,  264,
  265,  181,   59,  268,   59,  270,  271,  272,  273,   59,
  275,  264,  261,   59,  267,   59,  261,  282,  257,  271,
  272,  273,  261,  275,  263,   95,   96,  258,  261,  268,
   59,  270,  271,   41,  258,   43,  261,   45,  261,  258,
  257,  261,    0,  282,    0,  261,  263,  258,  279,  280,
  281,  268,    0,  270,  271,  279,  280,  281,   69,  258,
  279,  280,  281,   44,   -1,  282,   -1,  258,  279,  280,
  281,   -1,  258,   -1,   -1,  145,  146,   -1,  148,  149,
  279,  280,  281,   -1,  264,   -1,   -1,  267,  279,  280,
  281,   -1,   -1,  279,  280,  281,  271,  272,  273,   41,
  275,   43,   41,   45,   43,   41,   45,   43,   41,   45,
   43,   -1,   45,   -1,   -1,  271,  272,  273,  274,  275,
  276,  256,   -1,  271,  272,  273,   -1,  275,  276,  271,
  272,  273,   -1,  275,  276,   -1,  271,  272,  273,  274,
  275,  276,   -1,  271,  272,  273,   -1,  275,  276,   -1,
  271,  272,  273,   -1,  275,  276,   53,   54,   55,  271,
  272,  273,  257,  275,  276,   -1,  261,   -1,  263,  264,
  265,  266,   -1,  268,   -1,  270,  271,  272,  273,   41,
  275,   43,  257,   45,   -1,   -1,  261,  282,  263,  264,
  265,   -1,   -1,  268,   -1,  270,  271,  272,  273,   -1,
  275,   -1,   -1,   -1,   -1,   -1,  103,  282,  105,   -1,
  107,   -1,  109,  110,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  257,  122,  123,  124,  261,   -1,
  263,  264,  265,   -1,   -1,  268,   -1,  270,  271,  272,
  273,   -1,  275,   -1,   -1,   -1,   -1,   -1,   -1,  282,
  257,   -1,   -1,  260,   -1,   -1,  263,  264,  265,   -1,
  157,  268,  159,  270,  271,  272,  273,   -1,  275,   -1,
   -1,  257,   -1,   -1,   -1,  282,  173,  263,  264,  265,
  266,   -1,  268,   -1,  270,  271,  272,  273,   -1,  275,
   -1,  257,   -1,   -1,   -1,   -1,  282,  263,  264,  265,
  266,   -1,  268,   -1,  270,  271,  272,  273,   -1,  275,
   -1,  257,   -1,   -1,   -1,   -1,  282,  263,  264,  265,
  266,   -1,  268,   -1,  270,  271,  272,  273,   -1,  275,
   -1,  257,   -1,   -1,   -1,  261,  282,  263,  264,  265,
   -1,   -1,  268,   -1,  270,  271,  272,  273,   -1,  275,
   -1,  257,   -1,   -1,   -1,   -1,  282,  263,  264,  265,
  266,   -1,  268,   -1,  270,  271,  272,  273,   -1,  275,
   -1,  257,   -1,   -1,   -1,   -1,  282,  263,  264,  265,
  266,   -1,  268,   -1,  270,  271,  272,  273,   -1,  275,
   -1,  257,   -1,   -1,   -1,   -1,  282,  263,  264,  265,
  266,   -1,  268,   -1,  270,  271,  272,  273,   -1,  275,
   -1,  257,   -1,   -1,   -1,   -1,  282,  263,  264,  265,
   -1,   -1,  268,   -1,  270,  271,  272,  273,  257,  275,
   -1,  260,   -1,   -1,  263,   -1,  282,   -1,   -1,  268,
   -1,  270,  271,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  282,
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
"sentencia : ETIQUETA",
"sentencia_declaracion : tipo lista_variables ';'",
"sentencia_declaracion : tipo lista_variables",
"sentencia_declaracion : declaracion_funcion",
"sentencia_declaracion : TYPEDEF ID ASIGNACION tipo '(' subrango ')' ';'",
"sentencia_declaracion : TYPEDEF ID ASIGNACION tipo '(' subrango ';'",
"sentencia_declaracion : TYPEDEF ID ASIGNACION tipo subrango ')' ';'",
"sentencia_declaracion : TYPEDEF ID ASIGNACION tipo subrango ';'",
"sentencia_declaracion : TYPEDEF ID ASIGNACION tipo '(' ')' ';'",
"sentencia_declaracion : TYPEDEF ASIGNACION tipo '(' subrango ')' ';'",
"sentencia_declaracion : TYPEDEF ID ASIGNACION '(' subrango ')' ';'",
"sentencia_declaracion : TYPEDEF STRUCT '<' lista_tipos '>' '(' lista_variables ',' ')' ID ';'",
"sentencia_declaracion : TYPEDEF STRUCT lista_tipos '(' lista_variables ',' ')' ID ';'",
"sentencia_declaracion : TYPEDEF '<' lista_tipos '>' '(' lista_variables ',' ')' ID ';'",
"sentencia_declaracion : TYPEDEF STRUCT '<' lista_tipos '>' '(' lista_variables ',' ')' ';'",
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
"sentencia_ejecucion : invocacion_funcion ';'",
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
"tipo : HEXA",
"tipo : ID",
"comparador : '<'",
"comparador : '>'",
"comparador : MAYOR_IGUAL",
"comparador : MENOR_IGUAL",
"comparador : DISTINTO",
};

//#line 315 "gramatica.y"
void yyerror(String mensaje) {
  String ANSI_RESET = "\u001B[0m";
  String ANSI_RED = "\u001B[31m";
  // funcion utilizada para imprimir errores que produce yacc
  System.out.println(ANSI_RED + mensaje + ANSI_RESET);

}
//#line 720 "Parser.java"
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
case 12:
//#line 36 "gramatica.y"
{/*System.out.println($2);*/}
break;
case 13:
//#line 37 "gramatica.y"
{yyerror("ERROR, Falta ; en la sentencia de declaracion en la linea: " + lector.getNroLinea());}
break;
case 15:
//#line 39 "gramatica.y"
{System.out.println("Declaracion de Subtipo");}
break;
case 16:
//#line 40 "gramatica.y"
{yyerror("ERROR, Falta de parentesis en la linea: " + lector.getNroLinea());}
break;
case 17:
//#line 41 "gramatica.y"
{yyerror("ERROR, Falta de parentesis en la linea: " + lector.getNroLinea());}
break;
case 18:
//#line 42 "gramatica.y"
{yyerror("ERROR, Falta de llaves parentesis en la linea: " + lector.getNroLinea());}
break;
case 19:
//#line 43 "gramatica.y"
{yyerror("ERROR, Falta de rango en la linea: " + lector.getNroLinea());}
break;
case 20:
//#line 44 "gramatica.y"
{yyerror("ERROR, Falta nombre del tipo definido en la linea: " + lector.getNroLinea());}
break;
case 21:
//#line 45 "gramatica.y"
{yyerror("ERROR, Falta el tipo base en la linea: " + lector.getNroLinea());}
break;
case 22:
//#line 46 "gramatica.y"
{System.out.println("Declaracion de Struct");}
break;
case 23:
//#line 47 "gramatica.y"
{yyerror("ERROR, Falta <> en la linea: " + lector.getNroLinea());}
break;
case 24:
//#line 48 "gramatica.y"
{yyerror("ERROR, Falta la palabra STRUCT en la linea: " + lector.getNroLinea());}
break;
case 25:
//#line 49 "gramatica.y"
{yyerror("ERROR, Falta ID al final de la declaracion en la linea: " + lector.getNroLinea());}
break;
case 27:
//#line 53 "gramatica.y"
{yyerror("ERROR, Falta ',' entre los digitos del subrango en la linea: " + lector.getNroLinea());}
break;
case 29:
//#line 55 "gramatica.y"
{yyerror("ERROR, Falta ',' entre los digitos del subrango en la linea: " + lector.getNroLinea());}
break;
case 31:
//#line 57 "gramatica.y"
{yyerror("ERROR, Falta ',' entre los digitos del subrango en la linea: " + lector.getNroLinea());}
break;
case 33:
//#line 61 "gramatica.y"
{yyerror("ERROR, Falta sentencia return en la linea: " + lector.getNroLinea());}
break;
case 34:
//#line 62 "gramatica.y"
{yyerror("ERROR, Falta la declaracion del tipo de la FUN en la linea: " + lector.getNroLinea());}
break;
case 35:
//#line 63 "gramatica.y"
{yyerror("ERROR, Falta la declaracion de la palabra reservada FUN en la linea: " + lector.getNroLinea());}
break;
case 36:
//#line 64 "gramatica.y"
{yyerror("ERROR, Falta el ID de la funcion en la linea: " + lector.getNroLinea());}
break;
case 37:
//#line 65 "gramatica.y"
{yyerror("ERROR, Falta de () a la hora de los parametros en la linea: " + lector.getNroLinea());}
break;
case 38:
//#line 66 "gramatica.y"
{yyerror("ERROR, Falta de parametros en la FUN en la linea: " + lector.getNroLinea());}
break;
case 39:
//#line 67 "gramatica.y"
{yyerror("ERROR, Falta de BEGIN en la FUN en la linea: " + lector.getNroLinea());}
break;
case 40:
//#line 69 "gramatica.y"
{yyerror("ERROR, Falsa cuerpo de funcion en la linea: " + lector.getNroLinea());}
break;
case 46:
//#line 77 "gramatica.y"
{System.out.println("Declaracion de GOTO");}
break;
case 47:
//#line 78 "gramatica.y"
{yyerror("ERROR, Falta ';' al final de la declaracion en la linea: " + lector.getNroLinea());}
break;
case 48:
//#line 79 "gramatica.y"
{yyerror("ERROR, falta la ETIQUETA en la linea: " + lector.getNroLinea());}
break;
case 49:
//#line 80 "gramatica.y"
{yyerror("ERROR, falta el GOTO en la linea: " + lector.getNroLinea());}
break;
case 50:
//#line 83 "gramatica.y"
{System.out.println("Declaracion REPEAT-WHILE");}
break;
case 51:
//#line 85 "gramatica.y"
{yyerror("ERROR, falta palabra WHILE en la linea: " + lector.getNroLinea());}
break;
case 52:
//#line 86 "gramatica.y"
{yyerror("ERROR, falta palabra ';' al final de la declaracion en la linea: " + lector.getNroLinea());}
break;
case 53:
//#line 87 "gramatica.y"
{yyerror("ERROR, falta la condicion del WHILE en la linea: " + lector.getNroLinea());}
break;
case 54:
//#line 88 "gramatica.y"
{yyerror("ERROR, falta parentesis '(' en la declaracion de la condicion de WHILE en la linea: " + lector.getNroLinea());}
break;
case 55:
//#line 89 "gramatica.y"
{yyerror("ERROR, falta parentesis ')' en la declaracion de la condicion de WHILE en la linea: " + lector.getNroLinea());}
break;
case 56:
//#line 90 "gramatica.y"
{yyerror("ERROR, falta parentesis en la declaracion de la condicion de WHILE en la linea: " + lector.getNroLinea());}
break;
case 57:
//#line 91 "gramatica.y"
{yyerror("ERROR, falta el cuerpo de la iteracion repeat en la linea: " + lector.getNroLinea());}
break;
case 60:
//#line 96 "gramatica.y"
{yyerror("ERROR, Falta parámetro en sentencia OUTF en la linea: " + lector.getNroLinea());}
break;
case 61:
//#line 97 "gramatica.y"
{yyerror("ERROR, Falta ';' en la sentencia OUTF en la linea: " + lector.getNroLinea());}
break;
case 62:
//#line 98 "gramatica.y"
{yyerror("ERROR, Falta ';' en la sentencia OUTF en la linea: " + lector.getNroLinea());}
break;
case 63:
//#line 99 "gramatica.y"
{yyerror("ERROR, Faltan los parentesis en la sentencia OUTF en la linea: " + lector.getNroLinea());}
break;
case 64:
//#line 100 "gramatica.y"
{yyerror("ERROR, Faltan los parentesis en la sentencia OUTF en la linea: " + lector.getNroLinea());}
break;
case 65:
//#line 101 "gramatica.y"
{yyerror("ERROR, tipo invalido como parametro para la sentencia OUTF en la linea: " + lector.getNroLinea());}
break;
case 67:
//#line 105 "gramatica.y"
{yyerror("ERROR, falta declaracion de TIPO en la linea: " + lector.getNroLinea());}
break;
case 68:
//#line 109 "gramatica.y"
{if (val_peek(3).sval.equals(null)){ yyerror("No existe una funcion con ese nombre en la linea: " + lector.getNroLinea());}}
break;
case 69:
//#line 111 "gramatica.y"
{yyerror("ERROR, falta parametro en la invocacion de la funcion en la linea: " + lector.getNroLinea());}
break;
case 73:
//#line 121 "gramatica.y"
{System.out.println("Declaracion de IF");}
break;
case 74:
//#line 122 "gramatica.y"
{System.out.println("Declaracion de IF,ELSE");}
break;
case 75:
//#line 123 "gramatica.y"
{yyerror("ERROR, Falta THEN luego de la condicion en la linea: " + lector.getNroLinea());}
break;
case 76:
//#line 124 "gramatica.y"
{yyerror("ERROR,falta de Condicion en la linea: " + lector.getNroLinea());}
break;
case 77:
//#line 125 "gramatica.y"
{yyerror("ERROR,falta el bloque ejecutable en la linea: " + lector.getNroLinea());}
break;
case 78:
//#line 126 "gramatica.y"
{yyerror("ERROR,falta END_IF al final de la declaracion en la linea: " + lector.getNroLinea());}
break;
case 79:
//#line 127 "gramatica.y"
{yyerror("ERROR,falta END_IF; al final de la declaracion en la linea: " + lector.getNroLinea());}
break;
case 80:
//#line 128 "gramatica.y"
{yyerror("ERROR,falta ';' al final de la declaracion en la linea: " + lector.getNroLinea());}
break;
case 81:
//#line 129 "gramatica.y"
{yyerror("ERROR, falta ELSE luego de la sentencias de ejecucion en la linea: " + lector.getNroLinea());}
break;
case 82:
//#line 130 "gramatica.y"
{yyerror("ERROR,falta ';' al final de la declaracion en la linea: " + lector.getNroLinea());}
break;
case 83:
//#line 131 "gramatica.y"
{yyerror("ERROR, falta el bloque ejecutable en el ELSE en la linea: " + lector.getNroLinea());}
break;
case 84:
//#line 132 "gramatica.y"
{yyerror("ERROR, falta el bloque ejecutable en el IF en la linea: " + lector.getNroLinea());}
break;
case 85:
//#line 133 "gramatica.y"
{yyerror("ERROR,falta END_IF al final de la declaracion en la linea: " + lector.getNroLinea());}
break;
case 86:
//#line 134 "gramatica.y"
{yyerror("ERROR,falta de parentesis en la linea: " + lector.getNroLinea());}
break;
case 87:
//#line 135 "gramatica.y"
{yyerror("ERROR,falta un parentesis ')' en la linea: " + lector.getNroLinea());}
break;
case 88:
//#line 136 "gramatica.y"
{yyerror("ERROR,falta un parentesis '(' en la linea: " + lector.getNroLinea());}
break;
case 89:
//#line 137 "gramatica.y"
{yyerror("ERROR,falta un parentesis ')' "); }
break;
case 90:
//#line 138 "gramatica.y"
{yyerror("ERROR,falta un parentesis '(' "); }
break;
case 91:
//#line 139 "gramatica.y"
{yyerror("ERROR,falta un parentesis '()' "); }
break;
case 93:
//#line 141 "gramatica.y"
{System.out.println("Declaracion de IF,ELSE");}
break;
case 94:
//#line 142 "gramatica.y"
{System.out.println("Declaracion de IF,ELSE");}
break;
case 95:
//#line 143 "gramatica.y"
{System.out.println("Declaracion de IF,ELSE");}
break;
case 97:
//#line 148 "gramatica.y"
{yyerror("ERROR, falta comparador en comparacion en la linea: " + lector.getNroLinea());}
break;
case 98:
//#line 151 "gramatica.y"
{yyval = val_peek(1);}
break;
case 99:
//#line 153 "gramatica.y"
{yyval.ival = val_peek(2).ival + val_peek(0).ival;}
break;
case 100:
//#line 154 "gramatica.y"
{yyval.ival = val_peek(2).ival - val_peek(0).ival;}
break;
case 101:
//#line 155 "gramatica.y"
{yyerror("ERROR, hay 2 operadores en la linea: " + lector.getNroLinea());}
break;
case 102:
//#line 156 "gramatica.y"
{yyerror("ERROR, hay 2 operadores en la linea: " + lector.getNroLinea());}
break;
case 103:
//#line 158 "gramatica.y"
{yyerror("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
break;
case 104:
//#line 159 "gramatica.y"
{yyerror("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
break;
case 105:
//#line 160 "gramatica.y"
{yyerror("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
break;
case 106:
//#line 161 "gramatica.y"
{System.out.println("Declaracion de TOD");}
break;
case 107:
//#line 162 "gramatica.y"
{yyerror("ERROR, falta de expresion en la linea: " + lector.getNroLinea());}
break;
case 108:
//#line 163 "gramatica.y"
{yyval = val_peek(0);}
break;
case 111:
//#line 169 "gramatica.y"
{yyerror("ERROR, hay 2 operadores en la linea: " + lector.getNroLinea());}
break;
case 112:
//#line 170 "gramatica.y"
{yyerror("ERROR, hay 2 operadores en la linea: " + lector.getNroLinea());}
break;
case 113:
//#line 171 "gramatica.y"
{yyerror("ERROR, hay 2 operadores en la linea: " + lector.getNroLinea());}
break;
case 114:
//#line 172 "gramatica.y"
{yyerror("ERROR, hay 2 operadores en la linea: " + lector.getNroLinea());}
break;
case 115:
//#line 173 "gramatica.y"
{yyerror("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
break;
case 116:
//#line 174 "gramatica.y"
{yyerror("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
break;
case 117:
//#line 175 "gramatica.y"
{yyerror("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
break;
case 118:
//#line 176 "gramatica.y"
{yyerror("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
break;
case 119:
//#line 177 "gramatica.y"
{yyval = val_peek(0);}
break;
case 121:
//#line 183 "gramatica.y"
{yyval = val_peek(0);}
break;
case 123:
//#line 185 "gramatica.y"
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
case 124:
//#line 194 "gramatica.y"
{
                                                    yyval = val_peek(0); /*TODO: posible error*/
                                                    String lexema = '-'+ val_peek(0).sval;
                                                    int token =   this.LONGINT;
                                                    lector.tablaSimbolos.addToken(val_peek(0).sval,token,"LONGINT");
                                                }
break;
case 125:
//#line 201 "gramatica.y"
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
case 126:
//#line 215 "gramatica.y"
{
                                                    yyval = val_peek(0);
                                                    String lexema = '-'+ val_peek(0).sval;
                                                    int token =   this.HEXA;
                                                    lector.tablaSimbolos.addToken(val_peek(0).sval,token,"HEXA");
                                                }
break;
case 127:
//#line 221 "gramatica.y"
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
case 128:
//#line 250 "gramatica.y"
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
case 132:
//#line 284 "gramatica.y"
{/*System.out.println($1.sval);*/}
break;
case 137:
//#line 298 "gramatica.y"
{yyerror("ERROR, falta de ',' en la lista en la linea: " + lector.getNroLinea());}
break;
//#line 1368 "Parser.java"
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
