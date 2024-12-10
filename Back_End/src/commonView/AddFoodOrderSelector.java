package commonView;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.table.DefaultTableModel;

import model.EmpModel;
import tools.myFont;

public class AddFoodOrderSelector extends JDialog implements ActionListener, MouseListener {
	String ot = "";
	String sid = "";
	String oid = "";
	int flag = 0;
	int cflag = 0;
	
	JPanel m1,m2,p1;
	JButton food,combo,ok;
	JLabel l1,l2;
	JTable jt1,jt2;
	JScrollPane jsp1,jsp2;
	DefaultTableModel tm;
	JComboBox<Item> cat;
	
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
	
	public AddFoodOrderSelector(Frame owner,String title,boolean modal,int Flag,int locale,
			String shopid,String orderid) {
		super(owner,title,modal);
		localeCurrent = locale;
		sid = shopid;
		oid = orderid;
		flag = Flag;
		initLocale();
		
		EmpModel pSD = new EmpModel();
		String hgf = "select order_time from fyp_order "
				+ "where order_id='"+oid+"' and order_sid='"+sid+"' and order_flag='1' ";
		pSD.runSql(hgf);
		ot = pSD.getValueAt(0, 0).toString();
		
		mainPanel();
		
		try {
			titleIcon = ImageIO.read(new File("images/logo/logo.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setIconImage(titleIcon);
		int w = Toolkit.getDefaultToolkit().getScreenSize().width;
		int h = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation(w/2-800, h/2-400);
		this.setSize(1600, 800);
		this.setVisible(true);
	}
	
	public void mainPanel() {
		this.setLayout(new GridLayout(0,2));
		m1 = new JPanel(new BorderLayout());
		m2 = new JPanel(new BorderLayout());
		p1 = new JPanel();
		
		l1 = new JLabel(rb.getString("af_clickadd"), JLabel.CENTER);
		l1.setFont(myFont.f1);
		
		EmpModel emp = new EmpModel();
		String sql = "";
		if(localeCurrent==0) {
			sql = "select food_id, food_size, food_name, food_price from fyp_food where food_flag=1 "
					+ "order by length(food_id), food_id ASC ";
		}else {
			sql = "select food_id, food_size, food_name_zh, food_price from fyp_food where food_flag=1 "
					+ "order by length(food_id), food_id ASC ";
		}
		emp.runSql(sql);
		jt1 = new JTable(emp);
		jt1.setAutoCreateRowSorter(true);
		jt1.setRowHeight(30);
		jt1.setFont(myFont.f1);
		jt1.addMouseListener(this);
		setTable(1);
		jsp1 = new JScrollPane(jt1);
		
		food = new JButton(rb.getString("af_food"));
		food.setFont(myFont.f1);
		food.addActionListener(this);
		combo = new JButton(rb.getString("af_combo"));
		combo.setFont(myFont.f1);
		combo.addActionListener(this);
		EmpModel emp2 = new EmpModel();
		String sql2 = "";
		if(localeCurrent==0) {
			sql2 = "select cat_id, cat_name from fyp_cat where cat_flag=0 ";
		}else {
			sql2 = "select cat_id, cat_name_zh from fyp_cat where cat_flag=0 ";
		}
		emp2.runSql(sql2);
		Vector<Item> model = new Vector<Item>();
		for(int i=0;i<emp2.getRowCount();i++) {
			model.addElement(new Item(emp2.getValueAt(i, 0).toString(), emp2.getValueAt(i, 1).toString()));
		}
		cat = new JComboBox<Item>(model);
		cat.setFont(myFont.f1);
		cat.setSelectedIndex(0);
		cat.addActionListener(this);
		
		p1.add(food);p1.add(combo);p1.add(cat);
		m1.add(l1,"North");
		m1.add(jsp1,"Center");
		m1.add(p1,"South");
		this.add(m1);
		
		l2 = new JLabel(rb.getString("af_clickdel"), JLabel.CENTER);
		l2.setFont(myFont.f1);
		
		tm = new DefaultTableModel(){
		    @Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};
		tm.addColumn("c_id");
		tm.addColumn("c_show");
		tm.addColumn("c_change");
		tm.addColumn("f_id");
		tm.addColumn("Name");
		tm.addColumn("price");
		
		jt2 = new JTable(tm);
		jt2.setAutoCreateRowSorter(true);
		jt2.setRowHeight(30);
		jt2.setFont(myFont.f1);
		jt2.addMouseListener(this);
		setTable(2);
		jsp2 = new JScrollPane(jt2);
		
		ok = new JButton(rb.getString("common_ok"));
		ok.setFont(myFont.f1);
		ok.addActionListener(this);
		
		m2.add(l2,"North");
		m2.add(jsp2,"Center");
		m2.add(ok,"South");
		this.add(m2);
	}
	
	public void setTable(int f) {
		if(f==1) {
			jt1.getColumnModel().getColumn(0).setPreferredWidth(50);
			jt1.getColumnModel().getColumn(1).setPreferredWidth(50);
			jt1.getColumnModel().getColumn(2).setPreferredWidth(500);
			jt1.getColumnModel().getColumn(3).setPreferredWidth(100);
		}else if(f==2) {
			jt2.getColumnModel().getColumn(0).setPreferredWidth(100);
			jt2.getColumnModel().getColumn(1).setPreferredWidth(0);
			jt2.getColumnModel().getColumn(2).setPreferredWidth(0);
			jt2.getColumnModel().getColumn(3).setPreferredWidth(100);
			jt2.getColumnModel().getColumn(4).setPreferredWidth(500);
			jt2.getColumnModel().getColumn(5).setPreferredWidth(100);
		}else if(f==3) {
			jt1.getColumnModel().getColumn(0).setPreferredWidth(50);
			jt1.getColumnModel().getColumn(1).setPreferredWidth(500);
			jt1.getColumnModel().getColumn(2).setPreferredWidth(100);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == cat) {
			Item item = (Item)cat.getSelectedItem();
			String getCat = item.getId();
			if(cflag==0) {
				String sql = "";
				if(localeCurrent==0) {
					sql = "select food_id, food_size, food_name, food_price from fyp_food where "
							+ " food_cat = '"+getCat+"' and food_flag=1 "
							+ "order by length(food_id), food_id ASC ";
				}else {
					sql = "select food_id, food_size, food_name_zh, food_price from fyp_food where "
							+ " food_cat = '"+getCat+"' and food_flag=1 "
							+ "order by length(food_id), food_id ASC ";
				}
				EmpModel emp = new EmpModel();
				emp.runSql(sql);
				jt1.setModel(emp);
				setTable(1);
			}else {
				String sql = "";
				if(localeCurrent==0) {
					sql = "select c_id, c_name, c_price from fyp_combo where c_flag=1 and "
							+ " c_cat='"+getCat+"' order by length(c_id), c_id ASC ";
				}else {
					sql = "select c_id, c_name_zh, c_price from fyp_combo where c_flag=1 and "
							+ " c_cat='"+getCat+"' order by length(c_id), c_id ASC ";
				}
				EmpModel emp = new EmpModel();
				emp.runSql(sql);
				jt1.setModel(emp);
				setTable(3);
			}
		}else if(e.getSource() == food) {
			EmpModel emp = new EmpModel();
			String sql = "";
			if(localeCurrent==0) {
				sql = "select food_id, food_size, food_name, food_price from fyp_food where food_flag=1 "
						+ "order by length(food_id), food_id ASC ";
			}else {
				sql = "select food_id, food_size, food_name_zh, food_price from fyp_food where food_flag=1 "
						+ "order by length(food_id), food_id ASC ";
			}
			emp.runSql(sql);
			jt1.setModel(emp);
			setTable(1);
			EmpModel emp2 = new EmpModel();
			String sql2 = "";
			if(localeCurrent==0) {
				sql2 = "select cat_id, cat_name from fyp_cat where cat_flag=0 ";
			}else {
				sql2 = "select cat_id, cat_name_zh from fyp_cat where cat_flag=0 ";
			}
			emp2.runSql(sql2);
			Vector<Item> model = new Vector<Item>();
			for(int i=0;i<emp2.getRowCount();i++) {
				model.addElement(new Item(emp2.getValueAt(i, 0).toString(), emp2.getValueAt(i, 1).toString()));
			}
			cat.setModel(new DefaultComboBoxModel(model));
			cflag=0;
		}else if(e.getSource() == combo) {
			EmpModel emp = new EmpModel();
			String sql = "";
			if(localeCurrent==0) {
				sql = "select c_id, c_name, c_price from fyp_combo where c_flag=1 "
						+ "order by length(c_id), c_id ASC ";
			}else {
				sql = "select c_id, c_name_zh, c_price from fyp_combo where c_flag=1 "
						+ "order by length(c_id), c_id ASC ";
			}
			emp.runSql(sql);
			jt1.setModel(emp);
			setTable(3);
			Vector<Item> vv = new Vector<Item>();
			vv.addElement(new Item("com",rb.getString("combo_com")));
			vv.addElement(new Item("new",rb.getString("combo_new")));
			cat.setModel(new DefaultComboBoxModel(vv));
			cflag=1;
		}else if(e.getSource() == ok) {
			String area = "";
			if(flag==1) {
				area = "d";
			}else {
				area = "t";
			}
			for(int i=0;i<jt2.getRowCount();i++) {
				String c_id = jt2.getValueAt(i, 0).toString();
				Date dd = new Date();
			    SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			    String datetime = ft.format(dd);
				if(c_id.equals("na")) {
					EmpModel emp = new EmpModel();
					String sql = "insert into fyp_orderitem values "
							+ "(orderitem_increase.nextval,?,?,?,?,?,?,?,?,?,?,?,?) ";
					String[] paras = {oid,sid,datetime,"f","na","na",jt2.getValueAt(i, 3).toString(),area,
							jt2.getValueAt(i, 5).toString(),jt2.getValueAt(i, 1).toString(),"1",ot};
					emp.updInfo(sql, paras);
				}else {
					EmpModel emp = new EmpModel();
					String sql = "insert into fyp_orderitem values "
							+ "(orderitem_increase.nextval,?,?,?,?,?,?,?,?,?,?,?,?) ";
					String[] paras = {oid,sid,datetime,"c",jt2.getValueAt(i, 0).toString(),
							jt2.getValueAt(i, 2).toString(),jt2.getValueAt(i, 3).toString(),area,
							"0",jt2.getValueAt(i, 1).toString(),"1",ot};
					emp.updInfo(sql, paras);
				}
			}
			this.dispose();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == jt1) {
			if(cflag==0) {
				int fid = Integer.parseInt(jt1.getValueAt(jt1.getSelectedRow(), 0).toString());
				EmpModel emp = new EmpModel();
				String sql = "";
				if(localeCurrent==0) {
					sql = "select food_id, food_name, food_price from fyp_food where food_flag=1 "
						+ " and food_id = '"+fid+"' order by length(food_id), food_id ASC ";
				}else {
					sql = "select food_id, food_name_zh, food_price from fyp_food where food_flag=1 "
						+ " and food_id = '"+fid+"' order by length(food_id), food_id ASC ";
				}
				emp.runSql(sql);
				
				String sizesea = jt1.getValueAt(jt1.getSelectedRow(), 1).toString();
				
				for(int y=0;y<emp.getRowCount();y++) {
					float foodprice = Float.parseFloat(emp.getValueAt(y, 2).toString());
					if(sizesea.equals("o") || sizesea.equals("t") || sizesea.equals("h")) {
						new AddOrderWeightDialog(parent,rb.getString("addFood_title"),true,localeCurrent);
						BigDecimal fp = new BigDecimal(foodprice);
		    	        BigDecimal mul = new BigDecimal(AddOrderWeightDialog.getWeight());
		    	        BigDecimal newp = fp.multiply(mul);
		    	        DecimalFormat df = new DecimalFormat("#.#");
		    	        foodprice = Float.parseFloat(df.format(newp).toString());
					}
					Vector<String> vv = new Vector<String>();
					vv.addElement("na");
					vv.addElement("y");
					vv.addElement("na");
					vv.addElement(emp.getValueAt(y, 0).toString());
					vv.addElement(emp.getValueAt(y, 1).toString());
					vv.addElement(foodprice+"");
					tm.addRow(vv);
				}
			}else if(cflag==1) {
				String cid = jt1.getValueAt(jt1.getSelectedRow(), 0).toString();
				String cn = jt1.getValueAt(jt1.getSelectedRow(), 1).toString();
				String cp = jt1.getValueAt(jt1.getSelectedRow(), 2).toString();
				new AddFoodComboSelector(parent,rb.getString("addFood_title"),true,localeCurrent,cid);
				try {
					if(AddFoodComboSelector.getA().size()!=0) {
						EmpModel emp = new EmpModel();
						String sql = "select comboitem_increase.nextval from dual ";
						emp.runSql(sql);
						String cit = emp.getValueAt(0,0).toString();
						Vector<String> vv = new Vector<String>();
						vv.addElement(cid+"_"+cit);
						vv.addElement("n");
						vv.addElement("na");
						vv.addElement("na");
						vv.addElement(cn);
						vv.addElement(cp);
						tm.addRow(vv);
						for(int i=0;i<AddFoodComboSelector.getA().size();i++) {
							String log = AddFoodComboSelector.getA().get(i);
							String foodid = "";
							String change = "na";
							if(log.contains("@")) {
								String[] buff = log.split("@");
								foodid = buff[0];
								change = buff[1];
							}else {
								foodid = log;
							}
							
							if(localeCurrent==0) {
								sql = "select food_name from fyp_food where food_id = '"+foodid+"' ";
							}else {
								sql = "select food_name_zh from fyp_food where food_id = '"+foodid+"' ";
							}
							emp.runSql(sql);
							String foodname = emp.getValueAt(0, 0).toString();
							
							Vector<String> vvv = new Vector<String>();
							vvv.addElement(cid+"_"+cit);
							vvv.addElement("y");
							vvv.addElement(change);
							vvv.addElement(foodid);
							vvv.addElement(foodname);
							vvv.addElement("0");
							tm.addRow(vvv);
						}
					}
				}catch (Exception e2) {}
			}
		}else if(e.getSource() == jt2) {
			String g = jt2.getValueAt(jt2.getSelectedRow(), 0).toString();
			if(g.equals("na")) {
				((DefaultTableModel) jt2.getModel()).removeRow(jt2.getSelectedRow());
			}else {
				int reply = JOptionPane.showConfirmDialog(null,rb.getString("mess_confirm"),rb.getString("mess_confirmTitle"),JOptionPane.YES_NO_OPTION);
		        if (reply == JOptionPane.YES_OPTION) {
		        	for(int i=jt2.getRowCount()-1;i>=0;i--) {
		        		if(g.equals(jt2.getValueAt(i, 0).toString())) {
		        			((DefaultTableModel) jt2.getModel()).removeRow(i);
		        		}
		        	}
		        }
			}
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
