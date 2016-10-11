
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Guide {
	public static void main(String[] args) {
		// chuyen chuoi String thanh ArrayList cac tokens
		String expression = "1*(5-2)/3-(3*3-7)";

		// chuyen ArrayList cac tokens o infix thanh 1 ArrayList cac token o thu
		// tu postFix
		List<String> postfix = convertInfixToPostfix(getTokens(expression));
		System.out.println(postfix.toString());
		System.out.println(evaluate(postfix));
		// evaluate postfix
	}

	public static List<String> getTokens(String expression) {
		// them khoang trang giua cac tokens
		expression = expression.replace("+", " + ");
		expression = expression.replace("-", " - ");
		expression = expression.replace("/", " / ");
		expression = expression.replace("*", " * ");
		expression = expression.replace("(", " ( ");
		expression = expression.replace(")", " ) ");

		// tach chuoi thanh cac tokens
		String[] arrTokens = expression.split("\\s+");

		return Arrays.asList(arrTokens);
	}

	public static List<String> convertInfixToPostfix(List<String> infix) {
		List<String> postfix = new ArrayList<String>();
		Stack<String> stackOperator = new Stack<String>();

		for (String token : infix) {
			if (token.equals("(")) {
				stackOperator.push(token);
			} else if (token.equals(")")) {
				// Nếu là dấu đóng ngoặc “)”: lấy các toán tử trong stack ra và
				// cho vào output cho đến khi gặp dấu mở ngoặc “(“. (Dấu mở
				// ngoặc cũng phải được đưa ra khỏi stack)

				while (!stackOperator.isEmpty() && !stackOperator.peek().equals("(")) {
					postfix.add(stackOperator.pop());
				}
				stackOperator.pop();
			} else if (isOperator(token)) {
				// Chừng nào ở đỉnh stack là toán tử và toán tử đó có độ ưu tiên
				// lớn hơn hoặc bằng toán tử hiện tại thì lấy toán tử đó ra khỏi
				// stack và cho ra output.
				while (!stackOperator.isEmpty() && isOperator(stackOperator.peek())
						&& compareOperators(stackOperator.peek(), token) >= 0) {
					postfix.add(stackOperator.pop());
				}
				// Đưa toán tử hiện tại vào stack
				stackOperator.push(token);
			} else {
				postfix.add(token);
			}
		}
		while (!stackOperator.isEmpty()) {
			postfix.add(stackOperator.pop());
		}
		return postfix;
	}

	public static boolean isOperator(String token) {
		if (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/")) {
			return true;
		}
		return false;
	}

	public static int compareOperators(String operator1, String operator2) {
		if (operator1.equals("+") || operator1.equals("-")) {
			if (operator2.equals("+") || operator2.equals("-")) {
				return 0;
			} else
				return -1;
		} else {
			if (operator2.equals("+") || operator2.equals("-")) {
				return 1;
			} else
				return 0;
		}
	}

	public static double evaluate(List<String> postfix) {
		String operator = "+-*/";
		Stack<Double> stackOperand = new Stack<Double>();
		// Lặp qua các token của của biểu thức postfix từ trái qua phải:
		for (String token : postfix) {
			// Nếu là toán hạng: push vào stack
			if (!operator.contains(token)) {
				double num = Double.parseDouble(token);
				stackOperand.push(num);
			}
			// Nếu là toán tử: pop hai toán hạng trong stack ra và tính giá trị
			// của
			// chúng dựa vào toán tử này. Push kết quả đó lại vào stack.

			else {
				double num1 = stackOperand.pop();
				double num2 = stackOperand.pop();
				stackOperand.push(calculate(token, num1, num2));

			}

		}
		// Phần tử còn sót lại trong stack sau vòng lặp chính là kết quả của
		// biểu thức.
		return stackOperand.pop();
	}

	private static double calculate(String operator, double num1, double num2) {
		if (operator.equals("+")) {
			return num1 + num2;
		} else if (operator.equals("-")) {
			return num1 - num2;
		} else if (operator.equals("*")) {
			return num1 * num2;
		} else {
			return num1 / num2;
		}

	}
}
