/**
 * @file    clientTCP.cpp
 * @brief   Contém uma implementação de um socket cliente que faz uso do protocolo TCP
 */

#include <iostream>
#include <string>
#include <cstring>
#include <cstdio>
#include <thread>
#include <chrono>

/// BIBLIOTECAS PARA SOCKETS

#include <arpa/inet.h>
#include <netinet/in.h>  /// AF_INET
#include <unistd.h>
#include <netdb.h> /// struct hostent*

#define REMOTE_SERVER_PORT 4500
#define MAX_MSG 100 /// Quantidade de caracteres que uma mensagem pode transmitir  

/**
 * @brief   Escutando continuamente as mensagens do servidor
 * @param   arg Conexão do cliente com o servidor
 * @param   addrCliente_sin_addr    Endereço IP do cliente
 */
void escutarServidor( void *arg, struct in_addr addrCliente_sin_addr);

int main(int argc, char *argv[]){ 

    char bufferServer[MAX_MSG]; //-> Buffer que guardara a mensagem recebida e a que sera enviada
    std::string clienteResponse; //-> Resposta do Cliente entrada pelo usuario
    struct hostent *h;

    /* 1. VERIFICA OS ARGUMENTOS PASSADOS POR LINHA DE COMANDO */
    if(argc < 3){
        std::cout << "Uso : " << argv[0]
        << " <nome do cliente> <IP do servidor>" << std::endl;
        exit(1);
    }

    /* 2. OBTEM O ENDERECO IP e PESQUISA O NOME NO DNS */
    h = gethostbyname(argv[2]);
    if(h == NULL)    {
        std::cout << argv[0] << ": host desconhecido " << argv[1] << std::endl;
        exit(1);
    }
    std::cout << argv[0] << ": enviando dados para " << h->h_name
         << " (IP : " << inet_ntoa(*(struct in_addr *)h->h_addr_list[0]) << ")" << std::endl;
    
    /* 3. CONFIGURANDO AS PROPRIEDADES DA CONEXAO */
    struct sockaddr_in addrServer;
    
    addrServer.sin_family = h->h_addrtype;
    memcpy((char *) &addrServer.sin_addr.s_addr,
    h->h_addr_list[0], h->h_length);
    addrServer.sin_port = htons(REMOTE_SERVER_PORT);
 
    std::cout << "Iniciando cliente..." << std::endl;

    /* 4. CRIANDO O SOCKET (AF_INET = IPV4) DO CLIENTE */
    int socketId_Cliente = socket(AF_INET, SOCK_STREAM, 0);

    if( socketId_Cliente == -1){
        std::cerr << "Falha ao executar o socket do Cliente..." << std::endl;
        exit(EXIT_FAILURE);
    }
    else{
        std::cout << "Socket do Cliente criado com sucesso" << std::endl;
    }
    
    /* 5. ESTABELECENDO CONEXÃO VIA SOCKETS ENTRE O **CLIENTE** E O SERVIDOR */
    if( connect( socketId_Cliente, (struct sockaddr *) &addrServer, sizeof(addrServer) ) == -1 ){
        std::cerr << "Falha ao conectar o socket do Cliente e o do Servidor..." << std::endl;
        exit(EXIT_FAILURE);
    }
    else{
        std::cout << "Cliente conectado ao Servidor (via sockets)..." << std::endl << std::endl;
    }

    /* 6. ENVIANDO NOME DO CLIENTE PARA O SERVIDOR */
    send( socketId_Cliente, argv[1], MAX_MSG, 0 );

    /* 7. CRIAÇÃO DA THREAD DO CLIENTE QUE FAZ COM QUE ELE SEMPRE ESCUTE O SERVIDOR */
    std::thread th (escutarServidor, &socketId_Cliente, addrServer.sin_addr );                    
    th.detach();

    /* 8. LENDO MENSAGEM ENTRADA DO CLIENTE E ENVIANDO PARA O SERVIDOR */
    while( true ){

        memset(bufferServer,0x0,MAX_MSG);
        std::cin.getline(bufferServer, MAX_MSG);
        
        if( std::string(bufferServer) == "sair"){
            send( socketId_Cliente, "sair", MAX_MSG, 0 );
            break;    
        }        

        send( socketId_Cliente, bufferServer, MAX_MSG, 0 );
    }

    /* 9. FECHANDO O SOCKET DO CLIENTE */
    std::cout << "Conexao entre os sockets do Cliente e do Servidor foi quebrada..." << std::endl;
    close(socketId_Cliente);
    
    return EXIT_SUCCESS;

}    

/**
 * @brief   Escutando continuamente as mensagens do servidor
 * @param   arg Conexão do cliente com o servidor
 * @param   addrCliente_sin_addr    Endereço IP do cliente
 */
void escutarServidor( void *arg, struct in_addr addrServidor_sin_addr){
    
	int socketId_Servidor_Conexao = *((int *)arg);
    char msg[MAX_MSG] = ""; //-> Buffer que guardara a mensagem recebida e a que sera enviada

    /// RECEBENDO MENSAGEM DO SOCKET SERVIDOR E IMPRIMINDO
    while (true){
        memset(msg,0x0,MAX_MSG);
        recv( socketId_Servidor_Conexao, msg, MAX_MSG, 0 );
        std::cout << msg << std::endl;
    }

}
