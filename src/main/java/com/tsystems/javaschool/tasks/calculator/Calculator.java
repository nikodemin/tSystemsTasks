package com.tsystems.javaschool.tasks.calculator;

import java.text.DecimalFormat;
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
        if(statement == null || statement.contains(",") || statement.equals("")
        || statement.contains("..") || statement.contains("**") || statement.contains("//")
                || statement.contains("++") || statement.contains("--"))
            return null;

        //count parenthesis
        int openP = 0;
        int closeP = 0;
        Pattern pattern = Pattern.compile("\\(");
        Matcher matcher = pattern.matcher(statement);
        while (matcher.find())
        {
            ++openP;
        }
        pattern = Pattern.compile("\\)");
        matcher = pattern.matcher(statement);
        while (matcher.find())
        {
            ++closeP;
        }

        if(openP!=closeP)
            return null;

        statement = statement.replaceAll(" ","");

        pattern = Pattern.compile("\\((.+)\\)");
        matcher = pattern.matcher(statement);
        while (matcher.find())
        {
            String subQuery = matcher.group(1);
            System.out.println("OLD="+statement);
            statement = statement.replace("("+subQuery+")",evaluate(subQuery));
            System.out.println("NEW="+statement);
        }

        System.out.println("STATEMENT0="+statement);

        List<Double> nums = new ArrayList<>();
        pattern = Pattern.compile("-?[0-9]+\\.?[0-9]*");
        matcher = pattern.matcher(statement);
        while (matcher.find())
        {
            nums.add(Double.parseDouble(matcher.group(0)));
            System.out.println("NUM="+Double.parseDouble(matcher.group(0)));
        }

        //replace minuses between nums with pluses
        pattern = Pattern.compile("[0-9]+-[0-9]+");
        matcher = pattern.matcher(statement);
        while (matcher.find())
        {
            statement = statement.replaceFirst(matcher.group(0),
                    matcher.group(0).replaceFirst("-","+"));
        }
        System.out.println("STATEMENT1="+statement);

        List<String> operands = new ArrayList<>();
        pattern = Pattern.compile("[\\/\\*\\+]");
        matcher = pattern.matcher(statement);
        while (matcher.find())
        {
            operands.add(matcher.group(0));
            System.out.println("OPERAND="+matcher.group(0));
        }
        //handle calculations

        try
        {
            for (int i = 0; i < operands.size(); i++)
            {
                if (operands.get(i).equals("/"))
                {
                    if (nums.get(i + 1) == 0.0)
                        return null;
                    nums.set(i + 1, nums.get(i) / nums.get(i + 1));
                    nums.set(i, 0.0);
                } else if (operands.get(i).equals("*"))
                {
                    nums.set(i + 1, nums.get(i) * nums.get(i + 1));
                    nums.set(i, 0.0);
                }
            }
        }
        catch (IndexOutOfBoundsException e)
        {
            return null;
        }

        Double result = 0.0;
        for (Double d:nums)
        {
            System.out.println("SUM="+d);
            result+= d;
        }

        if(result%1==0)
            return String.format("%.0f",result);

        String strResult = String.format("%.4f",result);
        strResult = strResult.replace(',','.').replaceFirst("0+$","");
        return strResult;
    }

}
