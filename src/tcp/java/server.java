import java.io.*;
import java.net.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class server{
    static int cont = 0;
    static Date tempoLocal;
    static SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss");
    static Map <Socket, String> connectedSockets = new HashMap <Socket, String> ();
    
    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(12900, 100, InetAddress.getLocalHost());
        System.out.println("Servidor iniciado em: " + serverSocket.getInetAddress() + " - " + serverSocket.getLocalPort());
        System.out.println("Agurdando conexão...");
        System.out.println("");
        
        while(true){
            final Socket activeSocket = serverSocket.accept();

            System.out.println("Conexão recebida de " + activeSocket);
            Runnable runnable = () -> handleClientRequest(activeSocket);
            new Thread(runnable).start(); // start a new thread
        }
    }

    public static void handleClientRequest(Socket socket){
        String inMsg, outMsg, identifier = null;
        
        try{
            BufferedReader socketReader;
            socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter socketWriter;
            socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            
            socketWriter.write("Como gostaria de ser identificado?\n");
            socketWriter.flush();
            identifier = socketReader.readLine();
            tempoLocal = new Date();
            connectedSockets.put(socket, identifier);
            System.out.println(identifier + " se conectou.\n");
            outMsg = "Servidor: mensagem recebida às: " + ft.format(tempoLocal);
            socketWriter.write(outMsg);
            socketWriter.write("\n");
            socketWriter.flush();
            
            for(Map.Entry <Socket, String> it : connectedSockets.entrySet()){
                if(it.getKey() != socket){
                    socketWriter = new BufferedWriter(new OutputStreamWriter(it.getKey().getOutputStream()));
                    socketWriter.write(identifier + " entrou na conversa.");
                    socketWriter.write("\n");
                    socketWriter.flush();
                }
            }
            
            while((inMsg = socketReader.readLine()) != null){
                tempoLocal = new Date();
                socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                outMsg = "Servidor: mensagem recebida às: " + ft.format(tempoLocal);
                socketWriter.write(outMsg);
                socketWriter.write("\n");
                socketWriter.flush();
                
                if(inMsg.equalsIgnoreCase("sair")){
                    System.out.println("Conexão encerrada pelo cliente");
                    socketWriter.write("Sair\n");
                    socketWriter.flush();
                    connectedSockets.remove(socket);
                    break;
                }
                
                System.out.println(identifier + ": " + inMsg);                
                
                for(Map.Entry <Socket, String> it : connectedSockets.entrySet()){
                    if(it.getKey() != socket){
                        socketWriter = new BufferedWriter(new OutputStreamWriter(it.getKey().getOutputStream()));
                        socketWriter.write(identifier + ": " + inMsg);
                        socketWriter.write("\n");
                        socketWriter.flush();
                    }
                }
            }
            socket.close();
        }catch (Exception e){
            System.out.println("\nA conexão foi encerrada inesperadamente por " + identifier);
            connectedSockets.remove(socket);
        }
    }
}