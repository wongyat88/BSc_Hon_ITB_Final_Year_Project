package commonView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import model.EmpModel;
import tools.myBorder;
import tools.myColor;
import tools.myFont;
import view.LoadingScene;
import view.Login;

public class CommonMain extends JFrame implements ActionListener, MouseListener, Runnable{
	
	Boolean FLAG = true;
	
	JFrame parentFrame;
	int uid;
	String sid;
	String username;
	String tableid = "---";
	int sLeft = 0;
	int sNow = 0;
	int sumFlag = 0;
	int tableLoopFlag = 0;
	int takeOutFlag = 0;
	String tidFlag = "";
	
	JPanel mp;
	JPanel rmp,rp1,rp2,rp3;
	JPanel cmp,cp1,cp2,cp2p1,cp2p2,cp2p3;
	JPanel bmp,bp1,bp2,bp3;
	
	JLabel name,time;
	JButton language, logout;
	Timer t;
	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	Border border = BorderFactory.createLineBorder(Color.BLACK, 3);
	JLabel[] lv1 = new JLabel[81];
	JLabel[] lv2 = new JLabel[50];
	
	Border margin;
	JButton tkAdd,tkAll;
	
	JLabel tid,sitLeft,sitNow;
	JPanel[] pv = new JPanel[12];
	JLabel[] lv = new JLabel[12];
	JButton[] bv = new JButton[12];
	Border margin2,margin3;
	JButton add;
	JButton booking;
	
	Image titleIcon;
	
	ResourceBundle rb;
	int localeCurrent = 0;
	
	public void centerPanel() {
		cmp = new JPanel(new BorderLayout());
		cp1 = new JPanel(new GridBagLayout());
		cp1.setBackground(Color.lightGray);
		GridBagConstraints gbc = new GridBagConstraints();
		
		JLabel[] starter = new JLabel[9];
		for(int s=0;s<9;s++) {
			starter[s] = new JLabel("",JLabel.CENTER);
			starter[s].setText(""+(s+1));
			starter[s].setBackground(Color.LIGHT_GRAY);
			starter[s].setOpaque(true);
			gbc.fill = GridBagConstraints.BOTH;
			gbc.weightx = 1;
			gbc.weighty = 1;
			gbc.gridx = s;
			gbc.gridy = 0;
			gbc.anchor = GridBagConstraints.LINE_END;
			cp1.add(starter[s], gbc);
		}
		
		for(int x=0;x<10;x++) {
			String key = "t"+(x+1)+"%";
			EmpModel emp = new EmpModel();
			String sql = "select table_id, table_number, table_flag, table_now from fyp_table "
					+ "where table_shop = '"+sid+"' and table_id LIKE '"+key+"' order by table_id ";
			emp.runSql(sql);
			for(int i=0;i<emp.getRowCount();i++) {
				lv1[i] = new JLabel("",JLabel.CENTER);
				lv1[i].setName(emp.getValueAt(i, 0).toString());
				lv1[i].setText("<html>"+
									emp.getValueAt(i, 0).toString()+
									"<br><br>"+
									"<h1>"+emp.getValueAt(i, 3).toString()+" / "+
									emp.getValueAt(i, 1).toString()+"</h1>"+
									"</html>");
				lv1[i].setFont(myFont.f1);
				lv1[i].setBorder(border);
				if(Integer.parseInt(emp.getValueAt(i, 2).toString())==1) {
					lv1[i].setBackground(myColor.green);
				}else if (Integer.parseInt(emp.getValueAt(i, 2).toString())==2) {
					lv1[i].setBackground(myColor.yellow);
				}else {
					lv1[i].setBackground(myColor.red);
				}
				lv1[i].setOpaque(true);
				lv1[i].addMouseListener(this);
				gbc.fill = GridBagConstraints.BOTH;
				gbc.weightx = 1;
				gbc.weighty = 1;
				gbc.gridx = i;
				gbc.gridy = x+1;
				gbc.anchor = GridBagConstraints.LINE_END;
				cp1.add(lv1[i], gbc);
			}
		}
		EmpModel emp2 = new EmpModel();
		String sql2 = "select sum(table_flag) from fyp_table where table_shop = '"+sid+"'";
		emp2.runSql(sql2);
		sumFlag = Integer.parseInt(emp2.getValueAt(0, 0).toString());
		
		cmp.add(cp1,"Center");
		
		cp2 = new JPanel(new BorderLayout());
		margin = new EmptyBorder(10, 0, 10, 0);
		cp2.setBorder(margin);
		
		cp2p1 = new JPanel();
		JLabel takeout = new JLabel(rb.getString("cm_takeout"),JLabel.LEFT);
		takeout.setFont(myFont.f1);
		cp2p1.add(takeout);
		cp2.add(cp2p1,"North");
		
		cp2p2 = new JPanel(new GridBagLayout());
		GridBagConstraints gbc2 = new GridBagConstraints();
		
		EmpModel emp3 = new EmpModel();
		String sql3 = "select order_id from fyp_order where order_sid = '"+sid+"' and "
					+ " order_area = 2 and order_flag = 1 order by length(order_id), order_id ";
		emp3.runSql(sql3);
		
		int x = emp3.getRowCount();
		if(x<=9) {
			x = 9;
		}
		
		for(int i=0;i<x;i++) {
			lv2[i] = new JLabel(""+(i+1), JLabel.CENTER);
			lv2[i].setFont(myFont.f2);
			gbc2.fill = GridBagConstraints.BOTH;
			gbc2.weightx = 1;
			gbc2.weighty = 1;
			gbc2.gridx = i;
			gbc2.gridy = 0;
			gbc2.anchor = GridBagConstraints.LINE_END;
			cp2p2.add(lv2[i],gbc2);
		}
		
		for(int i=0;i<emp3.getRowCount();i++) {
			lv2[i] = new JLabel(emp3.getValueAt(i, 0).toString());
			lv2[i].setName(emp3.getValueAt(i, 0).toString());
			lv2[i].setFont(myFont.f1);
			lv2[i].setBorder(border);
			lv2[i].setBackground(myColor.green);
			lv2[i].setOpaque(true);
			lv2[i].addMouseListener(this);
			gbc2.fill = GridBagConstraints.BOTH;
			gbc2.weightx = 1;
			gbc2.weighty = 1;
			gbc2.gridx = i;
			gbc2.gridy = 1;
			gbc2.anchor = GridBagConstraints.LINE_END;
			cp2p2.add(lv2[i],gbc2);
		}
		cp2.add(cp2p2,"Center");
		
		cp2p3 = new JPanel();
		tkAdd = new JButton(rb.getString("cm_tkAdd"));
		tkAdd.setFont(myFont.f2);
		tkAdd.addActionListener(this);
		tkAll = new JButton(rb.getString("cm_tkAll"));
		tkAll.setFont(myFont.f2);
		tkAll.addActionListener(this);
		cp2p3.add(tkAdd);
		cp2p3.add(tkAll);
		
		cp2.add(cp2p3,"South");
		
		cmp.add(cp2,"South");
	}
	
	public void rightPanel() {
		rmp = new JPanel(new BorderLayout());
		rmp.setPreferredSize(new Dimension(500, 500));
		
		rp1 = new JPanel(new GridLayout(3,1));
		margin2 = new EmptyBorder(5, 50, 5, 50);
		rp1.setBorder(margin2);
		rp1.setBackground(Color.BLACK);
		tid = new JLabel(rb.getString("cm_tid")+tableid);
		tid.setFont(myFont.f1);
		tid.setForeground(Color.white);
		sitLeft = new JLabel(rb.getString("cm_sitLeft")+sLeft);
		sitLeft.setFont(myFont.f1);
		sitLeft.setForeground(Color.white);
		sitNow = new JLabel(rb.getString("cm_sitNow")+sNow);
		sitNow.setFont(myFont.f1);
		sitNow.setForeground(Color.white);
		rp1.add(tid);
		rp1.add(sitLeft);
		rp1.add(sitNow);
		
		rmp.add(rp1,"North");
		
		rp2 = new JPanel(new GridLayout(12,1));
		rp2.setBorder(margin3);
		
		rp3 = new JPanel(new BorderLayout());
		add = new JButton(rb.getString("cm_add"));
		add.addActionListener(this);
		add.setFont(myFont.f1);
		rp3.add(rp2,"Center");
		rp3.add(add,"South");
		
		rmp.add(rp3,"Center");
		
		booking = new JButton("Booking No. : 5");
		booking.setFont(myFont.f1);
		
		rmp.add(booking,"South");
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
	
	public CommonMain(int locale, int userid, String shopid) {
		localeCurrent = locale;
		uid = userid;
		sid = shopid;
		initLocale();
		
		centerPanel();
		rightPanel();
		bottomPanel();
		
		mp = new JPanel(new BorderLayout());
		mp.add(cmp,"Center");
		mp.add(rmp,"East");
		mp.add(bmp,"South");
		
		this.add(mp);
		
		try {
			titleIcon = ImageIO.read(new File("images/logo/logo.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setTitle(rb.getString("cm_title") +" | "+ userid+" : "+username+" -> "+shopid);
		this.setIconImage(titleIcon);
		this.setSize(1500,800);
		int w = Toolkit.getDefaultToolkit().getScreenSize().width;
		int h = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation(w/2-750, h/2-400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		this.setVisible(true);
	}
	
	public void reloading() {
		int newsumFlag = 0;
		EmpModel emp2 = new EmpModel();
		String sql2 = "select sum(table_now) from fyp_table where table_shop = '"+sid+"'";
		emp2.runSql(sql2);
		newsumFlag = Integer.parseInt(emp2.getValueAt(0, 0).toString());
		
		if(sumFlag != newsumFlag) {
			sumFlag = newsumFlag;
			cp1.removeAll();
			cp1.repaint();
			GridBagConstraints gbc = new GridBagConstraints();
			JLabel[] starter = new JLabel[9];
			for(int s=0;s<9;s++) {
				starter[s] = new JLabel("",JLabel.CENTER);
				starter[s].setText(""+(s+1));
				starter[s].setBackground(Color.LIGHT_GRAY);
				starter[s].setOpaque(true);
				gbc.fill = GridBagConstraints.BOTH;
				gbc.weightx = 1;
				gbc.weighty = 1;
				gbc.gridx = s;
				gbc.gridy = 0;
				gbc.anchor = GridBagConstraints.LINE_END;
				cp1.add(starter[s], gbc);
			}
			for(int x=0;x<10;x++) {
				String key = "t"+(x+1)+"%";
				EmpModel emp = new EmpModel();
				String sql = "select table_id, table_number, table_flag, table_now from fyp_table "
						+ "where table_shop = '"+sid+"' and table_id LIKE '"+key+"' order by table_id ";
				emp.runSql(sql);
				for(int i=0;i<emp.getRowCount();i++) {
					lv1[i] = new JLabel("",JLabel.CENTER);
					lv1[i].setName(emp.getValueAt(i, 0).toString());
					lv1[i].setText("<html>"+
										emp.getValueAt(i, 0).toString()+
										"<br><br>"+
										"<h1>"+emp.getValueAt(i, 3).toString()+" / "+
										emp.getValueAt(i, 1).toString()+"</h1>"+
										"</html>");
					lv1[i].setFont(myFont.f1);
					lv1[i].setBorder(border);
					if(Integer.parseInt(emp.getValueAt(i, 2).toString())==1) {
						lv1[i].setBackground(myColor.green);
					}else if (Integer.parseInt(emp.getValueAt(i, 2).toString())==2) {
						lv1[i].setBackground(myColor.yellow);
					}else {
						lv1[i].setBackground(myColor.red);
					}
					lv1[i].setOpaque(true);
					lv1[i].addMouseListener(this);
					gbc.fill = GridBagConstraints.BOTH;
					gbc.weightx = 1;
					gbc.weighty = 1;
					gbc.gridx = i;
					gbc.gridy = x+1;
					gbc.anchor = GridBagConstraints.LINE_END;
					cp1.add(lv1[i], gbc);
				}
			}
		}
	}
	
	public void reloadRight(int flagg) {
		if(tableid.equals("---")) {
			;
		}else {
			if(flagg==0) {
				EmpModel emp3 = new EmpModel();
				String sql3 = "select count(*) from fyp_order "
						+ "where order_flag = '1' and order_sid = '"+sid+"' "
						+ "and order_id like '"+tableid+"%' ";
				emp3.runSql(sql3);
				int newTableLoopFlag = Integer.parseInt(emp3.getValueAt(0, 0).toString());
				
				if(tableLoopFlag != newTableLoopFlag) {
					tableLoopFlag = newTableLoopFlag;
					int nowSitting = 0;
					EmpModel emp = new EmpModel();
					String sql = "select table_number from fyp_table where"
							+ " table_shop = '"+sid+"' and table_id = '"+tableid+"' ";
					emp.runSql(sql);
					
					rp2.removeAll();
					rp2.repaint();
					EmpModel emp2 = new EmpModel();
					String sql2 = "select order_id, order_ppl from fyp_order "
							+ "where order_flag = '1' and order_sid = '"+sid+"' "
							+ "and order_id like '"+tableid+"%' order by order_id ";
					emp2.runSql(sql2);
					for(int i=0;i<emp2.getRowCount();i++) {
						pv[i] = new JPanel(new GridLayout(1,2));
						nowSitting += Integer.parseInt(emp2.getValueAt(i, 1).toString());
						int siting = Integer.parseInt(emp2.getValueAt(i, 1).toString());
						String oid = rb.getString("cm_oid")+emp2.getValueAt(i, 0).toString();
						lv[i] = new JLabel(oid+"   "+siting+rb.getString("cm_ppl"));
						lv[i].setFont(myFont.f1);
						lv[i].setBackground(myColor.blue);
						lv[i].setOpaque(true);
						bv[i] = new JButton(rb.getString("cm_orderDetails"));
						bv[i].setFont(myFont.f1);
						bv[i].setName(emp2.getValueAt(i, 0).toString());
						bv[i].addActionListener(this);
						pv[i].add(lv[i]);
						pv[i].add(bv[i]);
						pv[i].setBorder(new CompoundBorder(myBorder.border3,margin3));
						pv[i].setBackground(myColor.blue);
						rp2.add(pv[i]);
					}
					sitNow.setText(rb.getString("cm_sitNow")+nowSitting);
					sLeft = Integer.parseInt(emp.getValueAt(0, 0).toString())-nowSitting;
					sitLeft.setText(rb.getString("cm_sitLeft")+sLeft);
				}
			}else {
				EmpModel emp3 = new EmpModel();
				String sql3 = "select count(*) from fyp_order "
						+ "where order_flag = '1' and order_sid = '"+sid+"' "
						+ "and order_id like '"+tableid+"%' ";
				emp3.runSql(sql3);
				int newTableLoopFlag = Integer.parseInt(emp3.getValueAt(0, 0).toString());
				tableLoopFlag = newTableLoopFlag;
				int nowSitting = 0;
				EmpModel emp = new EmpModel();
				String sql = "select table_number from fyp_table where"
						+ " table_shop = '"+sid+"' and table_id = '"+tableid+"' ";
				emp.runSql(sql);
				
				rp2.removeAll();
				rp2.repaint();
				margin3 = new EmptyBorder(5, 15, 5, 15);
				EmpModel emp2 = new EmpModel();
				String sql2 = "select order_id, order_ppl from fyp_order "
						+ "where order_flag = '1' and order_sid = '"+sid+"' "
						+ "and order_id like '"+tableid+"%' order by order_id ";
				emp2.runSql(sql2);
				for(int i=0;i<emp2.getRowCount();i++) {
					pv[i] = new JPanel(new GridLayout(1,2));
					nowSitting += Integer.parseInt(emp2.getValueAt(i, 1).toString());
					int siting = Integer.parseInt(emp2.getValueAt(i, 1).toString());
					String oid = rb.getString("cm_oid")+emp2.getValueAt(i, 0).toString();
					lv[i] = new JLabel(oid+"   "+siting+rb.getString("cm_ppl"));
					lv[i].setFont(myFont.f1);
					lv[i].setBackground(myColor.blue);
					lv[i].setOpaque(true);
					bv[i] = new JButton(rb.getString("cm_orderDetails"));
					bv[i].setFont(myFont.f1);
					bv[i].setName(emp2.getValueAt(i, 0).toString());
					bv[i].addActionListener(this);
					pv[i].add(lv[i]);
					pv[i].add(bv[i]);
					pv[i].setBorder(new CompoundBorder(myBorder.border3,margin3));
					pv[i].setBackground(myColor.blue);
					rp2.add(pv[i]);
				}
				sitNow.setText(rb.getString("cm_sitNow")+nowSitting);
				sLeft = Integer.parseInt(emp.getValueAt(0, 0).toString())-nowSitting;
				sitLeft.setText(rb.getString("cm_sitLeft")+sLeft);
			}
		}
	}
	
	public void reloadTakeout() {
		EmpModel emp = new EmpModel();
		String sql = "select count(*) from fyp_order where order_sid = '"+sid+"' and order_flag='1' ";
		emp.runSql(sql);
		int nowFlag = Integer.parseInt(emp.getValueAt(0, 0).toString());
		if(nowFlag==takeOutFlag) {
			;
		}else {
			takeOutFlag=nowFlag;
			cp2.remove(cp2p2);
			cp2.repaint();
			cp2p2 = new JPanel(new GridBagLayout());
			GridBagConstraints gbc2 = new GridBagConstraints();
			
			EmpModel emp3 = new EmpModel();
			String sql3 = "select order_id from fyp_order where order_sid = '"+sid+"' and "
						+ " order_area = 2 and order_flag = 1 order by length(order_id), order_id ";
			emp3.runSql(sql3);
			
			int x = emp3.getRowCount();
			if(x<=9) {
				x = 9;
			}
			for(int i=0;i<x;i++) {
				lv2[i] = new JLabel(""+(i+1), JLabel.CENTER);
				lv2[i].setFont(myFont.f2);
				gbc2.fill = GridBagConstraints.BOTH;
				gbc2.weightx = 1;
				gbc2.weighty = 1;
				gbc2.gridx = i;
				gbc2.gridy = 0;
				gbc2.anchor = GridBagConstraints.LINE_END;
				cp2p2.add(lv2[i],gbc2);
			}
			
			for(int i=0;i<emp3.getRowCount();i++) {
				lv2[i] = new JLabel(emp3.getValueAt(i, 0).toString());
				lv2[i].setName(emp3.getValueAt(i, 0).toString());
				lv2[i].setFont(myFont.f1);
				lv2[i].setBorder(border);
				lv2[i].setBackground(myColor.green);
				lv2[i].setOpaque(true);
				lv2[i].addMouseListener(this);
				gbc2.fill = GridBagConstraints.BOTH;
				gbc2.weightx = 1;
				gbc2.weighty = 1;
				gbc2.gridx = i;
				gbc2.gridy = 1;
				gbc2.anchor = GridBagConstraints.LINE_END;
				cp2p2.add(lv2[i],gbc2);
			}
			cp2.add(cp2p2,"Center");
		}
	}
	
	public void refresh() {
		cp1.removeAll();
		cp1.repaint();
		GridBagConstraints gbc = new GridBagConstraints();
		JLabel[] starter = new JLabel[9];
		for(int s=0;s<9;s++) {
			starter[s] = new JLabel("",JLabel.CENTER);
			starter[s].setText(""+(s+1));
			starter[s].setBackground(Color.LIGHT_GRAY);
			starter[s].setOpaque(true);
			gbc.fill = GridBagConstraints.BOTH;
			gbc.weightx = 1;
			gbc.weighty = 1;
			gbc.gridx = s;
			gbc.gridy = 0;
			gbc.anchor = GridBagConstraints.LINE_END;
			cp1.add(starter[s], gbc);
		}
		for(int x=0;x<10;x++) {
			String key = "t"+(x+1)+"%";
			EmpModel emp = new EmpModel();
			String sql = "select table_id, table_number, table_flag, table_now from fyp_table "
					+ "where table_shop = '"+sid+"' and table_id LIKE '"+key+"' order by table_id ";
			emp.runSql(sql);
			for(int i=0;i<emp.getRowCount();i++) {
				lv1[i] = new JLabel("",JLabel.CENTER);
				lv1[i].setName(emp.getValueAt(i, 0).toString());
				lv1[i].setText("<html>"+
									emp.getValueAt(i, 0).toString()+
									"<br><br>"+
									"<h1>"+emp.getValueAt(i, 3).toString()+" / "+
									emp.getValueAt(i, 1).toString()+"</h1>"+
									"</html>");
				lv1[i].setFont(myFont.f1);
				lv1[i].setBorder(border);
				if(Integer.parseInt(emp.getValueAt(i, 2).toString())==1) {
					lv1[i].setBackground(myColor.green);
				}else if (Integer.parseInt(emp.getValueAt(i, 2).toString())==2) {
					lv1[i].setBackground(myColor.yellow);
				}else {
					lv1[i].setBackground(myColor.red);
				}
				lv1[i].setOpaque(true);
				lv1[i].addMouseListener(this);
				gbc.fill = GridBagConstraints.BOTH;
				gbc.weightx = 1;
				gbc.weighty = 1;
				gbc.gridx = i;
				gbc.gridy = x+1;
				gbc.anchor = GridBagConstraints.LINE_END;
				cp1.add(lv1[i], gbc);
			}
		}
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

	public static void main(String[] args) {
		CommonMain cm = new CommonMain(0,1,"shop2");
		Thread t1 = new Thread(cm);
		t1.start();
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
			LoadingScene ls = new LoadingScene("c",x,uid,sid);
			Thread y = new Thread(ls);
			y.start();
		}else if(e.getSource() == logout) {
			FLAG = false;
			this.dispose();
			new Login(localeCurrent);
		}else if(e.getSource() == add){
			if(tableid.equals("---")) {
				JLabel w1 = new JLabel(rb.getString("w_sel_table"));
		    	w1.setFont(myFont.f3);
				JOptionPane.showMessageDialog(
						this,w1,rb.getString("w_w"),JOptionPane.ERROR_MESSAGE);
			}else {
				if(sLeft == 0) {
					JLabel w2 = new JLabel(rb.getString("w_full_table"));
			    	w2.setFont(myFont.f3);
					JOptionPane.showMessageDialog(
							this,w2,rb.getString("w_w"),JOptionPane.ERROR_MESSAGE);
				}else {
					new TableOrderAddDialog(parentFrame,rb.getString("AddTableOrder_title"),true,
							localeCurrent,tableid,sid,sLeft);
				}
			}
		}else if(e.getSource() == tkAdd){
			new TakeOutOrderAddDialog(parentFrame,rb.getString("AddTakeOutOrder_title"),true,localeCurrent,sid);
			
		}else if(e.getSource() == tkAll){
			new TakeOutOrderListDialog(parentFrame,rb.getString("TakeOutOrderList_title"),true,localeCurrent,sid);
		}else {
			try {
				JButton b = (JButton)e.getSource();
			    String oid = b.getName();
			    if(oid.contains("t")) {
			    	new AddFoodOrderDialog(parentFrame,rb.getString("AddFoodOrder_title")+oid,true,
			    			1,localeCurrent,sid,oid);
			    	refresh();
			    }
			}catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JLabel label = (JLabel)e.getSource();
	    String oid = label.getName();
	    if(oid.contains("to")) {
	    	new AddFoodOrderDialog(parentFrame,rb.getString("AddFoodOrderTO_title")+oid,true,
	    			2,localeCurrent,sid,oid);
	    }else {
	    	tableid = oid;
	    	tid.setText(rb.getString("cm_tid")+tableid);
	    	reloadRight(1);
	    }
	}
	
	@Override
	public void run() {
		while(FLAG) {
			try {
				reloading();
				reloadRight(0);
				reloadTakeout();
				Thread.sleep(1000);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
}
