import java.util.Stack;
public class LL1 
{
	
	static Stack<String> myStack = new Stack<String>();
	public boolean E(String token)
	{
		
		if(isInteger(token))
		{	
			if(T(token))
				newE(token);
			
		}
		switch(token)
		{		
			case "+": return false;
				
				
			case "*": return false;
				
		
			case "(": token = myStack.pop();
						if(T(token))
							if(newE(token))
								return true;
				
			case ")": return false;
				
		
			case "$": return false;
				
		
		}
			return true;
		
	}
	
	public boolean newE(String token)
	{
		
		if(isInteger(token))
			return false;
			
		
		switch(token)
		{
			
			case "+": myStack.push("+");
					  if(T(token))
						if(newE(token))
							return true;
					
			case "*": return false;
					
			case "(": return false;
					
			case ")": myStack.pop();
					  break;
				
			case "$": myStack.pop();
					  break;
		
		}
			return true;
	}
	
	public boolean T(String token)
	{
		if(isInteger(token))
		{
				if(F(token));
					newT(token);
		}
		
		switch(token)
		{		  
			case "+": return false;
			
			case "*": return false;
			
			case "(": 	token = myStack.pop();
						if(F(token))
							if(newT(token))
								return true;
					  
			case ")": return false;
			
			case "$": return false;
					  
		}
		
			return true;
	}
	
	public boolean newT(String token)
	{
		
		if(isInteger(token))
			return false;
			
		switch(token)
		{
					  
			case "+": myStack.pop();
					  break;
			
			case "*": myStack.push("*");
					  if(F(token))	
						  if(newT(token))
							  return true;
			
			case "(": return false;
					  
			case ")": myStack.pop();
					  break;
			
			case "$": myStack.pop();
					  break;
					
		}
			return true;

  }
  
  public boolean F(String token)
  {
	  
	  if(isInteger(token))
	  {
		  myStack.push(token);
		  return true;
	  }
			
		switch(token)
		{		  
			case "+": return false;
			
			case "*": return false;
			
			case "(": token = myStack.pop();
					  	if(E(token))
					  		myStack.push(")");
					  
			case ")": return false;
			
			case "$": return false;
					
		}
		
		return true;
  }
  
  public boolean isInteger(String token)
  {
	  try
		{
			Integer.parseInt(token);
			return true;
		}
	  	catch(Exception e)
	  	{
	  		return false;
	  	}
  }
  
  public static void main(String [] args)
  {
	  LL1 test = new LL1();
	  String input = "123+((23+2*3)))+3";
	  String [] result = input.split("(?!^)");
	  for(int i = 0; i<result.length; i++)
		  myStack.push(result[i]);

	  for(int i=0; i<result.length; i++)
	  {
		  if(test.E(myStack.pop()))
		  	System.out.println(result[i] + " is okay!");
		  else
			  System.out.println(result[i] + " is incorrect!");
	  }
  }
	
}