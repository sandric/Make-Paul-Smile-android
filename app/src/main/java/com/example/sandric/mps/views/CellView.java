package com.example.sandric.mps.views;

import android.content.Context;
import android.graphics.Color;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.sandric.mps.models.Cell;

/**
 * Created by sandric on 17.11.15.
 */
public class CellView extends FrameLayout {

    public Cell cell;

    public CellView(Context context, Cell cell) {
        super(context);

        this.cell = cell;

        this.draw();
    }

    public void draw () {
        this.removeAllViews();

        this.setBackgroundColor(this.getColor());

        if (!this.cell.isEmpty())
            this.drawPiece();
    }

    private void drawPiece () {
        ImageView imageView = new ImageView(this.getContext());

        int pieceResource = getResources().getIdentifier(this.cell.piece.getImagePath(), "drawable", getContext().getPackageName());
        imageView.setImageResource(pieceResource);

        imageView.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));

        this.addView(imageView);
    }


    private int getColor () {
        int color = Color.BLACK;

        if (this.cell.isSelected)
            color = Color.GREEN;
        else
            if (this.cell.isExpected)
                color = Color.YELLOW;
            else
                if (this.cell.isValid)
                    color = Color.BLUE;
                else
                    if (this.cell.color.equals("white"))
                        color = Color.WHITE;
                    else
                        if (this.cell.color.equals("black"))
                            color = Color.LTGRAY;

        return color;
    }
}
