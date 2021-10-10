/*     */ package javax.management;
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
/*     */ public class AttributeValueExp
/*     */   implements ValueExp
/*     */ {
/*     */   private static final long serialVersionUID = -7768025046539163385L;
/*     */   private String attr;
/*     */   
/*     */   @Deprecated
/*     */   public AttributeValueExp() {}
/*     */   
/*     */   public AttributeValueExp(String paramString) {
/*  71 */     this.attr = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAttributeName() {
/*  80 */     return this.attr;
/*     */   }
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
/*     */   public ValueExp apply(ObjectName paramObjectName) throws BadStringOperationException, BadBinaryOpValueExpException, BadAttributeValueExpException, InvalidApplicationException {
/* 105 */     Object object = getAttribute(paramObjectName);
/*     */     
/* 107 */     if (object instanceof Number)
/* 108 */       return new NumericValueExp((Number)object); 
/* 109 */     if (object instanceof String)
/* 110 */       return new StringValueExp((String)object); 
/* 111 */     if (object instanceof Boolean) {
/* 112 */       return new BooleanValueExp((Boolean)object);
/*     */     }
/* 114 */     throw new BadAttributeValueExpException(object);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 123 */     return this.attr;
/*     */   }
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
/*     */   @Deprecated
/*     */   public void setMBeanServer(MBeanServer paramMBeanServer) {}
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
/*     */   protected Object getAttribute(ObjectName paramObjectName) {
/*     */     try {
/* 161 */       MBeanServer mBeanServer = QueryEval.getMBeanServer();
/*     */       
/* 163 */       return mBeanServer.getAttribute(paramObjectName, this.attr);
/* 164 */     } catch (Exception exception) {
/* 165 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/AttributeValueExp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */