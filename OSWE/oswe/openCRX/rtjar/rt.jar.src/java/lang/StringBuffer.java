/*     */ package java.lang;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.ObjectStreamField;
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class StringBuffer
/*     */   extends AbstractStringBuilder
/*     */   implements Serializable, CharSequence
/*     */ {
/*     */   private transient char[] toStringCache;
/*     */   static final long serialVersionUID = 3388685877147921107L;
/*     */   
/*     */   public StringBuffer() {
/* 116 */     super(16);
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
/*     */   public StringBuffer(int paramInt) {
/* 128 */     super(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringBuffer(String paramString) {
/* 139 */     super(paramString.length() + 16);
/* 140 */     append(paramString);
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
/*     */   public StringBuffer(CharSequence paramCharSequence) {
/* 157 */     this(paramCharSequence.length() + 16);
/* 158 */     append(paramCharSequence);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized int length() {
/* 163 */     return this.count;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized int capacity() {
/* 168 */     return this.value.length;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void ensureCapacity(int paramInt) {
/* 174 */     super.ensureCapacity(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void trimToSize() {
/* 182 */     super.trimToSize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setLength(int paramInt) {
/* 191 */     this.toStringCache = null;
/* 192 */     super.setLength(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized char charAt(int paramInt) {
/* 201 */     if (paramInt < 0 || paramInt >= this.count)
/* 202 */       throw new StringIndexOutOfBoundsException(paramInt); 
/* 203 */     return this.value[paramInt];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized int codePointAt(int paramInt) {
/* 211 */     return super.codePointAt(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized int codePointBefore(int paramInt) {
/* 219 */     return super.codePointBefore(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized int codePointCount(int paramInt1, int paramInt2) {
/* 227 */     return super.codePointCount(paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized int offsetByCodePoints(int paramInt1, int paramInt2) {
/* 235 */     return super.offsetByCodePoints(paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void getChars(int paramInt1, int paramInt2, char[] paramArrayOfchar, int paramInt3) {
/* 245 */     super.getChars(paramInt1, paramInt2, paramArrayOfchar, paramInt3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setCharAt(int paramInt, char paramChar) {
/* 254 */     if (paramInt < 0 || paramInt >= this.count)
/* 255 */       throw new StringIndexOutOfBoundsException(paramInt); 
/* 256 */     this.toStringCache = null;
/* 257 */     this.value[paramInt] = paramChar;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized StringBuffer append(Object paramObject) {
/* 262 */     this.toStringCache = null;
/* 263 */     super.append(String.valueOf(paramObject));
/* 264 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized StringBuffer append(String paramString) {
/* 269 */     this.toStringCache = null;
/* 270 */     super.append(paramString);
/* 271 */     return this;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized StringBuffer append(StringBuffer paramStringBuffer) {
/* 299 */     this.toStringCache = null;
/* 300 */     super.append(paramStringBuffer);
/* 301 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized StringBuffer append(AbstractStringBuilder paramAbstractStringBuilder) {
/* 309 */     this.toStringCache = null;
/* 310 */     super.append(paramAbstractStringBuilder);
/* 311 */     return this;
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
/*     */   
/*     */   public synchronized StringBuffer append(CharSequence paramCharSequence) {
/* 337 */     this.toStringCache = null;
/* 338 */     super.append(paramCharSequence);
/* 339 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized StringBuffer append(CharSequence paramCharSequence, int paramInt1, int paramInt2) {
/* 349 */     this.toStringCache = null;
/* 350 */     super.append(paramCharSequence, paramInt1, paramInt2);
/* 351 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized StringBuffer append(char[] paramArrayOfchar) {
/* 356 */     this.toStringCache = null;
/* 357 */     super.append(paramArrayOfchar);
/* 358 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized StringBuffer append(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/* 366 */     this.toStringCache = null;
/* 367 */     super.append(paramArrayOfchar, paramInt1, paramInt2);
/* 368 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized StringBuffer append(boolean paramBoolean) {
/* 373 */     this.toStringCache = null;
/* 374 */     super.append(paramBoolean);
/* 375 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized StringBuffer append(char paramChar) {
/* 380 */     this.toStringCache = null;
/* 381 */     super.append(paramChar);
/* 382 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized StringBuffer append(int paramInt) {
/* 387 */     this.toStringCache = null;
/* 388 */     super.append(paramInt);
/* 389 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized StringBuffer appendCodePoint(int paramInt) {
/* 397 */     this.toStringCache = null;
/* 398 */     super.appendCodePoint(paramInt);
/* 399 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized StringBuffer append(long paramLong) {
/* 404 */     this.toStringCache = null;
/* 405 */     super.append(paramLong);
/* 406 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized StringBuffer append(float paramFloat) {
/* 411 */     this.toStringCache = null;
/* 412 */     super.append(paramFloat);
/* 413 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized StringBuffer append(double paramDouble) {
/* 418 */     this.toStringCache = null;
/* 419 */     super.append(paramDouble);
/* 420 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized StringBuffer delete(int paramInt1, int paramInt2) {
/* 429 */     this.toStringCache = null;
/* 430 */     super.delete(paramInt1, paramInt2);
/* 431 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized StringBuffer deleteCharAt(int paramInt) {
/* 440 */     this.toStringCache = null;
/* 441 */     super.deleteCharAt(paramInt);
/* 442 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized StringBuffer replace(int paramInt1, int paramInt2, String paramString) {
/* 451 */     this.toStringCache = null;
/* 452 */     super.replace(paramInt1, paramInt2, paramString);
/* 453 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized String substring(int paramInt) {
/* 462 */     return substring(paramInt, this.count);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized CharSequence subSequence(int paramInt1, int paramInt2) {
/* 471 */     return super.substring(paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized String substring(int paramInt1, int paramInt2) {
/* 480 */     return super.substring(paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized StringBuffer insert(int paramInt1, char[] paramArrayOfchar, int paramInt2, int paramInt3) {
/* 491 */     this.toStringCache = null;
/* 492 */     super.insert(paramInt1, paramArrayOfchar, paramInt2, paramInt3);
/* 493 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized StringBuffer insert(int paramInt, Object paramObject) {
/* 501 */     this.toStringCache = null;
/* 502 */     super.insert(paramInt, String.valueOf(paramObject));
/* 503 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized StringBuffer insert(int paramInt, String paramString) {
/* 511 */     this.toStringCache = null;
/* 512 */     super.insert(paramInt, paramString);
/* 513 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized StringBuffer insert(int paramInt, char[] paramArrayOfchar) {
/* 521 */     this.toStringCache = null;
/* 522 */     super.insert(paramInt, paramArrayOfchar);
/* 523 */     return this;
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
/*     */   public StringBuffer insert(int paramInt, CharSequence paramCharSequence) {
/* 535 */     super.insert(paramInt, paramCharSequence);
/* 536 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized StringBuffer insert(int paramInt1, CharSequence paramCharSequence, int paramInt2, int paramInt3) {
/* 547 */     this.toStringCache = null;
/* 548 */     super.insert(paramInt1, paramCharSequence, paramInt2, paramInt3);
/* 549 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringBuffer insert(int paramInt, boolean paramBoolean) {
/* 560 */     super.insert(paramInt, paramBoolean);
/* 561 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized StringBuffer insert(int paramInt, char paramChar) {
/* 569 */     this.toStringCache = null;
/* 570 */     super.insert(paramInt, paramChar);
/* 571 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringBuffer insert(int paramInt1, int paramInt2) {
/* 582 */     super.insert(paramInt1, paramInt2);
/* 583 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringBuffer insert(int paramInt, long paramLong) {
/* 594 */     super.insert(paramInt, paramLong);
/* 595 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringBuffer insert(int paramInt, float paramFloat) {
/* 606 */     super.insert(paramInt, paramFloat);
/* 607 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringBuffer insert(int paramInt, double paramDouble) {
/* 618 */     super.insert(paramInt, paramDouble);
/* 619 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int indexOf(String paramString) {
/* 628 */     return super.indexOf(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized int indexOf(String paramString, int paramInt) {
/* 636 */     return super.indexOf(paramString, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int lastIndexOf(String paramString) {
/* 645 */     return lastIndexOf(paramString, this.count);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized int lastIndexOf(String paramString, int paramInt) {
/* 653 */     return super.lastIndexOf(paramString, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized StringBuffer reverse() {
/* 661 */     this.toStringCache = null;
/* 662 */     super.reverse();
/* 663 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized String toString() {
/* 668 */     if (this.toStringCache == null) {
/* 669 */       this.toStringCache = Arrays.copyOfRange(this.value, 0, this.count);
/*     */     }
/* 671 */     return new String(this.toStringCache, true);
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
/* 685 */   private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("value", char[].class), new ObjectStreamField("count", int.class), new ObjectStreamField("shared", boolean.class) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 698 */     ObjectOutputStream.PutField putField = paramObjectOutputStream.putFields();
/* 699 */     putField.put("value", this.value);
/* 700 */     putField.put("count", this.count);
/* 701 */     putField.put("shared", false);
/* 702 */     paramObjectOutputStream.writeFields();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 711 */     ObjectInputStream.GetField getField = paramObjectInputStream.readFields();
/* 712 */     this.value = (char[])getField.get("value", (Object)null);
/* 713 */     this.count = getField.get("count", 0);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/StringBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */