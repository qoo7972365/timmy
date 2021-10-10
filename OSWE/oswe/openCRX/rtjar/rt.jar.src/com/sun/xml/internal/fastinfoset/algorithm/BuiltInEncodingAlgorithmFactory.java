/*    */ package com.sun.xml.internal.fastinfoset.algorithm;
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
/*    */ public final class BuiltInEncodingAlgorithmFactory
/*    */ {
/* 35 */   private static final BuiltInEncodingAlgorithm[] table = new BuiltInEncodingAlgorithm[10];
/*    */ 
/*    */   
/* 38 */   public static final HexadecimalEncodingAlgorithm hexadecimalEncodingAlgorithm = new HexadecimalEncodingAlgorithm();
/*    */   
/* 40 */   public static final BASE64EncodingAlgorithm base64EncodingAlgorithm = new BASE64EncodingAlgorithm();
/*    */   
/* 42 */   public static final BooleanEncodingAlgorithm booleanEncodingAlgorithm = new BooleanEncodingAlgorithm();
/*    */   
/* 44 */   public static final ShortEncodingAlgorithm shortEncodingAlgorithm = new ShortEncodingAlgorithm();
/*    */   
/* 46 */   public static final IntEncodingAlgorithm intEncodingAlgorithm = new IntEncodingAlgorithm();
/*    */   
/* 48 */   public static final LongEncodingAlgorithm longEncodingAlgorithm = new LongEncodingAlgorithm();
/*    */   
/* 50 */   public static final FloatEncodingAlgorithm floatEncodingAlgorithm = new FloatEncodingAlgorithm();
/*    */   
/* 52 */   public static final DoubleEncodingAlgorithm doubleEncodingAlgorithm = new DoubleEncodingAlgorithm();
/*    */   
/* 54 */   public static final UUIDEncodingAlgorithm uuidEncodingAlgorithm = new UUIDEncodingAlgorithm();
/*    */   
/*    */   static {
/* 57 */     table[0] = hexadecimalEncodingAlgorithm;
/* 58 */     table[1] = base64EncodingAlgorithm;
/* 59 */     table[2] = shortEncodingAlgorithm;
/* 60 */     table[3] = intEncodingAlgorithm;
/* 61 */     table[4] = longEncodingAlgorithm;
/* 62 */     table[5] = booleanEncodingAlgorithm;
/* 63 */     table[6] = floatEncodingAlgorithm;
/* 64 */     table[7] = doubleEncodingAlgorithm;
/* 65 */     table[8] = uuidEncodingAlgorithm;
/*    */   }
/*    */   
/*    */   public static BuiltInEncodingAlgorithm getAlgorithm(int index) {
/* 69 */     return table[index];
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/fastinfoset/algorithm/BuiltInEncodingAlgorithmFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */