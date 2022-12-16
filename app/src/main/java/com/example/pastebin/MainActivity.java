package com.example.pastebin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
    Button download;
    EditText link;
    TextView result;
    DownloadWebPageText webPageText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //https://justpaste.it/cbj53

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        link = (EditText) findViewById(R.id.link);
        download = (Button) findViewById(R.id.button);
        result = (TextView) findViewById(R.id.results);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gets the URL from the UI's text field.
                String stringUrl = link.getText().toString();
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                webPageText =    new DownloadWebPageText();
                webPageText.execute(stringUrl);
                result.setText(webPageText.getContentAsString());
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                       builder.setMessage("CANT CONNECT TO THE INTERNET");
                       builder.show();
                }




            }});


    }
}