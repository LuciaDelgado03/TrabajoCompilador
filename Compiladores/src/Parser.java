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
//#line 22 "Parser.java"




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
   19,   19,   19,   19,   19,
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
    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    3,    0,    0,    0,    0,    0,
    0,  128,  129,    0,    0,    8,    9,   10,    0,    0,
   14,   40,   41,   42,  120,    0,    0,    0,    0,    0,
  112,  114,    0,  116,    0,    0,    0,    0,    0,    0,
    0,  110,    0,  109,    0,    0,    0,    0,    0,    0,
    0,    0,   49,    0,   50,    7,    0,    0,    0,    0,
    0,    0,    2,    0,    0,    0,    0,    0,    0,    0,
   56,    0,    0,  113,  115,  117,  108,  107,    0,    0,
    0,    0,    0,   57,    0,    0,    0,  130,    0,  127,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   47,  122,    0,    0,    0,   12,    0,    0,    0,  119,
    1,   77,   79,   78,   86,    0,    0,    0,  131,  132,
  133,  134,  135,    0,    0,   97,    0,    0,   53,    0,
    0,    0,    0,    0,    0,    0,   99,    0,    0,  100,
    0,    0,    0,    0,    0,  126,    0,    0,    0,    0,
   72,    0,    0,    0,    0,    0,    0,    0,   88,    0,
    0,    0,    0,    0,    0,   70,   96,   51,   52,    0,
    0,  101,  102,  104,  103,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  125,    0,    0,   68,    0,   71,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   87,   27,    0,   31,    0,   29,
    0,    0,    0,    0,    0,   18,    0,    0,    0,    0,
    0,   46,    0,   44,    0,    0,    0,    0,    0,    0,
    0,    0,   85,   76,    0,    0,    0,    0,   75,   26,
   30,   28,   21,   19,   16,    0,    0,    0,    0,   20,
    0,    0,   43,    0,    0,    0,    0,    0,    0,    0,
   36,    0,    0,    0,    0,    0,   73,    0,   15,   17,
    0,   23,    0,   33,   37,   39,    0,   38,    0,   62,
    0,    0,    0,    0,   35,   34,   83,   82,    0,   84,
   80,    0,   25,   24,   32,   63,    0,   64,   61,    0,
   65,    0,   74,   22,   58,   66,    0,   67,   59,
};
final static short yydgoto[] = {                          3,
  229,   16,   17,   18,   19,   20,   21,  179,   91,  150,
  230,   22,   23,   24,   51,   68,   69,   42,  124,  108,
   43,   44,   25,
};
final static short yysindex[] = {                      -128,
  557,  573,    0,   -7,    0,  457,    3, -227, -173,  -40,
   44,    0,    0,   51,  641,    0,    0,    0, -218,  -41,
    0,    0,    0,    0,    0,  641,  589,  -90,  493,   71,
    0,    0,   83,    0,   80,  464,   22,  332,  332,  526,
   23,    0,  116,    0, -148,  255,  190,  190,  108,  433,
   -9,   93,    0, -106,    0,    0,  -24,  109,   12,  434,
 -105,  605,    0,  114,    8,  533,  -52,  170,  172,  434,
    0,  540,  116,    0,    0,    0,    0,    0,  175,  171,
  119,  348,  348,    0,  337,  337,  -84,    0,  190,    0,
  373,  110,  386,  190,   44,   51,  -23,  367,  195,  507,
    0,    0,  -31,  190,  190,    0,  137,   47,   44,    0,
    0,    0,    0,    0,    0,  172, -173,  -38,    0,    0,
    0,    0,    0,  434,  156,    0,  396,  209,    0,  218,
  464,  116,  464,  116,  332,  332,    0,  332,  332,    0,
   62,  -89,  391,   17,  190,    0,   62,  185,   39,  276,
    0,   67,  501,  289,  327,   88,  309,  329,    0,  434,
  434,  113, -190,  132,  467,    0,    0,    0,    0,  116,
  116,    0,    0,    0,    0,  -18,  -20,  -16,  263,  -83,
   62,  341,  287,   -1,    0,  317,   17,    0,  154,    0,
  388,  411,  405,  210,  431,  250,  222,  230,  137,  548,
  424, -173,   95,  442,    0,    0,  231,    0,  244,    0,
  248,  460,  465,  -39,  406,    0,   17,  266,  480,    2,
  250,    0,  486,    0,  250,  203,  513,  434,  625,  290,
  250,  250,    0,    0,  295,  -35,  504,  303,    0,    0,
    0,    0,    0,    0,    0,  511,  518,    4,  525,    0,
  301,  325,    0,  333,  223,  336,  434,   -5,  570,  519,
    0,  339,  353,  558,  559,   13,    0,  563,    0,    0,
  -10,    0,  572,    0,    0,    0,  355,    0,  126,    0,
  574,  575,  434,  356,    0,    0,    0,    0,  577,    0,
    0,  580,    0,    0,    0,    0,  581,    0,    0,  547,
    0,  587,    0,    0,    0,    0,  591,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -26,    0,    0,   41,  629,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  632,    0,    0,  -37,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   14,    0,    0,    0,    0,    0,    0,    0,
    0,   61,    0,    0,    0,    0,    0,   21,   81,    0,
    0,  647,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   34,    0,    0,    0,    0,    0,    0,    0,
    0,   54,   74,    0,  -30,  134,    0,    0,    0,    0,
    0,    0,    0,    0,  -42,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   48,    0,    1,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  101,    0,  121,
    0,   94,    0,  159,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -14,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  164,
  169,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   87,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  141,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  161,    0,
    0,    0,    0,    0,    0,    0,  392,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  102,   85,    0,  -13,  595,  -19,    0,  -58,   16,  -53,
  -81,    0,    0,    0,  551,  -65,  368,    0,  536,    0,
   26,  588,  593,
};
final static int YYTABLESIZE=923;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         59,
  118,  121,   61,  111,  111,  111,  111,  111,  155,  111,
  106,  106,  106,  106,  106,  104,  106,  121,   53,  245,
  121,  111,  111,  209,  111,  207,   69,  211,  106,  106,
  100,  106,   29,  180,  154,  281,   97,   82,  141,   83,
   11,  213,   61,   49,  118,   61,   57,   61,  293,  156,
  157,  158,   58,  280,   98,   61,   98,   98,   98,  118,
   48,   73,   48,   93,  121,   82,  114,   83,  202,   50,
  106,  290,   98,   98,   93,   98,   93,   93,   93,  121,
   13,   84,  121,  182,  152,  246,   50,  192,  186,   54,
  160,  124,   93,   93,   94,   93,   94,   94,   94,   56,
   55,  195,   15,   27,  143,  159,  124,  132,  134,   55,
   70,   56,   94,   94,   95,   94,   95,   95,   95,   72,
   54,  214,  215,  218,  184,  118,  251,   62,  271,   87,
  123,    1,   95,   95,   89,   95,   89,   89,   89,  252,
   45,   71,    2,  254,  256,  123,   56,   94,  105,  262,
  263,  101,   89,   89,   54,   89,  170,   85,  171,  130,
   81,   82,   86,   83,  102,  109,  297,  220,   82,   50,
   83,   64,  112,  277,  105,  105,  105,  105,  105,   82,
  105,   83,  176,  177,  296,  178,   88,   12,  176,  177,
   13,  178,  105,  105,  181,  105,  166,  248,   82,   90,
   83,   90,   90,   90,   91,  117,   91,   91,   91,   92,
  118,   92,   92,   92,   82,  128,   83,   90,   90,  163,
   90,   50,   91,   91,   50,   91,  265,   92,   92,  129,
   92,  119,  147,  120,  153,  121,   60,  151,  130,   88,
   12,   52,  228,   13,  130,   69,  103,  111,  111,  111,
   28,  121,  208,  206,  106,  106,  106,  118,  210,   99,
  292,  118,  228,  118,  118,  118,  118,  168,  118,  113,
  118,  118,  118,   45,  289,  118,  169,  121,  118,   46,
   47,  121,  118,  121,  121,  121,  121,   95,  121,  228,
  121,  121,  121,   74,   75,  121,   76,   11,   98,   98,
   98,   11,  121,   11,   11,   11,   11,  187,   11,  188,
   11,   11,   11,   56,   89,   11,  189,   48,   93,   93,
   93,   48,   11,   48,   48,   48,   48,  190,   48,  193,
   48,   48,   48,  176,  177,   48,  178,   13,   94,   94,
   94,   13,   48,   13,   13,   13,   13,  196,   13,  197,
   13,   13,   13,  236,   50,   13,  237,   55,   95,   95,
   95,   55,   13,   55,   55,   55,   55,  194,   55,  198,
   55,   55,   55,   41,  201,   55,   37,   54,   89,   89,
   89,   54,   55,   54,   54,   54,   54,  212,   54,   38,
   54,   54,   54,  204,   39,   54,  302,   45,   82,  216,
   83,   45,   54,   45,   45,   45,   45,   81,   45,  217,
   45,   45,   45,  221,  301,   45,  145,   81,  105,  105,
  105,   81,   45,   81,   81,   81,   81,  107,   81,  145,
   81,   81,   81,  116,  145,   81,  167,  125,   82,  127,
   83,  219,   81,   90,   90,   90,  222,  148,   91,   91,
   91,  223,  183,   92,   92,   92,  121,  122,  123,    4,
   88,   12,  255,  224,   13,    6,    7,    8,  227,  225,
    9,  226,   10,   11,   12,   38,   36,   13,   37,    4,
   39,  231,  234,  276,   14,    6,    7,    8,  227,  232,
    9,  165,   10,   11,   12,  144,   40,   13,   38,   36,
  239,   37,  240,   39,   14,   38,    4,  205,   37,   82,
   39,   83,    6,    7,    8,  227,  241,    9,  243,   10,
   11,   12,  242,  244,   13,   88,   12,  199,  200,   13,
  247,   14,   66,   67,   38,   36,  249,   37,  250,   39,
   66,  191,   38,   36,  253,   37,   66,   39,   38,   36,
  261,   37,  257,   39,   38,   36,  264,   37,  283,   39,
   38,   36,  267,   37,  268,   39,   80,   38,   36,  269,
   37,  273,   39,  115,   38,   36,  270,   37,   65,   39,
  126,   38,   36,  272,   37,  274,   39,  307,  233,   82,
   82,   83,   83,  275,  258,  259,  278,   88,   12,  285,
   98,   13,   30,   31,   32,  306,   34,   30,   31,   32,
  282,   34,   82,  286,   83,  295,  287,  288,   30,   31,
   32,  291,   34,    4,  279,   77,   78,  284,    6,    6,
  294,    5,  298,  299,    9,  303,   10,   95,  304,  305,
   90,   92,   90,   88,   12,  308,    4,   13,   96,  309,
  300,  161,   60,  110,    0,    0,   88,   12,    0,    0,
   13,   88,   12,    0,    0,   13,    0,  162,  164,    0,
    0,    0,  137,  140,    0,    0,    0,    0,    0,    0,
    0,  142,    0,   90,    0,  146,    0,  146,  149,    4,
    0,    0,   50,    0,    0,    6,    0,  149,  149,  149,
    9,    0,   10,   95,   30,   31,   32,    0,   34,   35,
    0,    0,    0,  203,   96,    0,    0,    0,    0,    0,
    0,    0,  172,  173,    0,  174,  175,   30,   31,   32,
   33,   34,   35,    0,   30,   31,   32,  146,   34,  185,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  149,
    0,    0,  235,  238,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   30,   31,   32,    0,   34,   35,    0,
    0,   30,   31,   32,    0,   34,   35,   30,   31,   32,
    0,   34,   35,   30,   31,   32,  266,   34,   35,   30,
   31,   32,    0,   34,   35,    0,   30,   31,   32,   79,
   34,   35,    0,   30,   31,   32,    0,   34,   35,    0,
   30,   31,   32,    4,   34,   35,    0,    5,    0,    6,
    7,    8,    0,    0,    9,    0,   10,   11,   12,    4,
    0,   13,   26,    0,    0,    6,    7,    8,   14,    0,
    9,    0,   10,   11,   12,    4,    0,   13,    0,   63,
    0,    6,    7,    8,   14,    0,    9,    0,   10,   11,
   12,    4,    0,   13,    0,  111,    0,    6,    7,    8,
   14,    0,    9,    0,   10,   11,   12,    0,    0,   13,
    0,    4,    0,    0,    0,    0,   14,    6,    7,    8,
  260,    0,    9,    0,   10,   11,   12,    4,    0,   13,
    0,    0,    0,    6,    7,    8,   14,    0,    9,    0,
   10,   11,   12,    0,    0,   13,    0,    0,    0,    0,
    0,    0,   14,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         19,
    0,   44,   44,   41,   42,   43,   44,   45,   40,   47,
   41,   42,   43,   44,   45,   40,   47,   44,   59,   59,
    0,   59,   60,   44,   62,   44,   41,   44,   59,   60,
   40,   62,   40,  123,  100,   41,   50,   43,  123,   45,
    0,  125,   44,  271,   44,   44,  265,   44,   59,  103,
  104,  105,  271,   59,   41,   44,   43,   44,   45,   59,
    0,   36,   60,   48,   44,   43,   59,   45,  259,  260,
   59,   59,   59,   60,   41,   62,   43,   44,   45,   59,
    0,   59,  125,  142,   98,  125,  260,  153,  147,   46,
   44,   44,   59,   60,   41,   62,   43,   44,   45,   15,
    0,  155,    1,    2,   89,   59,   59,   82,   83,   59,
   40,   27,   59,   60,   41,   62,   43,   44,   45,   40,
    0,  180,  181,  125,  144,  125,  125,   26,  125,  278,
   44,  260,   59,   60,   41,   62,   43,   44,   45,  221,
    0,   59,  271,  225,  226,   59,   62,   40,   40,  231,
  232,   59,   59,   60,   46,   62,  131,   42,  133,   41,
    0,   43,   47,   45,  271,  271,   41,  187,   43,  260,
   45,  262,   59,  255,   41,   42,   43,   44,   45,   43,
   47,   45,  272,  273,   59,  275,  271,  272,  272,  273,
  275,  275,   59,   60,  284,   62,   41,  217,   43,   41,
   45,   43,   44,   45,   41,  258,   43,   44,   45,   41,
   41,   43,   44,   45,   43,   41,   45,   59,   60,  258,
   62,  260,   59,   60,  260,   62,  262,   59,   60,   59,
   62,   60,  123,   62,   40,  278,  278,  261,  265,  271,
  272,  282,   40,  275,  271,  260,  271,  285,  286,  287,
  258,  278,  273,  272,  285,  286,  287,  257,  275,  269,
  271,  261,   40,  263,  264,  265,  266,   59,  268,  262,
  270,  271,  272,  271,  262,  275,   59,  257,  278,  277,
  278,  261,  282,  263,  264,  265,  266,  271,  268,   40,
  270,  271,  272,  272,  273,  275,  275,  257,  285,  286,
  287,  261,  282,  263,  264,  265,  266,  123,  268,  271,
  270,  271,  272,  229,   60,  275,   41,  257,  285,  286,
  287,  261,  282,  263,  264,  265,  266,  261,  268,   41,
  270,  271,  272,  272,  273,  275,  275,  257,  285,  286,
  287,  261,  282,  263,  264,  265,  266,  260,  268,   41,
  270,  271,  272,  259,  260,  275,  262,  257,  285,  286,
  287,  261,  282,  263,  264,  265,  266,   41,  268,   41,
  270,  271,  272,    6,  262,  275,   45,  257,  285,  286,
  287,  261,  282,  263,  264,  265,  266,  125,  268,   42,
  270,  271,  272,  262,   47,  275,   41,  257,   43,   59,
   45,  261,  282,  263,  264,  265,  266,   40,  268,  123,
  270,  271,  272,  260,   59,  275,   44,  257,  285,  286,
  287,  261,  282,  263,  264,  265,  266,   60,  268,   44,
  270,  271,  272,   66,   44,  275,   41,   70,   43,   72,
   45,  125,  282,  285,  286,  287,   59,   62,  285,  286,
  287,   41,   62,  285,  286,  287,  285,  286,  287,  257,
  271,  272,  260,   59,  275,  263,  264,  265,  266,  260,
  268,   41,  270,  271,  272,   42,   43,  275,   45,  257,
   47,  260,   59,  261,  282,  263,  264,  265,  266,  260,
  268,  124,  270,  271,  272,  123,   40,  275,   42,   43,
   59,   45,  272,   47,  282,   42,  257,   41,   45,   43,
   47,   45,  263,  264,  265,  266,  273,  268,   59,  270,
  271,  272,  275,   59,  275,  271,  272,  160,  161,  275,
  125,  282,   40,   41,   42,   43,  271,   45,   59,   47,
   40,   41,   42,   43,   59,   45,   40,   47,   42,   43,
  261,   45,   40,   47,   42,   43,  262,   45,   40,   47,
   42,   43,   59,   45,  262,   47,   41,   42,   43,   59,
   45,  271,   47,   41,   42,   43,   59,   45,   28,   47,
   41,   42,   43,   59,   45,  261,   47,   41,   41,   43,
   43,   45,   45,  261,  227,  228,  261,  271,  272,  261,
   50,  275,  271,  272,  273,   59,  275,  271,  272,  273,
   41,  275,   43,  261,   45,  261,   59,   59,  271,  272,
  273,   59,  275,  257,  257,   38,   39,  260,    0,  263,
   59,    0,   59,   59,  268,   59,  270,  271,   59,   59,
   46,   47,   48,  271,  272,   59,    0,  275,  282,   59,
  283,  116,  261,   61,   -1,   -1,  271,  272,   -1,   -1,
  275,  271,  272,   -1,   -1,  275,   -1,  117,  118,   -1,
   -1,   -1,   85,   86,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   87,   -1,   89,   -1,   91,   -1,   93,   94,  257,
   -1,   -1,  260,   -1,   -1,  263,   -1,  103,  104,  105,
  268,   -1,  270,  271,  271,  272,  273,   -1,  275,  276,
   -1,   -1,   -1,  163,  282,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  135,  136,   -1,  138,  139,  271,  272,  273,
  274,  275,  276,   -1,  271,  272,  273,  143,  275,  145,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  155,
   -1,   -1,  202,  203,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  271,  272,  273,   -1,  275,  276,   -1,
   -1,  271,  272,  273,   -1,  275,  276,  271,  272,  273,
   -1,  275,  276,  271,  272,  273,  236,  275,  276,  271,
  272,  273,   -1,  275,  276,   -1,  271,  272,  273,  274,
  275,  276,   -1,  271,  272,  273,   -1,  275,  276,   -1,
  271,  272,  273,  257,  275,  276,   -1,  261,   -1,  263,
  264,  265,   -1,   -1,  268,   -1,  270,  271,  272,  257,
   -1,  275,  260,   -1,   -1,  263,  264,  265,  282,   -1,
  268,   -1,  270,  271,  272,  257,   -1,  275,   -1,  261,
   -1,  263,  264,  265,  282,   -1,  268,   -1,  270,  271,
  272,  257,   -1,  275,   -1,  261,   -1,  263,  264,  265,
  282,   -1,  268,   -1,  270,  271,  272,   -1,   -1,  275,
   -1,  257,   -1,   -1,   -1,   -1,  282,  263,  264,  265,
  266,   -1,  268,   -1,  270,  271,  272,  257,   -1,  275,
   -1,   -1,   -1,  263,  264,  265,  282,   -1,  268,   -1,
  270,  271,  272,   -1,   -1,  275,   -1,   -1,   -1,   -1,
   -1,   -1,  282,
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
"tipo : LONGINT",
"tipo : DOUBLE",
"tipo : ID",
"comparador : '<'",
"comparador : '>'",
"comparador : \"MAYOR_IGUAL\"",
"comparador : \"MENOR_IGUAL\"",
"comparador : \"DISTINTO\"",
};

