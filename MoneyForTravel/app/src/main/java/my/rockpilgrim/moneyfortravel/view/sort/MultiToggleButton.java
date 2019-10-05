package my.rockpilgrim.moneyfortravel.view.sort;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;

import my.rockpilgrim.moneyfortravel.R;

public class MultiToggleButton extends androidx.appcompat.widget.AppCompatButton {

    public static final String TAG = "MultiToggleButton";
    public static final int MORE = 0;
    public static final int LESS = 1;
    public static final int EQUALS = 2;

    private Drawable lessDrawable;
    private Drawable moreDrawable;
    private Drawable equalsDrawable;

    public int state = 0;

    public MultiToggleButton(Context context) {
        super(context);
    }

    public MultiToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs, 0);
    }

    public MultiToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs, defStyleAttr);
    }

    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MultiToggleButton, defStyleAttr, 0);
        setMoreDrawable(typedArray.getDrawable(R.styleable.MultiToggleButton_pic1));
        setLessDrawable(typedArray.getDrawable(R.styleable.MultiToggleButton_pic2));
        setEqualsDrawable(typedArray.getDrawable(R.styleable.MultiToggleButton_pic3));
        setWidth(lessDrawable.getMinimumWidth());
        setBackground(moreDrawable);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    @Override
    public boolean performClick() {
        Log.i(TAG, "performClick");
        if (state == LESS) {
            setBackground(moreDrawable);
//            setBackgroundColor(Color.RED);
            state=MORE;
        } else if (state == MORE) {
            setBackground(equalsDrawable);
//            setBackgroundColor(Color.BLUE);
            state = EQUALS;
        }
        else if (state == EQUALS) {
            setBackground(lessDrawable);
//            setBackgroundColor(Color.GREEN);
            state = LESS;
        }
        return super.performClick();
    }

    public Drawable getLessDrawable() {
        return lessDrawable;
    }

    public void setLessDrawable(Drawable lessDrawable) {
        this.lessDrawable = lessDrawable;
    }

    public Drawable getMoreDrawable() {
        return moreDrawable;
    }

    public void setMoreDrawable(Drawable moreDrawable) {
        this.moreDrawable = moreDrawable;
    }

    public Drawable getEqualsDrawable() {
        return equalsDrawable;
    }

    public void setEqualsDrawable(Drawable equalsDrawable) {
        this.equalsDrawable = equalsDrawable;
    }
}
