import java.net.*;
import java.io.*;

public class TCPClient {
    
    public static String askServer(String hostname, int port, String ToServer) throws  IOException {
        if(ToServer == null){
          return askServer(hostname, port);
        }

        Socket clientSocket = new Socket(hostname, port);
        BufferedReader fromSocket = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        outToServer.writeBytes(ToServer + '\n');

        StringBuilder sb = new StringBuilder("");
        clientSocket.setSoTimeout(5000);

        String tmp;
        try{
            while((tmp = fromSocket.readLine()) != null && sb.length() <= 16384){
                sb.append(tmp);
            }
        } catch (java.net.SocketTimeoutException one){
            clientSocket.close();
            return sb.toString();

        }

        clientSocket.close();
        return sb.toString();

    }

    public static String askServer(String hostname, int port) throws  IOException {
        Socket clientSocket = new Socket(hostname, port);
        BufferedReader fromSocket = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        StringBuilder sb = new StringBuilder("");
        String tmp;
        clientSocket.setSoTimeout(5000);

        try{
            while((tmp = fromSocket.readLine()) != null && sb.length() <= 16384){
                sb.append(tmp);
            }
        } catch (java.net.SocketTimeoutException two){
            clientSocket.close();
            return sb.toString();
        }

        clientSocket.close();
        return sb.toString();
    }
}
