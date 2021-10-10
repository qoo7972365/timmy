/*    */ package com.sun.corba.se.impl.ior;
/*    */ 
/*    */ import java.util.Arrays;
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
/*    */ public class ObjectAdapterIdArray
/*    */   extends ObjectAdapterIdBase
/*    */ {
/*    */   private final String[] objectAdapterId;
/*    */   
/*    */   public ObjectAdapterIdArray(String[] paramArrayOfString) {
/* 36 */     this.objectAdapterId = paramArrayOfString;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ObjectAdapterIdArray(String paramString1, String paramString2) {
/* 43 */     this.objectAdapterId = new String[2];
/* 44 */     this.objectAdapterId[0] = paramString1;
/* 45 */     this.objectAdapterId[1] = paramString2;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getNumLevels() {
/* 50 */     return this.objectAdapterId.length;
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterator iterator() {
/* 55 */     return Arrays.<String>asList(this.objectAdapterId).iterator();
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getAdapterName() {
/* 60 */     return (String[])this.objectAdapterId.clone();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/ior/ObjectAdapterIdArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */