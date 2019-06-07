import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.text.SimpleDateFormat;

public class server {
    static int cont = 0;
    static Date tempoLocal;
    static SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss");
    
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(12900, 100,
                InetAddress.getLocalHost());
        System.out.println("Servidor iniciado em: " + serverSocket);

        while (true) {
            System.out.println("Agurdando conexão...");
            System.out.println("");

            final Socket activeSocket = serverSocket.accept();

            System.out.println("Conexão recebida de  " + activeSocket);
            Runnable runnable = () -> handleClientRequest(activeSocket);
            new Thread(runnable).start(); // start a new thread
        }
    }

    public static void handleClientRequest(Socket socket) {
        try {
            BufferedReader socketReader = null;
            BufferedWriter socketWriter = null;
            socketReader = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            socketWriter = new BufferedWriter(new OutputStreamWriter(
                    socket.getOutputStream()));

            String inMsg = null;
            while ((inMsg = socketReader.readLine()) != null) {
                tempoLocal = new Date();
                if(inMsg.equalsIgnoreCase("sair")){
                    System.out.println("Conexão encerrada pelo cliente");
                    socketWriter.write("Sair");
                    socketWriter.write("\n");
                    socketWriter.flush();
                    break;
                }
                System.out.println("Cliente : " + inMsg);
                
                String outMsg = "\nMensagem recebida às: " + ft.format(tempoLocal);
                socketWriter.write(outMsg);
                socketWriter.write("\n");
                socketWriter.flush();
            }
            socket.close();
        } catch (Exception e) {
            System.out.println("\nA conexão foi encerrada inesperadamente pelo Cliente " + "");
            System.out.println("");
        }
    }
}