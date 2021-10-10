/*    */ package com.sun.xml.internal.stream.events;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.Writer;
/*    */ import javax.xml.namespace.QName;
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
/*    */ public class NamedEvent
/*    */   extends DummyEvent
/*    */ {
/*    */   private QName name;
/*    */   
/*    */   public NamedEvent() {}
/*    */   
/*    */   public NamedEvent(QName qname) {
/* 43 */     this.name = qname;
/*    */   }
/*    */ 
/*    */   
/*    */   public NamedEvent(String prefix, String uri, String localpart) {
/* 48 */     this.name = new QName(uri, localpart, prefix);
/*    */   }
/*    */   
/*    */   public String getPrefix() {
/* 52 */     return this.name.getPrefix();
/*    */   }
/*    */ 
/*    */   
/*    */   public QName getName() {
/* 57 */     return this.name;
/*    */   }
/*    */   
/*    */   public void setName(QName qname) {
/* 61 */     this.name = qname;
/*    */   }
/*    */   
/*    */   public String nameAsString() {
/* 65 */     if ("".equals(this.name.getNamespaceURI()))
/* 66 */       return this.name.getLocalPart(); 
/* 67 */     if (this.name.getPrefix() != null) {
/* 68 */       return "['" + this.name.getNamespaceURI() + "']:" + getPrefix() + ":" + this.name.getLocalPart();
/*    */     }
/* 70 */     return "['" + this.name.getNamespaceURI() + "']:" + this.name.getLocalPart();
/*    */   }
/*    */   
/*    */   public String getNamespace() {
/* 74 */     return this.name.getNamespaceURI();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeAsEncodedUnicodeEx(Writer writer) throws IOException {
/* 80 */     writer.write(nameAsString());
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/stream/events/NamedEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */