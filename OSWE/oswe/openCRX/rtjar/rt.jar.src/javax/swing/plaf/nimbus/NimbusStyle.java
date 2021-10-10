/*      */ package javax.swing.plaf.nimbus;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.Font;
/*      */ import java.awt.Insets;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ import java.util.TreeMap;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.Painter;
/*      */ import javax.swing.UIDefaults;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.plaf.ColorUIResource;
/*      */ import javax.swing.plaf.synth.ColorType;
/*      */ import javax.swing.plaf.synth.SynthContext;
/*      */ import javax.swing.plaf.synth.SynthPainter;
/*      */ import javax.swing.plaf.synth.SynthStyle;
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
/*      */ public final class NimbusStyle
/*      */   extends SynthStyle
/*      */ {
/*      */   public static final String LARGE_KEY = "large";
/*      */   public static final String SMALL_KEY = "small";
/*      */   public static final String MINI_KEY = "mini";
/*      */   public static final double LARGE_SCALE = 1.15D;
/*      */   public static final double SMALL_SCALE = 0.857D;
/*      */   public static final double MINI_SCALE = 0.714D;
/*  137 */   private static final Object NULL = Character.valueOf(false);
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
/*  150 */   private static final Color DEFAULT_COLOR = new ColorUIResource(Color.BLACK);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  155 */   private static final Comparator<RuntimeState> STATE_COMPARATOR = new Comparator<RuntimeState>()
/*      */     {
/*      */       public int compare(NimbusStyle.RuntimeState param1RuntimeState1, NimbusStyle.RuntimeState param1RuntimeState2)
/*      */       {
/*  159 */         return param1RuntimeState1.state - param1RuntimeState2.state;
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String prefix;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private SynthPainter painter;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Values values;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  189 */   private CacheKey tmpKey = new CacheKey("", 0);
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
/*      */   private WeakReference<JComponent> component;
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
/*      */   NimbusStyle(String paramString, JComponent paramJComponent) {
/*  213 */     if (paramJComponent != null) {
/*  214 */       this.component = new WeakReference<>(paramJComponent);
/*      */     }
/*  216 */     this.prefix = paramString;
/*  217 */     this.painter = new SynthPainterImpl(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void installDefaults(SynthContext paramSynthContext) {
/*  227 */     validate();
/*      */ 
/*      */ 
/*      */     
/*  231 */     super.installDefaults(paramSynthContext);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void validate() {
/*      */     TreeMap<Object, Object> treeMap;
/*  241 */     if (this.values != null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  247 */     this.values = new Values();
/*      */ 
/*      */ 
/*      */     
/*  251 */     Map<String, Object> map = ((NimbusLookAndFeel)UIManager.getLookAndFeel()).getDefaultsForPrefix(this.prefix);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  256 */     if (this.component != null) {
/*      */ 
/*      */       
/*  259 */       Object object = ((JComponent)this.component.get()).getClientProperty("Nimbus.Overrides");
/*  260 */       if (object instanceof UIDefaults) {
/*  261 */         Object object1 = ((JComponent)this.component.get()).getClientProperty("Nimbus.Overrides.InheritDefaults");
/*      */         
/*  263 */         boolean bool = (object1 instanceof Boolean) ? ((Boolean)object1).booleanValue() : true;
/*  264 */         UIDefaults uIDefaults = (UIDefaults)object;
/*  265 */         TreeMap<Object, Object> treeMap1 = new TreeMap<>();
/*  266 */         for (String str1 : uIDefaults.keySet()) {
/*  267 */           if (str1 instanceof String) {
/*  268 */             String str2 = str1;
/*  269 */             if (str2.startsWith(this.prefix)) {
/*  270 */               treeMap1.put(str2, uIDefaults.get(str2));
/*      */             }
/*      */           } 
/*      */         } 
/*  274 */         if (bool) {
/*  275 */           map.putAll(treeMap1);
/*      */         } else {
/*  277 */           treeMap = treeMap1;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  286 */     ArrayList<State> arrayList = new ArrayList();
/*      */     
/*  288 */     HashMap<Object, Object> hashMap = new HashMap<>();
/*      */ 
/*      */     
/*  291 */     ArrayList<RuntimeState> arrayList1 = new ArrayList();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  298 */     String str = (String)treeMap.get(this.prefix + ".States");
/*  299 */     if (str != null) {
/*  300 */       String[] arrayOfString = str.split(","); int i;
/*  301 */       for (i = 0; i < arrayOfString.length; i++) {
/*  302 */         arrayOfString[i] = arrayOfString[i].trim();
/*  303 */         if (!State.isStandardStateName(arrayOfString[i])) {
/*      */ 
/*      */           
/*  306 */           String str1 = this.prefix + "." + arrayOfString[i];
/*  307 */           State state = (State)treeMap.get(str1);
/*  308 */           if (state != null) {
/*  309 */             arrayList.add(state);
/*      */           }
/*      */         } else {
/*  312 */           arrayList.add(State.getStandardState(arrayOfString[i]));
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  319 */       if (arrayList.size() > 0) {
/*  320 */         this.values.stateTypes = arrayList.<State>toArray(new State[arrayList.size()]);
/*      */       }
/*      */ 
/*      */       
/*  324 */       i = 1;
/*  325 */       for (State state : arrayList) {
/*  326 */         hashMap.put(state.getName(), Integer.valueOf(i));
/*  327 */         i <<= 1;
/*      */       
/*      */       }
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/*  336 */       arrayList.add(State.Enabled);
/*  337 */       arrayList.add(State.MouseOver);
/*  338 */       arrayList.add(State.Pressed);
/*  339 */       arrayList.add(State.Disabled);
/*  340 */       arrayList.add(State.Focused);
/*  341 */       arrayList.add(State.Selected);
/*  342 */       arrayList.add(State.Default);
/*      */ 
/*      */       
/*  345 */       hashMap.put("Enabled", Integer.valueOf(1));
/*  346 */       hashMap.put("MouseOver", Integer.valueOf(2));
/*  347 */       hashMap.put("Pressed", Integer.valueOf(4));
/*  348 */       hashMap.put("Disabled", Integer.valueOf(8));
/*  349 */       hashMap.put("Focused", Integer.valueOf(256));
/*  350 */       hashMap.put("Selected", Integer.valueOf(512));
/*  351 */       hashMap.put("Default", Integer.valueOf(1024));
/*      */     } 
/*      */ 
/*      */     
/*  355 */     for (String str1 : treeMap.keySet()) {
/*      */ 
/*      */ 
/*      */       
/*  359 */       String str2 = str1.substring(this.prefix.length());
/*      */ 
/*      */       
/*  362 */       if (str2.indexOf('"') != -1 || str2.indexOf(':') != -1)
/*      */         continue; 
/*  364 */       str2 = str2.substring(1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  372 */       String str3 = null;
/*  373 */       String str4 = null;
/*  374 */       int i = str2.indexOf(']');
/*  375 */       if (i < 0) {
/*      */         
/*  377 */         str4 = str2;
/*      */       } else {
/*  379 */         str3 = str2.substring(0, i);
/*  380 */         str4 = str2.substring(i + 2);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  385 */       if (str3 == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  392 */         if ("contentMargins".equals(str4)) {
/*  393 */           this.values.contentMargins = (Insets)treeMap.get(str1); continue;
/*  394 */         }  if ("States".equals(str4)) {
/*      */           continue;
/*      */         }
/*  397 */         this.values.defaults.put(str4, treeMap.get(str1));
/*      */ 
/*      */         
/*      */         continue;
/*      */       } 
/*      */ 
/*      */       
/*  404 */       boolean bool = false;
/*      */ 
/*      */       
/*  407 */       int j = 0;
/*      */ 
/*      */       
/*  410 */       String[] arrayOfString = str3.split("\\+");
/*      */ 
/*      */       
/*  413 */       for (String str5 : arrayOfString) {
/*  414 */         if (hashMap.containsKey(str5)) {
/*  415 */           j |= ((Integer)hashMap.get(str5)).intValue();
/*      */         }
/*      */         else {
/*      */           
/*  419 */           bool = true;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*  424 */       if (bool) {
/*      */         continue;
/*      */       }
/*  427 */       RuntimeState runtimeState = null;
/*  428 */       for (RuntimeState runtimeState1 : arrayList1) {
/*  429 */         if (runtimeState1.state == j) {
/*  430 */           runtimeState = runtimeState1;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */       
/*  436 */       if (runtimeState == null) {
/*  437 */         runtimeState = new RuntimeState(j, str3);
/*  438 */         arrayList1.add(runtimeState);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  446 */       if ("backgroundPainter".equals(str4)) {
/*  447 */         runtimeState.backgroundPainter = getPainter((Map)treeMap, str1); continue;
/*  448 */       }  if ("foregroundPainter".equals(str4)) {
/*  449 */         runtimeState.foregroundPainter = getPainter((Map)treeMap, str1); continue;
/*  450 */       }  if ("borderPainter".equals(str4)) {
/*  451 */         runtimeState.borderPainter = getPainter((Map)treeMap, str1); continue;
/*      */       } 
/*  453 */       runtimeState.defaults.put(str4, treeMap.get(str1));
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  460 */     Collections.sort(arrayList1, STATE_COMPARATOR);
/*      */ 
/*      */     
/*  463 */     this.values.states = arrayList1.<RuntimeState>toArray(new RuntimeState[arrayList1.size()]);
/*      */   }
/*      */   
/*      */   private Painter getPainter(Map<String, Object> paramMap, String paramString) {
/*  467 */     Object object = paramMap.get(paramString);
/*  468 */     if (object instanceof UIDefaults.LazyValue) {
/*  469 */       object = ((UIDefaults.LazyValue)object).createValue(UIManager.getDefaults());
/*      */     }
/*  471 */     return (object instanceof Painter) ? (Painter)object : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Insets getInsets(SynthContext paramSynthContext, Insets paramInsets) {
/*  481 */     if (paramInsets == null) {
/*  482 */       paramInsets = new Insets(0, 0, 0, 0);
/*      */     }
/*      */     
/*  485 */     Values values = getValues(paramSynthContext);
/*      */     
/*  487 */     if (values.contentMargins == null) {
/*  488 */       paramInsets.bottom = paramInsets.top = paramInsets.left = paramInsets.right = 0;
/*  489 */       return paramInsets;
/*      */     } 
/*  491 */     paramInsets.bottom = values.contentMargins.bottom;
/*  492 */     paramInsets.top = values.contentMargins.top;
/*  493 */     paramInsets.left = values.contentMargins.left;
/*  494 */     paramInsets.right = values.contentMargins.right;
/*      */ 
/*      */     
/*  497 */     String str = (String)paramSynthContext.getComponent().getClientProperty("JComponent.sizeVariant");
/*      */     
/*  499 */     if (str != null) {
/*  500 */       if ("large".equals(str)) {
/*  501 */         paramInsets.bottom = (int)(paramInsets.bottom * 1.15D);
/*  502 */         paramInsets.top = (int)(paramInsets.top * 1.15D);
/*  503 */         paramInsets.left = (int)(paramInsets.left * 1.15D);
/*  504 */         paramInsets.right = (int)(paramInsets.right * 1.15D);
/*  505 */       } else if ("small".equals(str)) {
/*  506 */         paramInsets.bottom = (int)(paramInsets.bottom * 0.857D);
/*  507 */         paramInsets.top = (int)(paramInsets.top * 0.857D);
/*  508 */         paramInsets.left = (int)(paramInsets.left * 0.857D);
/*  509 */         paramInsets.right = (int)(paramInsets.right * 0.857D);
/*  510 */       } else if ("mini".equals(str)) {
/*  511 */         paramInsets.bottom = (int)(paramInsets.bottom * 0.714D);
/*  512 */         paramInsets.top = (int)(paramInsets.top * 0.714D);
/*  513 */         paramInsets.left = (int)(paramInsets.left * 0.714D);
/*  514 */         paramInsets.right = (int)(paramInsets.right * 0.714D);
/*      */       } 
/*      */     }
/*  517 */     return paramInsets;
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
/*      */   protected Color getColorForState(SynthContext paramSynthContext, ColorType paramColorType) {
/*  541 */     String str = null;
/*  542 */     if (paramColorType == ColorType.BACKGROUND) {
/*  543 */       str = "background";
/*  544 */     } else if (paramColorType == ColorType.FOREGROUND) {
/*      */       
/*  546 */       str = "textForeground";
/*  547 */     } else if (paramColorType == ColorType.TEXT_BACKGROUND) {
/*  548 */       str = "textBackground";
/*  549 */     } else if (paramColorType == ColorType.TEXT_FOREGROUND) {
/*  550 */       str = "textForeground";
/*  551 */     } else if (paramColorType == ColorType.FOCUS) {
/*  552 */       str = "focus";
/*  553 */     } else if (paramColorType != null) {
/*  554 */       str = paramColorType.toString();
/*      */     } else {
/*  556 */       return DEFAULT_COLOR;
/*      */     } 
/*  558 */     Color color = (Color)get(paramSynthContext, str);
/*      */     
/*  560 */     if (color == null) color = DEFAULT_COLOR; 
/*  561 */     return color;
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
/*      */   protected Font getFontForState(SynthContext paramSynthContext) {
/*  573 */     Font font = (Font)get(paramSynthContext, "font");
/*  574 */     if (font == null) font = UIManager.getFont("defaultFont");
/*      */ 
/*      */ 
/*      */     
/*  578 */     String str = (String)paramSynthContext.getComponent().getClientProperty("JComponent.sizeVariant");
/*      */     
/*  580 */     if (str != null) {
/*  581 */       if ("large".equals(str)) {
/*  582 */         font = font.deriveFont((float)Math.round(font.getSize2D() * 1.15D));
/*  583 */       } else if ("small".equals(str)) {
/*  584 */         font = font.deriveFont((float)Math.round(font.getSize2D() * 0.857D));
/*  585 */       } else if ("mini".equals(str)) {
/*  586 */         font = font.deriveFont((float)Math.round(font.getSize2D() * 0.714D));
/*      */       } 
/*      */     }
/*  589 */     return font;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SynthPainter getPainter(SynthContext paramSynthContext) {
/*  599 */     return this.painter;
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
/*      */   public boolean isOpaque(SynthContext paramSynthContext) {
/*  611 */     if ("Table.cellRenderer".equals(paramSynthContext.getComponent().getName())) {
/*  612 */       return true;
/*      */     }
/*  614 */     Boolean bool = (Boolean)get(paramSynthContext, "opaque");
/*  615 */     return (bool == null) ? false : bool.booleanValue();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object get(SynthContext paramSynthContext, Object paramObject) {
/*  652 */     Values values = getValues(paramSynthContext);
/*      */ 
/*      */     
/*  655 */     String str1 = paramObject.toString();
/*  656 */     String str2 = str1.substring(str1.indexOf(".") + 1);
/*      */     
/*  658 */     Object object = null;
/*  659 */     int i = getExtendedState(paramSynthContext, values);
/*      */ 
/*      */     
/*  662 */     this.tmpKey.init(str2, i);
/*  663 */     object = values.cache.get(this.tmpKey);
/*  664 */     boolean bool = (object != null) ? true : false;
/*  665 */     if (!bool) {
/*      */       
/*  667 */       RuntimeState runtimeState = null;
/*  668 */       int[] arrayOfInt = { -1 };
/*  669 */       while (object == null && (
/*  670 */         runtimeState = getNextState(values.states, arrayOfInt, i)) != null) {
/*  671 */         object = runtimeState.defaults.get(str2);
/*      */       }
/*      */       
/*  674 */       if (object == null && values.defaults != null) {
/*  675 */         object = values.defaults.get(str2);
/*      */       }
/*      */ 
/*      */       
/*  679 */       if (object == null) object = UIManager.get(str1);
/*      */       
/*  681 */       if (object == null && str2.equals("focusInputMap")) {
/*  682 */         object = super.get(paramSynthContext, str1);
/*      */       }
/*      */       
/*  685 */       values.cache.put(new CacheKey(str2, i), (object == null) ? NULL : object);
/*      */     } 
/*      */ 
/*      */     
/*  689 */     return (object == NULL) ? null : object;
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
/*      */   public Painter getBackgroundPainter(SynthContext paramSynthContext) {
/*  702 */     Values values = getValues(paramSynthContext);
/*  703 */     int i = getExtendedState(paramSynthContext, values);
/*  704 */     Painter painter = null;
/*      */ 
/*      */     
/*  707 */     this.tmpKey.init("backgroundPainter$$instance", i);
/*  708 */     painter = (Painter)values.cache.get(this.tmpKey);
/*  709 */     if (painter != null) return painter;
/*      */ 
/*      */     
/*  712 */     RuntimeState runtimeState = null;
/*  713 */     int[] arrayOfInt = { -1 };
/*  714 */     while ((runtimeState = getNextState(values.states, arrayOfInt, i)) != null) {
/*  715 */       if (runtimeState.backgroundPainter != null) {
/*  716 */         painter = runtimeState.backgroundPainter;
/*      */         break;
/*      */       } 
/*      */     } 
/*  720 */     if (painter == null) painter = (Painter)get(paramSynthContext, "backgroundPainter"); 
/*  721 */     if (painter != null) {
/*  722 */       values.cache.put(new CacheKey("backgroundPainter$$instance", i), painter);
/*      */     }
/*  724 */     return painter;
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
/*      */   public Painter getForegroundPainter(SynthContext paramSynthContext) {
/*  737 */     Values values = getValues(paramSynthContext);
/*  738 */     int i = getExtendedState(paramSynthContext, values);
/*  739 */     Painter painter = null;
/*      */ 
/*      */     
/*  742 */     this.tmpKey.init("foregroundPainter$$instance", i);
/*  743 */     painter = (Painter)values.cache.get(this.tmpKey);
/*  744 */     if (painter != null) return painter;
/*      */ 
/*      */     
/*  747 */     RuntimeState runtimeState = null;
/*  748 */     int[] arrayOfInt = { -1 };
/*  749 */     while ((runtimeState = getNextState(values.states, arrayOfInt, i)) != null) {
/*  750 */       if (runtimeState.foregroundPainter != null) {
/*  751 */         painter = runtimeState.foregroundPainter;
/*      */         break;
/*      */       } 
/*      */     } 
/*  755 */     if (painter == null) painter = (Painter)get(paramSynthContext, "foregroundPainter"); 
/*  756 */     if (painter != null) {
/*  757 */       values.cache.put(new CacheKey("foregroundPainter$$instance", i), painter);
/*      */     }
/*  759 */     return painter;
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
/*      */   public Painter getBorderPainter(SynthContext paramSynthContext) {
/*  772 */     Values values = getValues(paramSynthContext);
/*  773 */     int i = getExtendedState(paramSynthContext, values);
/*  774 */     Painter painter = null;
/*      */ 
/*      */     
/*  777 */     this.tmpKey.init("borderPainter$$instance", i);
/*  778 */     painter = (Painter)values.cache.get(this.tmpKey);
/*  779 */     if (painter != null) return painter;
/*      */ 
/*      */     
/*  782 */     RuntimeState runtimeState = null;
/*  783 */     int[] arrayOfInt = { -1 };
/*  784 */     while ((runtimeState = getNextState(values.states, arrayOfInt, i)) != null) {
/*  785 */       if (runtimeState.borderPainter != null) {
/*  786 */         painter = runtimeState.borderPainter;
/*      */         break;
/*      */       } 
/*      */     } 
/*  790 */     if (painter == null) painter = (Painter)get(paramSynthContext, "borderPainter"); 
/*  791 */     if (painter != null) {
/*  792 */       values.cache.put(new CacheKey("borderPainter$$instance", i), painter);
/*      */     }
/*  794 */     return painter;
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
/*      */   private Values getValues(SynthContext paramSynthContext) {
/*  806 */     validate();
/*  807 */     return this.values;
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
/*      */   private boolean contains(String[] paramArrayOfString, String paramString) {
/*  822 */     assert paramString != null;
/*  823 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/*  824 */       if (paramString.equals(paramArrayOfString[b])) {
/*  825 */         return true;
/*      */       }
/*      */     } 
/*  828 */     return false;
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
/*      */   private int getExtendedState(SynthContext paramSynthContext, Values paramValues) {
/*  855 */     JComponent jComponent = paramSynthContext.getComponent();
/*  856 */     int i = 0;
/*  857 */     int j = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  862 */     Object object = jComponent.getClientProperty("Nimbus.State");
/*  863 */     if (object != null) {
/*  864 */       String str = object.toString();
/*  865 */       String[] arrayOfString = str.split("\\+");
/*  866 */       if (paramValues.stateTypes == null) {
/*      */         
/*  868 */         for (String str1 : arrayOfString) {
/*  869 */           State.StandardState standardState = State.getStandardState(str1);
/*  870 */           if (standardState != null) i |= standardState.getState();
/*      */         
/*      */         } 
/*      */       } else {
/*  874 */         for (State state : paramValues.stateTypes) {
/*  875 */           if (contains(arrayOfString, state.getName())) {
/*  876 */             i |= j;
/*      */           }
/*  878 */           j <<= 1;
/*      */         }
/*      */       
/*      */       } 
/*      */     } else {
/*      */       
/*  884 */       if (paramValues.stateTypes == null) return paramSynthContext.getComponentState();
/*      */ 
/*      */ 
/*      */       
/*  888 */       int k = paramSynthContext.getComponentState();
/*  889 */       for (State<JComponent> state : paramValues.stateTypes) {
/*  890 */         if (state.isInState(jComponent, k)) {
/*  891 */           i |= j;
/*      */         }
/*  893 */         j <<= 1;
/*      */       } 
/*      */     } 
/*  896 */     return i;
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
/*      */   private RuntimeState getNextState(RuntimeState[] paramArrayOfRuntimeState, int[] paramArrayOfint, int paramInt) {
/*  944 */     if (paramArrayOfRuntimeState != null && paramArrayOfRuntimeState.length > 0) {
/*  945 */       int i = 0;
/*  946 */       int j = -1;
/*  947 */       int k = -1;
/*      */ 
/*      */ 
/*      */       
/*  951 */       if (paramInt == 0) {
/*  952 */         for (int i1 = paramArrayOfRuntimeState.length - 1; i1 >= 0; i1--) {
/*  953 */           if ((paramArrayOfRuntimeState[i1]).state == 0) {
/*  954 */             paramArrayOfint[0] = i1;
/*  955 */             return paramArrayOfRuntimeState[i1];
/*      */           } 
/*      */         } 
/*      */         
/*  959 */         paramArrayOfint[0] = -1;
/*  960 */         return null;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  968 */       int m = (paramArrayOfint == null || paramArrayOfint[0] == -1) ? paramArrayOfRuntimeState.length : paramArrayOfint[0];
/*      */ 
/*      */       
/*  971 */       for (int n = m - 1; n >= 0; n--) {
/*  972 */         int i1 = (paramArrayOfRuntimeState[n]).state;
/*      */         
/*  974 */         if (i1 == 0) {
/*  975 */           if (k == -1) {
/*  976 */             k = n;
/*      */           }
/*  978 */         } else if ((paramInt & i1) == i1) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  985 */           int i2 = i1;
/*  986 */           i2 -= (0xAAAAAAAA & i2) >>> 1;
/*  987 */           i2 = (i2 & 0x33333333) + (i2 >>> 2 & 0x33333333);
/*      */           
/*  989 */           i2 = i2 + (i2 >>> 4) & 0xF0F0F0F;
/*  990 */           i2 += i2 >>> 8;
/*  991 */           i2 += i2 >>> 16;
/*  992 */           i2 &= 0xFF;
/*  993 */           if (i2 > i) {
/*  994 */             j = n;
/*  995 */             i = i2;
/*      */           } 
/*      */         } 
/*      */       } 
/*  999 */       if (j != -1) {
/* 1000 */         paramArrayOfint[0] = j;
/* 1001 */         return paramArrayOfRuntimeState[j];
/*      */       } 
/* 1003 */       if (k != -1) {
/* 1004 */         paramArrayOfint[0] = k;
/* 1005 */         return paramArrayOfRuntimeState[k];
/*      */       } 
/*      */     } 
/* 1008 */     paramArrayOfint[0] = -1;
/* 1009 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   private final class RuntimeState
/*      */     implements Cloneable
/*      */   {
/*      */     int state;
/*      */     
/*      */     Painter backgroundPainter;
/*      */     
/*      */     Painter foregroundPainter;
/*      */     
/*      */     Painter borderPainter;
/*      */     
/*      */     String stateName;
/* 1025 */     UIDefaults defaults = new UIDefaults(10, 0.7F);
/*      */     
/*      */     private RuntimeState(int param1Int, String param1String) {
/* 1028 */       this.state = param1Int;
/* 1029 */       this.stateName = param1String;
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 1034 */       return this.stateName;
/*      */     }
/*      */ 
/*      */     
/*      */     public RuntimeState clone() {
/* 1039 */       RuntimeState runtimeState = new RuntimeState(this.state, this.stateName);
/* 1040 */       runtimeState.backgroundPainter = this.backgroundPainter;
/* 1041 */       runtimeState.foregroundPainter = this.foregroundPainter;
/* 1042 */       runtimeState.borderPainter = this.borderPainter;
/* 1043 */       runtimeState.defaults.putAll(this.defaults);
/* 1044 */       return runtimeState;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class Values
/*      */   {
/*      */     private Values() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1058 */     State[] stateTypes = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1064 */     NimbusStyle.RuntimeState[] states = null;
/*      */ 
/*      */ 
/*      */     
/*      */     Insets contentMargins;
/*      */ 
/*      */ 
/*      */     
/* 1072 */     UIDefaults defaults = new UIDefaults(10, 0.7F);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1081 */     Map<NimbusStyle.CacheKey, Object> cache = new HashMap<>();
/*      */   }
/*      */ 
/*      */   
/*      */   private static final class CacheKey
/*      */   {
/*      */     private String key;
/*      */     
/*      */     private int xstate;
/*      */ 
/*      */     
/*      */     CacheKey(Object param1Object, int param1Int) {
/* 1093 */       init(param1Object, param1Int);
/*      */     }
/*      */     
/*      */     void init(Object param1Object, int param1Int) {
/* 1097 */       this.key = param1Object.toString();
/* 1098 */       this.xstate = param1Int;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object param1Object) {
/* 1103 */       CacheKey cacheKey = (CacheKey)param1Object;
/* 1104 */       if (param1Object == null) return false; 
/* 1105 */       if (this.xstate != cacheKey.xstate) return false; 
/* 1106 */       if (!this.key.equals(cacheKey.key)) return false; 
/* 1107 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/* 1112 */       int i = 3;
/* 1113 */       i = 29 * i + this.key.hashCode();
/* 1114 */       i = 29 * i + this.xstate;
/* 1115 */       return i;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/nimbus/NimbusStyle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */