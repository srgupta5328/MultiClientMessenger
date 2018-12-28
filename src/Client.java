import java.io.DataInputStream;
import java.io.PrintStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
  public static void main(String[] args) {

    Socket socket = null;
    DataInputStream is = null;
    PrintStream os = null;
    DataInputStream inputLine = null;

    try {
      socket = new Socket("localhost", 5050);
      
      os = new PrintStream(socket.getOutputStream());
      is = new DataInputStream(socket.getInputStream());
      inputLine = new DataInputStream(new BufferedInputStream(System.in));
      
    }
    
    catch (IOException e) {
      System.out.println("Rohan fix this stuff NOW");
    }


    if (socket != null && os != null && is != null) {
      try {

        System.out.println("The client started. To quit it type 'quit'.");
        String response;
        os.println(inputLine.readLine());
        while ((response = is.readLine()) != null) {
          System.out.println(response);
          if (response.indexOf("quit") != -1) {
            break;
          }
          
          os.println(inputLine.readLine());
        }

        os.close();
        is.close();
        socket.close();
        
      } 

      catch (IOException e) {
        System.out.println("IOException:  " + e);
      }
    }
  }
}