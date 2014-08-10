package test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class MainFrame extends JFrame {

	private static final JDesktopPane DESKTOP_PANE = new JDesktopPane();
	__ChatInnerFrame x = new __ChatInnerFrame("find1", "find2");

	public MainFrame() {
		super("这是主窗体");
		setSize(640, 480);

		addIFame(x);
		// 菜单设置
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		JMenu menu1 = new JMenu("菜单1");
		JMenu menu101 = new JMenu("菜单101");
		JMenuItem menu10101 = new JMenuItem("菜单10101");
		JMenuItem menu102 = new JMenuItem("菜单102");
		menu102.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addIFame(new __ChatInnerFrame("find1", "find2"));
			}
		});
		menu101.add(menu10101);
		menu1.add(menu101);
		menu1.add(menu102);
		JMenu menu2 = new JMenu("菜单2");
		menuBar.add(menu1);
		menuBar.add(menu2);
		this.addWindowListener(new WindowDestroyer());
//		this.getContentPane().add(DESKTOP_PANE);
		this.setVisible(true);

	}

	public static void addIFame(JInternalFrame iframe) { // 添加子窗体的方法
		DESKTOP_PANE.add(iframe);
	}

	public static void main(String[] args) {
		new MainFrame();
	}
	class WindowDestroyer extends WindowAdapter{
		public void windowClosing(WindowEvent e){
			x.disconnect();
			  System.exit(0);
	}
	}
}
