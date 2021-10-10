/*    */ package sun.java2d.cmm;
/*    */ 
/*    */ import java.awt.color.CMMException;
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
/*    */ public class Profile
/*    */ {
/*    */   private final long nativePtr;
/*    */   
/*    */   protected Profile(long paramLong) {
/* 34 */     this.nativePtr = paramLong;
/*    */   }
/*    */   
/*    */   protected final long getNativePtr() {
/* 38 */     if (this.nativePtr == 0L) {
/* 39 */       throw new CMMException("Invalid profile: ptr is null");
/*    */     }
/* 41 */     return this.nativePtr;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/cmm/Profile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */