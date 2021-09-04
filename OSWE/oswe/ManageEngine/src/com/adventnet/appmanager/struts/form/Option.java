/*    */ package com.adventnet.appmanager.struts.form;
/*    */ 
/*    */ public class Option {
/*  4 */   String label = "";
/*  5 */   String value = "";
/*    */   
/*    */   public Option() {}
/*    */   
/*    */   public Option(String arg1, String arg2)
/*    */   {
/* 11 */     this.label = arg1;
/* 12 */     this.value = arg2;
/*    */   }
/*    */   
/*    */   public String getLabel() {
/* 16 */     return this.label;
/*    */   }
/*    */   
/*    */   public String getValue() {
/* 20 */     return this.value;
/*    */   }
/*    */   
/*    */   public void setLabel(String temp) {
/* 24 */     this.label = temp;
/*    */   }
/*    */   
/*    */   public void setValue(String temp) {
/* 28 */     this.value = temp;
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\form\Option.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */