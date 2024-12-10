package managerView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import managerView.MaterialShopPanel.Item;
import model.EmpModel;
import tools.myColor;
import tools.myFont;

public class MaterialTranPanel extends JPanel implements ActionListener, MouseListener{
	
	String cat;
	JFrame parent;
	
	JPanel m,p1,p2,p3;
	JLabel l1;
	JTextField tf1,tf2;
	JButton go;
	JComboBox<Item> jb;
	
	JTable jtable;
	DefaultTableModel jj;
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
	
	public MaterialTranPanel(int locale) {
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
			sql2 = "select shop_id, shop_name from fyp_shop order by length(shop_id), shop_id ASC ";
		}else {
			sql2 = "select shop_id, shop_name_zh from fyp_shop order by length(shop_id), shop_id ASC ";
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
		
		String sql = "select * from fyp_materialTran order by t_id DESC ";
		emp.runSql(sql);
		
		jj = new DefaultTableModel();
		jj.addColumn("ID");
		jj.addColumn("Shop");
		jj.addColumn("Company");
		jj.addColumn("Date");
		jj.addColumn("Flag");
		jj.addColumn("Buy");
		jj.addColumn("Cancel");
		for(int i=0;i<emp.getRowCount();i++) {
			jj.addRow(new Object[]{emp.getValueAt(i, 0).toString(),emp.getValueAt(i, 1).toString(),
					emp.getValueAt(i, 2).toString(),emp.getValueAt(i, 3).toString(),
					emp.getValueAt(i, 4).toString(),rb.getString("mt_confirm"),rb.getString("mt_cancel")});
		}
		
		jtable = new JTable(jj){
			@Override
		    public Component prepareRenderer (TableCellRenderer renderer, int rowIndex, int columnIndex){
		        Component componenet = super.prepareRenderer(renderer, rowIndex, columnIndex);
		        
		        Object value = getModel().getValueAt(rowIndex,columnIndex);
		        
		        if(columnIndex == 4){
		        	if(value.equals("1")) {
		        		componenet.setBackground(myColor.red);
			            componenet.setForeground(Color.BLACK);
		        	}else {
		        		componenet.setBackground(myColor.green);
			            componenet.setForeground(Color.BLACK);
		        	}
		        }else if(columnIndex == 5){
		            componenet.setBackground(myColor.blue);
		            componenet.setForeground(Color.BLACK);
		        }else if(columnIndex == 6){
		            componenet.setBackground(myColor.orange);
		            componenet.setForeground(Color.BLACK);
		        } else {
		        	componenet.setBackground(Color.WHITE);
		            componenet.setForeground(Color.BLACK);
		        }
		        return componenet;
		    }
		};
		jtable.setAutoCreateRowSorter(true);
		jtable.setRowHeight(30);
		jtable.setFont(myFont.f1);
		jtable.addMouseListener(this);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		jtable.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
		jtable.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
		
		jsp = new JScrollPane(jtable);
		p2.add(jsp,"Center");
		
		m.add(p1,"North");
		m.add(p2,"Center");
		this.setLayout(new BorderLayout());
		this.add(m,"Center");
		this.setVisible(true);
	}

	public void reFresh() {
		EmpModel emp = new EmpModel();
		String sql = "select * from fyp_materialTran order by t_id DESC ";
		emp.runSql(sql);
		jj.setRowCount(0);
		for(int i=0;i<emp.getRowCount();i++) {
			jj.addRow(new Object[]{emp.getValueAt(i, 0).toString(),emp.getValueAt(i, 1).toString(),
					emp.getValueAt(i, 2).toString(),emp.getValueAt(i, 3).toString(),
					emp.getValueAt(i, 4).toString(),rb.getString("mt_confirm"),rb.getString("mt_cancel")});
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == b) {
			this.setVisible(false);
		}else if(e.getSource() == jb) {
			Item item = (Item)jb.getSelectedItem();
			String getSid = item.getId();
			EmpModel emp = new EmpModel();
			String sql = "select * from fyp_materialTran where t_sid='"+getSid+"' order by t_id DESC ";
			emp.runSql(sql);
			jj.setRowCount(0);
			for(int i=0;i<emp.getRowCount();i++) {
				jj.addRow(new Object[]{emp.getValueAt(i, 0).toString(),emp.getValueAt(i, 1).toString(),
						emp.getValueAt(i, 2).toString(),emp.getValueAt(i, 3).toString(),
						emp.getValueAt(i, 4).toString(),rb.getString("mt_confirm"),rb.getString("mt_cancel")});
			}
		}else if(e.getSource() == go) {
			String getID = tf1.getText();
			EmpModel emp = new EmpModel();
			String sql = "select * from fyp_materialTran "
					+ "where lower(t_cid) like lower('%"+getID+"%') order by t_id DESC ";
			emp.runSql(sql);
			jj.setRowCount(0);
			for(int i=0;i<emp.getRowCount();i++) {
				jj.addRow(new Object[]{emp.getValueAt(i, 0).toString(),emp.getValueAt(i, 1).toString(),
						emp.getValueAt(i, 2).toString(),emp.getValueAt(i, 3).toString(),
						emp.getValueAt(i, 4).toString(),rb.getString("mt_confirm"),rb.getString("mt_cancel")});
			}
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int col = jtable.columnAtPoint(e.getPoint());
		if(col==5) {
			int reply = JOptionPane.showConfirmDialog(null,rb.getString("mess_confirm"),rb.getString("mess_confirmTitle"),JOptionPane.YES_NO_OPTION);
	        if (reply == JOptionPane.YES_OPTION) {
	        	int id = Integer.parseInt(jtable.getValueAt(jtable.getSelectedRow(), 0).toString());
				EmpModel emp = new EmpModel();
				String sql = "update fyp_materialTran set t_flag=? where t_id=? ";
				String[] paras = {"2",id+""};
				emp.updInfo(sql, paras);
				
				String sid = jtable.getValueAt(jtable.getSelectedRow(), 1).toString();
				String cid = jtable.getValueAt(jtable.getSelectedRow(), 2).toString();
				
				sql = "select c_money , c_add from fyp_materialcom where c_id='"+cid+"' ";
				emp.runSql(sql);
				Float money = Float.parseFloat(emp.getValueAt(0, 0).toString());
				Float addNo = Float.parseFloat(emp.getValueAt(0, 1).toString());
				
				sql = "select m_id from fyp_material where m_c='"+cid+"' ";
				emp.runSql(sql);
				int mid = Integer.parseInt(emp.getValueAt(0, 0).toString());
				
				sql = "select m_l from fyp_materialshop where m_id='"+mid+"' and m_sid='"+sid+"' ";
				emp.runSql(sql);
				Float nowV = Float.parseFloat(emp.getValueAt(0, 0).toString());
				
				BigDecimal a = new BigDecimal(nowV); 
				BigDecimal b = new BigDecimal(addNo); 
				BigDecimal sum = a.add(b);
				
				sql = "update fyp_materialShop set m_l=? where m_id=? and m_sid=? ";
				String[] paras2 = {sum+"",mid+"",sid};
				emp.updInfo(sql, paras2);
				
				sql = "insert into fyp_expenses values (matexp_increase.nextval,TO_DATE(SYSDATE),?,?,?,?,?)";
				String[] paras3 = {sid,id+"","Buy material from "+cid,"物資購買 : "+cid,money+""};
				emp.updInfo(sql, paras3);
				
				reFresh();
	        }
		}else if(col==6) {
			int reply = JOptionPane.showConfirmDialog(null,rb.getString("mess_confirm"),rb.getString("mess_confirmTitle"),JOptionPane.YES_NO_OPTION);
	        if (reply == JOptionPane.YES_OPTION) {
	        	int flag = Integer.parseInt(jtable.getValueAt(jtable.getSelectedRow(), 4).toString());
	        	if(flag==2) {
	        		JLabel w3 = new JLabel(rb.getString("w_wSelect"));
	        		w3.setFont(myFont.f1);
	        		JOptionPane.showMessageDialog(
	        			this,w3,rb.getString("w_w"),JOptionPane.ERROR_MESSAGE);
	        	}else {
	        		int id = Integer.parseInt(jtable.getValueAt(jtable.getSelectedRow(), 0).toString());
	        		String del = "delete from fyp_materialTran where t_id=? ";
		        	EmpModel emp = new EmpModel();
		        	String[] paras = {id+""};
		        	emp.updInfo(del, paras);
	        	}
	        }
			reFresh();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
	
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
}
