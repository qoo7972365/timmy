/*     */ package com.sun.java.swing.plaf.gtk;
/*     */ 
/*     */ import java.awt.Font;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JScrollBar;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.synth.Region;
/*     */ import javax.swing.plaf.synth.SynthStyle;
/*     */ import javax.swing.plaf.synth.SynthStyleFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class GTKStyleFactory
/*     */   extends SynthStyleFactory
/*     */ {
/*  51 */   private final Map<Object, GTKStyle> stylesCache = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Font defaultFont;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized SynthStyle getStyle(JComponent paramJComponent, Region paramRegion) {
/*  62 */     GTKEngine.WidgetType widgetType2, widgetType1 = GTKEngine.getWidgetType(paramJComponent, paramRegion);
/*     */     
/*  64 */     ComplexKey complexKey = null;
/*  65 */     if (paramRegion == Region.SCROLL_BAR) {
/*     */ 
/*     */ 
/*     */       
/*  69 */       if (paramJComponent != null) {
/*  70 */         JScrollBar jScrollBar = (JScrollBar)paramJComponent;
/*  71 */         boolean bool1 = jScrollBar.getParent() instanceof javax.swing.JScrollPane;
/*  72 */         boolean bool = (jScrollBar.getOrientation() == 0) ? true : false;
/*  73 */         boolean bool2 = jScrollBar.getComponentOrientation().isLeftToRight();
/*  74 */         boolean bool3 = jScrollBar.isFocusable();
/*  75 */         complexKey = new ComplexKey(widgetType1, new Object[] { Boolean.valueOf(bool1), Boolean.valueOf(bool), Boolean.valueOf(bool2), Boolean.valueOf(bool3) });
/*     */       }
/*     */     
/*  78 */     } else if (paramRegion == Region.CHECK_BOX || paramRegion == Region.RADIO_BUTTON) {
/*     */ 
/*     */       
/*  81 */       if (paramJComponent != null) {
/*  82 */         boolean bool = paramJComponent.getComponentOrientation().isLeftToRight();
/*  83 */         complexKey = new ComplexKey(widgetType1, new Object[] { Boolean.valueOf(bool) });
/*     */       }
/*     */     
/*  86 */     } else if (paramRegion == Region.BUTTON) {
/*     */ 
/*     */       
/*  89 */       if (paramJComponent != null) {
/*  90 */         JButton jButton = (JButton)paramJComponent;
/*  91 */         boolean bool1 = jButton.getParent() instanceof javax.swing.JToolBar;
/*  92 */         boolean bool2 = jButton.isDefaultCapable();
/*  93 */         complexKey = new ComplexKey(widgetType1, new Object[] { Boolean.valueOf(bool1), Boolean.valueOf(bool2) });
/*     */       } 
/*  95 */     } else if (paramRegion == Region.MENU && 
/*  96 */       paramJComponent instanceof JMenu && ((JMenu)paramJComponent).isTopLevelMenu() && 
/*  97 */       UIManager.getBoolean("Menu.useMenuBarForTopLevelMenus")) {
/*  98 */       widgetType1 = GTKEngine.WidgetType.MENU_BAR;
/*     */     } 
/*     */ 
/*     */     
/* 102 */     if (complexKey == null)
/*     */     {
/* 104 */       widgetType2 = widgetType1;
/*     */     }
/*     */     
/* 107 */     GTKStyle gTKStyle = this.stylesCache.get(widgetType2);
/* 108 */     if (gTKStyle == null) {
/* 109 */       gTKStyle = new GTKStyle(this.defaultFont, widgetType1);
/* 110 */       this.stylesCache.put(widgetType2, gTKStyle);
/*     */     } 
/*     */     
/* 113 */     return gTKStyle;
/*     */   }
/*     */   
/*     */   void initStyles(Font paramFont) {
/* 117 */     this.defaultFont = paramFont;
/* 118 */     this.stylesCache.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class ComplexKey
/*     */   {
/*     */     private final GTKEngine.WidgetType wt;
/*     */ 
/*     */     
/*     */     private final Object[] args;
/*     */ 
/*     */     
/*     */     ComplexKey(GTKEngine.WidgetType param1WidgetType, Object... param1VarArgs) {
/* 132 */       this.wt = param1WidgetType;
/* 133 */       this.args = param1VarArgs;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 138 */       int i = this.wt.hashCode();
/* 139 */       if (this.args != null) {
/* 140 */         for (Object object : this.args) {
/* 141 */           i = i * 29 + ((object == null) ? 0 : object.hashCode());
/*     */         }
/*     */       }
/* 144 */       return i;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 149 */       if (!(param1Object instanceof ComplexKey)) {
/* 150 */         return false;
/*     */       }
/* 152 */       ComplexKey complexKey = (ComplexKey)param1Object;
/* 153 */       if (this.wt == complexKey.wt) {
/* 154 */         if (this.args == null && complexKey.args == null) {
/* 155 */           return true;
/*     */         }
/* 157 */         if (this.args != null && complexKey.args != null && this.args.length == complexKey.args.length) {
/*     */ 
/*     */           
/* 160 */           for (byte b = 0; b < this.args.length; ) {
/* 161 */             Object object1 = this.args[b];
/* 162 */             Object object2 = complexKey.args[b];
/* 163 */             if ((object1 == null) ? (object2 == null) : object1.equals(object2)) { b++; continue; }
/* 164 */              return false;
/*     */           } 
/*     */           
/* 167 */           return true;
/*     */         } 
/*     */       } 
/* 170 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 175 */       String str = "ComplexKey[wt=" + this.wt;
/* 176 */       if (this.args != null) {
/* 177 */         str = str + ",args=[";
/* 178 */         for (byte b = 0; b < this.args.length; b++) {
/* 179 */           str = str + this.args[b];
/* 180 */           if (b < this.args.length - 1) str = str + ","; 
/*     */         } 
/* 182 */         str = str + "]";
/*     */       } 
/* 184 */       str = str + "]";
/* 185 */       return str;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/gtk/GTKStyleFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */