package com.flyliang.ing.service.server;

import com.flyliang.ing.service.constants.ServerSettingConstants;
import com.flyliang.ing.service.demo.Hello;
import com.flyliang.ing.service.demo.HelloServiceImpl;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TBinaryProtocol.Factory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.transport.TTransportFactory;

public class HelloServiceServer {

    /**
     * 启动 Thrift 服务器
     * 
     * @param args
     */
    public static void main(String[] args) {

    }

    /**
     * simple singe thread server
     */
    public static void  simpleServer(){
        try {
            // 设置服务端口为 7911
            TServerSocket serverTransport = new TServerSocket(ServerSettingConstants.SERVER_PORT);

            // 设置协议工厂为 TBinaryProtocol.Factory
            TProtocolFactory tProtocolFactory = new TBinaryProtocol.Factory();

            // 关联处理器与 Hello 服务的实现
            TProcessor processor = new Hello.Processor(new HelloServiceImpl());

            // TServer.args
            TServer.Args tServerArgs = new TServer.Args(serverTransport);
            tServerArgs.processor(processor);
            tServerArgs.protocolFactory(tProtocolFactory);

            //
            TServer server = new TSimpleServer(tServerArgs);
            System.out.println("Start server on port 7911...");
            server.serve();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }

    /**
     * threads pool server
     */
    public static  void threadPoolServer(){
        try {
            // 设置服务端口为 7911
            TServerSocket serverTransport = new TServerSocket(ServerSettingConstants.SERVER_PORT);

            // 设置协议工厂为 TBinaryProtocol.Factory
            TProtocolFactory tProtocolFactory = new TBinaryProtocol.Factory();

            // 关联处理器与 Hello 服务的实现
            TProcessor processor = new Hello.Processor(new HelloServiceImpl());

            // TThreadPoolServer
            TThreadPoolServer.Args tServerArgs = new TThreadPoolServer.Args(serverTransport);
            tServerArgs.processor(processor);
            tServerArgs.protocolFactory(tProtocolFactory);

            //
            TServer server = new TThreadPoolServer(tServerArgs);
            System.out.println("Start server on port 7911...");
            server.serve();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }

    /**
     * 服务器端有客户端都需要使用相同的协议
     * 客户端nonBlockingClient
     * 异步客户端的服务器端结构
     */
    public static  void nonBlockingServer(){
        try {
            // 设置非阻塞服务端口为 7911
            TNonblockingServerSocket tNonblockingServerSocket = new TNonblockingServerSocket(ServerSettingConstants.SERVER_PORT);

            // 设置传输工厂为 TFramedTransport.Factory
            TTransportFactory tTransportFactory = new TFramedTransport.Factory();
            // 设置协议工厂为 TFramedTransport.Factory
            TProtocolFactory tProtocolFactory = new TCompactProtocol.Factory();

            // 关联处理器与 Hello 服务的实现
            TProcessor processor = new Hello.Processor(new HelloServiceImpl());

            // TNonblockingServer
            TNonblockingServer.Args tServerArgs = new TNonblockingServer.Args(tNonblockingServerSocket);
            tServerArgs.processor(processor);
            // add transportFactory
            tServerArgs.transportFactory(tTransportFactory);
            tServerArgs.protocolFactory(tProtocolFactory);

            //
            TServer server = new TNonblockingServer(tServerArgs);
            System.out.println("Start server on port 7911...");
            server.serve();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }

    /**
     * 半同步半异步，客户端使用与非阻塞相同，使用传输方式TFramedTransport
     * 客户端nonBlockingClient
     */
    public static  void hsHaServer(){
        try {
            // 设置非阻塞服务端口为 7911
            TNonblockingServerSocket tNonblockingServerSocket = new TNonblockingServerSocket(ServerSettingConstants.SERVER_PORT);

            // 设置传输工厂为 TFramedTransport.Factory
            TTransportFactory tTransportFactory = new TFramedTransport.Factory();
            // 设置协议工厂为 TBinaryProtocol.Factory
            TProtocolFactory tProtocolFactory = new TBinaryProtocol.Factory();

            // 关联处理器与 Hello 服务的实现
            TProcessor processor = new Hello.Processor(new HelloServiceImpl());

            // TNonblockingServer
            THsHaServer.Args tServerArgs = new THsHaServer.Args(tNonblockingServerSocket);
            tServerArgs.processor(processor);
            // add transportFactory
            tServerArgs.transportFactory(tTransportFactory);
            tServerArgs.protocolFactory(tProtocolFactory);

            // server
            TServer server = new THsHaServer(tServerArgs);
            System.out.println("Start server on port 7911...");
            server.serve();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }
}
