/*    */ package com.sun.xml.internal.ws.developer;
/*    */ 
/*    */ import com.sun.xml.internal.org.jvnet.staxex.StreamingDataHandler;
/*    */ import java.net.URL;
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
/*    */ public abstract class StreamingDataHandler
/*    */   extends StreamingDataHandler
/*    */ {
/*    */   private String hrefCid;
/*    */   
/*    */   public StreamingDataHandler(Object o, String s) {
/* 56 */     super(o, s);
/*    */   }
/*    */   
/*    */   public StreamingDataHandler(URL url) {
/* 60 */     super(url);
/*    */   }
/*    */   
/*    */   public StreamingDataHandler(DataSource dataSource) {
/* 64 */     super(dataSource);
/*    */   }
/*    */   
/*    */   public String getHrefCid() {
/* 68 */     return this.hrefCid;
/*    */   }
/*    */   
/*    */   public void setHrefCid(String cid) {
/* 72 */     this.hrefCid = cid;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/developer/StreamingDataHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */