# Sockets

## Introdução  

Nesta pasta contém a implementação de um socket na linguagem de programacao _Python_ que faz o uso do protocolo da camada de transporte TCP ou UDP.  

- [Requisitos](#requisitos) - Requisitos de software utilizado no projeto
- [Execução](#compilação-e-execução) - Executar o projeto

## Requisitos    

Faz-se necessario que haja o interpretador do Python instalado na maquina a ser utilizada. Porem, caso nao haja previamente instalada, acesse esse [link] para download. Em seguida, dentro dessa pagina web, faca o download e instale o instalador de acordo com o seu sistema operacional.

[link]:https://www.python.org/downloads/

## Execução    


Execute na linha de terminal, para que ocorra a interpretacao, de acordo com o respectivo tipo de socket que deseje utilizar. Diante disso, lembrando que em ambos é necessário iniciar o server.py primeiro. Abaixo temos a execucao do servidor TCP.

```
$ python3 ./src/tcp/server.py
```  
E, para o servidor UDP, temos que sera:

```
$ python3 ./src/udp/server.py
```  
Em seguida, de acordo com o tipo de cliente, UDP ou TCP, a execucao e diferente. No cliente UDP temos que sera:

```
$ python3 ./src/udp/client.py <IP do servidor> <mensagem>
```  
Ja no cliente TCP, temos que sera da seguinte forma: `

```
$ python3 ./src/tcp/client.py <IP do servidor> <mensagem>
```  

[UDP]:https://pt.wikipedia.org/wiki/User_Datagram_Protocol
