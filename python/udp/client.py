import socket 

HOST = "127.0.0.1"
PORT_NUMBER = 65431

with socket.socket( socket.AF_INET, socket.SOCK_DGRAM) as socketCliente:
    socketCliente.connect ((HOST, PORT_NUMBER))
    print("O socket do Cliente está conectado ao socket do Servidor\n") 
    message = str.encode('')

    while (message.decode() != "Sair"):
        server_address = (HOST, PORT_NUMBER)
        message = str.encode( input() )
        sent = socketCliente.sendto( message, server_address  )  # Enviando mensagem para o socket do Servidor
        
        data = socketCliente.recvfrom(4096) # Recebendo mensagem do socket do Servidor
        
        print("Servidor disse: ", data )


    socketCliente.close()

