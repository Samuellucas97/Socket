import socket
import pickle
from datetime import datetime

def main():  
	host = socket.gethostbyname( socket.gethostname() )
	portNumber = 65431

    ## 1. CRIANDO SOCKET SERVIDOR
	serverSocket = socket.socket( socket.AF_INET, socket.SOCK_DGRAM)
	print('Iniciando socket do servidor (IP: ' + str(host) + ')')

    ## 2. LIGANDO SOCKET À PORTA 
	serverSocket.bind( (host, portNumber) )
	print('Ligando-se socket à porta ' + str(portNumber) + '\n')
    
	try:
		while True:
			## 3. RECEBENDO MENSAGEM DE CLIENTE
			data, address = serverSocket.recvfrom(1024)
			message = pickle.loads(data)
			print(message + ': de ' + str(address[0]) + ':UDP(' + str(portNumber) + ')' )
		
			if data:
				## 4. RESPONDENDO MENSAGEM DE CLIENTE
				hourNow = datetime.now()
				messageResponse = ('Mensagem ' + message +' recebida as ' + hourNow.strftime("%H:%M:%S") )
				serverSocket.sendto( pickle.dumps(messageResponse), address)
    
	finally:
		print('Fechando socket do servidor')
		serverSocket.close()
    
if __name__=='__main__':
	main()
    
