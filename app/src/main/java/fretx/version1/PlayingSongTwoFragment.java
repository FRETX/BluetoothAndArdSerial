package fretx.version1;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;

public class PlayingSongTwoFragment extends Fragment {
    ObservableVideoView vvMain;
    RelativeLayout llMain;
    Tab2Activity mActivity;
    View rootView = null;
    MediaController mc;
    ToggleButton tgSwitch;
    Button   btStartLoop;
    Button   btEndLoop;
    TextView tvStartTime;
    TextView tvEndTime;
    boolean bStartCheckFlag = false;        ///Flag that current time is passed start time.
    boolean bEndCheckFlag = false;          ///Flag that current time is passed end time.

    Hashtable lstTimeText = new Hashtable();
    int[] arrayKeys;
    Boolean[] arrayCallStatus;

    private int m_currentTime = 0;                ////Now playing time.
    int durationTime = 0;               ///Video duration
    int startPos = 0;                   ///start point of loop
    int endPos = 0;                     ///end point of loop
    boolean  mbLoopable = false;        ///flag of checking loop
    boolean mbPlaying = true;           ///Flag of now playing.
    Uri videoUri;
    private Handler mCurTimeShowHandler = new Handler();

    boolean mbSendingFlag = false;

    private String strBuffer= null;

    public PlayingSongTwoFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = (Tab2Activity)getActivity();
        rootView = inflater.inflate(R.layout.playing_activity, container, false);
        initTxt();      ///Read titles from text resource.
        initUI();       ///Init UI(textView, VideoView....)
        return rootView;
    }
    void getStartEndTime()      ///Set startPos and endPos from TextView of tvStartTime and tvEndTime
                                ///Convert String data of TextView to Integer data.
    {
        if(tvStartTime.getText().toString().length() != 0)
            startPos = Integer.parseInt(tvStartTime.getText().toString());
        else
            startPos = 0;
        if(tvEndTime.getText().toString().length() != 0)
            endPos = Integer.parseInt(tvEndTime.getText().toString());
        else
            endPos = 0;
    }
    private void initUI() {

        llMain = (RelativeLayout)rootView.findViewById(R.id.llVideoView);
        tgSwitch = (ToggleButton)rootView.findViewById(R.id.tgSwitch);   ///ToggleButton that sets loop.
        btStartLoop = (Button)rootView.findViewById(R.id.btnStartLoop);  ///Button that sets startTime while playing video.
        btEndLoop = (Button)rootView.findViewById(R.id.btnEndLoop);      ///Button that sets endTime while playing video.
        tvStartTime = (TextView)rootView.findViewById(R.id.tvStartTime);
        tvEndTime = (TextView)rootView.findViewById(R.id.tvEndTime);
        tgSwitch.setChecked(false);
        tvStartTime.setText("0");
        tvEndTime.setText("0");
        videoUri = Uri.parse("android.resource://" + mActivity.getPackageName() + "/" + R.raw.songloopoff2);
        vvMain = (ObservableVideoView)rootView.findViewById(R.id.vvMainPlayer);
        vvMain.setVideoURI(videoUri);
        ////New MediaController including VideoView. It shows timeline and play and forward,
        // backward button on the VideoView.
        mc = new MediaController(vvMain.getContext());
        mc.setMediaPlayer(vvMain);
        mc.setAnchorView(llMain);

        vvMain.setMediaController(mc);
        vvMain.start(); ///Play Video
        durationTime = vvMain.getDuration(); //Get video duration.

        ////This is runnable thread that sets currentTime to tvCurTime TextView and check loop
        // available through startPos and endPos

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                {
                    ///Set currentTime to current time textview.
                    m_currentTime = vvMain.getCurrentPosition();
                    ///Set the current title of current time.
                    changeText(m_currentTime);

                    if(mbLoopable){
                        if(startPos >= endPos) {
                            Toast.makeText(mActivity, "Start time is bigger than End time.", Toast.LENGTH_LONG).show();
                            mbLoopable = false;
                            tgSwitch.setChecked(false);
                        }else{
                            ///if currentTime is smaller than startPos then set bStartCheckFlag
                            // true.
                            //and set endChekFlag to false. Set current pos to startPos. and loop
                            // video.
                            if((m_currentTime < startPos) && (!bStartCheckFlag)){
                                vvMain.seekTo(startPos);
                                bStartCheckFlag = true;
                                bEndCheckFlag = false;
                            }

                            ///if currentTime is bigger than startPos then set bEndCheckFlag
                            // true.
                            //and set startChekFlag to false.
                            ///Set current pos to startPos. and loop video.
                            if((m_currentTime > endPos) && (!bEndCheckFlag)){
                                bEndCheckFlag = false;
                                bStartCheckFlag = true;
                                vvMain.seekTo(startPos);
                            }
                        }
                    }
                    mCurTimeShowHandler.postDelayed(this, 1);
                }
            }
        };
        mCurTimeShowHandler.post(runnable);

        ///Set loopable flag.
        tgSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mbLoopable) {
                    tvStartTime.setTextColor(Color.parseColor("#000000"));
                    tvEndTime.setTextColor(Color.parseColor("#000000"));
                    mbLoopable = false;
                } else {
                    ///check current time is in duration of startPosition and endPosition.
                    String strStartPos = tvStartTime.getText().toString();
                    String strEndPos = tvEndTime.getText().toString();
                    int nTmpStartPos, nTmpEndPos;
                    if(strStartPos.length() != 0)
                        nTmpStartPos = Integer.parseInt(strStartPos);
                    else {
                        nTmpStartPos = 0;
                        tvStartTime.setText("0");
                    }
                    if(strEndPos.length() != 0)
                        nTmpEndPos = Integer.parseInt(strEndPos);
                    else {
                        nTmpEndPos = 0;
                        tvEndTime.setText("0");
                    }
                    ///if start position is bigger than end position then can not loop.
                    if (nTmpStartPos >= nTmpEndPos) {
                        tvStartTime.setTextColor(Color.parseColor("#000000"));
                        tvEndTime.setTextColor(Color.parseColor("#000000"));
                        mbLoopable = false;
                        Toast.makeText(mActivity, "Start time is bigger than End time.", Toast.LENGTH_LONG).show();
                        tgSwitch.setChecked(false);
                    } else {
                        tvStartTime.setTextColor(Color.parseColor("#FF0000"));
                        tvEndTime.setTextColor(Color.parseColor("#0000FF"));
                        getStartEndTime();
                        if ((m_currentTime < startPos) || (m_currentTime > endPos)) {
                            m_currentTime = startPos;
                            vvMain.seekTo(startPos);
                        }
                        bStartCheckFlag = false;
                        bEndCheckFlag = false;
                        mbLoopable = true;
                    }
                }
            }
        });
