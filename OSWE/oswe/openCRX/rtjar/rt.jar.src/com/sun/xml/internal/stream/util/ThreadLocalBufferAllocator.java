/*    */ package com.sun.xml.internal.stream.util;
/*    */ 
/*    */ import java.lang.ref.SoftReference;
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
/*    */ public class ThreadLocalBufferAllocator
/*    */ {
/* 42 */   private static final ThreadLocal<SoftReference<BufferAllocator>> TL = new ThreadLocal<>();
/*    */   
/*    */   public static BufferAllocator getBufferAllocator() {
/* 45 */     BufferAllocator ba = null;
/* 46 */     SoftReference<BufferAllocator> sr = TL.get();
/* 47 */     if (sr != null) {
/* 48 */       ba = sr.get();
/*    */     }
/* 50 */     if (ba == null) {
/* 51 */       ba = new BufferAllocator();
/* 52 */       sr = new SoftReference<>(ba);
/* 53 */       TL.set(sr);
/*    */     } 
/* 55 */     return ba;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/stream/util/ThreadLocalBufferAllocator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */