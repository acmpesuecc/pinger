package pinger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.net.URL;

public class Pinger {
    public static void main(String[] args)  {
        char ch='y';
        do {
            BufferedReader reader1 = new BufferedReader(new InputStreamReader(System.in));
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {

                System.out.println("Enter the URL to be pinged");
                String urlI = "http://" + reader.readLine();
                URL url = new URL(urlI);
                URLConnection conn = url.openConnection();
                if(conn instanceof HttpURLConnection) {
                    HttpURLConnection conn1 = (HttpURLConnection) url.openConnection();
                    try (InputStream stream = conn1.getInputStream()) {
                        String content = readAllLines(stream);
                        if (content.isEmpty()) {
                            content = "No content returned";
                        }
                        addField("Content Type", conn1.getContentType());
                        addField("Request Method", conn1.getRequestMethod());
                        addField("Connection Timeout", conn1.getConnectTimeout());
                        addField("Response code", conn1.getResponseCode());
                        addField("Header Fields", conn1.getHeaderFields());
                        addField("Content", content);
                        System.out.println("Enter y to continue and n to exit ");
                        ch = reader1.readLine().charAt(0);
                    }
                }
                else {
                    throw new Exception("Not a http url");
                }
            } catch (Exception e) {
                System.out.println(e);
            } 
        }while(ch == 'y');
    }

    private static String readAllLines(InputStream stream) throws IOException {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            StringBuilder builder = new StringBuilder();
            while(true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                builder.append(line);
                builder.append("\n");
            }
            return builder.toString();
        }
    }

    private static void addField(String name, Object value) {
        System.out.printf(name + ": %s", value);
    }
}
