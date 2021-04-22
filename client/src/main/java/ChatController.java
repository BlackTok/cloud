import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatController implements Initializable {
    public static final String CLIENT_DIR = "clientDir";
    public ListView<String> listView;
    public TextField input;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    public void send(ActionEvent actionEvent) throws IOException {
        outputStream.writeUTF(input.getText());
        outputStream.flush();
        input.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Socket socket = new Socket("localhost", 8189);
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
            ReadHandler handler = new ReadHandler(inputStream,
                    message -> Platform.runLater(
                            () -> listView.getItems().add(message)
                    )
            );
            Thread readThread = new Thread(handler);
            readThread.setDaemon(true);
            readThread.start();
        } catch (Exception e) {
            System.err.println("Socket error");
        }
    }
}
