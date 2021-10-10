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
/*     */ public class YearDV
/*     */   extends AbstractDateTimeDV
/*     */ {
/*     */   public Object getActualValue(String content, ValidationContext context) throws InvalidDatatypeValueException {
/*     */     try {
/*  50 */       return parse(content);
/*  51 */     } catch (Exception ex) {
/*  52 */       throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[] { content, "gYear" });
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
/*  65 */     AbstractDateTimeDV.DateTimeData date = new AbstractDateTimeDV.DateTimeData(str, this);
/*  66 */     int len = str.length();
/*     */ 
/*     */     
/*  69 */     int start = 0;
/*  70 */     if (str.charAt(0) == '-') {
/*  71 */       start = 1;
/*     */     }
/*  73 */     int sign = findUTCSign(str, start, len);
/*     */     
/*  75 */     int length = ((sign == -1) ? len : sign) - start;
/*  76 */     if (length < 4) {
/*  77 */       throw new RuntimeException("Year must have 'CCYY' format");
/*     */     }
/*  79 */     if (length > 4 && str.charAt(start) == '0') {
/*  80 */       throw new RuntimeException("Leading zeros are required if the year value would otherwise have fewer than four digits; otherwise they are forbidden");
/*     */     }
/*     */     
/*  83 */     if (sign == -1) {
/*  84 */       date.year = parseIntYear(str, len);
/*     */     } else {
/*     */       
/*  87 */       date.year = parseIntYear(str, sign);
/*  88 */       getTimeZone(str, date, sign, len);
/*     */     } 
/*     */ 
/*     */     
/*  92 */     date.month = 1;
/*  93 */     date.day = 1;
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
/* 104 */     date.position = 0;
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
/* 115 */     StringBuffer message = new StringBuffer(5);
/* 116 */     append(message, date.year, 4);
/* 117 */     append(message, (char)date.utc, 0);
/* 118 */     return message.toString();
/*     */   }
/*     */   
/*     */   protected XMLGregorianCalendar getXMLGregorianCalendar(AbstractDateTimeDV.DateTimeData date) {
/* 122 */     return datatypeFactory.newXMLGregorianCalendar(date.unNormYear, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, 
/*     */ 
/*     */         
/* 125 */         date.hasTimeZone() ? (date.timezoneHr * 60 + date.timezoneMin) : Integer.MIN_VALUE);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/dv/xs/YearDV.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */