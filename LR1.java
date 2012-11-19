/* LR 1 Parser - Assig4
 * Gino P. Corrales-Delgado
 * IT 327
 * November 14, 2012
 *
 * Assign 4 - LR(1) Parser - Takes a String to be evaluated if the expression
 *  is validunder the following parsing rules:
 * 
 * Grammar: 
 * E -> E + T  | T 
 * T -> T * F  | F
 * F -> ( E )  | id
 */

import java.util.Stack;
import java.util.StringTokenizer;
import java.io.IOException;

public class LR1 
{ 
  static StringTokenizer sTokenizer;
  static String ExpressionLeft;
  static String currentString;
  static Stack<String> stackSymbol = new Stack<String>();
  static Stack<Integer> stackState = new Stack<Integer>();
	
  // Setting the String to current to the next token also
  // evaluates if is an integer
  static void next()
  {
	currentString = sTokenizer.nextToken().intern();
	if(currentString == "+"){}
      else if(currentString == "*"){}
	else if(currentString == "("){}
	else if(currentString == ")"){}
	else if(currentString == "$"){}
	else if(Integer.parseInt(currentString) < 32767)
			currentString = "n";
  }
	
  static String input()
  {	
	ExpressionLeft = "";
	while(currentString != "$")
	{		
	  ExpressionLeft += currentString;
	  next();
	}
	ExpressionLeft += "$";
	return ExpressionLeft;
  }
	
  // shifts current state and removes the front Of the expression
  static void shift(int nextState)
  {
    stackSymbol.push("" + ExpressionLeft.charAt(0));
    stackState.push(nextState);
    ExpressionLeft = ExpressionLeft.substring(1); 
  }
	
  // LR(1) parsing begins
  static void LRParser()
  {
	stackSymbol.push("-");
	stackState.push(0);
	int resume = 1;
	while(resume == 1)
	{
		int stateOfStack = stackState.peek();
		char frontOfExpression = ExpressionLeft.charAt(0);
		switch(frontOfExpression)
		{
			case 'n':
			{
				if(stateOfStack == 0 || stateOfStack == 4 || stateOfStack == 6 || stateOfStack == 7)
					shift(5);
				else InvalidString();
				break;
			}
			case '+':
			{
				if(stateOfStack == 1 || stateOfStack == 8) shift(6);
				else if(stateOfStack == 2) ReductionHandler(2);
				else if(stateOfStack == 3) ReductionHandler(4);
				else if(stateOfStack == 5) ReductionHandler(6);
				else if(stateOfStack == 9) ReductionHandler(1);
				else if(stateOfStack == 10) ReductionHandler(3);
				else if(stateOfStack == 11) ReductionHandler(5);
				else InvalidString();
				break;
			}
			case '*':
			{
				if(stateOfStack == 2 || stateOfStack == 9) shift(7);					else if(stateOfStack == 3) ReductionHandler(4);
				else if(stateOfStack == 5) ReductionHandler(6);
				else if(stateOfStack == 10) ReductionHandler(3);
				else if(stateOfStack == 11) ReductionHandler(5);
				else InvalidString();
				break;
			}
			case '(':
			{
				if(stateOfStack == 0 || stateOfStack == 4 || stateOfStack == 6 || stateOfStack == 7)
					shift(4);
				else InvalidString();
				break;
			}
			case ')':
			{
				if(stateOfStack == 8) shift(11);
				else if(stateOfStack == 2) ReductionHandler(2);
				else if(stateOfStack == 3) ReductionHandler(4);
				else if(stateOfStack == 5) ReductionHandler(6);
				else if(stateOfStack == 9) ReductionHandler(1);
				else if(stateOfStack == 10) ReductionHandler(3);
				else if(stateOfStack == 11) ReductionHandler(5);
				else InvalidString();
				break;
			}
			case '$':
			{
				if(stateOfStack == 1){
			    	    	System.out.println("Good! The expression you entered is TOTALLY VALID! =) ");
					resume = 0;
				}
				else if(stateOfStack == 2) ReductionHandler(2);
				else if(stateOfStack == 3) ReductionHandler(4);
				else if(stateOfStack == 5) ReductionHandler(6);
				else if(stateOfStack == 9) ReductionHandler(1);
				else if(stateOfStack == 10) ReductionHandler(3);
				else if(stateOfStack == 11) ReductionHandler(5);
				else InvalidString();
				break;
			}
		}
		if(resume != 0)
     		{												System.out.print("["+stackSymbol.elementAt(0)+":"+stackState.elementAt(0)+"]");
		for(int i = 1; i < stackSymbol.size(); i++)
		{
			System.out.print("["+stackSymbol.elementAt(i)+":"+stackState.elementAt(i)+"]");
		}
		System.out.print("   " + ExpressionLeft + "\n");
	    }
	  }
	}
	
  static void InvalidString()
  {
	System.out.println("Sorry, the expression you entered is NOT VALID! =(");
	System.exit(-1);
  }

 // Function that handle reductions
 static void ReductionHandler(int rule)
 {
	switch(rule)
	{
		case 1:
		{
			//reduce E -> E+T
			stackSymbol.pop();
			stackSymbol.pop();
			stackState.pop();
			stackState.pop();
			if(stackState.peek() == 0)
        		{
				stackSymbol.push("E");
				stackState.push(1); 
			}
			else if(stackState.peek() == 4)
        		{
				stackSymbol.push("E");
				stackState.push(8);
			}
			break;
		}
		case 2:
		{
			//reduce E -> T
			stackSymbol.pop(); 
			stackState.pop();
			if(stackState.peek() == 0)
        		{
				stackSymbol.push("E");
				stackState.push(1);
			}
			else if(stackState.peek() == 4)
        		{
				stackSymbol.push("E");
				stackState.push(8); 
			}
			break;
		}
		case 3:
		{
			//reduce T -> T*F
			stackSymbol.pop();
			stackSymbol.pop();
			stackState.pop();
			stackState.pop();
			if(stackState.peek() == 0 || stackState.peek() == 4)
       		{
				stackSymbol.push("T");
				stackState.push(2);
			}
			else if(stackState.peek() == 6)
        		{
				stackSymbol.push("T");
				stackState.push(9);
			}
			break;
		}
		case 4:
		{
			//reduce T -> F
			stackSymbol.pop();
			stackState.pop();
			if(stackState.peek() == 0 || stackState.peek() == 4)
        		{
				stackSymbol.push("T");
				stackState.push(2);
			}
			else if(stackState.peek() == 6)
        		{
				stackSymbol.push("T");
				stackState.push(9);
			}
			break;
		}
		case 5:
		{
			//reduce F -> (E)
			stackSymbol.pop();
			stackSymbol.pop();
			stackSymbol.pop();
			stackState.pop();
			stackState.pop();
			stackState.pop();
			if(stackState.peek() == 0 || stackState.peek() == 4 || stackState.peek() == 6)
       	 	{
				stackSymbol.push("F");
				stackState.push(3);
			}
			else if(stackState.peek() == 7)
        		{
				stackSymbol.push("F");
				stackState.push(10);
			}
			break;
		}
		case 6:
		{
			//reduce F -> n
			stackSymbol.pop();
			stackState.pop();
			if(stackState.peek() == 0 || stackState.peek() == 4 || stackState.peek() == 6)
       		{
				stackSymbol.push("F");
				stackState.push(3);
			}
			else if(stackState.peek() == 7)
      	 	{
				stackSymbol.push("F");
				stackState.push(10);
			}
			break;
		}
	}
}

public static void main(String[] args) throws IOException 
{
	// input string
	String str = args[0];
	String aString = "";
      // separate the string in tokens
	int index = 0;	
	while(index < str.length())
	{
		if(str.charAt(index) == '+' || str.charAt(index) == '*' || str.charAt(index) == '(' || str.charAt(index) == ')' )
			aString += " " + str.charAt(index) + " ";
		else if(str.charAt(index) < 32767)
			aString += str.charAt(index);
		index++;
	}

      // tokenizing the input string
	sTokenizer = new StringTokenizer(aString + " $");	
  	next();
  	input();
  	LRParser();
     System.out.println("End Of the Program!");
  }
}
