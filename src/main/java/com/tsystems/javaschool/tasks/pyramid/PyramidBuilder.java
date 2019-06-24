package com.tsystems.javaschool.tasks.pyramid;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class PyramidBuilder
{
    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */
    public int[][] buildPyramid(List<Integer> inputNumbers)
    {
        List<List<Integer>> pyrmidList = new ArrayList<>();
        try
        {
            inputNumbers = inputNumbers.stream().sorted().collect(Collectors.toList());

            int quantity = 0;
            for (int i = 0; i < inputNumbers.size(); i += quantity)
            {
                ++quantity;
                List<Integer> list = new ArrayList<>();
                for (int j = i; j < i + quantity; j++)  //range in which next line is contained
                {
                    list.add(inputNumbers.get(j));
                }
                pyrmidList.add(list);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new CannotBuildPyramidException();
        }

        int maxSize = pyrmidList.get(pyrmidList.size()-1).size();
        maxSize += maxSize-1;
        int[][] result = new int[pyrmidList.size()][maxSize];

        if (pyrmidList.get(0)!=null && pyrmidList.get(1)!=null)
            if(pyrmidList.get(0).size()!= pyrmidList.get(1).size()-1)
                throw new CannotBuildPyramidException();

        for (int i = 0; i < pyrmidList.size(); i++)
        {
            Deque<Integer> deque = new ArrayDeque<>();
            for (Integer e:pyrmidList.get(i))
            {
                deque.add(e);
                deque.add(0);
            }
            deque.removeLast();

            int quantityToAdd = pyrmidList.get(pyrmidList.size()-1).size();
            quantityToAdd -= i;
            quantityToAdd += quantityToAdd-1;   //last row with zeroes
            quantityToAdd /= 2;

            for (int j = 0; j < quantityToAdd; j++)
            {
                deque.addFirst(0);
                deque.addLast(0);
            }

            int j = 0;
            for (Integer el:deque)
            {
                result[i][j++] = el;
            }
        }

        printPyramid(result);
        return result;
    }

    private void printPyramid(int[][] p)
    {
        for (int i = 0; i < p.length; i++)
        {
            for (int j = 0; j < p[i].length ; j++)
            {
                System.out.print(p[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
