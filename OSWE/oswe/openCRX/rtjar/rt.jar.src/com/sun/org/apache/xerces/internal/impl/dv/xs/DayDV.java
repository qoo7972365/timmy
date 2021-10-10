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
/*     */ public class DayDV
/*     */   extends AbstractDateTimeDV
/*     */ {
/*     */   private static final int DAY_SIZE = 5;
/*     */   
/*     */   public Object getActualValue(String content, ValidationContext context) throws InvalidDatatypeValueException {
/*     */     try {
/*  45 */       return parse(content);
/*  46 */     } catch (Exception ex) {
/*  47 */       throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[] { content, "gDay" });
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
/*     */   protected AbstractDateTimeDV.DateTimeData parse(String str) throws SchemaDateTimeException {
/*  61 */     AbstractDateTimeDV.DateTimeData date = new AbstractDateTimeDV.DateTimeData(str, this);
/*  62 */     int len = str.length();
/*     */     
/*  64 */     if (str.charAt(0) != '-' || str.charAt(1) != '-' || str.charAt(2) != '-') {
/*  65 */       throw new SchemaDateTimeException("Error in day parsing");
/*     */     }
/*     */ 
/*     */     
/*  69 */     date.year = 2000;
/*  70 */     date.month = 1;
/*     */     
/*  72 */     date.day = parseInt(str, 3, 5);
/*     */     
/*  74 */     if (5 < len) {
/*  75 */       if (!isNextCharUTCSign(str, 5, len)) {
/*  76 */         throw new SchemaDateTimeException("Error in day parsing");
/*     */       }
/*     */       
/*  79 */       getTimeZone(str, date, 5, len);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  84 */     validateDateTime(date);
/*     */ 
/*     */     
/*  87 */     saveUnnormalized(date);
/*     */     
/*  89 */     if (date.utc != 0 && date.utc != 90) {
/*  90 */       normalize(date);
/*     */     }
/*  92 */     date.position = 2;
/*  93 */     return date;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String dateToString(AbstractDateTimeDV.DateTimeData date) {
/* 103 */     StringBuffer message = new StringBuffer(6);
/* 104 */     message.append('-');
/* 105 */     message.append('-');
/* 106 */     message.append('-');
/* 107 */     append(message, date.day, 2);
/* 108 */     append(message, (char)date.utc, 0);
/* 109 */     return message.toString();
/*     */   }
/*     */   
/*     */   protected XMLGregorianCalendar getXMLGregorianCalendar(AbstractDateTimeDV.DateTimeData date) {
/* 113 */     return datatypeFactory.newXMLGregorianCalendar(-2147483648, -2147483648, date.unNormDay, -2147483648, -2147483648, -2147483648, -2147483648, 
/*     */ 
/*     */         
/* 116 */         date.hasTimeZone() ? (date.timezoneHr * 60 + date.timezoneMin) : Integer.MIN_VALUE);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/dv/xs/DayDV.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */