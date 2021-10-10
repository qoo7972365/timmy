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
/*     */ public class MonthDV
/*     */   extends AbstractDateTimeDV
/*     */ {
/*     */   public Object getActualValue(String content, ValidationContext context) throws InvalidDatatypeValueException {
/*     */     try {
/*  50 */       return parse(content);
/*  51 */     } catch (Exception ex) {
/*  52 */       throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[] { content, "gMonth" });
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
/*  69 */     date.year = 2000;
/*  70 */     date.day = 1;
/*  71 */     if (str.charAt(0) != '-' || str.charAt(1) != '-') {
/*  72 */       throw new SchemaDateTimeException("Invalid format for gMonth: " + str);
/*     */     }
/*  74 */     int stop = 4;
/*  75 */     date.month = parseInt(str, 2, stop);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  80 */     if (str.length() >= stop + 2 && str
/*  81 */       .charAt(stop) == '-' && str.charAt(stop + 1) == '-') {
/*  82 */       stop += 2;
/*     */     }
/*  84 */     if (stop < len) {
/*  85 */       if (!isNextCharUTCSign(str, stop, len)) {
/*  86 */         throw new SchemaDateTimeException("Error in month parsing: " + str);
/*     */       }
/*     */       
/*  89 */       getTimeZone(str, date, stop, len);
/*     */     } 
/*     */ 
/*     */     
/*  93 */     validateDateTime(date);
/*     */ 
/*     */     
/*  96 */     saveUnnormalized(date);
/*     */     
/*  98 */     if (date.utc != 0 && date.utc != 90) {
/*  99 */       normalize(date);
/*     */     }
/* 101 */     date.position = 1;
/* 102 */     return date;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String dateToString(AbstractDateTimeDV.DateTimeData date) {
/* 154 */     StringBuffer message = new StringBuffer(5);
/* 155 */     message.append('-');
/* 156 */     message.append('-');
/* 157 */     append(message, date.month, 2);
/* 158 */     append(message, (char)date.utc, 0);
/* 159 */     return message.toString();
/*     */   }
/*     */   
/*     */   protected XMLGregorianCalendar getXMLGregorianCalendar(AbstractDateTimeDV.DateTimeData date) {
/* 163 */     return datatypeFactory.newXMLGregorianCalendar(-2147483648, date.unNormMonth, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, 
/*     */ 
/*     */         
/* 166 */         date.hasTimeZone() ? (date.timezoneHr * 60 + date.timezoneMin) : Integer.MIN_VALUE);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/dv/xs/MonthDV.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */