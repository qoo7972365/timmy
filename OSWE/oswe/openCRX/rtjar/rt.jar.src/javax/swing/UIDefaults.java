/*      */ package javax.swing;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Font;
/*      */ import java.awt.Insets;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.Method;
/*      */ import java.security.AccessControlContext;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.MissingResourceException;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.Vector;
/*      */ import javax.swing.border.Border;
/*      */ import javax.swing.event.SwingPropertyChangeSupport;
/*      */ import javax.swing.plaf.ComponentUI;
/*      */ import sun.reflect.misc.MethodUtil;
/*      */ import sun.reflect.misc.ReflectUtil;
/*      */ import sun.swing.SwingUtilities2;
/*      */ import sun.util.CoreResourceBundleControl;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class UIDefaults
/*      */   extends Hashtable<Object, Object>
/*      */ {
/*   75 */   private static final Object PENDING = new Object();
/*      */   
/*      */   private SwingPropertyChangeSupport changeSupport;
/*      */   
/*      */   private Vector<String> resourceBundles;
/*      */   
/*   81 */   private Locale defaultLocale = Locale.getDefault();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Map<Locale, Map<String, Object>> resourceCache;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public UIDefaults() {
/*   95 */     this(700, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public UIDefaults(int paramInt, float paramFloat) {
/*  108 */     super(paramInt, paramFloat);
/*  109 */     this.resourceCache = new HashMap<>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public UIDefaults(Object[] paramArrayOfObject) {
/*  128 */     super(paramArrayOfObject.length / 2);
/*  129 */     for (byte b = 0; b < paramArrayOfObject.length; b += 2) {
/*  130 */       super.put(paramArrayOfObject[b], paramArrayOfObject[b + 1]);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object get(Object paramObject) {
/*  161 */     Object object = getFromHashtable(paramObject);
/*  162 */     return (object != null) ? object : getFromResourceBundle(paramObject, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Object getFromHashtable(Object paramObject) {
/*  173 */     Object object = super.get(paramObject);
/*  174 */     if (object != PENDING && !(object instanceof ActiveValue) && !(object instanceof LazyValue))
/*      */     {
/*      */       
/*  177 */       return object;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  186 */     synchronized (this) {
/*  187 */       object = super.get(paramObject);
/*  188 */       if (object == PENDING)
/*      */         while (true) {
/*      */           try {
/*  191 */             wait();
/*      */           }
/*  193 */           catch (InterruptedException interruptedException) {}
/*      */           
/*  195 */           object = super.get(paramObject);
/*      */           
/*  197 */           if (object != PENDING)
/*  198 */             return object; 
/*      */         }  
/*  200 */       if (object instanceof LazyValue) {
/*  201 */         super.put(paramObject, PENDING);
/*      */       }
/*  203 */       else if (!(object instanceof ActiveValue)) {
/*  204 */         return object;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  211 */     if (object instanceof LazyValue) {
/*      */ 
/*      */       
/*      */       try {
/*      */         
/*  216 */         object = ((LazyValue)object).createValue(this);
/*      */       } finally {
/*      */         
/*  219 */         synchronized (this) {
/*  220 */           if (object == null) {
/*  221 */             remove(paramObject);
/*      */           } else {
/*      */             
/*  224 */             super.put(paramObject, object);
/*      */           } 
/*  226 */           notifyAll();
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/*  231 */       object = ((ActiveValue)object).createValue(this);
/*      */     } 
/*      */     
/*  234 */     return object;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object get(Object paramObject, Locale paramLocale) {
/*  264 */     Object object = getFromHashtable(paramObject);
/*  265 */     return (object != null) ? object : getFromResourceBundle(paramObject, paramLocale);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Object getFromResourceBundle(Object paramObject, Locale paramLocale) {
/*  273 */     if (this.resourceBundles == null || this.resourceBundles
/*  274 */       .isEmpty() || !(paramObject instanceof String))
/*      */     {
/*  276 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  280 */     if (paramLocale == null) {
/*  281 */       if (this.defaultLocale == null) {
/*  282 */         return null;
/*      */       }
/*  284 */       paramLocale = this.defaultLocale;
/*      */     } 
/*      */     
/*  287 */     synchronized (this) {
/*  288 */       return getResourceCache(paramLocale).get(paramObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Map<String, Object> getResourceCache(Locale paramLocale) {
/*  296 */     Map<String, Object> map = this.resourceCache.get(paramLocale);
/*      */     
/*  298 */     if (map == null) {
/*  299 */       map = new TextAndMnemonicHashMap();
/*  300 */       for (int i = this.resourceBundles.size() - 1; i >= 0; i--) {
/*  301 */         String str = this.resourceBundles.get(i); try {
/*      */           ResourceBundle resourceBundle;
/*  303 */           CoreResourceBundleControl coreResourceBundleControl = CoreResourceBundleControl.getRBControlInstance(str);
/*      */           
/*  305 */           if (coreResourceBundleControl != null) {
/*  306 */             resourceBundle = ResourceBundle.getBundle(str, paramLocale, (ResourceBundle.Control)coreResourceBundleControl);
/*      */           } else {
/*  308 */             resourceBundle = ResourceBundle.getBundle(str, paramLocale, 
/*  309 */                 ClassLoader.getSystemClassLoader());
/*      */           } 
/*  311 */           Enumeration<String> enumeration = resourceBundle.getKeys();
/*      */           
/*  313 */           while (enumeration.hasMoreElements()) {
/*  314 */             String str1 = enumeration.nextElement();
/*      */             
/*  316 */             if (map.get(str1) == null) {
/*  317 */               Object object = resourceBundle.getObject(str1);
/*      */               
/*  319 */               map.put(str1, object);
/*      */             } 
/*      */           } 
/*  322 */         } catch (MissingResourceException missingResourceException) {}
/*      */       } 
/*      */ 
/*      */       
/*  326 */       this.resourceCache.put(paramLocale, map);
/*      */     } 
/*  328 */     return map;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object put(Object paramObject1, Object paramObject2) {
/*  346 */     V v = (paramObject2 == null) ? remove(paramObject1) : (V)super.put(paramObject1, paramObject2);
/*  347 */     if (paramObject1 instanceof String) {
/*  348 */       firePropertyChange((String)paramObject1, v, paramObject2);
/*      */     }
/*  350 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void putDefaults(Object[] paramArrayOfObject) {
/*      */     byte b;
/*      */     int i;
/*  366 */     for (b = 0, i = paramArrayOfObject.length; b < i; b += 2) {
/*  367 */       Object object = paramArrayOfObject[b + 1];
/*  368 */       if (object == null) {
/*  369 */         remove(paramArrayOfObject[b]);
/*      */       } else {
/*      */         
/*  372 */         super.put(paramArrayOfObject[b], object);
/*      */       } 
/*      */     } 
/*  375 */     firePropertyChange("UIDefaults", null, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Font getFont(Object paramObject) {
/*  388 */     Object object = get(paramObject);
/*  389 */     return (object instanceof Font) ? (Font)object : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Font getFont(Object paramObject, Locale paramLocale) {
/*  405 */     Object object = get(paramObject, paramLocale);
/*  406 */     return (object instanceof Font) ? (Font)object : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Color getColor(Object paramObject) {
/*  418 */     Object object = get(paramObject);
/*  419 */     return (object instanceof Color) ? (Color)object : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Color getColor(Object paramObject, Locale paramLocale) {
/*  435 */     Object object = get(paramObject, paramLocale);
/*  436 */     return (object instanceof Color) ? (Color)object : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Icon getIcon(Object paramObject) {
/*  449 */     Object object = get(paramObject);
/*  450 */     return (object instanceof Icon) ? (Icon)object : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Icon getIcon(Object paramObject, Locale paramLocale) {
/*  466 */     Object object = get(paramObject, paramLocale);
/*  467 */     return (object instanceof Icon) ? (Icon)object : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Border getBorder(Object paramObject) {
/*  480 */     Object object = get(paramObject);
/*  481 */     return (object instanceof Border) ? (Border)object : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Border getBorder(Object paramObject, Locale paramLocale) {
/*  497 */     Object object = get(paramObject, paramLocale);
/*  498 */     return (object instanceof Border) ? (Border)object : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getString(Object paramObject) {
/*  511 */     Object object = get(paramObject);
/*  512 */     return (object instanceof String) ? (String)object : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getString(Object paramObject, Locale paramLocale) {
/*  527 */     Object object = get(paramObject, paramLocale);
/*  528 */     return (object instanceof String) ? (String)object : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getInt(Object paramObject) {
/*  539 */     Object object = get(paramObject);
/*  540 */     return (object instanceof Integer) ? ((Integer)object).intValue() : 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getInt(Object paramObject, Locale paramLocale) {
/*  555 */     Object object = get(paramObject, paramLocale);
/*  556 */     return (object instanceof Integer) ? ((Integer)object).intValue() : 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getBoolean(Object paramObject) {
/*  570 */     Object object = get(paramObject);
/*  571 */     return (object instanceof Boolean) ? ((Boolean)object).booleanValue() : false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getBoolean(Object paramObject, Locale paramLocale) {
/*  587 */     Object object = get(paramObject, paramLocale);
/*  588 */     return (object instanceof Boolean) ? ((Boolean)object).booleanValue() : false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Insets getInsets(Object paramObject) {
/*  601 */     Object object = get(paramObject);
/*  602 */     return (object instanceof Insets) ? (Insets)object : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Insets getInsets(Object paramObject, Locale paramLocale) {
/*  618 */     Object object = get(paramObject, paramLocale);
/*  619 */     return (object instanceof Insets) ? (Insets)object : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension getDimension(Object paramObject) {
/*  632 */     Object object = get(paramObject);
/*  633 */     return (object instanceof Dimension) ? (Dimension)object : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension getDimension(Object paramObject, Locale paramLocale) {
/*  649 */     Object object = get(paramObject, paramLocale);
/*  650 */     return (object instanceof Dimension) ? (Dimension)object : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Class<? extends ComponentUI> getUIClass(String paramString, ClassLoader paramClassLoader) {
/*      */     try {
/*  678 */       String str = (String)get(paramString);
/*  679 */       if (str != null) {
/*  680 */         ReflectUtil.checkPackageAccess(str);
/*      */         
/*  682 */         Class<?> clazz = (Class)get(str);
/*  683 */         if (clazz == null) {
/*  684 */           if (paramClassLoader == null) {
/*  685 */             clazz = SwingUtilities.loadSystemClass(str);
/*      */           } else {
/*      */             
/*  688 */             clazz = paramClassLoader.loadClass(str);
/*      */           } 
/*  690 */           if (clazz != null)
/*      */           {
/*  692 */             put(str, clazz);
/*      */           }
/*      */         } 
/*  695 */         return (Class)clazz;
/*      */       }
/*      */     
/*  698 */     } catch (ClassNotFoundException classNotFoundException) {
/*  699 */       return null;
/*      */     }
/*  701 */     catch (ClassCastException classCastException) {
/*  702 */       return null;
/*      */     } 
/*  704 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Class<? extends ComponentUI> getUIClass(String paramString) {
/*  716 */     return getUIClass(paramString, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void getUIError(String paramString) {
/*  729 */     System.err.println("UIDefaults.getUI() failed: " + paramString);
/*      */     try {
/*  731 */       throw new Error();
/*      */     }
/*  733 */     catch (Throwable throwable) {
/*  734 */       throwable.printStackTrace();
/*      */       return;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ComponentUI getUI(JComponent paramJComponent) {
/*  754 */     Object object1 = get("ClassLoader");
/*      */     
/*  756 */     ClassLoader classLoader = (object1 != null) ? (ClassLoader)object1 : paramJComponent.getClass().getClassLoader();
/*  757 */     Class<? extends ComponentUI> clazz = getUIClass(paramJComponent.getUIClassID(), classLoader);
/*  758 */     Object object2 = null;
/*      */     
/*  760 */     if (clazz == null) {
/*  761 */       getUIError("no ComponentUI class for: " + paramJComponent);
/*      */     } else {
/*      */       
/*      */       try {
/*  765 */         Method method = (Method)get(clazz);
/*  766 */         if (method == null) {
/*  767 */           method = clazz.getMethod("createUI", new Class[] { JComponent.class });
/*  768 */           put(clazz, method);
/*      */         } 
/*  770 */         object2 = MethodUtil.invoke(method, null, new Object[] { paramJComponent });
/*      */       }
/*  772 */       catch (NoSuchMethodException noSuchMethodException) {
/*  773 */         getUIError("static createUI() method not found in " + clazz);
/*      */       }
/*  775 */       catch (Exception exception) {
/*  776 */         getUIError("createUI() failed for " + paramJComponent + " " + exception);
/*      */       } 
/*      */     } 
/*      */     
/*  780 */     return (ComponentUI)object2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void addPropertyChangeListener(PropertyChangeListener paramPropertyChangeListener) {
/*  794 */     if (this.changeSupport == null) {
/*  795 */       this.changeSupport = new SwingPropertyChangeSupport(this);
/*      */     }
/*  797 */     this.changeSupport.addPropertyChangeListener(paramPropertyChangeListener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void removePropertyChangeListener(PropertyChangeListener paramPropertyChangeListener) {
/*  810 */     if (this.changeSupport != null) {
/*  811 */       this.changeSupport.removePropertyChangeListener(paramPropertyChangeListener);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized PropertyChangeListener[] getPropertyChangeListeners() {
/*  825 */     if (this.changeSupport == null) {
/*  826 */       return new PropertyChangeListener[0];
/*      */     }
/*  828 */     return this.changeSupport.getPropertyChangeListeners();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void firePropertyChange(String paramString, Object paramObject1, Object paramObject2) {
/*  845 */     if (this.changeSupport != null) {
/*  846 */       this.changeSupport.firePropertyChange(paramString, paramObject1, paramObject2);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void addResourceBundle(String paramString) {
/*  863 */     if (paramString == null) {
/*      */       return;
/*      */     }
/*  866 */     if (this.resourceBundles == null) {
/*  867 */       this.resourceBundles = new Vector<>(5);
/*      */     }
/*  869 */     if (!this.resourceBundles.contains(paramString)) {
/*  870 */       this.resourceBundles.add(paramString);
/*  871 */       this.resourceCache.clear();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void removeResourceBundle(String paramString) {
/*  886 */     if (this.resourceBundles != null) {
/*  887 */       this.resourceBundles.remove(paramString);
/*      */     }
/*  889 */     this.resourceCache.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDefaultLocale(Locale paramLocale) {
/*  907 */     this.defaultLocale = paramLocale;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Locale getDefaultLocale() {
/*  925 */     return this.defaultLocale;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static interface LazyValue
/*      */   {
/*      */     Object createValue(UIDefaults param1UIDefaults);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static interface ActiveValue
/*      */   {
/*      */     Object createValue(UIDefaults param1UIDefaults);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class ProxyLazyValue
/*      */     implements LazyValue
/*      */   {
/*      */     private AccessControlContext acc;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String className;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String methodName;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Object[] args;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ProxyLazyValue(String param1String) {
/* 1023 */       this(param1String, (String)null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ProxyLazyValue(String param1String1, String param1String2) {
/* 1037 */       this(param1String1, param1String2, null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ProxyLazyValue(String param1String, Object[] param1ArrayOfObject) {
/* 1049 */       this(param1String, null, param1ArrayOfObject);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ProxyLazyValue(String param1String1, String param1String2, Object[] param1ArrayOfObject) {
/* 1065 */       this.acc = AccessController.getContext();
/* 1066 */       this.className = param1String1;
/* 1067 */       this.methodName = param1String2;
/* 1068 */       if (param1ArrayOfObject != null) {
/* 1069 */         this.args = (Object[])param1ArrayOfObject.clone();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object createValue(final UIDefaults table) {
/* 1084 */       if (this.acc == null && System.getSecurityManager() != null) {
/* 1085 */         throw new SecurityException("null AccessControlContext");
/*      */       }
/* 1087 */       return AccessController.doPrivileged(new PrivilegedAction()
/*      */           {
/*      */             public Object run()
/*      */             {
/*      */               try {
/*      */                 Object object;
/* 1093 */                 if (table == null || !(object = table.get("ClassLoader") instanceof ClassLoader)) {
/*      */ 
/*      */                   
/* 1096 */                   object = Thread.currentThread().getContextClassLoader();
/* 1097 */                   if (object == null)
/*      */                   {
/* 1099 */                     object = ClassLoader.getSystemClassLoader();
/*      */                   }
/*      */                 } 
/* 1102 */                 ReflectUtil.checkPackageAccess(UIDefaults.ProxyLazyValue.this.className);
/* 1103 */                 Class<?> clazz = Class.forName(UIDefaults.ProxyLazyValue.this.className, true, (ClassLoader)object);
/* 1104 */                 SwingUtilities2.checkAccess(clazz.getModifiers());
/* 1105 */                 if (UIDefaults.ProxyLazyValue.this.methodName != null) {
/* 1106 */                   Class[] arrayOfClass1 = UIDefaults.ProxyLazyValue.this.getClassArray(UIDefaults.ProxyLazyValue.this.args);
/* 1107 */                   Method method = clazz.getMethod(UIDefaults.ProxyLazyValue.this.methodName, arrayOfClass1);
/* 1108 */                   return MethodUtil.invoke(method, clazz, UIDefaults.ProxyLazyValue.this.args);
/*      */                 } 
/* 1110 */                 Class[] arrayOfClass = UIDefaults.ProxyLazyValue.this.getClassArray(UIDefaults.ProxyLazyValue.this.args);
/* 1111 */                 Constructor<?> constructor = clazz.getConstructor(arrayOfClass);
/* 1112 */                 SwingUtilities2.checkAccess(constructor.getModifiers());
/* 1113 */                 return constructor.newInstance(UIDefaults.ProxyLazyValue.this.args);
/*      */               }
/* 1115 */               catch (Exception exception) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 1122 */                 return null;
/*      */               } 
/*      */             }
/*      */           }this.acc);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Class[] getClassArray(Object[] param1ArrayOfObject) {
/* 1135 */       Class[] arrayOfClass = null;
/* 1136 */       if (param1ArrayOfObject != null) {
/* 1137 */         arrayOfClass = new Class[param1ArrayOfObject.length];
/* 1138 */         for (byte b = 0; b < param1ArrayOfObject.length; b++) {
/*      */ 
/*      */ 
/*      */           
/* 1142 */           if (param1ArrayOfObject[b] instanceof Integer) {
/* 1143 */             arrayOfClass[b] = int.class;
/* 1144 */           } else if (param1ArrayOfObject[b] instanceof Boolean) {
/* 1145 */             arrayOfClass[b] = boolean.class;
/* 1146 */           } else if (param1ArrayOfObject[b] instanceof javax.swing.plaf.ColorUIResource) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1155 */             arrayOfClass[b] = Color.class;
/*      */           } else {
/* 1157 */             arrayOfClass[b] = param1ArrayOfObject[b].getClass();
/*      */           } 
/*      */         } 
/*      */       } 
/* 1161 */       return arrayOfClass;
/*      */     }
/*      */     
/*      */     private String printArgs(Object[] param1ArrayOfObject) {
/* 1165 */       String str = "{";
/* 1166 */       if (param1ArrayOfObject != null) {
/* 1167 */         for (byte b = 0; b < param1ArrayOfObject.length - 1; b++) {
/* 1168 */           str = str.concat(param1ArrayOfObject[b] + ",");
/*      */         }
/* 1170 */         str = str.concat(param1ArrayOfObject[param1ArrayOfObject.length - 1] + "}");
/*      */       } else {
/* 1172 */         str = str.concat("}");
/*      */       } 
/* 1174 */       return str;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class LazyInputMap
/*      */     implements LazyValue
/*      */   {
/*      */     private Object[] bindings;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public LazyInputMap(Object[] param1ArrayOfObject) {
/* 1195 */       this.bindings = param1ArrayOfObject;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object createValue(UIDefaults param1UIDefaults) {
/* 1206 */       if (this.bindings != null) {
/* 1207 */         return LookAndFeel.makeInputMap(this.bindings);
/*      */       }
/*      */       
/* 1210 */       return null;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class TextAndMnemonicHashMap
/*      */     extends HashMap<String, Object>
/*      */   {
/*      */     static final String AND_MNEMONIC = "AndMnemonic";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static final String TITLE_SUFFIX = ".titleAndMnemonic";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static final String TEXT_SUFFIX = ".textAndMnemonic";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private TextAndMnemonicHashMap() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object get(Object param1Object) {
/* 1245 */       Object object = super.get(param1Object);
/*      */       
/* 1247 */       if (object == null) {
/*      */         
/* 1249 */         boolean bool = false;
/*      */         
/* 1251 */         String str1 = param1Object.toString();
/* 1252 */         String str2 = null;
/*      */         
/* 1254 */         if (str1.endsWith("AndMnemonic")) {
/* 1255 */           return null;
/*      */         }
/*      */         
/* 1258 */         if (str1.endsWith(".mnemonic")) {
/* 1259 */           str2 = composeKey(str1, 9, ".textAndMnemonic");
/* 1260 */         } else if (str1.endsWith("NameMnemonic")) {
/* 1261 */           str2 = composeKey(str1, 12, ".textAndMnemonic");
/* 1262 */         } else if (str1.endsWith("Mnemonic")) {
/* 1263 */           str2 = composeKey(str1, 8, ".textAndMnemonic");
/* 1264 */           bool = true;
/*      */         } 
/*      */         
/* 1267 */         if (str2 != null) {
/* 1268 */           object = super.get(str2);
/* 1269 */           if (object == null && bool) {
/* 1270 */             str2 = composeKey(str1, 8, ".titleAndMnemonic");
/* 1271 */             object = super.get(str2);
/*      */           } 
/*      */           
/* 1274 */           return (object == null) ? null : getMnemonicFromProperty(object.toString());
/*      */         } 
/*      */         
/* 1277 */         if (str1.endsWith("NameText")) {
/* 1278 */           str2 = composeKey(str1, 8, ".textAndMnemonic");
/* 1279 */         } else if (str1.endsWith(".nameText")) {
/* 1280 */           str2 = composeKey(str1, 9, ".textAndMnemonic");
/* 1281 */         } else if (str1.endsWith("Text")) {
/* 1282 */           str2 = composeKey(str1, 4, ".textAndMnemonic");
/* 1283 */         } else if (str1.endsWith("Title")) {
/* 1284 */           str2 = composeKey(str1, 5, ".titleAndMnemonic");
/*      */         } 
/*      */         
/* 1287 */         if (str2 != null) {
/* 1288 */           object = super.get(str2);
/* 1289 */           return (object == null) ? null : getTextFromProperty(object.toString());
/*      */         } 
/*      */         
/* 1292 */         if (str1.endsWith("DisplayedMnemonicIndex")) {
/* 1293 */           str2 = composeKey(str1, 22, ".textAndMnemonic");
/* 1294 */           object = super.get(str2);
/* 1295 */           if (object == null) {
/* 1296 */             str2 = composeKey(str1, 22, ".titleAndMnemonic");
/* 1297 */             object = super.get(str2);
/*      */           } 
/* 1299 */           return (object == null) ? null : getIndexFromProperty(object.toString());
/*      */         } 
/*      */       } 
/*      */       
/* 1303 */       return object;
/*      */     }
/*      */     
/*      */     String composeKey(String param1String1, int param1Int, String param1String2) {
/* 1307 */       return param1String1.substring(0, param1String1.length() - param1Int) + param1String2;
/*      */     }
/*      */     
/*      */     String getTextFromProperty(String param1String) {
/* 1311 */       return param1String.replace("&", "");
/*      */     }
/*      */     
/*      */     String getMnemonicFromProperty(String param1String) {
/* 1315 */       int i = param1String.indexOf('&');
/* 1316 */       if (0 <= i && i < param1String.length() - 1) {
/* 1317 */         char c = param1String.charAt(i + 1);
/* 1318 */         return Integer.toString(Character.toUpperCase(c));
/*      */       } 
/* 1320 */       return null;
/*      */     }
/*      */     
/*      */     String getIndexFromProperty(String param1String) {
/* 1324 */       int i = param1String.indexOf('&');
/* 1325 */       return (i == -1) ? null : Integer.toString(i);
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/UIDefaults.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */