import java.io.DataInputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;

public class clientThread extends Thread {
	 private DataInputStream is = null;
	  private PrintStream os = null;
	  private Socket socket = null;
	  private final clientThread[] threads;
	  private int maxClientsCount;

	  public clientThread(Socket socket, clientThread[] threads) {
	    this.socket = socket;
	    this.threads = threads;
	    maxClientsCount = threads.length;
	  }

	  public void run() {
	    int maxClientsCount = this.maxClientsCount;
	    clientThread[] threads = this.threads;

	    try {

	      is = new DataInputStream(socket.getInputStream());
	      os = new PrintStream(socket.getOutputStream());
	      os.println("Enter your name.");
	      String username = is.readLine().trim();
	      os.println("Hello " + username + " to our chat room.");
	      for (int i = 0; i < maxClientsCount; i++) {
	        if (threads[i] != null && threads[i] != this) {
	          threads[i].os.println("A new user " + username + " entered the chat room");
	        }
	      }
	      while (true) {
	        String line = is.readLine();
	        if (line.startsWith("/quit")) {
	          break;
	        }
	        for (int i = 0; i < maxClientsCount; i++) {
	          if (threads[i] != null) {
	            threads[i].os.println(username + line);
	          }
	        }
	      }
	      for (int i = 0; i < maxClientsCount; i++) {
	        if (threads[i] != null && threads[i] != this) {
	          threads[i].os.println( username + " is leaving the chat room");
	        }
	      }
	      
	      os.println("Bye" + username);


	      for (int i = 0; i < maxClientsCount; i++) {
	        if (threads[i] == this) {
	          threads[i] = null;
	        }
	      }


	      is.close();
	      os.close();
	      socket.close();
	    } catch (IOException e) {
	    }
	  }
}
