# UDP-Port-Knocking
Program with UDP and TCP connection

The program is written in Java SE with version JDK 1.8. 
-	By running the Server class we are asked to set the number of UDP packets to receive, and the correct order of receiving them.
-	All the chosen ports are running simultaneously through objects of MyThread class which makes it able to listen at the same time.
-	Client class represents the user which sets a number of ports he would like to knock and starts sending the packets to those ports.
-	If packets sent by a client to the ports are correct with the sequence of ports set by a server. The TCP connection starts between them.

Example of executing the program

At the beginning we run the server and set the number of packets to be received.
After that we set the correct order of ports which should be knocked.
We got the confirmation that all the ports we want to use are opened and ready to receive packets.
Now we run the application as a client, and we have to provide the IP with which we want to connect (in our case localhost), the number packets we would like to send  and the proper sequence of ports to knock.
The server shows the information of packets he receives, on what port and also who is the sender (we assume that packets are not lost in the network).
If the sequence of packets is correct then server informs that the TCP connection is being created with the given user.
The client gets informed that his sequence is accepted and the TCP connection starts.
After establishing the simple request-response type communication is made, and the program terminates.
