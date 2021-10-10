/*      */ package java.awt;
/*      */ 
/*      */ import java.awt.font.FontRenderContext;
/*      */ import java.awt.font.GlyphVector;
/*      */ import java.awt.font.LineMetrics;
/*      */ import java.awt.font.TextAttribute;
/*      */ import java.awt.font.TextLayout;
/*      */ import java.awt.geom.AffineTransform;
/*      */ import java.awt.geom.Point2D;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.awt.peer.FontPeer;
/*      */ import java.io.File;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.FilePermission;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.lang.ref.SoftReference;
/*      */ import java.nio.file.Files;
/*      */ import java.nio.file.attribute.FileAttribute;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedExceptionAction;
/*      */ import java.text.AttributedCharacterIterator;
/*      */ import java.text.CharacterIterator;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import sun.font.AttributeMap;
/*      */ import sun.font.AttributeValues;
/*      */ import sun.font.CompositeFont;
/*      */ import sun.font.CoreMetrics;
/*      */ import sun.font.CreatedFontTracker;
/*      */ import sun.font.EAttribute;
/*      */ import sun.font.Font2D;
/*      */ import sun.font.Font2DHandle;
/*      */ import sun.font.FontAccess;
/*      */ import sun.font.FontLineMetrics;
/*      */ import sun.font.FontManager;
/*      */ import sun.font.FontManagerFactory;
/*      */ import sun.font.FontUtilities;
/*      */ import sun.font.GlyphLayout;
/*      */ import sun.font.StandardGlyphVector;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Font
/*      */   implements Serializable
/*      */ {
/*      */   private Hashtable<Object, Object> fRequestedAttributes;
/*      */   public static final String DIALOG = "Dialog";
/*      */   public static final String DIALOG_INPUT = "DialogInput";
/*      */   public static final String SANS_SERIF = "SansSerif";
/*      */   public static final String SERIF = "Serif";
/*      */   public static final String MONOSPACED = "Monospaced";
/*      */   public static final int PLAIN = 0;
/*      */   public static final int BOLD = 1;
/*      */   public static final int ITALIC = 2;
/*      */   public static final int ROMAN_BASELINE = 0;
/*      */   public static final int CENTER_BASELINE = 1;
/*      */   public static final int HANGING_BASELINE = 2;
/*      */   public static final int TRUETYPE_FONT = 0;
/*      */   public static final int TYPE1_FONT = 1;
/*      */   protected String name;
/*      */   protected int style;
/*      */   protected int size;
/*      */   protected float pointSize;
/*      */   private transient FontPeer peer;
/*      */   private transient long pData;
/*      */   private transient Font2DHandle font2DHandle;
/*      */   private transient AttributeValues values;
/*      */   private transient boolean hasLayoutAttributes;
/*      */   
/*      */   private static class FontAccessImpl
/*      */     extends FontAccess
/*      */   {
/*      */     private FontAccessImpl() {}
/*      */     
/*      */     public Font2D getFont2D(Font param1Font) {
/*  228 */       return param1Font.getFont2D();
/*      */     }
/*      */     
/*      */     public void setFont2D(Font param1Font, Font2DHandle param1Font2DHandle) {
/*  232 */       param1Font.font2DHandle = param1Font2DHandle;
/*      */     }
/*      */     
/*      */     public void setCreatedFont(Font param1Font) {
/*  236 */       param1Font.createdFont = true;
/*      */     }
/*      */     
/*      */     public boolean isCreatedFont(Font param1Font) {
/*  240 */       return param1Font.createdFont;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static {
/*  246 */     Toolkit.loadLibraries();
/*  247 */     initIDs();
/*  248 */     FontAccess.setFontAccess(new FontAccessImpl());
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient boolean createdFont = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient boolean nonIdentityTx;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  429 */   private static final AffineTransform identityTx = new AffineTransform();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long serialVersionUID = -4206021311591459213L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public FontPeer getPeer() {
/*  444 */     return getPeer_NoClientCode();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final FontPeer getPeer_NoClientCode() {
/*  452 */     if (this.peer == null) {
/*  453 */       Toolkit toolkit = Toolkit.getDefaultToolkit();
/*  454 */       this.peer = toolkit.getFontPeer(this.name, this.style);
/*      */     } 
/*  456 */     return this.peer;
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
/*      */   private AttributeValues getAttributeValues() {
/*  471 */     if (this.values == null) {
/*  472 */       AttributeValues attributeValues = new AttributeValues();
/*  473 */       attributeValues.setFamily(this.name);
/*  474 */       attributeValues.setSize(this.pointSize);
/*      */       
/*  476 */       if ((this.style & 0x1) != 0) {
/*  477 */         attributeValues.setWeight(2.0F);
/*      */       }
/*      */       
/*  480 */       if ((this.style & 0x2) != 0) {
/*  481 */         attributeValues.setPosture(0.2F);
/*      */       }
/*  483 */       attributeValues.defineAll(PRIMARY_MASK);
/*  484 */       this.values = attributeValues;
/*      */     } 
/*      */     
/*  487 */     return this.values;
/*      */   }
/*      */   
/*      */   private Font2D getFont2D() {
/*  491 */     FontManager fontManager = FontManagerFactory.getInstance();
/*  492 */     if (fontManager.usingPerAppContextComposites() && this.font2DHandle != null && this.font2DHandle.font2D instanceof CompositeFont && ((CompositeFont)this.font2DHandle.font2D)
/*      */ 
/*      */       
/*  495 */       .isStdComposite()) {
/*  496 */       return fontManager.findFont2D(this.name, this.style, 2);
/*      */     }
/*  498 */     if (this.font2DHandle == null) {
/*  499 */       this
/*  500 */         .font2DHandle = (fontManager.findFont2D(this.name, this.style, 2)).handle;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  507 */     return this.font2DHandle.font2D;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Font(String paramString, int paramInt, float paramFloat, boolean paramBoolean, Font2DHandle paramFont2DHandle) {
/*  584 */     this(paramString, paramInt, paramFloat);
/*  585 */     this.createdFont = paramBoolean;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  595 */     if (paramBoolean) {
/*  596 */       if (paramFont2DHandle.font2D instanceof CompositeFont && paramFont2DHandle.font2D
/*  597 */         .getStyle() != paramInt) {
/*  598 */         FontManager fontManager = FontManagerFactory.getInstance();
/*  599 */         this.font2DHandle = fontManager.getNewComposite((String)null, paramInt, paramFont2DHandle);
/*      */       } else {
/*  601 */         this.font2DHandle = paramFont2DHandle;
/*      */       } 
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  721 */   private static final int RECOGNIZED_MASK = AttributeValues.MASK_ALL & (
/*  722 */     AttributeValues.getMask(EAttribute.EFONT) ^ 0xFFFFFFFF);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  728 */   private static final int PRIMARY_MASK = AttributeValues.getMask(new EAttribute[] { EAttribute.EFAMILY, EAttribute.EWEIGHT, EAttribute.EWIDTH, EAttribute.EPOSTURE, EAttribute.ESIZE, EAttribute.ETRANSFORM, EAttribute.ESUPERSCRIPT, EAttribute.ETRACKING });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  734 */   private static final int SECONDARY_MASK = RECOGNIZED_MASK & (PRIMARY_MASK ^ 0xFFFFFFFF);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  741 */   private static final int LAYOUT_MASK = AttributeValues.getMask(new EAttribute[] { 
/*      */         EAttribute.ECHAR_REPLACEMENT, EAttribute.EFOREGROUND, EAttribute.EBACKGROUND, EAttribute.EUNDERLINE, EAttribute.ESTRIKETHROUGH, EAttribute.ERUN_DIRECTION, EAttribute.EBIDI_EMBEDDING, EAttribute.EJUSTIFICATION, EAttribute.EINPUT_METHOD_HIGHLIGHT, EAttribute.EINPUT_METHOD_UNDERLINE, 
/*      */         EAttribute.ESWAP_COLORS, EAttribute.ENUMERIC_SHAPING, EAttribute.EKERNING, EAttribute.ELIGATURES, EAttribute.ETRACKING, EAttribute.ESUPERSCRIPT });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  749 */   private static final int EXTRA_MASK = AttributeValues.getMask(new EAttribute[] { EAttribute.ETRANSFORM, EAttribute.ESUPERSCRIPT, EAttribute.EWIDTH });
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initFromValues(AttributeValues paramAttributeValues) {
/*  755 */     this.values = paramAttributeValues;
/*  756 */     paramAttributeValues.defineAll(PRIMARY_MASK);
/*      */     
/*  758 */     this.name = paramAttributeValues.getFamily();
/*  759 */     this.pointSize = paramAttributeValues.getSize();
/*  760 */     this.size = (int)(paramAttributeValues.getSize() + 0.5D);
/*  761 */     if (paramAttributeValues.getWeight() >= 2.0F) this.style |= 0x1; 
/*  762 */     if (paramAttributeValues.getPosture() >= 0.2F) this.style |= 0x2;
/*      */     
/*  764 */     this.nonIdentityTx = paramAttributeValues.anyNonDefault(EXTRA_MASK);
/*  765 */     this.hasLayoutAttributes = paramAttributeValues.anyNonDefault(LAYOUT_MASK);
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
/*      */   public static Font getFont(Map<? extends AttributedCharacterIterator.Attribute, ?> paramMap) {
/*  790 */     if (paramMap instanceof AttributeMap && ((AttributeMap)paramMap)
/*  791 */       .getValues() != null) {
/*  792 */       AttributeValues attributeValues = ((AttributeMap)paramMap).getValues();
/*  793 */       if (attributeValues.isNonDefault(EAttribute.EFONT)) {
/*  794 */         Font font1 = attributeValues.getFont();
/*  795 */         if (!attributeValues.anyDefined(SECONDARY_MASK)) {
/*  796 */           return font1;
/*      */         }
/*      */         
/*  799 */         attributeValues = font1.getAttributeValues().clone();
/*  800 */         attributeValues.merge(paramMap, SECONDARY_MASK);
/*  801 */         return new Font(attributeValues, font1.name, font1.style, font1.createdFont, font1.font2DHandle);
/*      */       } 
/*      */       
/*  804 */       return new Font(paramMap);
/*      */     } 
/*      */     
/*  807 */     Font font = (Font)paramMap.get(TextAttribute.FONT);
/*  808 */     if (font != null) {
/*  809 */       if (paramMap.size() > 1) {
/*  810 */         AttributeValues attributeValues = font.getAttributeValues().clone();
/*  811 */         attributeValues.merge(paramMap, SECONDARY_MASK);
/*  812 */         return new Font(attributeValues, font.name, font.style, font.createdFont, font.font2DHandle);
/*      */       } 
/*      */ 
/*      */       
/*  816 */       return font;
/*      */     } 
/*      */     
/*  819 */     return new Font(paramMap);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean hasTempPermission() {
/*  829 */     if (System.getSecurityManager() == null) {
/*  830 */       return true;
/*      */     }
/*  832 */     File file = null;
/*  833 */     boolean bool = false;
/*      */     try {
/*  835 */       file = Files.createTempFile("+~JT", ".tmp", (FileAttribute<?>[])new FileAttribute[0]).toFile();
/*  836 */       file.delete();
/*  837 */       file = null;
/*  838 */       bool = true;
/*  839 */     } catch (Throwable throwable) {}
/*      */ 
/*      */     
/*  842 */     return bool;
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
/*      */   public static Font createFont(int paramInt, InputStream paramInputStream) throws FontFormatException, IOException {
/*  876 */     if (hasTempPermission()) {
/*  877 */       return createFont0(paramInt, paramInputStream, null);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  882 */     CreatedFontTracker createdFontTracker = CreatedFontTracker.getTracker();
/*  883 */     boolean bool = false;
/*      */     try {
/*  885 */       bool = createdFontTracker.acquirePermit();
/*  886 */       if (!bool) {
/*  887 */         throw new IOException("Timed out waiting for resources.");
/*      */       }
/*  889 */       return createFont0(paramInt, paramInputStream, createdFontTracker);
/*  890 */     } catch (InterruptedException interruptedException) {
/*  891 */       throw new IOException("Problem reading font data.");
/*      */     } finally {
/*  893 */       if (bool) {
/*  894 */         createdFontTracker.releasePermit();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Font createFont0(int paramInt, InputStream paramInputStream, CreatedFontTracker paramCreatedFontTracker) throws FontFormatException, IOException {
/*  903 */     if (paramInt != 0 && paramInt != 1)
/*      */     {
/*  905 */       throw new IllegalArgumentException("font format not recognized");
/*      */     }
/*  907 */     boolean bool = false;
/*      */     try {
/*  909 */       final File tFile = AccessController.<File>doPrivileged(new PrivilegedExceptionAction<File>()
/*      */           {
/*      */             public File run() throws IOException {
/*  912 */               return Files.createTempFile("+~JF", ".tmp", (FileAttribute<?>[])new FileAttribute[0]).toFile();
/*      */             }
/*      */           });
/*      */       
/*  916 */       if (paramCreatedFontTracker != null) {
/*  917 */         paramCreatedFontTracker.add(file);
/*      */       }
/*      */       
/*  920 */       int i = 0;
/*      */       
/*      */       try {
/*  923 */         OutputStream outputStream = AccessController.<OutputStream>doPrivileged(new PrivilegedExceptionAction<OutputStream>()
/*      */             {
/*      */               public OutputStream run() throws IOException {
/*  926 */                 return new FileOutputStream(tFile);
/*      */               }
/*      */             });
/*      */         
/*  930 */         if (paramCreatedFontTracker != null) {
/*  931 */           paramCreatedFontTracker.set(file, outputStream);
/*      */         }
/*      */         try {
/*  934 */           byte[] arrayOfByte = new byte[8192];
/*      */           while (true) {
/*  936 */             int j = paramInputStream.read(arrayOfByte);
/*  937 */             if (j < 0) {
/*      */               break;
/*      */             }
/*  940 */             if (paramCreatedFontTracker != null) {
/*  941 */               if (i + j > 33554432) {
/*  942 */                 throw new IOException("File too big.");
/*      */               }
/*  944 */               if (i + paramCreatedFontTracker.getNumBytes() > 335544320)
/*      */               {
/*      */                 
/*  947 */                 throw new IOException("Total files too big.");
/*      */               }
/*  949 */               i += j;
/*  950 */               paramCreatedFontTracker.addBytes(j);
/*      */             } 
/*  952 */             outputStream.write(arrayOfByte, 0, j);
/*      */           } 
/*      */         } finally {
/*      */           
/*  956 */           outputStream.close();
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  968 */         bool = true;
/*  969 */         Font font = new Font(file, paramInt, true, paramCreatedFontTracker);
/*  970 */         return font;
/*      */       } finally {
/*  972 */         if (paramCreatedFontTracker != null) {
/*  973 */           paramCreatedFontTracker.remove(file);
/*      */         }
/*  975 */         if (!bool) {
/*  976 */           if (paramCreatedFontTracker != null) {
/*  977 */             paramCreatedFontTracker.subBytes(i);
/*      */           }
/*  979 */           AccessController.doPrivileged(new PrivilegedExceptionAction<Void>()
/*      */               {
/*      */                 public Void run() {
/*  982 */                   tFile.delete();
/*  983 */                   return null;
/*      */                 }
/*      */               });
/*      */         }
/*      */       
/*      */       } 
/*  989 */     } catch (Throwable throwable1) {
/*  990 */       if (throwable1 instanceof FontFormatException) {
/*  991 */         throw (FontFormatException)throwable1;
/*      */       }
/*  993 */       if (throwable1 instanceof IOException) {
/*  994 */         throw (IOException)throwable1;
/*      */       }
/*  996 */       Throwable throwable2 = throwable1.getCause();
/*  997 */       if (throwable2 instanceof FontFormatException) {
/*  998 */         throw (FontFormatException)throwable2;
/*      */       }
/* 1000 */       throw new IOException("Problem reading font data.");
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Font createFont(int paramInt, File paramFile) throws FontFormatException, IOException {
/* 1041 */     paramFile = new File(paramFile.getPath());
/*      */     
/* 1043 */     if (paramInt != 0 && paramInt != 1)
/*      */     {
/* 1045 */       throw new IllegalArgumentException("font format not recognized");
/*      */     }
/* 1047 */     SecurityManager securityManager = System.getSecurityManager();
/* 1048 */     if (securityManager != null) {
/*      */       
/* 1050 */       FilePermission filePermission = new FilePermission(paramFile.getPath(), "read");
/* 1051 */       securityManager.checkPermission(filePermission);
/*      */     } 
/* 1053 */     if (!paramFile.canRead()) {
/* 1054 */       throw new IOException("Can't read " + paramFile);
/*      */     }
/* 1056 */     return new Font(paramFile, paramInt, false, null);
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
/*      */   public AffineTransform getTransform() {
/* 1087 */     if (this.nonIdentityTx) {
/* 1088 */       AttributeValues attributeValues = getAttributeValues();
/*      */ 
/*      */       
/* 1091 */       AffineTransform affineTransform = attributeValues.isNonDefault(EAttribute.ETRANSFORM) ? new AffineTransform(attributeValues.getTransform()) : new AffineTransform();
/*      */ 
/*      */       
/* 1094 */       if (attributeValues.getSuperscript() != 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1099 */         int i = attributeValues.getSuperscript();
/*      */         
/* 1101 */         double d1 = 0.0D;
/* 1102 */         int j = 0;
/* 1103 */         boolean bool = (i > 0) ? true : false;
/* 1104 */         byte b = bool ? -1 : 1;
/* 1105 */         int k = bool ? i : -i;
/*      */         
/* 1107 */         while ((k & 0x7) > j) {
/* 1108 */           int m = k & 0x7;
/* 1109 */           d1 += (b * (ssinfo[m] - ssinfo[j]));
/* 1110 */           k >>= 3;
/* 1111 */           b = -b;
/* 1112 */           j = m;
/*      */         } 
/* 1114 */         d1 *= this.pointSize;
/* 1115 */         double d2 = Math.pow(0.6666666666666666D, j);
/*      */         
/* 1117 */         affineTransform.preConcatenate(AffineTransform.getTranslateInstance(0.0D, d1));
/* 1118 */         affineTransform.scale(d2, d2);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1134 */       if (attributeValues.isNonDefault(EAttribute.EWIDTH)) {
/* 1135 */         affineTransform.scale(attributeValues.getWidth(), 1.0D);
/*      */       }
/*      */       
/* 1138 */       return affineTransform;
/*      */     } 
/*      */     
/* 1141 */     return new AffineTransform();
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
/* 1155 */   private static final float[] ssinfo = new float[] { 0.0F, 0.375F, 0.625F, 0.7916667F, 0.9027778F, 0.9768519F, 1.0262346F, 1.0591564F };
/*      */ 
/*      */ 
/*      */   
/*      */   transient int hash;
/*      */ 
/*      */ 
/*      */   
/*      */   private int fontSerializedDataVersion;
/*      */ 
/*      */ 
/*      */   
/*      */   private transient SoftReference<FontLineMetrics> flmref;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int LAYOUT_LEFT_TO_RIGHT = 0;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int LAYOUT_RIGHT_TO_LEFT = 1;
/*      */ 
/*      */   
/*      */   public static final int LAYOUT_NO_START_CONTEXT = 2;
/*      */ 
/*      */   
/*      */   public static final int LAYOUT_NO_LIMIT_CONTEXT = 4;
/*      */ 
/*      */ 
/*      */   
/*      */   public String getFamily() {
/* 1186 */     return getFamily_NoClientCode();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final String getFamily_NoClientCode() {
/* 1194 */     return getFamily(Locale.getDefault());
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
/*      */   public String getFamily(Locale paramLocale) {
/* 1217 */     if (paramLocale == null) {
/* 1218 */       throw new NullPointerException("null locale doesn't mean default");
/*      */     }
/* 1220 */     return getFont2D().getFamilyName(paramLocale);
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
/*      */   public String getPSName() {
/* 1232 */     return getFont2D().getPostscriptName();
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
/*      */   public String getName() {
/* 1246 */     return this.name;
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
/*      */   public String getFontName() {
/* 1261 */     return getFontName(Locale.getDefault());
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
/*      */   public String getFontName(Locale paramLocale) {
/* 1276 */     if (paramLocale == null) {
/* 1277 */       throw new NullPointerException("null locale doesn't mean default");
/*      */     }
/* 1279 */     return getFont2D().getFontName(paramLocale);
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
/*      */   public int getStyle() {
/* 1292 */     return this.style;
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
/*      */   public int getSize() {
/* 1318 */     return this.size;
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
/*      */   public float getSize2D() {
/* 1330 */     return this.pointSize;
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
/*      */   public boolean isPlain() {
/* 1343 */     return (this.style == 0);
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
/*      */   public boolean isBold() {
/* 1356 */     return ((this.style & 0x1) != 0);
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
/*      */   public boolean isItalic() {
/* 1369 */     return ((this.style & 0x2) != 0);
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
/*      */   public boolean isTransformed() {
/* 1383 */     return this.nonIdentityTx;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasLayoutAttributes() {
/* 1393 */     return this.hasLayoutAttributes;
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
/*      */   public static Font getFont(String paramString) {
/* 1413 */     return getFont(paramString, null);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Font decode(String paramString) {
/* 1490 */     String str1 = paramString;
/* 1491 */     String str2 = "";
/* 1492 */     int i = 12;
/* 1493 */     byte b1 = 0;
/*      */     
/* 1495 */     if (paramString == null) {
/* 1496 */       return new Font("Dialog", b1, i);
/*      */     }
/*      */     
/* 1499 */     int j = paramString.lastIndexOf('-');
/* 1500 */     int k = paramString.lastIndexOf(' ');
/* 1501 */     byte b2 = (j > k) ? 45 : 32;
/* 1502 */     int m = paramString.lastIndexOf(b2);
/* 1503 */     int n = paramString.lastIndexOf(b2, m - 1);
/* 1504 */     int i1 = paramString.length();
/*      */     
/* 1506 */     if (m > 0 && m + 1 < i1) {
/*      */       
/*      */       try {
/* 1509 */         i = Integer.valueOf(paramString.substring(m + 1)).intValue();
/* 1510 */         if (i <= 0) {
/* 1511 */           i = 12;
/*      */         }
/* 1513 */       } catch (NumberFormatException numberFormatException) {
/*      */ 
/*      */         
/* 1516 */         n = m;
/* 1517 */         m = i1;
/* 1518 */         if (paramString.charAt(m - 1) == b2) {
/* 1519 */           m--;
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 1524 */     if (n >= 0 && n + 1 < i1) {
/* 1525 */       str2 = paramString.substring(n + 1, m);
/* 1526 */       str2 = str2.toLowerCase(Locale.ENGLISH);
/* 1527 */       if (str2.equals("bolditalic")) {
/* 1528 */         b1 = 3;
/* 1529 */       } else if (str2.equals("italic")) {
/* 1530 */         b1 = 2;
/* 1531 */       } else if (str2.equals("bold")) {
/* 1532 */         b1 = 1;
/* 1533 */       } else if (str2.equals("plain")) {
/* 1534 */         b1 = 0;
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 1539 */         n = m;
/* 1540 */         if (paramString.charAt(n - 1) == b2) {
/* 1541 */           n--;
/*      */         }
/*      */       } 
/* 1544 */       str1 = paramString.substring(0, n);
/*      */     } else {
/*      */       
/* 1547 */       int i2 = i1;
/* 1548 */       if (n > 0) {
/* 1549 */         i2 = n;
/* 1550 */       } else if (m > 0) {
/* 1551 */         i2 = m;
/*      */       } 
/* 1553 */       if (i2 > 0 && paramString.charAt(i2 - 1) == b2) {
/* 1554 */         i2--;
/*      */       }
/* 1556 */       str1 = paramString.substring(0, i2);
/*      */     } 
/*      */     
/* 1559 */     return new Font(str1, b1, i);
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
/*      */   public static Font getFont(String paramString, Font paramFont) {
/* 1583 */     String str = null;
/*      */     try {
/* 1585 */       str = System.getProperty(paramString);
/* 1586 */     } catch (SecurityException securityException) {}
/*      */     
/* 1588 */     if (str == null) {
/* 1589 */       return paramFont;
/*      */     }
/* 1591 */     return decode(str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1601 */     if (this.hash == 0) {
/* 1602 */       this.hash = this.name.hashCode() ^ this.style ^ this.size;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1609 */       if (this.nonIdentityTx && this.values != null && this.values
/* 1610 */         .getTransform() != null) {
/* 1611 */         this.hash ^= this.values.getTransform().hashCode();
/*      */       }
/*      */     } 
/* 1614 */     return this.hash;
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
/*      */   public boolean equals(Object paramObject) {
/* 1628 */     if (paramObject == this) {
/* 1629 */       return true;
/*      */     }
/*      */     
/* 1632 */     if (paramObject != null) {
/*      */       try {
/* 1634 */         Font font = (Font)paramObject;
/* 1635 */         if (this.size == font.size && this.style == font.style && this.nonIdentityTx == font.nonIdentityTx && this.hasLayoutAttributes == font.hasLayoutAttributes && this.pointSize == font.pointSize && this.name
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1640 */           .equals(font.name))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1648 */           if (this.values == null) {
/* 1649 */             if (font.values == null) {
/* 1650 */               return true;
/*      */             }
/* 1652 */             return getAttributeValues().equals(font.values);
/*      */           } 
/*      */           
/* 1655 */           return this.values.equals(font.getAttributeValues());
/*      */         }
/*      */       
/*      */       }
/* 1659 */       catch (ClassCastException classCastException) {}
/*      */     }
/*      */     
/* 1662 */     return false;
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
/*      */   public String toString() {
/*      */     String str;
/* 1677 */     if (isBold()) {
/* 1678 */       str = isItalic() ? "bolditalic" : "bold";
/*      */     } else {
/* 1680 */       str = isItalic() ? "italic" : "plain";
/*      */     } 
/*      */     
/* 1683 */     return getClass().getName() + "[family=" + getFamily() + ",name=" + this.name + ",style=" + str + ",size=" + this.size + "]";
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
/*      */   public Font(String paramString, int paramInt1, int paramInt2) {
/* 1702 */     this.fontSerializedDataVersion = 1; this.name = (paramString != null) ? paramString : "Default"; this.style = ((paramInt1 & 0xFFFFFFFC) == 0) ? paramInt1 : 0; this.size = paramInt2; this.pointSize = paramInt2; } private Font(String paramString, int paramInt, float paramFloat) { this.fontSerializedDataVersion = 1; this.name = (paramString != null) ? paramString : "Default"; this.style = ((paramInt & 0xFFFFFFFC) == 0) ? paramInt : 0; this.size = (int)(paramFloat + 0.5D); this.pointSize = paramFloat; } private Font(File paramFile, int paramInt, boolean paramBoolean, CreatedFontTracker paramCreatedFontTracker) throws FontFormatException { this.fontSerializedDataVersion = 1; this.createdFont = true; FontManager fontManager = FontManagerFactory.getInstance(); this.font2DHandle = (fontManager.createFont2D(paramFile, paramInt, paramBoolean, paramCreatedFontTracker)).handle; this.name = this.font2DHandle.font2D.getFontName(Locale.getDefault()); this.style = 0; this.size = 1; this.pointSize = 1.0F; } private Font(AttributeValues paramAttributeValues, String paramString, int paramInt, boolean paramBoolean, Font2DHandle paramFont2DHandle) { this.fontSerializedDataVersion = 1; this.createdFont = paramBoolean; if (paramBoolean) { this.font2DHandle = paramFont2DHandle; String str = null; if (paramString != null) { str = paramAttributeValues.getFamily(); if (paramString.equals(str)) str = null;  }  int i = 0; if (paramInt == -1) { i = -1; } else { if (paramAttributeValues.getWeight() >= 2.0F) i = 1;  if (paramAttributeValues.getPosture() >= 0.2F) i |= 0x2;  if (paramInt == i) i = -1;  }  if (paramFont2DHandle.font2D instanceof CompositeFont) { if (i != -1 || str != null) { FontManager fontManager = FontManagerFactory.getInstance(); this.font2DHandle = fontManager.getNewComposite(str, i, paramFont2DHandle); }  } else if (str != null) { this.createdFont = false; this.font2DHandle = null; }  }  initFromValues(paramAttributeValues); } public Font(Map<? extends AttributedCharacterIterator.Attribute, ?> paramMap) { this.fontSerializedDataVersion = 1; initFromValues(AttributeValues.fromMap(paramMap, RECOGNIZED_MASK)); } protected Font(Font paramFont) { this.fontSerializedDataVersion = 1;
/*      */     if (paramFont.values != null) {
/*      */       initFromValues(paramFont.getAttributeValues().clone());
/*      */     } else {
/*      */       this.name = paramFont.name;
/*      */       this.style = paramFont.style;
/*      */       this.size = paramFont.size;
/*      */       this.pointSize = paramFont.pointSize;
/*      */     } 
/*      */     this.font2DHandle = paramFont.font2DHandle;
/*      */     this.createdFont = paramFont.createdFont; }
/*      */   
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws ClassNotFoundException, IOException {
/* 1715 */     if (this.values != null) {
/* 1716 */       synchronized (this.values) {
/*      */         
/* 1718 */         this.fRequestedAttributes = this.values.toSerializableHashtable();
/* 1719 */         paramObjectOutputStream.defaultWriteObject();
/* 1720 */         this.fRequestedAttributes = null;
/*      */       } 
/*      */     } else {
/* 1723 */       paramObjectOutputStream.defaultWriteObject();
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
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws ClassNotFoundException, IOException {
/* 1739 */     paramObjectInputStream.defaultReadObject();
/* 1740 */     if (this.pointSize == 0.0F) {
/* 1741 */       this.pointSize = this.size;
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
/* 1752 */     if (this.fRequestedAttributes != null) {
/*      */       try {
/* 1754 */         this.values = getAttributeValues();
/*      */         
/* 1756 */         AttributeValues attributeValues = AttributeValues.fromSerializableHashtable(this.fRequestedAttributes);
/* 1757 */         if (!AttributeValues.is16Hashtable(this.fRequestedAttributes)) {
/* 1758 */           attributeValues.unsetDefault();
/*      */         }
/* 1760 */         this.values = getAttributeValues().merge(attributeValues);
/* 1761 */         this.nonIdentityTx = this.values.anyNonDefault(EXTRA_MASK);
/* 1762 */         this.hasLayoutAttributes = this.values.anyNonDefault(LAYOUT_MASK);
/* 1763 */       } catch (Throwable throwable) {
/* 1764 */         throw new IOException(throwable);
/*      */       } finally {
/* 1766 */         this.fRequestedAttributes = null;
/*      */       } 
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
/*      */   public int getNumGlyphs() {
/* 1779 */     return getFont2D().getNumGlyphs();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMissingGlyphCode() {
/* 1789 */     return getFont2D().getMissingGlyphCode();
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
/*      */   public byte getBaselineFor(char paramChar) {
/* 1809 */     return getFont2D().getBaselineFor(paramChar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<TextAttribute, ?> getAttributes() {
/* 1819 */     return new AttributeMap(getAttributeValues());
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
/*      */   public AttributedCharacterIterator.Attribute[] getAvailableAttributes() {
/* 1833 */     return new AttributedCharacterIterator.Attribute[] { TextAttribute.FAMILY, TextAttribute.WEIGHT, TextAttribute.WIDTH, TextAttribute.POSTURE, TextAttribute.SIZE, TextAttribute.TRANSFORM, TextAttribute.SUPERSCRIPT, TextAttribute.CHAR_REPLACEMENT, TextAttribute.FOREGROUND, TextAttribute.BACKGROUND, TextAttribute.UNDERLINE, TextAttribute.STRIKETHROUGH, TextAttribute.RUN_DIRECTION, TextAttribute.BIDI_EMBEDDING, TextAttribute.JUSTIFICATION, TextAttribute.INPUT_METHOD_HIGHLIGHT, TextAttribute.INPUT_METHOD_UNDERLINE, TextAttribute.SWAP_COLORS, TextAttribute.NUMERIC_SHAPING, TextAttribute.KERNING, TextAttribute.LIGATURES, TextAttribute.TRACKING };
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
/*      */   public Font deriveFont(int paramInt, float paramFloat) {
/* 1870 */     if (this.values == null) {
/* 1871 */       return new Font(this.name, paramInt, paramFloat, this.createdFont, this.font2DHandle);
/*      */     }
/* 1873 */     AttributeValues attributeValues = getAttributeValues().clone();
/* 1874 */     boolean bool = (this.style != paramInt) ? this.style : true;
/* 1875 */     applyStyle(paramInt, attributeValues);
/* 1876 */     attributeValues.setSize(paramFloat);
/* 1877 */     return new Font(attributeValues, null, bool, this.createdFont, this.font2DHandle);
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
/*      */   public Font deriveFont(int paramInt, AffineTransform paramAffineTransform) {
/* 1892 */     AttributeValues attributeValues = getAttributeValues().clone();
/* 1893 */     boolean bool = (this.style != paramInt) ? this.style : true;
/* 1894 */     applyStyle(paramInt, attributeValues);
/* 1895 */     applyTransform(paramAffineTransform, attributeValues);
/* 1896 */     return new Font(attributeValues, null, bool, this.createdFont, this.font2DHandle);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Font deriveFont(float paramFloat) {
/* 1907 */     if (this.values == null) {
/* 1908 */       return new Font(this.name, this.style, paramFloat, this.createdFont, this.font2DHandle);
/*      */     }
/* 1910 */     AttributeValues attributeValues = getAttributeValues().clone();
/* 1911 */     attributeValues.setSize(paramFloat);
/* 1912 */     return new Font(attributeValues, null, -1, this.createdFont, this.font2DHandle);
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
/*      */   public Font deriveFont(AffineTransform paramAffineTransform) {
/* 1926 */     AttributeValues attributeValues = getAttributeValues().clone();
/* 1927 */     applyTransform(paramAffineTransform, attributeValues);
/* 1928 */     return new Font(attributeValues, null, -1, this.createdFont, this.font2DHandle);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Font deriveFont(int paramInt) {
/* 1939 */     if (this.values == null) {
/* 1940 */       return new Font(this.name, paramInt, this.size, this.createdFont, this.font2DHandle);
/*      */     }
/* 1942 */     AttributeValues attributeValues = getAttributeValues().clone();
/* 1943 */     boolean bool = (this.style != paramInt) ? this.style : true;
/* 1944 */     applyStyle(paramInt, attributeValues);
/* 1945 */     return new Font(attributeValues, null, bool, this.createdFont, this.font2DHandle);
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
/*      */   public Font deriveFont(Map<? extends AttributedCharacterIterator.Attribute, ?> paramMap) {
/* 1959 */     if (paramMap == null) {
/* 1960 */       return this;
/*      */     }
/* 1962 */     AttributeValues attributeValues = getAttributeValues().clone();
/* 1963 */     attributeValues.merge(paramMap, RECOGNIZED_MASK);
/*      */     
/* 1965 */     return new Font(attributeValues, this.name, this.style, this.createdFont, this.font2DHandle);
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
/*      */   public boolean canDisplay(char paramChar) {
/* 1984 */     return getFont2D().canDisplay(paramChar);
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
/*      */   public boolean canDisplay(int paramInt) {
/* 2001 */     if (!Character.isValidCodePoint(paramInt)) {
/* 2002 */       throw new IllegalArgumentException("invalid code point: " + 
/* 2003 */           Integer.toHexString(paramInt));
/*      */     }
/* 2005 */     return getFont2D().canDisplay(paramInt);
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
/*      */   public int canDisplayUpTo(String paramString) {
/* 2026 */     Font2D font2D = getFont2D();
/* 2027 */     int i = paramString.length();
/* 2028 */     for (byte b = 0; b < i; b++) {
/* 2029 */       char c = paramString.charAt(b);
/* 2030 */       if (!font2D.canDisplay(c)) {
/*      */ 
/*      */         
/* 2033 */         if (!Character.isHighSurrogate(c)) {
/* 2034 */           return b;
/*      */         }
/* 2036 */         if (!font2D.canDisplay(paramString.codePointAt(b))) {
/* 2037 */           return b;
/*      */         }
/* 2039 */         b++;
/*      */       } 
/* 2041 */     }  return -1;
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
/*      */   public int canDisplayUpTo(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/* 2064 */     Font2D font2D = getFont2D();
/* 2065 */     for (int i = paramInt1; i < paramInt2; i++) {
/* 2066 */       char c = paramArrayOfchar[i];
/* 2067 */       if (!font2D.canDisplay(c)) {
/*      */ 
/*      */         
/* 2070 */         if (!Character.isHighSurrogate(c)) {
/* 2071 */           return i;
/*      */         }
/* 2073 */         if (!font2D.canDisplay(Character.codePointAt(paramArrayOfchar, i, paramInt2))) {
/* 2074 */           return i;
/*      */         }
/* 2076 */         i++;
/*      */       } 
/* 2078 */     }  return -1;
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
/*      */   public int canDisplayUpTo(CharacterIterator paramCharacterIterator, int paramInt1, int paramInt2) {
/* 2099 */     Font2D font2D = getFont2D();
/* 2100 */     char c = paramCharacterIterator.setIndex(paramInt1);
/* 2101 */     for (int i = paramInt1; i < paramInt2; i++, c = paramCharacterIterator.next()) {
/* 2102 */       if (!font2D.canDisplay(c)) {
/*      */ 
/*      */         
/* 2105 */         if (!Character.isHighSurrogate(c)) {
/* 2106 */           return i;
/*      */         }
/* 2108 */         char c1 = paramCharacterIterator.next();
/*      */         
/* 2110 */         if (!Character.isLowSurrogate(c1)) {
/* 2111 */           return i;
/*      */         }
/* 2113 */         if (!font2D.canDisplay(Character.toCodePoint(c, c1))) {
/* 2114 */           return i;
/*      */         }
/* 2116 */         i++;
/*      */       } 
/* 2118 */     }  return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getItalicAngle() {
/* 2129 */     return getItalicAngle(null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float getItalicAngle(FontRenderContext paramFontRenderContext) {
/*      */     Object object1;
/*      */     Object object2;
/* 2142 */     if (paramFontRenderContext == null) {
/* 2143 */       object1 = RenderingHints.VALUE_TEXT_ANTIALIAS_OFF;
/* 2144 */       object2 = RenderingHints.VALUE_FRACTIONALMETRICS_OFF;
/*      */     } else {
/* 2146 */       object1 = paramFontRenderContext.getAntiAliasingHint();
/* 2147 */       object2 = paramFontRenderContext.getFractionalMetricsHint();
/*      */     } 
/* 2149 */     return getFont2D().getItalicAngle(this, identityTx, object1, object2);
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
/*      */   public boolean hasUniformLineMetrics() {
/* 2164 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private FontLineMetrics defaultLineMetrics(FontRenderContext paramFontRenderContext) {
/* 2169 */     FontLineMetrics fontLineMetrics = null;
/* 2170 */     if (this.flmref == null || (
/* 2171 */       fontLineMetrics = this.flmref.get()) == null || 
/* 2172 */       !fontLineMetrics.frc.equals(paramFontRenderContext)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2179 */       float[] arrayOfFloat1 = new float[8];
/* 2180 */       getFont2D().getFontMetrics(this, identityTx, paramFontRenderContext
/* 2181 */           .getAntiAliasingHint(), paramFontRenderContext
/* 2182 */           .getFractionalMetricsHint(), arrayOfFloat1);
/*      */       
/* 2184 */       float f1 = arrayOfFloat1[0];
/* 2185 */       float f2 = arrayOfFloat1[1];
/* 2186 */       float f3 = arrayOfFloat1[2];
/* 2187 */       float f4 = 0.0F;
/* 2188 */       if (this.values != null && this.values.getSuperscript() != 0) {
/* 2189 */         f4 = (float)getTransform().getTranslateY();
/* 2190 */         f1 -= f4;
/* 2191 */         f2 += f4;
/*      */       } 
/* 2193 */       float f5 = f1 + f2 + f3;
/*      */       
/* 2195 */       boolean bool = false;
/*      */       
/* 2197 */       float[] arrayOfFloat2 = { 0.0F, (f2 / 2.0F - f1) / 2.0F, -f1 };
/*      */       
/* 2199 */       float f6 = arrayOfFloat1[4];
/* 2200 */       float f7 = arrayOfFloat1[5];
/*      */       
/* 2202 */       float f8 = arrayOfFloat1[6];
/* 2203 */       float f9 = arrayOfFloat1[7];
/*      */       
/* 2205 */       float f10 = getItalicAngle(paramFontRenderContext);
/*      */       
/* 2207 */       if (isTransformed()) {
/* 2208 */         AffineTransform affineTransform = this.values.getCharTransform();
/* 2209 */         if (affineTransform != null) {
/* 2210 */           Point2D.Float float_ = new Point2D.Float();
/* 2211 */           float_.setLocation(0.0F, f6);
/* 2212 */           affineTransform.deltaTransform(float_, float_);
/* 2213 */           f6 = float_.y;
/* 2214 */           float_.setLocation(0.0F, f7);
/* 2215 */           affineTransform.deltaTransform(float_, float_);
/* 2216 */           f7 = float_.y;
/* 2217 */           float_.setLocation(0.0F, f8);
/* 2218 */           affineTransform.deltaTransform(float_, float_);
/* 2219 */           f8 = float_.y;
/* 2220 */           float_.setLocation(0.0F, f9);
/* 2221 */           affineTransform.deltaTransform(float_, float_);
/* 2222 */           f9 = float_.y;
/*      */         } 
/*      */       } 
/* 2225 */       f6 += f4;
/* 2226 */       f8 += f4;
/*      */       
/* 2228 */       CoreMetrics coreMetrics = new CoreMetrics(f1, f2, f3, f5, bool, arrayOfFloat2, f6, f7, f8, f9, f4, f10);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2234 */       fontLineMetrics = new FontLineMetrics(0, coreMetrics, paramFontRenderContext);
/* 2235 */       this.flmref = new SoftReference<>(fontLineMetrics);
/*      */     } 
/*      */     
/* 2238 */     return (FontLineMetrics)fontLineMetrics.clone();
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
/*      */   public LineMetrics getLineMetrics(String paramString, FontRenderContext paramFontRenderContext) {
/* 2250 */     FontLineMetrics fontLineMetrics = defaultLineMetrics(paramFontRenderContext);
/* 2251 */     fontLineMetrics.numchars = paramString.length();
/* 2252 */     return fontLineMetrics;
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
/*      */   public LineMetrics getLineMetrics(String paramString, int paramInt1, int paramInt2, FontRenderContext paramFontRenderContext) {
/* 2268 */     FontLineMetrics fontLineMetrics = defaultLineMetrics(paramFontRenderContext);
/* 2269 */     int i = paramInt2 - paramInt1;
/* 2270 */     fontLineMetrics.numchars = (i < 0) ? 0 : i;
/* 2271 */     return fontLineMetrics;
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
/*      */   public LineMetrics getLineMetrics(char[] paramArrayOfchar, int paramInt1, int paramInt2, FontRenderContext paramFontRenderContext) {
/* 2287 */     FontLineMetrics fontLineMetrics = defaultLineMetrics(paramFontRenderContext);
/* 2288 */     int i = paramInt2 - paramInt1;
/* 2289 */     fontLineMetrics.numchars = (i < 0) ? 0 : i;
/* 2290 */     return fontLineMetrics;
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
/*      */   public LineMetrics getLineMetrics(CharacterIterator paramCharacterIterator, int paramInt1, int paramInt2, FontRenderContext paramFontRenderContext) {
/* 2306 */     FontLineMetrics fontLineMetrics = defaultLineMetrics(paramFontRenderContext);
/* 2307 */     int i = paramInt2 - paramInt1;
/* 2308 */     fontLineMetrics.numchars = (i < 0) ? 0 : i;
/* 2309 */     return fontLineMetrics;
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
/*      */   public Rectangle2D getStringBounds(String paramString, FontRenderContext paramFontRenderContext) {
/* 2334 */     char[] arrayOfChar = paramString.toCharArray();
/* 2335 */     return getStringBounds(arrayOfChar, 0, arrayOfChar.length, paramFontRenderContext);
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
/*      */   public Rectangle2D getStringBounds(String paramString, int paramInt1, int paramInt2, FontRenderContext paramFontRenderContext) {
/* 2368 */     String str = paramString.substring(paramInt1, paramInt2);
/* 2369 */     return getStringBounds(str, paramFontRenderContext);
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
/*      */   public Rectangle2D getStringBounds(char[] paramArrayOfchar, int paramInt1, int paramInt2, FontRenderContext paramFontRenderContext) {
/* 2403 */     if (paramInt1 < 0) {
/* 2404 */       throw new IndexOutOfBoundsException("beginIndex: " + paramInt1);
/*      */     }
/* 2406 */     if (paramInt2 > paramArrayOfchar.length) {
/* 2407 */       throw new IndexOutOfBoundsException("limit: " + paramInt2);
/*      */     }
/* 2409 */     if (paramInt1 > paramInt2) {
/* 2410 */       throw new IndexOutOfBoundsException("range length: " + (paramInt2 - paramInt1));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2419 */     boolean bool = (this.values == null || (this.values.getKerning() == 0 && this.values.getLigatures() == 0 && this.values.getBaselineTransform() == null)) ? true : false;
/* 2420 */     if (bool) {
/* 2421 */       bool = !FontUtilities.isComplexText(paramArrayOfchar, paramInt1, paramInt2) ? true : false;
/*      */     }
/*      */     
/* 2424 */     if (bool) {
/* 2425 */       StandardGlyphVector standardGlyphVector = new StandardGlyphVector(this, paramArrayOfchar, paramInt1, paramInt2 - paramInt1, paramFontRenderContext);
/*      */       
/* 2427 */       return standardGlyphVector.getLogicalBounds();
/*      */     } 
/*      */     
/* 2430 */     String str = new String(paramArrayOfchar, paramInt1, paramInt2 - paramInt1);
/* 2431 */     TextLayout textLayout = new TextLayout(str, this, paramFontRenderContext);
/* 2432 */     return new Rectangle2D.Float(0.0F, -textLayout.getAscent(), textLayout.getAdvance(), textLayout
/* 2433 */         .getAscent() + textLayout.getDescent() + textLayout
/* 2434 */         .getLeading());
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
/*      */   public Rectangle2D getStringBounds(CharacterIterator paramCharacterIterator, int paramInt1, int paramInt2, FontRenderContext paramFontRenderContext) {
/* 2470 */     int i = paramCharacterIterator.getBeginIndex();
/* 2471 */     int j = paramCharacterIterator.getEndIndex();
/*      */     
/* 2473 */     if (paramInt1 < i) {
/* 2474 */       throw new IndexOutOfBoundsException("beginIndex: " + paramInt1);
/*      */     }
/* 2476 */     if (paramInt2 > j) {
/* 2477 */       throw new IndexOutOfBoundsException("limit: " + paramInt2);
/*      */     }
/* 2479 */     if (paramInt1 > paramInt2) {
/* 2480 */       throw new IndexOutOfBoundsException("range length: " + (paramInt2 - paramInt1));
/*      */     }
/*      */ 
/*      */     
/* 2484 */     char[] arrayOfChar = new char[paramInt2 - paramInt1];
/*      */     
/* 2486 */     paramCharacterIterator.setIndex(paramInt1);
/* 2487 */     for (byte b = 0; b < arrayOfChar.length; b++) {
/* 2488 */       arrayOfChar[b] = paramCharacterIterator.current();
/* 2489 */       paramCharacterIterator.next();
/*      */     } 
/*      */     
/* 2492 */     return getStringBounds(arrayOfChar, 0, arrayOfChar.length, paramFontRenderContext);
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
/*      */   public Rectangle2D getMaxCharBounds(FontRenderContext paramFontRenderContext) {
/* 2505 */     float[] arrayOfFloat = new float[4];
/*      */     
/* 2507 */     getFont2D().getFontMetrics(this, paramFontRenderContext, arrayOfFloat);
/*      */     
/* 2509 */     return new Rectangle2D.Float(0.0F, -arrayOfFloat[0], arrayOfFloat[3], arrayOfFloat[0] + arrayOfFloat[1] + arrayOfFloat[2]);
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
/*      */   public GlyphVector createGlyphVector(FontRenderContext paramFontRenderContext, String paramString) {
/* 2530 */     return new StandardGlyphVector(this, paramString, paramFontRenderContext);
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
/*      */   public GlyphVector createGlyphVector(FontRenderContext paramFontRenderContext, char[] paramArrayOfchar) {
/* 2549 */     return new StandardGlyphVector(this, paramArrayOfchar, paramFontRenderContext);
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
/*      */   public GlyphVector createGlyphVector(FontRenderContext paramFontRenderContext, CharacterIterator paramCharacterIterator) {
/* 2569 */     return new StandardGlyphVector(this, paramCharacterIterator, paramFontRenderContext);
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
/*      */   public GlyphVector createGlyphVector(FontRenderContext paramFontRenderContext, int[] paramArrayOfint) {
/* 2589 */     return new StandardGlyphVector(this, paramArrayOfint, paramFontRenderContext);
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
/*      */ 
/*      */ 
/*      */   
/*      */   public GlyphVector layoutGlyphVector(FontRenderContext paramFontRenderContext, char[] paramArrayOfchar, int paramInt1, int paramInt2, int paramInt3) {
/* 2640 */     GlyphLayout glyphLayout = GlyphLayout.get(null);
/* 2641 */     StandardGlyphVector standardGlyphVector = glyphLayout.layout(this, paramFontRenderContext, paramArrayOfchar, paramInt1, paramInt2 - paramInt1, paramInt3, null);
/*      */     
/* 2643 */     GlyphLayout.done(glyphLayout);
/* 2644 */     return standardGlyphVector;
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
/*      */   private static void applyTransform(AffineTransform paramAffineTransform, AttributeValues paramAttributeValues) {
/* 2673 */     if (paramAffineTransform == null) {
/* 2674 */       throw new IllegalArgumentException("transform must not be null");
/*      */     }
/* 2676 */     paramAttributeValues.setTransform(paramAffineTransform);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void applyStyle(int paramInt, AttributeValues paramAttributeValues) {
/* 2681 */     paramAttributeValues.setWeight(((paramInt & 0x1) != 0) ? 2.0F : 1.0F);
/*      */     
/* 2683 */     paramAttributeValues.setPosture(((paramInt & 0x2) != 0) ? 0.2F : 0.0F);
/*      */   }
/*      */   
/*      */   private static native void initIDs();
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/Font.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */