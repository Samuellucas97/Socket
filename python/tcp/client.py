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

def always_listening(socketCliente):  
    online = True                                  # Usanda na thread que ficara sempre escultando as mensagens do servidor
    while (online):
       
        data = socketCliente.recv(MESSAGE_SIZE) 
        if data.decode("utf8") == 'finalizar':
            online = False
            pass
            sys.exit()

        print(data.decode("utf8"))

    


with socket.socket( socket.AF_INET, socket.SOCK_STREAM ) as socketCliente:      
    socketCliente.connect( (HOST, PORT_NUMBER) )    
    print("O socket do Cliente está conectado ao socket do Servidor\n") 
    message = str.encode('')

    listening = Thread(target=always_listening, args=(socketCliente,) ) # sempre escutando o servidor
    listening.start()     
                                                                        
    socketCliente.send( clienteName.encode("utf8" ) )                   # mandando o nome do cliante para o servidor 
    online = True

    while (online):
        mens = str.encode( input() )
        socketCliente.send( mens )                     # Enviando mensagem para o socket do Servidor
        if "Q" == mens or "sair " == mens or "Sair" == mens  : 
            online = False

    socketCliente.close()

sys.exit()

            