package fretx.version1;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Tab1ActivityButtonFragment extends Fragment {

    Tab1Activity mActivity;
    View rootView = null;

    Button btExerciseOne;
    Button btExerciseTwo;
    Button btExerciseThree;

    public Tab1ActivityButtonFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = (Tab1Activity)getActivity();
        rootView = inflater.inflate(R.layout.learn_activity_buttons, container, false);
        btExerciseOne = (Button)rootView.findViewById(R.id.btExerciseOne);
        Global.bTab1State = false;
        btExerciseOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = mActivity.getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame1, new PlayingLearnOneFragment()).addToBackStack("LEARN").commit();
                Global.bTab1State = true;
            }
        });
        btExerciseTwo = (Button)rootView.findViewById(R.id.btExerciseTwo);
        btExerciseTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = mActivity.getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame1, new PlayingLearnTwoFragment()).addToBackStack("LEARN").commit();
                Global.bTab1State = true;
            }
        });
        btExerciseThree = (Button)rootView.findViewById(R.id.btExerciseThree);
        btExerciseThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = mActivity.getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame1, new PlayingLearnThreeFragment()).addToBackStack("LEARN").commit();
                Global.bTab1State = true;
            }
        });
        return rootView;
    }
}