/*    */ package com.sun.corba.se.impl.io;
/*    */ 
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
/*    */ 
/*    */ public class OptionalDataException
/*    */   extends IOException
/*    */ {
/*    */   public int length;
/*    */   public boolean eof;
/*    */   
/*    */   OptionalDataException(int paramInt) {
/* 52 */     this.eof = false;
/* 53 */     this.length = paramInt;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   OptionalDataException(boolean paramBoolean) {
/* 61 */     this.length = 0;
/* 62 */     this.eof = paramBoolean;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/io/OptionalDataException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */