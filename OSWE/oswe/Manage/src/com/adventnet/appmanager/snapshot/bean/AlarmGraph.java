/*    */ package com.adventnet.appmanager.snapshot.bean;
/*    */ 
/*    */ import com.adventnet.appmanager.struts.beans.AlarmUtil;
/*    */ import com.adventnet.awolf.data.DatasetProduceException;
/*    */ import com.adventnet.awolf.data.DatasetProducer;
/*    */ import java.awt.Color;
/*    */ import java.io.Serializable;
/*    */ import java.util.Date;
/*    */ import java.util.Map;
/*    */ import org.jfree.chart.JFreeChart;
/*    */ import org.jfree.chart.plot.CategoryPlot;
/*    */ import org.jfree.chart.renderer.category.CategoryItemRenderer;
/*    */ import org.jfree.data.category.DefaultCategoryDataset;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AlarmGraph
/*    */   implements DatasetProducer, Serializable
/*    */ {
/*    */   private String haid;
/*    */   
/*    */   public void sethaid(String id)
/*    */   {
/* 36 */     this.haid = id;
/*    */   }
/*    */   
/*    */   public String gethaid() {
/* 40 */     return this.haid;
/*    */   }
/*    */   
/*    */   public Object produceDataset(Map params) throws DatasetProduceException {
/* 44 */     DefaultCategoryDataset result = new DefaultCategoryDataset();
/* 45 */     result.addValue(new Integer(AlarmUtil.getApplicationAlertsForHA(gethaid(), "1")), "", "Critical(" + AlarmUtil.getApplicationAlertsForHA(gethaid(), "1") + ")");
/* 46 */     result.addValue(new Integer(AlarmUtil.getApplicationAlertsForHA(gethaid(), "4")), "", "Warning(" + AlarmUtil.getApplicationAlertsForHA(gethaid(), "4") + ")");
/* 47 */     result.addValue(new Integer(AlarmUtil.getApplicationAlertsForHA(gethaid(), "5")), "", "Clear(" + AlarmUtil.getApplicationAlertsForHA(gethaid(), "5") + ")");
/* 48 */     return result;
/*    */   }
/*    */   
/*    */   public boolean hasExpired(Map params, Date since)
/*    */   {
/* 53 */     return true;
/*    */   }
/*    */   
/*    */   public String getProducerId()
/*    */   {
/* 58 */     return "AlarmGraph";
/*    */   }
/*    */   
/*    */   public String generateLink(Object dataset, int series, Object category) {
/* 62 */     String toreturn = null;
/* 63 */     if (series == 0)
/* 64 */       toreturn = "javascript:showParticularSeverityAlarms('" + AlarmUtil.getApplicationAlertsForHA(gethaid(), "1") + "','1','" + gethaid() + "')";
/* 65 */     if (series == 1)
/* 66 */       toreturn = "javascript:showParticularSeverityAlarms('" + AlarmUtil.getApplicationAlertsForHA(gethaid(), "4") + "','4','" + gethaid() + "')";
/* 67 */     if (series == 2)
/*    */     {
/* 69 */       toreturn = "javascript:showParticularSeverityAlarms('" + AlarmUtil.getApplicationAlertsForHA(gethaid(), "5") + "','5','" + gethaid() + "')"; }
/* 70 */     return toreturn;
/*    */   }
/*    */   
/*    */   public void processChart(Object chart, Map params) {
/* 74 */     CategoryPlot plot = (CategoryPlot)((JFreeChart)chart).getPlot();
/* 75 */     for (int i = 0; i < params.size(); i++) {
/* 76 */       String colorStr = (String)params.get(String.valueOf(i));
/* 77 */       plot.getRenderer().setSeriesPaint(i, Color.decode(colorStr));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\snapshot\bean\AlarmGraph.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */