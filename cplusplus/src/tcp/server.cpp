/**
 * @file    serverSide.cpp
 * @brief   Implementação de arquitetura cliente-servidor (TCP) no lado do servidor
 */
#include <vector>
#include <iostream>
#include <string>
#include <cstring>
#include <thread>
#include <map>
#include <ctime>
#include <chrono>

/// BIBLIOTECAS P/ SOCKETS
#include <arpa/inet.h>
#include <netinet/in.h>  /// AF_INET
#include <netdb.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <unistd.h>

#define LOCAL_SERVER_PORT 4500    /// Numero da porta usada pelo socket do Servidor
#define QUEUE_SIZE_OF_REQUISITIONS 10   /// Tamanho da lista de requisicoes
#define MAX_MSG 100 /// Quantidade de caracteres que uma mensagem pode transmitir  


std::map<std::string, int> conexoesServidorEClientes;

/**
 * @brief   Ocorre a comunicacao entre o servidor e o cliente
 * @param   socketId_Client_Conexao 
 */
void comunicandoComCliente( void* arg, struct in_addr addrCliente_sin_addr);
   
int main(int argc, char* argv[]){


    std::vector<std::thread> threads;


    int socketId_Client_Conexao; //-> Indica se houve falha ou nao na retirada da requisicao da fila de requisicoes
    struct sockaddr_in addrCliente;
    char msg[MAX_MSG] = "";
    int messageSizeReceived;   
    
    /// CONFIGURANDO PROPRIEDADES DE CONEXÃO
    struct sockaddr_in addrServer;
    addrServer.sin_addr.s_addr = htonl( INADDR_ANY );   /// Reconhecendo internamente a **interface de rede** a ser usada
    addrServer.sin_port = htons( LOCAL_SERVER_PORT );
    addrServer.sin_family = AF_INET;

    std::cout << "Iniciando Servidor..." << std::endl;   

    /* 1. CRIA O SOCKET */
    int socketId_Server = socket(AF_INET, SOCK_STREAM, 0);
    
    if( socketId_Server == -1){
        std::cerr << "Falha ao criar o socket do Servidor..." << std::endl;
        exit(EXIT_FAILURE);
    }
    else{
        std::cout << "Socket Servidor criado com sucesso" << std::endl;
    }
    
    /* 2. LIGA O SOCKET CRIADO DO SERVIDOR À PORTA LOCAL_SERVER_PORT */
    if( bind( socketId_Server, (struct sockaddr*) &addrServer, sizeof(addrServer) ) != 0 ){
        std::cerr << "Falha em ligar o socket do Servidor a porta " + std::to_string(LOCAL_SERVER_PORT) << "..." << std::endl;
        exit(EXIT_FAILURE);                
    }
    else{
        std::cout << "A ligacao do socket do Servidor a porta " + std::to_string( LOCAL_SERVER_PORT) + " foi um sucesso" << std::endl;
    }
    
    /* 3. HABILITA O SOCKET DO SERVIDOR A ESCUTAR REQUISIÇÕES */ 
    if( listen( socketId_Server, QUEUE_SIZE_OF_REQUISITIONS ) != 0){
        std::cerr << "Falha em fazer o socket do Servidor escutar requisicoes..." << std::endl;
        exit(EXIT_FAILURE);                           
    }
    else{
        std::cout << "O socket do Servidor esta ouvindo se ha requisicoes..." << std::endl;
    }
    
    while(true){

        /* 4. RETIRANDO REQUISIÇÃO PENDENTE DA CABEÇA DA FILA DE REQUISIÇÕES */
        socklen_t cliente_len = sizeof(addrCliente);
        socketId_Client_Conexao = accept( socketId_Server, (struct sockaddr*)& addrCliente, &cliente_len);

        std::cout << "Socket do Servidor recebeu conexao de " << inet_ntoa(addrCliente.sin_addr) << std::endl;

        if( socketId_Client_Conexao == -1 ){
            std::cerr << "Falha ao retira requisicao da frente da fila de requisicoes..." << std::endl;
            exit(EXIT_FAILURE);
        }    
        
        std::cout << "Requisicao retirada da frente da fila de requisicoes com sucesso" << std::endl;

        /** 5. RECEBENDO NOME DO CLIENTE */
        messageSizeReceived = recv( socketId_Client_Conexao, msg, MAX_MSG, 0 );


        /* 6. ADICIONANDO A CONEXAO ESTABELECIDA AO MAP DE CONEXOES */
        conexoesServidorEClientes[msg] = (int)socketId_Client_Conexao;

        std::cout << "Requisicao retirada da frente da fila de requisicoes com sucesso" << std::endl;

        /* 7. ADICIONANDO COMUNICACAO COM SER */
        std::thread th (comunicandoComCliente, &socketId_Client_Conexao, addrCliente.sin_addr);                    
        th.detach();
       


    }

    //// QUEBRANDO CONEXÃO ENTRE O SOCKET DO SERVIDOR E O DO CLIENTE


    close(socketId_Server);

    std::cout << "Conexao entre os sockets do Servidor e do Cliente foi quebrada..." << std::endl;
    
    return EXIT_SUCCESS;
}    

/**
 * @brief   Ocorre a comunicacao entre o servidor e o cliente
 * @param   socketId_Client_Conexao 
 */
void comunicandoComCliente( void* arg, struct in_addr addrCliente_sin_addr ){
    
    int socketId_Client_Conexao = *((int *) arg);

    char msg[MAX_MSG] = ""; //-> Buffer que guardara a mensagem recebida e a que sera enviada
    int messageSizeReceived; //-> Tamanho da mensagem recebida
    std::string msgAnswer; //-> Resposta do Servidor entrada pelo usuario
    time_t timer;
    struct tm *horarioLocal; 

    /// SOCKET DO SERVIDOR **COMUNICANDO-SE** COM O SOCKET DO CLIENTE
    while (true){

        /// RECEBENDO MENSAGEM DO SOCKET CLIENTE
        memset(msg,0x0,MAX_MSG);
        messageSizeReceived = recv( socketId_Client_Conexao, msg, MAX_MSG, 0 );


        if( strlen(msg) > 0){  /// Situação em que o cliente mandou uma mensagem não vazia

            std::cout << "\nCliente (IP: " << inet_ntoa(addrCliente_sin_addr)  << ") disse: " << msg << "\n";

            /// ENVIANDO MENSAGEM PARA O SOCKET CLIENTE
            time( &timer);
            horarioLocal = localtime( &timer);  

            msgAnswer = "";
            msgAnswer += "Mensagem ";
            msgAnswer += msg;
            msgAnswer += " recebida as 99h99 (";
            msgAnswer += std::to_string(horarioLocal->tm_hour);
            msgAnswer += ":";
            msgAnswer += std::to_string(horarioLocal->tm_min);
            msgAnswer += ":";
            msgAnswer += std::to_string(horarioLocal->tm_sec);
            msgAnswer += ")";

        //   //  send( socketId_Client_Conexao, msgAnswer.c_str(), MAX_MSG, 0 );

            // send(  socketId_Client_Conexao, "BROADCAST...", MAX_MSG, 0 );
            // std::cout << "Enviando para destino\n";
            
            std::this_thread::sleep_for (std::chrono::seconds(3));    
            
            for(std::map<std::string, int>::iterator it = conexoesServidorEClientes.begin(); it != conexoesServidorEClientes.end(); ++it){
                // std::cout << "Enviando para outros\n";
                // std::cout << it->first << " " << inet_ntoa(addrCliente_sin_addr) << "  " <<  it->second << std::endl;

                if( it->second != socketId_Client_Conexao)               
                    send(  it->second, msgAnswer.c_str(), MAX_MSG, 0 );
                else
                    send(  it->second, "ACK", MAX_MSG, 0 );
            }
        }    
    
    }

    close(socketId_Client_Conexao);
    
}
