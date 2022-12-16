package com.example.pastebin;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class DownloadWebPageText extends AsyncTask {

    // onPostExecute displays the results of the AsyncTask.
    TextView result;
    public static String contentAsString = "";

    public String getContentAsString() {
        return contentAsString;
    }

    public void setContentAsString(String contentAsString) {
        this.contentAsString = contentAsString;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        // params comes from the execute() call: params[0] is the url.
        try {
            return downloadUrl((String) objects[0]);
        } catch (IOException e) {
            return "Unable to retrieve web page. URL may be invalid.";
        }

    }
    // Given a URL, establishes an HttpUrlConnection and retrieves
// the web page content as a InputStream, which it returns as
// a string.
    private String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 10000;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            is = conn.getInputStream();

            // Convert the InputStream into a string
            contentAsString = readIt(is, len);


            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                is.close();
            }
        }

        return contentAsString;
    }
    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        String resultado ="";
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
//Buscar como hacer esto en JSOUP
        for (int i = 0; i < buffer.length; i++) {
            if (buffer[i] == '<'){
                if (buffer[i+1] == 'p'){
                    if (buffer[i+2] == '>'){
                       boolean cogertexto = true;
                       int j =++i+2;
                       while (cogertexto){
                           if (buffer[j] == '<'){
                               break;
                           }
                           resultado +=buffer[j];
                        j++;
                       }
                    }
                }
            }
        }

        return resultado;
    }
}
