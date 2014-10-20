package com.jetucker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class Driver
{
    private static File s_inputFile = null;
    private static File s_outputFile = null;

    private static double s_javaFailureProbability = 0.0;
    private static double s_cFailureProbability = 0.0;
    private static long s_timeLimit = 0;

    private static HeapSorter s_javaSorter = new HeapSorter();
    private static InsertionSorter s_cSorter = null;

    public static void main(String[] args)
    {
        if(GetInputParams(args))
        {
            // first delete the output file if it exists
            if(s_outputFile.exists() && s_outputFile.delete())
            {
                System.out.println("Deleted existing output file.");
            }

            // get input
            int[] input = GetInput();
            System.out.println("Starting Java sort...");
            if(TrySort(input, s_javaSorter, s_javaFailureProbability))
            {
                System.out.println("Java sort completed successfully");
                OutputResults(input);
            }
            else
            {
                System.out.println("Java sort failed, falling back to C sort");
                File lib = new File("libcom_jetucker_InsertionSorter.so");
                System.load(lib.getAbsolutePath());
                s_cSorter = new InsertionSorter();
                input = GetInput();
                if(TrySort(input, s_cSorter, s_cFailureProbability))
                {
                    System.out.println("C sort completed successfully");
                }
                else
                {
                    System.out.println("C sort failed.\n Sort operation failed!");
                    System.exit(-1);
                }
            }
        }
        else
        {
            System.out.println(GetHelp());
        }
    }

    private static boolean TrySort(int[] input, ISortVarient sorter, double failureProbability)
    {
        Adjudicator adjudicator = new Adjudicator();
        boolean result = false;
        FutureTask<int[]> cSortResults = new FutureTask<>(()->
        {
            return sorter.Sort(input);
        });

        Thread cSortThread = new Thread(cSortResults);
        try
        {
            cSortThread.start();
            cSortResults.get(s_timeLimit, TimeUnit.MILLISECONDS);
            result = adjudicator.CheckResults(input, sorter.GetMemoryAccesses(), failureProbability);
        }
        catch(TimeoutException ex)
        {
            System.err.println(sorter.GetName() + " timed out");
        }
        catch(InterruptedException ex)
        {
            System.err.println(sorter.GetName() + " was interrupted");
        }
        catch(ExecutionException ex)
        {
            System.err.println(sorter.GetName() + " failed : \t" + ex.getMessage());
        }

        if(cSortThread.isAlive())
        {
            cSortThread.stop();
        }

        return result;
    }

    private static void OutputResults(int[] results)
    {
        try
        {
            try(PrintWriter out = new PrintWriter(new FileWriter(s_outputFile)))
            {
                Arrays.stream(results).boxed().map((val) -> val.toString()).forEach((val) -> out.println(val));
            }
        }
        catch (IOException ex)
        {
            System.out.println("Failed to create file: " + s_outputFile.getName() + " \t " + ex.toString());
        }
    }

    private static int[] GetInput()
    {
        int[] results;

        try
        {
            results = Files.lines(s_inputFile.toPath()).mapToInt(Integer::parseInt).toArray();
        }
        catch (IOException ex)
        {
            throw new RuntimeException("Failed to read file: " + s_inputFile.getName() + " \t " + ex.getMessage(), ex);
        }

        return results;
    }

    private static boolean GetInputParams(String[] args)
    {
        boolean result = true;

        if(args.length >= 5)
        {
            String inputFileName = args[0];
            String outputFileName = args[1];

            s_inputFile = new File(inputFileName);
            s_outputFile = new File(outputFileName);

            if(!s_inputFile.exists())
            {
                System.out.println("Input file does not exist!");
                result = false;
            }

            try
            {
                s_javaFailureProbability = Double.parseDouble(args[2]);
                s_cFailureProbability = Double.parseDouble(args[3]);
                s_timeLimit = Long.parseLong(args[4]);
            }
            catch(NumberFormatException ex)
            {
                System.out.println("Input number formatted incorrectly! Make sure probabilities are doubles and timeout is an int!!");
                result = false;
            }
        }

        return result;
    }

    private static String GetHelp()
    {
        return "Parameters should be formatted as follows:\n " +
                "inputFileName outputFileName javaFailureProbability cFailureProbability timeout";
    }
}
