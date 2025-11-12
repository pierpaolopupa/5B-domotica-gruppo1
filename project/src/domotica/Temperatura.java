package domotica;
import java.io.*;
import java.net.*;

public class Temperatura {
  String nomeServer ="localhost";                   
  int portaServer   = 80;                        
  Socket miosocket;                                
  BufferedReader tastiera;                       
  String stringaUtente;                         
  String stringaRicevutaDalServer;                 
  DataOutputStream outVersoServer;                
  BufferedReader inDalServer;                      

  public void comunica() {
    for (;;)                                     
    try{
      //devo scrivere le operazioni
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
      
      tastiera = new BufferedReader(new InputStreamReader(System.in));
     
      miosocket = new Socket(nomeServer,portaServer);
       
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
    Temperatura cliente = new Temperatura();
    cliente.connetti();
    cliente.comunica();
  }   
}

