/*
* Name: Evan Roubekas
* ID: V00891470
* Date: October 31rd, 2018
* Filename: ArithExpression.java
* Details: CSC115 Assignment 3
*/

/*
 * The shell of the class, to be completed as part of CSC115 Assignment 3 : Calculator.
 */

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ArithExpression extends StringStack {

	public TokenList postfixTokens;
	public TokenList infixTokens;

	/**
	 * Sets up a legal standard Arithmetic expression.
	 * The only parentheses accepted are "(" and ")".
	 * @param word An arithmetic expression in standard infix order.
	 * 	An invalid expression is not expressly checked for, but will not be
	 * 	successfully evaluated, when the <b>evaluate</b> method is called.
	 * @throws InvalidExpressionException if the expression cannot be properly parsed,
	 *  	or converted to a postfix expression.
	 */
	public ArithExpression(String word) {
		if (Tools.isBalancedBy("()",word)) {
			tokenizeInfix(word);
			infixToPostfix();
		} else {
			throw new InvalidExpressionException("Parentheses unbalanced");
		}
	}

	/*
	 * A private helper method that tokenizes a string by separating out
	 * any arithmetic operators or parens from the rest of the string.
	 * It does no error checking.
	 * The method makes use of Java Pattern matching and Regular expressions to
	 * isolate the operators and parentheses.
	 * The operands are assumed to be the substrings delimited by the operators and parentheses.
	 * The result is captured in the infixToken list, where each token is
	 * an operator, a paren or a operand.
	 * @param express The string that is assumed to be an arithmetic expression.
	 */
	private void tokenizeInfix(String express) {
		infixTokens  = new TokenList(express.length());

		// regular expression that looks for any operators or parentheses.
		Pattern opParenPattern = Pattern.compile("[-+*/^()]");
		Matcher opMatcher = opParenPattern.matcher(express);

		String matchedBit, nonMatchedBit;
		int lastNonMatchIndex = 0;
		String lastMatch = "";

		// find all occurrences of a matched substring
		while (opMatcher.find()) {
			matchedBit = opMatcher.group();
			// get the substring between matches
			nonMatchedBit = express.substring(lastNonMatchIndex, opMatcher.start());
			nonMatchedBit = nonMatchedBit.trim(); //removes outside whitespace
			// The very first '-' or a '-' that follows another operator is considered a negative sign
			if (matchedBit.charAt(0) == '-') {
				if (opMatcher.start() == 0 ||
					!lastMatch.equals(")") && nonMatchedBit.equals("")) {
					continue;  // ignore this match
				}
			}
			// nonMatchedBit can be empty when an operator follows a ')'
			if (nonMatchedBit.length() != 0) {
				infixTokens.append(nonMatchedBit);
			}
			lastNonMatchIndex = opMatcher.end();
			infixTokens.append(matchedBit);
			lastMatch = matchedBit;
		}
		// parse the final substring after the last operator or paren:
		if (lastNonMatchIndex < express.length()) {
			nonMatchedBit = express.substring(lastNonMatchIndex,express.length());
			nonMatchedBit = nonMatchedBit.trim();
			infixTokens.append(nonMatchedBit);
		}
	}

	/**
	 * Determines whether a single character string is an operator.
	 * The allowable operators are {+,-,*,/,^}.
	 * @param op The string in question.
	 * @return True if it is recognized as a an operator.
	 */
	public static boolean isOperator(String op) {
		switch(op) {
			case "+":
			case "-":
			case "/":
			case "*":
			case "^":
				return true;
			default:
				return false;
		}
	}

	/*
	 * NOTE TO STUDENT for infixToPostfix below...:
	 * You do not need to check that the infixTokens data field is a legitimate infix
	 * expression at this time.
	 * If, during the process, something unexpected happens, then throw an Exception, but it
	 * is okay for the postfixTokens to contain an invalid postfix expression.
	 * It is only when processing the public method 'evaluate', that any errors must be
	 * acknowledged.
	 */

	 /**
	 * A private method that initializes the postfixTokens data field.
	 * It takes the information from the infixTokens data field.
	 * If, during the process, it is determined that the expression is invalid,
	 * an InvalidExpressionException is thrown.
 	 * Note that since the only method that calls this method is the constructor,
	 * the Exception is propogated through the constructor.
	 */
	//=============================================================================================
	// infixToPostfix method takes the infix expression and converts it to a postfix expression
	public void infixToPostfix() {
		postfixTokens  = new TokenList();
		for(int i = 0; i < infixTokens.size(); i++){
			switch(infixTokens.get(i)){
				// LEFT PARENTHESIS -------------------------------------------------
				case "(":
					this.push(infixTokens.get(i));
					break;
				// RIGHT PARENTHESIS ------------------------------------------------
				case ")":
					while(this.head != null && !this.peek().equals("(")){
						postfixTokens.append(this.pop());
					}
					this.pop();
					break;
				// OPERATOR ---------------------------------------------------------
				case "+": case "-": case "*": case "/": case "^":
					if(this.isEmpty()){this.push(infixTokens.get(i));break;}
					while(!this.isEmpty() && !this.peek().equals("(") &&
							  precedence(infixTokens.get(i)) <= precedence(this.peek())){
								 postfixTokens.append(this.pop());
								}
						this.push(infixTokens.get(i));
						break;
				// OPERAND ----------------------------------------------------------
				default:
					postfixTokens.append(infixTokens.get(i));
					break;
			}
		}
		while(this.head != null){postfixTokens.append(this.pop());}
	}
	//=============================================================================================
	// Precedence method takes an operator and returns it's precedence from 1-3
	public int precedence(String i){
		switch(i){
			case "+": case "-":
				return 1;
			case "*": case "/":
				return 2;
			case "^":
				return 3;
			default:
				return -1;
		}
	}
	//=============================================================================================
	// getInfixExpression method converts the tokenized infix expression to a string
	public String getInfixExpression() {
	 	String infix = infixTokens.toString();
		return infix;
	}
	//=============================================================================================
	// getPostfixExpression method converts the tokenized postfix expression to a string
	public String getPostfixExpression() {
	 	String postfix = postfixTokens.toString();
		return postfix;
	}
	//=============================================================================================
 	// evaluate method returns the value of a arithmetic postfix expression
	public double evaluate() {
		double A, B;
		double fin = -1;
		double val = 0;

		try{
			for(int i = 0; i < postfixTokens.size(); i++){
				if(!isOperator(postfixTokens.get(i))){
					push(postfixTokens.get(i));
				} else {

					try{
						A = Double.parseDouble(this.pop());
						B = Double.parseDouble(this.pop());
					} catch(StackEmptyException e){
						throw new InvalidExpressionException("Stack is Empty! @evaluate");
					}

					switch(postfixTokens.get(i)){
						// EXPONENTIAL ---------------
						case "^":
						val = Math.pow(B,A);
						break;
						// MULTIPLICATION ------------
						case "*":
						val = B*A;
						break;
						// DIVISION ------------------
						case "/":
						val = B/A;
						break;
						// ADDITION ------------------
						case "+":
						val = B+A;
						break;
						// SUBTRACTION ---------------
						case "-":
						val = B-A;
						break;
					}
					push(String.valueOf(val));
				}
			}
			fin = Double.parseDouble(this.pop());

		} catch(NumberFormatException e){
			throw new InvalidExpressionException("Invalid. Cannot Evaluate!");
		}
			return fin;
	}
	//=============================================================================================
	// Used for testing
	public static void main(String[] args) {
	}

}
