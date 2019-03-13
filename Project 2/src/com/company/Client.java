package com.company;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Client
{
    public static void main(String[] args)
    {
        int length;
        Scanner in = new Scanner(System.in);
        String serverAddress;

        System.out.println("Enter IP you want to connect to or write localhost: ");
        serverAddress = in.next();
        in.reset();

        System.out.println("Enter the length of the sequence you want to knock: ");
        length = in.nextInt();
        in.reset();

        int[] listOfPorts = new int[length];

        System.out.println("Provide the ports for knocking: ");
        for(int i = 0; i < length; i++)
        {
            listOfPorts[i] = in.nextInt();
            in.reset();
        }

        System.out.print("Knocking ports: ");
        for(int i = 0; i < listOfPorts.length; i++)
            System.out.print(listOfPorts[i] + " ");
        System.out.print(" on address: " + serverAddress + "\n");


        DatagramSocket socket = null;
        try
        {
            socket = new DatagramSocket();

            String msg = "start";

            DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.length(), InetAddress.getByName(serverAddress), listOfPorts[0]);
            socket.send(packet);
            Thread.sleep(50);

            msg = "middle";

           for(int i = 1; i < listOfPorts.length - 1; i++)
           {
               packet = new DatagramPacket(msg.getBytes(), msg.length(), InetAddress.getByName(serverAddress), listOfPorts[i]);
               socket.send(packet);
               Thread.sleep(50);
           }

           msg = "last";

           packet = new DatagramPacket(msg.getBytes(), msg.length(), InetAddress.getByName(serverAddress), listOfPorts[listOfPorts.length - 1]);
           socket.send(packet);
           Thread.sleep(50);


           socket.setSoTimeout(5000);
           byte[] buffer = new byte[1000];

           packet = new DatagramPacket(buffer, 1000);

           socket.receive(packet);

           String[] s = new String(packet.getData()).split("\\.");

            //System.out.println(s[0]);
            System.out.println("Accepted, starting TCP connection with the server");

            ServerSocket TCPServer= new ServerSocket(0);

            Socket TCPSocket = new Socket(serverAddress, Integer.parseInt(s[0]));

            PrintWriter writer = new PrintWriter(TCPSocket.getOutputStream(), true);
            writer.println(TCPServer.getLocalPort());
            System.out.println("[TCP]: Request message has been sent to the server");
            TCPSocket.close();

            Socket response = TCPServer.accept();

            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getInputStream()));
            String message = reader.readLine();

            System.out.println("[TCP]: Response message: " + message);
            System.out.println("[TCP]: Connection finished");

            response.close();


           //socket.close();

        }catch(SocketTimeoutException e)
        {
            System.err.println("Time out, exiting the program");
            socket.close();
            System.exit(1);
        } catch(Exception e)
        {
            e.printStackTrace();
        }

    }
}
