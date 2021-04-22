import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

public class ReadHandler implements Runnable {
    private final DataInputStream inputStream;
    private final CallBack callBack;

    public ReadHandler(DataInputStream inputStream, CallBack callBack) {
        this.inputStream = inputStream;
        this.callBack = callBack;
    }

    @Override
    public void run() {
//        try {
//            String[] files = folder.list();
//            for (String file : files) {
//                callBack.call(file);
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//            System.err.println("Exception while read");
//        }
    }
}
