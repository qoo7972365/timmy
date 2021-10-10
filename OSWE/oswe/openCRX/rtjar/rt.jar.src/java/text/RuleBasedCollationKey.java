/*     */ package java.text;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class RuleBasedCollationKey
/*     */   extends CollationKey
/*     */ {
/*     */   private String key;
/*     */   
/*     */   public int compareTo(CollationKey paramCollationKey) {
/*  59 */     int i = this.key.compareTo(((RuleBasedCollationKey)paramCollationKey).key);
/*  60 */     if (i <= -1)
/*  61 */       return -1; 
/*  62 */     if (i >= 1)
/*  63 */       return 1; 
/*  64 */     return 0;
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
/*     */   public boolean equals(Object paramObject) {
/*  76 */     if (this == paramObject) return true; 
/*  77 */     if (paramObject == null || !getClass().equals(paramObject.getClass())) {
/*  78 */       return false;
/*     */     }
/*  80 */     RuleBasedCollationKey ruleBasedCollationKey = (RuleBasedCollationKey)paramObject;
/*  81 */     return this.key.equals(ruleBasedCollationKey.key);
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
/*     */   public int hashCode() {
/*  93 */     return this.key.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] toByteArray() {
/* 104 */     char[] arrayOfChar = this.key.toCharArray();
/* 105 */     byte[] arrayOfByte = new byte[2 * arrayOfChar.length];
/* 106 */     byte b1 = 0;
/* 107 */     for (byte b2 = 0; b2 < arrayOfChar.length; b2++) {
/* 108 */       arrayOfByte[b1++] = (byte)(arrayOfChar[b2] >>> 8);
/* 109 */       arrayOfByte[b1++] = (byte)(arrayOfChar[b2] & 0xFF);
/*     */     } 
/* 111 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   RuleBasedCollationKey(String paramString1, String paramString2) {
/* 118 */     super(paramString1);
/*     */ 
/*     */     
/* 121 */     this.key = null;
/*     */     this.key = paramString2;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/text/RuleBasedCollationKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */