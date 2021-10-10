/*    */ package com.sun.xml.internal.ws.encoding;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import javax.activation.DataHandler;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DataHandlerDataSource
/*    */   implements DataSource
/*    */ {
/*    */   private final DataHandler dataHandler;
/*    */   
/*    */   public DataHandlerDataSource(DataHandler dh) {
/* 43 */     this.dataHandler = dh;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public InputStream getInputStream() throws IOException {
/* 52 */     return this.dataHandler.getInputStream();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public OutputStream getOutputStream() throws IOException {
/* 61 */     return this.dataHandler.getOutputStream();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getContentType() {
/* 70 */     return this.dataHandler.getContentType();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 79 */     return this.dataHandler.getName();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/encoding/DataHandlerDataSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */