/*     */ package javax.sql.rowset.serial;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.sql.Blob;
/*     */ import java.sql.SQLException;
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
/*     */ public class SerialBlob
/*     */   implements Blob, Serializable, Cloneable
/*     */ {
/*     */   private byte[] buf;
/*     */   private Blob blob;
/*     */   private long len;
/*     */   private long origLen;
/*     */   static final long serialVersionUID = -8144641928112860441L;
/*     */   
/*     */   public SerialBlob(byte[] paramArrayOfbyte) throws SerialException, SQLException {
/* 108 */     this.len = paramArrayOfbyte.length;
/* 109 */     this.buf = new byte[(int)this.len];
/* 110 */     for (byte b = 0; b < this.len; b++) {
/* 111 */       this.buf[b] = paramArrayOfbyte[b];
/*     */     }
/* 113 */     this.origLen = this.len;
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
/*     */   public SerialBlob(Blob paramBlob) throws SerialException, SQLException {
/* 139 */     if (paramBlob == null) {
/* 140 */       throw new SQLException("Cannot instantiate a SerialBlob object with a null Blob object");
/*     */     }
/*     */ 
/*     */     
/* 144 */     this.len = paramBlob.length();
/* 145 */     this.buf = paramBlob.getBytes(1L, (int)this.len);
/* 146 */     this.blob = paramBlob;
/* 147 */     this.origLen = this.len;
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
/*     */   public byte[] getBytes(long paramLong, int paramInt) throws SerialException {
/* 172 */     isValid();
/* 173 */     if (paramInt > this.len) {
/* 174 */       paramInt = (int)this.len;
/*     */     }
/*     */     
/* 177 */     if (paramLong < 1L || this.len - paramLong < 0L) {
/* 178 */       throw new SerialException("Invalid arguments: position cannot be less than 1 or greater than the length of the SerialBlob");
/*     */     }
/*     */ 
/*     */     
/* 182 */     paramLong--;
/*     */     
/* 184 */     byte[] arrayOfByte = new byte[paramInt];
/*     */     
/* 186 */     for (byte b = 0; b < paramInt; b++) {
/* 187 */       arrayOfByte[b] = this.buf[(int)paramLong];
/* 188 */       paramLong++;
/*     */     } 
/* 190 */     return arrayOfByte;
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
/*     */   public long length() throws SerialException {
/* 203 */     isValid();
/* 204 */     return this.len;
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
/*     */   public InputStream getBinaryStream() throws SerialException {
/* 220 */     isValid();
/* 221 */     return new ByteArrayInputStream(this.buf);
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
/*     */   public long position(byte[] paramArrayOfbyte, long paramLong) throws SerialException, SQLException {
/* 249 */     isValid();
/* 250 */     if (paramLong < 1L || paramLong > this.len) {
/* 251 */       return -1L;
/*     */     }
/*     */     
/* 254 */     int i = (int)paramLong - 1;
/* 255 */     byte b = 0;
/* 256 */     long l = paramArrayOfbyte.length;
/*     */     
/* 258 */     while (i < this.len) {
/* 259 */       if (paramArrayOfbyte[b] == this.buf[i]) {
/* 260 */         if ((b + 1) == l) {
/* 261 */           return (i + 1) - l - 1L;
/*     */         }
/* 263 */         b++; i++; continue;
/* 264 */       }  if (paramArrayOfbyte[b] != this.buf[i]) {
/* 265 */         i++;
/*     */       }
/*     */     } 
/* 268 */     return -1L;
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
/*     */   public long position(Blob paramBlob, long paramLong) throws SerialException, SQLException {
/* 294 */     isValid();
/* 295 */     return position(paramBlob.getBytes(1L, (int)paramBlob.length()), paramLong);
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
/*     */   public int setBytes(long paramLong, byte[] paramArrayOfbyte) throws SerialException, SQLException {
/* 320 */     return setBytes(paramLong, paramArrayOfbyte, 0, paramArrayOfbyte.length);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int setBytes(long paramLong, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SerialException, SQLException {
/* 357 */     isValid();
/* 358 */     if (paramInt1 < 0 || paramInt1 > paramArrayOfbyte.length) {
/* 359 */       throw new SerialException("Invalid offset in byte array set");
/*     */     }
/*     */     
/* 362 */     if (paramLong < 1L || paramLong > length()) {
/* 363 */       throw new SerialException("Invalid position in BLOB object set");
/*     */     }
/*     */     
/* 366 */     if (paramInt2 > this.origLen) {
/* 367 */       throw new SerialException("Buffer is not sufficient to hold the value");
/*     */     }
/*     */     
/* 370 */     if (paramInt2 + paramInt1 > paramArrayOfbyte.length) {
/* 371 */       throw new SerialException("Invalid OffSet. Cannot have combined offset and length that is greater that the Blob buffer");
/*     */     }
/*     */ 
/*     */     
/* 375 */     byte b = 0;
/* 376 */     paramLong--;
/* 377 */     while (b < paramInt2 || paramInt1 + b + 1 < paramArrayOfbyte.length - paramInt1) {
/* 378 */       this.buf[(int)paramLong + b] = paramArrayOfbyte[paramInt1 + b];
/* 379 */       b++;
/*     */     } 
/* 381 */     return b;
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
/*     */   public OutputStream setBinaryStream(long paramLong) throws SerialException, SQLException {
/* 407 */     isValid();
/* 408 */     if (this.blob != null) {
/* 409 */       return this.blob.setBinaryStream(paramLong);
/*     */     }
/* 411 */     throw new SerialException("Unsupported operation. SerialBlob cannot return a writable binary stream, unless instantiated with a Blob object that provides a setBinaryStream() implementation");
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
/*     */   public void truncate(long paramLong) throws SerialException {
/* 429 */     isValid();
/* 430 */     if (paramLong > this.len) {
/* 431 */       throw new SerialException("Length more than what can be truncated");
/*     */     }
/* 433 */     if ((int)paramLong == 0) {
/* 434 */       this.buf = new byte[0];
/* 435 */       this.len = paramLong;
/*     */     } else {
/* 437 */       this.len = paramLong;
/* 438 */       this.buf = getBytes(1L, (int)this.len);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream getBinaryStream(long paramLong1, long paramLong2) throws SQLException {
/* 464 */     isValid();
/* 465 */     if (paramLong1 < 1L || paramLong1 > length()) {
/* 466 */       throw new SerialException("Invalid position in BLOB object set");
/*     */     }
/* 468 */     if (paramLong2 < 1L || paramLong2 > this.len - paramLong1 + 1L) {
/* 469 */       throw new SerialException("length is < 1 or pos + length > total number of bytes");
/*     */     }
/*     */     
/* 472 */     return new ByteArrayInputStream(this.buf, (int)paramLong1 - 1, (int)paramLong2);
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
/*     */   public void free() throws SQLException {
/* 486 */     if (this.buf != null) {
/* 487 */       this.buf = null;
/* 488 */       if (this.blob != null) {
/* 489 */         this.blob.free();
/*     */       }
/* 491 */       this.blob = null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 508 */     if (this == paramObject) {
/* 509 */       return true;
/*     */     }
/* 511 */     if (paramObject instanceof SerialBlob) {
/* 512 */       SerialBlob serialBlob = (SerialBlob)paramObject;
/* 513 */       if (this.len == serialBlob.len) {
/* 514 */         return Arrays.equals(this.buf, serialBlob.buf);
/*     */       }
/*     */     } 
/* 517 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 525 */     return ((31 + Arrays.hashCode(this.buf)) * 31 + (int)this.len) * 31 + (int)this.origLen;
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
/*     */   public Object clone() {
/*     */     try {
/* 538 */       SerialBlob serialBlob = (SerialBlob)super.clone();
/* 539 */       serialBlob.buf = (this.buf != null) ? Arrays.copyOf(this.buf, (int)this.len) : null;
/* 540 */       serialBlob.blob = null;
/* 541 */       return serialBlob;
/* 542 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*     */       
/* 544 */       throw new InternalError();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 555 */     ObjectInputStream.GetField getField = paramObjectInputStream.readFields();
/* 556 */     byte[] arrayOfByte = (byte[])getField.get("buf", (Object)null);
/* 557 */     if (arrayOfByte == null)
/* 558 */       throw new InvalidObjectException("buf is null and should not be!"); 
/* 559 */     this.buf = (byte[])arrayOfByte.clone();
/* 560 */     this.len = getField.get("len", 0L);
/* 561 */     if (this.buf.length != this.len)
/* 562 */       throw new InvalidObjectException("buf is not the expected size"); 
/* 563 */     this.origLen = getField.get("origLen", 0L);
/* 564 */     this.blob = (Blob)getField.get("blob", (Object)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException, ClassNotFoundException {
/* 574 */     ObjectOutputStream.PutField putField = paramObjectOutputStream.putFields();
/* 575 */     putField.put("buf", this.buf);
/* 576 */     putField.put("len", this.len);
/* 577 */     putField.put("origLen", this.origLen);
/*     */ 
/*     */     
/* 580 */     putField.put("blob", (this.blob instanceof Serializable) ? this.blob : null);
/* 581 */     paramObjectOutputStream.writeFields();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void isValid() throws SerialException {
/* 591 */     if (this.buf == null)
/* 592 */       throw new SerialException("Error: You cannot call a method on a SerialBlob instance once free() has been called."); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/sql/rowset/serial/SerialBlob.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */