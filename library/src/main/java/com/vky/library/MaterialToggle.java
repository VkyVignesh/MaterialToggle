package com.vky.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by ${Merlin} on ${3/29/2016}.
 */

public class MaterialToggle extends RelativeLayout implements View.OnClickListener {

    private int selected_position;
    private Drawable highlightDrawable_1;
    private Drawable highlightDrawable_2;
    private Drawable drawable_1;
    private Drawable drawable_2;
    private int stroke_width;
    private int stroke_color;
    private int bg_color;
    private int highlight_color;

    // Inflated Views
    private ImageView ivLeftView, ivRightView, ivHighlightView;
    private LinearLayout llBackgroundLayout;

    // Interface Initialization..
    private MaterialToggleListener listener;

    public MaterialToggle(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.vky_tab_layout, this);
    }

    public MaterialToggle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MaterialToggle(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.MaterialToggle, 0, 0);
        try {
            stroke_width = attr.getDimensionPixelSize(R.styleable.MaterialToggle_stroke_width, 4);
            stroke_color = attr.getColor(R.styleable.MaterialToggle_stroke_color, getResources().getColor(android.R.color.darker_gray));
            bg_color = attr.getColor(R.styleable.MaterialToggle_bg_color, getResources().getColor(android.R.color.white));
            highlight_color = attr.getColor(R.styleable.MaterialToggle_hightlight_color, getResources().getColor(R.color.colorAccent));

            drawable_1 = attr.getDrawable(R.styleable.MaterialToggle_icon_1);
            highlightDrawable_1 = attr.getDrawable(R.styleable.MaterialToggle_icon_1);
            if (drawable_1 == null) {
                drawable_1 = getResources().getDrawable(R.drawable.ic_add);
                highlightDrawable_1 = getResources().getDrawable(R.drawable.ic_add);
            }
            drawable_2 = attr.getDrawable(R.styleable.MaterialToggle_icon_2);
            highlightDrawable_2 = attr.getDrawable(R.styleable.MaterialToggle_icon_2);
            if (drawable_2 == null) {
                drawable_2 = getResources().getDrawable(R.drawable.ic_add);
                highlightDrawable_2 = getResources().getDrawable(R.drawable.ic_add);
            }
        } finally {
            attr.recycle();
        }

        LayoutInflater.from(context).inflate(R.layout.vky_tab_layout, this);
        // Start Initializing views from here...
        llBackgroundLayout = (LinearLayout) this.findViewById(R.id.main_background);
        ivLeftView = (ImageView) this.findViewById(R.id.left_view);
        ivRightView = (ImageView) this.findViewById(R.id.right_view);
        ivHighlightView = (ImageView) this.findViewById(R.id.highlight_view);
        ivLeftView.setOnClickListener(this);
        ivRightView.setOnClickListener(this);

        updateHighlightView();
        updateMainBackground();

        ivHighlightView.setColorFilter(Color.parseColor("#FFFFFF"));
        ivLeftView.setColorFilter(Color.parseColor("#AAAAAA"));
        ivRightView.setColorFilter(Color.parseColor("#AAAAAA"));

        ivLeftView.setImageDrawable(drawable_1);
        ivRightView.setImageDrawable(drawable_2);
        ivHighlightView.setImageDrawable(highlightDrawable_1);

    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.left_view) {
            if (selected_position != 0) {
                selected_position = 0;
                moveBackwardAnimation();
                ivHighlightView.setImageDrawable(highlightDrawable_1);
                if (listener != null)
                    listener.onPositionSelected(selected_position);
            }

        } else if (i == R.id.right_view) {
            if (selected_position != 1) {
                selected_position = 1;
                moveForwardAnimation();
                ivHighlightView.setImageDrawable(highlightDrawable_2);
                if (listener != null)
                    listener.onPositionSelected(selected_position);
            }
        }
    }

    private void moveForwardAnimation() {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.move);
        ivHighlightView.startAnimation(animation);
    }

    private void moveBackwardAnimation() {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.move_reverse);
        ivHighlightView.startAnimation(animation);
    }

    private void updateMainBackground() {

        GradientDrawable drawable = (GradientDrawable) llBackgroundLayout.getBackground();
        drawable.setStroke(stroke_width, stroke_color);
        drawable.setColor(bg_color);

        llBackgroundLayout.setBackground(drawable);
    }

    private void updateHighlightView() {

        GradientDrawable drawable = (GradientDrawable) ivHighlightView.getBackground();
        drawable.setColor(highlight_color);

        ivHighlightView.setBackground(drawable);
    }

    public int getSelectedPosition() {
        return selected_position;
    }


    public void setOnMaterialToggleListener(MaterialToggleListener listener) {
        this.listener = listener;
    }

    public interface MaterialToggleListener {
        void onPositionSelected(int position);
    }
}

