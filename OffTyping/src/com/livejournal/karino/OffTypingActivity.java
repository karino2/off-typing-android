package com.livejournal.karino;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class OffTypingActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        EditText et = (EditText)findViewById(R.id.editTextInput);
        et.addTextChangedListener(new TextWatcher(){

			public void afterTextChanged(Editable arg0) {
				String current = arg0.toString();
		        findTV(R.id.textViewDebug).setText(current);
		        if("いまはこれをにゅうりょく。".equals(current))
		        	((EditText)findViewById(R.id.editTextInput)).setText("");
			}

			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}

			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
			}});
        
        findTV(R.id.textViewCurrent).setText("いまはこれをにゅうりょく。");
        findTV(R.id.textViewNext).setText("つぎはこれをにゅうりょく。");
        findTV(R.id.textViewAfterNext).setText("そのつぎはこれをにゅうりょく。");
        
    }

	private TextView findTV(int id) {
		return (TextView)findViewById(id);
	}
}