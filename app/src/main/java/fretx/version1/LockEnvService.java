package fretx.version1;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class LockEnvService extends Service {

    private static final long INTERVAL = TimeUnit.SECONDS.toMillis(3); // periodic interval to check in seconds -> 2 seconds
    private static final String TAG = LockEnvService.class.getSimpleName();

    private Thread t = null;
    private static Context ctx = null;
    private boolean running = false;

    @Override
    public void onDestroy() {
        Log.e(TAG, "Stopping service 'LockEnvService'");
        running =false;
        super.onDestroy();
    }

    private void showLockMessage(){
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(
                new Runnable() {
                    @Override
                    public void run() {
                        stopViaData();
                        Toast.makeText(ctx, "Process is in background", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Starting service 'LockEnvService'");
        running = true;
        ctx = getBaseContext();

        // start a thread that periodically checks if your app is in the foreground
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                do {
                    //handleLockEnvMode();
                    if(!isCurPackLaunchedApp()) {
                        showLockMessage();
                        stopSelf();
                    }
                    try {
                        Thread.sleep(INTERVAL);
                    } catch (InterruptedException e) {
                        Log.i(TAG, "Thread interrupted: 'LockEnvService'");
                    }
                }while(running);
                stopSelf();
            }
        });

        t.start();
        return Service.START_NOT_STICKY;
    }

    private boolean isCurPackLaunchedApp(){
        ActivityManager am = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        String topRunningPackageName = null;
        if(Build.VERSION.SDK_INT > 20){
            topRunningPackageName = am.getRunningAppProcesses().get(0).processName;
        }
        else{
            topRunningPackageName = am.getRunningTasks(1).get(0).topActivity.getPackageName();
        }
        return (topRunningPackageName.equals(getPackageName()));
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public void stopViaData() {
        byte[] array = new byte[]{0};
        BluetoothClass.mHandler.obtainMessage(BluetoothClass.ARDUINO, array).sendToTarget();
    }


}