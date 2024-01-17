package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private EditText inputData;
    private TextView savedData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputData =  findViewById(R.id.editTextInput);
        savedData = findViewById(R.id.textViewSavedData);

        Button saveButton = findViewById(R.id.buttonSave);
        Button loadButton = findViewById(R.id.buttonLoad);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
    }

    private void saveData() {
        String data = inputData.getText().toString();
        try (FileOutputStream fos = openFileOutput("saved_data.txt", MODE_PRIVATE)) {
            fos.write(data.getBytes());
            inputData.getText().clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        try (FileInputStream fis = openFileInput("saved_data.txt")) {
            int size = fis.available();
            byte[] buffer = new byte[size];
            fis.read(buffer);
            String data = new String(buffer);
            savedData.setText(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
