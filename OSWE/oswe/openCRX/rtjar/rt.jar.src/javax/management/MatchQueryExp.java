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
/*     */ class MatchQueryExp
/*     */   extends QueryEval
/*     */   implements QueryExp
/*     */ {
/*     */   private static final long serialVersionUID = -7156603696948215014L;
/*     */   private AttributeValueExp exp;
/*     */   private String pattern;
/*     */   
/*     */   public MatchQueryExp() {}
/*     */   
/*     */   public MatchQueryExp(AttributeValueExp paramAttributeValueExp, StringValueExp paramStringValueExp) {
/*  64 */     this.exp = paramAttributeValueExp;
/*  65 */     this.pattern = paramStringValueExp.getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AttributeValueExp getAttribute() {
/*  73 */     return this.exp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPattern() {
/*  80 */     return this.pattern;
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
/* 101 */     ValueExp valueExp = this.exp.apply(paramObjectName);
/* 102 */     if (!(valueExp instanceof StringValueExp)) {
/* 103 */       return false;
/*     */     }
/* 105 */     return wildmatch(((StringValueExp)valueExp).getValue(), this.pattern);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 112 */     return this.exp + " like " + new StringValueExp(this.pattern);
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
/*     */   private static boolean wildmatch(String paramString1, String paramString2) {
/* 124 */     byte b1 = 0, b2 = 0;
/* 125 */     int i = paramString1.length();
/* 126 */     int j = paramString2.length();
/*     */     
/* 128 */     while (b2 < j) {
/* 129 */       char c = paramString2.charAt(b2++);
/* 130 */       if (c == '?') {
/* 131 */         if (++b1 > i)
/* 132 */           return false;  continue;
/* 133 */       }  if (c == '[') {
/* 134 */         if (b1 >= i)
/* 135 */           return false; 
/* 136 */         boolean bool1 = true;
/* 137 */         boolean bool2 = false;
/* 138 */         if (paramString2.charAt(b2) == '!') {
/* 139 */           bool1 = false;
/* 140 */           b2++;
/*     */         } 
/* 142 */         while ((c = paramString2.charAt(b2)) != ']' && ++b2 < j) {
/* 143 */           if (paramString2.charAt(b2) == '-' && b2 + 1 < j && paramString2
/*     */             
/* 145 */             .charAt(b2 + 1) != ']') {
/* 146 */             if (paramString1.charAt(b1) >= paramString2.charAt(b2 - 1) && paramString1
/* 147 */               .charAt(b1) <= paramString2.charAt(b2 + 1)) {
/* 148 */               bool2 = true;
/*     */             }
/* 150 */             b2++; continue;
/*     */           } 
/* 152 */           if (c == paramString1.charAt(b1)) {
/* 153 */             bool2 = true;
/*     */           }
/*     */         } 
/*     */         
/* 157 */         if (b2 >= j || bool1 != bool2) {
/* 158 */           return false;
/*     */         }
/* 160 */         b2++;
/* 161 */         b1++; continue;
/* 162 */       }  if (c == '*') {
/* 163 */         if (b2 >= j)
/* 164 */           return true; 
/*     */         while (true)
/* 166 */         { if (wildmatch(paramString1.substring(b1), paramString2.substring(b2)))
/* 167 */             return true; 
/* 168 */           if (++b1 >= i)
/* 169 */             return false;  } 
/* 170 */       }  if (c == '\\') {
/* 171 */         if (b2 >= j || b1 >= i || paramString2
/* 172 */           .charAt(b2++) != paramString1.charAt(b1++))
/* 173 */           return false;  continue;
/*     */       } 
/* 175 */       if (b1 >= i || c != paramString1.charAt(b1++)) {
/* 176 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 180 */     return (b1 == i);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/MatchQueryExp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */