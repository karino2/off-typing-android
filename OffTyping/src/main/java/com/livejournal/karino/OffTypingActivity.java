package com.livejournal.karino;

import android.animation.ArgbEvaluator;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.TextKeyListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class OffTypingActivity extends Activity {

	TextGenerater.PhraseList selectedPhraseList = TextGenerater.JAPANESE_PHRASES;
	TextGenerater tg;
	TextTracker tracker = new TextTracker("");

	final private ArgbEvaluator argbEvaluator = new ArgbEvaluator();
	int originalTextColor;
	int cautiousTextColor;
	int failedTextColor;
	int numMisstypings;

	long beginTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		originalTextColor = findTV(R.id.textViewNext).getTextColors().getDefaultColor();
		cautiousTextColor = (Integer)(argbEvaluator.evaluate(0.2f, originalTextColor, Color.RED));
		failedTextColor = (Integer)(argbEvaluator.evaluate(0.8f, originalTextColor, Color.RED));

		tg = new TextGenerater(selectedPhraseList);
        
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
        
        
        findStartButton().setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				handleStart();
			}});
        
        RadioGroup rg = (RadioGroup)findViewById(R.id.radioLaunguageGroup);
        rg.check(R.id.radioJapanese);
        rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.radioJapanese) {
					selectedPhraseList = TextGenerater.JAPANESE_PHRASES;
				} else if (checkedId == R.id.radioEnglish) {
					selectedPhraseList = TextGenerater.ENGLISH_PHRASES;
				}

			}
		});
        
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
		TextTracker.TypeResult result = tracker.reportTypedText(current);

		// Doing post() because setting color immediately here occasionally froze my IME
		// for some reason :-(
		switch (result) {
			case DONE:
				getWindow().getDecorView().post(doHandleSentenceTyped);
				break;
			case MISSTYPING:
				getWindow().getDecorView().post(doHandleBadTyping);
				break;
			case PENDING:
				getWindow().getDecorView().post(doHandleCautiousTyping);
				break;
			case DOING_WELL:
				getWindow().getDecorView().post(doHandleGoodTyping);
				break;
		}
	}

	private final Runnable doHandleSentenceTyped = new Runnable() {
		@Override
		public void run() {

			tg.moveNext();
			numMisstypings += tracker.getMisstypingCount();
			tracker = new TextTracker(tg.getCurrent());

			TextView currentText = findTV(R.id.textViewCurrent);
			currentText.setTextColor(originalTextColor);

			EditText et = ((EditText)findViewById(R.id.editTextInput));
			TextKeyListener.clear(et.getText());
			et.setText("");
			setTextToView();
			if(tg.isFinished())
				handleFinish();
		}
	};

	private final Runnable doHandleGoodTyping = new Runnable() {
		@Override
		public void run() {
			insertRetryIfNeeded();
			findTV(R.id.textViewCurrent).setTextColor(originalTextColor);
		}
	};

	private final Runnable doHandleCautiousTyping = new Runnable() {
		@Override
		public void run() {
			insertRetryIfNeeded();
			findTV(R.id.textViewCurrent).setTextColor(cautiousTextColor);
		}
	};

	private final Runnable doHandleBadTyping = new Runnable() {
		@Override
		public void run() {
			insertRetryIfNeeded();
			findTV(R.id.textViewCurrent).setTextColor(failedTextColor);
		}
	};

	private void insertRetryIfNeeded() {
		if (tracker.hadMisstyping() && tg.canRetry()) {
			tg.insertRetry();
			setTextToView();
		}
	}

	private String buildFinishMessage() {
		long endTime = System.currentTimeMillis();
		double minute = (endTime - beginTime)/(1000*60.0);
		int velocity = (int)(tg.getTotalCharacterNum()/minute);
		return String.format("分速%d文字(%d 誤入力)", velocity, numMisstypings);
	}

	private void handleFinish() {
		isRunning = false;
        findStartButton().setEnabled(true);
		findTV(R.id.textViewResult).setText(buildFinishMessage());
	}


	private void handleStart() {
		tg = new TextGenerater(selectedPhraseList, tg.retriedPhrases);
		setTextToView();
		isRunning = true;
		findStartButton().setEnabled(false);
		beginTime = System.currentTimeMillis();
		findTV(R.id.textViewResult).setText("");
		numMisstypings = 0;
		tracker = new TextTracker(tg.getCurrent());
	}
}