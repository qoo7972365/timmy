/*     */ package sun.rmi.log;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import sun.rmi.server.MarshalInputStream;
/*     */ import sun.rmi.server.MarshalOutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class LogHandler
/*     */ {
/*     */   public abstract Object initialSnapshot() throws Exception;
/*     */   
/*     */   public void snapshot(OutputStream paramOutputStream, Object paramObject) throws Exception {
/*  71 */     MarshalOutputStream marshalOutputStream = new MarshalOutputStream(paramOutputStream);
/*  72 */     marshalOutputStream.writeObject(paramObject);
/*  73 */     marshalOutputStream.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object recover(InputStream paramInputStream) throws Exception {
/*  87 */     MarshalInputStream marshalInputStream = new MarshalInputStream(paramInputStream);
/*  88 */     return marshalInputStream.readObject();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeUpdate(LogOutputStream paramLogOutputStream, Object paramObject) throws Exception {
/* 102 */     MarshalOutputStream marshalOutputStream = new MarshalOutputStream(paramLogOutputStream);
/* 103 */     marshalOutputStream.writeObject(paramObject);
/* 104 */     marshalOutputStream.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object readUpdate(LogInputStream paramLogInputStream, Object paramObject) throws Exception {
/* 121 */     MarshalInputStream marshalInputStream = new MarshalInputStream(paramLogInputStream);
/* 122 */     return applyUpdate(marshalInputStream.readObject(), paramObject);
/*     */   }
/*     */   
/*     */   public abstract Object applyUpdate(Object paramObject1, Object paramObject2) throws Exception;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/rmi/log/LogHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */