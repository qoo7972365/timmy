/*    */ package java.awt.dnd;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectOutputStream;
/*    */ import java.io.OutputStream;
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
/*    */ final class SerializationTester
/*    */ {
/*    */   private static ObjectOutputStream stream;
/*    */   
/*    */   static {
/*    */     try {
/* 43 */       stream = new ObjectOutputStream(new OutputStream() {
/*    */             public void write(int param1Int) {}
/*    */           });
/* 46 */     } catch (IOException iOException) {}
/*    */   }
/*    */ 
/*    */   
/*    */   static boolean test(Object paramObject) {
/* 51 */     if (!(paramObject instanceof java.io.Serializable)) {
/* 52 */       return false;
/*    */     }
/*    */     
/*    */     try {
/* 56 */       stream.writeObject(paramObject);
/* 57 */     } catch (IOException iOException) {
/* 58 */       return false;
/*    */     } finally {
/*    */ 
/*    */       
/*    */       try {
/*    */         
/* 64 */         stream.reset();
/* 65 */       } catch (IOException iOException) {}
/*    */     } 
/*    */ 
/*    */     
/* 69 */     return true;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/dnd/SerializationTester.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */