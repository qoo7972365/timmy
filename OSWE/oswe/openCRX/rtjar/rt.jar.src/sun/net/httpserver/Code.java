/*     */ package sun.net.httpserver;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Code
/*     */ {
/*     */   public static final int HTTP_CONTINUE = 100;
/*     */   public static final int HTTP_OK = 200;
/*     */   public static final int HTTP_CREATED = 201;
/*     */   public static final int HTTP_ACCEPTED = 202;
/*     */   public static final int HTTP_NOT_AUTHORITATIVE = 203;
/*     */   public static final int HTTP_NO_CONTENT = 204;
/*     */   public static final int HTTP_RESET = 205;
/*     */   public static final int HTTP_PARTIAL = 206;
/*     */   public static final int HTTP_MULT_CHOICE = 300;
/*     */   public static final int HTTP_MOVED_PERM = 301;
/*     */   public static final int HTTP_MOVED_TEMP = 302;
/*     */   public static final int HTTP_SEE_OTHER = 303;
/*     */   public static final int HTTP_NOT_MODIFIED = 304;
/*     */   public static final int HTTP_USE_PROXY = 305;
/*     */   public static final int HTTP_BAD_REQUEST = 400;
/*     */   public static final int HTTP_UNAUTHORIZED = 401;
/*     */   public static final int HTTP_PAYMENT_REQUIRED = 402;
/*     */   public static final int HTTP_FORBIDDEN = 403;
/*     */   public static final int HTTP_NOT_FOUND = 404;
/*     */   public static final int HTTP_BAD_METHOD = 405;
/*     */   public static final int HTTP_NOT_ACCEPTABLE = 406;
/*     */   public static final int HTTP_PROXY_AUTH = 407;
/*     */   public static final int HTTP_CLIENT_TIMEOUT = 408;
/*     */   public static final int HTTP_CONFLICT = 409;
/*     */   public static final int HTTP_GONE = 410;
/*     */   public static final int HTTP_LENGTH_REQUIRED = 411;
/*     */   public static final int HTTP_PRECON_FAILED = 412;
/*     */   public static final int HTTP_ENTITY_TOO_LARGE = 413;
/*     */   public static final int HTTP_REQ_TOO_LONG = 414;
/*     */   public static final int HTTP_UNSUPPORTED_TYPE = 415;
/*     */   public static final int HTTP_INTERNAL_ERROR = 500;
/*     */   public static final int HTTP_NOT_IMPLEMENTED = 501;
/*     */   public static final int HTTP_BAD_GATEWAY = 502;
/*     */   public static final int HTTP_UNAVAILABLE = 503;
/*     */   public static final int HTTP_GATEWAY_TIMEOUT = 504;
/*     */   public static final int HTTP_VERSION = 505;
/*     */   
/*     */   static String msg(int paramInt) {
/*  69 */     switch (paramInt) { case 200:
/*  70 */         return " OK";
/*  71 */       case 100: return " Continue";
/*  72 */       case 201: return " Created";
/*  73 */       case 202: return " Accepted";
/*  74 */       case 203: return " Non-Authoritative Information";
/*  75 */       case 204: return " No Content";
/*  76 */       case 205: return " Reset Content";
/*  77 */       case 206: return " Partial Content";
/*  78 */       case 300: return " Multiple Choices";
/*  79 */       case 301: return " Moved Permanently";
/*  80 */       case 302: return " Temporary Redirect";
/*  81 */       case 303: return " See Other";
/*  82 */       case 304: return " Not Modified";
/*  83 */       case 305: return " Use Proxy";
/*  84 */       case 400: return " Bad Request";
/*  85 */       case 401: return " Unauthorized";
/*  86 */       case 402: return " Payment Required";
/*  87 */       case 403: return " Forbidden";
/*  88 */       case 404: return " Not Found";
/*  89 */       case 405: return " Method Not Allowed";
/*  90 */       case 406: return " Not Acceptable";
/*  91 */       case 407: return " Proxy Authentication Required";
/*  92 */       case 408: return " Request Time-Out";
/*  93 */       case 409: return " Conflict";
/*  94 */       case 410: return " Gone";
/*  95 */       case 411: return " Length Required";
/*  96 */       case 412: return " Precondition Failed";
/*  97 */       case 413: return " Request Entity Too Large";
/*  98 */       case 414: return " Request-URI Too Large";
/*  99 */       case 415: return " Unsupported Media Type";
/* 100 */       case 500: return " Internal Server Error";
/* 101 */       case 501: return " Not Implemented";
/* 102 */       case 502: return " Bad Gateway";
/* 103 */       case 503: return " Service Unavailable";
/* 104 */       case 504: return " Gateway Timeout";
/* 105 */       case 505: return " HTTP Version Not Supported"; }
/* 106 */      return " ";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/httpserver/Code.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */