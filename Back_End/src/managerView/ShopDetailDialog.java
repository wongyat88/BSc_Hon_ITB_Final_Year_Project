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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import managerView.FoodDetailDialog.Item;
import model.EmpModel;
import tools.myFont;

public class ShopDetailDialog extends JDialog implements ActionListener{
	
	int flag = 0;
	String sid = "";
	
	Image titleIcon;
	
	JLabel id,loc,f,n,nzh,time,tel,cap,add,addzh,x,y;
	JTextField idf,nf,nzhf,timef,telf,capf,xf,yf;
	JTextArea addf,addzhf;
	JScrollPane js1,js2;
	
	JComboBox<Item> locf;
	JComboBox<String> ff;
	JButton go,up;
	
	JPanel m,p1,p2,p3,p4,p5;
	
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
	
	public ShopDetailDialog(Frame owner,String title,boolean modal,int locale,int fl,String shopid){
		super(owner,title,modal);
		localeCurrent = locale;
		flag = fl;
		sid = shopid;
		initLocale();
		
		m = new JPanel(new GridBagLayout());
		p1 = new JPanel(new FlowLayout());
		p2 = new JPanel(new FlowLayout());
		p3 = new JPanel(new FlowLayout());
		p4 = new JPanel(new FlowLayout());
		p5 = new JPanel(new FlowLayout());
		
		id = new JLabel(rb.getString("shop_id"));
		id.setFont(myFont.f1);
		idf = new JTextField(20);
		idf.setEnabled(false);
		idf.setFont(myFont.f1);
		
		loc = new JLabel("  "+rb.getString("shop_loc"));
		loc.setFont(myFont.f1);
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
		locf = new JComboBox<Item>(model);
		locf.setFont(myFont.f1);
		locf.setSelectedIndex(0);
		locf.addActionListener(this);
		
		f = new JLabel("  "+rb.getString("shop_f"));
		f.setFont(myFont.f1);
		String[] StatusStr = { rb.getString("md_choose"),rb.getString("md_av"),rb.getString("md_nav")};
		ff = new JComboBox<String>(StatusStr);
		ff.setFont(myFont.f1);
		ff.setSelectedIndex(0);
		ff.addActionListener(this);
		
		p1.add(id);p1.add(idf);
		p1.add(loc);p1.add(locf);
		p1.add(f);p1.add(ff);
		
		n = new JLabel(rb.getString("shop_n"));
		n.setFont(myFont.f1);
		nf = new JTextField(20);
		nf.setFont(myFont.f1);
		
		nzh = new JLabel("  "+rb.getString("shop_nzh"));
		nzh.setFont(myFont.f1);
		nzhf = new JTextField(20);
		nzhf.setFont(myFont.f1);
		
		p2.add(n);p2.add(nf);
		p2.add(nzh);p2.add(nzhf);
		
		time = new JLabel(rb.getString("shop_time"));
		time.setFont(myFont.f1);
		timef = new JTextField(10);
		timef.setFont(myFont.f1);
		
		tel = new JLabel("  "+rb.getString("shop_tel"));
		tel.setFont(myFont.f1);
		telf = new JTextField(10);
		telf.setFont(myFont.f1);
		
		cap = new JLabel("  "+rb.getString("shop_cap"));
		cap.setFont(myFont.f1);
		capf = new JTextField(10);
		capf.setText("0");
		capf.setEnabled(false);
		capf.setFont(myFont.f1);
		
		p3.add(time);p3.add(timef);
		p3.add(tel);p3.add(telf);
		p3.add(cap);p3.add(capf);
		
		add = new JLabel(rb.getString("shop_add"));
		add.setFont(myFont.f1);
		addf = new JTextArea(4,20);
		addf.setFont(myFont.f1);
		addf.setLineWrap(true);
		addf.setWrapStyleWord(true);
		js1 = new JScrollPane(addf);
		
		addzh = new JLabel("  "+rb.getString("shop_addzh"));
		addzh.setFont(myFont.f1);
		addzhf = new JTextArea(4,20);
		addzhf.setFont(myFont.f1);
		addzhf.setLineWrap(true);
		addzhf.setWrapStyleWord(true);
		js2 = new JScrollPane(addzhf);
		
		p4.add(add);p4.add(js1);
		p4.add(addzh);p4.add(js2);
		
		x = new JLabel(rb.getString("shop_x"));
		x.setFont(myFont.f1);
		xf = new JTextField(20);
		xf.setFont(myFont.f1);
		
		y = new JLabel("  "+rb.getString("shop_y"));
		y.setFont(myFont.f1);
		yf = new JTextField(20);
		yf.setFont(myFont.f1);
		
		p5.add(x);p5.add(xf);
		p5.add(y);p5.add(yf);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		m.add(p1,gbc);
		gbc.gridy = 1;
		m.add(p2,gbc);
		gbc.gridy = 2;
		m.add(p3,gbc);
		gbc.gridy = 3;
		m.add(p4,gbc);
		gbc.gridy = 4;
		m.add(p5,gbc);
		
		go = new JButton(rb.getString("common_ok"));
		go.setFont(myFont.f1);
		go.addActionListener(this);
		
		up = new JButton(rb.getString("fj_up"));
		up.setFont(myFont.f1);
		up.addActionListener(this);
		
		this.add(m,"North");
		if(flag==2) {
			updateData();
			this.add(up,"South");
		}else {
			this.add(go,"South");
		}
		
		try {
			titleIcon = ImageIO.read(new File("images/logo/logo.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int w = Toolkit.getDefaultToolkit().getScreenSize().width;
		int h = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setIconImage(titleIcon);
		this.setLocation(w/2-600, h/2-250);
		this.setSize(1200, 500);
		this.setVisible(true);
	}
	
	public void updateData() {
		EmpModel emp = new EmpModel();
		String sql = "select * from fyp_shop where shop_id = '"+sid+"' ";
		emp.runSql(sql);
		
		idf.setText(emp.getValueAt(0, 0).toString());
		nf.setText(emp.getValueAt(0, 1).toString());
		nzhf.setText(emp.getValueAt(0, 2).toString());
		if(emp.getValueAt(0, 3).toString().equals("k")) {
			locf.setSelectedIndex(0);
		}else if(emp.getValueAt(0, 3).toString().equals("h")) {
			locf.setSelectedIndex(1);
		}else {
			locf.setSelectedIndex(2);
		}
		addf.setText(emp.getValueAt(0, 4).toString());
		addzhf.setText(emp.getValueAt(0, 5).toString());
		capf.setText(emp.getValueAt(0, 6).toString());
		xf.setText(emp.getValueAt(0, 7).toString());
		yf.setText(emp.getValueAt(0, 8).toString());
		telf.setText(emp.getValueAt(0, 9).toString());
		timef.setText(emp.getValueAt(0, 10).toString());
		ff.setSelectedIndex(Integer.parseInt(emp.getValueAt(0, 11).toString()));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == go) {
			if(ff.getSelectedIndex()!=0) {
				EmpModel emp = new EmpModel();
				String sql = "insert into fyp_shop values ('shop' || to_char(shopid_increase.nextval),"
						+ "?,?,?,?,?,?,?,?,?,?,?) ";
				Item item = (Item)locf.getSelectedItem();
				String nloc = item.getId() + "";
				String nff = ff.getSelectedIndex() + "";
				String[] paras = {nf.getText(),nzhf.getText(),nloc,addf.getText(),addzhf.getText(),
						capf.getText(),xf.getText(),yf.getText(),telf.getText(),timef.getText(),nff};
				emp.updInfo(sql, paras);
				this.dispose();
			}else {
				JLabel w3 = new JLabel(rb.getString("w_wSelect"));
				w3.setFont(myFont.f1);
				JOptionPane.showMessageDialog(
					this,w3,rb.getString("w_w"),JOptionPane.ERROR_MESSAGE);
			}
		}else if(e.getSource() == up) {
			if(ff.getSelectedIndex()!=0) {
				EmpModel emp = new EmpModel();
				String sql = "update fyp_shop set shop_name=?,shop_name_zh=?,shop_location=?, "
						+ "shop_address=?,shop_address_zh=?,shop_capacity=?,shop_x=?,shop_y=?,"
						+ "shop_tel=?,shop_time=?,shop_flag=? where shop_id=? ";
				Item item = (Item)locf.getSelectedItem();
				String nloc = item.getId() + "";
				String nff = ff.getSelectedIndex() + "";
				String[] paras = {nf.getText(),nzhf.getText(),nloc,addf.getText(),addzhf.getText(),
						capf.getText(),xf.getText(),yf.getText(),telf.getText(),timef.getText(),nff,
						idf.getText()};
				emp.updInfo(sql, paras);
				this.dispose();
			}else {
				JLabel w3 = new JLabel(rb.getString("w_wSelect"));
				w3.setFont(myFont.f1);
				JOptionPane.showMessageDialog(
					this,w3,rb.getString("w_w"),JOptionPane.ERROR_MESSAGE);
			}
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
