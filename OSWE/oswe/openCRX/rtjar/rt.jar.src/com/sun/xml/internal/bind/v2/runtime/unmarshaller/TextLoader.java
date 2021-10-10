/*    */ package com.sun.xml.internal.bind.v2.runtime.unmarshaller;
/*    */ 
/*    */ import com.sun.xml.internal.bind.api.AccessorException;
/*    */ import com.sun.xml.internal.bind.v2.runtime.Transducer;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TextLoader
/*    */   extends Loader
/*    */ {
/*    */   private final Transducer xducer;
/*    */   
/*    */   public TextLoader(Transducer xducer) {
/* 48 */     super(true);
/* 49 */     this.xducer = xducer;
/*    */   }
/*    */   
/*    */   public void text(UnmarshallingContext.State state, CharSequence text) throws SAXException {
/*    */     try {
/* 54 */       state.setTarget(this.xducer.parse(text));
/* 55 */     } catch (AccessorException e) {
/* 56 */       handleGenericException((Exception)e, true);
/* 57 */     } catch (RuntimeException e) {
/* 58 */       handleParseConversionException(state, e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/unmarshaller/TextLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */