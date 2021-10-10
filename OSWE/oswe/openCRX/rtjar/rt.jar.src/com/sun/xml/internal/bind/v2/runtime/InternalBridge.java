/*    */ package com.sun.xml.internal.bind.v2.runtime;
/*    */ 
/*    */ import com.sun.xml.internal.bind.api.Bridge;
/*    */ import com.sun.xml.internal.bind.api.JAXBRIContext;
/*    */ import java.io.IOException;
/*    */ import javax.xml.stream.XMLStreamException;
/*    */ import org.xml.sax.SAXException;
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
/*    */ abstract class InternalBridge<T>
/*    */   extends Bridge<T>
/*    */ {
/*    */   protected InternalBridge(JAXBContextImpl context) {
/* 42 */     super(context);
/*    */   }
/*    */   
/*    */   public JAXBContextImpl getContext() {
/* 46 */     return this.context;
/*    */   }
/*    */   
/*    */   abstract void marshal(T paramT, XMLSerializer paramXMLSerializer) throws IOException, SAXException, XMLStreamException;
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/InternalBridge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */