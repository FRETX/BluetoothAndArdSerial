package fretx.version1;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;


public class MainActivity extends TabActivity
{
    /** Called when the activity is first created. */
    public TabHost tabHost;
    public TabHost.TabSpec tab1;
    public TabHost.TabSpec tab2;
    public TabHost.TabSpec tab3;

    private Intent m_intentTab1;
    private Intent m_intentTab2;
    private Intent m_intentTab3;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_sample);

        m_intentTab1=new Intent(this, Tab1Activity.class);
        m_intentTab2=new Intent(this, Tab2Activity.class);
        m_intentTab3=new Intent(this, Tab3Activity.class);
        // create the TabHost that will contain the Tabs
        tabHost = (TabHost)findViewById(android.R.id.tabhost);

        tab1 = tabHost.newTabSpec("First Tab");
        tab2 = tabHost.newTabSpec("Second Tab");
        tab3 = tabHost.newTabSpec("Third Tab");

        // Set the Tab name and Activity
        // that will be opened when particular Tab will be selected
        tab1.setIndicator("Learn");
        tab1.setContent(m_intentTab1);

        tab2.setIndicator("Play");
        tab2.setContent(m_intentTab2);

        tab3.setIndicator("Chord");
        tab3.setContent(m_intentTab3);

        /** Add the tabs  to the TabHost to display. */
        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if(tabId == "Second Tab"){
                    if (Global.tab1Activity != null){
                        FragmentManager fragmentManager = Global.tab1Activity.getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.content_frame1, new Tab1ActivityButtonFragment());
                        fragmentTransaction.commit();
                    }
                }
                else if(tabId == "First Tab") {
                    if (Global.tab2Activity != null){
                        FragmentManager fragmentManager = Global.tab2Activity.getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.content_frame, new Tab2ActivityButtonFragment());
                        if (!Global.tab2Activity.isDestroyed())
                            fragmentTransaction.commit();
                    }
                }else if(tabId == "Third Tab"){
                    if (Global.tab1Activity != null){
                        FragmentManager fragmentManager = Global.tab1Activity.getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.content_frame1, new Tab1ActivityButtonFragment());
                        fragmentTransaction.commit();
                    }
                    if (Global.tab2Activity != null){
                        FragmentManager fragmentManager = Global.tab2Activity.getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.content_frame, new Tab2ActivityButtonFragment());
                        if (!Global.tab2Activity.isDestroyed())
                            fragmentTransaction.commit();

                    }
                }
                stopViaData();
            }
        });

    }
    public void stopViaData() {
        byte[] array = new byte[]{0};
        BluetoothClass.mHandler.obtainMessage(BluetoothClass.ARDUINO, array).sendToTarget();
    }

    @Override
    protected  void onResume(){
        startService(new Intent(this, LockEnvService.class));
        super.onResume();
    }
}
