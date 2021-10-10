/*    */ package com.sun.xml.internal.ws.wsdl.parser;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import javax.xml.ws.WebServiceException;
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
/*    */ public class InaccessibleWSDLException
/*    */   extends WebServiceException
/*    */ {
/*    */   private final List<Throwable> errors;
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public InaccessibleWSDLException(List<Throwable> errors) {
/* 48 */     super(errors.size() + " counts of InaccessibleWSDLException.\n");
/* 49 */     assert !errors.isEmpty() : "there must be at least one error";
/* 50 */     this.errors = Collections.unmodifiableList(new ArrayList<>(errors));
/*    */   }
/*    */   
/*    */   public String toString() {
/* 54 */     StringBuilder sb = new StringBuilder(super.toString());
/* 55 */     sb.append('\n');
/*    */     
/* 57 */     for (Throwable error : this.errors) {
/* 58 */       sb.append(error.toString()).append('\n');
/*    */     }
/* 60 */     return sb.toString();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<Throwable> getErrors() {
/* 71 */     return this.errors;
/*    */   }
/*    */   
/*    */   public static class Builder implements ErrorHandler {
/* 75 */     private final List<Throwable> list = new ArrayList<>();
/*    */     public void error(Throwable e) {
/* 77 */       this.list.add(e);
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public void check() throws InaccessibleWSDLException {
/* 84 */       if (this.list.isEmpty())
/*    */         return; 
/* 86 */       throw new InaccessibleWSDLException(this.list);
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/wsdl/parser/InaccessibleWSDLException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */