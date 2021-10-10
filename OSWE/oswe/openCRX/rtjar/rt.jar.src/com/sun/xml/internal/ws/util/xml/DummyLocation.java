/*    */ package com.sun.xml.internal.ws.util.xml;
/*    */ 
/*    */ import javax.xml.stream.Location;
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
/*    */ public final class DummyLocation
/*    */   implements Location
/*    */ {
/* 38 */   public static final Location INSTANCE = new DummyLocation();
/*    */   
/*    */   public int getCharacterOffset() {
/* 41 */     return -1;
/*    */   }
/*    */   public int getColumnNumber() {
/* 44 */     return -1;
/*    */   }
/*    */   public int getLineNumber() {
/* 47 */     return -1;
/*    */   }
/*    */   public String getPublicId() {
/* 50 */     return null;
/*    */   }
/*    */   public String getSystemId() {
/* 53 */     return null;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/util/xml/DummyLocation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */