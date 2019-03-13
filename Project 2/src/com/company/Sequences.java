package com.company;


import javafx.util.Pair;

import java.util.HashMap;
import java.util.LinkedList;

public class Sequences
{
    private HashMap<Source, LinkedList<Integer>> sequence;
    private LinkedList<Integer> correctSeq;

    Sequences(LinkedList<Integer> properSeq)
    {
        this.correctSeq = properSeq;
        sequence = new HashMap<>();
    }


    synchronized void add(Source source, int port)
    {
        sequence.computeIfAbsent(source, n -> new LinkedList<>());
        sequence.get(source).add(port);
    }

    synchronized boolean equals(Source source)
    {
        return sequence.get(source).equals(correctSeq);
    }

    synchronized void remove(Source source)
    {
        sequence.remove(source);
    }

    public HashMap<Source, LinkedList<Integer>> getSequence()
    {
        return sequence;
    }

    public LinkedList<Integer> getCorrectSeq()
    {
        return correctSeq;
    }
}
