/*      */ package javax.swing.plaf.basic;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Insets;
/*      */ import java.awt.LayoutManager;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.event.FocusEvent;
/*      */ import java.awt.event.FocusListener;
/*      */ import java.awt.event.MouseAdapter;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.awt.event.MouseMotionListener;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import javax.swing.BoundedRangeModel;
/*      */ import javax.swing.InputMap;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JList;
/*      */ import javax.swing.JScrollBar;
/*      */ import javax.swing.JScrollPane;
/*      */ import javax.swing.JViewport;
/*      */ import javax.swing.LookAndFeel;
/*      */ import javax.swing.SwingConstants;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.Timer;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.event.ChangeEvent;
/*      */ import javax.swing.event.ChangeListener;
/*      */ import javax.swing.plaf.ComponentUI;
/*      */ import javax.swing.plaf.ScrollBarUI;
/*      */ import sun.swing.DefaultLookup;
/*      */ import sun.swing.SwingUtilities2;
/*      */ import sun.swing.UIAction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class BasicScrollBarUI
/*      */   extends ScrollBarUI
/*      */   implements LayoutManager, SwingConstants
/*      */ {
/*      */   private static final int POSITIVE_SCROLL = 1;
/*      */   private static final int NEGATIVE_SCROLL = -1;
/*      */   private static final int MIN_SCROLL = 2;
/*      */   private static final int MAX_SCROLL = 3;
/*      */   protected Dimension minimumThumbSize;
/*      */   protected Dimension maximumThumbSize;
/*      */   protected Color thumbHighlightColor;
/*      */   protected Color thumbLightShadowColor;
/*      */   protected Color thumbDarkShadowColor;
/*      */   protected Color thumbColor;
/*      */   protected Color trackColor;
/*      */   protected Color trackHighlightColor;
/*      */   protected JScrollBar scrollbar;
/*      */   protected JButton incrButton;
/*      */   protected JButton decrButton;
/*      */   protected boolean isDragging;
/*      */   protected TrackListener trackListener;
/*      */   protected ArrowButtonListener buttonListener;
/*      */   protected ModelListener modelListener;
/*      */   protected Rectangle thumbRect;
/*      */   protected Rectangle trackRect;
/*      */   protected int trackHighlight;
/*      */   protected static final int NO_HIGHLIGHT = 0;
/*      */   protected static final int DECREASE_HIGHLIGHT = 1;
/*      */   protected static final int INCREASE_HIGHLIGHT = 2;
/*      */   protected ScrollListener scrollListener;
/*      */   protected PropertyChangeListener propertyChangeListener;
/*      */   protected Timer scrollTimer;
/*      */   private static final int scrollSpeedThrottle = 60;
/*      */   private boolean supportsAbsolutePositioning;
/*      */   protected int scrollBarWidth;
/*      */   private Handler handler;
/*      */   private boolean thumbActive;
/*      */   private boolean useCachedValue = false;
/*      */   private int scrollBarValue;
/*      */   protected int incrGap;
/*      */   protected int decrGap;
/*      */   
/*      */   static void loadActionMap(LazyActionMap paramLazyActionMap) {
/*  141 */     paramLazyActionMap.put(new Actions("positiveUnitIncrement"));
/*  142 */     paramLazyActionMap.put(new Actions("positiveBlockIncrement"));
/*  143 */     paramLazyActionMap.put(new Actions("negativeUnitIncrement"));
/*  144 */     paramLazyActionMap.put(new Actions("negativeBlockIncrement"));
/*  145 */     paramLazyActionMap.put(new Actions("minScroll"));
/*  146 */     paramLazyActionMap.put(new Actions("maxScroll"));
/*      */   }
/*      */ 
/*      */   
/*      */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  151 */     return new BasicScrollBarUI();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void configureScrollBarColors() {
/*  157 */     LookAndFeel.installColors(this.scrollbar, "ScrollBar.background", "ScrollBar.foreground");
/*      */     
/*  159 */     this.thumbHighlightColor = UIManager.getColor("ScrollBar.thumbHighlight");
/*  160 */     this.thumbLightShadowColor = UIManager.getColor("ScrollBar.thumbShadow");
/*  161 */     this.thumbDarkShadowColor = UIManager.getColor("ScrollBar.thumbDarkShadow");
/*  162 */     this.thumbColor = UIManager.getColor("ScrollBar.thumb");
/*  163 */     this.trackColor = UIManager.getColor("ScrollBar.track");
/*  164 */     this.trackHighlightColor = UIManager.getColor("ScrollBar.trackHighlight");
/*      */   }
/*      */ 
/*      */   
/*      */   public void installUI(JComponent paramJComponent) {
/*  169 */     this.scrollbar = (JScrollBar)paramJComponent;
/*  170 */     this.thumbRect = new Rectangle(0, 0, 0, 0);
/*  171 */     this.trackRect = new Rectangle(0, 0, 0, 0);
/*  172 */     installDefaults();
/*  173 */     installComponents();
/*  174 */     installListeners();
/*  175 */     installKeyboardActions();
/*      */   }
/*      */   
/*      */   public void uninstallUI(JComponent paramJComponent) {
/*  179 */     this.scrollbar = (JScrollBar)paramJComponent;
/*  180 */     uninstallListeners();
/*  181 */     uninstallDefaults();
/*  182 */     uninstallComponents();
/*  183 */     uninstallKeyboardActions();
/*  184 */     this.thumbRect = null;
/*  185 */     this.scrollbar = null;
/*  186 */     this.incrButton = null;
/*  187 */     this.decrButton = null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void installDefaults() {
/*  193 */     this.scrollBarWidth = UIManager.getInt("ScrollBar.width");
/*  194 */     if (this.scrollBarWidth <= 0) {
/*  195 */       this.scrollBarWidth = 16;
/*      */     }
/*  197 */     this.minimumThumbSize = (Dimension)UIManager.get("ScrollBar.minimumThumbSize");
/*  198 */     this.maximumThumbSize = (Dimension)UIManager.get("ScrollBar.maximumThumbSize");
/*      */     
/*  200 */     Boolean bool = (Boolean)UIManager.get("ScrollBar.allowsAbsolutePositioning");
/*  201 */     this.supportsAbsolutePositioning = (bool != null) ? bool.booleanValue() : false;
/*      */ 
/*      */     
/*  204 */     this.trackHighlight = 0;
/*  205 */     if (this.scrollbar.getLayout() == null || this.scrollbar
/*  206 */       .getLayout() instanceof javax.swing.plaf.UIResource) {
/*  207 */       this.scrollbar.setLayout(this);
/*      */     }
/*  209 */     configureScrollBarColors();
/*  210 */     LookAndFeel.installBorder(this.scrollbar, "ScrollBar.border");
/*  211 */     LookAndFeel.installProperty(this.scrollbar, "opaque", Boolean.TRUE);
/*      */     
/*  213 */     this.scrollBarValue = this.scrollbar.getValue();
/*      */     
/*  215 */     this.incrGap = UIManager.getInt("ScrollBar.incrementButtonGap");
/*  216 */     this.decrGap = UIManager.getInt("ScrollBar.decrementButtonGap");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  222 */     String str = (String)this.scrollbar.getClientProperty("JComponent.sizeVariant");
/*      */     
/*  224 */     if (str != null) {
/*  225 */       if ("large".equals(str)) {
/*  226 */         this.scrollBarWidth = (int)(this.scrollBarWidth * 1.15D);
/*  227 */         this.incrGap = (int)(this.incrGap * 1.15D);
/*  228 */         this.decrGap = (int)(this.decrGap * 1.15D);
/*  229 */       } else if ("small".equals(str)) {
/*  230 */         this.scrollBarWidth = (int)(this.scrollBarWidth * 0.857D);
/*  231 */         this.incrGap = (int)(this.incrGap * 0.857D);
/*  232 */         this.decrGap = (int)(this.decrGap * 0.714D);
/*  233 */       } else if ("mini".equals(str)) {
/*  234 */         this.scrollBarWidth = (int)(this.scrollBarWidth * 0.714D);
/*  235 */         this.incrGap = (int)(this.incrGap * 0.714D);
/*  236 */         this.decrGap = (int)(this.decrGap * 0.714D);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void installComponents() {
/*  243 */     switch (this.scrollbar.getOrientation()) {
/*      */       case 1:
/*  245 */         this.incrButton = createIncreaseButton(5);
/*  246 */         this.decrButton = createDecreaseButton(1);
/*      */         break;
/*      */       
/*      */       case 0:
/*  250 */         if (this.scrollbar.getComponentOrientation().isLeftToRight()) {
/*  251 */           this.incrButton = createIncreaseButton(3);
/*  252 */           this.decrButton = createDecreaseButton(7); break;
/*      */         } 
/*  254 */         this.incrButton = createIncreaseButton(7);
/*  255 */         this.decrButton = createDecreaseButton(3);
/*      */         break;
/*      */     } 
/*      */     
/*  259 */     this.scrollbar.add(this.incrButton);
/*  260 */     this.scrollbar.add(this.decrButton);
/*      */     
/*  262 */     this.scrollbar.setEnabled(this.scrollbar.isEnabled());
/*      */   }
/*      */   
/*      */   protected void uninstallComponents() {
/*  266 */     this.scrollbar.remove(this.incrButton);
/*  267 */     this.scrollbar.remove(this.decrButton);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void installListeners() {
/*  272 */     this.trackListener = createTrackListener();
/*  273 */     this.buttonListener = createArrowButtonListener();
/*  274 */     this.modelListener = createModelListener();
/*  275 */     this.propertyChangeListener = createPropertyChangeListener();
/*      */     
/*  277 */     this.scrollbar.addMouseListener(this.trackListener);
/*  278 */     this.scrollbar.addMouseMotionListener(this.trackListener);
/*  279 */     this.scrollbar.getModel().addChangeListener(this.modelListener);
/*  280 */     this.scrollbar.addPropertyChangeListener(this.propertyChangeListener);
/*  281 */     this.scrollbar.addFocusListener(getHandler());
/*      */     
/*  283 */     if (this.incrButton != null) {
/*  284 */       this.incrButton.addMouseListener(this.buttonListener);
/*      */     }
/*  286 */     if (this.decrButton != null) {
/*  287 */       this.decrButton.addMouseListener(this.buttonListener);
/*      */     }
/*      */     
/*  290 */     this.scrollListener = createScrollListener();
/*  291 */     this.scrollTimer = new Timer(60, this.scrollListener);
/*  292 */     this.scrollTimer.setInitialDelay(300);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void installKeyboardActions() {
/*  297 */     LazyActionMap.installLazyActionMap(this.scrollbar, BasicScrollBarUI.class, "ScrollBar.actionMap");
/*      */ 
/*      */     
/*  300 */     InputMap inputMap = getInputMap(0);
/*  301 */     SwingUtilities.replaceUIInputMap(this.scrollbar, 0, inputMap);
/*      */     
/*  303 */     inputMap = getInputMap(1);
/*  304 */     SwingUtilities.replaceUIInputMap(this.scrollbar, 1, inputMap);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void uninstallKeyboardActions() {
/*  309 */     SwingUtilities.replaceUIInputMap(this.scrollbar, 0, null);
/*      */     
/*  311 */     SwingUtilities.replaceUIActionMap(this.scrollbar, null);
/*      */   }
/*      */   
/*      */   private InputMap getInputMap(int paramInt) {
/*  315 */     if (paramInt == 0) {
/*  316 */       InputMap inputMap1 = (InputMap)DefaultLookup.get(this.scrollbar, this, "ScrollBar.focusInputMap");
/*      */       
/*      */       InputMap inputMap2;
/*      */       
/*  320 */       if (this.scrollbar.getComponentOrientation().isLeftToRight() || (
/*  321 */         inputMap2 = (InputMap)DefaultLookup.get(this.scrollbar, this, "ScrollBar.focusInputMap.RightToLeft")) == null) {
/*  322 */         return inputMap1;
/*      */       }
/*  324 */       inputMap2.setParent(inputMap1);
/*  325 */       return inputMap2;
/*      */     } 
/*      */     
/*  328 */     if (paramInt == 1) {
/*  329 */       InputMap inputMap1 = (InputMap)DefaultLookup.get(this.scrollbar, this, "ScrollBar.ancestorInputMap");
/*      */       
/*      */       InputMap inputMap2;
/*      */       
/*  333 */       if (this.scrollbar.getComponentOrientation().isLeftToRight() || (
/*  334 */         inputMap2 = (InputMap)DefaultLookup.get(this.scrollbar, this, "ScrollBar.ancestorInputMap.RightToLeft")) == null) {
/*  335 */         return inputMap1;
/*      */       }
/*  337 */       inputMap2.setParent(inputMap1);
/*  338 */       return inputMap2;
/*      */     } 
/*      */     
/*  341 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void uninstallListeners() {
/*  346 */     this.scrollTimer.stop();
/*  347 */     this.scrollTimer = null;
/*      */     
/*  349 */     if (this.decrButton != null) {
/*  350 */       this.decrButton.removeMouseListener(this.buttonListener);
/*      */     }
/*  352 */     if (this.incrButton != null) {
/*  353 */       this.incrButton.removeMouseListener(this.buttonListener);
/*      */     }
/*      */     
/*  356 */     this.scrollbar.getModel().removeChangeListener(this.modelListener);
/*  357 */     this.scrollbar.removeMouseListener(this.trackListener);
/*  358 */     this.scrollbar.removeMouseMotionListener(this.trackListener);
/*  359 */     this.scrollbar.removePropertyChangeListener(this.propertyChangeListener);
/*  360 */     this.scrollbar.removeFocusListener(getHandler());
/*  361 */     this.handler = null;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void uninstallDefaults() {
/*  366 */     LookAndFeel.uninstallBorder(this.scrollbar);
/*  367 */     if (this.scrollbar.getLayout() == this) {
/*  368 */       this.scrollbar.setLayout((LayoutManager)null);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private Handler getHandler() {
/*  374 */     if (this.handler == null) {
/*  375 */       this.handler = new Handler();
/*      */     }
/*  377 */     return this.handler;
/*      */   }
/*      */   
/*      */   protected TrackListener createTrackListener() {
/*  381 */     return new TrackListener();
/*      */   }
/*      */   
/*      */   protected ArrowButtonListener createArrowButtonListener() {
/*  385 */     return new ArrowButtonListener();
/*      */   }
/*      */   
/*      */   protected ModelListener createModelListener() {
/*  389 */     return new ModelListener();
/*      */   }
/*      */   
/*      */   protected ScrollListener createScrollListener() {
/*  393 */     return new ScrollListener();
/*      */   }
/*      */   
/*      */   protected PropertyChangeListener createPropertyChangeListener() {
/*  397 */     return getHandler();
/*      */   }
/*      */   
/*      */   private void updateThumbState(int paramInt1, int paramInt2) {
/*  401 */     Rectangle rectangle = getThumbBounds();
/*      */     
/*  403 */     setThumbRollover(rectangle.contains(paramInt1, paramInt2));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setThumbRollover(boolean paramBoolean) {
/*  413 */     if (this.thumbActive != paramBoolean) {
/*  414 */       this.thumbActive = paramBoolean;
/*  415 */       this.scrollbar.repaint(getThumbBounds());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isThumbRollover() {
/*  426 */     return this.thumbActive;
/*      */   }
/*      */   
/*      */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {
/*  430 */     paintTrack(paramGraphics, paramJComponent, getTrackBounds());
/*  431 */     Rectangle rectangle = getThumbBounds();
/*  432 */     if (rectangle.intersects(paramGraphics.getClipBounds())) {
/*  433 */       paintThumb(paramGraphics, paramJComponent, rectangle);
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
/*      */   public Dimension getPreferredSize(JComponent paramJComponent) {
/*  455 */     return (this.scrollbar.getOrientation() == 1) ? new Dimension(this.scrollBarWidth, 48) : new Dimension(48, this.scrollBarWidth);
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
/*      */   public Dimension getMaximumSize(JComponent paramJComponent) {
/*  468 */     return new Dimension(2147483647, 2147483647);
/*      */   }
/*      */   
/*      */   protected JButton createDecreaseButton(int paramInt) {
/*  472 */     return new BasicArrowButton(paramInt, 
/*  473 */         UIManager.getColor("ScrollBar.thumb"), 
/*  474 */         UIManager.getColor("ScrollBar.thumbShadow"), 
/*  475 */         UIManager.getColor("ScrollBar.thumbDarkShadow"), 
/*  476 */         UIManager.getColor("ScrollBar.thumbHighlight"));
/*      */   }
/*      */   
/*      */   protected JButton createIncreaseButton(int paramInt) {
/*  480 */     return new BasicArrowButton(paramInt, 
/*  481 */         UIManager.getColor("ScrollBar.thumb"), 
/*  482 */         UIManager.getColor("ScrollBar.thumbShadow"), 
/*  483 */         UIManager.getColor("ScrollBar.thumbDarkShadow"), 
/*  484 */         UIManager.getColor("ScrollBar.thumbHighlight"));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void paintDecreaseHighlight(Graphics paramGraphics) {
/*  490 */     Insets insets = this.scrollbar.getInsets();
/*  491 */     Rectangle rectangle = getThumbBounds();
/*  492 */     paramGraphics.setColor(this.trackHighlightColor);
/*      */     
/*  494 */     if (this.scrollbar.getOrientation() == 1) {
/*      */       
/*  496 */       int i = insets.left;
/*  497 */       int j = this.trackRect.y;
/*  498 */       int k = this.scrollbar.getWidth() - insets.left + insets.right;
/*  499 */       int m = rectangle.y - j;
/*  500 */       paramGraphics.fillRect(i, j, k, m);
/*      */     } else {
/*      */       int i, j;
/*      */ 
/*      */ 
/*      */       
/*  506 */       if (this.scrollbar.getComponentOrientation().isLeftToRight()) {
/*  507 */         i = this.trackRect.x;
/*  508 */         j = rectangle.x - i;
/*      */       } else {
/*  510 */         i = rectangle.x + rectangle.width;
/*  511 */         j = this.trackRect.x + this.trackRect.width - i;
/*      */       } 
/*  513 */       int k = insets.top;
/*  514 */       int m = this.scrollbar.getHeight() - insets.top + insets.bottom;
/*  515 */       paramGraphics.fillRect(i, k, j, m);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void paintIncreaseHighlight(Graphics paramGraphics) {
/*  522 */     Insets insets = this.scrollbar.getInsets();
/*  523 */     Rectangle rectangle = getThumbBounds();
/*  524 */     paramGraphics.setColor(this.trackHighlightColor);
/*      */     
/*  526 */     if (this.scrollbar.getOrientation() == 1) {
/*      */       
/*  528 */       int i = insets.left;
/*  529 */       int j = rectangle.y + rectangle.height;
/*  530 */       int k = this.scrollbar.getWidth() - insets.left + insets.right;
/*  531 */       int m = this.trackRect.y + this.trackRect.height - j;
/*  532 */       paramGraphics.fillRect(i, j, k, m);
/*      */     } else {
/*      */       int i, j;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  539 */       if (this.scrollbar.getComponentOrientation().isLeftToRight()) {
/*  540 */         i = rectangle.x + rectangle.width;
/*  541 */         j = this.trackRect.x + this.trackRect.width - i;
/*      */       } else {
/*  543 */         i = this.trackRect.x;
/*  544 */         j = rectangle.x - i;
/*      */       } 
/*  546 */       int k = insets.top;
/*  547 */       int m = this.scrollbar.getHeight() - insets.top + insets.bottom;
/*  548 */       paramGraphics.fillRect(i, k, j, m);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void paintTrack(Graphics paramGraphics, JComponent paramJComponent, Rectangle paramRectangle) {
/*  555 */     paramGraphics.setColor(this.trackColor);
/*  556 */     paramGraphics.fillRect(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
/*      */     
/*  558 */     if (this.trackHighlight == 1) {
/*  559 */       paintDecreaseHighlight(paramGraphics);
/*      */     }
/*  561 */     else if (this.trackHighlight == 2) {
/*  562 */       paintIncreaseHighlight(paramGraphics);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void paintThumb(Graphics paramGraphics, JComponent paramJComponent, Rectangle paramRectangle) {
/*  569 */     if (paramRectangle.isEmpty() || !this.scrollbar.isEnabled()) {
/*      */       return;
/*      */     }
/*      */     
/*  573 */     int i = paramRectangle.width;
/*  574 */     int j = paramRectangle.height;
/*      */     
/*  576 */     paramGraphics.translate(paramRectangle.x, paramRectangle.y);
/*      */     
/*  578 */     paramGraphics.setColor(this.thumbDarkShadowColor);
/*  579 */     SwingUtilities2.drawRect(paramGraphics, 0, 0, i - 1, j - 1);
/*  580 */     paramGraphics.setColor(this.thumbColor);
/*  581 */     paramGraphics.fillRect(0, 0, i - 1, j - 1);
/*      */     
/*  583 */     paramGraphics.setColor(this.thumbHighlightColor);
/*  584 */     SwingUtilities2.drawVLine(paramGraphics, 1, 1, j - 2);
/*  585 */     SwingUtilities2.drawHLine(paramGraphics, 2, i - 3, 1);
/*      */     
/*  587 */     paramGraphics.setColor(this.thumbLightShadowColor);
/*  588 */     SwingUtilities2.drawHLine(paramGraphics, 2, i - 2, j - 2);
/*  589 */     SwingUtilities2.drawVLine(paramGraphics, i - 2, 1, j - 3);
/*      */     
/*  591 */     paramGraphics.translate(-paramRectangle.x, -paramRectangle.y);
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
/*      */   protected Dimension getMinimumThumbSize() {
/*  607 */     return this.minimumThumbSize;
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
/*      */   protected Dimension getMaximumThumbSize() {
/*  622 */     return this.maximumThumbSize;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void addLayoutComponent(String paramString, Component paramComponent) {}
/*      */ 
/*      */   
/*      */   public void removeLayoutComponent(Component paramComponent) {}
/*      */ 
/*      */   
/*      */   public Dimension preferredLayoutSize(Container paramContainer) {
/*  634 */     return getPreferredSize((JComponent)paramContainer);
/*      */   }
/*      */   
/*      */   public Dimension minimumLayoutSize(Container paramContainer) {
/*  638 */     return getMinimumSize((JComponent)paramContainer);
/*      */   }
/*      */   
/*      */   private int getValue(JScrollBar paramJScrollBar) {
/*  642 */     return this.useCachedValue ? this.scrollBarValue : paramJScrollBar.getValue();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void layoutVScrollbar(JScrollBar paramJScrollBar) {
/*  647 */     Dimension dimension = paramJScrollBar.getSize();
/*  648 */     Insets insets = paramJScrollBar.getInsets();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  653 */     int i = dimension.width - insets.left + insets.right;
/*  654 */     int j = insets.left;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  659 */     boolean bool = DefaultLookup.getBoolean(this.scrollbar, this, "ScrollBar.squareButtons", false);
/*      */ 
/*      */     
/*  662 */     int k = bool ? i : (this.decrButton.getPreferredSize()).height;
/*  663 */     int m = insets.top;
/*      */ 
/*      */     
/*  666 */     int n = bool ? i : (this.incrButton.getPreferredSize()).height;
/*  667 */     int i1 = dimension.height - insets.bottom + n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  673 */     int i2 = insets.top + insets.bottom;
/*  674 */     int i3 = k + n;
/*  675 */     int i4 = this.decrGap + this.incrGap;
/*  676 */     float f1 = (dimension.height - i2 + i3 - i4);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  684 */     float f2 = paramJScrollBar.getMinimum();
/*  685 */     float f3 = paramJScrollBar.getVisibleAmount();
/*  686 */     float f4 = paramJScrollBar.getMaximum() - f2;
/*  687 */     float f5 = getValue(paramJScrollBar);
/*      */ 
/*      */     
/*  690 */     int i5 = (f4 <= 0.0F) ? (getMaximumThumbSize()).height : (int)(f1 * f3 / f4);
/*  691 */     i5 = Math.max(i5, (getMinimumThumbSize()).height);
/*  692 */     i5 = Math.min(i5, (getMaximumThumbSize()).height);
/*      */     
/*  694 */     int i6 = i1 - this.incrGap - i5;
/*  695 */     if (f5 < (paramJScrollBar.getMaximum() - paramJScrollBar.getVisibleAmount())) {
/*  696 */       float f = f1 - i5;
/*  697 */       i6 = (int)(0.5F + f * (f5 - f2) / (f4 - f3));
/*  698 */       i6 += m + k + this.decrGap;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  704 */     int i7 = dimension.height - i2;
/*  705 */     if (i7 < i3) {
/*  706 */       n = k = i7 / 2;
/*  707 */       i1 = dimension.height - insets.bottom + n;
/*      */     } 
/*  709 */     this.decrButton.setBounds(j, m, i, k);
/*  710 */     this.incrButton.setBounds(j, i1, i, n);
/*      */ 
/*      */ 
/*      */     
/*  714 */     int i8 = m + k + this.decrGap;
/*  715 */     int i9 = i1 - this.incrGap - i8;
/*  716 */     this.trackRect.setBounds(j, i8, i, i9);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  722 */     if (i5 >= (int)f1) {
/*  723 */       if (UIManager.getBoolean("ScrollBar.alwaysShowThumb")) {
/*      */ 
/*      */         
/*  726 */         setThumbBounds(j, i8, i, i9);
/*      */       } else {
/*      */         
/*  729 */         setThumbBounds(0, 0, 0, 0);
/*      */       } 
/*      */     } else {
/*      */       
/*  733 */       if (i6 + i5 > i1 - this.incrGap) {
/*  734 */         i6 = i1 - this.incrGap - i5;
/*      */       }
/*  736 */       if (i6 < m + k + this.decrGap) {
/*  737 */         i6 = m + k + this.decrGap + 1;
/*      */       }
/*  739 */       setThumbBounds(j, i6, i, i5);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void layoutHScrollbar(JScrollBar paramJScrollBar) {
/*  746 */     Dimension dimension = paramJScrollBar.getSize();
/*  747 */     Insets insets = paramJScrollBar.getInsets();
/*      */ 
/*      */ 
/*      */     
/*  751 */     int i = dimension.height - insets.top + insets.bottom;
/*  752 */     int j = insets.top;
/*      */     
/*  754 */     boolean bool1 = paramJScrollBar.getComponentOrientation().isLeftToRight();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  759 */     boolean bool2 = DefaultLookup.getBoolean(this.scrollbar, this, "ScrollBar.squareButtons", false);
/*      */ 
/*      */     
/*  762 */     int k = bool2 ? i : (this.decrButton.getPreferredSize()).width;
/*      */     
/*  764 */     int m = bool2 ? i : (this.incrButton.getPreferredSize()).width;
/*  765 */     if (!bool1) {
/*  766 */       int i11 = k;
/*  767 */       k = m;
/*  768 */       m = i11;
/*      */     } 
/*  770 */     int n = insets.left;
/*  771 */     int i1 = dimension.width - insets.right + m;
/*  772 */     int i2 = bool1 ? this.decrGap : this.incrGap;
/*  773 */     int i3 = bool1 ? this.incrGap : this.decrGap;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  779 */     int i4 = insets.left + insets.right;
/*  780 */     int i5 = k + m;
/*  781 */     float f1 = (dimension.width - i4 + i5 - i2 + i3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  789 */     float f2 = paramJScrollBar.getMinimum();
/*  790 */     float f3 = paramJScrollBar.getMaximum();
/*  791 */     float f4 = paramJScrollBar.getVisibleAmount();
/*  792 */     float f5 = f3 - f2;
/*  793 */     float f6 = getValue(paramJScrollBar);
/*      */ 
/*      */     
/*  796 */     int i6 = (f5 <= 0.0F) ? (getMaximumThumbSize()).width : (int)(f1 * f4 / f5);
/*  797 */     i6 = Math.max(i6, (getMinimumThumbSize()).width);
/*  798 */     i6 = Math.min(i6, (getMaximumThumbSize()).width);
/*      */     
/*  800 */     int i7 = bool1 ? (i1 - i3 - i6) : (n + k + i2);
/*  801 */     if (f6 < f3 - paramJScrollBar.getVisibleAmount()) {
/*  802 */       float f = f1 - i6;
/*  803 */       if (bool1) {
/*  804 */         i7 = (int)(0.5F + f * (f6 - f2) / (f5 - f4));
/*      */       } else {
/*  806 */         i7 = (int)(0.5F + f * (f3 - f4 - f6) / (f5 - f4));
/*      */       } 
/*  808 */       i7 += n + k + i2;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  814 */     int i8 = dimension.width - i4;
/*  815 */     if (i8 < i5) {
/*  816 */       m = k = i8 / 2;
/*  817 */       i1 = dimension.width - insets.right + m + i3;
/*      */     } 
/*      */     
/*  820 */     (bool1 ? this.decrButton : this.incrButton).setBounds(n, j, k, i);
/*  821 */     (bool1 ? this.incrButton : this.decrButton).setBounds(i1, j, m, i);
/*      */ 
/*      */ 
/*      */     
/*  825 */     int i9 = n + k + i2;
/*  826 */     int i10 = i1 - i3 - i9;
/*  827 */     this.trackRect.setBounds(i9, j, i10, i);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  832 */     if (i6 >= (int)f1) {
/*  833 */       if (UIManager.getBoolean("ScrollBar.alwaysShowThumb")) {
/*      */ 
/*      */         
/*  836 */         setThumbBounds(i9, j, i10, i);
/*      */       } else {
/*      */         
/*  839 */         setThumbBounds(0, 0, 0, 0);
/*      */       } 
/*      */     } else {
/*      */       
/*  843 */       if (i7 + i6 > i1 - i3) {
/*  844 */         i7 = i1 - i3 - i6;
/*      */       }
/*  846 */       if (i7 < n + k + i2) {
/*  847 */         i7 = n + k + i2 + 1;
/*      */       }
/*  849 */       setThumbBounds(i7, j, i6, i);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void layoutContainer(Container paramContainer) {
/*  859 */     if (this.isDragging) {
/*      */       return;
/*      */     }
/*      */     
/*  863 */     JScrollBar jScrollBar = (JScrollBar)paramContainer;
/*  864 */     switch (jScrollBar.getOrientation()) {
/*      */       case 1:
/*  866 */         layoutVScrollbar(jScrollBar);
/*      */         break;
/*      */       
/*      */       case 0:
/*  870 */         layoutHScrollbar(jScrollBar);
/*      */         break;
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
/*      */   protected void setThumbBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  886 */     if (this.thumbRect.x == paramInt1 && this.thumbRect.y == paramInt2 && this.thumbRect.width == paramInt3 && this.thumbRect.height == paramInt4) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  896 */     int i = Math.min(paramInt1, this.thumbRect.x);
/*  897 */     int j = Math.min(paramInt2, this.thumbRect.y);
/*  898 */     int k = Math.max(paramInt1 + paramInt3, this.thumbRect.x + this.thumbRect.width);
/*  899 */     int m = Math.max(paramInt2 + paramInt4, this.thumbRect.y + this.thumbRect.height);
/*      */     
/*  901 */     this.thumbRect.setBounds(paramInt1, paramInt2, paramInt3, paramInt4);
/*  902 */     this.scrollbar.repaint(i, j, k - i, m - j);
/*      */ 
/*      */ 
/*      */     
/*  906 */     setThumbRollover(false);
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
/*      */   protected Rectangle getThumbBounds() {
/*  920 */     return this.thumbRect;
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
/*      */   protected Rectangle getTrackBounds() {
/*  937 */     return this.trackRect;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void scrollByBlock(JScrollBar paramJScrollBar, int paramInt) {
/*  947 */     int i = paramJScrollBar.getValue();
/*  948 */     int j = paramJScrollBar.getBlockIncrement(paramInt);
/*  949 */     int k = j * ((paramInt > 0) ? 1 : -1);
/*  950 */     int m = i + k;
/*      */ 
/*      */     
/*  953 */     if (k > 0 && m < i) {
/*  954 */       m = paramJScrollBar.getMaximum();
/*      */     }
/*  956 */     else if (k < 0 && m > i) {
/*  957 */       m = paramJScrollBar.getMinimum();
/*      */     } 
/*      */     
/*  960 */     paramJScrollBar.setValue(m);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void scrollByBlock(int paramInt) {
/*  965 */     scrollByBlock(this.scrollbar, paramInt);
/*  966 */     this.trackHighlight = (paramInt > 0) ? 2 : 1;
/*  967 */     Rectangle rectangle = getTrackBounds();
/*  968 */     this.scrollbar.repaint(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
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
/*      */   static void scrollByUnits(JScrollBar paramJScrollBar, int paramInt1, int paramInt2, boolean paramBoolean) {
/*  984 */     int i = -1;
/*      */     
/*  986 */     if (paramBoolean) {
/*  987 */       if (paramInt1 < 0) {
/*      */         
/*  989 */         i = paramJScrollBar.getValue() - paramJScrollBar.getBlockIncrement(paramInt1);
/*      */       }
/*      */       else {
/*      */         
/*  993 */         i = paramJScrollBar.getValue() + paramJScrollBar.getBlockIncrement(paramInt1);
/*      */       } 
/*      */     }
/*      */     
/*  997 */     for (byte b = 0; b < paramInt2; b++) {
/*  998 */       int j; if (paramInt1 > 0) {
/*  999 */         j = paramJScrollBar.getUnitIncrement(paramInt1);
/*      */       } else {
/*      */         
/* 1002 */         j = -paramJScrollBar.getUnitIncrement(paramInt1);
/*      */       } 
/*      */       
/* 1005 */       int k = paramJScrollBar.getValue();
/* 1006 */       int m = k + j;
/*      */ 
/*      */       
/* 1009 */       if (j > 0 && m < k) {
/* 1010 */         m = paramJScrollBar.getMaximum();
/*      */       }
/* 1012 */       else if (j < 0 && m > k) {
/* 1013 */         m = paramJScrollBar.getMinimum();
/*      */       } 
/* 1015 */       if (k == m) {
/*      */         break;
/*      */       }
/*      */       
/* 1019 */       if (paramBoolean && b > 0) {
/* 1020 */         assert i != -1;
/* 1021 */         if ((paramInt1 < 0 && m < i) || (paramInt1 > 0 && m > i)) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */       
/* 1026 */       paramJScrollBar.setValue(m);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void scrollByUnit(int paramInt) {
/* 1031 */     scrollByUnits(this.scrollbar, paramInt, 1, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getSupportsAbsolutePositioning() {
/* 1042 */     return this.supportsAbsolutePositioning;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected class ModelListener
/*      */     implements ChangeListener
/*      */   {
/*      */     public void stateChanged(ChangeEvent param1ChangeEvent) {
/* 1051 */       if (!BasicScrollBarUI.this.useCachedValue) {
/* 1052 */         BasicScrollBarUI.this.scrollBarValue = BasicScrollBarUI.this.scrollbar.getValue();
/*      */       }
/* 1054 */       BasicScrollBarUI.this.layoutContainer(BasicScrollBarUI.this.scrollbar);
/* 1055 */       BasicScrollBarUI.this.useCachedValue = false;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected class TrackListener
/*      */     extends MouseAdapter
/*      */     implements MouseMotionListener
/*      */   {
/*      */     protected transient int offset;
/*      */     
/*      */     protected transient int currentMouseX;
/*      */     protected transient int currentMouseY;
/* 1068 */     private transient int direction = 1;
/*      */ 
/*      */     
/*      */     public void mouseReleased(MouseEvent param1MouseEvent) {
/* 1072 */       if (BasicScrollBarUI.this.isDragging) {
/* 1073 */         BasicScrollBarUI.this.updateThumbState(param1MouseEvent.getX(), param1MouseEvent.getY());
/*      */       }
/* 1075 */       if (SwingUtilities.isRightMouseButton(param1MouseEvent) || (
/* 1076 */         !BasicScrollBarUI.this.getSupportsAbsolutePositioning() && 
/* 1077 */         SwingUtilities.isMiddleMouseButton(param1MouseEvent)))
/*      */         return; 
/* 1079 */       if (!BasicScrollBarUI.this.scrollbar.isEnabled()) {
/*      */         return;
/*      */       }
/* 1082 */       Rectangle rectangle = BasicScrollBarUI.this.getTrackBounds();
/* 1083 */       BasicScrollBarUI.this.scrollbar.repaint(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*      */       
/* 1085 */       BasicScrollBarUI.this.trackHighlight = 0;
/* 1086 */       BasicScrollBarUI.this.isDragging = false;
/* 1087 */       this.offset = 0;
/* 1088 */       BasicScrollBarUI.this.scrollTimer.stop();
/* 1089 */       BasicScrollBarUI.this.useCachedValue = true;
/* 1090 */       BasicScrollBarUI.this.scrollbar.setValueIsAdjusting(false);
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
/*      */     public void mousePressed(MouseEvent param1MouseEvent) {
/*      */       int i;
/* 1103 */       if (SwingUtilities.isRightMouseButton(param1MouseEvent) || (
/* 1104 */         !BasicScrollBarUI.this.getSupportsAbsolutePositioning() && 
/* 1105 */         SwingUtilities.isMiddleMouseButton(param1MouseEvent)))
/*      */         return; 
/* 1107 */       if (!BasicScrollBarUI.this.scrollbar.isEnabled()) {
/*      */         return;
/*      */       }
/* 1110 */       if (!BasicScrollBarUI.this.scrollbar.hasFocus() && BasicScrollBarUI.this.scrollbar.isRequestFocusEnabled()) {
/* 1111 */         BasicScrollBarUI.this.scrollbar.requestFocus();
/*      */       }
/*      */       
/* 1114 */       BasicScrollBarUI.this.useCachedValue = true;
/* 1115 */       BasicScrollBarUI.this.scrollbar.setValueIsAdjusting(true);
/*      */       
/* 1117 */       this.currentMouseX = param1MouseEvent.getX();
/* 1118 */       this.currentMouseY = param1MouseEvent.getY();
/*      */ 
/*      */       
/* 1121 */       if (BasicScrollBarUI.this.getThumbBounds().contains(this.currentMouseX, this.currentMouseY)) {
/* 1122 */         switch (BasicScrollBarUI.this.scrollbar.getOrientation()) {
/*      */           case 1:
/* 1124 */             this.offset = this.currentMouseY - (BasicScrollBarUI.this.getThumbBounds()).y;
/*      */             break;
/*      */           case 0:
/* 1127 */             this.offset = this.currentMouseX - (BasicScrollBarUI.this.getThumbBounds()).x;
/*      */             break;
/*      */         } 
/* 1130 */         BasicScrollBarUI.this.isDragging = true;
/*      */         return;
/*      */       } 
/* 1133 */       if (BasicScrollBarUI.this.getSupportsAbsolutePositioning() && 
/* 1134 */         SwingUtilities.isMiddleMouseButton(param1MouseEvent)) {
/* 1135 */         switch (BasicScrollBarUI.this.scrollbar.getOrientation()) {
/*      */           case 1:
/* 1137 */             this.offset = (BasicScrollBarUI.this.getThumbBounds()).height / 2;
/*      */             break;
/*      */           case 0:
/* 1140 */             this.offset = (BasicScrollBarUI.this.getThumbBounds()).width / 2;
/*      */             break;
/*      */         } 
/* 1143 */         BasicScrollBarUI.this.isDragging = true;
/* 1144 */         setValueFrom(param1MouseEvent);
/*      */         return;
/*      */       } 
/* 1147 */       BasicScrollBarUI.this.isDragging = false;
/*      */       
/* 1149 */       Dimension dimension = BasicScrollBarUI.this.scrollbar.getSize();
/* 1150 */       this.direction = 1;
/*      */       
/* 1152 */       switch (BasicScrollBarUI.this.scrollbar.getOrientation()) {
/*      */         case 1:
/* 1154 */           if (BasicScrollBarUI.this.getThumbBounds().isEmpty()) {
/* 1155 */             int j = dimension.height / 2;
/* 1156 */             this.direction = (this.currentMouseY < j) ? -1 : 1; break;
/*      */           } 
/* 1158 */           i = (BasicScrollBarUI.this.getThumbBounds()).y;
/* 1159 */           this.direction = (this.currentMouseY < i) ? -1 : 1;
/*      */           break;
/*      */         
/*      */         case 0:
/* 1163 */           if (BasicScrollBarUI.this.getThumbBounds().isEmpty()) {
/* 1164 */             i = dimension.width / 2;
/* 1165 */             this.direction = (this.currentMouseX < i) ? -1 : 1;
/*      */           } else {
/* 1167 */             i = (BasicScrollBarUI.this.getThumbBounds()).x;
/* 1168 */             this.direction = (this.currentMouseX < i) ? -1 : 1;
/*      */           } 
/* 1170 */           if (!BasicScrollBarUI.this.scrollbar.getComponentOrientation().isLeftToRight()) {
/* 1171 */             this.direction = -this.direction;
/*      */           }
/*      */           break;
/*      */       } 
/* 1175 */       BasicScrollBarUI.this.scrollByBlock(this.direction);
/*      */       
/* 1177 */       BasicScrollBarUI.this.scrollTimer.stop();
/* 1178 */       BasicScrollBarUI.this.scrollListener.setDirection(this.direction);
/* 1179 */       BasicScrollBarUI.this.scrollListener.setScrollByBlock(true);
/* 1180 */       startScrollTimerIfNecessary();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void mouseDragged(MouseEvent param1MouseEvent) {
/* 1191 */       if (SwingUtilities.isRightMouseButton(param1MouseEvent) || (
/* 1192 */         !BasicScrollBarUI.this.getSupportsAbsolutePositioning() && 
/* 1193 */         SwingUtilities.isMiddleMouseButton(param1MouseEvent)))
/*      */         return; 
/* 1195 */       if (!BasicScrollBarUI.this.scrollbar.isEnabled() || BasicScrollBarUI.this.getThumbBounds().isEmpty()) {
/*      */         return;
/*      */       }
/* 1198 */       if (BasicScrollBarUI.this.isDragging) {
/* 1199 */         setValueFrom(param1MouseEvent);
/*      */       } else {
/* 1201 */         this.currentMouseX = param1MouseEvent.getX();
/* 1202 */         this.currentMouseY = param1MouseEvent.getY();
/* 1203 */         BasicScrollBarUI.this.updateThumbState(this.currentMouseX, this.currentMouseY);
/* 1204 */         startScrollTimerIfNecessary();
/*      */       } 
/*      */     }
/*      */     private void setValueFrom(MouseEvent param1MouseEvent) {
/*      */       int i, j, k;
/* 1209 */       boolean bool = BasicScrollBarUI.this.isThumbRollover();
/* 1210 */       BoundedRangeModel boundedRangeModel = BasicScrollBarUI.this.scrollbar.getModel();
/* 1211 */       Rectangle rectangle = BasicScrollBarUI.this.getThumbBounds();
/*      */ 
/*      */ 
/*      */       
/* 1215 */       if (BasicScrollBarUI.this.scrollbar.getOrientation() == 1) {
/* 1216 */         i = BasicScrollBarUI.this.trackRect.y;
/* 1217 */         j = BasicScrollBarUI.this.trackRect.y + BasicScrollBarUI.this.trackRect.height - rectangle.height;
/* 1218 */         k = Math.min(j, Math.max(i, param1MouseEvent.getY() - this.offset));
/* 1219 */         BasicScrollBarUI.this.setThumbBounds(rectangle.x, k, rectangle.width, rectangle.height);
/* 1220 */         float f = (BasicScrollBarUI.this.getTrackBounds()).height;
/*      */       } else {
/*      */         
/* 1223 */         i = BasicScrollBarUI.this.trackRect.x;
/* 1224 */         j = BasicScrollBarUI.this.trackRect.x + BasicScrollBarUI.this.trackRect.width - rectangle.width;
/* 1225 */         k = Math.min(j, Math.max(i, param1MouseEvent.getX() - this.offset));
/* 1226 */         BasicScrollBarUI.this.setThumbBounds(k, rectangle.y, rectangle.width, rectangle.height);
/* 1227 */         float f = (BasicScrollBarUI.this.getTrackBounds()).width;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1234 */       if (k == j) {
/* 1235 */         if (BasicScrollBarUI.this.scrollbar.getOrientation() == 1 || BasicScrollBarUI.this.scrollbar
/* 1236 */           .getComponentOrientation().isLeftToRight()) {
/* 1237 */           BasicScrollBarUI.this.scrollbar.setValue(boundedRangeModel.getMaximum() - boundedRangeModel.getExtent());
/*      */         } else {
/* 1239 */           BasicScrollBarUI.this.scrollbar.setValue(boundedRangeModel.getMinimum());
/*      */         } 
/*      */       } else {
/*      */         int m;
/* 1243 */         float f1 = (boundedRangeModel.getMaximum() - boundedRangeModel.getExtent());
/* 1244 */         float f2 = f1 - boundedRangeModel.getMinimum();
/* 1245 */         float f3 = (k - i);
/* 1246 */         float f4 = (j - i);
/*      */         
/* 1248 */         if (BasicScrollBarUI.this.scrollbar.getOrientation() == 1 || BasicScrollBarUI.this.scrollbar
/* 1249 */           .getComponentOrientation().isLeftToRight()) {
/* 1250 */           m = (int)(0.5D + (f3 / f4 * f2));
/*      */         } else {
/* 1252 */           m = (int)(0.5D + ((j - k) / f4 * f2));
/*      */         } 
/*      */         
/* 1255 */         BasicScrollBarUI.this.useCachedValue = true;
/* 1256 */         BasicScrollBarUI.this.scrollBarValue = m + boundedRangeModel.getMinimum();
/* 1257 */         BasicScrollBarUI.this.scrollbar.setValue(adjustValueIfNecessary(BasicScrollBarUI.this.scrollBarValue));
/*      */       } 
/* 1259 */       BasicScrollBarUI.this.setThumbRollover(bool);
/*      */     }
/*      */     
/*      */     private int adjustValueIfNecessary(int param1Int) {
/* 1263 */       if (BasicScrollBarUI.this.scrollbar.getParent() instanceof JScrollPane) {
/* 1264 */         JScrollPane jScrollPane = (JScrollPane)BasicScrollBarUI.this.scrollbar.getParent();
/* 1265 */         JViewport jViewport = jScrollPane.getViewport();
/* 1266 */         Component component = jViewport.getView();
/* 1267 */         if (component instanceof JList) {
/* 1268 */           JList jList = (JList)component;
/* 1269 */           if (DefaultLookup.getBoolean(jList, jList.getUI(), "List.lockToPositionOnScroll", false)) {
/*      */             
/* 1271 */             int i = param1Int;
/* 1272 */             int j = jList.getLayoutOrientation();
/* 1273 */             int k = BasicScrollBarUI.this.scrollbar.getOrientation();
/* 1274 */             if (k == 1 && j == 0) {
/* 1275 */               int m = jList.locationToIndex(new Point(0, param1Int));
/* 1276 */               Rectangle rectangle = jList.getCellBounds(m, m);
/* 1277 */               if (rectangle != null) {
/* 1278 */                 i = rectangle.y;
/*      */               }
/*      */             } 
/* 1281 */             if (k == 0 && (j == 1 || j == 2))
/*      */             {
/* 1283 */               if (jScrollPane.getComponentOrientation().isLeftToRight()) {
/* 1284 */                 int m = jList.locationToIndex(new Point(param1Int, 0));
/* 1285 */                 Rectangle rectangle = jList.getCellBounds(m, m);
/* 1286 */                 if (rectangle != null) {
/* 1287 */                   i = rectangle.x;
/*      */                 }
/*      */               } else {
/*      */                 
/* 1291 */                 Point point = new Point(param1Int, 0);
/* 1292 */                 int m = (jViewport.getExtentSize()).width;
/* 1293 */                 point.x += m - 1;
/* 1294 */                 int n = jList.locationToIndex(point);
/* 1295 */                 Rectangle rectangle = jList.getCellBounds(n, n);
/* 1296 */                 if (rectangle != null) {
/* 1297 */                   i = rectangle.x + rectangle.width - m;
/*      */                 }
/*      */               } 
/*      */             }
/* 1301 */             param1Int = i;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1306 */       return param1Int;
/*      */     }
/*      */     
/*      */     private void startScrollTimerIfNecessary() {
/* 1310 */       if (BasicScrollBarUI.this.scrollTimer.isRunning()) {
/*      */         return;
/*      */       }
/*      */       
/* 1314 */       Rectangle rectangle = BasicScrollBarUI.this.getThumbBounds();
/*      */       
/* 1316 */       switch (BasicScrollBarUI.this.scrollbar.getOrientation()) {
/*      */         case 1:
/* 1318 */           if (this.direction > 0) {
/* 1319 */             if (rectangle.y + rectangle.height < BasicScrollBarUI.this.trackListener.currentMouseY)
/* 1320 */               BasicScrollBarUI.this.scrollTimer.start();  break;
/*      */           } 
/* 1322 */           if (rectangle.y > BasicScrollBarUI.this.trackListener.currentMouseY) {
/* 1323 */             BasicScrollBarUI.this.scrollTimer.start();
/*      */           }
/*      */           break;
/*      */         case 0:
/* 1327 */           if ((this.direction > 0 && BasicScrollBarUI.this.isMouseAfterThumb()) || (this.direction < 0 && BasicScrollBarUI.this
/* 1328 */             .isMouseBeforeThumb()))
/*      */           {
/* 1330 */             BasicScrollBarUI.this.scrollTimer.start();
/*      */           }
/*      */           break;
/*      */       } 
/*      */     }
/*      */     
/*      */     public void mouseMoved(MouseEvent param1MouseEvent) {
/* 1337 */       if (!BasicScrollBarUI.this.isDragging) {
/* 1338 */         BasicScrollBarUI.this.updateThumbState(param1MouseEvent.getX(), param1MouseEvent.getY());
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void mouseExited(MouseEvent param1MouseEvent) {
/* 1349 */       if (!BasicScrollBarUI.this.isDragging) {
/* 1350 */         BasicScrollBarUI.this.setThumbRollover(false);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected class ArrowButtonListener
/*      */     extends MouseAdapter
/*      */   {
/*      */     boolean handledEvent;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void mousePressed(MouseEvent param1MouseEvent) {
/* 1367 */       if (!BasicScrollBarUI.this.scrollbar.isEnabled()) {
/*      */         return;
/*      */       }
/* 1370 */       if (!SwingUtilities.isLeftMouseButton(param1MouseEvent))
/*      */         return; 
/* 1372 */       boolean bool = (param1MouseEvent.getSource() == BasicScrollBarUI.this.incrButton) ? true : true;
/*      */       
/* 1374 */       BasicScrollBarUI.this.scrollByUnit(bool);
/* 1375 */       BasicScrollBarUI.this.scrollTimer.stop();
/* 1376 */       BasicScrollBarUI.this.scrollListener.setDirection(bool);
/* 1377 */       BasicScrollBarUI.this.scrollListener.setScrollByBlock(false);
/* 1378 */       BasicScrollBarUI.this.scrollTimer.start();
/*      */       
/* 1380 */       this.handledEvent = true;
/* 1381 */       if (!BasicScrollBarUI.this.scrollbar.hasFocus() && BasicScrollBarUI.this.scrollbar.isRequestFocusEnabled()) {
/* 1382 */         BasicScrollBarUI.this.scrollbar.requestFocus();
/*      */       }
/*      */     }
/*      */     
/*      */     public void mouseReleased(MouseEvent param1MouseEvent) {
/* 1387 */       BasicScrollBarUI.this.scrollTimer.stop();
/* 1388 */       this.handledEvent = false;
/* 1389 */       BasicScrollBarUI.this.scrollbar.setValueIsAdjusting(false);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected class ScrollListener
/*      */     implements ActionListener
/*      */   {
/* 1400 */     int direction = 1;
/*      */     boolean useBlockIncrement;
/*      */     
/*      */     public ScrollListener() {
/* 1404 */       this.direction = 1;
/* 1405 */       this.useBlockIncrement = false;
/*      */     }
/*      */     
/*      */     public ScrollListener(int param1Int, boolean param1Boolean) {
/* 1409 */       this.direction = param1Int;
/* 1410 */       this.useBlockIncrement = param1Boolean;
/*      */     }
/*      */     
/* 1413 */     public void setDirection(int param1Int) { this.direction = param1Int; } public void setScrollByBlock(boolean param1Boolean) {
/* 1414 */       this.useBlockIncrement = param1Boolean;
/*      */     }
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1417 */       if (this.useBlockIncrement) {
/* 1418 */         BasicScrollBarUI.this.scrollByBlock(this.direction);
/*      */         
/* 1420 */         if (BasicScrollBarUI.this.scrollbar.getOrientation() == 1) {
/* 1421 */           if (this.direction > 0) {
/* 1422 */             if ((BasicScrollBarUI.this.getThumbBounds()).y + (BasicScrollBarUI.this.getThumbBounds()).height >= BasicScrollBarUI.this.trackListener.currentMouseY)
/*      */             {
/* 1424 */               ((Timer)param1ActionEvent.getSource()).stop(); } 
/* 1425 */           } else if ((BasicScrollBarUI.this.getThumbBounds()).y <= BasicScrollBarUI.this.trackListener.currentMouseY) {
/* 1426 */             ((Timer)param1ActionEvent.getSource()).stop();
/*      */           }
/*      */         
/* 1429 */         } else if ((this.direction > 0 && !BasicScrollBarUI.this.isMouseAfterThumb()) || (this.direction < 0 && 
/* 1430 */           !BasicScrollBarUI.this.isMouseBeforeThumb())) {
/*      */           
/* 1432 */           ((Timer)param1ActionEvent.getSource()).stop();
/*      */         } 
/*      */       } else {
/*      */         
/* 1436 */         BasicScrollBarUI.this.scrollByUnit(this.direction);
/*      */       } 
/*      */       
/* 1439 */       if (this.direction > 0 && BasicScrollBarUI.this.scrollbar
/* 1440 */         .getValue() + BasicScrollBarUI.this.scrollbar.getVisibleAmount() >= BasicScrollBarUI.this.scrollbar
/* 1441 */         .getMaximum()) {
/* 1442 */         ((Timer)param1ActionEvent.getSource()).stop();
/* 1443 */       } else if (this.direction < 0 && BasicScrollBarUI.this.scrollbar
/* 1444 */         .getValue() <= BasicScrollBarUI.this.scrollbar.getMinimum()) {
/* 1445 */         ((Timer)param1ActionEvent.getSource()).stop();
/*      */       } 
/*      */     } }
/*      */   
/*      */   private boolean isMouseLeftOfThumb() {
/* 1450 */     return (this.trackListener.currentMouseX < (getThumbBounds()).x);
/*      */   }
/*      */   
/*      */   private boolean isMouseRightOfThumb() {
/* 1454 */     Rectangle rectangle = getThumbBounds();
/* 1455 */     return (this.trackListener.currentMouseX > rectangle.x + rectangle.width);
/*      */   }
/*      */   
/*      */   private boolean isMouseBeforeThumb() {
/* 1459 */     return this.scrollbar.getComponentOrientation().isLeftToRight() ? 
/* 1460 */       isMouseLeftOfThumb() : 
/* 1461 */       isMouseRightOfThumb();
/*      */   }
/*      */   
/*      */   private boolean isMouseAfterThumb() {
/* 1465 */     return this.scrollbar.getComponentOrientation().isLeftToRight() ? 
/* 1466 */       isMouseRightOfThumb() : 
/* 1467 */       isMouseLeftOfThumb();
/*      */   }
/*      */   
/*      */   private void updateButtonDirections() {
/* 1471 */     int i = this.scrollbar.getOrientation();
/* 1472 */     if (this.scrollbar.getComponentOrientation().isLeftToRight()) {
/* 1473 */       if (this.incrButton instanceof BasicArrowButton) {
/* 1474 */         ((BasicArrowButton)this.incrButton).setDirection((i == 0) ? 3 : 5);
/*      */       }
/*      */       
/* 1477 */       if (this.decrButton instanceof BasicArrowButton) {
/* 1478 */         ((BasicArrowButton)this.decrButton).setDirection((i == 0) ? 7 : 1);
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/* 1483 */       if (this.incrButton instanceof BasicArrowButton) {
/* 1484 */         ((BasicArrowButton)this.incrButton).setDirection((i == 0) ? 7 : 5);
/*      */       }
/*      */       
/* 1487 */       if (this.decrButton instanceof BasicArrowButton) {
/* 1488 */         ((BasicArrowButton)this.decrButton).setDirection((i == 0) ? 3 : 1);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public class PropertyChangeHandler
/*      */     implements PropertyChangeListener
/*      */   {
/*      */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 1502 */       BasicScrollBarUI.this.getHandler().propertyChange(param1PropertyChangeEvent);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class Actions
/*      */     extends UIAction
/*      */   {
/*      */     private static final String POSITIVE_UNIT_INCREMENT = "positiveUnitIncrement";
/*      */     
/*      */     private static final String POSITIVE_BLOCK_INCREMENT = "positiveBlockIncrement";
/*      */     
/*      */     private static final String NEGATIVE_UNIT_INCREMENT = "negativeUnitIncrement";
/*      */     
/*      */     private static final String NEGATIVE_BLOCK_INCREMENT = "negativeBlockIncrement";
/*      */     
/*      */     private static final String MIN_SCROLL = "minScroll";
/*      */     
/*      */     private static final String MAX_SCROLL = "maxScroll";
/*      */     
/*      */     Actions(String param1String) {
/* 1523 */       super(param1String);
/*      */     }
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1527 */       JScrollBar jScrollBar = (JScrollBar)param1ActionEvent.getSource();
/* 1528 */       String str = getName();
/* 1529 */       if (str == "positiveUnitIncrement") {
/* 1530 */         scroll(jScrollBar, 1, false);
/*      */       }
/* 1532 */       else if (str == "positiveBlockIncrement") {
/* 1533 */         scroll(jScrollBar, 1, true);
/*      */       }
/* 1535 */       else if (str == "negativeUnitIncrement") {
/* 1536 */         scroll(jScrollBar, -1, false);
/*      */       }
/* 1538 */       else if (str == "negativeBlockIncrement") {
/* 1539 */         scroll(jScrollBar, -1, true);
/*      */       }
/* 1541 */       else if (str == "minScroll") {
/* 1542 */         scroll(jScrollBar, 2, true);
/*      */       }
/* 1544 */       else if (str == "maxScroll") {
/* 1545 */         scroll(jScrollBar, 3, true);
/*      */       } 
/*      */     }
/*      */     
/*      */     private void scroll(JScrollBar param1JScrollBar, int param1Int, boolean param1Boolean) {
/* 1550 */       if (param1Int == -1 || param1Int == 1) {
/*      */         int i;
/*      */ 
/*      */ 
/*      */         
/* 1555 */         if (param1Boolean) {
/* 1556 */           if (param1Int == -1) {
/* 1557 */             i = -1 * param1JScrollBar.getBlockIncrement(-1);
/*      */           } else {
/*      */             
/* 1560 */             i = param1JScrollBar.getBlockIncrement(1);
/*      */           }
/*      */         
/*      */         }
/* 1564 */         else if (param1Int == -1) {
/* 1565 */           i = -1 * param1JScrollBar.getUnitIncrement(-1);
/*      */         } else {
/*      */           
/* 1568 */           i = param1JScrollBar.getUnitIncrement(1);
/*      */         } 
/*      */         
/* 1571 */         param1JScrollBar.setValue(param1JScrollBar.getValue() + i);
/*      */       }
/* 1573 */       else if (param1Int == 2) {
/* 1574 */         param1JScrollBar.setValue(param1JScrollBar.getMinimum());
/*      */       }
/* 1576 */       else if (param1Int == 3) {
/* 1577 */         param1JScrollBar.setValue(param1JScrollBar.getMaximum());
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class Handler
/*      */     implements FocusListener, PropertyChangeListener
/*      */   {
/*      */     private Handler() {}
/*      */ 
/*      */     
/*      */     public void focusGained(FocusEvent param1FocusEvent) {
/* 1591 */       BasicScrollBarUI.this.scrollbar.repaint();
/*      */     }
/*      */     
/*      */     public void focusLost(FocusEvent param1FocusEvent) {
/* 1595 */       BasicScrollBarUI.this.scrollbar.repaint();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 1603 */       String str = param1PropertyChangeEvent.getPropertyName();
/*      */       
/* 1605 */       if ("model" == str) {
/* 1606 */         BoundedRangeModel boundedRangeModel1 = (BoundedRangeModel)param1PropertyChangeEvent.getOldValue();
/* 1607 */         BoundedRangeModel boundedRangeModel2 = (BoundedRangeModel)param1PropertyChangeEvent.getNewValue();
/* 1608 */         boundedRangeModel1.removeChangeListener(BasicScrollBarUI.this.modelListener);
/* 1609 */         boundedRangeModel2.addChangeListener(BasicScrollBarUI.this.modelListener);
/* 1610 */         BasicScrollBarUI.this.scrollBarValue = BasicScrollBarUI.this.scrollbar.getValue();
/* 1611 */         BasicScrollBarUI.this.scrollbar.repaint();
/* 1612 */         BasicScrollBarUI.this.scrollbar.revalidate();
/* 1613 */       } else if ("orientation" == str) {
/* 1614 */         BasicScrollBarUI.this.updateButtonDirections();
/* 1615 */       } else if ("componentOrientation" == str) {
/* 1616 */         BasicScrollBarUI.this.updateButtonDirections();
/* 1617 */         InputMap inputMap = BasicScrollBarUI.this.getInputMap(0);
/* 1618 */         SwingUtilities.replaceUIInputMap(BasicScrollBarUI.this.scrollbar, 0, inputMap);
/*      */       } 
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/BasicScrollBarUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */