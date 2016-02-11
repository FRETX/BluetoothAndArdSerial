package fretx.version1;

import android.app.Fragment;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

public class PlayingLearnOneFragment extends Fragment {
    static ObservableVideoView vvMain1;
//    MediaController mc;
    private Handler mCurTimeShowHandler = new Handler();
    RelativeLayout llMain;
    Tab1Activity mActivity;
    View rootView = null;
    Button   btLearned;
    Button   btPlayReplay;
    TextView tvTitle;

    Uri videoUri;


    int currentTime = 0;                ////Now playing time.
    int durationTime = 0;               ///Video duration

    public PlayingLearnOneFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = (Tab1Activity)getActivity();
        rootView = inflater.inflate(R.layout.learning_play_activity, container, false);
        initUI();       ///Init UI(textView, VideoView....)
        return rootView;
    }

    private void initUI() {


        llMain = (RelativeLayout)rootView.findViewById(R.id.llVideoView);
        tvTitle = (TextView)rootView.findViewById(R.id.tvTitle);
        videoUri = Uri.parse("android.resource://" + mActivity.getPackageName() + "/" + R.raw.learn_ex1);
        vvMain1 = (ObservableVideoView)rootView.findViewById(R.id.vvMain);
        vvMain1.setVideoURI(videoUri);
//        mc = new MediaController(vvMain.getContext());
//        mc.setMediaPlayer(vvMain);
//        mc.setAnchorView(llMain);
//        vvMain.setMediaController(mc);
        vvMain1.start();
        durationTime = vvMain1.getDuration(); //Get video duration.
        vvMain1.pause();

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
            {
                if (!vvMain1.isPlaying()){
                    startButton();
                }
                mCurTimeShowHandler.postDelayed(this, 1);
            }
            }
        };
        mCurTimeShowHandler.post(runnable);

        btLearned = (Button)rootView.findViewById(R.id.btLearned);
        btLearned.setVisibility(View.INVISIBLE);
        btLearned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.showYesNoDialog1();
            }
        });
        btPlayReplay = (Button)rootView.findViewById(R.id.btPlayReplay);
        btPlayReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btPlayReplay.setVisibility(View.INVISIBLE);
                vvMain1.start();
            }
        });
    }
    public void startButton(){
        btLearned.setVisibility(View.VISIBLE);
        btPlayReplay.setVisibility(View.VISIBLE);
    }
    public static VideoView getVideoView(){
        return vvMain1;
    }
}
