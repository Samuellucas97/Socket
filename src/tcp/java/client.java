import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class client{
    public static void main(String[] args) throws Exception{
        Socket serverSocket = connectServer();
        System.out.println("Conectado ao servidor em: " + serverSocket);
        BufferedReader socketReader = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
        BufferedWriter socketWriter = new BufferedWriter(new OutputStreamWriter(serverSocket.getOutputStream()));
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        String outMsg, inMsg;
        
        System.out.println("\nInformações para uso do chat:.");
        System.out.println("Caso não queira digitar nada, pressione apenas Enter.");
        System.out.println("Digite \"sair\" para sair.\n");
        
        try{
            inMsg = socketReader.readLine();
            System.out.println(inMsg);
            outMsg = consoleReader.readLine();
            socketWriter.write(outMsg + "\n");
            socketWriter.flush();
            inMsg = socketReader.readLine();
            System.out.println(inMsg + "\n");
            outMsg = "";
            
            do{
                if(!(outMsg.isEmpty())){
                    socketWriter.write(outMsg + "\n");
                    socketWriter.flush();
                    inMsg = socketReader.readLine();
                    System.out.println(inMsg);
                }
                inMsg = socketReader.readLine();
                if (inMsg.equalsIgnoreCase("sair")) {
                    System.out.println("Conexão encerrada.");
                    System.exit(0);
                }
                System.out.println(inMsg);
                System.out.println("Digite algo para continuar a conversa.");
            }while(((outMsg = consoleReader.readLine()) != null));
            serverSocket.close();
        }catch(Exception e){
            System.out.println("A conexão com o servidor foi encerrada.");
            System.out.println("A aplicação será encerrada.");
        }
    }
    
    public static Socket connectServer(){
        Socket socket = null;
        boolean condicao = true, excecao = true;
        Scanner leitor = new Scanner(System.in);
        String[] sepSocket;
        String getSocket;
        do{
            System.out.println("Digite o IP do servidor seguido da porta: (x.y.w.z 00000).");
            getSocket = leitor.nextLine();
            sepSocket = getSocket.split(" ");
            if(sepSocket.length == 2){
                do{
                    try{
                        socket = new Socket(sepSocket[0], Integer.parseInt(sepSocket[1]));
                        System.out.println("\nConexão ao servidor realizada com sucesso." + "\n");
                        excecao = condicao = false;
                    } catch (Exception e){
                        System.out.println("\n\nNão foi possível se conectar a esse servidor.");
                        System.out.println("Quer tentar se conectar novamente?");
                        System.out.println("Digite \"sim\" para digitar o IP e porta novamente.");
                        getSocket = leitor.nextLine();
                        if(getSocket.equalsIgnoreCase("sim")){
                            condicao = true;
                            excecao = false;
                        }else{
                            System.out.println("\nNão foi possível estabelecer conexão com o servidor.");
                            System.out.println("A aplicação será encerrada.");
                            System.exit(0);
                        }
                    }
                }while(excecao);
            }else{
                System.out.println("Certifique-se de digitar o IP juntamente com a porta.");
                condicao = true;
            }
        }while(condicao);
        
        return socket;
    }
}
