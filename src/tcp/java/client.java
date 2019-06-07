import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class client {
    public static void main(String[] args) throws Exception {
        Scanner leitor = new Scanner(System.in);
        String[] sepSocket;
        String getSocket;
        Socket socket = null;
        boolean condicao = true, excecao = true;

        do{
            System.out.println("Digite o IP do servidor seguido da porta: (x.y.w.z 00000)");
            getSocket = leitor.nextLine();
            sepSocket = getSocket.split(" ");
            if(sepSocket.length == 2){
                do{
                    try{
                        socket = new Socket(sepSocket[0], Integer.parseInt(sepSocket[1]));
                        System.out.println("\nConexão ao servidor realizada com sucesso." + "\n");
                        excecao = condicao = false;
                    } catch (Exception e){
                        System.out.println("\n\nNão foi possível se conectar a esse servidor");
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
