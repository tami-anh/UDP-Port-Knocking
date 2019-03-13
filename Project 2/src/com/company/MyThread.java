package com.company;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;

public class MyThread extends Thread
{
    private int port;
    DatagramSocket socket;
    DatagramPacket packet;

    MyThread(int port)
    {
        this.port = port;
    }

    @Override
    public void run()
    {

        while(true)
        {
            try
            {
                socket = new DatagramSocket(port);

                byte[] buffer = new byte[1000];

                DatagramPacket packet = new DatagramPacket(buffer, 1000);
                socket.receive(packet);

                Source client = new Source(packet.getAddress().getHostAddress(), packet.getPort());

                String s = new String(packet.getData());

                if(s.matches("start.*"))
                {
                    System.out.println("Port: " + port + " received start message from: " + packet.getAddress().getHostAddress());
                    Server.sequences.remove(client);
                }

                Server.sequences.add(client, port);

                if(s.matches("middle.*"))
                {
                    System.out.println("Port: " + port + " received middle message from: " + packet.getAddress().getHostAddress());
                }

//                for(int i : Server.sequences.sequence.get(client))
//                {
//                    System.out.println(i + " ");
//                }

                if(s.matches("last.*"))
                {
                    System.out.println("Port: " + port + " received last message from: " + packet.getAddress().getHostAddress());

                    if(Server.sequences.equals(client))
                    {
                        System.out.println("Correct sequence confirmed from client: " + packet.getAddress().getHostAddress() + "!");
                        System.out.println("Creating TCP connection");

                        ServerSocket TCPServer = new ServerSocket(0);

                        String serverPort = Integer.toString(TCPServer.getLocalPort()) + ".";

                        buffer = serverPort.getBytes();

                        packet = new DatagramPacket(buffer, buffer.length, packet.getAddress(), packet.getPort());

                        socket.send(packet);

                        String address = packet.getAddress().getHostAddress();      //lambda issues

                        new Thread(() -> {
                            Socket TCPSocket;
                            try
                            {
                                TCPSocket = TCPServer.accept();

                                BufferedReader reader = new BufferedReader(new InputStreamReader(TCPSocket.getInputStream()));

                                String message = reader.readLine();
                                System.out.println("[TCP]: Received a request message from a client");

                                Socket response = new Socket(address, Integer.parseInt(message));

                                PrintWriter writer = new PrintWriter(response.getOutputStream(), true);
                                writer.println("Hello from the server");

                                System.out.println("[TCP]: Response has been sent");

                                response.close();

                                TCPServer.close();


                            }catch(Exception e)
                            {
                                e.printStackTrace();
                            }
                        }).start();
                    }
                }

                socket.close();

            } catch (Exception e)
            {
                System.err.println("Can not create a socket");
            }

            //System.out.println("Port: " + port + " received " + new String(packet.getData()) + " message from: " + packet.getAddress().getHostAddress());
        }
    }
}
