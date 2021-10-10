/*     */ package javax.annotation.processing;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import javax.lang.model.SourceVersion;
/*     */ import javax.lang.model.element.AnnotationMirror;
/*     */ import javax.lang.model.element.Element;
/*     */ import javax.lang.model.element.ExecutableElement;
/*     */ import javax.lang.model.element.TypeElement;
/*     */ import javax.tools.Diagnostic;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractProcessor
/*     */   implements Processor
/*     */ {
/*     */   protected ProcessingEnvironment processingEnv;
/*     */   private boolean initialized = false;
/*     */   
/*     */   public Set<String> getSupportedOptions() {
/*  82 */     SupportedOptions supportedOptions = getClass().<SupportedOptions>getAnnotation(SupportedOptions.class);
/*  83 */     if (supportedOptions == null) {
/*  84 */       return Collections.emptySet();
/*     */     }
/*  86 */     return arrayToSet(supportedOptions.value());
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
/*     */   public Set<String> getSupportedAnnotationTypes() {
/*  99 */     SupportedAnnotationTypes supportedAnnotationTypes = getClass().<SupportedAnnotationTypes>getAnnotation(SupportedAnnotationTypes.class);
/* 100 */     if (supportedAnnotationTypes == null) {
/* 101 */       if (isInitialized()) {
/* 102 */         this.processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "No SupportedAnnotationTypes annotation found on " + 
/*     */             
/* 104 */             getClass().getName() + ", returning an empty set.");
/*     */       }
/* 106 */       return Collections.emptySet();
/*     */     } 
/*     */     
/* 109 */     return arrayToSet(supportedAnnotationTypes.value());
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
/*     */   public SourceVersion getSupportedSourceVersion() {
/* 121 */     SupportedSourceVersion supportedSourceVersion = getClass().<SupportedSourceVersion>getAnnotation(SupportedSourceVersion.class);
/* 122 */     SourceVersion sourceVersion = null;
/* 123 */     if (supportedSourceVersion == null) {
/* 124 */       sourceVersion = SourceVersion.RELEASE_6;
/* 125 */       if (isInitialized()) {
/* 126 */         this.processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "No SupportedSourceVersion annotation found on " + 
/*     */             
/* 128 */             getClass().getName() + ", returning " + sourceVersion + ".");
/*     */       }
/*     */     } else {
/* 131 */       sourceVersion = supportedSourceVersion.value();
/* 132 */     }  return sourceVersion;
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
/*     */   public synchronized void init(ProcessingEnvironment paramProcessingEnvironment) {
/* 148 */     if (this.initialized)
/* 149 */       throw new IllegalStateException("Cannot call init more than once."); 
/* 150 */     Objects.requireNonNull(paramProcessingEnvironment, "Tool provided null ProcessingEnvironment");
/*     */     
/* 152 */     this.processingEnv = paramProcessingEnvironment;
/* 153 */     this.initialized = true;
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
/*     */   public abstract boolean process(Set<? extends TypeElement> paramSet, RoundEnvironment paramRoundEnvironment);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterable<? extends Completion> getCompletions(Element paramElement, AnnotationMirror paramAnnotationMirror, ExecutableElement paramExecutableElement, String paramString) {
/* 174 */     return Collections.emptyList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected synchronized boolean isInitialized() {
/* 185 */     return this.initialized;
/*     */   }
/*     */   
/*     */   private static Set<String> arrayToSet(String[] paramArrayOfString) {
/* 189 */     assert paramArrayOfString != null;
/* 190 */     HashSet<String> hashSet = new HashSet(paramArrayOfString.length);
/* 191 */     for (String str : paramArrayOfString)
/* 192 */       hashSet.add(str); 
/* 193 */     return Collections.unmodifiableSet(hashSet);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/annotation/processing/AbstractProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */