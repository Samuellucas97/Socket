
#!/usr/bin/env python3

import socket  # Biblioteca para sockets
from threading import Thread

HOST = "127.0.0.1"  # (localhost)
PORT_NUMBER = 65431 # Porta usada pelo socket do Servidor
MESSAGE_SIZE = 40 # Quantidade de caracteres que uma mensagem pode transmitir  

def always_listening(socketCliente):
    while (True):
        if socketCliente != None:
            data = socketCliente.recv(MESSAGE_SIZE) # Recebendo mensagem do socket do Servidor
            print(data.decode())


with socket.socket( socket.AF_INET, socket.SOCK_STREAM ) as socketCliente:      # Criando o socket do Cliente
    socketCliente.connect( (HOST, PORT_NUMBER) )    # Conectando socket do Cliente ao socket do Servidor
    print("O socket do Cliente está conectado ao socket do Servidor\n") 
    message = str.encode('')

    listening = Thread(target=always_listening, args=(socketCliente,) ) # sempre escutando o servidor
    listening.start()

    while (True):
        socketCliente.send( str.encode( input() ) )  # Enviando mensagem para o socket do Servidor
        
        #always_listening(socketCliente)
        

    socketCliente.close()
            