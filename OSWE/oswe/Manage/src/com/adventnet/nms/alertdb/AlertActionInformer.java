/*     */ package com.adventnet.nms.alertdb;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AlertActionInformer
/*     */   implements Serializable
/*     */ {
/*     */   private int operation;
/*     */   private String userName;
/*     */   private Alert alert;
/*     */   private String notes;
/*     */   private Vector alertList;
/*     */   private boolean batchUpdate;
/*     */   
/*     */   public AlertActionInformer() {}
/*     */   
/*     */   public AlertActionInformer(int operation, String user, Alert a)
/*     */   {
/*  67 */     this.userName = user;
/*  68 */     this.operation = operation;
/*  69 */     this.alert = a;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public AlertActionInformer(int operation, String user, Vector list)
/*     */   {
/*  80 */     this.userName = user;
/*  81 */     this.operation = operation;
/*  82 */     this.alertList = list;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public AlertActionInformer(int operation, String user, String notes, Alert a)
/*     */   {
/*  94 */     this.userName = user;
/*  95 */     this.operation = operation;
/*  96 */     this.notes = notes;
/*  97 */     this.alert = a;
/*     */   }
/*     */   
/*     */   public void setBatchUpdate(boolean bool)
/*     */   {
/* 102 */     this.batchUpdate = bool;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isBatchUpdate()
/*     */   {
/* 117 */     return this.batchUpdate;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getOperation()
/*     */   {
/* 145 */     return this.operation;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setOperation(int operation)
/*     */   {
/* 155 */     if (operation > 0)
/*     */     {
/* 157 */       this.operation = operation;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getUserName()
/*     */   {
/* 171 */     return this.userName;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setUserName(String user)
/*     */   {
/* 181 */     this.userName = user;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Alert getAlert()
/*     */   {
/* 195 */     return this.alert;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setAlert(Alert a)
/*     */   {
/* 205 */     this.alert = a;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Vector getAlertList()
/*     */   {
/* 219 */     return this.alertList;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setAlertList(Vector list)
/*     */   {
/* 229 */     this.alertList = list;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getNotes()
/*     */   {
/* 244 */     return this.notes;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setNotes(String notes)
/*     */   {
/* 254 */     this.notes = notes;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\nms\alertdb\AlertActionInformer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */