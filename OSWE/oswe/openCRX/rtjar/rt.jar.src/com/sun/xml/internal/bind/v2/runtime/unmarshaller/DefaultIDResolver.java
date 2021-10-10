/*    */ package com.sun.xml.internal.bind.v2.runtime.unmarshaller;
/*    */ 
/*    */ import com.sun.xml.internal.bind.IDResolver;
/*    */ import java.util.HashMap;
/*    */ import java.util.concurrent.Callable;
/*    */ import javax.xml.bind.ValidationEventHandler;
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
/*    */ final class DefaultIDResolver
/*    */   extends IDResolver
/*    */ {
/* 44 */   private HashMap<String, Object> idmap = null;
/*    */ 
/*    */   
/*    */   public void startDocument(ValidationEventHandler eventHandler) throws SAXException {
/* 48 */     if (this.idmap != null) {
/* 49 */       this.idmap.clear();
/*    */     }
/*    */   }
/*    */   
/*    */   public void bind(String id, Object obj) {
/* 54 */     if (this.idmap == null) this.idmap = new HashMap<>(); 
/* 55 */     this.idmap.put(id, obj);
/*    */   }
/*    */ 
/*    */   
/*    */   public Callable resolve(final String id, Class targetType) {
/* 60 */     return new Callable() {
/*    */         public Object call() throws Exception {
/* 62 */           if (DefaultIDResolver.this.idmap == null) return null; 
/* 63 */           return DefaultIDResolver.this.idmap.get(id);
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/unmarshaller/DefaultIDResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */