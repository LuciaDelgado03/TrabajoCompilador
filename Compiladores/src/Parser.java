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
    7,    4,    4,    4,    4,    4,    4,    4,    4,    4,
   15,   15,   15,   15,   15,   15,   15,   15,   14,   14,
   14,   14,   14,   14,   14,   14,   10,   10,   16,   16,
   17,   19,   19,   13,   13,   13,   13,   13,   13,   13,
   13,   13,   13,   13,   13,   13,   13,   13,   13,   13,
   13,   13,   13,   13,   13,   13,   18,   18,   12,   12,
   11,   11,   11,   11,   11,   11,   11,   11,   11,   11,
   22,   22,   22,   22,   22,   22,   22,   22,   22,   22,
   22,   23,   23,   23,   23,   23,   23,   23,   23,   23,
    6,    6,    6,    6,   24,   21,   21,    9,    9,    9,
    5,    5,    5,    5,   20,   20,   20,   20,   20,
};
final static short yylen[] = {                            2,
    4,    3,    2,    3,    2,    2,    2,    1,    1,    1,
    1,    3,    2,    1,    8,    7,    7,    6,    7,    7,
    7,   11,    9,   10,   10,    3,    2,    3,    2,    3,
    2,   14,    9,   13,   13,   13,   12,   12,   13,   13,
    8,    1,    1,    1,    1,    2,    3,    2,    2,    2,
    7,    6,    6,    6,    6,    6,    5,    6,    5,    5,
    4,    4,    4,    3,    3,    5,    2,    1,    4,    3,
    3,    2,    1,    8,   10,    7,    7,    8,    7,    7,
    5,    9,    9,    9,    9,    9,    6,    7,    7,    9,
    9,    8,    8,   10,   10,   10,    3,    1,    4,    4,
    3,    3,    4,    4,    2,    2,    2,    4,    3,    1,
    3,    3,    4,    4,    4,    4,    2,    2,    2,    2,
    1,    1,    1,    1,    1,    2,    1,    2,    1,    2,
    3,    3,    1,    1,    3,    3,    1,    3,    2,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    3,    0,    0,    0,    0,    0,
    0,  142,  143,  141,    0,    0,    8,    9,   10,    0,
    0,   14,   42,   43,   44,   45,    0,  133,    0,    0,
    0,    0,  125,  127,  129,    0,    0,    0,    0,    0,
    0,    0,  122,    0,    0,    0,  121,  124,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   49,    0,    0,   50,    7,    0,    0,    0,    0,    0,
   46,    0,    2,    0,    0,    0,  126,  128,  130,  120,
  119,    0,    0,  149,  148,  147,    0,    0,  145,  146,
    0,    0,    0,    0,    0,    0,   64,    0,    0,    0,
    0,   65,    0,  144,    0,  140,    0,    0,    0,    0,
    0,    0,   73,    0,    0,    0,    0,   47,  135,   70,
    0,    0,    0,    0,   12,    0,    0,    0,  132,    1,
    0,  109,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  111,    0,    0,  112,
    0,    0,   61,    0,    0,    0,    0,    0,    0,  139,
    0,    0,    0,    0,    0,   71,   72,    0,    0,    0,
    0,   69,    0,    0,    0,    0,  100,   99,   81,  108,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  113,
  114,  116,  115,   66,   59,   60,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  138,    0,    0,   67,    0,
    0,    0,    0,   57,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   87,    0,    0,   27,    0,   31,    0,   29,    0,    0,
    0,    0,   18,    0,    0,    0,    0,    0,    0,   58,
   54,   56,    0,   55,   52,    0,    0,    0,    0,    0,
   77,    0,   88,    0,    0,    0,    0,   80,    0,    0,
   79,    0,   76,    0,    0,   89,   26,   30,   28,   21,
   19,   16,    0,   17,    0,    0,   20,    0,    0,   51,
    0,    0,    0,    0,    0,    0,    0,   78,    0,    0,
    0,   93,    0,    0,    0,   74,    0,   92,    0,   15,
    0,    0,    0,    0,    0,   41,    0,    0,    0,    0,
    0,   90,   85,    0,    0,   84,    0,    0,   86,   82,
   91,    0,   23,    0,    0,    0,   33,    0,    0,    0,
    0,    0,   96,   94,   95,   75,    0,   25,   24,    0,
    0,    0,    0,    0,    0,    0,    0,   22,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   38,
    0,   37,    0,    0,   34,   39,    0,   40,   36,   35,
   32,
};
final static short yydgoto[] = {                          3,
   16,   17,   18,   19,   20,   21,   22,  200,  107,  165,
   42,   23,   24,   25,   26,   43,   59,   44,  114,   91,
   45,   46,   47,   48,
};
final static short yysindex[] = {                      -170,
  718,  744,    0,   44,    0,    5,  -48, -246, -223,  -50,
   71,    0,    0,    0,   33,  967,    0,    0,    0, -201,
  -34,    0,    0,    0,    0,    0,   61,    0,  967,  767,
 -231,   71,    0,    0,    0,  123,  631,  -68,  456,  456,
  649,  -38,    0,  -25,   16,   92,    0,    0,  132,  642,
   83, -236,  472, -117, -117,  136,  -76,  142,  -17,  141,
    0,  -63,  656,    0,    0,  -36,   87,   -3,  676,  -54,
    0,  787,    0,  -44,  663,   92,    0,    0,    0,    0,
    0,  -39,   14,    0,    0,    0,  461,  461,    0,    0,
  676, -231,  -32,  676,  217,  217,    0,  187,  210,  216,
  119,    0,  149,    0, -117,    0,   36,  243,  466,  341,
   71,   33,    0,  999,  676,   67,  676,    0,    0,    0,
  423,  171,  341,  341,    0,  100,   15,  241,    0,    0,
   70,    0,  522, -231, -231,  -59,  631,   92,  631,   92,
  100, -127, -231,  100,  456,  456,    0,  456,  456,    0,
  269,  286,    0,  291,  497,   13,  479,   29, -117,    0,
  497,  315,    0,   98,  330,    0,    0,  337,  670,  -21,
  350,    0,  377,  140,  352,  361,    0,    0,    0,    0,
  151,  -89,  -85,  173,   92,   92, -231,  374,   82,    0,
    0,    0,    0,    0,    0,    0,  -16,   31,  -26,  404,
  -35,   -8,  410,  241,  413,    0,  418,   29,    0,  207,
  411,  420,   -5,    0,  422,  427,  252,  473,  967,  253,
  261,  471, -231,  474,  272, -231,  117,  -46,  484,  288,
    0, -231,  489,    0,  294,    0,  295,    0,  299,  524,
  535,   38,    0,  536,   29,   -2,  539,  556,  967,    0,
    0,    0,  542,    0,    0,  967,  807,  827,  967,  967,
    0,  342,    0,  547,  347, 1008,  552,    0,  984,  562,
    0,  364,    0,  571,  369,    0,    0,    0,    0,    0,
    0,    0,  573,    0,  591,  366,    0,    2,  847,    0,
  867,  887,  907,  600,  927,  947,  583,    0,  586,  385,
  395,    0,  603,  405,  -40,    0,  607,    0,  609,    0,
   20,  612,  415,  641,  648,    0,  492,  653,  676,  655,
  660,    0,    0,  643,  650,    0,  657,  666,    0,    0,
    0,   39,    0,  667,  676,  676,    0,   76,  676,  530,
  676,  676,    0,    0,    0,    0,  671,    0,    0,  679,
  739,  676,   93,  740,  681,  762,  768,    0,  683,  687,
  769,  446,  689,  453,  700,  702,  515,  518,  712,    0,
  525,    0,  526,  527,    0,    0,  531,    0,    0,    0,
    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  133,    0,    0,    0,  254,  735,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  794,
    0,  510,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   24,    0,    0,  -27,  537,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  124,
    0,    0,    0,    0,    0,    0,  144,  274,    0,    0,
    0,  800,    0,    0,    0,  548,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  574,  579,    0,    0,
    0,    0,    0,    0,  517,  543,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -33,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   81,    0,  102,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  584,    0,  610,
   37,    0,    0,  106,    0,    0,    0,    0,    0,    0,
    0,  166,    0,  190,    0,    0,    0,    0,    0,    0,
    0,    0,   -7,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  615,  620,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  758,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  212,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  234,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
    6,   10,    0,  -42,  854,  195,    0,  -53,  -28,    7,
   -6,    0,    0,    0,    0,   65,  -30,   52,    0,    0,
  746,   12,  738,    1,
};
final static int YYTABLESIZE=1290;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         51,
   74,   28,   28,  123,   87,  241,   88,   30,   61,   70,
  134,   55,  271,   98,  113,   93,   28,  239,  329,  215,
   28,   89,  117,   90,   56,   65,  109,  235,   57,   28,
   28,   98,  244,   68,   72,  253,   57,  214,  286,   65,
   70,  103,  313,  101,   50,   58,   39,   37,   76,   38,
  243,   40,  201,  252,  136,  125,  121,   28,   94,   94,
  332,  142,  126,   66,  137,   27,   27,  137,  133,   67,
  129,  167,   28,  178,  237,  158,  157,   97,  283,  159,
   27,   65,  137,   41,  141,   39,   37,  144,   38,    1,
   40,   64,   83,   27,   27,   97,  282,  348,  138,  140,
    2,  131,  202,  181,  182,  184,  169,  207,   39,   37,
   63,   38,  189,   40,   28,  352,   62,   39,   37,   71,
   38,   27,   40,   48,  137,   87,  124,   88,  174,  175,
  176,  187,   62,   95,  188,   87,   27,   88,   96,  137,
  227,  102,   87,  134,   88,  131,  136,  242,  185,  136,
  186,  362,  228,  104,   12,   13,  230,   14,   28,  154,
  131,   87,   75,   88,  136,   63,  168,  170,  171,  223,
  225,    4,  224,  226,   57,  110,  134,    6,   27,  218,
    4,  115,    9,   28,   10,  111,    6,  134,  155,   62,
   97,    9,  262,   10,  111,  265,  112,  272,  183,  118,
   57,  275,  134,   77,   78,  112,   79,  119,   28,  268,
  173,   53,  269,   57,   68,  270,  128,  131,  134,   28,
  213,  328,   52,  300,  258,  143,  304,  151,   53,   54,
   98,   60,   92,   83,  122,  301,  197,  198,  305,  199,
   84,   85,   86,   69,  134,   28,  129,   27,  238,   28,
  152,  116,   68,   11,  289,  234,   28,   28,   28,   28,
   28,  291,  293,  144,  295,  296,   28,   65,  128,   28,
  177,  135,  128,   13,  153,   32,   33,   34,   49,   35,
   36,  137,  161,   27,  197,  198,   62,  199,  129,   28,
  128,   28,   28,   28,   97,   28,   28,  317,   65,  204,
   65,   31,   65,  236,   65,   65,  104,   12,   13,  347,
   14,  129,  340,   27,   32,   33,   34,   28,   35,   36,
   27,   27,   27,   27,   27,  179,   65,  194,  350,  351,
   27,  353,  354,   27,  356,  357,  137,   32,   33,   34,
  232,   35,   36,  233,  195,  361,   32,   33,   34,  196,
   35,   36,  205,   27,  208,   27,   27,   27,  131,   27,
   27,  136,  131,  136,  131,  131,  131,  131,  209,  131,
  210,  131,  131,  131,  131,  266,  131,  211,  267,  131,
   48,   27,   48,  131,   48,   48,   48,   48,   48,   48,
  216,   48,  220,   48,   48,   48,   48,  144,   48,  219,
  134,  221,  248,  144,  134,   48,  134,  134,  134,  134,
  134,  134,  222,  134,  134,  134,  134,  217,  134,  104,
   12,   13,   63,   14,   63,  134,   63,   63,   63,   63,
   63,   63,  231,   63,  229,   63,   63,   63,   63,  285,
   63,  163,   12,   13,  240,   14,   62,   63,   62,  245,
   62,   62,   62,   62,   62,   62,  246,   62,  247,   62,
   62,   62,   62,  172,   62,   87,  249,   88,   53,  250,
   53,   62,   53,   53,   53,   53,   53,   53,  251,   53,
  254,   53,   53,   53,   53,  255,   53,   32,   33,   34,
   83,   35,   83,   53,   83,   83,   83,   83,   83,   83,
   38,   83,   39,   83,   83,   83,   83,   40,   83,  159,
   11,  256,  259,  257,   11,   83,   11,   11,   11,   11,
  260,   11,  159,   11,   11,   11,   11,  162,   11,  261,
   13,  105,  263,  264,   13,   11,   13,   13,   13,   13,
  203,   13,  273,   13,   13,   13,   13,  276,   13,  274,
  123,  123,  123,  123,  123,   13,  123,  118,  118,  118,
  118,  118,  180,  118,   87,  277,   88,  278,  123,  123,
  355,  123,   87,  279,   88,  118,  118,  110,  118,  110,
  110,  110,  280,  117,  117,  117,  117,  117,  105,  117,
  105,  105,  105,  281,  284,  110,  110,  287,  110,  288,
  290,  117,  117,  297,  117,  298,  105,  105,  299,  105,
  302,  163,   12,   13,  106,   14,  106,  106,  106,  107,
  306,  107,  107,  107,  101,  307,  101,  101,  101,  308,
  309,  310,  106,  106,  311,  106,  312,  107,  107,  319,
  107,  322,  101,  101,  323,  101,  324,  163,   12,   13,
  102,   14,  102,  102,  102,  103,  325,  103,  103,  103,
  104,  326,  104,  104,  104,  330,  327,  331,  102,  102,
  333,  102,   39,  103,  103,   38,  103,   40,  104,  104,
  335,  104,  100,   39,   37,  334,   38,  336,   40,   82,
   39,   37,  339,   38,  341,   40,  120,   39,   37,  342,
   38,  343,   40,  132,   39,   37,  370,   38,  344,   40,
  212,   39,   37,  372,   38,  345,   40,   39,   37,  359,
   38,   87,   40,   88,  346,  349,   32,   33,   34,  358,
   35,   32,   33,   34,    6,   35,  104,   12,   13,  364,
   14,  367,  104,   12,   13,  368,   14,  371,    4,  104,
   12,   13,  337,   14,    6,    7,    8,  338,  373,    9,
  374,   10,   11,   12,   13,  123,   14,  123,  197,  198,
  377,  199,  118,   15,  118,  375,   80,   81,  376,  360,
  363,   87,   87,   88,   88,  378,  379,  380,  123,  123,
  123,  381,  110,    5,  110,  118,  118,  118,  117,    4,
  117,  134,  365,  105,   87,  105,   88,    0,  366,  369,
   87,   87,   88,   88,  127,  110,  110,  110,    0,    0,
    0,  117,  117,  117,    0,    0,  105,  105,  105,  106,
    0,  106,  147,  150,  107,    0,  107,    0,    0,  101,
    0,  101,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  106,  106,  106,    0,    0,  107,  107,  107,
    0,    0,  101,  101,  101,  102,    0,  102,    0,    0,
  103,    0,  103,    0,    0,  104,    0,  104,    0,    0,
    0,    0,  190,  191,    0,  192,  193,    0,  102,  102,
  102,    0,    0,  103,  103,  103,    0,   98,  104,  104,
  104,   32,   33,   34,    0,   35,  106,  108,  106,    0,
    0,    0,   32,   33,   34,   99,   35,   36,    0,   32,
   33,   34,    0,   35,   36,    0,   32,   33,   34,    0,
   35,   36,    0,   32,   33,   34,    0,   35,   36,    0,
   32,   33,   34,    0,   35,   36,   32,   33,   34,    0,
   35,   36,    0,    0,    0,    0,  156,    0,  106,    0,
  160,    0,  160,  164,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    4,  164,  164,  164,    5,    0,
    6,    7,    8,    0,    0,    9,    0,   10,   11,   12,
   13,    0,   14,    0,    0,    0,    0,    0,    0,   15,
    4,    0,    0,   29,    0,    0,    6,    7,    8,    0,
  160,    9,  206,   10,   11,   12,   13,    0,   14,    0,
    0,    0,    0,    4,    0,   15,  164,   73,    0,    6,
    7,    8,    0,    0,    9,    0,   10,   11,   12,   13,
    0,   14,    0,    4,    0,    0,    0,  130,   15,    6,
    7,    8,    0,    0,    9,    0,   10,   11,   12,   13,
    0,   14,    0,    4,    0,    0,  292,    0,   15,    6,
    7,    8,    0,    0,    9,    0,   10,   11,   12,   13,
    0,   14,    0,    4,    0,    0,    0,    0,   15,    6,
    7,    8,  294,    0,    9,    0,   10,   11,   12,   13,
    0,   14,    0,    4,    0,    0,    0,    0,   15,    6,
    7,    8,  314,    0,    9,    0,   10,   11,   12,   13,
    0,   14,    0,    4,    0,    0,    0,    0,   15,    6,
    7,    8,  315,    0,    9,    0,   10,   11,   12,   13,
    0,   14,    0,    4,    0,    0,    0,  316,   15,    6,
    7,    8,    0,    0,    9,    0,   10,   11,   12,   13,
    0,   14,    0,    4,    0,    0,    0,    0,   15,    6,
    7,    8,  318,    0,    9,    0,   10,   11,   12,   13,
    0,   14,    0,    4,    0,    0,    0,    0,   15,    6,
    7,    8,  320,    0,    9,    0,   10,   11,   12,   13,
    0,   14,    0,    4,    0,    0,    0,    0,   15,    6,
    7,    8,  321,    0,    9,    0,   10,   11,   12,   13,
    0,   14,    0,    4,    0,    0,    0,    0,   15,    6,
    7,    8,    0,    0,    9,    0,   10,   11,   12,   13,
    4,   14,    0,   57,    0,  303,    6,    0,   15,    0,
    0,    9,    0,   10,  111,    4,    0,    0,    0,  166,
    0,    6,    0,    0,    4,  112,    9,   57,   10,  111,
    6,    0,    0,    0,    0,    9,    0,   10,  111,    0,
  112,    0,    0,    0,    0,    0,    0,    0,    0,  112,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          6,
   31,    1,    2,   40,   43,   41,   45,    2,   59,   44,
   44,   60,   59,   41,   57,   41,   16,   44,   59,   41,
   20,   60,   40,   62,  271,   16,   55,   44,  260,   29,
   30,   59,   41,   41,   29,   41,  260,   59,   41,   30,
   44,  278,   41,   50,   40,  269,   42,   43,   37,   45,
   59,   47,   40,   59,   41,   59,   63,   57,   44,   44,
   41,   92,   69,  265,   41,    1,    2,   44,   75,  271,
   70,  114,   72,   59,   44,   40,  105,   41,   41,   44,
   16,   72,   59,   40,   91,   42,   43,   94,   45,  260,
   47,   59,   41,   29,   30,   59,   59,   59,   87,   88,
  271,    0,  156,  134,  135,  136,   40,  161,   42,   43,
   40,   45,  143,   47,  114,   40,   46,   42,   43,   59,
   45,   57,   47,    0,   44,   43,   40,   45,  122,  123,
  124,  259,   46,   42,  262,   43,   72,   45,   47,   59,
  183,   59,   43,    0,   45,   44,   41,  201,  137,   44,
  139,   59,  183,  271,  272,  273,  187,  275,  158,   41,
   59,   43,   40,   45,   59,    0,  115,  116,  117,  259,
  256,  257,  262,  259,  260,   40,   44,  263,  114,  173,
  257,   40,  268,  183,  270,  271,  263,   44,   40,    0,
   59,  268,  223,  270,  271,  226,  282,  228,  258,   59,
  260,  232,   59,  272,  273,  282,  275,  271,  208,  256,
   40,    0,  259,  260,   20,  262,  271,  262,  258,  219,
  169,  262,  271,  266,  219,  258,  269,   41,  277,  278,
  258,  282,  258,    0,  271,  266,  272,  273,  269,  275,
  279,  280,  281,  278,  278,  245,  246,  183,  275,  249,
   41,  269,  260,    0,  249,  272,  256,  257,  258,  259,
  260,  256,  257,  271,  259,  260,  266,  258,  271,  269,
  256,  258,  271,    0,   59,  271,  272,  273,  274,  275,
  276,  258,   40,  219,  272,  273,   46,  275,  288,  289,
  271,  291,  292,  293,  258,  295,  296,  292,  289,  271,
  291,  258,  293,  273,  295,  296,  271,  272,  273,  271,
  275,  311,  319,  249,  271,  272,  273,  317,  275,  276,
  256,  257,  258,  259,  260,  256,  317,   59,  335,  336,
  266,  338,  339,  269,  341,  342,  256,  271,  272,  273,
  259,  275,  276,  262,   59,  352,  271,  272,  273,   59,
  275,  276,  158,  289,   40,  291,  292,  293,  257,  295,
  296,  256,  261,  258,  263,  264,  265,  266,  271,  268,
   41,  270,  271,  272,  273,  259,  275,   41,  262,  278,
  257,  317,  259,  282,  261,  262,  263,  264,  265,  266,
   41,  268,   41,  270,  271,  272,  273,  265,  275,  260,
  257,   41,  208,  271,  261,  282,  263,  264,  265,  266,
  278,  268,  262,  270,  271,  272,  273,   41,  275,  271,
  272,  273,  257,  275,  259,  282,  261,  262,  263,  264,
  265,  266,   59,  268,  262,  270,  271,  272,  273,  245,
  275,  271,  272,  273,   41,  275,  257,  282,  259,   40,
  261,  262,  263,  264,  265,  266,   44,  268,   41,  270,
  271,  272,  273,   41,  275,   43,  260,   45,  257,   59,
  259,  282,  261,  262,  263,  264,  265,  266,   59,  268,
   59,  270,  271,  272,  273,   59,  275,  271,  272,  273,
  257,  275,  259,  282,  261,  262,  263,  264,  265,  266,
   45,  268,   42,  270,  271,  272,  273,   47,  275,   44,
  257,  260,  260,   41,  261,  282,  263,  264,  265,  266,
  260,  268,   44,  270,  271,  272,  273,   62,  275,   59,
  257,   60,   59,  262,  261,  282,  263,  264,  265,  266,
   62,  268,   59,  270,  271,  272,  273,   59,  275,  262,
   41,   42,   43,   44,   45,  282,   47,   41,   42,   43,
   44,   45,   41,   47,   43,  272,   45,  273,   59,   60,
   41,   62,   43,  275,   45,   59,   60,   41,   62,   43,
   44,   45,   59,   41,   42,   43,   44,   45,   41,   47,
   43,   44,   45,   59,   59,   59,   60,   59,   62,   44,
   59,   59,   60,  262,   62,   59,   59,   60,  262,   62,
   59,  271,  272,  273,   41,  275,   43,   44,   45,   41,
   59,   43,   44,   45,   41,  262,   43,   44,   45,   59,
  262,   59,   59,   60,   44,   62,  271,   59,   60,   40,
   62,   59,   59,   60,   59,   62,  262,  271,  272,  273,
   41,  275,   43,   44,   45,   41,  262,   43,   44,   45,
   41,   59,   43,   44,   45,   59,  262,   59,   59,   60,
   59,   62,   42,   59,   60,   45,   62,   47,   59,   60,
   40,   62,   41,   42,   43,  271,   45,   40,   47,   41,
   42,   43,   40,   45,   40,   47,   41,   42,   43,   40,
   45,   59,   47,   41,   42,   43,  261,   45,   59,   47,
   41,   42,   43,  261,   45,   59,   47,   42,   43,   41,
   45,   43,   47,   45,   59,   59,  271,  272,  273,   59,
  275,  271,  272,  273,    0,  275,  271,  272,  273,   59,
  275,   59,  271,  272,  273,   59,  275,   59,  257,  271,
  272,  273,  261,  275,  263,  264,  265,  266,   59,  268,
   59,  270,  271,  272,  273,  256,  275,  258,  272,  273,
   59,  275,  256,  282,  258,  261,   39,   40,  261,   41,
   41,   43,   43,   45,   45,  261,  261,  261,  279,  280,
  281,  261,  256,    0,  258,  279,  280,  281,  256,    0,
  258,   44,   41,  256,   43,  258,   45,   -1,   41,   41,
   43,   43,   45,   45,   69,  279,  280,  281,   -1,   -1,
   -1,  279,  280,  281,   -1,   -1,  279,  280,  281,  256,
   -1,  258,   95,   96,  256,   -1,  258,   -1,   -1,  256,
   -1,  258,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  279,  280,  281,   -1,   -1,  279,  280,  281,
   -1,   -1,  279,  280,  281,  256,   -1,  258,   -1,   -1,
  256,   -1,  258,   -1,   -1,  256,   -1,  258,   -1,   -1,
   -1,   -1,  145,  146,   -1,  148,  149,   -1,  279,  280,
  281,   -1,   -1,  279,  280,  281,   -1,  256,  279,  280,
  281,  271,  272,  273,   -1,  275,   53,   54,   55,   -1,
   -1,   -1,  271,  272,  273,  274,  275,  276,   -1,  271,
  272,  273,   -1,  275,  276,   -1,  271,  272,  273,   -1,
  275,  276,   -1,  271,  272,  273,   -1,  275,  276,   -1,
  271,  272,  273,   -1,  275,  276,  271,  272,  273,   -1,
  275,  276,   -1,   -1,   -1,   -1,  103,   -1,  105,   -1,
  107,   -1,  109,  110,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  257,  122,  123,  124,  261,   -1,
  263,  264,  265,   -1,   -1,  268,   -1,  270,  271,  272,
  273,   -1,  275,   -1,   -1,   -1,   -1,   -1,   -1,  282,
  257,   -1,   -1,  260,   -1,   -1,  263,  264,  265,   -1,
  157,  268,  159,  270,  271,  272,  273,   -1,  275,   -1,
   -1,   -1,   -1,  257,   -1,  282,  173,  261,   -1,  263,
  264,  265,   -1,   -1,  268,   -1,  270,  271,  272,  273,
   -1,  275,   -1,  257,   -1,   -1,   -1,  261,  282,  263,
  264,  265,   -1,   -1,  268,   -1,  270,  271,  272,  273,
   -1,  275,   -1,  257,   -1,   -1,  260,   -1,  282,  263,
  264,  265,   -1,   -1,  268,   -1,  270,  271,  272,  273,
   -1,  275,   -1,  257,   -1,   -1,   -1,   -1,  282,  263,
  264,  265,  266,   -1,  268,   -1,  270,  271,  272,  273,
   -1,  275,   -1,  257,   -1,   -1,   -1,   -1,  282,  263,
  264,  265,  266,   -1,  268,   -1,  270,  271,  272,  273,
   -1,  275,   -1,  257,   -1,   -1,   -1,   -1,  282,  263,
  264,  265,  266,   -1,  268,   -1,  270,  271,  272,  273,
   -1,  275,   -1,  257,   -1,   -1,   -1,  261,  282,  263,
  264,  265,   -1,   -1,  268,   -1,  270,  271,  272,  273,
   -1,  275,   -1,  257,   -1,   -1,   -1,   -1,  282,  263,
  264,  265,  266,   -1,  268,   -1,  270,  271,  272,  273,
   -1,  275,   -1,  257,   -1,   -1,   -1,   -1,  282,  263,
  264,  265,  266,   -1,  268,   -1,  270,  271,  272,  273,
   -1,  275,   -1,  257,   -1,   -1,   -1,   -1,  282,  263,
  264,  265,  266,   -1,  268,   -1,  270,  271,  272,  273,
   -1,  275,   -1,  257,   -1,   -1,   -1,   -1,  282,  263,
  264,  265,   -1,   -1,  268,   -1,  270,  271,  272,  273,
  257,  275,   -1,  260,   -1,  262,  263,   -1,  282,   -1,
   -1,  268,   -1,  270,  271,  257,   -1,   -1,   -1,  261,
   -1,  263,   -1,   -1,  257,  282,  268,  260,  270,  271,
  263,   -1,   -1,   -1,   -1,  268,   -1,  270,  271,   -1,
  282,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  282,
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
"declaracion_funcion : tipo FUN ID '(' parametro ')' BEGIN cuerpo RET expresion ';' END",
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
"condicion : lista_expresiones",
"asignacion : lista_variables ASIGNACION lista_expresiones ';'",
"asignacion : lista_variables ASIGNACION lista_expresiones error",
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

