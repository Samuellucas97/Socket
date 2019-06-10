import java.io.*;
import java.net.*;
import java.util.Scanner;

public class udpClient{
    public static void main(String[] args) throws Exception{
        //Criação do Scanner para leitura de dados do usuário
        Scanner leitor = new Scanner(System.in);
        
        //Atribuindo os dados recebidos pelo usuário
        System.out.println("Insira o endereço IP do servidor:");
        String enderecoIP = leitor.nextLine();
        System.out.println("Agora insira a porta do servidor:");
        int porta = Integer.parseInt(leitor.nextLine());
        //Criação do socket e chamada de função para realizar a conexão com o servidor
        DatagramSocket clienteSocket = new DatagramSocket();
        System.out.println("\nEnviando dados para o endereço IP: " + enderecoIP);
        
        //Recebe a mensagem do usuário e a coloca em um pacote para depois enviar ao servidor
        System.out.println("\nInsira a sua mensagem:");
        String outMsg = leitor.nextLine();
        DatagramPacket sendPacote = new DatagramPacket(outMsg.getBytes(), outMsg.getBytes().length, InetAddress.getByName(enderecoIP), porta);
        clienteSocket.send(sendPacote);
        System.out.println("Mensagem enviada.\n");

        //Espera a resposta do servidor com a confirmação de recebimento do datagrama
        DatagramPacket receivePacote = new DatagramPacket(new byte[10240], 10240);
        clienteSocket.receive(receivePacote);
        outMsg = new String(receivePacote.getData());
        System.out.println(outMsg);

        //Encerra o socket
        clienteSocket.close();
    }
}