//#line 282 "gramatica.y"


void yyerror(String mensaje) {
  // funcion utilizada para imprimir errores que produce yacc
  System.out.println("Error yacc: " + mensaje);

AnalizadorLexico lex ;
}
//#line 635 "Parser.java"
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
//#line 20 "gramatica.y"
{System.out.println(val_peek(3));}
break;
case 2:
//#line 21 "gramatica.y"
{System.out.println("ERROR,falta begin programa principal");}
break;
case 3:
//#line 22 "gramatica.y"
{System.out.println("ERROR,falta el ID del programa principal");}
break;
case 4:
//#line 23 "gramatica.y"
{System.out.println("ERROR,falta END del programa principal");}
break;
case 5:
//#line 24 "gramatica.y"
{System.out.println("ERROR,falta BEGIN,END del programa principal");}
break;
case 6:
//#line 25 "gramatica.y"
{System.out.println("ERROR,falta ID,END del programa principal");}
break;
case 12:
//#line 37 "gramatica.y"
{System.out.println(val_peek(1));}
break;
case 13:
//#line 38 "gramatica.y"
{System.out.println("ERROR, Falta ; en la sentencia de declaracion");}
break;
case 15:
//#line 40 "gramatica.y"
{System.out.println("Declaracion de Subtipo");}
break;
case 16:
//#line 41 "gramatica.y"
{System.out.println("Error, Falta de llaves '{}'");}
break;
case 17:
//#line 42 "gramatica.y"
{System.out.println("Error, Falta de llaves '{}'");}
break;
case 18:
//#line 43 "gramatica.y"
{System.out.println("Error, Falta de llaves '{}'");}
break;
case 19:
//#line 44 "gramatica.y"
{System.out.println("ERROR, Falta de rango");}
break;
case 20:
//#line 45 "gramatica.y"
{System.out.println("ERROR, Falta nombre del tipo definido");}
break;
case 21:
//#line 46 "gramatica.y"
{System.out.println("ERROR, Falta el tipo base");}
break;
case 22:
//#line 47 "gramatica.y"
{System.out.println("Declaracion de Struct");}
break;
case 23:
//#line 48 "gramatica.y"
{System.out.println("ERROR, Falta <>.");}
break;
case 24:
//#line 49 "gramatica.y"
{System.out.println("ERROR, Falta la palabra STRUCT.");}
break;
case 25:
//#line 50 "gramatica.y"
{System.out.println("ERROR,Falta  ID al final de la declaracion");}
break;
case 27:
//#line 54 "gramatica.y"
{System.out.println("ERROR,Falta ','entre los digitos del subrango");}
break;
case 29:
//#line 56 "gramatica.y"
{System.out.println("ERROR,Falta ','entre los digitos del subrango");}
break;
case 31:
//#line 58 "gramatica.y"
{System.out.println("ERROR,Falta ','entre los digitos del subrango");}
break;
case 32:
//#line 61 "gramatica.y"
{System.out.println("Declaracion de Funcion");}
break;
case 33:
//#line 62 "gramatica.y"
{System.out.println("ERROR,Falta la declaracion del tipo de la FUN ");}
break;
case 34:
//#line 63 "gramatica.y"
{System.out.println("ERROR,Falta la declaracion de la palabra reservada FUN");}
break;
case 35:
//#line 64 "gramatica.y"
{System.out.println("ERROR,Falta el ID de la funcion");}
break;
case 36:
//#line 65 "gramatica.y"
{System.out.println("ERROR,Falta de () a la hora de los parametros");}
break;
case 37:
//#line 66 "gramatica.y"
{System.out.println("ERROR,Falta de parametros en la FUN");}
break;
case 38:
//#line 67 "gramatica.y"
{System.out.println("ERROR,Falta de BEGIN en la FUN");}
break;
case 39:
//#line 69 "gramatica.y"
{System.out.println("ERROR,Falsa cuerpo de funcion ");}
break;
case 43:
//#line 75 "gramatica.y"
{System.out.println("Declaracion REPEAT-WHILE");}
break;
case 44:
//#line 77 "gramatica.y"
{System.out.println("ERROR,falta palabra WHILE");}
break;
case 45:
//#line 78 "gramatica.y"
{System.out.println("ERROR,falta palabra ';' al final de la declaracion ");}
break;
case 46:
//#line 79 "gramatica.y"
{System.out.println("ERROR,falta falta la condicion del WHILE ");}
break;
case 47:
//#line 80 "gramatica.y"
{System.out.println("Declaracion de GOTO ");}
break;
case 48:
//#line 81 "gramatica.y"
{System.out.println("ERROR, Falta ';' al final de la declaracion  ");}
break;
case 49:
//#line 82 "gramatica.y"
{System.out.println("ERROR,falta la ETIQUETA  ");}
break;
case 50:
//#line 83 "gramatica.y"
{System.out.println("ERROR,falta el GOTO  ");}
break;
case 51:
//#line 86 "gramatica.y"
{System.out.println(val_peek(3));}
break;
case 53:
//#line 88 "gramatica.y"
{System.out.println("Falta parámetro en sentencia OUTF");}
break;
case 54:
//#line 89 "gramatica.y"
{System.out.println("Falta ';' en la sentencia OUTF");}
break;
case 55:
//#line 90 "gramatica.y"
{System.out.println("Falta ';' en la sentencia OUTF");}
break;
case 56:
//#line 91 "gramatica.y"
{System.out.println("Faltan los parentesis en la sentencia OUTF");}
break;
case 57:
//#line 92 "gramatica.y"
{System.out.println("Faltan los parentesis en la sentencia OUTF");}
break;
case 58:
//#line 95 "gramatica.y"
{System.out.println("Declaracion del Cuerpo de la funcion");}
break;
case 59:
//#line 96 "gramatica.y"
{System.out.println("Declaracion del Cuerpo de la funcion");}
break;
case 60:
//#line 97 "gramatica.y"
{System.out.println("ERROR, falta ';' al final de la declaracion");}
break;
case 61:
//#line 98 "gramatica.y"
{System.out.println("ERROR, falta RET de la funcion");}
break;
case 62:
//#line 99 "gramatica.y"
{System.out.println("ERROR, falta '()' a la hora de realizar el RET");}
break;
case 63:
//#line 100 "gramatica.y"
{System.out.println("ERROR, falta ')' a la hora de realizar el RET");}
break;
case 64:
//#line 101 "gramatica.y"
{System.out.println("ERROR, falta '(' a la hora de realizar el RET");}
break;
case 65:
//#line 102 "gramatica.y"
{System.out.println("ERROR, falta '()' a la hora de realizar el RET");}
break;
case 66:
//#line 103 "gramatica.y"
{System.out.println("ERROR, falta ')' a la hora de realizar el RET");}
break;
case 67:
//#line 104 "gramatica.y"
{System.out.println("ERROR, falta '(' a la hora de realizar el RET");}
break;
case 69:
//#line 109 "gramatica.y"
{System.out.println("ERROR, falta ID del parametro");}
break;
case 70:
//#line 113 "gramatica.y"
{if (val_peek(3).sval.equals(null)){ System.out.println("No existe una funcion con ese nombre");}}
break;
case 75:
//#line 124 "gramatica.y"
{System.out.println("ERROR, Falta THEN luego de la condicion");}
break;
case 76:
//#line 125 "gramatica.y"
{System.out.println("ERROR,falta de Condicion");}
break;
case 77:
//#line 126 "gramatica.y"
{System.out.println("ERROR,falta el bloque ejecutable");}
break;
case 78:
//#line 127 "gramatica.y"
{System.out.println("ERROR,falta END_IF al final de la declaracion");}
break;
case 79:
//#line 128 "gramatica.y"
{System.out.println("ERROR,falta ';' al final de la declaracion");}
break;
case 80:
//#line 129 "gramatica.y"
{System.out.println("ERROR, falta ELSE luego de la sentencias de ejecucion");}
break;
case 81:
//#line 130 "gramatica.y"
{System.out.println("ERROR,falta ';' al final de la declaracion");}
break;
case 82:
//#line 131 "gramatica.y"
{System.out.println("ERROR, falta el bloque ejecutable en el ELSE");}
break;
case 83:
//#line 132 "gramatica.y"
{System.out.println("ERROR, falta el bloque ejecutable en el IF");}
break;
case 84:
//#line 133 "gramatica.y"
{System.out.println("ERROR,falta END_IF al final de la declaracion");}
break;
case 86:
//#line 138 "gramatica.y"
{System.out.println("ERROR,falta de comparador en condicion");}
break;
case 87:
//#line 140 "gramatica.y"
{System.out.println("ERROR,falta de '(' ");}
break;
case 88:
//#line 143 "gramatica.y"
{val_peek(3).ival = val_peek(2).ival;}
break;
case 89:
//#line 146 "gramatica.y"
{yyval.ival = val_peek(2).ival + val_peek(0).ival;}
break;
case 90:
//#line 147 "gramatica.y"
{yyval.ival = val_peek(2).ival - val_peek(0).ival;}
break;
case 91:
//#line 148 "gramatica.y"
{System.out.println("ERROR, hay 2 operadores");}
break;
case 92:
//#line 149 "gramatica.y"
{System.out.println("ERROR, hay 2 operadores");}
break;
case 93:
//#line 151 "gramatica.y"
{System.out.println("ERROR, falta de operando");}
break;
case 94:
//#line 152 "gramatica.y"
{System.out.println("ERROR, falta de operando");}
break;
case 95:
//#line 153 "gramatica.y"
{System.out.println("ERROR, falta de operando");}
break;
case 96:
//#line 154 "gramatica.y"
{System.out.println("Declaracion de TOD");}
break;
case 97:
//#line 155 "gramatica.y"
{System.out.println("ERROR, falta de expresion");}
break;
case 98:
//#line 156 "gramatica.y"
{yyval = val_peek(0);}
break;
case 101:
//#line 162 "gramatica.y"
{System.out.println("ERROR, hay 2 operadores");}
break;
case 102:
//#line 163 "gramatica.y"
{System.out.println("ERROR, hay 2 operadores");}
break;
case 103:
//#line 164 "gramatica.y"
{System.out.println("ERROR, hay 2 operadores");}
break;
case 104:
//#line 165 "gramatica.y"
{System.out.println("ERROR, hay 2 operadores");}
break;
case 105:
//#line 166 "gramatica.y"
{System.out.println("ERROR, falta de operando");}
break;
case 106:
//#line 167 "gramatica.y"
{System.out.println("ERROR, falta de operando");}
break;
case 107:
//#line 168 "gramatica.y"
{System.out.println("ERROR, falta de operando");}
break;
case 108:
//#line 169 "gramatica.y"
{System.out.println("ERROR, falta de operando");}
break;
case 109:
//#line 170 "gramatica.y"
{yyval= val_peek(0);}
break;
case 111:
//#line 175 "gramatica.y"
{yyval = val_peek(0);  System.out.println("la variable" + val_peek(0).sval + "tiene valor: " + val_peek(0).ival);}
break;
case 112:
//#line 176 "gramatica.y"
{yyval = val_peek(0);
                                      Long valor = Long.parseLong(val_peek(0).sval);
                                      if (valor == 2147483648L){
                                        yyerror("El número está fuera del rango permitido para un longint positivo.");
                                      }
                                      lector.tablaSimbolos.addToken(val_peek(0).sval,yychar, "LONGINT");
                                    }
