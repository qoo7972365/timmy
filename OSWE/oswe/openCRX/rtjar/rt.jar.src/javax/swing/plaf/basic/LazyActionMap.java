/*     */ package javax.swing.plaf.basic;
/*     */ 
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import javax.swing.Action;
/*     */ import javax.swing.ActionMap;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.ActionMapUIResource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class LazyActionMap
/*     */   extends ActionMapUIResource
/*     */ {
/*     */   private transient Object _loader;
/*     */   
/*     */   static void installLazyActionMap(JComponent paramJComponent, Class paramClass, String paramString) {
/*  60 */     ActionMap actionMap = (ActionMap)UIManager.get(paramString);
/*  61 */     if (actionMap == null) {
/*  62 */       actionMap = new LazyActionMap(paramClass);
/*  63 */       UIManager.getLookAndFeelDefaults().put(paramString, actionMap);
/*     */     } 
/*  65 */     SwingUtilities.replaceUIActionMap(paramJComponent, actionMap);
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
/*     */   static ActionMap getActionMap(Class paramClass, String paramString) {
/*  83 */     ActionMap actionMap = (ActionMap)UIManager.get(paramString);
/*  84 */     if (actionMap == null) {
/*  85 */       actionMap = new LazyActionMap(paramClass);
/*  86 */       UIManager.getLookAndFeelDefaults().put(paramString, actionMap);
/*     */     } 
/*  88 */     return actionMap;
/*     */   }
/*     */ 
/*     */   
/*     */   private LazyActionMap(Class paramClass) {
/*  93 */     this._loader = paramClass;
/*     */   }
/*     */   
/*     */   public void put(Action paramAction) {
/*  97 */     put(paramAction.getValue("Name"), paramAction);
/*     */   }
/*     */   
/*     */   public void put(Object paramObject, Action paramAction) {
/* 101 */     loadIfNecessary();
/* 102 */     super.put(paramObject, paramAction);
/*     */   }
/*     */   
/*     */   public Action get(Object paramObject) {
/* 106 */     loadIfNecessary();
/* 107 */     return super.get(paramObject);
/*     */   }
/*     */   
/*     */   public void remove(Object paramObject) {
/* 111 */     loadIfNecessary();
/* 112 */     super.remove(paramObject);
/*     */   }
/*     */   
/*     */   public void clear() {
/* 116 */     loadIfNecessary();
/* 117 */     super.clear();
/*     */   }
/*     */   
/*     */   public Object[] keys() {
/* 121 */     loadIfNecessary();
/* 122 */     return super.keys();
/*     */   }
/*     */   
/*     */   public int size() {
/* 126 */     loadIfNecessary();
/* 127 */     return super.size();
/*     */   }
/*     */   
/*     */   public Object[] allKeys() {
/* 131 */     loadIfNecessary();
/* 132 */     return super.allKeys();
/*     */   }
/*     */   
/*     */   public void setParent(ActionMap paramActionMap) {
/* 136 */     loadIfNecessary();
/* 137 */     super.setParent(paramActionMap);
/*     */   }
/*     */   
/*     */   private void loadIfNecessary() {
/* 141 */     if (this._loader != null) {
/* 142 */       Object object = this._loader;
/*     */       
/* 144 */       this._loader = null;
/* 145 */       Class clazz = (Class)object;
/*     */       try {
/* 147 */         Method method = clazz.getDeclaredMethod("loadActionMap", new Class[] { LazyActionMap.class });
/*     */         
/* 149 */         method.invoke(clazz, new Object[] { this });
/* 150 */       } catch (NoSuchMethodException noSuchMethodException) {
/* 151 */         assert false : "LazyActionMap unable to load actions " + clazz;
/*     */       }
/* 153 */       catch (IllegalAccessException illegalAccessException) {
/* 154 */         assert false : "LazyActionMap unable to load actions " + illegalAccessException;
/*     */       }
/* 156 */       catch (InvocationTargetException invocationTargetException) {
/* 157 */         assert false : "LazyActionMap unable to load actions " + invocationTargetException;
/*     */       }
/* 159 */       catch (IllegalArgumentException illegalArgumentException) {
/* 160 */         assert false : "LazyActionMap unable to load actions " + illegalArgumentException;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/LazyActionMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */