package com.company;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Server
{
    static Sequences sequences;
    static String myIP;

    public static void main(String[] args)
    {
	    int length;

	    Scanner in = new Scanner(System.in);

        System.out.println("Provide the length of the sequence: ");

        length = in.nextInt();
        in.reset();

        LinkedList<Integer> seqOfPorts = new LinkedList<>();
        System.out.println("Set a proper port sequence: ");
        for(int i = 0; i < length; i++)
        {
            seqOfPorts.add(in.nextInt());
            in.reset();
        }

        try
        {
            myIP = InetAddress.getLocalHost().getHostAddress();
        }/* catch(SocketException e)
        {
            System.err.println("Busy");
            System.exit(1);
        }*/ catch (UnknownHostException e)
        {
            System.err.println("Unable to connect");
        }

        System.out.println("Opening ports on: " + seqOfPorts.stream().distinct().collect(Collectors.toList()).toString());      //Showing which ports we have oppened

        sequences = new Sequences(seqOfPorts);

        for(int i : seqOfPorts.stream().distinct().collect(Collectors.toList()))          //Each unique port will be listening
        {
            new MyThread(i).start();
        }

        //list.stream().distinct().collect(Collectors.toList()).forEach(n->new MyThread(n).start());
    }
}
