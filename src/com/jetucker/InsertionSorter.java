package com.jetucker;

public final class InsertionSorter implements ISortVarient
{
    public native int[] Sort(int[] input);
    public native int GetMemoryAccesses();

    public String GetName()
    {
        return "C InsertionSort";
    }
}
