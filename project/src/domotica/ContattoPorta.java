package domotica;
import java.util.*;


import java.io.*;
import java.net.*;
public class ContattoPorta {
  String nomeServer ="localhost";                    
  int portaServer   = 6789;                        
  Socket miosocket;                                
  Scanner tastiera;
  DataOutputStream outVersoServer;                 
  BufferedReader inDalServer;                      
  String id="1";
  String tipo="contatto";
  boolean valore;
  String zona;
  

  public void comunica() {
    for (;;)                                     
    try{
    	String risp;
    	
    	
    	
    	System.out.println("Il contatto si è interrotto?");
    	risp=tastiera.nextLine();
    	if(risp.equalsIgnoreCase("si"))
    	{
    		valore=true;
    		
    		do {
    			System.out.println("In quale zona?");
        		zona=tastiera.nextLine();
        	}while(!zona.equalsIgnoreCase("ingresso") || !zona.equalsIgnoreCase("cucina") || !zona.equalsIgnoreCase("garage"));
    		outVersoServer.writeBytes("Id: "+id+",\nTipo: "+tipo+",\nValore: "+valore+",\nZona: "+zona);
    		
    	}else{
    		valore=false;
    	}
    	outVersoServer.writeBytes("Id: "+id+",\nTipo: "+tipo+",\nValore: "+valore);
    	System.out.println("Risposta del server: "+inDalServer.readLine());
    	
    	System.out.println("Inserisci 'FINE' se vuoi chiudere la connessione");
		String finito=tastiera.nextLine();
		if(finito.equalsIgnoreCase("FINE")) {
			miosocket.close();
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
      
      tastiera = new Scanner(System.in);
      
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
    ContattoPorta cliente = new ContattoPorta();
    cliente.connetti();
    cliente.comunica();
  }   
}



