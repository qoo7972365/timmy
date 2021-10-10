/*     */ package java.util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class StringJoiner
/*     */ {
/*     */   private final String prefix;
/*     */   private final String delimiter;
/*     */   private final String suffix;
/*     */   private StringBuilder value;
/*     */   private String emptyValue;
/*     */   
/*     */   public StringJoiner(CharSequence paramCharSequence) {
/* 100 */     this(paramCharSequence, "", "");
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
/*     */   public StringJoiner(CharSequence paramCharSequence1, CharSequence paramCharSequence2, CharSequence paramCharSequence3) {
/* 121 */     Objects.requireNonNull(paramCharSequence2, "The prefix must not be null");
/* 122 */     Objects.requireNonNull(paramCharSequence1, "The delimiter must not be null");
/* 123 */     Objects.requireNonNull(paramCharSequence3, "The suffix must not be null");
/*     */     
/* 125 */     this.prefix = paramCharSequence2.toString();
/* 126 */     this.delimiter = paramCharSequence1.toString();
/* 127 */     this.suffix = paramCharSequence3.toString();
/* 128 */     this.emptyValue = this.prefix + this.suffix;
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
/*     */   public StringJoiner setEmptyValue(CharSequence paramCharSequence) {
/* 146 */     this
/* 147 */       .emptyValue = ((CharSequence)Objects.<CharSequence>requireNonNull(paramCharSequence, "The empty value must not be null")).toString();
/* 148 */     return this;
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
/*     */   public String toString() {
/* 161 */     if (this.value == null) {
/* 162 */       return this.emptyValue;
/*     */     }
/* 164 */     if (this.suffix.equals("")) {
/* 165 */       return this.value.toString();
/*     */     }
/* 167 */     int i = this.value.length();
/* 168 */     String str = this.value.append(this.suffix).toString();
/*     */     
/* 170 */     this.value.setLength(i);
/* 171 */     return str;
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
/*     */   public StringJoiner add(CharSequence paramCharSequence) {
/* 185 */     prepareBuilder().append(paramCharSequence);
/* 186 */     return this;
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
/*     */   public StringJoiner merge(StringJoiner paramStringJoiner) {
/* 209 */     Objects.requireNonNull(paramStringJoiner);
/* 210 */     if (paramStringJoiner.value != null) {
/* 211 */       int i = paramStringJoiner.value.length();
/*     */ 
/*     */ 
/*     */       
/* 215 */       StringBuilder stringBuilder = prepareBuilder();
/* 216 */       stringBuilder.append(paramStringJoiner.value, paramStringJoiner.prefix.length(), i);
/*     */     } 
/* 218 */     return this;
/*     */   }
/*     */   
/*     */   private StringBuilder prepareBuilder() {
/* 222 */     if (this.value != null) {
/* 223 */       this.value.append(this.delimiter);
/*     */     } else {
/* 225 */       this.value = (new StringBuilder()).append(this.prefix);
/*     */     } 
/* 227 */     return this.value;
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
/*     */   public int length() {
/* 244 */     return (this.value != null) ? (this.value.length() + this.suffix.length()) : this.emptyValue
/* 245 */       .length();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/StringJoiner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */