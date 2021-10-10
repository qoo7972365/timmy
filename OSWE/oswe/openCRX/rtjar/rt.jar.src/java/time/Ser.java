/*     */ package java.time;
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
/*     */ final class Ser
/*     */   implements Externalizable
/*     */ {
/*     */   private static final long serialVersionUID = -7683839454370182990L;
/*     */   static final byte DURATION_TYPE = 1;
/*     */   static final byte INSTANT_TYPE = 2;
/*     */   static final byte LOCAL_DATE_TYPE = 3;
/*     */   static final byte LOCAL_TIME_TYPE = 4;
/*     */   static final byte LOCAL_DATE_TIME_TYPE = 5;
/*     */   static final byte ZONE_DATE_TIME_TYPE = 6;
/*     */   static final byte ZONE_REGION_TYPE = 7;
/*     */   static final byte ZONE_OFFSET_TYPE = 8;
/*     */   static final byte OFFSET_TIME_TYPE = 9;
/*     */   static final byte OFFSET_DATE_TIME_TYPE = 10;
/*     */   static final byte YEAR_TYPE = 11;
/*     */   static final byte YEAR_MONTH_TYPE = 12;
/*     */   static final byte MONTH_DAY_TYPE = 13;
/*     */   static final byte PERIOD_TYPE = 14;
/*     */   private byte type;
/*     */   private Object object;
/*     */   
/*     */   public Ser() {}
/*     */   
/*     */   Ser(byte paramByte, Object paramObject) {
/* 129 */     this.type = paramByte;
/* 130 */     this.object = paramObject;
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
/*     */   public void writeExternal(ObjectOutput paramObjectOutput) throws IOException {
/* 162 */     writeInternal(this.type, this.object, paramObjectOutput);
/*     */   }
/*     */   
/*     */   static void writeInternal(byte paramByte, Object paramObject, ObjectOutput paramObjectOutput) throws IOException {
/* 166 */     paramObjectOutput.writeByte(paramByte);
/* 167 */     switch (paramByte) {
/*     */       case 1:
/* 169 */         ((Duration)paramObject).writeExternal(paramObjectOutput);
/*     */         return;
/*     */       case 2:
/* 172 */         ((Instant)paramObject).writeExternal(paramObjectOutput);
/*     */         return;
/*     */       case 3:
/* 175 */         ((LocalDate)paramObject).writeExternal(paramObjectOutput);
/*     */         return;
/*     */       case 5:
/* 178 */         ((LocalDateTime)paramObject).writeExternal(paramObjectOutput);
/*     */         return;
/*     */       case 4:
/* 181 */         ((LocalTime)paramObject).writeExternal(paramObjectOutput);
/*     */         return;
/*     */       case 7:
/* 184 */         ((ZoneRegion)paramObject).writeExternal(paramObjectOutput);
/*     */         return;
/*     */       case 8:
/* 187 */         ((ZoneOffset)paramObject).writeExternal(paramObjectOutput);
/*     */         return;
/*     */       case 6:
/* 190 */         ((ZonedDateTime)paramObject).writeExternal(paramObjectOutput);
/*     */         return;
/*     */       case 9:
/* 193 */         ((OffsetTime)paramObject).writeExternal(paramObjectOutput);
/*     */         return;
/*     */       case 10:
/* 196 */         ((OffsetDateTime)paramObject).writeExternal(paramObjectOutput);
/*     */         return;
/*     */       case 11:
/* 199 */         ((Year)paramObject).writeExternal(paramObjectOutput);
/*     */         return;
/*     */       case 12:
/* 202 */         ((YearMonth)paramObject).writeExternal(paramObjectOutput);
/*     */         return;
/*     */       case 13:
/* 205 */         ((MonthDay)paramObject).writeExternal(paramObjectOutput);
/*     */         return;
/*     */       case 14:
/* 208 */         ((Period)paramObject).writeExternal(paramObjectOutput);
/*     */         return;
/*     */     } 
/* 211 */     throw new InvalidClassException("Unknown serialized type");
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
/*     */   public void readExternal(ObjectInput paramObjectInput) throws IOException, ClassNotFoundException {
/* 245 */     this.type = paramObjectInput.readByte();
/* 246 */     this.object = readInternal(this.type, paramObjectInput);
/*     */   }
/*     */   
/*     */   static Object read(ObjectInput paramObjectInput) throws IOException, ClassNotFoundException {
/* 250 */     byte b = paramObjectInput.readByte();
/* 251 */     return readInternal(b, paramObjectInput);
/*     */   }
/*     */   
/*     */   private static Object readInternal(byte paramByte, ObjectInput paramObjectInput) throws IOException, ClassNotFoundException {
/* 255 */     switch (paramByte) { case 1:
/* 256 */         return Duration.readExternal(paramObjectInput);
/* 257 */       case 2: return Instant.readExternal(paramObjectInput);
/* 258 */       case 3: return LocalDate.readExternal(paramObjectInput);
/* 259 */       case 5: return LocalDateTime.readExternal(paramObjectInput);
/* 260 */       case 4: return LocalTime.readExternal(paramObjectInput);
/* 261 */       case 6: return ZonedDateTime.readExternal(paramObjectInput);
/* 262 */       case 8: return ZoneOffset.readExternal(paramObjectInput);
/* 263 */       case 7: return ZoneRegion.readExternal(paramObjectInput);
/* 264 */       case 9: return OffsetTime.readExternal(paramObjectInput);
/* 265 */       case 10: return OffsetDateTime.readExternal(paramObjectInput);
/* 266 */       case 11: return Year.readExternal(paramObjectInput);
/* 267 */       case 12: return YearMonth.readExternal(paramObjectInput);
/* 268 */       case 13: return MonthDay.readExternal(paramObjectInput);
/* 269 */       case 14: return Period.readExternal(paramObjectInput); }
/*     */     
/* 271 */     throw new StreamCorruptedException("Unknown serialized type");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object readResolve() {
/* 281 */     return this.object;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/Ser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */