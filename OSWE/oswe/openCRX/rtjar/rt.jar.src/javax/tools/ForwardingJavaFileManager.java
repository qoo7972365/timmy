/*     */ package javax.tools;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Iterator;
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
/*     */ public class ForwardingJavaFileManager<M extends JavaFileManager>
/*     */   implements JavaFileManager
/*     */ {
/*     */   protected final M fileManager;
/*     */   
/*     */   protected ForwardingJavaFileManager(M paramM) {
/*  54 */     paramM.getClass();
/*  55 */     this.fileManager = paramM;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClassLoader getClassLoader(JavaFileManager.Location paramLocation) {
/*  63 */     return this.fileManager.getClassLoader(paramLocation);
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
/*     */   public Iterable<JavaFileObject> list(JavaFileManager.Location paramLocation, String paramString, Set<JavaFileObject.Kind> paramSet, boolean paramBoolean) throws IOException {
/*  76 */     return this.fileManager.list(paramLocation, paramString, paramSet, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String inferBinaryName(JavaFileManager.Location paramLocation, JavaFileObject paramJavaFileObject) {
/*  83 */     return this.fileManager.inferBinaryName(paramLocation, paramJavaFileObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSameFile(FileObject paramFileObject1, FileObject paramFileObject2) {
/*  90 */     return this.fileManager.isSameFile(paramFileObject1, paramFileObject2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean handleOption(String paramString, Iterator<String> paramIterator) {
/*  98 */     return this.fileManager.handleOption(paramString, paramIterator);
/*     */   }
/*     */   
/*     */   public boolean hasLocation(JavaFileManager.Location paramLocation) {
/* 102 */     return this.fileManager.hasLocation(paramLocation);
/*     */   }
/*     */   
/*     */   public int isSupportedOption(String paramString) {
/* 106 */     return this.fileManager.isSupportedOption(paramString);
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
/*     */   public JavaFileObject getJavaFileForInput(JavaFileManager.Location paramLocation, String paramString, JavaFileObject.Kind paramKind) throws IOException {
/* 118 */     return this.fileManager.getJavaFileForInput(paramLocation, paramString, paramKind);
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
/*     */   public JavaFileObject getJavaFileForOutput(JavaFileManager.Location paramLocation, String paramString, JavaFileObject.Kind paramKind, FileObject paramFileObject) throws IOException {
/* 131 */     return this.fileManager.getJavaFileForOutput(paramLocation, paramString, paramKind, paramFileObject);
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
/*     */   public FileObject getFileForInput(JavaFileManager.Location paramLocation, String paramString1, String paramString2) throws IOException {
/* 143 */     return this.fileManager.getFileForInput(paramLocation, paramString1, paramString2);
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
/*     */   public FileObject getFileForOutput(JavaFileManager.Location paramLocation, String paramString1, String paramString2, FileObject paramFileObject) throws IOException {
/* 156 */     return this.fileManager.getFileForOutput(paramLocation, paramString1, paramString2, paramFileObject);
/*     */   }
/*     */   
/*     */   public void flush() throws IOException {
/* 160 */     this.fileManager.flush();
/*     */   }
/*     */   
/*     */   public void close() throws IOException {
/* 164 */     this.fileManager.close();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/tools/ForwardingJavaFileManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */