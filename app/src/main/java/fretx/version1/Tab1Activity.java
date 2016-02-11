package fretx.version1;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

/**
 * Created by Misho on 2/4/2016.
 */

public class Tab1Activity extends FragmentActivity implements YesNoDialogFragment1.YesNoDialogListener
{
    Tab1ActivityButtonFragment buttonFragment;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learn_activity_container);
        Global.tab1Activity = this;

        buttonFragment = new Tab1ActivityButtonFragment();

        FragmentManager fragmentManager = this.getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame1, buttonFragment);
        fragmentTransaction.commit();

    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //No call for super(). Bug on API Level > 11.
    }
    @Override
    public void onFinishYesNoDialog(int state) {
        if (state == 1){
            FragmentManager fragmentManager = this.getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_frame1, new Tab1ActivityButtonFragment());
            fragmentTransaction.commit();
            stopViaData();
        }else if(state == 2){
            FragmentManager fragmentManager = this.getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_frame1, new PlayingLearnTwoFragment());
            fragmentTransaction.commit();
        }else if(state == 3){
            FragmentManager fragmentManager = this.getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_frame1, new PlayingLearnThreeFragment());
            fragmentTransaction.commit();
        }
    }
    public void showYesNoDialog1() {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        YesNoDialogFragment1 yesnoDialog = new YesNoDialogFragment1();
        yesnoDialog.setCancelable(true);
        yesnoDialog.setDialogTitle("Congrats");
        yesnoDialog.show(fragmentManager, "Yes/No Dialog");
    }

    public void showYesNoDialog2() {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        YesNoDialogFragment2 yesnoDialog = new YesNoDialogFragment2();
        yesnoDialog.setCancelable(true);
        yesnoDialog.setDialogTitle("Congrats");
        yesnoDialog.show(fragmentManager, "Yes/No Dialog");
    }
    public void showYesNoDialog3() {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        YesNoDialogFragment3 yesnoDialog = new YesNoDialogFragment3();
        yesnoDialog.setCancelable(true);
        yesnoDialog.setDialogTitle("Congrats");
        yesnoDialog.show(fragmentManager, "Yes/No Dialog");
    }
    @Override
    public void onBackPressed() {
        if (Global.bTab1State == true) {//the fragment on which you want to handle your back press
            FragmentManager fragmentManager = this.getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_frame1, new Tab1ActivityButtonFragment());
            fragmentTransaction.commit();
            Global.bTab1State = false;
            stopViaData();
        }else{
//            try{
//                BluetoothClass.mmSocket.close();
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//
//            super.onBackPressed();
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
