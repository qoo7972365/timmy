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
/*     */ class InQueryExp
/*     */   extends QueryEval
/*     */   implements QueryExp
/*     */ {
/*     */   private static final long serialVersionUID = -5801329450358952434L;
/*     */   private ValueExp val;
/*     */   private ValueExp[] valueList;
/*     */   
/*     */   public InQueryExp() {}
/*     */   
/*     */   public InQueryExp(ValueExp paramValueExp, ValueExp[] paramArrayOfValueExp) {
/*  63 */     this.val = paramValueExp;
/*  64 */     this.valueList = paramArrayOfValueExp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ValueExp getCheckedValue() {
/*  72 */     return this.val;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ValueExp[] getExplicitValues() {
/*  79 */     return this.valueList;
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
/*     */   public boolean apply(ObjectName paramObjectName) throws BadStringOperationException, BadBinaryOpValueExpException, BadAttributeValueExpException, InvalidApplicationException {
/*  97 */     if (this.valueList != null) {
/*  98 */       ValueExp valueExp = this.val.apply(paramObjectName);
/*  99 */       boolean bool = valueExp instanceof NumericValueExp;
/*     */       
/* 101 */       for (ValueExp valueExp1 : this.valueList) {
/* 102 */         valueExp1 = valueExp1.apply(paramObjectName);
/* 103 */         if (bool) {
/* 104 */           if (((NumericValueExp)valueExp1).doubleValue() == ((NumericValueExp)valueExp)
/* 105 */             .doubleValue()) {
/* 106 */             return true;
/*     */           }
/*     */         }
/* 109 */         else if (((StringValueExp)valueExp1).getValue().equals(((StringValueExp)valueExp)
/* 110 */             .getValue())) {
/* 111 */           return true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 116 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 123 */     return this.val + " in (" + generateValueList() + ")";
/*     */   }
/*     */ 
/*     */   
/*     */   private String generateValueList() {
/* 128 */     if (this.valueList == null || this.valueList.length == 0) {
/* 129 */       return "";
/*     */     }
/*     */ 
/*     */     
/* 133 */     StringBuilder stringBuilder = new StringBuilder(this.valueList[0].toString());
/*     */     
/* 135 */     for (byte b = 1; b < this.valueList.length; b++) {
/* 136 */       stringBuilder.append(", ");
/* 137 */       stringBuilder.append(this.valueList[b]);
/*     */     } 
/*     */     
/* 140 */     return stringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/InQueryExp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */