package fretx.version1;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class YesNoDialogFragment1 extends DialogFragment {
	public Button btnYes,btnNo;
	static String DialogBoxTitle;




	public interface YesNoDialogListener {		
		void onFinishYesNoDialog(int state);
	}

	//---empty constructor required
	public YesNoDialogFragment1(){
		
	}
	//---set the title of the dialog window---
	public void setDialogTitle(String title) {
		DialogBoxTitle= title;
	}
	
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState ) {
		
		View view= inflater.inflate(R.layout.yes_no_dialogfragment, container);
		//---get the Button views---
		btnYes = (Button) view.findViewById(R.id.btnYes);
		btnNo = (Button) view.findViewById(R.id.btnNo);
		
		// Button listener 
		btnYes.setOnClickListener(btnListener);
		btnNo.setOnClickListener(btnListener);
		
		//---set the title for the dialog
		getDialog().setTitle(DialogBoxTitle);
		
		return view;
	}
	
	//---create an anonymous class to act as a button click listener
	private OnClickListener btnListener = new OnClickListener()
	{
		public void onClick(View v)
		{
			int state = 0;
			//---gets the calling activity---
			YesNoDialogListener activity = (YesNoDialogListener) getActivity();
			if (((Button) v).getText().toString().equals("Go to Learn Menu")){
				state = 1;
			}else if(((Button) v).getText().toString().equals("Next Exercise")) {
				state = 2;
			}

			activity.onFinishYesNoDialog(state);
			//---dismiss the alert---
			dismiss();
		}
	};
}
