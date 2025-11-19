package domotica;

import java.io.*;
import java.net.*;
import java.util.*;
import java.time.*;

public class Movimento {
  String nomeServer ="localhost";              
  int portaServer   = 6789;               
  Socket miosocket;                
  Scanner tastiera;                 
  String id;
  String tipo="movimento";
  boolean valore;              
  String zona;
  LocalTime ora;
  DataOutputStream outVersoServer;              
  BufferedReader inDalServer;                  

  public void comunica() {
    for (;;)                                    
    try{
    	outVersoServer.writeBytes("Tipo: "+tipo);
    	
    	String risp;
    	do {
    		System.out.println("È stato rivelato un movimento?");
    		risp=tastiera.nextLine();
    	}while(!risp.equalsIgnoreCase("si") || !risp.equalsIgnoreCase("no"));
    	
    	if(risp.equalsIgnoreCase("si")) {
    		valore=true;
    		}
    	else {
    		valore=false;
    	}
    	
    	outVersoServer.writeBytes("Valore:"+valore);
    	
    	
    	String zona;
    	do {
    		System.out.println("In che zona è stato rilevato il movimento?");
    		zona=tastiera.nextLine();
    	}while(!zona.equalsIgnoreCase("giardino") || !zona.equalsIgnoreCase("cucina") || !zona.equalsIgnoreCase("bagno"));
    	outVersoServer.writeBytes("Zona: "+zona);
    	
    	
    	int ore, minuti;
    	do {
    		System.out.println("Inserisci le ore");
   			ore=tastiera.nextInt();
   		}while(ore<0 || ore>23);
   		do {
   			System.out.println("Inserisci i minuti");
    		minuti=tastiera.nextInt();
    	}while(minuti<0 || minuti>59);
   		ora=LocalTime.of(ore, minuti);
   		outVersoServer.writeBytes("Ora: "+ora);	
    	
    } 
    catch (Exception e) 
    {
      System.out.println(e.getMessage());
      System.out.println("Errore durante la comunicazione col server!");
      System.exit(1);
    }
  }
  
  public Socket connetti(){
    System.out.println("CLIENT partito in esecuzione ...");
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
    Movimento cliente = new Movimento();
    cliente.connetti();
    cliente.comunica();
  }   
}