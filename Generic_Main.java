/* Karoline Brehm
 * Mtr.Nr. 117190
 * SS2017
 */

package algodat_1;
import java.util.Scanner;

public class Generic_Main {

	// returns priority of operator
	// (left parenthesis has lowest priority because it will always stay in stack
	// until matching rigth parenthesis is reached, which itself is never written
	// to stack or compared)
	public static int getPriority(char op)
	{
		switch (op)
		{
		case '(':
			return 1;
		case '+':
		case '-':
			return 2;
		case '*':
		case '/':
			return 3;
			
		default: return 100; //eventually add exception ?
		}
	}
	
	//returns queue with Infix read from command line
	public static Generic_Queue<Character> readInfix()
	{
		//read from input stream
		Scanner inputScanner = new Scanner(System.in);
		System.out.println("Please enter infix expression:");
		System.out.println("use only blank spacees, numbers and operators: +-*/()");
		String input = inputScanner.nextLine();
		inputScanner.close();
		
		//make queue containing infix expression with one character per node
		//and deleting possible blank space
		Generic_Queue<Character> infix = new Generic_Queue<Character>();
		for (int i = 0; i < input.length(); ++i)
		{
			char read = input.charAt(i);
			if(read != ' ')
			{
				infix.enqueue(read);
			}
		}
		return infix;
	}
	
	//requests infix input from user
	//converts infix expression queue to postfix expression using a stack
	//returns postfix as queue
	public static Generic_Queue<Character> conversion()
	{
		//Request infix input
		Generic_Queue<Character> infix = readInfix();
	
		//Conversion of expression:
		
		Generic_Queue<Character> postfix = new Generic_Queue<Character>();
		Generic_Stack<Character> conversion = new Generic_Stack<Character>();
		
		//Iteration over infix expression and dequeuing the nodes
		while(!infix.is_empty())
		{
			//look at next character in infix expression
			char next_char = infix.peek();

			//deal with it according to it's kind:

			//if it is an OPERATOR
			if (next_char == '+' || next_char == '-' || next_char == '*' ||	next_char == '/')
			{
				//while there are operators in stack to compare with
				//pop all operators in the stack that have greater or equal priority to new operator
				//and add them to the postfix expression
				while(!conversion.is_empty() && getPriority(conversion.peek()) >= getPriority(next_char))
				{
					postfix.enqueue(conversion.pop());
				}
				//then push operator to stack
				conversion.push(infix.dequeue());
			}
			
			//if it is a LEFT parenthesis ) -> push to stack
			else if (next_char == '('){
				conversion.push(infix.dequeue());
			}
			
			//if it is a RIGHT parenthesis ) -> means that a left parenthesis appeared some time ago
			else if (next_char == ')')
			{
				//pop operators from stack and enqueue them to postfix expression
				//until matching left parenthesis is found
				
				while(conversion.peek() != '(')
				{
					char x = conversion.pop();
					postfix.enqueue(x);
				}
				//the parenthesis are not needed in postfix expressions and therefore
				//popped/dequeued without beeing added to postfix expression
				conversion.pop();
				infix.dequeue();
			}
			
			//if it is an OPERAND enqueue operand
			else{
				postfix.enqueue(infix.dequeue());
			}
		}
		
		//when all characters in the infix expression are processed, add the
		//operators left on the stack to the queue
		while(!conversion.is_empty())
		{
			postfix.enqueue(conversion.pop());
		}
		
		//and return the postfix expression
		return postfix;
	}

	//takes postfix expression as queue
	//calculates the result in float using a stack, then rounds it to an int
	//returns rounded result
	public static int calculate(Generic_Queue<Character> postfix)
	{
		Generic_Stack<Float> calculate = new Generic_Stack<Float>();
		
		//
		while(!postfix.is_empty())
		{
			//look at next element in postfix expression
			char next_char = postfix.peek();

			//deal with it accordingly:
			
			//if it is an operator, calculate result of the next two operands in stack
			//by popping them from stack, calculating the result and pushing the result
			//back to the stack
			if (next_char == '+' || next_char == '-' || next_char == '*' ||	next_char == '/')
			{
				float y = calculate.pop();
				float x = calculate.pop();
				
				switch (next_char)
				{
				case '+':
					calculate.push(x + y);
					break;
				case '-':
					calculate.push(x - y);
					break;
				case '*':
					calculate.push(x * y);
					break;
				case '/':
					calculate.push(x / y);
					break;
				}			
				
				postfix.dequeue();
			}
			//if operand, push to stack
			else
			{
				calculate.push((float)Character.getNumericValue(postfix.dequeue()));
			}
		}
		
		//return result rounded to int
		return Math.round(calculate.pop());
	}

	public static void main(String args[]){
	
		//Calling conversion funktion
		Generic_Queue<Character> postfix = conversion();
		System.out.println("Postfix expression: " + postfix.to_string());
	
		//Calculation of result using postfix expression returned by
		//conversion function
		int result = calculate(postfix);
		System.out.println("Result: " + result);
	}
}
