
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

public class FoodDetailDialog extends JDialog implements ActionListener{
	
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
	
	public FoodDetailDialog(Frame owner,String title,boolean modal,int locale,int f,String foodid){
		super(owner,title,modal);
		localeCurrent = locale;
		flag = f;
		fid = foodid;
		initLocale();
		
		jp1 = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		jl1=new JLabel(rb.getString("md_id"));
		jl1.setFont(myFont.f1);
		
		jl2=new JLabel(rb.getString("md_cat"));
		jl2.setFont(myFont.f1);
		
		jl22=new JLabel(rb.getString("md_size"));
		jl22.setFont(myFont.f1);
		
		jl3=new JLabel(rb.getString("md_det"));
		jl3.setFont(myFont.f1);
		
		jl4=new JLabel(rb.getString("md_detzh"));
		jl4.setFont(myFont.f1);
		
		jl5=new JLabel(rb.getString("md_name"));
		jl5.setFont(myFont.f1);
		
		jl6=new JLabel(rb.getString("md_namezh"));
		jl6.setFont(myFont.f1);
		
		jl7=new JLabel(rb.getString("md_price"));
		jl7.setFont(myFont.f1);
		
		jl8=new JLabel(rb.getString("md_flag"));
		jl8.setFont(myFont.f1);
		
		jl9=new JLabel(rb.getString("md_img"));
		jl9.setFont(myFont.f1);
		
		jl99=new JLabel(rb.getString("md_mat"));
		jl99.setFont(myFont.f1);
		
		jtf1=new JTextField(20);
		jtf1.setEditable(false);
		jtf1.setFont(myFont.f1);
		
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
		
		jtf2=new JTextField(20);
		jtf2.setFont(myFont.f1);
		
		ja3=new JTextArea(4,20);
		ja3.setFont(myFont.f1);
		ja3.setLineWrap(true);
		ja3.setWrapStyleWord(true);
		scroll = new JScrollPane(ja3);
		
		ja4=new JTextArea(4,20);
		ja4.setFont(myFont.f1);
		ja4.setLineWrap(true);
		ja4.setWrapStyleWord(true);
		scroll2 = new JScrollPane(ja4);
		
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
		
		jtf9=new JTextField(20);
		jtf9.setFont(myFont.f1);
		
		jtf99=new JTextField(20);
		jtf99.setFont(myFont.f1);
		
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
		jp1.add(jl22, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		jp1.add(jl3, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		jp1.add(jl4, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 5;
		jp1.add(jl5, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 6;
		jp1.add(jl6, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 7;
		jp1.add(jl7, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 8;
		jp1.add(jl8, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 9;
		jp1.add(jl9, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 10;
		jp1.add(jl99, gbc);
		
		gbc.anchor = GridBagConstraints.LINE_START;
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		jp1.add(jtf1, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		jp1.add(jb, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		jp1.add(jtf2, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 3;
		jp1.add(scroll, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 4;
		jp1.add(scroll2, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 5;
		jp1.add(jtf5, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 6;
		jp1.add(jtf6, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 7;
		jp1.add(jtf7, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 8;
		jp1.add(StatusList, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 9;
		jp1.add(jtf9, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 10;
		jp1.add(jtf99, gbc);
		
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
		this.setLocation(w/2-400, h/2-400);
		this.setSize(800, 800);
		this.setVisible(true);
	}
	
	public void addData() {
		EmpModel emp = new EmpModel();
		String sql = "select * from fyp_food where food_id = '"+fid+"' ";
		emp.runSql(sql);
		String cat = emp.getValueAt(0, 1).toString();
		jtf1.setText(emp.getValueAt(0, 0).toString());
		jtf2.setText(emp.getValueAt(0, 2).toString());
		ja3.setText(emp.getValueAt(0, 5).toString());
		ja4.setText(emp.getValueAt(0, 6).toString());
		jtf5.setText(emp.getValueAt(0, 3).toString());
		jtf6.setText(emp.getValueAt(0, 4).toString());
		jtf7.setText(emp.getValueAt(0, 7).toString());
		jtf9.setText(emp.getValueAt(0, 8).toString());
		int fl = Integer.parseInt(emp.getValueAt(0, 9).toString());
		StatusList.setSelectedIndex(fl);
		jtf99.setText(emp.getValueAt(0, 10).toString());
		
		String sql2 = "select * from fyp_cat where cat_flag=0 ";
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
				String sql="insert into fyp_food values(fid_increase.nextval,?,?,?,?,?,?,?,?,?,?)";
				String []paras={cat2,jtf2.getText(),jtf5.getText(),jtf6.getText(),
						ja3.getText(),ja4.getText(),jtf7.getText(),jtf9.getText(),sta8,jtf99.getText()};
				
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
				String sql="update fyp_food set food_cat=?, food_size=?, food_name=?, food_name_zh=?, "
						+ "food_detail=?, food_detail_zh=?, food_price=?, food_img=?, food_flag=?, "
						+ "food_used=? where food_id=? ";
				String []paras={cat2,jtf2.getText(),jtf5.getText(),jtf6.getText(),
						ja3.getText(),ja4.getText(),jtf7.getText(),jtf9.getText(),
						sta8,jtf99.getText(),jtf1.getText()};
				
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
