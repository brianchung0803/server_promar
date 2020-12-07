import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ByteArrayOutputStream;
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
            while(!done){
               try
                  {   
                     int len = input.readInt();
                     int length=0;
                     System.out.println("got client");
                     System.out.println(len);
                     System.out.println("開始接收資料...");
                     FileOutputStream fos = new FileOutputStream(new File("./cc.jpg"));
                     ByteArrayOutputStream b_array_stream = new ByteArrayOutputStream();
                     byte[]inputByte = new byte[100000];
                     
                     while (len > 0) {
                        length = input.read(inputByte, 0, Math.min(inputByte.length,len));
                        len=len-length;
                        fos.write(inputByte, 0, length);
                        fos.flush();
                        b_array_stream.write(inputByte, 0, length);
                        b_array_stream.flush();
                     }

                     int poselen=input.readInt();
                     System.out.println(poselen);
                     byte[] poseByte=new byte[poselen];
                     input.read(poseByte,0,poselen);
                     String pose=new String(poseByte);
                     System.out.println(pose);

                        
                     //Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,len);
               }
               catch(IOException ioe)
               {
                  System.out.println(ioe);
                  done=true;
               }
            }
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
        serv =  new server(30000);
    }
}
