package sample;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by cos on 20.01.2017.
 */
public class RestComunication {
    public boolean sendToRPI(String urlString, JSONObject json)
    {       HttpURLConnection con;
            if((con = prepareConnection(urlString)) == null) return false;
        try {
            OutputStream os = con.getOutputStream();
            os.write(json.toString().getBytes());
            os.flush();
            if (con.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + con.getResponseCode());
            }
//          Implementacja do odbierania odpowiedzi z serwera czy po wysłaniu wiadomości otrzymam jakąś zwrotke do zastanowienia sie?
            BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
            return  br.readLine().equals("potwierdzam") ? true : false;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private HttpURLConnection prepareConnection(String urlString) {
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
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject getStatusFromRPI() {
        HttpURLConnection con;
        if((con = prepareConnection("URL_DO_POBIERANIA_STATUSU")) == null) return null;

        return new JSONObject();
    }
}
