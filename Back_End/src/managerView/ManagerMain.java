package managerView;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import model.EmpModel;
import tools.myFont;
import view.LoadingScene;
import view.Login;

public class ManagerMain extends JFrame implements ActionListener, MouseListener{
	Boolean FLAG = true;
	
	JPanel mp;
	
	//Center Panel
	Border margin;
	CardLayout cardP1,cardP2;
	JSplitPane jsp1;
	JPanel cp1,cp2,cc1,cc2,cc3;
	JLabel [] cl = new JLabel[9];
	JLabel b_left,b_right;
	
	//Bottom Panel
	JPanel bmp,bp1,bp2,bp3;
	JLabel name,time;
	JButton language, logout;
	
	public static int uid = 0;

	String username = "";
	
	Timer t;
	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	Image titleIcon;
	ResourceBundle rb;
	int localeCurrent = 0;
	
	public static int getUid() {
		return uid;
	}
	
	public void initLocale(){
    	if(localeCurrent == 0) {
    		this.setLocale(Locale.getDefault());
    		rb = ResourceBundle.getBundle("translation/my", Locale.getDefault());
    	}else {
    		this.setLocale(new Locale("zh", "TW"));
    		rb = ResourceBundle.getBundle("translation/my", new Locale("zh", "TW"));
    		}
    }
	
	public void bottomPanel() {
		String sql = "";
		Border margin;
		margin = new EmptyBorder(5, 20, 5, 20);
		
		bmp = new JPanel(new BorderLayout());
		bp1 = new JPanel();
		bp1.setBorder(margin);
		bp2 = new JPanel();
		bp3 = new JPanel();
		bp3.setBorder(margin);
		bp1.setBackground(new Color(202, 252, 252));
		bp2.setBackground(new Color(202, 252, 252));
		bp3.setBackground(new Color(202, 252, 252));
		
		if(localeCurrent==0) {
			sql = "select staff_name from fyp_staff where staff_id = " + uid;
		}else {
			sql = "select staff_name_zh from fyp_staff where staff_id = " + uid;
		}
		EmpModel emp = new EmpModel();
		emp.runSql(sql);
		username = emp.getValueAt(0, 0).toString();
		
		name = new JLabel(rb.getString("common_staffname")+username);
		name.setFont(myFont.f1);
		
		t = new Timer(1000,this); //1000 = 1 second
		t.start();
		time = new JLabel(rb.getString("timenow")+format1.format(Calendar.getInstance().getTime()).toString());
		time.setFont(myFont.f1);
		
		language = new JButton(rb.getString("login_locale"));
		language.setFont(myFont.f1);
		language.addActionListener(this);
		
		logout = new JButton(rb.getString("logout"));
		logout.setFont(myFont.f1);
		logout.addActionListener(this);
		
		bp1.add(name);
		bp2.add(language);
		bp2.add(logout);
		bp3.add(time);
		
		bmp.add(bp1,"West");
		bmp.add(bp2,"Center");
		bmp.add(bp3,"East");
	}
	
	public ManagerMain(int locale, int userid) {
		localeCurrent = locale;
		uid = userid;
		initLocale();
		
		mp = new JPanel(new BorderLayout());
		
		MainPanel();
		bottomPanel();
		
		mp.add(jsp1,"Center");
		mp.add(bmp,"South");
		this.add(mp);
		
		try {
			titleIcon = ImageIO.read(new File("images/logo/logo.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.setTitle(rb.getString("manager_title") +" - "+ userid);
		this.setIconImage(titleIcon);
		this.setSize(1500,800);
		int w = Toolkit.getDefaultToolkit().getScreenSize().width;
		int h = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation(w/2-750, h/2-400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		this.setVisible(true);
	}
	
	public void MainPanel() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage("images/logo/hc.png");
		Cursor c = toolkit.createCustomCursor(image , new Point(this.getX(), 
		           this.getY()), "img");
		
		cp1 = new JPanel(new GridLayout(6,1));
		margin = new EmptyBorder(0, 20, 0, 0);
		cp1.setBorder(margin);
		
		List<String> labelName = new ArrayList<String>();
		labelName.add("mm_menu");
		labelName.add("mm_shop");
		labelName.add("mm_profit");
		labelName.add("mm_material");
		labelName.add("mm_vip");
		labelName.add("mm_staff");
		for(int i=0;i<6;i++) {
			String x = labelName.get(i);
			cl[i] = new JLabel(rb.getString(x));
			cl[i].setFont(myFont.f1);
			cl[i].addMouseListener(this);
			cl[i].setCursor(c);
			cp1.add(cl[i]);
		}
		
		cp2 = new JPanel(new BorderLayout());
		
		cardP1 = new CardLayout();
		cc1 = new JPanel(this.cardP1);
		b_left = new JLabel(new ImageIcon("images/others/b_left.jpg"));
		b_left.addMouseListener(this);
		b_right = new JLabel(new ImageIcon("images/others/b_right.jpg"));
		b_right.addMouseListener(this);
		cc1.add(b_left,"0");
		cc1.add(b_right,"1");
		
		this.cardP2 = new CardLayout();
		cc3 = new JPanel(this.cardP2);
		
		FoodServicePanel mj = new FoodServicePanel(localeCurrent);
		cc3.add(mj,"0");
		
		ShopPanel sp = new ShopPanel(localeCurrent);
		cc3.add(sp,"1");
		
		StatisticServicePanel ssp= new StatisticServicePanel(localeCurrent);
		cc3.add(ssp,"2");
		
		MaterialServicePanel msp = new MaterialServicePanel(localeCurrent);
		cc3.add(msp,"3");
		
		UserServicePanel up = new UserServicePanel(localeCurrent);
		cc3.add(up,"4");
		
		StaffPanel stp = new StaffPanel(localeCurrent);
		cc3.add(stp,"5");
		
		cp2.add(cc1,"West");
		cp2.add(cc3,"Center");
		
		jsp1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,true,cp1,cp2);
		jsp1.setDividerLocation(200);
		jsp1.setDividerSize(0);
	}

	public static void main(String[] args) {
		ManagerMain mm = new ManagerMain(0, 2);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		this.time.setText((rb.getString("timenow")+format1.format(Calendar.getInstance().getTime()).toString()));
		if(e.getSource() == language) {
			int x = 0;
			if(localeCurrent == 0) {
				x = 1;
			}else {
				x = 0;
			}
			ResourceBundle.clearCache();
			FLAG = false;
			this.dispose();
			LoadingScene ls = new LoadingScene("m",x,uid,"1");
			Thread y = new Thread(ls);
			y.start();
		}else if(e.getSource() == logout) {
			FLAG = false;
			this.dispose();
			new Login(localeCurrent);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == this.cl[0]) {
			this.cardP2.show(cc3, "0");
		}else if(e.getSource() == this.cl[1]) {
			this.cardP2.show(cc3, "1");
		}else if(e.getSource() == this.cl[2]) {
			this.cardP2.show(cc3, "2");
		}else if(e.getSource() == this.cl[3]) {
			this.cardP2.show(cc3, "3");
		}else if(e.getSource() == this.cl[4]) {
			this.cardP2.show(cc3, "4");
		}else if(e.getSource() == this.cl[5]) {
			this.cardP2.show(cc3, "5");
		}else if(e.getSource() == this.b_left) {
			this.cardP1.show(cc1, "1");
			this.jsp1.setDividerLocation(0);
		}else if(e.getSource() == this.b_right) {
			this.cardP1.show(cc1, "0");
			this.jsp1.setDividerLocation(200);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(e.getSource() == this.cl[0]) {
			this.cl[0].setFont(myFont.f0);
			this.cl[0].setForeground(Color.red);
		}else if(e.getSource() == this.cl[1]) {
			this.cl[1].setFont(myFont.f0);
			this.cl[1].setForeground(Color.red);
		}else if(e.getSource() == this.cl[2]) {
			this.cl[2].setFont(myFont.f0);
			this.cl[2].setForeground(Color.red);
		}else if(e.getSource() == this.cl[3]) {
			this.cl[3].setFont(myFont.f0);
			this.cl[3].setForeground(Color.red);
		}else if(e.getSource() == this.cl[4]) {
			this.cl[4].setFont(myFont.f0);
			this.cl[4].setForeground(Color.red);
		}else if(e.getSource() == this.cl[5]) {
			this.cl[5].setFont(myFont.f0);
			this.cl[5].setForeground(Color.red);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(e.getSource() == this.cl[0]) {
			this.cl[0].setFont(myFont.f1);
			this.cl[0].setForeground(Color.BLACK);
		}else if(e.getSource() == this.cl[1]) {
			this.cl[1].setFont(myFont.f1);
			this.cl[1].setForeground(Color.BLACK);
		}else if(e.getSource() == this.cl[2]) {
			this.cl[2].setFont(myFont.f1);
			this.cl[2].setForeground(Color.BLACK);
		}else if(e.getSource() == this.cl[3]) {
			this.cl[3].setFont(myFont.f1);
			this.cl[3].setForeground(Color.BLACK);
		}else if(e.getSource() == this.cl[4]) {
			this.cl[4].setFont(myFont.f1);
			this.cl[4].setForeground(Color.BLACK);
		}else if(e.getSource() == this.cl[5]) {
			this.cl[5].setFont(myFont.f1);
			this.cl[5].setForeground(Color.BLACK);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
}