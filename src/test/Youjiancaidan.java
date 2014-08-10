package test;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Youjiancaidan extends JPanel {
	private static final long serialVersionUID = 1L;
PopupMenu popupMenu1 = new PopupMenu();
MenuItem menuItem1 = new MenuItem();
MenuItem menuItem2 = new MenuItem();
MenuItem menuItem3 = new MenuItem();
public static void main(String[] args) {
	JFrame frame=new JFrame();
	frame.add(new Youjiancaidan());
	frame.setVisible(true);
}
public Youjiancaidan() {
try {
jbInit();
add(new JLabel("find"));
}
catch(Exception ex) {
ex.printStackTrace();
}
}
void jbInit() throws Exception {
menuItem1.setLabel("菜单1");
menuItem1.addActionListener(new java.awt.event.ActionListener() {
public void actionPerformed(ActionEvent e) {
menuItem1_actionPerformed(e);
}
});
menuItem2.setLabel("菜单2");
menuItem3.setLabel("菜单3");
this.addMouseListener(new java.awt.event.MouseAdapter() {
public void mousePressed(MouseEvent e) {
this_mousePressed(e);
}
});
popupMenu1.add(menuItem1);
popupMenu1.add(menuItem2);
popupMenu1.add(menuItem3);
add(popupMenu1);
}

void this_mousePressed(MouseEvent e) {
int mods=e.getModifiers();
//鼠标右键
if((mods&InputEvent.BUTTON3_MASK)!=0){
//弹出菜单
popupMenu1.show(this,e.getX(),e.getY());
}
}

void menuItem1_actionPerformed(ActionEvent e) {
//菜单事件
}
}