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
/*     */ public final class PosixFilePermissions
/*     */ {
/*     */   private static void writeBits(StringBuilder paramStringBuilder, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3) {
/*  43 */     if (paramBoolean1) {
/*  44 */       paramStringBuilder.append('r');
/*     */     } else {
/*  46 */       paramStringBuilder.append('-');
/*     */     } 
/*  48 */     if (paramBoolean2) {
/*  49 */       paramStringBuilder.append('w');
/*     */     } else {
/*  51 */       paramStringBuilder.append('-');
/*     */     } 
/*  53 */     if (paramBoolean3) {
/*  54 */       paramStringBuilder.append('x');
/*     */     } else {
/*  56 */       paramStringBuilder.append('-');
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
/*     */   
/*     */   public static String toString(Set<PosixFilePermission> paramSet) {
/*  74 */     StringBuilder stringBuilder = new StringBuilder(9);
/*  75 */     writeBits(stringBuilder, paramSet.contains(PosixFilePermission.OWNER_READ), paramSet.contains(PosixFilePermission.OWNER_WRITE), paramSet
/*  76 */         .contains(PosixFilePermission.OWNER_EXECUTE));
/*  77 */     writeBits(stringBuilder, paramSet.contains(PosixFilePermission.GROUP_READ), paramSet.contains(PosixFilePermission.GROUP_WRITE), paramSet
/*  78 */         .contains(PosixFilePermission.GROUP_EXECUTE));
/*  79 */     writeBits(stringBuilder, paramSet.contains(PosixFilePermission.OTHERS_READ), paramSet.contains(PosixFilePermission.OTHERS_WRITE), paramSet
/*  80 */         .contains(PosixFilePermission.OTHERS_EXECUTE));
/*  81 */     return stringBuilder.toString();
/*     */   }
/*     */   
/*     */   private static boolean isSet(char paramChar1, char paramChar2) {
/*  85 */     if (paramChar1 == paramChar2)
/*  86 */       return true; 
/*  87 */     if (paramChar1 == '-')
/*  88 */       return false; 
/*  89 */     throw new IllegalArgumentException("Invalid mode");
/*     */   }
/*  91 */   private static boolean isR(char paramChar) { return isSet(paramChar, 'r'); }
/*  92 */   private static boolean isW(char paramChar) { return isSet(paramChar, 'w'); } private static boolean isX(char paramChar) {
/*  93 */     return isSet(paramChar, 'x');
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Set<PosixFilePermission> fromString(String paramString) {
/* 127 */     if (paramString.length() != 9)
/* 128 */       throw new IllegalArgumentException("Invalid mode"); 
/* 129 */     EnumSet<PosixFilePermission> enumSet = EnumSet.noneOf(PosixFilePermission.class);
/* 130 */     if (isR(paramString.charAt(0))) enumSet.add(PosixFilePermission.OWNER_READ); 
/* 131 */     if (isW(paramString.charAt(1))) enumSet.add(PosixFilePermission.OWNER_WRITE); 
/* 132 */     if (isX(paramString.charAt(2))) enumSet.add(PosixFilePermission.OWNER_EXECUTE); 
/* 133 */     if (isR(paramString.charAt(3))) enumSet.add(PosixFilePermission.GROUP_READ); 
/* 134 */     if (isW(paramString.charAt(4))) enumSet.add(PosixFilePermission.GROUP_WRITE); 
/* 135 */     if (isX(paramString.charAt(5))) enumSet.add(PosixFilePermission.GROUP_EXECUTE); 
/* 136 */     if (isR(paramString.charAt(6))) enumSet.add(PosixFilePermission.OTHERS_READ); 
/* 137 */     if (isW(paramString.charAt(7))) enumSet.add(PosixFilePermission.OTHERS_WRITE); 
/* 138 */     if (isX(paramString.charAt(8))) enumSet.add(PosixFilePermission.OTHERS_EXECUTE); 
/* 139 */     return enumSet;
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
/*     */   public static FileAttribute<Set<PosixFilePermission>> asFileAttribute(Set<PosixFilePermission> paramSet) {
/* 163 */     paramSet = new HashSet<>(paramSet);
/* 164 */     for (PosixFilePermission posixFilePermission : paramSet) {
/* 165 */       if (posixFilePermission == null)
/* 166 */         throw new NullPointerException(); 
/*     */     } 
/* 168 */     final Set<PosixFilePermission> value = paramSet;
/* 169 */     return new FileAttribute<Set<PosixFilePermission>>()
/*     */       {
/*     */         public String name() {
/* 172 */           return "posix:permissions";
/*     */         }
/*     */         
/*     */         public Set<PosixFilePermission> value() {
/* 176 */           return Collections.unmodifiableSet(value);
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/nio/file/attribute/PosixFilePermissions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */