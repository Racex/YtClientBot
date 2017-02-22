package main;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by cos on 20.01.2017.
 */
public class RestComunication {
    public static String urlString;
    public boolean sendToRPI(String urlString, JSONObject json) throws Exception {
        HttpURLConnection con;
        if ((con = prepareConnection(urlString)) == null) return false;
        OutputStream os = con.getOutputStream();
        os.write(json.toString().getBytes());
        os.flush();
        if (con.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + con.getResponseCode());
        }
//          Implementacja do odbierania odpowiedzi z serwera czy po wysłaniu wiadomości otrzymam jakąś zwrotke do zastanowienia sie?
        BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
        return br.readLine().equals("potwierdzam") ? true : false;

    }

    private HttpURLConnection prepareConnection(String urlString) throws IOException {
        try {
            HttpURLConnection con = (HttpURLConnection) new URL(urlString).openConnection();
//      Jezeli polaczenie wymagania autentyfikacji
//      byte[] message = ("username"+":"+ "passeward").getBytes("UTF-8");
//      String encoded = javax.xml.bind.DatatypeConverter.printBase64Binary(message);
//      con.setRequestProperty("Authorization", "Basic "+encoded);
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            return con;
        } catch (IOException e) {
            String massage = "";//nie wiem czy jest to konieczne
            for (StackTraceElement x : e.getStackTrace()) {
                massage += x.toString() + "\n";
            }
            throw new IOException("Failed " + e.toString() + massage);
        }

    }

    public JSONObject getStatusFromRPI() {
        HttpURLConnection con;
        try {
            if ((con = prepareConnection(urlString +"/pobierz status dla zadania")) == null) return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }
}
