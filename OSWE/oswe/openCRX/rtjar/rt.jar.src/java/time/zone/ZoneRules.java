/*      */ package java.time.zone;
/*      */ 
/*      */ import java.io.DataInput;
/*      */ import java.io.DataOutput;
/*      */ import java.io.IOException;
/*      */ import java.io.InvalidObjectException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.Serializable;
/*      */ import java.time.Duration;
/*      */ import java.time.Instant;
/*      */ import java.time.LocalDate;
/*      */ import java.time.LocalDateTime;
/*      */ import java.time.ZoneOffset;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.List;
/*      */ import java.util.Objects;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.concurrent.ConcurrentMap;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class ZoneRules
/*      */   implements Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 3044319355680032515L;
/*      */   private static final int LAST_CACHED_YEAR = 2100;
/*      */   private final long[] standardTransitions;
/*      */   private final ZoneOffset[] standardOffsets;
/*      */   private final long[] savingsInstantTransitions;
/*      */   private final LocalDateTime[] savingsLocalTransitions;
/*      */   private final ZoneOffset[] wallOffsets;
/*      */   private final ZoneOffsetTransitionRule[] lastRules;
/*  150 */   private final transient ConcurrentMap<Integer, ZoneOffsetTransition[]> lastRulesCache = (ConcurrentMap)new ConcurrentHashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  155 */   private static final long[] EMPTY_LONG_ARRAY = new long[0];
/*      */ 
/*      */ 
/*      */   
/*  159 */   private static final ZoneOffsetTransitionRule[] EMPTY_LASTRULES = new ZoneOffsetTransitionRule[0];
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  164 */   private static final LocalDateTime[] EMPTY_LDT_ARRAY = new LocalDateTime[0];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ZoneRules of(ZoneOffset paramZoneOffset1, ZoneOffset paramZoneOffset2, List<ZoneOffsetTransition> paramList1, List<ZoneOffsetTransition> paramList2, List<ZoneOffsetTransitionRule> paramList) {
/*  181 */     Objects.requireNonNull(paramZoneOffset1, "baseStandardOffset");
/*  182 */     Objects.requireNonNull(paramZoneOffset2, "baseWallOffset");
/*  183 */     Objects.requireNonNull(paramList1, "standardOffsetTransitionList");
/*  184 */     Objects.requireNonNull(paramList2, "transitionList");
/*  185 */     Objects.requireNonNull(paramList, "lastRules");
/*  186 */     return new ZoneRules(paramZoneOffset1, paramZoneOffset2, paramList1, paramList2, paramList);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ZoneRules of(ZoneOffset paramZoneOffset) {
/*  198 */     Objects.requireNonNull(paramZoneOffset, "offset");
/*  199 */     return new ZoneRules(paramZoneOffset);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   ZoneRules(ZoneOffset paramZoneOffset1, ZoneOffset paramZoneOffset2, List<ZoneOffsetTransition> paramList1, List<ZoneOffsetTransition> paramList2, List<ZoneOffsetTransitionRule> paramList) {
/*  220 */     this.standardTransitions = new long[paramList1.size()];
/*      */     
/*  222 */     this.standardOffsets = new ZoneOffset[paramList1.size() + 1];
/*  223 */     this.standardOffsets[0] = paramZoneOffset1;
/*  224 */     for (byte b1 = 0; b1 < paramList1.size(); b1++) {
/*  225 */       this.standardTransitions[b1] = ((ZoneOffsetTransition)paramList1.get(b1)).toEpochSecond();
/*  226 */       this.standardOffsets[b1 + 1] = ((ZoneOffsetTransition)paramList1.get(b1)).getOffsetAfter();
/*      */     } 
/*      */ 
/*      */     
/*  230 */     ArrayList<LocalDateTime> arrayList = new ArrayList();
/*  231 */     ArrayList<ZoneOffset> arrayList1 = new ArrayList();
/*  232 */     arrayList1.add(paramZoneOffset2);
/*  233 */     for (ZoneOffsetTransition zoneOffsetTransition : paramList2) {
/*  234 */       if (zoneOffsetTransition.isGap()) {
/*  235 */         arrayList.add(zoneOffsetTransition.getDateTimeBefore());
/*  236 */         arrayList.add(zoneOffsetTransition.getDateTimeAfter());
/*      */       } else {
/*  238 */         arrayList.add(zoneOffsetTransition.getDateTimeAfter());
/*  239 */         arrayList.add(zoneOffsetTransition.getDateTimeBefore());
/*      */       } 
/*  241 */       arrayList1.add(zoneOffsetTransition.getOffsetAfter());
/*      */     } 
/*  243 */     this.savingsLocalTransitions = arrayList.<LocalDateTime>toArray(new LocalDateTime[arrayList.size()]);
/*  244 */     this.wallOffsets = arrayList1.<ZoneOffset>toArray(new ZoneOffset[arrayList1.size()]);
/*      */ 
/*      */     
/*  247 */     this.savingsInstantTransitions = new long[paramList2.size()];
/*  248 */     for (byte b2 = 0; b2 < paramList2.size(); b2++) {
/*  249 */       this.savingsInstantTransitions[b2] = ((ZoneOffsetTransition)paramList2.get(b2)).toEpochSecond();
/*      */     }
/*      */ 
/*      */     
/*  253 */     if (paramList.size() > 16) {
/*  254 */       throw new IllegalArgumentException("Too many transition rules");
/*      */     }
/*  256 */     this.lastRules = paramList.<ZoneOffsetTransitionRule>toArray(new ZoneOffsetTransitionRule[paramList.size()]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ZoneRules(long[] paramArrayOflong1, ZoneOffset[] paramArrayOfZoneOffset1, long[] paramArrayOflong2, ZoneOffset[] paramArrayOfZoneOffset2, ZoneOffsetTransitionRule[] paramArrayOfZoneOffsetTransitionRule) {
/*  275 */     this.standardTransitions = paramArrayOflong1;
/*  276 */     this.standardOffsets = paramArrayOfZoneOffset1;
/*  277 */     this.savingsInstantTransitions = paramArrayOflong2;
/*  278 */     this.wallOffsets = paramArrayOfZoneOffset2;
/*  279 */     this.lastRules = paramArrayOfZoneOffsetTransitionRule;
/*      */     
/*  281 */     if (paramArrayOflong2.length == 0) {
/*  282 */       this.savingsLocalTransitions = EMPTY_LDT_ARRAY;
/*      */     } else {
/*      */       
/*  285 */       ArrayList<LocalDateTime> arrayList = new ArrayList();
/*  286 */       for (byte b = 0; b < paramArrayOflong2.length; b++) {
/*  287 */         ZoneOffset zoneOffset1 = paramArrayOfZoneOffset2[b];
/*  288 */         ZoneOffset zoneOffset2 = paramArrayOfZoneOffset2[b + 1];
/*  289 */         ZoneOffsetTransition zoneOffsetTransition = new ZoneOffsetTransition(paramArrayOflong2[b], zoneOffset1, zoneOffset2);
/*  290 */         if (zoneOffsetTransition.isGap()) {
/*  291 */           arrayList.add(zoneOffsetTransition.getDateTimeBefore());
/*  292 */           arrayList.add(zoneOffsetTransition.getDateTimeAfter());
/*      */         } else {
/*  294 */           arrayList.add(zoneOffsetTransition.getDateTimeAfter());
/*  295 */           arrayList.add(zoneOffsetTransition.getDateTimeBefore());
/*      */         } 
/*      */       } 
/*  298 */       this.savingsLocalTransitions = arrayList.<LocalDateTime>toArray(new LocalDateTime[arrayList.size()]);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ZoneRules(ZoneOffset paramZoneOffset) {
/*  310 */     this.standardOffsets = new ZoneOffset[1];
/*  311 */     this.standardOffsets[0] = paramZoneOffset;
/*  312 */     this.standardTransitions = EMPTY_LONG_ARRAY;
/*  313 */     this.savingsInstantTransitions = EMPTY_LONG_ARRAY;
/*  314 */     this.savingsLocalTransitions = EMPTY_LDT_ARRAY;
/*  315 */     this.wallOffsets = this.standardOffsets;
/*  316 */     this.lastRules = EMPTY_LASTRULES;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws InvalidObjectException {
/*  326 */     throw new InvalidObjectException("Deserialization via serialization delegate");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Object writeReplace() {
/*  392 */     return new Ser((byte)1, this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void writeExternal(DataOutput paramDataOutput) throws IOException {
/*  402 */     paramDataOutput.writeInt(this.standardTransitions.length);
/*  403 */     for (long l : this.standardTransitions) {
/*  404 */       Ser.writeEpochSec(l, paramDataOutput);
/*      */     }
/*  406 */     for (ZoneOffset zoneOffset : this.standardOffsets) {
/*  407 */       Ser.writeOffset(zoneOffset, paramDataOutput);
/*      */     }
/*  409 */     paramDataOutput.writeInt(this.savingsInstantTransitions.length);
/*  410 */     for (long l : this.savingsInstantTransitions) {
/*  411 */       Ser.writeEpochSec(l, paramDataOutput);
/*      */     }
/*  413 */     for (ZoneOffset zoneOffset : this.wallOffsets) {
/*  414 */       Ser.writeOffset(zoneOffset, paramDataOutput);
/*      */     }
/*  416 */     paramDataOutput.writeByte(this.lastRules.length);
/*  417 */     for (ZoneOffsetTransitionRule zoneOffsetTransitionRule : this.lastRules) {
/*  418 */       zoneOffsetTransitionRule.writeExternal(paramDataOutput);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static ZoneRules readExternal(DataInput paramDataInput) throws IOException, ClassNotFoundException {
/*  430 */     int i = paramDataInput.readInt();
/*  431 */     long[] arrayOfLong1 = (i == 0) ? EMPTY_LONG_ARRAY : new long[i];
/*      */     
/*  433 */     for (byte b1 = 0; b1 < i; b1++) {
/*  434 */       arrayOfLong1[b1] = Ser.readEpochSec(paramDataInput);
/*      */     }
/*  436 */     ZoneOffset[] arrayOfZoneOffset1 = new ZoneOffset[i + 1]; int j;
/*  437 */     for (j = 0; j < arrayOfZoneOffset1.length; j++) {
/*  438 */       arrayOfZoneOffset1[j] = Ser.readOffset(paramDataInput);
/*      */     }
/*  440 */     j = paramDataInput.readInt();
/*  441 */     long[] arrayOfLong2 = (j == 0) ? EMPTY_LONG_ARRAY : new long[j];
/*      */     
/*  443 */     for (byte b2 = 0; b2 < j; b2++) {
/*  444 */       arrayOfLong2[b2] = Ser.readEpochSec(paramDataInput);
/*      */     }
/*  446 */     ZoneOffset[] arrayOfZoneOffset2 = new ZoneOffset[j + 1]; byte b;
/*  447 */     for (b = 0; b < arrayOfZoneOffset2.length; b++) {
/*  448 */       arrayOfZoneOffset2[b] = Ser.readOffset(paramDataInput);
/*      */     }
/*  450 */     b = paramDataInput.readByte();
/*  451 */     ZoneOffsetTransitionRule[] arrayOfZoneOffsetTransitionRule = (b == 0) ? EMPTY_LASTRULES : new ZoneOffsetTransitionRule[b];
/*      */     
/*  453 */     for (byte b3 = 0; b3 < b; b3++) {
/*  454 */       arrayOfZoneOffsetTransitionRule[b3] = ZoneOffsetTransitionRule.readExternal(paramDataInput);
/*      */     }
/*  456 */     return new ZoneRules(arrayOfLong1, arrayOfZoneOffset1, arrayOfLong2, arrayOfZoneOffset2, arrayOfZoneOffsetTransitionRule);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFixedOffset() {
/*  465 */     return (this.savingsInstantTransitions.length == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ZoneOffset getOffset(Instant paramInstant) {
/*  480 */     if (this.savingsInstantTransitions.length == 0) {
/*  481 */       return this.standardOffsets[0];
/*      */     }
/*  483 */     long l = paramInstant.getEpochSecond();
/*      */     
/*  485 */     if (this.lastRules.length > 0 && l > this.savingsInstantTransitions[this.savingsInstantTransitions.length - 1]) {
/*      */       
/*  487 */       int j = findYear(l, this.wallOffsets[this.wallOffsets.length - 1]);
/*  488 */       ZoneOffsetTransition[] arrayOfZoneOffsetTransition = findTransitionArray(j);
/*  489 */       ZoneOffsetTransition zoneOffsetTransition = null;
/*  490 */       for (byte b = 0; b < arrayOfZoneOffsetTransition.length; b++) {
/*  491 */         zoneOffsetTransition = arrayOfZoneOffsetTransition[b];
/*  492 */         if (l < zoneOffsetTransition.toEpochSecond()) {
/*  493 */           return zoneOffsetTransition.getOffsetBefore();
/*      */         }
/*      */       } 
/*  496 */       return zoneOffsetTransition.getOffsetAfter();
/*      */     } 
/*      */ 
/*      */     
/*  500 */     int i = Arrays.binarySearch(this.savingsInstantTransitions, l);
/*  501 */     if (i < 0)
/*      */     {
/*  503 */       i = -i - 2;
/*      */     }
/*  505 */     return this.wallOffsets[i + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ZoneOffset getOffset(LocalDateTime paramLocalDateTime) {
/*  537 */     Object object = getOffsetInfo(paramLocalDateTime);
/*  538 */     if (object instanceof ZoneOffsetTransition) {
/*  539 */       return ((ZoneOffsetTransition)object).getOffsetBefore();
/*      */     }
/*  541 */     return (ZoneOffset)object;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<ZoneOffset> getValidOffsets(LocalDateTime paramLocalDateTime) {
/*  588 */     Object object = getOffsetInfo(paramLocalDateTime);
/*  589 */     if (object instanceof ZoneOffsetTransition) {
/*  590 */       return ((ZoneOffsetTransition)object).getValidOffsets();
/*      */     }
/*  592 */     return Collections.singletonList((ZoneOffset)object);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ZoneOffsetTransition getTransition(LocalDateTime paramLocalDateTime) {
/*  630 */     Object object = getOffsetInfo(paramLocalDateTime);
/*  631 */     return (object instanceof ZoneOffsetTransition) ? (ZoneOffsetTransition)object : null;
/*      */   }
/*      */   
/*      */   private Object getOffsetInfo(LocalDateTime paramLocalDateTime) {
/*  635 */     if (this.savingsInstantTransitions.length == 0) {
/*  636 */       return this.standardOffsets[0];
/*      */     }
/*      */     
/*  639 */     if (this.lastRules.length > 0 && paramLocalDateTime
/*  640 */       .isAfter(this.savingsLocalTransitions[this.savingsLocalTransitions.length - 1])) {
/*  641 */       ZoneOffsetTransition[] arrayOfZoneOffsetTransition = findTransitionArray(paramLocalDateTime.getYear());
/*  642 */       Object object = null;
/*  643 */       for (ZoneOffsetTransition zoneOffsetTransition : arrayOfZoneOffsetTransition) {
/*  644 */         object = findOffsetInfo(paramLocalDateTime, zoneOffsetTransition);
/*  645 */         if (object instanceof ZoneOffsetTransition || object.equals(zoneOffsetTransition.getOffsetBefore())) {
/*  646 */           return object;
/*      */         }
/*      */       } 
/*  649 */       return object;
/*      */     } 
/*      */ 
/*      */     
/*  653 */     int i = Arrays.binarySearch((Object[])this.savingsLocalTransitions, paramLocalDateTime);
/*  654 */     if (i == -1)
/*      */     {
/*  656 */       return this.wallOffsets[0];
/*      */     }
/*  658 */     if (i < 0) {
/*      */       
/*  660 */       i = -i - 2;
/*  661 */     } else if (i < this.savingsLocalTransitions.length - 1 && this.savingsLocalTransitions[i]
/*  662 */       .equals(this.savingsLocalTransitions[i + 1])) {
/*      */       
/*  664 */       i++;
/*      */     } 
/*  666 */     if ((i & 0x1) == 0) {
/*      */       
/*  668 */       LocalDateTime localDateTime1 = this.savingsLocalTransitions[i];
/*  669 */       LocalDateTime localDateTime2 = this.savingsLocalTransitions[i + 1];
/*  670 */       ZoneOffset zoneOffset1 = this.wallOffsets[i / 2];
/*  671 */       ZoneOffset zoneOffset2 = this.wallOffsets[i / 2 + 1];
/*  672 */       if (zoneOffset2.getTotalSeconds() > zoneOffset1.getTotalSeconds())
/*      */       {
/*  674 */         return new ZoneOffsetTransition(localDateTime1, zoneOffset1, zoneOffset2);
/*      */       }
/*      */       
/*  677 */       return new ZoneOffsetTransition(localDateTime2, zoneOffset1, zoneOffset2);
/*      */     } 
/*      */ 
/*      */     
/*  681 */     return this.wallOffsets[i / 2 + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Object findOffsetInfo(LocalDateTime paramLocalDateTime, ZoneOffsetTransition paramZoneOffsetTransition) {
/*  693 */     LocalDateTime localDateTime = paramZoneOffsetTransition.getDateTimeBefore();
/*  694 */     if (paramZoneOffsetTransition.isGap()) {
/*  695 */       if (paramLocalDateTime.isBefore(localDateTime)) {
/*  696 */         return paramZoneOffsetTransition.getOffsetBefore();
/*      */       }
/*  698 */       if (paramLocalDateTime.isBefore(paramZoneOffsetTransition.getDateTimeAfter())) {
/*  699 */         return paramZoneOffsetTransition;
/*      */       }
/*  701 */       return paramZoneOffsetTransition.getOffsetAfter();
/*      */     } 
/*      */     
/*  704 */     if (!paramLocalDateTime.isBefore(localDateTime)) {
/*  705 */       return paramZoneOffsetTransition.getOffsetAfter();
/*      */     }
/*  707 */     if (paramLocalDateTime.isBefore(paramZoneOffsetTransition.getDateTimeAfter())) {
/*  708 */       return paramZoneOffsetTransition.getOffsetBefore();
/*      */     }
/*  710 */     return paramZoneOffsetTransition;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ZoneOffsetTransition[] findTransitionArray(int paramInt) {
/*  722 */     Integer integer = Integer.valueOf(paramInt);
/*  723 */     ZoneOffsetTransition[] arrayOfZoneOffsetTransition = this.lastRulesCache.get(integer);
/*  724 */     if (arrayOfZoneOffsetTransition != null) {
/*  725 */       return arrayOfZoneOffsetTransition;
/*      */     }
/*  727 */     ZoneOffsetTransitionRule[] arrayOfZoneOffsetTransitionRule = this.lastRules;
/*  728 */     arrayOfZoneOffsetTransition = new ZoneOffsetTransition[arrayOfZoneOffsetTransitionRule.length];
/*  729 */     for (byte b = 0; b < arrayOfZoneOffsetTransitionRule.length; b++) {
/*  730 */       arrayOfZoneOffsetTransition[b] = arrayOfZoneOffsetTransitionRule[b].createTransition(paramInt);
/*      */     }
/*  732 */     if (paramInt < 2100) {
/*  733 */       this.lastRulesCache.putIfAbsent(integer, arrayOfZoneOffsetTransition);
/*      */     }
/*  735 */     return arrayOfZoneOffsetTransition;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ZoneOffset getStandardOffset(Instant paramInstant) {
/*  751 */     if (this.savingsInstantTransitions.length == 0) {
/*  752 */       return this.standardOffsets[0];
/*      */     }
/*  754 */     long l = paramInstant.getEpochSecond();
/*  755 */     int i = Arrays.binarySearch(this.standardTransitions, l);
/*  756 */     if (i < 0)
/*      */     {
/*  758 */       i = -i - 2;
/*      */     }
/*  760 */     return this.standardOffsets[i + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Duration getDaylightSavings(Instant paramInstant) {
/*  781 */     if (this.savingsInstantTransitions.length == 0) {
/*  782 */       return Duration.ZERO;
/*      */     }
/*  784 */     ZoneOffset zoneOffset1 = getStandardOffset(paramInstant);
/*  785 */     ZoneOffset zoneOffset2 = getOffset(paramInstant);
/*  786 */     return Duration.ofSeconds((zoneOffset2.getTotalSeconds() - zoneOffset1.getTotalSeconds()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDaylightSavings(Instant paramInstant) {
/*  804 */     return !getStandardOffset(paramInstant).equals(getOffset(paramInstant));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isValidOffset(LocalDateTime paramLocalDateTime, ZoneOffset paramZoneOffset) {
/*  822 */     return getValidOffsets(paramLocalDateTime).contains(paramZoneOffset);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ZoneOffsetTransition nextTransition(Instant paramInstant) {
/*  837 */     if (this.savingsInstantTransitions.length == 0) {
/*  838 */       return null;
/*      */     }
/*  840 */     long l = paramInstant.getEpochSecond();
/*      */     
/*  842 */     if (l >= this.savingsInstantTransitions[this.savingsInstantTransitions.length - 1]) {
/*  843 */       if (this.lastRules.length == 0) {
/*  844 */         return null;
/*      */       }
/*      */       
/*  847 */       int j = findYear(l, this.wallOffsets[this.wallOffsets.length - 1]);
/*  848 */       ZoneOffsetTransition[] arrayOfZoneOffsetTransition = findTransitionArray(j);
/*  849 */       for (ZoneOffsetTransition zoneOffsetTransition : arrayOfZoneOffsetTransition) {
/*  850 */         if (l < zoneOffsetTransition.toEpochSecond()) {
/*  851 */           return zoneOffsetTransition;
/*      */         }
/*      */       } 
/*      */       
/*  855 */       if (j < 999999999) {
/*  856 */         arrayOfZoneOffsetTransition = findTransitionArray(j + 1);
/*  857 */         return arrayOfZoneOffsetTransition[0];
/*      */       } 
/*  859 */       return null;
/*      */     } 
/*      */ 
/*      */     
/*  863 */     int i = Arrays.binarySearch(this.savingsInstantTransitions, l);
/*  864 */     if (i < 0) {
/*  865 */       i = -i - 1;
/*      */     } else {
/*  867 */       i++;
/*      */     } 
/*  869 */     return new ZoneOffsetTransition(this.savingsInstantTransitions[i], this.wallOffsets[i], this.wallOffsets[i + 1]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ZoneOffsetTransition previousTransition(Instant paramInstant) {
/*  884 */     if (this.savingsInstantTransitions.length == 0) {
/*  885 */       return null;
/*      */     }
/*  887 */     long l1 = paramInstant.getEpochSecond();
/*  888 */     if (paramInstant.getNano() > 0 && l1 < Long.MAX_VALUE) {
/*  889 */       l1++;
/*      */     }
/*      */ 
/*      */     
/*  893 */     long l2 = this.savingsInstantTransitions[this.savingsInstantTransitions.length - 1];
/*  894 */     if (this.lastRules.length > 0 && l1 > l2) {
/*      */       
/*  896 */       ZoneOffset zoneOffset = this.wallOffsets[this.wallOffsets.length - 1];
/*  897 */       int j = findYear(l1, zoneOffset);
/*  898 */       ZoneOffsetTransition[] arrayOfZoneOffsetTransition = findTransitionArray(j); int k;
/*  899 */       for (k = arrayOfZoneOffsetTransition.length - 1; k >= 0; k--) {
/*  900 */         if (l1 > arrayOfZoneOffsetTransition[k].toEpochSecond()) {
/*  901 */           return arrayOfZoneOffsetTransition[k];
/*      */         }
/*      */       } 
/*      */       
/*  905 */       k = findYear(l2, zoneOffset);
/*  906 */       if (--j > k) {
/*  907 */         arrayOfZoneOffsetTransition = findTransitionArray(j);
/*  908 */         return arrayOfZoneOffsetTransition[arrayOfZoneOffsetTransition.length - 1];
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  914 */     int i = Arrays.binarySearch(this.savingsInstantTransitions, l1);
/*  915 */     if (i < 0) {
/*  916 */       i = -i - 1;
/*      */     }
/*  918 */     if (i <= 0) {
/*  919 */       return null;
/*      */     }
/*  921 */     return new ZoneOffsetTransition(this.savingsInstantTransitions[i - 1], this.wallOffsets[i - 1], this.wallOffsets[i]);
/*      */   }
/*      */ 
/*      */   
/*      */   private int findYear(long paramLong, ZoneOffset paramZoneOffset) {
/*  926 */     long l1 = paramLong + paramZoneOffset.getTotalSeconds();
/*  927 */     long l2 = Math.floorDiv(l1, 86400L);
/*  928 */     return LocalDate.ofEpochDay(l2).getYear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<ZoneOffsetTransition> getTransitions() {
/*  944 */     ArrayList<ZoneOffsetTransition> arrayList = new ArrayList();
/*  945 */     for (byte b = 0; b < this.savingsInstantTransitions.length; b++) {
/*  946 */       arrayList.add(new ZoneOffsetTransition(this.savingsInstantTransitions[b], this.wallOffsets[b], this.wallOffsets[b + 1]));
/*      */     }
/*  948 */     return Collections.unmodifiableList(arrayList);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<ZoneOffsetTransitionRule> getTransitionRules() {
/*  973 */     return Collections.unmodifiableList(Arrays.asList(this.lastRules));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object paramObject) {
/*  990 */     if (this == paramObject) {
/*  991 */       return true;
/*      */     }
/*  993 */     if (paramObject instanceof ZoneRules) {
/*  994 */       ZoneRules zoneRules = (ZoneRules)paramObject;
/*  995 */       return (Arrays.equals(this.standardTransitions, zoneRules.standardTransitions) && 
/*  996 */         Arrays.equals((Object[])this.standardOffsets, (Object[])zoneRules.standardOffsets) && 
/*  997 */         Arrays.equals(this.savingsInstantTransitions, zoneRules.savingsInstantTransitions) && 
/*  998 */         Arrays.equals((Object[])this.wallOffsets, (Object[])zoneRules.wallOffsets) && 
/*  999 */         Arrays.equals((Object[])this.lastRules, (Object[])zoneRules.lastRules));
/*      */     } 
/* 1001 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1011 */     return Arrays.hashCode(this.standardTransitions) ^ 
/* 1012 */       Arrays.hashCode((Object[])this.standardOffsets) ^ 
/* 1013 */       Arrays.hashCode(this.savingsInstantTransitions) ^ 
/* 1014 */       Arrays.hashCode((Object[])this.wallOffsets) ^ 
/* 1015 */       Arrays.hashCode((Object[])this.lastRules);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1025 */     return "ZoneRules[currentStandardOffset=" + this.standardOffsets[this.standardOffsets.length - 1] + "]";
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/zone/ZoneRules.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */