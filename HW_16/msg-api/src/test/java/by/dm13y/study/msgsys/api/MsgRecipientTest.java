package by.dm13y.study.msgsys.api;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class MsgRecipientTest {

    @Before
    public void init() throws Exception{
        initMsgServer();
    }

    @Test
    public void registerTest() throws Exception{
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("localhost", 9999));
        Header header = new Header(RecipientType.WEB_SOCKET);
        Assert.assertNull(header.getId());
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(header);
        System.out.println(header.getId());
        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
        header = (Header)input.readObject();
        System.out.println(header.getId());
        Assert.assertNotNull(header.getId());
        Thread.sleep(1000);
        out.writeObject("sdfasfd");
        System.out.println(socket.isConnected());
        socket.close();
    }


    private void initMsgServer() throws Exception {
        Thread tr = new Thread(){
            @Override
            public void run() {
                ServerSocket socket = null;
                try {
                    socket = new ServerSocket(9999);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Socket client = socket.accept();
                    Thread.sleep(10000);
                    System.out.println("IIIIIII");
                    ObjectInputStream inputStream = new ObjectInputStream(client.getInputStream());
                    inputStream.close();
                    Object obj = inputStream.readObject();
                    if (obj instanceof Header) {
                        Header header = ((Header) obj);
                        header.setId(new Random().nextInt());
                        ObjectOutputStream outputStream = new ObjectOutputStream(client.getOutputStream());
                        outputStream.writeObject(header);
                    } else {
                        throw new UnsupportedOperationException("Object is not header");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                int i = 10;
            }
        };
        tr.start();
    }
}