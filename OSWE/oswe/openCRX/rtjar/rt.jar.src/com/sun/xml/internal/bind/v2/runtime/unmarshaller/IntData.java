/*    */ package com.sun.xml.internal.bind.v2.runtime.unmarshaller;
/*    */ 
/*    */ import com.sun.xml.internal.bind.v2.runtime.output.Pcdata;
/*    */ import com.sun.xml.internal.bind.v2.runtime.output.UTF8XmlOutput;
/*    */ import java.io.IOException;
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
/*    */ public class IntData
/*    */   extends Pcdata
/*    */ {
/*    */   private int data;
/*    */   private int length;
/*    */   
/*    */   public void reset(int i) {
/* 53 */     this.data = i;
/* 54 */     if (i == Integer.MIN_VALUE) {
/* 55 */       this.length = 11;
/*    */     } else {
/* 57 */       this.length = (i < 0) ? (stringSizeOfInt(-i) + 1) : stringSizeOfInt(i);
/*    */     } 
/*    */   }
/* 60 */   private static final int[] sizeTable = new int[] { 9, 99, 999, 9999, 99999, 999999, 9999999, 99999999, 999999999, Integer.MAX_VALUE };
/*    */ 
/*    */ 
/*    */   
/*    */   private static int stringSizeOfInt(int x) {
/* 65 */     for (int i = 0;; i++) {
/* 66 */       if (x <= sizeTable[i])
/* 67 */         return i + 1; 
/*    */     } 
/*    */   }
/*    */   public String toString() {
/* 71 */     return String.valueOf(this.data);
/*    */   }
/*    */ 
/*    */   
/*    */   public int length() {
/* 76 */     return this.length;
/*    */   }
/*    */   
/*    */   public char charAt(int index) {
/* 80 */     return toString().charAt(index);
/*    */   }
/*    */   
/*    */   public CharSequence subSequence(int start, int end) {
/* 84 */     return toString().substring(start, end);
/*    */   }
/*    */   
/*    */   public void writeTo(UTF8XmlOutput output) throws IOException {
/* 88 */     output.text(this.data);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/unmarshaller/IntData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */