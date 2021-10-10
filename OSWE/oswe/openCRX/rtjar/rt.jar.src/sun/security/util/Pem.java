/*    */ package sun.security.util;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.nio.charset.StandardCharsets;
/*    */ import java.util.Base64;
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
/*    */ public class Pem
/*    */ {
/*    */   public static byte[] decode(String paramString) throws IOException {
/* 47 */     byte[] arrayOfByte = paramString.replaceAll("\\s+", "").getBytes(StandardCharsets.ISO_8859_1);
/*    */     try {
/* 49 */       return Base64.getDecoder().decode(arrayOfByte);
/* 50 */     } catch (IllegalArgumentException illegalArgumentException) {
/* 51 */       throw new IOException(illegalArgumentException);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/util/Pem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */