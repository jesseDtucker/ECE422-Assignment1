package com.jetucker;

public class Adjudicator
{
    public boolean CheckResults(int[] sortedList, int memoryAccesses, double probabilityOfFailure)
    {
        boolean result = true;
        result = AcceptanceTest(sortedList);
        if(result)
        {
            // TODO::JT need to do probablity of failure
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
