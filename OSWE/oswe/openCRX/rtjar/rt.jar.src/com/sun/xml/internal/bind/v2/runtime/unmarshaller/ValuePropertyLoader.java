/*    */ package com.sun.xml.internal.bind.v2.runtime.unmarshaller;
/*    */ 
/*    */ import com.sun.xml.internal.bind.api.AccessorException;
/*    */ import com.sun.xml.internal.bind.v2.runtime.reflect.TransducedAccessor;
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
/*    */ public class ValuePropertyLoader
/*    */   extends Loader
/*    */ {
/*    */   private final TransducedAccessor xacc;
/*    */   
/*    */   public ValuePropertyLoader(TransducedAccessor xacc) {
/* 46 */     super(true);
/* 47 */     this.xacc = xacc;
/*    */   }
/*    */   
/*    */   public void text(UnmarshallingContext.State state, CharSequence text) throws SAXException {
/*    */     try {
/* 52 */       this.xacc.parse(state.getTarget(), text);
/* 53 */     } catch (AccessorException e) {
/* 54 */       handleGenericException((Exception)e, true);
/* 55 */     } catch (RuntimeException e) {
/* 56 */       if (state.getPrev() != null) {
/* 57 */         if (!(state.getPrev().getTarget() instanceof javax.xml.bind.JAXBElement)) {
/* 58 */           handleParseConversionException(state, e);
/*    */         
/*    */         }
/*    */       }
/*    */       else {
/*    */         
/* 64 */         handleParseConversionException(state, e);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/unmarshaller/ValuePropertyLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */