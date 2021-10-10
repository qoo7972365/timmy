/*     */ package java.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.security.BasicPermission;
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
/*     */ public final class PropertyPermission
/*     */   extends BasicPermission
/*     */ {
/*     */   private static final int READ = 1;
/*     */   private static final int WRITE = 2;
/*     */   private static final int ALL = 3;
/*     */   private static final int NONE = 0;
/*     */   private transient int mask;
/*     */   private String actions;
/*     */   private static final long serialVersionUID = 885438825399942851L;
/*     */   
/*     */   private void init(int paramInt) {
/* 133 */     if ((paramInt & 0x3) != paramInt) {
/* 134 */       throw new IllegalArgumentException("invalid actions mask");
/*     */     }
/* 136 */     if (paramInt == 0) {
/* 137 */       throw new IllegalArgumentException("invalid actions mask");
/*     */     }
/* 139 */     if (getName() == null) {
/* 140 */       throw new NullPointerException("name can't be null");
/*     */     }
/* 142 */     this.mask = paramInt;
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
/*     */   public PropertyPermission(String paramString1, String paramString2) {
/* 160 */     super(paramString1, paramString2);
/* 161 */     init(getMask(paramString2));
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
/* 182 */     if (!(paramPermission instanceof PropertyPermission)) {
/* 183 */       return false;
/*     */     }
/* 185 */     PropertyPermission propertyPermission = (PropertyPermission)paramPermission;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 190 */     return ((this.mask & propertyPermission.mask) == propertyPermission.mask && super.implies(propertyPermission));
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
/*     */   public boolean equals(Object paramObject) {
/* 202 */     if (paramObject == this) {
/* 203 */       return true;
/*     */     }
/* 205 */     if (!(paramObject instanceof PropertyPermission)) {
/* 206 */       return false;
/*     */     }
/* 208 */     PropertyPermission propertyPermission = (PropertyPermission)paramObject;
/*     */     
/* 210 */     return (this.mask == propertyPermission.mask && 
/* 211 */       getName().equals(propertyPermission.getName()));
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
/* 223 */     return getName().hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getMask(String paramString) {
/* 234 */     int i = 0;
/*     */     
/* 236 */     if (paramString == null) {
/* 237 */       return i;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 242 */     if (paramString == "read")
/* 243 */       return 1; 
/* 244 */     if (paramString == "write")
/* 245 */       return 2; 
/* 246 */     if (paramString == "read,write") {
/* 247 */       return 3;
/*     */     }
/*     */     
/* 250 */     char[] arrayOfChar = paramString.toCharArray();
/*     */     
/* 252 */     int j = arrayOfChar.length - 1;
/* 253 */     if (j < 0) {
/* 254 */       return i;
/*     */     }
/* 256 */     while (j != -1) {
/*     */       byte b;
/*     */       
/*     */       char c;
/* 260 */       while (j != -1 && ((c = arrayOfChar[j]) == ' ' || c == '\r' || c == '\n' || c == '\f' || c == '\t'))
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 265 */         j--;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 270 */       if (j >= 3 && (arrayOfChar[j - 3] == 'r' || arrayOfChar[j - 3] == 'R') && (arrayOfChar[j - 2] == 'e' || arrayOfChar[j - 2] == 'E') && (arrayOfChar[j - 1] == 'a' || arrayOfChar[j - 1] == 'A') && (arrayOfChar[j] == 'd' || arrayOfChar[j] == 'D')) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 275 */         b = 4;
/* 276 */         i |= 0x1;
/*     */       }
/* 278 */       else if (j >= 4 && (arrayOfChar[j - 4] == 'w' || arrayOfChar[j - 4] == 'W') && (arrayOfChar[j - 3] == 'r' || arrayOfChar[j - 3] == 'R') && (arrayOfChar[j - 2] == 'i' || arrayOfChar[j - 2] == 'I') && (arrayOfChar[j - 1] == 't' || arrayOfChar[j - 1] == 'T') && (arrayOfChar[j] == 'e' || arrayOfChar[j] == 'E')) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 284 */         b = 5;
/* 285 */         i |= 0x2;
/*     */       }
/*     */       else {
/*     */         
/* 289 */         throw new IllegalArgumentException("invalid permission: " + paramString);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 295 */       boolean bool = false;
/* 296 */       while (j >= b && !bool) {
/* 297 */         switch (arrayOfChar[j - b]) {
/*     */           case ',':
/* 299 */             bool = true; break;
/*     */           case '\t': case '\n': case '\f':
/*     */           case '\r':
/*     */           case ' ':
/*     */             break;
/*     */           default:
/* 305 */             throw new IllegalArgumentException("invalid permission: " + paramString);
/*     */         } 
/*     */         
/* 308 */         j--;
/*     */       } 
/*     */ 
/*     */       
/* 312 */       j -= b;
/*     */     } 
/*     */     
/* 315 */     return i;
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
/*     */   static String getActions(int paramInt) {
/* 327 */     StringBuilder stringBuilder = new StringBuilder();
/* 328 */     boolean bool = false;
/*     */     
/* 330 */     if ((paramInt & 0x1) == 1) {
/* 331 */       bool = true;
/* 332 */       stringBuilder.append("read");
/*     */     } 
/*     */     
/* 335 */     if ((paramInt & 0x2) == 2) {
/* 336 */       if (bool) { stringBuilder.append(','); }
/* 337 */       else { bool = true; }
/* 338 */        stringBuilder.append("write");
/*     */     } 
/* 340 */     return stringBuilder.toString();
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
/*     */   public String getActions() {
/* 353 */     if (this.actions == null) {
/* 354 */       this.actions = getActions(this.mask);
/*     */     }
/* 356 */     return this.actions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getMask() {
/* 366 */     return this.mask;
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
/*     */   public PermissionCollection newPermissionCollection() {
/* 378 */     return new PropertyPermissionCollection();
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
/*     */   private synchronized void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 394 */     if (this.actions == null)
/* 395 */       getActions(); 
/* 396 */     paramObjectOutputStream.defaultWriteObject();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 407 */     paramObjectInputStream.defaultReadObject();
/* 408 */     init(getMask(this.actions));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/PropertyPermission.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */