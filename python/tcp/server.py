#!/usr/bin/env python3
from datetime import datetime   # Necessario para pegar a hora do sistema 
import socket                   # Biblioteca para sockets
import traceback
from threading import Thread
import sys

HOST = "127.0.0.1"  # (localhost)
PORT_NUMBER = 65431  # Porta usada pelo socket do Servidor

socketsClients = {} # dicionário de clientes 
connClients = {}
now = datetime.now()

def start_server(): # apenas inicia o server
    server()

def client_thread(connection, ip, port, clientes={},  clientName='' ,max_buffer_size = 10000): # Essa funcão vai gerenciar cada uma das threads 
        is_active = True

        try:
            while is_active: 
                
                client_input = receive_input(connection, max_buffer_size, ip, port)
                if "Q" == client_input or "sair " == client_input or "Sair" == client_input  :      # Condicional para a remocao do cliente do chat
                    
                    print("Cliente"+ clientName +" esta requisitando a finalização da comunicação.")
                    print("Conexao  " + ip + ":" + port + " fechada.")
                    connection.send("finalizar".encode("utf8"))
                    connection.close()
                    is_active = False
                    del clientes[str(ip + ":" + port)]
                    for soc in clientes:                                                            # broadcast para os clientes
                        clientes[soc].send((clientName + " saiu do chat." ).encode("utf8"))


                elif client_input != '':
                    print( clientName + " disse :" +  client_input)                                 # imprime a messagem do cliente no servidor
                    for soc in clientes:                                                            # broadcast para os clientes
                        if soc != str(ip + ":" + port):
                            clientes[soc].send((clientName + " disse :" +  client_input  ).encode("utf8"))

        except:
            print("Cliente"+ clientName +" esta requisitando a finalização da comunicação.")
            print("Conexao  " + ip + ":" + port + " fechada.")
            del clientes[str(ip + ":" + port)]
            for soc in clientes:                                                            # broadcast para os clientes
                clientes[soc].send((clientName + " saiu do chat." ).encode("utf8"))
            

                

def receive_input(connection, max_buffer_size, ip='yyy', port='yyy'):               # Faz aquilo que o nome diz, recebe input dos clientes
    client_input = connection.recv(max_buffer_size)
    client_input_size = sys.getsizeof(client_input)
    
    if client_input_size > max_buffer_size:
        print("O tamnho do input passado e maior que o permitido : {}".format(client_input_size))

    connection.send(("Mensagem  recebida as " +now.strftime("%H:%M:%S")).encode("utf8"))
    result = decoded_input = client_input.decode("utf8").rstrip()                   # decode and strip end of line

    return result


def server():
    with socket.socket( socket.AF_INET, socket.SOCK_STREAM ) as socketServidor:      # Criando o socket do Servidor
        socketServidor.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    
        try:
            socketServidor.bind( ( HOST, PORT_NUMBER ) )                             # Ligando o socket do Servidor a porta PORT_NUMBER 
        except:
            print("Bind falhou,erro ao criar o vinculo. Erro: " + str(sys.exc_info())) 
            sys.exit()

        socketServidor.listen( )                                                    # Colocando o socket do Servidor para escutar as requisições  
       
        print( "O socket do Servidor está escutando com sucesso...\n")

        
        
        online = True

        while (online):
            connection, address = socketServidor.accept()                            # Retirando da fila de requisições uma requisição
            ip, port = str(address[0]), str(address[1])
            key_socket = str(ip+':'+port)                                            # chave usando no dicinario para endentificar cada um dos clientes
            clientName = connection.recv(100).decode("utf8").rstrip()

            print("- Conectado com o cliente " + clientName +" com o socket; [" + ip + ":" + port + "] -")

            
            for soc in connClients:                                          
                if soc != str(ip + ":" + port):
                    connClients[soc].send((clientName + " entou no chat " ).encode("utf8"))

            try:
                if key_socket not in socketsClients:                                # Aqui coloco no dicionario cada uma das threads
                    connClients[key_socket] =  connection
                    socketsClients[key_socket] = Thread(target=client_thread, args=(connection, ip, port, connClients, clientName))
            
            except:
                print("A thread para ."  + ip + ":" + port + " não iniciou.")
                traceback.print_exc()

            socketsClients[key_socket].start()                                      # inicia a thread da exercução corrente

        socketServidor.close()


start_server()