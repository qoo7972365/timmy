/*     */ package com.sun.org.apache.xerces.internal.impl.dv.xs;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.impl.dv.InvalidDatatypeValueException;
/*     */ import com.sun.org.apache.xerces.internal.impl.dv.ValidationContext;
/*     */ import java.math.BigInteger;
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
/*     */ public class TimeDV
/*     */   extends AbstractDateTimeDV
/*     */ {
/*     */   public Object getActualValue(String content, ValidationContext context) throws InvalidDatatypeValueException {
/*     */     try {
/*  49 */       return parse(content);
/*  50 */     } catch (Exception ex) {
/*  51 */       throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[] { content, "time" });
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
/*  65 */     AbstractDateTimeDV.DateTimeData date = new AbstractDateTimeDV.DateTimeData(str, this);
/*  66 */     int len = str.length();
/*     */ 
/*     */ 
/*     */     
/*  70 */     date.year = 2000;
/*  71 */     date.month = 1;
/*  72 */     date.day = 15;
/*  73 */     getTime(str, 0, len, date);
/*     */ 
/*     */ 
/*     */     
/*  77 */     validateDateTime(date);
/*     */ 
/*     */     
/*  80 */     saveUnnormalized(date);
/*     */     
/*  82 */     if (date.utc != 0 && date.utc != 90) {
/*  83 */       normalize(date);
/*     */     }
/*  85 */     date.position = 2;
/*  86 */     return date;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String dateToString(AbstractDateTimeDV.DateTimeData date) {
/*  96 */     StringBuffer message = new StringBuffer(16);
/*  97 */     append(message, date.hour, 2);
/*  98 */     message.append(':');
/*  99 */     append(message, date.minute, 2);
/* 100 */     message.append(':');
/* 101 */     append(message, date.second);
/*     */     
/* 103 */     append(message, (char)date.utc, 0);
/* 104 */     return message.toString();
/*     */   }
/*     */   
/*     */   protected XMLGregorianCalendar getXMLGregorianCalendar(AbstractDateTimeDV.DateTimeData date) {
/* 108 */     return datatypeFactory.newXMLGregorianCalendar((BigInteger)null, -2147483648, -2147483648, date.unNormHour, date.unNormMinute, (int)date.unNormSecond, (date.unNormSecond != 0.0D) ? 
/*     */         
/* 110 */         getFractionalSecondsAsBigDecimal(date) : null, 
/* 111 */         date.hasTimeZone() ? (date.timezoneHr * 60 + date.timezoneMin) : Integer.MIN_VALUE);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/dv/xs/TimeDV.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */