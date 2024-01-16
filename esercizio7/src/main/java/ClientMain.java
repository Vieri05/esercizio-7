

public class ClientMain {
    public static void main(String[] args) {

        Client client = new Client("localhost", 8000);
        client.connect();
        client.communicate();
    }
}