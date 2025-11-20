package domotica;
import java.io.*;
import java.net.*;

public class Temperatura {

    String nomeServer = "localhost";
    int portaServer = 6789;
    Socket miosocket;
    BufferedReader tastiera;
    DataOutputStream outVersoServer;
    BufferedReader inDalServer;

    String ID = "001"; 

    public void comunica() {
        try {
            for (;;) {
                System.out.println("Inserisci temperatura (oppure FINE per uscire): ");

                String input = tastiera.readLine();

                if (input.equalsIgnoreCase("FINE")) {
                    outVersoServer.writeBytes("FINE\n");
                    System.out.println("Chiusura del client...");
                    miosocket.close();
                    break;
                }

                try {
                    Double.parseDouble(input);
                } catch (NumberFormatException e) {
                    System.out.println("Inserire un numero valido!");
                    continue;
                }

                
                outVersoServer.writeBytes("ID: " + ID + " | TEMP: " + input + "\n");

                String risposta = inDalServer.readLine();
                System.out.println("Risposta dal server: " + risposta);
            }
        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    public Socket connetti() {
        System.out.println("Client avviato...");
        try {
            tastiera = new BufferedReader(new InputStreamReader(System.in));
            miosocket = new Socket(nomeServer, portaServer);
            outVersoServer = new DataOutputStream(miosocket.getOutputStream());
            inDalServer = new BufferedReader(new InputStreamReader(miosocket.getInputStream()));
        } catch (Exception e) {
            System.out.println("Errore di connessione: " + e.getMessage());
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



