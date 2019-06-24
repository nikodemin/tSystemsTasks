package com.tsystems.javaschool.tasks.subsequence;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Subsequence {

    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */
    @SuppressWarnings("rawtypes")
    public boolean find(List x, List y)
    {
        if(x==null || y==null)
            throw new IllegalArgumentException();
        if(x.size()!=0 && y.size()==0)
            return false;

        int yIter = 0;
        for (Object xEl:x)
        {
            while (!y.get(yIter).equals(xEl))
            {
                ++yIter;
                if (yIter > y.size() - 1)
                    return false;
            }
            if(yIter == y.size()-1)
                return false;
        }
        return true;
    }
}
