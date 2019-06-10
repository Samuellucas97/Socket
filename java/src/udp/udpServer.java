import java.io.*;
import java.net.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class udpServer{
    //Definição de atributos estáticos de controle usados pelo servidor para lidar com as diversas conexões
    static Date tempoLocal;
    static SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss");
    
    public static void main(String[] args) throws Exception{
        //Criação e associação do socket ao endereço do servidor
        DatagramSocket serverSocket = new DatagramSocket(12900, InetAddress.getByName(getRedeIP()));

        //Mostra as informações do socket ao usuário
        System.out.println("Servidor iniciado no endereço " + serverSocket.getLocalSocketAddress());
        System.out.println("Agurdando conexão na porta: " + serverSocket.getLocalPort());
        System.out.println("");

        String outMsg;

        //Laço infinito para receber os envios dos possíveis clientes
        while(true){
        	//Definição do datagrama para receber os dados
            DatagramPacket pacote = new DatagramPacket(new byte[1024], 1024);
            System.out.println("Aguardando dados...");
            serverSocket.receive(pacote);
            tempoLocal = new Date();

            //Atribuindo as informações de IP e porta do cliente que enviou o datagrama
            InetAddress EnderecoIP = pacote.getAddress();
            int porta = pacote.getPort();

            //Colocando a mensagem recebida em uma string para mostrar na tela
            String mensagemCliente = new String(pacote.getData());
            System.out.println("Mensagem recebida: " + mensagemCliente);
            outMsg = "Servidor: datagrama \"" + mensagemCliente + "\" recebido às " + ft.format(tempoLocal) + " (hora local)\n";
            
            //Cria um datagrama com informações sobre o datagrama recebido e a transmite para o cliente
            byte[] sendMess = outMsg.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendMess, sendMess.length, EnderecoIP, porta);
            serverSocket.send(sendPacket);
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
        //Retorna o endereço IP
        return atualIP ;
    }
}