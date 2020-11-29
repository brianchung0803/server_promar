import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.*;
import java.io.*;


public class server implements Runnable
{
    private Thread thread = null;
    private ServerSocket serverSocket = null;
    private Socket socket = null;
    private DataInputStream input = null;


    public server(int port)
   {  try
      {  System.out.println("Binding to port " + port + ", please wait  ...");
         serverSocket = new ServerSocket(port);  
         start();
      }
      catch(IOException ioe)
      {  System.out.println(ioe); 
      }
   }

    public  void run()
   {  while (thread != null)
      {   try
         {  System.out.println("Waiting for a client ...");
            socket = serverSocket.accept();
            System.out.println("Client accepted: " + socket);
            open();
            boolean done = false;
            try
               {   
                  long len = input.readLong();
                  byte[] bytes = new byte[(int) len];
                  input.read(bytes);
                  System.out.println("got client");
                  System.out.println(len);
                  //Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,len);
            }
            catch(IOException ioe)
            {  done = true;  }
            
            close();
         }
         catch(IOException ie)
         {  System.out.println("Acceptance Error: " + ie);  }
      }
   }

   public void start()
   {  if (thread == null)
      {  thread = new Thread(this); 
         thread.start();
      }
   }
    public void open() throws IOException
    {  
        input = new DataInputStream(socket.getInputStream());
    }
    public void close() throws IOException
   {  if (socket != null)    socket.close();
      if (input != null)  input.close();
   }

   public static void main(String[] args) {
        server serv = null;
        serv =  new server(30002);
    }
}
