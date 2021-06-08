package portfolioq7client;

import java.io.*;
import java.net.ServerSocket;
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
//        run();
        ReceiveThread rt = new ReceiveThread();
        rt.setSocket(socket);
        rt.setIs(is);
        rt.setBos(bos);
    }
    
    @Override
    public void run() {
        try {
            byte[] fileBytes = new byte[1024];
        
            String fileName = "data.csv";
    //        BufferedReader buf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    //        String receivedString = buf.readLine();
    //        String[] clientMsg = receivedString.split(",");
    //        if (clientMsg[0].equals("fileName:")) {
    //            fileName = clientMsg[1];
    //        }

            is = socket.getInputStream();
            bos = new BufferedOutputStream(new FileOutputStream(fileName));

            while (true) {
                int bytesRead = is.read(fileBytes, 0, fileBytes.length);
                bos.write(fileBytes, 0, bytesRead);
                bos.flush();

                if (bytesRead < fileBytes.length) {
                    JOptionPane.showMessageDialog(null, "File received from Server");
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e);
        } finally {
            try {
                closeAll();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error: " + e);
            }
        }
    }
    public void receiveFile(Socket socket) throws IOException {

        
    }
    
    public void sendFile(File file) throws IOException {
        PrintWriter pw = new PrintWriter(socket.getOutputStream()); 
        pw.println("fileName:," + file.getName());
        pw.flush();
        pw.close();
        
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