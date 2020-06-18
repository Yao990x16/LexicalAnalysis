package lexicalanalysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Lexicalanalysis {
	
	/*public static void main(String[] args) throws IOException {
		analysis("testfile.txt", "output.txt");
	}
	*/
	public static void analysis(String testfile,String outfile) throws IOException {
		
		String  letterFirst = "[a-zA-Z_$]";
		String  letterFollow = "[0-9a-zA-Z_]";
		
		String  digitFirst = "[0-9]";
		String  digitFollow="[0-9]";
		
		String scopeSymbol = "[(){},;]";
		String operationSymbol = "[*+-/><=!]" ;
		
		BufferedReader input = new BufferedReader(new FileReader(new File(testfile)));
		BufferedWriter output = new BufferedWriter(new FileWriter(new File(outfile)));
		
		String inLine = null;
		
		while(true) {//read this testfile
			inLine = input.readLine();
			 if(inLine == null) break;//readed all
			 
			// System.out.println("_____________________");
			 //System.out.println(inLine);
			 
			 char[] inChars = inLine.toCharArray();
				int num = 0; //char array sign
				 
				 while(true) {// dispose the line or inChars[]
					 
					 if(num >= inChars.length) break; //deal with finish
						
					 while(inChars[num] == ' '||inChars[num] == '	'){// deal with spacing and tab
							num++;
							if(num >= inChars.length) {
								break;
							}
						}
					 
					 if(num >= inChars.length) {
						 break;
					 }
					 
					 String str = null;
					 String kindStr = null;
						
					 if(String.valueOf(inChars[num]).matches(letterFirst)) {//
							str = getStrings(inChars,letterFollow,num);
							num+=str.length();
							switch(str) {
							case "int": kindStr = "INTTK"; break;
							case "const": kindStr = "CONSTTK"; break;
							case "char": kindStr = "CHARTK";  break;
							case "return":kindStr = "RETURNTK"; break;
							case "void":kindStr = "VOIDTK";  break;
							case "main":kindStr = "MAINTK";  break;
							case "printf": kindStr = "PRINTFTK"; break;
							case "if":kindStr = "IFTK"; break;
							case "else":kindStr = "ELSETK"; break;
							case "do":kindStr = "DOTK"; break;
							case "while":kindStr = "WHILETK"; break;
							case "for":kindStr = "FORTK"; break;
							case "scanf":kindStr = "SCANFTK"; break;
							case "break":kindStr = "BREAKTKTK";break;
							case "continue":kindStr = "COUNTINUETK";break;
							case "default":kindStr = "DEFAULTTK";break;
							case "double":kindStr = "DOUBLETK";break;
							default:
								kindStr = "IDENFR";
							}
							
						}
						else if(String.valueOf(inChars[num]).matches(digitFirst)) {
							str = getStrings(inChars,digitFollow,num);
							num+=str.length();
							kindStr = "INTCON";
							
						}
						else if(inChars[num] == '"') {
							num++;
							char[] strcon = new char[inChars.length] ;
							int j = 0;
							while(inChars[num] != '"') {
								strcon[j] = inChars[num];
								num++;
								j++;
							}
							num++;
							str = String.valueOf(strcon);
							str = str.trim();
							kindStr = "STRCON";
							
						}
						else if(String.valueOf(inChars[num])=="'"||String.valueOf(inChars[num]).equals("'")) {
							num++;
							str=String.valueOf(inChars[num]);
							kindStr = "CHARCON";
							num+=2;
						}
						else if(String.valueOf(inChars[num]).matches(scopeSymbol)||inChars[num]==']'||inChars[num]=='[') {
							str=Character.valueOf(inChars[num]).toString();
							num++;
							switch(str) {
							case "[":kindStr = "LBRACK";break;
							case "]":kindStr = "RBRACK";break;
							case "{":kindStr = "LBRACE";break;
							case "}":kindStr = "RBRACE";break;
							case "(":kindStr = "LPARENT";break;
							case ")":kindStr = "RPARENT";break;
							case ";":kindStr = "SEMICN";break;
							case ",":
								kindStr = "COMMA";break;
							default:
								kindStr = "UNKNOW";
							}
							
						}
						else {
							str = getStrings(inChars,operationSymbol,num);
							
							switch(str) {
							case "+": 
							case "++":
							case "+=":
								str ="+";kindStr = "PLUS";break;
							case "-":
							case "--":
							case "-=":
								str="-";kindStr = "MINU";break;
							case "*": kindStr = "MULT";break;
							case "/": kindStr = "DIV";break;
							case "/*": 
							case "//":
							case "*/": kindStr ="annotation";break;
							case ">": kindStr = "GRE";break;
							case "<": kindStr = "LSS";break;
							case "=":
							case "=+":
							case "=-":str="=";kindStr = "ASSIGN";break;
							case "==": kindStr = "EQL";break;
							
							case ">=": kindStr = "GEQ";break;
							case "<=": kindStr = "LEQ";break;
							case "!=": kindStr = "NEQ";break;
							
							default:
								kindStr="UNKNOW";
							}
							if(kindStr=="annotation") {
								break;
							}
							num+=str.length();
						}
					 if(str==null||str.equals("")) {
						 num++;
					 }
							//System.out.println(kindStr + " "+ str);
							output.write(kindStr + " "+ str);
							output.newLine(); 
					 
				 }
		}
		input.close();
		output.close();
	}
	/**
	 * 
	 * @param in
	 * @param follow   available char in the back of in[i] 
	 * @param i
	 * @return form in[i] get a effective String 
	 */
	
	public static String getStrings(char[] in, String follow, int i) {
		String inS = "";
		while(Character.valueOf(in[i]).toString().matches(follow)) {
			inS+=in[i];
			i++;
			while(i >= in.length) {
				return inS;
			}
		}
		return inS;
	}

}
