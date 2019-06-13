/**
 * @file    client.cpp
 * @brief   Contém uma implementação de um socket cliente que faz uso do protocolo TCP
 */

#include <iostream>
#include <string>
#include <cstring>
#include <cstdio>
#include <thread>

/// BIBLIOTECAS PARA SOCKETS

#include <arpa/inet.h>
#include <netinet/in.h>  /// AF_INET
#include <unistd.h>
#include <netdb.h> /// struct hostent*

#define REMOTE_SERVER_PORT 4504
#define MAX_MSG 100 /// Quantidade de caracteres que uma mensagem pode transmitir  

void escutarServidor( void *arg, struct in_addr addrCliente_sin_addr);

int main(int argc, char *argv[]){ 

    char bufferServer[MAX_MSG]; //-> Buffer que guardara a mensagem recebida e a que sera enviada
    int messageSizeReceived; //-> Tamanho da mensagem recebida
    std::string clienteResponse; //-> Resposta do Cliente entrada pelo usuario
    struct hostent *h;

    /* VERIFICA OS ARGUMENTOS PASSADOS POR LINHA DE COMANDO */
    if(argc < 2){
        std::cout << "Uso : " << argv[0]
        << " <IP do servidor>" << std::endl;
        exit(1);
    }

    /* OBTEM O ENDERECO IP e PESQUISA O NOME NO DNS */
    h = gethostbyname(argv[1]);
    if(h == NULL)    {
        std::cout << argv[0] << ": host desconhecido " << argv[1] << std::endl;
        exit(1);
    }
    std::cout << argv[0] << ": enviando dados para " << h->h_name
         << " (IP : " << inet_ntoa(*(struct in_addr *)h->h_addr_list[0]) << ")" << std::endl;
    
    /// CONFIGURANDO AS PROPRIEDADES DA CONEXAO
    struct sockaddr_in addrServer;
    
    addrServer.sin_family = h->h_addrtype;
    memcpy((char *) &addrServer.sin_addr.s_addr,
    h->h_addr_list[0], h->h_length);
    addrServer.sin_port = htons(REMOTE_SERVER_PORT);
 
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


    /// CLIENTE PASSA A OUVIR SEMPRE O SERVIDOR
    std::thread th (escutarServidor, &socketId_Cliente, addrServer.sin_addr );                    
    th.join();

    // /// SOCKET DO CLIENTE **COMUNICANDO-SE** COM O SOCKET DO SERVIDOR
    // while( true ){

    //     memset(bufferServer,0x0,MAX_MSG);
    //     std::cin.getline(bufferServer, MAX_MSG);
        
    //     send( socketId_Cliente, bufferServer, MAX_MSG, 0 );
    //     messageSizeReceived = recv(socketId_Cliente, bufferServer, MAX_MSG, 0);

    //     if( messageSizeReceived > 0 ){  /// Situação em que o cliente mandou uma mensagem não vazia
            
    //         std::cout << "Servidor disse: " << bufferServer << std::endl;
            
    //         /// QUEBRANDO A CONEXÃO COM O SOCKET SERVIDOR
    //         if( std::string(bufferServer) == "tchau" ||
    //             std::string(bufferServer) == "bye" ||
    //             std::string(bufferServer) == "Ate logo"){
    //                 break;
    //             }
    //     }

    // }

    // //// QUEBRANDO CONEXÃO ENTRE O SOCKET DO CLIENTE E O DO SERVIDOR
    // close( socketId_Cliente );
    
    // std::cout << "Conexao entre os sockets do Cliente e do Servidor foi quebrada..." << std::endl;
    
    return EXIT_SUCCESS;

}    


void escutarServidor( void *arg, struct in_addr addrServidor_sin_addr){
    
	int socketId_Servidor_Conexao = *((int *)arg);
    char msg[MAX_MSG] = ""; //-> Buffer que guardara a mensagem recebida e a que sera enviada
    int messageSizeReceived; //-> Tamanho da mensagem recebida

    /// SOCKET DO SERVIDOR **COMUNICANDO-SE** COM O SOCKET DO CLIENTE
    while (true){

        /// RECEBENDO MENSAGEM DO SOCKET SERVIDOR
        memset(msg,0x0,MAX_MSG);
        std::cout << "OI\n";

        messageSizeReceived = recv( socketId_Servidor_Conexao, msg, MAX_MSG, 0 );

        if( strlen(msg) > 0){  /// Situação em que o cliente mandou uma mensagem não vazia

            std::cout << "\nServidor (IP: " << inet_ntoa(addrServidor_sin_addr)  << ") disse: " << msg << "\n";

        }
    
    }

    close(socketId_Servidor_Conexao);
}
