/*     */ package java.lang;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class StringBuilder
/*     */   extends AbstractStringBuilder
/*     */   implements Serializable, CharSequence
/*     */ {
/*     */   static final long serialVersionUID = 4383685877147921099L;
/*     */   
/*     */   public StringBuilder() {
/*  89 */     super(16);
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
/*     */   public StringBuilder(int paramInt) {
/* 101 */     super(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringBuilder(String paramString) {
/* 112 */     super(paramString.length() + 16);
/* 113 */     append(paramString);
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
/*     */   public StringBuilder(CharSequence paramCharSequence) {
/* 125 */     this(paramCharSequence.length() + 16);
/* 126 */     append(paramCharSequence);
/*     */   }
/*     */ 
/*     */   
/*     */   public StringBuilder append(Object paramObject) {
/* 131 */     return append(String.valueOf(paramObject));
/*     */   }
/*     */ 
/*     */   
/*     */   public StringBuilder append(String paramString) {
/* 136 */     super.append(paramString);
/* 137 */     return this;
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
/*     */   public StringBuilder append(StringBuffer paramStringBuffer) {
/* 160 */     super.append(paramStringBuffer);
/* 161 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public StringBuilder append(CharSequence paramCharSequence) {
/* 166 */     super.append(paramCharSequence);
/* 167 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringBuilder append(CharSequence paramCharSequence, int paramInt1, int paramInt2) {
/* 175 */     super.append(paramCharSequence, paramInt1, paramInt2);
/* 176 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public StringBuilder append(char[] paramArrayOfchar) {
/* 181 */     super.append(paramArrayOfchar);
/* 182 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringBuilder append(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/* 190 */     super.append(paramArrayOfchar, paramInt1, paramInt2);
/* 191 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public StringBuilder append(boolean paramBoolean) {
/* 196 */     super.append(paramBoolean);
/* 197 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public StringBuilder append(char paramChar) {
/* 202 */     super.append(paramChar);
/* 203 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public StringBuilder append(int paramInt) {
/* 208 */     super.append(paramInt);
/* 209 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public StringBuilder append(long paramLong) {
/* 214 */     super.append(paramLong);
/* 215 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public StringBuilder append(float paramFloat) {
/* 220 */     super.append(paramFloat);
/* 221 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public StringBuilder append(double paramDouble) {
/* 226 */     super.append(paramDouble);
/* 227 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringBuilder appendCodePoint(int paramInt) {
/* 235 */     super.appendCodePoint(paramInt);
/* 236 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringBuilder delete(int paramInt1, int paramInt2) {
/* 244 */     super.delete(paramInt1, paramInt2);
/* 245 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringBuilder deleteCharAt(int paramInt) {
/* 253 */     super.deleteCharAt(paramInt);
/* 254 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringBuilder replace(int paramInt1, int paramInt2, String paramString) {
/* 262 */     super.replace(paramInt1, paramInt2, paramString);
/* 263 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringBuilder insert(int paramInt1, char[] paramArrayOfchar, int paramInt2, int paramInt3) {
/* 273 */     super.insert(paramInt1, paramArrayOfchar, paramInt2, paramInt3);
/* 274 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringBuilder insert(int paramInt, Object paramObject) {
/* 282 */     super.insert(paramInt, paramObject);
/* 283 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringBuilder insert(int paramInt, String paramString) {
/* 291 */     super.insert(paramInt, paramString);
/* 292 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringBuilder insert(int paramInt, char[] paramArrayOfchar) {
/* 300 */     super.insert(paramInt, paramArrayOfchar);
/* 301 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringBuilder insert(int paramInt, CharSequence paramCharSequence) {
/* 309 */     super.insert(paramInt, paramCharSequence);
/* 310 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringBuilder insert(int paramInt1, CharSequence paramCharSequence, int paramInt2, int paramInt3) {
/* 320 */     super.insert(paramInt1, paramCharSequence, paramInt2, paramInt3);
/* 321 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringBuilder insert(int paramInt, boolean paramBoolean) {
/* 329 */     super.insert(paramInt, paramBoolean);
/* 330 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringBuilder insert(int paramInt, char paramChar) {
/* 338 */     super.insert(paramInt, paramChar);
/* 339 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringBuilder insert(int paramInt1, int paramInt2) {
/* 347 */     super.insert(paramInt1, paramInt2);
/* 348 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringBuilder insert(int paramInt, long paramLong) {
/* 356 */     super.insert(paramInt, paramLong);
/* 357 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringBuilder insert(int paramInt, float paramFloat) {
/* 365 */     super.insert(paramInt, paramFloat);
/* 366 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringBuilder insert(int paramInt, double paramDouble) {
/* 374 */     super.insert(paramInt, paramDouble);
/* 375 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int indexOf(String paramString) {
/* 380 */     return super.indexOf(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public int indexOf(String paramString, int paramInt) {
/* 385 */     return super.indexOf(paramString, paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public int lastIndexOf(String paramString) {
/* 390 */     return super.lastIndexOf(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public int lastIndexOf(String paramString, int paramInt) {
/* 395 */     return super.lastIndexOf(paramString, paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public StringBuilder reverse() {
/* 400 */     super.reverse();
/* 401 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 407 */     return new String(this.value, 0, this.count);
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
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 423 */     paramObjectOutputStream.defaultWriteObject();
/* 424 */     paramObjectOutputStream.writeInt(this.count);
/* 425 */     paramObjectOutputStream.writeObject(this.value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 434 */     paramObjectInputStream.defaultReadObject();
/* 435 */     this.count = paramObjectInputStream.readInt();
/* 436 */     this.value = (char[])paramObjectInputStream.readObject();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/StringBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */