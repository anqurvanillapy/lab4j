import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.channels.SocketChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.Selector;
import java.nio.channels.SelectionKey;
import java.util.Set;
import java.util.Iterator;
import java.net.InetSocketAddress;
import java.net.InetAddress;

public class NioSelectorServer {
    public static void main(String[] args) throws IOException {
        try (Selector selector = Selector.open()) {
            final String host = "localhost";
            final int port = 9099;
            System.out.println("Serving " + host + ":" + port + "...");
            serve(selector, host, port);
        }
    }

    private static void serve(Selector selector, String host, int port)
        throws IOException {
        final ServerSocketChannel ch = ServerSocketChannel.open();
        ch.bind(new InetSocketAddress(host, port));
        ch.configureBlocking(false);
        ch.register(selector, SelectionKey.OP_ACCEPT);
        final ByteBuffer buf = ByteBuffer.allocate(4096); // page size

        while (true) {
            selector.select();

            final Set<SelectionKey> keys = selector.selectedKeys();
            final Iterator<SelectionKey> iter = keys.iterator();

            // Cannot simply range-for the selected keys, for there is some
            // underlying concurrent modification I guess.
            while (iter.hasNext()) {
                final SelectionKey key = iter.next();

                if (key.isAcceptable()) {
                    registerReadChannel(selector, ch);
                }

                if (key.isReadable()) {
                    handleEcho(key, buf);
                }

                iter.remove();
            }
        }
    }

    private static void registerReadChannel(Selector selector, ServerSocketChannel ch)
        throws IOException {
        final SocketChannel client = ch.accept();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
    }

    private static void handleEcho(SelectionKey key, ByteBuffer buf)
        throws IOException {
        final SocketChannel client = (SocketChannel) key.channel();
        final InetAddress addr = client.socket().getInetAddress().getHostAddress();

        client.read(buf);
        final String msg = "Hello, " + new String(buf.array()).trim();

        System.out.println(addr + " | Message: " + msg);

        client.write(ByteBuffer.wrap(msg.getBytes()));
        client.close();
        buf.clear();
    }
}
