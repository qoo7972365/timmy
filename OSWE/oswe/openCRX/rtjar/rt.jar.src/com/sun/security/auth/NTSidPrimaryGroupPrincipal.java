/*     */ package com.sun.security.auth;
/*     */ 
/*     */ import java.text.MessageFormat;
/*     */ import jdk.Exported;
/*     */ import sun.security.util.ResourcesMgr;
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
/*     */ @Exported
/*     */ public class NTSidPrimaryGroupPrincipal
/*     */   extends NTSid
/*     */ {
/*     */   private static final long serialVersionUID = 8011978367305190527L;
/*     */   
/*     */   public NTSidPrimaryGroupPrincipal(String paramString) {
/*  59 */     super(paramString);
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
/*     */   public String toString() {
/*  74 */     MessageFormat messageFormat = new MessageFormat(ResourcesMgr.getString("NTSidPrimaryGroupPrincipal.name", "sun.security.util.AuthResources"));
/*     */     
/*  76 */     Object[] arrayOfObject = { getName() };
/*  77 */     return messageFormat.format(arrayOfObject);
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
/*     */   public boolean equals(Object paramObject) {
/*  96 */     if (paramObject == null) {
/*  97 */       return false;
/*     */     }
/*  99 */     if (this == paramObject) {
/* 100 */       return true;
/*     */     }
/* 102 */     if (!(paramObject instanceof NTSidPrimaryGroupPrincipal)) {
/* 103 */       return false;
/*     */     }
/* 105 */     return super.equals(paramObject);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/security/auth/NTSidPrimaryGroupPrincipal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */