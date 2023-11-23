package view.screens;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    /**
     * Checks for the validity of
     * the inputted server ip.
     *
     * @return Whether the ip is valid.
     */
    public boolean validateServerIp() {
        Pattern pattern = Pattern.compile("^([0-9]{1,3}\\.){3}[0-9]{1,3}$");
        Matcher matcher = pattern.matcher(serverIp);

        boolean validPattern = matcher.find();
        if (!validPattern) serverIp = "Invalid ip.";

        return validPattern;
    }

    /* ---------- Getters / Setters ---------- */

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
