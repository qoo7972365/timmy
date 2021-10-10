/*    */ package sun.nio.fs;
/*    */ 
/*    */ import java.nio.file.attribute.FileAttribute;
/*    */ import java.nio.file.attribute.PosixFilePermission;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class UnixFileModeAttribute
/*    */ {
/*    */   static final int ALL_PERMISSIONS = 511;
/*    */   static final int ALL_READWRITE = 438;
/*    */   static final int TEMPFILE_PERMISSIONS = 448;
/*    */   
/*    */   static int toUnixMode(Set<PosixFilePermission> paramSet) {
/* 49 */     int i = 0;
/* 50 */     for (PosixFilePermission posixFilePermission : paramSet) {
/* 51 */       if (posixFilePermission == null)
/* 52 */         throw new NullPointerException(); 
/* 53 */       switch (posixFilePermission) { case OWNER_READ:
/* 54 */           i |= 0x100;
/* 55 */         case OWNER_WRITE: i |= 0x80;
/* 56 */         case OWNER_EXECUTE: i |= 0x40;
/* 57 */         case GROUP_READ: i |= 0x20;
/* 58 */         case GROUP_WRITE: i |= 0x10;
/* 59 */         case GROUP_EXECUTE: i |= 0x8;
/* 60 */         case OTHERS_READ: i |= 0x4;
/* 61 */         case OTHERS_WRITE: i |= 0x2;
/* 62 */         case OTHERS_EXECUTE: i |= 0x1; }
/*    */     
/*    */     } 
/* 65 */     return i;
/*    */   }
/*    */ 
/*    */   
/*    */   static int toUnixMode(int paramInt, FileAttribute<?>... paramVarArgs) {
/* 70 */     int i = paramInt;
/* 71 */     for (FileAttribute<?> fileAttribute : paramVarArgs) {
/* 72 */       String str = fileAttribute.name();
/* 73 */       if (!str.equals("posix:permissions") && !str.equals("unix:permissions")) {
/* 74 */         throw new UnsupportedOperationException("'" + fileAttribute.name() + "' not supported as initial attribute");
/*    */       }
/*    */       
/* 77 */       i = toUnixMode((Set<PosixFilePermission>)fileAttribute.value());
/*    */     } 
/* 79 */     return i;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/fs/UnixFileModeAttribute.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */