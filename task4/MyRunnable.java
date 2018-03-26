import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Runnable;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class MyRunnable implements Runnable{
    private Socket connectionSocket;

    public MyRunnable(Socket socket){
        this.connectionSocket = socket;
    }

    public void run(){
        try {
            String sentence;
            StringBuilder sb = new StringBuilder();
            DataOutputStream toClient = new DataOutputStream(connectionSocket.getOutputStream());
            connectionSocket.setSoTimeout(20000);
            BufferedReader fromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            try {
                while((sentence = fromClient.readLine()) != null && sentence.length() != 0){
                    sb.append(sentence + "\n");
                }

            }catch (java.net.SocketTimeoutException x){

            }
            String host = "";
            int port1 = 0;
            String query = "";

            String [] string1 = sb.toString().split("\\s|\\?|=|&|=");
            for(int i = 0; i < string1.length; i++) {
                if(string1[i].equals("hostname")){
                    host = string1[i+1];
                }else if(string1[i].equals("port")){
                    port1 = Integer.parseInt(string1[i+1]);
                }
                if(string1[i].equals("string")){
                    query = string1[i+1];
                }
            }

            try{
                String sendToTCP = askServer(host, port1, query);
                String res = "HTTP/1.1 200 OK" + "\r\n"
                        + "Content-type: text/plain" + "\r\n"
                        + "Content-Length: " + sendToTCP.length() + "\r\n\r\n";

                toClient.writeBytes(res + sendToTCP + "\n");
                connectionSocket.close();
            }catch (java.lang.NumberFormatException x){

            }
        } catch (IOException e) {

        }
    }

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
                sb.append(tmp + "\n");
            }
        } catch (SocketTimeoutException one){
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
        } catch (SocketTimeoutException two){
            clientSocket.close();
            return sb.toString();
        }

        clientSocket.close();
        return sb.toString();
    }

}
