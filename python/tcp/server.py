# import socket programming library 
import socket 

# import thread module 
from _thread import *
import threading 

#print_lock = threading.Lock()                                                     ## CASO DESEJE MULTIPLOS CLIENTE, COMENTE ESTA LINHA

# thread fuction 
def threaded(c, addr): 
    while True: 

        # data received from client 
        data = c.recv(1024) 
        print( "Client (" + str(addr) + ")ask:" + data.decode() )

        if not data: 
            print( "Client (" + str(addr) + ") disconnect")
            break
            # lock released on exit 
 #           print_lock.release()                                            ## CASO DESEJE MULTIPLOS CLIENTE, COMENTE ESTA LINHA 
            

        # reverse the given string from client 
        data = data[::-1] 

        # send back reversed string to client 
        c.send(data) 

    # connection closed 
    c.close() 


def Main(): 
    host = "" 

    # reverse a port on your computer 
    # in our case it is 12345 but it 
    # can be anything 
    port = 14343
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM) 
    s.bind((host, port)) 
    print("socket binded to post", port) 

    # put the socket into listening mode 
    s.listen(5) 
    print("socket is listening") 

    # a forever loop until client wants to exit 
    while True: 

        # establish connection with client 
        c, addr = s.accept() 

        # lock acquired by client 
        #print_lock.acquire()                                                    ## CASO DESEJE MULTIPLOS CLIENTE, COMENTE ESTA LINHA
        print('Connected to :', addr[0], ':', addr[1]) 

        # Start a new thread and return its identifier 
        start_new_thread(threaded, (c,addr ,)) 
    s.close() 


if __name__ == '__main__': 
Main() 
