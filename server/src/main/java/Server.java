/*
  Code is taken from Computer Networking: A Top-Down Approach Featuring
  the Internet, second edition, copyright 1996-2002 J.F Kurose and K.W. Ross,
  All Rights Reserved.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.*;

class Server {
    private static Users users;

    public static void main(String argv[]) throws Exception {
        // Binding to port 1155 which is technically not correct for SFTP but the application needs root privileges
        // to bind to port 115.
        int port = 1155;
        ServerSocket socket = new ServerSocket(port);
        System.out.println("Server listening on port: " + String.valueOf(port));

        try {
            Reader reader = new InputStreamReader(Server.class.getResourceAsStream("/users.json"));
            Gson gson = new GsonBuilder().create();
            users = gson.fromJson(reader, Users.class);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Listen for incoming connections to the server.
        //noinspection InfiniteLoopStatement
        while (true) {
            Socket s = socket.accept();
            Client client = new Client(s);
            client.start();
        }
    }

    static Users getUsers() {
        return Server.users;
    }
}