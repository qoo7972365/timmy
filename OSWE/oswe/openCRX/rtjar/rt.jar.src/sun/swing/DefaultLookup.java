/*     */ package sun.swing;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Insets;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import sun.awt.AppContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultLookup
/*     */ {
/*  50 */   private static final Object DEFAULT_LOOKUP_KEY = new StringBuffer("DefaultLookup");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Thread currentDefaultThread;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static DefaultLookup currentDefaultLookup;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isLookupSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setDefaultLookup(DefaultLookup paramDefaultLookup) {
/*  73 */     synchronized (DefaultLookup.class) {
/*  74 */       if (!isLookupSet && paramDefaultLookup == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/*  79 */       if (paramDefaultLookup == null)
/*     */       {
/*     */ 
/*     */         
/*  83 */         paramDefaultLookup = new DefaultLookup();
/*     */       }
/*  85 */       isLookupSet = true;
/*  86 */       AppContext.getAppContext().put(DEFAULT_LOOKUP_KEY, paramDefaultLookup);
/*  87 */       currentDefaultThread = Thread.currentThread();
/*  88 */       currentDefaultLookup = paramDefaultLookup;
/*     */     } 
/*     */   }
/*     */   public static Object get(JComponent paramJComponent, ComponentUI paramComponentUI, String paramString) {
/*     */     boolean bool;
/*     */     DefaultLookup defaultLookup;
/*  94 */     synchronized (DefaultLookup.class) {
/*  95 */       bool = isLookupSet;
/*     */     } 
/*  97 */     if (!bool)
/*     */     {
/*  99 */       return UIManager.get(paramString, paramJComponent.getLocale());
/*     */     }
/* 101 */     Thread thread = Thread.currentThread();
/*     */     
/* 103 */     synchronized (DefaultLookup.class) {
/*     */ 
/*     */       
/* 106 */       if (thread == currentDefaultThread) {
/*     */         
/* 108 */         defaultLookup = currentDefaultLookup;
/*     */       }
/*     */       else {
/*     */         
/* 112 */         defaultLookup = (DefaultLookup)AppContext.getAppContext().get(DEFAULT_LOOKUP_KEY);
/*     */         
/* 114 */         if (defaultLookup == null) {
/*     */ 
/*     */           
/* 117 */           defaultLookup = new DefaultLookup();
/* 118 */           AppContext.getAppContext().put(DEFAULT_LOOKUP_KEY, defaultLookup);
/*     */         } 
/*     */         
/* 121 */         currentDefaultThread = thread;
/* 122 */         currentDefaultLookup = defaultLookup;
/*     */       } 
/*     */     } 
/* 125 */     return defaultLookup.getDefault(paramJComponent, paramComponentUI, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getInt(JComponent paramJComponent, ComponentUI paramComponentUI, String paramString, int paramInt) {
/* 133 */     Object object = get(paramJComponent, paramComponentUI, paramString);
/*     */     
/* 135 */     if (object == null || !(object instanceof Number)) {
/* 136 */       return paramInt;
/*     */     }
/* 138 */     return ((Number)object).intValue();
/*     */   }
/*     */   
/*     */   public static int getInt(JComponent paramJComponent, ComponentUI paramComponentUI, String paramString) {
/* 142 */     return getInt(paramJComponent, paramComponentUI, paramString, -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Insets getInsets(JComponent paramJComponent, ComponentUI paramComponentUI, String paramString, Insets paramInsets) {
/* 147 */     Object object = get(paramJComponent, paramComponentUI, paramString);
/*     */     
/* 149 */     if (object == null || !(object instanceof Insets)) {
/* 150 */       return paramInsets;
/*     */     }
/* 152 */     return (Insets)object;
/*     */   }
/*     */   
/*     */   public static Insets getInsets(JComponent paramJComponent, ComponentUI paramComponentUI, String paramString) {
/* 156 */     return getInsets(paramJComponent, paramComponentUI, paramString, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean getBoolean(JComponent paramJComponent, ComponentUI paramComponentUI, String paramString, boolean paramBoolean) {
/* 161 */     Object object = get(paramJComponent, paramComponentUI, paramString);
/*     */     
/* 163 */     if (object == null || !(object instanceof Boolean)) {
/* 164 */       return paramBoolean;
/*     */     }
/* 166 */     return ((Boolean)object).booleanValue();
/*     */   }
/*     */   
/*     */   public static boolean getBoolean(JComponent paramJComponent, ComponentUI paramComponentUI, String paramString) {
/* 170 */     return getBoolean(paramJComponent, paramComponentUI, paramString, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Color getColor(JComponent paramJComponent, ComponentUI paramComponentUI, String paramString, Color paramColor) {
/* 175 */     Object object = get(paramJComponent, paramComponentUI, paramString);
/*     */     
/* 177 */     if (object == null || !(object instanceof Color)) {
/* 178 */       return paramColor;
/*     */     }
/* 180 */     return (Color)object;
/*     */   }
/*     */   
/*     */   public static Color getColor(JComponent paramJComponent, ComponentUI paramComponentUI, String paramString) {
/* 184 */     return getColor(paramJComponent, paramComponentUI, paramString, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Icon getIcon(JComponent paramJComponent, ComponentUI paramComponentUI, String paramString, Icon paramIcon) {
/* 189 */     Object object = get(paramJComponent, paramComponentUI, paramString);
/* 190 */     if (object == null || !(object instanceof Icon)) {
/* 191 */       return paramIcon;
/*     */     }
/* 193 */     return (Icon)object;
/*     */   }
/*     */   
/*     */   public static Icon getIcon(JComponent paramJComponent, ComponentUI paramComponentUI, String paramString) {
/* 197 */     return getIcon(paramJComponent, paramComponentUI, paramString, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Border getBorder(JComponent paramJComponent, ComponentUI paramComponentUI, String paramString, Border paramBorder) {
/* 202 */     Object object = get(paramJComponent, paramComponentUI, paramString);
/* 203 */     if (object == null || !(object instanceof Border)) {
/* 204 */       return paramBorder;
/*     */     }
/* 206 */     return (Border)object;
/*     */   }
/*     */   
/*     */   public static Border getBorder(JComponent paramJComponent, ComponentUI paramComponentUI, String paramString) {
/* 210 */     return getBorder(paramJComponent, paramComponentUI, paramString, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getDefault(JComponent paramJComponent, ComponentUI paramComponentUI, String paramString) {
/* 215 */     return UIManager.get(paramString, paramJComponent.getLocale());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/swing/DefaultLookup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */