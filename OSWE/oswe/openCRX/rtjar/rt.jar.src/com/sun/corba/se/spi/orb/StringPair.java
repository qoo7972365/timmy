/*    */ package com.sun.corba.se.spi.orb;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StringPair
/*    */ {
/*    */   private String first;
/*    */   private String second;
/*    */   
/*    */   public boolean equals(Object paramObject) {
/* 33 */     if (this == paramObject) {
/* 34 */       return true;
/*    */     }
/* 36 */     if (!(paramObject instanceof StringPair)) {
/* 37 */       return false;
/*    */     }
/* 39 */     StringPair stringPair = (StringPair)paramObject;
/*    */     
/* 41 */     return (this.first.equals(stringPair.first) && this.second
/* 42 */       .equals(stringPair.second));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 47 */     return this.first.hashCode() ^ this.second.hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public StringPair(String paramString1, String paramString2) {
/* 52 */     this.first = paramString1;
/* 53 */     this.second = paramString2;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getFirst() {
/* 58 */     return this.first;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSecond() {
/* 63 */     return this.second;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/orb/StringPair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */