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
   14,   14,   14,   14,   14,   10,   10,   16,   16,   16,
   17,   19,   19,   13,   13,   13,   13,   13,   13,   13,
   13,   13,   13,   13,   13,   13,   13,   13,   13,   13,
   13,   13,   13,   13,   13,   13,   18,   18,   12,   11,
   11,   11,   11,   11,   11,   11,   11,   11,   11,   22,
   22,   22,   22,   22,   22,   22,   22,   22,   22,   22,
   23,   23,   23,   23,   23,   23,   23,   23,   23,    6,
    6,    6,    6,   24,   21,   21,    9,    9,    9,    5,
    5,    5,    5,   20,   20,   20,   20,   20,
};
final static short yylen[] = {                            2,
    4,    3,    2,    3,    2,    2,    2,    1,    1,    1,
    1,    3,    2,    1,    8,    7,    7,    6,    7,    7,
    7,   11,    9,   10,   10,    3,    2,    3,    2,    3,
    2,   14,    9,   13,   13,   13,   12,   13,   13,    8,
    1,    1,    1,    1,    2,    3,    2,    2,    2,    7,
    6,    6,    6,    6,    6,    5,    6,    5,    5,    4,
    4,    4,    3,    3,    5,    2,    1,    4,    3,    3,
    3,    2,    1,    8,   10,    7,    7,    8,    7,    7,
    4,    9,    9,    9,    9,    9,    6,    7,    7,    9,
    9,    8,    8,   10,   10,   10,    3,    1,    4,    3,
    3,    4,    4,    2,    2,    2,    4,    3,    1,    3,
    3,    4,    4,    4,    4,    2,    2,    2,    2,    1,
    1,    1,    1,    1,    2,    1,    2,    1,    2,    3,
    3,    1,    1,    3,    3,    1,    3,    2,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    3,    0,    0,    0,    0,    0,
    0,  141,  142,  140,    0,    0,    0,    8,    9,   10,
    0,    0,   14,   41,   42,   43,   44,    0,  132,    0,
    0,    0,    0,  124,  126,  128,    0,    0,    0,    0,
    0,    0,    0,  121,    0,    0,    0,  120,  123,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   48,    0,    0,   49,    0,    7,    0,    0,    0,
    0,    0,   45,    0,    2,    0,    0,    0,  125,  127,
  129,  119,  118,    0,    0,    0,  148,  147,  146,    0,
    0,  144,  145,    0,    0,    0,    0,    0,    0,   63,
    0,    0,    0,    0,   64,    0,  143,    0,  139,    0,
    0,    0,    0,    0,    0,   73,    0,    0,    0,    0,
   46,  134,   70,    0,   69,    0,    0,    0,   12,    0,
    0,    0,  131,    1,   81,  108,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  110,    0,    0,  111,    0,    0,   60,    0,    0,    0,
    0,    0,    0,  138,    0,    0,    0,    0,    0,   71,
   72,    0,    0,    0,    0,   68,    0,    0,    0,    0,
   99,  107,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  112,  113,  115,  114,   65,   58,   59,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  137,    0,    0,
   66,    0,    0,    0,    0,   56,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   87,    0,    0,   27,    0,   31,    0,   29,
    0,    0,    0,    0,   18,    0,    0,    0,    0,    0,
    0,   57,   53,   55,    0,   54,   51,    0,    0,    0,
    0,    0,   77,    0,   88,    0,    0,    0,    0,   80,
    0,    0,   79,    0,   76,    0,    0,   89,   26,   30,
   28,   21,   19,   16,    0,   17,    0,    0,   20,    0,
    0,   50,    0,    0,    0,    0,    0,    0,    0,   78,
    0,    0,    0,   93,    0,    0,    0,   74,    0,   92,
    0,   15,    0,    0,    0,    0,    0,   40,    0,    0,
    0,    0,    0,   90,   85,    0,    0,   84,    0,    0,
   86,   82,   91,    0,   23,    0,    0,    0,   33,    0,
    0,    0,    0,    0,   96,   94,   95,   75,    0,   25,
   24,    0,    0,    0,    0,    0,    0,    0,   22,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   37,    0,    0,   34,   38,    0,   39,   36,   35,   32,
};
final static short yydgoto[] = {                          3,
   17,   18,   19,   20,   21,   22,   23,  202,  110,  169,
   43,   24,   25,   26,   27,   44,   60,   45,  117,   94,
   46,   47,   48,   49,
};
final static short yysindex[] = {                      -174,
  347,  378,    0, 1010,    0,  991,  384, -237, -200,  -55,
   53,    0,    0,    0,  -17, 1040,  607,    0,    0,    0,
 -257,  -37,    0,    0,    0,    0,    0,  -13,    0,  607,
  402, -206,   53,    0,    0,    0,   45,  156,  -95,  180,
  180,   74,   38,    0,  -32,   34,   93,    0,    0,   35,
 1002,   61, -142,  305, -129, -129,   88,   71,  101,  -39,
   89,    0, -110, 1018,    0,  117,    0,  -16,   62,  -21,
 1040, -106,    0,  424,    0,  -92, 1026,   93,    0,    0,
    0,    0,    0,  -87,    8,   -1,    0,    0,    0,  826,
  826,    0,    0, 1040, -206,  -65, 1040,  205,  205,    0,
  154,  158,  141,  123,    0,  281,    0, -129,    0,  240,
  164,  839, -116,   53,  -17,    0,  639, 1040, 1049, 1040,
    0,    0,    0,  131,    0,  745, -116, -116,    0,   58,
   -9,  161,    0,    0,    0,    0,  149, -206, -206,  -71,
  156,   93,  156,   93,   58, -150, -206,   58,  180,  180,
    0,  180,  180,    0,  150,  160,    0,  207,  341,   82,
  846,  -58, -129,    0,  341,  241,    0,   39,  302,    0,
    0,  310, 1034,  -26,  320,    0,  230,  114,  335,  350,
    0,    0,   60, -125,  -28,  126,   93,   93, -206,  337,
  -78,    0,    0,    0,    0,    0,    0,    0,  -38,   33,
  -19,  357,  364,   -2,  363,  161,  362,    0,  368,  -58,
    0,  165,  361,  371,    2,    0,  376,  390,  206,  433,
  607,  222,  231,  434, -206,  439,  238, -206,  -41,  -54,
  448,  246,    0, -206,  450,    0,  248,    0,  249,    0,
  257,  481,  483,    3,    0,  490,  -58,   -4,  491,  515,
  607,    0,    0,    0,  492,    0,    0,  607,  444,  464,
  607,  607,    0,  307,    0,  526,  312,  -24,  530,    0,
  624,  535,    0,  324,    0,  537,  344,    0,    0,    0,
    0,    0,    0,    0,  562,    0,  579,  353,    0,   11,
  487,    0,  507,  527,  547,  585,  567,  587,  569,    0,
  571,  369,  370,    0,  574,  372,  -46,    0,  581,    0,
  586,    0,   22,  593,  355,  604,  614,    0,  327,  616,
 1040,  617,  618,    0,    0,  609,  610,    0,  612,  619,
    0,    0,    0,  -48,    0,  621, 1040, 1040,    0,  636,
 1040,  169, 1040, 1040,    0,    0,    0,    0,  623,    0,
    0,  340,  430, 1040,  488,  627,  517,  538,    0,  631,
  632,  560,  634,  422,  641,  643,  437,  442,  646,  449,
    0,  452,  457,    0,    0,  459,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   12,    0,    0,    0,  273,    0,  711,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  722,    0,  870,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   46,    0,    0,   51,  875,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  151,    0,    0,    0,    0,    0,    0,    0,  253,  300,
    0,    0,    0,  723,    0,    0,    0,  910,    0,    0,
    0,    0,    0,    0,   94,    0,    0,    0,    0,  915,
  935,    0,    0,    0,    0,    0,    0,  881,  905,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -27,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   30,
    0,  129,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  940,    0,  945,   72,    0,    0,   86,    0,    0,
    0,    0,    0,    0,    0,  175,    0,  107,    0,    0,
    0,    0,    0,    0,    0,    0,   14,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  965,  970,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  680,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  197,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  224,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   17,   10,    0,  961, 1263,   55,    0, -139,  -36,  -98,
   -6,    0,    0,    0,    0,   65,  901,    6,    0,    0,
  654,   42, 1084,    1,
};
final static int YYTABLESIZE=1440;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         52,
  120,   29,   29,   62,  273,  237,   72,   68,   96,   66,
  350,   16,  331,   69,  217,   16,  133,   29,   31,  112,
  204,   29,   72,  127,  241,  209,   67,  178,  179,  180,
   29,   29,  216,   57,   97,   85,  288,  129,  246,  140,
   67,   65,  255,  285,  104,   73,   74,   86,  125,  181,
   90,  315,   91,   58,   67,  133,  245,  124,   29,   58,
  254,  284,  334,  244,  130,   28,   28,   92,   59,   93,
  137,  161,  133,  136,   29,   70,  239,   97,  220,   78,
   90,   28,   91,   67,   77,    1,  136,  145,  136,  136,
  148,   98,   64,  100,   28,   28,    2,   92,   63,   93,
   90,  128,   91,   90,  136,   91,   61,   63,  189,   98,
   16,  190,   97,   16,   84,   40,   38,   29,   39,  105,
   41,  203,   28,  172,  174,  175,  135,  113,  130,  135,
   97,  142,  144,  225,   98,  106,  226,  136,   28,   99,
  118,  107,   12,   13,  135,   14,   61,  121,   69,   69,
   47,   69,  136,   69,  167,   12,   13,  125,   14,   90,
  122,   91,   29,  158,  132,   90,   85,   91,  130,  135,
  138,  176,  130,   90,   62,   91,   79,   80,  215,   81,
  234,   28,  187,  235,  188,   29,  185,  130,   58,  182,
   47,   90,  147,   91,  155,   16,   52,   40,  156,  157,
   39,  270,   41,  165,  271,   58,   63,  272,  196,  356,
   29,   90,  206,   91,   62,  330,  207,  268,  197,   16,
  269,   29,  349,   83,   39,   95,   61,  227,    4,  119,
  228,   58,    4,  236,    6,   58,   52,  260,    6,    9,
   71,   10,  114,    9,   16,   10,  114,   29,  133,   28,
  133,   29,  133,  115,  126,  240,  139,  115,   29,   29,
   29,   29,   29,   83,  250,  198,  132,  291,   29,   67,
  219,   29,   11,   67,  293,  295,  143,  297,  298,  162,
  210,  132,  143,  163,  143,   28,   87,   88,   89,  133,
  133,   29,  132,   29,   29,   29,  133,   29,   29,   13,
   67,  287,   67,  136,   67,  238,   67,   67,   98,  211,
  319,  133,   11,  133,  342,   28,   87,   88,   89,   29,
  159,  224,   28,   28,   28,   28,   28,    4,   67,   97,
  352,  353,   28,    6,  355,   28,  357,  358,    9,   13,
   10,  114,  212,  135,   33,   34,   35,  362,   36,   37,
  213,  136,  115,  199,  200,   28,  201,   28,   28,   28,
  218,   28,   28,   61,  108,   61,   16,   61,   61,   61,
   61,   61,   61,  221,   61,  222,   61,   61,   61,   61,
  360,   61,   90,   28,   91,  130,   16,  231,   61,  130,
  223,  130,  130,  130,  130,  233,  130,  242,  130,  130,
  130,  130,  247,  130,  243,  248,  130,   47,  249,   47,
  130,   47,   47,   47,   47,   47,   47,   16,   47,  252,
   47,   47,   47,   47,  251,   47,   33,   34,   35,  253,
   36,   62,   47,   62,  256,   62,   62,   62,   62,   62,
   62,   16,   62,   56,   62,   62,   62,   62,  257,   62,
   33,   34,   35,   52,   36,   52,   62,   52,   52,   52,
   52,   52,   52,   16,   52,  258,   52,   52,   52,   52,
  361,   52,   90,  259,   91,   33,   34,   35,   52,   36,
   83,  261,   83,   16,   83,   83,   83,   83,   83,   83,
  262,   83,  263,   83,   83,   83,   83,  265,   83,  266,
  167,   12,   13,   16,   14,   83,  275,  276,  278,  133,
  107,   12,   13,  133,   14,  133,  133,  133,  133,  279,
  133,  280,  133,  133,  133,  133,   16,  133,  363,   11,
   90,  281,   91,   11,  133,   11,   11,   11,   11,  282,
   11,  283,   11,   11,   11,   11,   16,   11,  286,  289,
  292,  107,   12,   13,   11,   14,   13,  365,  290,   90,
   13,   91,   13,   13,   13,   13,   16,   13,  299,   13,
   13,   13,   13,  301,   13,  107,   12,   13,  366,   14,
   90,   13,   91,    4,  300,  309,   16,  339,  304,    6,
    7,    8,  340,  308,    9,  310,   10,   11,   12,   13,
  369,   14,   90,    4,   91,  311,   16,    5,   15,    6,
    7,    8,  199,  200,    9,  201,   10,   11,   12,   13,
  312,   14,  313,  314,  321,  336,   16,  324,   15,  325,
  326,  327,  328,  329,    4,  199,  200,   30,  201,  332,
    6,    7,    8,  337,  333,    9,   16,   10,   11,   12,
   13,  335,   14,  338,   53,  341,  343,  344,    4,   15,
   54,   55,   75,   16,    6,    7,    8,  345,  346,    9,
  347,   10,   11,   12,   13,  354,   14,  348,   16,  351,
    4,  359,  371,   15,  134,  364,    6,    7,    8,  367,
  368,    9,  370,   10,   11,   12,   13,  374,   14,  372,
    4,  373,  375,  294,  376,   15,    6,    7,    8,  377,
    6,    9,  378,   10,   11,   12,   13,  379,   14,  380,
    4,    5,    4,  133,  131,   15,    6,    7,    8,  296,
    0,    9,    0,   10,   11,   12,   13,    0,   14,    0,
    0,    0,    0,    4,    0,   15,    0,    0,    0,    6,
    7,    8,  316,    0,    9,    0,   10,   11,   12,   13,
    0,   14,    0,    4,    0,    0,    0,    0,   15,    6,
    7,    8,  317,    0,    9,    0,   10,   11,   12,   13,
    0,   14,    0,    4,  177,    0,    0,  318,   15,    6,
    7,    8,    0,    0,    9,    0,   10,   11,   12,   13,
    0,   14,    0,    4,    0,    0,    0,    0,   15,    6,
    7,    8,  320,    0,    9,    0,   10,   11,   12,   13,
    0,   14,    0,    4,    0,    0,    0,    0,   15,    6,
    7,    8,  322,    0,    9,    0,   10,   11,   12,   13,
    0,   14,    0,    4,    0,    0,    0,    0,   15,    6,
    7,    8,  323,    0,    9,    0,   10,   11,   12,   13,
    0,   14,    0,    4,    0,   16,    0,   40,   15,    6,
    7,    8,   41,    0,    9,    0,   10,   11,   12,   13,
    4,   14,  163,   58,    0,  305,    6,    0,   15,  163,
    0,    9,    0,   10,  114,    4,    0,    0,    0,  170,
  166,    6,    0,    0,    0,  115,    9,  205,   10,  114,
  122,  122,  122,  122,  122,  109,  122,  109,  109,  109,
  115,  117,  117,  117,  117,  117,    0,  117,  122,  122,
    0,  122,   76,  109,  109,    0,  109,    0,    0,  117,
  117,    0,  117,    0,    0,  116,  116,  116,  116,  116,
  104,  116,  104,  104,  104,  105,    0,  105,  105,  105,
    0,    0,    0,  116,  116,    0,  116,    0,  104,  104,
    0,  104,    0,  105,  105,  106,  105,  106,  106,  106,
  100,    0,  100,  100,  100,  101,    0,  101,  101,  101,
    0,    0,    0,  106,  106,  146,  106,    0,  100,  100,
    0,  100,    0,  101,  101,  102,  101,  102,  102,  102,
  103,    0,  103,  103,  103,  167,   12,   13,  116,   14,
    0,    0,    0,  102,  102,    0,  102,    0,  103,  103,
   51,  103,   40,   38,    0,   39,    0,   41,  183,  184,
  186,   16,  103,   40,   38,    0,   39,  191,   41,   42,
    0,   40,   38,    0,   39,    0,   41,   16,  123,   40,
   38,    0,   39,    0,   41,   16,  136,   40,   38,    0,
   39,    0,   41,   16,  214,   40,   38,  171,   39,   16,
   41,   40,   38,    0,   39,  230,   41,    0,  173,  232,
   40,   38,    0,   39,    0,   41,   33,   34,   35,    0,
   36,    0,    0,    0,    0,    0,    0,    0,    0,  107,
   12,   13,    0,   14,    0,    0,  107,   12,   13,    0,
   14,    0,    0,   82,   83,  264,    0,  122,  267,    0,
  274,    0,  109,    0,  277,    0,    0,    0,  117,    0,
    0,    0,    0,    0,    0,  229,    0,    0,  122,  122,
  122,    0,    0,  109,  109,  109,    0,    0,    0,  117,
  117,  117,  116,    0,    0,    0,    0,  104,  303,    0,
    0,  307,  105,    0,    0,    0,    0,    0,    0,    0,
    0,  151,  154,  116,  116,  116,    0,    0,  104,  104,
  104,    0,  106,  105,  105,  105,    0,  100,    0,    0,
    0,    0,  101,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  106,  106,  106,    0,    0,  100,  100,
  100,    0,  102,  101,  101,  101,    0,  103,  302,    0,
    0,  306,  192,  193,    0,  194,  195,    0,    0,    0,
    0,    0,    0,  102,  102,  102,    0,    0,  103,  103,
  103,    0,    0,    0,    0,    0,    0,  101,    0,    0,
    0,   33,   34,   35,   50,   36,   37,   32,    0,    0,
    0,    0,   33,   34,   35,  102,   36,   37,    0,    0,
   33,   34,   35,    0,   36,   37,    0,    0,   33,   34,
   35,    0,   36,   37,    0,    0,   33,   34,   35,    0,
   36,   37,    0,    0,   33,   34,   35,    0,   36,   37,
   33,   34,   35,    0,   36,   37,  109,  111,  109,   33,
   34,   35,    0,   36,   37,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  160,    0,
  109,    0,  164,    0,  164,  168,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  168,  168,
  168,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  164,    0,  208,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  168,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          6,
   40,    1,    2,   59,   59,   44,   44,  265,   41,   16,
   59,   40,   59,  271,   41,   40,   44,   17,    2,   56,
  160,   21,   44,   40,   44,  165,   17,  126,  127,  128,
   30,   31,   59,  271,   44,   42,   41,   59,   41,   41,
   31,   59,   41,   41,   51,   59,   30,   42,   41,   59,
   43,   41,   45,  260,   41,   44,   59,   64,   58,  260,
   59,   59,   41,  203,   71,    1,    2,   60,  269,   62,
   77,  108,   72,   44,   74,   21,   44,   44,  177,   38,
   43,   17,   45,   74,   40,  260,   41,   94,   59,   44,
   97,   41,   40,   59,   30,   31,  271,   60,   46,   62,
   43,   40,   45,   43,   59,   45,    0,   46,  259,   59,
   40,  262,   41,   40,   41,   42,   43,  117,   45,   59,
   47,   40,   58,  118,  119,  120,   41,   40,    0,   44,
   59,   90,   91,  259,   42,  278,  262,   44,   74,   47,
   40,  271,  272,  273,   59,  275,   40,   59,   42,   43,
    0,   45,   59,   47,  271,  272,  273,   41,  275,   43,
  271,   45,  162,   41,  271,   43,  173,   45,   40,  262,
  258,   41,   44,   43,    0,   45,  272,  273,  173,  275,
  259,  117,  141,  262,  143,  185,  258,   59,  260,   41,
   40,   43,  258,   45,   41,   40,    0,   42,   41,   59,
   45,  256,   47,   40,  259,  260,   46,  262,   59,   41,
  210,   43,  271,   45,   40,  262,  162,  259,   59,   40,
  262,  221,  271,    0,   45,  258,  282,  256,  257,  269,
  259,  260,  257,  272,  263,  260,   40,  221,  263,  268,
  278,  270,  271,  268,   40,  270,  271,  247,  248,  185,
  278,  251,    0,  282,  271,  275,  258,  282,  258,  259,
  260,  261,  262,   40,  210,   59,  271,  251,  268,  260,
   41,  271,    0,  260,  258,  259,  265,  261,  262,   40,
   40,  271,  271,   44,  271,  221,  279,  280,  281,  278,
  290,  291,  271,  293,  294,  295,   44,  297,  298,    0,
  291,  247,  293,  258,  295,  273,  297,  298,  258,  271,
  294,   59,   40,  313,  321,  251,  279,  280,  281,  319,
   40,  262,  258,  259,  260,  261,  262,  257,  319,  258,
  337,  338,  268,  263,  341,  271,  343,  344,  268,   40,
  270,  271,   41,  258,  271,  272,  273,  354,  275,  276,
   41,  258,  282,  272,  273,  291,  275,  293,  294,  295,
   41,  297,  298,  257,   60,  259,   40,  261,  262,  263,
  264,  265,  266,  260,  268,   41,  270,  271,  272,  273,
   41,  275,   43,  319,   45,  257,   40,  262,  282,  261,
   41,  263,  264,  265,  266,   59,  268,   41,  270,  271,
  272,  273,   40,  275,   41,   44,  278,  257,   41,  259,
  282,  261,  262,  263,  264,  265,  266,   40,  268,   59,
  270,  271,  272,  273,  260,  275,  271,  272,  273,   59,
  275,  257,  282,  259,   59,  261,  262,  263,  264,  265,
  266,   40,  268,   60,  270,  271,  272,  273,   59,  275,
  271,  272,  273,  257,  275,  259,  282,  261,  262,  263,
  264,  265,  266,   40,  268,  260,  270,  271,  272,  273,
   41,  275,   43,   41,   45,  271,  272,  273,  282,  275,
  257,  260,  259,   40,  261,  262,  263,  264,  265,  266,
  260,  268,   59,  270,  271,  272,  273,   59,  275,  262,
  271,  272,  273,   40,  275,  282,   59,  262,   59,  257,
  271,  272,  273,  261,  275,  263,  264,  265,  266,  272,
  268,  273,  270,  271,  272,  273,   40,  275,   41,  257,
   43,  275,   45,  261,  282,  263,  264,  265,  266,   59,
  268,   59,  270,  271,  272,  273,   40,  275,   59,   59,
   59,  271,  272,  273,  282,  275,  257,   41,   44,   43,
  261,   45,  263,  264,  265,  266,   40,  268,  262,  270,
  271,  272,  273,  262,  275,  271,  272,  273,   41,  275,
   43,  282,   45,  257,   59,  262,   40,  261,   59,  263,
  264,  265,  266,   59,  268,   59,  270,  271,  272,  273,
   41,  275,   43,  257,   45,  262,   40,  261,  282,  263,
  264,  265,  272,  273,  268,  275,  270,  271,  272,  273,
   59,  275,   44,  271,   40,  271,   40,   59,  282,   59,
  262,  262,   59,  262,  257,  272,  273,  260,  275,   59,
  263,  264,  265,   40,   59,  268,   40,  270,  271,  272,
  273,   59,  275,   40,  271,   40,   40,   40,  257,  282,
  277,  278,  261,   40,  263,  264,  265,   59,   59,  268,
   59,  270,  271,  272,  273,   40,  275,   59,   40,   59,
  257,   59,  261,  282,  261,   59,  263,  264,  265,   59,
   59,  268,   59,  270,  271,  272,  273,  261,  275,   59,
  257,   59,  261,  260,   59,  282,  263,  264,  265,  261,
    0,  268,  261,  270,  271,  272,  273,  261,  275,  261,
  257,    0,    0,   44,   71,  282,  263,  264,  265,  266,
   -1,  268,   -1,  270,  271,  272,  273,   -1,  275,   -1,
   -1,   -1,   -1,  257,   -1,  282,   -1,   -1,   -1,  263,
  264,  265,  266,   -1,  268,   -1,  270,  271,  272,  273,
   -1,  275,   -1,  257,   -1,   -1,   -1,   -1,  282,  263,
  264,  265,  266,   -1,  268,   -1,  270,  271,  272,  273,
   -1,  275,   -1,  257,   40,   -1,   -1,  261,  282,  263,
  264,  265,   -1,   -1,  268,   -1,  270,  271,  272,  273,
   -1,  275,   -1,  257,   -1,   -1,   -1,   -1,  282,  263,
  264,  265,  266,   -1,  268,   -1,  270,  271,  272,  273,
   -1,  275,   -1,  257,   -1,   -1,   -1,   -1,  282,  263,
  264,  265,  266,   -1,  268,   -1,  270,  271,  272,  273,
   -1,  275,   -1,  257,   -1,   -1,   -1,   -1,  282,  263,
  264,  265,  266,   -1,  268,   -1,  270,  271,  272,  273,
   -1,  275,   -1,  257,   -1,   40,   -1,   42,  282,  263,
  264,  265,   47,   -1,  268,   -1,  270,  271,  272,  273,
  257,  275,   44,  260,   -1,  262,  263,   -1,  282,   44,
   -1,  268,   -1,  270,  271,  257,   -1,   -1,   -1,  261,
   62,  263,   -1,   -1,   -1,  282,  268,   62,  270,  271,
   41,   42,   43,   44,   45,   41,   47,   43,   44,   45,
  282,   41,   42,   43,   44,   45,   -1,   47,   59,   60,
   -1,   62,   32,   59,   60,   -1,   62,   -1,   -1,   59,
   60,   -1,   62,   -1,   -1,   41,   42,   43,   44,   45,
   41,   47,   43,   44,   45,   41,   -1,   43,   44,   45,
   -1,   -1,   -1,   59,   60,   -1,   62,   -1,   59,   60,
   -1,   62,   -1,   59,   60,   41,   62,   43,   44,   45,
   41,   -1,   43,   44,   45,   41,   -1,   43,   44,   45,
   -1,   -1,   -1,   59,   60,   95,   62,   -1,   59,   60,
   -1,   62,   -1,   59,   60,   41,   62,   43,   44,   45,
   41,   -1,   43,   44,   45,  271,  272,  273,   58,  275,
   -1,   -1,   -1,   59,   60,   -1,   62,   -1,   59,   60,
   40,   62,   42,   43,   -1,   45,   -1,   47,  138,  139,
  140,   40,   41,   42,   43,   -1,   45,  147,   47,   40,
   -1,   42,   43,   -1,   45,   -1,   47,   40,   41,   42,
   43,   -1,   45,   -1,   47,   40,   41,   42,   43,   -1,
   45,   -1,   47,   40,   41,   42,   43,  117,   45,   40,
   47,   42,   43,   -1,   45,  185,   47,   -1,   40,  189,
   42,   43,   -1,   45,   -1,   47,  271,  272,  273,   -1,
  275,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  271,
  272,  273,   -1,  275,   -1,   -1,  271,  272,  273,   -1,
  275,   -1,   -1,   40,   41,  225,   -1,  258,  228,   -1,
  230,   -1,  258,   -1,  234,   -1,   -1,   -1,  258,   -1,
   -1,   -1,   -1,   -1,   -1,  185,   -1,   -1,  279,  280,
  281,   -1,   -1,  279,  280,  281,   -1,   -1,   -1,  279,
  280,  281,  258,   -1,   -1,   -1,   -1,  258,  268,   -1,
   -1,  271,  258,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   98,   99,  279,  280,  281,   -1,   -1,  279,  280,
  281,   -1,  258,  279,  280,  281,   -1,  258,   -1,   -1,
   -1,   -1,  258,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  279,  280,  281,   -1,   -1,  279,  280,
  281,   -1,  258,  279,  280,  281,   -1,  258,  268,   -1,
   -1,  271,  149,  150,   -1,  152,  153,   -1,   -1,   -1,
   -1,   -1,   -1,  279,  280,  281,   -1,   -1,  279,  280,
  281,   -1,   -1,   -1,   -1,   -1,   -1,  256,   -1,   -1,
   -1,  271,  272,  273,  274,  275,  276,  258,   -1,   -1,
   -1,   -1,  271,  272,  273,  274,  275,  276,   -1,   -1,
  271,  272,  273,   -1,  275,  276,   -1,   -1,  271,  272,
  273,   -1,  275,  276,   -1,   -1,  271,  272,  273,   -1,
  275,  276,   -1,   -1,  271,  272,  273,   -1,  275,  276,
  271,  272,  273,   -1,  275,  276,   54,   55,   56,  271,
  272,  273,   -1,  275,  276,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  106,   -1,
  108,   -1,  110,   -1,  112,  113,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  126,  127,
  128,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  161,   -1,  163,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  177,
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
"invocacion_funcion : '(' expresion ')'",
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

