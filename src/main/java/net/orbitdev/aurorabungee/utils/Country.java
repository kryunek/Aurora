package net.orbitdev.aurorabungee.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;

public class Country {

    public static String getCountry(String IP) {
        String country = "";
        try {
            URL url = new URL("http://ip-api.com/json/" + IP + "?fields=country");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            country = bufferedReader.readLine().trim();
            if (country.length() <= 0) {
                try {
                    InetAddress ip = InetAddress.getLocalHost();
                    System.out.println(ip.getHostAddress().trim());
                    country = ip.getHostAddress().trim();
                } catch (Exception exp) {
                    country = "Not Found";
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return country
                .replace("{", "")
                .replace("}", "")
                .replace("country", "")
                .replace(":", "")
                .replace("\"", "");
    }
}