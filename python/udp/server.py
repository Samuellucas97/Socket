#!/usr/bin/env python3
from datetime import datetime
import socket

HOST = '127.0.0.1'
PORT_NUMBER = 65431
MESSAGE_SIZE = 40

with socket.socket( socket.AF_INET, socket.SOCK_DGRAM) as socketServer:
    socketServer.bind((HOST, PORT_NUMBER))
    print("Ligacao entre socjet Servidor e porta " + str(PORT_NUMBER))
    print( "O socket do Servidor está escutando com sucesso...\n")

    while (True):
        now = datetime.now()
        data, address = socketServer.recvfrom(4096)
        print("Foi recebido " + str(len(data)) + " bytes de " + str(address[0]) )
        print(data)


        if data:
            backMassagestr = ('Mensagem ERROAQUI recebida as ' + now.strftime("%H:%M:%S") )
            backMassage = bytes(backMassagestr, 'utf-8')
            sent = socketServer.sendto( backMassage, address)
            