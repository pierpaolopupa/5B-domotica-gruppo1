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
    for (;;) {
        try {
            // leggo una riga da tastiera
            System.out.println("Inserisci temperatura (oppure FINE per uscire): ");
            stringaUtente = tastiera.readLine();

            // se l'utente vuole chiudere
            if (stringaUtente.equalsIgnoreCase("FINE")) {
                System.out.println("Chiusura del client...");
                miosocket.close();
                break;
            }

            // invio la temperatura al server
            outVersoServer.writeBytes(stringaUtente + "\n");

            // attendo la risposta dal server
            stringaRicevutaDalServer = inDalServer.readLine();
            System.out.println("Risposta dal server: " + stringaRicevutaDalServer);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Errore durante la comunicazione col server!");
            System.exit(1);
        }
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

