
## Algumas instruções rápidos de como utilizar o chat em TCP.

  

- Ambos os arquivos podem ser utilizados alterando as configurações das variáveis globais, ou apenas passando os dados por argumentos. Por padrão coloquei como endereço de porta do servidor localhost e porta 65431.

  
  

## instruções para a utilização do chat.

	1. execute python3 server.py, para iniciar o server. Caso seja

	necessaŕio pode-se passar o endereço ip e a porta do servidor como argumento executando

	$ python3 server.py "endereçp ip" "porta"

	3. Uma vez que o servidor está ativo, os clientes podem se conectar executando:

	$ python3 client.py "nome do cliente"

	Claro, estou supondo que as variáveis globais já estão configuradas com os endereços do server.

	Caso não estejam pode-se passa-las utilizando argumentos na exercução de cliente.py:

	$ python3 client.py "nome do cliente" "endereço servidor" "porta"
		

