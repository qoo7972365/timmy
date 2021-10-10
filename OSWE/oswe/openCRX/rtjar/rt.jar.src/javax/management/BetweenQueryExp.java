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
/*     */ class BetweenQueryExp
/*     */   extends QueryEval
/*     */   implements QueryExp
/*     */ {
/*     */   private static final long serialVersionUID = -2933597532866307444L;
/*     */   private ValueExp exp1;
/*     */   private ValueExp exp2;
/*     */   private ValueExp exp3;
/*     */   
/*     */   public BetweenQueryExp() {}
/*     */   
/*     */   public BetweenQueryExp(ValueExp paramValueExp1, ValueExp paramValueExp2, ValueExp paramValueExp3) {
/*  68 */     this.exp1 = paramValueExp1;
/*  69 */     this.exp2 = paramValueExp2;
/*  70 */     this.exp3 = paramValueExp3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ValueExp getCheckedValue() {
/*  78 */     return this.exp1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ValueExp getLowerBound() {
/*  85 */     return this.exp2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ValueExp getUpperBound() {
/*  92 */     return this.exp3;
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
/*     */   public boolean apply(ObjectName paramObjectName) throws BadStringOperationException, BadBinaryOpValueExpException, BadAttributeValueExpException, InvalidApplicationException {
/* 109 */     ValueExp valueExp1 = this.exp1.apply(paramObjectName);
/* 110 */     ValueExp valueExp2 = this.exp2.apply(paramObjectName);
/* 111 */     ValueExp valueExp3 = this.exp3.apply(paramObjectName);
/* 112 */     boolean bool = valueExp1 instanceof NumericValueExp;
/*     */     
/* 114 */     if (bool) {
/* 115 */       if (((NumericValueExp)valueExp1).isLong()) {
/* 116 */         long l1 = ((NumericValueExp)valueExp1).longValue();
/* 117 */         long l2 = ((NumericValueExp)valueExp2).longValue();
/* 118 */         long l3 = ((NumericValueExp)valueExp3).longValue();
/* 119 */         return (l2 <= l1 && l1 <= l3);
/*     */       } 
/* 121 */       double d1 = ((NumericValueExp)valueExp1).doubleValue();
/* 122 */       double d2 = ((NumericValueExp)valueExp2).doubleValue();
/* 123 */       double d3 = ((NumericValueExp)valueExp3).doubleValue();
/* 124 */       return (d2 <= d1 && d1 <= d3);
/*     */     } 
/*     */ 
/*     */     
/* 128 */     String str1 = ((StringValueExp)valueExp1).getValue();
/* 129 */     String str2 = ((StringValueExp)valueExp2).getValue();
/* 130 */     String str3 = ((StringValueExp)valueExp3).getValue();
/* 131 */     return (str2.compareTo(str1) <= 0 && str1.compareTo(str3) <= 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 140 */     return "(" + this.exp1 + ") between (" + this.exp2 + ") and (" + this.exp3 + ")";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/BetweenQueryExp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */