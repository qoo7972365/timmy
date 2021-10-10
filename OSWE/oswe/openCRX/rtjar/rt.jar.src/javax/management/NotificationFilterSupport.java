/*     */ package javax.management;
/*     */ 
/*     */ import java.util.List;
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
/*     */ public class NotificationFilterSupport
/*     */   implements NotificationFilter
/*     */ {
/*     */   private static final long serialVersionUID = 6579080007561786969L;
/*  67 */   private List<String> enabledTypes = new Vector<>();
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
/*     */   public synchronized boolean isNotificationEnabled(Notification paramNotification) {
/*  81 */     String str = paramNotification.getType();
/*     */     
/*  83 */     if (str == null) {
/*  84 */       return false;
/*     */     }
/*     */     try {
/*  87 */       for (String str1 : this.enabledTypes) {
/*  88 */         if (str.startsWith(str1)) {
/*  89 */           return true;
/*     */         }
/*     */       } 
/*  92 */     } catch (NullPointerException nullPointerException) {
/*     */       
/*  94 */       return false;
/*     */     } 
/*  96 */     return false;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void enableType(String paramString) throws IllegalArgumentException {
/* 127 */     if (paramString == null) {
/* 128 */       throw new IllegalArgumentException("The prefix cannot be null.");
/*     */     }
/* 130 */     if (!this.enabledTypes.contains(paramString)) {
/* 131 */       this.enabledTypes.add(paramString);
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
/*     */   public synchronized void disableType(String paramString) {
/* 143 */     this.enabledTypes.remove(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void disableAllTypes() {
/* 150 */     this.enabledTypes.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized Vector<String> getEnabledTypes() {
/* 160 */     return (Vector<String>)this.enabledTypes;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/NotificationFilterSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */