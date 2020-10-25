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
                        ch = reader.readLine().charAt(0);
                    }
                }
                else {
                    throw new Exception("Not a http url");
                }
            } catch (Exception e) {
                System.out.println("error :"+e.getMessage());
                e.printStackTrace();
            } 
        }while(ch == 'y');
    }
    /*The readalllines() is used to take input repetatively
    until the user enter a null character
     */
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
    /*
    The addfield() is used to assign the property values which
    were present in the user input
     */
    private static void addField(String name, Object value) {
        System.out.printf(name + ":"+ value);
    }
}
