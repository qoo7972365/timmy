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
/*    */ class YearMonthDurationDV
/*    */   extends DurationDV
/*    */ {
/*    */   public Object getActualValue(String content, ValidationContext context) throws InvalidDatatypeValueException {
/*    */     try {
/* 44 */       return parse(content, 1);
/*    */     }
/* 46 */     catch (Exception ex) {
/* 47 */       throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[] { content, "yearMonthDuration" });
/*    */     } 
/*    */   }
/*    */   
/*    */   protected Duration getDuration(AbstractDateTimeDV.DateTimeData date) {
/* 52 */     int sign = 1;
/* 53 */     if (date.year < 0 || date.month < 0) {
/* 54 */       sign = -1;
/*    */     }
/* 56 */     return datatypeFactory.newDuration((sign == 1), (date.year != Integer.MIN_VALUE) ? 
/* 57 */         BigInteger.valueOf((sign * date.year)) : null, (date.month != Integer.MIN_VALUE) ? 
/* 58 */         BigInteger.valueOf((sign * date.month)) : null, (BigInteger)null, (BigInteger)null, (BigInteger)null, (BigDecimal)null);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/dv/xs/YearMonthDurationDV.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */