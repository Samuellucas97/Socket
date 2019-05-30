# Sockets

## Introdução  

Nesta pasta contém a implementação de um socket nas linguagens de programacao C++, Java e Python que faz o uso do protocolo da camada de transporte TCP ou UDP referente ao trabalho da 2a unidade da disciplina de _Redes de Computadores_ ministrada pelo dr. Silvio da Costa Sampaio.

- [Objetivo](#objetivo) - Objetivo da implementacao.
- [Compilação e execução](#compilação-e-execução) - Como compilar e executar o projeto
- [Autores](#autores) - Autores do projeto.


## Objetivo  

Explorar aspectos teóricos e práticos sobre sockets.

## Compilação e execução  

### Codigos em Cplusplus

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
  
Diante disso, basta executa o respectivo código de acordo como o nome do executável. Logo, por exemplo, caso seja o `udpClient`  

```
$ ./bin/udpClient
```
  
Além disso, caso deseje ver a documentação desses codigos, execute  

```
$ make doc
```  
Em seguida, vá até a pasta **doc** e abra o arquivo `index.html` em um navegador qualquer.   
## Autores  
Felipe Tiberio Maciel Barbosa (_email_), Clenilson Jose Silva de Sousa (_email_), e Samuel Lucas de Moura Ferino ( _samuellucas97@ufrn.edu.br_ ).      
:copyright: IMD/UFRN 2019. 


