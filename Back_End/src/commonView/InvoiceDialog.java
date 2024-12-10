package commonView;

import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.Element;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.ImageView;

import model.EmpModel;
import tools.myFont;

public class InvoiceDialog extends JDialog implements ActionListener {
	JPanel jp1,jp2;
	JScrollPane jsp;
	JTextPane jta1;
	JButton print;
	JLabel jl1;
	EmpModel emp;
	
	String ot = "";
	String sid = "";
	String oid = "";
	String time = "";
	String ppl = "";
	String area = "";
	String sname = "";
	String sphone = "";
	String saddress = "";
	String timeNow = "";
	String repeated = "";
	String header = "";
	
	int priceFlag = 0;
	float sum = 0;
	
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
	
	public InvoiceDialog(Frame owner,String title,boolean modal,int locale,String shopid,String orderid) {
		super(owner,title,modal);
		sid = shopid;
		oid = orderid;
		localeCurrent = locale;
		initLocale();
		
		EmpModel pSD = new EmpModel();
		String hgf = "select order_time from fyp_order "
				+ "where order_id='"+oid+"' and order_sid='"+sid+"' and order_flag='1' ";
		pSD.runSql(hgf);
		ot = pSD.getValueAt(0, 0).toString();
		
		Object[] options = {rb.getString("in_com"),rb.getString("in_mor"),
							rb.getString("in_tea"),rb.getString("in_hol")};
		int choose = JOptionPane.showOptionDialog(this,
			rb.getString("in_selectp"),
		    rb.getString("md_choose"),
		    JOptionPane.YES_NO_CANCEL_OPTION,
		    JOptionPane.QUESTION_MESSAGE,
		    null,
		    options,
		    options[3]);
		switch(choose) {
		case 0:
			priceFlag = 0;
			break;
		case 1:
			priceFlag = 1;
			break;
		case 2:
			priceFlag = 2;
			break;
		case 3:
			priceFlag = 3;
			break;
		}
		
		jp1 = new JPanel(new BorderLayout());
		
		jta1 = new JTextPane();
		jta1.setFont(myFont.f1);
		jta1.setEditable(false);
		jta1.setEditorKit(new HTMLEditorKit() {
            @Override
            public ViewFactory getViewFactory() {
                return new HTMLFactory() {

                    @Override
                    public View create(Element elem) {
                        View view = super.create(elem);
                        if (view instanceof ImageView) {
                            ((ImageView) view).setLoadsSynchronously(true);
                        }
                        return view;
                    }
                };
            }
        });
		jsp = new JScrollPane(jta1);
		
		jp1.add(jsp,"Center");
		
		this.add(jp1,"Center");
		
		print = new JButton(rb.getString("in_print"));
		print.setFont(myFont.f1);
		print.addActionListener(this);
		
		this.add(print,"South");
		
		EmpModel emp = new EmpModel();
		String sql = "select order_time,order_ppl,order_area from fyp_order where"
				+ " order_id='"+oid+"' and order_sid='"+sid+"' and order_flag='1' ";
		emp.runSql(sql);
		time = emp.getValueAt(0, 0).toString();
		ppl = emp.getValueAt(0, 1).toString();
		area = emp.getValueAt(0, 2).toString();
		if(localeCurrent==0) {
			sql = "select shop_name , shop_tel, shop_address from fyp_shop where shop_id='"+sid+"' ";
		}else {
			sql = "select shop_name_zh , shop_tel, shop_address_zh from fyp_shop where shop_id='"+sid+"' ";
		}
		emp.runSql(sql);
		sname = emp.getValueAt(0, 0).toString();
		sphone = emp.getValueAt(0, 1).toString();
		saddress = emp.getValueAt(0, 2).toString();
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		timeNow = formatter.format(date);
		
		addInvoiceDetails();
		
		try {
			titleIcon = ImageIO.read(new File("images/logo/logo.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setIconImage(titleIcon);
		int w = Toolkit.getDefaultToolkit().getScreenSize().width;
		int h = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation(w/2-300, h/2-300);
		this.setSize(600,600);
		this.setVisible(true);
	}
	
	public void addInvoiceDetails() {
		jta1.setContentType("text/html");
		Header();
		Middle();
		End();
		Final();
	}
	
	private void Header(){
		header = 
			"<html><head><style>.right{float:right;}.left{float:left;}</style></head>"
		   +"<h1> ID: "+oid+"</h1>"+"<br>"
		   +"<center><h1>"+sname+"</h1></center>"
		   +"<center><h4>"+sphone+"</h4></center>"
		   +"<center><p>"+saddress+"</p></center>"
		   +"<center>===================================================</center><br>"
		   +"<pre>"
		   +rb.getString("cm_oid")+oid+"\n"
		   +rb.getString("afo_time")+time+"\n"
		   +rb.getString("in_ppl")+ppl+"\n"
			+"</pre>"
		   +"<center>===================================================</center><br>";
		jta1.setText(header);
    }
	
	public void Middle() {
		EmpModel emp = new EmpModel();
		String sql = "select * from fyp_orderitem where oi_oid='"+oid+"' and oi_sid='"+sid+"' and "
				+ "(oi_flag='1' or oi_flag='2') and oi_ptime='"+ot+"' order by length(oi_id), oi_id ";
		emp.runSql(sql);
		for(int i=0;i<emp.getRowCount();i++) {
			if(emp.getValueAt(i, 4).toString().equals("f")) {
				String fid = emp.getValueAt(i, 7).toString();
				String oiid = emp.getValueAt(i, 0).toString();
				String ffprice = emp.getValueAt(i, 9).toString();
				
				EmpModel emp2 = new EmpModel();
				String sql2 = "";
				if(localeCurrent==0) {
					sql2 = "select food_cat, food_size, food_name, food_price from fyp_food "
							+ "where food_id='"+fid+"' ";
				}else {
					sql2 = "select food_cat, food_size, food_name_zh, food_price from fyp_food "
							+ "where food_id='"+fid+"' ";
				}
				emp2.runSql(sql2);
				
				String foodname = emp2.getValueAt(0, 2).toString();
				float foodp = Float.parseFloat(ffprice);
				
				int wordLength = 0;
    			for(int d = 0;d<((String)emp2.getValueAt(0, 2)).length();d++) {
    				wordLength++;
    			}
    			wordSpace(wordLength);
    			
    			EmpModel emp3 = new EmpModel();
    			String sql3 = "";
    			if(emp2.getValueAt(0, 0).toString().equals("ds") || emp2.getValueAt(0, 0).toString().equals("local")) {
    				if(priceFlag==0) {
    					sql3 = "select s_price from fyp_size "
    							+ "where s_id='"+emp2.getValueAt(0, 1).toString()+"' ";
    					emp3.runSql(sql3);
    	    			foodp = Float.parseFloat(emp3.getValueAt(0, 0).toString());
    				}else if(priceFlag==1) {
    					sql3 = "select s_morn from fyp_size "
    							+ "where s_id='"+emp2.getValueAt(0, 1).toString()+"' ";
    					emp3.runSql(sql3);
    	    			foodp = Float.parseFloat(emp3.getValueAt(0, 0).toString());
    				}else if(priceFlag==2) {
    					sql3 = "select s_tea from fyp_size "
    							+ "where s_id='"+emp2.getValueAt(0, 1).toString()+"' ";
    					emp3.runSql(sql3);
    	    			foodp = Float.parseFloat(emp3.getValueAt(0, 0).toString());
    				}else if(priceFlag==3) {
    					sql3 = "select s_hoilday from fyp_size "
    							+ "where s_id='"+emp2.getValueAt(0, 1).toString()+"' ";
    					emp3.runSql(sql3);
    	    			foodp = Float.parseFloat(emp3.getValueAt(0, 0).toString());
    				}
    			}
    			
    			BigDecimal fp = new BigDecimal(foodp);
    	        BigDecimal nowp = new BigDecimal(sum);
    	        BigDecimal newp = nowp.add(fp);
    	        sum = Float.parseFloat(newp.toString());
    	        
    			String record = 
    					"<pre>" + 
    					emp.getValueAt(i, 3).toString().substring(10, 19)+"\t"+foodname+repeated+foodp+
    					"</pre>";
    			
    			header = header + record;
    			jta1.setText(header);
    			
    	        sql3 = "select * from fyp_sorder where so_o_id='"+oiid+"' ";
    	        emp3.runSql(sql3);
    	        try {
    	        	for(int x=0;x<emp3.getRowCount();x++) {
    	        		String sid = emp3.getValueAt(x, 2).toString();
    	        		
    	        		EmpModel emp4 = new EmpModel();
    	        		String sql4 = "";
    	        		if(localeCurrent==0) {
    	        			sql4 = "select si_name, si_price from fyp_sitem where si_id='"+sid+"' ";
    	        		}else {
    	        			sql4 = "select si_name_zh, si_price from fyp_sitem where si_id='"+sid+"' ";
    	        		}
    	        		emp4.runSql(sql4);
    	        		String siname = emp4.getValueAt(0, 0).toString();
    	        		String sip = emp4.getValueAt(0, 1).toString();
    	        		
    	        		BigDecimal fp2 = new BigDecimal(sip);
    	    	        BigDecimal nowp2 = new BigDecimal(sum);
    	    	        BigDecimal newp2 = nowp2.add(fp2);
    	    	        sum = Float.parseFloat(newp2.toString());
    	        		
    	    	        int wordLength2 =0;
    	    			for(int d = 0;d<((String)emp4.getValueAt(0, 0)).length();d++) {
    	    				wordLength2++;
    	    			}
    	    			wordSpace(wordLength2);
    	    	        
    	    	        String record2 = 
    	    					"<pre>" + 
    	    					"\t\t\t"+siname+repeated+sip+
    	    					"</pre>";
    	    			header = header + record2;
    	    			jta1.setText(header);
    	        	}
    	        }catch (Exception e2) {
					e2.printStackTrace();
				}
			}else {
				if(emp.getValueAt(i, 10).toString().equals("n")) {
					String comid = emp.getValueAt(i, 5).toString();
					String[] buff = comid.split("_");
					String cid = buff[0];
					
					EmpModel qwe = new EmpModel();
					String asd ="";
					if(localeCurrent==0) {
						asd="select c_name,c_price from fyp_combo where c_id='"+cid+"' ";
					}else {
						asd="select c_name_zh,c_price from fyp_combo where c_id='"+cid+"' ";
					}
					qwe.runSql(asd);
					
					BigDecimal fp = new BigDecimal((String)qwe.getValueAt(0, 1));
	    	        BigDecimal nowp = new BigDecimal(sum);
	    	        BigDecimal newp = nowp.add(fp);
	    	        sum = Float.parseFloat(newp.toString());
					
					int wordLength2 =0;
	    			for(int d = 0;d<((String)qwe.getValueAt(0, 0)).length();d++) {
	    				wordLength2++;
	    			}
	    			wordSpace(wordLength2);
	    			
					String cr = "<pre>"+
								emp.getValueAt(i, 3).toString().substring(10, 19)+"\t"
							    +(String)qwe.getValueAt(0, 0)+"\n"
								+"\t\t\t\t\t\t"+(String)qwe.getValueAt(0, 1)+
								"</pre>";
					header = header+cr;
					jta1.setText(header);
					
					asd = "select * from fyp_orderitem where oi_cid='"+comid+"' and oi_show='y' ";
					qwe.runSql(asd);
					
					for(int y=0;y<qwe.getRowCount();y++) {
						String fid = qwe.getValueAt(y, 7).toString();
						String oiid = qwe.getValueAt(y, 0).toString();
						
						EmpModel emp2 = new EmpModel();
						String sql2 = "";
						if(localeCurrent==0) {
							sql2 = "select food_cat, food_size, food_name, food_price from fyp_food "
									+ "where food_id='"+fid+"' ";
						}else {
							sql2 = "select food_cat, food_size, food_name_zh, food_price from fyp_food "
									+ "where food_id='"+fid+"' ";
						}
						emp2.runSql(sql2);
						
						String foodname = emp2.getValueAt(0, 2).toString();
						float foodp = Float.parseFloat(emp2.getValueAt(0, 3).toString());
						
						int wordLength = 0;
		    			for(int d = 0;d<((String)emp2.getValueAt(0, 2)).length();d++) {
		    				wordLength++;
		    			}
		    			wordSpace(wordLength);
		    			
		    			EmpModel emp3 = new EmpModel();
		    			String sql3 = "";
		    	        
		    			String record = 
		    					"<pre>" + 
		    					qwe.getValueAt(y, 3).toString().substring(10, 19)+"\t"+foodname+repeated+0+
		    					"</pre>";
		    			
		    			header = header + record;
		    			jta1.setText(header);
		    			
		    	        sql3 = "select * from fyp_sorder where so_o_id='"+oiid+"' ";
		    	        emp3.runSql(sql3);
		    	        try {
		    	        	for(int x=0;x<emp3.getRowCount();x++) {
		    	        		String sid = emp3.getValueAt(x, 2).toString();
		    	        		
		    	        		EmpModel emp4 = new EmpModel();
		    	        		String sql4 = "";
		    	        		if(localeCurrent==0) {
		    	        			sql4 = "select si_name, si_price from fyp_sitem where si_id='"+sid+"' ";
		    	        		}else {
		    	        			sql4 = "select si_name_zh, si_price from fyp_sitem where si_id='"+sid+"' ";
		    	        		}
		    	        		emp4.runSql(sql4);
		    	        		String siname = emp4.getValueAt(0, 0).toString();
		    	        		String sip = emp4.getValueAt(0, 1).toString();
		    	        		
		    	        		BigDecimal fp3 = new BigDecimal(sip);
		    	    	        BigDecimal nowp3 = new BigDecimal(sum);
		    	    	        BigDecimal newp3 = nowp3.add(fp3);
		    	    	        sum = Float.parseFloat(newp3.toString());
		    	        		
		    	    	        int wordLength3 =0;
		    	    			for(int d = 0;d<((String)emp4.getValueAt(0, 0)).length();d++) {
		    	    				wordLength3++;
		    	    			}
		    	    			wordSpace(wordLength3);
		    	    	        
		    	    	        String record2 = 
		    	    					"<pre>" + 
		    	    					"\t\t\t"+siname+repeated+sip+
		    	    					"</pre>";
		    	    			header = header + record2;
		    	    			jta1.setText(header);
		    	        	}
		    	        }catch (Exception e2) {
							e2.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	public void End() {
		float sc = 0 ;
		
		EmpModel emp = new EmpModel();
		String sql = "select s_price from fyp_serviceFee where s_id='sc' ";
		if(area.equals("1")) {
			emp.runSql(sql);
			BigDecimal fp = new BigDecimal((String)emp.getValueAt(0, 0));
			
			BigDecimal nowp = new BigDecimal(sum);
			BigDecimal fp2 = fp.multiply(nowp);
			DecimalFormat df = new DecimalFormat("#.#");
			sc = Float.parseFloat(df.format(fp2).toString());
			
			String scc = "<pre>"+
						rb.getString("in_sum")+"\t\t\t\t\t\t"+sum+"</pre>"+
						"<center><p>---------------------------------------------------</p></center>"+
						"<pre>"+
						rb.getString("in_sc")+" 10%"+"\t\t\t\t\t"+sc
						+"</pre>";
			
			header = header + scc;
			jta1.setText(header);
			
	        BigDecimal newp = nowp.add(fp2);
	        sql = "select s_price from fyp_serviceFee where s_id='tc' ";
			emp.runSql(sql);
			BigDecimal tp = new BigDecimal((String)emp.getValueAt(0, 0));
			BigDecimal people = new BigDecimal(ppl);
			BigDecimal ntp = tp.multiply(people);
			
			String tcc = "<pre>"+
					rb.getString("in_tc")+" :"+ppl+"\t\t\t\t\t"+Float.parseFloat(ntp.toString())+"</pre>";
			
			header = header + tcc;
			jta1.setText(header);
			
			BigDecimal finalP = newp.add(ntp);
			sum = Float.parseFloat(df.format(finalP).toString());
			
			String fcc = 
					"<center><p>---------------------------------------------------</p></center>"+
					"<pre>"+
					rb.getString("in_finalSum")+"\t\t\t\t\t"+sum
					+"</pre>";
		
			header = header + fcc;
			jta1.setText(header);
		}else {
			DecimalFormat df = new DecimalFormat("#.#");
			sql = "select s_price from fyp_serviceFee where s_id='tkc' ";
			emp.runSql(sql);
			BigDecimal tkp = new BigDecimal((String)emp.getValueAt(0, 0));
			
			BigDecimal fsum = new BigDecimal(sum);
			BigDecimal finalP = tkp.add(fsum);
			sum = Float.parseFloat(df.format(finalP).toString());
			
			String scc = 
					"<center><p>---------------------------------------------------</p></center>"+
					"<pre>"+
					rb.getString("in_tk")+"\t\t\t\t\t"+(String)emp.getValueAt(0, 0)+"\n"+
					rb.getString("in_finalSum")+"\t\t\t\t\t"+sum
					+"</pre>";
		
			header = header + scc;
			jta1.setText(header);
		}
	}
	
	public void Final() {
		String gg = "<center>=========================================</center><br>"+
				"<center><p><b>"+rb.getString("in_f1")+"<br>"+rb.getString("in_f2")+
				"</center></p></b></html>";
		header = header+gg;
		jta1.setText(header);
	}
	
	public void wordSpace(int wordLength) {
		String space = "\t";
		repeated = "";
		if(localeCurrent==0) {
			switch(wordLength) {
			case 2:
				repeated = new String(new char[4]).replace("\0", space);
				break;
			case 3:
				repeated = new String(new char[4]).replace("\0", space);
				break;
			case 4:
				repeated = new String(new char[4]).replace("\0", space);
				break;
			case 5:
				repeated = new String(new char[4]).replace("\0", space);
				break;
			case 6:
				repeated = new String(new char[3]).replace("\0", space);
				break;
			case 7:
				repeated = new String(new char[3]).replace("\0", space);
				break;
			case 8:
				repeated = new String(new char[3]).replace("\0", space);
				break;
			case 9:
				repeated = new String(new char[3]).replace("\0", space);
				break;
			case 10:
				repeated = new String(new char[3]).replace("\0", space);
				break;
			case 11:
				repeated = new String(new char[3]).replace("\0", space);
				break;
			case 12:
				repeated = new String(new char[3]).replace("\0", space);
				break;
			case 13:
				repeated = new String(new char[3]).replace("\0", space);
				break;
			case 14:
				repeated = new String(new char[3]).replace("\0", space);
				break;
			case 15:
				repeated = new String(new char[3]).replace("\0", space);
				break;
			case 16:
				repeated = new String(new char[2]).replace("\0", space);
				break;
			case 17:
				repeated = new String(new char[2]).replace("\0", space);
				break;
			case 18:
				repeated = new String(new char[2]).replace("\0", space);
				break;
			case 19:
				repeated = new String(new char[2]).replace("\0", space);
				break;
			case 20:
				repeated = new String(new char[2]).replace("\0", space);
				break;
			case 21:
				repeated = new String(new char[2]).replace("\0", space);
				break;
			default:
				repeated = new String(new char[1]).replace("\0", space);
				break;
			}
		}else {
			switch(wordLength) {
			case 2:
				repeated = new String(new char[5]).replace("\0", space);
				break;
			case 3:
				repeated = new String(new char[4]).replace("\0", space);
				break;
			case 4:
				repeated = new String(new char[4]).replace("\0", space);
				break;
			case 5:
				repeated = new String(new char[4]).replace("\0", space);
				break;
			case 6:
				repeated = new String(new char[3]).replace("\0", space);
				break;
			case 7:
				repeated = new String(new char[3]).replace("\0", space);
				break;
			case 8:
				repeated = new String(new char[3]).replace("\0", space);
				break;
			case 9:
				repeated = new String(new char[3]).replace("\0", space);
				break;
			case 10:
				repeated = new String(new char[3]).replace("\0", space);
				break;
			case 11:
				repeated = new String(new char[3]).replace("\0", space);
				break;
			case 12:
				repeated = new String(new char[3]).replace("\0", space);
				break;
			case 13:
				repeated = new String(new char[3]).replace("\0", space);
				break;
			case 14:
				repeated = new String(new char[3]).replace("\0", space);
				break;
			case 15:
				repeated = new String(new char[3]).replace("\0", space);
				break;
			case 16:
				repeated = new String(new char[2]).replace("\0", space);
				break;
			case 17:
				repeated = new String(new char[2]).replace("\0", space);
				break;
			case 18:
				repeated = new String(new char[2]).replace("\0", space);
				break;
			case 19:
				repeated = new String(new char[2]).replace("\0", space);
				break;
			case 20:
				repeated = new String(new char[2]).replace("\0", space);
				break;
			case 21:
				repeated = new String(new char[2]).replace("\0", space);
				break;
			default:
				repeated = new String(new char[1]).replace("\0", space);
				break;
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == print) {
			try {
				jta1.print();
			} catch (PrinterException e1) {
				e1.printStackTrace();
			}
		}
		
	}
}
