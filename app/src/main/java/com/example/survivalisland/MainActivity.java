package com.example.survivalisland;

import android.app.*;
import android.os.*;
import android.graphics.*;
import android.view.*;
import java.util.*;

public class MainActivity extends Activity {
  public void onCreate(Bundle b){super.onCreate(b); getWindow().setFlags(1024,1024); setContentView(new GameView());}
  class GameView extends View {
    Paint p=new Paint(1); float px=600,py=400; int wood=0,stone=0; HashMap<String,PointF> trees=new HashMap<>(), rocks=new HashMap<>();
    boolean up,down,left,right;
    GameView(){super(MainActivity.this); setFocusable(true); for(int i=0;i<16;i++) trees.put("t"+i,new PointF(180+(i%4)*240,180+(i/4)*220)); for(int i=0;i<10;i++) rocks.put("r"+i,new PointF(300+(i%5)*260,260+(i/5)*300));}
    protected void onDraw(Canvas c){
      int w=getWidth(),h=getHeight(); c.drawColor(Color.rgb(72,150,190));
      p.setColor(Color.rgb(194,174,105)); c.drawOval(60,70,w-60,h-70,p);
      p.setColor(Color.rgb(45,125,55)); c.drawOval(100,100,w-100,h-100,p);
      p.setColor(Color.rgb(30,100,45)); for(PointF q:trees.values()){c.drawCircle(q.x,q.y,28,p); c.drawRect(q.x-7,q.y+18,q.x+7,q.y+55,p);}
      p.setColor(Color.GRAY); for(PointF q:rocks.values())c.drawCircle(q.x,q.y,24,p);
      p.setColor(Color.rgb(210,120,70)); c.drawCircle(px,py,24,p);
      p.setColor(Color.WHITE); p.setTextSize(28); c.drawText("🌲 SURVIVAL ISLAND",30,40,p);
      c.drawText("Дерево: "+wood+"   Камень: "+stone,30,80,p);
      p.setColor(Color.argb(190,0,0,0)); c.drawRoundRect(w-230,h-170,w-30,h-30,25,25,p);
      p.setColor(Color.WHITE); p.setTextSize(22); c.drawText("▲",w-145,h-125,p); c.drawText("◀  ▼  ▶",w-205,h-55,p);
      p.setTextSize(18); c.drawText("Подойди к дереву/камню",30,h-25,p);
    }
    public boolean onTouchEvent(android.view.MotionEvent e){
      float x=e.getX(),y=e.getY(); if(e.getAction()==1){
        if(x>getWidth()-230 && y>getHeight()-170){ 
          if(y<getHeight()-100) py-=35; else if(x<getWidth()-165) px-=35; else if(x>getWidth()-95) px+=35; else py+=35;
          collect(); invalidate(); return true;
        }
        if(x<getWidth()/2) px=Math.max(100,Math.min(getWidth()-100,x));
        else py=Math.max(100,Math.min(getHeight()-100,y));
        collect(); invalidate(); return true;
      } return true;
    }
    void collect(){
      for(PointF q:trees.values())if(Math.hypot(px-q.x,py-q.y)<60){wood++; q.x=-100; q.y=-100; break;}
      for(PointF q:rocks.values())if(Math.hypot(px-q.x,py-q.y)<55){stone++; q.x=-100; q.y=-100; break;}
    }
  }
}
