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

import managerView.FoodJPanel.Item;
import model.EmpModel;
import tools.myFont;

public class ComboPanel extends JPanel implements ActionListener{
	
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
	JButton add,up,del;
	
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
	
	public ComboPanel(int locale) {
		localeCurrent = locale;
		initLocale();
		
		m = new JPanel(new BorderLayout());
		
		p1 = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		b = new JButton(rb.getString("common_exit"));
		b.setFont(myFont.f1);
		b.addActionListener(this);
		b.setFont(myFont.f1);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		p1.add(b,gbc);
		
		EmpModel emp = new EmpModel();
		
		Vector<Item> model = new Vector<Item>();
		
		model.addElement(new Item("com",rb.getString("combo_com")));
		model.addElement(new Item("new",rb.getString("combo_new")));

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
		
		String sql = "select c_id, c_name, c_name_zh, c_price, c_flag "
				+ "from fyp_combo order by length(c_id), c_id ASC ";
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
		add.setFont(myFont.f1);
		up.setFont(myFont.f1);
		del.setFont(myFont.f1);
		add.addActionListener(this);
		up.addActionListener(this);
		del.addActionListener(this);
		n2.add(add);
		n2.add(up);
		n2.add(del);
		
		m.add(p1,"North");
		m.add(p2,"Center");
		m.add(n2,"South");
		this.setLayout(new BorderLayout());
		this.add(m,"Center");
		this.setVisible(true);
	}
	
	public void setTable(){
		jtable.getColumnModel().getColumn(0).setPreferredWidth(50);
		jtable.getColumnModel().getColumn(1).setPreferredWidth(600);
		jtable.getColumnModel().getColumn(2).setPreferredWidth(400);
		jtable.getColumnModel().getColumn(3).setPreferredWidth(50);
		jtable.getColumnModel().getColumn(4).setPreferredWidth(50);
	}
	
	public void reFresh() {
		EmpModel emp = new EmpModel();
		String sql = "select c_id, c_name, c_name_zh, c_price, c_flag "
				+ "from fyp_combo order by length(c_id), c_id ASC ";
		emp.runSql(sql);
		jtable.setModel(emp);
		setTable();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == b) {
			this.setVisible(false);
		}else if(e.getSource() == jb) {
			Item item = (Item)jb.getSelectedItem();
			String getCat = item.getId();
			cat = getCat;
			String sql = "select c_id, c_name, c_name_zh, c_price, c_flag "
					+ "from fyp_combo where c_cat = '"+getCat+"' order by length(c_id), c_id ASC ";
			EmpModel emp = new EmpModel();
			emp.runSql(sql);
			jtable.setModel(emp);
			setTable();
		}else if(e.getSource() == go) {
			String input = tf1.getText();
			String sql = "select c_id, c_name, c_name_zh, c_price, c_flag "
				+ " from fyp_combo where "
				+ "(lower(c_name) like lower('%"+input+"%') or "
				+ "lower(c_name_zh) like lower('%"+input+"%')) "
				+ "order by length(c_id), c_id ASC ";
			EmpModel emp = new EmpModel();
			emp.runSql(sql);
			jtable.setModel(emp);
			setTable();
		}else if(e.getSource() == add) {
			new ComboDetailDialog(parent,rb.getString("combo_Detail_title")+" "+rb.getString("fj_add"),true,localeCurrent,1,"0");
			reFresh();
		}else if(e.getSource() == up) {
			try {
				String cid = jtable.getValueAt(jtable.getSelectedRow(), 0).toString();
				new ComboDetailDialog(parent,rb.getString("combo_Detail_title")+" "+cid,true,localeCurrent,2,cid);
				reFresh();
			}catch (Exception e2) {
				// TODO: handle exception
			}
		}else if(e.getSource() == del) {
			String cid = jtable.getValueAt(jtable.getSelectedRow(), 0).toString();
			int reply = JOptionPane.showConfirmDialog(null,rb.getString("mess_confirm"),rb.getString("mess_confirmTitle"),JOptionPane.YES_NO_OPTION);
	        if (reply == JOptionPane.YES_OPTION) {
	        	String del = "delete from fyp_combo where c_id=? ";
	        	EmpModel emp = new EmpModel();
	        	String[] paras = {cid};
	        	emp.updInfo(del, paras);
	        }
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
