/*      */ package java.awt.datatransfer;
/*      */ 
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.CharArrayReader;
/*      */ import java.io.Externalizable;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.ObjectInput;
/*      */ import java.io.ObjectOutput;
/*      */ import java.io.OptionalDataException;
/*      */ import java.io.Reader;
/*      */ import java.io.Serializable;
/*      */ import java.io.StringReader;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.CharBuffer;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.List;
/*      */ import java.util.Objects;
/*      */ import sun.awt.datatransfer.DataTransferer;
/*      */ import sun.reflect.misc.ReflectUtil;
/*      */ import sun.security.util.SecurityConstants;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DataFlavor
/*      */   implements Externalizable, Cloneable
/*      */ {
/*      */   private static final long serialVersionUID = 8367026044764648243L;
/*  122 */   private static final Class<InputStream> ioInputStreamClass = InputStream.class;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final Class<?> tryToLoadClass(String paramString, ClassLoader paramClassLoader) throws ClassNotFoundException {
/*  137 */     ReflectUtil.checkPackageAccess(paramString);
/*      */     try {
/*  139 */       SecurityManager securityManager = System.getSecurityManager();
/*  140 */       if (securityManager != null) {
/*  141 */         securityManager.checkPermission(SecurityConstants.GET_CLASSLOADER_PERMISSION);
/*      */       }
/*  143 */       ClassLoader classLoader = ClassLoader.getSystemClassLoader();
/*      */       
/*      */       try {
/*  146 */         return Class.forName(paramString, true, classLoader);
/*      */       }
/*  148 */       catch (ClassNotFoundException classNotFoundException) {
/*      */         
/*  150 */         classLoader = Thread.currentThread().getContextClassLoader();
/*  151 */         if (classLoader != null) {
/*      */           try {
/*  153 */             return Class.forName(paramString, true, classLoader);
/*      */           }
/*  155 */           catch (ClassNotFoundException classNotFoundException1) {}
/*      */         }
/*      */       }
/*      */     
/*      */     }
/*  160 */     catch (SecurityException securityException) {}
/*      */ 
/*      */     
/*  163 */     return Class.forName(paramString, true, paramClassLoader);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static DataFlavor createConstant(Class<?> paramClass, String paramString) {
/*      */     try {
/*  171 */       return new DataFlavor(paramClass, paramString);
/*  172 */     } catch (Exception exception) {
/*  173 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static DataFlavor createConstant(String paramString1, String paramString2) {
/*      */     try {
/*  182 */       return new DataFlavor(paramString1, paramString2);
/*  183 */     } catch (Exception exception) {
/*  184 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static DataFlavor initHtmlDataFlavor(String paramString) {
/*      */     try {
/*  193 */       return new DataFlavor("text/html; class=java.lang.String;document=" + paramString + ";charset=Unicode");
/*      */     }
/*  195 */     catch (Exception exception) {
/*  196 */       return null;
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
/*  208 */   public static final DataFlavor stringFlavor = createConstant(String.class, "Unicode String");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  218 */   public static final DataFlavor imageFlavor = createConstant("image/x-java-image; class=java.awt.Image", "Image");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*  237 */   public static final DataFlavor plainTextFlavor = createConstant("text/plain; charset=unicode; class=java.io.InputStream", "Plain Text");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String javaSerializedObjectMimeType = "application/x-java-serialized-object";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  256 */   public static final DataFlavor javaFileListFlavor = createConstant("application/x-java-file-list;class=java.util.List", (String)null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String javaJVMLocalObjectMimeType = "application/x-java-jvm-local-objectref";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String javaRemoteObjectMimeType = "application/x-java-remote-object";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  296 */   public static DataFlavor selectionHtmlFlavor = initHtmlDataFlavor("selection");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  308 */   public static DataFlavor fragmentHtmlFlavor = initHtmlDataFlavor("fragment");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  321 */   public static DataFlavor allHtmlFlavor = initHtmlDataFlavor("all");
/*      */ 
/*      */   
/*      */   private static Comparator<DataFlavor> textFlavorComparator;
/*      */ 
/*      */   
/*      */   transient int atom;
/*      */ 
/*      */   
/*      */   MimeType mimeType;
/*      */ 
/*      */   
/*      */   private String humanPresentableName;
/*      */ 
/*      */   
/*      */   private Class<?> representationClass;
/*      */ 
/*      */   
/*      */   public DataFlavor() {}
/*      */ 
/*      */   
/*      */   private DataFlavor(String paramString1, String paramString2, MimeTypeParameterList paramMimeTypeParameterList, Class<?> paramClass, String paramString3) {
/*  343 */     if (paramString1 == null) {
/*  344 */       throw new NullPointerException("primaryType");
/*      */     }
/*  346 */     if (paramString2 == null) {
/*  347 */       throw new NullPointerException("subType");
/*      */     }
/*  349 */     if (paramClass == null) {
/*  350 */       throw new NullPointerException("representationClass");
/*      */     }
/*      */     
/*  353 */     if (paramMimeTypeParameterList == null) paramMimeTypeParameterList = new MimeTypeParameterList();
/*      */     
/*  355 */     paramMimeTypeParameterList.set("class", paramClass.getName());
/*      */     
/*  357 */     if (paramString3 == null) {
/*  358 */       paramString3 = paramMimeTypeParameterList.get("humanPresentableName");
/*      */       
/*  360 */       if (paramString3 == null) {
/*  361 */         paramString3 = paramString1 + "/" + paramString2;
/*      */       }
/*      */     } 
/*      */     try {
/*  365 */       this.mimeType = new MimeType(paramString1, paramString2, paramMimeTypeParameterList);
/*  366 */     } catch (MimeTypeParseException mimeTypeParseException) {
/*  367 */       throw new IllegalArgumentException("MimeType Parse Exception: " + mimeTypeParseException.getMessage());
/*      */     } 
/*      */     
/*  370 */     this.representationClass = paramClass;
/*  371 */     this.humanPresentableName = paramString3;
/*      */     
/*  373 */     this.mimeType.removeParameter("humanPresentableName");
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
/*      */   public DataFlavor(Class<?> paramClass, String paramString) {
/*  392 */     this("application", "x-java-serialized-object", null, paramClass, paramString);
/*  393 */     if (paramClass == null) {
/*  394 */       throw new NullPointerException("representationClass");
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
/*      */   public DataFlavor(String paramString1, String paramString2) {
/*  429 */     if (paramString1 == null) {
/*  430 */       throw new NullPointerException("mimeType");
/*      */     }
/*      */     try {
/*  433 */       initialize(paramString1, paramString2, getClass().getClassLoader());
/*  434 */     } catch (MimeTypeParseException mimeTypeParseException) {
/*  435 */       throw new IllegalArgumentException("failed to parse:" + paramString1);
/*  436 */     } catch (ClassNotFoundException classNotFoundException) {
/*  437 */       throw new IllegalArgumentException("can't find specified class: " + classNotFoundException.getMessage());
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
/*      */   public DataFlavor(String paramString1, String paramString2, ClassLoader paramClassLoader) throws ClassNotFoundException {
/*  469 */     if (paramString1 == null) {
/*  470 */       throw new NullPointerException("mimeType");
/*      */     }
/*      */     try {
/*  473 */       initialize(paramString1, paramString2, paramClassLoader);
/*  474 */     } catch (MimeTypeParseException mimeTypeParseException) {
/*  475 */       throw new IllegalArgumentException("failed to parse:" + paramString1);
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
/*      */   public DataFlavor(String paramString) throws ClassNotFoundException {
/*  497 */     if (paramString == null) {
/*  498 */       throw new NullPointerException("mimeType");
/*      */     }
/*      */     try {
/*  501 */       initialize(paramString, null, getClass().getClassLoader());
/*  502 */     } catch (MimeTypeParseException mimeTypeParseException) {
/*  503 */       throw new IllegalArgumentException("failed to parse:" + paramString);
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
/*      */   private void initialize(String paramString1, String paramString2, ClassLoader paramClassLoader) throws MimeTypeParseException, ClassNotFoundException {
/*  522 */     if (paramString1 == null) {
/*  523 */       throw new NullPointerException("mimeType");
/*      */     }
/*      */     
/*  526 */     this.mimeType = new MimeType(paramString1);
/*      */     
/*  528 */     String str = getParameter("class");
/*      */     
/*  530 */     if (str == null) {
/*  531 */       if ("application/x-java-serialized-object".equals(this.mimeType.getBaseType()))
/*      */       {
/*  533 */         throw new IllegalArgumentException("no representation class specified for:" + paramString1);
/*      */       }
/*  535 */       this.representationClass = InputStream.class;
/*      */     } else {
/*  537 */       this.representationClass = tryToLoadClass(str, paramClassLoader);
/*      */     } 
/*      */     
/*  540 */     this.mimeType.setParameter("class", this.representationClass.getName());
/*      */     
/*  542 */     if (paramString2 == null) {
/*  543 */       paramString2 = this.mimeType.getParameter("humanPresentableName");
/*  544 */       if (paramString2 == null) {
/*  545 */         paramString2 = this.mimeType.getPrimaryType() + "/" + this.mimeType.getSubType();
/*      */       }
/*      */     } 
/*  548 */     this.humanPresentableName = paramString2;
/*      */     
/*  550 */     this.mimeType.removeParameter("humanPresentableName");
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
/*      */   public String toString() {
/*  566 */     String str = getClass().getName();
/*  567 */     str = str + "[" + paramString() + "]";
/*  568 */     return str;
/*      */   }
/*      */   
/*      */   private String paramString() {
/*  572 */     String str = "";
/*  573 */     str = str + "mimetype=";
/*  574 */     if (this.mimeType == null) {
/*  575 */       str = str + "null";
/*      */     } else {
/*  577 */       str = str + this.mimeType.getBaseType();
/*      */     } 
/*  579 */     str = str + ";representationclass=";
/*  580 */     if (this.representationClass == null) {
/*  581 */       str = str + "null";
/*      */     } else {
/*  583 */       str = str + this.representationClass.getName();
/*      */     } 
/*  585 */     if (DataTransferer.isFlavorCharsetTextType(this) && (
/*  586 */       isRepresentationClassInputStream() || 
/*  587 */       isRepresentationClassByteBuffer() || byte[].class
/*  588 */       .equals(this.representationClass)))
/*      */     {
/*  590 */       str = str + ";charset=" + DataTransferer.getTextCharset(this);
/*      */     }
/*  592 */     return str;
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
/*      */   public static final DataFlavor getTextPlainUnicodeFlavor() {
/*  612 */     String str = null;
/*  613 */     DataTransferer dataTransferer = DataTransferer.getInstance();
/*  614 */     if (dataTransferer != null) {
/*  615 */       str = dataTransferer.getDefaultUnicodeEncoding();
/*      */     }
/*  617 */     return new DataFlavor("text/plain;charset=" + str + ";class=java.io.InputStream", "Plain Text");
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final DataFlavor selectBestTextFlavor(DataFlavor[] paramArrayOfDataFlavor) {
/*  740 */     if (paramArrayOfDataFlavor == null || paramArrayOfDataFlavor.length == 0) {
/*  741 */       return null;
/*      */     }
/*      */     
/*  744 */     if (textFlavorComparator == null) {
/*  745 */       textFlavorComparator = (Comparator<DataFlavor>)new TextFlavorComparator();
/*      */     }
/*      */ 
/*      */     
/*  749 */     DataFlavor dataFlavor = Collections.<DataFlavor>max(Arrays.asList(paramArrayOfDataFlavor), textFlavorComparator);
/*      */ 
/*      */     
/*  752 */     if (!dataFlavor.isFlavorTextType()) {
/*  753 */       return null;
/*      */     }
/*      */     
/*  756 */     return dataFlavor;
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
/*      */   static class TextFlavorComparator
/*      */     extends DataTransferer.DataFlavorComparator
/*      */   {
/*      */     public int compare(Object param1Object1, Object param1Object2) {
/*  785 */       DataFlavor dataFlavor1 = (DataFlavor)param1Object1;
/*  786 */       DataFlavor dataFlavor2 = (DataFlavor)param1Object2;
/*      */       
/*  788 */       if (dataFlavor1.isFlavorTextType()) {
/*  789 */         if (dataFlavor2.isFlavorTextType()) {
/*  790 */           return super.compare(param1Object1, param1Object2);
/*      */         }
/*  792 */         return 1;
/*      */       } 
/*  794 */       if (dataFlavor2.isFlavorTextType()) {
/*  795 */         return -1;
/*      */       }
/*  797 */       return 0;
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
/*      */   public Reader getReaderForText(Transferable paramTransferable) throws UnsupportedFlavorException, IOException {
/*  845 */     Object object = paramTransferable.getTransferData(this);
/*  846 */     if (object == null) {
/*  847 */       throw new IllegalArgumentException("getTransferData() returned null");
/*      */     }
/*      */ 
/*      */     
/*  851 */     if (object instanceof Reader)
/*  852 */       return (Reader)object; 
/*  853 */     if (object instanceof String)
/*  854 */       return new StringReader((String)object); 
/*  855 */     if (object instanceof CharBuffer) {
/*  856 */       CharBuffer charBuffer = (CharBuffer)object;
/*  857 */       int i = charBuffer.remaining();
/*  858 */       char[] arrayOfChar = new char[i];
/*  859 */       charBuffer.get(arrayOfChar, 0, i);
/*  860 */       return new CharArrayReader(arrayOfChar);
/*  861 */     }  if (object instanceof char[]) {
/*  862 */       return new CharArrayReader((char[])object);
/*      */     }
/*      */     
/*  865 */     InputStream inputStream = null;
/*      */     
/*  867 */     if (object instanceof InputStream) {
/*  868 */       inputStream = (InputStream)object;
/*  869 */     } else if (object instanceof ByteBuffer) {
/*  870 */       ByteBuffer byteBuffer = (ByteBuffer)object;
/*  871 */       int i = byteBuffer.remaining();
/*  872 */       byte[] arrayOfByte = new byte[i];
/*  873 */       byteBuffer.get(arrayOfByte, 0, i);
/*  874 */       inputStream = new ByteArrayInputStream(arrayOfByte);
/*  875 */     } else if (object instanceof byte[]) {
/*  876 */       inputStream = new ByteArrayInputStream((byte[])object);
/*      */     } 
/*      */     
/*  879 */     if (inputStream == null) {
/*  880 */       throw new IllegalArgumentException("transfer data is not Reader, String, CharBuffer, char array, InputStream, ByteBuffer, or byte array");
/*      */     }
/*      */     
/*  883 */     String str = getParameter("charset");
/*  884 */     return (str == null) ? new InputStreamReader(inputStream) : new InputStreamReader(inputStream, str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getMimeType() {
/*  894 */     return (this.mimeType != null) ? this.mimeType.toString() : null;
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
/*      */   public Class<?> getRepresentationClass() {
/*  906 */     return this.representationClass;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getHumanPresentableName() {
/*  917 */     return this.humanPresentableName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPrimaryType() {
/*  925 */     return (this.mimeType != null) ? this.mimeType.getPrimaryType() : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getSubType() {
/*  933 */     return (this.mimeType != null) ? this.mimeType.getSubType() : null;
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
/*      */   public String getParameter(String paramString) {
/*  946 */     if (paramString.equals("humanPresentableName")) {
/*  947 */       return this.humanPresentableName;
/*      */     }
/*  949 */     return (this.mimeType != null) ? this.mimeType
/*  950 */       .getParameter(paramString) : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setHumanPresentableName(String paramString) {
/*  961 */     this.humanPresentableName = paramString;
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
/*      */   public boolean equals(Object paramObject) {
/*  986 */     return (paramObject instanceof DataFlavor && equals((DataFlavor)paramObject));
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
/*      */   public boolean equals(DataFlavor paramDataFlavor) {
/* 1001 */     if (paramDataFlavor == null) {
/* 1002 */       return false;
/*      */     }
/* 1004 */     if (this == paramDataFlavor) {
/* 1005 */       return true;
/*      */     }
/*      */     
/* 1008 */     if (!Objects.equals(getRepresentationClass(), paramDataFlavor.getRepresentationClass())) {
/* 1009 */       return false;
/*      */     }
/*      */     
/* 1012 */     if (this.mimeType == null) {
/* 1013 */       if (paramDataFlavor.mimeType != null) {
/* 1014 */         return false;
/*      */       }
/*      */     } else {
/* 1017 */       if (!this.mimeType.match(paramDataFlavor.mimeType)) {
/* 1018 */         return false;
/*      */       }
/*      */       
/* 1021 */       if ("text".equals(getPrimaryType())) {
/* 1022 */         if (DataTransferer.doesSubtypeSupportCharset(this) && this.representationClass != null && 
/*      */           
/* 1024 */           !isStandardTextRepresentationClass()) {
/*      */           
/* 1026 */           String str1 = DataTransferer.canonicalName(getParameter("charset"));
/*      */           
/* 1028 */           String str2 = DataTransferer.canonicalName(paramDataFlavor.getParameter("charset"));
/* 1029 */           if (!Objects.equals(str1, str2)) {
/* 1030 */             return false;
/*      */           }
/*      */         } 
/*      */         
/* 1034 */         if ("html".equals(getSubType())) {
/* 1035 */           String str1 = getParameter("document");
/* 1036 */           String str2 = paramDataFlavor.getParameter("document");
/* 1037 */           if (!Objects.equals(str1, str2)) {
/* 1038 */             return false;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1044 */     return true;
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
/*      */   @Deprecated
/*      */   public boolean equals(String paramString) {
/* 1062 */     if (paramString == null || this.mimeType == null)
/* 1063 */       return false; 
/* 1064 */     return isMimeTypeEqual(paramString);
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
/*      */   public int hashCode() {
/* 1078 */     int i = 0;
/*      */     
/* 1080 */     if (this.representationClass != null) {
/* 1081 */       i += this.representationClass.hashCode();
/*      */     }
/*      */     
/* 1084 */     if (this.mimeType != null) {
/* 1085 */       String str = this.mimeType.getPrimaryType();
/* 1086 */       if (str != null) {
/* 1087 */         i += str.hashCode();
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1094 */       if ("text".equals(str)) {
/* 1095 */         if (DataTransferer.doesSubtypeSupportCharset(this) && this.representationClass != null && 
/*      */           
/* 1097 */           !isStandardTextRepresentationClass()) {
/* 1098 */           String str1 = DataTransferer.canonicalName(getParameter("charset"));
/* 1099 */           if (str1 != null) {
/* 1100 */             i += str1.hashCode();
/*      */           }
/*      */         } 
/*      */         
/* 1104 */         if ("html".equals(getSubType())) {
/* 1105 */           String str1 = getParameter("document");
/* 1106 */           if (str1 != null) {
/* 1107 */             i += str1.hashCode();
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1113 */     return i;
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
/*      */   public boolean match(DataFlavor paramDataFlavor) {
/* 1127 */     return equals(paramDataFlavor);
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
/*      */   public boolean isMimeTypeEqual(String paramString) {
/* 1143 */     if (paramString == null) {
/* 1144 */       throw new NullPointerException("mimeType");
/*      */     }
/* 1146 */     if (this.mimeType == null) {
/* 1147 */       return false;
/*      */     }
/*      */     try {
/* 1150 */       return this.mimeType.match(new MimeType(paramString));
/* 1151 */     } catch (MimeTypeParseException mimeTypeParseException) {
/* 1152 */       return false;
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
/*      */   public final boolean isMimeTypeEqual(DataFlavor paramDataFlavor) {
/* 1166 */     return isMimeTypeEqual(paramDataFlavor.mimeType);
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
/*      */   private boolean isMimeTypeEqual(MimeType paramMimeType) {
/* 1178 */     if (this.mimeType == null) {
/* 1179 */       return (paramMimeType == null);
/*      */     }
/* 1181 */     return this.mimeType.match(paramMimeType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isStandardTextRepresentationClass() {
/* 1192 */     return (isRepresentationClassReader() || String.class
/* 1193 */       .equals(this.representationClass) || 
/* 1194 */       isRepresentationClassCharBuffer() || char[].class
/* 1195 */       .equals(this.representationClass));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isMimeTypeSerializedObject() {
/* 1203 */     return isMimeTypeEqual("application/x-java-serialized-object");
/*      */   }
/*      */   
/*      */   public final Class<?> getDefaultRepresentationClass() {
/* 1207 */     return ioInputStreamClass;
/*      */   }
/*      */   
/*      */   public final String getDefaultRepresentationClassAsString() {
/* 1211 */     return getDefaultRepresentationClass().getName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRepresentationClassInputStream() {
/* 1220 */     return ioInputStreamClass.isAssignableFrom(this.representationClass);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRepresentationClassReader() {
/* 1231 */     return Reader.class.isAssignableFrom(this.representationClass);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRepresentationClassCharBuffer() {
/* 1242 */     return CharBuffer.class.isAssignableFrom(this.representationClass);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRepresentationClassByteBuffer() {
/* 1253 */     return ByteBuffer.class.isAssignableFrom(this.representationClass);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRepresentationClassSerializable() {
/* 1262 */     return Serializable.class.isAssignableFrom(this.representationClass);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRepresentationClassRemote() {
/* 1271 */     return DataTransferer.isRemote(this.representationClass);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFlavorSerializedObjectType() {
/* 1282 */     return (isRepresentationClassSerializable() && isMimeTypeEqual("application/x-java-serialized-object"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFlavorRemoteObjectType() {
/* 1293 */     return (isRepresentationClassRemote() && 
/* 1294 */       isRepresentationClassSerializable() && 
/* 1295 */       isMimeTypeEqual("application/x-java-remote-object"));
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
/*      */   public boolean isFlavorJavaFileListType() {
/* 1307 */     if (this.mimeType == null || this.representationClass == null)
/* 1308 */       return false; 
/* 1309 */     return (List.class.isAssignableFrom(this.representationClass) && this.mimeType
/* 1310 */       .match(javaFileListFlavor.mimeType));
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
/*      */   public boolean isFlavorTextType() {
/* 1345 */     return (DataTransferer.isFlavorCharsetTextType(this) || 
/* 1346 */       DataTransferer.isFlavorNoncharsetTextType(this));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void writeExternal(ObjectOutput paramObjectOutput) throws IOException {
/* 1354 */     if (this.mimeType != null) {
/* 1355 */       this.mimeType.setParameter("humanPresentableName", this.humanPresentableName);
/* 1356 */       paramObjectOutput.writeObject(this.mimeType);
/* 1357 */       this.mimeType.removeParameter("humanPresentableName");
/*      */     } else {
/* 1359 */       paramObjectOutput.writeObject(null);
/*      */     } 
/*      */     
/* 1362 */     paramObjectOutput.writeObject(this.representationClass);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void readExternal(ObjectInput paramObjectInput) throws IOException, ClassNotFoundException {
/* 1370 */     String str = null;
/* 1371 */     this.mimeType = (MimeType)paramObjectInput.readObject();
/*      */     
/* 1373 */     if (this.mimeType != null) {
/* 1374 */       this
/* 1375 */         .humanPresentableName = this.mimeType.getParameter("humanPresentableName");
/* 1376 */       this.mimeType.removeParameter("humanPresentableName");
/* 1377 */       str = this.mimeType.getParameter("class");
/* 1378 */       if (str == null) {
/* 1379 */         throw new IOException("no class parameter specified in: " + this.mimeType);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/* 1385 */       this.representationClass = (Class)paramObjectInput.readObject();
/* 1386 */     } catch (OptionalDataException optionalDataException) {
/* 1387 */       if (!optionalDataException.eof || optionalDataException.length != 0) {
/* 1388 */         throw optionalDataException;
/*      */       }
/*      */ 
/*      */       
/* 1392 */       if (str != null) {
/* 1393 */         this
/* 1394 */           .representationClass = tryToLoadClass(str, getClass().getClassLoader());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object clone() throws CloneNotSupportedException {
/* 1405 */     Object object = super.clone();
/* 1406 */     if (this.mimeType != null) {
/* 1407 */       ((DataFlavor)object).mimeType = (MimeType)this.mimeType.clone();
/*      */     }
/* 1409 */     return object;
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
/*      */   @Deprecated
/*      */   protected String normalizeMimeTypeParameter(String paramString1, String paramString2) {
/* 1428 */     return paramString2;
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
/*      */   @Deprecated
/*      */   protected String normalizeMimeType(String paramString) {
/* 1444 */     return paramString;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/datatransfer/DataFlavor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */