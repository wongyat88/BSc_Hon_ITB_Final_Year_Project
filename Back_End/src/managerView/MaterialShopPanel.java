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
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
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

import model.EmpModel;
import tools.myColor;
import tools.myFont;

public class MaterialShopPanel extends JPanel implements ActionListener, MouseListener{
	
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
	
	public MaterialShopPanel(int locale) {
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
		
		String sql = "select ms.*, m.M_L , m.M_u , m.M_c, m.m_id from fyp_materialShop ms "
				+ "JOIN fyp_material m on ms.m_id=m.m_id " 
				+ "order by ms.m_id ASC ";
		emp.runSql(sql);
		
		jj = new DefaultTableModel();
		jj.addColumn("Material ID");
		jj.addColumn("Shop");
		jj.addColumn("Now Value");
		jj.addColumn("Break Down");
		jj.addColumn("Unit");
		jj.addColumn("Company_ID");
		jj.addColumn("Buy");
		
		for(int i=0;i<emp.getRowCount();i++) {
			jj.addRow(new Object[]{emp.getValueAt(i, 0).toString(),emp.getValueAt(i, 1).toString(),
					emp.getValueAt(i, 2).toString(),emp.getValueAt(i, 3).toString(),
					emp.getValueAt(i, 4).toString(),emp.getValueAt(i, 5).toString(),rb.getString("mt_buy")});
		}
		
		jtable = new JTable(jj){
			@Override
		    public Component prepareRenderer (TableCellRenderer renderer, int rowIndex, int columnIndex){
		        Component componenet = super.prepareRenderer(renderer, rowIndex, columnIndex);
		        
		        Object value = getModel().getValueAt(rowIndex,columnIndex);
		        
		        float nowValue = Float.parseFloat(getModel().getValueAt(rowIndex,2).toString());
		        float breakValue = Float.parseFloat(getModel().getValueAt(rowIndex,3).toString());
		        if(columnIndex == 2){
		        	if(nowValue<=breakValue) {
		        		componenet.setBackground(myColor.red);
			            componenet.setForeground(Color.BLACK);
		        	}else {
		        		componenet.setBackground(myColor.green);
			            componenet.setForeground(Color.BLACK);
		        	}
		        }else if(columnIndex == 6){
		            componenet.setBackground(myColor.blue);
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
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		jtable.getColumnModel().getColumn(6).setCellRenderer( centerRenderer );
		
		jsp = new JScrollPane(jtable);
		p2.add(jsp,"Center");
		
		JPanel n2 = new JPanel();
		add = new JButton(rb.getString("fj_add"));
		up = new JButton(rb.getString("fj_up"));
		del = new JButton(rb.getString("fj_del"));
		add.setFont(myFont.f1);
		up.setFont(myFont.f1);
		del.setFont(myFont.f1);
		add.addActionListener(this);
		up.addActionListener(this);
		del.addActionListener(this);
		n2.add(add);
		n2.add(up);
		n2.add(del);
		
		m.add(p1,"North");
		m.add(p2,"Center");
		m.add(n2,"South");
		this.setLayout(new BorderLayout());
		this.add(m,"Center");
		this.setVisible(true);
	}

	public void reFresh() {
		EmpModel emp = new EmpModel();
		String sql = "select ms.*, m.M_L , m.M_u , m.M_c, m.m_id from fyp_materialShop ms "
				+ "JOIN fyp_material m on ms.m_id=m.m_id " 
				+ "order by ms.m_id ASC ";
		emp.runSql(sql);
		jj.setRowCount(0);
		for(int i=0;i<emp.getRowCount();i++) {
			jj.addRow(new Object[]{emp.getValueAt(i, 0).toString(),emp.getValueAt(i, 1).toString(),
					emp.getValueAt(i, 2).toString(),emp.getValueAt(i, 3).toString(),
					emp.getValueAt(i, 4).toString(),emp.getValueAt(i, 5).toString(),rb.getString("mt_buy")});
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == b) {
			this.setVisible(false);
		}else if(e.getSource() == go) {
			String input = tf1.getText();
			String sql = "select ms.*, m.M_L , m.M_u , m.M_c, m.m_id from fyp_materialShop ms "
					+ "JOIN fyp_material m on ms.m_id=m.m_id "
					+ "where lower(ms.m_id) like lower('%"+input+"%') "
					+ "order by ms.m_id ASC ";
			EmpModel emp = new EmpModel();
			emp.runSql(sql);
			jj.setRowCount(0);
			for(int i=0;i<emp.getRowCount();i++) {
				jj.addRow(new Object[]{emp.getValueAt(i, 0).toString(),emp.getValueAt(i, 1).toString(),
						emp.getValueAt(i, 2).toString(),emp.getValueAt(i, 3).toString(),
						emp.getValueAt(i, 4).toString(),emp.getValueAt(i, 5).toString(),rb.getString("mt_buy")});
			}
		}else if(e.getSource() == jb) {
			Item item = (Item)jb.getSelectedItem();
			String getSid = item.getId();
			String sql = "select ms.*, m.M_L , m.M_u , m.M_c, m.m_id from fyp_materialShop ms "
					+ "JOIN fyp_material m on ms.m_id=m.m_id "
					+ "where ms.m_sid = '"+getSid+"' "
					+ "order by ms.m_id ASC ";
			EmpModel emp = new EmpModel();
			emp.runSql(sql);
			jj.setRowCount(0);
			for(int i=0;i<emp.getRowCount();i++) {
				jj.addRow(new Object[]{emp.getValueAt(i, 0).toString(),emp.getValueAt(i, 1).toString(),
						emp.getValueAt(i, 2).toString(),emp.getValueAt(i, 3).toString(),
						emp.getValueAt(i, 4).toString(),emp.getValueAt(i, 5).toString(),rb.getString("mt_buy")});
			}
		}else if(e.getSource() == add){
			new MaterialShopDetailDialog(parent,rb.getString("menuDetail_title")+" "+rb.getString("fj_add"),
					true,localeCurrent,1,"0","0");
			reFresh();
		}else if(e.getSource() == up){
			try {
				String sid = jtable.getValueAt(jtable.getSelectedRow(), 1).toString();
				String mid = jtable.getValueAt(jtable.getSelectedRow(), 0).toString();
				new MaterialShopDetailDialog(parent,rb.getString("menuDetail_title")+" "+rb.getString("fj_up"),
						true,localeCurrent,2,mid,sid);
				reFresh();
			}catch (Exception e2) {}
		}else if(e.getSource() == del){
			String mid = jtable.getValueAt(jtable.getSelectedRow(), 0).toString();
			String sid = jtable.getValueAt(jtable.getSelectedRow(), 1).toString();
		    int reply = JOptionPane.showConfirmDialog(null,rb.getString("mess_confirm"),rb.getString("mess_confirmTitle"),JOptionPane.YES_NO_OPTION);
	        if (reply == JOptionPane.YES_OPTION) {
	        	String del = "delete from fyp_materialShop where m_id=? and m_sid=? ";
	        	EmpModel emp = new EmpModel();
	        	String[] paras = {mid,sid};
	        	emp.updInfo(del, paras);
	        }
	        reFresh();
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == jtable) {
			int col = jtable.columnAtPoint(e.getPoint());
			if(col==6) {
				String mid = jtable.getValueAt(jtable.getSelectedRow(), 0).toString();
				String sid = jtable.getValueAt(jtable.getSelectedRow(), 1).toString();
				String cid = jtable.getValueAt(jtable.getSelectedRow(), 5).toString();
				String ouid = jtable.getValueAt(jtable.getSelectedRow(), 4).toString();
				String uid = ""	;
				String uidzh = ""	;
				if(ouid.equals("Each")) {
					uid = "Each";
					uidzh = "個";
				}else {
					uid = ouid;
					uidzh = ouid;
				}
				int reply = JOptionPane.showConfirmDialog(null,rb.getString("mess_confirm"),rb.getString("mess_confirmTitle"),JOptionPane.YES_NO_OPTION);
		        if (reply == JOptionPane.YES_OPTION) {
		        	EmpModel emp = new EmpModel();
		        	String sql = "select * from fyp_materialCom where c_id = '"+cid+"' ";
		        	emp.runSql(sql);
		        	String comName = emp.getValueAt(0, 1).toString();
		        	String comEmail = emp.getValueAt(0, 2).toString();
		        	Float comMoney = Float.parseFloat(emp.getValueAt(0, 4).toString());
		        	Float comAdd = Float.parseFloat(emp.getValueAt(0, 5).toString());
		        	
		        	sql = "select m_n,m_nzh from fyp_material where m_id = '"+mid+"' ";
		        	emp.runSql(sql);
		        	String fName = emp.getValueAt(0, 0).toString();
		        	String fNamezh = emp.getValueAt(0, 1).toString();
		        	
		        	sql = "select shop_name,shop_name_zh,shop_address,shop_address_zh from fyp_shop where shop_id = '"+sid+"' ";
		        	emp.runSql(sql);
		        	String sName = emp.getValueAt(0, 0).toString();
		        	String sNamezh = emp.getValueAt(0, 1).toString();
		        	String sAd = emp.getValueAt(0, 2).toString();
		        	String sAdzh = emp.getValueAt(0, 3).toString();
		        	
		        	String timeStamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
		        	
					String to = comEmail;
					String from = "ilovepython2018@outlook.com";
					String host = "smtp.outlook.com";
					Properties properties = System.getProperties();
					properties.setProperty("mail.smtp.host", host);
					properties.put("mail.smtp.port", "587");  
					properties.put("mail.smtp.auth", "true");
					properties.put("mail.smtp.ssl.enable", "false");
					properties.put("mail.smtp.starttls.enable", "true");
					  
					Session session = Session.getDefaultInstance(properties,new Authenticator(){
						public PasswordAuthentication getPasswordAuthentication(){
							return new PasswordAuthentication("ilovepython2018@outlook.com", "python123");
							}
						}
					);
					
					try {
						MimeMessage message = new MimeMessage(session);
						message.setFrom(new InternetAddress(from));
						message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
						message.setSubject("商品購買 , Purchasing Goods", "UTF-8");
						String body = 
						"<p>"+comName+" : </p><br>"
					  + "<h3>商品購買</h3><br>"
					  + "<p>本公司將向貴公司購買以下商品,故特專函通知。</p>"
					  + "<h3>商品名稱 : "+fNamezh+"</h3>"
					  + "<h3>商品數量 : "+comAdd+" "+uidzh+"</h3>"
					  + "<h3>商品合約價格沒有更變,為 HKD$"+comMoney+"</h3>"
					  + "<h3>商品接收地址為 "+sNamezh+" , "+sAdzh+"</h3><br>"
					  + "<p>感謝</p><br>"
					  + "<p>富臨集團有限公司</p><br>"
					  + "<p>此電郵由系統自動發送,請勿回覆此電郵。如欲與我們聯絡，請"
					  + "<a href='https://www.fulum.com.hk'>按此處</a>"
					  + "或致電客戶服務熱線。<br>"
					  + timeStamp+"</p><br>"
					  + "---------------------------------------------------------<br>"
					  +	"<p> To "+comName+", </p><br>"
					  + "<h3>Purchase for Products</h3><br>"
					  + "<p>We are looking forward on purchasing your compnay's products.</p>"
					  + "<h3>Product Name : "+fName+"</h3>"
					  + "<h3>Product Quantity : "+comAdd+" "+uid+"</h3>"
					  + "<h3>The commodity contract prices have not changed, same as HKD$"+comMoney+"</h3>"
					  + "<h3>Please deliver to "+sName+", "+sAd+"</h3><br>"
					  + "<p>Thank you</p><br>"
					  + "<p>From Fulum Group</p><br>"
					  + "<p>This email is sent automatically by the system. Please do not reply to this email. "
					  + "To contact us, <a href='https://www.fulum.com.hk/en/'>please click here </a>"
					  + "or call the customer service hotline.<br>"
					  + timeStamp+"</p><br>";
						
						Multipart multipart = new MimeMultipart();

				        MimeBodyPart htmlPart = new MimeBodyPart();
				        htmlPart.setText(body, "utf-8", "html");
				        multipart.addBodyPart(htmlPart);
				        
						message.setContent(multipart,"text/html");
						
						Transport.send(message);
						
						sql = "insert into fyp_materialTran values(matran_increase.nextval,?,?,TO_DATE(SYSDATE),?) ";
						String[] paras = {sid,cid,"1"};
						emp.updInfo(sql, paras);
						
						JLabel w2 = new JLabel(rb.getString("w_sA"));
						w2.setFont(myFont.f1);
						JOptionPane.showMessageDialog(
							this,w2,rb.getString("w_s"),JOptionPane.INFORMATION_MESSAGE);
						
					} catch (MessagingException mex) {
						mex.printStackTrace();
					}
		        }
		        reFresh();
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

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
}
