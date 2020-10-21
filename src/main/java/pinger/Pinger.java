package pinger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Pinger {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Enter the URL to be pinged");
            String urlI = "http://" + reader.readLine();
            URL url = new URL(urlI);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            try (InputStream stream = conn.getInputStream()) {
                String content = new String(stream.readAllBytes());
                if (content.isEmpty() && content.isBlank()) {
                    content = "No content returned";
                }
                addField("Content Type", conn.getContentType());
                addField("Request Method", conn.getRequestMethod());
                addField("Connection Timeout", conn.getConnectTimeout());
                addField("Response code", conn.getResponseCode());
                addField("Header Fields", conn.getHeaderFields());
                addField("Content", content);
            }
        }
    }

    private static void addField(String name, Object value) {
        System.out.printf(name + ": %s", value);
    }
}
