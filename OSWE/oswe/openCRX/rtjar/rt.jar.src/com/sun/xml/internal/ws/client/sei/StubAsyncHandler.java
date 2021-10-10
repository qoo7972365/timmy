/*     */ package com.sun.xml.internal.ws.client.sei;
/*     */ 
/*     */ import com.sun.xml.internal.ws.api.message.MessageContextFactory;
/*     */ import com.sun.xml.internal.ws.model.JavaMethodImpl;
/*     */ import com.sun.xml.internal.ws.model.ParameterImpl;
/*     */ import com.sun.xml.internal.ws.model.WrapperParameter;
/*     */ import java.util.List;
/*     */ import javax.jws.soap.SOAPBinding;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StubAsyncHandler
/*     */   extends StubHandler
/*     */ {
/*     */   private final Class asyncBeanClass;
/*     */   
/*     */   public StubAsyncHandler(JavaMethodImpl jm, JavaMethodImpl sync, MessageContextFactory mcf) {
/*  42 */     super(sync, mcf);
/*     */     
/*  44 */     List<ParameterImpl> rp = sync.getResponseParameters();
/*  45 */     int size = 0;
/*  46 */     for (ParameterImpl param : rp) {
/*  47 */       if (param.isWrapperStyle()) {
/*  48 */         WrapperParameter wrapParam = (WrapperParameter)param;
/*  49 */         size += wrapParam.getWrapperChildren().size();
/*  50 */         if (sync.getBinding().getStyle() == SOAPBinding.Style.DOCUMENT)
/*     */         {
/*     */           
/*  53 */           size += 2; } 
/*     */         continue;
/*     */       } 
/*  56 */       size++;
/*     */     } 
/*     */ 
/*     */     
/*  60 */     Class tempWrap = null;
/*  61 */     if (size > 1) {
/*  62 */       rp = jm.getResponseParameters();
/*  63 */       for (ParameterImpl param : rp) {
/*  64 */         if (param.isWrapperStyle()) {
/*  65 */           WrapperParameter wrapParam = (WrapperParameter)param;
/*  66 */           if (sync.getBinding().getStyle() == SOAPBinding.Style.DOCUMENT) {
/*     */             
/*  68 */             tempWrap = (Class)(wrapParam.getTypeInfo()).type;
/*     */             break;
/*     */           } 
/*  71 */           for (ParameterImpl p : wrapParam.getWrapperChildren()) {
/*  72 */             if (p.getIndex() == -1) {
/*  73 */               tempWrap = (Class)(p.getTypeInfo()).type;
/*     */               break;
/*     */             } 
/*     */           } 
/*  77 */           if (tempWrap != null)
/*     */             break; 
/*     */           continue;
/*     */         } 
/*  81 */         if (param.getIndex() == -1) {
/*  82 */           tempWrap = (Class)(param.getTypeInfo()).type;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*  88 */     this.asyncBeanClass = tempWrap;
/*     */     
/*  90 */     switch (size) {
/*     */       case 0:
/*  92 */         this.responseBuilder = buildResponseBuilder(sync, ValueSetterFactory.NONE);
/*     */         return;
/*     */       case 1:
/*  95 */         this.responseBuilder = buildResponseBuilder(sync, ValueSetterFactory.SINGLE);
/*     */         return;
/*     */     } 
/*  98 */     this.responseBuilder = buildResponseBuilder(sync, new ValueSetterFactory.AsyncBeanValueSetterFactory(this.asyncBeanClass));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initArgs(Object[] args) throws Exception {
/* 104 */     if (this.asyncBeanClass != null) {
/* 105 */       args[0] = this.asyncBeanClass.newInstance();
/*     */     }
/*     */   }
/*     */   
/*     */   ValueGetterFactory getValueGetterFactory() {
/* 110 */     return ValueGetterFactory.ASYNC;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/client/sei/StubAsyncHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */