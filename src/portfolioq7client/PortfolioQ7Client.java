/**
 * Portfolio Question 7
 * SangJoon Lee
 * 30024165
 * 08/06/2021
 */
package portfolioq7client;

import javafx.application.Application;
import javafx.stage.Stage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javax.swing.JOptionPane;

public class PortfolioQ7Client extends Application {
    private Stage stage;
    private Scene scene;
    private VBox vBox;
    private Button openBtn;
    private Button sendBtn;
    private TextField textField;
    static Socket socket;
    private InputStream is;
    private BufferedOutputStream bos;
    private File file;
    static Client client = new Client();
    
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("File Transfer [CLIENT]");
        
        // Buttons
        Button startBtn = new Button("Connect Socket");
        startBtn.setOnAction(e -> startButtonClicked());
        
        openBtn = new Button("Open File");
        openBtn.setOnAction(e -> openButtonClicked());

        sendBtn = new Button("Send File");
        sendBtn.setOnAction(e -> sendButtonClicked());
                
        // TextField
        textField = new TextField();
        
        vBox = new VBox();
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.setSpacing(10);
        vBox.getChildren().addAll(startBtn, openBtn, textField, sendBtn);
        
        scene = new Scene(vBox, 300, 150);
        stage.setScene(scene);
        stage.show();
        
    }
    public void startButtonClicked() {
        try {
            client.connect();
        } catch (IOException e) {
            
        }
        
    }
    public void openButtonClicked() {
        FileChooser fileChooser = new FileChooser();
        file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            textField.setText(file.getName());
        }
    }
    
    public void sendButtonClicked() {
        try {
            if (file != null) {
                client.sendFile(file);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e);
        }
    }
    public static void main(String[] args) throws IOException {
        
        launch(args);
//        client.connect();
    }
}