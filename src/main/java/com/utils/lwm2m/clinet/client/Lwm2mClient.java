package com.utils.lwm2m.clinet.client;


import com.utils.lwm2m.clinet.model.MyDevice;
import com.utils.lwm2m.clinet.properties.Lwm2mConfigPoJo;
import com.utils.spring.SpringBootUtil;
import org.eclipse.californium.core.network.config.NetworkConfig;
import org.eclipse.californium.elements.Connector;
import org.eclipse.californium.scandium.DTLSConnector;
import org.eclipse.californium.scandium.config.DtlsConnectorConfig;
import org.eclipse.californium.scandium.dtls.*;
import org.eclipse.leshan.client.californium.LeshanClient;
import org.eclipse.leshan.client.californium.LeshanClientBuilder;
import org.eclipse.leshan.client.engine.DefaultRegistrationEngineFactory;
import org.eclipse.leshan.client.object.Device;
import org.eclipse.leshan.client.object.Security;
import org.eclipse.leshan.client.object.Server;
import org.eclipse.leshan.client.resource.LwM2mObjectEnabler;
import org.eclipse.leshan.client.resource.ObjectsInitializer;
import org.eclipse.leshan.client.resource.listener.ObjectsListenerAdapter;
import org.eclipse.leshan.core.LwM2mId;
import org.eclipse.leshan.core.californium.DefaultEndpointFactory;
import org.eclipse.leshan.core.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @program: iot-cloud-ota-coap
 * @description: Lwm2m客户端
 * @author: miaomingwei
 * @create: 2020-09-04 12:26
 */
@Component
public class Lwm2mClient {
    public static final String MODEL_NUMBER = "IT-TEST-123";
    private static final Logger logger = LoggerFactory.getLogger(Lwm2mClient.class);
    public final static String[] modelPaths = new String[]{
            "LWM2M_Device-v1_0_3.xml",
            "LWM2M_APN_Connection_Profile-v1_0_1.xml", "LWM2M_Bearer_Selection-v1_0_1.xml",
            "LWM2M_Cellular_Connectivity-v1_0_1.xml", "LWM2M_DevCapMgmt-v1_0.xml",
            "LWM2M_LOCKWIPE-v1_0_1.xml", "LWM2M_Portfolio-v1_0.xml",
            "LWM2M_Software_Component-v1_0.xml", "LWM2M_Software_Management-v1_0.xml",
            "LWM2M_WLAN_connectivity4-v1_0.xml", "LwM2M_BinaryAppDataContainer-v1_0_1.xml",
            "LwM2M_EventLog-V1_0.xml", "LWM2M_Firmware_Update-v1_0_3.xml", "3308.xml"};

    //public final static String[] modelPaths = new String[]{ "3308.xml"};


    public void createLwm2mClient(String endpoint) throws InvalidModelException, InvalidDDFFileException, IOException {
        Lwm2mConfigPoJo lwm2MConfigPoJo = SpringBootUtil.getBean(Lwm2mConfigPoJo.class);
        //加载默认对象
        List<ObjectModel> models = ObjectLoader.loadDefault();
        //加载需要添加的对象
        models.addAll(ObjectLoader.loadDdfResources("/models/", modelPaths));
        //创建对象构造器
        final ObjectsInitializer initializer = new ObjectsInitializer(new StaticModel(models));
        //创建 CoAP 配置
        NetworkConfig coapConfig;
        File configFile = new File(NetworkConfig.DEFAULT_FILE_NAME);
        if (configFile.isFile()) {
            coapConfig = new NetworkConfig();
            coapConfig.load(configFile);
        } else {
            coapConfig = LeshanClientBuilder.createDefaultNetworkConfig();
            coapConfig.store(configFile);
        }
        //创建 Security(0)实例
        Security security = Security
                .noSec("coap://" + lwm2MConfigPoJo.getIp() + ":" + lwm2MConfigPoJo.getPort(), 12345);
        //创建 Server(1)实例
        Server server = new Server(12345, 5 * 60);
        //创建 Device(3)实例
        Device device = new Device("Eclipse Leshan", MODEL_NUMBER, "12345");
        //创建 自定义(3308)实例0
        MyDevice myObject = new MyDevice();
        //创建 自定义(3308)实例1
        MyDevice myObject1 = new MyDevice();
        initializer.setInstancesForObject(LwM2mId.SECURITY, security);
        initializer.setInstancesForObject(LwM2mId.SERVER, server);
        initializer.setInstancesForObject(LwM2mId.DEVICE, device);
        initializer.setInstancesForObject(3308, myObject, myObject1);
        //创建客户端构建器
        LeshanClientBuilder builder = new LeshanClientBuilder(endpoint);
        //创建默认注册引擎
        DefaultRegistrationEngineFactory engineFactory = new DefaultRegistrationEngineFactory();
        //创建端点工厂
        DefaultEndpointFactory endpointFactory = getEndpointFactory();
        Map<String, String> bsAdditionalAttributes = null;
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(20);
        List<LwM2mObjectEnabler> enablers = initializer.createAll();

        //设置附加属性
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("a", "adc");
        builder.setAdditionalAttributes(hashMap);
        builder.setCoapConfig(coapConfig);
        builder.setObjects(enablers);
        builder.setSharedExecutor(scheduledExecutorService);
        builder.setRegistrationEngineFactory(engineFactory);
        builder.setEndpointFactory(endpointFactory);
        builder.setBootstrapAdditionalAttributes(bsAdditionalAttributes);
        LeshanClient client = builder.build();
        client.getObjectTree().addListener(new ObjectsListenerAdapter() {
            @Override
            public void objectRemoved(LwM2mObjectEnabler object) {
                logger.info("Object {} disabled.");
            }

            @Override
            public void objectAdded(LwM2mObjectEnabler object) {
                logger.info("Object {} disabled.");
            }
        });
        client.start();
    }

    /**
     * 配置 EndpointFactory
     *
     * @return
     */
    static DefaultEndpointFactory getEndpointFactory() {
        return new DefaultEndpointFactory("LWM2M CLIENT") {
            @Override
            protected Connector createSecuredConnector(DtlsConnectorConfig dtlsConfig) {
                return new DTLSConnector(dtlsConfig) {
                    @Override
                    public void onInitializeHandshaker(Handshaker handshaker) {
                        handshaker.addSessionListener(new SessionAdapter() {
                            @Override
                            public void handshakeStarted(Handshaker handshaker) throws HandshakeException {
                                if (handshaker instanceof ServerHandshaker) {
                                    logger.info("DTLS Full Handshake initiated by server : STARTED ...");
                                } else if (handshaker instanceof ResumingServerHandshaker) {
                                    logger.info("DTLS abbreviated Handshake initiated by server : STARTED ...");
                                } else if (handshaker instanceof ClientHandshaker) {
                                    logger.info("DTLS Full Handshake initiated by client : STARTED ...");
                                } else if (handshaker instanceof ResumingClientHandshaker) {
                                    logger.info("DTLS abbreviated Handshake initiated by client : STARTED ...");
                                }
                            }

                            @Override
                            public void sessionEstablished(Handshaker handshaker, DTLSSession establishedSession) {
                                if (handshaker instanceof ServerHandshaker) {
                                    logger.info("DTLS Full Handshake initiated by server : SUCCEED");
                                } else if (handshaker instanceof ResumingServerHandshaker) {
                                    logger.info("DTLS abbreviated Handshake initiated by server : SUCCEED");
                                } else if (handshaker instanceof ClientHandshaker) {
                                    logger.info("DTLS Full Handshake initiated by client : SUCCEED");
                                } else if (handshaker instanceof ResumingClientHandshaker) {
                                    logger.info("DTLS abbreviated Handshake initiated by client : SUCCEED");
                                }
                            }

                            @Override
                            public void handshakeFailed(Handshaker handshaker, Throwable error) {
                                // get cause
                                String cause;
                                if (error != null) {
                                    if (error.getMessage() != null) {
                                        cause = error.getMessage();
                                    } else {
                                        cause = error.getClass().getName();
                                    }
                                } else {
                                    cause = "unknown cause";
                                }
                                if (handshaker instanceof ServerHandshaker) {
                                    logger.info("DTLS Full Handshake initiated by server : FAILED ({})", cause);
                                } else if (handshaker instanceof ResumingServerHandshaker) {
                                    logger.info("DTLS abbreviated Handshake initiated by server : FAILED ({})", cause);
                                } else if (handshaker instanceof ClientHandshaker) {
                                    logger.info("DTLS Full Handshake initiated by client : FAILED ({})", cause);
                                } else if (handshaker instanceof ResumingClientHandshaker) {
                                    logger.info("DTLS abbreviated Handshake initiated by client : FAILED ({})", cause);
                                }
                            }
                        });
                    }
                };
            }
        };
    }

}
