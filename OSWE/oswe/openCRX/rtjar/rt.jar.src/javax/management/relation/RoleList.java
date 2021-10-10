/*     */ package javax.management.relation;
/*     */ 
/*     */ import com.sun.jmx.mbeanserver.Util;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RoleList
/*     */   extends ArrayList<Object>
/*     */ {
/*     */   private transient boolean typeSafe;
/*     */   private transient boolean tainted;
/*     */   private static final long serialVersionUID = 5568344346499649313L;
/*     */   
/*     */   public RoleList() {}
/*     */   
/*     */   public RoleList(int paramInt) {
/*  82 */     super(paramInt);
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
/*     */   public RoleList(List<Role> paramList) throws IllegalArgumentException {
/* 104 */     if (paramList == null) {
/* 105 */       throw new IllegalArgumentException("Null parameter");
/*     */     }
/*     */ 
/*     */     
/* 109 */     checkTypeSafe(paramList);
/*     */ 
/*     */ 
/*     */     
/* 113 */     super.addAll(paramList);
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
/*     */   public List<Role> asList() {
/* 139 */     if (!this.typeSafe) {
/* 140 */       if (this.tainted)
/* 141 */         checkTypeSafe(this); 
/* 142 */       this.typeSafe = true;
/*     */     } 
/* 144 */     return Util.<List<Role>>cast(this);
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
/*     */   public void add(Role paramRole) throws IllegalArgumentException {
/* 161 */     if (paramRole == null) {
/* 162 */       String str = "Invalid parameter";
/* 163 */       throw new IllegalArgumentException(str);
/*     */     } 
/* 165 */     super.add(paramRole);
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
/*     */   public void add(int paramInt, Role paramRole) throws IllegalArgumentException, IndexOutOfBoundsException {
/* 186 */     if (paramRole == null) {
/* 187 */       String str = "Invalid parameter";
/* 188 */       throw new IllegalArgumentException(str);
/*     */     } 
/*     */     
/* 191 */     super.add(paramInt, paramRole);
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
/*     */   public void set(int paramInt, Role paramRole) throws IllegalArgumentException, IndexOutOfBoundsException {
/* 211 */     if (paramRole == null) {
/*     */       
/* 213 */       String str = "Invalid parameter.";
/* 214 */       throw new IllegalArgumentException(str);
/*     */     } 
/*     */     
/* 217 */     super.set(paramInt, paramRole);
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
/*     */   public boolean addAll(RoleList paramRoleList) throws IndexOutOfBoundsException {
/* 237 */     if (paramRoleList == null) {
/* 238 */       return true;
/*     */     }
/*     */     
/* 241 */     return super.addAll(paramRoleList);
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
/*     */   public boolean addAll(int paramInt, RoleList paramRoleList) throws IllegalArgumentException, IndexOutOfBoundsException {
/* 266 */     if (paramRoleList == null) {
/*     */       
/* 268 */       String str = "Invalid parameter.";
/* 269 */       throw new IllegalArgumentException(str);
/*     */     } 
/*     */     
/* 272 */     return super.addAll(paramInt, paramRoleList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean add(Object paramObject) {
/* 283 */     if (!this.tainted)
/* 284 */       this.tainted = isTainted(paramObject); 
/* 285 */     if (this.typeSafe)
/* 286 */       checkTypeSafe(paramObject); 
/* 287 */     return super.add(paramObject);
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(int paramInt, Object paramObject) {
/* 292 */     if (!this.tainted)
/* 293 */       this.tainted = isTainted(paramObject); 
/* 294 */     if (this.typeSafe)
/* 295 */       checkTypeSafe(paramObject); 
/* 296 */     super.add(paramInt, paramObject);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(Collection<?> paramCollection) {
/* 301 */     if (!this.tainted)
/* 302 */       this.tainted = isTainted(paramCollection); 
/* 303 */     if (this.typeSafe)
/* 304 */       checkTypeSafe(paramCollection); 
/* 305 */     return super.addAll(paramCollection);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(int paramInt, Collection<?> paramCollection) {
/* 310 */     if (!this.tainted)
/* 311 */       this.tainted = isTainted(paramCollection); 
/* 312 */     if (this.typeSafe)
/* 313 */       checkTypeSafe(paramCollection); 
/* 314 */     return super.addAll(paramInt, paramCollection);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object set(int paramInt, Object paramObject) {
/* 319 */     if (!this.tainted)
/* 320 */       this.tainted = isTainted(paramObject); 
/* 321 */     if (this.typeSafe)
/* 322 */       checkTypeSafe(paramObject); 
/* 323 */     return super.set(paramInt, paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void checkTypeSafe(Object paramObject) {
/*     */     try {
/* 331 */       paramObject = paramObject;
/* 332 */     } catch (ClassCastException classCastException) {
/* 333 */       throw new IllegalArgumentException(classCastException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void checkTypeSafe(Collection<?> paramCollection) {
/*     */     try {
/* 343 */       for (Object object : paramCollection)
/* 344 */         Role role = (Role)object; 
/* 345 */     } catch (ClassCastException classCastException) {
/* 346 */       throw new IllegalArgumentException(classCastException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isTainted(Object paramObject) {
/*     */     try {
/* 355 */       checkTypeSafe(paramObject);
/* 356 */     } catch (IllegalArgumentException illegalArgumentException) {
/* 357 */       return true;
/*     */     } 
/* 359 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isTainted(Collection<?> paramCollection) {
/*     */     try {
/* 367 */       checkTypeSafe(paramCollection);
/* 368 */     } catch (IllegalArgumentException illegalArgumentException) {
/* 369 */       return true;
/*     */     } 
/* 371 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/relation/RoleList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */