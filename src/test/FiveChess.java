package test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
  
public   class   FiveChess   
{   
public   static   void   main(String[]   args)   
{   
JFrame   myFrame   =   new   JFrame("快乐五子棋");     
myFrame.getContentPane().add(new   MyPanel());     
Toolkit   mykit   =   myFrame.getToolkit();     
Dimension   mysize   =   mykit.getScreenSize();     
myFrame.setBounds(0,0,mysize.width,mysize.height   -   40);   
myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
myFrame.show();     
}   
}   
  
class   MyPanel   extends   JPanel   implements   MouseListener     
{   
private   Point   start   =   new   Point(0,0);     
private   Point   next   =   new   Point(0,0);   
boolean[][]   chessBo   =   new   boolean[19][19];   
boolean   chessCo   =   true; //判断颜色的，true   黑   
boolean   winchess   =   true; //true,为胜利   
  
MyPanel(){   
addMouseListener(this);     
setBackground(Color.blue);   
}   
public   boolean   winkill(int   x,int   y) //win   ?   
{   
int   countBlack   =   0,countWhite   =   0;   
boolean   winchess01   =   false;   
//x,y   取到r01,r02的值   
for(int   i   =   0;i   <   5;i++)//判断纵向下   
{   
if((x   +   i)   >   20)   
break;   
if(chessCo   &&   chessBo[x   +   i][y])   
countBlack++;   
else   if(chessBo[x   +   i][y]   &&   (chessCo   ==   false))   
countWhite++;   
}   
for(int   i   =   0;i   <   5;i++)//判断纵向上   
{   
if((x   -   i)   <=   0)   
break;   
if(chessCo   &&   chessBo[x   -   i][y])   
countBlack++;   
else   if(chessBo[x   -   i][y]   &&   (chessCo   ==   false))   
countWhite++;   
}   
if((countBlack   ==   5)   ||   (countWhite   ==   5))   
winchess01   =   true;   
return   winchess01;   
}   
public   void   paint(Graphics   g)//画棋盘   
        {   
Graphics2D   g2D   =   (Graphics2D)g;   
  
g2D.setPaint(Color.black);   
float   pay   =   60.0f,pbx   =   60.0f;   
float   lett   =   25.0f;   
Point2D.Float   p1   =   new   Point2D.Float(60.0f,pay);   
Point2D.Float   p2   =   new   Point2D.Float(510.0f,pay);   
Point2D.Float   p3   =   new   Point2D.Float(pbx,60.0f);   
Point2D.Float   p4   =   new   Point2D.Float(pbx,510.0f);   
for(int   i=0;i<19;i++){   
Line2D.Float   lineH   =   new   Line2D.Float(p1,p2);   
Line2D.Float   lineV   =   new   Line2D.Float(p3,p4);   
pay   +=   lett;   
p1   =   new   Point2D.Float(60.0f,pay);   
p2   =   new   Point2D.Float(510.0f,pay);   
pbx   +=   lett;   
p3   =   new   Point2D.Float(pbx,60.0f);   
p4   =   new   Point2D.Float(pbx,510.0f);   
g2D.draw(lineH);   
g2D.draw(lineV);   
}   
}   
public   void   mousePressed(MouseEvent   evt)     
{   
}   
public   void   mouseClicked(MouseEvent   evt)     
{   
int   x   =   evt.getX();     
int   y   =   evt.getY();     
int   clickCount   =   evt.getClickCount();     
if(clickCount   >=1   ){   
if((x   >   48   &&   x   <   522)   &&   (y   >   48   &&   y   <   522))   
draw(x,y);     
}   
}   
  
public   void   draw(int   dx,int   dy)   
{   
int   r01   =   0,r02   =   0;   
Graphics   g   =   getGraphics();   
start.x   =   dx;     
start.y   =   dy;     
r01   =   (start.x   +   13   -   60)   /   25; //r01   、r02当前的格子   
r02   =   (start.y   +   13   -   60)   /   25;   
//System.out.println(r01   +   "-"   +   r02);   
next.x   =   25   *   r01   +   60   -   11;   
next.y   =   25   *   r02   +   60   -   11;   
//System.out.println(next.x   +   "-"   +   next.y);   
if(chessCo){   
chessCo   =   false;   
g.setColor(Color.black);   
g.fillOval(next.x,next.y,20,20);   
chessBo[next.x][next.y]   =   true;//用这个时，黑白子可交替出现，   
//chessBo[r01][r02]   =   true;//用这个代替上面那个时黑白子不可交替了，不知道为什么会这样   
//加入判断胜负的方法winkill()   
if(winchess   ==   winkill(r01,r02))   
showMessage();   
}   
if(!chessCo){   
chessCo   =   true;   
g.setColor(Color.white);   
g.fillOval(next.x,next.y,20,20);   
  
chessBo[r01][r02]   =   true;   
}   
//g.drawOval(next.x,next.y,20,20);   
g.dispose();   
}   
  
public   void   mouseReleased(MouseEvent   evt)   
{   
}   
  
public   void   mouseEntered(MouseEvent   evt)   
{   
}   
  
public   void   mouseExited(MouseEvent   evt)   
{     
}   
public   void   showMessage()   
{   
JOptionPane.showMessageDialog(null,   
"You   are   win.",   
"wide288   to   Message!",   
JOptionPane.INFORMATION_MESSAGE);   
}   
}
