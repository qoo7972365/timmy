/*     */ package com.sun.xml.internal.ws.api.policy.subject;
/*     */ 
/*     */ import com.sun.istack.internal.logging.Logger;
/*     */ import com.sun.xml.internal.ws.resources.BindingApiMessages;
/*     */ import javax.xml.namespace.QName;
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
/*     */ 
/*     */ public class BindingSubject
/*     */ {
/*     */   private enum WsdlMessageType
/*     */   {
/*  46 */     NO_MESSAGE,
/*  47 */     INPUT,
/*  48 */     OUTPUT,
/*  49 */     FAULT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private enum WsdlNameScope
/*     */   {
/*  60 */     SERVICE,
/*  61 */     ENDPOINT,
/*  62 */     OPERATION,
/*  63 */     MESSAGE;
/*     */   }
/*     */   
/*  66 */   private static final Logger LOGGER = Logger.getLogger(BindingSubject.class);
/*     */   
/*     */   private final QName name;
/*     */   private final WsdlMessageType messageType;
/*     */   private final WsdlNameScope nameScope;
/*     */   private final BindingSubject parent;
/*     */   
/*     */   BindingSubject(QName name, WsdlNameScope scope, BindingSubject parent) {
/*  74 */     this(name, WsdlMessageType.NO_MESSAGE, scope, parent);
/*     */   }
/*     */   
/*     */   BindingSubject(QName name, WsdlMessageType messageType, WsdlNameScope scope, BindingSubject parent) {
/*  78 */     this.name = name;
/*  79 */     this.messageType = messageType;
/*  80 */     this.nameScope = scope;
/*  81 */     this.parent = parent;
/*     */   }
/*     */   
/*     */   public static BindingSubject createBindingSubject(QName bindingName) {
/*  85 */     return new BindingSubject(bindingName, WsdlNameScope.ENDPOINT, null);
/*     */   }
/*     */   
/*     */   public static BindingSubject createOperationSubject(QName bindingName, QName operationName) {
/*  89 */     BindingSubject bindingSubject = createBindingSubject(bindingName);
/*  90 */     return new BindingSubject(operationName, WsdlNameScope.OPERATION, bindingSubject);
/*     */   }
/*     */   
/*     */   public static BindingSubject createInputMessageSubject(QName bindingName, QName operationName, QName messageName) {
/*  94 */     BindingSubject operationSubject = createOperationSubject(bindingName, operationName);
/*  95 */     return new BindingSubject(messageName, WsdlMessageType.INPUT, WsdlNameScope.MESSAGE, operationSubject);
/*     */   }
/*     */   
/*     */   public static BindingSubject createOutputMessageSubject(QName bindingName, QName operationName, QName messageName) {
/*  99 */     BindingSubject operationSubject = createOperationSubject(bindingName, operationName);
/* 100 */     return new BindingSubject(messageName, WsdlMessageType.OUTPUT, WsdlNameScope.MESSAGE, operationSubject);
/*     */   }
/*     */   
/*     */   public static BindingSubject createFaultMessageSubject(QName bindingName, QName operationName, QName messageName) {
/* 104 */     if (messageName == null) {
/* 105 */       throw (IllegalArgumentException)LOGGER.logSevereException(new IllegalArgumentException(BindingApiMessages.BINDING_API_NO_FAULT_MESSAGE_NAME()));
/*     */     }
/* 107 */     BindingSubject operationSubject = createOperationSubject(bindingName, operationName);
/* 108 */     return new BindingSubject(messageName, WsdlMessageType.FAULT, WsdlNameScope.MESSAGE, operationSubject);
/*     */   }
/*     */   
/*     */   public QName getName() {
/* 112 */     return this.name;
/*     */   }
/*     */   
/*     */   public BindingSubject getParent() {
/* 116 */     return this.parent;
/*     */   }
/*     */   
/*     */   public boolean isBindingSubject() {
/* 120 */     if (this.nameScope == WsdlNameScope.ENDPOINT) {
/* 121 */       return (this.parent == null);
/*     */     }
/*     */     
/* 124 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOperationSubject() {
/* 129 */     if (this.nameScope == WsdlNameScope.OPERATION && 
/* 130 */       this.parent != null) {
/* 131 */       return this.parent.isBindingSubject();
/*     */     }
/*     */     
/* 134 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isMessageSubject() {
/* 138 */     if (this.nameScope == WsdlNameScope.MESSAGE && 
/* 139 */       this.parent != null) {
/* 140 */       return this.parent.isOperationSubject();
/*     */     }
/*     */     
/* 143 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isInputMessageSubject() {
/* 147 */     return (isMessageSubject() && this.messageType == WsdlMessageType.INPUT);
/*     */   }
/*     */   
/*     */   public boolean isOutputMessageSubject() {
/* 151 */     return (isMessageSubject() && this.messageType == WsdlMessageType.OUTPUT);
/*     */   }
/*     */   
/*     */   public boolean isFaultMessageSubject() {
/* 155 */     return (isMessageSubject() && this.messageType == WsdlMessageType.FAULT);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object that) {
/* 160 */     if (this == that) {
/* 161 */       return true;
/*     */     }
/*     */     
/* 164 */     if (that == null || !(that instanceof BindingSubject)) {
/* 165 */       return false;
/*     */     }
/*     */     
/* 168 */     BindingSubject thatSubject = (BindingSubject)that;
/* 169 */     boolean isEqual = true;
/*     */     
/* 171 */     isEqual = (isEqual && ((this.name == null) ? (thatSubject.name == null) : this.name.equals(thatSubject.name)));
/* 172 */     isEqual = (isEqual && this.messageType.equals(thatSubject.messageType));
/* 173 */     isEqual = (isEqual && this.nameScope.equals(thatSubject.nameScope));
/* 174 */     isEqual = (isEqual && ((this.parent == null) ? (thatSubject.parent == null) : this.parent.equals(thatSubject.parent)));
/*     */     
/* 176 */     return isEqual;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 181 */     int result = 23;
/*     */     
/* 183 */     result = 29 * result + ((this.name == null) ? 0 : this.name.hashCode());
/* 184 */     result = 29 * result + this.messageType.hashCode();
/* 185 */     result = 29 * result + this.nameScope.hashCode();
/* 186 */     result = 29 * result + ((this.parent == null) ? 0 : this.parent.hashCode());
/*     */     
/* 188 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 193 */     StringBuilder result = new StringBuilder("BindingSubject[");
/* 194 */     result.append(this.name).append(", ").append(this.messageType);
/* 195 */     result.append(", ").append(this.nameScope).append(", ").append(this.parent);
/* 196 */     return result.append("]").toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/policy/subject/BindingSubject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */