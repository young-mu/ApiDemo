package com.young.ApiDemo.sdk;

import com.young.ApiDemo.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class WidgetActivity extends Activity implements OnClickListener {
    private static final String TAG = "ApiDemo";
    private Button recyclerviewBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sdk_widget);
        Log.i(TAG, "enter SDK Widget Activity");
        // get buttons and set listeners
        recyclerviewBtn = (Button)findViewById(R.id.recyclerview_button);
        recyclerviewBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.recyclerview_button:
            break;
        default:
            break;
        }
    }
}
