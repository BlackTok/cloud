import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatController implements Initializable {
    private Socket socket;
    private byte[] buffer = new byte[8 * 1024];

    private final String CLIENT_DIR = "F:\\clientFolder\\";
    private final File folder = new File(CLIENT_DIR);

    public ListView<String> listView;
    public TextField input;
    private InputStream inputStream;
    private DataOutputStream outputStream;

    public void refresh(ActionEvent actionEvent) {
        addFilesToList();
    }

    public void send(ActionEvent actionEvent) throws IOException {
        String fileName = listView.getSelectionModel().getSelectedItem();
        File file = new File(CLIENT_DIR + fileName);
        byte[] buffer = new byte[(int) file.length()];
        Object[] objects = new Object[] {fileName, buffer};
        OutputStream os = socket.getOutputStream();
        DataOutputStream dos = new DataOutputStream(os);
        dos.writeUTF(file.getName());
        dos.write(buffer, 0, buffer.length);
        dos.flush();
    }

    private void addFilesToList() {
        listView.getItems().clear();
        String[] files = folder.list();
        listView.getItems().addAll(files);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addFilesToList();
        try {
            socket = new Socket("localhost", 8189);
        } catch (Exception e) {
            System.err.println("Socket error");
        }
    }
}
