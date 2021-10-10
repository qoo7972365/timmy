/*     */ package java.io;
/*     */ 
/*     */ import java.net.URI;
/*     */ import java.nio.file.FileSystem;
/*     */ import java.nio.file.InvalidPathException;
/*     */ import java.security.AccessController;
/*     */ import java.security.Permission;
/*     */ import java.security.PermissionCollection;
/*     */ import java.security.PrivilegedAction;
/*     */ import sun.nio.fs.DefaultFileSystemProvider;
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
/*     */ public final class FilePermission
/*     */   extends Permission
/*     */   implements Serializable
/*     */ {
/*     */   private static final int EXECUTE = 1;
/*     */   private static final int WRITE = 2;
/*     */   private static final int READ = 4;
/*     */   private static final int DELETE = 8;
/*     */   private static final int READLINK = 16;
/*     */   private static final int ALL = 31;
/*     */   private static final int NONE = 0;
/*     */   private transient int mask;
/*     */   private transient boolean directory;
/*     */   private transient boolean recursive;
/*     */   private String actions;
/*     */   private transient String cpath;
/*     */   private transient boolean allFiles;
/*     */   private transient boolean invalid;
/*     */   private static final char RECURSIVE_CHAR = '-';
/*     */   private static final char WILD_CHAR = '*';
/*     */   private static final long serialVersionUID = 7930732926638008763L;
/* 191 */   private static final FileSystem builtInFS = DefaultFileSystemProvider.create()
/* 192 */     .getFileSystem(URI.create("file:///"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void init(int paramInt) {
/* 202 */     if ((paramInt & 0x1F) != paramInt) {
/* 203 */       throw new IllegalArgumentException("invalid actions mask");
/*     */     }
/* 205 */     if (paramInt == 0) {
/* 206 */       throw new IllegalArgumentException("invalid actions mask");
/*     */     }
/* 208 */     if ((this.cpath = getName()) == null) {
/* 209 */       throw new NullPointerException("name can't be null");
/*     */     }
/* 211 */     this.mask = paramInt;
/*     */     
/* 213 */     if (this.cpath.equals("<<ALL FILES>>")) {
/* 214 */       this.allFiles = true;
/* 215 */       this.directory = true;
/* 216 */       this.recursive = true;
/* 217 */       this.cpath = "";
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/* 224 */     if (builtInFS != null) {
/*     */       
/*     */       try {
/* 227 */         String str = this.cpath.endsWith("*") ? (this.cpath.substring(0, this.cpath.length() - 1) + "-") : this.cpath;
/* 228 */         builtInFS.getPath((new File(str)).getPath(), new String[0]);
/* 229 */       } catch (InvalidPathException invalidPathException) {
/* 230 */         this.invalid = true;
/*     */         
/*     */         return;
/*     */       } 
/*     */     }
/*     */     
/* 236 */     this.cpath = AccessController.<String>doPrivileged(new PrivilegedAction<String>() {
/*     */           public String run() {
/*     */             try {
/* 239 */               String str = FilePermission.this.cpath;
/* 240 */               if (FilePermission.this.cpath.endsWith("*")) {
/*     */ 
/*     */ 
/*     */                 
/* 244 */                 str = str.substring(0, str.length() - 1) + "-";
/* 245 */                 str = (new File(str)).getCanonicalPath();
/* 246 */                 return str.substring(0, str.length() - 1) + "*";
/*     */               } 
/* 248 */               return (new File(str)).getCanonicalPath();
/*     */             }
/* 250 */             catch (IOException iOException) {
/* 251 */               return FilePermission.this.cpath;
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/* 256 */     int i = this.cpath.length();
/* 257 */     boolean bool = (i > 0) ? this.cpath.charAt(i - 1) : false;
/*     */     
/* 259 */     if (bool == 45 && this.cpath
/* 260 */       .charAt(i - 2) == File.separatorChar) {
/* 261 */       this.directory = true;
/* 262 */       this.recursive = true;
/* 263 */       this.cpath = this.cpath.substring(0, --i);
/* 264 */     } else if (bool == 42 && this.cpath
/* 265 */       .charAt(i - 2) == File.separatorChar) {
/* 266 */       this.directory = true;
/*     */       
/* 268 */       this.cpath = this.cpath.substring(0, --i);
/*     */     } 
/*     */   }
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
/*     */   public FilePermission(String paramString1, String paramString2) {
/* 309 */     super(paramString1);
/* 310 */     init(getMask(paramString2));
/*     */   }
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
/*     */   FilePermission(String paramString, int paramInt) {
/* 326 */     super(paramString);
/* 327 */     init(paramInt);
/*     */   }
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
/*     */   
/*     */   public boolean implies(Permission paramPermission) {
/* 368 */     if (!(paramPermission instanceof FilePermission)) {
/* 369 */       return false;
/*     */     }
/* 371 */     FilePermission filePermission = (FilePermission)paramPermission;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 376 */     return ((this.mask & filePermission.mask) == filePermission.mask && impliesIgnoreMask(filePermission));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean impliesIgnoreMask(FilePermission paramFilePermission) {
/* 388 */     if (this == paramFilePermission) {
/* 389 */       return true;
/*     */     }
/* 391 */     if (this.allFiles) {
/* 392 */       return true;
/*     */     }
/* 394 */     if (this.invalid || paramFilePermission.invalid) {
/* 395 */       return false;
/*     */     }
/* 397 */     if (paramFilePermission.allFiles) {
/* 398 */       return false;
/*     */     }
/* 400 */     if (this.directory) {
/* 401 */       if (this.recursive) {
/*     */ 
/*     */         
/* 404 */         if (paramFilePermission.directory) {
/* 405 */           return (paramFilePermission.cpath.length() >= this.cpath.length() && paramFilePermission.cpath
/* 406 */             .startsWith(this.cpath));
/*     */         }
/* 408 */         return (paramFilePermission.cpath.length() > this.cpath.length() && paramFilePermission.cpath
/* 409 */           .startsWith(this.cpath));
/*     */       } 
/*     */       
/* 412 */       if (paramFilePermission.directory) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 417 */         if (paramFilePermission.recursive) {
/* 418 */           return false;
/*     */         }
/* 420 */         return this.cpath.equals(paramFilePermission.cpath);
/*     */       } 
/* 422 */       int i = paramFilePermission.cpath.lastIndexOf(File.separatorChar);
/* 423 */       if (i == -1) {
/* 424 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 428 */       return (this.cpath.length() == i + 1 && this.cpath
/* 429 */         .regionMatches(0, paramFilePermission.cpath, 0, i + 1));
/*     */     } 
/*     */ 
/*     */     
/* 433 */     if (paramFilePermission.directory)
/*     */     {
/*     */       
/* 436 */       return false;
/*     */     }
/* 438 */     return this.cpath.equals(paramFilePermission.cpath);
/*     */   }
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
/*     */   public boolean equals(Object paramObject) {
/* 456 */     if (paramObject == this) {
/* 457 */       return true;
/*     */     }
/* 459 */     if (!(paramObject instanceof FilePermission)) {
/* 460 */       return false;
/*     */     }
/* 462 */     FilePermission filePermission = (FilePermission)paramObject;
/*     */     
/* 464 */     if (this.invalid || filePermission.invalid) {
/* 465 */       return false;
/*     */     }
/* 467 */     return (this.mask == filePermission.mask && this.allFiles == filePermission.allFiles && this.cpath
/*     */       
/* 469 */       .equals(filePermission.cpath) && this.directory == filePermission.directory && this.recursive == filePermission.recursive);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 480 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getMask(String paramString) {
/* 490 */     int i = 0;
/*     */ 
/*     */     
/* 493 */     if (paramString == null) {
/* 494 */       return i;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 499 */     if (paramString == "read")
/* 500 */       return 4; 
/* 501 */     if (paramString == "write")
/* 502 */       return 2; 
/* 503 */     if (paramString == "execute")
/* 504 */       return 1; 
/* 505 */     if (paramString == "delete")
/* 506 */       return 8; 
/* 507 */     if (paramString == "readlink") {
/* 508 */       return 16;
/*     */     }
/*     */     
/* 511 */     char[] arrayOfChar = paramString.toCharArray();
/*     */     
/* 513 */     int j = arrayOfChar.length - 1;
/* 514 */     if (j < 0) {
/* 515 */       return i;
/*     */     }
/* 517 */     while (j != -1) {
/*     */       byte b;
/*     */       
/*     */       char c;
/* 521 */       while (j != -1 && ((c = arrayOfChar[j]) == ' ' || c == '\r' || c == '\n' || c == '\f' || c == '\t'))
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 526 */         j--;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 531 */       if (j >= 3 && (arrayOfChar[j - 3] == 'r' || arrayOfChar[j - 3] == 'R') && (arrayOfChar[j - 2] == 'e' || arrayOfChar[j - 2] == 'E') && (arrayOfChar[j - 1] == 'a' || arrayOfChar[j - 1] == 'A') && (arrayOfChar[j] == 'd' || arrayOfChar[j] == 'D')) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 536 */         b = 4;
/* 537 */         i |= 0x4;
/*     */       }
/* 539 */       else if (j >= 4 && (arrayOfChar[j - 4] == 'w' || arrayOfChar[j - 4] == 'W') && (arrayOfChar[j - 3] == 'r' || arrayOfChar[j - 3] == 'R') && (arrayOfChar[j - 2] == 'i' || arrayOfChar[j - 2] == 'I') && (arrayOfChar[j - 1] == 't' || arrayOfChar[j - 1] == 'T') && (arrayOfChar[j] == 'e' || arrayOfChar[j] == 'E')) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 545 */         b = 5;
/* 546 */         i |= 0x2;
/*     */       }
/* 548 */       else if (j >= 6 && (arrayOfChar[j - 6] == 'e' || arrayOfChar[j - 6] == 'E') && (arrayOfChar[j - 5] == 'x' || arrayOfChar[j - 5] == 'X') && (arrayOfChar[j - 4] == 'e' || arrayOfChar[j - 4] == 'E') && (arrayOfChar[j - 3] == 'c' || arrayOfChar[j - 3] == 'C') && (arrayOfChar[j - 2] == 'u' || arrayOfChar[j - 2] == 'U') && (arrayOfChar[j - 1] == 't' || arrayOfChar[j - 1] == 'T') && (arrayOfChar[j] == 'e' || arrayOfChar[j] == 'E')) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 556 */         b = 7;
/* 557 */         i |= 0x1;
/*     */       }
/* 559 */       else if (j >= 5 && (arrayOfChar[j - 5] == 'd' || arrayOfChar[j - 5] == 'D') && (arrayOfChar[j - 4] == 'e' || arrayOfChar[j - 4] == 'E') && (arrayOfChar[j - 3] == 'l' || arrayOfChar[j - 3] == 'L') && (arrayOfChar[j - 2] == 'e' || arrayOfChar[j - 2] == 'E') && (arrayOfChar[j - 1] == 't' || arrayOfChar[j - 1] == 'T') && (arrayOfChar[j] == 'e' || arrayOfChar[j] == 'E')) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 566 */         b = 6;
/* 567 */         i |= 0x8;
/*     */       }
/* 569 */       else if (j >= 7 && (arrayOfChar[j - 7] == 'r' || arrayOfChar[j - 7] == 'R') && (arrayOfChar[j - 6] == 'e' || arrayOfChar[j - 6] == 'E') && (arrayOfChar[j - 5] == 'a' || arrayOfChar[j - 5] == 'A') && (arrayOfChar[j - 4] == 'd' || arrayOfChar[j - 4] == 'D') && (arrayOfChar[j - 3] == 'l' || arrayOfChar[j - 3] == 'L') && (arrayOfChar[j - 2] == 'i' || arrayOfChar[j - 2] == 'I') && (arrayOfChar[j - 1] == 'n' || arrayOfChar[j - 1] == 'N') && (arrayOfChar[j] == 'k' || arrayOfChar[j] == 'K')) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 578 */         b = 8;
/* 579 */         i |= 0x10;
/*     */       }
/*     */       else {
/*     */         
/* 583 */         throw new IllegalArgumentException("invalid permission: " + paramString);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 589 */       boolean bool = false;
/* 590 */       while (j >= b && !bool) {
/* 591 */         switch (arrayOfChar[j - b]) {
/*     */           case ',':
/* 593 */             bool = true; break;
/*     */           case '\t': case '\n': case '\f':
/*     */           case '\r':
/*     */           case ' ':
/*     */             break;
/*     */           default:
/* 599 */             throw new IllegalArgumentException("invalid permission: " + paramString);
/*     */         } 
/*     */         
/* 602 */         j--;
/*     */       } 
/*     */ 
/*     */       
/* 606 */       j -= b;
/*     */     } 
/*     */     
/* 609 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getMask() {
/* 618 */     return this.mask;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getActions(int paramInt) {
/* 629 */     StringBuilder stringBuilder = new StringBuilder();
/* 630 */     boolean bool = false;
/*     */     
/* 632 */     if ((paramInt & 0x4) == 4) {
/* 633 */       bool = true;
/* 634 */       stringBuilder.append("read");
/*     */     } 
/*     */     
/* 637 */     if ((paramInt & 0x2) == 2) {
/* 638 */       if (bool) { stringBuilder.append(','); }
/* 639 */       else { bool = true; }
/* 640 */        stringBuilder.append("write");
/*     */     } 
/*     */     
/* 643 */     if ((paramInt & 0x1) == 1) {
/* 644 */       if (bool) { stringBuilder.append(','); }
/* 645 */       else { bool = true; }
/* 646 */        stringBuilder.append("execute");
/*     */     } 
/*     */     
/* 649 */     if ((paramInt & 0x8) == 8) {
/* 650 */       if (bool) { stringBuilder.append(','); }
/* 651 */       else { bool = true; }
/* 652 */        stringBuilder.append("delete");
/*     */     } 
/*     */     
/* 655 */     if ((paramInt & 0x10) == 16) {
/* 656 */       if (bool) { stringBuilder.append(','); }
/* 657 */       else { bool = true; }
/* 658 */        stringBuilder.append("readlink");
/*     */     } 
/*     */     
/* 661 */     return stringBuilder.toString();
/*     */   }
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
/*     */   public String getActions() {
/* 674 */     if (this.actions == null) {
/* 675 */       this.actions = getActions(this.mask);
/*     */     }
/* 677 */     return this.actions;
/*     */   }
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
/*     */   public PermissionCollection newPermissionCollection() {
/* 712 */     return new FilePermissionCollection();
/*     */   }
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
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 725 */     if (this.actions == null)
/* 726 */       getActions(); 
/* 727 */     paramObjectOutputStream.defaultWriteObject();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 738 */     paramObjectInputStream.defaultReadObject();
/* 739 */     init(getMask(this.actions));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/io/FilePermission.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */