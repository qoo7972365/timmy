/*    */ package sun.reflect.generics.scope;
/*    */ 
/*    */ import java.lang.reflect.GenericDeclaration;
/*    */ import java.lang.reflect.TypeVariable;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractScope<D extends GenericDeclaration>
/*    */   implements Scope
/*    */ {
/*    */   private final D recvr;
/*    */   private volatile Scope enclosingScope;
/*    */   
/*    */   protected AbstractScope(D paramD) {
/* 55 */     this.recvr = paramD;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected D getRecvr() {
/* 62 */     return this.recvr;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected abstract Scope computeEnclosingScope();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected Scope getEnclosingScope() {
/* 76 */     Scope scope = this.enclosingScope;
/* 77 */     if (scope == null) {
/* 78 */       scope = computeEnclosingScope();
/* 79 */       this.enclosingScope = scope;
/*    */     } 
/* 81 */     return scope;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TypeVariable<?> lookup(String paramString) {
/* 92 */     TypeVariable[] arrayOfTypeVariable = (TypeVariable[])getRecvr().getTypeParameters();
/* 93 */     for (TypeVariable<?> typeVariable : arrayOfTypeVariable) {
/* 94 */       if (typeVariable.getName().equals(paramString)) return typeVariable; 
/*    */     } 
/* 96 */     return getEnclosingScope().lookup(paramString);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/generics/scope/AbstractScope.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */