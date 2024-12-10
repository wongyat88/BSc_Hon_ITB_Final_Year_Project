package managerView;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Frame;
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

import managerView.MaterialShopPanel.Item;
import model.EmpModel;
import tools.myFont;

public class MaterialShopDetailDialog extends JDialog implements ActionListener{
	Image titleIcon;
	
	String mid = "";
	String sid = "";
	
	JPanel p1;
	JLabel idm,ids,v;
	JTextField vf;
	JComboBox<Item> idmf,idsf;
	JButton add,up;
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
	
	public MaterialShopDetailDialog(Frame owner,String title,boolean modal,int locale,
			int f,String materialid,String shopid){
		super(owner,title,modal);
		localeCurrent = locale;
		mid = materialid;
		sid = shopid;
		initLocale();
		
		p1 = new JPanel(new FlowLayout());
		
		idm = new JLabel(rb.getString("ms_mid"));
		idm.setFont(myFont.f1);
		EmpModel emp = new EmpModel();
		String sql2 = "";
		if(localeCurrent==0) {
			sql2 = "select m_id, m_n from fyp_material order by m_id ASC ";
		}else {
			sql2 = "select m_id, m_nzh from fyp_material order by m_id ASC ";
		}
		emp.runSql(sql2);
		model = new Vector<Item>();
		for(int i=0;i<emp.getRowCount();i++) {
			model.addElement(new Item(emp.getValueAt(i, 0).toString(), emp.getValueAt(i, 1).toString()));
		}
		idmf = new JComboBox<Item>(model);
		idmf.setFont(myFont.f1);
		idmf.setSelectedIndex(0);
		
		ids = new JLabel(rb.getString("shop_sid"));
		ids.setFont(myFont.f1);
		if(localeCurrent==0) {
			sql2 = "select shop_id, shop_name from fyp_shop order by length(shop_id),shop_id ASC ";
		}else {
			sql2 = "select shop_id, shop_name_zh from fyp_shop order by length(shop_id),shop_id ASC ";
		}
		emp.runSql(sql2);
		model2 = new Vector<Item>();
		for(int i=0;i<emp.getRowCount();i++) {
			model2.addElement(new Item(emp.getValueAt(i, 0).toString(), emp.getValueAt(i, 1).toString()));
		}
		idsf = new JComboBox<Item>(model2);
		idsf.setFont(myFont.f1);
		idsf.setSelectedIndex(0);
		
		v = new JLabel(rb.getString("cp_v"));
		v.setFont(myFont.f1);
		vf = new JTextField(5);
		vf.setFont(myFont.f1);
		
		p1.add(idm);p1.add(idmf);
		p1.add(ids);p1.add(idsf);
		p1.add(v);p1.add(vf);
		
		add = new JButton(rb.getString("fj_add"));
		add.setFont(myFont.f1);
		add.addActionListener(this);
		
		up = new JButton(rb.getString("fj_up"));
		up.setFont(myFont.f1);
		up.addActionListener(this);
		
		this.add(p1,"Center");
		if(f==1) {
			this.add(add,"South");
		}else {
			update();
			idmf.setEnabled(false);
			idsf.setEnabled(false);
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
		this.setLocation(w/2-600, h/2-75);
		this.setSize(1200,150);
		this.setVisible(true);
	}
	
	public void update() {
		EmpModel emp = new EmpModel();
		String sql = "select * from fyp_materialshop where m_id='"+mid+"' and m_sid='"+sid+"' ";
		emp.runSql(sql);
		int x = 0;
		int y = 0;
		String omid = emp.getValueAt(0, 0).toString();
		String osid = emp.getValueAt(0, 1).toString();
		String ov = emp.getValueAt(0, 2).toString();
		
		sql = "select m_id from fyp_material order by m_id ASC ";
		emp.runSql(sql);
		for(int i=0;i<emp.getRowCount();i++) {
			if((emp.getValueAt(i, 0).toString()).equals(omid)) {
				x=i;
			}
		}
		sql = "select shop_id from fyp_shop order by length(shop_id),shop_id ASC ";
		emp.runSql(sql);
		for(int i=0;i<emp.getRowCount();i++) {
			if((emp.getValueAt(i, 0).toString()).equals(osid)) {
				y=i;
			}
		}
		idmf.setSelectedIndex(x);
		idsf.setSelectedIndex(y);
		vf.setText(ov);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == add) {
			EmpModel emp = new EmpModel();
			String sql = "insert into fyp_materialShop values (?,?,?) ";
			Item item = (Item)idmf.getSelectedItem();
			String mm = item.getId() + "";
			Item item2 = (Item)idsf.getSelectedItem();
			String ss = item2.getId() + "";
			String [] paras = {mm,ss,vf.getText()};
			emp.updInfo(sql, paras);
			this.dispose();
		}else if(e.getSource() == up) {
			EmpModel emp = new EmpModel();
			String sql = "update fyp_materialShop set m_l=? where m_id=? and m_sid=? ";
			Item item = (Item)idmf.getSelectedItem();
			String mm = item.getId() + "";
			Item item2 = (Item)idsf.getSelectedItem();
			String ss = item2.getId() + "";
			String [] paras = {vf.getText(),mm,ss};
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
