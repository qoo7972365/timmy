/*     */ package com.sun.jndi.ldap;
/*     */ 
/*     */ import javax.naming.ldap.Control;
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
/*     */ public class BasicControl
/*     */   implements Control
/*     */ {
/*     */   protected String id;
/*     */   protected boolean criticality = false;
/*  57 */   protected byte[] value = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = -5914033725246428413L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicControl(String paramString) {
/*  69 */     this.id = paramString;
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
/*     */   public BasicControl(String paramString, boolean paramBoolean, byte[] paramArrayOfbyte) {
/*  81 */     this.id = paramString;
/*  82 */     this.criticality = paramBoolean;
/*  83 */     if (paramArrayOfbyte != null) {
/*  84 */       this.value = (byte[])paramArrayOfbyte.clone();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getID() {
/*  94 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCritical() {
/* 103 */     return this.criticality;
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
/*     */   public byte[] getEncodedValue() {
/* 116 */     return (this.value == null) ? null : (byte[])this.value.clone();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/ldap/BasicControl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */