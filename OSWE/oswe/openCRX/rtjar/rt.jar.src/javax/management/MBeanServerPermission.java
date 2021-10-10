/*     */ package javax.management;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.security.BasicPermission;
/*     */ import java.security.Permission;
/*     */ import java.security.PermissionCollection;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MBeanServerPermission
/*     */   extends BasicPermission
/*     */ {
/*     */   private static final long serialVersionUID = -5661980843569388590L;
/*     */   private static final int CREATE = 0;
/*     */   private static final int FIND = 1;
/*     */   private static final int NEW = 2;
/*     */   private static final int RELEASE = 3;
/*     */   private static final int N_NAMES = 4;
/*  79 */   private static final String[] names = new String[] { "createMBeanServer", "findMBeanServer", "newMBeanServer", "releaseMBeanServer" };
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int CREATE_MASK = 1;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int FIND_MASK = 2;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int NEW_MASK = 4;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int RELEASE_MASK = 8;
/*     */ 
/*     */   
/*     */   private static final int ALL_MASK = 15;
/*     */ 
/*     */   
/* 101 */   private static final String[] canonicalNames = new String[16];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   transient int mask;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MBeanServerPermission(String paramString) {
/* 124 */     this(paramString, null);
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
/*     */   public MBeanServerPermission(String paramString1, String paramString2) {
/* 144 */     super(getCanonicalName(parseMask(paramString1)), paramString2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 151 */     this.mask = parseMask(paramString1);
/*     */ 
/*     */     
/* 154 */     if (paramString2 != null && paramString2.length() > 0) {
/* 155 */       throw new IllegalArgumentException("MBeanServerPermission actions must be null: " + paramString2);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   MBeanServerPermission(int paramInt) {
/* 161 */     super(getCanonicalName(paramInt));
/* 162 */     this.mask = impliedMask(paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 167 */     paramObjectInputStream.defaultReadObject();
/* 168 */     this.mask = parseMask(getName());
/*     */   }
/*     */   
/*     */   static int simplifyMask(int paramInt) {
/* 172 */     if ((paramInt & 0x1) != 0)
/* 173 */       paramInt &= 0xFFFFFFFB; 
/* 174 */     return paramInt;
/*     */   }
/*     */   
/*     */   static int impliedMask(int paramInt) {
/* 178 */     if ((paramInt & 0x1) != 0)
/* 179 */       paramInt |= 0x4; 
/* 180 */     return paramInt;
/*     */   }
/*     */   
/*     */   static String getCanonicalName(int paramInt) {
/* 184 */     if (paramInt == 15) {
/* 185 */       return "*";
/*     */     }
/* 187 */     paramInt = simplifyMask(paramInt);
/*     */     
/* 189 */     synchronized (canonicalNames) {
/* 190 */       if (canonicalNames[paramInt] == null) {
/* 191 */         canonicalNames[paramInt] = makeCanonicalName(paramInt);
/*     */       }
/*     */     } 
/* 194 */     return canonicalNames[paramInt];
/*     */   }
/*     */   
/*     */   private static String makeCanonicalName(int paramInt) {
/* 198 */     StringBuilder stringBuilder = new StringBuilder();
/* 199 */     for (byte b = 0; b < 4; b++) {
/* 200 */       if ((paramInt & 1 << b) != 0) {
/* 201 */         if (stringBuilder.length() > 0)
/* 202 */           stringBuilder.append(','); 
/* 203 */         stringBuilder.append(names[b]);
/*     */       } 
/*     */     } 
/* 206 */     return stringBuilder.toString().intern();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int parseMask(String paramString) {
/* 216 */     if (paramString == null) {
/* 217 */       throw new NullPointerException("MBeanServerPermission: target name can't be null");
/*     */     }
/*     */ 
/*     */     
/* 221 */     paramString = paramString.trim();
/* 222 */     if (paramString.equals("*")) {
/* 223 */       return 15;
/*     */     }
/*     */     
/* 226 */     if (paramString.indexOf(',') < 0) {
/* 227 */       return impliedMask(1 << nameIndex(paramString.trim()));
/*     */     }
/* 229 */     int i = 0;
/*     */     
/* 231 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString, ",");
/* 232 */     while (stringTokenizer.hasMoreTokens()) {
/* 233 */       String str = stringTokenizer.nextToken();
/* 234 */       int j = nameIndex(str.trim());
/* 235 */       i |= 1 << j;
/*     */     } 
/*     */     
/* 238 */     return impliedMask(i);
/*     */   }
/*     */ 
/*     */   
/*     */   private static int nameIndex(String paramString) throws IllegalArgumentException {
/* 243 */     for (byte b = 0; b < 4; b++) {
/* 244 */       if (names[b].equals(paramString))
/* 245 */         return b; 
/*     */     } 
/* 247 */     String str = "Invalid MBeanServerPermission name: \"" + paramString + "\"";
/*     */     
/* 249 */     throw new IllegalArgumentException(str);
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 253 */     return this.mask;
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
/*     */   public boolean implies(Permission paramPermission) {
/* 276 */     if (!(paramPermission instanceof MBeanServerPermission)) {
/* 277 */       return false;
/*     */     }
/* 279 */     MBeanServerPermission mBeanServerPermission = (MBeanServerPermission)paramPermission;
/*     */     
/* 281 */     return ((this.mask & mBeanServerPermission.mask) == mBeanServerPermission.mask);
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
/* 293 */     if (paramObject == this) {
/* 294 */       return true;
/*     */     }
/* 296 */     if (!(paramObject instanceof MBeanServerPermission)) {
/* 297 */       return false;
/*     */     }
/* 299 */     MBeanServerPermission mBeanServerPermission = (MBeanServerPermission)paramObject;
/*     */     
/* 301 */     return (this.mask == mBeanServerPermission.mask);
/*     */   }
/*     */   
/*     */   public PermissionCollection newPermissionCollection() {
/* 305 */     return new MBeanServerPermissionCollection();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/MBeanServerPermission.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */