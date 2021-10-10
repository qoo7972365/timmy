/*     */ package javax.tools;
/*     */ 
/*     */ import java.io.CharArrayReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.Reader;
/*     */ import java.io.StringReader;
/*     */ import java.io.Writer;
/*     */ import java.net.URI;
/*     */ import java.nio.CharBuffer;
/*     */ import javax.lang.model.element.Modifier;
/*     */ import javax.lang.model.element.NestingKind;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleJavaFileObject
/*     */   implements JavaFileObject
/*     */ {
/*     */   protected final URI uri;
/*     */   protected final JavaFileObject.Kind kind;
/*     */   
/*     */   protected SimpleJavaFileObject(URI paramURI, JavaFileObject.Kind paramKind) {
/*  65 */     paramURI.getClass();
/*  66 */     paramKind.getClass();
/*  67 */     if (paramURI.getPath() == null)
/*  68 */       throw new IllegalArgumentException("URI must have a path: " + paramURI); 
/*  69 */     this.uri = paramURI;
/*  70 */     this.kind = paramKind;
/*     */   }
/*     */   
/*     */   public URI toUri() {
/*  74 */     return this.uri;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  78 */     return toUri().getPath();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream openInputStream() throws IOException {
/*  88 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OutputStream openOutputStream() throws IOException {
/*  98 */     throw new UnsupportedOperationException();
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
/*     */   public Reader openReader(boolean paramBoolean) throws IOException {
/* 113 */     CharSequence charSequence = getCharContent(paramBoolean);
/* 114 */     if (charSequence == null)
/* 115 */       throw new UnsupportedOperationException(); 
/* 116 */     if (charSequence instanceof CharBuffer) {
/* 117 */       CharBuffer charBuffer = (CharBuffer)charSequence;
/* 118 */       if (charBuffer.hasArray())
/* 119 */         return new CharArrayReader(charBuffer.array()); 
/*     */     } 
/* 121 */     return new StringReader(charSequence.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CharSequence getCharContent(boolean paramBoolean) throws IOException {
/* 131 */     throw new UnsupportedOperationException();
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
/*     */   public Writer openWriter() throws IOException {
/* 145 */     return new OutputStreamWriter(openOutputStream());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getLastModified() {
/* 156 */     return 0L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean delete() {
/* 167 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JavaFileObject.Kind getKind() {
/* 174 */     return this.kind;
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
/*     */   public boolean isNameCompatible(String paramString, JavaFileObject.Kind paramKind) {
/* 192 */     String str = paramString + paramKind.extension;
/* 193 */     return (paramKind.equals(getKind()) && (str
/* 194 */       .equals(toUri().getPath()) || 
/* 195 */       toUri().getPath().endsWith("/" + str)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NestingKind getNestingKind() {
/* 203 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Modifier getAccessLevel() {
/* 210 */     return null;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 214 */     return getClass().getName() + "[" + toUri() + "]";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/tools/SimpleJavaFileObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */