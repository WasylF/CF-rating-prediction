package com.wslfinc.helpers.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.json.JSONException;
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

    public static JSONObject read(final String url) throws IOException, JSONException {
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readWhole(reader);
            JSONObject json = new JSONObject(jsonText);
            return json;
        }
    }
}
