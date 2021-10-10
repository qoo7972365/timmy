/*    */ package com.sun.xml.internal.bind.v2.runtime.unmarshaller;
/*    */ 
/*    */ import com.sun.xml.internal.bind.v2.model.core.WildcardMode;
/*    */ import javax.xml.bind.annotation.DomHandler;
/*    */ import javax.xml.transform.Result;
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
/*    */ 
/*    */ 
/*    */ public final class WildcardLoader
/*    */   extends ProxyLoader
/*    */ {
/*    */   private final DomLoader dom;
/*    */   private final WildcardMode mode;
/*    */   
/*    */   public WildcardLoader(DomHandler<?, Result> dom, WildcardMode mode) {
/* 52 */     this.dom = new DomLoader<>(dom);
/* 53 */     this.mode = mode;
/*    */   }
/*    */   
/*    */   protected Loader selectLoader(UnmarshallingContext.State state, TagName tag) throws SAXException {
/* 57 */     UnmarshallingContext context = state.getContext();
/*    */     
/* 59 */     if (this.mode.allowTypedObject) {
/* 60 */       Loader l = context.selectRootLoader(state, tag);
/* 61 */       if (l != null)
/* 62 */         return l; 
/*    */     } 
/* 64 */     if (this.mode.allowDom) {
/* 65 */       return this.dom;
/*    */     }
/*    */     
/* 68 */     return Discarder.INSTANCE;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/unmarshaller/WildcardLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */