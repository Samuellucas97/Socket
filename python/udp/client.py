import socket 
import sys
import pickle

def main():    

    ## 1. VERIFICANDO ARGUMENTOS  
    if len(sys.argv) != 3:
        print("uso: python3 client.py <IP do servidor> <mensagem>")
        sys.exit()

    host = sys.argv[1]
    portNumber = 65431
    message = sys.argv[2]
    server_address = (host, portNumber)

    ## 2. CRIANDO SOCKET CLIENTE 
    clientSocket = socket.socket( socket.AF_INET, socket.SOCK_DGRAM)
    print('Iniciando socket do cliente...')
        
    try:
        ## 3. ENVIANDO MENSAGEM PARA O SERVIDOR
        sent = clientSocket.sendto(pickle.dumps( message ), server_address)

        ## 4. RECEBENDO MENSAGEM DO SERVIDOR
        data, server = clientSocket.recvfrom(1024)
        messageResponse = pickle.loads(data)
        print(messageResponse)

    finally:
        print('Fechando socket do cliente')
        clientSocket.close()

if __name__=='__main__':
    main()
