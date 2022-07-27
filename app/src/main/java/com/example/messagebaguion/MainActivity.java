package com.example.messagebaguion;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String PREF_NIGHT = "NIGHT";
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getSharedPreferences("Baguion", Activity.MODE_PRIVATE);
        editor = preferences.edit();
        boolean night = preferences.getBoolean(PREF_NIGHT, false);
        initializeNightMode(night);
        btnSendListenerMethod();
        btnCallListenerMethod();
        btnMapListenerMethod();
        btnWebListenerMethod();
        swNightListenerMethod();
    }

    private void initializeNightMode(boolean night) {
        ConstraintLayout clMain = findViewById(R.id.clMain);
        int textviews[] = {R.id.etMessage, R.id.swNight};
        if (night) {
            Switch swNight = findViewById(R.id.swNight);
            swNight.setChecked(true);
            clMain.setBackgroundColor(Color.BLACK);
            for (int tv : textviews) {
                TextView v = findViewById(tv);
                v.setTextColor(Color.WHITE);
            }
        } else {
            clMain.setBackgroundColor(Color.WHITE);
            for (int tv : textviews) {
                TextView v = findViewById(tv);
                v.setTextColor(Color.BLACK);
            }
        }
    }

    private void swNightListenerMethod() {
        Switch swNight = findViewById(R.id.swNight);
        swNight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConstraintLayout clMain = findViewById(R.id.clMain);
                int textviews[] = {R.id.etMessage, R.id.swNight};
                if (swNight.isChecked()) {
                    editor.putBoolean(PREF_NIGHT, true);
                    clMain.setBackgroundColor(Color.BLACK);
                    for (int tv : textviews) {
                        TextView v = findViewById(tv);
                        v.setTextColor(Color.WHITE);
                    }
                } else {
                    editor.putBoolean(PREF_NIGHT, false);
                    clMain.setBackgroundColor(Color.WHITE);
                    for (int tv : textviews) {
                        TextView v = findViewById(tv);
                        v.setTextColor(Color.BLACK);
                    }
                }
                editor.apply();
            }
        });
    }





    private void btnWebListenerMethod() {
        ImageButton btnWeb = findViewById(R.id.btnWeb);
        Uri webpage = Uri.parse("https://www.google.co.kr");
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        btnWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });

    }

    private void btnMapListenerMethod() {
        ImageButton btnMap = findViewById(R.id.btnMap);
        Uri map = Uri.parse("geo:0,0?q=City+of+Ormoc+City+Ormoc+Philippines");
        Intent intent = new Intent(Intent.ACTION_VIEW, map);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });
    }
    private void btnCallListenerMethod() {
        ImageButton btnCall = findViewById(R.id.btnCall);
        Uri number = Uri.parse("tel:09192066499");
        Intent intent = new Intent(Intent.ACTION_DIAL, number);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });
    }

    private void btnSendListenerMethod() {

        Intent intent = new Intent(this, MessageActivity.class);
        Button btnSend = findViewById(R.id.btnSend);

        ActivityResultLauncher<Intent>  launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK){
                            Intent outIntent = result.getData();
                            boolean good = outIntent.getBooleanExtra(MessageActivity.EXTRA_RESULT, false);
                            if (good) {
                                Toast.makeText(getBaseContext(), "This is a GOOD message", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getBaseContext(), "This is a BAD message", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etMessage = findViewById(R.id.etMessage);
                String message = etMessage.getText().toString();
                intent.putExtra(MessageActivity.EXTRA_MESSAGE, message);
                launcher.launch(intent);
            }
        });
    }
}