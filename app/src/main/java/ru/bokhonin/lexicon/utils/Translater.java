package ru.bokhonin.lexicon.utils;

import android.util.Log;

import javax.net.ssl.HttpsURLConnection;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by IVAN on 04.06.2017.
 */
public class Translater {

    private static final String API_KEY_TRANS = "trnsl.1.1.20131030T070606Z.e297ce8dca9f74d1.84091fee328c7cb3a996a12102346376163f4b04";
    private static final String PATH_TRANS = "https://translate.yandex.net/api/v1.5/tr.json/translate";

    private static final String API_KEY_DICT = "dict.1.1.20170924T222659Z.5448372c2df43bec.45c465fbbf95389f9ce5bda32fb8e544470e02df";
    private static final String PATH_DICT = "https://dictionary.yandex.net/api/v1/dicservice.json/lookup";

    public String translateTrans(String lang, String input) throws IOException {

        String urlStr = PATH_TRANS + "?key=" + API_KEY_TRANS + "&lang=" + lang + "&text=" + URLEncoder.encode(input, "UTF-8");

        String translatedWord = getUrl(urlStr);

        //"code":200,"lang":"en-ru","text":["Проект чертежи"]
        JSONObject dataJsonObj = null;
        String clearTranslatedWord = "...";

        try {
            dataJsonObj = new JSONObject(translatedWord);
            clearTranslatedWord = dataJsonObj.getString("text");

            clearTranslatedWord = clearTranslatedWord.substring(2, clearTranslatedWord.length() - 2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return clearTranslatedWord;
    }

    public String translateDict(String lang, String input) throws IOException {

        String urlStr = PATH_DICT + "?key=" + API_KEY_DICT + "&lang=" + lang + "&text=" + URLEncoder.encode(input, "UTF-8");

        String translatedWord = getUrl(urlStr);

        JSONObject dataJsonObj = null;
        String clearTranslatedWord = "";

        try {
            dataJsonObj = new JSONObject(translatedWord);
            JSONArray part1 = dataJsonObj.getJSONArray("def");
            JSONObject part2 = part1.getJSONObject(0);
            JSONArray words = part2.getJSONArray("tr");

            for (int i = 0; i < words.length(); i++) {
                JSONObject word = words.getJSONObject(i);

                String wordText = word.getString("text");

                clearTranslatedWord = clearTranslatedWord + " - " + wordText + System.lineSeparator();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return clearTranslatedWord;
    }


    public static String getUrl(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public static byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();

//        Log.d("test____!!!", "step 1");

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();

            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

}

