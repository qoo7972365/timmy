/*    */ package com.adventnet.appmanager.struts.beans;
/*    */ 
/*    */ import java.util.Date;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UrlData
/*    */ {
/*    */   private float availability;
/*    */   private String urlid;
/*    */   private String url;
/*    */   private String urlname;
/*    */   private String averageresponsetime;
/*    */   private String monitorstarttime;
/*    */   private String severtiy;
/*    */   
/*    */   public String getUrlId()
/*    */   {
/* 19 */     return this.urlid;
/*    */   }
/*    */   
/*    */   public String getURL()
/*    */   {
/* 24 */     return this.url;
/*    */   }
/*    */   
/*    */   public float getAvailability() {
/* 28 */     return this.availability;
/*    */   }
/*    */   
/*    */   public String getResponseTime() {
/* 32 */     if (Integer.parseInt(this.averageresponsetime) < 0)
/*    */     {
/* 34 */       this.averageresponsetime = "0";
/*    */     }
/* 36 */     return this.averageresponsetime;
/*    */   }
/*    */   
/*    */   public String getUrlName() {
/* 40 */     return this.urlname;
/*    */   }
/*    */   
/*    */   public String getMonitorStartTime()
/*    */   {
/*    */     try
/*    */     {
/* 47 */       return new Date(Long.parseLong(this.monitorstarttime)).toString();
/*    */     }
/*    */     catch (NumberFormatException ne) {}
/*    */     
/* 51 */     return this.monitorstarttime;
/*    */   }
/*    */   
/*    */   public String getSeverity()
/*    */   {
/* 56 */     return this.severtiy;
/*    */   }
/*    */   
/*    */   public void setUrlName(String temp)
/*    */   {
/* 61 */     this.urlname = temp;
/*    */   }
/*    */   
/*    */   public void setUrlId(String temp) {
/* 65 */     this.urlid = temp;
/*    */   }
/*    */   
/*    */   public void setMonitorStartTime(String temp) {
/* 69 */     this.monitorstarttime = temp;
/*    */   }
/*    */   
/*    */   public void setURL(String temp) {
/* 73 */     this.url = temp;
/*    */   }
/*    */   
/*    */   public void setAvailability(float temp)
/*    */   {
/* 78 */     this.availability = temp;
/*    */   }
/*    */   
/*    */   public void setResponseTime(String temp)
/*    */   {
/* 83 */     this.averageresponsetime = temp;
/*    */   }
/*    */   
/*    */   public void setSeverity(String temp)
/*    */   {
/* 88 */     this.severtiy = temp;
/*    */   }
/*    */   
/*    */   public void setReason(String reason) {
/* 92 */     this.reason = reason;
/*    */   }
/*    */   
/*    */ 
/* 96 */   public String getReason() { return this.reason; }
/*    */   
/* 98 */   private String reason = "";
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\beans\UrlData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */