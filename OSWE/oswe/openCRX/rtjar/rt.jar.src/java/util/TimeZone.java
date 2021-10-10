/*     */ package java.util;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.time.ZoneId;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ import sun.util.calendar.ZoneInfo;
/*     */ import sun.util.calendar.ZoneInfoFile;
/*     */ import sun.util.locale.provider.TimeZoneNameUtility;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class TimeZone
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   public static final int SHORT = 0;
/*     */   public static final int LONG = 1;
/*     */   private static final int ONE_MINUTE = 60000;
/*     */   private static final int ONE_HOUR = 3600000;
/*     */   private static final int ONE_DAY = 86400000;
/*     */   static final long serialVersionUID = 3581463369166924961L;
/*     */   
/*     */   public abstract int getOffset(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
/*     */   
/*     */   public int getOffset(long paramLong) {
/* 209 */     if (inDaylightTime(new Date(paramLong))) {
/* 210 */       return getRawOffset() + getDSTSavings();
/*     */     }
/* 212 */     return getRawOffset();
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
/*     */   int getOffsets(long paramLong, int[] paramArrayOfint) {
/* 232 */     int i = getRawOffset();
/* 233 */     int j = 0;
/* 234 */     if (inDaylightTime(new Date(paramLong))) {
/* 235 */       j = getDSTSavings();
/*     */     }
/* 237 */     if (paramArrayOfint != null) {
/* 238 */       paramArrayOfint[0] = i;
/* 239 */       paramArrayOfint[1] = j;
/*     */     } 
/* 241 */     return i + j;
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
/*     */   public abstract void setRawOffset(int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int getRawOffset();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getID() {
/* 282 */     return this.ID;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setID(String paramString) {
/* 292 */     if (paramString == null) {
/* 293 */       throw new NullPointerException();
/*     */     }
/* 295 */     this.ID = paramString;
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
/*     */   public final String getDisplayName() {
/* 315 */     return getDisplayName(false, 1, 
/* 316 */         Locale.getDefault(Locale.Category.DISPLAY));
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
/*     */   public final String getDisplayName(Locale paramLocale) {
/* 335 */     return getDisplayName(false, 1, paramLocale);
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
/*     */   public final String getDisplayName(boolean paramBoolean, int paramInt) {
/* 363 */     return getDisplayName(paramBoolean, paramInt, 
/* 364 */         Locale.getDefault(Locale.Category.DISPLAY));
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
/*     */   public String getDisplayName(boolean paramBoolean, int paramInt, Locale paramLocale) {
/* 396 */     if (paramInt != 0 && paramInt != 1) {
/* 397 */       throw new IllegalArgumentException("Illegal style: " + paramInt);
/*     */     }
/* 399 */     String str1 = getID();
/* 400 */     String str2 = TimeZoneNameUtility.retrieveDisplayName(str1, paramBoolean, paramInt, paramLocale);
/* 401 */     if (str2 != null) {
/* 402 */       return str2;
/*     */     }
/*     */     
/* 405 */     if (str1.startsWith("GMT") && str1.length() > 3) {
/* 406 */       char c = str1.charAt(3);
/* 407 */       if (c == '+' || c == '-') {
/* 408 */         return str1;
/*     */       }
/*     */     } 
/* 411 */     int i = getRawOffset();
/* 412 */     if (paramBoolean) {
/* 413 */       i += getDSTSavings();
/*     */     }
/* 415 */     return ZoneInfoFile.toCustomID(i);
/*     */   }
/*     */   
/*     */   private static String[] getDisplayNames(String paramString, Locale paramLocale) {
/* 419 */     return TimeZoneNameUtility.retrieveDisplayNames(paramString, paramLocale);
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
/*     */   public int getDSTSavings() {
/* 449 */     if (useDaylightTime()) {
/* 450 */       return 3600000;
/*     */     }
/* 452 */     return 0;
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
/*     */   public abstract boolean useDaylightTime();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean observesDaylightTime() {
/* 491 */     return (useDaylightTime() || inDaylightTime(new Date()));
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
/*     */   public abstract boolean inDaylightTime(Date paramDate);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized TimeZone getTimeZone(String paramString) {
/* 516 */     return getTimeZone(paramString, true);
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
/*     */   public static TimeZone getTimeZone(ZoneId paramZoneId) {
/* 529 */     String str = paramZoneId.getId();
/* 530 */     char c = str.charAt(0);
/* 531 */     if (c == '+' || c == '-') {
/* 532 */       str = "GMT" + str;
/* 533 */     } else if (c == 'Z' && str.length() == 1) {
/* 534 */       str = "UTC";
/*     */     } 
/* 536 */     return getTimeZone(str, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZoneId toZoneId() {
/* 547 */     String str = getID();
/* 548 */     if (ZoneInfoFile.useOldMapping() && str.length() == 3) {
/* 549 */       if ("EST".equals(str))
/* 550 */         return ZoneId.of("America/New_York"); 
/* 551 */       if ("MST".equals(str))
/* 552 */         return ZoneId.of("America/Denver"); 
/* 553 */       if ("HST".equals(str))
/* 554 */         return ZoneId.of("America/Honolulu"); 
/*     */     } 
/* 556 */     return ZoneId.of(str, ZoneId.SHORT_IDS);
/*     */   }
/*     */   
/*     */   private static TimeZone getTimeZone(String paramString, boolean paramBoolean) {
/* 560 */     TimeZone timeZone = ZoneInfo.getTimeZone(paramString);
/* 561 */     if (timeZone == null) {
/* 562 */       timeZone = parseCustomTimeZone(paramString);
/* 563 */       if (timeZone == null && paramBoolean) {
/* 564 */         timeZone = new ZoneInfo("GMT", 0);
/*     */       }
/*     */     } 
/* 567 */     return timeZone;
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
/*     */   public static synchronized String[] getAvailableIDs(int paramInt) {
/* 580 */     return ZoneInfo.getAvailableIDs(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized String[] getAvailableIDs() {
/* 588 */     return ZoneInfo.getAvailableIDs();
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
/*     */   private static native String getSystemTimeZoneID(String paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static native String getSystemGMTOffsetID();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TimeZone getDefault() {
/* 625 */     return (TimeZone)getDefaultRef().clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static TimeZone getDefaultRef() {
/* 633 */     TimeZone timeZone = defaultTimeZone;
/* 634 */     if (timeZone == null) {
/*     */       
/* 636 */       timeZone = setDefaultZone();
/* 637 */       assert timeZone != null;
/*     */     } 
/*     */     
/* 640 */     return timeZone;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static synchronized TimeZone setDefaultZone() {
/* 646 */     String str1 = AccessController.<String>doPrivileged(new GetPropertyAction("user.timezone"));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 651 */     if (str1 == null || str1.isEmpty()) {
/* 652 */       String str = AccessController.<String>doPrivileged(new GetPropertyAction("java.home"));
/*     */       
/*     */       try {
/* 655 */         str1 = getSystemTimeZoneID(str);
/* 656 */         if (str1 == null) {
/* 657 */           str1 = "GMT";
/*     */         }
/* 659 */       } catch (NullPointerException nullPointerException) {
/* 660 */         str1 = "GMT";
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 666 */     TimeZone timeZone = getTimeZone(str1, false);
/*     */     
/* 668 */     if (timeZone == null) {
/*     */ 
/*     */ 
/*     */       
/* 672 */       String str = getSystemGMTOffsetID();
/* 673 */       if (str != null) {
/* 674 */         str1 = str;
/*     */       }
/* 676 */       timeZone = getTimeZone(str1, true);
/*     */     } 
/* 678 */     assert timeZone != null;
/*     */     
/* 680 */     final String id = str1;
/* 681 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */         {
/*     */           public Void run() {
/* 684 */             System.setProperty("user.timezone", id);
/* 685 */             return null;
/*     */           }
/*     */         });
/*     */     
/* 689 */     defaultTimeZone = timeZone;
/* 690 */     return timeZone;
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
/*     */   public static void setDefault(TimeZone paramTimeZone) {
/* 708 */     SecurityManager securityManager = System.getSecurityManager();
/* 709 */     if (securityManager != null) {
/* 710 */       securityManager.checkPermission(new PropertyPermission("user.timezone", "write"));
/*     */     }
/*     */     
/* 713 */     defaultTimeZone = paramTimeZone;
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
/*     */   public boolean hasSameRules(TimeZone paramTimeZone) {
/* 726 */     return (paramTimeZone != null && getRawOffset() == paramTimeZone.getRawOffset() && 
/* 727 */       useDaylightTime() == paramTimeZone.useDaylightTime());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/*     */     try {
/* 738 */       TimeZone timeZone = (TimeZone)super.clone();
/* 739 */       timeZone.ID = this.ID;
/* 740 */       return timeZone;
/* 741 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/* 742 */       throw new InternalError(cloneNotSupportedException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 749 */   static final TimeZone NO_TIMEZONE = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String ID;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static volatile TimeZone defaultTimeZone;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final String GMT_ID = "GMT";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int GMT_ID_LENGTH = 3;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static volatile TimeZone mainAppContextDefault;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final TimeZone parseCustomTimeZone(String paramString) {
/*     */     int i;
/* 783 */     if ((i = paramString.length()) < 5 || paramString
/* 784 */       .indexOf("GMT") != 0) {
/* 785 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 793 */     ZoneInfo zoneInfo = ZoneInfoFile.getZoneInfo(paramString);
/* 794 */     if (zoneInfo != null) {
/* 795 */       return zoneInfo;
/*     */     }
/*     */     
/* 798 */     byte b1 = 3;
/* 799 */     boolean bool = false;
/* 800 */     char c = paramString.charAt(b1++);
/* 801 */     if (c == '-') {
/* 802 */       bool = true;
/* 803 */     } else if (c != '+') {
/* 804 */       return null;
/*     */     } 
/*     */     
/* 807 */     int j = 0;
/* 808 */     int k = 0;
/* 809 */     byte b2 = 0;
/* 810 */     byte b3 = 0;
/* 811 */     while (b1 < i) {
/* 812 */       c = paramString.charAt(b1++);
/* 813 */       if (c == ':') {
/* 814 */         if (b2) {
/* 815 */           return null;
/*     */         }
/* 817 */         if (b3 > 2) {
/* 818 */           return null;
/*     */         }
/* 820 */         j = k;
/* 821 */         b2++;
/* 822 */         k = 0;
/* 823 */         b3 = 0;
/*     */         continue;
/*     */       } 
/* 826 */       if (c < '0' || c > '9') {
/* 827 */         return null;
/*     */       }
/* 829 */       k = k * 10 + c - 48;
/* 830 */       b3++;
/*     */     } 
/* 832 */     if (b1 != i) {
/* 833 */       return null;
/*     */     }
/* 835 */     if (b2 == 0) {
/* 836 */       if (b3 <= 2) {
/* 837 */         j = k;
/* 838 */         k = 0;
/*     */       } else {
/* 840 */         j = k / 100;
/* 841 */         k %= 100;
/*     */       }
/*     */     
/* 844 */     } else if (b3 != 2) {
/* 845 */       return null;
/*     */     } 
/*     */     
/* 848 */     if (j > 23 || k > 59) {
/* 849 */       return null;
/*     */     }
/* 851 */     int m = (j * 60 + k) * 60 * 1000;
/*     */     
/* 853 */     if (m == 0) {
/* 854 */       zoneInfo = ZoneInfoFile.getZoneInfo("GMT");
/* 855 */       if (bool) {
/* 856 */         zoneInfo.setID("GMT-00:00");
/*     */       } else {
/* 858 */         zoneInfo.setID("GMT+00:00");
/*     */       } 
/*     */     } else {
/* 861 */       zoneInfo = ZoneInfoFile.getCustomTimeZone(paramString, bool ? -m : m);
/*     */     } 
/* 863 */     return zoneInfo;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/TimeZone.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */