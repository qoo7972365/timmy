/*     */ package com.sun.java.swing.plaf.gtk;
/*     */ 
/*     */ import java.awt.Graphics;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JToolBar;
/*     */ import javax.swing.plaf.UIResource;
/*     */ import javax.swing.plaf.synth.Region;
/*     */ import javax.swing.plaf.synth.SynthContext;
/*     */ import javax.swing.plaf.synth.SynthLookAndFeel;
/*     */ import javax.swing.plaf.synth.SynthStyle;
/*     */ import sun.swing.plaf.synth.SynthIcon;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class GTKIconFactory
/*     */ {
/*     */   static final int CHECK_ICON_EXTRA_INSET = 1;
/*     */   static final int DEFAULT_ICON_SPACING = 2;
/*     */   static final int DEFAULT_ICON_SIZE = 13;
/*     */   static final int DEFAULT_TOGGLE_MENU_ITEM_SIZE = 12;
/*     */   private static final String RADIO_BUTTON_ICON = "paintRadioButtonIcon";
/*     */   private static final String CHECK_BOX_ICON = "paintCheckBoxIcon";
/*     */   private static final String MENU_ARROW_ICON = "paintMenuArrowIcon";
/*     */   private static final String CHECK_BOX_MENU_ITEM_CHECK_ICON = "paintCheckBoxMenuItemCheckIcon";
/*     */   private static final String RADIO_BUTTON_MENU_ITEM_CHECK_ICON = "paintRadioButtonMenuItemCheckIcon";
/*     */   private static final String TREE_EXPANDED_ICON = "paintTreeExpandedIcon";
/*     */   private static final String TREE_COLLAPSED_ICON = "paintTreeCollapsedIcon";
/*     */   private static final String ASCENDING_SORT_ICON = "paintAscendingSortIcon";
/*     */   private static final String DESCENDING_SORT_ICON = "paintDescendingSortIcon";
/*     */   private static final String TOOL_BAR_HANDLE_ICON = "paintToolBarHandleIcon";
/*  62 */   private static Map<String, DelegatingIcon> iconsPool = Collections.synchronizedMap(new HashMap<>());
/*     */   
/*     */   private static DelegatingIcon getIcon(String paramString) {
/*  65 */     DelegatingIcon delegatingIcon = iconsPool.get(paramString);
/*  66 */     if (delegatingIcon == null) {
/*  67 */       if (paramString == "paintTreeCollapsedIcon" || paramString == "paintTreeExpandedIcon") {
/*     */ 
/*     */         
/*  70 */         delegatingIcon = new SynthExpanderIcon(paramString);
/*     */       }
/*  72 */       else if (paramString == "paintToolBarHandleIcon") {
/*  73 */         delegatingIcon = new ToolBarHandleIcon();
/*     */       }
/*  75 */       else if (paramString == "paintMenuArrowIcon") {
/*  76 */         delegatingIcon = new MenuArrowIcon();
/*     */       } else {
/*     */         
/*  79 */         delegatingIcon = new DelegatingIcon(paramString);
/*     */       } 
/*  81 */       iconsPool.put(paramString, delegatingIcon);
/*     */     } 
/*  83 */     return delegatingIcon;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Icon getAscendingSortIcon() {
/*  90 */     return (Icon)getIcon("paintAscendingSortIcon");
/*     */   }
/*     */   
/*     */   public static Icon getDescendingSortIcon() {
/*  94 */     return (Icon)getIcon("paintDescendingSortIcon");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SynthIcon getTreeExpandedIcon() {
/* 101 */     return getIcon("paintTreeExpandedIcon");
/*     */   }
/*     */   
/*     */   public static SynthIcon getTreeCollapsedIcon() {
/* 105 */     return getIcon("paintTreeCollapsedIcon");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SynthIcon getRadioButtonIcon() {
/* 112 */     return getIcon("paintRadioButtonIcon");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SynthIcon getCheckBoxIcon() {
/* 119 */     return getIcon("paintCheckBoxIcon");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SynthIcon getMenuArrowIcon() {
/* 126 */     return getIcon("paintMenuArrowIcon");
/*     */   }
/*     */   
/*     */   public static SynthIcon getCheckBoxMenuItemCheckIcon() {
/* 130 */     return getIcon("paintCheckBoxMenuItemCheckIcon");
/*     */   }
/*     */   
/*     */   public static SynthIcon getRadioButtonMenuItemCheckIcon() {
/* 134 */     return getIcon("paintRadioButtonMenuItemCheckIcon");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SynthIcon getToolBarHandleIcon() {
/* 141 */     return getIcon("paintToolBarHandleIcon");
/*     */   }
/*     */   
/*     */   static void resetIcons() {
/* 145 */     synchronized (iconsPool) {
/* 146 */       for (DelegatingIcon delegatingIcon : iconsPool.values())
/* 147 */         delegatingIcon.resetIconDimensions(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class DelegatingIcon
/*     */     extends SynthIcon
/*     */     implements UIResource {
/* 154 */     private static final Class[] PARAM_TYPES = new Class[] { SynthContext.class, Graphics.class, int.class, int.class, int.class, int.class, int.class };
/*     */ 
/*     */     
/*     */     private Object method;
/*     */ 
/*     */     
/* 160 */     int iconDimension = -1;
/*     */     
/*     */     DelegatingIcon(String param1String) {
/* 163 */       this.method = param1String;
/*     */     }
/*     */ 
/*     */     
/*     */     public void paintIcon(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 168 */       if (param1SynthContext != null) {
/* 169 */         GTKPainter.INSTANCE.paintIcon(param1SynthContext, param1Graphics, 
/* 170 */             getMethod(), param1Int1, param1Int2, param1Int3, param1Int4);
/*     */       }
/*     */     }
/*     */     
/*     */     public int getIconWidth(SynthContext param1SynthContext) {
/* 175 */       return getIconDimension(param1SynthContext);
/*     */     }
/*     */     
/*     */     public int getIconHeight(SynthContext param1SynthContext) {
/* 179 */       return getIconDimension(param1SynthContext);
/*     */     }
/*     */     
/*     */     void resetIconDimensions() {
/* 183 */       this.iconDimension = -1;
/*     */     }
/*     */     
/*     */     protected Method getMethod() {
/* 187 */       if (this.method instanceof String) {
/* 188 */         this.method = resolveMethod((String)this.method);
/*     */       }
/* 190 */       return (Method)this.method;
/*     */     }
/*     */     
/*     */     protected Class[] getMethodParamTypes() {
/* 194 */       return PARAM_TYPES;
/*     */     }
/*     */     
/*     */     private Method resolveMethod(String param1String) {
/*     */       try {
/* 199 */         return GTKPainter.class.getMethod(param1String, getMethodParamTypes());
/* 200 */       } catch (NoSuchMethodException noSuchMethodException) {
/*     */         assert false;
/*     */         
/* 203 */         return null;
/*     */       } 
/*     */     }
/*     */     int getIconDimension(SynthContext param1SynthContext) {
/* 207 */       if (this.iconDimension >= 0) {
/* 208 */         return this.iconDimension;
/*     */       }
/*     */       
/* 211 */       if (param1SynthContext == null) {
/* 212 */         return 13;
/*     */       }
/*     */       
/* 215 */       Region region = param1SynthContext.getRegion();
/* 216 */       GTKStyle gTKStyle = (GTKStyle)param1SynthContext.getStyle();
/* 217 */       if (GTKLookAndFeel.is3() && region == Region.MENU) {
/* 218 */         Object object = gTKStyle.getClassSpecificValue("arrow-scaling");
/* 219 */         if (object instanceof Number) {
/* 220 */           this
/*     */             
/* 222 */             .iconDimension = (int)(((Number)object).floatValue() * (gTKStyle.getFont(param1SynthContext).getSize2D() + (2 * gTKStyle.getClassSpecificIntValue(param1SynthContext, "indicator-spacing", 2))));
/*     */           
/* 224 */           if (this.iconDimension > 0) {
/* 225 */             return this.iconDimension;
/*     */           }
/*     */         } 
/*     */       } 
/* 229 */       this.iconDimension = gTKStyle.getClassSpecificIntValue(param1SynthContext, "indicator-size", (region == Region.CHECK_BOX_MENU_ITEM || region == Region.RADIO_BUTTON_MENU_ITEM) ? 12 : 13);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 235 */       if (region == Region.CHECK_BOX || region == Region.RADIO_BUTTON) {
/* 236 */         this.iconDimension += 2 * gTKStyle.getClassSpecificIntValue(param1SynthContext, "indicator-spacing", 2);
/*     */       }
/* 238 */       else if (region == Region.CHECK_BOX_MENU_ITEM || region == Region.RADIO_BUTTON_MENU_ITEM) {
/*     */         
/* 240 */         this.iconDimension += 2;
/*     */       } 
/* 242 */       return this.iconDimension;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class SynthExpanderIcon extends DelegatingIcon {
/*     */     SynthExpanderIcon(String param1String) {
/* 248 */       super(param1String);
/*     */     }
/*     */ 
/*     */     
/*     */     public void paintIcon(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 253 */       if (param1SynthContext != null) {
/* 254 */         super.paintIcon(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/* 255 */         updateSizeIfNecessary(param1SynthContext);
/*     */       } 
/*     */     }
/*     */     
/*     */     int getIconDimension(SynthContext param1SynthContext) {
/* 260 */       updateSizeIfNecessary(param1SynthContext);
/* 261 */       return (this.iconDimension == -1) ? 13 : this.iconDimension;
/*     */     }
/*     */ 
/*     */     
/*     */     private void updateSizeIfNecessary(SynthContext param1SynthContext) {
/* 266 */       if (this.iconDimension == -1 && param1SynthContext != null) {
/* 267 */         this.iconDimension = param1SynthContext.getStyle().getInt(param1SynthContext, "Tree.expanderSize", 10);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class ToolBarHandleIcon
/*     */     extends DelegatingIcon
/*     */   {
/* 277 */     private static final Class[] PARAM_TYPES = new Class[] { SynthContext.class, Graphics.class, int.class, int.class, int.class, int.class, int.class, GTKConstants.Orientation.class };
/*     */ 
/*     */     
/*     */     private SynthStyle style;
/*     */ 
/*     */ 
/*     */     
/*     */     public ToolBarHandleIcon() {
/* 285 */       super("paintToolBarHandleIcon");
/*     */     }
/*     */     
/*     */     protected Class[] getMethodParamTypes() {
/* 289 */       return PARAM_TYPES;
/*     */     }
/*     */ 
/*     */     
/*     */     public void paintIcon(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 294 */       if (param1SynthContext != null) {
/* 295 */         JToolBar jToolBar = (JToolBar)param1SynthContext.getComponent();
/*     */         
/* 297 */         GTKConstants.Orientation orientation = (jToolBar.getOrientation() == 0) ? GTKConstants.Orientation.HORIZONTAL : GTKConstants.Orientation.VERTICAL;
/*     */ 
/*     */         
/* 300 */         if (this.style == null) {
/* 301 */           this.style = SynthLookAndFeel.getStyleFactory().getStyle(param1SynthContext
/* 302 */               .getComponent(), GTKRegion.HANDLE_BOX);
/*     */         }
/* 304 */         param1SynthContext = new SynthContext(jToolBar, GTKRegion.HANDLE_BOX, this.style, 1);
/*     */ 
/*     */         
/* 307 */         GTKPainter.INSTANCE.paintIcon(param1SynthContext, param1Graphics, 
/* 308 */             getMethod(), param1Int1, param1Int2, param1Int3, param1Int4, orientation);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int getIconWidth(SynthContext param1SynthContext) {
/* 313 */       if (param1SynthContext == null) {
/* 314 */         return 10;
/*     */       }
/* 316 */       if (((JToolBar)param1SynthContext.getComponent()).getOrientation() == 0)
/*     */       {
/* 318 */         return 10;
/*     */       }
/* 320 */       return param1SynthContext.getComponent().getWidth();
/*     */     }
/*     */ 
/*     */     
/*     */     public int getIconHeight(SynthContext param1SynthContext) {
/* 325 */       if (param1SynthContext == null) {
/* 326 */         return 10;
/*     */       }
/* 328 */       if (((JToolBar)param1SynthContext.getComponent()).getOrientation() == 0)
/*     */       {
/* 330 */         return param1SynthContext.getComponent().getHeight();
/*     */       }
/* 332 */       return 10;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class MenuArrowIcon
/*     */     extends DelegatingIcon {
/* 338 */     private static final Class[] PARAM_TYPES = new Class[] { SynthContext.class, Graphics.class, int.class, int.class, int.class, int.class, int.class, GTKConstants.ArrowType.class };
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public MenuArrowIcon() {
/* 344 */       super("paintMenuArrowIcon");
/*     */     }
/*     */     
/*     */     protected Class[] getMethodParamTypes() {
/* 348 */       return PARAM_TYPES;
/*     */     }
/*     */ 
/*     */     
/*     */     public void paintIcon(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 353 */       if (param1SynthContext != null) {
/* 354 */         GTKConstants.ArrowType arrowType = GTKConstants.ArrowType.RIGHT;
/* 355 */         if (!param1SynthContext.getComponent().getComponentOrientation().isLeftToRight()) {
/* 356 */           arrowType = GTKConstants.ArrowType.LEFT;
/*     */         }
/* 358 */         GTKPainter.INSTANCE.paintIcon(param1SynthContext, param1Graphics, 
/* 359 */             getMethod(), param1Int1, param1Int2, param1Int3, param1Int4, arrowType);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/gtk/GTKIconFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */