#!/usr/bin/env python3
from datetime import datetime   # Necessario para pegar a hora do sistema 
import socket                   # Biblioteca para sockets
import traceback
from threading import Thread
import sys

HOST = "127.0.0.1"  # (localhost)
PORT_NUMBER = 65431  # Porta usada pelo socket do Servidor
MESSAGE_SIZE = 40 # Quantidade de caracteres que uma mensagem pode transmitir  

def start_server():
    server()

def client_thread(connection, ip, port, max_buffer_size = 5120): # Essa funcão vai gerenciar cada uma das threads 
        is_active = True

        while is_active: 
            client_input = receive_input(connection, max_buffer_size)

            if "Q" in client_input:
                print("Cliente está requisitando a finalização da comunicação.")
                connection.close()
                print("Conexão  " + ip + ":" + port + " fechada.")
                is_active = False
            else:
                print(": {}".format(client_input))
                connection.sendall("-".encode("utf8"))

def receive_input(connection, max_buffer_size):
    client_input = connection.recv(max_buffer_size)
    client_input_size = sys.getsizeof(client_input)

    if client_input_size > max_buffer_size:
        print("O tamnho do input passado é maior que o permitido : {}".format(client_input_size))

    decoded_input = client_input.decode("utf8").rstrip()  # decode and strip end of line
    result = process_input(decoded_input)

    return result

def process_input(input_str, ip='', port=''):
    print("Processando a mensagem recebido pelo cliente...")
    return ( str(input_str).upper())


def server():
    with socket.socket( socket.AF_INET, socket.SOCK_STREAM ) as socketServidor:      # Criando o socket do Servidor
        socketServidor.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    
        try:
            socketServidor.bind( ( HOST, PORT_NUMBER ) )     # Ligando o socket do Servidor a porta PORT_NUMBER 
        except:
            print("Bind falhou,erro ao criar o vinculo. Erro: " + str(sys.exc_info())) 
            sys.exit()

        socketServidor.listen( )     # Colocando o socket do Servidor para escutar as requisições  
       
        print( "O socket do Servidor está escutando com sucesso...\n")
        while (True):
            connection, address = socketServidor.accept()      # Retirando da fila de requisições uma requisição
            ip, port = str(address[0]), str(address[1])
            print("Conectado com  " + ip + ":" + port)

            try:
                Thread(target=client_thread, args=(connection, ip, port)).start()
            except:
                print("A thread para ."  + ip + ":" + port + " não iniciou.")
            traceback.print_exc()

        socketServidor.close()

start_server()