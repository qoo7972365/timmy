/*     */ package java.time.zone;
/*     */ 
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.Externalizable;
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidClassException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
/*     */ import java.io.StreamCorruptedException;
/*     */ import java.time.ZoneOffset;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class Ser
/*     */   implements Externalizable
/*     */ {
/*     */   private static final long serialVersionUID = -8885321777449118786L;
/*     */   static final byte ZRULES = 1;
/*     */   static final byte ZOT = 2;
/*     */   static final byte ZOTRULE = 3;
/*     */   private byte type;
/*     */   private Object object;
/*     */   
/*     */   public Ser() {}
/*     */   
/*     */   Ser(byte paramByte, Object paramObject) {
/* 115 */     this.type = paramByte;
/* 116 */     this.object = paramObject;
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
/*     */   public void writeExternal(ObjectOutput paramObjectOutput) throws IOException {
/* 137 */     writeInternal(this.type, this.object, paramObjectOutput);
/*     */   }
/*     */   
/*     */   static void write(Object paramObject, DataOutput paramDataOutput) throws IOException {
/* 141 */     writeInternal((byte)1, paramObject, paramDataOutput);
/*     */   }
/*     */   
/*     */   private static void writeInternal(byte paramByte, Object paramObject, DataOutput paramDataOutput) throws IOException {
/* 145 */     paramDataOutput.writeByte(paramByte);
/* 146 */     switch (paramByte) {
/*     */       case 1:
/* 148 */         ((ZoneRules)paramObject).writeExternal(paramDataOutput);
/*     */         return;
/*     */       case 2:
/* 151 */         ((ZoneOffsetTransition)paramObject).writeExternal(paramDataOutput);
/*     */         return;
/*     */       case 3:
/* 154 */         ((ZoneOffsetTransitionRule)paramObject).writeExternal(paramDataOutput);
/*     */         return;
/*     */     } 
/* 157 */     throw new InvalidClassException("Unknown serialized type");
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
/*     */   public void readExternal(ObjectInput paramObjectInput) throws IOException, ClassNotFoundException {
/* 182 */     this.type = paramObjectInput.readByte();
/* 183 */     this.object = readInternal(this.type, paramObjectInput);
/*     */   }
/*     */   
/*     */   static Object read(DataInput paramDataInput) throws IOException, ClassNotFoundException {
/* 187 */     byte b = paramDataInput.readByte();
/* 188 */     return readInternal(b, paramDataInput);
/*     */   }
/*     */   
/*     */   private static Object readInternal(byte paramByte, DataInput paramDataInput) throws IOException, ClassNotFoundException {
/* 192 */     switch (paramByte) {
/*     */       case 1:
/* 194 */         return ZoneRules.readExternal(paramDataInput);
/*     */       case 2:
/* 196 */         return ZoneOffsetTransition.readExternal(paramDataInput);
/*     */       case 3:
/* 198 */         return ZoneOffsetTransitionRule.readExternal(paramDataInput);
/*     */     } 
/* 200 */     throw new StreamCorruptedException("Unknown serialized type");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object readResolve() {
/* 210 */     return this.object;
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
/*     */   static void writeOffset(ZoneOffset paramZoneOffset, DataOutput paramDataOutput) throws IOException {
/* 222 */     int i = paramZoneOffset.getTotalSeconds();
/* 223 */     boolean bool = (i % 900 == 0) ? (i / 900) : true;
/* 224 */     paramDataOutput.writeByte(bool);
/* 225 */     if (bool == 127) {
/* 226 */       paramDataOutput.writeInt(i);
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
/*     */   static ZoneOffset readOffset(DataInput paramDataInput) throws IOException {
/* 238 */     byte b = paramDataInput.readByte();
/* 239 */     return (b == Byte.MAX_VALUE) ? ZoneOffset.ofTotalSeconds(paramDataInput.readInt()) : ZoneOffset.ofTotalSeconds(b * 900);
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
/*     */   static void writeEpochSec(long paramLong, DataOutput paramDataOutput) throws IOException {
/* 251 */     if (paramLong >= -4575744000L && paramLong < 10413792000L && paramLong % 900L == 0L) {
/* 252 */       int i = (int)((paramLong + 4575744000L) / 900L);
/* 253 */       paramDataOutput.writeByte(i >>> 16 & 0xFF);
/* 254 */       paramDataOutput.writeByte(i >>> 8 & 0xFF);
/* 255 */       paramDataOutput.writeByte(i & 0xFF);
/*     */     } else {
/* 257 */       paramDataOutput.writeByte(255);
/* 258 */       paramDataOutput.writeLong(paramLong);
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
/*     */   static long readEpochSec(DataInput paramDataInput) throws IOException {
/* 270 */     int i = paramDataInput.readByte() & 0xFF;
/* 271 */     if (i == 255) {
/* 272 */       return paramDataInput.readLong();
/*     */     }
/* 274 */     int j = paramDataInput.readByte() & 0xFF;
/* 275 */     int k = paramDataInput.readByte() & 0xFF;
/* 276 */     long l = ((i << 16) + (j << 8) + k);
/* 277 */     return l * 900L - 4575744000L;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/zone/Ser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */