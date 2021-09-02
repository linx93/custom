package com.tianji.chain.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author tanwei
 * @desc http配置类
 * @time 1/29/21 11:07 AM
 * @since 1.0.0
 */
@Configuration
public class HttpConfig {
    private static boolean enableProxy;
    private static String proxyIp;
    private static int proxyPort;

    @Value("${custom.http.proxy.enable:false}")
    public void setEnableProxy(boolean enableProxy) {
        HttpConfig.enableProxy = enableProxy;
    }

    @Value("${custom.http.proxy.ip:127.0.0.1}")
    public void setProxyIp(String proxyIp) {
        HttpConfig.proxyIp = proxyIp;
    }

    @Value("${custom.http.proxy.port:8080}")
    public void setProxyPort(int proxyPort) {
        HttpConfig.proxyPort = proxyPort;
    }

    public static boolean getEnableProxy() {
        return enableProxy;
    }

    public static String getProxyIp() {
        return proxyIp;
    }

    public static int getProxyPort() {
        return proxyPort;
    }
}
