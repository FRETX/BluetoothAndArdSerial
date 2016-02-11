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
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Misho on 2/4/2016.
 */

public class Tab3Activity extends Activity
{
    //the images to display
    Integer[] imageIDs = {
            R.drawable.dor,
            R.drawable.re,
            R.drawable.mi,
            R.drawable.fa,
            R.drawable.so,
            R.drawable.la,
            R.drawable.si
    };
    Integer[] imageBackgroundIDs = {
            R.drawable.backone,
            R.drawable.backtwo,
            R.drawable.backthree,
            R.drawable.backfour,
            R.drawable.backfive,
            R.drawable.backsix,
            R.drawable.backseven
    };
    ObservableVideoView vvMain;
    Uri[] videoUri = new Uri[7];
    ArrayList<byte[]> musicArray = new ArrayList<>(7);
    ImageView imgBack;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chord_activity_container1);


        vvMain = (ObservableVideoView)findViewById(R.id.vvMain);
        imgBack = (ImageView)findViewById(R.id.imgBackground);
        imgBack.setImageResource(imageBackgroundIDs[0]);

        // input data file
        videoUri[0] = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.cmajor);
        videoUri[1] = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.dmajor);
        videoUri[2] = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.emajor);
        videoUri[3] = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.fmajor);
        videoUri[4] = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.gmajor);
        videoUri[5] = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.amajor);
        videoUri[6] = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.bmajor);


        byte[] array1 = new byte[]{1,3,12,24,35,0};
        byte[] array2 = new byte[]{4,21,23,32,0};
        byte[] array3 = new byte[]{1,2,6,13,24,25,0};
        byte[] array4 = new byte[]{11,12,16,23,34,35,0};
        byte[] array5 = new byte[]{2,3,4,25,31,36,0};
        byte[] array6 = new byte[]{1,5,22,23,24,0};
        byte[] array7 = new byte[]{21,25,32,43,44,0};//{21,25,32,43,44,0};//{21,25,32,43,44,0}
        musicArray.add(array1);
        musicArray.add(array2);
        musicArray.add(array3);
        musicArray.add(array4);
        musicArray.add(array5);
        musicArray.add(array6);
        musicArray.add(array7);
        Gallery gallery = (Gallery) findViewById(R.id.gallery1);
        gallery.setAdapter(new ImageAdapter(this));
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(getBaseContext(), "pic" + (position + 1) + " selected", Toast.LENGTH_SHORT).show();
                playChord(position);
            }
        });
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //No call for super(). Bug on API Level > 11.
    }
    public void playChord(int i){
        vvMain.setVideoURI(videoUri[i]);
        vvMain.start();
        imgBack.setImageResource(imageBackgroundIDs[i]);
        startViaData(i);

    }
    public class ImageAdapter extends BaseAdapter {
        private Context context;
        private int itemBackground;
        public ImageAdapter(Context c)
        {
            context = c;
            // sets a grey background; wraps around the images
            TypedArray a =obtainStyledAttributes(R.styleable.MyGallery);
            itemBackground = a.getResourceId(R.styleable.MyGallery_android_galleryItemBackground, 0);
            a.recycle();
        }
        // returns the number of images
        public int getCount() {
            return imageIDs.length;
        }
        // returns the ID of an item
        public Object getItem(int position) {
            return position;
        }
        // returns the ID of an item
        public long getItemId(int position) {
            return position;
        }
        // returns an ImageView view
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(imageIDs[position]);
            imageView.setLayoutParams(new Gallery.LayoutParams(300, 450));
            imageView.setBackgroundResource(itemBackground);
            return imageView;
        }
    }
    public void startViaData(int index) {
        BluetoothClass.mHandler.obtainMessage(BluetoothClass.ARDUINO, musicArray.get(index)).sendToTarget();
    }

    public void stopViaData() {
        byte[] array = new byte[]{0};
        BluetoothClass.mHandler.obtainMessage(BluetoothClass.ARDUINO, array).sendToTarget();
    }
    @Override
    public void finish(){
        stopViaData();
    }
    @Override
    public void onBackPressed() {
//        try{
//            stopViaData();
//            BluetoothClass.mmSocket.close();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        super.onBackPressed();
//        finish();
//        Intent main = new Intent(Intent.ACTION_MAIN);
//        main.addCategory(Intent.CATEGORY_HOME);
//        main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(main);
    }
}
