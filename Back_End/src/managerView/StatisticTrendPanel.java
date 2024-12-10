package managerView;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import managerView.StatisticProPanel.Item;
import model.EmpModel;
import tools.myFont;

public class StatisticTrendPanel extends JPanel implements ActionListener {
	JPanel m,up,p1,p2,p3,p4;
	JTable jt;
	JScrollPane jsp;
	JButton exit;
	JComboBox<Item> shop;
	JComboBox<String> year,month;
	DefaultTableModel tm;
	
	int nyear = 0;
	String nmonth = "";
	String sid = "";
	
	JFreeChart pieChart,pieChart2;
	ChartPanel panel;
	ChartPanel chartPanel;
	DecimalFormat df = new DecimalFormat("#.#");
	
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
	
	public StatisticTrendPanel(int locale) {
		localeCurrent = locale;
		initLocale();
		this.setLayout(new BorderLayout());
		
		Date date= new Date();
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int year = localDate.getYear();
		nyear = year;
		
		m = new JPanel(new GridLayout(2,0));
		
		upPanel();
		p3Panel();
		
		this.add(m,"Center");
	}
	
	public void upPanel() {
		up = new JPanel(new GridLayout(0,2));
		
		p1 = new JPanel(new BorderLayout());
		pieChart = ChartFactory.createPieChart(
	            "",
	            createDataset(0),
	            true, 
	            true,
	            false);
        pieChart.setTitle(new TextTitle(nyear+" "+rb.getString("st_cat"),myFont.f1));
        PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator(
                " {0} - ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
            ((PiePlot) pieChart.getPlot()).setLabelGenerator(labelGenerator);
            
        // Create Panel
        pieChart.getLegend().setItemFont(myFont.f1);
        PiePlot plot = (PiePlot) pieChart.getPlot();
        plot.setLabelFont(myFont.f1);
        panel = new ChartPanel(pieChart);
        
	    p1.add(panel,"Center");
	    
	    up2();
	    up.add(p1);
	    up.add(p2);
	    
	    m.add(up);
	}
	
	public void up2() {
	    p2 = new JPanel(new BorderLayout());
		pieChart2 = ChartFactory.createPieChart(
	            "",
	            createDataset1(0),
	            true, 
	            true,
	            false);
        pieChart2.setTitle(new TextTitle(nyear+" "+rb.getString("st_cf"),myFont.f1));
        PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator(
                " {0} - ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
            ((PiePlot) pieChart.getPlot()).setLabelGenerator(labelGenerator);
            
        // Create Panel
        pieChart2.getLegend().setItemFont(myFont.f1);
        PiePlot plot = (PiePlot) pieChart2.getPlot();
        plot.setLabelFont(myFont.f1);
        panel = new ChartPanel(pieChart2);
        
        p2.add(panel,"Center");
	}

	public void p3Panel() {
		p3 = new JPanel(new BorderLayout());
		
		tm = new DefaultTableModel(){
		    @Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};
		tm.addColumn("Name");
		tm.addColumn("Value");
		
		upjt(0);
		
		jt = new JTable(tm);
		jt.setAutoCreateRowSorter(true);
		jt.setRowHeight(30);
		jt.setFont(myFont.f1);
		jsp = new JScrollPane(jt);
		
		p4 = new JPanel(new FlowLayout());
		EmpModel emp = new EmpModel();
		
		String[] listOfYear = {nyear-2+"",nyear-1+"",nyear+"",nyear+1+"",nyear+2+""};
		year = new JComboBox<String>(listOfYear);
		year.setFont(myFont.f1);
		year.setSelectedIndex(2);
		year.addActionListener(this);
		p4.add(year);
		
		String[] listOfMon = {rb.getString("st_all"),"01","02","03","04","05","06",
				"07","08","09","10","11","12"};
		month = new JComboBox<String>(listOfMon);
		month.setFont(myFont.f1);
		month.setSelectedIndex(0);
		month.addActionListener(this);
		p4.add(month);
		
		String shopsql = "";
		if(localeCurrent==0) {
			shopsql = "select SHOP_ID, SHOP_NAME from fyp_shop where SHOP_FLAG='1' ";
		}else {
			shopsql = "select SHOP_ID, SHOP_NAME_ZH from fyp_shop where SHOP_FLAG='1' ";
		}
		emp.runSql(shopsql);
		Vector<Item> model = new Vector<Item>();
		model.addElement(new Item("all",rb.getString("st_sall")));
		for(int i=0;i<emp.getRowCount();i++) {
			model.addElement(new Item(emp.getValueAt(i, 0).toString(), emp.getValueAt(i, 1).toString()));
		}
		shop = new JComboBox<Item>(model);
		shop.setFont(myFont.f1);
		shop.setSelectedIndex(0);
		shop.addActionListener(this);
		p4.add(shop);
		
		exit = new JButton(rb.getString("common_exit"));
		exit.setFont(myFont.f1);
		exit.addActionListener(this);
		p4.add(exit);
		
		p3.add(jsp,"Center");
		p3.add(p4,"South");
		
		m.add(p3);
	}
	
	public void upjt(int flag) {
		EmpModel emp = new EmpModel();
		EmpModel emp1 = new EmpModel();
		String sql = "";
		String sql1 = "";
		
		if(localeCurrent==0) {
			sql = "select food_id, food_name from fyp_food";
		}else {
			sql = "select food_id, food_name_zh from fyp_food";
		}
		emp.runSql(sql);
		for(int i=0;i<emp.getRowCount();i++) {
			Vector<String> row = new Vector<String>();
			if(flag==0) {
				sql1 = "select count(*) from fyp_orderitem where "
					+ "oi_fflag='f' and oi_fid='"+emp.getValueAt(i, 0).toString()+"' " + 
					" and (oi_flag='1' or oi_flag='2') " + 
					" and oi_time LIKE ('"+nyear+"-%') ";
			}else if(flag==1) {
				sql1 = "select count(*) from fyp_orderitem where "  
					+ "oi_fflag='f' and oi_fid='"+emp.getValueAt(i, 0).toString()+"' " + 
					" and (oi_flag='1' or oi_flag='2') " + 
					" and oi_time LIKE ('"+nyear+"-"+nmonth+"-%') ";
			}else if(flag==2) {
				sql1 = "select count(*) from fyp_orderitem where "  
					+ "oi_fflag='f' and oi_fid='"+emp.getValueAt(i, 0).toString()+"' " + 
					" and (oi_flag='1' or oi_flag='2') " + 
					" and oi_sid='"+sid+"' " +
					" and oi_time LIKE ('"+nyear+"-%') ";
			}else if(flag==3) {
				sql1 = "select count(*) from fyp_orderitem where "  
					+ "oi_fflag='f' and oi_fid='"+emp.getValueAt(i, 0).toString()+"' " + 
					" and (oi_flag='1' or oi_flag='2') " + 
					" and oi_sid='"+sid+"' " +
					" and oi_time LIKE ('"+nyear+"-"+nmonth+"-%') ";
			}
			emp1.runSql(sql1);
			row.addElement(emp.getValueAt(i, 1).toString());
			row.addElement(emp1.getValueAt(0, 0).toString());
			tm.addRow(row);
		}
	}
	
	private DefaultPieDataset createDataset(int flag) {
		EmpModel emp = new EmpModel();
		String sql = "";
		if(localeCurrent==0) {
			sql = "select cat_id,cat_name from fyp_cat where cat_flag=0 order by length(cat_id),cat_id ";
		}else {
			sql = "select cat_id,cat_name_zh from fyp_cat where cat_flag=0 order by length(cat_id),cat_id ";
		}
		emp.runSql(sql);
		
		EmpModel emp1 = new EmpModel();
		String sql1 = "";
		DefaultPieDataset dataset=new DefaultPieDataset();
		for(int i=0;i<emp.getRowCount();i++) {
			if(flag==0) {
				sql1 = "select sum(REGEXP_COUNT(food_cat,'"+emp.getValueAt(i, 0).toString()+"')) from ( " + 
					" select food_cat from ( " + 
					" select ff.*,oi.* from fyp_food ff JOIN fyp_orderitem oi ON ff.food_id = oi.oi_fid ) " + 
					" where (oi_flag='1' or oi_flag='2') " + 
					" and oi_fflag='f' " +
					" and oi_time LIKE ('"+nyear+"-%')) ";
			}else if(flag==1) {
				sql1 = "select sum(REGEXP_COUNT(food_cat,'"+emp.getValueAt(i, 0).toString()+"')) from ( " + 
					" select food_cat from ( " + 
					" select ff.*,oi.* from fyp_food ff JOIN fyp_orderitem oi ON ff.food_id = oi.oi_fid ) " + 
					" where (oi_flag='1' or oi_flag='2') " + 
					" and oi_fflag='f' " +
					" and oi_time LIKE ('"+nyear+"-"+nmonth+"-%')) ";
			}else if(flag==2) {
				sql1 = "select sum(REGEXP_COUNT(food_cat,'"+emp.getValueAt(i, 0).toString()+"')) from ( " + 
					" select food_cat from ( " + 
					" select ff.*,oi.* from fyp_food ff JOIN fyp_orderitem oi ON ff.food_id = oi.oi_fid ) " + 
					" where (oi_flag='1' or oi_flag='2') " + 
					" and oi_sid='"+sid+"' " +
					" and oi_fflag='f' " +
					" and oi_time LIKE ('"+nyear+"-%')) ";
			}else if(flag==3) {
				sql1 = "select sum(REGEXP_COUNT(food_cat,'"+emp.getValueAt(i, 0).toString()+"')) from ( " + 
					" select food_cat from ( " + 
					" select ff.*,oi.* from fyp_food ff JOIN fyp_orderitem oi ON ff.food_id = oi.oi_fid ) " + 
					" where (oi_flag='1' or oi_flag='2') " + 
					" and oi_sid='"+sid+"' " +
					" and oi_fflag='f' " +
					" and oi_time LIKE ('"+nyear+"-"+nmonth+"-%')) ";
			}
			emp1.runSql(sql1);
			int catNo = 0;
			try {
				catNo = Integer.parseInt((String)emp1.getValueAt(0, 0));
			}catch (Exception e) {
				catNo = 0;
			}
			dataset.setValue(emp.getValueAt(i, 1).toString()+" : "+catNo, catNo);
		}
	    return dataset;
	}
	
	private DefaultPieDataset createDataset1(int flag) {
		EmpModel emp1 = new EmpModel();
		String sql1 = "";
		DefaultPieDataset dataset=new DefaultPieDataset();
		if(flag==0) {
			sql1 = "select count(*) from fyp_orderitem where "
				+ "oi_fflag='f' " + 
				" and (oi_flag='1' or oi_flag='2') " + 
				" and oi_time LIKE ('"+nyear+"-%') ";
		}else if(flag==1) {
			sql1 = "select count(*) from fyp_orderitem where "  
				+ "oi_fflag='f' " + 
				" and (oi_flag='1' or oi_flag='2') " + 
				" and oi_time LIKE ('"+nyear+"-"+nmonth+"-%') ";
		}else if(flag==2) {
			sql1 = "select count(*) from fyp_orderitem where "  
				+ "oi_fflag='f' " + 
				" and (oi_flag='1' or oi_flag='2') " + 
				" and oi_sid='"+sid+"' " +
				" and oi_time LIKE ('"+nyear+"-%') ";
		}else if(flag==3) {
			sql1 = "select count(*) from fyp_orderitem where "  
				+ "oi_fflag='f' " + 
				" and (oi_flag='1' or oi_flag='2') " + 
				" and oi_sid='"+sid+"' " +
				" and oi_time LIKE ('"+nyear+"-"+nmonth+"-%') ";
		}
		emp1.runSql(sql1);
		int fNo = Integer.parseInt((String)emp1.getValueAt(0, 0));
		dataset.setValue(rb.getString("st_food")+" : "+fNo, fNo);
		
		if(flag==0) {
			sql1 = "select count(*) from fyp_orderitem where "
				+ "oi_fflag='c' " + 
				" and (oi_flag='1' or oi_flag='2') " + 
				" and oi_time LIKE ('"+nyear+"-%') ";
		}else if(flag==1) {
			sql1 = "select count(*) from fyp_orderitem where "  
				+ "oi_fflag='c' " + 
				" and (oi_flag='1' or oi_flag='2') " + 
				" and oi_time LIKE ('"+nyear+"-"+nmonth+"-%') ";
		}else if(flag==2) {
			sql1 = "select count(*) from fyp_orderitem where "  
				+ "oi_fflag='c' " + 
				" and (oi_flag='1' or oi_flag='2') " + 
				" and oi_sid='"+sid+"' " +
				" and oi_time LIKE ('"+nyear+"-%') ";
		}else if(flag==3) {
			sql1 = "select count(*) from fyp_orderitem where "  
				+ "oi_fflag='c' " + 
				" and (oi_flag='1' or oi_flag='2') " + 
				" and oi_sid='"+sid+"' " +
				" and oi_time LIKE ('"+nyear+"-"+nmonth+"-%') ";
		}
		emp1.runSql(sql1);
		int sNo = Integer.parseInt((String)emp1.getValueAt(0, 0));
		dataset.setValue(rb.getString("st_set")+" : "+sNo, sNo);
		
	    return dataset;
	}
	
	public void monthUp(int flag){
		int yearcb = Integer.parseInt((String)year.getSelectedItem());
		int monthcb = month.getSelectedIndex();
		
		String monthget = (String)month.getSelectedItem();
		if(monthcb!=0) {
			int x=0;
			if(flag==0) {
				x=1;
			}else {
				x=3;
			}
			nyear=yearcb;
			nmonth=monthget;
			
			// cat Chart
			PieDataset dataset = createDataset(x);
			PiePlot plot = (PiePlot) pieChart.getPlot();
			plot.setDataset((PieDataset) dataset);
			pieChart.setTitle(new TextTitle(nyear+"/"+nmonth+" "+rb.getString("st_cat"),myFont.f1));
			
			// f vs c Chart
			PieDataset dataset2 = createDataset1(x);
			PiePlot plot2 = (PiePlot) pieChart2.getPlot();
			plot2.setDataset((PieDataset) dataset2);
			pieChart2.setTitle(new TextTitle(nyear+"/"+nmonth+" "+rb.getString("st_cf"),myFont.f1));
			
			// table
			tm.setRowCount(0);
			upjt(x);
			jt.setModel(tm);
			
		}else {
			int x=0;
			if(flag==0) {
				x=0;
			}else {
				x=2;
			}
			
			nyear=yearcb;
			
			// cat Chart
			PieDataset dataset = createDataset(x);
			PiePlot plot = (PiePlot) pieChart.getPlot();
			plot.setDataset((PieDataset) dataset);
			pieChart.setTitle(new TextTitle(nyear+" "+rb.getString("st_cat"),myFont.f1));
			
			// f vs c Chart
			PieDataset dataset2 = createDataset1(x);
			PiePlot plot2 = (PiePlot) pieChart2.getPlot();
			plot2.setDataset((PieDataset) dataset2);
			pieChart2.setTitle(new TextTitle(nyear+" "+rb.getString("st_cf"),myFont.f1));
			
			// table
			tm.setRowCount(0);
			upjt(x);
			jt.setModel(tm);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == exit) {
			this.setVisible(false);
		}else if(e.getSource() == year) {
			int yearcb = Integer.parseInt((String)year.getSelectedItem());
			nyear=yearcb;
			
			// cat Chart
			PieDataset dataset = createDataset(0);
			PiePlot plot = (PiePlot) pieChart.getPlot();
			plot.setDataset((PieDataset) dataset);
			pieChart.setTitle(new TextTitle(nyear+" "+rb.getString("st_cat"),myFont.f1));
			
			// f vs c Chart
			PieDataset dataset2 = createDataset1(0);
			PiePlot plot2 = (PiePlot) pieChart2.getPlot();
			plot2.setDataset((PieDataset) dataset2);
			pieChart2.setTitle(new TextTitle(nyear+" "+rb.getString("st_cf"),myFont.f1));
			
			// table
			tm.setRowCount(0);
			upjt(0);
			jt.setModel(tm);
		}else if(e.getSource() == month) {
			monthUp(0);
		}else if(e.getSource() == shop) {
			Item item = (Item)shop.getSelectedItem();
			String getsid = item.getId();
			if(getsid.equals("all")) {
				monthUp(0);
			}else {
				sid = getsid;
				monthUp(1);
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
