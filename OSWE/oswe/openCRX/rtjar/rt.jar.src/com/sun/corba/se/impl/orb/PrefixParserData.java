/*    */ package com.sun.corba.se.impl.orb;
/*    */ 
/*    */ import com.sun.corba.se.spi.orb.Operation;
/*    */ import com.sun.corba.se.spi.orb.PropertyParser;
/*    */ import com.sun.corba.se.spi.orb.StringPair;
/*    */ import java.util.Properties;
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
/*    */ public class PrefixParserData
/*    */   extends ParserDataBase
/*    */ {
/*    */   private StringPair[] testData;
/*    */   private Class componentType;
/*    */   
/*    */   public PrefixParserData(String paramString1, Operation paramOperation, String paramString2, Object paramObject1, Object paramObject2, StringPair[] paramArrayOfStringPair, Class paramClass) {
/* 43 */     super(paramString1, paramOperation, paramString2, paramObject1, paramObject2);
/* 44 */     this.testData = paramArrayOfStringPair;
/* 45 */     this.componentType = paramClass;
/*    */   }
/*    */ 
/*    */   
/*    */   public void addToParser(PropertyParser paramPropertyParser) {
/* 50 */     paramPropertyParser.addPrefix(getPropertyName(), getOperation(), getFieldName(), this.componentType);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void addToProperties(Properties paramProperties) {
/* 56 */     for (byte b = 0; b < this.testData.length; b++) {
/* 57 */       StringPair stringPair = this.testData[b];
/*    */       
/* 59 */       String str = getPropertyName();
/* 60 */       if (str.charAt(str.length() - 1) != '.') {
/* 61 */         str = str + ".";
/*    */       }
/* 63 */       paramProperties.setProperty(str + stringPair.getFirst(), stringPair.getSecond());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/orb/PrefixParserData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */