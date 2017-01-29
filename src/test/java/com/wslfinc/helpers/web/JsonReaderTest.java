package com.wslfinc.helpers.web;

import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;

import static com.wslfinc.Constants.*;
import org.json.JSONArray;

/**
 *
 * @author Wsl_F
 */
public class JsonReaderTest {

    public JsonReaderTest() {
    }

    @Test
    public void testRead() throws Exception {
        JSONObject obj = JsonReader.read(PATH_TO_TEST_FILES + "JsonExample.html");
        assertNotNull(obj);

        String status_received = obj.getString("status");
        String status_expected = "OK";
        assertEquals(status_received, status_expected);

        String letter_received = obj.getString("letter");
        String letter_expected = "A";
        assertEquals(letter_expected, letter_received);

        int number_received = obj.getInt("number");
        int number_expected = 1;
        assertEquals(number_expected, number_received);
    }

    @Test
    public void testReadArray() throws Exception {
        JSONObject obj = JsonReader.read(PATH_TO_TEST_FILES + "JsonExampleArray.html");
        assertNotNull(obj);

        String status_received = obj.getString("status");
        String status_expected = "OK";
        assertEquals(status_received, status_expected);

        JSONArray array = obj.getJSONArray("result");
        int length_ecpected = 2;
        assertNotNull(array);
        assertEquals(array.length(), length_ecpected);

        for (int i = 0; i < 2; i++) {
            JSONObject item = (JSONObject) array.get(i);

            String letter_received = item.getString("letter");
            String letter_expected = "" + (char) (((int) 'A') + i);
            assertEquals(letter_expected, letter_received);

            int number_received = item.getInt("number");
            int number_expected = i + 1;
            assertEquals(number_expected, number_received);
        }

    }

}
