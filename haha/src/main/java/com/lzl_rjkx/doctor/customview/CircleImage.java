package com.lzl_rjkx.doctor.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CircleImage extends ImageView {

	public CircleImage(Context context) {
		super(context);
	}

	public CircleImage(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CircleImage(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Drawable drawable = getDrawable();
		if (drawable == null) {
			return;
		}
		if (getWidth() == 0 || getHeight() == 0) {
			return;
		}

		Bitmap b = ((BitmapDrawable) drawable).getBitmap();
		Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);

		Bitmap roundBitmap = getCroppedBitmap(bitmap, getWidth());
		canvas.drawBitmap(roundBitmap, 0, 0, null);
	}

	// 对bitmap剪裁，使其变成圆形
	private Bitmap getCroppedBitmap(Bitmap bitmap, int radius) {
		Bitmap sump;

		if (bitmap.getWidth() != radius || bitmap.getHeight() != radius) {
			sump = Bitmap.createScaledBitmap(bitmap, radius, radius, false);
		} else {
			sump = bitmap;
		}

		Bitmap output = Bitmap.createBitmap(sump.getWidth(), sump.getHeight(),
				Bitmap.Config.ARGB_8888);

		final Rect rect = new Rect(0, 0, sump.getWidth(), sump.getHeight());
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setDither(true);
		paint.setColor(Color.parseColor("#BAB399"));

		Canvas c = new Canvas(output);
		c.drawARGB(0, 0, 0, 0);
		c.drawCircle(sump.getWidth() / 2 + 0.7f, sump.getHeight() / 2 + 0.7f,
				sump.getWidth() / 2 + 0.1f, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		c.drawBitmap(sump, rect, rect, paint);
		return output;
	}
}
