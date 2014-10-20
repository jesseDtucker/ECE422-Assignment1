package com.jetucker;

import java.util.Random;

public class Adjudicator
{
    public boolean CheckResults(int[] sortedList, int memoryAccesses, double probabilityOfFailure)
    {
        boolean result = true;
        result = AcceptanceTest(sortedList);
        if(result)
        {
            double testNum = memoryAccesses * probabilityOfFailure;
            double random = (new Random()).nextDouble();
            result = !(random > 0.5 && random < (testNum + 0.5));
        }
        return result;
    }

    private boolean AcceptanceTest(int[] sortedList)
    {
        boolean result = true;
        int prevVal = sortedList[0];
        for(int val : sortedList)
        {
            if(val < prevVal)
            {
                result = false;
                break;
            }
        }

        return result;
    }
}
