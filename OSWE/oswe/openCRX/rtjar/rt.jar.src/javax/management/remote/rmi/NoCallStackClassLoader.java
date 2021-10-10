/*     */ package javax.management.remote.rmi;
/*     */ 
/*     */ import java.security.ProtectionDomain;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class NoCallStackClassLoader
/*     */   extends ClassLoader
/*     */ {
/*     */   private final String[] classNames;
/*     */   private final byte[][] byteCodes;
/*     */   private final String[] referencedClassNames;
/*     */   private final ClassLoader referencedClassLoader;
/*     */   private final ProtectionDomain protectionDomain;
/*     */   
/*     */   public NoCallStackClassLoader(String paramString, byte[] paramArrayOfbyte, String[] paramArrayOfString, ClassLoader paramClassLoader, ProtectionDomain paramProtectionDomain) {
/*  85 */     this(new String[] { paramString }, new byte[][] { paramArrayOfbyte }, paramArrayOfString, paramClassLoader, paramProtectionDomain);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NoCallStackClassLoader(String[] paramArrayOfString1, byte[][] paramArrayOfbyte, String[] paramArrayOfString2, ClassLoader paramClassLoader, ProtectionDomain paramProtectionDomain) {
/*  94 */     super(null);
/*     */ 
/*     */     
/*  97 */     if (paramArrayOfString1 == null || paramArrayOfString1.length == 0 || paramArrayOfbyte == null || paramArrayOfString1.length != paramArrayOfbyte.length || paramArrayOfString2 == null || paramProtectionDomain == null)
/*     */     {
/*     */       
/* 100 */       throw new IllegalArgumentException(); }  byte b;
/* 101 */     for (b = 0; b < paramArrayOfString1.length; b++) {
/* 102 */       if (paramArrayOfString1[b] == null || paramArrayOfbyte[b] == null)
/* 103 */         throw new IllegalArgumentException(); 
/*     */     } 
/* 105 */     for (b = 0; b < paramArrayOfString2.length; b++) {
/* 106 */       if (paramArrayOfString2[b] == null) {
/* 107 */         throw new IllegalArgumentException();
/*     */       }
/*     */     } 
/* 110 */     this.classNames = paramArrayOfString1;
/* 111 */     this.byteCodes = paramArrayOfbyte;
/* 112 */     this.referencedClassNames = paramArrayOfString2;
/* 113 */     this.referencedClassLoader = paramClassLoader;
/* 114 */     this.protectionDomain = paramProtectionDomain;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Class<?> findClass(String paramString) throws ClassNotFoundException {
/*     */     byte b;
/* 124 */     for (b = 0; b < this.classNames.length; b++) {
/* 125 */       if (paramString.equals(this.classNames[b])) {
/* 126 */         return defineClass(this.classNames[b], this.byteCodes[b], 0, (this.byteCodes[b]).length, this.protectionDomain);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 135 */     if (this.referencedClassLoader != null) {
/* 136 */       for (b = 0; b < this.referencedClassNames.length; b++) {
/* 137 */         if (paramString.equals(this.referencedClassNames[b])) {
/* 138 */           return this.referencedClassLoader.loadClass(paramString);
/*     */         }
/*     */       } 
/*     */     }
/* 142 */     throw new ClassNotFoundException(paramString);
/*     */   }
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
/*     */   public static byte[] stringToBytes(String paramString) {
/* 176 */     int i = paramString.length();
/* 177 */     byte[] arrayOfByte = new byte[i];
/* 178 */     for (byte b = 0; b < i; b++)
/* 179 */       arrayOfByte[b] = (byte)paramString.charAt(b); 
/* 180 */     return arrayOfByte;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/remote/rmi/NoCallStackClassLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */