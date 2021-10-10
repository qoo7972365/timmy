/*      */ package java.nio.file;
/*      */ 
/*      */ import java.io.BufferedReader;
/*      */ import java.io.BufferedWriter;
/*      */ import java.io.Closeable;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.OutputStream;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.io.UncheckedIOException;
/*      */ import java.nio.channels.Channels;
/*      */ import java.nio.channels.SeekableByteChannel;
/*      */ import java.nio.charset.Charset;
/*      */ import java.nio.charset.CharsetDecoder;
/*      */ import java.nio.charset.CharsetEncoder;
/*      */ import java.nio.charset.StandardCharsets;
/*      */ import java.nio.file.attribute.BasicFileAttributeView;
/*      */ import java.nio.file.attribute.BasicFileAttributes;
/*      */ import java.nio.file.attribute.FileAttribute;
/*      */ import java.nio.file.attribute.FileOwnerAttributeView;
/*      */ import java.nio.file.attribute.FileTime;
/*      */ import java.nio.file.attribute.PosixFileAttributeView;
/*      */ import java.nio.file.attribute.PosixFileAttributes;
/*      */ import java.nio.file.attribute.PosixFilePermission;
/*      */ import java.nio.file.attribute.UserPrincipal;
/*      */ import java.nio.file.spi.FileSystemProvider;
/*      */ import java.nio.file.spi.FileTypeDetector;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.EnumSet;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import java.util.ServiceLoader;
/*      */ import java.util.Set;
/*      */ import java.util.Spliterators;
/*      */ import java.util.function.BiPredicate;
/*      */ import java.util.stream.Stream;
/*      */ import java.util.stream.StreamSupport;
/*      */ import sun.nio.fs.DefaultFileTypeDetector;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class Files
/*      */ {
/*      */   private static final int BUFFER_SIZE = 8192;
/*      */   private static final int MAX_BUFFER_SIZE = 2147483639;
/*      */   
/*      */   private static FileSystemProvider provider(Path paramPath) {
/*   97 */     return paramPath.getFileSystem().provider();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Runnable asUncheckedRunnable(Closeable paramCloseable) {
/*  105 */     return () -> {
/*      */         try {
/*      */           paramCloseable.close();
/*  108 */         } catch (IOException iOException) {
/*      */           throw new UncheckedIOException(iOException);
/*      */         } 
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static InputStream newInputStream(Path paramPath, OpenOption... paramVarArgs) throws IOException {
/*  152 */     return provider(paramPath).newInputStream(paramPath, paramVarArgs);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static OutputStream newOutputStream(Path paramPath, OpenOption... paramVarArgs) throws IOException {
/*  216 */     return provider(paramPath).newOutputStream(paramPath, paramVarArgs);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SeekableByteChannel newByteChannel(Path paramPath, Set<? extends OpenOption> paramSet, FileAttribute<?>... paramVarArgs) throws IOException {
/*  361 */     return provider(paramPath).newByteChannel(paramPath, paramSet, paramVarArgs);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SeekableByteChannel newByteChannel(Path paramPath, OpenOption... paramVarArgs) throws IOException {
/*  405 */     HashSet<? super OpenOption> hashSet = new HashSet(paramVarArgs.length);
/*  406 */     Collections.addAll(hashSet, paramVarArgs);
/*  407 */     return newByteChannel(paramPath, (Set)hashSet, (FileAttribute<?>[])new FileAttribute[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class AcceptAllFilter
/*      */     implements DirectoryStream.Filter<Path>
/*      */   {
/*      */     public boolean accept(Path param1Path) {
/*  418 */       return true;
/*      */     }
/*  420 */     static final AcceptAllFilter FILTER = new AcceptAllFilter();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DirectoryStream<Path> newDirectoryStream(Path paramPath) throws IOException {
/*  457 */     return provider(paramPath).newDirectoryStream(paramPath, AcceptAllFilter.FILTER);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DirectoryStream<Path> newDirectoryStream(Path paramPath, String paramString) throws IOException {
/*  513 */     if (paramString.equals("*")) {
/*  514 */       return newDirectoryStream(paramPath);
/*      */     }
/*      */     
/*  517 */     FileSystem fileSystem = paramPath.getFileSystem();
/*  518 */     final PathMatcher matcher = fileSystem.getPathMatcher("glob:" + paramString);
/*  519 */     DirectoryStream.Filter<Path> filter = new DirectoryStream.Filter<Path>()
/*      */       {
/*      */         public boolean accept(Path param1Path) {
/*  522 */           return matcher.matches(param1Path.getFileName());
/*      */         }
/*      */       };
/*  525 */     return fileSystem.provider().newDirectoryStream(paramPath, filter);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DirectoryStream<Path> newDirectoryStream(Path paramPath, DirectoryStream.Filter<? super Path> paramFilter) throws IOException {
/*  589 */     return provider(paramPath).newDirectoryStream(paramPath, paramFilter);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Path createFile(Path paramPath, FileAttribute<?>... paramVarArgs) throws IOException {
/*  631 */     EnumSet<StandardOpenOption> enumSet = EnumSet.of(StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
/*  632 */     newByteChannel(paramPath, (Set)enumSet, paramVarArgs).close();
/*  633 */     return paramPath;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Path createDirectory(Path paramPath, FileAttribute<?>... paramVarArgs) throws IOException {
/*  674 */     provider(paramPath).createDirectory(paramPath, paramVarArgs);
/*  675 */     return paramPath;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Path createDirectories(Path paramPath, FileAttribute<?>... paramVarArgs) throws IOException {
/*      */     try {
/*  727 */       createAndCheckIsDirectory(paramPath, paramVarArgs);
/*  728 */       return paramPath;
/*  729 */     } catch (FileAlreadyExistsException fileAlreadyExistsException) {
/*      */       
/*  731 */       throw fileAlreadyExistsException;
/*  732 */     } catch (IOException iOException) {
/*      */       SecurityException securityException;
/*      */       
/*  735 */       iOException = null;
/*      */       try {
/*  737 */         paramPath = paramPath.toAbsolutePath();
/*  738 */       } catch (SecurityException securityException1) {
/*      */         
/*  740 */         securityException = securityException1;
/*      */       } 
/*      */       
/*  743 */       Path path1 = paramPath.getParent();
/*  744 */       while (path1 != null) {
/*      */         try {
/*  746 */           provider(path1).checkAccess(path1, new AccessMode[0]);
/*      */           break;
/*  748 */         } catch (NoSuchFileException noSuchFileException) {
/*      */ 
/*      */           
/*  751 */           path1 = path1.getParent();
/*      */         } 
/*  753 */       }  if (path1 == null) {
/*      */         
/*  755 */         if (securityException == null) {
/*  756 */           throw new FileSystemException(paramPath.toString(), null, "Unable to determine if root directory exists");
/*      */         }
/*      */         
/*  759 */         throw securityException;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  764 */       Path path2 = path1;
/*  765 */       for (Path path : path1.relativize(paramPath)) {
/*  766 */         path2 = path2.resolve(path);
/*  767 */         createAndCheckIsDirectory(path2, paramVarArgs);
/*      */       } 
/*  769 */       return paramPath;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void createAndCheckIsDirectory(Path paramPath, FileAttribute<?>... paramVarArgs) throws IOException {
/*      */     try {
/*  781 */       createDirectory(paramPath, paramVarArgs);
/*  782 */     } catch (FileAlreadyExistsException fileAlreadyExistsException) {
/*  783 */       if (!isDirectory(paramPath, new LinkOption[] { LinkOption.NOFOLLOW_LINKS })) {
/*  784 */         throw fileAlreadyExistsException;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Path createTempFile(Path paramPath, String paramString1, String paramString2, FileAttribute<?>... paramVarArgs) throws IOException {
/*  852 */     return TempFileHelper.createTempFile(Objects.<Path>requireNonNull(paramPath), paramString1, paramString2, paramVarArgs);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Path createTempFile(String paramString1, String paramString2, FileAttribute<?>... paramVarArgs) throws IOException {
/*  897 */     return TempFileHelper.createTempFile(null, paramString1, paramString2, paramVarArgs);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Path createTempDirectory(Path paramPath, String paramString, FileAttribute<?>... paramVarArgs) throws IOException {
/*  950 */     return TempFileHelper.createTempDirectory(Objects.<Path>requireNonNull(paramPath), paramString, paramVarArgs);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Path createTempDirectory(String paramString, FileAttribute<?>... paramVarArgs) throws IOException {
/*  991 */     return TempFileHelper.createTempDirectory(null, paramString, paramVarArgs);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Path createSymbolicLink(Path paramPath1, Path paramPath2, FileAttribute<?>... paramVarArgs) throws IOException {
/* 1043 */     provider(paramPath1).createSymbolicLink(paramPath1, paramPath2, paramVarArgs);
/* 1044 */     return paramPath1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Path createLink(Path paramPath1, Path paramPath2) throws IOException {
/* 1086 */     provider(paramPath1).createLink(paramPath1, paramPath2);
/* 1087 */     return paramPath1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void delete(Path paramPath) throws IOException {
/* 1126 */     provider(paramPath).delete(paramPath);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean deleteIfExists(Path paramPath) throws IOException {
/* 1165 */     return provider(paramPath).deleteIfExists(paramPath);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Path copy(Path paramPath1, Path paramPath2, CopyOption... paramVarArgs) throws IOException {
/* 1271 */     FileSystemProvider fileSystemProvider = provider(paramPath1);
/* 1272 */     if (provider(paramPath2) == fileSystemProvider) {
/*      */       
/* 1274 */       fileSystemProvider.copy(paramPath1, paramPath2, paramVarArgs);
/*      */     } else {
/*      */       
/* 1277 */       CopyMoveHelper.copyToForeignTarget(paramPath1, paramPath2, paramVarArgs);
/*      */     } 
/* 1279 */     return paramPath2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Path move(Path paramPath1, Path paramPath2, CopyOption... paramVarArgs) throws IOException {
/* 1392 */     FileSystemProvider fileSystemProvider = provider(paramPath1);
/* 1393 */     if (provider(paramPath2) == fileSystemProvider) {
/*      */       
/* 1395 */       fileSystemProvider.move(paramPath1, paramPath2, paramVarArgs);
/*      */     } else {
/*      */       
/* 1398 */       CopyMoveHelper.moveToForeignTarget(paramPath1, paramPath2, paramVarArgs);
/*      */     } 
/* 1400 */     return paramPath2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Path readSymbolicLink(Path paramPath) throws IOException {
/* 1432 */     return provider(paramPath).readSymbolicLink(paramPath);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FileStore getFileStore(Path paramPath) throws IOException {
/* 1461 */     return provider(paramPath).getFileStore(paramPath);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSameFile(Path paramPath1, Path paramPath2) throws IOException {
/* 1504 */     return provider(paramPath1).isSameFile(paramPath1, paramPath2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isHidden(Path paramPath) throws IOException {
/* 1531 */     return provider(paramPath).isHidden(paramPath);
/*      */   }
/*      */ 
/*      */   
/*      */   private static class FileTypeDetectors
/*      */   {
/* 1537 */     static final FileTypeDetector defaultFileTypeDetector = createDefaultFileTypeDetector();
/*      */     
/* 1539 */     static final List<FileTypeDetector> installeDetectors = loadInstalledDetectors();
/*      */ 
/*      */     
/*      */     private static FileTypeDetector createDefaultFileTypeDetector() {
/* 1543 */       return 
/* 1544 */         AccessController.<FileTypeDetector>doPrivileged(new PrivilegedAction<FileTypeDetector>() {
/*      */             public FileTypeDetector run() {
/* 1546 */               return DefaultFileTypeDetector.create();
/*      */             }
/*      */           });
/*      */     }
/*      */     
/*      */     private static List<FileTypeDetector> loadInstalledDetectors() {
/* 1552 */       return 
/* 1553 */         AccessController.<List<FileTypeDetector>>doPrivileged(new PrivilegedAction<List<FileTypeDetector>>() {
/*      */             public List<FileTypeDetector> run() {
/* 1555 */               ArrayList<FileTypeDetector> arrayList = new ArrayList();
/*      */               
/* 1557 */               ServiceLoader<FileTypeDetector> serviceLoader = ServiceLoader.load(FileTypeDetector.class, ClassLoader.getSystemClassLoader());
/* 1558 */               for (FileTypeDetector fileTypeDetector : serviceLoader) {
/* 1559 */                 arrayList.add(fileTypeDetector);
/*      */               }
/* 1561 */               return arrayList;
/*      */             }
/*      */           });
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String probeContentType(Path paramPath) throws IOException {
/* 1617 */     for (FileTypeDetector fileTypeDetector : FileTypeDetectors.installeDetectors) {
/* 1618 */       String str = fileTypeDetector.probeContentType(paramPath);
/* 1619 */       if (str != null) {
/* 1620 */         return str;
/*      */       }
/*      */     } 
/*      */     
/* 1624 */     return FileTypeDetectors.defaultFileTypeDetector.probeContentType(paramPath);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <V extends java.nio.file.attribute.FileAttributeView> V getFileAttributeView(Path paramPath, Class<V> paramClass, LinkOption... paramVarArgs) {
/* 1675 */     return provider(paramPath).getFileAttributeView(paramPath, paramClass, paramVarArgs);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <A extends BasicFileAttributes> A readAttributes(Path paramPath, Class<A> paramClass, LinkOption... paramVarArgs) throws IOException {
/* 1737 */     return provider(paramPath).readAttributes(paramPath, paramClass, paramVarArgs);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Path setAttribute(Path paramPath, String paramString, Object paramObject, LinkOption... paramVarArgs) throws IOException {
/* 1805 */     provider(paramPath).setAttribute(paramPath, paramString, paramObject, paramVarArgs);
/* 1806 */     return paramPath;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Object getAttribute(Path paramPath, String paramString, LinkOption... paramVarArgs) throws IOException {
/*      */     String str;
/* 1867 */     if (paramString.indexOf('*') >= 0 || paramString.indexOf(',') >= 0)
/* 1868 */       throw new IllegalArgumentException(paramString); 
/* 1869 */     Map<String, Object> map = readAttributes(paramPath, paramString, paramVarArgs);
/* 1870 */     assert map.size() == 1;
/*      */     
/* 1872 */     int i = paramString.indexOf(':');
/* 1873 */     if (i == -1) {
/* 1874 */       str = paramString;
/*      */     } else {
/* 1876 */       str = (i == paramString.length()) ? "" : paramString.substring(i + 1);
/*      */     } 
/* 1878 */     return map.get(str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Map<String, Object> readAttributes(Path paramPath, String paramString, LinkOption... paramVarArgs) throws IOException {
/* 1964 */     return provider(paramPath).readAttributes(paramPath, paramString, paramVarArgs);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Set<PosixFilePermission> getPosixFilePermissions(Path paramPath, LinkOption... paramVarArgs) throws IOException {
/* 2004 */     return ((PosixFileAttributes)readAttributes(paramPath, PosixFileAttributes.class, paramVarArgs)).permissions();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Path setPosixFilePermissions(Path paramPath, Set<PosixFilePermission> paramSet) throws IOException {
/* 2042 */     PosixFileAttributeView posixFileAttributeView = getFileAttributeView(paramPath, PosixFileAttributeView.class, new LinkOption[0]);
/* 2043 */     if (posixFileAttributeView == null)
/* 2044 */       throw new UnsupportedOperationException(); 
/* 2045 */     posixFileAttributeView.setPermissions(paramSet);
/* 2046 */     return paramPath;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static UserPrincipal getOwner(Path paramPath, LinkOption... paramVarArgs) throws IOException {
/* 2076 */     FileOwnerAttributeView fileOwnerAttributeView = getFileAttributeView(paramPath, FileOwnerAttributeView.class, paramVarArgs);
/* 2077 */     if (fileOwnerAttributeView == null)
/* 2078 */       throw new UnsupportedOperationException(); 
/* 2079 */     return fileOwnerAttributeView.getOwner();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Path setOwner(Path paramPath, UserPrincipal paramUserPrincipal) throws IOException {
/* 2124 */     FileOwnerAttributeView fileOwnerAttributeView = getFileAttributeView(paramPath, FileOwnerAttributeView.class, new LinkOption[0]);
/* 2125 */     if (fileOwnerAttributeView == null)
/* 2126 */       throw new UnsupportedOperationException(); 
/* 2127 */     fileOwnerAttributeView.setOwner(paramUserPrincipal);
/* 2128 */     return paramPath;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSymbolicLink(Path paramPath) {
/*      */     try {
/* 2153 */       return readAttributes(paramPath, BasicFileAttributes.class, new LinkOption[] { LinkOption.NOFOLLOW_LINKS
/*      */           
/* 2155 */           }).isSymbolicLink();
/* 2156 */     } catch (IOException iOException) {
/* 2157 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isDirectory(Path paramPath, LinkOption... paramVarArgs) {
/*      */     try {
/* 2192 */       return readAttributes(paramPath, BasicFileAttributes.class, paramVarArgs).isDirectory();
/* 2193 */     } catch (IOException iOException) {
/* 2194 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isRegularFile(Path paramPath, LinkOption... paramVarArgs) {
/*      */     try {
/* 2229 */       return readAttributes(paramPath, BasicFileAttributes.class, paramVarArgs).isRegularFile();
/* 2230 */     } catch (IOException iOException) {
/* 2231 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FileTime getLastModifiedTime(Path paramPath, LinkOption... paramVarArgs) throws IOException {
/* 2266 */     return readAttributes(paramPath, BasicFileAttributes.class, paramVarArgs).lastModifiedTime();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Path setLastModifiedTime(Path paramPath, FileTime paramFileTime) throws IOException {
/* 2305 */     ((BasicFileAttributeView)getFileAttributeView(paramPath, BasicFileAttributeView.class, new LinkOption[0]))
/* 2306 */       .setTimes(paramFileTime, null, null);
/* 2307 */     return paramPath;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long size(Path paramPath) throws IOException {
/* 2332 */     return readAttributes(paramPath, BasicFileAttributes.class, new LinkOption[0]).size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean followLinks(LinkOption... paramVarArgs) {
/* 2341 */     boolean bool = true;
/* 2342 */     for (LinkOption linkOption : paramVarArgs) {
/* 2343 */       if (linkOption == LinkOption.NOFOLLOW_LINKS) {
/* 2344 */         bool = false;
/*      */       } else {
/*      */         
/* 2347 */         if (linkOption == null)
/* 2348 */           throw new NullPointerException(); 
/* 2349 */         throw new AssertionError("Should not get here");
/*      */       } 
/* 2351 */     }  return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean exists(Path paramPath, LinkOption... paramVarArgs) {
/*      */     try {
/* 2384 */       if (followLinks(paramVarArgs)) {
/* 2385 */         provider(paramPath).checkAccess(paramPath, new AccessMode[0]);
/*      */       } else {
/*      */         
/* 2388 */         readAttributes(paramPath, BasicFileAttributes.class, new LinkOption[] { LinkOption.NOFOLLOW_LINKS });
/*      */       } 
/*      */ 
/*      */       
/* 2392 */       return true;
/* 2393 */     } catch (IOException iOException) {
/*      */       
/* 2395 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean notExists(Path paramPath, LinkOption... paramVarArgs) {
/*      */     try {
/* 2433 */       if (followLinks(paramVarArgs)) {
/* 2434 */         provider(paramPath).checkAccess(paramPath, new AccessMode[0]);
/*      */       } else {
/*      */         
/* 2437 */         readAttributes(paramPath, BasicFileAttributes.class, new LinkOption[] { LinkOption.NOFOLLOW_LINKS });
/*      */       } 
/*      */ 
/*      */       
/* 2441 */       return false;
/* 2442 */     } catch (NoSuchFileException noSuchFileException) {
/*      */       
/* 2444 */       return true;
/* 2445 */     } catch (IOException iOException) {
/* 2446 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isAccessible(Path paramPath, AccessMode... paramVarArgs) {
/*      */     try {
/* 2455 */       provider(paramPath).checkAccess(paramPath, paramVarArgs);
/* 2456 */       return true;
/* 2457 */     } catch (IOException iOException) {
/* 2458 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isReadable(Path paramPath) {
/* 2490 */     return isAccessible(paramPath, new AccessMode[] { AccessMode.READ });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isWritable(Path paramPath) {
/* 2521 */     return isAccessible(paramPath, new AccessMode[] { AccessMode.WRITE });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isExecutable(Path paramPath) {
/* 2556 */     return isAccessible(paramPath, new AccessMode[] { AccessMode.EXECUTE });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Path walkFileTree(Path paramPath, Set<FileVisitOption> paramSet, int paramInt, FileVisitor<? super Path> paramFileVisitor) throws IOException {
/* 2661 */     try (FileTreeWalker null = new FileTreeWalker(paramSet, paramInt)) {
/* 2662 */       FileTreeWalker.Event event = fileTreeWalker.walk(paramPath); do {
/*      */         FileVisitResult fileVisitResult;
/*      */         IOException iOException;
/* 2665 */         switch (event.type()) {
/*      */           case ENTRY:
/* 2667 */             iOException = event.ioeException();
/* 2668 */             if (iOException == null) {
/* 2669 */               assert event.attributes() != null;
/* 2670 */               FileVisitResult fileVisitResult1 = paramFileVisitor.visitFile(event.file(), event.attributes()); break;
/*      */             } 
/* 2672 */             fileVisitResult = paramFileVisitor.visitFileFailed(event.file(), iOException);
/*      */             break;
/*      */ 
/*      */           
/*      */           case START_DIRECTORY:
/* 2677 */             fileVisitResult = paramFileVisitor.preVisitDirectory(event.file(), event.attributes());
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 2682 */             if (fileVisitResult == FileVisitResult.SKIP_SUBTREE || fileVisitResult == FileVisitResult.SKIP_SIBLINGS)
/*      */             {
/* 2684 */               fileTreeWalker.pop();
/*      */             }
/*      */             break;
/*      */           case END_DIRECTORY:
/* 2688 */             fileVisitResult = paramFileVisitor.postVisitDirectory(event.file(), event.ioeException());
/*      */ 
/*      */             
/* 2691 */             if (fileVisitResult == FileVisitResult.SKIP_SIBLINGS) {
/* 2692 */               fileVisitResult = FileVisitResult.CONTINUE;
/*      */             }
/*      */             break;
/*      */           default:
/* 2696 */             throw new AssertionError("Should not get here");
/*      */         } 
/*      */         
/* 2699 */         if (Objects.requireNonNull(fileVisitResult) != FileVisitResult.CONTINUE) {
/* 2700 */           if (fileVisitResult == FileVisitResult.TERMINATE)
/*      */             break; 
/* 2702 */           if (fileVisitResult == FileVisitResult.SKIP_SIBLINGS) {
/* 2703 */             fileTreeWalker.skipRemainingSiblings();
/*      */           }
/*      */         } 
/* 2706 */         event = fileTreeWalker.next();
/* 2707 */       } while (event != null);
/*      */     } 
/*      */     
/* 2710 */     return paramPath;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Path walkFileTree(Path paramPath, FileVisitor<? super Path> paramFileVisitor) throws IOException {
/* 2742 */     return walkFileTree(paramPath, 
/* 2743 */         EnumSet.noneOf(FileVisitOption.class), 2147483647, paramFileVisitor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BufferedReader newBufferedReader(Path paramPath, Charset paramCharset) throws IOException {
/* 2783 */     CharsetDecoder charsetDecoder = paramCharset.newDecoder();
/* 2784 */     InputStreamReader inputStreamReader = new InputStreamReader(newInputStream(paramPath, new OpenOption[0]), charsetDecoder);
/* 2785 */     return new BufferedReader(inputStreamReader);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BufferedReader newBufferedReader(Path paramPath) throws IOException {
/* 2816 */     return newBufferedReader(paramPath, StandardCharsets.UTF_8);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BufferedWriter newBufferedWriter(Path paramPath, Charset paramCharset, OpenOption... paramVarArgs) throws IOException {
/* 2859 */     CharsetEncoder charsetEncoder = paramCharset.newEncoder();
/* 2860 */     OutputStreamWriter outputStreamWriter = new OutputStreamWriter(newOutputStream(paramPath, paramVarArgs), charsetEncoder);
/* 2861 */     return new BufferedWriter(outputStreamWriter);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BufferedWriter newBufferedWriter(Path paramPath, OpenOption... paramVarArgs) throws IOException {
/* 2896 */     return newBufferedWriter(paramPath, StandardCharsets.UTF_8, paramVarArgs);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static long copy(InputStream paramInputStream, OutputStream paramOutputStream) throws IOException {
/* 2905 */     long l = 0L;
/* 2906 */     byte[] arrayOfByte = new byte[8192];
/*      */     int i;
/* 2908 */     while ((i = paramInputStream.read(arrayOfByte)) > 0) {
/* 2909 */       paramOutputStream.write(arrayOfByte, 0, i);
/* 2910 */       l += i;
/*      */     } 
/* 2912 */     return l;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long copy(InputStream paramInputStream, Path paramPath, CopyOption... paramVarArgs) throws IOException {
/*      */     OutputStream outputStream1;
/* 2984 */     Objects.requireNonNull(paramInputStream);
/*      */ 
/*      */     
/* 2987 */     boolean bool = false;
/* 2988 */     for (CopyOption copyOption : paramVarArgs) {
/* 2989 */       if (copyOption == StandardCopyOption.REPLACE_EXISTING) {
/* 2990 */         bool = true;
/*      */       } else {
/* 2992 */         if (copyOption == null) {
/* 2993 */           throw new NullPointerException("options contains 'null'");
/*      */         }
/* 2995 */         throw new UnsupportedOperationException(copyOption + " not supported");
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 3001 */     SecurityException securityException = null;
/* 3002 */     if (bool) {
/*      */       try {
/* 3004 */         deleteIfExists(paramPath);
/* 3005 */       } catch (SecurityException securityException1) {
/* 3006 */         securityException = securityException1;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 3016 */       outputStream1 = newOutputStream(paramPath, new OpenOption[] { StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE });
/*      */     }
/* 3018 */     catch (FileAlreadyExistsException fileAlreadyExistsException) {
/* 3019 */       if (securityException != null) {
/* 3020 */         throw securityException;
/*      */       }
/* 3022 */       throw fileAlreadyExistsException;
/*      */     } 
/*      */ 
/*      */     
/* 3026 */     try (OutputStream null = outputStream1) {
/* 3027 */       return copy(paramInputStream, outputStream2);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long copy(Path paramPath, OutputStream paramOutputStream) throws IOException {
/* 3066 */     Objects.requireNonNull(paramOutputStream);
/*      */     
/* 3068 */     try (InputStream null = newInputStream(paramPath, new OpenOption[0])) {
/* 3069 */       return copy(inputStream, paramOutputStream);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static byte[] read(InputStream paramInputStream, int paramInt) throws IOException {
/* 3098 */     int i = paramInt;
/* 3099 */     byte[] arrayOfByte = new byte[i];
/* 3100 */     int j = 0;
/*      */     
/*      */     while (true) {
/*      */       int k;
/*      */       
/* 3105 */       while ((k = paramInputStream.read(arrayOfByte, j, i - j)) > 0) {
/* 3106 */         j += k;
/*      */       }
/*      */ 
/*      */       
/* 3110 */       if (k < 0 || (k = paramInputStream.read()) < 0) {
/*      */         break;
/*      */       }
/*      */       
/* 3114 */       if (i <= 2147483639 - i) {
/* 3115 */         i = Math.max(i << 1, 8192);
/*      */       } else {
/* 3117 */         if (i == 2147483639)
/* 3118 */           throw new OutOfMemoryError("Required array size too large"); 
/* 3119 */         i = 2147483639;
/*      */       } 
/* 3121 */       arrayOfByte = Arrays.copyOf(arrayOfByte, i);
/* 3122 */       arrayOfByte[j++] = (byte)k;
/*      */     } 
/* 3124 */     return (i == j) ? arrayOfByte : Arrays.copyOf(arrayOfByte, j);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[] readAllBytes(Path paramPath) throws IOException {
/* 3152 */     try(SeekableByteChannel null = newByteChannel(paramPath, new OpenOption[0]); 
/* 3153 */         InputStream null = Channels.newInputStream(seekableByteChannel)) {
/* 3154 */       long l = seekableByteChannel.size();
/* 3155 */       if (l > 2147483639L) {
/* 3156 */         throw new OutOfMemoryError("Required array size too large");
/*      */       }
/* 3158 */       return read(inputStream, (int)l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static List<String> readAllLines(Path paramPath, Charset paramCharset) throws IOException {
/* 3202 */     try (BufferedReader null = newBufferedReader(paramPath, paramCharset)) {
/* 3203 */       ArrayList<String> arrayList = new ArrayList();
/*      */       while (true) {
/* 3205 */         String str = bufferedReader.readLine();
/* 3206 */         if (str == null)
/*      */           break; 
/* 3208 */         arrayList.add(str);
/*      */       } 
/* 3210 */       return arrayList;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static List<String> readAllLines(Path paramPath) throws IOException {
/* 3242 */     return readAllLines(paramPath, StandardCharsets.UTF_8);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Path write(Path paramPath, byte[] paramArrayOfbyte, OpenOption... paramVarArgs) throws IOException {
/* 3290 */     Objects.requireNonNull(paramArrayOfbyte);
/*      */     
/* 3292 */     try (OutputStream null = newOutputStream(paramPath, paramVarArgs)) {
/* 3293 */       int i = paramArrayOfbyte.length;
/* 3294 */       int j = i;
/* 3295 */       while (j > 0) {
/* 3296 */         int k = Math.min(j, 8192);
/* 3297 */         outputStream.write(paramArrayOfbyte, i - j, k);
/* 3298 */         j -= k;
/*      */       } 
/*      */     } 
/* 3301 */     return paramPath;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Path write(Path paramPath, Iterable<? extends CharSequence> paramIterable, Charset paramCharset, OpenOption... paramVarArgs) throws IOException {
/* 3349 */     Objects.requireNonNull(paramIterable);
/* 3350 */     CharsetEncoder charsetEncoder = paramCharset.newEncoder();
/* 3351 */     OutputStream outputStream = newOutputStream(paramPath, paramVarArgs);
/* 3352 */     try (BufferedWriter null = new BufferedWriter(new OutputStreamWriter(outputStream, charsetEncoder))) {
/* 3353 */       for (CharSequence charSequence : paramIterable) {
/* 3354 */         bufferedWriter.append(charSequence);
/* 3355 */         bufferedWriter.newLine();
/*      */       } 
/*      */     } 
/* 3358 */     return paramPath;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Path write(Path paramPath, Iterable<? extends CharSequence> paramIterable, OpenOption... paramVarArgs) throws IOException {
/* 3397 */     return write(paramPath, paramIterable, StandardCharsets.UTF_8, paramVarArgs);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Stream<Path> list(Path paramPath) throws IOException {
/* 3451 */     DirectoryStream<Path> directoryStream = newDirectoryStream(paramPath);
/*      */     try {
/* 3453 */       final Iterator<Path> delegate = directoryStream.iterator();
/*      */ 
/*      */       
/* 3456 */       Iterator<Path> iterator2 = new Iterator<Path>()
/*      */         {
/*      */           public boolean hasNext() {
/*      */             try {
/* 3460 */               return delegate.hasNext();
/* 3461 */             } catch (DirectoryIteratorException directoryIteratorException) {
/* 3462 */               throw new UncheckedIOException(directoryIteratorException.getCause());
/*      */             } 
/*      */           }
/*      */           
/*      */           public Path next() {
/*      */             try {
/* 3468 */               return delegate.next();
/* 3469 */             } catch (DirectoryIteratorException directoryIteratorException) {
/* 3470 */               throw new UncheckedIOException(directoryIteratorException.getCause());
/*      */             } 
/*      */           }
/*      */         };
/*      */       
/* 3475 */       return StreamSupport.<Path>stream(Spliterators.spliteratorUnknownSize(iterator2, 1), false)
/* 3476 */         .onClose(asUncheckedRunnable(directoryStream));
/* 3477 */     } catch (Error|RuntimeException error) {
/*      */       try {
/* 3479 */         directoryStream.close();
/* 3480 */       } catch (IOException iOException) {
/*      */         try {
/* 3482 */           error.addSuppressed(iOException);
/* 3483 */         } catch (Throwable throwable) {}
/*      */       } 
/* 3485 */       throw error;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Stream<Path> walk(Path paramPath, int paramInt, FileVisitOption... paramVarArgs) throws IOException {
/* 3574 */     FileTreeIterator fileTreeIterator = new FileTreeIterator(paramPath, paramInt, paramVarArgs);
/*      */     try {
/* 3576 */       return StreamSupport.stream(Spliterators.spliteratorUnknownSize(fileTreeIterator, 1), false)
/* 3577 */         .onClose(fileTreeIterator::close)
/* 3578 */         .map(paramEvent -> paramEvent.file());
/* 3579 */     } catch (Error|RuntimeException error) {
/* 3580 */       fileTreeIterator.close();
/* 3581 */       throw error;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Stream<Path> walk(Path paramPath, FileVisitOption... paramVarArgs) throws IOException {
/* 3625 */     return walk(paramPath, 2147483647, paramVarArgs);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Stream<Path> find(Path paramPath, int paramInt, BiPredicate<Path, BasicFileAttributes> paramBiPredicate, FileVisitOption... paramVarArgs) throws IOException {
/* 3687 */     FileTreeIterator fileTreeIterator = new FileTreeIterator(paramPath, paramInt, paramVarArgs);
/*      */     try {
/* 3689 */       return StreamSupport.stream(Spliterators.spliteratorUnknownSize(fileTreeIterator, 1), false)
/* 3690 */         .onClose(fileTreeIterator::close)
/* 3691 */         .filter(paramEvent -> paramBiPredicate.test(paramEvent.file(), paramEvent.attributes()))
/* 3692 */         .map(paramEvent -> paramEvent.file());
/* 3693 */     } catch (Error|RuntimeException error) {
/* 3694 */       fileTreeIterator.close();
/* 3695 */       throw error;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Stream<String> lines(Path paramPath, Charset paramCharset) throws IOException {
/* 3744 */     BufferedReader bufferedReader = newBufferedReader(paramPath, paramCharset);
/*      */     try {
/* 3746 */       return bufferedReader.lines().onClose(asUncheckedRunnable(bufferedReader));
/* 3747 */     } catch (Error|RuntimeException error) {
/*      */       try {
/* 3749 */         bufferedReader.close();
/* 3750 */       } catch (IOException iOException) {
/*      */         try {
/* 3752 */           error.addSuppressed(iOException);
/* 3753 */         } catch (Throwable throwable) {}
/*      */       } 
/* 3755 */       throw error;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Stream<String> lines(Path paramPath) throws IOException {
/* 3785 */     return lines(paramPath, StandardCharsets.UTF_8);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/nio/file/Files.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */