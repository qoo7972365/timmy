/*    */ package com.sun.corba.se.impl.ior;
/*    */ 
/*    */ import com.sun.corba.se.spi.ior.ObjectAdapterId;
/*    */ import java.util.Iterator;
/*    */ import org.omg.CORBA_2_3.portable.OutputStream;
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
/*    */ abstract class ObjectAdapterIdBase
/*    */   implements ObjectAdapterId
/*    */ {
/*    */   public boolean equals(Object paramObject) {
/* 37 */     if (!(paramObject instanceof ObjectAdapterId)) {
/* 38 */       return false;
/*    */     }
/* 40 */     ObjectAdapterId objectAdapterId = (ObjectAdapterId)paramObject;
/*    */     
/* 42 */     Iterator<String> iterator1 = iterator();
/* 43 */     Iterator<String> iterator2 = objectAdapterId.iterator();
/*    */     
/* 45 */     while (iterator1.hasNext() && iterator2.hasNext()) {
/* 46 */       String str1 = iterator1.next();
/* 47 */       String str2 = iterator2.next();
/*    */       
/* 49 */       if (!str1.equals(str2)) {
/* 50 */         return false;
/*    */       }
/*    */     } 
/* 53 */     return (iterator1.hasNext() == iterator2.hasNext());
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 58 */     int i = 17;
/* 59 */     Iterator<String> iterator = iterator();
/* 60 */     while (iterator.hasNext()) {
/* 61 */       String str = iterator.next();
/* 62 */       i = 37 * i + str.hashCode();
/*    */     } 
/* 64 */     return i;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 69 */     StringBuffer stringBuffer = new StringBuffer();
/* 70 */     stringBuffer.append("ObjectAdapterID[");
/* 71 */     Iterator<String> iterator = iterator();
/* 72 */     boolean bool = true;
/* 73 */     while (iterator.hasNext()) {
/* 74 */       String str = iterator.next();
/*    */       
/* 76 */       if (bool) {
/* 77 */         bool = false;
/*    */       } else {
/* 79 */         stringBuffer.append("/");
/*    */       } 
/* 81 */       stringBuffer.append(str);
/*    */     } 
/*    */     
/* 84 */     stringBuffer.append("]");
/*    */     
/* 86 */     return stringBuffer.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(OutputStream paramOutputStream) {
/* 91 */     paramOutputStream.write_long(getNumLevels());
/* 92 */     Iterator<String> iterator = iterator();
/* 93 */     while (iterator.hasNext()) {
/* 94 */       String str = iterator.next();
/* 95 */       paramOutputStream.write_string(str);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/ior/ObjectAdapterIdBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */