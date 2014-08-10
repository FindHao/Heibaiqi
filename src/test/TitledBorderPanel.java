package test;


import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class TitledBorderPanel extends JFrame {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private TitledBorder titledBorder;
    
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
    	try {
            UIManager
                    .setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Throwable e) {
            e.printStackTrace();
        }
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TitledBorderPanel frame = new TitledBorderPanel();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    	
    }
    
    /**
     * Create the frame.
     */
    public TitledBorderPanel() {
        setTitle("实现带标题边框的面板容器");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 448, 389);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new GridLayout(0, 3, 0, 0));
        
        JPanel panel_1 = new JPanel();// 创建面板
        panel_1.setBorder(new TitledBorder(null, "水平居中的标题",
                TitledBorder.CENTER, TitledBorder.TOP));// 添加标题边框
        contentPane.add(panel_1);
        
        JPanel panel_2 = new JPanel();// 创建置顶标题的边框面板
        titledBorder = new TitledBorder("置顶标题");
        panel_2.setBorder(titledBorder);
        contentPane.add(panel_2);
        
        JPanel panel_3 = new JPanel();// 创建带有边框内置顶标题的面板
        titledBorder = new TitledBorder(null, "边框内置顶标题", TitledBorder.LEADING,
                TitledBorder.BELOW_TOP);
        panel_3.setBorder(titledBorder);
        contentPane.add(panel_3);
        
        JPanel panel_4 = new JPanel();// 创建带有边框内底边标题的面板
        titledBorder = new TitledBorder(null, "边框内底边标题", TitledBorder.LEADING,
                TitledBorder.ABOVE_BOTTOM);
        panel_4.setBorder(titledBorder);
        contentPane.add(panel_4);
        
        JPanel panel_5 = new JPanel();// 创建带有底边标题边框的面板
        titledBorder = new TitledBorder(null, "底边标题", TitledBorder.LEADING,
                TitledBorder.BOTTOM);
        panel_5.setBorder(titledBorder);
        contentPane.add(panel_5);
        
        JPanel panel_6 = new JPanel();// 创建带有置底标题边框的面板
        titledBorder = new TitledBorder(null, "置底标题", TitledBorder.LEADING,
                TitledBorder.BELOW_BOTTOM);
        panel_6.setBorder(titledBorder);
        contentPane.add(panel_6);
        
        JPanel panel_7 = new JPanel();// 创建带有水平居中的白色标题的面板
        titledBorder = new TitledBorder(null, "", TitledBorder.CENTER,
                TitledBorder.TOP);
        panel_7.setBorder(titledBorder);
        contentPane.add(panel_7);
    }
}
    




