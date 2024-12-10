package managerView;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import managerView.FoodJPanel.Item;
import model.EmpModel;
import tools.myFont;

public class SpecialDetailDialog extends JDialog implements ActionListener{
	
	int flag = 0;
	String fid = "";
	Image titleIcon;
	
	JLabel jl1,jl2,jl22,jl3,jl4,jl5,jl6,jl7,jl8,jl9,jl99;
	
	JButton jb3,jb1,jb2;
	JTextField jtf1,jtf2,jtf3,jtf4,jtf5,jtf6,jtf7,jtf9,jtf99;
	JTextArea ja3,ja4;
	
	JPanel jp1, jp2, jp3;
	
	JComboBox<Item> jb;
	JComboBox<String> StatusList;
	
	JScrollPane scroll,scroll2;
	
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
	
	public SpecialDetailDialog(Frame owner,String title,boolean modal,int locale,int f,String specialid){
		super(owner,title,modal);
		localeCurrent = locale;
		flag = f;
		fid = specialid;
		initLocale();
		
		jp1 = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		jl1=new JLabel(rb.getString("sd_id"));
		jl1.setFont(myFont.f1);
		
		jl2=new JLabel(rb.getString("md_cat"));
		jl2.setFont(myFont.f1);
		
		jl5=new JLabel(rb.getString("md_name"));
		jl5.setFont(myFont.f1);
		
		jl6=new JLabel(rb.getString("md_namezh"));
		jl6.setFont(myFont.f1);
		
		jl7=new JLabel(rb.getString("md_price"));
		jl7.setFont(myFont.f1);
		
		jl8=new JLabel(rb.getString("md_flag"));
		jl8.setFont(myFont.f1);
		
		jtf1=new JTextField(20);
		jtf1.setEditable(false);
		jtf1.setFont(myFont.f1);
		
		EmpModel emp = new EmpModel();
		String sql2 = "";
		if(localeCurrent==0) {
			sql2 = "select cat_id, cat_name from fyp_cat ";
		}else {
			sql2 = "select cat_id, cat_name_zh from fyp_cat ";
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
		
		jtf5=new JTextField(20);
		jtf5.setFont(myFont.f1);
		
		jtf6=new JTextField(20);
		jtf6.setFont(myFont.f1);
		
		jtf7=new JTextField(20);
		jtf7.setFont(myFont.f1);
		
		String[] StatusStr = { rb.getString("md_choose"),rb.getString("md_av"),rb.getString("md_nav")};
		StatusList = new JComboBox<String>(StatusStr);
		StatusList.setFont(myFont.f1);
		StatusList.setSelectedIndex(0);
		StatusList.addActionListener(this);
		
		jb1=new JButton(rb.getString("cm_add"));
		jb1.setFont(myFont.f1);
		
		jb3=new JButton(rb.getString("fj_up"));
		jb3.setFont(myFont.f1);
		
		jb2=new JButton(rb.getString("common_exit"));
		jb2.setFont(myFont.f1);
		
		jb1.addActionListener(this);
		jb3.addActionListener(this);
		jb2.addActionListener(this);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		jp1.add(jl1, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		jp1.add(jl2, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		jp1.add(jl5, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		jp1.add(jl6, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		jp1.add(jl7, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 5;
		jp1.add(jl8, gbc);
		
		gbc.anchor = GridBagConstraints.LINE_START;
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		jp1.add(jtf1, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		jp1.add(jb, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		jp1.add(jtf5, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 3;
		jp1.add(jtf6, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 4;
		jp1.add(jtf7, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 5;
		jp1.add(StatusList, gbc);
		
		jp3 = new JPanel();
		if(flag==1) {
			jp3.add(jb1);
			jp3.add(jb2);
		}else {
			jp3.add(jb3);
			jp3.add(jb2);
		}
		
		jp2 = new JPanel();
		getRootPane().add(jp2);
		
		jp2.add(jp1);
		
		if(flag==2) {
			addData();
		}
		
		this.add(jp2,BorderLayout.CENTER);
		this.add(jp3,BorderLayout.SOUTH);
		
		try {
			titleIcon = ImageIO.read(new File("images/logo/logo.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int w = Toolkit.getDefaultToolkit().getScreenSize().width;
		int h = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setIconImage(titleIcon);
		this.setLocation(w/2-325, h/2-200);
		this.setSize(650, 400);
		this.setVisible(true);
	}
	
	public void addData() {
		EmpModel emp = new EmpModel();
		String sql = "select * from fyp_sitem where si_id = '"+fid+"' ";
		emp.runSql(sql);
		String cat = emp.getValueAt(0, 3).toString();
		jtf1.setText(emp.getValueAt(0, 0).toString());
		jtf5.setText(emp.getValueAt(0, 1).toString());
		jtf6.setText(emp.getValueAt(0, 2).toString());
		jtf7.setText(emp.getValueAt(0, 4).toString());
		int fl = Integer.parseInt(emp.getValueAt(0, 5).toString());
		StatusList.setSelectedIndex(fl);
		
		String sql2 = "select * from fyp_cat ";
		emp.runSql(sql2);
		int x = 0;
		for(int i=0;i<emp.getRowCount();i++) {
			if(cat.equals(emp.getValueAt(i, 0).toString())) {
				x = i;
			}
		}
		jb.setSelectedIndex(x);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==jb1){
			JLabel w3 = new JLabel(rb.getString("w_wSelect"));
			JLabel w2 = new JLabel(rb.getString("w_wInput"));
			w3.setFont(myFont.f1);
			w2.setFont(myFont.f1);
			if(StatusList.getSelectedIndex() == 0) {
				JOptionPane.showMessageDialog(
						this,w3,rb.getString("w_w"),JOptionPane.ERROR_MESSAGE);
			}else {
				Item item = (Item)jb.getSelectedItem();
				String cat2 = item.getId() + "";
				String sta8 = StatusList.getSelectedIndex() + "";
				
				EmpModel temp=new EmpModel();
				String sql="insert into fyp_sitem values(siid_increase.nextval,?,?,?,?,?)";
				String []paras={jtf5.getText(),jtf6.getText(),cat2,jtf7.getText(),sta8};
				
				if(!temp.updInfo(sql, paras)){
					JOptionPane.showMessageDialog(this,w2,rb.getString("w_w"), JOptionPane.ERROR_MESSAGE);
				}else{
					this.dispose();
				}
			}
		}else if(e.getSource() == jb3){
			JLabel w3 = new JLabel(rb.getString("w_wSelect"));
			JLabel w2 = new JLabel(rb.getString("w_wInput"));
			w3.setFont(myFont.f1);
			w2.setFont(myFont.f1);
			if(StatusList.getSelectedIndex() == 0) {
				JOptionPane.showMessageDialog(
						this,w3,rb.getString("w_w"),JOptionPane.ERROR_MESSAGE);
			}else {
				Item item = (Item)jb.getSelectedItem();
				String cat2 = item.getId() + "";
				String sta8 = StatusList.getSelectedIndex() + "";
				
				EmpModel temp=new EmpModel();
				String sql="update fyp_sitem set si_cat=?, si_name=?, si_name_zh=?, "
						+ "si_price=?, si_flag=? where si_id=? ";
				String []paras={cat2,jtf5.getText(),jtf6.getText(),jtf7.getText(),sta8,jtf1.getText()};
				
				if(!temp.updInfo(sql, paras)){
					JOptionPane.showMessageDialog(this,w2,rb.getString("w_w"), JOptionPane.ERROR_MESSAGE);
				}else{
					this.dispose();
				}
			}
		}else if(e.getSource()==jb2){
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

