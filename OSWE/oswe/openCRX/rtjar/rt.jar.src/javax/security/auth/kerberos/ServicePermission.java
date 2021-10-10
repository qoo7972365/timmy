/*     */ package javax.security.auth.kerberos;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.security.Permission;
/*     */ import java.security.PermissionCollection;
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
/*     */ public final class ServicePermission
/*     */   extends Permission
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -1227585031618624935L;
/*     */   private static final int INITIATE = 1;
/*     */   private static final int ACCEPT = 2;
/*     */   private static final int ALL = 3;
/*     */   private static final int NONE = 0;
/*     */   private transient int mask;
/*     */   private String actions;
/*     */   
/*     */   public ServicePermission(String paramString1, String paramString2) {
/* 151 */     super(paramString1);
/* 152 */     init(paramString1, getMask(paramString2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void init(String paramString, int paramInt) {
/* 161 */     if (paramString == null) {
/* 162 */       throw new NullPointerException("service principal can't be null");
/*     */     }
/* 164 */     if ((paramInt & 0x3) != paramInt) {
/* 165 */       throw new IllegalArgumentException("invalid actions mask");
/*     */     }
/* 167 */     this.mask = paramInt;
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
/*     */   public boolean implies(Permission paramPermission) {
/* 182 */     if (!(paramPermission instanceof ServicePermission)) {
/* 183 */       return false;
/*     */     }
/* 185 */     ServicePermission servicePermission = (ServicePermission)paramPermission;
/*     */     
/* 187 */     return ((this.mask & servicePermission.mask) == servicePermission.mask && 
/* 188 */       impliesIgnoreMask(servicePermission));
/*     */   }
/*     */ 
/*     */   
/*     */   boolean impliesIgnoreMask(ServicePermission paramServicePermission) {
/* 193 */     return (getName().equals("*") || 
/* 194 */       getName().equals(paramServicePermission.getName()) || (paramServicePermission
/* 195 */       .getName().startsWith("@") && 
/* 196 */       getName().endsWith(paramServicePermission.getName())));
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
/*     */   public boolean equals(Object paramObject) {
/* 209 */     if (paramObject == this) {
/* 210 */       return true;
/*     */     }
/* 212 */     if (!(paramObject instanceof ServicePermission)) {
/* 213 */       return false;
/*     */     }
/* 215 */     ServicePermission servicePermission = (ServicePermission)paramObject;
/* 216 */     return ((this.mask & servicePermission.mask) == servicePermission.mask && 
/* 217 */       getName().equals(servicePermission.getName()));
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
/*     */   public int hashCode() {
/* 229 */     return getName().hashCode() ^ this.mask;
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
/*     */   private static String getActions(int paramInt) {
/* 244 */     StringBuilder stringBuilder = new StringBuilder();
/* 245 */     boolean bool = false;
/*     */     
/* 247 */     if ((paramInt & 0x1) == 1) {
/* 248 */       if (bool) { stringBuilder.append(','); }
/* 249 */       else { bool = true; }
/* 250 */        stringBuilder.append("initiate");
/*     */     } 
/*     */     
/* 253 */     if ((paramInt & 0x2) == 2) {
/* 254 */       if (bool) { stringBuilder.append(','); }
/* 255 */       else { bool = true; }
/* 256 */        stringBuilder.append("accept");
/*     */     } 
/*     */     
/* 259 */     return stringBuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getActions() {
/* 268 */     if (this.actions == null) {
/* 269 */       this.actions = getActions(this.mask);
/*     */     }
/* 271 */     return this.actions;
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
/*     */   public PermissionCollection newPermissionCollection() {
/* 288 */     return new KrbServicePermissionCollection();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getMask() {
/* 297 */     return this.mask;
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
/*     */   private static int getMask(String paramString) {
/* 311 */     if (paramString == null) {
/* 312 */       throw new NullPointerException("action can't be null");
/*     */     }
/*     */     
/* 315 */     if (paramString.equals("")) {
/* 316 */       throw new IllegalArgumentException("action can't be empty");
/*     */     }
/*     */     
/* 319 */     int i = 0;
/*     */     
/* 321 */     char[] arrayOfChar = paramString.toCharArray();
/*     */     
/* 323 */     if (arrayOfChar.length == 1 && arrayOfChar[0] == '-') {
/* 324 */       return i;
/*     */     }
/*     */     
/* 327 */     int j = arrayOfChar.length - 1;
/*     */     
/* 329 */     while (j != -1) {
/*     */       byte b;
/*     */       
/*     */       char c;
/* 333 */       while (j != -1 && ((c = arrayOfChar[j]) == ' ' || c == '\r' || c == '\n' || c == '\f' || c == '\t'))
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 338 */         j--;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 343 */       if (j >= 7 && (arrayOfChar[j - 7] == 'i' || arrayOfChar[j - 7] == 'I') && (arrayOfChar[j - 6] == 'n' || arrayOfChar[j - 6] == 'N') && (arrayOfChar[j - 5] == 'i' || arrayOfChar[j - 5] == 'I') && (arrayOfChar[j - 4] == 't' || arrayOfChar[j - 4] == 'T') && (arrayOfChar[j - 3] == 'i' || arrayOfChar[j - 3] == 'I') && (arrayOfChar[j - 2] == 'a' || arrayOfChar[j - 2] == 'A') && (arrayOfChar[j - 1] == 't' || arrayOfChar[j - 1] == 'T') && (arrayOfChar[j] == 'e' || arrayOfChar[j] == 'E')) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 352 */         b = 8;
/* 353 */         i |= 0x1;
/*     */       }
/* 355 */       else if (j >= 5 && (arrayOfChar[j - 5] == 'a' || arrayOfChar[j - 5] == 'A') && (arrayOfChar[j - 4] == 'c' || arrayOfChar[j - 4] == 'C') && (arrayOfChar[j - 3] == 'c' || arrayOfChar[j - 3] == 'C') && (arrayOfChar[j - 2] == 'e' || arrayOfChar[j - 2] == 'E') && (arrayOfChar[j - 1] == 'p' || arrayOfChar[j - 1] == 'P') && (arrayOfChar[j] == 't' || arrayOfChar[j] == 'T')) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 362 */         b = 6;
/* 363 */         i |= 0x2;
/*     */       }
/*     */       else {
/*     */         
/* 367 */         throw new IllegalArgumentException("invalid permission: " + paramString);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 373 */       boolean bool = false;
/* 374 */       while (j >= b && !bool) {
/* 375 */         switch (arrayOfChar[j - b]) {
/*     */           case ',':
/* 377 */             bool = true; break;
/*     */           case '\t': case '\n': case '\f':
/*     */           case '\r':
/*     */           case ' ':
/*     */             break;
/*     */           default:
/* 383 */             throw new IllegalArgumentException("invalid permission: " + paramString);
/*     */         } 
/*     */         
/* 386 */         j--;
/*     */       } 
/*     */ 
/*     */       
/* 390 */       j -= b;
/*     */     } 
/*     */     
/* 393 */     return i;
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
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 407 */     if (this.actions == null)
/* 408 */       getActions(); 
/* 409 */     paramObjectOutputStream.defaultWriteObject();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 420 */     paramObjectInputStream.defaultReadObject();
/* 421 */     init(getName(), getMask(this.actions));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/security/auth/kerberos/ServicePermission.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */