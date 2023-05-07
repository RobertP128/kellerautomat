import java.util.Scanner;

public class Main {

    private static double performOperator(double num1, double num2,char operator) throws Exception{
        switch (operator){
            case '+':
                return num1+num2;
            case '-':
                return num1-num2;
            case '*':
                return num1*num2;
            case '/':
                if (num2==0) throw new Exception("Division by 0");
                return num1/num2;
        }
        throw new Exception("Unknown Operator");
    }

    private static boolean isNumberic(char cmin1, char c,char cplus1){
        return (c >= '0' && c <= '9')||c=='.' || (c=='-' && !(cmin1>='0' && cmin1<='9') && (cplus1>='0' && cplus1<='9'));
    }


    public static void main(String[] args) {
        var scanner=new Scanner(System.in);
        String formel ="";
        do {
            System.out.print("Geben Sie eine vollständig geklammerte Formel ein [(q)uit]:");

            formel = scanner.next();

            double[] numberStack = new double[50];
            int numberStackpos = -1;
            char[] operantorStack = new char[50];
            int operatorStackpos = -1;

            String knownOperators = "+-*/";

            try {

                if (!formel.equals("q")) {

                    for (int pos = 0; pos < formel.length(); pos++) {
                        char c = formel.charAt(pos);
                        char cmin1 = (pos > 0) ? formel.charAt(pos - 1) : ' ';
                        char cplus1 = (pos < formel.length()-1) ? formel.charAt(pos+1) : ' ';
                        if (c == '(') {
                            continue;
                        } else if (isNumberic(cmin1, c,cplus1)) {
                            // check if end of string or next char is number or '.'
                            // if so then add to the current number string
                            // if not push the current numberString as  double
                            String tmpNumberStr = "";
                            do {
                                tmpNumberStr += formel.charAt(pos);
                                pos++;
                                cplus1 = (pos < formel.length()-1) ? formel.charAt(pos+1) : ' ';
                            } while (pos < formel.length() && isNumberic(formel.charAt(pos - 1), formel.charAt(pos),cplus1));
                            pos--;
                            numberStackpos++;
                            numberStack[numberStackpos] = Double.parseDouble(tmpNumberStr);
                        } else if (knownOperators.indexOf(c) >= 0) {
                            operatorStackpos++;
                            operantorStack[operatorStackpos] = c;
                        } else if (c == ')') {
                            // Calculate the result of the statck
                            while (operatorStackpos >= 0 && numberStackpos >= 0) {
                                // pull the operator
                                char op = operantorStack[operatorStackpos];
                                operatorStackpos--;
                                // pull 2x number
                                // perform operation
                                // push result
                                if (numberStackpos >= 1) {
                                    numberStack[numberStackpos - 1] = performOperator(numberStack[numberStackpos - 1], numberStack[numberStackpos], op);
                                    numberStackpos--;
                                }
                            }
                        }


                    }

                    // if no error then number[0] will store the result
                    System.out.println(numberStack[0]);
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        while (!formel.equals("q"));
        System.out.println("Vielen Dank!!");
    }
}