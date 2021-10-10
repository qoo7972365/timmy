/*    */ package sun.reflect;
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
/*    */ class UTF8
/*    */ {
/*    */   static byte[] encode(String paramString) {
/* 35 */     int i = paramString.length();
/* 36 */     byte[] arrayOfByte = new byte[utf8Length(paramString)];
/* 37 */     byte b = 0;
/*    */     try {
/* 39 */       for (byte b1 = 0; b1 < i; b1++) {
/* 40 */         int j = paramString.charAt(b1) & Character.MAX_VALUE;
/* 41 */         if (j >= 1 && j <= 127) {
/* 42 */           arrayOfByte[b++] = (byte)j;
/* 43 */         } else if (j == 0 || (j >= 128 && j <= 2047)) {
/*    */           
/* 45 */           arrayOfByte[b++] = (byte)(192 + (j >> 6));
/* 46 */           arrayOfByte[b++] = (byte)(128 + (j & 0x3F));
/*    */         } else {
/* 48 */           arrayOfByte[b++] = (byte)(224 + (j >> 12));
/* 49 */           arrayOfByte[b++] = (byte)(128 + (j >> 6 & 0x3F));
/* 50 */           arrayOfByte[b++] = (byte)(128 + (j & 0x3F));
/*    */         } 
/*    */       } 
/* 53 */     } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
/* 54 */       throw new InternalError("Bug in sun.reflect bootstrap UTF-8 encoder", arrayIndexOutOfBoundsException);
/*    */     } 
/*    */     
/* 57 */     return arrayOfByte;
/*    */   }
/*    */   
/*    */   private static int utf8Length(String paramString) {
/* 61 */     int i = paramString.length();
/* 62 */     byte b1 = 0;
/* 63 */     for (byte b2 = 0; b2 < i; b2++) {
/* 64 */       int j = paramString.charAt(b2) & Character.MAX_VALUE;
/* 65 */       if (j >= 1 && j <= 127) {
/* 66 */         b1++;
/* 67 */       } else if (j == 0 || (j >= 128 && j <= 2047)) {
/*    */         
/* 69 */         b1 += 2;
/*    */       } else {
/* 71 */         b1 += 3;
/*    */       } 
/*    */     } 
/* 74 */     return b1;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/UTF8.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */