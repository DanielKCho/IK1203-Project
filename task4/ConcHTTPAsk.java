import java.net.*;
import java.io.*;


public class ConcHTTPAsk {
    public static void main(String[] args) throws IOException{
        try{
            int port = Integer.parseInt(args[0]);
            ServerSocket welcomeSocket = new ServerSocket(port);
            while(true){
                Socket connectionSocket = welcomeSocket.accept();
                MyRunnable socketX = new MyRunnable(connectionSocket);
                new Thread(socketX).start();
            }

        }catch (java.io.IOException one){

        }
    }
}

