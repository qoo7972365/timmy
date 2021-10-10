/*     */ package sun.nio.fs;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.file.attribute.AclFileAttributeView;
/*     */ import java.nio.file.attribute.FileAttributeView;
/*     */ import java.nio.file.attribute.FileOwnerAttributeView;
/*     */ import java.nio.file.attribute.PosixFileAttributeView;
/*     */ import java.nio.file.attribute.UserPrincipal;
/*     */ import java.util.HashMap;
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
/*     */ final class FileOwnerAttributeViewImpl
/*     */   implements FileOwnerAttributeView, DynamicFileAttributeView
/*     */ {
/*     */   private static final String OWNER_NAME = "owner";
/*     */   private final FileAttributeView view;
/*     */   private final boolean isPosixView;
/*     */   
/*     */   FileOwnerAttributeViewImpl(PosixFileAttributeView paramPosixFileAttributeView) {
/*  46 */     this.view = paramPosixFileAttributeView;
/*  47 */     this.isPosixView = true;
/*     */   }
/*     */   
/*     */   FileOwnerAttributeViewImpl(AclFileAttributeView paramAclFileAttributeView) {
/*  51 */     this.view = paramAclFileAttributeView;
/*  52 */     this.isPosixView = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public String name() {
/*  57 */     return "owner";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAttribute(String paramString, Object paramObject) throws IOException {
/*  64 */     if (paramString.equals("owner")) {
/*  65 */       setOwner((UserPrincipal)paramObject);
/*     */     } else {
/*  67 */       throw new IllegalArgumentException("'" + name() + ":" + paramString + "' not recognized");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Object> readAttributes(String[] paramArrayOfString) throws IOException {
/*  74 */     HashMap<Object, Object> hashMap = new HashMap<>();
/*  75 */     for (String str : paramArrayOfString) {
/*  76 */       if (str.equals("*") || str.equals("owner")) {
/*  77 */         hashMap.put("owner", getOwner());
/*     */       } else {
/*  79 */         throw new IllegalArgumentException("'" + name() + ":" + str + "' not recognized");
/*     */       } 
/*     */     } 
/*     */     
/*  83 */     return (Map)hashMap;
/*     */   }
/*     */ 
/*     */   
/*     */   public UserPrincipal getOwner() throws IOException {
/*  88 */     if (this.isPosixView) {
/*  89 */       return ((PosixFileAttributeView)this.view).readAttributes().owner();
/*     */     }
/*  91 */     return ((AclFileAttributeView)this.view).getOwner();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOwner(UserPrincipal paramUserPrincipal) throws IOException {
/*  99 */     if (this.isPosixView) {
/* 100 */       ((PosixFileAttributeView)this.view).setOwner(paramUserPrincipal);
/*     */     } else {
/* 102 */       ((AclFileAttributeView)this.view).setOwner(paramUserPrincipal);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/fs/FileOwnerAttributeViewImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */