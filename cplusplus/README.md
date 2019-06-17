# Sockets

## Introdução  

Nesta pasta contém a implementação de um socket na linguagem de programacao C++ que faz o uso do protocolo da camada de transporte TCP ou UDP.

- [Compilação e execução](#compilação-e-execução) - Como compilar e executar o projeto

## Compilação e execução  

execute na linha de terminal para a compilação e criação do objetos

```
$ make
```  
Ou, caso deseje apagar os objetos e os executáveis, digite  

```
$ make clean
```  
Em seguida, serão criados os seguintes arquivos binários (executáveis):

| Nome do executável: | Descrição: | 
| ---------- | ------------- |
|`udpClient` 	|Programa que implementa socket cliente que faz uso do protocolo UDP.  
|`udpServer` 	|Programa que implementa socket servidor que faz uso do protocolo UDP.  
|`tcpClient` 	|Programa que implementa socket cliente que faz uso do protocolo TCP.  
|`tcpServer` 	|Programa que implementa socket servidor que faz uso do protocolo TCP.  
  
Diante disso, para a execução dos executáveis teremos que no caso dos servidores, tanto TCP quanto UDP, basta executar o arquivo sem parâmetro nenhum. Por exemplo, no caso do servidor TCP, `tcpServer`, temos que:  

```
$ ./bin/tcpServer
```
Em contrapartida, no caso dos clientes, haverá a necessidade de inserir alguns argumentos. Pois, será necessária que haja o endereço IP do servidor para o estabelecimento da conexão e também o cliente deverá enviar o seu nome de usuário para o servidor cadastrá-lo, no caso em que é um cliente TCP. Por isso, por exemplo, para o cliente TCP, teremos que:

```
$ ./bin/tcpClient Lucas localhost
```  
Além disso, caso deseje ver a documentação desses códigos, execute  

```
$ make doc
```  
Em seguida, vá até a pasta **doc** e abra o arquivo `index.html` em um navegador qualquer.   



