/*    */ package java.util;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.NotSerializableException;
/*    */ import java.io.ObjectInputStream;
/*    */ import java.io.ObjectOutputStream;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InvalidPropertiesFormatException
/*    */   extends IOException
/*    */ {
/*    */   private static final long serialVersionUID = 7763056076009360219L;
/*    */   
/*    */   public InvalidPropertiesFormatException(Throwable paramThrowable) {
/* 58 */     super((paramThrowable == null) ? null : paramThrowable.toString());
/* 59 */     initCause(paramThrowable);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public InvalidPropertiesFormatException(String paramString) {
/* 70 */     super(paramString);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws NotSerializableException {
/* 80 */     throw new NotSerializableException("Not serializable.");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void readObject(ObjectInputStream paramObjectInputStream) throws NotSerializableException {
/* 90 */     throw new NotSerializableException("Not serializable.");
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/InvalidPropertiesFormatException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */