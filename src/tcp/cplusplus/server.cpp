/**
 * @file    serverSide.cpp
 * @brief   Implementação de arquitetura cliente-servidor (TCP) no lado do servidor
 */

#include <iostream>
#include <string>
#include <cstring>

/// BIBLIOTECAS P/ SOCKETS
#include <arpa/inet.h>
#include <netinet/in.h>  /// AF_INET
#include <netdb.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <unistd.h>

#define HOST "192.168.7.1"
#define PORT_NUMBER 4339    /// Numero da porta usada pelo socket do Servidor
#define QUEUE_SIZE_OF_REQUISITIONS 10   /// Tamanho da lista de requisicoes
#define MESSAGE_SIZE 40 /// Quantidade de caracteres que uma mensagem pode transmitir  

int main(){

    char bufferServer[MESSAGE_SIZE]=""; //-> Buffer que guardara a mensagem recebida e a que sera enviada
    int socketId_Client_Conexao; //-> Indica se houve falha ou nao na retirada da requisicao da fila de requisicoes
    int messageSizeReceived; //-> Tamanho da mensagem recebida
    std::string serverResponse; //-> Resposta do Servidor entrada pelo usuario


    /// CONFIGURANDO PROPRIEDADES DE CONEXÃO
    struct sockaddr_in addrServer;
    addrServer.sin_addr.s_addr = inet_addr( HOST );
    addrServer.sin_port = htons( PORT_NUMBER );
    addrServer.sin_family = AF_INET;

    std::cout << "Iniciando Servidor..." << std::endl;   

    /// CRIANDO O SOCKET IPV4 DO SERVIDOR COM PROTOCOLO TCP
    int socketId_Server = socket(AF_INET, SOCK_STREAM, 0);
    
    if( socketId_Server == -1){
        std::cerr << "Falha ao criar o socket do Servidor..." << std::endl;
        exit(EXIT_FAILURE);
    }

    std::cout << "Socket Servidor criado com sucesso" << std::endl;

    /// LIGANDO O SOCKET CRIADO DO SERVIDOR À PORTA PORT_NUMBER
    if( bind( socketId_Server,
              (struct sockaddr*) &addrServer,
              sizeof(addrServer)   
            ) != 0 ){
        std::cerr << "Falha em ligar o socket do Servidor a porta " + std::to_string(PORT_NUMBER) << "..." << std::endl;
        exit(EXIT_FAILURE);                
    }

    std::cout << "A ligacao do socket do Servidor a porta " + std::to_string( PORT_NUMBER) + " foi um sucesso" << std::endl;

    /// HABILITA O SOCKET DO SERVIDOR A ESCUTAR REQUISIÇÕES 
    if( listen( socketId_Server, 
                QUEUE_SIZE_OF_REQUISITIONS
               ) != 0){
        std::cerr << "Falha em fazer o socket do Servidor escutar requisicoes..." << std::endl;
        exit(EXIT_FAILURE);                           
    }

    std::cout << "O socket do Servidor esta ouvindo se ha requisicoes..." << std::endl;

    /// RETIRANDO REQUISIÇÃO PENDENTE DA CABEÇA DA FILA DE REQUISIÇÕES
    struct sockaddr_in addrCliente;   
    socklen_t cliente_len = sizeof(addrCliente);

    socketId_Client_Conexao = accept( socketId_Server,
                                (struct sockaddr*)& addrCliente,
                                &cliente_len);

    std::cout << "Socket do Servidor recebeu conexao de " << inet_ntoa(addrCliente.sin_addr) << std::endl;

    if( socketId_Client_Conexao == -1 ){
        std::cerr << "Falha ao retira requisicao da frente da fila de requisicoes..." << std::endl;
        exit(EXIT_FAILURE);
    }    
    
    std::cout << "Requisicao retirada da frente da fila de requisicoes com sucesso" << std::endl;
        
    /// ENVIANDO MENSAGEM DO SOCKET DO SERVIDOR PARA O SOCKET DO CLIENTE 

    if ( send( socketId_Client_Conexao, 
               "Oi cliente! Voce estah pronto?\n", 
               MESSAGE_SIZE, 0 
            ) == -1 ){
	fflush(stderr);
	std::cerr << "Falha no envio da mensagem por parte do socket do Servidor..." << std::endl;
	exit(EXIT_FAILURE);                           
    }

    std::cout <<"O socket do Servidor enviou uma mensagem. Logo o cliente esta conectado" << std::endl << std::endl;
    
    /// SOCKET DO SERVIDOR **COMUNICANDO-SE** COM O SOCKET DO CLIENTE
    while (true){
       fflush(stdout); 
        messageSizeReceived = recv( socketId_Client_Conexao, 
                                        bufferServer,
                                        MESSAGE_SIZE, 0 );

        if( messageSizeReceived > 0 ){  /// Situação em que o cliente mandou uma mensagem não vazia
            
            std::cout << "Cliente disse: " << bufferServer[0] << std::endl;

            if( std::string(1, bufferServer[0]) == "" ){
                send( socketId_Client_Conexao, "Sem comando", MESSAGE_SIZE, 0 );
                break;

            }

        }

	fflush(stdin);
        std::getline(std::cin, serverResponse);
	strcpy(bufferServer,serverResponse.c_str()); 
        send( socketId_Client_Conexao, bufferServer, MESSAGE_SIZE, 0 );

    }


    //// QUEBRANDO CONEXÃO ENTRE O SOCKET DO SERVIDOR E O DO CLIENTE
    close(socketId_Client_Conexao);
    close(socketId_Server);

    std::cout << "Conexao entre os sockets do Servidor e do Cliente foi quebrada..." << std::endl;
    
    return EXIT_SUCCESS;
}    
