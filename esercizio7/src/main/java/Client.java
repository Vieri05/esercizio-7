
import java.io.*;
import java.net.*;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class Client {

    int serverPort;
    String serverAddress;

    Socket client;
    BufferedReader input;
    PrintWriter output;

    XmlMapper mapper = new XmlMapper();

    public Client(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public Socket connect(){

        try {
            client = new Socket(serverAddress, serverPort);
            
            input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            output = new PrintWriter(client.getOutputStream(), true);

            System.out.println("Connesso all'host: " + serverAddress + ":" + serverPort);
        } catch (UnknownHostException e) {
            System.err.println("Host sconusciuto");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("OPS...Qualcosa è andato storto!!!, chiusura client");
            System.exit(1);
        }

        return client;
    }

    public void communicate(){

        String xml;

        try {
            while(!(xml = input.readLine()).isEmpty()){
                Persona p = mapper.readValue(xml, Persona.class);
                System.out.println("Nome: " + p.nome + ";\nCognome: " + p.cognome + ";\nSesso: " + "\nEta': " + p.eta + ";");
                closeConnection();
            }
        
        } catch (IOException e) {
            System.out.println("OPS...Qualcosa è andato storto!!!");
        }
    }

    public void closeConnection(){

        try {
            output.close();
            input.close();
            client.close();
        } catch (IOException e) {
            System.out.println("OPS...Qualcosa è andato storto!!!");
        }
    }

}