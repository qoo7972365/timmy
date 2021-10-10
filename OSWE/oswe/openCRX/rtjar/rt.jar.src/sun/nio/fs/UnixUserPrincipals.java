/*     */ package sun.nio.fs;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.file.attribute.GroupPrincipal;
/*     */ import java.nio.file.attribute.UserPrincipal;
/*     */ import java.nio.file.attribute.UserPrincipalNotFoundException;
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
/*     */ class UnixUserPrincipals
/*     */ {
/*     */   private static User createSpecial(String paramString) {
/*  37 */     return new User(-1, paramString);
/*     */   }
/*  39 */   static final User SPECIAL_OWNER = createSpecial("OWNER@");
/*  40 */   static final User SPECIAL_GROUP = createSpecial("GROUP@");
/*  41 */   static final User SPECIAL_EVERYONE = createSpecial("EVERYONE@");
/*     */   
/*     */   static class User implements UserPrincipal {
/*     */     private final int id;
/*     */     private final boolean isGroup;
/*     */     private final String name;
/*     */     
/*     */     private User(int param1Int, boolean param1Boolean, String param1String) {
/*  49 */       this.id = param1Int;
/*  50 */       this.isGroup = param1Boolean;
/*  51 */       this.name = param1String;
/*     */     }
/*     */     
/*     */     User(int param1Int, String param1String) {
/*  55 */       this(param1Int, false, param1String);
/*     */     }
/*     */     
/*     */     int uid() {
/*  59 */       if (this.isGroup)
/*  60 */         throw new AssertionError(); 
/*  61 */       return this.id;
/*     */     }
/*     */     
/*     */     int gid() {
/*  65 */       if (this.isGroup)
/*  66 */         return this.id; 
/*  67 */       throw new AssertionError();
/*     */     }
/*     */     
/*     */     boolean isSpecial() {
/*  71 */       return (this.id == -1);
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/*  76 */       return this.name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  81 */       return this.name;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object param1Object) {
/*  86 */       if (param1Object == this)
/*  87 */         return true; 
/*  88 */       if (!(param1Object instanceof User))
/*  89 */         return false; 
/*  90 */       User user = (User)param1Object;
/*  91 */       if (this.id != user.id || this.isGroup != user.isGroup)
/*     */       {
/*  93 */         return false;
/*     */       }
/*     */       
/*  96 */       if (this.id == -1 && user.id == -1) {
/*  97 */         return this.name.equals(user.name);
/*     */       }
/*  99 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 104 */       return (this.id != -1) ? this.id : this.name.hashCode();
/*     */     }
/*     */   }
/*     */   
/*     */   static class Group extends User implements GroupPrincipal {
/*     */     Group(int param1Int, String param1String) {
/* 110 */       super(param1Int, true, param1String);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static User fromUid(int paramInt) {
/* 116 */     String str = null;
/*     */     try {
/* 118 */       str = Util.toString(UnixNativeDispatcher.getpwuid(paramInt));
/* 119 */     } catch (UnixException unixException) {
/* 120 */       str = Integer.toString(paramInt);
/*     */     } 
/* 122 */     return new User(paramInt, str);
/*     */   }
/*     */ 
/*     */   
/*     */   static Group fromGid(int paramInt) {
/* 127 */     String str = null;
/*     */     try {
/* 129 */       str = Util.toString(UnixNativeDispatcher.getgrgid(paramInt));
/* 130 */     } catch (UnixException unixException) {
/* 131 */       str = Integer.toString(paramInt);
/*     */     } 
/* 133 */     return new Group(paramInt, str);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int lookupName(String paramString, boolean paramBoolean) throws IOException {
/* 140 */     SecurityManager securityManager = System.getSecurityManager();
/* 141 */     if (securityManager != null) {
/* 142 */       securityManager.checkPermission(new RuntimePermission("lookupUserInformation"));
/*     */     }
/* 144 */     int i = -1;
/*     */     try {
/* 146 */       i = paramBoolean ? UnixNativeDispatcher.getgrnam(paramString) : UnixNativeDispatcher.getpwnam(paramString);
/* 147 */     } catch (UnixException unixException) {
/* 148 */       throw new IOException(paramString + ": " + unixException.errorString());
/*     */     } 
/* 150 */     if (i == -1) {
/*     */       
/*     */       try {
/* 153 */         i = Integer.parseInt(paramString);
/* 154 */       } catch (NumberFormatException numberFormatException) {
/* 155 */         throw new UserPrincipalNotFoundException(paramString);
/*     */       } 
/*     */     }
/* 158 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static UserPrincipal lookupUser(String paramString) throws IOException {
/* 164 */     if (paramString.equals(SPECIAL_OWNER.getName()))
/* 165 */       return SPECIAL_OWNER; 
/* 166 */     if (paramString.equals(SPECIAL_GROUP.getName()))
/* 167 */       return SPECIAL_GROUP; 
/* 168 */     if (paramString.equals(SPECIAL_EVERYONE.getName()))
/* 169 */       return SPECIAL_EVERYONE; 
/* 170 */     int i = lookupName(paramString, false);
/* 171 */     return new User(i, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static GroupPrincipal lookupGroup(String paramString) throws IOException {
/* 178 */     int i = lookupName(paramString, true);
/* 179 */     return new Group(i, paramString);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/fs/UnixUserPrincipals.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */