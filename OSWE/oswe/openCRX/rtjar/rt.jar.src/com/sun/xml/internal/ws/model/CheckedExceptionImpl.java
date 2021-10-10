/*     */ package com.sun.xml.internal.ws.model;
/*     */ 
/*     */ import com.sun.xml.internal.bind.api.Bridge;
/*     */ import com.sun.xml.internal.ws.addressing.WsaActionUtil;
/*     */ import com.sun.xml.internal.ws.api.model.CheckedException;
/*     */ import com.sun.xml.internal.ws.api.model.ExceptionType;
/*     */ import com.sun.xml.internal.ws.api.model.JavaMethod;
/*     */ import com.sun.xml.internal.ws.api.model.SEIModel;
/*     */ import com.sun.xml.internal.ws.spi.db.TypeInfo;
/*     */ import com.sun.xml.internal.ws.spi.db.XMLBridge;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class CheckedExceptionImpl
/*     */   implements CheckedException
/*     */ {
/*     */   private final Class exceptionClass;
/*     */   private final TypeInfo detail;
/*     */   private final ExceptionType exceptionType;
/*     */   private final JavaMethodImpl javaMethod;
/*     */   private String messageName;
/*  54 */   private String faultAction = "";
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
/*     */   public CheckedExceptionImpl(JavaMethodImpl jm, Class exceptionClass, TypeInfo detail, ExceptionType exceptionType) {
/*  67 */     this.detail = detail;
/*  68 */     this.exceptionType = exceptionType;
/*  69 */     this.exceptionClass = exceptionClass;
/*  70 */     this.javaMethod = jm;
/*     */   }
/*     */   
/*     */   public AbstractSEIModelImpl getOwner() {
/*  74 */     return this.javaMethod.owner;
/*     */   }
/*     */   
/*     */   public JavaMethod getParent() {
/*  78 */     return this.javaMethod;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class getExceptionClass() {
/*  86 */     return this.exceptionClass;
/*     */   }
/*     */   
/*     */   public Class getDetailBean() {
/*  90 */     return (Class)this.detail.type;
/*     */   }
/*     */ 
/*     */   
/*     */   public Bridge getBridge() {
/*  95 */     return null;
/*     */   }
/*     */   
/*     */   public XMLBridge getBond() {
/*  99 */     return getOwner().getXMLBridge(this.detail);
/*     */   }
/*     */   
/*     */   public TypeInfo getDetailType() {
/* 103 */     return this.detail;
/*     */   }
/*     */   
/*     */   public ExceptionType getExceptionType() {
/* 107 */     return this.exceptionType;
/*     */   }
/*     */   
/*     */   public String getMessageName() {
/* 111 */     return this.messageName;
/*     */   }
/*     */   
/*     */   public void setMessageName(String messageName) {
/* 115 */     this.messageName = messageName;
/*     */   }
/*     */   
/*     */   public String getFaultAction() {
/* 119 */     return this.faultAction;
/*     */   }
/*     */   
/*     */   public void setFaultAction(String faultAction) {
/* 123 */     this.faultAction = faultAction;
/*     */   }
/*     */   
/*     */   public String getDefaultFaultAction() {
/* 127 */     return WsaActionUtil.getDefaultFaultAction(this.javaMethod, this);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/model/CheckedExceptionImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */