/*      */ package java.util;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.text.DateFormat;
/*      */ import java.time.Instant;
/*      */ import sun.util.calendar.BaseCalendar;
/*      */ import sun.util.calendar.CalendarSystem;
/*      */ import sun.util.calendar.CalendarUtils;
/*      */ import sun.util.calendar.ZoneInfo;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Date
/*      */   implements Serializable, Cloneable, Comparable<Date>
/*      */ {
/*  135 */   private static final BaseCalendar gcal = CalendarSystem.getGregorianCalendar();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static BaseCalendar jcal;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient long fastTime;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient BaseCalendar.Date cdate;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int defaultCenturyStart;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long serialVersionUID = 7523967970034938905L;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Date() {
/*  165 */     this(System.currentTimeMillis());
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
/*      */   public Date(long paramLong) {
/*  178 */     this.fastTime = paramLong;
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
/*      */   @Deprecated
/*      */   public Date(int paramInt1, int paramInt2, int paramInt3) {
/*  197 */     this(paramInt1, paramInt2, paramInt3, 0, 0, 0);
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
/*      */   @Deprecated
/*      */   public Date(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/*  220 */     this(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, 0);
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
/*      */   @Deprecated
/*      */   public Date(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/*  244 */     int i = paramInt1 + 1900;
/*      */     
/*  246 */     if (paramInt2 >= 12) {
/*  247 */       i += paramInt2 / 12;
/*  248 */       paramInt2 %= 12;
/*  249 */     } else if (paramInt2 < 0) {
/*  250 */       i += CalendarUtils.floorDivide(paramInt2, 12);
/*  251 */       paramInt2 = CalendarUtils.mod(paramInt2, 12);
/*      */     } 
/*  253 */     BaseCalendar baseCalendar = getCalendarSystem(i);
/*  254 */     this.cdate = (BaseCalendar.Date)baseCalendar.newCalendarDate(TimeZone.getDefaultRef());
/*  255 */     this.cdate.setNormalizedDate(i, paramInt2 + 1, paramInt3).setTimeOfDay(paramInt4, paramInt5, paramInt6, 0);
/*  256 */     getTimeImpl();
/*  257 */     this.cdate = null;
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
/*      */   @Deprecated
/*      */   public Date(String paramString) {
/*  274 */     this(parse(paramString));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object clone() {
/*  281 */     Date date = null;
/*      */     try {
/*  283 */       date = (Date)super.clone();
/*  284 */       if (this.cdate != null) {
/*  285 */         date.cdate = (BaseCalendar.Date)this.cdate.clone();
/*      */       }
/*  287 */     } catch (CloneNotSupportedException cloneNotSupportedException) {}
/*  288 */     return date;
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
/*      */   @Deprecated
/*      */   public static long UTC(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/*  319 */     int i = paramInt1 + 1900;
/*      */     
/*  321 */     if (paramInt2 >= 12) {
/*  322 */       i += paramInt2 / 12;
/*  323 */       paramInt2 %= 12;
/*  324 */     } else if (paramInt2 < 0) {
/*  325 */       i += CalendarUtils.floorDivide(paramInt2, 12);
/*  326 */       paramInt2 = CalendarUtils.mod(paramInt2, 12);
/*      */     } 
/*  328 */     int j = paramInt2 + 1;
/*  329 */     BaseCalendar baseCalendar = getCalendarSystem(i);
/*  330 */     BaseCalendar.Date date = (BaseCalendar.Date)baseCalendar.newCalendarDate(null);
/*  331 */     date.setNormalizedDate(i, j, paramInt3).setTimeOfDay(paramInt4, paramInt5, paramInt6, 0);
/*      */ 
/*      */ 
/*      */     
/*  335 */     Date date1 = new Date(0L);
/*  336 */     date1.normalize(date);
/*  337 */     return date1.fastTime;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static long parse(String paramString) {
/*  455 */     int i = Integer.MIN_VALUE;
/*  456 */     byte b1 = -1;
/*  457 */     byte b2 = -1;
/*  458 */     byte b3 = -1;
/*  459 */     byte b4 = -1;
/*  460 */     byte b5 = -1;
/*  461 */     byte b6 = -1;
/*  462 */     int j = -1;
/*  463 */     byte b = 0;
/*  464 */     int k = -1;
/*  465 */     byte b7 = -1;
/*  466 */     int m = -1;
/*  467 */     int n = 0;
/*      */ 
/*      */     
/*  470 */     if (paramString != null) {
/*      */       
/*  472 */       int i1 = paramString.length(); while (true) {
/*  473 */         int i2; if (b < i1)
/*  474 */         { j = paramString.charAt(b);
/*  475 */           b++;
/*  476 */           if (j <= 32 || j == 44)
/*      */             continue; 
/*  478 */           if (j == 40) {
/*  479 */             byte b8 = 1;
/*  480 */             while (b < i1) {
/*  481 */               j = paramString.charAt(b);
/*  482 */               b++;
/*  483 */               if (j == 40) { b8++; continue; }
/*  484 */                if (j == 41 && 
/*  485 */                 --b8 <= 0)
/*      */                 break; 
/*      */             } 
/*      */             continue;
/*      */           } 
/*  490 */           if (48 <= j && j <= 57) {
/*  491 */             k = j - 48;
/*  492 */             while (b < i1 && 48 <= (j = paramString.charAt(b)) && j <= 57) {
/*  493 */               k = k * 10 + j - 48;
/*  494 */               b++;
/*      */             } 
/*  496 */             if (n == 43 || (n == 45 && i != Integer.MIN_VALUE))
/*      */             
/*  498 */             { if (k < 24) {
/*  499 */                 k *= 60;
/*      */               } else {
/*  501 */                 k = k % 100 + k / 100 * 60;
/*  502 */               }  if (n == 43)
/*  503 */                 k = -k; 
/*  504 */               if (m != 0 && m != -1)
/*      */                 // Byte code: goto -> 1000 
/*  506 */               m = k; }
/*  507 */             else if (k >= 70)
/*  508 */             { if (i != Integer.MIN_VALUE)
/*      */                 // Byte code: goto -> 1000 
/*  510 */               if (j <= 32 || j == 44 || j == 47 || b >= i1)
/*      */               
/*  512 */               { i = k; }
/*      */               else { // Byte code: goto -> 1000 }
/*      */                }
/*  515 */             else if (j == 58)
/*  516 */             { if (b3 < 0)
/*  517 */               { b3 = (byte)k; }
/*  518 */               else if (b4 < 0)
/*  519 */               { b4 = (byte)k; }
/*      */               else { // Byte code: goto -> 1000 }
/*      */                }
/*  522 */             else if (j == 47)
/*  523 */             { if (b1 < 0)
/*  524 */               { b1 = (byte)(k - 1); }
/*  525 */               else if (b2 < 0)
/*  526 */               { b2 = (byte)k; }
/*      */               else { // Byte code: goto -> 1000 }
/*      */                }
/*  529 */             else { if (b < i1 && j != 44 && j > 32 && j != 45)
/*      */                 // Byte code: goto -> 1000 
/*  531 */               if (b3 >= 0 && b4 < 0)
/*  532 */               { b4 = (byte)k; }
/*  533 */               else if (b4 >= 0 && b5 < 0)
/*  534 */               { b5 = (byte)k; }
/*  535 */               else if (b2 < 0)
/*  536 */               { b2 = (byte)k; }
/*      */               
/*  538 */               else if (i == Integer.MIN_VALUE) { if (b1 >= 0) { if (b2 >= 0)
/*  539 */                   { i = k; } else { // Byte code: goto -> 1000 }  } else { // Byte code: goto -> 1000 }  }
/*      */               else { // Byte code: goto -> 1000 }
/*      */                }
/*  542 */              n = 0; continue;
/*  543 */           }  if (j == 47 || j == 58 || j == 43 || j == 45) {
/*  544 */             n = j; continue;
/*      */           } 
/*  546 */           int i3 = b - 1;
/*  547 */           while (b < i1) {
/*  548 */             j = paramString.charAt(b);
/*  549 */             if ((65 > j || j > 90) && (97 > j || j > 122))
/*      */               break; 
/*  551 */             b++;
/*      */           } 
/*  553 */           if (b <= i3 + 1) {
/*      */             // Byte code: goto -> 1000
/*      */           }
/*  556 */           i2 = wtb.length; while (true) { if (--i2 >= 0)
/*  557 */             { if (wtb[i2].regionMatches(true, 0, paramString, i3, b - i3))
/*  558 */               { int i4 = ttb[i2];
/*  559 */                 if (i4 != 0)
/*  560 */                 { if (i4 == 1) {
/*  561 */                     if (b3 <= 12) { if (b3 < 1)
/*      */                         // Byte code: goto -> 1000 
/*  563 */                       if (b3 < 12)
/*  564 */                         b3 += 12;  break; }  // Byte code: goto -> 1000
/*  565 */                   }  if (i4 == 14)
/*  566 */                   { if (b3 <= 12) { if (b3 < 1)
/*      */                         // Byte code: goto -> 1000 
/*  568 */                       if (b3 == 12)
/*  569 */                         b3 = 0;  } else { // Byte code: goto -> 1000 }  }
/*  570 */                   else if (i4 <= 13)
/*  571 */                   { if (b1 < 0)
/*  572 */                     { b1 = (byte)(i4 - 2); }
/*      */                     else { // Byte code: goto -> 1000 }
/*      */                      }
/*      */                   else
/*  576 */                   { m = i4 - 10000; break; }  } else { break; }
/*      */                  }
/*      */               else { continue; }
/*      */                }
/*      */             else { break; }
/*  581 */              if (i2 < 0)
/*      */               // Byte code: goto -> 1000 
/*  583 */             n = 0; }  } else { break; }  if (i2 < 0) // Byte code: goto -> 1000  n = 0;
/*      */       } 
/*      */       
/*  586 */       if (i != Integer.MIN_VALUE && b1 >= 0 && b2 >= 0) {
/*      */ 
/*      */         
/*  589 */         if (i < 100) {
/*  590 */           synchronized (Date.class) {
/*  591 */             if (defaultCenturyStart == 0) {
/*  592 */               defaultCenturyStart = gcal.getCalendarDate().getYear() - 80;
/*      */             }
/*      */           } 
/*  595 */           i += defaultCenturyStart / 100 * 100;
/*  596 */           if (i < defaultCenturyStart) i += 100; 
/*      */         } 
/*  598 */         if (b5 < 0)
/*  599 */           b5 = 0; 
/*  600 */         if (b4 < 0)
/*  601 */           b4 = 0; 
/*  602 */         if (b3 < 0)
/*  603 */           b3 = 0; 
/*  604 */         BaseCalendar baseCalendar = getCalendarSystem(i);
/*  605 */         if (m == -1) {
/*  606 */           BaseCalendar.Date date1 = (BaseCalendar.Date)baseCalendar.newCalendarDate(TimeZone.getDefaultRef());
/*  607 */           date1.setDate(i, b1 + 1, b2);
/*  608 */           date1.setTimeOfDay(b3, b4, b5, 0);
/*  609 */           return baseCalendar.getTime(date1);
/*      */         } 
/*  611 */         BaseCalendar.Date date = (BaseCalendar.Date)baseCalendar.newCalendarDate(null);
/*  612 */         date.setDate(i, b1 + 1, b2);
/*  613 */         date.setTimeOfDay(b3, b4, b5, 0);
/*  614 */         return baseCalendar.getTime(date) + (m * 60000);
/*      */       } 
/*      */     } 
/*  617 */     throw new IllegalArgumentException();
/*      */   }
/*  619 */   private static final String[] wtb = new String[] { "am", "pm", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday", "january", "february", "march", "april", "may", "june", "july", "august", "september", "october", "november", "december", "gmt", "ut", "utc", "est", "edt", "cst", "cdt", "mst", "mdt", "pst", "pdt" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  628 */   private static final int[] ttb = new int[] { 14, 1, 0, 0, 0, 0, 0, 0, 0, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 10000, 10000, 10000, 10300, 10240, 10360, 10300, 10420, 10360, 10480, 10420 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public int getYear() {
/*  651 */     return normalize().getYear() - 1900;
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
/*      */   @Deprecated
/*      */   public void setYear(int paramInt) {
/*  671 */     getCalendarDate().setNormalizedYear(paramInt + 1900);
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
/*      */   @Deprecated
/*      */   public int getMonth() {
/*  687 */     return normalize().getMonth() - 1;
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
/*      */   @Deprecated
/*      */   public void setMonth(int paramInt) {
/*  706 */     int i = 0;
/*  707 */     if (paramInt >= 12) {
/*  708 */       i = paramInt / 12;
/*  709 */       paramInt %= 12;
/*  710 */     } else if (paramInt < 0) {
/*  711 */       i = CalendarUtils.floorDivide(paramInt, 12);
/*  712 */       paramInt = CalendarUtils.mod(paramInt, 12);
/*      */     } 
/*  714 */     BaseCalendar.Date date = getCalendarDate();
/*  715 */     if (i != 0) {
/*  716 */       date.setNormalizedYear(date.getNormalizedYear() + i);
/*      */     }
/*  718 */     date.setMonth(paramInt + 1);
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
/*      */   @Deprecated
/*      */   public int getDate() {
/*  736 */     return normalize().getDayOfMonth();
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
/*      */   @Deprecated
/*      */   public void setDate(int paramInt) {
/*  756 */     getCalendarDate().setDayOfMonth(paramInt);
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
/*      */   @Deprecated
/*      */   public int getDay() {
/*  775 */     return normalize().getDayOfWeek() - 1;
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
/*      */   @Deprecated
/*      */   public int getHours() {
/*  792 */     return normalize().getHours();
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
/*      */   @Deprecated
/*      */   public void setHours(int paramInt) {
/*  809 */     getCalendarDate().setHours(paramInt);
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
/*      */   @Deprecated
/*      */   public int getMinutes() {
/*  824 */     return normalize().getMinutes();
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
/*      */   @Deprecated
/*      */   public void setMinutes(int paramInt) {
/*  841 */     getCalendarDate().setMinutes(paramInt);
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
/*      */   @Deprecated
/*      */   public int getSeconds() {
/*  857 */     return normalize().getSeconds();
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
/*      */   @Deprecated
/*      */   public void setSeconds(int paramInt) {
/*  874 */     getCalendarDate().setSeconds(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getTime() {
/*  885 */     return getTimeImpl();
/*      */   }
/*      */   
/*      */   private final long getTimeImpl() {
/*  889 */     if (this.cdate != null && !this.cdate.isNormalized()) {
/*  890 */       normalize();
/*      */     }
/*  892 */     return this.fastTime;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTime(long paramLong) {
/*  902 */     this.fastTime = paramLong;
/*  903 */     this.cdate = null;
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
/*      */   public boolean before(Date paramDate) {
/*  917 */     return (getMillisOf(this) < getMillisOf(paramDate));
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
/*      */   public boolean after(Date paramDate) {
/*  931 */     return (getMillisOf(this) > getMillisOf(paramDate));
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
/*      */   public boolean equals(Object paramObject) {
/*  950 */     return (paramObject instanceof Date && getTime() == ((Date)paramObject).getTime());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final long getMillisOf(Date paramDate) {
/*  958 */     if (paramDate.cdate == null || paramDate.cdate.isNormalized()) {
/*  959 */       return paramDate.fastTime;
/*      */     }
/*  961 */     BaseCalendar.Date date = (BaseCalendar.Date)paramDate.cdate.clone();
/*  962 */     return gcal.getTime(date);
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
/*      */   public int compareTo(Date paramDate) {
/*  977 */     long l1 = getMillisOf(this);
/*  978 */     long l2 = getMillisOf(paramDate);
/*  979 */     return (l1 < l2) ? -1 : ((l1 == l2) ? 0 : 1);
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
/*      */   public int hashCode() {
/*  994 */     long l = getTime();
/*  995 */     return (int)l ^ (int)(l >> 32L);
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
/*      */   public String toString() {
/* 1030 */     BaseCalendar.Date date = normalize();
/* 1031 */     StringBuilder stringBuilder = new StringBuilder(28);
/* 1032 */     int i = date.getDayOfWeek();
/* 1033 */     if (i == 1) {
/* 1034 */       i = 8;
/*      */     }
/* 1036 */     convertToAbbr(stringBuilder, wtb[i]).append(' ');
/* 1037 */     convertToAbbr(stringBuilder, wtb[date.getMonth() - 1 + 2 + 7]).append(' ');
/* 1038 */     CalendarUtils.sprintf0d(stringBuilder, date.getDayOfMonth(), 2).append(' ');
/*      */     
/* 1040 */     CalendarUtils.sprintf0d(stringBuilder, date.getHours(), 2).append(':');
/* 1041 */     CalendarUtils.sprintf0d(stringBuilder, date.getMinutes(), 2).append(':');
/* 1042 */     CalendarUtils.sprintf0d(stringBuilder, date.getSeconds(), 2).append(' ');
/* 1043 */     TimeZone timeZone = date.getZone();
/* 1044 */     if (timeZone != null) {
/* 1045 */       stringBuilder.append(timeZone.getDisplayName(date.isDaylightTime(), 0, Locale.US));
/*      */     } else {
/* 1047 */       stringBuilder.append("GMT");
/*      */     } 
/* 1049 */     stringBuilder.append(' ').append(date.getYear());
/* 1050 */     return stringBuilder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final StringBuilder convertToAbbr(StringBuilder paramStringBuilder, String paramString) {
/* 1059 */     paramStringBuilder.append(Character.toUpperCase(paramString.charAt(0)));
/* 1060 */     paramStringBuilder.append(paramString.charAt(1)).append(paramString.charAt(2));
/* 1061 */     return paramStringBuilder;
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
/*      */   @Deprecated
/*      */   public String toLocaleString() {
/* 1082 */     DateFormat dateFormat = DateFormat.getDateTimeInstance();
/* 1083 */     return dateFormat.format(this);
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
/*      */   @Deprecated
/*      */   public String toGMTString() {
/* 1120 */     long l = getTime();
/* 1121 */     BaseCalendar baseCalendar = getCalendarSystem(l);
/*      */     
/* 1123 */     BaseCalendar.Date date = (BaseCalendar.Date)baseCalendar.getCalendarDate(getTime(), (TimeZone)null);
/* 1124 */     StringBuilder stringBuilder = new StringBuilder(32);
/* 1125 */     CalendarUtils.sprintf0d(stringBuilder, date.getDayOfMonth(), 1).append(' ');
/* 1126 */     convertToAbbr(stringBuilder, wtb[date.getMonth() - 1 + 2 + 7]).append(' ');
/* 1127 */     stringBuilder.append(date.getYear()).append(' ');
/* 1128 */     CalendarUtils.sprintf0d(stringBuilder, date.getHours(), 2).append(':');
/* 1129 */     CalendarUtils.sprintf0d(stringBuilder, date.getMinutes(), 2).append(':');
/* 1130 */     CalendarUtils.sprintf0d(stringBuilder, date.getSeconds(), 2);
/* 1131 */     stringBuilder.append(" GMT");
/* 1132 */     return stringBuilder.toString();
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
/*      */   @Deprecated
/*      */   public int getTimezoneOffset() {
/*      */     int i;
/* 1170 */     if (this.cdate == null) {
/* 1171 */       TimeZone timeZone = TimeZone.getDefaultRef();
/* 1172 */       if (timeZone instanceof ZoneInfo) {
/* 1173 */         i = ((ZoneInfo)timeZone).getOffsets(this.fastTime, null);
/*      */       } else {
/* 1175 */         i = timeZone.getOffset(this.fastTime);
/*      */       } 
/*      */     } else {
/* 1178 */       normalize();
/* 1179 */       i = this.cdate.getZoneOffset();
/*      */     } 
/* 1181 */     return -i / 60000;
/*      */   }
/*      */   
/*      */   private final BaseCalendar.Date getCalendarDate() {
/* 1185 */     if (this.cdate == null) {
/* 1186 */       BaseCalendar baseCalendar = getCalendarSystem(this.fastTime);
/* 1187 */       this.cdate = (BaseCalendar.Date)baseCalendar.getCalendarDate(this.fastTime, 
/* 1188 */           TimeZone.getDefaultRef());
/*      */     } 
/* 1190 */     return this.cdate;
/*      */   }
/*      */   
/*      */   private final BaseCalendar.Date normalize() {
/* 1194 */     if (this.cdate == null) {
/* 1195 */       BaseCalendar baseCalendar = getCalendarSystem(this.fastTime);
/* 1196 */       this.cdate = (BaseCalendar.Date)baseCalendar.getCalendarDate(this.fastTime, 
/* 1197 */           TimeZone.getDefaultRef());
/* 1198 */       return this.cdate;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1203 */     if (!this.cdate.isNormalized()) {
/* 1204 */       this.cdate = normalize(this.cdate);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1209 */     TimeZone timeZone = TimeZone.getDefaultRef();
/* 1210 */     if (timeZone != this.cdate.getZone()) {
/* 1211 */       this.cdate.setZone(timeZone);
/* 1212 */       BaseCalendar baseCalendar = getCalendarSystem(this.cdate);
/* 1213 */       baseCalendar.getCalendarDate(this.fastTime, this.cdate);
/*      */     } 
/* 1215 */     return this.cdate;
/*      */   }
/*      */ 
/*      */   
/*      */   private final BaseCalendar.Date normalize(BaseCalendar.Date paramDate) {
/* 1220 */     int i = paramDate.getNormalizedYear();
/* 1221 */     int j = paramDate.getMonth();
/* 1222 */     int k = paramDate.getDayOfMonth();
/* 1223 */     int m = paramDate.getHours();
/* 1224 */     int n = paramDate.getMinutes();
/* 1225 */     int i1 = paramDate.getSeconds();
/* 1226 */     int i2 = paramDate.getMillis();
/* 1227 */     TimeZone timeZone = paramDate.getZone();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1237 */     if (i == 1582 || i > 280000000 || i < -280000000) {
/* 1238 */       if (timeZone == null) {
/* 1239 */         timeZone = TimeZone.getTimeZone("GMT");
/*      */       }
/* 1241 */       GregorianCalendar gregorianCalendar = new GregorianCalendar(timeZone);
/* 1242 */       gregorianCalendar.clear();
/* 1243 */       gregorianCalendar.set(14, i2);
/* 1244 */       gregorianCalendar.set(i, j - 1, k, m, n, i1);
/* 1245 */       this.fastTime = gregorianCalendar.getTimeInMillis();
/* 1246 */       BaseCalendar baseCalendar = getCalendarSystem(this.fastTime);
/* 1247 */       paramDate = (BaseCalendar.Date)baseCalendar.getCalendarDate(this.fastTime, timeZone);
/* 1248 */       return paramDate;
/*      */     } 
/*      */     
/* 1251 */     BaseCalendar baseCalendar1 = getCalendarSystem(i);
/* 1252 */     if (baseCalendar1 != getCalendarSystem(paramDate)) {
/* 1253 */       paramDate = (BaseCalendar.Date)baseCalendar1.newCalendarDate(timeZone);
/* 1254 */       paramDate.setNormalizedDate(i, j, k).setTimeOfDay(m, n, i1, i2);
/*      */     } 
/*      */     
/* 1257 */     this.fastTime = baseCalendar1.getTime(paramDate);
/*      */ 
/*      */ 
/*      */     
/* 1261 */     BaseCalendar baseCalendar2 = getCalendarSystem(this.fastTime);
/* 1262 */     if (baseCalendar2 != baseCalendar1) {
/* 1263 */       paramDate = (BaseCalendar.Date)baseCalendar2.newCalendarDate(timeZone);
/* 1264 */       paramDate.setNormalizedDate(i, j, k).setTimeOfDay(m, n, i1, i2);
/* 1265 */       this.fastTime = baseCalendar2.getTime(paramDate);
/*      */     } 
/* 1267 */     return paramDate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final BaseCalendar getCalendarSystem(int paramInt) {
/* 1278 */     if (paramInt >= 1582) {
/* 1279 */       return gcal;
/*      */     }
/* 1281 */     return getJulianCalendar();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final BaseCalendar getCalendarSystem(long paramLong) {
/* 1288 */     if (paramLong >= 0L || paramLong >= -12219292800000L - 
/*      */       
/* 1290 */       TimeZone.getDefaultRef().getOffset(paramLong)) {
/* 1291 */       return gcal;
/*      */     }
/* 1293 */     return getJulianCalendar();
/*      */   }
/*      */   
/*      */   private static final BaseCalendar getCalendarSystem(BaseCalendar.Date paramDate) {
/* 1297 */     if (jcal == null) {
/* 1298 */       return gcal;
/*      */     }
/* 1300 */     if (paramDate.getEra() != null) {
/* 1301 */       return jcal;
/*      */     }
/* 1303 */     return gcal;
/*      */   }
/*      */   
/*      */   private static final synchronized BaseCalendar getJulianCalendar() {
/* 1307 */     if (jcal == null) {
/* 1308 */       jcal = (BaseCalendar)CalendarSystem.forName("julian");
/*      */     }
/* 1310 */     return jcal;
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
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 1323 */     paramObjectOutputStream.writeLong(getTimeImpl());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 1332 */     this.fastTime = paramObjectInputStream.readLong();
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
/*      */   public static Date from(Instant paramInstant) {
/*      */     try {
/* 1357 */       return new Date(paramInstant.toEpochMilli());
/* 1358 */     } catch (ArithmeticException arithmeticException) {
/* 1359 */       throw new IllegalArgumentException(arithmeticException);
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
/*      */ 
/*      */ 
/*      */   
/*      */   public Instant toInstant() {
/* 1374 */     return Instant.ofEpochMilli(getTime());
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/Date.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */