/*     */ package sun.nio.fs;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.file.attribute.UserDefinedFileAttributeView;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ abstract class AbstractUserDefinedFileAttributeView
/*     */   implements UserDefinedFileAttributeView, DynamicFileAttributeView
/*     */ {
/*     */   protected void checkAccess(String paramString, boolean paramBoolean1, boolean paramBoolean2) {
/*  46 */     assert paramBoolean1 || paramBoolean2;
/*  47 */     SecurityManager securityManager = System.getSecurityManager();
/*  48 */     if (securityManager != null) {
/*  49 */       if (paramBoolean1)
/*  50 */         securityManager.checkRead(paramString); 
/*  51 */       if (paramBoolean2)
/*  52 */         securityManager.checkWrite(paramString); 
/*  53 */       securityManager.checkPermission(new RuntimePermission("accessUserDefinedAttributes"));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final String name() {
/*  59 */     return "user";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setAttribute(String paramString, Object paramObject) throws IOException {
/*     */     ByteBuffer byteBuffer;
/*  67 */     if (paramObject instanceof byte[]) {
/*  68 */       byteBuffer = ByteBuffer.wrap((byte[])paramObject);
/*     */     } else {
/*  70 */       byteBuffer = (ByteBuffer)paramObject;
/*     */     } 
/*  72 */     write(paramString, byteBuffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Map<String, Object> readAttributes(String[] paramArrayOfString) throws IOException {
/*  80 */     List<String> list = new ArrayList();
/*  81 */     for (String str : paramArrayOfString) {
/*  82 */       if (str.equals("*")) {
/*  83 */         list = list();
/*     */         break;
/*     */       } 
/*  86 */       if (str.length() == 0)
/*  87 */         throw new IllegalArgumentException(); 
/*  88 */       list.add(str);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  93 */     HashMap<Object, Object> hashMap = new HashMap<>();
/*  94 */     for (String str : list) {
/*  95 */       int i = size(str);
/*  96 */       byte[] arrayOfByte1 = new byte[i];
/*  97 */       int j = read(str, ByteBuffer.wrap(arrayOfByte1));
/*  98 */       byte[] arrayOfByte2 = (j == i) ? arrayOfByte1 : Arrays.copyOf(arrayOfByte1, j);
/*  99 */       hashMap.put(str, arrayOfByte2);
/*     */     } 
/* 101 */     return (Map)hashMap;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/fs/AbstractUserDefinedFileAttributeView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */