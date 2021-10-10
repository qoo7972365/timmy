/*     */ package com.sun.jndi.ldap;
/*     */ 
/*     */ import javax.naming.directory.SearchControls;
/*     */ import javax.naming.event.NamingListener;
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
/*     */ final class NotifierArgs
/*     */ {
/*     */   static final int ADDED_MASK = 1;
/*     */   static final int REMOVED_MASK = 2;
/*     */   static final int CHANGED_MASK = 4;
/*     */   static final int RENAMED_MASK = 8;
/*     */   String name;
/*     */   String filter;
/*     */   SearchControls controls;
/*     */   int mask;
/*     */   private int sum;
/*     */   
/*     */   NotifierArgs(String paramString, int paramInt, NamingListener paramNamingListener) {
/*  56 */     this(paramString, "(objectclass=*)", null, paramNamingListener);
/*     */ 
/*     */     
/*  59 */     if (paramInt != 1) {
/*  60 */       this.controls = new SearchControls();
/*  61 */       this.controls.setSearchScope(paramInt);
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
/*     */   public boolean equals(Object paramObject) {
/*     */     if (paramObject instanceof NotifierArgs) {
/*     */       NotifierArgs notifierArgs = (NotifierArgs)paramObject;
/*     */       return (this.mask == notifierArgs.mask && this.name.equals(notifierArgs.name) && this.filter.equals(notifierArgs.filter) && checkControls(notifierArgs.controls));
/*     */     } 
/*     */     return false;
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
/*     */   NotifierArgs(String paramString1, String paramString2, SearchControls paramSearchControls, NamingListener paramNamingListener)
/*     */   {
/* 125 */     this.sum = -1; this.name = paramString1; this.filter = paramString2; this.controls = paramSearchControls; if (paramNamingListener instanceof javax.naming.event.NamespaceChangeListener)
/*     */       this.mask |= 0xB;  if (paramNamingListener instanceof javax.naming.event.ObjectChangeListener)
/* 127 */       this.mask |= 0x4;  } public int hashCode() { if (this.sum == -1)
/* 128 */       this.sum = this.mask + this.name.hashCode() + this.filter.hashCode() + controlsCode(); 
/* 129 */     return this.sum; } private boolean checkControls(SearchControls paramSearchControls) {
/*     */     if (this.controls == null || paramSearchControls == null)
/*     */       return (paramSearchControls == this.controls); 
/*     */     return (this.controls.getSearchScope() == paramSearchControls.getSearchScope() && this.controls.getTimeLimit() == paramSearchControls.getTimeLimit() && this.controls.getDerefLinkFlag() == paramSearchControls.getDerefLinkFlag() && this.controls.getReturningObjFlag() == paramSearchControls.getReturningObjFlag() && this.controls.getCountLimit() == paramSearchControls.getCountLimit() && checkStringArrays(this.controls.getReturningAttributes(), paramSearchControls.getReturningAttributes()));
/*     */   } private int controlsCode() {
/* 134 */     if (this.controls == null) return 0;
/*     */ 
/*     */ 
/*     */     
/* 138 */     int i = this.controls.getTimeLimit() + (int)this.controls.getCountLimit() + (this.controls.getDerefLinkFlag() ? 1 : 0) + (this.controls.getReturningObjFlag() ? 1 : 0);
/*     */     
/* 140 */     String[] arrayOfString = this.controls.getReturningAttributes();
/* 141 */     if (arrayOfString != null) {
/* 142 */       for (byte b = 0; b < arrayOfString.length; b++) {
/* 143 */         i += arrayOfString[b].hashCode();
/*     */       }
/*     */     }
/*     */     
/* 147 */     return i;
/*     */   }
/*     */   
/*     */   private static boolean checkStringArrays(String[] paramArrayOfString1, String[] paramArrayOfString2) {
/*     */     if (paramArrayOfString1 == null || paramArrayOfString2 == null)
/*     */       return (paramArrayOfString1 == paramArrayOfString2); 
/*     */     if (paramArrayOfString1.length != paramArrayOfString2.length)
/*     */       return false; 
/*     */     for (byte b = 0; b < paramArrayOfString1.length; b++) {
/*     */       if (!paramArrayOfString1[b].equals(paramArrayOfString2[b]))
/*     */         return false; 
/*     */     } 
/*     */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/ldap/NotifierArgs.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */