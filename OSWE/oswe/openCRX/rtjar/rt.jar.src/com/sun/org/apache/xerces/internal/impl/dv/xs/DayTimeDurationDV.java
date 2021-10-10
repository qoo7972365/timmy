/*    */ package com.sun.org.apache.xerces.internal.impl.dv.xs;
/*    */ 
/*    */ import com.sun.org.apache.xerces.internal.impl.dv.InvalidDatatypeValueException;
/*    */ import com.sun.org.apache.xerces.internal.impl.dv.ValidationContext;
/*    */ import java.math.BigDecimal;
/*    */ import java.math.BigInteger;
/*    */ import javax.xml.datatype.Duration;
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
/*    */ class DayTimeDurationDV
/*    */   extends DurationDV
/*    */ {
/*    */   public Object getActualValue(String content, ValidationContext context) throws InvalidDatatypeValueException {
/*    */     try {
/* 45 */       return parse(content, 2);
/*    */     }
/* 47 */     catch (Exception ex) {
/* 48 */       throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[] { content, "dayTimeDuration" });
/*    */     } 
/*    */   }
/*    */   
/*    */   protected Duration getDuration(AbstractDateTimeDV.DateTimeData date) {
/* 53 */     int sign = 1;
/* 54 */     if (date.day < 0 || date.hour < 0 || date.minute < 0 || date.second < 0.0D) {
/* 55 */       sign = -1;
/*    */     }
/* 57 */     return datatypeFactory.newDuration((sign == 1), (BigInteger)null, (BigInteger)null, (date.day != Integer.MIN_VALUE) ? 
/* 58 */         BigInteger.valueOf((sign * date.day)) : null, (date.hour != Integer.MIN_VALUE) ? 
/* 59 */         BigInteger.valueOf((sign * date.hour)) : null, (date.minute != Integer.MIN_VALUE) ? 
/* 60 */         BigInteger.valueOf((sign * date.minute)) : null, (date.second != -2.147483648E9D) ? new BigDecimal(
/* 61 */           String.valueOf(sign * date.second)) : null);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/dv/xs/DayTimeDurationDV.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */