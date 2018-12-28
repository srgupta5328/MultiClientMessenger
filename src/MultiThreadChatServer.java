import java.io.DataInputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;


public class MultiThreadChatServer {
	  private static ServerSocket serverSocket = null;
	  private static Socket clientSocket = null;
	  private static final int maxClientsCount = 30;
	  private static final clientThread[] threads = new clientThread[maxClientsCount];

	  public static void main(String args[]) {


	    int portNumber = 5050;
	    if (args.length < 1) {
	      System.out .println("using port number=" + portNumber);
	    } else {
	      portNumber = Integer.valueOf(args[0]).intValue();
	    }

	    try {
	      serverSocket = new ServerSocket(portNumber);
	    } catch (IOException e) {
	      System.out.println(e);
	    }

	    while (true) {
	      try {
	        clientSocket = serverSocket.accept();
	        int k = 0;
	        for ( k = 0; k < maxClientsCount; k++) {
	          if (threads[k] == null) {
	            (threads[k] = new clientThread(clientSocket, threads)).start();
	            break;
	          }
	        }
	        if (k == maxClientsCount) {
	          PrintStream os = new PrintStream(clientSocket.getOutputStream());
	          os.println("Server is busy. Try later.");
	          os.close();
	          clientSocket.close();
	        }
	      } catch (IOException e) {
	        System.out.println(e);
	      }
	    }
	  }
}