///Set startPosition of video by pressing "START LOOP" Button
        btStartLoop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPos = m_currentTime;
                tvStartTime.setText(String.format("%d", startPos));
            }
        });
///Set endPosition of video by pressing "END LOOP" Button
        btEndLoop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endPos = m_currentTime;
                tvEndTime.setText(String.format("%d", endPos));
            }
        });

        /////This is videoview listener.
        vvMain.setVideoViewListener(new ObservableVideoView.IVideoViewActionListener() {
            @Override
            public void onPause() {
                mbPlaying = false;
                ////if press pause button then stop loop.
//                stopViaData();
                mCurTimeShowHandler.removeCallbacks(runnable);
            }

            @Override
            public void onResume() {
                mbPlaying = true;
                getStartEndTime();
                ////if press resume button then start loop
                mCurTimeShowHandler.post(runnable);
            }

            @Override
            public void onTimeBarSeekChanged(int currentTime) {
                if (mbLoopable) {
                    ///When current time is setted from seeking timeline,
                    ///If current time is smaller than start position then set bStartChekFlag false.
                    ///current time is smaller than start position, compare start position and
                    // current time in thread.it must check in thread.
                    ///Also sets bEndCheckFlag false when current time is bigger than end position.
                    ///So the thread have to compare between end position and current time.
                    if (currentTime < startPos)
                        bStartCheckFlag = false;
                    else if (currentTime > endPos)
                        bEndCheckFlag = false;
                    else
                    {
                        ///When current time is in the duration of loop.
                        bStartCheckFlag = true;
                        bEndCheckFlag = false;
                    }
                }
                //Set the current time of textview and change the text of current timee while
                // seeking the timeline.
                m_currentTime = currentTime;
                changeText(currentTime);
            }

        });

    }


    ///Read from text source and get the array of time and its text.
    public void initTxt()
    {
        String str= readRawTextFile(mActivity.getBaseContext(), R.raw.songloopoff2txt);
        String[] strArray = str.split( "\n" );
        for( int nIndex= 0; nIndex < strArray.length; nIndex++ )
        {
            ///Split the every line of source text to two parts.
            // Every line is splited by ' ',
            String[] strArrTemp = strArray[nIndex].split(" ");
            String strTime = strArrTemp[0];     ///This is time
            String strText = strArrTemp[1];     // This is text of that strTime.
            ///If ther's same time, then add two text to hashtable.
            // else add one text of the time to hashtable.
            if(lstTimeText.containsKey(Integer.parseInt(strTime)))
            // if there's same key in the
            // hashtable then add other text of same time.
            {
                String strTemp = (String)lstTimeText.get(Integer.parseInt(strTime));
                lstTimeText.put(Integer.parseInt(strTime), strTemp + ":" + strText);
            }else
                lstTimeText.put(Integer.parseInt(strTime), strText);

        }
        ///save the key array of hashtable to int array.
        arrayKeys = new int[lstTimeText.size()];
        arrayCallStatus = new Boolean[lstTimeText.size()];

        int i = 0;
        for ( Enumeration e = lstTimeText.keys(); e.hasMoreElements(); ) {
            arrayKeys[i] = (int) e.nextElement();
            arrayCallStatus[i] = false;
            i++;
        }
        Arrays.sort(arrayKeys);
    }
    ///Read the text file from resource(Raw) and divide by end line mark('\n")
    public static String readRawTextFile(Context ctx, int resId) {
        InputStream inputStream = ctx.getResources().openRawResource(resId);

        InputStreamReader inputreader = new InputStreamReader(inputStream);
        BufferedReader buffreader = new BufferedReader(inputreader);
        String line;
        StringBuilder text = new StringBuilder();

        try {
            while ((line = buffreader.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
        } catch (IOException e) {
            return null;
        }
        return text.toString();
    }
    ///changeText(int currentTime)
    ///Set the text of currentTime.
    public void changeText(int currentTime)
    {
        //From the first to number of hashtable keys, Search index that its value is bigger than
        // current time. Then sets the text that was finded in hashtable keys.
        for ( int nIndex = 0; nIndex < arrayKeys.length -1; nIndex++ )
        {
            if ( arrayKeys[nIndex] <= currentTime && arrayKeys[nIndex + 1] > currentTime )
            {
                if( arrayCallStatus[nIndex] )
                    return;

                arrayCallStatus[nIndex] = true;
                ConnectThread connectThread = new ConnectThread(str2array((String) lstTimeText.get(arrayKeys[nIndex])));
                connectThread.run();
                setDefaultValues(arrayCallStatus);
                arrayCallStatus[nIndex] = true;

            }
        }

        if ( arrayKeys[arrayKeys.length -1] <= currentTime )
        {
            if( arrayCallStatus[arrayKeys.length -1] )
                return;

            arrayCallStatus[arrayKeys.length -1] = true;
            //tvNotice.setText((String) lstTimeText.get(arrayKeys[arrayKeys.length -1]));
            ConnectThread connectThread = new ConnectThread(str2array((String) lstTimeText.get(arrayKeys[arrayKeys.length -1])));
            connectThread.run();
            setDefaultValues(arrayCallStatus);
            arrayCallStatus[arrayKeys.length -1] = true;
        }
    }
    public void setDefaultValues(Boolean[] bArray)
    {
        for (int i = 0; i < bArray.length; i ++){
            bArray[i] = false;
        }
    }
    public void startViaData(byte[] array) {
        BluetoothClass.mHandler.obtainMessage(BluetoothClass.ARDUINO, array).sendToTarget();
    }

    public void stopViaData() {
        byte[] array = new byte[]{0};
        BluetoothClass.mHandler.obtainMessage(BluetoothClass.ARDUINO, array).sendToTarget();
    }
    public byte[] str2array(String string){
        String strSub = string.replaceAll("[{}]", "");
        String[] parts = strSub.split(",");
        byte[] array = new byte[parts.length];
        for (int i = 0; i < parts.length; i ++)
        {
            array[i] = Byte.valueOf(parts[i]);
        }
        return array;
    }
    private class ConnectThread extends Thread {
        byte[] array;
        public ConnectThread(byte[] tmp) {
            array = tmp;
        }

        public void run() {
            try {
                // Connect the device through the socket. This will block
                // until it succeeds or throws an exception
                startViaData(array);
            } catch (Exception connectException) {
                Log.i(BluetoothClass.tag, "connect failed");
                // Unable to connect; close the socket and get out
                try {
                    BluetoothClass.mmSocket.close();
                } catch (IOException closeException) {
                    Log.e(BluetoothClass.tag, "mmSocket.close");
                }
                return;
            }
            // Do work to manage the connection (in a separate thread)
            if (BluetoothClass.mHandler == null)
                Log.v("debug", "mHandler is null @ obtain message");
            else
                Log.v("debug", "mHandler is not null @ obtain message");
            mbSendingFlag = false;
        }
    }
}
