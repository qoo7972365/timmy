/*     */ package sun.security.provider.certpath;
/*     */ 
/*     */ import java.security.cert.CertPathBuilderException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SunCertPathBuilderException
/*     */   extends CertPathBuilderException
/*     */ {
/*     */   private static final long serialVersionUID = -7814288414129264709L;
/*     */   private transient AdjacencyList adjList;
/*     */   
/*     */   public SunCertPathBuilderException() {}
/*     */   
/*     */   public SunCertPathBuilderException(String paramString) {
/*  65 */     super(paramString);
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
/*     */   public SunCertPathBuilderException(Throwable paramThrowable) {
/*  82 */     super(paramThrowable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SunCertPathBuilderException(String paramString, Throwable paramThrowable) {
/*  93 */     super(paramString, paramThrowable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   SunCertPathBuilderException(String paramString, AdjacencyList paramAdjacencyList) {
/* 104 */     this(paramString);
/* 105 */     this.adjList = paramAdjacencyList;
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
/*     */   SunCertPathBuilderException(String paramString, Throwable paramThrowable, AdjacencyList paramAdjacencyList) {
/* 119 */     this(paramString, paramThrowable);
/* 120 */     this.adjList = paramAdjacencyList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AdjacencyList getAdjacencyList() {
/* 129 */     return this.adjList;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/certpath/SunCertPathBuilderException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */