package kitchenView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
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
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import model.EmpModel;
import tools.myColor;
import tools.myFont;
import view.LoadingScene;
import view.Login;

public class KitchenMain extends JFrame implements ActionListener, MouseListener, Runnable{
	boolean FLAG = true;
	int gflag = 0;
	int tflag = 0;
	int uid = 0;
	String sid = "";
	String username;
	
	JPanel mp;
	JPanel mmp,mp1,mp2,np,np1,np2;
	JPanel bmp,bp1,bp2,bp3;
	
	JLabel blue,yellow,red;
	JLabel[] sname = new JLabel[50];
	
	JTable jt1,jt2;
	JScrollPane jsp1,jsp2;
	DefaultTableModel tm1,tm2;
	
	JLabel name,time;
	JButton language, logout;
	Timer t;
	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	Image titleIcon;
	ResourceBundle rb;
	int localeCurrent = 0;
	public void initLocale(){
    	if(localeCurrent == 0) {
    		this.setLocale(Locale.getDefault());
    		rb = ResourceBundle.getBundle("translation/my", Locale.getDefault());
    	}else {
    		this.setLocale(new Locale("zh", "TW"));
    		rb = ResourceBundle.getBundle("translation/my", new Locale("zh", "TW"));
    	}
    }
	
	public KitchenMain(int locale, int userid, String shopid) {
		uid = userid;
		sid = shopid;
		localeCurrent = locale;
		initLocale();
		
		mp = new JPanel(new BorderLayout());
		MainPanel();
		bottomPanel();
		
		this.add(mp,"Center");
		this.add(bmp,"South");
		
		start();
		
		try {
			titleIcon = ImageIO.read(new File("images/logo/logo.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setTitle(rb.getString("kitchen_title") +" | "+ userid+" : "+username+" -> "+shopid);
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
		mmp = new JPanel(new GridLayout(0,2));
		mp1 = new JPanel(new BorderLayout());
		mp2 = new JPanel(new BorderLayout());
		
		tm1 = new DefaultTableModel(){
		    @Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};
		tm1.addColumn("ID");
		tm1.addColumn("Time");
		tm1.addColumn("Food Name");
		tm1.addColumn("SID");
		tm1.addColumn("Area");
		tm1.addColumn("Complete");
		tm1.addColumn("Cancel");
		tm1.addColumn("Cancel with Material");
		tm1.addColumn("Flag");
		
		tm2 = new DefaultTableModel(){
		    @Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};
		tm2.addColumn("ID");
		tm2.addColumn("Time");
		tm2.addColumn("Food Name");
		tm2.addColumn("SID");
		tm2.addColumn("Area");
		tm2.addColumn("Complete");
		tm2.addColumn("Cancel");
		tm2.addColumn("Cancel with Material");
		tm2.addColumn("Flag");
		
		update();
		
		jt1 = new JTable(tm1) {
			@Override
		    public Component prepareRenderer (TableCellRenderer renderer, int rowIndex, int columnIndex){
		        Component componenet = super.prepareRenderer(renderer, rowIndex, columnIndex);
		        
		        int flag = Integer.parseInt(getModel().getValueAt(rowIndex,8).toString());
		        String area = getModel().getValueAt(rowIndex,4).toString();
		        if(flag==1) {
		        	componenet.setBackground(myColor.green);
		            componenet.setForeground(Color.BLACK);
		            if(columnIndex == 4) {
		            	if(area.equals("d")) {
		            		componenet.setBackground(myColor.pur);
				            componenet.setForeground(Color.BLACK);
		            	}else {
		            		componenet.setBackground(myColor.blood);
				            componenet.setForeground(Color.BLACK);
		            	}
		            }if(columnIndex == 5) {
		            	componenet.setBackground(myColor.blue);
			            componenet.setForeground(Color.BLACK);
		            }else if(columnIndex == 6) {
		            	componenet.setBackground(myColor.green);
			            componenet.setForeground(myColor.green);
		            }else if(columnIndex == 7) {
		            	componenet.setBackground(myColor.green);
			            componenet.setForeground(myColor.green);
		            }
		        }else {
		        	componenet.setBackground(myColor.orange);
		            componenet.setForeground(Color.BLACK);
		            if(columnIndex == 4) {
		            	if(area.equals("d")) {
		            		componenet.setBackground(myColor.pur);
				            componenet.setForeground(Color.BLACK);
		            	}else {
		            		componenet.setBackground(myColor.blood);
				            componenet.setForeground(Color.BLACK);
		            	}
		            }if(columnIndex == 5) {
		            	componenet.setBackground(myColor.orange);
			            componenet.setForeground(myColor.orange);
		            }else if(columnIndex == 6) {
		            	componenet.setBackground(myColor.yellow);
			            componenet.setForeground(Color.BLACK);
		            }else if(columnIndex == 7) {
		            	componenet.setBackground(myColor.red);
			            componenet.setForeground(Color.BLACK);
		            }
		        }
		        return componenet;
		    }
		};
		jt1.setAutoCreateRowSorter(true);
		jt1.setRowHeight(30);
		jt1.setFont(myFont.f1);
		jt1.addMouseListener(this);
		jsp1 = new JScrollPane(jt1);
		setTable(1);
		mp1.add(jsp1,"Center");
		mmp.add(mp1);
		
		jt2 = new JTable(tm2){
			@Override
		    public Component prepareRenderer (TableCellRenderer renderer, int rowIndex, int columnIndex){
		        Component componenet = super.prepareRenderer(renderer, rowIndex, columnIndex);
		        
		        int flag = Integer.parseInt(getModel().getValueAt(rowIndex,8).toString());
		        String area = getModel().getValueAt(rowIndex,4).toString();
		        if(flag==1) {
		        	componenet.setBackground(myColor.green);
		            componenet.setForeground(Color.BLACK);
		            if(columnIndex == 4) {
		            	if(area.equals("d")) {
		            		componenet.setBackground(myColor.pur);
				            componenet.setForeground(Color.BLACK);
		            	}else {
		            		componenet.setBackground(myColor.blood);
				            componenet.setForeground(Color.BLACK);
		            	}
		            }else if(columnIndex == 5) {
		            	componenet.setBackground(myColor.blue);
			            componenet.setForeground(Color.BLACK);
		            }else if(columnIndex == 6) {
		            	componenet.setBackground(myColor.green);
			            componenet.setForeground(myColor.green);
		            }else if(columnIndex == 7) {
		            	componenet.setBackground(myColor.green);
			            componenet.setForeground(myColor.green);
		            }
		        }else {
		        	componenet.setBackground(myColor.orange);
		            componenet.setForeground(Color.BLACK);
		            if(columnIndex == 4) {
		            	if(area.equals("d")) {
		            		componenet.setBackground(myColor.pur);
				            componenet.setForeground(Color.BLACK);
		            	}else {
		            		componenet.setBackground(myColor.blood);
				            componenet.setForeground(Color.BLACK);
		            	}
		            }if(columnIndex == 5) {
		            	componenet.setBackground(myColor.orange);
			            componenet.setForeground(myColor.orange);
		            }else if(columnIndex == 6) {
		            	componenet.setBackground(myColor.yellow);
			            componenet.setForeground(Color.BLACK);
		            }else if(columnIndex == 7) {
		            	componenet.setBackground(myColor.red);
			            componenet.setForeground(Color.BLACK);
		            }
		        }
		        return componenet;
		    }
		};
		jt2.setAutoCreateRowSorter(true);
		jt2.setRowHeight(30);
		jt2.setFont(myFont.f1);
		jt2.addMouseListener(this);
		jsp2 = new JScrollPane(jt2);
		setTable(2);
		mp2.add(jsp2,"Center");
		mmp.add(mp2);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		jt1.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
		jt1.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
		jt1.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
		jt2.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
		jt2.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
		jt2.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
		
		mp.add(mmp,"Center");
		
		np = new JPanel(new BorderLayout());
		np1 = new JPanel(new FlowLayout());
		np2 = new JPanel(new GridBagLayout());
		
		blue = new JLabel(rb.getString("kit_blue"));
		blue.setFont(myFont.f0);
		blue.setForeground(myColor.blue);
		blue.setBackground(Color.BLACK);
		blue.setOpaque(true);
		yellow = new JLabel(rb.getString("kit_yel"));
		yellow.setFont(myFont.f0);
		yellow.setForeground(myColor.yellow);
		yellow.setBackground(Color.BLACK);
		yellow.setOpaque(true);
		red = new JLabel(rb.getString("kit_red"));
		red.setFont(myFont.f0);
		red.setForeground(myColor.red);
		red.setBackground(Color.BLACK);
		red.setOpaque(true);
		JLabel dine = new JLabel(rb.getString("af_dine"));
		dine.setFont(myFont.f3);
		dine.setForeground(new Color(222, 56, 255));
		dine.setOpaque(true);
		JLabel take = new JLabel(rb.getString("af_take"));
		take.setFont(myFont.f3);
		take.setForeground(new Color(255, 56, 56));
		take.setOpaque(true);
		
		np1.add(blue);np1.add(yellow);np1.add(red);np1.add(dine);np1.add(take);
		np.add(np1,"North");
		
		EmpModel emp = new EmpModel();
		String sql = "";
		if(localeCurrent==0) {
			sql = "select si_id, si_name from fyp_sitem ";
		}else {
			sql = "select si_id, si_name_zh from fyp_sitem ";
		}
		emp.runSql(sql);
		
		GridBagConstraints gbc = new GridBagConstraints();
		int x = 0;
		int u = 0;
		int n = 0;
		for(int q=0;q<emp.getRowCount();q++) {
			String ss = "ID: "+emp.getValueAt(q, 0).toString()+"--- "+emp.getValueAt(q, 1).toString();
			sname[q] = new JLabel(ss);
			sname[q].setFont(myFont.f1);
			gbc.fill = GridBagConstraints.BOTH;
			gbc.weightx = 1;
			gbc.weighty = 1;
			gbc.gridy = x;
			gbc.gridx = u;
			np2.add(sname[q],gbc);
			u++;
			if(u==4) {
				u=0;
			}
			if(q==3) {
				n=q;
				x+=1;
			}
			if(q-n==4) {
				n=q;
				x+=1;
			}
		}
		np.add(np2,"Center");
		
		mp.add(np,"South");
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
	
	public void setTable(int ff) {
		if(ff==1) {
			jt1.getColumnModel().getColumn(0).setPreferredWidth(20);
			jt1.getColumnModel().getColumn(1).setPreferredWidth(100);
			jt1.getColumnModel().getColumn(2).setPreferredWidth(400);
			jt1.getColumnModel().getColumn(3).setPreferredWidth(100);
			jt1.getColumnModel().getColumn(4).setPreferredWidth(20);
			jt1.getColumnModel().getColumn(5).setPreferredWidth(50);
			jt1.getColumnModel().getColumn(6).setPreferredWidth(50);
			jt1.getColumnModel().getColumn(7).setPreferredWidth(50);
			jt1.getColumnModel().getColumn(8).setPreferredWidth(0);
		}else if(ff==2) {
			jt2.getColumnModel().getColumn(0).setPreferredWidth(20);
			jt2.getColumnModel().getColumn(1).setPreferredWidth(100);
			jt2.getColumnModel().getColumn(2).setPreferredWidth(400);
			jt2.getColumnModel().getColumn(3).setPreferredWidth(100);
			jt2.getColumnModel().getColumn(4).setPreferredWidth(20);
			jt2.getColumnModel().getColumn(5).setPreferredWidth(50);
			jt2.getColumnModel().getColumn(6).setPreferredWidth(50);
			jt2.getColumnModel().getColumn(7).setPreferredWidth(50);
			jt2.getColumnModel().getColumn(8).setPreferredWidth(0);
		}
	}
	
	private void start() {
		SwingWorker<Void,Void> worker = new SwingWorker<Void, Void>(){
			@Override
			protected Void doInBackground() throws Exception {
				while(FLAG) {
					try {
						Thread.sleep(1000);
						update();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				return null;
			}
		};
		worker.execute();
	}
	
	public void update() {
		EmpModel cc = new EmpModel();
		String gg = "select sum(oi_flag) from fyp_orderitem where oi_sid='"+sid+"' ";
		String tt = "select count(*) from fyp_sorder";
		cc.runSql(gg);
		int ngg = 0;
		int ntt = 0;
		try{
			ngg = Integer.parseInt(cc.getValueAt(0, 0).toString());
			ntt = Integer.parseInt(cc.getValueAt(0, 0).toString());
		}catch (Exception e) {
			e.printStackTrace();
		}
		cc.runSql(tt);
		if(ngg!=gflag || ntt!=tflag) {
			gflag=ngg;
			tflag=ntt;
			EmpModel t1 = new EmpModel();
			String sql = "";
			if(localeCurrent==0) {
				sql = "select oi_id,oi_time,food_name,oi_area,oi_flag,food_cat from (  " + 
					"select ff.*,oi.* from fyp_food ff JOIN fyp_orderitem oi ON ff.food_id = oi.oi_fid ) " + 
					"where oi_sid = '"+sid+"' and (oi_flag='1' or oi_flag='3') " + 
					"and (food_cat='ds' or food_cat='sv' or food_cat='bt' or food_cat='pot' or food_cat='rice') "+
					"order by regexp_substr(oi_id, '^\\D*') nulls first, to_number(regexp_substr(oi_id, '\\d+')) ASC ";		
			}else {
				sql = "select oi_id,oi_time,food_name_zh,oi_area,oi_flag,food_cat from (  " + 
					"select ff.*,oi.* from fyp_food ff JOIN fyp_orderitem oi ON ff.food_id = oi.oi_fid ) " + 
					"where oi_sid = '"+sid+"' and (oi_flag='1' or oi_flag='3') " + 
					"and (food_cat='ds' or food_cat='sv' or food_cat='bt' or food_cat='pot' or food_cat='rice') "+
					"order by regexp_substr(oi_id, '^\\D*') nulls first, to_number(regexp_substr(oi_id, '\\d+')) ASC ";		
			}
			t1.runSql(sql);
			
			EmpModel emp = new EmpModel();
			tm1.setRowCount(0);
			for(int y=0;y<t1.getRowCount();y++) {
				sql = "select so_si_id from fyp_sorder where so_o_id='"+t1.getValueAt(y, 0).toString()+"' ";
				emp.runSql(sql);
				String specialLog = "";
				for(int x=0;x<emp.getRowCount();x++) {
					if(x==0) {
						specialLog = emp.getValueAt(x, 0).toString();
					}else {
						specialLog = specialLog+","+emp.getValueAt(x, 0).toString();
					}
				}
				Vector<String> vv = new Vector<String>();
				vv.addElement(t1.getValueAt(y, 0).toString());
				vv.addElement(t1.getValueAt(y, 1).toString().substring(10,19));
				vv.addElement(t1.getValueAt(y, 2).toString());
				vv.addElement(specialLog);
				vv.addElement(t1.getValueAt(y, 3).toString());
				vv.addElement(rb.getString("kit_com"));
				vv.addElement(rb.getString("kit_can"));
				vv.addElement(rb.getString("kit_canm"));
				vv.addElement(t1.getValueAt(y, 4).toString());
				tm1.addRow(vv);
			}
			
			EmpModel t2 = new EmpModel();
			String sql2 = "";
			if(localeCurrent==0) {
				sql2 = "select oi_id,oi_time,food_name,oi_area,oi_flag,food_cat from (  " + 
					"select ff.*,oi.* from fyp_food ff JOIN fyp_orderitem oi ON ff.food_id = oi.oi_fid ) " + 
					"where oi_sid = '"+sid+"' and (oi_flag='1' or oi_flag='3') " + 
					"and (food_cat!='ds' and food_cat!='sv' and food_cat!='bt' and food_cat!='pot' and food_cat!='rice') "+
					"order by regexp_substr(oi_id, '^\\D*') nulls first, to_number(regexp_substr(oi_id, '\\d+')) ASC ";		
			}else {
				sql2 = "select oi_id,oi_time,food_name_zh,oi_area,oi_flag,food_cat from (  " + 
					"select ff.*,oi.* from fyp_food ff JOIN fyp_orderitem oi ON ff.food_id = oi.oi_fid ) " + 
					"where oi_sid = '"+sid+"' and (oi_flag='1' or oi_flag='3') " + 
					"and (food_cat!='ds' and food_cat!='sv' and food_cat!='bt' and food_cat!='pot' and food_cat!='rice') "+
					"order by regexp_substr(oi_id, '^\\D*') nulls first, to_number(regexp_substr(oi_id, '\\d+')) ASC ";		
			}
			t2.runSql(sql2);
			
			tm2.setRowCount(0);
			for(int y=0;y<t2.getRowCount();y++) {
				sql = "select so_si_id from fyp_sorder where so_o_id='"+t2.getValueAt(y, 0).toString()+"' ";
				emp.runSql(sql);
				String specialLog = "";
				for(int x=0;x<emp.getRowCount();x++) {
					if(x==0) {
						specialLog = emp.getValueAt(x, 0).toString();
					}else {
						specialLog = specialLog+","+emp.getValueAt(x, 0).toString();
					}
				}
				Vector<String> vv = new Vector<String>();
				vv.addElement(t2.getValueAt(y, 0).toString());
				vv.addElement(t2.getValueAt(y, 1).toString().substring(10,19));
				vv.addElement(t2.getValueAt(y, 2).toString());
				vv.addElement(specialLog);
				vv.addElement(t2.getValueAt(y, 3).toString());
				vv.addElement(rb.getString("kit_com"));
				vv.addElement(rb.getString("kit_can"));
				vv.addElement(rb.getString("kit_canm"));
				vv.addElement(t2.getValueAt(y, 4).toString());
				tm2.addRow(vv);
			}
		}
	}
	
	public static void main(String[] args) {
		KitchenMain km = new KitchenMain(0, 2, "shop2");
		Thread upT = new Thread(km);
		upT.start();
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
			LoadingScene ls = new LoadingScene("k",x,uid,sid);
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
		if(e.getSource() == jt1) {
			int col = jt1.columnAtPoint(e.getPoint());
			int flag = Integer.parseInt(jt1.getValueAt(jt1.getSelectedRow(), 8).toString());
			int oiid = Integer.parseInt(jt1.getValueAt(jt1.getSelectedRow(), 0).toString());
			EmpModel emp = new EmpModel();
			String sql = "";
			if(col==5) { // ok
				if(flag==1) {
					sql = "update fyp_orderitem set oi_flag=? where oi_id=? ";
					String [] paras = {2+"",oiid+""};
					emp.updInfo(sql, paras);
					sql = "select oi_fid from fyp_orderitem where oi_id='"+oiid+"' ";
					emp.runSql(sql);
					String fid = emp.getValueAt(0, 0).toString();
					sql="select FOOD_USED from fyp_food where food_id='"+fid+"' ";
					emp.runSql(sql);
					String fu = emp.getValueAt(0, 0).toString();
					if(fu.equals("x")) {
						;
					}else {
						EmpModel emp2 = new EmpModel();
						String[] buff = fu.split(",");
						for(int x=0;x<buff.length;x++) {
							String[] buff2 = buff[x].split(":");
							int mid = Integer.parseInt(buff2[0]);
							float mu = Float.parseFloat(buff2[1]);
							sql = "select m_l from fyp_materialshop where"
									+ " m_id='"+mid+"' and m_sid='"+sid+"' ";
							emp2.runSql(sql);
							float nmu = Float.parseFloat(emp2.getValueAt(0, 0).toString());
							BigDecimal a = new BigDecimal(nmu);
	    	    	        BigDecimal b = new BigDecimal(mu);
	    	    	        BigDecimal c = a.subtract(b);
	    	    	        float newmu = Float.parseFloat(c.toString());
	    	    	        sql = "update fyp_materialshop set m_l=? where m_id=? and m_sid=? ";
	    	    	        String[] paras2 = {newmu+"",mid+"",sid};
	    	    	        emp2.updInfo(sql, paras2);
						}
					}
				}
			}else if(col==6) { //delete without M
				if(flag==3) {
					sql = "update fyp_orderitem set oi_flag=? where oi_id=? ";
					String [] paras = {4+"",oiid+""};
					emp.updInfo(sql, paras);
				}
			}else if(col==7) { //delete with M
				if(flag==3) {
					sql = "update fyp_orderitem set oi_flag=? where oi_id=? ";
					String [] paras = {4+"",oiid+""};
					emp.updInfo(sql, paras);
					sql = "select oi_fid from fyp_orderitem where oi_id='"+oiid+"' ";
					emp.runSql(sql);
					String fid = emp.getValueAt(0, 0).toString();
					sql="select FOOD_USED from fyp_food where food_id='"+fid+"' ";
					emp.runSql(sql);
					String fu = emp.getValueAt(0, 0).toString();
					if(fu.equals("x")) {
						;
					}else {
						EmpModel emp2 = new EmpModel();
						String[] buff = fu.split(",");
						for(int x=0;x<buff.length;x++) {
							String[] buff2 = buff[x].split(":");
							int mid = Integer.parseInt(buff2[0]);
							float mu = Float.parseFloat(buff2[1]);
							sql = "select m_l from fyp_materialshop where"
									+ " m_id='"+mid+"' and m_sid='"+sid+"' ";
							emp2.runSql(sql);
							float nmu = Float.parseFloat(emp2.getValueAt(0, 0).toString());
							BigDecimal a = new BigDecimal(nmu);
	    	    	        BigDecimal b = new BigDecimal(mu);
	    	    	        BigDecimal c = a.subtract(b);
	    	    	        float newmu = Float.parseFloat(c.toString());
	    	    	        sql = "update fyp_materialshop set m_l=? where m_id=? and m_sid=? ";
	    	    	        String[] paras2 = {newmu+"",mid+"",sid};
	    	    	        emp2.updInfo(sql, paras2);
						}
					}
				}
			}
		}else if(e.getSource() == jt2) {
			int col = jt2.columnAtPoint(e.getPoint());
			int flag = Integer.parseInt(jt2.getValueAt(jt2.getSelectedRow(), 8).toString());
			int oiid = Integer.parseInt(jt2.getValueAt(jt2.getSelectedRow(), 0).toString());
			EmpModel emp = new EmpModel();
			String sql = "";
			if(col==5) { // ok
				if(flag==1) {
					sql = "update fyp_orderitem set oi_flag=? where oi_id=? ";
					String [] paras = {2+"",oiid+""};
					emp.updInfo(sql, paras);
					sql = "select oi_fid from fyp_orderitem where oi_id='"+oiid+"' ";
					emp.runSql(sql);
					String fid = emp.getValueAt(0, 0).toString();
					sql="select FOOD_USED from fyp_food where food_id='"+fid+"' ";
					emp.runSql(sql);
					String fu = emp.getValueAt(0, 0).toString();
					if(fu.equals("x")) {
						;
					}else {
						EmpModel emp2 = new EmpModel();
						String[] buff = fu.split(",");
						for(int x=0;x<buff.length;x++) {
							String[] buff2 = buff[x].split(":");
							int mid = Integer.parseInt(buff2[0]);
							float mu = Float.parseFloat(buff2[1]);
							sql = "select m_l from fyp_materialshop where"
									+ " m_id='"+mid+"' and m_sid='"+sid+"' ";
							emp2.runSql(sql);
							float nmu = Float.parseFloat(emp2.getValueAt(0, 0).toString());
							BigDecimal a = new BigDecimal(nmu);
	    	    	        BigDecimal b = new BigDecimal(mu);
	    	    	        BigDecimal c = a.subtract(b);
	    	    	        float newmu = Float.parseFloat(c.toString());
	    	    	        sql = "update fyp_materialshop set m_l=? where m_id=? and m_sid=? ";
	    	    	        String[] paras2 = {newmu+"",mid+"",sid};
	    	    	        emp2.updInfo(sql, paras2);
						}
					}
				}
			}else if(col==6) { //delete without M
				if(flag==3) {
					sql = "update fyp_orderitem set oi_flag=? where oi_id=? ";
					String [] paras = {4+"",oiid+""};
					emp.updInfo(sql, paras);
				}
			}else if(col==7) { //delete with M
				if(flag==3) {
					sql = "update fyp_orderitem set oi_flag=? where oi_id=? ";
					String [] paras = {4+"",oiid+""};
					emp.updInfo(sql, paras);
					sql = "select oi_fid from fyp_orderitem where oi_id='"+oiid+"' ";
					emp.runSql(sql);
					String fid = emp.getValueAt(0, 0).toString();
					sql="select FOOD_USED from fyp_food where food_id='"+fid+"' ";
					emp.runSql(sql);
					String fu = emp.getValueAt(0, 0).toString();
					if(fu.equals("x")) {
						;
					}else {
						EmpModel emp2 = new EmpModel();
						String[] buff = fu.split(",");
						for(int x=0;x<buff.length;x++) {
							String[] buff2 = buff[x].split(":");
							int mid = Integer.parseInt(buff2[0]);
							float mu = Float.parseFloat(buff2[1]);
							sql = "select m_l from fyp_materialshop where"
									+ " m_id='"+mid+"' and m_sid='"+sid+"' ";
							emp2.runSql(sql);
							float nmu = Float.parseFloat(emp2.getValueAt(0, 0).toString());
							BigDecimal a = new BigDecimal(nmu);
	    	    	        BigDecimal b = new BigDecimal(mu);
	    	    	        BigDecimal c = a.subtract(b);
	    	    	        float newmu = Float.parseFloat(c.toString());
	    	    	        sql = "update fyp_materialshop set m_l=? where m_id=? and m_sid=? ";
	    	    	        String[] paras2 = {newmu+"",mid+"",sid};
	    	    	        emp2.updInfo(sql, paras2);
						}
					}
				}
			}
		}
	}

	@Override
	public void run() {}
	@Override
	public void mouseEntered(MouseEvent arg0) {}
	@Override
	public void mouseExited(MouseEvent arg0) {}
	@Override
	public void mousePressed(MouseEvent arg0) {}
	@Override
	public void mouseReleased(MouseEvent arg0) {}
}
