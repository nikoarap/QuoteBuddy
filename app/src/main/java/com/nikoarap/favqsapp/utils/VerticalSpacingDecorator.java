package com.nikoarap.favqsapp.utils;

import android.graphics.Rect;
import android.view.View;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//used for adding spaces between the list items in the RecyvlerView
public class VerticalSpacingDecorator extends RecyclerView.ItemDecoration{

    private final int verticalSpaceHeight;

    public VerticalSpacingDecorator(int verticalSpaceHeight) {
        this.verticalSpaceHeight = verticalSpaceHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, @NonNull View view, @NotNull RecyclerView parent, @NotNull RecyclerView.State state) {
        outRect.bottom = verticalSpaceHeight;
        outRect.left = verticalSpaceHeight;
        outRect.right = verticalSpaceHeight;
        outRect.top = verticalSpaceHeight;
    }
}