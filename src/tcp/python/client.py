#!/usr/bin/env python3

import socket  # Biblioteca para sockets

HOST = "127.0.0.1"  # (localhost)
PORT_NUMBER = 4324  # Porta usada pelo socket do Servidor
MESSAGE_SIZE = 40 # Quantidade de caracteres que uma mensagem pode transmitir  


with socket.socket( socket.AF_INET, socket.SOCK_STREAM ) as s:      # Criando o socket do Cliente
    s.connect( (HOST, PORT_NUMBER) )    # Conectando socket do Cliente ao socket do Servidor
    print("O socket do Cliente está conectado ao socket do Servidor\n")
    while (True):
        s.send( str.encode( input() ) )  # Enviando mensagem para o socket do Servidor
        data = s.recv(MESSAGE_SIZE) # Recebendo mensagem do socket do Servidor
        print("Servidor disse: " + data.decode() )
        if not data or data.decode() == "tchau":
            break
            