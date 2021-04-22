package io;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Server {
    private static final String SERVER_DIR = "F:\\serverFolder\\";
    private final ConcurrentLinkedQueue<ClientHandler> clients;

    public Server(int port) {
        clients = new ConcurrentLinkedQueue<>();
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("Server started!");
            File myFile = new File(SERVER_DIR + "s.pdf");
            while (true) {
                int bytesRead;
                Socket socket = server.accept();
                InputStream inputStream = socket.getInputStream();
                DataInputStream clientData = new DataInputStream(inputStream);
                String fileName = clientData.readUTF();
                OutputStream output = new FileOutputStream(SERVER_DIR + fileName);
                BufferedOutputStream bos = new BufferedOutputStream(output);
                long size = clientData.readLong();
                byte[] buffer = new byte[1024];
                bytesRead = inputStream.read(buffer, 0, buffer.length);
                bos.write(buffer, 0, bytesRead);

                while (size > 0 && (bytesRead = clientData.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
                    output.write(buffer, 0, bytesRead);
                    size -= bytesRead;
                }
            }
        } catch (Exception e) {
            System.err.println("Server was broken");
        }
    }

    public void kickClient(ClientHandler handler) {
        clients.remove(handler);
    }
}
