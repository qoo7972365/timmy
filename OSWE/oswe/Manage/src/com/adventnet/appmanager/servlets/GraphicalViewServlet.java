/*     */ package com.adventnet.appmanager.servlets;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.fault.FaultUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Properties;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ 
/*     */ public class GraphicalViewServlet extends HttpServlet
/*     */ {
/*  21 */   private ServletContext servletContext = null;
/*  22 */   private ServletConfig config = null;
/*     */   
/*     */   public void init(ServletConfig sConfig) throws ServletException {
/*  25 */     super.init(sConfig);
/*  26 */     this.servletContext = sConfig.getServletContext();
/*     */   }
/*     */   
/*     */ 
/*     */   public void doPost(HttpServletRequest request, HttpServletResponse response)
/*     */     throws ServletException, IOException
/*     */   {
/*  33 */     doGet(request, response);
/*     */   }
/*     */   
/*     */ 
/*     */   public void doGet(HttpServletRequest request, HttpServletResponse response)
/*     */     throws ServletException, IOException
/*     */   {
/*     */     try
/*     */     {
/*  42 */       PrintWriter out = response.getWriter();
/*  43 */       response.setContentType("text/xml; charset=UTF-8");
/*     */       
/*  45 */       String haid = request.getParameter("haid");
/*  46 */       String groupName = request.getParameter("groupName");
/*  47 */       String haStatus = request.getParameter("healthStatus");
/*  48 */       Hashtable healthkeys = (Hashtable)request.getSession().getServletContext().getAttribute("healthkeys");
/*     */       
/*  50 */       ArrayList resIdList = new ArrayList();
/*  51 */       ArrayList attIdList = new ArrayList();
/*  52 */       HashMap map = new HashMap();
/*  53 */       ArrayList childList = new ArrayList();
/*  54 */       AMConnectionPool pool = AMConnectionPool.getInstance();
/*     */       
/*  56 */       if (request.getParameter("expandOnClick") != null)
/*     */       {
/*  58 */         String childXML = request.getParameter("childXML");
/*     */         
/*  60 */         String query = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.TYPE,AM_ManagedResourceType.IMAGEPATH from AM_PARENTCHILDMAPPER,AM_ManagedObject,AM_ManagedResourceType where AM_ManagedResourceType.IMAGEPATH=AM_ManagedObject.TYPE and  AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and AM_PARENTCHILDMAPPER.PARENTID=" + haid;
/*  61 */         AMConnectionPool.getInstance();ResultSet rs = AMConnectionPool.executeQueryStmt(query);
/*  62 */         while (rs.next())
/*     */         {
/*  64 */           ArrayList list = new ArrayList();
/*  65 */           list.add(rs.getString("RESOURCEID"));
/*  66 */           list.add(rs.getString("DISPLAYNAME"));
/*  67 */           list.add(rs.getString("TYPE"));
/*  68 */           list.add(rs.getString("IMAGEPATH"));
/*     */           
/*  70 */           resIdList.add(rs.getString("RESOURCEID"));
/*  71 */           attIdList.add(healthkeys.get(rs.getString("TYPE")));
/*     */           
/*  73 */           childList.add(list);
/*     */         }
/*  75 */         rs.close();
/*  76 */         map.put(groupName, childList);
/*  77 */         generateXML(map, groupName, resIdList, attIdList, healthkeys, haStatus, haid, childXML, out);
/*     */       }
/*     */       else
/*     */       {
/*  81 */         String query = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.TYPE,AM_ManagedResourceType.IMAGEPATH from AM_PARENTCHILDMAPPER,AM_ManagedObject,AM_ManagedResourceType where AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and AM_PARENTCHILDMAPPER.PARENTID=" + haid;
/*     */         
/*  83 */         AMConnectionPool.getInstance();ResultSet rs = AMConnectionPool.executeQueryStmt(query);
/*  84 */         while (rs.next())
/*     */         {
/*  86 */           ArrayList list = new ArrayList();
/*  87 */           list.add(rs.getString("RESOURCEID"));
/*  88 */           list.add(rs.getString("DISPLAYNAME"));
/*  89 */           list.add(rs.getString("TYPE"));
/*  90 */           list.add(rs.getString("IMAGEPATH"));
/*     */           
/*  92 */           resIdList.add(rs.getString("RESOURCEID"));
/*  93 */           attIdList.add(healthkeys.get(rs.getString("TYPE")));
/*     */           
/*  95 */           childList.add(list);
/*     */           
/*  97 */           if (rs.getString("TYPE").equals("HAI"))
/*     */           {
/*  99 */             checkForChildsAndAddToMap(map, rs.getString("RESOURCEID"), rs.getString("DISPLAYNAME"), resIdList, attIdList, healthkeys);
/*     */           }
/*     */         }
/* 102 */         rs.close();
/* 103 */         map.put(haid, childList);
/* 104 */         generateFullXML(map, groupName, resIdList, attIdList, healthkeys, haStatus, haid, out);
/*     */       }
/*     */       try
/*     */       {
/* 108 */         out.close();
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 112 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 117 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public void checkForChildsAndAddToMap(HashMap map, String haid, String groupName, ArrayList resIdList, ArrayList attIdList, Hashtable healthkeys)
/*     */   {
/*     */     try
/*     */     {
/* 125 */       ArrayList childList = new ArrayList();
/* 126 */       String query = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.TYPE,AM_ManagedResourceType.IMAGEPATH from AM_PARENTCHILDMAPPER,AM_ManagedObject,AM_ManagedResourceType where AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and AM_PARENTCHILDMAPPER.PARENTID=" + haid;
/*     */       
/* 128 */       AMConnectionPool.getInstance();ResultSet rs = AMConnectionPool.executeQueryStmt(query);
/* 129 */       while (rs.next())
/*     */       {
/* 131 */         ArrayList list = new ArrayList();
/* 132 */         list.add(rs.getString("RESOURCEID"));
/* 133 */         list.add(rs.getString("DISPLAYNAME"));
/* 134 */         list.add(rs.getString("TYPE"));
/* 135 */         list.add(rs.getString("IMAGEPATH"));
/* 136 */         resIdList.add(rs.getString("RESOURCEID"));
/* 137 */         attIdList.add(healthkeys.get(rs.getString("TYPE")));
/* 138 */         childList.add(list);
/*     */         
/* 140 */         if (rs.getString("TYPE").equals("HAI"))
/*     */         {
/* 142 */           checkForChildsAndAddToMap(map, rs.getString("RESOURCEID"), rs.getString("DISPLAYNAME"), resIdList, attIdList, healthkeys);
/*     */         }
/*     */       }
/* 145 */       rs.close();
/* 146 */       map.put(haid, childList);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 150 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void generateFullXML(HashMap map, String rootName, ArrayList resIdList, ArrayList attIdList, Hashtable healthkeys, String haStatus, String groupId, PrintWriter out)
/*     */   {
/* 157 */     out.println("<Groups>");
/* 158 */     if (haStatus == null)
/*     */     {
/* 160 */       haStatus = "0";
/*     */     }
/* 162 */     String tempType1 = "HAI";
/* 163 */     String imagePath1 = "/fl//images/icon_monitors_app.gif";
/* 164 */     out.println("<Monitor-Group type='" + tempType1 + "' name='" + rootName + "' id='" + groupId + "' imagepath='" + imagePath1 + "' severity='" + haStatus + "'>");
/* 165 */     Properties alert = FaultUtil.getStatus(resIdList, attIdList);
/* 166 */     System.out.println("ALLLLLLLLLLLLLLEEEEEE " + alert);
/*     */     
/* 168 */     ArrayList childList = new ArrayList();
/* 169 */     childList = (ArrayList)map.get(groupId);
/*     */     
/* 171 */     int level = 1;
/* 172 */     for (int i = 0; i < childList.size(); i++)
/*     */     {
/* 174 */       ArrayList elementList = (ArrayList)childList.get(i);
/* 175 */       String resId = (String)elementList.get(0);
/* 176 */       String dispName = (String)elementList.get(1);
/* 177 */       String type = (String)elementList.get(2);
/* 178 */       String imagepath = "/fl/" + (String)elementList.get(3);
/* 179 */       String severity = alert.getProperty(resId + "#" + healthkeys.get(type));
/* 180 */       String messageKey = resId + "#" + healthkeys.get(type) + "#MESSAGE";
/* 181 */       String message = (String)alert.get(messageKey);
/* 182 */       if (message != null)
/*     */       {
/* 184 */         message = message.replaceAll("<", "&lt;");
/*     */       }
/*     */       
/* 187 */       if (severity == null)
/*     */       {
/* 189 */         severity = "0";
/*     */       }
/* 191 */       String tempType = "HAI";
/* 192 */       if (type.equals("HAI"))
/*     */       {
/* 194 */         out.println("<Sub-Group type='" + tempType + "' name='" + dispName + "' id='" + resId + "' imagepath='" + imagepath + "' message='" + message + "' severity='" + severity + "'>");
/* 195 */         addChildElement(map, dispName, healthkeys, alert, level, resId, out);
/* 196 */         out.println("</Sub-Group>");
/*     */       }
/*     */       else
/*     */       {
/* 200 */         out.println("<Monitor type='" + type + "' name='" + dispName + "' id='" + resId + "' imagepath='" + imagepath + "' message='" + message + "' severity='" + severity + "'>");
/* 201 */         out.println("</Monitor>");
/*     */       }
/*     */     }
/* 204 */     out.println("</Monitor-Group>");
/* 205 */     out.println("</Groups>");
/*     */   }
/*     */   
/*     */   public void addChildElement(HashMap map, String groupName, Hashtable healthkeys, Properties alert, int level, String tempId, PrintWriter out)
/*     */   {
/* 210 */     int tempLevel = level + 1;
/* 211 */     ArrayList childList = (ArrayList)map.get(tempId);
/* 212 */     for (int i = 0; i < childList.size(); i++)
/*     */     {
/* 214 */       ArrayList elementList = (ArrayList)childList.get(i);
/* 215 */       String resId = (String)elementList.get(0);
/* 216 */       String dispName = (String)elementList.get(1);
/* 217 */       String type = (String)elementList.get(2);
/* 218 */       String imagepath = "/fl/" + (String)elementList.get(3);
/* 219 */       String severity = alert.getProperty(resId + "#" + healthkeys.get(type));
/* 220 */       String messageKey = resId + "#" + healthkeys.get(type) + "#MESSAGE";
/* 221 */       String message = (String)alert.get(messageKey);
/* 222 */       if (message != null)
/*     */       {
/* 224 */         message = message.replaceAll("<", "&lt;");
/*     */       }
/*     */       
/* 227 */       if (severity == null)
/*     */       {
/* 229 */         severity = "0";
/*     */       }
/* 231 */       String tempType = "HAI";
/* 232 */       if (type.equals("HAI"))
/*     */       {
/* 234 */         out.println("<Sub-Group type='" + tempType + "' name='" + dispName + "' id='" + resId + "' imagepath='" + imagepath + "' message='" + message + "' severity='" + severity + "'>");
/* 235 */         addChildElement(map, dispName, healthkeys, alert, tempLevel, resId, out);
/* 236 */         out.println("</Sub-Group>");
/*     */       }
/*     */       else
/*     */       {
/* 240 */         out.println("<Monitor type='" + type + "' name='" + dispName + "' id='" + resId + "' imagepath='" + imagepath + "' message='" + message + "' severity='" + severity + "'>");
/* 241 */         out.println("</Monitor>");
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void generateXML(HashMap map, String rootName, ArrayList resIdList, ArrayList attIdList, Hashtable healthkeys, String haStatus, String groupId, String childXML, PrintWriter out)
/*     */   {
/*     */     try
/*     */     {
/* 250 */       out.println("<Groups>");
/* 251 */       String imagePath1 = "/fl//images/icon_monitors_app.gif";
/* 252 */       if (haStatus == null)
/*     */       {
/* 254 */         haStatus = "0";
/*     */       }
/*     */       
/* 257 */       if (childXML == null)
/*     */       {
/* 259 */         String tempType1 = "HAI";
/* 260 */         out.println("<Group type='" + tempType1 + "' name='" + rootName + "' іmagepath='" + imagePath1 + "' id='" + groupId + "' severity='" + haStatus + "'>");
/*     */       }
/* 262 */       Properties alert = FaultUtil.getStatus(resIdList, attIdList);
/*     */       
/* 264 */       ArrayList childList = new ArrayList();
/* 265 */       childList = (ArrayList)map.get(rootName);
/*     */       
/* 267 */       int level = 1;
/* 268 */       for (int i = 0; i < childList.size(); i++)
/*     */       {
/* 270 */         ArrayList elementList = (ArrayList)childList.get(i);
/* 271 */         String resId = (String)elementList.get(0);
/* 272 */         String dispName = (String)elementList.get(1);
/* 273 */         String type = (String)elementList.get(2);
/* 274 */         String imagepath = (String)elementList.get(3);
/* 275 */         String severity = alert.getProperty(resId + "#" + healthkeys.get(type));
/* 276 */         String messageKey = resId + "#" + healthkeys.get(type) + "#MESSAGE";
/* 277 */         String message = (String)alert.get(messageKey);
/* 278 */         if (message != null)
/*     */         {
/* 280 */           message = message.replaceAll("<", "&lt;");
/*     */         }
/*     */         
/* 283 */         if (severity == null)
/*     */         {
/* 285 */           severity = "0";
/*     */         }
/* 287 */         String tempType = type;
/* 288 */         if (tempType.indexOf("-") != -1)
/*     */         {
/* 290 */           tempType = tempType.substring(0, tempType.indexOf("-"));
/*     */         }
/* 292 */         else if (tempType.indexOf(' ') != -1)
/*     */         {
/* 294 */           tempType = tempType.substring(0, tempType.indexOf(' '));
/*     */         }
/* 296 */         else if (tempType.startsWith("Windows"))
/*     */         {
/* 298 */           tempType = "Windows";
/*     */         }
/* 300 */         else if (tempType.equals("JDK1.5"))
/*     */         {
/* 302 */           tempType = "JDK";
/*     */         }
/* 304 */         else if (tempType.equals(".Net"))
/*     */         {
/* 306 */           tempType = "DotNet";
/*     */         }
/* 308 */         else if (tempType.equals("JMX1.2-MX4J-RMI"))
/*     */         {
/* 310 */           tempType = "JMX";
/*     */         }
/*     */         
/*     */ 
/* 314 */         String clipName = tempType;
/* 315 */         if (message != null)
/*     */         {
/* 317 */           message = message.replaceAll("<", "&lt;");
/*     */         }
/* 319 */         out.println("<Monitor type='" + tempType + "' name='" + dispName + "' imagepath='" + imagepath + "' id='" + resId + "' message='" + message + "' severity='" + severity + "'>");
/* 320 */         out.println("</Monitor>");
/*     */       }
/*     */       
/* 323 */       if (childXML == null)
/*     */       {
/* 325 */         out.println("</Group>");
/*     */       }
/* 327 */       out.println("</Groups>");
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 331 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public void destroy() {}
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\servlets\GraphicalViewServlet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */