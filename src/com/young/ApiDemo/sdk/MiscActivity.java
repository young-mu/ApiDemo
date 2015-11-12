package com.young.ApiDemo.sdk;

import com.young.ApiDemo.R;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.os.Build;
import android.os.Environment;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import dalvik.system.DexClassLoader;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

public class MiscActivity extends Activity implements OnClickListener {
    private static final String TAG = "ApiDemo";
    private Button abiBtn;
    private Button envDirBtn;
    private Button appDirBtn;
    private Button classLoaderBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sdk_misc);
        Log.i(TAG, "enter SDK Misc Activity");
        // get buttons and set listeners
        abiBtn = (Button)findViewById(R.id.abi_button);
        envDirBtn = (Button)findViewById(R.id.env_directory_button);
        appDirBtn = (Button)findViewById(R.id.app_directory_button);
        classLoaderBtn = (Button)findViewById(R.id.classloader_button);
        abiBtn.setOnClickListener(this);
        envDirBtn.setOnClickListener(this);
        appDirBtn.setOnClickListener(this);
        classLoaderBtn.setOnClickListener(this);
    }

    public void getSystemABI() {
        // get ABI from Java level
        Log.i(TAG, "CPU_ABI(java): " + Build.CPU_ABI);
        String[] abis = Build.SUPPORTED_ABIS;
        for (int i = 0; i < abis.length; i ++) {
            Log.i(TAG, "abis[" + i + "](java): " + abis[i]);
        }
        // get ABI from Native level
        try {
            Process process = Runtime.getRuntime().exec("getprop ro.product.cpu.abi");
            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            Log.i(TAG, "CPU_ABI(native): " + bufferedReader.readLine());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void getEnvironmentDirectories() {
        Log.i(TAG, "getRootDirectory(): " + Environment.getRootDirectory().toString());
        Log.i(TAG, "getDataDirectory(): " + Environment.getDataDirectory().toString());
        Log.i(TAG, "getDownloadCacheDirectory(): " + Environment.getDownloadCacheDirectory().toString());
        Log.i(TAG, "getExternalStorageDirectory(): " + Environment.getExternalStorageDirectory().toString());
        Log.i(TAG, "getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES): " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString());
        Log.i(TAG, "isExternalStorageEmulated(): " + Environment.isExternalStorageEmulated());
        Log.i(TAG, "isExternalStorageRemovable(): " + Environment.isExternalStorageRemovable());
    }

    public void getApplicationDirectories(Context context) {
        Log.i(TAG, "getFilesDir(): " + context.getFilesDir().toString());
        Log.i(TAG, "getCacheDir(): " + context.getCacheDir().toString());
        Log.i(TAG, "getExternalFilesDir(null): " + context.getExternalFilesDir(null).toString());
        Log.i(TAG, "getExternalCacheDir(): " + context.getExternalCacheDir().toString());
    }

    public void useDexClassLoader() {
        Intent intent = new Intent("young.android.plugin");
        PackageManager pm = getPackageManager();
        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, PackageManager.MATCH_ALL); // MATCH_ALL is available after API23
        if (resolveInfos.size() == 0) {
            Log.e(TAG, "resolveInfos length is 0, CHECK if the plugin is installed");
            return;
        } else {
            for (ResolveInfo resolveInfo : resolveInfos) {
                Log.i(TAG, "resolveInfo: " + resolveInfo);
            }
        }
        ActivityInfo actInfo = resolveInfos.get(0).activityInfo;
        String packageName = actInfo.packageName;
        Log.i(TAG, "packageName: " + packageName);

        String apkPath = actInfo.applicationInfo.sourceDir;
        String dexOutputPath = getApplicationInfo().dataDir;
        String libPath = actInfo.applicationInfo.nativeLibraryDir;
        Log.i(TAG, "apkPath: " + apkPath);
        Log.i(TAG, "dexOutputPath: " + dexOutputPath);
        Log.i(TAG, "libPath: " + libPath);
        Log.i(TAG, "classLoader: " + this.getClass().getClassLoader().toString());
        DexClassLoader classLoader = new DexClassLoader(apkPath, dexOutputPath, libPath, this.getClass().getClassLoader());

        try {
            Class clazz = classLoader.loadClass(packageName + ".Plugin");
            Object obj = clazz.newInstance();
            Class[] params = new Class[2];
            params[0] = Integer.TYPE;
            params[1] = Integer.TYPE;
            Method mtd = clazz.getMethod("function", params);
            Integer result = (Integer)mtd.invoke(obj, 100, 200);
            Log.i(TAG, "result is: " + result.toString());
        } catch (ClassNotFoundException e) { // loadClass
            e.printStackTrace();
        } catch (IllegalAccessException e) { // newInstance/invoke
            e.printStackTrace();
        } catch (InstantiationException e) { // newInstance/invoke
            e.printStackTrace();
        } catch (NoSuchMethodException e) { // getMethod
            e.printStackTrace();
        } catch (NullPointerException e) { // inovke
            e.printStackTrace();
        } catch (InvocationTargetException e) { // invoke
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.abi_button:
            getSystemABI();
            break;
        case R.id.env_directory_button:
            getEnvironmentDirectories();
            break;
        case R.id.app_directory_button:
            getApplicationDirectories(this);
            break;
        case R.id.classloader_button:
            useDexClassLoader();
            break;
        default:
            break;
        }
    }
}
