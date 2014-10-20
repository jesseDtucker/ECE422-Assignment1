package com.jetucker;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class Generator
{
    private static String GetHelp()
    {
        return "Please enter in a filename as the first parameter and an integer as the second value!";
    }

    private static void GenerateNumbers(String file, long count)
    {
        // stomp the existing file if it exists
        try
        {
            try(PrintWriter out = new PrintWriter(new FileWriter(file)))
            {
                Random rand = new Random();
                rand.ints(count).forEach(i -> out.println(i));
            }
        }
        catch (IOException ex)
        {
            System.out.println("Failed to create file: " + file + " \t " + ex.toString());
        }
    }

    public static void main(String[] args)
    {
        if(args.length >= 2)
        {
            String filename = args[0];
            try
            {
                long valCount = Long.parseLong(args[1]);
                GenerateNumbers(filename, valCount);
            }
            catch(NumberFormatException ex)
            {
                System.out.println(GetHelp());
            }
        }
        else
        {
            System.out.println(GetHelp());
        }
    }
}
