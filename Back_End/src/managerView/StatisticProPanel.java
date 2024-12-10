package managerView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
import javax.swing.table.TableCellRenderer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import model.EmpModel;
import tools.myColor;
import tools.myFont;

public class StatisticProPanel extends JPanel implements ActionListener {
	String sid = "";
	
	JPanel m,p1,p2,p3,p4;
	JTable jt1,jt2;
	JScrollPane jsp,jsp2;
	DefaultTableModel tm,tm2;
	JButton exit;
	
	int nyear = 0;
	int nmonth = 0;
	
	JFreeChart pieChart,pieChart2;
	ChartPanel panel;
	JFreeChart lineChart;
	ChartPanel chartPanel;
	DecimalFormat df = new DecimalFormat("#.#");
	
	JComboBox<Item> shop;
	JComboBox<String> year,month;
	
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
	
	public StatisticProPanel(int locale) {
		localeCurrent = locale;
		initLocale();
		
		Date date= new Date();
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int month = localDate.getMonthValue();
		int year = localDate.getYear();
		nyear = year;
		nmonth = month;
		
		m = new JPanel(new GridLayout(2,2));
		
		p1panel();
		p2panel();
		p3panel();
		p4panel();
		
		m.add(p1);
		m.add(p2);
		m.add(p3);
		m.add(p4);
		
		this.setLayout(new BorderLayout());
		this.add(m,"Center");
	}
	
	public void p1panel() {
		p1 = new JPanel(new BorderLayout());
		
		String x = rb.getString("st_mon");
		String y = rb.getString("st_pro");
		lineChart = ChartFactory.createLineChart(
		        "",x,y,
		    createDataset(0),
		    PlotOrientation.VERTICAL,
		    true,true,false);
		
		lineChart.setTitle(new TextTitle(nyear+" "+rb.getString("st_lctile"),myFont.f1));
        // Create Panel
		lineChart.getLegend().setItemFont(myFont.f1);
		CategoryPlot categoryplot = (CategoryPlot) lineChart.getPlot();
		CategoryAxis categoryaxis = categoryplot.getDomainAxis();
		categoryaxis.setLabelFont(myFont.f1);
		
		chartPanel = new ChartPanel(lineChart);
		p1.add(chartPanel,"Center");
	}
	
	private DefaultCategoryDataset createDataset(int flag) {  
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		String series1 = rb.getString("st_pro");  
	    String series2 = rb.getString("st_exp");  
	    
	    EmpModel emp1 = new EmpModel();
		String sql1 = "";
		if(flag==0) {
			sql1 = "select m_id, NVL( profit , 0 ) from " + 
					"(select to_char( p_date, 'MM' ) a ,sum(p_val) as profit " + 
					"from fyp_profit " + 
					"where EXTRACT ( YEAR FROM p_date ) IN ("+nyear+") " + 
					"group by to_char( p_date, 'MM' ) " + 
					"order by to_char( p_date, 'MM' )) " + 
					"right join fyp_month ON fyp_month.m_id = a " + 
					"order by m_id ";
		}else {
			sql1 = "select m_id, NVL( profit , 0 ) from " + 
					"(select to_char( p_date, 'MM' ) a ,sum(p_val) as profit " + 
					"from fyp_profit " + 
					"where EXTRACT ( YEAR FROM p_date ) IN ("+nyear+") " + 
					"and P_sid='"+sid+"' " +
					"group by to_char( p_date, 'MM' ) " + 
					"order by to_char( p_date, 'MM' )) " + 
					"right join fyp_month ON fyp_month.m_id = a " + 
					"order by m_id ";
		}

		emp1.runSql(sql1);
		for(int i=0;i<emp1.getRowCount();i++) {
			String mon = (String)emp1.getValueAt(i, 0);
			int profitPerMonth = Math.round(Float.parseFloat((String)emp1.getValueAt(i, 1)));
			
			dataset.addValue(profitPerMonth,series1,mon);
		}
		
		if(flag==0) {
			sql1 = "select m_id, NVL( exp , 0 ) from " + 
					"(select to_char( e_date, 'MM' ) a ,sum(e_v) as exp " + 
					"from fyp_expenses " + 
					"where EXTRACT ( YEAR FROM e_date ) IN ("+nyear+") " + 
					"group by to_char( e_date, 'MM' ) " + 
					"order by to_char( e_date, 'MM' )) " + 
					"right join fyp_month ON fyp_month.m_id = a " + 
					"order by m_id ";
		}else {
			sql1 = "select m_id, NVL( exp , 0 ) from " + 
					"(select to_char( e_date, 'MM' ) a ,sum(e_v) as exp " + 
					"from fyp_expenses " + 
					"where EXTRACT ( YEAR FROM e_date ) IN ("+nyear+") " + 
					"and e_sid='"+sid+"' " +
					"group by to_char( e_date, 'MM' ) " + 
					"order by to_char( e_date, 'MM' )) " + 
					"right join fyp_month ON fyp_month.m_id = a " + 
					"order by m_id ";
		}
		
		emp1.runSql(sql1);
		for(int i=0;i<emp1.getRowCount();i++) {
			String mon = (String)emp1.getValueAt(i, 0);
			int profitPerMonth = Math.round(Float.parseFloat((String)emp1.getValueAt(i, 1)));
			dataset.addValue(profitPerMonth,series2,mon);
		}
	    return dataset;
	}
	
	public void p2panel() {
		p2 = new JPanel(new BorderLayout());
		pieChart = ChartFactory.createPieChart(
	            "",
	            createDataset2(0),
	            true, 
	            true,
	            false);
        pieChart.setTitle(new TextTitle(nyear+"/"+nmonth+" "+rb.getString("st_pctitle"),myFont.f1));
        PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator(
                " {0} - ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
            ((PiePlot) pieChart.getPlot()).setLabelGenerator(labelGenerator);
            
        // Create Panel
        pieChart.getLegend().setItemFont(myFont.f1);
        PiePlot plot = (PiePlot) pieChart.getPlot();
        plot.setLabelFont(myFont.f1);
        panel = new ChartPanel(pieChart);
        
	    p2.add(panel,"Center");
	}
	
	private DefaultPieDataset createDataset2(int flag) {
		EmpModel emp1 = new EmpModel();
		String sql1 = "";
		
		if(flag==0) {
			sql1 = "select COUNT(*) from fyp_order where order_area=1 "
					+ "and EXTRACT( MONTH FROM order_date ) IN ( "+ nmonth +" ) "
					+ "and EXTRACT( YEAR FROM order_date ) IN ( "+ nyear +" ) ";
		}else if(flag==1) {
			sql1 = "select COUNT(*) from fyp_order where order_area=1 "
					+ "and EXTRACT( YEAR FROM order_date ) IN ( "+ nyear +" ) ";
		}else if(flag==2) {
			sql1 = "select COUNT(*) from fyp_order where order_area=1 "
					+ "and order_sid='"+sid+"' "
					+ "and EXTRACT( MONTH FROM order_date ) IN ( "+ nmonth +" ) "
					+ "and EXTRACT( YEAR FROM order_date ) IN ( "+ nyear +" ) ";
		}else if(flag==3) {
			sql1 = "select COUNT(*) from fyp_order where order_area=1 "
					+ "and order_sid='"+sid+"' "
					+ "and EXTRACT( YEAR FROM order_date ) IN ( "+ nyear +" ) ";
		}
		emp1.runSql(sql1);
		int dineinNo = Integer.parseInt((String)emp1.getValueAt(0, 0));
		if(flag==0) {
			sql1 = "select COUNT(*) from fyp_order where order_area=2 "
					+ "and EXTRACT( MONTH FROM order_date ) IN ( "+ nmonth +" ) "
					+ "and EXTRACT( YEAR FROM order_date ) IN ( "+ nyear +" ) ";
		}else if(flag==1) {
			sql1 = "select COUNT(*) from fyp_order where order_area=2 "
					+ "and EXTRACT( YEAR FROM order_date ) IN ( "+ nyear +" ) ";
		}else if(flag==2) {
			sql1 = "select COUNT(*) from fyp_order where order_area=2 "
					+ "and order_sid='"+sid+"' "
					+ "and EXTRACT( MONTH FROM order_date ) IN ( "+ nmonth +" ) "
					+ "and EXTRACT( YEAR FROM order_date ) IN ( "+ nyear +" ) ";
		}else if(flag==3) {
			sql1 = "select COUNT(*) from fyp_order where order_area=2 "
					+ "and order_sid='"+sid+"' "
					+ "and EXTRACT( YEAR FROM order_date ) IN ( "+ nyear +" ) ";
		}
		emp1.runSql(sql1);
		int takeoutNo = Integer.parseInt((String)emp1.getValueAt(0, 0));
		
	    DefaultPieDataset dataset=new DefaultPieDataset();
	    dataset.setValue("Dine in :"+dineinNo, dineinNo);
	    dataset.setValue("Take out :"+takeoutNo, takeoutNo);
	    return dataset;
	}
	
	public void p3panel() {
		JPanel mm = new JPanel(new GridLayout(2,0));
		
		p3 = new JPanel(new BorderLayout());
		
		EmpModel emp = new EmpModel();
		String sql = "";
		
		tm = new DefaultTableModel(){
		    @Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};
		tm.addColumn(rb.getString("st_sym"));
		tm.addColumn(rb.getString("st_tm"));
		tm.addColumn(rb.getString("st_lm"));
		tm.addColumn(rb.getString("st_lym"));
		
		monthDataCom(0);

		jt1 = new JTable(tm){
			@Override
		    public Component prepareRenderer (TableCellRenderer renderer, int rowIndex, int columnIndex){
		        Component componenet = super.prepareRenderer(renderer, rowIndex, columnIndex);
		        
		        String val = getModel().getValueAt(rowIndex,columnIndex).toString();
		        
		        if(rowIndex==3) {
		        	if(val.equals(" % ")) {
		        		componenet.setBackground(Color.white);
			            componenet.setForeground(Color.BLACK);
		        	}else if(val.equals(" NA ")) {
		        		componenet.setBackground(myColor.orange);
			            componenet.setForeground(Color.BLACK);
		        	}else if(val.contains("-")) {
		        		componenet.setBackground(myColor.red);
			            componenet.setForeground(Color.BLACK);
		        	}else {
		        		componenet.setBackground(myColor.green);
			            componenet.setForeground(Color.BLACK);
		        	}
		        }else {
		        	componenet.setBackground(Color.white);
		            componenet.setForeground(Color.BLACK);
		        }
		        return componenet;
		    }
		};
		jt1.setAutoCreateRowSorter(true);
		jt1.setRowHeight(30);
		jt1.setFont(myFont.f1);
		jsp = new JScrollPane(jt1);
		
		tm2 = new DefaultTableModel(){
		    @Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};
		tm2.addColumn(rb.getString("st_sym"));
		tm2.addColumn(rb.getString("st_ly"));
		tm2.addColumn(rb.getString("st_ty"));
		tm2.addColumn(rb.getString("st_ny"));
		
		yearDataCom(0);
		
		jt2 = new JTable(tm2){
			@Override
		    public Component prepareRenderer (TableCellRenderer renderer, int rowIndex, int columnIndex){
		        Component componenet = super.prepareRenderer(renderer, rowIndex, columnIndex);
		        
		        String val = getModel().getValueAt(rowIndex,columnIndex).toString();
		        
		        if(rowIndex==3) {
		        	if(val.equals(" % ")) {
		        		componenet.setBackground(Color.white);
			            componenet.setForeground(Color.BLACK);
		        	}else if(val.equals(" NA ")) {
		        		componenet.setBackground(myColor.orange);
			            componenet.setForeground(Color.BLACK);
		        	}else if(val.contains("-")) {
		        		componenet.setBackground(myColor.red);
			            componenet.setForeground(Color.BLACK);
		        	}else {
		        		componenet.setBackground(myColor.green);
			            componenet.setForeground(Color.BLACK);
		        	}
		        }else {
		        	componenet.setBackground(Color.white);
		            componenet.setForeground(Color.BLACK);
		        }
		        return componenet;
		    }
		};
		jt2.setAutoCreateRowSorter(true);
		jt2.setRowHeight(30);
		jt2.setFont(myFont.f1);
		jsp2 = new JScrollPane(jt2);
		
		mm.add(jsp);
		mm.add(jsp2);
		
		//Control Panel
		JPanel hh = new JPanel(new FlowLayout());
		
		String[] listOfYear = {nyear-2+"",nyear-1+"",nyear+"",nyear+1+"",nyear+2+""};
		year = new JComboBox<String>(listOfYear);
		year.setFont(myFont.f1);
		year.setSelectedIndex(2);
		year.addActionListener(this);
		hh.add(year);
		
		String[] listOfMon = {rb.getString("st_all"),"1","2","3","4","5","6","7","8","9","10","11","12"};
		month = new JComboBox<String>(listOfMon);
		month.setFont(myFont.f1);
		month.setSelectedIndex(nmonth);
		month.addActionListener(this);
		hh.add(month);
		
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
		hh.add(shop);
		
		exit = new JButton(rb.getString("common_exit"));
		exit.setFont(myFont.f1);
		exit.addActionListener(this);
		hh.add(exit);
		
		p3.add(mm,"Center");
		p3.add(hh,"South");
	}
	
	public void monthDataCom(int flag) {
		EmpModel emp = new EmpModel();
		String sql = "";
		Vector<String> pro = new Vector<String>();

		// profit on 3 version
		pro.addElement(" + ");
		if(flag==0) {
			sql = "select sum(p_val) from fyp_profit where "
					+ "EXTRACT( MONTH FROM p_date ) IN ( "+ nmonth +" ) and "
					+ "EXTRACT( YEAR FROM p_date ) IN ( "+ nyear +" ) ";
		}else {
			sql = "select sum(p_val) from fyp_profit where "
					+ "p_sid = '"+sid+"' and "
					+ "EXTRACT( MONTH FROM p_date ) IN ( "+ nmonth +" ) and "
					+ "EXTRACT( YEAR FROM p_date ) IN ( "+ nyear +" ) ";
		}
		
		emp.runSql(sql);
		float thispro = 0;
		try {
			thispro = Float.parseFloat(emp.getValueAt(0, 0).toString());
		}catch (Exception e2) {
			System.out.println("this pro is null");
		}
		pro.addElement(thispro+"");
		
		int nm = 0;
		int ny = 0;
		if(nmonth-1==0) {
			nm = 12;
			ny = nyear-1;
		}else {
			nm = nmonth-1;
			ny = nyear;
		}
		
		if(flag==0) {
			sql = "select sum(p_val) from fyp_profit where "
					+ "EXTRACT( MONTH FROM p_date ) IN ( "+ nm +" ) and "
					+ "EXTRACT( YEAR FROM p_date ) IN ( "+ ny +" ) ";
		}else {
			sql = "select sum(p_val) from fyp_profit where "
					+ "p_sid='"+sid+"' and "
					+ "EXTRACT( MONTH FROM p_date ) IN ( "+ nm +" ) and "
					+ "EXTRACT( YEAR FROM p_date ) IN ( "+ ny +" ) ";
		}
		emp.runSql(sql);
		float lastpro = 0;
		try {
			lastpro = Float.parseFloat(emp.getValueAt(0, 0).toString());
		}catch (Exception e2) {
			System.out.println("last pro is null");
		}
		pro.addElement(lastpro+"");
		
		if(flag==0) {
			sql = "select sum(p_val) from fyp_profit where "
					+ "EXTRACT( MONTH FROM p_date ) IN ( "+ nmonth +" ) and "
					+ "EXTRACT( YEAR FROM p_date ) IN ( "+ (nyear-1) +" ) ";
		}else {
			sql = "select sum(p_val) from fyp_profit where "
					+ "p_sid='"+sid+"' and "
					+ "EXTRACT( MONTH FROM p_date ) IN ( "+ nmonth +" ) and "
					+ "EXTRACT( YEAR FROM p_date ) IN ( "+ (nyear-1) +" ) ";
		}
		emp.runSql(sql);
		float lastypro = 0;
		try {
			lastypro = Float.parseFloat(emp.getValueAt(0, 0).toString());
		}catch (Exception e2) {
			System.out.println("last year pro is null");
		}
		pro.addElement(lastypro+"");
		
		Vector<String> exp = new Vector<String>();
		// Expenses
		exp.addElement(" - ");
		float thisexp = 0;
		if(flag==0) {
			sql = "select sum(e_v) from fyp_expenses where "
					+ "EXTRACT( MONTH FROM e_date ) IN ( "+ nmonth +" ) and "
					+ "EXTRACT( YEAR FROM e_date ) IN ( "+ nyear +" ) ";
		}else {
			sql = "select sum(e_v) from fyp_expenses where "
					+ "e_sid = '"+sid+"' and "
					+ "EXTRACT( MONTH FROM e_date ) IN ( "+ nmonth +" ) and "
					+ "EXTRACT( YEAR FROM e_date ) IN ( "+ nyear +" ) ";
		}
		emp.runSql(sql);
		try {
			thisexp = Float.parseFloat(emp.getValueAt(0, 0).toString());
		}catch (Exception e2) {
			System.out.println("exp is null");
		}
		exp.addElement(thisexp+"");
		
		float lastexp = 0;
		if(flag==0) {
			sql = "select sum(e_v) from fyp_expenses where "
					+ "EXTRACT( MONTH FROM e_date ) IN ( "+ nm +" ) and "
					+ "EXTRACT( YEAR FROM e_date ) IN ( "+ ny +" ) ";
		}else {
			sql = "select sum(e_v) from fyp_expenses where "
					+ "e_sid='"+sid+"' and "
					+ "EXTRACT( MONTH FROM e_date ) IN ( "+ nm +" ) and "
					+ "EXTRACT( YEAR FROM e_date ) IN ( "+ ny +" ) ";
		}
		emp.runSql(sql);
		try {
			lastexp = Float.parseFloat(emp.getValueAt(0, 0).toString());
		}catch (Exception e2) {
			System.out.println("exp is null");
		}
		exp.addElement(lastexp+"");
		
		float lastyexp = 0;
		if(flag==0) {
			sql = "select sum(e_v) from fyp_expenses where "
					+ "EXTRACT( MONTH FROM e_date ) IN ( "+ nmonth +" ) and "
					+ "EXTRACT( YEAR FROM e_date ) IN ( "+ (nyear-1) +" ) ";
		}else {
			sql = "select sum(e_v) from fyp_expenses where "
					+ "e_sid='"+sid+"' and "
					+ "EXTRACT( MONTH FROM e_date ) IN ( "+ nmonth +" ) and "
					+ "EXTRACT( YEAR FROM e_date ) IN ( "+ (nyear-1) +" ) ";
		}
		emp.runSql(sql);
		try {
			lastyexp = Float.parseFloat(emp.getValueAt(0, 0).toString());
		}catch (Exception e2) {
			System.out.println("exp is null");
		}
		exp.addElement(lastyexp+"");
		
		//Sum
		Vector<String> sum = new Vector<String>();
		BigDecimal tp = new BigDecimal(thispro);
		BigDecimal te = new BigDecimal(thisexp);
		BigDecimal tsum = tp.subtract(te);
		
		BigDecimal lp = new BigDecimal(lastpro);
		BigDecimal le = new BigDecimal(lastexp);
		BigDecimal lsum = lp.subtract(le);
		
		BigDecimal lyp = new BigDecimal(lastypro);
		BigDecimal lye = new BigDecimal(lastyexp);
		BigDecimal lysum = lyp.subtract(lye);
		sum.addElement(" = ");
		sum.addElement(df.format(tsum).toString());
		sum.addElement(df.format(lsum).toString());
		sum.addElement(df.format(lysum).toString());
		
		// different
		Vector<String> diff = new Vector<String>();
		diff.addElement(" % ");
		diff.addElement(" NA ");
		try {
			BigDecimal ld = ((tsum.subtract(lsum)).divide(lsum,5, RoundingMode.HALF_UP))
					.multiply(new BigDecimal(100));
			diff.addElement(df.format(ld).toString()+"%");
		}catch (Exception e) {
			diff.addElement(" NA ");
		}
		try {
			BigDecimal lyd = ((tsum.subtract(lysum)).divide(lysum,5, RoundingMode.HALF_UP))
					.multiply(new BigDecimal(100));
			diff.addElement(df.format(lyd).toString()+"%");
		}catch (Exception e) {
			diff.addElement(" NA ");
		}
		tm.addRow(pro);
		tm.addRow(exp);
		tm.addRow(sum);
		tm.addRow(diff);
	}
	
	
	public void yearDataCom(int flag) {
		EmpModel emp = new EmpModel();
		String sql = "";
		
		Vector<String> proy = new Vector<String>();

		// profit on 3 version
		proy.addElement(" + ");
		if(flag==0) {
			sql = "select sum(p_val) from fyp_profit where EXTRACT( YEAR FROM p_date ) IN ( "+ (nyear-1) +" ) ";
		}else {
			sql = "select sum(p_val) from fyp_profit where "
					+ "p_sid='"+sid+"' and EXTRACT( YEAR FROM p_date ) IN ( "+ (nyear-1) +" ) ";
		}
		emp.runSql(sql);
		float lastproy = 0;
		try {
			lastproy = Float.parseFloat(emp.getValueAt(0, 0).toString());
		}catch (Exception e2) {
			System.out.println("Last year pro is null");
		}
		proy.addElement(lastproy+"");
		
		if(flag==0) {
			sql = "select sum(p_val) from fyp_profit where EXTRACT( YEAR FROM p_date ) IN ( "+ (nyear) +" ) ";
		}else {
			sql = "select sum(p_val) from fyp_profit where "
					+ "p_sid='"+sid+"' and EXTRACT( YEAR FROM p_date ) IN ( "+ (nyear) +" ) ";
		}
		emp.runSql(sql);
		float thisproy = 0;
		try {
			thisproy = Float.parseFloat(emp.getValueAt(0, 0).toString());
		}catch (Exception e2) {
			System.out.println("This year pro is null");
		}
		proy.addElement(thisproy+"");
		
		if(flag==0) {
			sql = "select sum(p_val) from fyp_profit where EXTRACT( YEAR FROM p_date ) IN ( "+ (nyear+1) +" ) ";
		}else {
			sql = "select sum(p_val) from fyp_profit where "
					+ "p_sid='"+sid+"' and EXTRACT( YEAR FROM p_date ) IN ( "+ (nyear+1) +" ) ";
		}
		emp.runSql(sql);
		float nextproy = 0;
		try {
			nextproy = Float.parseFloat(emp.getValueAt(0, 0).toString());
		}catch (Exception e2) {
			System.out.println("Next year pro is null");
		}
		proy.addElement(nextproy+"");
		
		// Expenses
		Vector<String> expy = new Vector<String>();

		expy.addElement(" - ");
		float lastexpy = 0;
		if(flag==0) {
			sql = "select sum(e_v) from fyp_expenses where EXTRACT( YEAR FROM e_date ) IN ( "+ (nyear-1) +" ) ";
		}else {
			sql = "select sum(e_v) from fyp_expenses where "
					+ "e_sid='"+sid+"' and EXTRACT( YEAR FROM e_date ) IN ( "+ (nyear-1) +" ) ";
		}
		emp.runSql(sql);
		try {
			lastexpy = Float.parseFloat(emp.getValueAt(0, 0).toString());
		}catch (Exception e2) {
			System.out.println("last exp is null");
		}
		expy.addElement(lastexpy+"");
		
		float thisexpy = 0;
		if(flag==0) {
			sql = "select sum(e_v) from fyp_expenses where EXTRACT( YEAR FROM e_date ) IN ( "+ (nyear) +" ) ";
		}else {
			sql = "select sum(e_v) from fyp_expenses where "
					+ "e_sid='"+sid+"' and EXTRACT( YEAR FROM e_date ) IN ( "+ (nyear) +" ) ";
		}
		emp.runSql(sql);
		try {
			thisexpy = Float.parseFloat(emp.getValueAt(0, 0).toString());
		}catch (Exception e2) {
			System.out.println("This exp is null");
		}
		expy.addElement(thisexpy+"");
		
		float nextexpy = 0;
		if(flag==0) {
			sql = "select sum(e_v) from fyp_expenses where EXTRACT( YEAR FROM e_date ) IN ( "+ (nyear+1) +" ) ";
		}else {
			sql = "select sum(e_v) from fyp_expenses where "
					+ "e_sid='"+sid+"' and EXTRACT( YEAR FROM e_date ) IN ( "+ (nyear+1) +" ) ";
		}
		emp.runSql(sql);
		try {
			nextexpy = Float.parseFloat(emp.getValueAt(0, 0).toString());
		}catch (Exception e2) {
			System.out.println("next exp is null");
		}
		expy.addElement(nextexpy+"");
		
		//Sum
		Vector<String> sumy = new Vector<String>();
		BigDecimal tpy = new BigDecimal(thisproy);
		BigDecimal tey = new BigDecimal(thisexpy);
		BigDecimal tsumy = tpy.subtract(tey);
		
		BigDecimal lpy = new BigDecimal(lastproy);
		BigDecimal ley = new BigDecimal(lastexpy);
		BigDecimal lsumy = lpy.subtract(ley);
		
		BigDecimal npy = new BigDecimal(nextproy);
		BigDecimal ney = new BigDecimal(nextexpy);
		BigDecimal nsumy = npy.subtract(ney);
		sumy.addElement(" = ");
		sumy.addElement(df.format(lsumy).toString());
		sumy.addElement(df.format(tsumy).toString());
		sumy.addElement(df.format(nsumy).toString());
		
		// Difference
		Vector<String> diffy = new Vector<String>();
		diffy.addElement(" % ");
		try {
			BigDecimal lypd = ((tsumy.subtract(lsumy)).divide(lsumy,5, RoundingMode.HALF_UP))
					.multiply(new BigDecimal(100));
			diffy.addElement(df.format(lypd).toString()+"%");
			
		}catch (Exception e) {
			diffy.addElement(" NA ");
		}
		diffy.addElement(" NA ");
		try {
			BigDecimal nypd = ((tsumy.subtract(nsumy)).divide(nsumy,5, RoundingMode.HALF_UP))
					.multiply(new BigDecimal(100));
			diffy.addElement(df.format(nypd).toString()+"%");
		}catch (Exception e) {
			diffy.addElement(" NA ");
		}
		
		tm2.addRow(proy);
		tm2.addRow(expy);
		tm2.addRow(sumy);
		tm2.addRow(diffy);
	}
	
	public void p4panel() {
		p4 = new JPanel(new BorderLayout());
		pieChart2 = ChartFactory.createPieChart(
	            "",
	            createDataset3(0),
	            true, 
	            true,
	            false);
		pieChart2.setTitle(new TextTitle(nyear+"/"+nmonth+" "+rb.getString("st_pc2title"),myFont.f1));
        PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator(
                " {0} - ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
            ((PiePlot) pieChart2.getPlot()).setLabelGenerator(labelGenerator);
            
        // Create Panel
        pieChart2.getLegend().setItemFont(myFont.f1);
        PiePlot plot = (PiePlot) pieChart2.getPlot();
        plot.setLabelFont(myFont.f1);
        panel = new ChartPanel(pieChart2);
        
	    p4.add(panel,"Center");
	}
	
	private DefaultPieDataset createDataset3(int flag) {
		DefaultPieDataset dataset=new DefaultPieDataset();
		
		EmpModel emp = new EmpModel();
		String sql = "";
		if(localeCurrent==0) {
			sql = "select p_id,p_n from fyp_paymethod order by p_id ASC ";
		}else {
			sql = "select p_id,p_nzh from fyp_paymethod order by p_id ASC ";
		}
		emp.runSql(sql);
		
		String sql1 = "";
		EmpModel emp1 = new EmpModel();
		for(int i=0;i<emp.getRowCount();i++) {
			if(flag==0) {
				sql1 = "select COUNT(*) from fyp_profit where p_method='"+emp.getValueAt(i, 0).toString()+"' "
						+ "and EXTRACT( MONTH FROM p_date ) IN ( "+ nmonth +" ) "
						+ "and EXTRACT( YEAR FROM p_date ) IN ( "+ nyear +" ) ";
			}else if(flag==1){
				sql1 = "select COUNT(*) from fyp_profit where p_method='"+emp.getValueAt(i, 0).toString()+"' "
						+ "and EXTRACT( YEAR FROM p_date ) IN ( "+ nyear +" ) ";
			}else if(flag==2){
				sql1 = "select COUNT(*) from fyp_profit where p_method='"+emp.getValueAt(i, 0).toString()+"' "
						+ "and p_sid = '"+sid+"' "
						+ "and EXTRACT( MONTH FROM p_date ) IN ( "+ nmonth +" ) "
						+ "and EXTRACT( YEAR FROM p_date ) IN ( "+ nyear +" ) ";
			}else if(flag==3){
				sql1 = "select COUNT(*) from fyp_profit where p_method='"+emp.getValueAt(i, 0).toString()+"' "
						+ "and p_sid = '"+sid+"' "
						+ "and EXTRACT( YEAR FROM p_date ) IN ( "+ nyear +" ) ";
			}
			emp1.runSql(sql1);
			int x = Integer.parseInt((String)emp1.getValueAt(0, 0));
			dataset.setValue(emp.getValueAt(i, 1).toString()+" : "+x, x);
		}
	    return dataset;
	}
	
	
	public void updateM(int flag) {
		int yearcb = Integer.parseInt((String)year.getSelectedItem());
		int monthcb = month.getSelectedIndex();
		
		if(monthcb!=0) {
			int x=0;
			int y=0;
			if(flag==0) {
				x=0;
				y=0;
			}else {
				x=2;
				y=1;
			}
			
			nyear=yearcb;
			nmonth=monthcb;
			
			// Line Chart Change
			CategoryDataset dataset1 = createDataset(y);
			CategoryPlot plot1 = (CategoryPlot) lineChart.getPlot();
			plot1.setDataset((CategoryDataset) dataset1);
			lineChart.setTitle(new TextTitle(nyear+" "+rb.getString("st_lctile"),myFont.f1));
			
			// Order Area Chart
			PieDataset dataset2 = createDataset2(x);
			PiePlot plot2 = (PiePlot) pieChart.getPlot();
			plot2.setDataset((PieDataset) dataset2);
			pieChart.setTitle(new TextTitle(nyear+"/"+nmonth+" "+rb.getString("st_pctitle"),myFont.f1));
			
			// Payment Method Chart
			PieDataset dataset3 = createDataset3(x);
			PiePlot plot3 = (PiePlot) pieChart2.getPlot();
			plot3.setDataset((PieDataset) dataset3);
			pieChart2.setTitle(new TextTitle(nyear+"/"+nmonth+" "+rb.getString("st_pc2title"),myFont.f1));
			
			// Table panel 3
			tm.setRowCount(0);
			monthDataCom(0);
			jt1.setModel(tm);
			
			tm2.setRowCount(0);
			yearDataCom(0);
			jt2.setModel(tm2);
		}else {
			int x=0;
			if(flag==0) {
				x=1;
			}else {
				x=3;
			}
			// Order Area Chart
			PieDataset dataset2 = createDataset2(x);
			PiePlot plot2 = (PiePlot) pieChart.getPlot();
			plot2.setDataset((PieDataset) dataset2);
			pieChart.setTitle(new TextTitle(nyear+" "+rb.getString("st_pctitle"),myFont.f1));
			
			// Payment Method Chart
			PieDataset dataset3 = createDataset3(x);
			PiePlot plot3 = (PiePlot) pieChart2.getPlot();
			plot3.setDataset((PieDataset) dataset3);
			pieChart2.setTitle(new TextTitle(nyear+" "+rb.getString("st_pc2title"),myFont.f1));
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == year) {
			int yearcb = Integer.parseInt((String)year.getSelectedItem());
			nyear=yearcb;
			
			// Line Chart Change
			CategoryDataset dataset1 = createDataset(0);
			CategoryPlot plot1 = (CategoryPlot) lineChart.getPlot();
			plot1.setDataset((CategoryDataset) dataset1);
			lineChart.setTitle(new TextTitle(nyear+" "+rb.getString("st_lctile"),myFont.f1));
			
			// Order Area Chart
			PieDataset dataset2 = createDataset2(0);
			PiePlot plot2 = (PiePlot) pieChart.getPlot();
			plot2.setDataset((PieDataset) dataset2);
			pieChart.setTitle(new TextTitle(nyear+"/"+nmonth+" "+rb.getString("st_pctitle"),myFont.f1));
			
			// Payment Method Chart
			PieDataset dataset3 = createDataset3(0);
			PiePlot plot3 = (PiePlot) pieChart2.getPlot();
			plot3.setDataset((PieDataset) dataset3);
			pieChart2.setTitle(new TextTitle(nyear+"/"+nmonth+" "+rb.getString("st_pc2title"),myFont.f1));
			
			// Table panel 3
			tm.setRowCount(0);
			monthDataCom(0);
			jt1.setModel(tm);
			
			tm2.setRowCount(0);
			yearDataCom(0);
			jt2.setModel(tm2);
		}else if(e.getSource() == month) {
			updateM(0);
		}else if(e.getSource() == shop) {
			Item item = (Item)shop.getSelectedItem();
			String getsid = item.getId();
			if(getsid.equals("all")) {
				updateM(0);
			}else {
				sid = getsid;
				updateM(1);
				
				tm.setRowCount(0);
				monthDataCom(1);
				jt1.setModel(tm);
				
				tm2.setRowCount(0);
				yearDataCom(1);
				jt2.setModel(tm2);
			}
		}else if(e.getSource() == exit) {
			this.setVisible(false);
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
