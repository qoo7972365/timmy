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
/*     */ class InstanceOfQueryExp
/*     */   extends QueryEval
/*     */   implements QueryExp
/*     */ {
/*     */   private static final long serialVersionUID = -1081892073854801359L;
/*     */   private StringValueExp classNameValue;
/*     */   
/*     */   public InstanceOfQueryExp(StringValueExp paramStringValueExp) {
/*  59 */     if (paramStringValueExp == null) {
/*  60 */       throw new IllegalArgumentException("Null class name.");
/*     */     }
/*     */     
/*  63 */     this.classNameValue = paramStringValueExp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringValueExp getClassNameValue() {
/*  72 */     return this.classNameValue;
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
/*     */   public boolean apply(ObjectName paramObjectName) throws BadStringOperationException, BadBinaryOpValueExpException, BadAttributeValueExpException, InvalidApplicationException {
/*     */     StringValueExp stringValueExp;
/*     */     try {
/*  95 */       stringValueExp = (StringValueExp)this.classNameValue.apply(paramObjectName);
/*  96 */     } catch (ClassCastException classCastException) {
/*     */ 
/*     */ 
/*     */       
/* 100 */       BadStringOperationException badStringOperationException = new BadStringOperationException(classCastException.toString());
/* 101 */       badStringOperationException.initCause(classCastException);
/* 102 */       throw badStringOperationException;
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 107 */       return getMBeanServer().isInstanceOf(paramObjectName, stringValueExp.getValue());
/* 108 */     } catch (InstanceNotFoundException instanceNotFoundException) {
/* 109 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 118 */     return "InstanceOf " + this.classNameValue.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/InstanceOfQueryExp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */