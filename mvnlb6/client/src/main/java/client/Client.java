package client;

import client.network.ClientSenderReceiver;

public class Client {
    public static void main(String[] args) {
        ClientSenderReceiver client = new ClientSenderReceiver("localhost", 65437);
        client.run();
    }
}