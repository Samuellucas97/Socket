import java.io.*;
import java.net.*;
import java.util.Scanner;

public class client{
    public static void main(String[] args) throws Exception{
        //Criação do buffer para leitura do usuário
        Scanner leitor = new Scanner(System.in);
        
        String enderecoIP = args[0];
        int porta = Integer.parseInt(args[1]);
        //Criação do socket e chamada de função para realizar a conexão com o servidor
        DatagramSocket clienteSocket = new DatagramSocket();
        System.out.println("Enviando dados para o endereço IP: " + enderecoIP);
        
        System.out.println("Insira a sua mensagem:");
        String outMsg = leitor.nextLine();
        DatagramPacket sendPacote = new DatagramPacket(outMsg.getBytes(), outMsg.getBytes().length,
                                                       InetAddress.getByName(enderecoIP), porta);
        clienteSocket.send(sendPacote);
        System.out.println("Mensagem enviada.");
        DatagramPacket receivePacote = new DatagramPacket(new byte[1024], 1024);
        clienteSocket.receive(receivePacote);
        outMsg = new String(receivePacote.getData());
        System.out.println(outMsg);
        clienteSocket.close();
    }
}