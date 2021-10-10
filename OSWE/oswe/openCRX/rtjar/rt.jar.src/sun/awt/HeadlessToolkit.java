/*     */ package sun.awt;
/*     */ import java.awt.Canvas;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dialog;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.EventQueue;
/*     */ import java.awt.Font;
/*     */ import java.awt.Frame;
/*     */ import java.awt.HeadlessException;
/*     */ import java.awt.Image;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Label;
/*     */ import java.awt.List;
/*     */ import java.awt.MenuBar;
/*     */ import java.awt.PageAttributes;
/*     */ import java.awt.Panel;
/*     */ import java.awt.PrintJob;
/*     */ import java.awt.Robot;
/*     */ import java.awt.Scrollbar;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.TrayIcon;
/*     */ import java.awt.Window;
/*     */ import java.awt.dnd.DragGestureEvent;
/*     */ import java.awt.dnd.InvalidDnDOperationException;
/*     */ import java.awt.event.AWTEventListener;
/*     */ import java.awt.im.InputMethodHighlight;
/*     */ import java.awt.image.ImageObserver;
/*     */ import java.awt.image.ImageProducer;
/*     */ import java.awt.peer.CanvasPeer;
/*     */ import java.awt.peer.FontPeer;
/*     */ import java.awt.peer.FramePeer;
/*     */ import java.awt.peer.KeyboardFocusManagerPeer;
/*     */ import java.awt.peer.MenuBarPeer;
/*     */ import java.awt.peer.MenuItemPeer;
/*     */ import java.awt.peer.PanelPeer;
/*     */ import java.awt.peer.PopupMenuPeer;
/*     */ import java.awt.peer.RobotPeer;
/*     */ import java.awt.peer.SystemTrayPeer;
/*     */ import java.awt.peer.WindowPeer;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.net.URL;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ 
/*     */ public class HeadlessToolkit extends Toolkit implements ComponentFactory, KeyboardFocusManagerPeerProvider {
/*  46 */   private static final KeyboardFocusManagerPeer kfmPeer = new KeyboardFocusManagerPeer() { public void setCurrentFocusedWindow(Window param1Window) {}
/*     */       public Window getCurrentFocusedWindow() {
/*  48 */         return null;
/*     */       } public void setCurrentFocusOwner(Component param1Component) {} public Component getCurrentFocusOwner() {
/*  50 */         return null;
/*     */       }
/*     */       public void clearGlobalFocusOwner(Window param1Window) {} }
/*     */   ;
/*     */   private Toolkit tk;
/*     */   private ComponentFactory componentFactory;
/*     */   
/*     */   public HeadlessToolkit(Toolkit paramToolkit) {
/*  58 */     this.tk = paramToolkit;
/*  59 */     if (paramToolkit instanceof ComponentFactory) {
/*  60 */       this.componentFactory = (ComponentFactory)paramToolkit;
/*     */     }
/*     */   }
/*     */   
/*     */   public Toolkit getUnderlyingToolkit() {
/*  65 */     return this.tk;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CanvasPeer createCanvas(Canvas paramCanvas) {
/*  75 */     return (CanvasPeer)createComponent(paramCanvas);
/*     */   }
/*     */   
/*     */   public PanelPeer createPanel(Panel paramPanel) {
/*  79 */     return (PanelPeer)createComponent(paramPanel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WindowPeer createWindow(Window paramWindow) throws HeadlessException {
/*  88 */     throw new HeadlessException();
/*     */   }
/*     */ 
/*     */   
/*     */   public FramePeer createFrame(Frame paramFrame) throws HeadlessException {
/*  93 */     throw new HeadlessException();
/*     */   }
/*     */ 
/*     */   
/*     */   public DialogPeer createDialog(Dialog paramDialog) throws HeadlessException {
/*  98 */     throw new HeadlessException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ButtonPeer createButton(Button paramButton) throws HeadlessException {
/* 103 */     throw new HeadlessException();
/*     */   }
/*     */ 
/*     */   
/*     */   public TextFieldPeer createTextField(TextField paramTextField) throws HeadlessException {
/* 108 */     throw new HeadlessException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChoicePeer createChoice(Choice paramChoice) throws HeadlessException {
/* 113 */     throw new HeadlessException();
/*     */   }
/*     */ 
/*     */   
/*     */   public LabelPeer createLabel(Label paramLabel) throws HeadlessException {
/* 118 */     throw new HeadlessException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ListPeer createList(List paramList) throws HeadlessException {
/* 123 */     throw new HeadlessException();
/*     */   }
/*     */ 
/*     */   
/*     */   public CheckboxPeer createCheckbox(Checkbox paramCheckbox) throws HeadlessException {
/* 128 */     throw new HeadlessException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ScrollbarPeer createScrollbar(Scrollbar paramScrollbar) throws HeadlessException {
/* 133 */     throw new HeadlessException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ScrollPanePeer createScrollPane(ScrollPane paramScrollPane) throws HeadlessException {
/* 138 */     throw new HeadlessException();
/*     */   }
/*     */ 
/*     */   
/*     */   public TextAreaPeer createTextArea(TextArea paramTextArea) throws HeadlessException {
/* 143 */     throw new HeadlessException();
/*     */   }
/*     */ 
/*     */   
/*     */   public FileDialogPeer createFileDialog(FileDialog paramFileDialog) throws HeadlessException {
/* 148 */     throw new HeadlessException();
/*     */   }
/*     */ 
/*     */   
/*     */   public MenuBarPeer createMenuBar(MenuBar paramMenuBar) throws HeadlessException {
/* 153 */     throw new HeadlessException();
/*     */   }
/*     */ 
/*     */   
/*     */   public MenuPeer createMenu(Menu paramMenu) throws HeadlessException {
/* 158 */     throw new HeadlessException();
/*     */   }
/*     */ 
/*     */   
/*     */   public PopupMenuPeer createPopupMenu(PopupMenu paramPopupMenu) throws HeadlessException {
/* 163 */     throw new HeadlessException();
/*     */   }
/*     */ 
/*     */   
/*     */   public MenuItemPeer createMenuItem(MenuItem paramMenuItem) throws HeadlessException {
/* 168 */     throw new HeadlessException();
/*     */   }
/*     */ 
/*     */   
/*     */   public CheckboxMenuItemPeer createCheckboxMenuItem(CheckboxMenuItem paramCheckboxMenuItem) throws HeadlessException {
/* 173 */     throw new HeadlessException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DragSourceContextPeer createDragSourceContextPeer(DragGestureEvent paramDragGestureEvent) throws InvalidDnDOperationException {
/* 179 */     throw new InvalidDnDOperationException("Headless environment");
/*     */   }
/*     */ 
/*     */   
/*     */   public RobotPeer createRobot(Robot paramRobot, GraphicsDevice paramGraphicsDevice) throws AWTException, HeadlessException {
/* 184 */     throw new HeadlessException();
/*     */   }
/*     */ 
/*     */   
/*     */   public KeyboardFocusManagerPeer getKeyboardFocusManagerPeer() {
/* 189 */     return kfmPeer;
/*     */   }
/*     */ 
/*     */   
/*     */   public TrayIconPeer createTrayIcon(TrayIcon paramTrayIcon) throws HeadlessException {
/* 194 */     throw new HeadlessException();
/*     */   }
/*     */ 
/*     */   
/*     */   public SystemTrayPeer createSystemTray(SystemTray paramSystemTray) throws HeadlessException {
/* 199 */     throw new HeadlessException();
/*     */   }
/*     */   
/*     */   public boolean isTraySupported() {
/* 203 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public GlobalCursorManager getGlobalCursorManager() throws HeadlessException {
/* 208 */     throw new HeadlessException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void loadSystemColors(int[] paramArrayOfint) throws HeadlessException {
/* 216 */     throw new HeadlessException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ColorModel getColorModel() throws HeadlessException {
/* 221 */     throw new HeadlessException();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getScreenResolution() throws HeadlessException {
/* 226 */     throw new HeadlessException();
/*     */   }
/*     */ 
/*     */   
/*     */   public Map mapInputMethodHighlight(InputMethodHighlight paramInputMethodHighlight) throws HeadlessException {
/* 231 */     throw new HeadlessException();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMenuShortcutKeyMask() throws HeadlessException {
/* 236 */     throw new HeadlessException();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getLockingKeyState(int paramInt) throws UnsupportedOperationException {
/* 241 */     throw new HeadlessException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLockingKeyState(int paramInt, boolean paramBoolean) throws UnsupportedOperationException {
/* 246 */     throw new HeadlessException();
/*     */   }
/*     */ 
/*     */   
/*     */   public Cursor createCustomCursor(Image paramImage, Point paramPoint, String paramString) throws IndexOutOfBoundsException, HeadlessException {
/* 251 */     throw new HeadlessException();
/*     */   }
/*     */ 
/*     */   
/*     */   public Dimension getBestCursorSize(int paramInt1, int paramInt2) throws HeadlessException {
/* 256 */     throw new HeadlessException();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaximumCursorColors() throws HeadlessException {
/* 261 */     throw new HeadlessException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends java.awt.dnd.DragGestureRecognizer> T createDragGestureRecognizer(Class<T> paramClass, DragSource paramDragSource, Component paramComponent, int paramInt, DragGestureListener paramDragGestureListener) {
/* 269 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getScreenHeight() throws HeadlessException {
/* 274 */     throw new HeadlessException();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getScreenWidth() throws HeadlessException {
/* 279 */     throw new HeadlessException();
/*     */   }
/*     */ 
/*     */   
/*     */   public Dimension getScreenSize() throws HeadlessException {
/* 284 */     throw new HeadlessException();
/*     */   }
/*     */ 
/*     */   
/*     */   public Insets getScreenInsets(GraphicsConfiguration paramGraphicsConfiguration) throws HeadlessException {
/* 289 */     throw new HeadlessException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDynamicLayout(boolean paramBoolean) throws HeadlessException {
/* 294 */     throw new HeadlessException();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isDynamicLayoutSet() throws HeadlessException {
/* 299 */     throw new HeadlessException();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDynamicLayoutActive() throws HeadlessException {
/* 304 */     throw new HeadlessException();
/*     */   }
/*     */ 
/*     */   
/*     */   public Clipboard getSystemClipboard() throws HeadlessException {
/* 309 */     throw new HeadlessException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PrintJob getPrintJob(Frame paramFrame, String paramString, JobAttributes paramJobAttributes, PageAttributes paramPageAttributes) {
/* 318 */     if (paramFrame != null)
/*     */     {
/* 320 */       throw new HeadlessException();
/*     */     }
/* 322 */     throw new NullPointerException("frame must not be null");
/*     */   }
/*     */ 
/*     */   
/*     */   public PrintJob getPrintJob(Frame paramFrame, String paramString, Properties paramProperties) {
/* 327 */     if (paramFrame != null)
/*     */     {
/* 329 */       throw new HeadlessException();
/*     */     }
/* 331 */     throw new NullPointerException("frame must not be null");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sync() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void beep() {
/* 344 */     System.out.write(7);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EventQueue getSystemEventQueueImpl() {
/* 351 */     return SunToolkit.getSystemEventQueueImplPP();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int checkImage(Image paramImage, int paramInt1, int paramInt2, ImageObserver paramImageObserver) {
/* 358 */     return this.tk.checkImage(paramImage, paramInt1, paramInt2, paramImageObserver);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean prepareImage(Image paramImage, int paramInt1, int paramInt2, ImageObserver paramImageObserver) {
/* 363 */     return this.tk.prepareImage(paramImage, paramInt1, paramInt2, paramImageObserver);
/*     */   }
/*     */   
/*     */   public Image getImage(String paramString) {
/* 367 */     return this.tk.getImage(paramString);
/*     */   }
/*     */   
/*     */   public Image getImage(URL paramURL) {
/* 371 */     return this.tk.getImage(paramURL);
/*     */   }
/*     */   
/*     */   public Image createImage(String paramString) {
/* 375 */     return this.tk.createImage(paramString);
/*     */   }
/*     */   
/*     */   public Image createImage(URL paramURL) {
/* 379 */     return this.tk.createImage(paramURL);
/*     */   }
/*     */   
/*     */   public Image createImage(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 383 */     return this.tk.createImage(paramArrayOfbyte, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public Image createImage(ImageProducer paramImageProducer) {
/* 387 */     return this.tk.createImage(paramImageProducer);
/*     */   }
/*     */   
/*     */   public Image createImage(byte[] paramArrayOfbyte) {
/* 391 */     return this.tk.createImage(paramArrayOfbyte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FontPeer getFontPeer(String paramString, int paramInt) {
/* 400 */     if (this.componentFactory != null) {
/* 401 */       return this.componentFactory.getFontPeer(paramString, paramInt);
/*     */     }
/* 403 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public DataTransferer getDataTransferer() {
/* 408 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public FontMetrics getFontMetrics(Font paramFont) {
/* 413 */     return this.tk.getFontMetrics(paramFont);
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getFontList() {
/* 418 */     return this.tk.getFontList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addPropertyChangeListener(String paramString, PropertyChangeListener paramPropertyChangeListener) {
/* 427 */     this.tk.addPropertyChangeListener(paramString, paramPropertyChangeListener);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removePropertyChangeListener(String paramString, PropertyChangeListener paramPropertyChangeListener) {
/* 432 */     this.tk.removePropertyChangeListener(paramString, paramPropertyChangeListener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isModalityTypeSupported(Dialog.ModalityType paramModalityType) {
/* 439 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isModalExclusionTypeSupported(Dialog.ModalExclusionType paramModalExclusionType) {
/* 443 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAlwaysOnTopSupported() {
/* 450 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAWTEventListener(AWTEventListener paramAWTEventListener, long paramLong) {
/* 459 */     this.tk.addAWTEventListener(paramAWTEventListener, paramLong);
/*     */   }
/*     */   
/*     */   public void removeAWTEventListener(AWTEventListener paramAWTEventListener) {
/* 463 */     this.tk.removeAWTEventListener(paramAWTEventListener);
/*     */   }
/*     */   
/*     */   public AWTEventListener[] getAWTEventListeners() {
/* 467 */     return this.tk.getAWTEventListeners();
/*     */   }
/*     */   
/*     */   public AWTEventListener[] getAWTEventListeners(long paramLong) {
/* 471 */     return this.tk.getAWTEventListeners(paramLong);
/*     */   }
/*     */   
/*     */   public boolean isDesktopSupported() {
/* 475 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public DesktopPeer createDesktopPeer(Desktop paramDesktop) throws HeadlessException {
/* 480 */     throw new HeadlessException();
/*     */   }
/*     */   
/*     */   public boolean areExtraMouseButtonsEnabled() throws HeadlessException {
/* 484 */     throw new HeadlessException();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/HeadlessToolkit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */