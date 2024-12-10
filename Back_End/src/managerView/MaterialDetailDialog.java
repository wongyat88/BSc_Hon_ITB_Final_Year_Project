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

public class MaterialDetailDialog extends JDialog implements ActionListener{
	Image titleIcon;
	int flag = 0;
	String mid = "";
	
	JPanel m,p1,p2,p3,p4;
	JLabel id,n,nzh,e,p,pw,pt;
	JTextField idf,nf,nzhf,ef,pf,ptf;
	JComboBox<Item> pwf;
	
	JButton add,up;
	
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
	
	public MaterialDetailDialog(Frame owner,String title,boolean modal,int locale,int f,String materialid){
		super(owner,title,modal);
		localeCurrent = locale;
		flag = f;
		mid = materialid;
		initLocale();
		
		p1 = new JPanel(new FlowLayout());
		p2 = new JPanel(new FlowLayout());
		p3 = new JPanel(new FlowLayout());
		p4 = new JPanel(new FlowLayout());
		
		id = new JLabel(rb.getString("shop_id"));
		id.setFont(myFont.f1);
		idf = new JTextField(5);
		idf.setEnabled(false);
		idf.setFont(myFont.f1);
		
		n = new JLabel(rb.getString("md_name"));
		n.setFont(myFont.f1);
		nf = new JTextField(20);
		nf.setFont(myFont.f1);
		
		nzh = new JLabel(rb.getString("md_namezh"));
		nzh.setFont(myFont.f1);
		nzhf = new JTextField(10);
		nzhf.setFont(myFont.f1);
		
		p1.add(id);p1.add(idf);
		p1.add(n);p1.add(nf);
		p1.add(nzh);p1.add(nzhf);
		
		e = new JLabel(rb.getString("mt_unit"));
		e.setFont(myFont.f1);
		ef = new JTextField(5);
		ef.setFont(myFont.f1);
		
		p = new JLabel(rb.getString("mt_bp"));
		p.setFont(myFont.f1);
		pf = new JTextField(10);
		pf.setFont(myFont.f1);
		
		pw = new JLabel(rb.getString("ms_c"));
		pw.setFont(myFont.f1);
		EmpModel emp = new EmpModel();
		String sql2 = "select c_id, c_n from fyp_materialcom order by length(c_id) ,c_id ";
		emp.runSql(sql2);
		Vector<Item> model = new Vector<Item>();
		for(int i=0;i<emp.getRowCount();i++) {
			model.addElement(new Item(emp.getValueAt(i, 0).toString(), emp.getValueAt(i, 1).toString()));
		}
		pwf = new JComboBox<Item>(model);
		pwf.setFont(myFont.f1);
		pwf.setSelectedIndex(0);
		
		p2.add(e);p2.add(ef);
		p2.add(p);p2.add(pf);
		p2.add(pw);p2.add(pwf);
		
		add = new JButton(rb.getString("fj_add"));
		add.setFont(myFont.f1);
		add.addActionListener(this);
		up = new JButton(rb.getString("fj_up"));
		up.setFont(myFont.f1);
		up.addActionListener(this);
		
		m = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		m.add(p1,gbc);
		gbc.gridy = 1;
		m.add(p2,gbc);
		
		this.add(m,"North");
		if(flag==1) {
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
		this.setLocation(w/2-600, h/2-100);
		this.setSize(1200, 200);
		this.setVisible(true);
	}
	
	public void update() {
		EmpModel emp = new EmpModel();
		String sql = "select * from fyp_material where m_id = '"+mid+"' ";
		emp.runSql(sql);
		idf.setText(emp.getValueAt(0, 0).toString());
		nf.setText(emp.getValueAt(0, 1).toString());
		nzhf.setText(emp.getValueAt(0, 2).toString());
		ef.setText(emp.getValueAt(0, 3).toString());
		pf.setText(emp.getValueAt(0, 4).toString());
		String cc = emp.getValueAt(0, 5).toString();
		sql = "select c_id from fyp_materialcom order by length(c_id) ,c_id ";
		emp.runSql(sql);
		int x = 0;
		for(int i=0;i<emp.getRowCount();i++) {
			if(cc.equals(emp.getValueAt(i, 0).toString())) {
				x=i;
			}
		}
		pwf.setSelectedIndex(x);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == add) {
			Item item = (Item)pwf.getSelectedItem();
			String company = item.getId();
			EmpModel emp = new EmpModel();
			String sql = "insert into fyp_material values (mat_increase.nextval,?,?,?,?,?) ";
			String[] paras = {nf.getText(),nzhf.getText(),ef.getText(),pf.getText(),company};
			emp.updInfo(sql, paras);
			this.dispose();
		}else if(e.getSource() == up) {
			Item item = (Item)pwf.getSelectedItem();
			String company = item.getId();
			EmpModel emp = new EmpModel();
			String sql = "update fyp_material set m_id=?,m_n=?,m_nzh=?,m_u=?,m_l=?,m_c=? where m_id=? ";
			String[] paras = {idf.getText(),nf.getText(),nzhf.getText(),ef.getText(),pf.getText(),company,
					idf.getText()};
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
