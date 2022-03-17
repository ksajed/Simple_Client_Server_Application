package fr.sajed.socket;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class GetIpHostName {

    public static InetAddress now() {

        InetAddress ip = null;
        String hostname = null;

            try {
                  ip = InetAddress.getLocalHost();
                  hostname = ip.getHostName();
            } catch (UnknownHostException e) {
                  e.printStackTrace();
            }

        return ip;
    }
}
