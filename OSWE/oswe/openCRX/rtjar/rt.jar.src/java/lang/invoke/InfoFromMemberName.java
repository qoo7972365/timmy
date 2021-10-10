/*     */ package java.lang.invoke;
/*     */ 
/*     */ import java.lang.invoke.InfoFromMemberName;
/*     */ import java.lang.invoke.MemberName;
/*     */ import java.lang.invoke.MethodHandleInfo;
/*     */ import java.lang.invoke.MethodHandleNatives;
/*     */ import java.lang.invoke.MethodHandles;
/*     */ import java.lang.invoke.MethodType;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Member;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class InfoFromMemberName
/*     */   implements MethodHandleInfo
/*     */ {
/*     */   private final MemberName member;
/*     */   private final int referenceKind;
/*     */   
/*     */   InfoFromMemberName(MethodHandles.Lookup paramLookup, MemberName paramMemberName, byte paramByte) {
/*  44 */     assert paramMemberName.isResolved() || paramMemberName.isMethodHandleInvoke();
/*  45 */     assert paramMemberName.referenceKindIsConsistentWith(paramByte);
/*  46 */     this.member = paramMemberName;
/*  47 */     this.referenceKind = paramByte;
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<?> getDeclaringClass() {
/*  52 */     return this.member.getDeclaringClass();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  57 */     return this.member.getName();
/*     */   }
/*     */ 
/*     */   
/*     */   public MethodType getMethodType() {
/*  62 */     return this.member.getMethodOrFieldType();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getModifiers() {
/*  67 */     return this.member.getModifiers();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getReferenceKind() {
/*  72 */     return this.referenceKind;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  77 */     return MethodHandleInfo.toString(getReferenceKind(), getDeclaringClass(), getName(), getMethodType());
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends Member> T reflectAs(Class<T> paramClass, MethodHandles.Lookup paramLookup) {
/*  82 */     if (this.member.isMethodHandleInvoke() && !this.member.isVarargs())
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  88 */       throw new IllegalArgumentException("cannot reflect signature polymorphic method");
/*     */     }
/*  90 */     Member member = AccessController.<Member>doPrivileged(new PrivilegedAction<Member>() {
/*     */           public Member run() {
/*     */             try {
/*  93 */               return InfoFromMemberName.this.reflectUnchecked();
/*  94 */             } catch (ReflectiveOperationException reflectiveOperationException) {
/*  95 */               throw new IllegalArgumentException(reflectiveOperationException);
/*     */             } 
/*     */           }
/*     */         });
/*     */     try {
/* 100 */       Class<?> clazz = getDeclaringClass();
/* 101 */       byte b = (byte)getReferenceKind();
/* 102 */       paramLookup.checkAccess(b, clazz, convertToMemberName(b, member));
/* 103 */     } catch (IllegalAccessException illegalAccessException) {
/* 104 */       throw new IllegalArgumentException(illegalAccessException);
/*     */     } 
/* 106 */     return paramClass.cast(member);
/*     */   }
/*     */   
/*     */   private Member reflectUnchecked() throws ReflectiveOperationException {
/* 110 */     byte b = (byte)getReferenceKind();
/* 111 */     Class<?> clazz = getDeclaringClass();
/* 112 */     boolean bool = Modifier.isPublic(getModifiers());
/* 113 */     if (MethodHandleNatives.refKindIsMethod(b)) {
/* 114 */       if (bool) {
/* 115 */         return clazz.getMethod(getName(), getMethodType().parameterArray());
/*     */       }
/* 117 */       return clazz.getDeclaredMethod(getName(), getMethodType().parameterArray());
/* 118 */     }  if (MethodHandleNatives.refKindIsConstructor(b)) {
/* 119 */       if (bool) {
/* 120 */         return clazz.getConstructor(getMethodType().parameterArray());
/*     */       }
/* 122 */       return clazz.getDeclaredConstructor(getMethodType().parameterArray());
/* 123 */     }  if (MethodHandleNatives.refKindIsField(b)) {
/* 124 */       if (bool) {
/* 125 */         return clazz.getField(getName());
/*     */       }
/* 127 */       return clazz.getDeclaredField(getName());
/*     */     } 
/* 129 */     throw new IllegalArgumentException("referenceKind=" + b);
/*     */   }
/*     */ 
/*     */   
/*     */   private static MemberName convertToMemberName(byte paramByte, Member paramMember) throws IllegalAccessException {
/* 134 */     if (paramMember instanceof Method) {
/* 135 */       boolean bool = (paramByte == 7) ? true : false;
/* 136 */       return new MemberName((Method)paramMember, bool);
/* 137 */     }  if (paramMember instanceof Constructor)
/* 138 */       return new MemberName((Constructor)paramMember); 
/* 139 */     if (paramMember instanceof Field) {
/* 140 */       boolean bool = (paramByte == 3 || paramByte == 4) ? true : false;
/* 141 */       return new MemberName((Field)paramMember, bool);
/*     */     } 
/* 143 */     throw new InternalError(paramMember.getClass().getName());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/invoke/InfoFromMemberName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */