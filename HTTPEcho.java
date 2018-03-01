import java.net.*;
import java.io.*;

public class HTTPEcho {
    public static void main( String[] args) throws IOException{
        try{
            int port = Integer.parseInt(args[0]);
            ServerSocket welcomeSocket = new ServerSocket(port);
            String sentence;
            while(true){
                StringBuilder sb = new StringBuilder("");
                Socket connectionSocket = welcomeSocket.accept();
                BufferedReader fromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                DataOutputStream toClient = new DataOutputStream(connectionSocket.getOutputStream());
                connectionSocket.setSoTimeout(2000);

                try {
                    while((sentence = fromClient.readLine()) != null && sentence.length() != 0){
                        sb.append(sentence + "\n");
                    }

                }catch (java.net.SocketTimeoutException x){

                }
                String res = "HTTP/1.1 200 OK" + "\r\n"
                        + "Content-type: text/plain" + "\r\n"
                        + "Content-Length: " + sb.length() + "\r\n\r\n";

                toClient.writeBytes(res + sb.toString() + "\n");
                connectionSocket.close();
            }

        }catch (java.io.IOException one){

        }
    }
}