break;
case 113:
//#line 183 "gramatica.y"
{
                                             String lexema = '-'+ val_peek(0).sval;
                                            lector.tablaSimbolos.addToken(lexema,yychar,"LONGINT");
                                             }
break;
case 114:
//#line 190 "gramatica.y"
{
                                      /*ParserVal valor = val_peek(1);
                                      System.out.println("llegue para LOGNINT positivo" + valor.lval);
                                      Long numero = valor.lval;
                                      if (numero > 0x7FFFFFFF) {
                                        yyerror("El número está fuera del rango permitido para un HEXA positivo.");
                                      }
                                      else{
                                        valor.ival = numero.intValue();
                                        lector.tablaSimbolos.addToken(numero.toString(),273);
                                     } */
                                                                            }
break;
case 115:
//#line 202 "gramatica.y"
{/*
                                          ParserVal valor = val_peek(1);
                                          System.out.println("llegue para LONGINT negativo " + valor.lval);
                                          Long numero = valor.lval;
                                          numero = -numero;
                                          int max = -2147483648;
                                          if (numero < -2147483648) {
                                            yyerror("El número está fuera del rango permitido para un longint negativo.");
                                          } else{
                                            valor.ival = numero.intValue();
                                            lector.tablaSimbolos.addToken(numero.toString(),273);
                                          }*/
                                     }
break;
case 116:
//#line 216 "gramatica.y"
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
//#line 230 "gramatica.y"
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
//#line 253 "gramatica.y"
{System.out.println(val_peek(0));}
break;
case 126:
//#line 265 "gramatica.y"
{System.out.println("ERROR, falta de ',' en la lista ");}
break;
//#line 1232 "Parser.java"
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
