package managerView;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Frame;
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

import managerView.FoodJPanel.Item;
import model.EmpModel;
import tools.myFont;

public class ShopPanel extends JPanel implements ActionListener{

	JFrame parent;
	
	JPanel p1,p2;
	JComboBox<Item> jb;
	JLabel l1;
	JTextField tf1;
	JButton go;
	
	JTable jt;
	JScrollPane jsp;
	
	JButton add,up,table,del;
	
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
	
	public ShopPanel(int locale) {
		localeCurrent = locale;
		initLocale();
		
		p1 = new JPanel(new FlowLayout());
		
		Vector<Item> model = new Vector<Item>();
		if(localeCurrent==0) {
			model.addElement(new Item("k","Kolwoon"));
			model.addElement(new Item("h","Hong Kong Island"));
			model.addElement(new Item("n","New Territories"));
		}else {
			model.addElement(new Item("k","九龍"));
			model.addElement(new Item("h","香港島"));
			model.addElement(new Item("n","新界"));
		}
		jb = new JComboBox<Item>(model);
		jb.setFont(myFont.f1);
		jb.setSelectedIndex(0);
		jb.addActionListener(this);
		
		l1 = new JLabel("  "+rb.getString("common_search"));
		l1.setFont(myFont.f1);
		
		tf1 = new JTextField(20);
		tf1.setFont(myFont.f1);
		
		go = new JButton(rb.getString("common_search"));
		go.setFont(myFont.f1);
		go.addActionListener(this);
		
		p1.add(jb);
		p1.add(l1);
		p1.add(tf1);
		p1.add(go);
		
		EmpModel emp = new EmpModel();
		String sql = "select shop_id,shop_name,shop_name_zh,shop_location,shop_capacity,shop_tel,"
				+ "shop_flag from fyp_shop order by length(shop_id), shop_id ASC ";
		emp.runSql(sql);
		jt = new JTable(emp);
		jt.setAutoCreateRowSorter(true);
		jt.setRowHeight(30);
		jt.setFont(myFont.f1);
		setTable();
		jsp = new JScrollPane(jt);
		
		p2 = new JPanel(new FlowLayout());
		
		add = new JButton(rb.getString("fj_add"));
		up = new JButton(rb.getString("fj_up"));
		table = new JButton(rb.getString("sp_table"));
		del = new JButton(rb.getString("fj_del"));
		add.setFont(myFont.f1);
		up.setFont(myFont.f1);
		table.setFont(myFont.f1);
		del.setFont(myFont.f1);
		add.addActionListener(this);
		up.addActionListener(this);
		table.addActionListener(this);
		del.addActionListener(this);
		
		p2.add(add);
		p2.add(up);
		p2.add(table);
		p2.add(del);
		
		this.setLayout(new BorderLayout());
		this.add(p1,"North");
		this.add(jsp,"Center");
		this.add(p2,"South");
	}
	
	public void setTable() {
		jt.getColumnModel().getColumn(0).setPreferredWidth(50);
		jt.getColumnModel().getColumn(1).setPreferredWidth(500);
		jt.getColumnModel().getColumn(2).setPreferredWidth(400);
		jt.getColumnModel().getColumn(3).setPreferredWidth(50);
		jt.getColumnModel().getColumn(4).setPreferredWidth(50);
		jt.getColumnModel().getColumn(5).setPreferredWidth(100);
		jt.getColumnModel().getColumn(5).setPreferredWidth(50);
	}
	
	public void reFresh() {
		EmpModel emp = new EmpModel();
		String sql = "select shop_id,shop_name,shop_name_zh,shop_location,shop_capacity,shop_tel,"
				+ "shop_flag from fyp_shop order by length(shop_id), shop_id ASC ";
		emp.runSql(sql);
		jt.setModel(emp);
		setTable();
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == add) {
			new ShopDetailDialog(parent,rb.getString("shopDetail_title")+" "+rb.getString("fj_add"),
					true,localeCurrent,1,"0");
			reFresh();
		}else if(e.getSource() == up) {
			try {
				String sid = jt.getValueAt(jt.getSelectedRow(), 0).toString();
				new ShopDetailDialog(parent,rb.getString("shopDetail_title")+" "+rb.getString("fj_up"),
						true,localeCurrent,2,sid);
				reFresh();
			}catch (Exception e2) {}
		}else if(e.getSource() == table) {
			try {
				String sid = jt.getValueAt(jt.getSelectedRow(), 0).toString();
				new ShopTableDialog(parent,rb.getString("shopTableDialog_title"),true,localeCurrent,sid);
				reFresh();
			}catch (Exception e2) {}
		}else if(e.getSource() == del) {
			String sid = jt.getValueAt(jt.getSelectedRow(), 0).toString();
			int reply = JOptionPane.showConfirmDialog(null,rb.getString("mess_confirm"),rb.getString("mess_confirmTitle"),JOptionPane.YES_NO_OPTION);
	        if (reply == JOptionPane.YES_OPTION) {
	        	String del = "delete from fyp_shop where shop_id=? ";
	        	EmpModel emp = new EmpModel();
	        	String[] paras = {sid};
	        	emp.updInfo(del, paras);
	        }
			reFresh();
		}else if(e.getSource() == jb) {
			Item item = (Item)jb.getSelectedItem();
			String getloc = item.getId();
			EmpModel emp = new EmpModel();
			String sql = "select shop_id,shop_name,shop_name_zh,shop_location,shop_capacity,shop_tel,"
					+ "shop_flag from fyp_shop where shop_location = '"+getloc+"' "
					+ "order by length(shop_id), shop_id ASC ";
			emp.runSql(sql);
			jt.setModel(emp);
			setTable();
		}else if(e.getSource() == go) {
			String input = tf1.getText();
			String sql = "select shop_id,shop_name,shop_name_zh,shop_location,shop_capacity,shop_tel,"
				+ " shop_flag from fyp_shop where "
				+ "(lower(shop_name) like lower('%"+input+"%') or "
				+ "lower(shop_name_zh) like lower('%"+input+"%')) "
				+ "order by length(shop_id), shop_id ASC ";
			EmpModel emp = new EmpModel();
			emp.runSql(sql);
			jt.setModel(emp);
			setTable();
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
