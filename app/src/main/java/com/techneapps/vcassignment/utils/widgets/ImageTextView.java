package com.techneapps.vcassignment.utils.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.techneapps.vcassignment.providers.clicklisteners.OnPrefClickListener;
import com.techneapps.vcassignment.R;
import com.techneapps.vcassignment.databinding.ImageTextViewBinding;

public class ImageTextView extends ConstraintLayout {

    private OnPrefClickListener onPrefClickListener;

    public ImageTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ImageTextViewBinding viewBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.image_text_view, this, true);
        TypedArray attributes = null;
        try {
            attributes = context.obtainStyledAttributes(attrs, R.styleable.ImageTextView);
            String textViewText = attributes.getString(R.styleable.ImageTextView_title);
            Integer imageViewImage = attributes.getResourceId(R.styleable.ImageTextView_icon, R.drawable.ic_sort_black_16dp);

            viewBinding.textView.setText(textViewText);
            Glide.with(context).load(imageViewImage).into(viewBinding.imageView);

        } finally {
            assert attributes != null;
            attributes.recycle();
        }

        setOnClickListener(v -> {
            if (onPrefClickListener != null) {
                onPrefClickListener.OnPrefClick();
            }
        });
    }
    public void addOnPrefClickListener(OnPrefClickListener onPrefClickListener) {
        this.onPrefClickListener = onPrefClickListener;
        setOnClickListener(v -> {
            if (onPrefClickListener != null) {
                onPrefClickListener.OnPrefClick();
            }
        });
    }
}
