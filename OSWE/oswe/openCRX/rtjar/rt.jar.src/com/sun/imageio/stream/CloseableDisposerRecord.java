/*    */ package com.sun.imageio.stream;
/*    */ 
/*    */ import java.io.Closeable;
/*    */ import java.io.IOException;
/*    */ import sun.java2d.DisposerRecord;
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
/*    */ public class CloseableDisposerRecord
/*    */   implements DisposerRecord
/*    */ {
/*    */   private Closeable closeable;
/*    */   
/*    */   public CloseableDisposerRecord(Closeable paramCloseable) {
/* 41 */     this.closeable = paramCloseable;
/*    */   }
/*    */   
/*    */   public synchronized void dispose() {
/* 45 */     if (this.closeable != null)
/*    */       
/* 47 */       try { this.closeable.close(); }
/* 48 */       catch (IOException iOException) {  }
/*    */       finally
/* 50 */       { this.closeable = null; }
/*    */        
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/stream/CloseableDisposerRecord.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */