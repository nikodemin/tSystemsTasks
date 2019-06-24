package com.tsystems.javaschool.tasks.calculator;

import java.util.*;

public class Calculator
{
    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */


    public String evaluate(String statement)
    {
        if(statement == null)
            return null;

        String[] numsStr = statement.split("[\\(\\)\\-\\+\\*\\/]");
        List<String> operations = new ArrayList<>(Arrays.asList(statement.split("[0-9]+(\\.[0-9])*")));
        Deque<Double> nums = new ArrayDeque<>();
        try
        {
            for (String num : numsStr)
                nums.add(Double.parseDouble(num));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

        for (int i = 0; i < operations.size(); i++)
            if(operations.get(i).matches("[\t\n\r]"))
                operations.remove(i);
        for (int i = 0; i < operations.size(); i++)
        {
            operations.set(i,operations.get(i).trim());
            System.out.println(operations.get(i));
        }

        System.out.println(operations.size());

        Double result = nums.pollLast();
        for (int i = 0; i < operations.size(); i++)
        {
            Double operand = nums.pollLast();
            switch (operations.get(i))
            {
                case "+":
                    result+= operand;
                    break;
                case "-":
                    result-= operand;
                    break;
                case "*":
                    result*= operand;
                    break;
                case "/":
                    result/= operand;
                    break;
                case "(":
                    break;
                case ")":
                    break;
                default:
                    return null;
            }
        }
        System.out.println(result);
        return "5";
    }

}
