

import java.io.*;
import java.net.*;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class Server {

    int porta;
    ServerSocket server;
    Socket client;
    BufferedReader in;
    PrintWriter out;

    XmlMapper mapper = new XmlMapper();

    public Server(int porta) {

        this.porta = porta;
        try {
            server = new ServerSocket(porta);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void connect() {

        try {
            System.out.println("Attendo connessione");
            client = server.accept();

            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);

            System.out.println("Client connesso: " + client.getInetAddress() + ":" + porta);

        } catch (IOException e) {

        }
    }
    
    public void communicate() {

        try {

            Persona p = new Persona("Lorenzo", "Lasagni", 4);
            String xml = mapper.writeValueAsString(p);
            System.out.println(xml);
            out.println(xml);
            closeConnection();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("OPS...Qualcosa è andato storto!!!");
        }
    }

    public void closeConnection() {

        System.out.println("chiusura connessione");
        try {
            in.close();
            out.close();
            client.close();
        } catch (Exception e) {
            System.out.println("OPS...Qualcosa è andato storto!!!");
        }
    }
       
}
