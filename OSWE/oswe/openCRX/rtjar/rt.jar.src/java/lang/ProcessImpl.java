/*     */ package java.lang;
/*     */ 
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import sun.misc.JavaIOFileDescriptorAccess;
/*     */ import sun.misc.SharedSecrets;
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
/*     */ final class ProcessImpl
/*     */ {
/*  43 */   private static final JavaIOFileDescriptorAccess fdAccess = SharedSecrets.getJavaIOFileDescriptorAccess();
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] toCString(String paramString) {
/*  48 */     if (paramString == null)
/*  49 */       return null; 
/*  50 */     byte[] arrayOfByte1 = paramString.getBytes();
/*  51 */     byte[] arrayOfByte2 = new byte[arrayOfByte1.length + 1];
/*  52 */     System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, arrayOfByte1.length);
/*     */ 
/*     */     
/*  55 */     arrayOfByte2[arrayOfByte2.length - 1] = 0;
/*  56 */     return arrayOfByte2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Process start(String[] paramArrayOfString, Map<String, String> paramMap, String paramString, ProcessBuilder.Redirect[] paramArrayOfRedirect, boolean paramBoolean) throws IOException {
/*  67 */     assert paramArrayOfString != null && paramArrayOfString.length > 0;
/*     */ 
/*     */ 
/*     */     
/*  71 */     byte[][] arrayOfByte = new byte[paramArrayOfString.length - 1][];
/*  72 */     int i = arrayOfByte.length;
/*  73 */     for (byte b = 0; b < arrayOfByte.length; b++) {
/*  74 */       arrayOfByte[b] = paramArrayOfString[b + 1].getBytes();
/*  75 */       i += (arrayOfByte[b]).length;
/*     */     } 
/*  77 */     byte[] arrayOfByte1 = new byte[i];
/*  78 */     int j = 0;
/*  79 */     for (byte[] arrayOfByte3 : arrayOfByte) {
/*  80 */       System.arraycopy(arrayOfByte3, 0, arrayOfByte1, j, arrayOfByte3.length);
/*  81 */       j += arrayOfByte3.length + 1;
/*     */     } 
/*     */ 
/*     */     
/*  85 */     int[] arrayOfInt = new int[1];
/*  86 */     byte[] arrayOfByte2 = ProcessEnvironment.toEnvironmentBlock(paramMap, arrayOfInt);
/*     */ 
/*     */ 
/*     */     
/*  90 */     FileInputStream fileInputStream = null;
/*  91 */     FileOutputStream fileOutputStream1 = null;
/*  92 */     FileOutputStream fileOutputStream2 = null;
/*     */     try {
/*     */       int[] arrayOfInt1;
/*  95 */       if (paramArrayOfRedirect == null) {
/*  96 */         arrayOfInt1 = new int[] { -1, -1, -1 };
/*     */       } else {
/*  98 */         arrayOfInt1 = new int[3];
/*     */         
/* 100 */         if (paramArrayOfRedirect[0] == ProcessBuilder.Redirect.PIPE) {
/* 101 */           arrayOfInt1[0] = -1;
/* 102 */         } else if (paramArrayOfRedirect[0] == ProcessBuilder.Redirect.INHERIT) {
/* 103 */           arrayOfInt1[0] = 0;
/*     */         } else {
/* 105 */           fileInputStream = new FileInputStream(paramArrayOfRedirect[0].file());
/* 106 */           arrayOfInt1[0] = fdAccess.get(fileInputStream.getFD());
/*     */         } 
/*     */         
/* 109 */         if (paramArrayOfRedirect[1] == ProcessBuilder.Redirect.PIPE) {
/* 110 */           arrayOfInt1[1] = -1;
/* 111 */         } else if (paramArrayOfRedirect[1] == ProcessBuilder.Redirect.INHERIT) {
/* 112 */           arrayOfInt1[1] = 1;
/*     */         } else {
/*     */           
/* 115 */           fileOutputStream1 = new FileOutputStream(paramArrayOfRedirect[1].file(), paramArrayOfRedirect[1].append());
/* 116 */           arrayOfInt1[1] = fdAccess.get(fileOutputStream1.getFD());
/*     */         } 
/*     */         
/* 119 */         if (paramArrayOfRedirect[2] == ProcessBuilder.Redirect.PIPE) {
/* 120 */           arrayOfInt1[2] = -1;
/* 121 */         } else if (paramArrayOfRedirect[2] == ProcessBuilder.Redirect.INHERIT) {
/* 122 */           arrayOfInt1[2] = 2;
/*     */         } else {
/*     */           
/* 125 */           fileOutputStream2 = new FileOutputStream(paramArrayOfRedirect[2].file(), paramArrayOfRedirect[2].append());
/* 126 */           arrayOfInt1[2] = fdAccess.get(fileOutputStream2.getFD());
/*     */         } 
/*     */       } 
/*     */       
/* 130 */       return new UNIXProcess(
/* 131 */           toCString(paramArrayOfString[0]), arrayOfByte1, arrayOfByte.length, arrayOfByte2, arrayOfInt[0], 
/*     */ 
/*     */           
/* 134 */           toCString(paramString), arrayOfInt1, paramBoolean);
/*     */     } finally {
/*     */ 
/*     */ 
/*     */       
/*     */       try { 
/* 140 */         if (fileInputStream != null) fileInputStream.close();  }
/*     */       finally { 
/* 142 */         try { if (fileOutputStream1 != null) fileOutputStream1.close();  }
/* 143 */         finally { if (fileOutputStream2 != null) fileOutputStream2.close();  }
/*     */          }
/*     */     
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/ProcessImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */