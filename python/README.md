# Sockets

## Introdução  

Nesta pasta contém a implementação de um socket na linguagem de programacao _Python_ que faz o uso do protocolo da camada de transporte TCP ou UDP.  

- [Requisitos](#requisitos) - Requisitos de software utilizado no projeto
- [Execução](#compilação-e-execução) - Executar o projeto

## Requisitos    

Faz-se necessario que haja o interpretador do Python instalado na maquina a ser utilizada. Porem, caso nao haja previamente instalada, acesse esse [link] para download. Em seguida, dentro dessa pagina web, faca o download e instale o instalador de acordo com o seu sistema operacional.

[link]:https://www.python.org/downloads/

## Execução    


Execute na linha de terminal, para que ocorra a interpretacao, de acordo com o respectivo tipo de socket que deseje utilizar. Diante disso, 
por exemplo, caso seja o socket cliente que faz uso do protocolo [UDP], `client.py`

```
$ python3 ./src/udp/client.py
```  

Dessa forma, sempre sera `pytho3 ./src/*PROTOCOL_NAME*/*CLIENT_OR_SERVER*`.

[UDP]:https://pt.wikipedia.org/wiki/User_Datagram_Protocol
