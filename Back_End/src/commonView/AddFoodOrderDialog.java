package commonView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import model.EmpModel;
import tools.myBorder;
import tools.myFont;

public class AddFoodOrderDialog extends JDialog implements ActionListener {
	String sid;
	String oid;
	String ot = "";
	int flag = 0;
	int ppl = 0;
	
	JPanel top,mid,but,m;
	JLabel loid,lppl,ltime,lphone,statusText;
	JButton del,chB,couponB,delOrder;
	
	JTable jtable;
	JScrollPane jsp;
	DefaultTableModel tm;
	
	JButton changeP,add,changeT,invoice,bill,cancel;
	
	JFrame parent;
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
	
	public AddFoodOrderDialog(Frame owner,String title,boolean modal,
			int FLAG,int locale,String shopid,String orderid) {
		super(owner,title,modal);
		localeCurrent = locale;
		sid = shopid;
		oid = orderid;
		flag = FLAG;
		
		initLocale();
		
		mainPanel(flag);
		
		try {
			titleIcon = ImageIO.read(new File("images/logo/logo.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setIconImage(titleIcon);
		int w = Toolkit.getDefaultToolkit().getScreenSize().width;
		int h = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation(w/2-600, h/2- 400);
		this.setSize(1200,800);
		this.setVisible(true);
	}
	
	public void mainPanel(int f) {
		EmpModel emp = new EmpModel();
		String sql = "select order_time, order_ppl, order_phone from fyp_order "
				   + "where order_area = '"+f+"' and order_sid = '"+sid+"' and order_flag = 1 and "
				   + "order_id = '"+oid+"' ";
		emp.runSql(sql);
		
		ppl = Integer.parseInt(emp.getValueAt(0, 1).toString());
		ot = emp.getValueAt(0, 0).toString();
		
		m = new JPanel(new BorderLayout());
		
		top = new JPanel(new GridLayout(0,f+2));
		loid = new JLabel("<html>"+rb.getString("afo_oid")+"<br>"+oid+"</html>");
		lppl = new JLabel("<html>"+rb.getString("afo_ppl")+"<br>"+emp.getValueAt(0, 1).toString()+"</html>");
		ltime = new JLabel("<html>"+rb.getString("afo_time")+"<br>"+emp.getValueAt(0, 0).toString()+"</html>");
		loid.setFont(myFont.f1);
		lppl.setFont(myFont.f1);
		ltime.setFont(myFont.f1);
		loid.setBorder(myBorder.border3);
		lppl.setBorder(myBorder.border3);
		ltime.setBorder(myBorder.border3);
		top.add(loid);
		top.add(lppl);
		top.add(ltime);
		
		if(f==2) {
			lphone = new JLabel("<html>"+rb.getString("afo_phone")+"<br>"+emp.getValueAt(0, 2).toString()+"</html>");
			lphone.setFont(myFont.f1);
			lphone.setBorder(myBorder.border3);
			top.add(lphone);
		}
		
		m.add(top,"North");
		
		mid = new JPanel(new BorderLayout());
		JPanel mid2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		mid2.setBackground(Color.black);
		
		tm = new DefaultTableModel(){
		    @Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};
		tm.addColumn("oi_id");
		tm.addColumn("Time");
		tm.addColumn("Combo ID");
		tm.addColumn("Food ID");
		tm.addColumn("Area");
		tm.addColumn("Price");
		tm.addColumn("Flag");
		
		String sql2 = "";
		if(localeCurrent==0) {
			sql2 = "select oi_id,oi_time,oi_cid,food_name,oi_area,oi_price,oi_flag from ( " + 
				   "select ff.*,oi.* from fyp_food ff JOIN fyp_orderitem oi ON ff.food_id = oi.oi_fid ) " + 
				   "where oi_oid = '"+oid+"' and oi_sid = '"+sid+"' and (oi_flag='1' or oi_flag='2') " +
				   "and oi_ptime = '"+ot+"' order by length(oi_id), oi_id ASC ";
		}else {
			sql2 = "select oi_id,oi_time,oi_cid,food_name_zh,oi_area,oi_price,oi_flag from ( " + 
				   "select ff.*,oi.* from fyp_food ff JOIN fyp_orderitem oi ON ff.food_id = oi.oi_fid ) " + 
				   "where oi_oid = '"+oid+"' and oi_sid = '"+sid+"' and (oi_flag='1' or oi_flag='2') "
				   	+ "and oi_ptime = '"+ot+"' order by length(oi_id), oi_id ASC ";
		}
		emp.runSql(sql2);
		for(int y=0;y<emp.getRowCount();y++) {
			Vector<String> vv = new Vector<String>();
			for(int x=0;x<emp.getColumnCount();x++) {
				vv.addElement(emp.getValueAt(y, x).toString());
			}
			tm.addRow(vv);
		}
		jtable = new JTable(tm);
		jtable.setAutoCreateRowSorter(true);
		jtable.setRowHeight(30);
		jtable.setFont(myFont.f3);
		jtable.setForeground(Color.WHITE);
		jtable.setBackground(Color.black);
		setTable();
		jsp = new JScrollPane(jtable);
		jsp.getViewport().setBackground(Color.BLACK);
		mid.add(jsp,"Center");
		
		add = new JButton(rb.getString("afo_add"));
		add.setFont(myFont.f1);
		add.setBackground(Color.BLACK);
		add.setForeground(Color.WHITE);
		add.addActionListener(this);
		del = new JButton(rb.getString("afo_delB"));
		del.setFont(myFont.f1);
		del.setBackground(Color.BLACK);
		del.setForeground(Color.WHITE);
		del.addActionListener(this);
		chB = new JButton(rb.getString("fj_up"));
		chB.setFont(myFont.f1);
		chB.setBackground(Color.BLACK);
		chB.setForeground(Color.WHITE);
		chB.addActionListener(this);
		couponB = new JButton(rb.getString("uj_coupon"));
		couponB.setFont(myFont.f1);
		couponB.setBackground(Color.BLACK);
		couponB.setForeground(Color.WHITE);
		couponB.addActionListener(this);
		mid2.add(add);
		mid2.add(del);
		mid2.add(chB);
		mid2.add(couponB);
		mid.add(mid2,"South");
		
		m.add(mid,"Center");
		
		but = new JPanel(new FlowLayout(FlowLayout.CENTER));
		but.setBackground(Color.WHITE);
		changeP = new JButton(rb.getString("afo_chaP"));
		changeT = new JButton(rb.getString("afo_chT"));
		invoice = new JButton(rb.getString("afo_inv"));
		bill = new JButton(rb.getString("afo_bill"));
		cancel = new JButton(rb.getString("afo_cancel"));
		delOrder = new JButton(rb.getString("afo_cancelA"));
		changeP.setFont(myFont.f1);
		changeT.setFont(myFont.f1);
		invoice.setFont(myFont.f1);
		bill.setFont(myFont.f1);
		cancel.setFont(myFont.f1);
		delOrder.setFont(myFont.f1);
		changeP.addActionListener(this);
		changeT.addActionListener(this);
		invoice.addActionListener(this);
		bill.addActionListener(this);
		cancel.addActionListener(this);
		delOrder.addActionListener(this);
		
		if(f==2) {
			but.add(changeP);
		}else {
			but.add(changeT);
		}
		but.add(invoice);
		but.add(bill);
		but.add(cancel);
		but.add(delOrder);
		
		m.add(but,"South");
		
		this.add(m);
	}
	
	public void update() {
		EmpModel emp = new EmpModel();
		String sql2 = "";
		if(localeCurrent==0) {
			sql2 = "select oi_id,oi_time,oi_cid,food_name,oi_area,oi_price,oi_flag from ( " + 
				   "select ff.*,oi.* from fyp_food ff JOIN fyp_orderitem oi ON ff.food_id = oi.oi_fid ) " + 
				   "where oi_oid = '"+oid+"' and oi_sid = '"+sid+"' and (oi_flag='1' or oi_flag='2') "
				   		+ "and oi_ptime = '"+ot+"' order by length(oi_id), oi_id ASC ";
		}else {
			sql2 = "select oi_id,oi_time,oi_cid,food_name_zh,oi_area,oi_price,oi_flag from ( " + 
				   "select ff.*,oi.* from fyp_food ff JOIN fyp_orderitem oi ON ff.food_id = oi.oi_fid ) " + 
				   "where oi_oid = '"+oid+"' and oi_sid = '"+sid+"' and (oi_flag='1' or oi_flag='2') "
				   		+ "and oi_ptime = '"+ot+"' order by length(oi_id), oi_id ASC ";
		}
		emp.runSql(sql2);
		tm.setRowCount(0);
		for(int y=0;y<emp.getRowCount();y++) {
			Vector<String> vv = new Vector<String>();
			for(int x=0;x<emp.getColumnCount();x++) {
				vv.addElement(emp.getValueAt(y, x).toString());
			}
			tm.addRow(vv);
		}
		jtable.setModel(tm);
		setTable();
	}
	
	public void setTable() {
		jtable.getColumnModel().getColumn(0).setPreferredWidth(50);
		jtable.getColumnModel().getColumn(1).setPreferredWidth(200);
		jtable.getColumnModel().getColumn(2).setPreferredWidth(100);
		jtable.getColumnModel().getColumn(3).setPreferredWidth(500);
		jtable.getColumnModel().getColumn(4).setPreferredWidth(50);
		jtable.getColumnModel().getColumn(5).setPreferredWidth(50);
		jtable.getColumnModel().getColumn(5).setPreferredWidth(50);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == changeT) {
			new ChangeTableDialog(parent, rb.getString("ChangeTable_title")+oid,
					true, localeCurrent, sid, oid, ppl);
			this.dispose();
		}else if(e.getSource() == changeP) {
			new ChangePhoneDialog(parent, rb.getString("ChangeTable_title")+oid,
					true, localeCurrent, sid, oid);
			int n = ChangePhoneDialog.newPhoneGetValue();
			lphone.setText("<html>"+rb.getString("afo_phone")+"<br>"+n+"</html>");
		}else if(e.getSource() == cancel) {
			this.dispose();
		}else if(e.getSource() == add) {
			new AddFoodOrderSelector(parent,rb.getString("addFood_title"),true,flag,localeCurrent,sid,oid);
			update();
		}else if(e.getSource() == chB) {
			String oiid = jtable.getValueAt(jtable.getSelectedRow(), 0).toString();
			new AddOrderUpdater(parent,rb.getString("addFood_title"),true,localeCurrent,oiid,sid,oid);
			update();
		}else if(e.getSource() == del) {
			try {
				String oiid = jtable.getValueAt(jtable.getSelectedRow(), 0).toString();
				String ocomboid = jtable.getValueAt(jtable.getSelectedRow(), 2).toString();
				if(ocomboid.equals("na")) {
					EmpModel emp = new EmpModel();
					String sql = "update fyp_orderitem set oi_flag='3' where oi_id=? and oi_sid=? ";
					String [] paras = {oiid,sid};
					emp.updInfo(sql, paras);
				}else {
					int reply = JOptionPane.showConfirmDialog(null,rb.getString("mess_confirm"),rb.getString("mess_confirmTitle"),JOptionPane.YES_NO_OPTION);
			        if (reply == JOptionPane.YES_OPTION) {
			        	EmpModel emp = new EmpModel();
						String sql = "update fyp_orderitem set oi_flag='3' where oi_cid=? and oi_sid=? ";
						String [] paras = {ocomboid,sid};
						emp.updInfo(sql, paras);
			        }
				}
				update();
			}catch (Exception e2) {
				e2.printStackTrace();
			}
		}else if(e.getSource() == invoice) {
			new InvoiceDialog(parent,rb.getString("invoice_title"),true,localeCurrent,sid,oid);
			update();
		}else if(e.getSource() == bill) {
			new BillDialog(parent,rb.getString("invoice_title"),true,localeCurrent,sid,oid,ppl);
			this.dispose();
		}else if(e.getSource() == couponB) {
			new AddFoodFreeDialog(parent,rb.getString("addFood_title"),true,localeCurrent,sid,oid,flag);
			update();
		}else if(e.getSource() == delOrder) {
			int reply = JOptionPane.showConfirmDialog(null,rb.getString("mess_confirm"),rb.getString("mess_confirmTitle"),JOptionPane.YES_NO_OPTION);
	        if (reply == JOptionPane.YES_OPTION) {
	        	EmpModel emp = new EmpModel();
	        	String sql = "update fyp_order set order_flag='2' "
	        			+ "where order_id=? and order_sid=? and order_time=? ";
	        	String[] paras = {oid,sid,ot};
	        	emp.updInfo(sql, paras);
	        	
	        	sql = "update fyp_orderitem set oi_flag='3' "
	        			+ "where oi_oid=? and oi_sid=? and oi_ptime=? and oi_flag='1' ";
	        	String[] paras2 = {oid,sid,ot};
	        	emp.updInfo(sql, paras2);
	        	
	        	String tid = oid.substring(0,3);
	        	
	        	if(flag==2) {
	        		;
	        	}else {
	        		sql = "select table_number, table_flag, table_now from fyp_table "
		        			+ "where table_id='"+tid+"' and table_shop='"+sid+"' ";
		        	emp.runSql(sql);
		        	
		        	int tno = Integer.parseInt(emp.getValueAt(0, 0).toString());
		        	int tf = Integer.parseInt(emp.getValueAt(0, 1).toString());
		        	int tnow = Integer.parseInt(emp.getValueAt(0, 2).toString());
		        	
		        	if((tno-tnow-ppl)==0) {
		        		tf=1;
		        	}else {
		        		tf=2;
		        	}
		        	tnow = tnow+ppl;
		        	sql = "update fyp_table set table_flag=?, table_now=? "
		        			+ "where table_id=? and table_shop=? ";
		        	String[] paras3 = {tf+"",tnow+"",tid,sid};
		        	emp.updInfo(sql, paras3);
	        	}
	        	this.dispose();
	        }
		}
	}
}
