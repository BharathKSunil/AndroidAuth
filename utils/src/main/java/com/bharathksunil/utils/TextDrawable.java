package com.bharathksunil.utils;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.RoundRectShape;

/**
 * TextDrawable This light-weight library provides images with letter/text like the Gmail app
 * This class is imported from com.amulyakhare:com.amulyakhare.textdrawable:1.0.1
 *
 * @author Bharath on 15-08-2017.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public final class TextDrawable extends ShapeDrawable {

    private static final float SHADE_FACTOR = 0.9f;
    private final Paint mTextPaint;
    private final Paint mBorderPaint;
    private final String mText;
    @SuppressWarnings("FieldCanBeLocal")
    private final int mColor;
    private final RectShape mShape;
    private final int mDrawableHeight;
    private final int mDrawableWidth;
    private final int mTextFontSize;
    private final float mDrawableRadius;
    private final int mDrawableBorderThickness;

    //todo: Add documentation and code cleanup
    private TextDrawable(Builder builder) {
        super(builder.shape);

        // shape properties
        mShape = builder.shape;
        mDrawableHeight = builder.height;
        mDrawableWidth = builder.width;
        mDrawableRadius = builder.mDrawableRadius;

        // text and color
        mText = builder.toUpperCase ? builder.mDrawableText.toUpperCase() : builder.mDrawableText;
        mColor = builder.color;

        // text paint settings
        mTextFontSize = builder.fontSize;
        mTextPaint = new Paint();
        mTextPaint.setColor(builder.mTextColor);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setFakeBoldText(builder.isBold);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTypeface(builder.font);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setStrokeWidth(builder.borderThickness);

        // border paint settings
        mDrawableBorderThickness = builder.borderThickness;
        mBorderPaint = new Paint();
        mBorderPaint.setColor(getDarkerShade(mColor));
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(mDrawableBorderThickness);

    }

    public static IShapeBuilder builder() {
        return new Builder();
    }

    private int getDarkerShade(int color) {
        return Color.rgb((int) (SHADE_FACTOR * Color.red(color)),
                (int) (SHADE_FACTOR * Color.green(color)),
                (int) (SHADE_FACTOR * Color.blue(color)));
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Rect r = getBounds();


        // draw border
        if (mDrawableBorderThickness > 0) {
            drawBorder(canvas);
        }

        int count = canvas.save();
        canvas.translate(r.left, r.top);

        // draw text
        int width = this.mDrawableWidth < 0 ? r.width() : this.mDrawableWidth;
        int height = this.mDrawableHeight < 0 ? r.height() : this.mDrawableHeight;
        int fontSize = this.mTextFontSize < 0 ? (Math.min(width, height) / 2) : this.mTextFontSize;
        mTextPaint.setTextSize(fontSize);
        canvas.drawText(mText, width / 2, height / 2 - ((mTextPaint.descent() + mTextPaint.ascent()) / 2), mTextPaint);

        canvas.restoreToCount(count);

    }

    private void drawBorder(Canvas canvas) {
        RectF rect = new RectF(getBounds());
        rect.inset(mDrawableBorderThickness / 2, mDrawableBorderThickness / 2);

        if (mShape instanceof OvalShape) {
            canvas.drawOval(rect, mBorderPaint);
        } else if (mShape instanceof RoundRectShape) {
            canvas.drawRoundRect(rect, mDrawableRadius, mDrawableRadius, mBorderPaint);
        } else {
            canvas.drawRect(rect, mBorderPaint);
        }
    }

    @Override
    public void setAlpha(int alpha) {
        mTextPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mTextPaint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public int getIntrinsicWidth() {
        return mDrawableWidth;
    }

    @Override
    public int getIntrinsicHeight() {
        return mDrawableHeight;
    }

    public interface IConfigBuilder {
        IConfigBuilder width(int width);

        IConfigBuilder height(int height);

        IConfigBuilder textColor(int color);

        IConfigBuilder withBorder(int thickness);

        IConfigBuilder useFont(Typeface font);

        IConfigBuilder fontSize(int size);

        IConfigBuilder bold();

        IConfigBuilder toUpperCase();

        IShapeBuilder endConfig();
    }

    public interface IBuilder {

        TextDrawable build(String text, int color);
    }

    @SuppressWarnings("UnusedReturnValue")
    public interface IShapeBuilder {

        IConfigBuilder beginConfig();

        IBuilder rect();

        IBuilder round();

        IBuilder roundRect(int radius);

        TextDrawable buildRect(String text, int color);

        TextDrawable buildRoundRect(String text, int color, int radius);

        TextDrawable buildRound(String text, int color);
    }

    private static class Builder implements IConfigBuilder, IShapeBuilder, IBuilder {

        private int mTextColor;
        float mDrawableRadius;
        private String mDrawableText;
        private int color;
        private int borderThickness;
        private int width;
        private int height;
        private Typeface font;
        private RectShape shape;
        private int fontSize;
        private boolean isBold;
        private boolean toUpperCase;

        private Builder() {
            mDrawableText = "";
            color = Color.GRAY;
            mTextColor = Color.WHITE;
            borderThickness = 0;
            width = -1;
            height = -1;
            shape = new RectShape();
            font = Typeface.create("sans-serif-light", Typeface.NORMAL);
            fontSize = -1;
            isBold = false;
            toUpperCase = false;
        }

        public IConfigBuilder width(int width) {
            this.width = width;
            return this;
        }

        public IConfigBuilder height(int height) {
            this.height = height;
            return this;
        }

        public IConfigBuilder textColor(int color) {
            this.mTextColor = color;
            return this;
        }

        public IConfigBuilder withBorder(int thickness) {
            this.borderThickness = thickness;
            return this;
        }

        public IConfigBuilder useFont(Typeface font) {
            this.font = font;
            return this;
        }

        public IConfigBuilder fontSize(int size) {
            this.fontSize = size;
            return this;
        }

        public IConfigBuilder bold() {
            this.isBold = true;
            return this;
        }

        public IConfigBuilder toUpperCase() {
            this.toUpperCase = true;
            return this;
        }

        @Override
        public IConfigBuilder beginConfig() {
            return this;
        }

        @Override
        public IShapeBuilder endConfig() {
            return this;
        }

        @Override
        public IBuilder rect() {
            this.shape = new RectShape();
            return this;
        }

        @Override
        public IBuilder round() {
            this.shape = new OvalShape();
            return this;
        }

        @Override
        public IBuilder roundRect(int radius) {
            this.mDrawableRadius = radius;
            float[] radii = {radius, radius, radius, radius, radius, radius, radius, radius};
            this.shape = new RoundRectShape(radii, null, null);
            return this;
        }

        @Override
        public TextDrawable buildRect(String text, int color) {
            rect();
            return build(text, color);
        }

        @Override
        public TextDrawable buildRoundRect(String text, int color, int radius) {
            roundRect(radius);
            return build(text, color);
        }

        @Override
        public TextDrawable buildRound(String text, int color) {
            round();
            return build(text, color);
        }

        @Override
        public TextDrawable build(String text, int color) {
            this.color = color;
            this.mDrawableText = text;
            return new TextDrawable(this);
        }
    }
}
