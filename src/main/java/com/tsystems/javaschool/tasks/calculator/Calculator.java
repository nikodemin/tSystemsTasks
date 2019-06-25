package com.tsystems.javaschool.tasks.calculator;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        statement = statement.replace(" ","");

        Pattern pattern = Pattern.compile("\\((.+)\\)");
        Matcher matcher = pattern.matcher(statement);
        while (matcher.find())
        {
            String subQuery = matcher.group(1);
            System.out.println("OLD="+statement);
            statement = statement.replace("("+subQuery+")",evaluate(subQuery));
            System.out.println("NEW="+statement);
        }

        System.out.println("STATEMENT0="+statement);
        List<String> nums = new ArrayList<>();
        pattern = Pattern.compile("-?[0-9]+");
        matcher = pattern.matcher(statement);
        while (matcher.find())
        {
            nums.add(matcher.group(0));
            System.out.println("MATCHER="+matcher.group(0));
        }
        if(nums.size()>2)
        {
            //division
            statement = evalSubQuery(statement, "-?[0-9]+\\/[0-9]+");
            //multiplication
            statement = evalSubQuery(statement, "-?[0-9]+\\*[0-9]+");
            //substract
            statement = evalSubQuery(statement, "-?[0-9]+\\-[0-9]+");
            //addition
            statement = evalSubQuery(statement, "-?[0-9]+\\+[0-9]+");
        }

        //here statement will be like 4+3 or 1/3
        System.out.println("STATEMENT="+statement);
        System.out.println(statement.split("[\\/\\*\\+\\-]").length);
        if (statement.split("[\\/\\*\\+\\-]").length==1)
            return statement;
        Double leftOp = Double.parseDouble(nums.get(0));
        Double rigthOp = Double.parseDouble(nums.get(1));
        String operator;
        Double result = leftOp;

        pattern = Pattern.compile("[0-9]+([\\*\\/\\+\\-])[0-9]+");
        matcher = pattern.matcher(statement);
        if (matcher.find())
            operator = matcher.group(1);
        else
            return null;
        System.out.println("OPERATOR="+operator);
        System.out.println("left="+leftOp);
        System.out.println("rigth="+rigthOp);
        switch (operator)
        {
            case "/":
                result/= rigthOp;
                break;
            case "*":
                result*= rigthOp;
                break;
            case "+":
                result+= rigthOp;
                break;
            case "-":
                result+= rigthOp;
                break;
        }


        if(result%1==0)
            return String.format("%.0f",result);
        return String.format("%.4f",result);
    }

    private String evalSubQuery(String statement, String regexp)
    {
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(statement);
        while (matcher.find())
        {
            String subQuery = matcher.group(0);
            System.out.println("OLD2="+statement);
            statement = statement.replace(subQuery,evaluate(subQuery));
            System.out.println("NEW2="+statement);
        }
        return statement;
    }

}
