/*     */ package javax.naming;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class RefAddr
/*     */   implements Serializable
/*     */ {
/*     */   protected String addrType;
/*     */   private static final long serialVersionUID = -1468165120479154358L;
/*     */   
/*     */   protected RefAddr(String paramString) {
/*  71 */     this.addrType = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getType() {
/*  80 */     return this.addrType;
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
/*     */   public abstract Object getContent();
/*     */ 
/*     */ 
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
/* 107 */     if (paramObject != null && paramObject instanceof RefAddr) {
/* 108 */       RefAddr refAddr = (RefAddr)paramObject;
/* 109 */       if (this.addrType.compareTo(refAddr.addrType) == 0) {
/* 110 */         Object object1 = getContent();
/* 111 */         Object object2 = refAddr.getContent();
/* 112 */         if (object1 == object2)
/* 113 */           return true; 
/* 114 */         if (object1 != null)
/* 115 */           return object1.equals(object2); 
/*     */       } 
/*     */     } 
/* 118 */     return false;
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
/*     */   public int hashCode() {
/* 130 */     return (getContent() == null) ? this.addrType
/* 131 */       .hashCode() : (this.addrType
/* 132 */       .hashCode() + getContent().hashCode());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 142 */     StringBuffer stringBuffer = new StringBuffer("Type: " + this.addrType + "\n");
/*     */     
/* 144 */     stringBuffer.append("Content: " + getContent() + "\n");
/* 145 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/naming/RefAddr.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */