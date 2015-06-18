package com.flyliang.ing.service.client;

import com.flyliang.ing.service.demo.Hello;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class HelloServiceClient {

    private static final Integer SERVER_PORT    = 7911;
    private static final String  SERVER_ADDRESS = "localhost";
    private static final Integer TIME_OUT       = 30000;

    /**
     * 调用 Hello 服务
     * 
     * @param args
     */
    public static void main(String[] args) {
        try {
            // 设置调用的服务地址为本地
            // TTransport transport = new TSocket(SERVER_ADDRESS, SERVER_PORT);
            TTransport transport = new TSocket(SERVER_ADDRESS, SERVER_PORT, TIME_OUT);

            transport.open();
            // 设置传输协议为 TBinaryProtocol
            TProtocol protocol = new TBinaryProtocol(transport);
            Hello.Client client = new Hello.Client(protocol);
            // 调用服务的 helloVoid 方法
            client.helloVoid();
            client.helloString("hello world");
            transport.close();
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        }
    }
}
