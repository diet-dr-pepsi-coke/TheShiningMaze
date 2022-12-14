package edu.wm.cs.cs301.tomcook.gui;

import static edu.wm.cs.cs301.tomcook.R.*;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import edu.wm.cs.cs301.tomcook.R;

public class MazePanel extends View implements P5PanelF21 {

    private Paint paint = new Paint();
    private Bitmap bitmap;
    private Canvas canvas_bm;
    private Path path;
    private int width = 1440, height = 1200;

    public MazePanel(Context context) {
        super(context);
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        canvas_bm = new Canvas(bitmap);
        //paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        path = new Path();
    }

    public MazePanel(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        canvas_bm = new Canvas(bitmap);
        //paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = resolveSizeAndState(getSuggestedMinimumWidth(), widthMeasureSpec, 1);
        int height = resolveSizeAndState(MeasureSpec.getSize(width), heightMeasureSpec, 0);

        setMeasuredDimension(width, height);
    }

    /**
     * Method to draw the buffer image on a graphics object that is
     * obtained from the superclass.
     * Warning: do not override getGraphics() or drawing might fail.
     */
    public void update() {
        invalidate();
    }

    public void addLine(int startX, int startY, int endX, int endY) {
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAlpha(255);
        for (int i=0; i<5; i++)
        canvas_bm.drawLine((float) startX, (float) startY, (float) endX, (float) endY, paint);
    }

    @Override
    public void commit() {
        invalidate();

    }


    public void paint(Canvas c) {
        c.drawBitmap(bitmap, (float) 0, (float) 0, paint);
    }

    @Override
    public boolean isOperational() {
        /*
        Graphics g = this.getBufferGraphics();
        if (g != null) {
            return true;
        }
        else {
            return false;
        }

         */
        return true;
    }

    @Override
    public void setColor(int rgb) {
        paint.setColor(rgb);
        paint.setAlpha(255);
    }

    /**
     * Provides an alternate way to set the color used for drawing
     * @param r the red value 0-255
     * @param g the green value 0-255
     * @param b the blue value 0-255
     */
    public void setColor(int r, int g, int b) {
        paint.setColor(Color.rgb(r, g, b));
        paint.setAlpha(255);
    }

    /**
     * Provides an alternate way to set the color used for drawing
     * @param r a multiplier used on 255 to determine red value
     * @param g a multiplier used on 255 to determine green value
     * @param b a multiplier used on 255 to determine blue value
     * @param a a multiplier used on 255 to determine alpha value
     */
    public void setColor(float r, float g, float b, float a) {
        setColor((int) r, (int) g, (int) b);
        paint.setAlpha((int) a);
    }

    @Override
    public int getColor() {
        return paint.getColor();
    }

    @Override
    public void addBackground(float percentToExit) {

    }

    @Override
    public void addFilledRectangle(int x, int y, int width, int height) {
        Log.v("MazePanel", "drawing Filled Rectangle");
        //paint.setShader(shader);
        paint.setStyle(Paint.Style.FILL);
        canvas_bm.drawRect(x, y, width, height, paint);
        update();
    }

    @Override
    public void addFilledPolygon(int[] xPoints, int[] yPoints, int nPoints, boolean bitmapon) {
        if (bitmapon == true) {
            Bitmap bitmapBackground = BitmapFactory.decodeResource(getResources(), drawable.hedgetexture3);
            BitmapShader shader = new BitmapShader(bitmapBackground, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
            paint.setShader(shader);
        }
                    path.reset();
            paint.setStyle(Paint.Style.FILL);
            path.moveTo((float) xPoints[0], (float) yPoints[0]);
            for (int i=1; i<nPoints; i++) {
                path.lineTo((float) xPoints[i], (float) yPoints[i]);
            }
            canvas_bm.drawPath(path, paint);
            paint.setShader(null);
    }

    @Override
    public void addPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        path.reset();
        paint.setStyle(Paint.Style.STROKE);
        path.moveTo((float) xPoints[0], (float) yPoints[0]);
        for (int i=1; i<nPoints; i++) {
            path.lineTo((float) xPoints[i], (float) yPoints[i]);
        }
        path.lineTo((float) xPoints[0], (float) yPoints[0]);
        canvas_bm.drawPath(path, paint);
    }

    @Override
    public void addFilledOval(int x, int y, int width, int height) {
        paint.setStyle(Paint.Style.FILL);
        canvas_bm.drawOval((float) x, (float) y, (float) x + width, (float) y + height, paint);

    }

    @Override
    public void addArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        paint.setStyle(Paint.Style.STROKE);
        canvas_bm.drawArc((float) x, (float) y, (float) width, (float) height, (float) startAngle, (float) arcAngle, false, paint);
    }

    @Override
    public void addMarker(float x, float y, String str) {
        paint.setTextSize(64);
        paint.setStyle(Paint.Style.FILL);
        paint.setTypeface(Typeface.SERIF);
        canvas_bm.drawText(str, x, y, paint);
    }

    @Override
    public void setRenderingHint(P5RenderingHints hintKey, P5RenderingHints hintValue) {
        /*
        Graphics2D g = (Graphics2D) this.getBufferGraphics();
        g.setRenderingHint(convertP5toGraphicHint(hintKey), convertP5toGraphicObject(hintValue));

         */

    }

    public void addBackgroundImage(int x, int y, int width, int height, int image) {
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), image);
        Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap1, width, height, false);
        canvas_bm.drawBitmap(bitmap2, x, y, paint);
    }

    private void myTestImage(Canvas c) {

        //addLine(0,0,width, height);
        //addArc(0, 0, width, height, 0, 360);
        setColor(Color.GREEN);
        addFilledOval(0,0, width, height);
        //addMarker(500, 500, "yeet");
        //int[] x = {100, 200, 300};
        //int[] y = {100, 300, 100};
        //addFilledPolygon(x,y,3);
        //addFilledRectangle(0,0,width,height);
        //addPolygon(x,y,3);


        c.drawBitmap(bitmap, (float) 0, (float) 0, paint);
    }

}
