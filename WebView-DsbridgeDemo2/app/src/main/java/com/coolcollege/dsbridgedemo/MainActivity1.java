package com.coolcollege.dsbridgedemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity1 extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_d_webview1);

        findViewById(R.id.kuxueyuan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity1.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
