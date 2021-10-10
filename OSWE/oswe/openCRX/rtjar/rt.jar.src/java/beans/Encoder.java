/*     */ package java.beans;
/*     */ 
/*     */ import com.sun.beans.finder.PersistenceDelegateFinder;
/*     */ import java.util.HashMap;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Encoder
/*     */ {
/*  49 */   private final PersistenceDelegateFinder finder = new PersistenceDelegateFinder();
/*  50 */   private Map<Object, Expression> bindings = new IdentityHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ExceptionListener exceptionListener;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean executeStatements = true;
/*     */ 
/*     */ 
/*     */   
/*     */   private Map<Object, Object> attributes;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeObject(Object paramObject) {
/*  70 */     if (paramObject == this) {
/*     */       return;
/*     */     }
/*  73 */     PersistenceDelegate persistenceDelegate = getPersistenceDelegate((paramObject == null) ? null : paramObject.getClass());
/*  74 */     persistenceDelegate.writeObject(paramObject, this);
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
/*     */   public void setExceptionListener(ExceptionListener paramExceptionListener) {
/*  88 */     this.exceptionListener = paramExceptionListener;
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
/*     */   public ExceptionListener getExceptionListener() {
/* 100 */     return (this.exceptionListener != null) ? this.exceptionListener : Statement.defaultExceptionListener;
/*     */   }
/*     */   
/*     */   Object getValue(Expression paramExpression) {
/*     */     try {
/* 105 */       return (paramExpression == null) ? null : paramExpression.getValue();
/*     */     }
/* 107 */     catch (Exception exception) {
/* 108 */       getExceptionListener().exceptionThrown(exception);
/* 109 */       throw new RuntimeException("failed to evaluate: " + paramExpression.toString());
/*     */     } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PersistenceDelegate getPersistenceDelegate(Class<?> paramClass) {
/* 197 */     PersistenceDelegate persistenceDelegate = this.finder.find(paramClass);
/* 198 */     if (persistenceDelegate == null) {
/* 199 */       persistenceDelegate = MetaData.getPersistenceDelegate(paramClass);
/* 200 */       if (persistenceDelegate != null) {
/* 201 */         this.finder.register(paramClass, persistenceDelegate);
/*     */       }
/*     */     } 
/* 204 */     return persistenceDelegate;
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
/*     */   public void setPersistenceDelegate(Class<?> paramClass, PersistenceDelegate paramPersistenceDelegate) {
/* 218 */     this.finder.register(paramClass, paramPersistenceDelegate);
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
/*     */   public Object remove(Object paramObject) {
/* 230 */     Expression expression = this.bindings.remove(paramObject);
/* 231 */     return getValue(expression);
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
/*     */   public Object get(Object paramObject) {
/* 247 */     if (paramObject == null || paramObject == this || paramObject
/* 248 */       .getClass() == String.class) {
/* 249 */       return paramObject;
/*     */     }
/* 251 */     Expression expression = this.bindings.get(paramObject);
/* 252 */     return getValue(expression);
/*     */   }
/*     */   
/*     */   private Object writeObject1(Object paramObject) {
/* 256 */     Object object = get(paramObject);
/* 257 */     if (object == null) {
/* 258 */       writeObject(paramObject);
/* 259 */       object = get(paramObject);
/*     */     } 
/* 261 */     return object;
/*     */   }
/*     */   
/*     */   private Statement cloneStatement(Statement paramStatement) {
/* 265 */     Object object1 = paramStatement.getTarget();
/* 266 */     Object object2 = writeObject1(object1);
/*     */     
/* 268 */     Object[] arrayOfObject1 = paramStatement.getArguments();
/* 269 */     Object[] arrayOfObject2 = new Object[arrayOfObject1.length];
/* 270 */     for (byte b = 0; b < arrayOfObject1.length; b++) {
/* 271 */       arrayOfObject2[b] = writeObject1(arrayOfObject1[b]);
/*     */     }
/*     */ 
/*     */     
/* 275 */     Statement statement = Statement.class.equals(paramStatement.getClass()) ? new Statement(object2, paramStatement.getMethodName(), arrayOfObject2) : new Expression(object2, paramStatement.getMethodName(), arrayOfObject2);
/* 276 */     statement.loader = paramStatement.loader;
/* 277 */     return statement;
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
/*     */   public void writeStatement(Statement paramStatement) {
/* 301 */     Statement statement = cloneStatement(paramStatement);
/* 302 */     if (paramStatement.getTarget() != this && this.executeStatements) {
/*     */       try {
/* 304 */         statement.execute();
/* 305 */       } catch (Exception exception) {
/* 306 */         getExceptionListener().exceptionThrown(new Exception("Encoder: discarding statement " + statement, exception));
/*     */       } 
/*     */     }
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
/*     */   public void writeExpression(Expression paramExpression) {
/* 325 */     Object object = getValue(paramExpression);
/* 326 */     if (get(object) != null) {
/*     */       return;
/*     */     }
/* 329 */     this.bindings.put(object, (Expression)cloneStatement(paramExpression));
/* 330 */     writeObject(object);
/*     */   }
/*     */   
/*     */   void clear() {
/* 334 */     this.bindings.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   void setAttribute(Object paramObject1, Object paramObject2) {
/* 339 */     if (this.attributes == null) {
/* 340 */       this.attributes = new HashMap<>();
/*     */     }
/* 342 */     this.attributes.put(paramObject1, paramObject2);
/*     */   }
/*     */   
/*     */   Object getAttribute(Object paramObject) {
/* 346 */     if (this.attributes == null) {
/* 347 */       return null;
/*     */     }
/* 349 */     return this.attributes.get(paramObject);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/beans/Encoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */