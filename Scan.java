import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;


public class Scan {
	
	private static int charClass = -1;
	private static char []lexeme = new char[100] ;
	private static char nextChar;
	private static int lexLen = 0;
	private static int token = 0;
	private static int nextToken = 0;
	
	//Character classes
	private static final int LETTER = 0;
	private static final int DIGIT = 1;
	private static final int UNKNOWN = 99;
	private static final int EOF = 999;
	
	//Token Codes
	private static final int INT_LIT = 10;
	private static final int IDENT = 11;
	private static final int ASIGN_OP = 20;
	private static final int ADD_OP = 21;
	private static final int SUB_OP = 22;
	private static final int MULT_OP = 23;
	private static final int DIV_OP = 24;
	private static final int LEFT_PAREN = 25;
	private static final int RIGHT_PAREN = 26;
	
	private static BufferedReader br = null;
	

	public static void main(String[] args)throws IOException {
		
		 try{
			 br = new BufferedReader(new FileReader("front.in"));
		 } 
		
		catch(Exception e){
			 System.out.println("FILE NOT FOUND!");
			 System.out.println("Current working dir: " + System.getProperty("user.dir"));
			 System.exit(-1);
		 }
		 
		 
		getChar();
		 do{
		    lex();
		 }while(nextToken != EOF);
		 br.close();
	}
	
	
	public static void getChar(){
		int r;
		try{
			if((r = br.read()) != -1){
				nextChar = (char)r;
				if(Character.isAlphabetic(nextChar)){
					charClass = LETTER;
				}
				else if (Character.isDigit(nextChar)){
					charClass = DIGIT;
				}
				else {
					charClass = UNKNOWN;
				}
				
			}
			else {
			
				charClass = EOF;
				nextChar = '\0';
			}
		} catch(IOException ex) {
			ex.printStackTrace();
		}		
		
	}
	
	public static void addChar(){
		if(lexLen <= 98){
			lexeme[lexLen++] = nextChar;
			lexeme[lexLen] = 0;
		}
		else {
			System.out.println("Error: lexeme is too long");
		}
	}
	
	public static void getNoNBlank(){
		while(Character.isWhitespace(nextChar)){
			getChar();
		}
	}
	
	public static int lookup(char ch){
		switch(ch){
		case '(' : addChar();
			   nextToken = LEFT_PAREN;
			   break;
		case ')' : addChar();
			   nextToken = RIGHT_PAREN;
			   break;
		case '+' : addChar();
			   nextToken = ADD_OP;
			   break;
		case '-' : addChar();
			   nextToken = SUB_OP;
			   break;
		case '=' : addChar();
			   nextToken = ASIGN_OP;
			   break;
		case '*' : addChar();
			   nextToken = MULT_OP;
			   break;
		case '/' : addChar();
			   nextToken = DIV_OP;
			   break;
		default : addChar();
			  nextToken = EOF;
			  break;
		}
		return nextToken;
	}
	
	
	public static int lex(){
		lexLen = 0;
		lexeme = new char[100];
		getNoNBlank();
		switch(charClass){
		
		case LETTER : addChar();
			      getChar();
			      while(charClass == LETTER){
				addChar();
				getChar();
				}
			      nextToken = IDENT;
			      break;
		case DIGIT : addChar();
			     getChar();
			     while(charClass == DIGIT){
				addChar();
				getChar();
				}
			     nextToken = INT_LIT;
			     break;
		case UNKNOWN : lookup(nextChar);
			       getChar();
			       break;
		case EOF : nextToken = EOF;
			   lexeme[0] = 'E';
			   lexeme[1] = 'O';
			   lexeme[2] = 'F';
			   lexeme[3] = '\0';
					
		}
		
		System.out.println("Next token is: " + nextToken + " Next lexeme is " + new String(lexeme));
		return nextToken;
	}
	
	
}
