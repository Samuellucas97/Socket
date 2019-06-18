import socket 
import sys
import pickle


def main():    
    
    ## VERIFICANDO ARGUMENTOS  
    if(sys.argv[1] || sys.argv[2])
        print("uso: python3 client.py <IP do servidor> <mensagem>")
        sys.exit()

    host = sys.argv[1]
    portNumber = 65431
    message = sys.argv[2]
    server_address = (host, portNumber)

    ## CRIANDO SOCKET CLIENTE 
    clientSocket = socket.socket( socket.AF_INET, socket.SOCK_DGRAM)
            
    try:

        ## ENVIANDO PARA O SERVIDOR
        sent = sock.sendto(message, server_address)

        # Receive response
        data, server = sock.recvfrom(4096)
        print('received {!r}'.format(data))

    finally:
        print('closing socket')
        sock.close()




if __name__=='__main__':
    main()




    print("O socket do Cliente está conectado ao socket do Servidor\n") 
    message = str.encode('')

    while (message.decode() != "Sair"):
        s
        message = str.encode( input() )
        sent = socketCliente.sendto( message, server_address  )  # Enviando mensagem para o socket do Servidor
        
        data = socketCliente.recvfrom(4096) # Recebendo mensagem do socket do Servidor
        
        print("Servidor disse: ", data )


    socketCliente.close()

