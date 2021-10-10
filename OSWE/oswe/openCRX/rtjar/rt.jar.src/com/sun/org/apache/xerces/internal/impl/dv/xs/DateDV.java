/*    */ package com.sun.org.apache.xerces.internal.impl.dv.xs;
/*    */ 
/*    */ import com.sun.org.apache.xerces.internal.impl.dv.InvalidDatatypeValueException;
/*    */ import com.sun.org.apache.xerces.internal.impl.dv.ValidationContext;
/*    */ import javax.xml.datatype.XMLGregorianCalendar;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DateDV
/*    */   extends DateTimeDV
/*    */ {
/*    */   public Object getActualValue(String content, ValidationContext context) throws InvalidDatatypeValueException {
/*    */     try {
/* 43 */       return parse(content);
/* 44 */     } catch (Exception ex) {
/* 45 */       throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[] { content, "date" });
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected AbstractDateTimeDV.DateTimeData parse(String str) throws SchemaDateTimeException {
/* 58 */     AbstractDateTimeDV.DateTimeData date = new AbstractDateTimeDV.DateTimeData(str, this);
/* 59 */     int len = str.length();
/*    */     
/* 61 */     int end = getDate(str, 0, len, date);
/* 62 */     parseTimeZone(str, end, len, date);
/*    */ 
/*    */ 
/*    */     
/* 66 */     validateDateTime(date);
/*    */ 
/*    */     
/* 69 */     saveUnnormalized(date);
/*    */     
/* 71 */     if (date.utc != 0 && date.utc != 90) {
/* 72 */       normalize(date);
/*    */     }
/* 74 */     return date;
/*    */   }
/*    */   
/*    */   protected String dateToString(AbstractDateTimeDV.DateTimeData date) {
/* 78 */     StringBuffer message = new StringBuffer(25);
/* 79 */     append(message, date.year, 4);
/* 80 */     message.append('-');
/* 81 */     append(message, date.month, 2);
/* 82 */     message.append('-');
/* 83 */     append(message, date.day, 2);
/* 84 */     append(message, (char)date.utc, 0);
/* 85 */     return message.toString();
/*    */   }
/*    */   
/*    */   protected XMLGregorianCalendar getXMLGregorianCalendar(AbstractDateTimeDV.DateTimeData date) {
/* 89 */     return datatypeFactory.newXMLGregorianCalendar(date.unNormYear, date.unNormMonth, date.unNormDay, -2147483648, -2147483648, -2147483648, -2147483648, 
/*    */ 
/*    */         
/* 92 */         date.hasTimeZone() ? (date.timezoneHr * 60 + date.timezoneMin) : Integer.MIN_VALUE);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/dv/xs/DateDV.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */