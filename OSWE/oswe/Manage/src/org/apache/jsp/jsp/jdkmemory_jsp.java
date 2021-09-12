/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.jasper.runtime.HttpJspBase;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.tomcat.InstanceManager;
/*     */ 
/*     */ public final class jdkmemory_jsp extends HttpJspBase implements JspSourceDependent
/*     */ {
/*  20 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  29 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  33 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  34 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspDestroy() {}
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  44 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  47 */     JspWriter out = null;
/*  48 */     Object page = this;
/*  49 */     JspWriter _jspx_out = null;
/*  50 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  54 */       response.setContentType("text/html");
/*  55 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  57 */       _jspx_page_context = pageContext;
/*  58 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  59 */       ServletConfig config = pageContext.getServletConfig();
/*  60 */       session = pageContext.getSession();
/*  61 */       out = pageContext.getOut();
/*  62 */       _jspx_out = out;
/*     */       
/*  64 */       out.write("<!--$Id$-->\n<html>\n<head>\n<title>Untitled Document</title>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n<link href=\"../images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n</head>\n\n\n<body>\n");
/*     */       
/*  66 */       String vendor1 = (String)request.getAttribute("vendor");
/*  67 */       boolean isMetaSpace1 = "true".equals((String)request.getAttribute("isMetaspace"));
/*  68 */       pageContext.setAttribute("isMetaSpace1", Boolean.valueOf(isMetaSpace1));
/*  69 */       long mspaceperg = 0L;long ccspaceperg = 0L;
/*  70 */       HashMap memoryProps1 = (HashMap)request.getAttribute("memoryProps");
/*     */       
/*  72 */       long eden = ((Long)memoryProps1.get("eden")).longValue();
/*  73 */       long maxeden = ((Long)memoryProps1.get("maxeden")).longValue();
/*  74 */       long edenper = (memoryProps1.get("edenper") != null) && ((memoryProps1.get("edenper").equals("-")) || (memoryProps1.get("edenper").equals("-1"))) ? 0L : ((Long)memoryProps1.get("edenper")).longValue();
/*  75 */       long survivor = ((Long)memoryProps1.get("survivor")).longValue();
/*  76 */       long maxsurvivor = ((Long)memoryProps1.get("maxsurvivor")).longValue();
/*  77 */       long survivorper = (memoryProps1.get("survivorper") != null) && ((memoryProps1.get("survivorper").equals("-")) || (memoryProps1.get("survivorper").equals("-1"))) ? 0L : ((Long)memoryProps1.get("survivorper")).longValue();
/*  78 */       long tengen = ((Long)memoryProps1.get("tengen")).longValue();
/*  79 */       long maxtengen = ((Long)memoryProps1.get("maxtengen")).longValue();
/*  80 */       long tengenper = (memoryProps1.get("tengenper") != null) && ((memoryProps1.get("tengenper").equals("-")) || (memoryProps1.get("tengenper").equals("-1"))) ? 0L : ((Long)memoryProps1.get("tengenper")).longValue();
/*     */       
/*  82 */       long permgen = ((Long)memoryProps1.get("permgen")).longValue();
/*  83 */       long maxpermgen = ((Long)memoryProps1.get("maxpermgen")).longValue();
/*  84 */       long permgenper = (memoryProps1.get("permgenper") != null) && ((memoryProps1.get("permgenper").equals("-")) || (memoryProps1.get("permgenper").equals("-1"))) ? 0L : ((Long)memoryProps1.get("permgenper")).longValue();
/*  85 */       long codecache = ((Long)memoryProps1.get("codecache")).longValue();
/*  86 */       long maxcodecache = ((Long)memoryProps1.get("maxcodecache")).longValue();
/*  87 */       long codecacheper = (memoryProps1.get("codecacheper") != null) && ((memoryProps1.get("codecacheper").equals("-")) || (memoryProps1.get("codecacheper").equals("-1"))) ? 0L : ((Long)memoryProps1.get("codecacheper")).longValue();
/*  88 */       long permgenro = ((Long)memoryProps1.get("permgenro")).longValue();
/*  89 */       long maxpermgenro = ((Long)memoryProps1.get("maxpermgenro")).longValue();
/*  90 */       long permgenroper = (memoryProps1.get("permgenroper") != null) && ((memoryProps1.get("permgenroper").equals("-")) || (memoryProps1.get("permgenroper").equals("-1"))) ? 0L : ((Long)memoryProps1.get("permgenroper")).longValue();
/*  91 */       long permgenrw = ((Long)memoryProps1.get("permgenrw")).longValue();
/*  92 */       long maxpermgenrw = ((Long)memoryProps1.get("maxpermgenrw")).longValue();
/*  93 */       long permgenrwper = (memoryProps1.get("permgenrwper") != null) && ((memoryProps1.get("permgenrwper").equals("-")) || (memoryProps1.get("permgenrwper").equals("-1"))) ? 0L : ((Long)memoryProps1.get("permgenrwper")).longValue();
/*     */       
/*  95 */       if (isMetaSpace1) {
/*  96 */         mspaceperg = (memoryProps1.get("MSPACEPER") != null) && ((memoryProps1.get("MSPACEPER").equals("-")) || (memoryProps1.get("MSPACEPER").equals("-1"))) ? 0L : ((Long)memoryProps1.get("MSPACEPER")).longValue();
/*  97 */         ccspaceperg = (memoryProps1.get("CCSPACEPER") != null) && ((memoryProps1.get("CCSPACEPER").equals("-")) || (memoryProps1.get("CCSPACEPER").equals("-1"))) ? 0L : ((Long)memoryProps1.get("CCSPACEPER")).longValue();
/*     */       }
/*     */       
/* 100 */       if (vendor1.contains("Sun"))
/*     */       {
/*     */ 
/* 103 */         out.write("\t\n<table height=\"135\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n  <tr>\n   \n    <td> \n      <table width=\"100%\" border=\"0\" cellspacing=\"3\" cellpadding=\"0\">\n      <tr>\n      <td height=\"20px\">&nbsp;</td>\n      <td><span class=\"bodytext\">ES</span></td>\n      <td><span class=\"bodytext\">SS</span></td>\n      <td><span class=\"bodytext\">TG</span></td>\n      </tr>\n        <tr>\n       \t<td align=\"right\"><img src=\"/images/percentages.gif\"></td>\t\n          <td background=\"../images/bg_unusedjvm.gif\" height=\"116\" valign=\"bottom\" title=\"");
/* 104 */         out.print(FormatUtil.getString("am.webclient.jdk15.eden.text"));
/* 105 */         out.write(32);
/* 106 */         out.write(45);
/* 107 */         out.write(32);
/* 108 */         out.print(edenper);
/* 109 */         out.write("%\">\n\t  <table height=\"");
/* 110 */         out.print(edenper * 116L / 100L);
/* 111 */         out.write("px\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n              <tr>\n                <td background=\"../images/bg_usedjvm.gif\" width=\"21\"><!--img src=\"../images/spacer.gif\" width=\"21\" height=\"1\"--></td>\n              </tr>\n            </table>\n\t  </td>\n          <td background=\"../images/bg_unusedjvm.gif\" valign=\"bottom\" height=\"116\" title=\"");
/* 112 */         out.print(FormatUtil.getString("am.webclient.jdk15.survivor.text"));
/* 113 */         out.write(32);
/* 114 */         out.write(45);
/* 115 */         out.write(32);
/* 116 */         out.print(survivorper);
/* 117 */         out.write("%\">\n\t  <table height=\"");
/* 118 */         out.print(survivorper * 116L / 100L);
/* 119 */         out.write("px\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n              <tr>\n                <td background=\"../images/bg_usedjvm.gif\" width=\"21\"><!--img src=\"../images/spacer.gif\" width=\"21\" height=\"1\"--></td>\n              </tr>\n            </table>\n\t    </td>\n          <td background=\"../images/bg_unusedjvm.gif\" valign=\"bottom\" height=\"116\" title=\"");
/* 120 */         out.print(FormatUtil.getString("am.webclient.jdk15.tenuredgen.text"));
/* 121 */         out.write(32);
/* 122 */         out.write(45);
/* 123 */         out.write(32);
/* 124 */         out.print(tengenper);
/* 125 */         out.write("%\">\n\t  <table height=\"");
/* 126 */         out.print(tengenper * 116L / 100L);
/* 127 */         out.write("px\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n              <tr>\n                <td background=\"../images/bg_usedjvm.gif\" width=\"21\"><!--img src=\"../images/spacer.gif\" width=\"21\" height=\"1\"--></td>\n              </tr>\n            </table>\n          </td>\n        </tr>\n        <tr align=\"center\">\n       \t<td><br></td>\t\n          <td colspan=\"3\" height=\"25\" class=\"columnheadingb\">");
/* 128 */         out.print(FormatUtil.getString("am.webclient.jdk15.heap.text"));
/* 129 */         out.write("</td>\n        </tr>\n      </table></td>\n    <td>&nbsp;</td>\n    <td> \n      <table width=\"100%\" border=\"0\" cellspacing=\"3\" cellpadding=\"0\">\n      <tr>\n            <td height=\"20px\"><span class=\"bodytext\">CC</span></td>");
/* 130 */         out.write("\n        \t<c:if test='${isMetaSpace1}'>\n            <td><span class=\"bodytext\">MS</span></td>\n            <td><span class=\"bodytext\">CCS</span></td>\n            </c:if><c:if test='${not isMetaSpace1}'>\n            <td><span class=\"bodytext\">PG</span></td>");
/* 131 */         out.write("\n            <td><span class=\"bodytext\">RO</span></td>\n            <td><span class=\"bodytext\">RW</span></td>\n            </c:if>\n      </tr>\n        <tr> \n          <td background=\"../images/bg_unusedjvm.gif\" valign=\"bottom\" height=\"116\" title=\"");
/* 132 */         out.print(FormatUtil.getString("am.webclient.jdk15.codecache.text"));
/* 133 */         out.write(32);
/* 134 */         out.write(45);
/* 135 */         out.write(32);
/* 136 */         out.print(codecacheper);
/* 137 */         out.write("%\">\n\t  <table height=\"");
/* 138 */         out.print(codecacheper * 116L / 100L);
/* 139 */         out.write("px\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n              <tr>\n                <td background=\"../images/bg_usedjvm.gif\" width=\"21\"><!--img src=\"../images/spacer.gif\" width=\"21\" height=\"1\"--></td>\n              </tr>\n            </table>\n\t  </td>\n\t  \n\t <c:if test='${isMetaSpace1}'>\n       \n\t\t<td background=\"../images/bg_unusedjvm.gif\" valign=\"bottom\" height=\"116\" title=\"");
/* 140 */         out.print(FormatUtil.getString("am.webclient.jdk15.metaspace.text"));
/* 141 */         out.write(32);
/* 142 */         out.write(45);
/* 143 */         out.write(32);
/* 144 */         out.print(mspaceperg);
/* 145 */         out.write("%\">\n\t\t<table height=\"");
/* 146 */         out.print(permgenper * 116L / 100L);
/* 147 */         out.write("px\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n\t\t<tr>\n\t\t  <td background=\"../images/bg_usedjvm.gif\" width=\"21\"><!--img src=\"../images/spacer.gif\" width=\"21\" height=\"1\"--></td>\n\t\t           </tr>\n\t\t         </table>\n\t\t</td>\n \n\t\t<td background=\"../images/bg_unusedjvm.gif\" valign=\"bottom\" height=\"116\" title=\"");
/* 148 */         out.print(FormatUtil.getString("am.webclient.jdk15.Compressed.text"));
/* 149 */         out.write(32);
/* 150 */         out.write(45);
/* 151 */         out.write(32);
/* 152 */         out.print(ccspaceperg);
/* 153 */         out.write("%\">\n\t\t<table height=\"");
/* 154 */         out.print(permgenper * 116L / 100L);
/* 155 */         out.write("px\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n\t\t<tr>\n\t\t  <td background=\"../images/bg_usedjvm.gif\" width=\"21\"><!--img src=\"../images/spacer.gif\" width=\"21\" height=\"1\"--></td>\n\t\t           </tr>\n\t\t         </table>\n\t\t</td>\n       \n       </c:if><c:if test='${not isMetaSpace1}'>\n\t  <td background=\"../images/bg_unusedjvm.gif\" valign=\"bottom\" height=\"116\" title=\"");
/* 156 */         out.print(FormatUtil.getString("am.webclient.jdk15.permgen.text"));
/* 157 */         out.write(32);
/* 158 */         out.write(45);
/* 159 */         out.write(32);
/* 160 */         out.print(permgenper);
/* 161 */         out.write("%\">\n\t  <table height=\"");
/* 162 */         out.print(permgenper * 116L / 100L);
/* 163 */         out.write("px\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n              <tr>\n                <td background=\"../images/bg_usedjvm.gif\" width=\"21\"><!--img src=\"../images/spacer.gif\" width=\"21\" height=\"1\"--></td>\n              </tr>\n            </table>\n\t  </td>\n\t  ");
/*     */         
/* 165 */         if (permgenro != -1L)
/*     */         {
/* 167 */           out.write("\n          <td background=\"../images/bg_unusedjvm.gif\" valign=\"bottom\" height=\"116\" title=\"");
/* 168 */           out.print(FormatUtil.getString("am.webclient.jdk15.permgenro.text"));
/* 169 */           out.write(32);
/* 170 */           out.write(45);
/* 171 */           out.write(32);
/* 172 */           out.print(permgenroper);
/* 173 */           out.write("%\">\n\t  <table height=\"");
/* 174 */           out.print(permgenroper * 116L / 100L);
/* 175 */           out.write("px\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n              <tr>\n                <td background=\"../images/bg_usedjvm.gif\" width=\"21\"><!--img src=\"../images/spacer.gif\" width=\"21\" height=\"1\"--></td>\n              </tr>\n            </table>\n\t  </td>\n          <td background=\"../images/bg_unusedjvm.gif\" valign=\"bottom\" height=\"116\" title=\"");
/* 176 */           out.print(FormatUtil.getString("am.webclient.jdk15.permgenrw.text"));
/* 177 */           out.write(32);
/* 178 */           out.write(45);
/* 179 */           out.write(32);
/* 180 */           out.print(permgenrwper);
/* 181 */           out.write("%\">\n\t  <table height=\"");
/* 182 */           out.print(permgenrwper * 116L / 100L);
/* 183 */           out.write("px\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n              <tr>\n                <td background=\"../images/bg_usedjvm.gif\" width=\"21\"><!--img src=\"../images/spacer.gif\" width=\"21\" height=\"1\"--></td>\n              </tr>\n            </table>\n\t  </td>\n\t  ");
/*     */         }
/* 185 */         out.write("\n\t  </c:if>\n        </tr>\n        <tr align=\"center\"> \n          <td height=\"25\" colspan=\"4\" class=\"columnheadingb\">");
/* 186 */         out.print(FormatUtil.getString("am.webclient.jdk15.nonheap.text"));
/* 187 */         out.write("</td>\n        </tr>\n      </table></td>\n  </tr>\n</table>\n\n");
/* 188 */       } else if (vendor1.contains("IBM")) {
/* 189 */         long perjitcache = ((Long)memoryProps1.get("PERJITCCACHE")).longValue();
/* 190 */         long perjitdcache = ((Long)memoryProps1.get("PERJITDCACHE")).longValue();
/* 191 */         long perclassstor = ((Long)memoryProps1.get("PERCLASSSTOR")).longValue();
/* 192 */         long pernonhpstor = ((Long)memoryProps1.get("PERNONHPSTOR")).longValue();
/* 193 */         long perjavaheap = ((Long)memoryProps1.get("PERJAVAHEAP")).longValue();
/*     */         
/* 195 */         out.write("\t\n\n\n<table height=\"135\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n  <tr>\n   \n    <td> \n      <table width=\"100%\" border=\"0\" cellspacing=\"3\" cellpadding=\"0\">\n      <tr>\n      <td height=\"20px\">&nbsp;</td>\n      <td><span class=\"bodytext\">JH</span></td>");
/* 196 */         out.write("\n \n      </tr>\n        <tr>\n       \t<td align=\"right\"><img src=\"/images/percentages.gif\"></td>\t\n          <td background=\"../images/bg_unusedjvm.gif\" height=\"116\" valign=\"bottom\" title=\"");
/* 197 */         out.print(FormatUtil.getString("am.webclient.javaruntime.javaheap.text1"));
/* 198 */         out.write(32);
/* 199 */         out.write(45);
/* 200 */         out.write(32);
/* 201 */         out.print(perjavaheap);
/* 202 */         out.write("%\">\n\t       <table height=\"");
/* 203 */         out.print(perjavaheap * 116L / 100L);
/* 204 */         out.write("px\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n              <tr>\n                <td background=\"../images/bg_usedjvm.gif\" width=\"21\"><!--img src=\"../images/spacer.gif\" width=\"21\" height=\"1\"--></td>\n              </tr>\n            </table>\n\t  </td>\n        </tr>\n        <tr align=\"center\">\n       \t<td><br></td>\t\n          <td colspan=\"3\" height=\"25\" class=\"columnheadingb\">");
/* 205 */         out.print(FormatUtil.getString("am.webclient.jdk15.heap.text"));
/* 206 */         out.write("</td>\n        </tr>\n      </table></td>\n    <td>&nbsp;</td>\n    <td> \n      <table width=\"100%\" border=\"0\" cellspacing=\"3\" cellpadding=\"0\">\n      <tr>\n            <td height=\"20px\"><span class=\"bodytext\">JCC</span></td>");
/* 207 */         out.write("\n            <td><span class=\"bodytext\">JDC</span></td>");
/* 208 */         out.write("\n            <td><span class=\"bodytext\">CS</span></td>");
/* 209 */         out.write("\n            <td><span class=\"bodytext\">MS</span></td>");
/* 210 */         out.write("\n      </tr>\n        <tr> \n          <td background=\"../images/bg_unusedjvm.gif\" valign=\"bottom\" height=\"116\" title=\"");
/* 211 */         out.print(FormatUtil.getString("am.webclient.javaruntime.jitcodecache.text"));
/* 212 */         out.write(32);
/* 213 */         out.write(45);
/* 214 */         out.write(32);
/* 215 */         out.print(perjitcache);
/* 216 */         out.write("%\">\n\t  <table height=\"");
/* 217 */         out.print(perjitcache * 116L / 100L);
/* 218 */         out.write("px\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n              <tr>\n                <td background=\"../images/bg_usedjvm.gif\" width=\"21\"><!--img src=\"../images/spacer.gif\" width=\"21\" height=\"1\"--></td>\n              </tr>\n            </table>\n\t  </td>\n          <td background=\"../images/bg_unusedjvm.gif\" valign=\"bottom\" height=\"116\" title=\"");
/* 219 */         out.print(FormatUtil.getString("am.webclient.javaruntime.jitdatacache.text"));
/* 220 */         out.write(32);
/* 221 */         out.write(45);
/* 222 */         out.write(32);
/* 223 */         out.print(perjitdcache);
/* 224 */         out.write("%\">\n\t  <table height=\"");
/* 225 */         out.print(perjitdcache * 116L / 100L);
/* 226 */         out.write("px\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n              <tr>\n                <td background=\"../images/bg_usedjvm.gif\" width=\"21\"><!--img src=\"../images/spacer.gif\" width=\"21\" height=\"1\"--></td>\n              </tr>\n            </table>\n\t  </td>\n\t  \n          <td background=\"../images/bg_unusedjvm.gif\" valign=\"bottom\" height=\"116\" title=\"");
/* 227 */         out.print(FormatUtil.getString("am.webclient.javaruntime.classstorage.text"));
/* 228 */         out.write(32);
/* 229 */         out.write(45);
/* 230 */         out.write(32);
/* 231 */         out.print(perclassstor);
/* 232 */         out.write("%\">\n\t  <table height=\"");
/* 233 */         out.print(perclassstor * 116L / 100L);
/* 234 */         out.write("px\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n              <tr>\n                <td background=\"../images/bg_usedjvm.gif\" width=\"21\"><!--img src=\"../images/spacer.gif\" width=\"21\" height=\"1\"--></td>\n              </tr>\n            </table>\n\t  </td>\n          <td background=\"../images/bg_unusedjvm.gif\" valign=\"bottom\" height=\"116\" title=\"");
/* 235 */         out.print(FormatUtil.getString("am.webclient.javaruntime.nonheapstorage.text"));
/* 236 */         out.write(32);
/* 237 */         out.write(45);
/* 238 */         out.write(32);
/* 239 */         out.print(pernonhpstor);
/* 240 */         out.write("%\">\n\t  <table height=\"");
/* 241 */         out.print(pernonhpstor * 116L / 100L);
/* 242 */         out.write("px\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n              <tr>\n                <td background=\"../images/bg_usedjvm.gif\" width=\"21\"><!--img src=\"../images/spacer.gif\" width=\"21\" height=\"1\"--></td>\n              </tr>\n            </table>\n\t  </td>\n\t  \n        </tr>\n        <tr align=\"center\"> \n          <td height=\"25\" colspan=\"4\" class=\"columnheadingb\">");
/* 243 */         out.print(FormatUtil.getString("am.webclient.jdk15.nonheap.text"));
/* 244 */         out.write("</td>\n        </tr>\n      </table></td>\n  </tr>\n</table>\n\n\n\n");
/* 245 */       } else if ((vendor1.contains("BEA")) || (vendor1.contains("Oracle"))) {
/* 246 */         long perjjitcache = ((Long)memoryProps1.get("PERJJAVAHEAP")).longValue();
/* 247 */         long pernursery = ((Long)memoryProps1.get("PERNURSERY")).longValue();
/* 248 */         long peroldspace = ((Long)memoryProps1.get("PEROLDSPACE")).longValue();
/* 249 */         long perclassmem = ((Long)memoryProps1.get("PERCLASSMEM")).longValue();
/* 250 */         long perclassblock = ((Long)memoryProps1.get("PERCLASSBLOCK")).longValue();
/* 251 */         long nonheapper = (memoryProps1.get("nonheapper") != null) && ((memoryProps1.get("nonheapper").equals("-")) || (memoryProps1.get("nonheapper").equals("-1"))) ? 0L : ((Long)memoryProps1.get("nonheapper")).longValue();
/*     */         
/* 253 */         out.write("\n\n<table height=\"135\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n  <tr>\n   \n    <td> \n      <table width=\"100%\" border=\"0\" cellspacing=\"3\" cellpadding=\"0\">\n      <tr>\n      <td height=\"20px\">&nbsp;</td>\n      <td><span class=\"bodytext\">JH</span></td>");
/* 254 */         out.write("\n      <td><span class=\"bodytext\">NU</span></td>");
/* 255 */         out.write("\n      <td><span class=\"bodytext\">OS</span></td>");
/* 256 */         out.write("\n      </tr>\n        <tr>\n       \t<td align=\"right\"><img src=\"/images/percentages.gif\"></td>\t\n          <td background=\"../images/bg_unusedjvm.gif\" height=\"116\" valign=\"bottom\" title=\"");
/* 257 */         out.print(FormatUtil.getString("am.webclient.javaruntime.javaheap.text1"));
/* 258 */         out.write(32);
/* 259 */         out.write(45);
/* 260 */         out.write(32);
/* 261 */         out.print(perjjitcache);
/* 262 */         out.write("%\">\n\t       <table height=\"");
/* 263 */         out.print(perjjitcache * 116L / 100L);
/* 264 */         out.write("px\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n              <tr>\n                <td background=\"../images/bg_usedjvm.gif\" width=\"21\"><!--img src=\"../images/spacer.gif\" width=\"21\" height=\"1\"--></td>\n              </tr>\n            </table>\n\t  </td>\n\t  <td background=\"../images/bg_unusedjvm.gif\" height=\"116\" valign=\"bottom\" title=\"");
/* 265 */         out.print(FormatUtil.getString("am.webclient.javaruntime.nursery.text"));
/* 266 */         out.write(32);
/* 267 */         out.write(45);
/* 268 */         out.write(32);
/* 269 */         out.print(pernursery);
/* 270 */         out.write("%\">\n\t       <table height=\"");
/* 271 */         out.print(pernursery * 116L / 100L);
/* 272 */         out.write("px\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n\t\t<tr>\n\t\t  <td background=\"../images/bg_usedjvm.gif\" width=\"21\"><!--img src=\"../images/spacer.gif\" width=\"21\" height=\"1\"--></td>\n\t\t</tr>\n\t      </table>\n\t  </td>\n\t  <td background=\"../images/bg_unusedjvm.gif\" height=\"116\" valign=\"bottom\" title=\"");
/* 273 */         out.print(FormatUtil.getString("am.webclient.javaruntime.oldspace.text"));
/* 274 */         out.write(32);
/* 275 */         out.write(45);
/* 276 */         out.write(32);
/* 277 */         out.print(peroldspace);
/* 278 */         out.write("%\">\n\t       <table height=\"");
/* 279 */         out.print(peroldspace * 116L / 100L);
/* 280 */         out.write("px\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n\t\t<tr>\n\t\t  <td background=\"../images/bg_usedjvm.gif\" width=\"21\"><!--img src=\"../images/spacer.gif\" width=\"21\" height=\"1\"--></td>\n\t\t</tr>\n\t      </table>\n\t  </td>\n        </tr>\n        <tr align=\"center\">\n       \t<td><br></td>\t\n          <td colspan=\"3\" height=\"25\" class=\"columnheadingb\">");
/* 281 */         out.print(FormatUtil.getString("am.webclient.jdk15.heap.text"));
/* 282 */         out.write("</td>\n        </tr>\n      </table></td>\n    \n    <td> \n      <table width=\"100%\" border=\"0\" cellspacing=\"3\" cellpadding=\"0\">\n      <tr>\n            <td height=\"20px\"><span class=\"bodytext\">NHM</span></td>");
/* 283 */         out.write("\n            <td height=\"20px\"><span class=\"bodytext\">CM</span></td>");
/* 284 */         out.write("\n            <td height=\"20px\"><span class=\"bodytext\">CB</span></td>");
/* 285 */         out.write("\n      </tr>\n        <tr> \n          <td background=\"../images/bg_unusedjvm.gif\" valign=\"bottom\" height=\"116\" title=\"");
/* 286 */         out.print(FormatUtil.getString("am.webclient.jdk15.nonheap.text"));
/* 287 */         out.write(32);
/* 288 */         out.write(45);
/* 289 */         out.write(32);
/* 290 */         out.print(nonheapper);
/* 291 */         out.write("%\">\n\t  <table height=\"");
/* 292 */         out.print(nonheapper * 116L / 100L);
/* 293 */         out.write("px\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n              <tr>\n                <td background=\"../images/bg_usedjvm.gif\" width=\"21\"><!--img src=\"../images/spacer.gif\" width=\"21\" height=\"1\"--></td>\n              </tr>\n            </table>\n\t  </td>\n\t  <td background=\"../images/bg_unusedjvm.gif\" valign=\"bottom\" height=\"116\" title=\"");
/* 294 */         out.print(FormatUtil.getString("am.webclient.jdk15.classmemory.text"));
/* 295 */         out.write(32);
/* 296 */         out.write(45);
/* 297 */         out.write(32);
/* 298 */         out.print(perclassmem);
/* 299 */         out.write("%\">\n\t    <table height=\"");
/* 300 */         out.print(perclassmem * 116L / 100L);
/* 301 */         out.write("px\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n\t\t<tr>\n\t\t  <td background=\"../images/bg_usedjvm.gif\" width=\"21\"><!--img src=\"../images/spacer.gif\" width=\"21\" height=\"1\"--></td>\n\t\t</tr>\n\t    </table>\n\t  </td>\n\t  <td background=\"../images/bg_unusedjvm.gif\" valign=\"bottom\" height=\"116\" title=\"");
/* 302 */         out.print(FormatUtil.getString("am.webclient.jdk15.classblock.text"));
/* 303 */         out.write(32);
/* 304 */         out.write(45);
/* 305 */         out.write(32);
/* 306 */         out.print(perclassblock);
/* 307 */         out.write("%\">\n\t    <table height=\"");
/* 308 */         out.print(perclassblock * 116L / 100L);
/* 309 */         out.write("px\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n\t\t<tr>\n\t\t  <td background=\"../images/bg_usedjvm.gif\" width=\"21\"><!--img src=\"../images/spacer.gif\" width=\"21\" height=\"1\"--></td>\n\t\t</tr>\n\t    </table>\n\t  </td>\n        </tr>\n        <tr align=\"center\"> \n          <td height=\"25\" colspan=\"4\" class=\"columnheadingb\">");
/* 310 */         out.print(FormatUtil.getString("am.webclient.jdk15.nonheap.text"));
/* 311 */         out.write("</td>\n        </tr>\n      </table></td>\n  </tr>\n</table>\n\n");
/*     */       }
/* 313 */       out.write("\n</body>\n</html>\n");
/*     */     } catch (Throwable t) {
/* 315 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 316 */         out = _jspx_out;
/* 317 */         if ((out != null) && (out.getBufferSize() != 0))
/* 318 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 319 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 322 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\jdkmemory_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */