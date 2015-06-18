package com.flyliang.ing.service.client;

import com.flyliang.ing.service.constants.ServerSettingConstants;
import com.flyliang.ing.service.demo.Hello;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class HelloServiceClient {

    /**
     * 调用 Hello 服务
     * 
     * @param args
     */
    public static void main(String[] args) {
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
}
