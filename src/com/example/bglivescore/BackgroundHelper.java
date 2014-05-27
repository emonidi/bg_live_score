package com.example.bglivescore;

import java.util.HashMap;
import java.util.Map;

import android.R.integer;
import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.ApplicationErrorReport.CrashInfo;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.Script;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Display;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout.LayoutParams;

public class BackgroundHelper {
	WindowManager wm;
	ValueAnimator animator;
	int numberOfPositions;
	Point screenSize;
	int backgroundWidth;
	Map<Integer,Integer> positions;
	ImageView imageView;
	HorizontalScrollView scrollView;
	int currentPosition= 0;
	Paint paint;
	Bitmap bitmap;
	public BackgroundHelper(WindowManager wm,HorizontalScrollView scrollView,int numberOfPositions){
		this.wm = wm;		
		this.scrollView = scrollView;
		this.numberOfPositions= numberOfPositions;
		paint = new Paint();
		animator = new ValueAnimator();
		imageView = (ImageView) scrollView.findViewById(R.id.background_image_view);
		backgroundWidth = imageView.getWidth();
		positions = new HashMap<Integer,Integer>();
		bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
		calculateScreenSize();
		calculatePositions();
		
		
	}
	
	private void calculateScreenSize(){
		Display display = wm.getDefaultDisplay();
		screenSize = new Point();
		display.getSize(screenSize);
	}
	
	
	
	private void calculatePositions(){
		for(int i = 0; i < numberOfPositions; i++){
			positions.put(i, backgroundWidth/numberOfPositions*i);
		}
	}
	
	public void scrollToPosition(int position){
		//scrollView.scrollTo(positions.get(position), 0);
		animator.setDuration(300);
		animator.ofInt();
		animator.setIntValues(positions.get(currentPosition),positions.get(position));
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			
			@Override
		public void onAnimationUpdate(ValueAnimator animation) {
				// TODO Auto-generated method stub
				scrollView.scrollTo(Integer.parseInt(String.valueOf(animator.getAnimatedValue())), 0);
			}
		});
		
		currentPosition = position;
		animator.start();
	}
	
	
	public void blurImageBitmap(Context context,float radius){
		RenderScript rs = RenderScript.create(context);
		final Allocation input = Allocation.createFromBitmap(rs, bitmap);
		final Allocation output = Allocation.createTyped(rs, input.getType());
		final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
		script.setRadius(radius);
		script.setInput(input);
		script.forEach(output);
		output.copyTo(bitmap);
		imageView.setImageBitmap(bitmap);
	}
	
	
	
}
