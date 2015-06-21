package com.flyliang.ing.service.client;

import com.flyliang.ing.service.constants.ServerSettingConstants;
import com.flyliang.ing.service.demo.Hello;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TNonblockingTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.concurrent.CountDownLatch;

public class HelloServiceClient {

    /**
     * 调用 Hello 服务
     * 
     * @param args
     */
    public static void main(String[] args) {

    }

    public static void simpleClient() {
        try {
            // 设置调用的服务地址为本地
            TTransport transport = new TSocket(ServerSettingConstants.SERVER_ADDRESS,
                                               ServerSettingConstants.SERVER_PORT);

            // TTransport transport = new TSocket(ServerSettingConstants.SERVER_ADDRESS,
            // ServerSettingConstants.SERVER_PORT, ServerSettingConstants.TIME_OUT);

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

    public static void nonBlockingClient() {
        try {
            // 设置调用的服务地址为本地
            TTransport transport = new TFramedTransport(new TSocket(ServerSettingConstants.SERVER_ADDRESS,
                                                                    ServerSettingConstants.SERVER_PORT,
                                                                    ServerSettingConstants.TIME_OUT));

            // 设置传输协议为 TCompactProtocol
            TProtocol tProtocol = new TCompactProtocol(transport);
            transport.open();

            Hello.Client client = new Hello.Client(tProtocol);

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

//    public static void asyncNonBlockingClient() {
//        try {
//            TAsyncClientManager tAsyncClientManager = new TAsyncClientManager();
//            // 设置调用的服务地址为本地
//            TNonblockingTransport tNonBlockingTransport = new TNonblockingSocket(ServerSettingConstants.SERVER_ADDRESS,
//                                                                                 ServerSettingConstants.SERVER_PORT,
//                                                                                 ServerSettingConstants.TIME_OUT);
//            // 设置传输协议为 TCompactProtocol
//            TProtocolFactory tProtocolFactory = new TCompactProtocol.Factory();
//
//
//            Hello.AsyncClient asyncClient = new Hello.AsyncClient(tProtocolFactory,tAsyncClientManager,tNonBlockingTransport);
//            System.out.println("client starting ....");
//
//            CountDownLatch latch = new CountDownLatch(1);
//
//            // 调用服务的 helloVoid 方法
////            client.helloVoid();
////            client.helloString("hello world");
////            transport.close();
//        } catch (TTransportException e) {
//            e.printStackTrace();
//        } catch (TException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//
//        }
//    }


//    public class AsynCallback implements AsyncMethodCallback<> {
//
//    }

}
