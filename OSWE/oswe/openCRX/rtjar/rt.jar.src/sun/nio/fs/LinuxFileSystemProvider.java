/*     */ package sun.nio.fs;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.file.FileStore;
/*     */ import java.nio.file.LinkOption;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ import java.nio.file.attribute.DosFileAttributeView;
/*     */ import java.nio.file.attribute.DosFileAttributes;
/*     */ import java.nio.file.attribute.UserDefinedFileAttributeView;
/*     */ import java.nio.file.spi.FileTypeDetector;
/*     */ import java.security.AccessController;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LinuxFileSystemProvider
/*     */   extends UnixFileSystemProvider
/*     */ {
/*     */   LinuxFileSystem newFileSystem(String paramString) {
/*  46 */     return new LinuxFileSystem(this, paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   LinuxFileStore getFileStore(UnixPath paramUnixPath) throws IOException {
/*  51 */     return new LinuxFileStore(paramUnixPath);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <V extends java.nio.file.attribute.FileAttributeView> V getFileAttributeView(Path paramPath, Class<V> paramClass, LinkOption... paramVarArgs) {
/*  60 */     if (paramClass == DosFileAttributeView.class) {
/*  61 */       return (V)new LinuxDosFileAttributeView(UnixPath.toUnixPath(paramPath), 
/*  62 */           Util.followLinks(paramVarArgs));
/*     */     }
/*  64 */     if (paramClass == UserDefinedFileAttributeView.class) {
/*  65 */       return (V)new LinuxUserDefinedFileAttributeView(UnixPath.toUnixPath(paramPath), 
/*  66 */           Util.followLinks(paramVarArgs));
/*     */     }
/*  68 */     return super.getFileAttributeView(paramPath, paramClass, paramVarArgs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DynamicFileAttributeView getFileAttributeView(Path paramPath, String paramString, LinkOption... paramVarArgs) {
/*  76 */     if (paramString.equals("dos")) {
/*  77 */       return new LinuxDosFileAttributeView(UnixPath.toUnixPath(paramPath), 
/*  78 */           Util.followLinks(paramVarArgs));
/*     */     }
/*  80 */     if (paramString.equals("user")) {
/*  81 */       return new LinuxUserDefinedFileAttributeView(UnixPath.toUnixPath(paramPath), 
/*  82 */           Util.followLinks(paramVarArgs));
/*     */     }
/*  84 */     return super.getFileAttributeView(paramPath, paramString, paramVarArgs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <A extends java.nio.file.attribute.BasicFileAttributes> A readAttributes(Path paramPath, Class<A> paramClass, LinkOption... paramVarArgs) throws IOException {
/*  94 */     if (paramClass == DosFileAttributes.class) {
/*     */       
/*  96 */       DosFileAttributeView dosFileAttributeView = getFileAttributeView(paramPath, DosFileAttributeView.class, paramVarArgs);
/*  97 */       return (A)dosFileAttributeView.readAttributes();
/*     */     } 
/*  99 */     return super.readAttributes(paramPath, paramClass, paramVarArgs);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   FileTypeDetector getFileTypeDetector() {
/* 105 */     Path path1 = Paths.get(AccessController.<String>doPrivileged(new GetPropertyAction("user.home")), new String[] { ".mime.types" });
/*     */     
/* 107 */     Path path2 = Paths.get("/etc/mime.types", new String[0]);
/*     */     
/* 109 */     return chain(new AbstractFileTypeDetector[] { new GnomeFileTypeDetector(), new MimeTypesFileTypeDetector(path1), new MimeTypesFileTypeDetector(path2), new MagicFileTypeDetector() });
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/fs/LinuxFileSystemProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */