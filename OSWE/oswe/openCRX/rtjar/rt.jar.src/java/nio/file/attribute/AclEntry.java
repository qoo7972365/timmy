/*     */ package java.nio.file.attribute;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.EnumSet;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class AclEntry
/*     */ {
/*     */   private final AclEntryType type;
/*     */   private final UserPrincipal who;
/*     */   private final Set<AclEntryPermission> perms;
/*     */   private final Set<AclEntryFlag> flags;
/*     */   private volatile int hash;
/*     */   
/*     */   private AclEntry(AclEntryType paramAclEntryType, UserPrincipal paramUserPrincipal, Set<AclEntryPermission> paramSet, Set<AclEntryFlag> paramSet1) {
/*  80 */     this.type = paramAclEntryType;
/*  81 */     this.who = paramUserPrincipal;
/*  82 */     this.perms = paramSet;
/*  83 */     this.flags = paramSet1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Builder
/*     */   {
/*     */     private AclEntryType type;
/*     */ 
/*     */ 
/*     */     
/*     */     private UserPrincipal who;
/*     */ 
/*     */ 
/*     */     
/*     */     private Set<AclEntryPermission> perms;
/*     */ 
/*     */ 
/*     */     
/*     */     private Set<AclEntryFlag> flags;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Builder(AclEntryType param1AclEntryType, UserPrincipal param1UserPrincipal, Set<AclEntryPermission> param1Set, Set<AclEntryFlag> param1Set1) {
/* 109 */       assert param1Set != null && param1Set1 != null;
/* 110 */       this.type = param1AclEntryType;
/* 111 */       this.who = param1UserPrincipal;
/* 112 */       this.perms = param1Set;
/* 113 */       this.flags = param1Set1;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AclEntry build() {
/* 127 */       if (this.type == null)
/* 128 */         throw new IllegalStateException("Missing type component"); 
/* 129 */       if (this.who == null)
/* 130 */         throw new IllegalStateException("Missing who component"); 
/* 131 */       return new AclEntry(this.type, this.who, this.perms, this.flags);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setType(AclEntryType param1AclEntryType) {
/* 141 */       if (param1AclEntryType == null)
/* 142 */         throw new NullPointerException(); 
/* 143 */       this.type = param1AclEntryType;
/* 144 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setPrincipal(UserPrincipal param1UserPrincipal) {
/* 154 */       if (param1UserPrincipal == null)
/* 155 */         throw new NullPointerException(); 
/* 156 */       this.who = param1UserPrincipal;
/* 157 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     private static void checkSet(Set<?> param1Set, Class<?> param1Class) {
/* 162 */       for (Object object : param1Set) {
/* 163 */         if (object == null)
/* 164 */           throw new NullPointerException(); 
/* 165 */         param1Class.cast(object);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setPermissions(Set<AclEntryPermission> param1Set) {
/* 181 */       if (param1Set.isEmpty()) {
/*     */         
/* 183 */         param1Set = Collections.emptySet();
/*     */       } else {
/*     */         
/* 186 */         param1Set = EnumSet.copyOf(param1Set);
/* 187 */         checkSet(param1Set, AclEntryPermission.class);
/*     */       } 
/*     */       
/* 190 */       this.perms = param1Set;
/* 191 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setPermissions(AclEntryPermission... param1VarArgs) {
/* 203 */       EnumSet<AclEntryPermission> enumSet = EnumSet.noneOf(AclEntryPermission.class);
/*     */       
/* 205 */       for (AclEntryPermission aclEntryPermission : param1VarArgs) {
/* 206 */         if (aclEntryPermission == null)
/* 207 */           throw new NullPointerException(); 
/* 208 */         enumSet.add(aclEntryPermission);
/*     */       } 
/* 210 */       this.perms = enumSet;
/* 211 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setFlags(Set<AclEntryFlag> param1Set) {
/* 226 */       if (param1Set.isEmpty()) {
/*     */         
/* 228 */         param1Set = Collections.emptySet();
/*     */       } else {
/*     */         
/* 231 */         param1Set = EnumSet.copyOf(param1Set);
/* 232 */         checkSet(param1Set, AclEntryFlag.class);
/*     */       } 
/*     */       
/* 235 */       this.flags = param1Set;
/* 236 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setFlags(AclEntryFlag... param1VarArgs) {
/* 248 */       EnumSet<AclEntryFlag> enumSet = EnumSet.noneOf(AclEntryFlag.class);
/*     */       
/* 250 */       for (AclEntryFlag aclEntryFlag : param1VarArgs) {
/* 251 */         if (aclEntryFlag == null)
/* 252 */           throw new NullPointerException(); 
/* 253 */         enumSet.add(aclEntryFlag);
/*     */       } 
/* 255 */       this.flags = enumSet;
/* 256 */       return this;
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
/*     */   public static Builder newBuilder() {
/* 268 */     Set<?> set1 = Collections.emptySet();
/* 269 */     Set<?> set2 = Collections.emptySet();
/* 270 */     return new Builder(null, null, set1, set2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Builder newBuilder(AclEntry paramAclEntry) {
/* 280 */     return new Builder(paramAclEntry.type, paramAclEntry.who, paramAclEntry.perms, paramAclEntry.flags);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AclEntryType type() {
/* 289 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UserPrincipal principal() {
/* 298 */     return this.who;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<AclEntryPermission> permissions() {
/* 309 */     return new HashSet<>(this.perms);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<AclEntryFlag> flags() {
/* 320 */     return new HashSet<>(this.flags);
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
/*     */   public boolean equals(Object paramObject) {
/* 343 */     if (paramObject == this)
/* 344 */       return true; 
/* 345 */     if (paramObject == null || !(paramObject instanceof AclEntry))
/* 346 */       return false; 
/* 347 */     AclEntry aclEntry = (AclEntry)paramObject;
/* 348 */     if (this.type != aclEntry.type)
/* 349 */       return false; 
/* 350 */     if (!this.who.equals(aclEntry.who))
/* 351 */       return false; 
/* 352 */     if (!this.perms.equals(aclEntry.perms))
/* 353 */       return false; 
/* 354 */     if (!this.flags.equals(aclEntry.flags))
/* 355 */       return false; 
/* 356 */     return true;
/*     */   }
/*     */   
/*     */   private static int hash(int paramInt, Object paramObject) {
/* 360 */     return paramInt * 127 + paramObject.hashCode();
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
/* 372 */     if (this.hash != 0)
/* 373 */       return this.hash; 
/* 374 */     int i = this.type.hashCode();
/* 375 */     i = hash(i, this.who);
/* 376 */     i = hash(i, this.perms);
/* 377 */     i = hash(i, this.flags);
/* 378 */     this.hash = i;
/* 379 */     return this.hash;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 389 */     StringBuilder stringBuilder = new StringBuilder();
/*     */ 
/*     */     
/* 392 */     stringBuilder.append(this.who.getName());
/* 393 */     stringBuilder.append(':');
/*     */ 
/*     */     
/* 396 */     for (AclEntryPermission aclEntryPermission : this.perms) {
/* 397 */       stringBuilder.append(aclEntryPermission.name());
/* 398 */       stringBuilder.append('/');
/*     */     } 
/* 400 */     stringBuilder.setLength(stringBuilder.length() - 1);
/* 401 */     stringBuilder.append(':');
/*     */ 
/*     */     
/* 404 */     if (!this.flags.isEmpty()) {
/* 405 */       for (AclEntryFlag aclEntryFlag : this.flags) {
/* 406 */         stringBuilder.append(aclEntryFlag.name());
/* 407 */         stringBuilder.append('/');
/*     */       } 
/* 409 */       stringBuilder.setLength(stringBuilder.length() - 1);
/* 410 */       stringBuilder.append(':');
/*     */     } 
/*     */ 
/*     */     
/* 414 */     stringBuilder.append(this.type.name());
/* 415 */     return stringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/nio/file/attribute/AclEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */