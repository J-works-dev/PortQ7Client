package portfolioq7client;

import java.io.*;
import java.net.Socket;
import javax.swing.JOptionPane;

public class Client extends Thread {
    private Socket socket;
    private InputStream is;
    private BufferedInputStream bis;
    private OutputStream os;
    private BufferedOutputStream bos;
    
    public void connect() throws IOException {
        socket = new Socket("localhost", 9999);
        System.out.println(socket.getInetAddress().getLocalHost().getHostName() + ": connected");
        receiveFile(socket);
        closeAll();
    }
    
    public void receiveFile(Socket socket) throws IOException {
        byte[] fileBytes = new byte[1024];
        String fileName = "data.csv";
        is = socket.getInputStream();
        bos = new BufferedOutputStream(new FileOutputStream(fileName, false));

        while (true) {
            int bytesRead = is.read(fileBytes, 0, fileBytes.length);
            bos.write(fileBytes, 0, bytesRead);
            bos.flush();

            if (bytesRead < fileBytes.length) {
                JOptionPane.showMessageDialog(null, "File received from Server");
            }
        }        
    }
    
    public void sendFile(File file) throws IOException {
        byte[] fileBytes = new byte[(int) file.length()];
        os = socket.getOutputStream();

        bis = new BufferedInputStream(new FileInputStream(file));
        bis.read(fileBytes, 0, fileBytes.length);

        os.write(fileBytes, 0, fileBytes.length);
        os.flush();
    }
    
    public void closeAll() throws IOException {
        is.close();
        bis.close();
        os.close();
        bos.close();
        socket.close();
    }
}
