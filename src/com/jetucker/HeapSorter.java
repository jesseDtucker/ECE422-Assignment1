package com.jetucker;

import java.util.Arrays;
import java.util.stream.IntStream;

public final class HeapSorter implements ISortVarient
{
    private int[] m_heap = null;
    private int m_heapSize = 0;
    private int m_memoryAccesses = 0;

    public String GetName()
    {
        return "Java Heap Sort";
    }

    public int[] Sort(int[] items)
    {
        m_heap = new int[items.length];
        IntStream intStream = Arrays.stream(items);
        m_memoryAccesses += 3;

        // first take each element in the array and insert it into the heap
        intStream.forEach(this::InsertIntoHeap);
        m_memoryAccesses += m_heapSize;

        // now remove each element from the heap placing it back into the array
        m_memoryAccesses += m_heapSize;
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
        return m_memoryAccesses;
    }

    private void swap(int i,int j)
    {
        int temp = m_heap[i];
        m_heap[i] = m_heap[j];
        m_heap[j] = temp;

        m_memoryAccesses += 10;
    }

    private void InsertIntoHeap(int val)
    {
        // first insert at the end of the heap
        m_heap[m_heapSize] = val;
        ++m_heapSize;

        m_memoryAccesses += 4;

        // now sift up
        int k = m_heapSize - 1;
        m_memoryAccesses += 2;
        while(k > 0)
        {
            int p = (k-1)/2;
            int item = m_heap[k];
            int parent = m_heap[p];
            m_memoryAccesses += 10;
            if(item > parent)
            {
                // then we move it up
                swap(k, p);
                k = p;
                m_memoryAccesses += 1;
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
        m_memoryAccesses += 6;
        while(lower < m_heapSize)
        {
            int max = lower;
            int r = lower + 1;
            m_memoryAccesses += 6;
            if(r < m_heapSize)
            {
                // right child
                m_memoryAccesses += 3;
                if(m_heap[r] > m_heap[lower])
                {
                    m_memoryAccesses += 1;
                    ++max;
                }
            }
            m_memoryAccesses += 3;
            if(m_heap[k] < m_heap[max])
            {
                swap(k, max);
                k = max;
                lower = 2 * k + 1;
                m_memoryAccesses += 3;
            }
            else
            {
                break;
            }
        }

        return result;
    }
}
