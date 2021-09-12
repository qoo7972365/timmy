/*    */ package com.me.apm.client.selenium.form;
/*    */ 
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.upload.FormFile;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SeleniumForm
/*    */   extends ActionForm
/*    */ {
/*    */   private FormFile file;
/*    */   private String displayName;
/*    */   private String[] agentIds;
/* 19 */   private int pollingInterval = 10;
/*    */   
/*    */   public int getPollingInterval() {
/* 22 */     return this.pollingInterval;
/*    */   }
/*    */   
/*    */   public void setPollingInterval(int pollingInterval) {
/* 26 */     this.pollingInterval = pollingInterval;
/*    */   }
/*    */   
/*    */   public String[] getAgentIds() {
/* 30 */     return this.agentIds;
/*    */   }
/*    */   
/*    */   public void setAgentIds(String[] agentIds) {
/* 34 */     this.agentIds = agentIds;
/*    */   }
/*    */   
/*    */   public String getDisplayName() {
/* 38 */     return this.displayName;
/*    */   }
/*    */   
/*    */   public void setDisplayName(String displayName) {
/* 42 */     this.displayName = displayName;
/*    */   }
/*    */   
/*    */   public FormFile getFile() {
/* 46 */     return this.file;
/*    */   }
/*    */   
/*    */   public void setFile(FormFile file) {
/* 50 */     this.file = file;
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\me\apm\client\selenium\form\SeleniumForm.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */