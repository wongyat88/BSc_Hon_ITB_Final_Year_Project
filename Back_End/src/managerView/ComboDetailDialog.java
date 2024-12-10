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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import model.EmpModel;
import tools.myFont;

public class ComboDetailDialog extends JDialog implements ActionListener{
	Image titleIcon;
	
	String cid = "";
	String log = "";
	
	JLabel jl1,jl2,jl3,jl4,jl5,jl6,jl7,jl8,jl9;
	JLabel jp5_jl1;
	
	JButton jb1,jb2,jb3,jb4,up;
	
	JTextField jtf1,jtf4,jtf5,jtf6,jtf8,jtf9,jtf10;
	JTextArea ja2,ja3;
	
	JPanel jp, jp1, jp2, jp3, jp4, jp5, jp6;
	JPanel j1,j2;
	
	JComboBox<String> StaList;
	JComboBox<Item> cat;
	JScrollPane jsp;
	
	JTable jt1;
	DefaultTableModel model = new DefaultTableModel();
	
	JScrollPane scroll,scroll2;
	
	JFrame parentFrame2;
	
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
	
	public ComboDetailDialog(Frame owner,String title,boolean modal,int locale,int flag,String comboid){
		super(owner,title,modal);
		
		cid = comboid;
		localeCurrent = locale;
		initLocale();
		
		jl1=new JLabel(rb.getString("c_id"));
		jl1.setFont(myFont.f1);
		
		jl2=new JLabel(rb.getString("c_de"));
		jl2.setFont(myFont.f1);
		
		jl3=new JLabel(rb.getString("c_de_zh"));
		jl3.setFont(myFont.f1);
		
		jl4=new JLabel(rb.getString("c_n"));
		jl4.setFont(myFont.f1);
		
		jl5=new JLabel(rb.getString("c_n_zh"));
		jl5.setFont(myFont.f1);
		
		jl6=new JLabel(rb.getString("c_p"));
		jl6.setFont(myFont.f1);
		
		jl7=new JLabel("  "+rb.getString("c_flag"));
		jl7.setFont(myFont.f1);
		
		jl8=new JLabel(rb.getString("c_link"));
		jl8.setFont(myFont.f1);
		
		jl9=new JLabel(rb.getString("c_hash"),JLabel.CENTER);
		jl9.setFont(myFont.f1);
		
		jtf1=new JTextField(5);
		jtf1.setEditable(false);
		jtf1.setFont(myFont.f1);
		
		ja2=new JTextArea(4,24);
		ja2.setFont(myFont.f1);
		ja2.setLineWrap(true);
		ja2.setWrapStyleWord(true);
		scroll = new JScrollPane(ja2);
		
		ja3=new JTextArea(4,24);
		ja3.setFont(myFont.f1);
		ja3.setLineWrap(true);
		ja3.setWrapStyleWord(true);
		scroll2 = new JScrollPane(ja3);
		
		jtf4=new JTextField(20);
		jtf4.setFont(myFont.f1);
		
		jtf5=new JTextField(20);
		jtf5.setFont(myFont.f1);
		
		jtf6=new JTextField(20);
		jtf6.setFont(myFont.f1);
		
		String[] StaStr = { rb.getString("c_jb1"),rb.getString("c_jb2"),rb.getString("c_jb3")};
		StaList = new JComboBox<String>(StaStr);
		StaList.setFont(myFont.f1);
		StaList.setSelectedIndex(0);
		StaList.addActionListener(this);
		
		jtf8=new JTextField(18);
		jtf8.setFont(myFont.f1);
		
		jb1=new JButton(rb.getString("fj_add"));
		jb1.setFont(myFont.f1);
		
		jb2=new JButton(rb.getString("afo_cancel"));
		jb2.setFont(myFont.f1);
		
		up = new JButton(rb.getString("fj_up"));
		up.setFont(myFont.f1);
		
		JLabel catL = new JLabel("  "+rb.getString("md_cat"));
		catL.setFont(myFont.f1);
		Vector<Item> vv = new Vector<Item>();
		vv.addElement(new Item("com",rb.getString("combo_com")));
		vv.addElement(new Item("new",rb.getString("combo_new")));

		cat = new JComboBox<Item>(vv);
		cat.setFont(myFont.f1);
		cat.setSelectedIndex(0);
		
		jp = new JPanel(new GridBagLayout());
		GridBagConstraints gbc0 = new GridBagConstraints();
		
		gbc0.gridx = 0;
		gbc0.gridy = 0;
		gbc0.anchor = GridBagConstraints.LINE_END;
		jp.add(jl1, gbc0);
		
		gbc0.gridx = 1;
		gbc0.gridy = 0;
		gbc0.anchor = GridBagConstraints.LINE_START;
		jp.add(jtf1, gbc0);
		
		gbc0.gridx = 3;
		gbc0.gridy = 0;
		gbc0.anchor = GridBagConstraints.LINE_END;
		jp.add(jl7, gbc0);
		
		gbc0.gridx = 4;
		gbc0.gridy = 0;
		gbc0.anchor = GridBagConstraints.LINE_START;
		jp.add(StaList, gbc0);
		
		gbc0.gridx = 6;
		gbc0.gridy = 0;
		jp.add(catL, gbc0);
		
		gbc0.gridx = 7;
		gbc0.gridy = 0;
		gbc0.anchor = GridBagConstraints.LINE_END;
		jp.add(cat, gbc0);
		
		jp1 = new JPanel(new FlowLayout());
		jp1.add(jl4);
		jp1.add(jtf4);
		jp1.add(jl5);
		jp1.add(jtf5);
		
		jp2 = new JPanel(new FlowLayout());
		jp2.add(jl2);
		jp2.add(scroll);
		jp2.add(jl3);
		jp2.add(scroll2);
		
		jp4 = new JPanel(new GridBagLayout());
		GridBagConstraints gbc4 = new GridBagConstraints();
		
		gbc4.gridx = 0;
		gbc4.gridy = 0;
		gbc4.anchor = GridBagConstraints.LINE_END;
		jp4.add(jl6, gbc4);
		
		gbc4.gridx = 1;
		gbc4.gridy = 0;
		gbc4.anchor = GridBagConstraints.LINE_START;
		jp4.add(jtf6, gbc4);
		
		gbc4.gridx = 2;
		gbc4.gridy = 0;
		gbc4.anchor = GridBagConstraints.LINE_END;
		jp4.add(jl8, gbc4);
		
		gbc4.gridx = 3;
		gbc4.gridy = 0;
		gbc4.anchor = GridBagConstraints.LINE_START;
		jp4.add(jtf8, gbc4);
		
		jp5 = new JPanel(new BorderLayout());
		
		jp5.add(jl9, "North");
		
        model.addColumn("ID");
        model.addColumn("Cat_Info");
        model.addColumn("Cat_Info_zh");
        model.addColumn("food_name");
        model.addColumn("food_name_zh");
        model.addColumn("status_info");
        model.addColumn("Link");
        jt1 = new JTable(model){
			@Override

		    public Component prepareRenderer (TableCellRenderer renderer, int rowIndex, int columnIndex){
		        Component componenet = super.prepareRenderer(renderer, rowIndex, columnIndex);
		        
		        Object value = getModel().getValueAt(rowIndex,columnIndex);

		        if(columnIndex == 5){
		            if(value.equals("1")){
		                componenet.setBackground(new Color(170, 255, 128));
		                componenet.setForeground(Color.BLACK);
		            }
		            if(value.equals("2")){
		                componenet.setBackground(new Color(255, 128, 128));
		                componenet.setForeground(Color.BLACK);
		            }
		            
		        } else {
		        	componenet.setBackground(Color.WHITE);
		            componenet.setForeground(Color.BLACK);
		        }
		        
		        return componenet;
		    }
		};

		jt1.setRowHeight(30);
		jt1.setFont(myFont.f1);
		jsp = new JScrollPane(jt1);
		jsp.getViewport().setBackground(Color.BLACK);
		
		jp5.add(jsp, "Center");
		
		jb3 = new JButton(rb.getString("fj_add"));
		jb3.setFont(myFont.f1);
		jb3.setForeground(Color.WHITE);
		jb3.setBackground(Color.black);
		jb4 = new JButton(rb.getString("fj_del"));
		jb4.setFont(myFont.f1);
		jb4.setForeground(Color.WHITE);
		jb4.setBackground(Color.black);
		
		jp6 = new JPanel();
		jp6.setBackground(Color.black);
		jp6.add(jb3);
		jp6.add(jb4);
		
		jp5.add(jp6,"South");
		
		jp3 = new JPanel();
		if(flag==1) {
			jb1.addActionListener(this);
			jp3.add(jb1);
		}else {
			up.addActionListener(this);
			jp3.add(up);
		}
		jp3.add(jb2);
		
		j2 = new JPanel(new BorderLayout());
		
		JPanel v = new JPanel(new GridBagLayout());
		GridBagConstraints ff = new GridBagConstraints();
		ff.gridx = 0;
		ff.gridy = 0;
		v.add(jp,ff);
		ff.gridx = 0;
		ff.gridy = 1;
		v.add(jp1,ff);
		ff.gridx = 0;
		ff.gridy = 2;
		v.add(jp2,ff);
		ff.gridx = 0;
		ff.gridy = 3;
		v.add(jp4,ff);
		
		j2.add(v,"North");
		j2.add(jp5,"Center");
		j2.add(jp3,"South");
		
		jb2.addActionListener(this);
		jb3.addActionListener(this);
		jb4.addActionListener(this);
		
		this.add(j2,BorderLayout.CENTER);
		
		if(flag==2) {
			updateDetail();
		}
		
		try {
			titleIcon = ImageIO.read(new File("images/logo/logo.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int w = Toolkit.getDefaultToolkit().getScreenSize().width;
		int h = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setIconImage(titleIcon);
		this.setLocation(w/2-900, h/2-400);
		this.setSize(1800, 800);
		this.setVisible(true);
	}
	
	public void setTable() {
		jt1.getColumnModel().getColumn(0).setPreferredWidth(50);
		jt1.getColumnModel().getColumn(1).setPreferredWidth(50);
		jt1.getColumnModel().getColumn(2).setPreferredWidth(50);
		jt1.getColumnModel().getColumn(3).setPreferredWidth(600);
		jt1.getColumnModel().getColumn(4).setPreferredWidth(400);
		jt1.getColumnModel().getColumn(5).setPreferredWidth(50);
		jt1.getColumnModel().getColumn(6).setPreferredWidth(100);
	}
	
	public void updateDetail() {
		EmpModel emp = new EmpModel();
		String sql = "select * from fyp_combo where c_id = '"+cid+"' ";
		emp.runSql(sql);
		jtf1.setText(emp.getValueAt(0, 0).toString());
		ja2.setText(emp.getValueAt(0, 4).toString());
		ja3.setText(emp.getValueAt(0, 5).toString());
		jtf4.setText(emp.getValueAt(0, 2).toString());
		jtf5.setText(emp.getValueAt(0, 3).toString());
		jtf6.setText(emp.getValueAt(0, 7).toString());
		jtf8.setText(emp.getValueAt(0, 8).toString());
		StaList.setSelectedIndex(Integer.parseInt(emp.getValueAt(0, 9).toString()));
		if(emp.getValueAt(0, 1).toString().equals("com")) {
			cat.setSelectedIndex(0);
		}else {
			cat.setSelectedIndex(1);
		}
		String hash = emp.getValueAt(0, 6).toString();
		String[] buff = hash.split(",");
        for(int i=0;i<buff.length;i++){
        	if(buff[i].contains(":")) {
        		String[] buff2 = buff[i].split(":");
                for(int g=0;g<buff2.length;g++){
                	if(buff2[g].contains("_")) {
                		String[] buff3 = buff2[g].split("_");
                        for(int e=0;e<buff3.length;e++){
                        	String sqlg = "select food_id, cat_name, cat_name_zh,"
                					+ "food_name, food_name_zh, food_flag "
                					+ "from ( select pf.* , pc.* from fyp_food pf "
                					+ "JOIN fyp_cat pc ON pf.food_cat = pc.cat_id ) "
                					+ "where food_id = '"+ buff3[e] +"' ";
                			emp.runSql(sqlg);
                	        model.addRow(new Object[]{emp.getValueAt(0, 0), emp.getValueAt(0, 1), 
                	        		emp.getValueAt(0, 2),emp.getValueAt(0, 3),emp.getValueAt(0, 4),
                	        		emp.getValueAt(0, 5),buff[i]});
                        }
                	}
                }
        	}else {
        		String sqlg = "select food_id, cat_name, cat_name_zh,"
    					+ "food_name, food_name_zh, food_flag "
    					+ "from ( select pf.* , pc.* from fyp_food pf "
    					+ "JOIN fyp_cat pc ON pf.food_cat = pc.cat_id ) "
    					+ "where food_id = '"+ buff[i] +"' ";
    			emp.runSql(sqlg);
    			//System.out.println(emp.getValueAt(0, 0));
    	        model.addRow(new Object[]{emp.getValueAt(0, 0), emp.getValueAt(0, 1), 
    	        		emp.getValueAt(0, 2),emp.getValueAt(0, 3),emp.getValueAt(0, 4)
    	        		,emp.getValueAt(0, 5),"null"});
        	}
        }
		setTable();
	}

	public void createLog() {
		log = "";
		String add = "";
		String partner = "";
		for(int c=0;c<jt1.getRowCount();c++) {
			if(c==0) {
				if(jt1.getValueAt(c, 6).toString().equals("null")) {
					add = ""+jt1.getValueAt(c, 0);
					log = log + add;
				}else if(!jt1.getValueAt(c, 6).toString().equals("pass")){
					String linkkey = jt1.getValueAt(c, 6).toString();
					int choose = 0;
					for(int i=0;i<jt1.getRowCount();i++) {
						if(choose == 0) {
							String add2 = "";
							if(jt1.getValueAt(i, 6).toString().equals(linkkey)) {
								jt1.setValueAt("pass", i, 6);
								add2 = ""+jt1.getValueAt(i, 0);
								partner = add2;
								choose = choose+1;
								add2 = "";
							}
						}else {
							String add2 = "";
							if(jt1.getValueAt(i, 6).toString().equals(linkkey)) {
								jt1.setValueAt("pass", i, 6);
								add2 = "_"+jt1.getValueAt(i, 0);
								partner = partner+add2;
								choose = choose+1;
								add2 = "";
							}
						}
					}
					log = choose+":"+partner;
				}
			}else if(c!= 0) {
				if(jt1.getValueAt(c, 6).toString().equals("null")) {
					add = ","+jt1.getValueAt(c, 0);
					log = log + add;
				}else if(!jt1.getValueAt(c, 6).toString().equals("pass")){
					String linkkey = jt1.getValueAt(c, 6).toString();
					int choose = 0;
					for(int i=0;i<jt1.getRowCount();i++) {
						if(choose == 0) {
							String add2 = "";
							if(jt1.getValueAt(i, 6).toString().equals(linkkey)) {
								jt1.setValueAt("pass", i, 6);
								add2 = ""+jt1.getValueAt(i, 0);
								partner = add2;
								choose = choose+1;
								add2 = "";
							}
						}else {
							String add2 = "";
							if(jt1.getValueAt(i, 6).toString().equals(linkkey)) {
								jt1.setValueAt("pass", i, 6);
								add2 = "_"+jt1.getValueAt(i, 0);
								partner = partner+add2;
								choose = choose+1;
								add2 = "";
							}
						}
					}
					log = log +","+choose+":"+partner;
				}
			}
		}
		//System.out.println(log);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==jb1){ // New Combo Add Button
			if(StaList.getSelectedIndex() == 0) {
				JLabel w3 = new JLabel(rb.getString("w_wSelect"));
				w3.setFont(myFont.f1);
				JOptionPane.showMessageDialog(
					this,w3,rb.getString("w_w"),JOptionPane.ERROR_MESSAGE);
			}else {
				String sta7 = StaList.getSelectedIndex() + "";
				Item item = (Item)cat.getSelectedItem();
				String cat2 = item.getId() + "";
				createLog();
				
				EmpModel temp=new EmpModel();
				String sql2="insert into fyp_combo values(cid_increase.nextval,?,?,?,?,?,?,?,?,?)";
				String []paras={cat2,jtf4.getText(),jtf5.getText(),ja2.getText(),ja3.getText(),
						log,jtf6.getText(),jtf8.getText(),sta7};
				
				if(!temp.updInfo(sql2, paras)){
					JLabel w2 = new JLabel(rb.getString("w_sA"));
					w2.setFont(myFont.f1);
					JOptionPane.showMessageDialog(
						this,w2,rb.getString("w_s"),JOptionPane.INFORMATION_MESSAGE);
				}else{
					this.dispose();
				}
			}
		}else if(e.getSource()==up){ // Update Combo Button
			if(StaList.getSelectedIndex() == 0) {
				JLabel w3 = new JLabel(rb.getString("w_wSelect"));
				w3.setFont(myFont.f1);
				JOptionPane.showMessageDialog(
					this,w3,rb.getString("w_w"),JOptionPane.ERROR_MESSAGE);
			}else {
				String sta7 = StaList.getSelectedIndex() + "";
				Item item = (Item)cat.getSelectedItem();
				String cat2 = item.getId() + "";
				createLog();
				
				EmpModel temp=new EmpModel();
				String sql2="update fyp_combo set c_cat=?, c_name=?, c_name_zh=?, "
						  + "c_detail=?, c_detail_zh=?, c_hash=?, c_price=?, c_img=?, c_flag=? "
						  + "where c_id=? ";
				String []paras={cat2,jtf4.getText(),jtf5.getText(),ja2.getText(),ja3.getText(),
						log,jtf6.getText(),jtf8.getText(),sta7,jtf1.getText()};
				
				if(!temp.updInfo(sql2, paras)){
					JLabel w2 = new JLabel(rb.getString("w_sA"));
					w2.setFont(myFont.f1);
					JOptionPane.showMessageDialog(
						this,w2,rb.getString("w_s"),JOptionPane.INFORMATION_MESSAGE);
				}else{
					this.dispose();
				}
			}
		}else if(e.getSource()==jb2){ // Cancel Button
			this.dispose();
		}else if(e.getSource()==jb3){ // Add Food Item Button
			new ComboSelectFoodDialog(parentFrame2,rb.getString("comboSelectFood_title"),true,localeCurrent);
			//System.out.println("Final: "+ComboSelectFoodDialog.getA());
			EmpModel emp;
			int size = ComboSelectFoodDialog.getA().size();
			for(int j = 0;j<size;j++) {
				String sql = "select food_id, cat_name, cat_name_zh,"
    					+ "food_name, food_name_zh, food_flag "
    					+ "from ( select pf.* , pc.* from fyp_food pf "
    					+ "JOIN fyp_cat pc ON pf.food_cat = pc.cat_id ) "
    					+ "where food_id = '"+ ComboSelectFoodDialog.getA().get(j) +"' ";
				emp = new EmpModel();
				emp.runSql(sql);
				DefaultTableModel model = (DefaultTableModel) jt1.getModel();
		        model.addRow(new Object[]{emp.getValueAt(0, 0), emp.getValueAt(0, 1), emp.getValueAt(0, 2),
		        		emp.getValueAt(0, 3),emp.getValueAt(0, 4),emp.getValueAt(0, 5),"null"});
			}
			setTable();
		}else if(e.getSource()==jb4){ // Delete Food Item Button
			int rowNum=this.jt1.getSelectedRow();
			if(rowNum!=-1){
				try {
					DefaultTableModel model = (DefaultTableModel) jt1.getModel();
					model.removeRow(rowNum);
				}catch(Exception e2) {}
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