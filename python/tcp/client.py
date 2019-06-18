#!/usr/bin/env python3
import socket  
from threading import Thread
import sys

HOST = "127.0.0.1"                                  
PORT_NUMBER = 65431                                 
MESSAGE_SIZE = 80
clienteName  = ''                                
if len(sys.argv) > 1:
    clienteName = sys.argv[1]

def always_listening(socketCliente): # Usanda na thread que ficara sempre escultando as mensagens do servidor
    while (True):
        if socketCliente != None:
            data = socketCliente.recv(MESSAGE_SIZE) 
            print(data.decode("utf8"))


with socket.socket( socket.AF_INET, socket.SOCK_STREAM ) as socketCliente:      
    socketCliente.connect( (HOST, PORT_NUMBER) )    
    print("O socket do Cliente está conectado ao socket do Servidor\n") 
    message = str.encode('')

    listening = Thread(target=always_listening, args=(socketCliente,) ) # sempre escutando o servidor
    listening.start()                                                   # Inicia a Thread para escutar o servidor
    socketCliente.send( clienteName.encode("utf8" ) )                   # mandando o nome do cliante para o servidor 


    while (True):
        socketCliente.send( str.encode( input() ) )                     # Enviando mensagem para o socket do Servidor
    socketCliente.close()
            