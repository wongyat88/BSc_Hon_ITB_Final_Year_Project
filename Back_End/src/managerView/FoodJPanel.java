package managerView;

import java.awt.BorderLayout;
import java.awt.Component;
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

import model.EmpModel;
import tools.myFont;

public class FoodJPanel extends JPanel implements ActionListener{
	
	String cat;
	JFrame parent;
	
	JPanel m,p1,p2,p3;
	JLabel l1;
	JTextField tf1,tf2;
	JButton go;
	JComboBox<Item> jb;
	
	JTable jtable;
	JScrollPane jsp;
	
	JButton b;
	JButton add,up,del,price;
	
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
	
	public FoodJPanel(int locale) {
		localeCurrent = locale;
		initLocale();
		
		m = new JPanel(new BorderLayout());
		
		p1 = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		b = new JButton(rb.getString("common_exit"));
		b.setFont(myFont.f1);
		b.addActionListener(this);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		p1.add(b,gbc);
		
		EmpModel emp = new EmpModel();
		String sql2 = "";
		if(localeCurrent==0) {
			sql2 = "select cat_id, cat_name from fyp_cat where cat_flag=0 ";
		}else {
			sql2 = "select cat_id, cat_name_zh from fyp_cat where cat_flag=0 ";
		}
		emp.runSql(sql2);
		Vector<Item> model = new Vector<Item>();
		for(int i=0;i<emp.getRowCount();i++) {
			model.addElement(new Item(emp.getValueAt(i, 0).toString(), emp.getValueAt(i, 1).toString()));
		}
		jb = new JComboBox<Item>(model);
		jb.setFont(myFont.f1);
		jb.setSelectedIndex(0);
		jb.addActionListener(this);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridx = 1;
		gbc.gridy = 0;
		p1.add(jb,gbc);
		
		l1 = new JLabel(rb.getString("common_search")+" : ",JLabel.RIGHT);
		l1.setFont(myFont.f1);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridx = 2;
		gbc.gridy = 0;
		p1.add(l1,gbc);
		
		tf1 = new JTextField(20);
		tf1.setFont(myFont.f1);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridx = 3;
		gbc.gridy = 0;
		p1.add(tf1,gbc);
		
		go = new JButton(rb.getString("common_search"));
		go.setFont(myFont.f1);
		go.addActionListener(this);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridx = 4;
		gbc.gridy = 0;
		p1.add(go,gbc);
		
		p2 = new JPanel(new BorderLayout());
		
		String sql = "select food_id, food_size, food_name, food_name_zh, food_price, food_flag "
				+ "from fyp_food order by length(food_id), food_id ASC ";
		emp.runSql(sql);
		jtable = new JTable(emp);
		jtable.setAutoCreateRowSorter(true);
		jtable.setRowHeight(30);
		jtable.setFont(myFont.f1);
		setTable();
		jsp = new JScrollPane(jtable);
		p2.add(jsp,"Center");
		
		JPanel n2 = new JPanel();
		add = new JButton(rb.getString("fj_add"));
		up = new JButton(rb.getString("fj_up"));
		del = new JButton(rb.getString("fj_del"));
		price = new JButton(rb.getString("fj_price"));
		add.setFont(myFont.f1);
		up.setFont(myFont.f1);
		del.setFont(myFont.f1);
		price.setFont(myFont.f1);
		add.addActionListener(this);
		up.addActionListener(this);
		del.addActionListener(this);
		price.addActionListener(this);
		n2.add(add);
		n2.add(up);
		n2.add(del);
		n2.add(price);
		
		m.add(p1,"North");
		m.add(p2,"Center");
		m.add(n2,"South");
		this.setLayout(new BorderLayout());
		this.add(m,"Center");
		this.setVisible(true);
	}
	
	public void setTable(){
		jtable.getColumnModel().getColumn(0).setPreferredWidth(50);
		jtable.getColumnModel().getColumn(1).setPreferredWidth(50);
		jtable.getColumnModel().getColumn(2).setPreferredWidth(600);
		jtable.getColumnModel().getColumn(3).setPreferredWidth(200);
		jtable.getColumnModel().getColumn(4).setPreferredWidth(100);
		jtable.getColumnModel().getColumn(5).setPreferredWidth(50);
	}

	public void reFresh() {
		EmpModel emp = new EmpModel();
		String sql = "select food_id, food_size, food_name, food_name_zh, food_price, food_flag "
				+ "from fyp_food order by length(food_id), food_id ASC ";
		emp.runSql(sql);
		jtable.setModel(emp);
		setTable();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == b) {
			this.setVisible(false);
		}else if(e.getSource() == go) {
			String input = tf1.getText();
			String sql = "select food_id, food_size, food_name, food_name_zh, food_price, food_flag "
				+ " from fyp_food where "
				+ "(lower(food_name) like lower('%"+input+"%') or "
				+ "lower(food_name_zh) like lower('%"+input+"%')) "
				+ "order by length(food_id), food_id ASC ";
			EmpModel emp = new EmpModel();
			emp.runSql(sql);
			jtable.setModel(emp);
			setTable();
		}else if(e.getSource() == jb) {
			Item item = (Item)jb.getSelectedItem();
			String getCat = item.getId();
			cat = getCat;
			String sql = "select food_id, food_size, food_name, food_name_zh, food_price, food_flag "
					+ "from fyp_food where food_cat = '"+getCat+"' order by length(food_id), food_id ASC ";
			EmpModel emp = new EmpModel();
			emp.runSql(sql);
			jtable.setModel(emp);
			setTable();
		}else if(e.getSource() == add){
			new FoodDetailDialog(parent,rb.getString("menuDetail_title")+" "+rb.getString("fj_add"),true,localeCurrent,1,"0");
			reFresh();
		}else if(e.getSource() == up){
			String fid = "";
			try {
				fid = jtable.getValueAt(jtable.getSelectedRow(), 0).toString();
				new FoodDetailDialog(parent,rb.getString("menuDetail_title")+" "+rb.getString("fj_up"),true,localeCurrent,2,fid);
				reFresh();
			}catch (Exception e2) {
				// TODO: handle exception
			}
			
		}else if(e.getSource() == del){
			String fid = jtable.getValueAt(jtable.getSelectedRow(), 0).toString();
		    int reply = JOptionPane.showConfirmDialog(null,rb.getString("mess_confirm"),rb.getString("mess_confirmTitle"),JOptionPane.YES_NO_OPTION);
	        if (reply == JOptionPane.YES_OPTION) {
	        	String del = "delete from fyp_food where food_id=? ";
	        	EmpModel emp = new EmpModel();
	        	String[] paras = {fid};
	        	emp.updInfo(del, paras);
	        }
	        reFresh();
		}else if(e.getSource() == price) {
			new FoodSizePriceDialog(parent,rb.getString("sizePrice_title"),true,localeCurrent);
			reFresh();
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
