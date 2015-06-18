package com.flyliang.ing.service.server;

import com.flyliang.ing.service.demo.Hello;
import com.flyliang.ing.service.demo.HelloServiceImpl;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TBinaryProtocol.Factory;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

public class HelloServiceServer {

    /**
     * 启动 Thrift 服务器
     * 
     * @param args
     */
    public static void main(String[] args) {
        try {
            // 设置服务端口为 7911
            TServerSocket serverTransport = new TServerSocket(7911);

            // 设置协议工厂为 TBinaryProtocol.Factory
            Factory proFactory = new Factory();
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

}
