package fretx.version1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;

/**
 * Created by Misho on 2/4/2016.
 */

public class PresentationActivity extends Activity
{
    //the images to display

    ObservableVideoView vvMain;
    MediaController mc;
    Uri videoUri;
    Button btGoMenu;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presentation_activity);


        videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video_intro);

        vvMain = (ObservableVideoView)findViewById(R.id.vvMain);
        vvMain.setVideoURI(videoUri);
        mc = new MediaController(vvMain.getContext());
        mc.setMediaPlayer(vvMain);
//        mc.setAnchorView(llMain);

        vvMain.setMediaController(mc);

        vvMain.start();

        btGoMenu = (Button)findViewById(R.id.btGoMenu);
        btGoMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(PresentationActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        //No call for super(). Bug on API Level > 11.
//    }

    @Override
    public void onBackPressed() {
//        try{
//            BluetoothClass.mmSocket.close();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        super.onBackPressed();
    }

}
