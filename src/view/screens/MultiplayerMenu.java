package view.screens;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class MultiplayerMenu {
    private String localHostIp;
    private String serverIp;

    public MultiplayerMenu() {
        try {
            localHostIp = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            localHostIp = "Unresolvable.";
        }

        serverIp = "Start typing.";
    }

    public String getLocalHostIp() {
        return localHostIp;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getWaitingText() {
        return "Waiting for players...";
    }
}
