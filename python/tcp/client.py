#!/usr/bin/env python3
import socket  
from threading import Thread
import sys

HOST = "127.0.0.1"                                  
PORT_NUMBER = 65431                                 
MESSAGE_SIZE = 80
clienteName  = ''    

if len(sys.argv) == 2:          # opcoes para que se possar passar o enderecoe porta do servidor como argumentos
    clienteName =   sys.argv[1]
if len(sys.argv) == 3:
    clienteName =   sys.argv[1]
    HOST =          sys.argv[2]
if len(sys.argv) == 4:
    clienteName =   sys.argv[1]
    HOST =          sys.argv[2]
    PORT_NUMBER =   sys.argv[3]


if len(sys.argv) > 1:
    clienteName = sys.argv[1]


class Always_listening  (Thread):
    def __init__(self, socketCliente):
        super(Always_listening, self).__init__()
        self._is_running = True

    def run(self):
        online = True                                # Usanda na thread que ficara sempre escultando as mensagens do servidor
        while (self._is_running):
            try:
                data = socketCliente.recv(MESSAGE_SIZE) 
                if data.decode("utf8") == 'finalizar':
                    self._is_running = False
                    socketCliente.close()
                    return
                  
                else:
                    print(data.decode("utf8"))
            except:
                self._is_running = False
                socketCliente.close()
                return
    def stop(self):
        self._is_running = False


with socket.socket( socket.AF_INET, socket.SOCK_STREAM ) as socketCliente:      
    socketCliente.connect( (HOST, PORT_NUMBER) )    
    print("O socket do Cliente está conectado ao socket do Servidor\n") 
    message = str.encode('')

    listening = Always_listening(socketCliente)
    
    listening.start()     
                                                                        
    socketCliente.send( clienteName.encode("utf8" ) )                   # mandando o nome do cliante para o servidor 
    online = True

    while (online):
        mens = str.encode( input() )
        socketCliente.sendall( mens )                                       # Enviando mensagem para o socket do Servidor
        if "Q" == mens or "sair " == mens or "Sair" == mens  : 
            online = False
            print("valor de mens=" + mens)
            listening.stop()
    socketCliente.close()

sys.exit()

            