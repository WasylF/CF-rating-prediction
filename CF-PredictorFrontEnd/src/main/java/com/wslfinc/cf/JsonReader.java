package com.wslfinc.cf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.json.JSONObject;

/**
 *
 * @author Wsl_F
 */
public class JsonReader {

    private static final int BUFFER_SIZE = 1000;

    private static String readWhole(Reader reader) throws IOException {
        StringBuilder text = new StringBuilder();
        char[] cbuf = new char[BUFFER_SIZE];
        int readed = 0;
        while (readed != -1) {
            readed = reader.read(cbuf);
            if (readed > 0) {
                text.append(Arrays.copyOfRange(cbuf, 0, readed));
            }
        }
        return text.toString();
    }

    public static JSONObject read(final String url) throws Exception {
        Exception exception = null;
        for (int i = 0; i < 3; i++) {
            try (InputStream is = new URL(url).openStream()) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                String jsonText = readWhole(reader);
                JSONObject json = new JSONObject(jsonText);
                return json;
            } catch (Exception ex) {
                System.err.println("Failed read url: " + url
                        + " try #" + (i + 1) + "\n" + ex.getMessage());
                exception = ex;
            }
        }

        throw exception;
    }

    public static boolean isExists(final String urlString) {
        try {
            final URL url = new URL(urlString);
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            huc.setRequestMethod("HEAD");
            int responseCode = huc.getResponseCode();

            return responseCode == 200;
        } catch (Exception ex) {
            System.err.println("Failed to check existance of url: " + urlString
                    + "\n" + ex.getMessage());
        }

        return false;
    }
}
