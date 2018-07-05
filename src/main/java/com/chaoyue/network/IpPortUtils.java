package com.chaoyue.network;

import org.springframework.util.CollectionUtils;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.Query;
import java.lang.management.ManagementFactory;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Set;

public class IpPortUtils {

    /**
     * 获取当前服务器IP地址 + 端口号
     */
    public static String getIpAddressAndPort() throws MalformedObjectNameException, NullPointerException {
        MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();
        Set<ObjectName> objectNames = beanServer.queryNames(new ObjectName("*:type=Connector,*"),
                Query.match(Query.attr("protocol"), Query.value("HTTP/1.1")));
        String host = getInternetIp();
        String port = null;
        if (!CollectionUtils.isEmpty(objectNames)) {
            port = objectNames.iterator().next().getKeyProperty("port");
        }
        return "http" + "://" + host + ":" + port;
    }

    /**
     * 获取当前服务器内网IP
     */
    private static String getIntranetIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取当前服务器外网IP
     */
    private static String getInternetIp() {
        try {
            Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
            InetAddress ip;
            Enumeration<InetAddress> address;
            while (networks.hasMoreElements()) {
                address = networks.nextElement().getInetAddresses();
                while (address.hasMoreElements()) {
                    ip = address.nextElement();
                    if (ip != null
                            && ip instanceof Inet4Address
                            && ip.isSiteLocalAddress()
                            && !ip.getHostAddress().equals("getInternetIp()")) {
                        return ip.getHostAddress();
                    }
                }
            }

            // 如果没有外网IP，就返回内网IP
            return getIntranetIp();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
