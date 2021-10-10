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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class YearMonthDV
/*    */   extends AbstractDateTimeDV
/*    */ {
/*    */   public Object getActualValue(String content, ValidationContext context) throws InvalidDatatypeValueException {
/*    */     try {
/* 49 */       return parse(content);
/* 50 */     } catch (Exception ex) {
/* 51 */       throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[] { content, "gYearMonth" });
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
/* 64 */     AbstractDateTimeDV.DateTimeData date = new AbstractDateTimeDV.DateTimeData(str, this);
/* 65 */     int len = str.length();
/*    */ 
/*    */     
/* 68 */     int end = getYearMonth(str, 0, len, date);
/* 69 */     date.day = 1;
/* 70 */     parseTimeZone(str, end, len, date);
/*    */ 
/*    */ 
/*    */     
/* 74 */     validateDateTime(date);
/*    */ 
/*    */     
/* 77 */     saveUnnormalized(date);
/*    */     
/* 79 */     if (date.utc != 0 && date.utc != 90) {
/* 80 */       normalize(date);
/*    */     }
/* 82 */     date.position = 0;
/* 83 */     return date;
/*    */   }
/*    */   
/*    */   protected String dateToString(AbstractDateTimeDV.DateTimeData date) {
/* 87 */     StringBuffer message = new StringBuffer(25);
/* 88 */     append(message, date.year, 4);
/* 89 */     message.append('-');
/* 90 */     append(message, date.month, 2);
/* 91 */     append(message, (char)date.utc, 0);
/* 92 */     return message.toString();
/*    */   }
/*    */   
/*    */   protected XMLGregorianCalendar getXMLGregorianCalendar(AbstractDateTimeDV.DateTimeData date) {
/* 96 */     return datatypeFactory.newXMLGregorianCalendar(date.unNormYear, date.unNormMonth, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, 
/*    */ 
/*    */         
/* 99 */         date.hasTimeZone() ? (date.timezoneHr * 60 + date.timezoneMin) : Integer.MIN_VALUE);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/dv/xs/YearMonthDV.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */