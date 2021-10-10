/*     */ package com.sun.org.apache.xerces.internal.impl.dv.xs;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.impl.dv.InvalidDatatypeValueException;
/*     */ import com.sun.org.apache.xerces.internal.impl.dv.ValidationContext;
/*     */ import javax.xml.datatype.XMLGregorianCalendar;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MonthDayDV
/*     */   extends AbstractDateTimeDV
/*     */ {
/*     */   private static final int MONTHDAY_SIZE = 7;
/*     */   
/*     */   public Object getActualValue(String content, ValidationContext context) throws InvalidDatatypeValueException {
/*     */     try {
/*  53 */       return parse(content);
/*  54 */     } catch (Exception ex) {
/*  55 */       throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[] { content, "gMonthDay" });
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
/*     */   protected AbstractDateTimeDV.DateTimeData parse(String str) throws SchemaDateTimeException {
/*  68 */     AbstractDateTimeDV.DateTimeData date = new AbstractDateTimeDV.DateTimeData(str, this);
/*  69 */     int len = str.length();
/*     */ 
/*     */     
/*  72 */     date.year = 2000;
/*     */     
/*  74 */     if (str.charAt(0) != '-' || str.charAt(1) != '-') {
/*  75 */       throw new SchemaDateTimeException("Invalid format for gMonthDay: " + str);
/*     */     }
/*  77 */     date.month = parseInt(str, 2, 4);
/*  78 */     int start = 4;
/*     */     
/*  80 */     if (str.charAt(start++) != '-') {
/*  81 */       throw new SchemaDateTimeException("Invalid format for gMonthDay: " + str);
/*     */     }
/*     */     
/*  84 */     date.day = parseInt(str, start, start + 2);
/*     */     
/*  86 */     if (7 < len) {
/*  87 */       if (!isNextCharUTCSign(str, 7, len)) {
/*  88 */         throw new SchemaDateTimeException("Error in month parsing:" + str);
/*     */       }
/*     */       
/*  91 */       getTimeZone(str, date, 7, len);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  96 */     validateDateTime(date);
/*     */ 
/*     */     
/*  99 */     saveUnnormalized(date);
/*     */     
/* 101 */     if (date.utc != 0 && date.utc != 90) {
/* 102 */       normalize(date);
/*     */     }
/* 104 */     date.position = 1;
/* 105 */     return date;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String dateToString(AbstractDateTimeDV.DateTimeData date) {
/* 115 */     StringBuffer message = new StringBuffer(8);
/* 116 */     message.append('-');
/* 117 */     message.append('-');
/* 118 */     append(message, date.month, 2);
/* 119 */     message.append('-');
/* 120 */     append(message, date.day, 2);
/* 121 */     append(message, (char)date.utc, 0);
/* 122 */     return message.toString();
/*     */   }
/*     */   
/*     */   protected XMLGregorianCalendar getXMLGregorianCalendar(AbstractDateTimeDV.DateTimeData date) {
/* 126 */     return datatypeFactory.newXMLGregorianCalendar(-2147483648, date.unNormMonth, date.unNormDay, -2147483648, -2147483648, -2147483648, -2147483648, 
/*     */ 
/*     */         
/* 129 */         date.hasTimeZone() ? (date.timezoneHr * 60 + date.timezoneMin) : Integer.MIN_VALUE);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/dv/xs/MonthDayDV.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */