package aqtap_main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.Color;
import java.awt.Dimension;
import java.sql.PreparedStatement;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Toolkit;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.jdbc.JDBCCategoryDataset;
import Charts.ChartFrame;
import Charts.ChartFrame1;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import javax.swing.SwingConstants;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.ui.TextAnchor;
/**
 *
 * @author 906729
 */
public class Queue extends javax.swing.JInternalFrame {

    private Object plot;

    /**
     * Creates new form Queue
     */
    public Queue() {
        initComponents();
        setTitle("Queue Length Estimate");
        
        
        
    }
     public Connection getConnection(){
            Connection conn = null;
            conn = javaconnect.ConnectDb();
            try{


            }catch (Exception e ){
                System.out.println(e.getMessage());

            }
            return conn;
        }
    
           public void findFlowrateComparativerFresh() throws SQLException
    {
        JDialog ProcessingDialog = new JDialog();
        JLabel label = new JLabel("Please wait...",SwingConstants.CENTER);
        ProcessingDialog.setPreferredSize(new Dimension(500,100));
        ProcessingDialog.setLocationRelativeTo(null);
        ProcessingDialog.setTitle("This might take a minute, Please Wait...");
        ProcessingDialog.add(label);
        ProcessingDialog.pack();      

        ProcessingDialog.setVisible(true);

        
        ArrayList<FlowrateComparativeTable> flowrateTimeC = new ArrayList<FlowrateComparativeTable>();
        
        DefaultTableModel model = new DefaultTableModel();
        
        model.setColumnIdentifiers(new Object[] {"Date","Dispenser","Average Flowrate"});
        
        Object[] row = new Object [3];
        
    
    
        Statement st;
        ResultSet rs;
    
   
        Connection conn = getConnection();
        st = conn.createStatement();
        String sql = " SELECT CAST(T.START_TIME AS DATE) AS DATE,U.UNIT_NAME AS DISPENSER,ROUND(AVG(T.FLOW_RATE),1) AS AVG_FLOWRATE\n" +
        "        \n" +
        " FROM\n" +
        "    TRANS T\n" +
        "        JOIN\n" +
        "    UNIT U ON T.UNIT_ID = U.ID" +
        "	WHERE\n" +
        "	    T.VOLUME_RELEASED IS NOT NULL\n" +
        "         \n" +
        "        AND CAST(T.U_LOG_RTC AS DATE) BETWEEN '"+((JTextField)startDate1.getDateEditor().getUiComponent()).getText() +"' AND '"+((JTextField)endDate1.getDateEditor().getUiComponent()).getText() +"'\n" +
        "AND U.GROUP_ID = '"+location.getSelectedItem()+"' GROUP BY CAST(T.LOG_RTC AS DATE) ORDER BY U.UNIT_NAME,T.U_LOG_RTC ASC" +
        "; ";
        System.out.println(sql);
        rs=st.executeQuery(sql);

        System.out.println("rs-->"+rs);

        FlowrateComparativeTable flowrate;

        while (rs.next())
        {

        //Do the job

            flowrate = new FlowrateComparativeTable(
            rs.getDate("DATE"),
            rs.getString("DISPENSER"),
             rs.getBigDecimal("AVG_FLOWRATE")      


            );

            flowrateTimeC.add(flowrate);

        }
                
        for(int i = 0; i<flowrateTimeC.size(); i++)
        {
            row[0] = flowrateTimeC.get(i).getDATE();
            row[1] = flowrateTimeC.get(i).getDISPENSER();        
            row[2] = flowrateTimeC.get(i).getAVG_FLOWRATE();

            
            model.addRow(row);
           
        }
        FlowrateComparativeTable.setModel(model); 

        ProcessingDialog.setVisible(false);
    
    }
           
      public void findFlowrateComparative() throws SQLException
    {
    JDialog ProcessingDialog = new JDialog();
    JLabel label = new JLabel("Please wait...",SwingConstants.CENTER);
    ProcessingDialog.setPreferredSize(new Dimension(500,100));
    ProcessingDialog.setLocationRelativeTo(null);
    ProcessingDialog.setTitle("This might take a minute, Please Wait...");
    ProcessingDialog.add(label);
    ProcessingDialog.pack();      

    ProcessingDialog.setVisible(true);

        
    ArrayList<FlowrateComparativeTable> flowrateTimeC = new ArrayList<FlowrateComparativeTable>();

    DefaultTableModel model = new DefaultTableModel();

    model.setColumnIdentifiers(new Object[] {"Date","Dispenser","Average Flowrate"});

    Object[] row = new Object [3];
        
    
    
    Statement st;
    ResultSet rs;
    
   
    Connection conn = getConnection();
    st = conn.createStatement();
    String sql = " SELECT CAST(T.START_TIME AS DATE) AS DATE,U.UNIT_NAME AS DISPENSER,ROUND(AVG(T.FLOW_RATE),1) AS AVG_FLOWRATE\n" +
    "        \n" +
    " FROM\n" +
    "    TRANS T\n" +
    "        JOIN\n" +
    "    UNIT U ON T.UNIT_ID = U.ID" +
    "	WHERE\n" +
    "	    T.VOLUME_RELEASED IS NOT NULL\n" +
    "         \n" +
    "        AND CAST(T.U_LOG_RTC AS DATE) BETWEEN '"+((JTextField)startDate.getDateEditor().getUiComponent()).getText() +"' AND '"+((JTextField)endDate.getDateEditor().getUiComponent()).getText() +"'\n" +
    "AND U.GROUP_ID = '"+location.getSelectedItem()+"' GROUP BY CAST(T.LOG_RTC AS DATE) ORDER BY U.UNIT_NAME,T.U_LOG_RTC ASC" +
    "; ";
    System.out.println(sql);
    rs=st.executeQuery(sql);

    System.out.println("rs-->"+rs);

    FlowrateComparativeTable flowrate;

    while (rs.next())
            {
             
            //Do the job
            
           
            flowrate = new FlowrateComparativeTable(
            rs.getDate("DATE"),
            rs.getString("DISPENSER"),
             rs.getBigDecimal("AVG_FLOWRATE")      



            );

            flowrateTimeC.add(flowrate);
                
                
                
            }
            
        
        
        for(int i = 0; i<flowrateTimeC.size(); i++)
        {
            row[0] = flowrateTimeC.get(i).getDATE();
            row[1] = flowrateTimeC.get(i).getDISPENSER();        
            row[2] = flowrateTimeC.get(i).getAVG_FLOWRATE();

            
            model.addRow(row);
           
        }
        FlowrateComparativeTable.setModel(model); 

    ProcessingDialog.setVisible(false);
    
    }
          
          
       public void findFlowrate() throws SQLException
    {
        JDialog ProcessingDialog = new JDialog();
        JLabel label = new JLabel("Please wait...",SwingConstants.CENTER);
        ProcessingDialog.setPreferredSize(new Dimension(500,100));
        ProcessingDialog.setLocationRelativeTo(null);
        ProcessingDialog.setTitle("This might take a minute, Please Wait...");
        ProcessingDialog.add(label);
        ProcessingDialog.pack();      

        ProcessingDialog.setVisible(true);

        
        ArrayList<FlowrateTable> flowrateTime = new ArrayList<FlowrateTable>();
        
        DefaultTableModel model = new DefaultTableModel();
        
        model.setColumnIdentifiers(new Object[] {"Date","Dispenser","Min Flowrate", "Max Flowrate","Average Flowrate"});
        
        Object[] row = new Object [5];

        Statement st;
        ResultSet rs;
        Connection conn = getConnection();
        st = conn.createStatement();
        String sql = " SELECT CAST(T.START_TIME AS DATE) AS DATE,U.UNIT_NAME AS DISPENSER,ROUND(MAX(T.FLOW_RATE),1) AS MAX_FLOWRATE,ROUND(MIN(T.FLOW_RATE),1) AS MIN_FLOWRATE,ROUND(AVG(T.FLOW_RATE),1) AS AVG_FLOWRATE\n" +
        "        \n" +
        " FROM  TRANS T\n" +
        "        JOIN\n" +
        "    UNIT U ON T.UNIT_ID = U.ID" +
        "	WHERE\n" +
        "	    T.VOLUME_RELEASED IS NOT NULL\n" +
        "        AND U.UNIT_NAME = '"+dispenser.getSelectedItem()+"'  \n" +
        "        AND CAST(T.U_LOG_RTC AS DATE) BETWEEN '"+((JTextField)startDate.getDateEditor().getUiComponent()).getText() +"' AND '"+((JTextField)endDate.getDateEditor().getUiComponent()).getText() +"'\n" +
        "AND U.GROUP_ID = '"+location.getSelectedItem()+"' GROUP BY CAST(T.LOG_RTC AS DATE) ORDER BY T.U_LOG_RTC ASC" +
        "; ";
        System.out.println(sql);
        rs=st.executeQuery(sql);

        System.out.println("rs-->"+rs);

        FlowrateTable flowrate;

        while (rs.next())
            {
             
            //Do the job
            
           
                flowrate = new FlowrateTable(
                rs.getDate("DATE"),
                rs.getString("DISPENSER"),
                rs.getBigDecimal("MIN_FLOWRATE"),
                rs.getBigDecimal("MAX_FLOWRATE"),
                 rs.getBigDecimal("AVG_FLOWRATE")      
               
                        
                
                );
                
                flowrateTime.add(flowrate);
                
                
                
            }
            
        
        
        for(int i = 0; i<flowrateTime.size(); i++)
        {
            row[0] = flowrateTime.get(i).getDATE();
            row[1] = flowrateTime.get(i).getDISPENSER();
            row[2] = flowrateTime.get(i).getMIN_FLOWRATE();
            row[3] = flowrateTime.get(i).getMAX_FLOWRATE();          
            row[4] = flowrateTime.get(i).getAVG_FLOWRATE();

            
            model.addRow(row);
           
        }
        FlowrateTable.setModel(model); 

    ProcessingDialog.setVisible(false);
    
    }
           
           
           
       
        
        
       public void findQueue() throws SQLException
    {
        JDialog ProcessingDialog = new JDialog();
        JLabel label = new JLabel("Please wait...",SwingConstants.CENTER);
        ProcessingDialog.setPreferredSize(new Dimension(500,100));
        ProcessingDialog.setLocationRelativeTo(null);
        ProcessingDialog.setTitle("This might take a minute, Please Wait...");
        ProcessingDialog.add(label);
        ProcessingDialog.pack();      

        ProcessingDialog.setVisible(true);

       StatusLabel.setText("Processing..."); 
        ArrayList<QueueTable> queue = new ArrayList<QueueTable>();
        
        DefaultTableModel model = new DefaultTableModel();
        
        model.setColumnIdentifiers(new Object[] {"Cluster Id", "Location","Group Id","Dispenser", "Unit Id","User Id",
       "Date", "Transaction Start","Transaction End","Volume", "Duration","Last Conection","Status"});
        
        Object[] row = new Object [13];
        
 
        Statement st;
        ResultSet rs;
        Connection conn = getConnection();
        st = conn.createStatement();
         String sql ="SELECT \n" +
        "    CASE\n" +
        "        WHEN U.CLUSTER_ID IS NULL THEN 'NULL'\n" +
        "        ELSE U.CLUSTER_ID\n" +
        "    END AS CLUSTER_ID,\n" +
        "    CASE\n" +
        "        WHEN U.GROUP_ID = 1005 THEN 'NAIROBI WATER'\n" +
        "        WHEN U.GROUP_ID = 1003 THEN 'UGANDA'\n" +
        "        ELSE NULL\n" +
        "    END AS LOCATION,\n" +
        "    U.GROUP_ID AS GROUP_ID,\n" +
        "    U.UNIT_NAME AS DISPENSER,\n" +
        "    T.UNIT_ID AS UNIT_ID,\n" +
        "    T.CARD_ID1 AS USER,\n" +
        "    CAST(T.LOG_RTC AS DATE) AS DATE,\n" +
        "    START_TIME,\n" +
        "    CAST(T.LOG_RTC AS TIME) AS END_TIME,\n" +
        "    T.VOLUME_RELEASED AS VOLUME,\n" +
        "    TAPPING_TIME AS DURATION,\n" +
        "    U.LAST_CONNECTION AS LAST_CONNECTION,\n" +
        "    CASE\n" +
        "        WHEN U.LAST_CONNECTION = '2016-01-15 12:27:48' THEN 'CONNECTED'\n" +
        "        WHEN U.LAST_CONNECTION < '2016-01-15 12:27:48' THEN 'NOT CONNECTED'\n" +
        "        ELSE NULL\n" +
        "    END AS STATUS\n" +
        "FROM\n" +
        "    TRANS T\n" +
        "        JOIN\n" +
        "    UNIT U ON T.UNIT_ID = U.ID WHERE\n" +
        " T.VOLUME_RELEASED IS NOT NULL\n" +
        "        AND U.UNIT_NAME = '"+dispenser.getSelectedItem()+"'  \n" +
        "        AND CAST(T.U_LOG_RTC AS DATE) BETWEEN '"+((JTextField)startDate.getDateEditor().getUiComponent()).getText() +"' AND '"+((JTextField)endDate.getDateEditor().getUiComponent()).getText() +"'\n" +
        "AND U.GROUP_ID = '"+location.getSelectedItem()+"' ORDER BY T.U_LOG_RTC ASC;";
            
          
              
        System.out.println(sql);
        rs=st.executeQuery(sql);

        System.out.println("rs-->"+rs);

        QueueTable queue1;

        while (rs.next())
            {
                    
                System.out.println("Inside while");
              dispenser.addItem(rs.getString("DISPENSER"));  
                queue1 = new QueueTable(
              
                rs.getInt("CLUSTER_ID"),
                rs.getString("LOCATION"),
                rs.getInt("GROUP_ID"),
                rs.getString("DISPENSER"),
                rs.getInt("UNIT_ID"),
                rs.getInt("USER"),
                rs.getDate("DATE"),
                rs.getTime("START_TIME"),
                rs.getTime("END_TIME"),
                rs.getBigDecimal("VOLUME"),
                rs.getTime("DURATION"),
                rs.getTimestamp("LAST_CONNECTION"),
                rs.getString("STATUS")
                                
                );
                
                queue.add(queue1);
                             
            }

        
        for(int i = 0; i<queue.size(); i++)
        {
            row[0] = queue.get(i).getCLUSTER_ID();
            row[1] = queue.get(i).getLOCATION();          
            row[2] = queue.get(i).getGROUP_ID();
            row[3] = queue.get(i).getDISPENSER();
            row[4] = queue.get(i).getUNIT_ID();          
            row[5] = queue.get(i).getUSER();
            row[6] = queue.get(i).getDATE();
            row[7] = queue.get(i).getSTART_TIME();          
            row[8] = queue.get(i).getEND_TIME();
            row[9] = queue.get(i).getVOLUME();
            row[10] = queue.get(i).getDURATION();          
            row[11] = queue.get(i).getLAST_CONNECTION();
            row[12] = queue.get(i).getSTATUS();
            
            model.addRow(row);
            
        }
        QueueTable.setModel(model);
         StatusLabel.setText("Done!");
         StatusLabel.setForeground (Color.BLUE );
    ProcessingDialog.setVisible(false);

    }
       
       

