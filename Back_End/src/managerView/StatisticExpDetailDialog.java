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

import model.EmpModel;
import tools.myFont;

public class StatisticExpDetailDialog extends JDialog implements ActionListener{
	Image titleIcon;
	
	String eid = "";
	
	JPanel m,p1,p2,p3;
	JLabel id,date,from,tid,d,dzh,v;
	JTextField idf,datef,tidf,df,dzhf,vf;
	JComboBox<Item> fromf;
	JButton exit,add,up;
	
	Vector<Item> model;
	
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
	
	public StatisticExpDetailDialog(Frame owner,String title,boolean modal,int locale,int f,String expensesid){
		super(owner,title,modal);
		localeCurrent = locale;
		eid = expensesid;
		initLocale();
		
		m = new JPanel(new GridBagLayout());
		p1 = new JPanel(new FlowLayout());
		p2 = new JPanel(new FlowLayout());
		p3 = new JPanel(new FlowLayout());
		
		id = new JLabel(rb.getString("shop_id"));
		id.setFont(myFont.f1);
		idf = new JTextField(10);
		idf.setEnabled(false);
		idf.setFont(myFont.f1);
		
		date = new JLabel(rb.getString("cu_date"));
		date.setFont(myFont.f1);
		datef = new JTextField(20);
		datef.setEnabled(false);
		datef.setFont(myFont.f1);
		
		p1.add(id);p1.add(idf);
		p1.add(date);p1.add(datef);
		
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
		model = new Vector<Item>();
		model.addElement(new Item("net", rb.getString("cu_net")));
		for(int i=0;i<emp.getRowCount();i++) {
			model.addElement(new Item(emp.getValueAt(i, 0).toString(), emp.getValueAt(i, 1).toString()));
		}
		fromf = new JComboBox<Item>(model);
		fromf.setFont(myFont.f1);
		fromf.setSelectedIndex(0);
		
		tid = new JLabel(rb.getString("ss_tranID"));
		tid.setFont(myFont.f1);
		tidf = new JTextField(5);
		tidf.setFont(myFont.f1);
		
		v = new JLabel(rb.getString("mc_cost"));
		v.setFont(myFont.f1);
		vf = new JTextField(10);
		vf.setFont(myFont.f1);
		
		p2.add(from);p2.add(fromf);
		p2.add(tid);p2.add(tidf);
		p2.add(v);p2.add(vf);
		
		d = new JLabel(rb.getString("md_det"));
		d.setFont(myFont.f1);
		df = new JTextField(20);
		df.setFont(myFont.f1);
		
		dzh = new JLabel(rb.getString("md_detzh"));
		dzh.setFont(myFont.f1);
		dzhf = new JTextField(20);
		dzhf.setFont(myFont.f1);
		
		p3.add(d);p3.add(df);
		p3.add(dzh);p3.add(dzhf);
		
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
		this.setLocation(w/2-600, h/2-150);
		this.setSize(1200, 300);
		this.setVisible(true);
	}
	
	public void update() {
		EmpModel emp = new EmpModel();
		String sql = "select * from fyp_expenses where e_id = '"+eid+"' order by e_id DESC ";
		emp.runSql(sql);
		idf.setText(emp.getValueAt(0, 0).toString());
		datef.setText(emp.getValueAt(0, 1).toString());
		tidf.setText(emp.getValueAt(0, 3).toString());
		df.setText(emp.getValueAt(0, 4).toString());
		dzhf.setText(emp.getValueAt(0, 5).toString());
		vf.setText(emp.getValueAt(0, 6).toString());
		String ofrom = emp.getValueAt(0, 2).toString();
		
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
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == add) {
			EmpModel emp = new EmpModel();
			String sql = "insert into fyp_expenses values "
					+ "(matexp_increase.nextval,TO_DATE(SYSDATE),?,?,?,?,?) ";
			Item item = (Item)fromf.getSelectedItem();
			String ff = item.getId() + "";
			String[] paras = {ff,tidf.getText(),df.getText(),dzhf.getText(),vf.getText()};
			emp.updInfo(sql, paras);
			this.dispose();
		}else if(e.getSource() == up) {
			EmpModel emp = new EmpModel();
			String sql = "update fyp_expenses set e_sid=?,e_tid=?,e_d=?,e_dzh=?,e_v=? "
					+ "where e_id=? ";
			Item item = (Item)fromf.getSelectedItem();
			String ff = item.getId() + "";
			String[] paras = {ff,tidf.getText(),df.getText(),dzhf.getText(),vf.getText(),idf.getText()};
			emp.updInfo(sql, paras);
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