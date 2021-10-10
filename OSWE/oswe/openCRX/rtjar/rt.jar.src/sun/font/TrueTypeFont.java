/*      */ package sun.font;
/*      */ 
/*      */ import java.awt.FontFormatException;
/*      */ import java.awt.GraphicsEnvironment;
/*      */ import java.awt.geom.Point2D;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.IOException;
/*      */ import java.io.RandomAccessFile;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.CharBuffer;
/*      */ import java.nio.IntBuffer;
/*      */ import java.nio.ShortBuffer;
/*      */ import java.nio.channels.ClosedChannelException;
/*      */ import java.nio.channels.FileChannel;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import sun.awt.SunToolkit;
/*      */ import sun.java2d.Disposer;
/*      */ import sun.java2d.DisposerRecord;
/*      */ import sun.security.action.GetPropertyAction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class TrueTypeFont
/*      */   extends FileFont
/*      */ {
/*      */   public static final int cmapTag = 1668112752;
/*      */   public static final int glyfTag = 1735162214;
/*      */   public static final int headTag = 1751474532;
/*      */   public static final int hheaTag = 1751672161;
/*      */   public static final int hmtxTag = 1752003704;
/*      */   public static final int locaTag = 1819239265;
/*      */   public static final int maxpTag = 1835104368;
/*      */   public static final int nameTag = 1851878757;
/*      */   public static final int postTag = 1886352244;
/*      */   public static final int os_2Tag = 1330851634;
/*      */   public static final int GDEFTag = 1195656518;
/*      */   public static final int GPOSTag = 1196445523;
/*      */   public static final int GSUBTag = 1196643650;
/*      */   public static final int mortTag = 1836020340;
/*      */   public static final int morxTag = 1836020344;
/*      */   public static final int fdscTag = 1717859171;
/*      */   public static final int fvarTag = 1719034226;
/*      */   public static final int featTag = 1717920116;
/*      */   public static final int EBLCTag = 1161972803;
/*      */   public static final int gaspTag = 1734439792;
/*      */   public static final int ttcfTag = 1953784678;
/*      */   public static final int v1ttTag = 65536;
/*      */   public static final int trueTag = 1953658213;
/*      */   public static final int ottoTag = 1330926671;
/*      */   public static final int MS_PLATFORM_ID = 3;
/*      */   public static final short ENGLISH_LOCALE_ID = 1033;
/*      */   public static final int FAMILY_NAME_ID = 1;
/*      */   public static final int FULL_NAME_ID = 4;
/*      */   public static final int POSTSCRIPT_NAME_ID = 6;
/*      */   private static final short US_LCID = 1033;
/*      */   private static Map<String, Short> lcidMap;
/*      */   
/*      */   static class DirectoryEntry
/*      */   {
/*      */     int tag;
/*      */     int offset;
/*      */     int length;
/*      */   }
/*      */   
/*      */   private static class TTDisposerRecord
/*      */     implements DisposerRecord
/*      */   {
/*  126 */     FileChannel channel = null;
/*      */     
/*      */     public synchronized void dispose() {
/*      */       
/*  130 */       try { if (this.channel != null) {
/*  131 */           this.channel.close();
/*      */         } }
/*  133 */       catch (IOException iOException) {  }
/*      */       finally
/*  135 */       { this.channel = null; }
/*      */     
/*      */     }
/*      */     
/*      */     private TTDisposerRecord() {} }
/*  140 */   TTDisposerRecord disposerRecord = new TTDisposerRecord();
/*      */ 
/*      */   
/*  143 */   int fontIndex = 0;
/*      */   int directoryOffset; int numTables; DirectoryEntry[] tableDirectory; private boolean supportsJA; private boolean supportsCJK; private Locale nameLocale; private String localeFamilyName; private String localeFullName; private static final int TTCHEADERSIZE = 12; private static final int DIRECTORYHEADERSIZE = 12; private static final int DIRECTORYENTRYSIZE = 16; protected boolean checkUseNatives() { if (this.checkedNatives) return this.useNatives;  if (!FontUtilities.isSolaris || this.useJavaRasterizer || FontUtilities.useT2K || this.nativeNames == null || getDirectoryEntry(1161972803) != null || GraphicsEnvironment.isHeadless()) { this.checkedNatives = true; return false; }  if (this.nativeNames instanceof String) { String str = (String)this.nativeNames; if (str.indexOf("8859") > 0) { this.checkedNatives = true; return false; }  if (NativeFont.hasExternalBitmaps(str)) { this.nativeFonts = new NativeFont[1]; try { this.nativeFonts[0] = new NativeFont(str, true); this.useNatives = true; } catch (FontFormatException fontFormatException) { this.nativeFonts = null; }  }  } else if (this.nativeNames instanceof String[]) { String[] arrayOfString = (String[])this.nativeNames; int i = arrayOfString.length; boolean bool = false; byte b; for (b = 0; b < i; b++) { if (arrayOfString[b].indexOf("8859") > 0) { this.checkedNatives = true; return false; }  if (NativeFont.hasExternalBitmaps(arrayOfString[b]))
/*      */           bool = true;  }  if (!bool) { this.checkedNatives = true; return false; }  this.useNatives = true; this.nativeFonts = new NativeFont[i]; for (b = 0; b < i; b++) { try { this.nativeFonts[b] = new NativeFont(arrayOfString[b], true); } catch (FontFormatException fontFormatException) { this.useNatives = false; this.nativeFonts = null; }  }  }  if (this.useNatives)
/*  146 */       this.glyphToCharMap = new char[getMapper().getNumGlyphs()];  this.checkedNatives = true; return this.useNatives; } int directoryCount = 1;
/*      */   private synchronized FileChannel open() throws FontFormatException { return open(true); }
/*      */   private synchronized FileChannel open(boolean paramBoolean) throws FontFormatException { if (this.disposerRecord.channel == null) { if (FontUtilities.isLogging())
/*      */         FontUtilities.getLogger().info("open TTF: " + this.platName);  try { RandomAccessFile randomAccessFile = AccessController.<RandomAccessFile>doPrivileged(new PrivilegedAction<RandomAccessFile>() {
/*      */               public Object run() { try { return new RandomAccessFile(TrueTypeFont.this.platName, "r"); } catch (FileNotFoundException fileNotFoundException) { return null; }  }
/*      */             }); this.disposerRecord.channel = randomAccessFile.getChannel(); this.fileSize = (int)this.disposerRecord.channel.size(); if (paramBoolean) { FontManager fontManager = FontManagerFactory.getInstance(); if (fontManager instanceof SunFontManager)
/*      */             ((SunFontManager)fontManager).addToPool(this);  }
/*      */          }
/*      */       catch (NullPointerException nullPointerException) { close(); throw new FontFormatException(nullPointerException.toString()); }
/*      */       catch (ClosedChannelException closedChannelException) { Thread.interrupted(); close(); open(); }
/*      */       catch (IOException iOException) { close(); throw new FontFormatException(iOException.toString()); }
/*      */        }
/*      */      return this.disposerRecord.channel; }
/*      */   protected synchronized void close() { this.disposerRecord.dispose(); } int readBlock(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2) { int i = 0; try { synchronized (this) { if (this.disposerRecord.channel == null)
/*      */           open();  if (paramInt1 + paramInt2 > this.fileSize) { if (paramInt1 >= this.fileSize) { if (FontUtilities.isLogging()) { String str = "Read offset is " + paramInt1 + " file size is " + this.fileSize + " file is " + this.platName; FontUtilities.getLogger().severe(str); }
/*      */              return -1; }
/*      */            paramInt2 = this.fileSize - paramInt1; }
/*      */          paramByteBuffer.clear(); this.disposerRecord.channel.position(paramInt1); while (i < paramInt2) { int j = this.disposerRecord.channel.read(paramByteBuffer); if (j == -1) { String str = "Unexpected EOF " + this; int k = (int)this.disposerRecord.channel.size(); if (k != this.fileSize)
/*      */               str = str + " File size was " + this.fileSize + " and now is " + k;  if (FontUtilities.isLogging())
/*      */               FontUtilities.getLogger().severe(str);  if (i > paramInt2 / 2 || i > 16384) { paramByteBuffer.flip(); if (FontUtilities.isLogging()) { str = "Returning " + i + " bytes instead of " + paramInt2; FontUtilities.getLogger().severe(str); }
/*      */                }
/*      */             else { i = -1; }
/*      */              throw new IOException(str); }
/*      */            i += j; }
/*      */          paramByteBuffer.flip(); if (i > paramInt2)
/*      */           i = paramInt2;  }
/*      */        }
/*      */     catch (FontFormatException fontFormatException) { if (FontUtilities.isLogging())
/*      */         FontUtilities.getLogger().severe("While reading " + this.platName, fontFormatException);  i = -1; deregisterFontAndClearStrikeCache(); }
/*      */     catch (ClosedChannelException closedChannelException) { Thread.interrupted(); close(); return readBlock(paramByteBuffer, paramInt1, paramInt2); }
/*      */     catch (IOException iOException) { if (FontUtilities.isLogging())
/*      */         FontUtilities.getLogger().severe("While reading " + this.platName, iOException);  if (i == 0) { i = -1; deregisterFontAndClearStrikeCache(); }
/*      */        }
/*  179 */      return i; } public TrueTypeFont(String paramString, Object paramObject, int paramInt, boolean paramBoolean) throws FontFormatException { this(paramString, paramObject, paramInt, paramBoolean, true); } ByteBuffer readBlock(int paramInt1, int paramInt2) { ByteBuffer byteBuffer = ByteBuffer.allocate(paramInt2); try { synchronized (this) { if (this.disposerRecord.channel == null) open();  if (paramInt1 + paramInt2 > this.fileSize) { if (paramInt1 > this.fileSize) return null;  byteBuffer = ByteBuffer.allocate(this.fileSize - paramInt1); }  this.disposerRecord.channel.position(paramInt1); this.disposerRecord.channel.read(byteBuffer); byteBuffer.flip(); }  } catch (FontFormatException fontFormatException) { return null; } catch (ClosedChannelException closedChannelException) { Thread.interrupted(); close(); readBlock(byteBuffer, paramInt1, paramInt2); } catch (IOException iOException) { return null; }  return byteBuffer; } byte[] readBytes(int paramInt1, int paramInt2) { ByteBuffer byteBuffer = readBlock(paramInt1, paramInt2); if (byteBuffer.hasArray()) return byteBuffer.array();  byte[] arrayOfByte = new byte[byteBuffer.limit()]; byteBuffer.get(arrayOfByte); return arrayOfByte; }
/*      */   private void verify(boolean paramBoolean) throws FontFormatException { open(paramBoolean); }
/*      */   protected void init(int paramInt) throws FontFormatException { int i = 0; ByteBuffer byteBuffer1 = readBlock(0, 12); try { switch (byteBuffer1.getInt()) { case 1953784678: byteBuffer1.getInt(); this.directoryCount = byteBuffer1.getInt(); if (paramInt >= this.directoryCount) throw new FontFormatException("Bad collection index");  this.fontIndex = paramInt; byteBuffer1 = readBlock(12 + 4 * paramInt, 4); i = byteBuffer1.getInt(); break;case 65536: case 1330926671: case 1953658213: break;default: throw new FontFormatException("Unsupported sfnt " + getPublicFileName()); }  byteBuffer1 = readBlock(i + 4, 2); this.numTables = byteBuffer1.getShort(); this.directoryOffset = i + 12; ByteBuffer byteBuffer = readBlock(this.directoryOffset, this.numTables * 16); IntBuffer intBuffer = byteBuffer.asIntBuffer(); this.tableDirectory = new DirectoryEntry[this.numTables]; for (byte b = 0; b < this.numTables; b++) { DirectoryEntry directoryEntry = new DirectoryEntry(); directoryEntry.tag = intBuffer.get(); intBuffer.get(); directoryEntry.offset = intBuffer.get(); directoryEntry.length = intBuffer.get(); if (directoryEntry.offset + directoryEntry.length > this.fileSize)
/*      */           throw new FontFormatException("bad table, tag=" + directoryEntry.tag);  }  if (getDirectoryEntry(1751474532) == null)
/*      */         throw new FontFormatException("missing head table");  if (getDirectoryEntry(1835104368) == null)
/*      */         throw new FontFormatException("missing maxp table");  if (getDirectoryEntry(1752003704) != null && getDirectoryEntry(1751672161) == null)
/*      */         throw new FontFormatException("missing hhea table");  initNames(); } catch (Exception exception) { if (FontUtilities.isLogging())
/*      */         FontUtilities.getLogger().severe(exception.toString());  if (exception instanceof FontFormatException)
/*      */         throw (FontFormatException)exception;  throw new FontFormatException(exception.toString()); }  if (this.familyName == null || this.fullName == null)
/*      */       throw new FontFormatException("Font name not found");  ByteBuffer byteBuffer2 = getTableBuffer(1330851634); setStyle(byteBuffer2); setCJKSupport(byteBuffer2); }
/*      */   static final String[] encoding_mapping = new String[] { 
/*      */       "cp1252", "cp1250", "cp1251", "cp1253", "cp1254", "cp1255", "cp1256", "cp1257", "", "", 
/*      */       "", "", "", "", "", "", "ms874", "ms932", "gbk", "ms949", 
/*      */       "ms950", "ms1361", "", "", "", "", "", "", "", "", 
/*      */       "", "" };
/*  194 */   public TrueTypeFont(String paramString, Object paramObject, int paramInt, boolean paramBoolean1, boolean paramBoolean2) throws FontFormatException { super(paramString, paramObject);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  980 */     this.fontWidth = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  986 */     this.fontWeight = 0; this.useJavaRasterizer = paramBoolean1; this.fontRank = 3; try { verify(paramBoolean2); init(paramInt); if (!paramBoolean2) close();  } catch (Throwable throwable) { close(); if (throwable instanceof FontFormatException) throw (FontFormatException)throwable;  throw new FontFormatException("Unexpected runtime exception."); }  Disposer.addObjectRecord(this, this.disposerRecord); } private static final String[][] languages = new String[][] { { "en", "ca", "da", "de", "es", "fi", "fr", "is", "it", "nl", "no", "pt", "sq", "sv" }, { "cs", "cz", "et", "hr", "hu", "nr", "pl", "ro", "sk", "sl", "sq", "sr" }, { "bg", "mk", "ru", "sh", "uk" }, { "el" }, { "tr" }, { "he" }, { "ar" }, { "et", "lt", "lv" }, { "th" }, { "ja" }, 
/*      */       { "zh", "zh_CN" }, { "ko" }, { "zh_HK", "zh_TW" }, { "ko" } }; private static final String[] codePages = new String[] { 
/*      */       "cp1252", "cp1250", "cp1251", "cp1253", "cp1254", "cp1255", "cp1256", "cp1257", "ms874", "ms932", 
/*  989 */       "gbk", "ms949", "ms950", "ms1361" }; private static String defaultCodePage = null; public static final int reserved_bits1 = -2147483648; public static final int reserved_bits2 = 65535; private int fontWidth; private int fontWeight; private static final int fsSelectionItalicBit = 1; private static final int fsSelectionBoldBit = 32; public int getWeight() { return (this.fontWeight > 0) ? this.fontWeight : super.getWeight(); } private static final int fsSelectionRegularBit = 64; private float stSize; private float stPos; private float ulSize; private float ulPos; private char[] gaspTable; static String getCodePage() { if (defaultCodePage != null)
/*      */       return defaultCodePage;  if (FontUtilities.isWindows) { defaultCodePage = AccessController.<String>doPrivileged(new GetPropertyAction("file.encoding")); } else { if (languages.length != codePages.length)
/*      */         throw new InternalError("wrong code pages array length");  Locale locale = SunToolkit.getStartupLocale(); String str = locale.getLanguage(); if (str != null) { if (str.equals("zh")) { String str1 = locale.getCountry(); if (str1 != null)
/*      */             str = str + "_" + str1;  }  for (byte b = 0; b < languages.length; b++) { for (byte b1 = 0; b1 < (languages[b]).length; b1++) { if (str.equals(languages[b][b1])) { defaultCodePage = codePages[b]; return defaultCodePage; }  }  }  }
/*      */        }
/*      */      if (defaultCodePage == null)
/*      */       defaultCodePage = "";  return defaultCodePage; }
/*      */   boolean supportsEncoding(String paramString) { if (paramString == null)
/*      */       paramString = getCodePage();  if ("".equals(paramString))
/*      */       return false;  paramString = paramString.toLowerCase(); if (paramString.equals("gb18030")) { paramString = "gbk"; }
/*      */     else if (paramString.equals("ms950_hkscs")) { paramString = "ms950"; }
/*      */      ByteBuffer byteBuffer = getTableBuffer(1330851634); if (byteBuffer == null || byteBuffer.capacity() < 86)
/*      */       return false;  int i = byteBuffer.getInt(78); int j = byteBuffer.getInt(82); for (byte b = 0; b < encoding_mapping.length; b++) { if (encoding_mapping[b].equals(paramString) && (1 << b & i) != 0)
/*      */         return true;  }
/*      */      return false; }
/*      */   private void setCJKSupport(ByteBuffer paramByteBuffer) { if (paramByteBuffer == null || paramByteBuffer.capacity() < 50)
/*      */       return;  int i = paramByteBuffer.getInt(46); this.supportsCJK = ((i & 0x29BF0000) != 0); this.supportsJA = ((i & 0x60000) != 0); }
/*      */   boolean supportsJA() { return this.supportsJA; }
/* 1007 */   private void setStyle(ByteBuffer paramByteBuffer) { if (paramByteBuffer == null) {
/*      */       return;
/*      */     }
/* 1010 */     if (paramByteBuffer.capacity() >= 8) {
/* 1011 */       this.fontWeight = paramByteBuffer.getChar(4) & Character.MAX_VALUE;
/* 1012 */       this.fontWidth = paramByteBuffer.getChar(6) & Character.MAX_VALUE;
/*      */     } 
/*      */     
/* 1015 */     if (paramByteBuffer.capacity() < 64) {
/* 1016 */       super.setStyle();
/*      */       return;
/*      */     } 
/* 1019 */     int i = paramByteBuffer.getChar(62) & Character.MAX_VALUE;
/* 1020 */     int j = i & 0x1;
/* 1021 */     int k = i & 0x20;
/* 1022 */     int m = i & 0x40;
/*      */ 
/*      */ 
/*      */     
/* 1026 */     if (m != 0 && (j | k) != 0) {
/*      */       
/* 1028 */       super.setStyle(); return;
/*      */     } 
/* 1030 */     if ((m | j | k) == 0) {
/*      */       
/* 1032 */       super.setStyle();
/*      */       return;
/*      */     } 
/* 1035 */     switch (k | j)
/*      */     { case 1:
/* 1037 */         this.style = 2;
/*      */         break;
/*      */       case 32:
/* 1040 */         if (FontUtilities.isSolaris && this.platName.endsWith("HG-GothicB.ttf")) {
/*      */ 
/*      */ 
/*      */           
/* 1044 */           this.style = 0; break;
/*      */         } 
/* 1046 */         this.style = 1;
/*      */         break;
/*      */       
/*      */       case 33:
/* 1050 */         this.style = 3; break; }  } ByteBuffer getTableBuffer(int paramInt) { DirectoryEntry directoryEntry = null; int i; for (i = 0; i < this.numTables; i++) { if ((this.tableDirectory[i]).tag == paramInt) { directoryEntry = this.tableDirectory[i]; break; }  }  if (directoryEntry == null || directoryEntry.length == 0 || directoryEntry.offset + directoryEntry.length > this.fileSize) return null;  i = 0; ByteBuffer byteBuffer = ByteBuffer.allocate(directoryEntry.length); synchronized (this) { try { if (this.disposerRecord.channel == null) open();  this.disposerRecord.channel.position(directoryEntry.offset); i = this.disposerRecord.channel.read(byteBuffer); byteBuffer.flip(); } catch (ClosedChannelException closedChannelException) { Thread.interrupted(); close(); return getTableBuffer(paramInt); } catch (IOException iOException) { return null; } catch (FontFormatException fontFormatException) { return null; }  if (i < directoryEntry.length) return null;  return byteBuffer; }  } protected long getLayoutTableCache() { try { return getScaler().getLayoutTableCache(); } catch (FontScalerException fontScalerException) { return 0L; }  } protected byte[] getTableBytes(int paramInt) { ByteBuffer byteBuffer = getTableBuffer(paramInt); if (byteBuffer == null) return null;  if (byteBuffer.hasArray()) try { return byteBuffer.array(); } catch (Exception exception) {}  byte[] arrayOfByte = new byte[getTableSize(paramInt)]; byteBuffer.get(arrayOfByte); return arrayOfByte; } int getTableSize(int paramInt) { for (byte b = 0; b < this.numTables; b++) { if ((this.tableDirectory[b]).tag == paramInt) return (this.tableDirectory[b]).length;  }  return 0; }
/*      */   int getTableOffset(int paramInt) { for (byte b = 0; b < this.numTables; b++) { if ((this.tableDirectory[b]).tag == paramInt) return (this.tableDirectory[b]).offset;  }  return 0; }
/*      */   DirectoryEntry getDirectoryEntry(int paramInt) { for (byte b = 0; b < this.numTables; b++) { if ((this.tableDirectory[b]).tag == paramInt) return this.tableDirectory[b];  }  return null; }
/*      */   boolean useEmbeddedBitmapsForSize(int paramInt) { if (!this.supportsCJK) return false;  if (getDirectoryEntry(1161972803) == null) return false;  ByteBuffer byteBuffer = getTableBuffer(1161972803); int i = byteBuffer.getInt(4); for (byte b = 0; b < i; b++) { int j = byteBuffer.get(8 + b * 48 + 45) & 0xFF; if (j == paramInt) return true;  }  return false; }
/*      */   public String getFullName() { return this.fullName; }
/*      */   protected void setStyle() { setStyle(getTableBuffer(1330851634)); }
/*      */   public int getWidth() { return (this.fontWidth > 0) ? this.fontWidth : super.getWidth(); }
/* 1057 */   private void setStrikethroughMetrics(ByteBuffer paramByteBuffer, int paramInt) { if (paramByteBuffer == null || paramByteBuffer.capacity() < 30 || paramInt < 0) {
/* 1058 */       this.stSize = 0.05F;
/* 1059 */       this.stPos = -0.4F;
/*      */       return;
/*      */     } 
/* 1062 */     ShortBuffer shortBuffer = paramByteBuffer.asShortBuffer();
/* 1063 */     this.stSize = shortBuffer.get(13) / paramInt;
/* 1064 */     this.stPos = -shortBuffer.get(14) / paramInt; }
/*      */ 
/*      */   
/*      */   private void setUnderlineMetrics(ByteBuffer paramByteBuffer, int paramInt) {
/* 1068 */     if (paramByteBuffer == null || paramByteBuffer.capacity() < 12 || paramInt < 0) {
/* 1069 */       this.ulSize = 0.05F;
/* 1070 */       this.ulPos = 0.1F;
/*      */       return;
/*      */     } 
/* 1073 */     ShortBuffer shortBuffer = paramByteBuffer.asShortBuffer();
/* 1074 */     this.ulSize = shortBuffer.get(5) / paramInt;
/* 1075 */     this.ulPos = -shortBuffer.get(4) / paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void getStyleMetrics(float paramFloat, float[] paramArrayOffloat, int paramInt) {
/* 1081 */     if (this.ulSize == 0.0F && this.ulPos == 0.0F) {
/*      */       
/* 1083 */       ByteBuffer byteBuffer1 = getTableBuffer(1751474532);
/* 1084 */       int i = -1;
/* 1085 */       if (byteBuffer1 != null && byteBuffer1.capacity() >= 18) {
/* 1086 */         ShortBuffer shortBuffer = byteBuffer1.asShortBuffer();
/* 1087 */         i = shortBuffer.get(9) & 0xFFFF;
/* 1088 */         if (i < 16 || i > 16384) {
/* 1089 */           i = 2048;
/*      */         }
/*      */       } 
/*      */       
/* 1093 */       ByteBuffer byteBuffer2 = getTableBuffer(1330851634);
/* 1094 */       setStrikethroughMetrics(byteBuffer2, i);
/*      */       
/* 1096 */       ByteBuffer byteBuffer3 = getTableBuffer(1886352244);
/* 1097 */       setUnderlineMetrics(byteBuffer3, i);
/*      */     } 
/*      */     
/* 1100 */     paramArrayOffloat[paramInt] = this.stPos * paramFloat;
/* 1101 */     paramArrayOffloat[paramInt + 1] = this.stSize * paramFloat;
/*      */     
/* 1103 */     paramArrayOffloat[paramInt + 2] = this.ulPos * paramFloat;
/* 1104 */     paramArrayOffloat[paramInt + 3] = this.ulSize * paramFloat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String makeString(byte[] paramArrayOfbyte, int paramInt, short paramShort) {
/*      */     String str;
/* 1115 */     if (paramShort >= 2 && paramShort <= 6) {
/* 1116 */       byte[] arrayOfByte = paramArrayOfbyte;
/* 1117 */       int i = paramInt;
/* 1118 */       paramArrayOfbyte = new byte[i];
/* 1119 */       paramInt = 0;
/* 1120 */       for (byte b = 0; b < i; b++) {
/* 1121 */         if (arrayOfByte[b] != 0) {
/* 1122 */           paramArrayOfbyte[paramInt++] = arrayOfByte[b];
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1128 */     switch (paramShort) { case 1:
/* 1129 */         str = "UTF-16"; break;
/* 1130 */       case 0: str = "UTF-16"; break;
/* 1131 */       case 2: str = "SJIS"; break;
/* 1132 */       case 3: str = "GBK"; break;
/* 1133 */       case 4: str = "MS950"; break;
/* 1134 */       case 5: str = "EUC_KR"; break;
/* 1135 */       case 6: str = "Johab"; break;
/* 1136 */       default: str = "UTF-16";
/*      */         break; }
/*      */     
/*      */     try {
/* 1140 */       return new String(paramArrayOfbyte, 0, paramInt, str);
/* 1141 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 1142 */       if (FontUtilities.isLogging()) {
/* 1143 */         FontUtilities.getLogger().warning(unsupportedEncodingException + " EncodingID=" + paramShort);
/*      */       }
/* 1145 */       return new String(paramArrayOfbyte, 0, paramInt);
/* 1146 */     } catch (Throwable throwable) {
/* 1147 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void initNames() {
/* 1153 */     byte[] arrayOfByte = new byte[256];
/* 1154 */     ByteBuffer byteBuffer = getTableBuffer(1851878757);
/*      */     
/* 1156 */     if (byteBuffer != null) {
/* 1157 */       ShortBuffer shortBuffer = byteBuffer.asShortBuffer();
/* 1158 */       shortBuffer.get();
/* 1159 */       short s1 = shortBuffer.get();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1165 */       int i = shortBuffer.get() & 0xFFFF;
/*      */       
/* 1167 */       this.nameLocale = SunToolkit.getStartupLocale();
/* 1168 */       short s2 = getLCIDFromLocale(this.nameLocale);
/*      */       
/* 1170 */       for (byte b = 0; b < s1; b++) {
/* 1171 */         short s = shortBuffer.get();
/* 1172 */         if (s != 3) {
/* 1173 */           shortBuffer.position(shortBuffer.position() + 5);
/*      */         } else {
/*      */           
/* 1176 */           short s3 = shortBuffer.get();
/* 1177 */           short s4 = shortBuffer.get();
/* 1178 */           short s5 = shortBuffer.get();
/* 1179 */           int j = shortBuffer.get() & 0xFFFF;
/* 1180 */           int k = (shortBuffer.get() & 0xFFFF) + i;
/* 1181 */           String str = null;
/* 1182 */           switch (s5) {
/*      */ 
/*      */             
/*      */             case 1:
/* 1186 */               if (this.familyName == null || s4 == 1033 || s4 == s2) {
/*      */ 
/*      */                 
/* 1189 */                 byteBuffer.position(k);
/* 1190 */                 byteBuffer.get(arrayOfByte, 0, j);
/* 1191 */                 str = makeString(arrayOfByte, j, s3);
/*      */                 
/* 1193 */                 if (this.familyName == null || s4 == 1033) {
/* 1194 */                   this.familyName = str;
/*      */                 }
/* 1196 */                 if (s4 == s2) {
/* 1197 */                   this.localeFamilyName = str;
/*      */                 }
/*      */               } 
/*      */               break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             case 4:
/* 1216 */               if (this.fullName == null || s4 == 1033 || s4 == s2) {
/*      */ 
/*      */                 
/* 1219 */                 byteBuffer.position(k);
/* 1220 */                 byteBuffer.get(arrayOfByte, 0, j);
/* 1221 */                 str = makeString(arrayOfByte, j, s3);
/*      */                 
/* 1223 */                 if (this.fullName == null || s4 == 1033) {
/* 1224 */                   this.fullName = str;
/*      */                 }
/* 1226 */                 if (s4 == s2)
/* 1227 */                   this.localeFullName = str; 
/*      */               } 
/*      */               break;
/*      */           } 
/*      */         } 
/*      */       } 
/* 1233 */       if (this.localeFamilyName == null) {
/* 1234 */         this.localeFamilyName = this.familyName;
/*      */       }
/* 1236 */       if (this.localeFullName == null) {
/* 1237 */         this.localeFullName = this.fullName;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String lookupName(short paramShort, int paramInt) {
/* 1248 */     String str = null;
/* 1249 */     byte[] arrayOfByte = new byte[1024];
/*      */     
/* 1251 */     ByteBuffer byteBuffer = getTableBuffer(1851878757);
/* 1252 */     if (byteBuffer != null) {
/* 1253 */       ShortBuffer shortBuffer = byteBuffer.asShortBuffer();
/* 1254 */       shortBuffer.get();
/* 1255 */       short s = shortBuffer.get();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1262 */       int i = shortBuffer.get() & 0xFFFF;
/*      */       
/* 1264 */       for (byte b = 0; b < s; b++) {
/* 1265 */         short s1 = shortBuffer.get();
/* 1266 */         if (s1 != 3) {
/* 1267 */           shortBuffer.position(shortBuffer.position() + 5);
/*      */         } else {
/*      */           
/* 1270 */           short s2 = shortBuffer.get();
/* 1271 */           short s3 = shortBuffer.get();
/* 1272 */           short s4 = shortBuffer.get();
/* 1273 */           int j = shortBuffer.get() & 0xFFFF;
/* 1274 */           int k = (shortBuffer.get() & 0xFFFF) + i;
/* 1275 */           if (s4 == paramInt && ((str == null && s3 == 1033) || s3 == paramShort)) {
/*      */ 
/*      */             
/* 1278 */             byteBuffer.position(k);
/* 1279 */             byteBuffer.get(arrayOfByte, 0, j);
/* 1280 */             str = makeString(arrayOfByte, j, s2);
/* 1281 */             if (s3 == paramShort)
/* 1282 */               return str; 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/* 1287 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getFontCount() {
/* 1294 */     return this.directoryCount;
/*      */   }
/*      */   
/*      */   protected synchronized FontScaler getScaler() {
/* 1298 */     if (this.scaler == null) {
/* 1299 */       this.scaler = FontScaler.getScaler(this, this.fontIndex, this.supportsCJK, this.fileSize);
/*      */     }
/*      */     
/* 1302 */     return this.scaler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPostscriptName() {
/* 1311 */     String str = lookupName((short)1033, 6);
/* 1312 */     if (str == null) {
/* 1313 */       return this.fullName;
/*      */     }
/* 1315 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getFontName(Locale paramLocale) {
/* 1321 */     if (paramLocale == null)
/* 1322 */       return this.fullName; 
/* 1323 */     if (paramLocale.equals(this.nameLocale) && this.localeFullName != null) {
/* 1324 */       return this.localeFullName;
/*      */     }
/* 1326 */     short s = getLCIDFromLocale(paramLocale);
/* 1327 */     String str = lookupName(s, 4);
/* 1328 */     if (str == null) {
/* 1329 */       return this.fullName;
/*      */     }
/* 1331 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void addLCIDMapEntry(Map<String, Short> paramMap, String paramString, short paramShort) {
/* 1341 */     paramMap.put(paramString, Short.valueOf(paramShort));
/*      */   }
/*      */   
/*      */   private static synchronized void createLCIDMap() {
/* 1345 */     if (lcidMap != null) {
/*      */       return;
/*      */     }
/*      */     
/* 1349 */     HashMap<Object, Object> hashMap = new HashMap<>(200);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1377 */     addLCIDMapEntry((Map)hashMap, "ar", (short)1025);
/* 1378 */     addLCIDMapEntry((Map)hashMap, "bg", (short)1026);
/* 1379 */     addLCIDMapEntry((Map)hashMap, "ca", (short)1027);
/* 1380 */     addLCIDMapEntry((Map)hashMap, "zh", (short)1028);
/* 1381 */     addLCIDMapEntry((Map)hashMap, "cs", (short)1029);
/* 1382 */     addLCIDMapEntry((Map)hashMap, "da", (short)1030);
/* 1383 */     addLCIDMapEntry((Map)hashMap, "de", (short)1031);
/* 1384 */     addLCIDMapEntry((Map)hashMap, "el", (short)1032);
/* 1385 */     addLCIDMapEntry((Map)hashMap, "es", (short)1034);
/* 1386 */     addLCIDMapEntry((Map)hashMap, "fi", (short)1035);
/* 1387 */     addLCIDMapEntry((Map)hashMap, "fr", (short)1036);
/* 1388 */     addLCIDMapEntry((Map)hashMap, "iw", (short)1037);
/* 1389 */     addLCIDMapEntry((Map)hashMap, "hu", (short)1038);
/* 1390 */     addLCIDMapEntry((Map)hashMap, "is", (short)1039);
/* 1391 */     addLCIDMapEntry((Map)hashMap, "it", (short)1040);
/* 1392 */     addLCIDMapEntry((Map)hashMap, "ja", (short)1041);
/* 1393 */     addLCIDMapEntry((Map)hashMap, "ko", (short)1042);
/* 1394 */     addLCIDMapEntry((Map)hashMap, "nl", (short)1043);
/* 1395 */     addLCIDMapEntry((Map)hashMap, "no", (short)1044);
/* 1396 */     addLCIDMapEntry((Map)hashMap, "pl", (short)1045);
/* 1397 */     addLCIDMapEntry((Map)hashMap, "pt", (short)1046);
/* 1398 */     addLCIDMapEntry((Map)hashMap, "rm", (short)1047);
/* 1399 */     addLCIDMapEntry((Map)hashMap, "ro", (short)1048);
/* 1400 */     addLCIDMapEntry((Map)hashMap, "ru", (short)1049);
/* 1401 */     addLCIDMapEntry((Map)hashMap, "hr", (short)1050);
/* 1402 */     addLCIDMapEntry((Map)hashMap, "sk", (short)1051);
/* 1403 */     addLCIDMapEntry((Map)hashMap, "sq", (short)1052);
/* 1404 */     addLCIDMapEntry((Map)hashMap, "sv", (short)1053);
/* 1405 */     addLCIDMapEntry((Map)hashMap, "th", (short)1054);
/* 1406 */     addLCIDMapEntry((Map)hashMap, "tr", (short)1055);
/* 1407 */     addLCIDMapEntry((Map)hashMap, "ur", (short)1056);
/* 1408 */     addLCIDMapEntry((Map)hashMap, "in", (short)1057);
/* 1409 */     addLCIDMapEntry((Map)hashMap, "uk", (short)1058);
/* 1410 */     addLCIDMapEntry((Map)hashMap, "be", (short)1059);
/* 1411 */     addLCIDMapEntry((Map)hashMap, "sl", (short)1060);
/* 1412 */     addLCIDMapEntry((Map)hashMap, "et", (short)1061);
/* 1413 */     addLCIDMapEntry((Map)hashMap, "lv", (short)1062);
/* 1414 */     addLCIDMapEntry((Map)hashMap, "lt", (short)1063);
/* 1415 */     addLCIDMapEntry((Map)hashMap, "fa", (short)1065);
/* 1416 */     addLCIDMapEntry((Map)hashMap, "vi", (short)1066);
/* 1417 */     addLCIDMapEntry((Map)hashMap, "hy", (short)1067);
/* 1418 */     addLCIDMapEntry((Map)hashMap, "eu", (short)1069);
/* 1419 */     addLCIDMapEntry((Map)hashMap, "mk", (short)1071);
/* 1420 */     addLCIDMapEntry((Map)hashMap, "tn", (short)1074);
/* 1421 */     addLCIDMapEntry((Map)hashMap, "xh", (short)1076);
/* 1422 */     addLCIDMapEntry((Map)hashMap, "zu", (short)1077);
/* 1423 */     addLCIDMapEntry((Map)hashMap, "af", (short)1078);
/* 1424 */     addLCIDMapEntry((Map)hashMap, "ka", (short)1079);
/* 1425 */     addLCIDMapEntry((Map)hashMap, "fo", (short)1080);
/* 1426 */     addLCIDMapEntry((Map)hashMap, "hi", (short)1081);
/* 1427 */     addLCIDMapEntry((Map)hashMap, "mt", (short)1082);
/* 1428 */     addLCIDMapEntry((Map)hashMap, "se", (short)1083);
/* 1429 */     addLCIDMapEntry((Map)hashMap, "gd", (short)1084);
/* 1430 */     addLCIDMapEntry((Map)hashMap, "ms", (short)1086);
/* 1431 */     addLCIDMapEntry((Map)hashMap, "kk", (short)1087);
/* 1432 */     addLCIDMapEntry((Map)hashMap, "ky", (short)1088);
/* 1433 */     addLCIDMapEntry((Map)hashMap, "sw", (short)1089);
/* 1434 */     addLCIDMapEntry((Map)hashMap, "tt", (short)1092);
/* 1435 */     addLCIDMapEntry((Map)hashMap, "bn", (short)1093);
/* 1436 */     addLCIDMapEntry((Map)hashMap, "pa", (short)1094);
/* 1437 */     addLCIDMapEntry((Map)hashMap, "gu", (short)1095);
/* 1438 */     addLCIDMapEntry((Map)hashMap, "ta", (short)1097);
/* 1439 */     addLCIDMapEntry((Map)hashMap, "te", (short)1098);
/* 1440 */     addLCIDMapEntry((Map)hashMap, "kn", (short)1099);
/* 1441 */     addLCIDMapEntry((Map)hashMap, "ml", (short)1100);
/* 1442 */     addLCIDMapEntry((Map)hashMap, "mr", (short)1102);
/* 1443 */     addLCIDMapEntry((Map)hashMap, "sa", (short)1103);
/* 1444 */     addLCIDMapEntry((Map)hashMap, "mn", (short)1104);
/* 1445 */     addLCIDMapEntry((Map)hashMap, "cy", (short)1106);
/* 1446 */     addLCIDMapEntry((Map)hashMap, "gl", (short)1110);
/* 1447 */     addLCIDMapEntry((Map)hashMap, "dv", (short)1125);
/* 1448 */     addLCIDMapEntry((Map)hashMap, "qu", (short)1131);
/* 1449 */     addLCIDMapEntry((Map)hashMap, "mi", (short)1153);
/* 1450 */     addLCIDMapEntry((Map)hashMap, "ar_IQ", (short)2049);
/* 1451 */     addLCIDMapEntry((Map)hashMap, "zh_CN", (short)2052);
/* 1452 */     addLCIDMapEntry((Map)hashMap, "de_CH", (short)2055);
/* 1453 */     addLCIDMapEntry((Map)hashMap, "en_GB", (short)2057);
/* 1454 */     addLCIDMapEntry((Map)hashMap, "es_MX", (short)2058);
/* 1455 */     addLCIDMapEntry((Map)hashMap, "fr_BE", (short)2060);
/* 1456 */     addLCIDMapEntry((Map)hashMap, "it_CH", (short)2064);
/* 1457 */     addLCIDMapEntry((Map)hashMap, "nl_BE", (short)2067);
/* 1458 */     addLCIDMapEntry((Map)hashMap, "no_NO_NY", (short)2068);
/* 1459 */     addLCIDMapEntry((Map)hashMap, "pt_PT", (short)2070);
/* 1460 */     addLCIDMapEntry((Map)hashMap, "ro_MD", (short)2072);
/* 1461 */     addLCIDMapEntry((Map)hashMap, "ru_MD", (short)2073);
/* 1462 */     addLCIDMapEntry((Map)hashMap, "sr_CS", (short)2074);
/* 1463 */     addLCIDMapEntry((Map)hashMap, "sv_FI", (short)2077);
/* 1464 */     addLCIDMapEntry((Map)hashMap, "az_AZ", (short)2092);
/* 1465 */     addLCIDMapEntry((Map)hashMap, "se_SE", (short)2107);
/* 1466 */     addLCIDMapEntry((Map)hashMap, "ga_IE", (short)2108);
/* 1467 */     addLCIDMapEntry((Map)hashMap, "ms_BN", (short)2110);
/* 1468 */     addLCIDMapEntry((Map)hashMap, "uz_UZ", (short)2115);
/* 1469 */     addLCIDMapEntry((Map)hashMap, "qu_EC", (short)2155);
/* 1470 */     addLCIDMapEntry((Map)hashMap, "ar_EG", (short)3073);
/* 1471 */     addLCIDMapEntry((Map)hashMap, "zh_HK", (short)3076);
/* 1472 */     addLCIDMapEntry((Map)hashMap, "de_AT", (short)3079);
/* 1473 */     addLCIDMapEntry((Map)hashMap, "en_AU", (short)3081);
/* 1474 */     addLCIDMapEntry((Map)hashMap, "fr_CA", (short)3084);
/* 1475 */     addLCIDMapEntry((Map)hashMap, "sr_CS", (short)3098);
/* 1476 */     addLCIDMapEntry((Map)hashMap, "se_FI", (short)3131);
/* 1477 */     addLCIDMapEntry((Map)hashMap, "qu_PE", (short)3179);
/* 1478 */     addLCIDMapEntry((Map)hashMap, "ar_LY", (short)4097);
/* 1479 */     addLCIDMapEntry((Map)hashMap, "zh_SG", (short)4100);
/* 1480 */     addLCIDMapEntry((Map)hashMap, "de_LU", (short)4103);
/* 1481 */     addLCIDMapEntry((Map)hashMap, "en_CA", (short)4105);
/* 1482 */     addLCIDMapEntry((Map)hashMap, "es_GT", (short)4106);
/* 1483 */     addLCIDMapEntry((Map)hashMap, "fr_CH", (short)4108);
/* 1484 */     addLCIDMapEntry((Map)hashMap, "hr_BA", (short)4122);
/* 1485 */     addLCIDMapEntry((Map)hashMap, "ar_DZ", (short)5121);
/* 1486 */     addLCIDMapEntry((Map)hashMap, "zh_MO", (short)5124);
/* 1487 */     addLCIDMapEntry((Map)hashMap, "de_LI", (short)5127);
/* 1488 */     addLCIDMapEntry((Map)hashMap, "en_NZ", (short)5129);
/* 1489 */     addLCIDMapEntry((Map)hashMap, "es_CR", (short)5130);
/* 1490 */     addLCIDMapEntry((Map)hashMap, "fr_LU", (short)5132);
/* 1491 */     addLCIDMapEntry((Map)hashMap, "bs_BA", (short)5146);
/* 1492 */     addLCIDMapEntry((Map)hashMap, "ar_MA", (short)6145);
/* 1493 */     addLCIDMapEntry((Map)hashMap, "en_IE", (short)6153);
/* 1494 */     addLCIDMapEntry((Map)hashMap, "es_PA", (short)6154);
/* 1495 */     addLCIDMapEntry((Map)hashMap, "fr_MC", (short)6156);
/* 1496 */     addLCIDMapEntry((Map)hashMap, "sr_BA", (short)6170);
/* 1497 */     addLCIDMapEntry((Map)hashMap, "ar_TN", (short)7169);
/* 1498 */     addLCIDMapEntry((Map)hashMap, "en_ZA", (short)7177);
/* 1499 */     addLCIDMapEntry((Map)hashMap, "es_DO", (short)7178);
/* 1500 */     addLCIDMapEntry((Map)hashMap, "sr_BA", (short)7194);
/* 1501 */     addLCIDMapEntry((Map)hashMap, "ar_OM", (short)8193);
/* 1502 */     addLCIDMapEntry((Map)hashMap, "en_JM", (short)8201);
/* 1503 */     addLCIDMapEntry((Map)hashMap, "es_VE", (short)8202);
/* 1504 */     addLCIDMapEntry((Map)hashMap, "ar_YE", (short)9217);
/* 1505 */     addLCIDMapEntry((Map)hashMap, "es_CO", (short)9226);
/* 1506 */     addLCIDMapEntry((Map)hashMap, "ar_SY", (short)10241);
/* 1507 */     addLCIDMapEntry((Map)hashMap, "en_BZ", (short)10249);
/* 1508 */     addLCIDMapEntry((Map)hashMap, "es_PE", (short)10250);
/* 1509 */     addLCIDMapEntry((Map)hashMap, "ar_JO", (short)11265);
/* 1510 */     addLCIDMapEntry((Map)hashMap, "en_TT", (short)11273);
/* 1511 */     addLCIDMapEntry((Map)hashMap, "es_AR", (short)11274);
/* 1512 */     addLCIDMapEntry((Map)hashMap, "ar_LB", (short)12289);
/* 1513 */     addLCIDMapEntry((Map)hashMap, "en_ZW", (short)12297);
/* 1514 */     addLCIDMapEntry((Map)hashMap, "es_EC", (short)12298);
/* 1515 */     addLCIDMapEntry((Map)hashMap, "ar_KW", (short)13313);
/* 1516 */     addLCIDMapEntry((Map)hashMap, "en_PH", (short)13321);
/* 1517 */     addLCIDMapEntry((Map)hashMap, "es_CL", (short)13322);
/* 1518 */     addLCIDMapEntry((Map)hashMap, "ar_AE", (short)14337);
/* 1519 */     addLCIDMapEntry((Map)hashMap, "es_UY", (short)14346);
/* 1520 */     addLCIDMapEntry((Map)hashMap, "ar_BH", (short)15361);
/* 1521 */     addLCIDMapEntry((Map)hashMap, "es_PY", (short)15370);
/* 1522 */     addLCIDMapEntry((Map)hashMap, "ar_QA", (short)16385);
/* 1523 */     addLCIDMapEntry((Map)hashMap, "es_BO", (short)16394);
/* 1524 */     addLCIDMapEntry((Map)hashMap, "es_SV", (short)17418);
/* 1525 */     addLCIDMapEntry((Map)hashMap, "es_HN", (short)18442);
/* 1526 */     addLCIDMapEntry((Map)hashMap, "es_NI", (short)19466);
/* 1527 */     addLCIDMapEntry((Map)hashMap, "es_PR", (short)20490);
/*      */     
/* 1529 */     lcidMap = (Map)hashMap;
/*      */   }
/*      */ 
/*      */   
/*      */   private static short getLCIDFromLocale(Locale paramLocale) {
/* 1534 */     if (paramLocale.equals(Locale.US)) {
/* 1535 */       return 1033;
/*      */     }
/*      */     
/* 1538 */     if (lcidMap == null) {
/* 1539 */       createLCIDMap();
/*      */     }
/*      */     
/* 1542 */     String str = paramLocale.toString();
/* 1543 */     while (!"".equals(str)) {
/* 1544 */       Short short_ = lcidMap.get(str);
/* 1545 */       if (short_ != null) {
/* 1546 */         return short_.shortValue();
/*      */       }
/* 1548 */       int i = str.lastIndexOf('_');
/* 1549 */       if (i < 1) {
/* 1550 */         return 1033;
/*      */       }
/* 1552 */       str = str.substring(0, i);
/*      */     } 
/*      */     
/* 1555 */     return 1033;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getFamilyName(Locale paramLocale) {
/* 1560 */     if (paramLocale == null)
/* 1561 */       return this.familyName; 
/* 1562 */     if (paramLocale.equals(this.nameLocale) && this.localeFamilyName != null) {
/* 1563 */       return this.localeFamilyName;
/*      */     }
/* 1565 */     short s = getLCIDFromLocale(paramLocale);
/* 1566 */     String str = lookupName(s, 1);
/* 1567 */     if (str == null) {
/* 1568 */       return this.familyName;
/*      */     }
/* 1570 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public CharToGlyphMapper getMapper() {
/* 1576 */     if (this.mapper == null) {
/* 1577 */       this.mapper = new TrueTypeGlyphMapper(this);
/*      */     }
/* 1579 */     return this.mapper;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void initAllNames(int paramInt, HashSet<String> paramHashSet) {
/* 1588 */     byte[] arrayOfByte = new byte[256];
/* 1589 */     ByteBuffer byteBuffer = getTableBuffer(1851878757);
/*      */     
/* 1591 */     if (byteBuffer != null) {
/* 1592 */       ShortBuffer shortBuffer = byteBuffer.asShortBuffer();
/* 1593 */       shortBuffer.get();
/* 1594 */       short s = shortBuffer.get();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1601 */       int i = shortBuffer.get() & 0xFFFF;
/* 1602 */       for (byte b = 0; b < s; b++) {
/* 1603 */         short s1 = shortBuffer.get();
/* 1604 */         if (s1 != 3) {
/* 1605 */           shortBuffer.position(shortBuffer.position() + 5);
/*      */         } else {
/*      */           
/* 1608 */           short s2 = shortBuffer.get();
/* 1609 */           short s3 = shortBuffer.get();
/* 1610 */           short s4 = shortBuffer.get();
/* 1611 */           int j = shortBuffer.get() & 0xFFFF;
/* 1612 */           int k = (shortBuffer.get() & 0xFFFF) + i;
/*      */           
/* 1614 */           if (s4 == paramInt) {
/* 1615 */             byteBuffer.position(k);
/* 1616 */             byteBuffer.get(arrayOfByte, 0, j);
/* 1617 */             paramHashSet.add(makeString(arrayOfByte, j, s2));
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   String[] getAllFamilyNames() {
/* 1624 */     HashSet hashSet = new HashSet();
/*      */     try {
/* 1626 */       initAllNames(1, hashSet);
/* 1627 */     } catch (Exception exception) {}
/*      */ 
/*      */     
/* 1630 */     return (String[])hashSet.toArray((Object[])new String[0]);
/*      */   }
/*      */   
/*      */   String[] getAllFullNames() {
/* 1634 */     HashSet hashSet = new HashSet();
/*      */     try {
/* 1636 */       initAllNames(4, hashSet);
/* 1637 */     } catch (Exception exception) {}
/*      */ 
/*      */     
/* 1640 */     return (String[])hashSet.toArray((Object[])new String[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Point2D.Float getGlyphPoint(long paramLong, int paramInt1, int paramInt2) {
/*      */     try {
/* 1649 */       return getScaler().getGlyphPoint(paramLong, paramInt1, paramInt2);
/*      */     }
/* 1651 */     catch (FontScalerException fontScalerException) {
/* 1652 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private char[] getGaspTable() {
/* 1660 */     if (this.gaspTable != null) {
/* 1661 */       return this.gaspTable;
/*      */     }
/*      */     
/* 1664 */     ByteBuffer byteBuffer = getTableBuffer(1734439792);
/* 1665 */     if (byteBuffer == null) {
/* 1666 */       return this.gaspTable = new char[0];
/*      */     }
/*      */     
/* 1669 */     CharBuffer charBuffer = byteBuffer.asCharBuffer();
/* 1670 */     char c1 = charBuffer.get();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1675 */     if (c1 > '\001') {
/* 1676 */       return this.gaspTable = new char[0];
/*      */     }
/*      */     
/* 1679 */     char c2 = charBuffer.get();
/* 1680 */     if (4 + c2 * 4 > getTableSize(1734439792)) {
/* 1681 */       return this.gaspTable = new char[0];
/*      */     }
/* 1683 */     this.gaspTable = new char[2 * c2];
/* 1684 */     charBuffer.get(this.gaspTable);
/* 1685 */     return this.gaspTable;
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
/*      */   public boolean useAAForPtSize(int paramInt) {
/* 1710 */     char[] arrayOfChar = getGaspTable();
/* 1711 */     if (arrayOfChar.length > 0) {
/* 1712 */       for (byte b = 0; b < arrayOfChar.length; b += 2) {
/* 1713 */         if (paramInt <= arrayOfChar[b]) {
/* 1714 */           return ((arrayOfChar[b + 1] & 0x2) != 0);
/*      */         }
/*      */       } 
/* 1717 */       return true;
/*      */     } 
/*      */     
/* 1720 */     if (this.style == 1) {
/* 1721 */       return true;
/*      */     }
/* 1723 */     return (paramInt <= 8 || paramInt >= 18);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasSupplementaryChars() {
/* 1729 */     return ((TrueTypeGlyphMapper)getMapper()).hasSupplementaryChars();
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1734 */     return "** TrueType Font: Family=" + this.familyName + " Name=" + this.fullName + " style=" + this.style + " fileName=" + 
/* 1735 */       getPublicFileName();
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/font/TrueTypeFont.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */