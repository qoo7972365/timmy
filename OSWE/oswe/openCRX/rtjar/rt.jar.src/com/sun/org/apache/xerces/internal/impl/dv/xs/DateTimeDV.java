/*    */ package com.sun.org.apache.xerces.internal.impl.dv.xs;
/*    */ 
/*    */ import com.sun.org.apache.xerces.internal.impl.dv.InvalidDatatypeValueException;
/*    */ import com.sun.org.apache.xerces.internal.impl.dv.ValidationContext;
/*    */ import java.math.BigInteger;
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
/*    */ public class DateTimeDV
/*    */   extends AbstractDateTimeDV
/*    */ {
/*    */   public Object getActualValue(String content, ValidationContext context) throws InvalidDatatypeValueException {
/*    */     try {
/* 45 */       return parse(content);
/* 46 */     } catch (Exception ex) {
/* 47 */       throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[] { content, "dateTime" });
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
/* 60 */     AbstractDateTimeDV.DateTimeData date = new AbstractDateTimeDV.DateTimeData(str, this);
/* 61 */     int len = str.length();
/*    */     
/* 63 */     int end = indexOf(str, 0, len, 'T');
/*    */ 
/*    */     
/* 66 */     int dateEnd = getDate(str, 0, end, date);
/* 67 */     getTime(str, end + 1, len, date);
/*    */ 
/*    */     
/* 70 */     if (dateEnd != end) {
/* 71 */       throw new RuntimeException(str + " is an invalid dateTime dataype value. Invalid character(s) seprating date and time values.");
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 79 */     validateDateTime(date);
/*    */ 
/*    */     
/* 82 */     saveUnnormalized(date);
/*    */     
/* 84 */     if (date.utc != 0 && date.utc != 90) {
/* 85 */       normalize(date);
/*    */     }
/* 87 */     return date;
/*    */   }
/*    */   
/*    */   protected XMLGregorianCalendar getXMLGregorianCalendar(AbstractDateTimeDV.DateTimeData date) {
/* 91 */     return datatypeFactory.newXMLGregorianCalendar(BigInteger.valueOf(date.unNormYear), date.unNormMonth, date.unNormDay, date.unNormHour, date.unNormMinute, (int)date.unNormSecond, (date.unNormSecond != 0.0D) ? 
/*    */         
/* 93 */         getFractionalSecondsAsBigDecimal(date) : null, 
/* 94 */         date.hasTimeZone() ? (date.timezoneHr * 60 + date.timezoneMin) : Integer.MIN_VALUE);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/dv/xs/DateTimeDV.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */