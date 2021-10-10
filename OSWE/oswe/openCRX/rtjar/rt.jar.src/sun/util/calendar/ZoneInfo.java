/*     */ package sun.util.calendar;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.util.Date;
/*     */ import java.util.Map;
/*     */ import java.util.SimpleTimeZone;
/*     */ import java.util.TimeZone;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ZoneInfo
/*     */   extends TimeZone
/*     */ {
/*     */   private static final int UTC_TIME = 0;
/*     */   private static final int STANDARD_TIME = 1;
/*     */   private static final int WALL_TIME = 2;
/*     */   private static final long OFFSET_MASK = 15L;
/*     */   private static final long DST_MASK = 240L;
/*     */   private static final int DST_NSHIFT = 4;
/*     */   private static final long ABBR_MASK = 3840L;
/*     */   private static final int TRANSITION_NSHIFT = 12;
/*  83 */   private static final CalendarSystem gcal = CalendarSystem.getGregorianCalendar();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int rawOffset;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  99 */   private int rawOffsetDiff = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int checksum;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int dstSavings;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long[] transitions;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int[] offsets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int[] simpleTimeZoneParams;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean willGMTOffsetChange = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient boolean dirty = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 2653134537216586139L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient SimpleTimeZone lastRule;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZoneInfo() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZoneInfo(String paramString, int paramInt) {
/* 187 */     this(paramString, paramInt, 0, 0, (long[])null, (int[])null, (int[])null, false);
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
/*     */   ZoneInfo(String paramString, int paramInt1, int paramInt2, int paramInt3, long[] paramArrayOflong, int[] paramArrayOfint1, int[] paramArrayOfint2, boolean paramBoolean) {
/* 213 */     setID(paramString);
/* 214 */     this.rawOffset = paramInt1;
/* 215 */     this.dstSavings = paramInt2;
/* 216 */     this.checksum = paramInt3;
/* 217 */     this.transitions = paramArrayOflong;
/* 218 */     this.offsets = paramArrayOfint1;
/* 219 */     this.simpleTimeZoneParams = paramArrayOfint2;
/* 220 */     this.willGMTOffsetChange = paramBoolean;
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
/*     */   public int getOffset(long paramLong) {
/* 232 */     return getOffsets(paramLong, (int[])null, 0);
/*     */   }
/*     */   
/*     */   public int getOffsets(long paramLong, int[] paramArrayOfint) {
/* 236 */     return getOffsets(paramLong, paramArrayOfint, 0);
/*     */   }
/*     */   
/*     */   public int getOffsetsByStandard(long paramLong, int[] paramArrayOfint) {
/* 240 */     return getOffsets(paramLong, paramArrayOfint, 1);
/*     */   }
/*     */   
/*     */   public int getOffsetsByWall(long paramLong, int[] paramArrayOfint) {
/* 244 */     return getOffsets(paramLong, paramArrayOfint, 2);
/*     */   }
/*     */ 
/*     */   
/*     */   private int getOffsets(long paramLong, int[] paramArrayOfint, int paramInt) {
/* 249 */     if (this.transitions == null) {
/* 250 */       int k = getLastRawOffset();
/* 251 */       if (paramArrayOfint != null) {
/* 252 */         paramArrayOfint[0] = k;
/* 253 */         paramArrayOfint[1] = 0;
/*     */       } 
/* 255 */       return k;
/*     */     } 
/*     */     
/* 258 */     paramLong -= this.rawOffsetDiff;
/* 259 */     int i = getTransitionIndex(paramLong, paramInt);
/*     */ 
/*     */ 
/*     */     
/* 263 */     if (i < 0) {
/* 264 */       int k = getLastRawOffset();
/* 265 */       if (paramArrayOfint != null) {
/* 266 */         paramArrayOfint[0] = k;
/* 267 */         paramArrayOfint[1] = 0;
/*     */       } 
/* 269 */       return k;
/*     */     } 
/*     */     
/* 272 */     if (i < this.transitions.length) {
/* 273 */       long l = this.transitions[i];
/* 274 */       int k = this.offsets[(int)(l & 0xFL)] + this.rawOffsetDiff;
/* 275 */       if (paramArrayOfint != null) {
/* 276 */         int m = (int)(l >>> 4L & 0xFL);
/* 277 */         byte b = (m == 0) ? 0 : this.offsets[m];
/* 278 */         paramArrayOfint[0] = k - b;
/* 279 */         paramArrayOfint[1] = b;
/*     */       } 
/* 281 */       return k;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 286 */     SimpleTimeZone simpleTimeZone = getLastRule();
/* 287 */     if (simpleTimeZone != null) {
/* 288 */       int k = simpleTimeZone.getRawOffset();
/* 289 */       long l = paramLong;
/* 290 */       if (paramInt != 0) {
/* 291 */         l -= this.rawOffset;
/*     */       }
/* 293 */       int m = simpleTimeZone.getOffset(l) - this.rawOffset;
/*     */ 
/*     */       
/* 296 */       if (m > 0 && simpleTimeZone.getOffset(l - m) == k) {
/* 297 */         m = 0;
/*     */       }
/*     */       
/* 300 */       if (paramArrayOfint != null) {
/* 301 */         paramArrayOfint[0] = k;
/* 302 */         paramArrayOfint[1] = m;
/*     */       } 
/* 304 */       return k + m;
/*     */     } 
/* 306 */     int j = getLastRawOffset();
/* 307 */     if (paramArrayOfint != null) {
/* 308 */       paramArrayOfint[0] = j;
/* 309 */       paramArrayOfint[1] = 0;
/*     */     } 
/* 311 */     return j;
/*     */   }
/*     */   
/*     */   private int getTransitionIndex(long paramLong, int paramInt) {
/* 315 */     int i = 0;
/* 316 */     int j = this.transitions.length - 1;
/*     */     
/* 318 */     while (i <= j) {
/* 319 */       int k = (i + j) / 2;
/* 320 */       long l1 = this.transitions[k];
/* 321 */       long l2 = l1 >> 12L;
/* 322 */       if (paramInt != 0) {
/* 323 */         l2 += this.offsets[(int)(l1 & 0xFL)];
/*     */       }
/* 325 */       if (paramInt == 1) {
/* 326 */         int m = (int)(l1 >>> 4L & 0xFL);
/* 327 */         if (m != 0) {
/* 328 */           l2 -= this.offsets[m];
/*     */         }
/*     */       } 
/*     */       
/* 332 */       if (l2 < paramLong) {
/* 333 */         i = k + 1; continue;
/* 334 */       }  if (l2 > paramLong) {
/* 335 */         j = k - 1; continue;
/*     */       } 
/* 337 */       return k;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 342 */     if (i >= this.transitions.length) {
/* 343 */       return i;
/*     */     }
/* 345 */     return i - 1;
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
/*     */   public int getOffset(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 372 */     if (paramInt6 < 0 || paramInt6 >= 86400000) {
/* 373 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/* 376 */     if (paramInt1 == 0) {
/* 377 */       paramInt2 = 1 - paramInt2;
/* 378 */     } else if (paramInt1 != 1) {
/* 379 */       throw new IllegalArgumentException();
/*     */     } 
/*     */     
/* 382 */     CalendarDate calendarDate = gcal.newCalendarDate(null);
/* 383 */     calendarDate.setDate(paramInt2, paramInt3 + 1, paramInt4);
/* 384 */     if (!gcal.validate(calendarDate)) {
/* 385 */       throw new IllegalArgumentException();
/*     */     }
/*     */ 
/*     */     
/* 389 */     if (paramInt5 < 1 || paramInt5 > 7)
/*     */     {
/* 391 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/* 394 */     if (this.transitions == null) {
/* 395 */       return getLastRawOffset();
/*     */     }
/*     */     
/* 398 */     long l = gcal.getTime(calendarDate) + paramInt6;
/* 399 */     l -= this.rawOffset;
/* 400 */     return getOffsets(l, (int[])null, 0);
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
/*     */   public synchronized void setRawOffset(int paramInt) {
/* 412 */     if (paramInt == this.rawOffset + this.rawOffsetDiff) {
/*     */       return;
/*     */     }
/* 415 */     this.rawOffsetDiff = paramInt - this.rawOffset;
/* 416 */     if (this.lastRule != null) {
/* 417 */       this.lastRule.setRawOffset(paramInt);
/*     */     }
/* 419 */     this.dirty = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRawOffset() {
/* 430 */     if (!this.willGMTOffsetChange) {
/* 431 */       return this.rawOffset + this.rawOffsetDiff;
/*     */     }
/*     */     
/* 434 */     int[] arrayOfInt = new int[2];
/* 435 */     getOffsets(System.currentTimeMillis(), arrayOfInt, 0);
/* 436 */     return arrayOfInt[0];
/*     */   }
/*     */   
/*     */   public boolean isDirty() {
/* 440 */     return this.dirty;
/*     */   }
/*     */   
/*     */   private int getLastRawOffset() {
/* 444 */     return this.rawOffset + this.rawOffsetDiff;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean useDaylightTime() {
/* 451 */     return (this.simpleTimeZoneParams != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean observesDaylightTime() {
/* 456 */     if (this.simpleTimeZoneParams != null) {
/* 457 */       return true;
/*     */     }
/* 459 */     if (this.transitions == null) {
/* 460 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 466 */     long l = System.currentTimeMillis() - this.rawOffsetDiff;
/* 467 */     int i = getTransitionIndex(l, 0);
/*     */ 
/*     */     
/* 470 */     if (i < 0) {
/* 471 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 475 */     for (int j = i; j < this.transitions.length; j++) {
/* 476 */       if ((this.transitions[j] & 0xF0L) != 0L) {
/* 477 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 481 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean inDaylightTime(Date paramDate) {
/* 488 */     if (paramDate == null) {
/* 489 */       throw new NullPointerException();
/*     */     }
/*     */     
/* 492 */     if (this.transitions == null) {
/* 493 */       return false;
/*     */     }
/*     */     
/* 496 */     long l = paramDate.getTime() - this.rawOffsetDiff;
/* 497 */     int i = getTransitionIndex(l, 0);
/*     */ 
/*     */     
/* 500 */     if (i < 0) {
/* 501 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 505 */     if (i < this.transitions.length) {
/* 506 */       return ((this.transitions[i] & 0xF0L) != 0L);
/*     */     }
/*     */ 
/*     */     
/* 510 */     SimpleTimeZone simpleTimeZone = getLastRule();
/* 511 */     if (simpleTimeZone != null) {
/* 512 */       return simpleTimeZone.inDaylightTime(paramDate);
/*     */     }
/* 514 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDSTSavings() {
/* 525 */     return this.dstSavings;
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
/*     */   public String toString() {
/* 548 */     return getClass().getName() + "[id=\"" + 
/* 549 */       getID() + "\",offset=" + 
/* 550 */       getLastRawOffset() + ",dstSavings=" + this.dstSavings + ",useDaylight=" + 
/*     */       
/* 552 */       useDaylightTime() + ",transitions=" + ((this.transitions != null) ? this.transitions.length : 0) + ",lastRule=" + ((this.lastRule == null) ? (String)
/*     */       
/* 554 */       getLastRuleInstance() : (String)this.lastRule) + "]";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String[] getAvailableIDs() {
/* 564 */     return ZoneInfoFile.getZoneIds();
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
/*     */   public static String[] getAvailableIDs(int paramInt) {
/* 577 */     return ZoneInfoFile.getZoneIds(paramInt);
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
/*     */   public static TimeZone getTimeZone(String paramString) {
/* 589 */     return ZoneInfoFile.getZoneInfo(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized SimpleTimeZone getLastRule() {
/* 600 */     if (this.lastRule == null) {
/* 601 */       this.lastRule = getLastRuleInstance();
/*     */     }
/* 603 */     return this.lastRule;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleTimeZone getLastRuleInstance() {
/* 614 */     if (this.simpleTimeZoneParams == null) {
/* 615 */       return null;
/*     */     }
/* 617 */     if (this.simpleTimeZoneParams.length == 10) {
/* 618 */       return new SimpleTimeZone(getLastRawOffset(), getID(), this.simpleTimeZoneParams[0], this.simpleTimeZoneParams[1], this.simpleTimeZoneParams[2], this.simpleTimeZoneParams[3], this.simpleTimeZoneParams[4], this.simpleTimeZoneParams[5], this.simpleTimeZoneParams[6], this.simpleTimeZoneParams[7], this.simpleTimeZoneParams[8], this.simpleTimeZoneParams[9], this.dstSavings);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 631 */     return new SimpleTimeZone(getLastRawOffset(), getID(), this.simpleTimeZoneParams[0], this.simpleTimeZoneParams[1], this.simpleTimeZoneParams[2], this.simpleTimeZoneParams[3], this.simpleTimeZoneParams[4], this.simpleTimeZoneParams[5], this.simpleTimeZoneParams[6], this.simpleTimeZoneParams[7], this.dstSavings);
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
/*     */   public Object clone() {
/* 647 */     ZoneInfo zoneInfo = (ZoneInfo)super.clone();
/* 648 */     zoneInfo.lastRule = null;
/* 649 */     return zoneInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 658 */     return getLastRawOffset() ^ this.checksum;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 669 */     if (this == paramObject) {
/* 670 */       return true;
/*     */     }
/* 672 */     if (!(paramObject instanceof ZoneInfo)) {
/* 673 */       return false;
/*     */     }
/* 675 */     ZoneInfo zoneInfo = (ZoneInfo)paramObject;
/* 676 */     return (getID().equals(zoneInfo.getID()) && 
/* 677 */       getLastRawOffset() == zoneInfo.getLastRawOffset() && this.checksum == zoneInfo.checksum);
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
/*     */   public boolean hasSameRules(TimeZone paramTimeZone) {
/* 693 */     if (this == paramTimeZone) {
/* 694 */       return true;
/*     */     }
/* 696 */     if (paramTimeZone == null) {
/* 697 */       return false;
/*     */     }
/* 699 */     if (!(paramTimeZone instanceof ZoneInfo)) {
/* 700 */       if (getRawOffset() != paramTimeZone.getRawOffset()) {
/* 701 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 705 */       if (this.transitions == null && 
/* 706 */         !useDaylightTime() && 
/* 707 */         !paramTimeZone.useDaylightTime()) {
/* 708 */         return true;
/*     */       }
/* 710 */       return false;
/*     */     } 
/* 712 */     if (getLastRawOffset() != ((ZoneInfo)paramTimeZone).getLastRawOffset()) {
/* 713 */       return false;
/*     */     }
/* 715 */     return (this.checksum == ((ZoneInfo)paramTimeZone).checksum);
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
/*     */   public static Map<String, String> getAliasTable() {
/* 727 */     return ZoneInfoFile.getAliasMap();
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 732 */     paramObjectInputStream.defaultReadObject();
/*     */ 
/*     */     
/* 735 */     this.dirty = true;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/calendar/ZoneInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */