package com.example.testdown;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ProgressBar;

public class ProgressButton extends ProgressBar {
	private Paint paint;
	private String text;

	public ProgressButton(Context context) {
		super(context);
		init();
	}

	public ProgressButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ProgressButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public void setText(String text) {
		this.text = text;
		this.postInvalidate();
		Log.e("tag", "执行了设值" );

	}
	public String getText() {
		Log.e("tag", "执行了取值" );
		return text;
	}
	private void init() {
		paint = new Paint();
		paint.setColor(Color.WHITE);
		text = "下载";
		Log.e("tag", "执行了init" );
	}

	@Override
	protected synchronized void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Rect rect = new Rect();
		this.paint.getTextBounds(this.text, 0, this.text.length(), rect);
		this.paint.setTextSize(getHeight());
		int x = getWidth() / 2 - rect.centerX();
		int y = getHeight() / 2 - rect.centerY();
		canvas.drawText(text, x, y, paint);
		Log.e("tag", "text="+text );
	}

}
