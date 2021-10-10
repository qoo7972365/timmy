/*    */ package com.sun.xml.internal.bind.v2.runtime.output;
/*    */ 
/*    */ import com.sun.xml.internal.bind.marshaller.CharacterEscapeHandler;
/*    */ import com.sun.xml.internal.bind.marshaller.NoEscapeHandler;
/*    */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Base64Data;
/*    */ import com.sun.xml.internal.org.jvnet.staxex.XMLStreamWriterEx;
/*    */ import javax.xml.stream.XMLStreamException;
/*    */ import javax.xml.stream.XMLStreamWriter;
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
/*    */ public final class StAXExStreamWriterOutput
/*    */   extends XMLStreamWriterOutput
/*    */ {
/*    */   private final XMLStreamWriterEx out;
/*    */   
/*    */   public StAXExStreamWriterOutput(XMLStreamWriterEx out) {
/* 44 */     super((XMLStreamWriter)out, (CharacterEscapeHandler)NoEscapeHandler.theInstance);
/* 45 */     this.out = out;
/*    */   }
/*    */   
/*    */   public void text(Pcdata value, boolean needsSeparatingWhitespace) throws XMLStreamException {
/* 49 */     if (needsSeparatingWhitespace) {
/* 50 */       this.out.writeCharacters(" ");
/*    */     }
/*    */     
/* 53 */     if (!(value instanceof Base64Data)) {
/* 54 */       this.out.writeCharacters(value.toString());
/*    */     } else {
/* 56 */       Base64Data v = (Base64Data)value;
/* 57 */       this.out.writeBinary(v.getDataHandler());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/output/StAXExStreamWriterOutput.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */