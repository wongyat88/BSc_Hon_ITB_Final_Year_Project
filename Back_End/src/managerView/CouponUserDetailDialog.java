package managerView;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import managerView.FoodDetailDialog.Item;
import model.EmpModel;
import tools.myFont;

public class CouponUserDetailDialog extends JDialog implements ActionListener{
	Image titleIcon;
	
	int cid = 0;
	
	JPanel m,p1,p2,p3;
	JLabel id,flag,uid,from,date,t;
	JTextField idf,uidf,datef;
	JComboBox<Item> fromf,tf;
	JComboBox<String> flagf;
	JButton exit,add,up;
	
	Vector<Item> model,model2;
	
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
	
	public CouponUserDetailDialog(Frame owner,String title,boolean modal,int locale,int f,int couponid){
		super(owner,title,modal);
		localeCurrent = locale;
		cid = couponid;
		initLocale();
		
		m = new JPanel(new GridBagLayout());
		p1 = new JPanel(new FlowLayout());
		p2 = new JPanel(new FlowLayout());
		p3 = new JPanel(new FlowLayout());
		
		id = new JLabel("  "+rb.getString("shop_id"));
		id.setFont(myFont.f1);
		idf = new JTextField(10);
		idf.setEnabled(false);
		idf.setFont(myFont.f1);
		
		flag = new JLabel("  "+rb.getString("shop_f"));
		flag.setFont(myFont.f1);
		String[] StatusStr = { rb.getString("cu_notuse"),rb.getString("cu_used"),rb.getString("cu_del")};
		flagf = new JComboBox<String>(StatusStr);
		flagf.setFont(myFont.f1);
		flagf.setSelectedIndex(0);
		
		p1.add(id);p1.add(idf);
		p1.add(flag);p1.add(flagf);
		
		uid = new JLabel(rb.getString("cu_uid"));
		uid.setFont(myFont.f1);
		uidf = new JTextField(10);
		uidf.setFont(myFont.f1);
		
		from = new JLabel(rb.getString("cu_from"));
		from.setFont(myFont.f1);
		EmpModel emp = new EmpModel();
		String sql2 = "";
		if(localeCurrent==0) {
			sql2 = "select shop_id, shop_name from fyp_shop order by length(shop_id), shop_id ";
		}else {
			sql2 = "select shop_id, shop_name_zh from fyp_shop order by length(shop_id), shop_id ";
		}
		emp.runSql(sql2);
		model2 = new Vector<Item>();
		model2.addElement(new Item("net", rb.getString("cu_net")));
		for(int i=0;i<emp.getRowCount();i++) {
			model2.addElement(new Item(emp.getValueAt(i, 0).toString(), emp.getValueAt(i, 1).toString()));
		}
		fromf = new JComboBox<Item>(model2);
		fromf.setFont(myFont.f1);
		fromf.setSelectedIndex(0);
		
		p2.add(uid);p2.add(uidf);
		p2.add(from);p2.add(fromf);
		
		date = new JLabel(rb.getString("cu_date"));
		date.setFont(myFont.f1);
		datef = new JTextField(20);
		datef.setEditable(false);
		datef.setFont(myFont.f1);
		
		t = new JLabel(rb.getString("cu_type"));
		t.setFont(myFont.f1);
		if(localeCurrent==0) {
			sql2 = "select c_type, c_n from fyp_coupon order by length(c_type),c_type ";
		}else {
			sql2 = "select c_type, c_nzh from fyp_coupon order by length(c_type),c_type ";
		}
		emp.runSql(sql2);
		model = new Vector<Item>();
		for(int i=0;i<emp.getRowCount();i++) {
			model.addElement(new Item(emp.getValueAt(i, 0).toString(), emp.getValueAt(i, 1).toString()));
		}
		tf = new JComboBox<Item>(model);
		tf.setFont(myFont.f1);
		tf.setSelectedIndex(0);
		
		p3.add(t);p3.add(tf);
		p3.add(date);p3.add(datef);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		m.add(p1,gbc);
		gbc.gridy = 1;
		m.add(p2,gbc);
		gbc.gridy = 2;
		m.add(p3,gbc);
		
		add = new JButton(rb.getString("fj_add"));
		add.setFont(myFont.f1);
		add.addActionListener(this);
		up = new JButton(rb.getString("fj_up"));
		up.setFont(myFont.f1);
		up.addActionListener(this);
		
		this.add(m,"Center");
		if(f==1) {
			this.add(add,"South");
		}else {
			update();
			this.add(up,"South");
		}
		
		try {
			titleIcon = ImageIO.read(new File("images/logo/logo.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int w = Toolkit.getDefaultToolkit().getScreenSize().width;
		int h = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setIconImage(titleIcon);
		this.setLocation(w/2-500, h/2-150);
		this.setSize(1000, 300);
		this.setVisible(true);
	}
	
	public void update() {
		EmpModel emp = new EmpModel();
		String sql = "select * from fyp_userCoupon where c_id = '"+cid+"' order by length(c_type),c_type ";
		emp.runSql(sql);
		idf.setText(emp.getValueAt(0, 0).toString());
		flagf.setSelectedIndex(Integer.parseInt(emp.getValueAt(0, 5).toString())-1);
		uidf.setText(emp.getValueAt(0, 2).toString());
		datef.setText(emp.getValueAt(0, 3).toString());
		String otype = emp.getValueAt(0, 1).toString();
		String ofrom = emp.getValueAt(0, 4).toString();
		
		if(ofrom.equals("net")) {
			fromf.setSelectedIndex(0);
		}else {
			int nfrom = 0;
			String sql2 = "select shop_id from fyp_shop order by length(shop_id), shop_id";
			emp.runSql(sql2);
			for(int i=0;i<emp.getRowCount();i++) {
				if(ofrom.equals(emp.getValueAt(i, 0).toString())) {
					nfrom = i;
				}
			}
			fromf.setSelectedIndex(nfrom+1);
		}

		int nt = 0;
		String sql2 = "select c_type from fyp_coupon order by length(c_type),c_type ";
		emp.runSql(sql2);
		for(int i=0;i<emp.getRowCount();i++) {
			if(otype.equals(emp.getValueAt(i, 0).toString())) {
				nt = i;
			}
		}
		tf.setSelectedIndex(nt);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == add) {
			EmpModel emp = new EmpModel();
			String ss = "select ucid_increase.nextval from dual ";
			emp.runSql(ss);
			int id = Integer.parseInt(emp.getValueAt(0, 0).toString());
			String sql = "insert into fyp_usercoupon values "
					+ "(?,?,?,TO_DATE(SYSDATE),?,?) ";
			Item item = (Item)tf.getSelectedItem();
			String type = item.getId() + "";
			Item item2 = (Item)fromf.getSelectedItem();
			String ff = item2.getId() + "";
			String[] paras = {id+"",type,uidf.getText(),
					ff,(flagf.getSelectedIndex()+1)+""};
			emp.updInfo(sql, paras);
			
			int userID = 0;
			if(uidf.getText().equals("guest")) {
				userID = 0;
			}else {
				userID=Integer.parseInt(uidf.getText());
			}
			int staffid = ManagerMain.getUid();
			sql = "insert into fyp_couponlog values "
					+ "(clog_increase.nextval,?,?,TO_DATE(SYSDATE),?,?,?) ";
			String[] paras2 = {id+"",userID+"",ff,staffid+"",1+""};
			emp.updInfo(sql, paras2);
			this.dispose();
		}else if(e.getSource() == up) {
			EmpModel emp = new EmpModel();
			String sql = "update fyp_usercoupon set C_TYPE=?,C_UID=?,"
					+ "C_FROM=?,C_FLAG=? where C_ID=?";
			Item item = (Item)tf.getSelectedItem();
			String type = item.getId() + "";
			Item item2 = (Item)fromf.getSelectedItem();
			String ff = item2.getId() + "";
			String[] paras = {type,uidf.getText(),ff,
					(flagf.getSelectedIndex()+1)+"",idf.getText()};
			emp.updInfo(sql, paras);
			
			int userID = 0;
			if(uidf.getText().equals("guest")) {
				userID = 0;
			}else {
				userID=Integer.parseInt(uidf.getText());
			}
			int staffid = ManagerMain.getUid();
			sql = "insert into fyp_couponlog values "
					+ "(clog_increase.nextval,?,?,TO_DATE(SYSDATE),?,?,?) ";
			String[] paras2 = {cid+"",userID+"",ff,staffid+"",2+""};
			emp.updInfo(sql, paras2);
			this.dispose();
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
