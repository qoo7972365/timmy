/*    */ package javax.tools;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
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
/*    */ public final class DiagnosticCollector<S>
/*    */   implements DiagnosticListener<S>
/*    */ {
/* 43 */   private List<Diagnostic<? extends S>> diagnostics = Collections.synchronizedList(new ArrayList<>());
/*    */   
/*    */   public void report(Diagnostic<? extends S> paramDiagnostic) {
/* 46 */     paramDiagnostic.getClass();
/* 47 */     this.diagnostics.add(paramDiagnostic);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<Diagnostic<? extends S>> getDiagnostics() {
/* 56 */     return Collections.unmodifiableList(this.diagnostics);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/tools/DiagnosticCollector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */