package domotica;

import java.io.*;
import java.net.*;
public class ContattoPorta {
  String nomeServer ="localhost";                  // indirizzo server locale  
  int portaServer   = 6789;                        // porta x servizio data e ora
  Socket miosocket;                                
  BufferedReader tastiera;                         // buffer per l'input da tastiera
  DataOutputStream outVersoServer;                 // stream di output
  BufferedReader inDalServer;                      // stream di input 
  String id;
  

  public void comunica() {
    for (;;)                                     // ciclo infinito: termina con FINE
    try{
      
      }
    } 
    catch (Exception e) 
    {
      System.out.println(e.getMessage());
      System.out.println("Errore durante la comunicazione col server!");
      System.exit(1);
    }
  }
  
  public Socket connetti(){
    System.out.println("2 CLIENT partito in esecuzione ...");
    try{
      // input da tastiera
      tastiera = new BufferedReader(new InputStreamReader(System.in));
      //  miosocket = new Socket(InetAddress.getLocalHost(), 6789);
      miosocket = new Socket(nomeServer,portaServer);
      // associo due oggetti al socket per effettuare la scrittura e la lettura 
      outVersoServer = new DataOutputStream(miosocket.getOutputStream());
      inDalServer    = new BufferedReader(new InputStreamReader (miosocket.getInputStream()));
    } 
    catch (UnknownHostException e){
      System.err.println("Host sconosciuto"); } 
    catch (Exception e){
      System.out.println(e.getMessage());
      System.out.println("Errore durante la connessione!");
      System.exit(1);
    }
    return miosocket;
  }

  public static void main(String args[]) {
    ContattoPorta cliente = new ContattoPorta();
    cliente.connetti();
    cliente.comunica();
  }   
}



