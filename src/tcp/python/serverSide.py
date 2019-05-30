#!/usr/bin/env python3

import socket  # Biblioteca para sockets

HOST = "192.168.7.1"  # (localhost)
PORT_NUMBER = 4324  # Porta usada pelo socket do Servidor
MESSAGE_SIZE = 40 # Quantidade de caracteres que uma mensagem pode transmitir  


with socket.socket( socket.AF_INET, socket.SOCK_STREAM ) as s:      # Criando o socket do Servidor
    s.bind( ( HOST, PORT_NUMBER ) )     # Ligando o socket do Servidor a porta PORT_NUMBER
    print( "Ligacao entre socket Servidor e porta " +  str(PORT_NUMBER) + " executada com sucesso...\n")
    s.listen( )     # Colocando o socket do Servidor para escutar as requisições  
    print( "O socket do Servidor está escutando com sucesso...\n")
    conn, addr = s.accept()      # Retirando da fila de requisições uma requisição
    with conn:
        print( "Connected by " + str(addr) + "\n")
        while True:
            data = conn.recv(MESSAGE_SIZE)  # Recebendo mensagem do socket do Cliente
            print("Cliente disse:" + data.decode() + "\n")
            if not data or data.decode() == "tchau":
                conn.send( str.encode( "tchau" ) )
                break
            conn.send( str.encode( input() ) ) # Enviando mensagem para socket do Cliente
