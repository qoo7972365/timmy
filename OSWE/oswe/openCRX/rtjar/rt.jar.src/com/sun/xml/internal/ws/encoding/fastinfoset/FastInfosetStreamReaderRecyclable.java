/*    */ package com.sun.xml.internal.ws.encoding.fastinfoset;
/*    */ 
/*    */ import com.sun.xml.internal.fastinfoset.stax.StAXDocumentParser;
/*    */ import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;
/*    */ import java.io.InputStream;
/*    */ import javax.xml.stream.XMLStreamReader;
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
/*    */ public final class FastInfosetStreamReaderRecyclable
/*    */   extends StAXDocumentParser
/*    */   implements XMLStreamReaderFactory.RecycleAware
/*    */ {
/* 36 */   private static final FastInfosetStreamReaderFactory READER_FACTORY = FastInfosetStreamReaderFactory.getInstance();
/*    */ 
/*    */   
/*    */   public FastInfosetStreamReaderRecyclable() {}
/*    */ 
/*    */   
/*    */   public FastInfosetStreamReaderRecyclable(InputStream in) {
/* 43 */     super(in);
/*    */   }
/*    */   
/*    */   public void onRecycled() {
/* 47 */     READER_FACTORY.doRecycle((XMLStreamReader)this);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/encoding/fastinfoset/FastInfosetStreamReaderRecyclable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */