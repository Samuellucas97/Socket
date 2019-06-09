/**
 * @file    client.cpp
 * @brief   Contém uma implementação de um socket cliente que faz uso do protocolo TCP
 */

#include <iostream>
#include <string>
#include <cstring>
#include <cstdio>

/// BIBLIOTECAS P/ SOCKETS
#include <arpa/inet.h>
#include <netinet/in.h>  /// AF_INET
#include <unistd.h>

#define LOCAL_SERVER_PORT 4504
#define MAX_MSG 100 /// Quantidade de caracteres que uma mensagem pode transmitir  

int main(){ 

    char bufferServer[MAX_MSG]; //-> Buffer que guardara a mensagem recebida e a que sera enviada
    int messageSizeReceived; //-> Tamanho da mensagem recebida
    std::string clienteResponse; //-> Resposta do Cliente entrada pelo usuario

    /// CONFIGURANDO AS PROPRIEDADES DA CONEXAO
    struct sockaddr_in addrServer;
    addrServer.sin_addr.s_addr = htonl(INADDR_ANY);
    addrServer.sin_port = htons( LOCAL_SERVER_PORT );
    addrServer.sin_family = AF_INET;
     
    std::cout << "Iniciando cliente..." << std::endl;

    /// CRIANDO O SOCKET (AF_INET = IPV4) DO CLIENTE
    int socketId_Cliente = socket(AF_INET, SOCK_STREAM, 0);

    if( socketId_Cliente == -1){
        std::cerr << "Falha ao executar o socket do Cliente..." << std::endl;
        exit(EXIT_FAILURE);
    }
    else{
        std::cout << "Socket do Cliente criado com sucesso" << std::endl;
    }
    
    /// ESTABELECENDO CONEXÃO VIA SOCKETS ENTRE O **CLIENTE** E O SERVIDOR
    if( connect( socketId_Cliente, (struct sockaddr *) &addrServer, sizeof(addrServer) ) == -1 ){
        std::cerr << "Falha ao conectar o socket do Cliente e o do Servidor..." << std::endl;
        exit(EXIT_FAILURE);
    }
    else{
        std::cout << "Cliente conectado ao Servidor (via sockets)..." << std::endl << std::endl;
    }

    /// SOCKET DO CLIENTE **COMUNICANDO-SE** COM O SOCKET DO SERVIDOR
    while( true ){

        memset(bufferServer,0x0,MAX_MSG);
        std::cin.getline(bufferServer, MAX_MSG);
        
        send( socketId_Cliente, bufferServer, MAX_MSG, 0 );
        messageSizeReceived = recv(socketId_Cliente, bufferServer, MAX_MSG, 0);

        if( messageSizeReceived > 0 ){  /// Situação em que o cliente mandou uma mensagem não vazia
            
            std::cout << "Servidor disse: " << bufferServer << std::endl;
            
            /// QUEBRANDO A CONEXÃO COM O SOCKET SERVIDOR
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


