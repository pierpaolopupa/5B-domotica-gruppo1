package domotica;

import java.net.*;
import java.io.*;
import java.time.LocalTime;

class ServerThread extends Thread {
    Socket client = null;
    BufferedReader inDalClient;
    DataOutputStream outVersoClient;

    private final LocalTime ORA_LIMITE = LocalTime.of(22, 0);

    public ServerThread(Socket socket) {
        this.client = socket;
    }

    public void run() {
        try {
            comunica();  
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public void comunica() throws Exception {
        inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
        outVersoClient = new DataOutputStream(client.getOutputStream());

        String messaggioRicevuto;

        for (;;) {
            messaggioRicevuto = inDalClient.readLine(); 

            if (messaggioRicevuto == null || messaggioRicevuto.equalsIgnoreCase("FINE")) {
                outVersoClient.writeBytes("Connessione chiusa.\n");
                break;
            }

            System.out.println("Messaggio ricevuto: " + messaggioRicevuto);

            String risposta = elaboraMessaggio(messaggioRicevuto);

            outVersoClient.writeBytes(risposta + "\n");
        }

        outVersoClient.close();
        inDalClient.close();
        client.close();
        System.out.println("Chiusura socket: " + client);
    }

    private String elaboraMessaggio(String msg) {
        msg = msg.trim().toUpperCase();

        if (msg.startsWith("TEMP:")) {
            try {
                double temperatura = Double.parseDouble(msg.substring(5).trim());
                if (temperatura > 35) {
                    return "ALLARME: Temperatura troppo alta (" + temperatura + "°C)";
                } else {
                    return "Temperatura OK (" + temperatura + "°C)";
                }
            } catch (NumberFormatException e) {
                return "Errore: formato temperatura non valido";
            }
        }

        if (msg.startsWith("MOVIMENTO:")) {
            try {
                String oraStr = msg.substring(10).trim();
                LocalTime oraMovimento = LocalTime.parse(oraStr);
                if (oraMovimento.isAfter(ORA_LIMITE)) {
                    return "ALLARME: Movimento rilevato dopo l’ora limite (" + ORA_LIMITE + ")";
                } else {
                    return "Movimento rilevato in orario consentito";
                }
            } catch (Exception e) {
                return "Errore: formato ora non valido (usa HH:MM)";
            }
        }

        return "Comando non riconosciuto";
    }
}

public class Server {
    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(6789);
            System.out.println("Server avviato sulla porta 6789.");

            for (;;) {
                System.out.println("In attesa di connessioni...");
                Socket socket = serverSocket.accept(); 
                System.out.println("Client connesso: " + socket);

                ServerThread serverThread = new ServerThread(socket);
                serverThread.start();
            }
        } catch (Exception e) {
            System.out.println("Errore server: " + e.getMessage());
            System.exit(1);
        }
    }

   
    public static void main(String[] args) {
        Server tcpServer = new Server();
        tcpServer.start(); 
    }
}
