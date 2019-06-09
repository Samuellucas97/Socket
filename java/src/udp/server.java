import java.io.*;
import java.net.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class server{
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
            DatagramPacket pacote = new DatagramPacket(new byte[1024], 1024);
            System.out.println("Aguardando dados...");
            serverSocket.receive(pacote);
            tempoLocal = new Date();
            String mensagemCliente = new String(pacote.getData());
            System.out.println("Mensagem recebida: " + mensagemCliente);
            outMsg = "Mensagem \"" + mensagemCliente + "\" recebida às " + ft.format(tempoLocal) +
                     " (hora local)\n";
            
            InetAddress EnderecoIP = pacote.getAddress();
            int porta = pacote.getPort();
            DatagramPacket sendPacket = new DatagramPacket(outMsg.getBytes(), outMsg.getBytes().length,
                                                           EnderecoIP, porta);
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
        return atualIP ;
    }
}