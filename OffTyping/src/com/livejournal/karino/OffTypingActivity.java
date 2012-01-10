package com.livejournal.karino;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class OffTypingActivity extends Activity {
	
	TextGenerater tg;
	long beginTime;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        tg = new TextGenerater();
        
        EditText et = (EditText)findViewById(R.id.editTextInput);
        et.addTextChangedListener(new TextWatcher(){

			public void afterTextChanged(Editable arg0) {
				if(isRunning)
					handleInput(arg0.toString());
			}

			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}

			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
			}});
        
        
        findStartButton().setOnClickListener(new OnClickListener(){
			public void onClick(View arg0) {
				handleStart();
			}});
        
        
    }

	private Button findStartButton() {
		return (Button)findViewById(R.id.buttonStart);
	}
    boolean isRunning = false;

	private void setTextToView() {
		findTV(R.id.textViewCurrent).setText(tg.getCurrent());
        findTV(R.id.textViewNext).setText(tg.getNext());
        findTV(R.id.textViewAfterNext).setText(tg.getAfterNext());
	}

	private TextView findTV(int id) {
		return (TextView)findViewById(id);
	}

	private void handleInput(String current) {
		// findTV(R.id.textViewResult).setText(current);
		if(tg.getCurrent().equals(current))
		{
			((EditText)findViewById(R.id.editTextInput)).setText("");
			tg.moveNext();
			setTextToView();
			if(tg.isFinished())
				handleFinish();
		}
	}

	private void handleFinish() {
		isRunning = false;
        findStartButton().setEnabled(true);
        long endTime = System.currentTimeMillis();
        double minute = (endTime - beginTime)/(1000*60.0);
		findTV(R.id.textViewResult).setText("分速" +  (int)(tg.getTotalCharacterNum()/minute) + " 文字");
	}

	private void handleStart() {
		tg.reset();
		setTextToView();
		isRunning = true;
		findStartButton().setEnabled(false);
		beginTime = System.currentTimeMillis();
		findTV(R.id.textViewResult).setText("");
	}
}