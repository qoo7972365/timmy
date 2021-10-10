/*     */ package java.security;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
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
/*     */ 
/*     */ public abstract class BasicPermission
/*     */   extends Permission
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 6279438298436773498L;
/*     */   private transient boolean wildcard;
/*     */   private transient String path;
/*     */   private transient boolean exitVM;
/*     */   
/*     */   private void init(String paramString) {
/*  88 */     if (paramString == null) {
/*  89 */       throw new NullPointerException("name can't be null");
/*     */     }
/*  91 */     int i = paramString.length();
/*     */     
/*  93 */     if (i == 0) {
/*  94 */       throw new IllegalArgumentException("name can't be empty");
/*     */     }
/*     */     
/*  97 */     char c = paramString.charAt(i - 1);
/*     */ 
/*     */     
/* 100 */     if (c == '*' && (i == 1 || paramString.charAt(i - 2) == '.')) {
/* 101 */       this.wildcard = true;
/* 102 */       if (i == 1) {
/* 103 */         this.path = "";
/*     */       } else {
/* 105 */         this.path = paramString.substring(0, i - 1);
/*     */       }
/*     */     
/* 108 */     } else if (paramString.equals("exitVM")) {
/* 109 */       this.wildcard = true;
/* 110 */       this.path = "exitVM.";
/* 111 */       this.exitVM = true;
/*     */     } else {
/* 113 */       this.path = paramString;
/*     */     } 
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
/*     */   public BasicPermission(String paramString) {
/* 130 */     super(paramString);
/* 131 */     init(paramString);
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
/*     */   public BasicPermission(String paramString1, String paramString2) {
/* 147 */     super(paramString1);
/* 148 */     init(paramString1);
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
/*     */   public boolean implies(Permission paramPermission) {
/* 169 */     if (paramPermission == null || paramPermission.getClass() != getClass()) {
/* 170 */       return false;
/*     */     }
/* 172 */     BasicPermission basicPermission = (BasicPermission)paramPermission;
/*     */     
/* 174 */     if (this.wildcard) {
/* 175 */       if (basicPermission.wildcard)
/*     */       {
/* 177 */         return basicPermission.path.startsWith(this.path);
/*     */       }
/*     */       
/* 180 */       return (basicPermission.path.length() > this.path.length() && basicPermission.path
/* 181 */         .startsWith(this.path));
/*     */     } 
/*     */     
/* 184 */     if (basicPermission.wildcard)
/*     */     {
/* 186 */       return false;
/*     */     }
/*     */     
/* 189 */     return this.path.equals(basicPermission.path);
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
/*     */   public boolean equals(Object paramObject) {
/* 204 */     if (paramObject == this) {
/* 205 */       return true;
/*     */     }
/* 207 */     if (paramObject == null || paramObject.getClass() != getClass()) {
/* 208 */       return false;
/*     */     }
/* 210 */     BasicPermission basicPermission = (BasicPermission)paramObject;
/*     */     
/* 212 */     return getName().equals(basicPermission.getName());
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
/*     */   public int hashCode() {
/* 225 */     return getName().hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getActions() {
/* 236 */     return "";
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
/*     */   public PermissionCollection newPermissionCollection() {
/* 252 */     return new BasicPermissionCollection(getClass());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 262 */     paramObjectInputStream.defaultReadObject();
/*     */     
/* 264 */     init(getName());
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
/*     */   final String getCanonicalName() {
/* 276 */     return this.exitVM ? "exitVM.*" : getName();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/BasicPermission.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */