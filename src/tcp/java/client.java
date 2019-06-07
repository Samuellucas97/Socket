import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class TCP_Client {
    public static void main(String[] args) throws Exception {
        Scanner leitor = new Scanner(System.in);
        String[] sepSocket;
        String getSocket;
        boolean condicao = true;

        do{
            System.out.println("Digite o IP do servidor seguido da porta: (x.y.w.z 00000)");
            getSocket = leitor.nextLine();
            sepSocket = getSocket.split(" ");
            if(sepSocket.length == 2){
                condicao = false;
            }else{
                System.out.println("Certifique-se de digitar o IP juntamente com a porta.");
                condicao = true;
            }
        }while(condicao);
        
        Socket socket = new Socket(sepSocket[0], Integer.parseInt(sepSocket[1]));
        System.out.println("Conectado ao servidor em: "
                + socket.getLocalSocketAddress());
        BufferedReader socketReader = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        BufferedWriter socketWriter = new BufferedWriter(new OutputStreamWriter(
                socket.getOutputStream()));
        BufferedReader consoleReader = new BufferedReader(
                new InputStreamReader(System.in));

        String promptMsg = "Por favor, insira uma mensagem.  (Sair para encerrar):\n";
        String outMsg;
        String inMsg;

        System.out.print(promptMsg);
        while ((outMsg = consoleReader.readLine()) != null) {
            socketWriter.write(outMsg);
            socketWriter.write("\n");
            socketWriter.flush();

            inMsg = socketReader.readLine();
            if (inMsg.equalsIgnoreCase("sair")) {
                System.out.println("Conexão encerrada");
                break;
            }
            System.out.println("Servidor: " + inMsg);
            System.out.print(promptMsg);
        }
        socket.close();
    }
}
