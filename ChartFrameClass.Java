package Charts;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.GridPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
/**
 *
 * @author 906729
 */



/**
 *
 * @author DK
 */
public class ChartFrame extends JFrame {

  JFXPanel fxPanel;
  private JFreeChart chart;
  public ChartFrame(){
    initSwingComponents();


  }
  
  public ChartFrame(String title, JFreeChart chart){
      super(title);
      this.chart = chart;
    initSwingComponents();

//    initFxComponents();
    
  }

  private void initSwingComponents(){
    JPanel mainPanel = new JPanel(new BorderLayout());
    fxPanel = new JFXPanel();
    mainPanel.add(fxPanel, BorderLayout.CENTER);



    ChartPanel chartPanel = new ChartPanel(chart, false);
        chartPanel.setPreferredSize(new Dimension(640, 1200));
        mainPanel.add(chartPanel, BorderLayout.CENTER);
        
    this.add(mainPanel);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.setSize(800,400);
    
  }

 
}

    

