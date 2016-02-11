package fretx.version1;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Tab2ActivityButtonFragment extends Fragment {

    Tab2Activity mActivity;
    View rootView = null;

    Button btSongOne;
    Button btSongTwo;

    public Tab2ActivityButtonFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = (Tab2Activity)getActivity();
        rootView = inflater.inflate(R.layout.play_activity_buttons, container, false);
        btSongOne = (Button)rootView.findViewById(R.id.btSongOne);
        Global.bTab2State = false;
        btSongOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = mActivity.getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, new PlayingSongOneFragment()).addToBackStack("PLAY").commit();
                Global.bTab2State = true;
            }
        });
        btSongTwo= (Button)rootView.findViewById(R.id.btSongTwo);
        btSongTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = mActivity.getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, new PlayingSongTwoFragment()).addToBackStack("PLAY").commit();
                Global.bTab2State = true;
            }
        });

        return rootView;
    }
}