package com.example.androidclient;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final String HOST = "http://10.0.2.2:3000";
    private static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void makeGetAllRequest(View view) {
        new HttpGetRequest().execute(HOST);
    }

    public void makeGetOneRequest(View view) {
        int idToGet = 0;
        String url = HOST + "/" + idToGet;
        new HttpGetRequest().execute(url);
    }

    public void makePostRequest(View view) {
        String json = "{\"fruit\": \"pear\", \"color\": \"green\"}";
        new HttpPostRequest().execute(HOST, json);
    }

    public void makePutRequest(View view) {
        int idToReplace = 0;
        String url = HOST + "/" + idToReplace;
        String json = "{\"fruit\": \"watermellon\", \"color\": \"red and green\"}";
        new HttpPutRequest().execute(url, json);
    }

    public void makePatchRequest(View view) {
        int idToReplace = 0;
        String url = HOST + "/" + idToReplace;
        String json = "{\"color\": \"green\"}";
        new HttpPatchRequest().execute(url, json);
    }

    public void makeDeleteRequest(View view) {
        int idToReplace = 0;
        String url = HOST + "/" + idToReplace;
        new HttpDeleteRequest().execute(url);
    }

    public static class HttpGetRequest extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params){
            String urlString = params[0];

            try {
                URL myUrl = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
                connection.setRequestMethod("GET");

                String result = getStringFromInputStream(connection.getInputStream());
                int statusCode = connection.getResponseCode();
                connection.disconnect();

                Log.i(TAG, "GET result: " + statusCode + " " + result);

            } catch(IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static class HttpPostRequest extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params){
            String urlString = params[0];
            String json = params[1];

            try {
                URL myUrl = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");

                writeStringToOutputStream(json, connection.getOutputStream());
                String result = getStringFromInputStream(connection.getInputStream());
                int statusCode = connection.getResponseCode();
                connection.disconnect();

                Log.i(TAG, "POST result: " + statusCode + " " + result);

            } catch(IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static class HttpPutRequest extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params){
            String urlString = params[0];
            String json = params[1];

            try {
                URL myUrl = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
                connection.setRequestMethod("PUT");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");

                writeStringToOutputStream(json, connection.getOutputStream());
                String result = getStringFromInputStream(connection.getInputStream());
                int statusCode = connection.getResponseCode();
                connection.disconnect();

                Log.i(TAG, "PUT result: " + statusCode + " " + result);

            } catch(IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static class HttpPatchRequest extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params){
            String urlString = params[0];
            String json = params[1];

            try {
                URL myUrl = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
                connection.setRequestMethod("PATCH");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");

                writeStringToOutputStream(json, connection.getOutputStream());
                String result = getStringFromInputStream(connection.getInputStream());
                int statusCode = connection.getResponseCode();
                connection.disconnect();

                Log.i(TAG, "PATCH result: " + statusCode + " " + result);

            } catch(IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }



    public static class HttpDeleteRequest extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params){
            String urlString = params[0];

            try {
                URL myUrl = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
                connection.setRequestMethod("DELETE");

                String result = getStringFromInputStream(connection.getInputStream());
                int statusCode = connection.getResponseCode();
                connection.disconnect();

                Log.i(TAG, "DELETE result: " + statusCode + " " + result);

            } catch(IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private static void writeStringToOutputStream(String json, OutputStream outputStream) throws IOException {
        byte[] bytes = json.getBytes("UTF-8"); // API 19: StandardCharsets.UTF_8
        outputStream.write(bytes);
        outputStream.close();
    }

    private static String getStringFromInputStream(InputStream stream) throws IOException {
        InputStreamReader streamReader = new InputStreamReader(stream);
        BufferedReader reader = new BufferedReader(streamReader);
        StringBuilder stringBuilder = new StringBuilder();
        String inputLine;
        while((inputLine = reader.readLine()) != null){
            stringBuilder.append(inputLine);
        }
        reader.close();
        streamReader.close();
        return stringBuilder.toString();
    }

}
