import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class client{
    public static void main(String[] args) throws Exception{
        //Criação do socket e chamada de função para realizar a conexão com o servidor
        Socket serverSocket = connectServer();
        System.out.println("Enviando dados para o endereço IP: " + serverSocket.getInetAddress());
        
        //Criação dos buffers de escrita e leitura para usar na conexão
        BufferedWriter socketWriter = new BufferedWriter(new OutputStreamWriter(serverSocket.getOutputStream()));
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        String outMsg;
        
        try{
            //Criação de um thread para ficar sempre ouvindo em busca de mensagens
            Runnable receiver = () -> receiveData(serverSocket);
            new Thread(receiver).start();
            
            //Respondendo ao servidor com a identificação pedida
            outMsg = consoleReader.readLine();
            socketWriter.write(outMsg + "\n");
            socketWriter.flush();
            System.out.println("");
            
            System.out.println("Digite \"sair\" para encerrar o bate-papo.");
            //Laço para receber e enviar mensagens ao servidor
            while((outMsg = consoleReader.readLine()) != null){
                socketWriter.write(outMsg + "\n");
                socketWriter.flush();
            }
        }catch(Exception e){
            //Exceção para quando o servidor encerra a conexão sem avisar
            System.out.println("A conexão com o servidor foi encerrada.");
            System.out.println("A aplicação será encerrada.");
            serverSocket.close();
        }
    }
    
    //Funcção para ficar ouvindo até receber uma mensagem e a imprime na tela
    public static void receiveData(Socket socket_){
        String inMsg;
        try{
            //Criação do buffer de leitura do socket
            BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket_.getInputStream()));
            while((inMsg = socketReader.readLine()) != null){
                //Condicional para receber mensagem de saida do servidor e encerrar a conexão.
                if (inMsg.equalsIgnoreCase("sair")) {
                    System.out.println("Conexão encerrada.");
                    socket_.close();
                    System.exit(0);
                }
                System.out.println(inMsg);
            }
        }catch(Exception e){
            System.out.println("Houve um erro com a leitura da mensagem recebida");
        }
    }
    
    //Função para realizar a conexão ao servidor
    public static Socket connectServer(){
        Socket socket = null;
        boolean condicao = true, excecao = true;
        Scanner leitor = new Scanner(System.in);
        String[] sepSocket;
        String getSocket;
        
        //Laço para receber as informações para tentar se conectar ao servidor
        do{
            System.out.println("Digite o IP do servidor seguido da porta: (x.y.w.z 00000).");
            getSocket = leitor.nextLine();
            sepSocket = getSocket.split(" ");
            
            //Condicional para verificar se recebeu a quantidade correta de dados.
            if(sepSocket.length == 2){
                do{
                    //Tenta conectar ao servidor com as informações que foram passadas
                    //Caso não consiga, dá opções ao cliente
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
                //Mensagem de erro caso receba um valor de dados diferentes de dois
                System.out.println("Certifique-se de digitar o IP juntamente com a porta.");
                condicao = true;
            }
        }while(condicao);
        
        return socket;
    }
}