import java.io.*;
import java.net.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class server{
    //Definição de atributos estáticos de controle usados pelo servidor para lidar com as diversas conexões
    static int cont = 0;
    static Date tempoLocal;
    static SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss");
    static Map <Socket, String> connectedSockets = new HashMap <Socket, String> ();
    
    public static void main(String[] args) throws Exception{
        //Criação e associação do socket ao endereço do servidor
        ServerSocket serverSocket = new ServerSocket(12900, 100, InetAddress.getByName(getRedeIP()));
        //Mostra as informações do socket ao usuário
        System.out.println("Servidor iniciado no endereço " + serverSocket.getInetAddress());
        System.out.println("Agurdando conexão na porta: " + serverSocket.getLocalPort());
        System.out.println("");
        
        //Laço infinito para aceitar as conexões ao servidor e inicia uma thread com o cliente
        while(true){
            final Socket activeSocket = serverSocket.accept();

            System.out.println("Conexão recebida de " + activeSocket);
            Runnable runnable = () -> handleClientRequest(activeSocket);
            new Thread(runnable).start(); // start a new thread
        }
    }

    //Função para lidar com a conexão do cliente
    public static void handleClientRequest(Socket socket){
        String inMsg, outMsg, identifier = null;
        
        try{
            //Definição e inicialização dos buffers de leitura e escrita para se comunicar com o cliente
            BufferedReader socketReader;
            socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter socketWriter;
            socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            
            //Pergunta ao cliente como ele gostaria de ser identificado no servidor e manda mensagem de confirmação
            socketWriter.write("Como gostaria de ser identificado?\n");
            socketWriter.flush();
            identifier = socketReader.readLine();
            tempoLocal = new Date();
            connectedSockets.put(socket, identifier);
            System.out.println(identifier + " se conectou.\n");
            outMsg = "Servidor: mensagem recebida às: " + ft.format(tempoLocal);
            socketWriter.write(outMsg + "\n");
            socketWriter.write("Bem-vindo(a) a conversa " + identifier + "\n");
            socketWriter.flush();
            
            //Verifica a lista de clientes para mandar mensagem aos demais sobre a entrada do novo cliente
            for(Map.Entry <Socket, String> it : connectedSockets.entrySet()){
                if(it.getKey() != socket){
                    socketWriter = new BufferedWriter(new OutputStreamWriter(it.getKey().getOutputStream()));
                    socketWriter.write(identifier + " entrou na conversa.");
                    socketWriter.write("\n");
                    socketWriter.flush();
                }
            }
            
            //Laço para ficar lendo as mensagens do cliente da thread e repassar para os demais clientes conectados
            while((inMsg = socketReader.readLine()) != null){
                tempoLocal = new Date();
                socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                outMsg = "Servidor: mensagem recebida às: " + ft.format(tempoLocal);
                socketWriter.write(outMsg);
                socketWriter.write("\n");
                socketWriter.flush();
                
                //Verificação para ver se o cliente quer encerrar a conexão
                if(inMsg.equalsIgnoreCase("sair")){
                    System.out.println("Conexão encerrada pelo cliente");
                    socketWriter.write("Sair\n");
                    socketWriter.flush();
                    connectedSockets.remove(socket);
                    break;
                }
                
                System.out.println(identifier + ": " + inMsg);                
                
                //Manda a mensagem recebida do cliente para os demais
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
            //Mostra mensagem quando o cliente encerra a conexão sem avisar e o remove da lista de clientes
            System.out.println("\nA conexão foi encerrada inesperadamente por " + identifier);
            connectedSockets.remove(socket);
        }
    }

    //Função para conseguir o endereço IP principal da rede
    public static String getRedeIP() {
        String atualIP = null;
        if (atualIP  == null) {
            Enumeration<NetworkInterface> interfacesRede = null;
            try {
                interfacesRede = NetworkInterface.getNetworkInterfaces();

                //Enquanto a interface tem elementos, testa para ver se encontra um que se adequa
                while (interfacesRede.hasMoreElements()) {
                    NetworkInterface ni = interfacesRede.nextElement();
                    Enumeration<InetAddress> address = ni.getInetAddresses();
                    while (address.hasMoreElements()) {
                        InetAddress addr = address.nextElement();
                        if (!addr.isLoopbackAddress() && addr.isSiteLocalAddress()
                                && !(addr.getHostAddress().indexOf(":") > -1)) {
                            atualIP  = addr.getHostAddress();
                        }
                    }
                }
                //Caso o laço não resulte em nenhum resultado, define o IP como o localhost
                if (atualIP  == null) {
                    atualIP  = "127.0.0.1";
                }

            } catch (SocketException e) {
                //Caso haja alguma exceção, o IP é definido como localhost
                atualIP  = "127.0.0.1";
            }
        }
        return atualIP ;
    }
}