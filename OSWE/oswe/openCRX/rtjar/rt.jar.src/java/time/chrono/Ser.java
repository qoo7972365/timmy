/*     */ package java.time.chrono;
/*     */ 
/*     */ import java.io.Externalizable;
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidClassException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
/*     */ import java.io.StreamCorruptedException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   private static final long serialVersionUID = -6103370247208168577L;
/*     */   static final byte CHRONO_TYPE = 1;
/*     */   static final byte CHRONO_LOCAL_DATE_TIME_TYPE = 2;
/*     */   static final byte CHRONO_ZONE_DATE_TIME_TYPE = 3;
/*     */   static final byte JAPANESE_DATE_TYPE = 4;
/*     */   static final byte JAPANESE_ERA_TYPE = 5;
/*     */   static final byte HIJRAH_DATE_TYPE = 6;
/*     */   static final byte MINGUO_DATE_TYPE = 7;
/*     */   static final byte THAIBUDDHIST_DATE_TYPE = 8;
/*     */   static final byte CHRONO_PERIOD_TYPE = 9;
/*     */   private byte type;
/*     */   private Object object;
/*     */   
/*     */   public Ser() {}
/*     */   
/*     */   Ser(byte paramByte, Object paramObject) {
/* 127 */     this.type = paramByte;
/* 128 */     this.object = paramObject;
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
/*     */   public void writeExternal(ObjectOutput paramObjectOutput) throws IOException {
/* 157 */     writeInternal(this.type, this.object, paramObjectOutput);
/*     */   }
/*     */   
/*     */   private static void writeInternal(byte paramByte, Object paramObject, ObjectOutput paramObjectOutput) throws IOException {
/* 161 */     paramObjectOutput.writeByte(paramByte);
/* 162 */     switch (paramByte) {
/*     */       case 1:
/* 164 */         ((AbstractChronology)paramObject).writeExternal(paramObjectOutput);
/*     */         return;
/*     */       case 2:
/* 167 */         ((ChronoLocalDateTimeImpl)paramObject).writeExternal(paramObjectOutput);
/*     */         return;
/*     */       case 3:
/* 170 */         ((ChronoZonedDateTimeImpl)paramObject).writeExternal(paramObjectOutput);
/*     */         return;
/*     */       case 4:
/* 173 */         ((JapaneseDate)paramObject).writeExternal(paramObjectOutput);
/*     */         return;
/*     */       case 5:
/* 176 */         ((JapaneseEra)paramObject).writeExternal(paramObjectOutput);
/*     */         return;
/*     */       case 6:
/* 179 */         ((HijrahDate)paramObject).writeExternal(paramObjectOutput);
/*     */         return;
/*     */       case 7:
/* 182 */         ((MinguoDate)paramObject).writeExternal(paramObjectOutput);
/*     */         return;
/*     */       case 8:
/* 185 */         ((ThaiBuddhistDate)paramObject).writeExternal(paramObjectOutput);
/*     */         return;
/*     */       case 9:
/* 188 */         ((ChronoPeriodImpl)paramObject).writeExternal(paramObjectOutput);
/*     */         return;
/*     */     } 
/* 191 */     throw new InvalidClassException("Unknown serialized type");
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
/*     */   public void readExternal(ObjectInput paramObjectInput) throws IOException, ClassNotFoundException {
/* 223 */     this.type = paramObjectInput.readByte();
/* 224 */     this.object = readInternal(this.type, paramObjectInput);
/*     */   }
/*     */   
/*     */   static Object read(ObjectInput paramObjectInput) throws IOException, ClassNotFoundException {
/* 228 */     byte b = paramObjectInput.readByte();
/* 229 */     return readInternal(b, paramObjectInput);
/*     */   }
/*     */   
/*     */   private static Object readInternal(byte paramByte, ObjectInput paramObjectInput) throws IOException, ClassNotFoundException {
/* 233 */     switch (paramByte) { case 1:
/* 234 */         return AbstractChronology.readExternal(paramObjectInput);
/* 235 */       case 2: return ChronoLocalDateTimeImpl.readExternal(paramObjectInput);
/* 236 */       case 3: return ChronoZonedDateTimeImpl.readExternal(paramObjectInput);
/* 237 */       case 4: return JapaneseDate.readExternal(paramObjectInput);
/* 238 */       case 5: return JapaneseEra.readExternal(paramObjectInput);
/* 239 */       case 6: return HijrahDate.readExternal(paramObjectInput);
/* 240 */       case 7: return MinguoDate.readExternal(paramObjectInput);
/* 241 */       case 8: return ThaiBuddhistDate.readExternal(paramObjectInput);
/* 242 */       case 9: return ChronoPeriodImpl.readExternal(paramObjectInput); }
/* 243 */      throw new StreamCorruptedException("Unknown serialized type");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object readResolve() {
/* 253 */     return this.object;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/chrono/Ser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */