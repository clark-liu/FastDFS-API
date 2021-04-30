package pers.liule.fastdfs.api.config;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Properties;

/**
 * @Description: fastdfs 配置类
 * @Author: liule
 * @Date: 2020/8/4 10:33
 */
@Configuration
public class FastDfsClientConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(FastDfsClientConfiguration.class);

    @Value("${fastdfs.connect.timeout.in.seconds}")
    private String connectTimeout;

    @Value("${fastdfs.network.timeout.in.seconds}")
    private String networkTimeout;

    @Value("${fastdfs.charset}")
    private String charset;

    @Value("${fastdfs.http.tracker.http.port}")
    private String trackerPort;

    @Value("${fastdfs.http.anti.steal.token}")
    private String antiStealToken;

    @Value("${fastdfs.http.secret.key}")
    private String secretKey;

    @Value("${fastdfs.tracker.servers}")
    private String trackerServers;


    @PostConstruct
    public void initFastDfsConfig(){
        Properties props = new Properties();
        props.put(ClientGlobal.PROP_KEY_TRACKER_SERVERS, trackerServers);
        props.put(ClientGlobal.PROP_KEY_HTTP_SECRET_KEY, secretKey);
        props.put(ClientGlobal.PROP_KEY_HTTP_TRACKER_HTTP_PORT, trackerPort);
        props.put(ClientGlobal.PROP_KEY_HTTP_ANTI_STEAL_TOKEN, antiStealToken);
        props.put(ClientGlobal.PROP_KEY_CHARSET, charset);
        props.put(ClientGlobal.PROP_KEY_NETWORK_TIMEOUT_IN_SECONDS, networkTimeout);
        try {
            ClientGlobal.initByProperties(props);
        }catch (MyException | IOException e){
            logger.error("FastDFS Client Init Fail!",e);
        }
    }
}
