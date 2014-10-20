package com.jetucker;

import java.util.Arrays;
import java.util.stream.IntStream;

public final class HeapSorter implements ISortVarient
{
    private int[] m_heap = null;
    private int m_heapSize = 0;

    public String GetName()
    {
        return "Java Heap Sort";
    }

    public int[] Sort(int[] items)
    {
        m_heap = new int[items.length];
        IntStream intStream = Arrays.stream(items);

        // first take each element in the array and insert it into the heap
        intStream.forEach(this::InsertIntoHeap);

        // now remove each element from the heap placing it back into the array
        while(m_heapSize > 0)
        {
            items[m_heapSize - 1] = RemoveTopFromHeap();
        }

        m_heapSize = 0;
        m_heap = null;

        return items;
    }

    public int GetMemoryAccesses()
    {
        return 0;
    }

    private void swap(int i,int j)
    {
        int temp = m_heap[i];
        m_heap[i] = m_heap[j];
        m_heap[j] = temp;
    }

    private void InsertIntoHeap(int val)
    {
        // first insert at the end of the heap
        m_heap[m_heapSize] = val;
        ++m_heapSize;

        // now sift up
        int k = m_heapSize - 1;
        while(k > 0)
        {
            int p = (k-1)/2;
            int item = m_heap[k];
            int parent = m_heap[p];
            if(item > parent)
            {
                // then we move it up
                swap(k, p);
                k = p;
            }
            else
            {
                // final position found
                break;
            }
        }
    }

    private int RemoveTopFromHeap()
    {
        if(!(m_heapSize > 0))
        {
            throw new RuntimeException("No element to remove!");
        }

        int result = m_heap[0];

        // first swap the first element with the last element
        swap(0, m_heapSize - 1);
        --m_heapSize;

        // now sift down
        int k = 0;
        int lower = 2*k+1;
        while(lower < m_heapSize)
        {
            int max = lower;
            int r = lower + 1;
            if(r < m_heapSize)
            {
                // right child
                if(m_heap[r] > m_heap[lower])
                {
                    ++max;
                }
            }
            if(m_heap[k] < m_heap[max])
            {
                swap(k, max);
                k = max;
                lower = 2 * k + 1;
            }
            else
            {
                break;
            }
        }

        return result;
    }
}
