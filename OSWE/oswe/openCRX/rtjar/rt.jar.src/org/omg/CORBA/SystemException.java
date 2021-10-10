/*     */ package org.omg.CORBA;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SystemException
/*     */   extends RuntimeException
/*     */ {
/*     */   public int minor;
/*     */   public CompletionStatus completed;
/*     */   
/*     */   protected SystemException(String paramString, int paramInt, CompletionStatus paramCompletionStatus) {
/*  74 */     super(paramString);
/*  75 */     this.minor = paramInt;
/*  76 */     this.completed = paramCompletionStatus;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  84 */     String str = super.toString();
/*     */ 
/*     */     
/*  87 */     int i = this.minor & 0xFFFFF000;
/*  88 */     switch (i) {
/*     */       case 1330446336:
/*  90 */         str = str + "  vmcid: OMG";
/*     */         break;
/*     */       case 1398079488:
/*  93 */         str = str + "  vmcid: SUN";
/*     */         break;
/*     */       default:
/*  96 */         str = str + "  vmcid: 0x" + Integer.toHexString(i);
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 101 */     int j = this.minor & 0xFFF;
/* 102 */     str = str + "  minor code: " + j;
/*     */ 
/*     */     
/* 105 */     switch (this.completed.value())
/*     */     { case 0:
/* 107 */         str = str + "  completed: Yes";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 117 */         return str;case 1: str = str + "  completed: No"; return str; }  str = str + " completed: Maybe"; return str;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CORBA/SystemException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */