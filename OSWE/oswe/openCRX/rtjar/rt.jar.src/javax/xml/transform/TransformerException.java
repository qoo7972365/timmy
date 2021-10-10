/*     */ package javax.xml.transform;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.CodeSigner;
/*     */ import java.security.CodeSource;
/*     */ import java.security.PermissionCollection;
/*     */ import java.security.Permissions;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.ProtectionDomain;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TransformerException
/*     */   extends Exception
/*     */ {
/*     */   private static final long serialVersionUID = 975798773772956428L;
/*     */   SourceLocator locator;
/*     */   Throwable containedException;
/*     */   
/*     */   public SourceLocator getLocator() {
/*  58 */     return this.locator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLocator(SourceLocator location) {
/*  68 */     this.locator = location;
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
/*     */   public Throwable getException() {
/*  81 */     return this.containedException;
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
/*     */   public Throwable getCause() {
/*  93 */     return (this.containedException == this) ? null : this.containedException;
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
/*     */   public synchronized Throwable initCause(Throwable cause) {
/* 125 */     if (this.containedException != null) {
/* 126 */       throw new IllegalStateException("Can't overwrite cause");
/*     */     }
/*     */     
/* 129 */     if (cause == this) {
/* 130 */       throw new IllegalArgumentException("Self-causation not permitted");
/*     */     }
/*     */ 
/*     */     
/* 134 */     this.containedException = cause;
/*     */     
/* 136 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TransformerException(String message) {
/* 145 */     this(message, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TransformerException(Throwable e) {
/* 154 */     this(null, null, e);
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
/*     */   public TransformerException(String message, Throwable e) {
/* 168 */     this(message, null, e);
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
/*     */   public TransformerException(String message, SourceLocator locator) {
/* 182 */     this(message, locator, null);
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
/*     */   public TransformerException(String message, SourceLocator locator, Throwable e) {
/* 195 */     super((message == null || message.length() == 0) ? ((e == null) ? "" : e
/* 196 */         .toString()) : message);
/*     */ 
/*     */     
/* 199 */     this.containedException = e;
/* 200 */     this.locator = locator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMessageAndLocation() {
/* 211 */     StringBuilder sbuffer = new StringBuilder();
/* 212 */     sbuffer.append(Objects.toString(getMessage(), ""));
/* 213 */     sbuffer.append(Objects.toString(getLocationAsString(), ""));
/*     */     
/* 215 */     return sbuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocationAsString() {
/* 225 */     if (this.locator == null) {
/* 226 */       return null;
/*     */     }
/*     */     
/* 229 */     if (System.getSecurityManager() == null) {
/* 230 */       return getLocationString();
/*     */     }
/* 232 */     return AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*     */         {
/*     */           public String run() {
/* 235 */             return TransformerException.this.getLocationString();
/*     */           }
/*     */         }, 
/* 238 */         new AccessControlContext(new ProtectionDomain[] { getNonPrivDomain() }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getLocationString() {
/* 248 */     if (this.locator == null) {
/* 249 */       return null;
/*     */     }
/*     */     
/* 252 */     StringBuilder sbuffer = new StringBuilder();
/* 253 */     String systemID = this.locator.getSystemId();
/* 254 */     int line = this.locator.getLineNumber();
/* 255 */     int column = this.locator.getColumnNumber();
/*     */     
/* 257 */     if (null != systemID) {
/* 258 */       sbuffer.append("; SystemID: ");
/* 259 */       sbuffer.append(systemID);
/*     */     } 
/*     */     
/* 262 */     if (0 != line) {
/* 263 */       sbuffer.append("; Line#: ");
/* 264 */       sbuffer.append(line);
/*     */     } 
/*     */     
/* 267 */     if (0 != column) {
/* 268 */       sbuffer.append("; Column#: ");
/* 269 */       sbuffer.append(column);
/*     */     } 
/*     */     
/* 272 */     return sbuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printStackTrace() {
/* 282 */     printStackTrace(new PrintWriter(System.err, true));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printStackTrace(PrintStream s) {
/* 293 */     printStackTrace(new PrintWriter(s));
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
/*     */   public void printStackTrace(PrintWriter s) {
/* 305 */     if (s == null) {
/* 306 */       s = new PrintWriter(System.err, true);
/*     */     }
/*     */     
/*     */     try {
/* 310 */       String locInfo = getLocationAsString();
/*     */       
/* 312 */       if (null != locInfo) {
/* 313 */         s.println(locInfo);
/*     */       }
/*     */       
/* 316 */       super.printStackTrace(s);
/* 317 */     } catch (Throwable throwable) {}
/*     */     
/* 319 */     Throwable exception = getException();
/*     */     
/* 321 */     for (int i = 0; i < 10 && null != exception; i++) {
/* 322 */       s.println("---------");
/*     */       
/*     */       try {
/* 325 */         if (exception instanceof TransformerException) {
/*     */ 
/*     */           
/* 328 */           String locInfo = ((TransformerException)exception).getLocationAsString();
/*     */           
/* 330 */           if (null != locInfo) {
/* 331 */             s.println(locInfo);
/*     */           }
/*     */         } 
/*     */         
/* 335 */         exception.printStackTrace(s);
/* 336 */       } catch (Throwable e) {
/* 337 */         s.println("Could not print stack trace...");
/*     */       } 
/*     */ 
/*     */       
/*     */       try {
/* 342 */         Method meth = exception.getClass().getMethod("getException", (Class[])null);
/*     */ 
/*     */         
/* 345 */         if (null != meth) {
/* 346 */           Throwable prev = exception;
/*     */           
/* 348 */           exception = (Throwable)meth.invoke(exception, (Object[])null);
/*     */           
/* 350 */           if (prev == exception) {
/*     */             break;
/*     */           }
/*     */         } else {
/* 354 */           exception = null;
/*     */         } 
/* 356 */       } catch (InvocationTargetException|IllegalAccessException|NoSuchMethodException e) {
/*     */         
/* 358 */         exception = null;
/*     */       } 
/*     */     } 
/*     */     
/* 362 */     s.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ProtectionDomain getNonPrivDomain() {
/* 370 */     CodeSource nullSource = new CodeSource(null, (CodeSigner[])null);
/* 371 */     PermissionCollection noPermission = new Permissions();
/* 372 */     return new ProtectionDomain(nullSource, noPermission);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/transform/TransformerException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */