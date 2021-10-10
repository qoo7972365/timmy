/*    */ package com.sun.xml.internal.ws.encoding;
/*    */ 
/*    */ import com.sun.xml.internal.ws.developer.StreamingDataHandler;
/*    */ import java.io.File;
/*    */ import java.io.FileOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import javax.activation.DataSource;
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
/*    */ public class DataSourceStreamingDataHandler
/*    */   extends StreamingDataHandler
/*    */ {
/*    */   public DataSourceStreamingDataHandler(DataSource ds) {
/* 39 */     super(ds);
/*    */   }
/*    */ 
/*    */   
/*    */   public InputStream readOnce() throws IOException {
/* 44 */     return getInputStream();
/*    */   }
/*    */ 
/*    */   
/*    */   public void moveTo(File file) throws IOException {
/* 49 */     InputStream in = getInputStream();
/* 50 */     OutputStream os = new FileOutputStream(file);
/*    */     try {
/* 52 */       byte[] temp = new byte[8192];
/*    */       int len;
/* 54 */       while ((len = in.read(temp)) != -1) {
/* 55 */         os.write(temp, 0, len);
/*    */       }
/* 57 */       in.close();
/*    */     } finally {
/* 59 */       if (os != null)
/* 60 */         os.close(); 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void close() throws IOException {}
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/encoding/DataSourceStreamingDataHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */