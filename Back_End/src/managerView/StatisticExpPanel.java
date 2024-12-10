package managerView;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
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
import tools.myBorder;
import tools.myFont;

public class StatisticExpPanel extends JPanel implements ActionListener{
	JFrame parent;
	JPanel m,p1,p11,p2;
	JLabel search;
	JTextField sf;
	JButton b,sgo, add,up,del;
	
	JTable jt;
	JScrollPane jsp;
	
	JComboBox<Item> type,from;
	JComboBox<String> flag,month,year;
	JButton dgo,reset;
	
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
	
	public StatisticExpPanel(int locale) {
		localeCurrent = locale;
		initLocale();
		
		p1 = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		b = new JButton(rb.getString("common_exit"));
		b.setFont(myFont.f1);
		b.addActionListener(this);
		
		search = new JLabel(rb.getString("common_sid"),JLabel.RIGHT);
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
		p1.add(search);
		gbc.gridx = 2;
		p1.add(sf);
		gbc.gridx = 3;
		p1.add(sgo);
		
		p11 = new JPanel(new GridBagLayout());
		
		EmpModel emp = new EmpModel();
		String sql2 = "";
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
		p11.add(from,gbc);
		gbc.gridx = 1;
		p11.add(jj,gbc);
		gbc.gridx = 2;
		p11.add(reset,gbc);
		
		m = new JPanel(new GridBagLayout());
		gbc.gridx = 0;
		gbc.gridy = 0;
		m.add(p1,gbc);
		gbc.gridy = 1;
		m.add(p11,gbc);
		
		String sql = "select * from fyp_expenses order by e_id DESC ";
		emp.runSql(sql);
		jt = new JTable(emp);
		jt.setAutoCreateRowSorter(true);
		jt.setRowHeight(30);
		jt.setFont(myFont.f1);
		jsp = new JScrollPane(jt);
		setTable();
		
		p2 = new JPanel();
		
		add = new JButton(rb.getString("fj_add"));
		up = new JButton(rb.getString("fj_up"));
		del = new JButton(rb.getString("fj_del"));
		add.setFont(myFont.f1);
		up.setFont(myFont.f1);
		del.setFont(myFont.f1);
		add.addActionListener(this);
		up.addActionListener(this);
		del.addActionListener(this);
		
		p2.add(add);
		p2.add(up);
		p2.add(del);
		
		this.setLayout(new BorderLayout());
		this.add(m,"North");
		this.add(jsp,"Center");
		this.add(p2,"South");
	}
	
	public void update() {
		EmpModel emp = new EmpModel();
		String sql = "select * from fyp_expenses order by e_id DESC ";
		emp.runSql(sql);
		jt.setModel(emp);
		setTable();
	}
	
	public void setTable() {
		jt.getColumnModel().getColumn(0).setPreferredWidth(20);
		jt.getColumnModel().getColumn(1).setPreferredWidth(80);
		jt.getColumnModel().getColumn(2).setPreferredWidth(50);
		jt.getColumnModel().getColumn(3).setPreferredWidth(50);
		jt.getColumnModel().getColumn(4).setPreferredWidth(500);
		jt.getColumnModel().getColumn(5).setPreferredWidth(300);
		jt.getColumnModel().getColumn(6).setPreferredWidth(100);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == b) {
			this.setVisible(false);
		}else if(e.getSource() == sgo) {
			String input = sf.getText();
			String sql = "select * from fyp_expenses where "
					   + "(lower(e_d) like lower('%"+input+"%') OR lower(e_dzh) like lower('%"+input+"%')) "
					   + "order by e_id DESC ";
			EmpModel emp = new EmpModel();
			emp.runSql(sql);
			jt.setModel(emp);
			setTable();
		}else if(e.getSource() == from) {
			Item item = (Item)from.getSelectedItem();
			String getFrom = item.getId();
			String sql = "select * from fyp_expenses where e_sid='"+getFrom+"' "
					   + "order by e_id DESC ";
			EmpModel emp = new EmpModel();
			emp.runSql(sql);
			jt.setModel(emp);
			setTable();
		}else if(e.getSource() == dgo) {
			String m = month.getSelectedItem().toString();
			String y = year.getSelectedItem().toString();
			String sql = "select * from fyp_expenses "
					+ " where EXTRACT( MONTH FROM E_DATE ) IN ( "+ m +" ) and "
					+ " EXTRACT( YEAR FROM E_DATE ) IN ( "+ y +" ) order by e_id DESC ";
			EmpModel emp = new EmpModel();
			emp.runSql(sql);
			jt.setModel(emp);
			setTable();
		}else if(e.getSource() == reset) {
			update();
		}else if(e.getSource() == add) {
			new StatisticExpDetailDialog(parent,rb.getString("statistic_title"),true,localeCurrent,1,"0");
			update();
		}else if(e.getSource() == up) {
			String eid = jt.getValueAt(jt.getSelectedRow(), 0).toString();
			new StatisticExpDetailDialog(parent,rb.getString("statistic_title"),true,localeCurrent,2,eid);
			update();
		}else if(e.getSource() == del) {
			try {
				String uid = jt.getValueAt(jt.getSelectedRow(), 0).toString();
				int reply = JOptionPane.showConfirmDialog(null,rb.getString("mess_confirm"),rb.getString("mess_confirmTitle"),JOptionPane.YES_NO_OPTION);
		        if (reply == JOptionPane.YES_OPTION) {
		        	String del = "delete from fyp_expenses where e_id=? ";
		        	EmpModel emp = new EmpModel();
		        	String[] paras = {uid};
		        	emp.updInfo(del, paras);
		        }
			}catch (Exception e2) {}
			update();
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
