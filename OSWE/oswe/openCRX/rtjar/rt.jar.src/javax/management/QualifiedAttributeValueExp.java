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
/*     */ class QualifiedAttributeValueExp
/*     */   extends AttributeValueExp
/*     */ {
/*     */   private static final long serialVersionUID = 8832517277410933254L;
/*     */   private String className;
/*     */   
/*     */   @Deprecated
/*     */   public QualifiedAttributeValueExp() {}
/*     */   
/*     */   public QualifiedAttributeValueExp(String paramString1, String paramString2) {
/*  64 */     super(paramString2);
/*  65 */     this.className = paramString1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAttrClassName() {
/*  73 */     return this.className;
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
/*     */   public ValueExp apply(ObjectName paramObjectName) throws BadStringOperationException, BadBinaryOpValueExpException, BadAttributeValueExpException, InvalidApplicationException {
/*     */     try {
/*  92 */       MBeanServer mBeanServer = QueryEval.getMBeanServer();
/*  93 */       String str = mBeanServer.getObjectInstance(paramObjectName).getClassName();
/*     */       
/*  95 */       if (str.equals(this.className)) {
/*  96 */         return super.apply(paramObjectName);
/*     */       }
/*  98 */       throw new InvalidApplicationException("Class name is " + str + ", should be " + this.className);
/*     */     
/*     */     }
/* 101 */     catch (Exception exception) {
/* 102 */       throw new InvalidApplicationException("Qualified attribute: " + exception);
/*     */     } 
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
/*     */   public String toString() {
/* 115 */     if (this.className != null) {
/* 116 */       return this.className + "." + super.toString();
/*     */     }
/* 118 */     return super.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/QualifiedAttributeValueExp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */