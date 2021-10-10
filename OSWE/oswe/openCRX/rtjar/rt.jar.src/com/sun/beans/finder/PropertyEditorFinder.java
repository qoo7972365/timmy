/*    */ package com.sun.beans.finder;
/*    */ 
/*    */ import com.sun.beans.WeakCache;
/*    */ import com.sun.beans.editors.BooleanEditor;
/*    */ import com.sun.beans.editors.ByteEditor;
/*    */ import com.sun.beans.editors.DoubleEditor;
/*    */ import com.sun.beans.editors.FloatEditor;
/*    */ import com.sun.beans.editors.IntegerEditor;
/*    */ import com.sun.beans.editors.LongEditor;
/*    */ import com.sun.beans.editors.ShortEditor;
/*    */ import java.beans.PropertyEditor;
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
/*    */ public final class PropertyEditorFinder
/*    */   extends InstanceFinder<PropertyEditor>
/*    */ {
/*    */   private static final String DEFAULT = "sun.beans.editors";
/*    */   private static final String DEFAULT_NEW = "com.sun.beans.editors";
/*    */   private final WeakCache<Class<?>, Class<?>> registry;
/*    */   
/*    */   public PropertyEditorFinder() {
/* 57 */     super(PropertyEditor.class, false, "Editor", new String[] { "sun.beans.editors" });
/*    */     
/* 59 */     this.registry = new WeakCache<>();
/* 60 */     this.registry.put(byte.class, ByteEditor.class);
/* 61 */     this.registry.put(short.class, ShortEditor.class);
/* 62 */     this.registry.put(int.class, IntegerEditor.class);
/* 63 */     this.registry.put(long.class, LongEditor.class);
/* 64 */     this.registry.put(boolean.class, BooleanEditor.class);
/* 65 */     this.registry.put(float.class, FloatEditor.class);
/* 66 */     this.registry.put(double.class, DoubleEditor.class);
/*    */   }
/*    */   
/*    */   public void register(Class<?> paramClass1, Class<?> paramClass2) {
/* 70 */     synchronized (this.registry) {
/* 71 */       this.registry.put(paramClass1, paramClass2);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PropertyEditor find(Class<?> paramClass) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: getfield registry : Lcom/sun/beans/WeakCache;
/*    */     //   4: dup
/*    */     //   5: astore_3
/*    */     //   6: monitorenter
/*    */     //   7: aload_0
/*    */     //   8: getfield registry : Lcom/sun/beans/WeakCache;
/*    */     //   11: aload_1
/*    */     //   12: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
/*    */     //   15: checkcast java/lang/Class
/*    */     //   18: astore_2
/*    */     //   19: aload_3
/*    */     //   20: monitorexit
/*    */     //   21: goto -> 31
/*    */     //   24: astore #4
/*    */     //   26: aload_3
/*    */     //   27: monitorexit
/*    */     //   28: aload #4
/*    */     //   30: athrow
/*    */     //   31: aload_0
/*    */     //   32: aload_2
/*    */     //   33: aconst_null
/*    */     //   34: invokevirtual instantiate : (Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Object;
/*    */     //   37: checkcast java/beans/PropertyEditor
/*    */     //   40: astore_3
/*    */     //   41: aload_3
/*    */     //   42: ifnonnull -> 75
/*    */     //   45: aload_0
/*    */     //   46: aload_1
/*    */     //   47: invokespecial find : (Ljava/lang/Class;)Ljava/lang/Object;
/*    */     //   50: checkcast java/beans/PropertyEditor
/*    */     //   53: astore_3
/*    */     //   54: aload_3
/*    */     //   55: ifnonnull -> 75
/*    */     //   58: aconst_null
/*    */     //   59: aload_1
/*    */     //   60: invokevirtual getEnumConstants : ()[Ljava/lang/Object;
/*    */     //   63: if_acmpeq -> 75
/*    */     //   66: new com/sun/beans/editors/EnumEditor
/*    */     //   69: dup
/*    */     //   70: aload_1
/*    */     //   71: invokespecial <init> : (Ljava/lang/Class;)V
/*    */     //   74: astore_3
/*    */     //   75: aload_3
/*    */     //   76: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #78	-> 0
/*    */     //   #79	-> 7
/*    */     //   #80	-> 19
/*    */     //   #81	-> 31
/*    */     //   #82	-> 41
/*    */     //   #83	-> 45
/*    */     //   #84	-> 54
/*    */     //   #85	-> 66
/*    */     //   #88	-> 75
/*    */     // Exception table:
/*    */     //   from	to	target	type
/*    */     //   7	21	24	finally
/*    */     //   24	28	24	finally
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected PropertyEditor instantiate(Class<?> paramClass, String paramString1, String paramString2) {
/* 93 */     return super.instantiate(paramClass, "sun.beans.editors".equals(paramString1) ? "com.sun.beans.editors" : paramString1, paramString2);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/beans/finder/PropertyEditorFinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */