package com.example.sandric.mps.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import com.example.sandric.mps.interfaces.BoardActivityInterface;
import com.example.sandric.mps.models.Board;
import com.example.sandric.mps.models.Cell;
import com.example.sandric.mps.models.Opening;

/**
 * Created by sandric on 17.11.15.
 */
public class BoardLayout extends GridLayout {

    public Board board;

    private int cellWidth;

    private String type;

    private Boolean newGeneration;

    public BoardLayout (Context context) {
        super(context);
    }

    public BoardLayout (Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, widthMeasureSpec);

        this.cellWidth = widthMeasureSpec / 8;

        this.buildBoard();
    }

    public void generate (String type, Opening opening, BoardActivityInterface boardActivityDelegate) {

        this.newGeneration = true;
        this.board = new Board(type, opening, boardActivityDelegate);
        this.type = type;

        this.buildBoard();
    }

    private void buildBoard()  {
        if (this.cellWidth > 0 && this.newGeneration && this.board != null) {
            for (Cell cell : this.board.cells) {
                CellView cellView = new CellView(this.getContext(), cell);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(this.cellWidth, this.cellWidth);
                cellView.setLayoutParams(lp);
                this.addView(cellView);

                if (this.type.equals("training"))
                    cellView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            BoardLayout.this.onCellClicked(((CellView) v).cell);
                        }
                    });
            }

            this.newGeneration = false;
        }
    }


    public void draw () {
        for (int i = 0 ; i < this.getChildCount(); i++)
            ((CellView)this.getChildAt(i)).draw();
    }


    public void onCellClicked (Cell cell) {
        this.board.selectCell(cell);
        this.draw();
    }


    public void highlightHint () {
        this.board.highlightHint();
        this.draw();
    }


    public void step () {
        this.board.simulateMove();
        this.draw();
    }

    public void play () {
        this.board.startMovesSimulation();
        this.draw();
    }

    public void stop () {
        this.board.stopMovesSimulation();
        this.draw();
    }


}