//#line 318 "gramatica.y"
void yyerror(String mensaje) {
  // funcion utilizada para imprimir errores que produce yacc
  System.out.println("Error yacc: " + mensaje);

}
//#line 765 "Parser.java"
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
{/*System.out.println($2);*/}
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
case 46:
//#line 77 "gramatica.y"
{System.out.println("Declaracion de GOTO");}
break;
case 47:
//#line 78 "gramatica.y"
{System.out.println("ERROR, Falta ';' al final de la declaracion en la linea: " + lector.getNroLinea());}
break;
case 48:
//#line 79 "gramatica.y"
{System.out.println("ERROR,falta la ETIQUETA en la linea: " + lector.getNroLinea());}
break;
case 49:
//#line 80 "gramatica.y"
{System.out.println("ERROR,falta el GOTO en la linea: " + lector.getNroLinea());}
break;
case 50:
//#line 83 "gramatica.y"
{System.out.println("Declaracion REPEAT-WHILE");}
break;
case 51:
//#line 85 "gramatica.y"
{System.out.println("ERROR,falta palabra WHILE en la linea: " + lector.getNroLinea());}
break;
case 52:
//#line 86 "gramatica.y"
{System.out.println("ERROR,falta palabra ';' al final de la declaracion en la linea: " + lector.getNroLinea());}
break;
case 53:
//#line 87 "gramatica.y"
{System.out.println("ERROR,falta falta la condicion del WHILE en la linea: " + lector.getNroLinea());}
break;
case 54:
//#line 88 "gramatica.y"
{System.out.println("ERRROR, falta parentesis '(' en la declaracion de la condicion de  WHILE en la linea: " + lector.getNroLinea());}
break;
case 55:
//#line 89 "gramatica.y"
{System.out.println("ERRROR, falta parentesis ')' en la declaracion de la condicion de  WHILE en la linea: " + lector.getNroLinea());}
break;
case 56:
//#line 90 "gramatica.y"
{System.out.println("ERRROR, falta parentesis  en la declaracion de la condicion de  WHILE en la linea: " + lector.getNroLinea());}
break;
case 57:
//#line 91 "gramatica.y"
{System.out.println("ERRROR, falta el cuerpo de la iteracion repeat en la linea: " + lector.getNroLinea());}
break;
case 60:
//#line 96 "gramatica.y"
{System.out.println("ERROR, Falta parámetro en sentencia OUTF en la linea: " + lector.getNroLinea());}
break;
case 61:
//#line 97 "gramatica.y"
{System.out.println("ERROR, Falta ';' en la sentencia OUTF en la linea: " + lector.getNroLinea());}
break;
case 62:
//#line 98 "gramatica.y"
{System.out.println("ERROR, Falta ';' en la sentencia OUTF en la linea: " + lector.getNroLinea());}
break;
case 63:
//#line 99 "gramatica.y"
{System.out.println("ERROR, Faltan los parentesis en la sentencia OUTF en la linea: " + lector.getNroLinea());}
break;
case 64:
//#line 100 "gramatica.y"
{System.out.println("ERROR, Faltan los parentesis en la sentencia OUTF en la linea: " + lector.getNroLinea());}
break;
case 65:
//#line 101 "gramatica.y"
{System.out.println("ERROR, tipo invalido como parametro para la sentencia OUTF en la linea: " + lector.getNroLinea());}
break;
case 67:
//#line 105 "gramatica.y"
{System.out.println("ERROR, falta declaracion de TIPO en la linea: " + lector.getNroLinea());}
break;
case 68:
//#line 110 "gramatica.y"
{if (val_peek(3).sval.equals(null)){ System.out.println("No existe una funcion con ese nombre en la linea: " + lector.getNroLinea());}}
break;
case 69:
//#line 111 "gramatica.y"
{System.out.println("ERROR, falta ID en la invocacion en la linea: " + lector.getNroLinea());}
break;
case 70:
//#line 112 "gramatica.y"
{System.out.println("ERROR, falta parametro en la invocacion de la funcion en la linea: " + lector.getNroLinea());}
break;
case 74:
//#line 122 "gramatica.y"
{System.out.println("Declaracion de IF");}
break;
case 75:
//#line 123 "gramatica.y"
{System.out.println("Declaracion de IF,ELSE");}
break;
case 76:
//#line 124 "gramatica.y"
{System.out.println("ERROR, Falta THEN luego de la condicion en la linea: " + lector.getNroLinea());}
break;
case 77:
//#line 125 "gramatica.y"
{System.out.println("ERROR,falta de Condicion en la linea: " + lector.getNroLinea());}
break;
case 78:
//#line 126 "gramatica.y"
{System.out.println("ERROR,falta el bloque ejecutable en la linea: " + lector.getNroLinea());}
break;
case 79:
//#line 127 "gramatica.y"
{System.out.println("ERROR,falta END_IF al final de la declaracion en la linea: " + lector.getNroLinea());}
break;
case 80:
//#line 128 "gramatica.y"
{System.out.println("ERROR,falta END_IF; al final de la declaracion en la linea: " + lector.getNroLinea());}
break;
case 81:
//#line 129 "gramatica.y"
{System.out.println("ERROR,falta ';' al final de la declaracion en la linea: " + lector.getNroLinea());}
break;
case 82:
//#line 130 "gramatica.y"
{System.out.println("ERROR, falta ELSE luego de la sentencias de ejecucion en la linea: " + lector.getNroLinea());}
break;
case 83:
//#line 131 "gramatica.y"
{System.out.println("ERROR,falta ';' al final de la declaracion en la linea: " + lector.getNroLinea());}
break;
case 84:
//#line 132 "gramatica.y"
{System.out.println("ERROR, falta el bloque ejecutable en el ELSE en la linea: " + lector.getNroLinea());}
break;
case 85:
//#line 133 "gramatica.y"
{System.out.println("ERROR, falta el bloque ejecutable en el IF en la linea: " + lector.getNroLinea());}
break;
case 86:
//#line 134 "gramatica.y"
{System.out.println("ERROR,falta END_IF al final de la declaracion en la linea: " + lector.getNroLinea());}
break;
case 87:
//#line 135 "gramatica.y"
{System.out.println("ERROR,falta de parentesis en la linea: " + lector.getNroLinea());}
break;
case 88:
//#line 136 "gramatica.y"
{System.out.println("ERROR,falta un parentesis ')' en la linea: " + lector.getNroLinea());}
break;
case 89:
//#line 137 "gramatica.y"
{System.out.println("ERROR,falta un parentesis '(' en la linea: " + lector.getNroLinea());}
break;
case 90:
//#line 138 "gramatica.y"
{System.out.println("ERROR,falta un parentesis ')' ");}
break;
case 91:
//#line 139 "gramatica.y"
{System.out.println("ERROR,falta un parentesis '(' ");}
break;
case 92:
//#line 140 "gramatica.y"
{System.out.println("ERROR,falta un parentesis '()' ");}
break;
case 94:
//#line 142 "gramatica.y"
{System.out.println("Declaracion de IF,ELSE");}
break;
case 95:
//#line 143 "gramatica.y"
{System.out.println("Declaracion de IF,ELSE");}
break;
case 96:
//#line 144 "gramatica.y"
{System.out.println("Declaracion de IF,ELSE");}
break;
case 98:
//#line 149 "gramatica.y"
{System.out.println("ERROR, falta comparador en comparacion en la linea: " + lector.getNroLinea());}
break;
case 99:
//#line 152 "gramatica.y"
{yyval= val_peek(1);}
break;
case 100:
//#line 155 "gramatica.y"
{yyval.ival = val_peek(2).ival + val_peek(0).ival;}
break;
case 101:
//#line 156 "gramatica.y"
{yyval.ival = val_peek(2).ival - val_peek(0).ival;}
break;
case 102:
//#line 157 "gramatica.y"
{System.out.println("ERROR, hay 2 operadores en la linea: " + lector.getNroLinea());}
break;
case 103:
//#line 158 "gramatica.y"
{System.out.println("ERROR, hay 2 operadores en la linea: " + lector.getNroLinea());}
break;
case 104:
//#line 160 "gramatica.y"
{System.out.println("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
break;
case 105:
//#line 161 "gramatica.y"
{System.out.println("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
break;
case 106:
//#line 162 "gramatica.y"
{System.out.println("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
break;
case 107:
//#line 163 "gramatica.y"
{System.out.println("Declaracion de TOD");}
break;
case 108:
//#line 164 "gramatica.y"
{System.out.println("ERROR, falta de expresion en la linea: " + lector.getNroLinea());}
break;
case 109:
//#line 165 "gramatica.y"
{yyval = val_peek(0);}
break;
case 112:
//#line 171 "gramatica.y"
{System.out.println("ERROR, hay 2 operadores en la linea: " + lector.getNroLinea());}
break;
case 113:
//#line 172 "gramatica.y"
{System.out.println("ERROR, hay 2 operadores en la linea: " + lector.getNroLinea());}
break;
case 114:
//#line 173 "gramatica.y"
{System.out.println("ERROR, hay 2 operadores en la linea: " + lector.getNroLinea());}
break;
case 115:
//#line 174 "gramatica.y"
{System.out.println("ERROR, hay 2 operadores en la linea: " + lector.getNroLinea());}
break;
case 116:
//#line 175 "gramatica.y"
{System.out.println("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
break;
case 117:
//#line 176 "gramatica.y"
{System.out.println("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
break;
case 118:
//#line 177 "gramatica.y"
{System.out.println("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
break;
case 119:
//#line 178 "gramatica.y"
{System.out.println("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
break;
case 120:
//#line 179 "gramatica.y"
{yyval= val_peek(0);}
break;
case 122:
//#line 185 "gramatica.y"
{yyval = val_peek(0);}
break;
case 124:
//#line 187 "gramatica.y"
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
case 125:
//#line 196 "gramatica.y"
{
                                                    yyval = val_peek(0); /*TODO: posible error*/
                                                    String lexema = '-'+ val_peek(0).sval;
                                                    int token =   this.LONGINT;
                                                    lector.tablaSimbolos.addToken(val_peek(0).sval,token,"LONGINT");
                                                }
break;
case 126:
//#line 203 "gramatica.y"
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
case 127:
//#line 217 "gramatica.y"
{
                                                    yyval = val_peek(0);
                                                    String lexema = '-'+ val_peek(0).sval;
                                                    int token =   this.HEXA;
                                                    lector.tablaSimbolos.addToken(val_peek(0).sval,token,"HEXA");
                                                }
break;
case 128:
//#line 223 "gramatica.y"
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
case 129:
//#line 252 "gramatica.y"
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
case 133:
//#line 286 "gramatica.y"
{/*System.out.println($1.sval);*/}
break;
case 138:
//#line 301 "gramatica.y"
{System.out.println("ERROR, falta de ',' en la lista en la linea: " + lector.getNroLinea());}
break;
//#line 1417 "Parser.java"
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
