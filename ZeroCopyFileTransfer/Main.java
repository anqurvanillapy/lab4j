import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

// NOTE: Use echo.py for echoing TCP requests.

public class Main {
    public static void main(String[] args)
            throws IOException {
        String filename = "hello.txt";
        InetSocketAddress addr = new InetSocketAddress("localhost", 30000);

        try (RandomAccessFile file = new RandomAccessFile(filename, "r");
             FileChannel fileChan = file.getChannel();
             SocketChannel sockChan = SocketChannel.open()) {
            sockChan.connect(addr);

            // In *nix, the system call `sendfile` is used to perform zero-copy
            // data transferring.
            fileChan.transferTo(0, 1024, sockChan);
        }
    }
}