          public void findIdle() throws SQLException
    {
        JDialog ProcessingDialog = new JDialog();
        JLabel label = new JLabel("Please wait...",SwingConstants.CENTER);
        ProcessingDialog.setPreferredSize(new Dimension(500,100));
        ProcessingDialog.setLocationRelativeTo(null);
        ProcessingDialog.setTitle("This might take a minute, Please Wait...");
        ProcessingDialog.add(label);
        ProcessingDialog.pack();      

        ProcessingDialog.setVisible(true);

        
        ArrayList<IdleTable> IdleTime = new ArrayList<IdleTable>();
        
        DefaultTableModel model = new DefaultTableModel();
        
        model.setColumnIdentifiers(new Object[] {"Dispenser","Date", "Total Volume","Queue Length"});
        
        Object[] row = new Object [4];

        Statement st;
        ResultSet rs;
        
         Connection conn = getConnection();
        st = conn.createStatement();
        String sql = "SELECT U.UNIT_NAME AS DISPENSER,CAST(T.START_TIME AS DATE) AS DATE,SUM(T.VOLUME_RELEASED) AS TOTAL_VOLUME,SUM(T.QUEUE_LENGTH) AS QUEUE_LENGTH\n" +
        "        \n" +
        " FROM  TRANS T\n" +
        "        JOIN\n" +
        "    UNIT U ON T.UNIT_ID = U.ID" +
        "	WHERE\n" +
        "	    T.VOLUME_RELEASED IS NOT NULL\n" +
        "        AND U.UNIT_NAME = '"+dispenser.getSelectedItem()+"'  \n" +
        "        AND CAST(T.U_LOG_RTC AS DATE) BETWEEN '"+((JTextField)startDate.getDateEditor().getUiComponent()).getText() +"' AND '"+((JTextField)endDate.getDateEditor().getUiComponent()).getText() +"'\n" +
        "AND U.GROUP_ID = '"+location.getSelectedItem()+"' GROUP BY CAST(T.LOG_RTC AS DATE) ORDER BY T.U_LOG_RTC ASC" +
        "; ";
            System.out.println(sql);
            rs=st.executeQuery(sql);
            
            System.out.println("rs-->"+rs);
            
            IdleTable idle;
            
            while (rs.next())
            {
             
            //Do the job
            
           
                idle = new IdleTable(
              
                rs.getString("DISPENSER"),
                rs.getDate("DATE"),
                rs.getBigDecimal("TOTAL_VOLUME"),
                rs.getInt("QUEUE_LENGTH")
                        
                
                );
                
                IdleTime.add(idle);

                
            }
        
        for(int i = 0; i<IdleTime.size(); i++)
        {
            row[0] = IdleTime.get(i).getDISPENSER();
            row[1] = IdleTime.get(i).getDATE();
            row[2] = IdleTime.get(i).getTOTAL_VOLUME();          
            row[3] = IdleTime.get(i).getQUEUE_LENGTH();

            
            model.addRow(row);
           
        }
        IdleTable.setModel(model); 

ProcessingDialog.setVisible(false);
    
    }
          
    
          public void getLineChartByDate()throws SQLException{
                
                try{
            String query1 = " SELECT CAST(T.START_TIME AS DATE) AS DATE,ROUND(AVG(T.FLOW_RATE),1) AS AVERAGE_FLOWRATE\n" +
        "FROM\n" +
        "    TRANS T\n" +
        "        JOIN\n" +
        "    UNIT U ON T.UNIT_ID = U.ID\n" +
        "WHERE\n" +
        "    T.VOLUME_RELEASED IS NOT NULL\n" +
        "        AND U.UNIT_NAME = '"+dispenser.getSelectedItem()+"'  \n" +
        "        AND CAST(T.U_LOG_RTC AS DATE) BETWEEN '"+((JTextField)startDate.getDateEditor().getUiComponent()).getText() +"' AND '"+((JTextField)endDate.getDateEditor().getUiComponent()).getText() +"'\n" +
        "AND U.GROUP_ID = '"+location.getSelectedItem()+"'AND  T.FLOW_RATE IS NOT NULL GROUP BY CAST(LOG_RTC AS DATE) ORDER BY T.U_LOG_RTC ASC;";
            
        JDBCCategoryDataset dataset = new JDBCCategoryDataset(javaconnect.ConnectDb(),query1);
        JFreeChart chart = ChartFactory.createLineChart("", "Date","Average Flow Rate",dataset,PlotOrientation.VERTICAL,false, true, true);
        BarRenderer renderer = null;
        CategoryPlot plot = chart.getCategoryPlot();
        CategoryAxis xAxis = (CategoryAxis)plot.getDomainAxis();
        xAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_90);
         chart.setBackgroundPaint(Color.WHITE);  


      ValueMarker marker = new ValueMarker(5);
        marker.setLabel("Queue Threshold");
        marker.setLabelTextAnchor(TextAnchor.TOP_CENTER);
        marker.setPaint(Color.BLACK);
        plot.addRangeMarker(marker);
        renderer = new BarRenderer();
        
        CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator();
      renderer.setSeriesItemLabelGenerator(0, generator);
      renderer.setSeriesItemLabelsVisible(0, false);
      renderer.setSeriesPositiveItemLabelPosition(0, new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12,TextAnchor.BASELINE_CENTER));
      renderer.setItemLabelAnchorOffset(10);
        ChartFrame frame = new ChartFrame("Flow Rate Estimate - By Date", chart);
        frame.setVisible(true);
        frame.setSize(1000,700);
        frame.setLocationRelativeTo(null);
frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("iconimage1.png")));
       
        
                }
        
        catch (Exception e){
    JOptionPane.showMessageDialog(null, e);
    
}
        
        }
          
         public void getLineChartByDay()throws SQLException{
                
                try{
            String query1 = " SELECT DAYNAME(CAST(T.START_TIME AS DATE)) AS DAY,ROUND(AVG(T.FLOW_RATE),1) AS AVERAGE_FLOWRATE\n" +
"        \n" +
" FROM\n" +
"    TRANS T\n" +
"        JOIN\n" +
"    UNIT U ON T.UNIT_ID = U.ID\n" +
"WHERE\n" +
"    T.VOLUME_RELEASED IS NOT NULL\n" +
"        AND U.UNIT_NAME = '"+dispenser.getSelectedItem()+"'  \n" +
"        AND CAST(T.U_LOG_RTC AS DATE) BETWEEN '"+((JTextField)startDate.getDateEditor().getUiComponent()).getText() +"' AND '"+((JTextField)endDate.getDateEditor().getUiComponent()).getText() +"'\n" +
"AND U.GROUP_ID = '"+location.getSelectedItem()+"'  GROUP BY DAYNAME(CAST(T.START_TIME AS DATE)) \n" +
" ORDER BY CASE\n" +
"    WHEN DAYNAME(CAST(T.START_TIME AS DATE)) LIKE \"%MON%\" THEN 1 \n" +
"    WHEN DAYNAME(CAST(T.START_TIME AS DATE)) LIKE \"%TUES%\"  THEN 2 \n" +
"    WHEN DAYNAME(CAST(T.START_TIME AS DATE)) LIKE \"%WED%\" THEN 3\n" +
"    WHEN DAYNAME(CAST(T.START_TIME AS DATE)) LIKE \"%THUR%\"  THEN 4 \n" +
"	WHEN DAYNAME(CAST(T.START_TIME AS DATE)) LIKE \"%FRI%\" THEN 5\n" +
"    WHEN DAYNAME(CAST(T.START_TIME AS DATE)) LIKE \"%SAT%\"  THEN 6\n" +
"    ELSE 7\n" +
"END;";
            
        JDBCCategoryDataset dataset = new JDBCCategoryDataset(javaconnect.ConnectDb(),query1);
        JFreeChart chart = ChartFactory.createLineChart("", "Day of Week","Average Flow Rate",dataset,PlotOrientation.VERTICAL,false, true, true);
        BarRenderer renderer = null;
        CategoryPlot plot = chart.getCategoryPlot();
        CategoryAxis xAxis = (CategoryAxis)plot.getDomainAxis();
        xAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_90);
 chart.setBackgroundPaint(Color.WHITE);  


  ValueMarker marker = new ValueMarker(5);
        marker.setLabel("Flow Rate Threshold");
        marker.setLabelTextAnchor(TextAnchor.TOP_CENTER);
        marker.setPaint(Color.BLACK);
        plot.addRangeMarker(marker);
        renderer = new BarRenderer();
        
        CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator();
  renderer.setSeriesItemLabelGenerator(0, generator);
  renderer.setSeriesItemLabelsVisible(0, false);
  renderer.setSeriesPositiveItemLabelPosition(0, new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12,TextAnchor.BASELINE_CENTER));
  renderer.setItemLabelAnchorOffset(10);
            ChartFrame frame = new ChartFrame("Flow Rate Estimate - By Day of week", chart);
        frame.setVisible(true);
        frame.setSize(1000,700);
        frame.setLocationRelativeTo(null);
frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("iconimage1.png")));
       
        
                }
        
        catch (Exception e){
    JOptionPane.showMessageDialog(null, e);
    
}
        
        }
         
              public void getLineChartByTime()throws SQLException{
                
                try{
            String query1 = " SELECT CASE\n" +
"  WHEN CAST(T.START_TIME AS TIME) BETWEEN '03:00:00' AND '08:00:00' THEN 'EARLY MORNING'\n" +
"  WHEN CAST(T.START_TIME AS TIME) BETWEEN '08:00:01' AND '11:00:00' THEN 'MID MORNING'\n" +
"  WHEN CAST(T.START_TIME AS TIME) BETWEEN '11:00:01' AND '15:00:00' THEN 'AFTERNOON'\n" +
"  WHEN CAST(T.START_TIME AS TIME) BETWEEN '15:00:01' AND '18:00:00' THEN  'EVENING'\n" +
"  WHEN CAST(T.START_TIME AS TIME) BETWEEN '18:00:01' AND '23:00:00' THEN  'NIGHT'\n" +
"  END AS TIME,\n" +
"ROUND(AVG(T.FLOW_RATE),1) AS AVERAGE_FLOWRATE\n" +
"        \n" +
" FROM\n" +
"    TRANS T\n" +
"        JOIN\n" +
"    UNIT U ON T.UNIT_ID = U.ID\n" +
"WHERE\n" +
"    T.VOLUME_RELEASED IS NOT NULL\n" +
"        AND U.UNIT_NAME = '"+dispenser.getSelectedItem()+"'  \n" +
"        AND CAST(T.U_LOG_RTC AS DATE) BETWEEN '"+((JTextField)startDate.getDateEditor().getUiComponent()).getText() +"' AND '"+((JTextField)endDate.getDateEditor().getUiComponent()).getText() +"'\n" +
"AND U.GROUP_ID = '"+location.getSelectedItem()+"'  AND DAYNAME(CAST(T.START_TIME AS DATE)) = '"+SelectTimeofdayLine.getSelectedItem()+"'  GROUP BY TIME ORDER BY\n" +
" CASE\n" +
" WHEN TIME LIKE \"%EAR%\" THEN 1 \n" +
" WHEN TIME LIKE \"%MID%\" THEN 2 \n" +
" WHEN TIME LIKE \"%AFT%\" THEN 3\n" +
" WHEN TIME LIKE \"%EVE%\" THEN 4\n" +
" WHEN TIME LIKE \"%NIG%\" THEN 5\n" +
"   \n" +
"END;";
            
        JDBCCategoryDataset dataset = new JDBCCategoryDataset(javaconnect.ConnectDb(),query1);
        JFreeChart chart = ChartFactory.createLineChart("", "Time of day","Average Flow Rate",dataset,PlotOrientation.VERTICAL,false, true, true);
        BarRenderer renderer = null;
        CategoryPlot plot = chart.getCategoryPlot();
        CategoryAxis xAxis = (CategoryAxis)plot.getDomainAxis();
        xAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_90);
 chart.setBackgroundPaint(Color.WHITE);  


  ValueMarker marker = new ValueMarker(5);
        marker.setLabel("Flow Rate Threshold");
        marker.setLabelTextAnchor(TextAnchor.TOP_CENTER);
        marker.setPaint(Color.BLACK);
        plot.addRangeMarker(marker);
        renderer = new BarRenderer();
        
        CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator();
  renderer.setSeriesItemLabelGenerator(0, generator);
  renderer.setSeriesItemLabelsVisible(0, false);
  renderer.setSeriesPositiveItemLabelPosition(0, new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12,TextAnchor.BASELINE_CENTER));
  renderer.setItemLabelAnchorOffset(10);
            ChartFrame frame = new ChartFrame("Flow Rate Estimate - By Time of day", chart);
        frame.setVisible(true);
        frame.setSize(1000,700);
        frame.setLocationRelativeTo(null);
frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("iconimage1.png")));
       
        
                }
        
        catch (Exception e){
    JOptionPane.showMessageDialog(null, e);
    
}
              } 
   public void getLineChartByMonth()throws SQLException{
                
                try{
            String query1 = " SELECT MONTHNAME(CAST(T.START_TIME AS DATE)) AS MONTH,ROUND(AVG(T.FLOW_RATE),1) AS AVERAGE_FLOWRATE\n" +
"        \n" +
" FROM\n" +
"    TRANS T\n" +
"        JOIN\n" +
"    UNIT U ON T.UNIT_ID = U.ID\n" +
"WHERE\n" +
"    T.VOLUME_RELEASED IS NOT NULL\n" +
"        AND U.UNIT_NAME = '"+dispenser.getSelectedItem()+"'  \n" +
"        AND CAST(T.U_LOG_RTC AS DATE) BETWEEN '"+((JTextField)startDate.getDateEditor().getUiComponent()).getText() +"' AND '"+((JTextField)endDate.getDateEditor().getUiComponent()).getText() +"'\n" +
"AND U.GROUP_ID = '"+location.getSelectedItem()+"' GROUP BY MONTHNAME(CAST(T.START_TIME AS DATE)) \n" +
"  ORDER BY CASE\n" +
"    WHEN MONTHNAME(CAST(T.START_TIME AS DATE)) LIKE \"%JAN%\" THEN 1 \n" +
"    WHEN MONTHNAME(CAST(T.START_TIME AS DATE)) LIKE \"%FEB%\"  THEN 2 \n" +
"    WHEN MONTHNAME(CAST(T.START_TIME AS DATE)) LIKE \"%MAR%\" THEN 3\n" +
"    WHEN MONTHNAME(CAST(T.START_TIME AS DATE)) LIKE \"%APR%\"  THEN 4 \n" +
"	WHEN MONTHNAME(CAST(T.START_TIME AS DATE)) LIKE \"%MAY%\" THEN 5\n" +
"    WHEN MONTHNAME(CAST(T.START_TIME AS DATE)) LIKE \"%JUN%\"  THEN 6\n" +
"	WHEN MONTHNAME(CAST(T.START_TIME AS DATE)) LIKE \"%JUL%\" THEN 7 \n" +
"    WHEN MONTHNAME(CAST(T.START_TIME AS DATE)) LIKE \"%AUG%\"  THEN 8\n" +
"    WHEN MONTHNAME(CAST(T.START_TIME AS DATE)) LIKE \"%SEP%\" THEN 9\n" +
"    WHEN MONTHNAME(CAST(T.START_TIME AS DATE)) LIKE \"%OCT%\"  THEN 10\n" +
"	WHEN MONTHNAME(CAST(T.START_TIME AS DATE)) LIKE \"%NOV%\" THEN 11\n" +
"    ELSE 12\n" +
"END;";
            
        JDBCCategoryDataset dataset = new JDBCCategoryDataset(javaconnect.ConnectDb(),query1);
        JFreeChart chart = ChartFactory.createLineChart("", "Month","Average Flow Rate",dataset,PlotOrientation.VERTICAL,false, true, true);
        BarRenderer renderer = null;
        CategoryPlot plot = chart.getCategoryPlot();
        CategoryAxis xAxis = (CategoryAxis)plot.getDomainAxis();
        xAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_90);
 chart.setBackgroundPaint(Color.WHITE);  


  ValueMarker marker = new ValueMarker(5);
        marker.setLabel("Flowrate Threshold");
        marker.setLabelTextAnchor(TextAnchor.TOP_CENTER);
        marker.setPaint(Color.BLACK);
        plot.addRangeMarker(marker);
        renderer = new BarRenderer();
        
        CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator();
  renderer.setSeriesItemLabelGenerator(0, generator);
  renderer.setSeriesItemLabelsVisible(0, false);
  renderer.setSeriesPositiveItemLabelPosition(0, new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12,TextAnchor.BASELINE_CENTER));
  renderer.setItemLabelAnchorOffset(10);
            ChartFrame frame = new ChartFrame("Flow Rate Estimate - By Month", chart);
        frame.setVisible(true);
        frame.setSize(1000,700);
        frame.setLocationRelativeTo(null);
frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("iconimage1.png")));
       
        
                }
        
        catch (Exception e){
    JOptionPane.showMessageDialog(null, e);
    
}
        
        }
        
         public void getChartByDate()throws SQLException{
                
                try{
            String query1 = "SELECT CAST(T.START_TIME AS DATE) AS DATE,SUM(T.QUEUE_LENGTH) AS QUEUE_LENGTH\n" +
        "        \n" +
        " FROM  TRANS T\n" +
        "        JOIN\n" +
        "    UNIT U ON T.UNIT_ID = U.ID\n" +
        "WHERE\n" +
        "    T.VOLUME_RELEASED IS NOT NULL\n" +
        "        AND U.UNIT_NAME = '"+dispenser.getSelectedItem()+"'  \n" +
        "        AND CAST(T.U_LOG_RTC AS DATE) BETWEEN '"+((JTextField)startDate.getDateEditor().getUiComponent()).getText() +"' AND '"+((JTextField)endDate.getDateEditor().getUiComponent()).getText() +"'\n" +
        "AND U.GROUP_ID = '"+location.getSelectedItem()+"' GROUP BY CAST(T.LOG_RTC AS DATE) ORDER BY T.U_LOG_RTC ASC ;";
            
        JDBCCategoryDataset dataset = new JDBCCategoryDataset(javaconnect.ConnectDb(),query1);
        JFreeChart chart = ChartFactory.createBarChart3D("", "Date","Queue Length",dataset,PlotOrientation.VERTICAL,false, true, true);
        chart.setBackgroundPaint(Color.WHITE);
        


        BarRenderer renderer = null;
        CategoryPlot plot = chart.getCategoryPlot();
        CategoryAxis xAxis = (CategoryAxis)plot.getDomainAxis();
        xAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_90);
        
        ValueMarker marker = new ValueMarker(5);
        marker.setLabel("Queue Threshold");
        marker.setLabelTextAnchor(TextAnchor.TOP_CENTER);
        marker.setPaint(Color.LIGHT_GRAY);
        plot.addRangeMarker(marker);
        plot.setRangeGridlinePaint(Color.BLUE);
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
       
        plot.setForegroundAlpha(1.0f);
        plot.rendererChanged(null);
        plot.setOutlineVisible(true);
        BarRenderer barRenderer = (BarRenderer)plot.getRenderer();
       
        barRenderer.setSeriesPaint(0, Color.getHSBColor(99,123,123));

        renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);

        CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator();
          renderer.setSeriesItemLabelGenerator(0, generator);
          renderer.setSeriesItemLabelsVisible(0, false);
          renderer.setSeriesPositiveItemLabelPosition(0, new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12,TextAnchor.BASELINE_CENTER));
          renderer.setItemLabelAnchorOffset(10);

        ChartFrame frame = new ChartFrame("Queue Length Estimate - By Date", chart);
        frame.setVisible(true);
        frame.setSize(1200,700);
        frame.setLocationRelativeTo(null);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("iconimage1.png")));

       
        
                }
        
        catch (Exception e){
        JOptionPane.showMessageDialog(null, e);
    
}
                
                
        
                
        }
         
         
            public void getChartByDay()throws SQLException{
                
                try{
            String query1 = "SELECT  DAYNAME(CAST(T.START_TIME AS DATE)) AS DAY,SUM(T.QUEUE_LENGTH) AS QUEUE_LENGTH\n" +
            "        \n" +
            " FROM  TRANS T\n" +
            "        JOIN\n" +
            "    UNIT U ON T.UNIT_ID = U.ID\n" +
            "WHERE\n" +
            "    T.VOLUME_RELEASED IS NOT NULL AND DAYNAME(CAST(T.START_TIME AS DATE)) IS NOT NULL \n" +
            "        AND U.UNIT_NAME = '"+dispenser.getSelectedItem()+"'  \n" +
            "        AND CAST(T.U_LOG_RTC AS DATE) BETWEEN '"+((JTextField)startDate.getDateEditor().getUiComponent()).getText() +"' AND '"+((JTextField)endDate.getDateEditor().getUiComponent()).getText() +"'\n" +
            "AND U.GROUP_ID = '"+location.getSelectedItem()+"' GROUP BY DAYNAME(CAST(T.START_TIME AS DATE)) ORDER BY CASE\n" +
            "    WHEN DAYNAME(CAST(T.START_TIME AS DATE)) LIKE \"%MON%\" THEN 1 \n" +
            "    WHEN DAYNAME(CAST(T.START_TIME AS DATE)) LIKE \"%TUES%\"  THEN 2 \n" +
            "    WHEN DAYNAME(CAST(T.START_TIME AS DATE)) LIKE \"%WE%\" THEN 3\n" +
            "    WHEN DAYNAME(CAST(T.START_TIME AS DATE)) LIKE \"%THUR%\"  THEN 4 \n" +
            "	WHEN DAYNAME(CAST(T.START_TIME AS DATE)) LIKE \"%FRI%\" THEN 5\n" +
            "    WHEN DAYNAME(CAST(T.START_TIME AS DATE)) LIKE \"%SAT%\"  THEN 6\n" +
            "    ELSE 7\n" +
            "END; ;";
            
        JDBCCategoryDataset dataset = new JDBCCategoryDataset(javaconnect.ConnectDb(),query1);
        JFreeChart chart = ChartFactory.createBarChart3D("", "Day of week ","Total Queue Length",dataset,PlotOrientation.VERTICAL,false, true, true);
        chart.setBackgroundPaint(Color.WHITE);
        


        BarRenderer renderer = null;
        CategoryPlot plot = chart.getCategoryPlot();
        CategoryAxis xAxis = (CategoryAxis)plot.getDomainAxis();
        xAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
        
   
        
        plot.setRangeGridlinePaint(Color.BLUE);
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
       
        plot.setForegroundAlpha(1.0f);
        plot.rendererChanged(null);
        plot.setOutlineVisible(true);
        BarRenderer barRenderer = (BarRenderer)plot.getRenderer();
       
        barRenderer.setSeriesPaint(0, Color.getHSBColor(99,123,123));

        renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);

        CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator();
          renderer.setSeriesItemLabelGenerator(0, generator);
          renderer.setSeriesItemLabelsVisible(0, false);
          renderer.setSeriesPositiveItemLabelPosition(0, new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12,TextAnchor.BASELINE_CENTER));
          renderer.setItemLabelAnchorOffset(10);

        ChartFrame frame = new ChartFrame("Queue Length Estimate - By Day of Week", chart);
        frame.setVisible(true);
        frame.setSize(700,500);
        frame.setLocationRelativeTo(null);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("iconimage1.png")));

       
        
                }
        
        catch (Exception e){
        JOptionPane.showMessageDialog(null, e);
    
}
                
                
        
                
        }
         
              public void getChartByTime()throws SQLException{
                
                try{
            String query1 = "SELECT CASE\n" +
            "  WHEN CAST(T.START_TIME AS TIME) BETWEEN '03:00:00' AND '08:00:00' THEN 'EARLY MORNING'\n" +
            "  WHEN CAST(T.START_TIME AS TIME) BETWEEN '08:00:01' AND '11:00:00' THEN 'MID MORNING'\n" +
            "  WHEN CAST(T.START_TIME AS TIME) BETWEEN '11:00:01' AND '15:00:00' THEN 'AFTERNOON'\n" +
            "  WHEN CAST(T.START_TIME AS TIME) BETWEEN '15:00:01' AND '18:00:00' THEN  'EVENING'\n" +
            "  WHEN CAST(T.START_TIME AS TIME) BETWEEN '18:00:01' AND '23:00:00' THEN  'NIGHT'\n" +
            "  END AS TIME,\n" +
            " \n" +
            " SUM(T.QUEUE_LENGTH) AS QUEUE_LENGTH\n" +
            "        \n" +
            " FROM  TRANS T\n" +
            "        JOIN\n" +
            "    UNIT U ON T.UNIT_ID = U.ID\n" +
            "WHERE\n" +
            "    T.VOLUME_RELEASED IS NOT NULL  AND  T.QUEUE_LENGTH IS NOT NULL  AND CAST(T.START_TIME AS TIME) BETWEEN '03:00:00' AND '23:00:00'\n" +
            "        AND U.UNIT_NAME = '"+dispenser.getSelectedItem()+"'  \n" +
            "        AND CAST(T.U_LOG_RTC AS DATE) BETWEEN '"+((JTextField)startDate.getDateEditor().getUiComponent()).getText() +"' AND '"+((JTextField)endDate.getDateEditor().getUiComponent()).getText() +"'\n" +
            "AND U.GROUP_ID = '"+location.getSelectedItem()+"'AND DAYNAME(CAST(T.START_TIME AS DATE)) = '"+SelectTimeofday.getSelectedItem()+"' GROUP BY  \n" +
            "    CASE\n" +
            " WHEN TIME LIKE \"%EAR%\" THEN 1 \n" +
            " WHEN TIME LIKE \"%MID%\" THEN 2 \n" +
            " WHEN TIME LIKE \"%AFT%\" THEN 3\n" +
            " WHEN TIME LIKE \"%EVE%\" THEN 4\n" +
            " WHEN TIME LIKE \"%NIG%\" THEN 5\n" +
            "   \n" +
            "END;\n" +
            "";
            
        JDBCCategoryDataset dataset = new JDBCCategoryDataset(javaconnect.ConnectDb(),query1);
        JFreeChart chart = ChartFactory.createBarChart3D("", "Time of Day ","Total Queue Length",dataset,PlotOrientation.VERTICAL,false, true, true);
        chart.setBackgroundPaint(Color.WHITE);
        


        BarRenderer renderer = null;
        CategoryPlot plot = chart.getCategoryPlot();
        CategoryAxis xAxis = (CategoryAxis)plot.getDomainAxis();
        xAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
        
        ValueMarker marker = new ValueMarker(5);
        marker.setLabel("Queue Threshold");
        marker.setLabelTextAnchor(TextAnchor.TOP_CENTER);
        marker.setPaint(Color.LIGHT_GRAY);
        plot.addRangeMarker(marker);
        
        plot.setRangeGridlinePaint(Color.BLUE);
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
       
        plot.setForegroundAlpha(1.0f);
        plot.rendererChanged(null);
        plot.setOutlineVisible(true);
        BarRenderer barRenderer = (BarRenderer)plot.getRenderer();
       
        barRenderer.setSeriesPaint(0, Color.getHSBColor(99,123,123));

        renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);

        CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator();
          renderer.setSeriesItemLabelGenerator(0, generator);
          renderer.setSeriesItemLabelsVisible(0, false);
          renderer.setSeriesPositiveItemLabelPosition(0, new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12,TextAnchor.BASELINE_CENTER));
          renderer.setItemLabelAnchorOffset(10);

        ChartFrame1 frame = new ChartFrame1("Queue Length Estimate - By Time of day", chart);
        frame.setVisible(true);
        frame.setSize(850,600);
        frame.setLocationRelativeTo(null);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("iconimage1.png")));

       
        
                }
        
        catch (Exception e){
    JOptionPane.showMessageDialog(null, e);
    
}
                
                
        
                
        }
            
              public void getChartByMonth()throws SQLException{
                
                try{
            String query1 = "SELECT  MONTHNAME(CAST(T.START_TIME AS DATE)) AS MONTH,SUM(T.QUEUE_LENGTH) AS QUEUE_LENGTH\n" +
            "        \n" +
            " FROM  TRANS T\n" +
            "        JOIN\n" +
            "    UNIT U ON T.UNIT_ID = U.ID\n" +
            "WHERE\n" +
            "    T.VOLUME_RELEASED IS NOT NULL  \n" +
            "        AND U.UNIT_NAME = '"+dispenser.getSelectedItem()+"'  \n" +
            "        AND CAST(T.U_LOG_RTC AS DATE) BETWEEN '"+((JTextField)startDate.getDateEditor().getUiComponent()).getText() +"' AND '"+((JTextField)endDate.getDateEditor().getUiComponent()).getText() +"'\n" +
            "AND U.GROUP_ID = '"+location.getSelectedItem()+"' GROUP BY MONTHNAME(CAST(T.START_TIME AS DATE)) \n" +
            "  ORDER BY CASE\n" +
            "    WHEN MONTHNAME(CAST(T.START_TIME AS DATE)) LIKE \"%JAN%\" THEN 1 \n" +
            "    WHEN MONTHNAME(CAST(T.START_TIME AS DATE)) LIKE \"%FEB%\"  THEN 2 \n" +
            "    WHEN MONTHNAME(CAST(T.START_TIME AS DATE)) LIKE \"%MAR%\" THEN 3\n" +
            "    WHEN MONTHNAME(CAST(T.START_TIME AS DATE)) LIKE \"%APR%\"  THEN 4 \n" +
            "	WHEN MONTHNAME(CAST(T.START_TIME AS DATE)) LIKE \"%MAY%\" THEN 5\n" +
            "    WHEN MONTHNAME(CAST(T.START_TIME AS DATE)) LIKE \"%JUN%\"  THEN 6\n" +
            "	WHEN MONTHNAME(CAST(T.START_TIME AS DATE)) LIKE \"%JUL%\" THEN 7 \n" +
            "    WHEN MONTHNAME(CAST(T.START_TIME AS DATE)) LIKE \"%AUG%\"  THEN 8\n" +
            "    WHEN MONTHNAME(CAST(T.START_TIME AS DATE)) LIKE \"%SEP%\" THEN 9\n" +
            "    WHEN MONTHNAME(CAST(T.START_TIME AS DATE)) LIKE \"%OCT%\"  THEN 10\n" +
            "	WHEN MONTHNAME(CAST(T.START_TIME AS DATE)) LIKE \"%NOV%\" THEN 11\n" +
            "    ELSE 12\n" +
            "END;";
            
        JDBCCategoryDataset dataset = new JDBCCategoryDataset(javaconnect.ConnectDb(),query1);
        JFreeChart chart = ChartFactory.createBarChart3D("", "Month","Total Queue Length",dataset,PlotOrientation.VERTICAL,false, true, true);
        chart.setBackgroundPaint(Color.WHITE);
        


        BarRenderer renderer = null;
        CategoryPlot plot = chart.getCategoryPlot();
        CategoryAxis xAxis = (CategoryAxis)plot.getDomainAxis();
        xAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
        
        plot.setRangeGridlinePaint(Color.BLUE);
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
       
        plot.setForegroundAlpha(1.0f);
        plot.rendererChanged(null);
        plot.setOutlineVisible(true);
        BarRenderer barRenderer = (BarRenderer)plot.getRenderer();
       
        barRenderer.setSeriesPaint(0, Color.getHSBColor(99,123,123));

        renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);

        CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator();
          renderer.setSeriesItemLabelGenerator(0, generator);
          renderer.setSeriesItemLabelsVisible(0, false);
          renderer.setSeriesPositiveItemLabelPosition(0, new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12,TextAnchor.BASELINE_CENTER));
          renderer.setItemLabelAnchorOffset(10);

        ChartFrame frame = new ChartFrame("Queue Length Estimate - By month", chart);
        frame.setVisible(true);
        frame.setSize(500,700);
        frame.setLocationRelativeTo(null);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("iconimage1.png")));

       
        
                }
        
        catch (Exception e){
        JOptionPane.showMessageDialog(null, e);
    
}
                
                
        
                
        }
         
         
         
public void comboselect(){
    try {
                    int d = SelectChart.getSelectedIndex();
                    if(d==1){
                         
                      getChartByMonth();
                    }
                     if(d==2){
                     
                     getChartByDay();
                                         
                        }
                      if(d==3){
                     
                     getChartByDate();
                      }
                      
                       
                         } catch (SQLException ex) {
            Logger.getLogger(Queue.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    

public void comboselectLine(){
    try {
                    int d = SelectFlowrateCombo.getSelectedIndex();
                    if(d==1){
                         
                      getLineChartByMonth();
                    }
                     if(d==2){
                     
                     getLineChartByDay();
                                         
                        }
                      if(d==3){
                     
                     getLineChartByDate();
                      }
                      
                       
                         } catch (SQLException ex) {
            Logger.getLogger(Queue.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    
}