package aqtap_main;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.math.BigDecimal;
import java.text.MessageFormat;
import javax.swing.JTable;

/**
 *
 * @author 906729
 */

public class QueueTable {

    static void print(JTable.PrintMode printMode, MessageFormat header, MessageFormat footer) {
       
    }
    private Integer CLUSTER_ID;
    private String LOCATION;
    private Integer GROUP_ID;
    private String DISPENSER;
    private Integer UNIT_ID;
    private Integer USER;
    private Date DATE;
    private Time START_TIME;
    private Time END_TIME;
    private BigDecimal VOLUME;
    private Time DURATION;
    private Timestamp LAST_CONNECTION;
    private String STATUS;
    
    
    public QueueTable(Integer CLUSTER_ID,String LOCATION,Integer GROUP_ID,String DISPENSER,Integer UNIT_ID,Integer USER,Date DATE,Time START_TIME,Time END_TIME,BigDecimal VOLUME,
           Time DURATION,Timestamp LAST_CONNECTION,String STATUS ){
        this.CLUSTER_ID = CLUSTER_ID;
        this.LOCATION = LOCATION;    
        this.GROUP_ID = GROUP_ID;
        this.DISPENSER = DISPENSER;
        this.UNIT_ID = UNIT_ID;    
        this.USER = USER;
        this.DATE = DATE;
        this.START_TIME = START_TIME;    
        this.END_TIME = END_TIME;
        this.VOLUME = VOLUME;
        this.DURATION = DURATION;    
        this.LAST_CONNECTION = LAST_CONNECTION;
        this.STATUS = STATUS;
    }


    public Integer getCLUSTER_ID(){
        return CLUSTER_ID;
    }
    
    public String getLOCATION(){
        return LOCATION;
    }
    
    public Integer getGROUP_ID(){
        return GROUP_ID;
    }
      public String getDISPENSER(){
        return DISPENSER;
    }
          public Integer getUNIT_ID(){
        return UNIT_ID;
    }
    public Integer getUSER(){
        return USER;
    }
    
    public Date getDATE(){
        return DATE;
    }

    
    public Time getSTART_TIME(){
        return START_TIME;
    }
    
    public Time getEND_TIME(){
        return END_TIME;
    }
        public BigDecimal getVOLUME(){
        return VOLUME;
    }
            public Time getDURATION(){
        return DURATION;
    }
    
    public Timestamp getLAST_CONNECTION(){
        return LAST_CONNECTION;
    }
        public String getSTATUS(){
        return STATUS;
    }
}