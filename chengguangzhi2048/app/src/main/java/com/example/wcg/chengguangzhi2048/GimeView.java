package com.example.wcg.chengguangzhi2048;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wcg on 2015/12/4.
 */
public class GimeView extends GridLayout {
    private Card[][] cards = new Card[4][4];
    private List<Point> emptyPoints = new ArrayList<>();

    public GimeView(Context context) {
        super(context);
        initView();
    }

    public GimeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public GimeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {

        setColumnCount(4);
        setBackgroundColor(0xffbbada0);
        setOnTouchListener(new OnTouchListener() {
            private float startX, startY, offsetX, offsetY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetX = event.getX() - startX;
                        offsetY = event.getY() - startY;
                        if (Math.abs(offsetX) > Math.abs(offsetY)) {
                            if (offsetX < -5) {
                                swapleft();
                            } else if (offsetX > 5) {
                                swapright();
                            }
                        } else {
                            if (offsetY < -5) {
                                swapup();
                            } else if (offsetY > 5) {
                                swapdown();
                            }
                        }
                        checkComplete();
                        break;

                }

                return true;
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int cardwidth = (Math.min(w, h) - 10) / 4;
        addCards(cardwidth, cardwidth);

        startgame();
    }

    private void addCards(int cardwidth, int cardheight) {
        Card c;
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                c = new Card(getContext());
                c.setNum(0);
                addView(c, cardwidth, cardwidth);
                cards[x][y] = c;

            }
        }

    }

    private void startgame() {
        MainActivity.getMainActivity().clearscore();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                cards[x][y].setNum(0);
            }
        }
        addRandomNum();
        addRandomNum();
    }

    private void addRandomNum() {

        emptyPoints.clear();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (cards[x][y].getNum() <= 0) {
                    emptyPoints.add(new Point(x, y));
                }
            }
        }
        Point p = emptyPoints.remove((int) (Math.random() * emptyPoints.size()));
        cards[p.x][p.y].setNum(Math.random() > 0.1 ? 2 : 4);

    }

    private void swapleft() {
        boolean merge =false;
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                for (int x1 = x + 1; x1 < 4; x1++) {
                    if (cards[x1][y].getNum() > 0) {
                        if (cards[x][y].getNum() <= 0) {
                            cards[x][y].setNum(cards[x1][y].getNum());
                            cards[x1 ][y].setNum(0);
                            x--;
                            merge=true;
                        } else if (cards[x][y].equals(cards[x1][y])) {
                            cards[x][y].setNum(cards[x][y].getNum() * 2);
                            cards[x1][y].setNum(0);
                            MainActivity.getMainActivity().addscore(cards[x][y].getNum());
                            merge=true;
                        }
                        break;
                    }
                }
            }
        }
        if(merge){
            addRandomNum();
        }
    }

    private void swapright() {
        boolean merge =false;
        for (int y = 0; y < 4; y++) {
            for (int x = 3; x >=0; x--) {
                for (int x1 = x -1; x1 >=0; x1--) {
                    if (cards[x1][y].getNum() > 0) {
                        if (cards[x][y].getNum() <= 0) {
                            cards[x][y].setNum(cards[x1][y].getNum());
                            cards[x1][y].setNum(0);
                            x++;
                            merge=true;
                        } else if (cards[x][y].equals(cards[x1][y])) {
                            cards[x][y].setNum(cards[x][y].getNum() * 2);
                            cards[x1][y].setNum(0);
                            MainActivity.getMainActivity().addscore(cards[x][y].getNum());
                            merge=true;
                        }
                        break;
                    }
                }
            }
        }
        if(merge){
            addRandomNum();
        }
    }

    private void swapup() {
        boolean merge =false;
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                for (int y1 = y + 1; y1 < 4; y1++) {
                    if (cards[x][y1].getNum() > 0) {
                        if (cards[x][y].getNum() <= 0) {
                            cards[x][y].setNum(cards[x][y1].getNum());
                            cards[x][y1].setNum(0);
                            y--;
                            merge=true;
                        } else if (cards[x][y].equals(cards[x][y1])) {
                            cards[x][y].setNum(cards[x][y].getNum() * 2);
                            cards[x][y1].setNum(0);
                            MainActivity.getMainActivity().addscore(cards[x][y].getNum());
                            merge=true;
                        }
                        break;
                    }
                }
            }
        }
        if(merge){
            addRandomNum();
        }
    }

    private void swapdown() {
        boolean merge =false;
        for (int x = 0; x < 4; x++) {
            for (int y = 3; y >=0; y--) {
                for (int y1 = y - 1; y1 >=0; y1--) {
                    if (cards[x][y1].getNum() > 0) {
                        if (cards[x][y].getNum() <= 0) {
                            cards[x][y].setNum(cards[x][y1].getNum());
                            cards[x][y1].setNum(0);
                            y++;
                            merge=true;
                        } else if (cards[x][y].equals(cards[x][y1])) {
                            cards[x][y].setNum(cards[x][y].getNum() * 2);
                            cards[x][y1].setNum(0);
                            MainActivity.getMainActivity().addscore(cards[x][y].getNum());
                            merge=true;
                        }
                        break;
                    }
                }
            }
        }
        if(merge){
            addRandomNum();
        }
    }
    private void checkComplete(){

        boolean complete = true;

        ALL:
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (cards[x][y].getNum()==0||
                        (x>0&&cards[x][y].equals(cards[x-1][y]))||
                        (x<3&&cards[x][y].equals(cards[x+1][y]))||
                        (y>0&&cards[x][y].equals(cards[x][y-1]))||
                        (y<3&&cards[x][y].equals(cards[x][y+1]))) {

                    complete = false;
                    break ALL;
                }
            }
        }

        if (complete) {
            new AlertDialog.Builder(getContext()).setTitle("你好").setMessage("游戏结束").setPositiveButton("重来", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startGame();
                }
            }).setNegativeButton("保存评分", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    save();
                }
            }).show();
        }

    }
    private void startGame(){

        MainActivity.getMainActivity().clearscore();

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                cards[x][y].setNum(0);
            }
        }

        addRandomNum();
        addRandomNum();
    }
    public void save()
    {

        try {
            FileOutputStream outStream=getContext().openFileOutput("a.txt", Context.MODE_WORLD_READABLE);
            outStream.write((Integer.toString(MainActivity.getMainActivity().getScore())+" ").getBytes());
            outStream.close();
            Toast.makeText(getContext(), "Saved", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            return;
        }
        catch (IOException e){
            return ;
        }

    }

}
