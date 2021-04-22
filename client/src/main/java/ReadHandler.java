import java.io.DataInputStream;

public class ReadHandler implements Runnable {
    private final DataInputStream inputStream;
    private final CallBack callBack;

    public ReadHandler(DataInputStream inputStream, CallBack callBack) {
        this.inputStream = inputStream;
        this.callBack = callBack;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = inputStream.readUTF();
                callBack.call(message);
            }
        } catch (Exception e) {
            System.err.println("Exception while read");
        }
    }
}
