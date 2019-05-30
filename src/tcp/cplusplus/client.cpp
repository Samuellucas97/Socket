#include <iostream>

/// BIBLIOTECAS P/ SOCKETS
#include <arpa/inet.h>
#include <netinet/in.h>  /// AF_INET
#include <unistd.h>

#define HOST "127.0.0.1"
#define PORT_NUMBER 4326
#define MESSAGE_SIZE 40 /// Quantidade de caracteres que uma mensagem pode transmitir  

int main(){ 

    char bufferServer[MESSAGE_SIZE]; //-> Buffer que guardara a mensagem recebida e a que sera enviada
    int messageSizeReceived; //-> Tamanho da mensagem recebida
    std::string clienteResponse; //-> Resposta do Cliente entrada pelo usuario


    /// CONFIGURANDO AS PROPRIEDADES DA CONEXAO
    struct sockaddr_in addrServer;
    addrServer.sin_addr.s_addr = inet_addr( HOST );
    addrServer.sin_port = htons( PORT_NUMBER );
    addrServer.sin_family = AF_INET;
     
    std::cout << "Iniciando cliente..." << std::endl;

    /// CRIANDO O SOCKET (AF_INET = IPV4) DO CLIENTE
    int socketId_Cliente = socket(AF_INET, SOCK_STREAM, 0);

    if( socketId_Cliente == -1){
        std::cerr << "Falha ao executar o socket do Cliente..." << std::endl;
        exit(EXIT_FAILURE);
    }

    std::cout << "Socket do Cliente criado com sucesso" << std::endl;

    /// ESTABELECENDO CONEXÃO VIA SOCKETS ENTRE O **CLIENTE** E O SERVIDOR
    if( connect( socketId_Cliente, 
                 (struct sockaddr *) &addrServer, 
                  sizeof(addrServer) 
                ) == -1 ){
        std::cerr << "Falha ao conectar o socket do Cliente e o do Servidor..." << std::endl;
        exit(EXIT_FAILURE);
    }

    std::cout << "Cliente conectado ao Servidor (via sockets)..." << std::endl << std::endl;

    
    /// SOCKET DO CLIENTE RECEBENDO MENSAGEM DO SOCKET DO SERVIDOR
    messageSizeReceived = recv(socketId_Cliente, bufferServer, MESSAGE_SIZE, 0);

    if( messageSizeReceived > 0 ){ /// Situação em que o Servidor mandou uma mensagem não vazia
        std::cout << "Servidor disse: " 
             << bufferServer 
             << ". Logo, o servidor esta conectado..."
             << std::endl
	     << std::endl;
    }

    /// SOCKET DO CLIENTE **COMUNICANDO-SE** COM O SOCKET DO SERVIDOR
    while( true ){

        std::getline(std::cin, clienteResponse);

        send( socketId_Cliente, clienteResponse.c_str(), MESSAGE_SIZE, 0 );

        messageSizeReceived = recv(socketId_Cliente, bufferServer, MESSAGE_SIZE, 0);

        if( messageSizeReceived > 0 ){  /// Situação em que o cliente mandou uma mensagem não vazia
            
            std::cout << "Servidor disse: " << bufferServer << std::endl;
            
            if( std::string(bufferServer) == "tchau" ||
                std::string(bufferServer) == "bye" ||
                std::string(bufferServer) == "Ate logo"){
                    break;
                }

        }

    }

    //// QUEBRANDO CONEXÃO ENTRE O SOCKET DO CLIENTE E O DO SERVIDOR
    close( socketId_Cliente );
    
    std::cout << "Conexao entre os sockets do Cliente e do Servidor foi quebrada..." << std::endl;
    
    return EXIT_SUCCESS;

}    


