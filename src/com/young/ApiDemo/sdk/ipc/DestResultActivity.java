package com.young.ApiDemo.sdk.ipc;

import java.lang.InterruptedException;
import com.young.ApiDemo.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.content.Intent;

public class DestResultActivity extends Activity {
    private static final String TAG = "ApiDemo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sdk_ipc_activity_destresult);
        Log.i(TAG, "enter SDK IPC DestResultActivity Activity");
        Intent intent = new Intent();
        intent.putExtra("dest_result", "I'm from DestResultActivity");
        setResult(2, intent); // resultCode
    }
}
