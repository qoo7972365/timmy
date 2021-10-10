/*    */ package com.sun.xml.internal.ws.client.sei;
/*    */ 
/*    */ import com.sun.xml.internal.ws.model.ParameterImpl;
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
/*    */ public abstract class ValueSetterFactory
/*    */ {
/* 41 */   public static final ValueSetterFactory SYNC = new ValueSetterFactory() {
/*    */       public ValueSetter get(ParameterImpl p) {
/* 43 */         return ValueSetter.getSync(p);
/*    */       }
/*    */     };
/*    */   
/* 47 */   public static final ValueSetterFactory NONE = new ValueSetterFactory() {
/*    */       public ValueSetter get(ParameterImpl p) {
/* 49 */         throw new WebServiceException("This shouldn't happen. No response parameters.");
/*    */       }
/*    */     };
/*    */   
/* 53 */   public static final ValueSetterFactory SINGLE = new ValueSetterFactory() {
/*    */       public ValueSetter get(ParameterImpl p) {
/* 55 */         return ValueSetter.SINGLE_VALUE;
/*    */       }
/*    */     };
/*    */   
/*    */   public abstract ValueSetter get(ParameterImpl paramParameterImpl);
/*    */   
/*    */   public static final class AsyncBeanValueSetterFactory extends ValueSetterFactory {
/*    */     public AsyncBeanValueSetterFactory(Class asyncBean) {
/* 63 */       this.asyncBean = asyncBean;
/*    */     }
/*    */     private Class asyncBean;
/*    */     public ValueSetter get(ParameterImpl p) {
/* 67 */       return new ValueSetter.AsyncBeanValueSetter(p, this.asyncBean);
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/client/sei/ValueSetterFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */