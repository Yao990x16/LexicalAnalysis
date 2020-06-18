package grammaticalanalysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
public class Grammaticalanalysis {
	private int num=0;//单词位置计量
	private ArrayList<String> strKindList =new  ArrayList<String>();//单词种类
	private ArrayList<String> strList = new ArrayList<String>();//单词
	private ArrayList<String> returnFunction = new ArrayList<String>();//记录有返回值函数
	private ArrayList<String> voidFunction=new ArrayList<String>();//记录无返回值函数
	private BufferedWriter output;//语法翻译后生成的文件
	public Grammaticalanalysis() {
		try {
			FileOutputStream writerStream = new FileOutputStream("output.txt");
			
			output = new BufferedWriter(new OutputStreamWriter(writerStream, "UTF-8"));
			//System.out.println("I come here.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/*
	 * ＜加法运算符＞ ::= +｜-
	 */
	public void addingOperator() throws IOException {
		String strKind = strKindList.get(num);
		if(strKind.equals("PLUS")||strKind.equals("MINU")) {
			output.write(strKindList.get(num)+" "+strList.get(num));
			output.newLine();
			System.out.println(strKindList.get(num)+" "+strList.get(num));
			num++;
			
		}
	}
	
	/**
	 * ＜乘法运算符＞  ::= *｜/
	 * @throws IOException
	 */
	public void multiplyingOperator() throws IOException {
		String strKind = strKindList.get(num);
		if(strKind.equals("MULT")||strKind.equals("DIV")) {
			output.write(strKindList.get(num)+" "+strList.get(num));
			output.newLine();
			System.out.println(strKindList.get(num)+" "+strList.get(num));
			num++;
			
		}
		
	}
	/**
	 * ＜关系运算符＞  ::=  <｜<=｜>｜>=｜!=｜==
	 * @throws IOException
	 */
	public void relationalOperator() throws IOException {
		String strKind = strKindList.get(num);
		if(strKind.equals("LSS")||strKind.equals("GRE")||strKind.equals("EQL")||strKind.equals("GEQ")
				||strKind.equals("LEQ")||strKind.equals("NEQ")) {
			output.write(strKindList.get(num)+" "+strList.get(num));
			output.newLine();
			System.out.println(strKindList.get(num)+" "+strList.get(num));
			num++;
			
		}
	}
	/**
	 * ＜字符＞    ::=  '＜加法运算符＞'｜'＜乘法运算符＞'｜'＜字母＞'｜'＜数字＞'
	 * @throws IOException
	 */
	public void character() throws IOException {
		writeThisGetNext("CHARCON");
	}
	/**
	 * 字符串
	 * @throws IOException
	 */
	public void  characterString () throws IOException {
		writeThisGetNext("STRCON");
		output.write("<字符串>");
		output.newLine();
		System.out.println("<字符串>");
	}
	/**
	 * ＜程序＞    ::= ［＜常量说明＞］［＜变量说明＞］{＜有返回值函数定义＞|＜无返回值函数定义＞}＜主函数＞
	 * @throws IOException
	 */
	public void procedure() throws IOException {
		if(strKindList.get(num).equals("CONSTTK")) {
			constantDeclaration();
		}
		if((strKindList.get(num).equals("INTTK")||strKindList.get(num).equals("CHARTK"))&&!strKindList.get(num+2).equals("LPARENT")) {
			variableDeclaration();
		}
		while(num<strKindList.size()) {
			if(strKindList.get(num+1).equals("MAINTK")) break;
			if(strKindList.get(num).equals("VOIDTK")) {
				noReturnValueFunction();
			}else {
				returnValueFunction();
			}
			
		}
		mainFunction();
		output.write("<程序>");
		output.newLine();
		System.out.println("<程序>");
	}
	/***
	 * ＜标识符＞    ::=  ＜字母＞｛＜字母＞｜＜数字＞｝
	 * @throws IOException
	 */
	public void  idenfrString () throws IOException {
		String strKind = strKindList.get(num);
		if(strKind.equals("IDENFR")) {
			output.write(strKindList.get(num)+" "+strList.get(num));
			output.newLine();
			System.out.println(strKindList.get(num)+" "+strList.get(num));
			
			num++;
			
		}
	}
	/**＜无符号整数＞  ::= ＜非零数字＞｛＜数字＞｝| 0
	 * 
	 * @throws IOException
	 */
	public void  intconString () throws IOException {
		String strOut=null;
		String strKind = strKindList.get(num);
		if(strKind=="INTCON"||strKind.equals("INTCON")) {
			strOut="<无符号整数>";
			output.write(strKindList.get(num)+" "+strList.get(num));
			output.newLine();
			output.write(strOut);
			output.newLine();
			System.out.println(strKindList.get(num)+" "+strList.get(num));
			System.out.println(strOut);
			num++;
			
		}
	}
	/**
	 * 整数
	 * @throws IOException
	 */
	public  void intString() throws IOException {
		addingOperator();
		intconString();
		output.write("<整数>");
		output.newLine();
		System.out.println("<整数>");
		
	}
	/**
	 * ＜常量定义＞   ::=   int＜标识符＞＝＜整数＞{,＜标识符＞＝＜整数＞}
	 *
     *            | char＜标识符＞＝＜字符＞{,＜标识符＞＝＜字符＞}
	 * @throws IOException
	 */
	public void   constantDefinition  () throws IOException {
		if(strKindList.get(num).equals("INTTK")) {
			writeThisGetNext("INTTK");
			while(true) {
			idenfrString();
			writeThisGetNext("ASSIGN");
			intString();
			if(!strKindList.get(num).equals("COMMA")) break;
				writeThisGetNext("COMMA");
			}
		}
		else if(strKindList.get(num).equals("CHARTK")) {
			writeThisGetNext("CHARTK");
			while(true) {
			idenfrString();
			writeThisGetNext("ASSIGN");
			writeThisGetNext("CHARCON");
			if(!strKindList.get(num).equals("COMMA")) break;
			else {
				writeThisGetNext("COMMA");
			}
			}
		}
		
		output.write("<常量定义>");
		output.newLine();
		System.out.println("<常量定义>");
	}
	/**
	 * ＜常量说明＞ ::=  const＜常量定义＞;{ const＜常量定义＞;}
	 * @throws IOException
	 */
	public void constantDeclaration() throws IOException {
		while(true) {
			writeThisGetNext("CONSTTK");
			constantDefinition();
			writeThisGetNext("SEMICN");
			if(num>=strKindList.size())break;
			if(!strKindList.get(num).equals("CONSTTK")) {
				break;
			}
		}
		output.write("<常量说明>");
		output.newLine();
		System.out.println("<常量说明>");
	}
	/**
	 * ＜声明头部＞   ::=  int＜标识符＞ |char＜标识符＞
	 * @throws IOException
	 */
	public void headStatement() throws IOException {
		String strKind = strKindList.get(num);
		String strOut=null;
		if(strKind.equals("INTTK")) {
			writeThisGetNext("INTTK");
			idenfrString();
		}
		else if(strKind.equals("CHARTK")) {
			writeThisGetNext("CHARTK");
			idenfrString();
		}
		output.write("<声明头部>");
		output.newLine();
		System.out.println("<声明头部>");
	}
	/**
	 * ＜变量说明＞  ::= ＜变量定义＞;{＜变量定义＞;}
	 * @throws IOException
	 */
	public void variableDeclaration() throws IOException {
		while(true) {
			variableDefinition();
			writeThisGetNext("SEMICN");
			
			if(num>=strKindList.size())break;
			String strKind = strKindList.get(num);
			if(!(strKind.equals("INTTK")||strKind.equals("CHARTK"))) { break;}
			if((strKind.equals("INTTK")||strKind.equals("CHARTK"))&&(strKindList.get(num+2).equals("LPARENT"))) { break;}
		}
		output.write("<变量说明>");
		output.newLine();
		System.out.println("<变量说明>");
	}
	/**
	 * ＜变量定义＞  ::= ＜类型标识符＞(＜标识符＞|＜标识符＞'['＜无符号整数＞']')
	 * {,(＜标识符＞|＜标识符＞'['＜无符号整数＞']' )}
	 * @throws IOException
	 */
	public void variableDefinition() throws IOException {
		typeIdentifier();
		while(true) {
			idenfrString();
			if(strKindList.get(num).equals("LBRACK")) {
				writeThisGetNext("LBRACK");
				intconString();
				writeThisGetNext("RBRACK");
			}
			if(strKindList.size()<=num)break;
			if(!strKindList.get(num).equals("COMMA")) break;
			writeThisGetNext("COMMA");
		}
		
			
		output.write("<变量定义>");
		output.newLine();
		System.out.println("<变量定义>");
	}
	/**
	 * ＜类型标识符＞      ::=  int | char
	 * @throws IOException
	 */
	public void typeIdentifier () throws IOException {
		String strKind = strKindList.get(num);
		if(strKind.equals("INTTK")||strKind.equals("CHARTK")) {
			output.write(strKindList.get(num)+" "+strList.get(num));
			output.newLine();
			System.out.println(strKindList.get(num)+" "+strList.get(num));
			num++;
		}
	}
	/**
	 * ＜有返回值函数定义＞  ::=  ＜声明头部＞'('＜参数表＞')' '{'＜复合语句＞'}'
	 * @throws IOException
	 */
	public void returnValueFunction() throws IOException {
		headStatement();
		returnFunction.add(strList.get(num-1));
		writeThisGetNext("LPARENT");
		parameterList();
		writeThisGetNext("RPARENT");
		
		writeThisGetNext("LBRACE");
		compoundStatement();
		writeThisGetNext("RBRACE");
		output.write("<有返回值函数定义>");
		output.newLine();
		System.out.println("<有返回值函数定义>");
	}
	/**
	 * ＜无返回值函数定义＞  ::= void＜标识符＞'('＜参数表＞')''{'＜复合语句＞'}'
	 * @throws IOException
	 */
	public void noReturnValueFunction() throws IOException {
		writeThisGetNext("VOIDTK");
		voidFunction.add(strList.get(num));
		idenfrString();
		writeThisGetNext("LPARENT");
		parameterList();
		writeThisGetNext("RPARENT");
		writeThisGetNext("LBRACE");
		compoundStatement();
		writeThisGetNext("RBRACE");
		output.write("<无返回值函数定义>");
		output.newLine();
		System.out.println("<无返回值函数定义>");
	}
	/***
	 * ＜复合语句＞   ::=  ［＜常量说明＞］［＜变量说明＞］＜语句列＞
	 * @throws IOException
	 */
	public void compoundStatement() throws IOException {
		if(strKindList.get(num).equals("CONSTTK")) {
			constantDeclaration();
		}
		if(strKindList.get(num).equals("INTTK")||strKindList.get(num).equals("CHARTK")) {
			variableDeclaration();
		}
		statementList();
		output.write("<复合语句>");
		output.newLine();
		System.out.println("<复合语句>");
	}
	/**
	 * ＜参数表＞    ::=  ＜类型标识符＞＜标识符＞{,＜类型标识符＞＜标识符＞}| ＜空＞
	 * @throws IOException
	 */
	public void  parameterList() throws IOException {
		while(true) {
			typeIdentifier();
			idenfrString();
			if(num>=strKindList.size())break;
			String strKind = strKindList.get(num);
			if(!strKind.equals("COMMA")) {
				break;
			}
			writeThisGetNext("COMMA");
		}
		
		output.write("<参数表>");
		output.newLine();
		System.out.println("<参数表>");
	}
	/**
	 * ＜主函数＞    ::= void main‘(’‘)’ ‘{’＜复合语句＞‘}’
	 * @throws IOException
	 */
	public void mainFunction() throws IOException {
		writeThisGetNext("VOIDTK");
		writeThisGetNext("MAINTK");
		writeThisGetNext("LPARENT");
		writeThisGetNext("RPARENT");
		writeThisGetNext("LBRACE");
		compoundStatement();
		writeThisGetNext("RBRACE");
		output.write("<主函数>");
		output.newLine();
		System.out.println("<主函数>");
	}
	/**
	 * ＜表达式＞    ::= ［＋｜－］＜项＞{＜加法运算符＞＜项＞}
	 * @throws IOException
	 */
	public void  expression() throws IOException {
		if(strKindList.get(num).equals("PLUS")||strKindList.get(num).equals("MINU")) {
			output.write(strKindList.get(num)+" "+strList.get(num));
			output.newLine();
			System.out.println(strKindList.get(num)+" "+strList.get(num));
			num++;
		}
		item();
		while(true) {
			if(!(strKindList.get(num).equals("PLUS")||strKindList.get(num).equals("MINU"))) {
				break;
			}
			
			addingOperator();
			item();
		
		}
		output.write("<表达式>");
		output.newLine();
		System.out.println("<表达式>");
	}
	
	/**
	 * ＜项＞     ::= ＜因子＞{＜乘法运算符＞＜因子＞}
	 * @throws IOException
	 */
	public void item() throws IOException {
		divisor();
		while(true) {
			if(!(strKindList.get(num).equals("MULT")||strKindList.get(num).equals("DIV"))) {
				break;
			}
			multiplyingOperator();
			divisor();
			
		}
		output.write("<项>");
		output.newLine();
		System.out.println("<项>");
	}
	/**
	 * ＜因子＞    ::= ＜标识符＞｜＜标识符＞'['＜表达式＞']'|'('＜表达式＞')'｜＜整数＞|＜字符＞｜＜有返回值函数调用语句＞   
	 * @throws IOException
	 */
	public void divisor() throws IOException {
		if(strKindList.get(num).equals("IDENFR")&&!strKindList.get(num+1).equals("LPARENT")) {
			idenfrString();
			if(strKindList.get(num).equals("LBRACK")) {
				writeThisGetNext("LBRACK");
				expression();
				writeThisGetNext("RBRACK");
			}
		}
		else if(strKindList.get(num).equals("LPARENT")){
			writeThisGetNext("LPARENT");
			expression();
			writeThisGetNext("RPARENT");
		}
		else if(strKindList.get(num).equals("INTCON")
				||strKindList.get(num).equals("PLUS")||strKindList.get(num).equals("MINU")) {
			intString();
			
		}
		else if(strKindList.get(num).equals("CHARCON")) {
			writeThisGetNext("CHARCON");
		}
		else {
			returnValueStatement();
		}
		output.write("<因子>");
		output.newLine();
		System.out.println("<因子>");
	}
	/**
	 * ＜语句＞    ::= ＜条件语句＞｜＜循环语句＞| '{'＜语句列＞'}'| ＜有返回值函数调用语句＞; 
                    |＜无返回值函数调用语句＞;｜＜赋值语句＞;｜＜读语句＞;｜＜写语句＞;｜＜空＞;|＜返回语句＞;
	 * @throws IOException
	 */
	public void  statement() throws IOException {
		if(strKindList.get(num).equals("IFTK")) {
			ifStatement();
		}
		else if(strKindList.get(num).equals("WHILETK")||strKindList.get(num).equals("DOTK")||strKindList.get(num).equals("FORTK")) {
			whileStatement();
		}
		else if(strKindList.get(num).equals("LBRACE")) {
			writeThisGetNext("LBRACE");
			statementList();
			writeThisGetNext("RBRACE");
		}
		else if(strKindList.get(num).equals("IDENFR")) {
			if(strKindList.get(num+1).equals("ASSIGN")||strKindList.get(num+1).equals("LBRACK")) {
				assignmentStatement();
			}
			else if(strKindList.get(num+1).equals("LPARENT")){
				
				for(int i=0;i<returnFunction.size();i++) {
					System.out.println(strList.get(num)+"  "+returnFunction.get(i));
					
					if(strList.get(num).equals(returnFunction.get(i))) {
						returnValueStatement();
					}
				}
				for(int i=0;i<voidFunction.size();i++) {
					if(strList.get(num).equals(voidFunction.get(i))) {
						noReturnValueStatement();
					}
				}
			}
			writeThisGetNext("SEMICN");
		}
		else if(strKindList.get(num).equals("SCANFTK")) {
			scanfStstement();
			writeThisGetNext("SEMICN");
		}
		else if(strKindList.get(num).equals("PRINTFTK")) {
			printfStatement();
			writeThisGetNext("SEMICN");
		}
		else if(strKindList.get(num).equals("RETURNTK")) {
			returnStatement();
			writeThisGetNext("SEMICN");
		}
		else if(strKindList.get(num).equals("SEMICN")) {
			writeThisGetNext("SEMICN");
		}
		output.write("<语句>");
		output.newLine();
		System.out.println("<语句>");
	}
	
	/**
	 * ＜标识符＞＝＜表达式＞|＜标识符＞'['＜表达式＞']'=＜表达式＞
	 * @throws IOException
	 */
	public void assignmentStatement() throws IOException {
		idenfrString();
		if(strKindList.get(num).equals("LBRACK")) {
			writeThisGetNext("LBRACK");
			expression();
			writeThisGetNext("RBRACK");
		}
		writeThisGetNext("ASSIGN");
		expression();
		output.write("<赋值语句>");
		output.newLine();
		System.out.println("<赋值语句>");
	}
	/**
	 * ＜条件语句＞  ::= if '('＜条件＞')'＜语句＞［else＜语句＞］
	 * @throws IOException
	 */
	public void ifStatement() throws IOException {
		writeThisGetNext("IFTK");
		writeThisGetNext("LPARENT");
		condition();
		writeThisGetNext("RPARENT");
		statement();
		if(strKindList.get(num).equals("ELSETK")) {
			writeThisGetNext("ELSETK");
			statement();
		}
		output.write("<条件语句>");
		output.newLine();
		System.out.println("<条件语句>");
	}
	/**
	 * ＜条件＞    ::=  ＜表达式＞＜关系运算符＞＜表达式＞ //整型表达式之间才能进行关系运算
	 * 				｜＜表达式＞    //表达式为整型，其值为0条件为假，值不为0时条件为真   
	 * @throws IOException
	 */
	public void condition() throws IOException {
		expression();
		String strKind = strKindList.get(num);
		if(strKind.equals("LSS")||strKind.equals("GRE")||strKind.equals("EQL")||strKind.equals("GEQ")
				||strKind.equals("LEQ")||strKind.equals("NEQ")){
		relationalOperator();
		expression();
		}
		output.write("<条件>");
		output.newLine();
		System.out.println("<条件>");
	}
	/**
	 * ＜循环语句＞   ::=  while '('＜条件＞')'＜语句＞| 
	 * do＜语句＞while '('＜条件＞')' |
	 * for'('＜标识符＞＝＜表达式＞;＜条件＞;＜标识符＞＝＜标识符＞(+|-)＜步长＞')'＜语句＞
	 * @throws IOException
	 */
	public void whileStatement() throws IOException {
		if(strKindList.get(num).equals("WHILETK")) {
			writeThisGetNext("WHILETK");
			writeThisGetNext("LPARENT");
			condition();
			writeThisGetNext("RPARENT");
			statement();
		}
		else if(strKindList.get(num).equals("DOTK")) {
			writeThisGetNext("DOTK");
			statement();
			writeThisGetNext("WHILETK");
			writeThisGetNext("LPARENT");
			condition();
			writeThisGetNext("RPARENT");
			
		}
		else if(strKindList.get(num).equals("FORTK")) {
			writeThisGetNext("FORTK");
			writeThisGetNext("LPARENT");
			idenfrString();
			writeThisGetNext("ASSIGN");
			expression();
			writeThisGetNext("SEMICN");
			condition();
			writeThisGetNext("SEMICN");
			idenfrString();
			writeThisGetNext("ASSIGN");
			idenfrString();
			addingOperator();
			stepSize();
			writeThisGetNext("RPARENT");
			statement();
		}
		
		output.write("<循环语句>");
		output.newLine();
		System.out.println("<循环语句>");
	}
	/**
	 * 步长
	 * @throws IOException
	 */
	public void  stepSize() throws IOException {
		intconString();
		output.write("<步长>");
		output.newLine();
		System.out.println("<步长>");
	}
	/**
	 * ＜有返回值函数调用语句＞ ::= ＜标识符＞'('＜值参数表＞')'
	 * @throws IOException
	 */
	public void returnValueStatement() throws IOException {
		idenfrString();
		writeThisGetNext("LPARENT");
		valueParameterTable();
		writeThisGetNext("RPARENT");
		output.write("<有返回值函数调用语句>");
		output.newLine();
		System.out.println("<有返回值函数调用语句>");
	}
	/**
	 * ＜无返回值函数调用语句＞ ::= ＜标识符＞'('＜值参数表＞')'
	 * @throws IOException
	 */
	public void noReturnValueStatement() throws IOException {
		idenfrString();
		writeThisGetNext("LPARENT");
		valueParameterTable();
		writeThisGetNext("RPARENT");
		output.write("<无返回值函数调用语句>");
		output.newLine();
		System.out.println("<无返回值函数调用语句>");
	}
	/**
	 * ＜值参数表＞   ::= ＜表达式＞{,＜表达式＞}｜＜空＞
	 * @throws IOException
	 */
	public void valueParameterTable() throws IOException {
		if(strKindList.get(num).equals("PLUS")||strKindList.get(num).equals("MINU")||strKindList.get(num).equals("IDENFR")
				||strKindList.get(num).equals("LPARENT")||strKindList.get(num).equals("INTCON")
				||strKindList.get(num).equals("CHARCON")) {
			expression();
			while(true) {
				if(!strKindList.get(num).equals("COMMA")) {
					break;
				}
				writeThisGetNext("COMMA");
				expression();
				
			}
		}
		output.write("<值参数表>");
		output.newLine();
		System.out.println("<值参数表>");
	}
	/**
	 * ＜语句列＞   ::= ｛＜语句＞｝
	 * @throws IOException
	 */
	public void statementList() throws IOException {
		while(true) {
			if(!(strKindList.get(num).equals("IFTK")||
					strKindList.get(num).equals("WHILETK")||strKindList.get(num).equals("DOTK")||strKindList.get(num).equals("FORTK")
					||strKindList.get(num).equals("LBRACE")||(strKindList.get(num).equals("SEMICN")
					||strKindList.get(num).equals("SCANFTK")||strKindList.get(num).equals("PRINTFTK")
					||strKindList.get(num).equals("RETURNTK")||strKindList.get(num).equals("IDENFR"))))break;
			statement();
		}
		output.write("<语句列>");
		output.newLine();
		System.out.println("<语句列>");
	}
	/**
	 * ＜读语句＞    ::=  scanf '('＜标识符＞{,＜标识符＞}')'
	 * @throws IOException
	 */
	public void scanfStstement() throws IOException {
		writeThisGetNext("SCANFTK");
		writeThisGetNext("LPARENT");
		idenfrString();
		while(true) {
			if(strKindList.get(num).equals("RPARENT")) {
				break;
			}
			writeThisGetNext("COMMA");
			idenfrString();
			
		}
		writeThisGetNext("RPARENT");
		output.write("<读语句>");
		output.newLine();
		System.out.println("<读语句>");
	}
	/**
	 * ＜写语句＞    ::= printf '(' ＜字符串＞,＜表达式＞ ')'| printf '('＜字符串＞ ')'| printf '('＜表达式＞')'
	 * @throws IOException
	 */
	public void printfStatement() throws IOException {
		writeThisGetNext("PRINTFTK");
		writeThisGetNext("LPARENT");
		if(strKindList.get(num).equals("STRCON")) {
			characterString();
			if(strKindList.get(num).equals("COMMA")) {
				writeThisGetNext("COMMA");
				expression();
			}
		}
		else {
			expression();
		}
		writeThisGetNext("RPARENT");
		output.write("<写语句>");
		output.newLine();
		System.out.println("<写语句>");
	}
	
	public void returnStatement() throws IOException {
		writeThisGetNext("RETURNTK");
		if(strKindList.get(num).equals("LPARENT")) {
			writeThisGetNext("LPARENT");
			expression();
			writeThisGetNext("RPARENT");
		}
		output.write("<返回语句>");
		output.newLine();
		System.out.println("<返回语句>");
	}
	/**
	 * 在单词种类线性表中，读取当前种类，并将其和相应的单词写入输出流中，并将计数器+1
	 * @param strkind
	 * @throws IOException
	 */
	public void writeThisGetNext(String strkind) throws IOException {
		
		if(strKindList.get(num).equals(strkind)) {
		output.write(strKindList.get(num)+" "+strList.get(num));
		output.newLine();
		System.out.println(strKindList.get(num)+" "+strList.get(num));
		num++;
		}else {
			System.out.println("________________________________________________"+strKindList.get(num)+"erro："+strkind);
			num++;
		}
	}
	
	public static void main(String[] args) throws IOException {
		Grammaticalanalysis grammaticalanalysis = new Grammaticalanalysis();
		lexicalanalysis.Lexicalanalysis.analysis("testfile.txt", "output2.txt");//源文件，词法分析器生成的文件
		BufferedReader input = new BufferedReader(new FileReader(new File("output2.txt")));
		String inLine = null;
		while(true) {
			inLine = input.readLine();
			if(inLine==null) break;
				String[] inStr=inLine.split(" ",2);
				grammaticalanalysis.strList.add(inStr[1].trim());
				grammaticalanalysis.strKindList.add(inStr[0]);
		}
		input.close();//关闭词法分析生成文件
		grammaticalanalysis.procedure();//启动语法分析
		grammaticalanalysis.output.close();//关闭输出文件
	}
}