//#line 317 "gramatica.y"
void yyerror(String mensaje) {
  String ANSI_RESET = "\u001B[0m";
  String ANSI_RED = "\u001B[31m";
  // funcion utilizada para imprimir errores que produce yacc
  System.out.println(ANSI_RED + mensaje + ANSI_RESET);

}
//#line 741 "Parser.java"
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
case 32:
//#line 60 "gramatica.y"
{System.out.println("Declaracion de Funcion");}
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
{yyerror("ERROR, Falta de () a la hora de la expresion en la linea: " + lector.getNroLinea());}
break;
case 39:
//#line 67 "gramatica.y"
{yyerror("ERROR, Falta de parametros en la FUN en la linea: " + lector.getNroLinea());}
break;
case 40:
//#line 68 "gramatica.y"
{yyerror("ERROR, Falta de BEGIN en la FUN en la linea: " + lector.getNroLinea());}
break;
case 41:
//#line 70 "gramatica.y"
{yyerror("ERROR, Falsa cuerpo de funcion en la linea: " + lector.getNroLinea());}
break;
case 47:
//#line 78 "gramatica.y"
{System.out.println("Declaracion de GOTO");}
break;
case 48:
//#line 79 "gramatica.y"
{yyerror("ERROR, Falta ';' al final de la declaracion en la linea: " + lector.getNroLinea());}
break;
case 49:
//#line 80 "gramatica.y"
{yyerror("ERROR, falta la ETIQUETA en la linea: " + lector.getNroLinea());}
break;
case 50:
//#line 81 "gramatica.y"
{yyerror("ERROR, falta el GOTO en la linea: " + lector.getNroLinea());}
break;
case 51:
//#line 84 "gramatica.y"
{System.out.println("Declaracion REPEAT-WHILE");}
break;
case 52:
//#line 86 "gramatica.y"
{yyerror("ERROR, falta palabra WHILE en la linea: " + lector.getNroLinea());}
break;
case 53:
//#line 87 "gramatica.y"
{yyerror("ERROR, falta palabra ';' al final de la declaracion en la linea: " + lector.getNroLinea());}
break;
case 54:
//#line 88 "gramatica.y"
{yyerror("ERROR, falta la condicion del WHILE en la linea: " + lector.getNroLinea());}
break;
case 55:
//#line 89 "gramatica.y"
{yyerror("ERROR, falta parentesis '(' en la declaracion de la condicion de WHILE en la linea: " + lector.getNroLinea());}
break;
case 56:
//#line 90 "gramatica.y"
{yyerror("ERROR, falta parentesis ')' en la declaracion de la condicion de WHILE en la linea: " + lector.getNroLinea());}
break;
case 57:
//#line 91 "gramatica.y"
{yyerror("ERROR, falta parentesis en la declaracion de la condicion de WHILE en la linea: " + lector.getNroLinea());}
break;
case 58:
//#line 92 "gramatica.y"
{yyerror("ERROR, falta el cuerpo de la iteracion repeat en la linea: " + lector.getNroLinea());}
break;
case 61:
//#line 97 "gramatica.y"
{yyerror("ERROR, Falta parámetro en sentencia OUTF en la linea: " + lector.getNroLinea());}
break;
case 62:
//#line 98 "gramatica.y"
{yyerror("ERROR, Falta ';' en la sentencia OUTF en la linea: " + lector.getNroLinea());}
break;
case 63:
//#line 99 "gramatica.y"
{yyerror("ERROR, Falta ';' en la sentencia OUTF en la linea: " + lector.getNroLinea());}
break;
case 64:
//#line 100 "gramatica.y"
{yyerror("ERROR, Faltan los parentesis en la sentencia OUTF en la linea: " + lector.getNroLinea());}
break;
case 65:
//#line 101 "gramatica.y"
{yyerror("ERROR, Faltan los parentesis en la sentencia OUTF en la linea: " + lector.getNroLinea());}
break;
case 66:
//#line 102 "gramatica.y"
{yyerror("ERROR, tipo invalido como parametro para la sentencia OUTF en la linea: " + lector.getNroLinea());}
break;
case 68:
//#line 106 "gramatica.y"
{yyerror("ERROR, falta declaracion de TIPO en la linea: " + lector.getNroLinea());}
break;
case 69:
//#line 110 "gramatica.y"
{if (val_peek(3).sval.equals(null)){ yyerror("No existe una funcion con ese nombre en la linea: " + lector.getNroLinea());}}
break;
case 70:
//#line 112 "gramatica.y"
{yyerror("ERROR, falta parametro en la invocacion de la funcion en la linea: " + lector.getNroLinea());}
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
{yyerror("ERROR, Falta THEN luego de la condicion en la linea: " + lector.getNroLinea());}
break;
case 77:
//#line 125 "gramatica.y"
{yyerror("ERROR,falta de Condicion en la linea: " + lector.getNroLinea());}
break;
case 78:
//#line 126 "gramatica.y"
{yyerror("ERROR,falta el bloque ejecutable en la linea: " + lector.getNroLinea());}
break;
case 79:
//#line 127 "gramatica.y"
{yyerror("ERROR,falta END_IF al final de la declaracion en la linea: " + lector.getNroLinea());}
break;
case 80:
//#line 128 "gramatica.y"
{yyerror("ERROR,falta END_IF; al final de la declaracion en la linea: " + lector.getNroLinea());}
break;
case 81:
//#line 129 "gramatica.y"
{yyerror("ERROR,falta ';' al final de la declaracion en la linea: " + lector.getNroLinea());}
break;
case 82:
//#line 130 "gramatica.y"
{yyerror("ERROR, falta ELSE luego de la sentencias de ejecucion en la linea: " + lector.getNroLinea());}
break;
case 83:
//#line 131 "gramatica.y"
{yyerror("ERROR,falta ';' al final de la declaracion en la linea: " + lector.getNroLinea());}
break;
case 84:
//#line 132 "gramatica.y"
{yyerror("ERROR, falta el bloque ejecutable en el ELSE en la linea: " + lector.getNroLinea());}
break;
case 85:
//#line 133 "gramatica.y"
{yyerror("ERROR, falta el bloque ejecutable en el IF en la linea: " + lector.getNroLinea());}
break;
case 86:
//#line 134 "gramatica.y"
{yyerror("ERROR,falta END_IF al final de la declaracion en la linea: " + lector.getNroLinea());}
break;
case 87:
//#line 135 "gramatica.y"
{yyerror("ERROR,falta de parentesis en la linea: " + lector.getNroLinea());}
break;
case 88:
//#line 136 "gramatica.y"
{yyerror("ERROR,falta un parentesis ')' en la linea: " + lector.getNroLinea());}
break;
case 89:
//#line 137 "gramatica.y"
{yyerror("ERROR,falta un parentesis '(' en la linea: " + lector.getNroLinea());}
break;
case 90:
//#line 138 "gramatica.y"
{yyerror("ERROR,falta un parentesis ')' "); }
break;
case 91:
//#line 139 "gramatica.y"
{yyerror("ERROR,falta un parentesis '(' "); }
break;
case 92:
//#line 140 "gramatica.y"
{yyerror("ERROR,falta un parentesis '()' "); }
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
{yyerror("ERROR, falta comparador en comparacion en la linea: " + lector.getNroLinea());}
break;
case 99:
//#line 152 "gramatica.y"
{yyval = val_peek(1);}
break;
case 100:
//#line 153 "gramatica.y"
{yyerror("ERROR, falta de ';' en la asignacion de la linea: " + lector.getNroLinea());}
break;
case 101:
//#line 156 "gramatica.y"
{yyval.ival = val_peek(2).ival + val_peek(0).ival;}
break;
case 102:
//#line 157 "gramatica.y"
{yyval.ival = val_peek(2).ival - val_peek(0).ival;}
break;
case 103:
//#line 158 "gramatica.y"
{yyerror("ERROR, hay 2 operadores en la linea: " + lector.getNroLinea());}
break;
case 104:
//#line 159 "gramatica.y"
{yyerror("ERROR, hay 2 operadores en la linea: " + lector.getNroLinea());}
break;
case 105:
//#line 161 "gramatica.y"
{yyerror("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
break;
case 106:
//#line 162 "gramatica.y"
{yyerror("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
break;
case 107:
//#line 163 "gramatica.y"
{yyerror("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
break;
case 108:
//#line 164 "gramatica.y"
{System.out.println("Declaracion de TOD");}
break;
case 109:
//#line 165 "gramatica.y"
{yyerror("ERROR, falta de expresion en la linea: " + lector.getNroLinea());}
break;
case 110:
//#line 166 "gramatica.y"
{yyval = val_peek(0);}
break;
case 113:
//#line 172 "gramatica.y"
{yyerror("ERROR, hay 2 operadores en la linea: " + lector.getNroLinea());}
break;
case 114:
//#line 173 "gramatica.y"
{yyerror("ERROR, hay 2 operadores en la linea: " + lector.getNroLinea());}
break;
case 115:
//#line 174 "gramatica.y"
{yyerror("ERROR, hay 2 operadores en la linea: " + lector.getNroLinea());}
break;
case 116:
//#line 175 "gramatica.y"
{yyerror("ERROR, hay 2 operadores en la linea: " + lector.getNroLinea());}
break;
case 117:
//#line 176 "gramatica.y"
{yyerror("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
break;
case 118:
//#line 177 "gramatica.y"
{yyerror("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
break;
case 119:
//#line 178 "gramatica.y"
{yyerror("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
break;
case 120:
//#line 179 "gramatica.y"
{yyerror("ERROR, falta de operando en la linea: " + lector.getNroLinea());}
break;
case 121:
//#line 180 "gramatica.y"
{yyval = val_peek(0);}
break;
case 123:
//#line 186 "gramatica.y"
{yyval = val_peek(0);}
break;
case 125:
//#line 188 "gramatica.y"
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
case 126:
//#line 197 "gramatica.y"
{
                                                    yyval = val_peek(0); /*TODO: posible error*/
                                                    String lexema = '-'+ val_peek(0).sval;
                                                    int token =   this.LONGINT;
                                                    lector.tablaSimbolos.addToken(val_peek(0).sval,token,"LONGINT");
                                                }
break;
case 127:
//#line 204 "gramatica.y"
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
case 128:
//#line 218 "gramatica.y"
{
                                                    yyval = val_peek(0);
                                                    String lexema = '-'+ val_peek(0).sval;
                                                    int token =   this.HEXA;
                                                    lector.tablaSimbolos.addToken(val_peek(0).sval,token,"HEXA");
                                                }
break;
case 129:
//#line 224 "gramatica.y"
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
case 130:
//#line 253 "gramatica.y"
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
case 134:
//#line 286 "gramatica.y"
{/*System.out.println($1.sval);*/}
break;
case 139:
//#line 300 "gramatica.y"
{yyerror("ERROR, falta de ',' en la lista en la linea: " + lector.getNroLinea());}
break;
//#line 1400 "Parser.java"
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
