package com.example.pastebin;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

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

        Document doc = Jsoup.connect(myurl).get();
        String texto = String.valueOf(doc.getElementById("articleContent").text());
        System.out.println(texto);
        contentAsString = texto;
        return contentAsString;
    }
}
