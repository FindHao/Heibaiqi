package test;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableCellRenderer;
public class tabletest extends JFrame implements ActionListener{
 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
/**
  * @param args
  */
 double sum=0;
 JTable table;
 JButton jieguo=new JButton("计算总价格");
 JTextArea price;
 Object [][] computerDIY={{"CPU","P4赛扬346",new Integer(500)},
                     {"主板","foxconnPM800",new Integer(504)},
                     {"硬盘","P4赛扬346",new Integer(320)},
   
 };
 String[] tablename={"部件","名称","价格"};
 public tabletest() {
  // TODO Auto-generated constructor stub
  super("创建表格");
  JLabel title=new JLabel("个人电脑配置",JLabel.CENTER); 
  table=new JTable(computerDIY,tablename);
  DefaultTableCellRenderer r=new DefaultTableCellRenderer();
  r.setHorizontalAlignment(JLabel.CENTER);
  table.setDefaultRenderer(table.getColumnClass(2), r);
  Container c=getContentPane();
  c.add(title,BorderLayout.NORTH);
  c.add(new JScrollPane(table),BorderLayout.CENTER);
  JPanel showJPanel=new JPanel();
  JLabel showjJLabel=new JLabel("所选部件的价格为："); 
  jieguo.addActionListener(this);
  price=new JTextArea();
  price.setEnabled(false);
  showJPanel.add(jieguo);
  showJPanel.add(showjJLabel);
  showJPanel.add(price);
  c.add(showJPanel,BorderLayout.SOUTH); 
 }
    public void actionPerformed(ActionEvent e) {
     // TODO Auto-generated method stub
     int tablecolumn=table.getColumnCount()-1;
     int tablerow=table.getRowCount();
     for (int i = 0; i < tablerow; i++) {
     sum=sum+Double.parseDouble(computerDIY[i][tablecolumn].toString());
  }
        price.setText("");
  price.setEnabled(true);
  price.append(String.valueOf(sum));
    }
 
 public static void main(String[] args) {
    tabletest t=new tabletest();
  t.setBounds(400, 200, 500,300);
  t.setVisible(true); 
 }}
