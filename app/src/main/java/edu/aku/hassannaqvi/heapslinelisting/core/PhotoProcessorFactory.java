package edu.aku.hassannaqvi.heapslinelisting.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

public class PhotoProcessorFactory {

    public static Bitmap overlayDirectionOnPhoto(Bitmap originalBitmap, float azimuth, Context context) {
        Bitmap mutableBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(mutableBitmap);
        Paint paint = new Paint();
        paint.setTextSize(40);
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);

        // Draw directional text
        paint.setColor(Color.RED);
        String direction = "N";
        canvas.drawText(direction, mutableBitmap.getWidth() / 2, 100, paint);

        // Draw red triangle pointing north
        paint.setColor(Color.RED);
        drawRedTriangle(canvas, mutableBitmap.getWidth() / 2, 150, azimuth, paint);

        // Draw gray triangle pointing south
        paint.setColor(Color.GRAY);
        drawGrayTriangle(canvas, mutableBitmap.getWidth() / 2, 250, paint);

        return mutableBitmap;
    }

    private static void drawRedTriangle(Canvas canvas, float x, float y, float azimuth, Paint paint) {
        paint.setStyle(Paint.Style.FILL);

        Path path = new Path();
        float triangleHeight = 50;
        float halfBase = 25;

        // Points of the triangle (upwards direction by default)
        float x1 = x;
        float y1 = y - triangleHeight;
        float x2 = x - halfBase;
        float y2 = y + triangleHeight / 2;
        float x3 = x + halfBase;
        float y3 = y + triangleHeight / 2;

        // Rotate points around (x, y) based on azimuth
        PointF p1 = rotatePoint(x1, y1, x, y, azimuth);
        PointF p2 = rotatePoint(x2, y2, x, y, azimuth);
        PointF p3 = rotatePoint(x3, y3, x, y, azimuth);

        path.moveTo(p1.x, p1.y);
        path.lineTo(p2.x, p2.y);
        path.lineTo(p3.x, p3.y);
        path.close();

        canvas.drawPath(path, paint);
    }

    private static void drawGrayTriangle(Canvas canvas, float x, float y, Paint paint) {
        paint.setStyle(Paint.Style.FILL);

        Path path = new Path();
        float triangleHeight = 50;
        float halfBase = 25;

        // Points of the triangle (downwards direction by default)
        float x1 = x;
        float y1 = y + triangleHeight;
        float x2 = x - halfBase;
        float y2 = y - triangleHeight / 2;
        float x3 = x + halfBase;
        float y3 = y - triangleHeight / 2;

        path.moveTo(x1, y1);
        path.lineTo(x2, y2);
        path.lineTo(x3, y3);
        path.close();

        canvas.drawPath(path, paint);
    }

    private static PointF rotatePoint(float x, float y, float cx, float cy, float angle) {
        double radians = Math.toRadians(angle);
        float cos = (float) Math.cos(radians);
        float sin = (float) Math.sin(radians);

        float nx = cos * (x - cx) - sin * (y - cy) + cx;
        float ny = sin * (x - cx) + cos * (y - cy) + cy;

        return new PointF(nx, ny);
    }
}
