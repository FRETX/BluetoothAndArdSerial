package fretx.version1;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by Misho on 2/4/2016.
 */

public class Tab2Activity extends FragmentActivity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(null);
        setContentView(R.layout.play_activity_container);

        Global.tab2Activity = this;

        FragmentManager fragmentManager = this.getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, new Tab2ActivityButtonFragment());
        fragmentTransaction.commit();

    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //No call for super(). Bug on API Level > 11.
    }

    @Override
    public void onBackPressed() {
        if (Global.bTab2State == true) {//the fragment on which you want to handle your back press
            FragmentManager fragmentManager = this.getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, new Tab2ActivityButtonFragment());
            fragmentTransaction.commit();
            Global.bTab2State = false;
            stopViaData();
        }else{
//            try{
//                stopViaData();
//                BluetoothClass.mmSocket.close();
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//            super.onBackPressed();
//            finish();
//            Intent main = new Intent(Intent.ACTION_MAIN);
//            main.addCategory(Intent.CATEGORY_HOME);
//            main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(main);
        }
    }

    public void stopViaData() {
        byte[] array = new byte[]{0};
        BluetoothClass.mHandler.obtainMessage(BluetoothClass.ARDUINO, array).sendToTarget();
    }
    @Override
    public void finish(){
        stopViaData();
    }
}
