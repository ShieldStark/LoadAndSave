package com.example.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private EditText inputData;
    private TextView savedData;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputData = findViewById(R.id.editTextInput);
        savedData = findViewById(R.id.textViewSavedData);
        progressBar = findViewById(R.id.progressBar);

        Button saveButton = findViewById(R.id.buttonSave);
        Button loadButton = findViewById(R.id.buttonLoad);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SaveDataTask().execute();
            }
        });

        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoadDataTask().execute();
            }
        });
    }

    private class SaveDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            String data = inputData.getText().toString();
            try (FileOutputStream fos = openFileOutput("saved_data.txt", MODE_PRIVATE)) {
                fos.write(data.getBytes());
                inputData.getText().clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            progressBar.setVisibility(View.GONE);
        }
    }

    private class LoadDataTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... params) {
            try (FileInputStream fis = openFileInput("saved_data.txt")) {
                int size = fis.available();
                byte[] buffer = new byte[size];
                fis.read(buffer);
                return new String(buffer);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.GONE);
            if (result != null) {
                savedData.setText(result);
            }
        }
    }
}
