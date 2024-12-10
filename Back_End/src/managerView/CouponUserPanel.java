package managerView;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import managerView.FoodDetailDialog.Item;
import model.EmpModel;
import tools.myBorder;
import tools.myFont;

public class CouponUserPanel extends JPanel implements ActionListener{
	JFrame parent;
	
	JPanel m,p1,p11,p2;
	JLabel search;
	JTextField sf;
	JButton b,sgo, add,up,del;
	
	JComboBox<Item> type,from;
	JComboBox<String> flag,month,year;
	JButton dgo,reset;
	
	JTable jt;
	JScrollPane jsp;
	
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
	
	public CouponUserPanel(int locale) {
		localeCurrent = locale;
		initLocale();
		
		p1 = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		b = new JButton(rb.getString("common_exit"));
		b.setFont(myFont.f1);
		b.addActionListener(this);
		
		String[] StatusStr = { rb.getString("cu_notuse"),rb.getString("cu_used"),rb.getString("cu_del")};
		flag = new JComboBox<String>(StatusStr);
		flag.setFont(myFont.f1);
		flag.setSelectedIndex(0);
		flag.addActionListener(this);
		
		search = new JLabel(rb.getString("hr_search"),JLabel.RIGHT);
		search.setFont(myFont.f1);
		
		sf = new JTextField(40);
		sf.setFont(myFont.f1);
		
		sgo = new JButton(rb.getString("common_search"));
		sgo.setFont(myFont.f1);
		sgo.addActionListener(this);
		
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		p1.add(b,gbc);
		gbc.gridx = 1;
		p1.add(flag);
		gbc.gridx = 2;
		p1.add(search);
		gbc.gridx = 3;
		p1.add(sf);
		gbc.gridx = 4;
		p1.add(sgo);
		
		p11 = new JPanel(new GridBagLayout());
		
		EmpModel emp = new EmpModel();
		String sql2 = "";
		if(localeCurrent==0) {
			sql2 = "select c_type, c_n from fyp_coupon order by c_type ASC ";
		}else {
			sql2 = "select c_type, c_nzh from fyp_coupon order by c_type ASC ";
		}
		emp.runSql(sql2);
		Vector<Item> model = new Vector<Item>();
		for(int i=0;i<emp.getRowCount();i++) {
			model.addElement(new Item(emp.getValueAt(i, 0).toString(), emp.getValueAt(i, 1).toString()));
		}
		type = new JComboBox<Item>(model);
		type.setFont(myFont.f1);
		type.setSelectedIndex(0);
		type.addActionListener(this);
		
		if(localeCurrent==0) {
			sql2 = "select shop_id, shop_name from fyp_shop order by length(shop_id), shop_id ";
		}else {
			sql2 = "select shop_id, shop_name_zh from fyp_shop order by length(shop_id), shop_id ";
		}
		emp.runSql(sql2);
		Vector<Item> model2 = new Vector<Item>();
		model2.addElement(new Item("net", rb.getString("cu_net")));
		for(int i=0;i<emp.getRowCount();i++) {
			model2.addElement(new Item(emp.getValueAt(i, 0).toString(), emp.getValueAt(i, 1).toString()));
		}
		from = new JComboBox<Item>(model2);
		from.setFont(myFont.f1);
		from.setSelectedIndex(0);
		from.addActionListener(this);
		
		JPanel jj = new JPanel();
		
		String[] Year = { "2022","2021","2020","2019","2018","2017"};
		year = new JComboBox<String>(Year);
		year.setFont(myFont.f1);
		year.setSelectedIndex(2);
		
		String[] Mon = { "1","2","3","4","5","6","7","8","9","10","11","12"};
		month = new JComboBox<String>(Mon);
		month.setFont(myFont.f1);
		month.setSelectedIndex(0);
		
		dgo = new JButton(rb.getString("common_ok"));
		dgo.setFont(myFont.f1);
		dgo.addActionListener(this);
		
		jj.setBorder(myBorder.border3);
		jj.add(year);
		jj.add(month);
		jj.add(dgo);

		reset = new JButton(rb.getString("common_reset"));
		reset.setFont(myFont.f1);
		reset.addActionListener(this);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		p11.add(type,gbc);
		gbc.gridx = 1;
		p11.add(from,gbc);
		gbc.gridx = 2;
		p11.add(jj,gbc);
		gbc.gridx = 3;
		p11.add(reset,gbc);
		
		m = new JPanel(new GridBagLayout());
		gbc.gridx = 0;
		gbc.gridy = 0;
		m.add(p1,gbc);
		gbc.gridy = 1;
		m.add(p11,gbc);
		
		String sql = "select C_ID,C_TYPE,C_UID,C_DATE,C_FROM,C_FLAG from fyp_usercoupon order by c_id DESC ";
		emp.runSql(sql);
		jt = new JTable(emp);
		jt.setAutoCreateRowSorter(true);
		jt.setRowHeight(30);
		jt.setFont(myFont.f1);
		jsp = new JScrollPane(jt);
		
		add = new JButton(rb.getString("fj_add"));
		up = new JButton(rb.getString("fj_up"));
		del = new JButton(rb.getString("fj_del"));
		add.setFont(myFont.f1);
		up.setFont(myFont.f1);
		del.setFont(myFont.f1);
		add.addActionListener(this);
		up.addActionListener(this);
		del.addActionListener(this);
		
		p2 = new JPanel();
		p2.add(add);
		p2.add(up);
		p2.add(del);
		
		this.setLayout(new BorderLayout());
		this.add(m,"North");
		this.add(jsp,"Center");
		this.add(p2,"South");
	}
	
	public void reFresh() {
		EmpModel emp = new EmpModel();
		String sql = "select C_ID,C_TYPE,C_UID,C_DATE,C_FROM,C_FLAG from fyp_usercoupon order by c_id DESC ";
		emp.runSql(sql);
		jt.setModel(emp);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == sgo) {
			String input = sf.getText();
			String sql = "select C_ID,C_TYPE,C_UID,C_DATE,C_FROM,C_FLAG from fyp_usercoupon where "
				+ "(lower(C_UID) like lower('%"+input+"%') or "
				+ "lower(C_ID) like lower('%"+input+"%')) "
				+ "order by c_id DESC ";
			EmpModel emp = new EmpModel();
			emp.runSql(sql);
			jt.setModel(emp);
		}else if(e.getSource() == reset) {
			reFresh();
		}else if(e.getSource() == add) {
			new CouponUserDetailDialog(parent,rb.getString("couUserDetail_title"),true,localeCurrent,1,0);
			reFresh();
		}else if(e.getSource() == up) {
			try {
				int cid = Integer.parseInt(jt.getValueAt(jt.getSelectedRow(), 0).toString());
				new CouponUserDetailDialog(parent,rb.getString("couUserDetail_title"),true,localeCurrent,2,cid);
				reFresh();
			}catch (Exception e2) {}
		}else if(e.getSource() == del) {
			String cid = jt.getValueAt(jt.getSelectedRow(), 0).toString();
			String uidf = jt.getValueAt(jt.getSelectedRow(), 2).toString();
			String ff = jt.getValueAt(jt.getSelectedRow(), 4).toString();
			int userID = 0;
			if(uidf.equals("guest")) {
				userID = 0;
			}else {
				userID=Integer.parseInt(uidf);
			}
			int reply = JOptionPane.showConfirmDialog(null,rb.getString("mess_confirm"),rb.getString("mess_confirmTitle"),JOptionPane.YES_NO_OPTION);
	        if (reply == JOptionPane.YES_OPTION) {
	        	String del = "delete from fyp_usercoupon where c_id=? ";
	        	EmpModel emp = new EmpModel();
	        	String[] paras = {cid};
	        	emp.updInfo(del, paras);
	        	
				int staffid = ManagerMain.getUid();
				String sql = "insert into fyp_couponlog values "
						+ "(clog_increase.nextval,?,?,TO_DATE(SYSDATE),?,?,?) ";
				String[] paras2 = {cid,userID+"",ff,staffid+"",4+""};
				emp.updInfo(sql, paras2);
	        }
	        reFresh();
		}else if(e.getSource() == b) {
			this.setVisible(false);
		}else if(e.getSource() == flag) {
			EmpModel emp = new EmpModel();
			String sql = "select C_ID,C_TYPE,C_UID,C_DATE,C_FROM,C_FLAG from fyp_usercoupon"
					+ " where C_FLAG='"+(flag.getSelectedIndex()+1)+"' order by c_id DESC ";
			emp.runSql(sql);
			jt.setModel(emp);
		}else if(e.getSource() == type) {
			Item item = (Item)type.getSelectedItem();
			String getType = item.getId();
			EmpModel emp = new EmpModel();
			String sql = "select C_ID,C_TYPE,C_UID,C_DATE,C_FROM,C_FLAG from fyp_usercoupon"
					+ " where C_TYPE='"+getType+"' order by c_id DESC ";
			emp.runSql(sql);
			jt.setModel(emp);
		}else if(e.getSource() == from) {
			Item item = (Item)from.getSelectedItem();
			String getFrom = item.getId();
			EmpModel emp = new EmpModel();
			String sql = "select C_ID,C_TYPE,C_UID,C_DATE,C_FROM,C_FLAG from fyp_usercoupon"
					+ " where C_FROM='"+getFrom+"' order by c_id DESC ";
			emp.runSql(sql);
			jt.setModel(emp);
		}else if(e.getSource() == dgo) {
			String m = month.getSelectedItem().toString();
			String y = year.getSelectedItem().toString();
			EmpModel emp = new EmpModel();
			String sql = "select C_ID,C_TYPE,C_UID,C_DATE,C_FROM,C_FLAG from fyp_usercoupon"
					+ " where EXTRACT( MONTH FROM C_DATE ) IN ( "+ m +" ) and "
					+ " EXTRACT( YEAR FROM C_DATE ) IN ( "+ y +" ) order by c_id DESC ";
			emp.runSql(sql);
			jt.setModel(emp);
		}
	}
	
    class ItemRenderer extends BasicComboBoxRenderer{
        public Component getListCellRendererComponent(
            JList list, Object value, int index,
            boolean isSelected, boolean cellHasFocus){
            super.getListCellRendererComponent(list, value, index,
                isSelected, cellHasFocus);
 
            if (value != null){
                Item item = (Item)value;
                setText( item.getDescription().toUpperCase() );
            }
 
            if (index == -1){
                Item item = (Item)value;
                setText( "" + item.getId());
            }
            return this;
        }
    }
 
    class Item{
        private String id;
        private String description;
 
        public Item(String id, String description){
            this.id = id;
            this.description = description;
        }
 
        public String getId(){
            return id;
        }
 
        public String getDescription(){
            return description;
        }
 
        public String toString(){
            return description;
        }
    }
}
