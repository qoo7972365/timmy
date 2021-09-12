/*     */ package com.adventnet.appmanager.servlets;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.util.DBUtil;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.appmanager.util.ExtProdUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.appmanager.util.MenuConstants;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.LinkedList;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MenuHandlerServlet
/*     */   extends HttpServlet
/*     */   implements MenuConstants
/*     */ {
/*     */   public void doGet(HttpServletRequest request, HttpServletResponse response)
/*     */     throws ServletException, IOException
/*     */   {
/*  41 */     doPost(request, response);
/*     */   }
/*     */   
/*     */ 
/*     */   public void doPost(HttpServletRequest request, HttpServletResponse response)
/*     */     throws ServletException, IOException
/*     */   {
/*  48 */     String loginName = EnterpriseUtil.getLoggedInUserName(request);
/*     */     
/*  50 */     String action = request.getParameter("action");
/*  51 */     String jsonString = "";
/*  52 */     response.setCharacterEncoding("UTF-8");
/*  53 */     PrintWriter out = response.getWriter();
/*  54 */     if (action != null)
/*     */     {
/*  56 */       Gson gson = new Gson();
/*  57 */       HashMap<String, ArrayList<ArrayList<String>>> menuHash = new HashMap();
/*  58 */       AMLog.debug("[MenuHandlerServlet-doPost] action : " + action);
/*  59 */       if ("getAllowedConfigs".equals(action))
/*     */       {
/*     */ 
/*  62 */         jsonString = gson.toJson(getAdminAllowedConfigs(loginName), new TypeToken() {}.getType());
/*     */       }
/*  64 */       else if ("getQuickLinks".equals(action))
/*     */       {
/*     */ 
/*  67 */         jsonString = gson.toJson(getQuickLinks(loginName), new TypeToken() {}.getType());
/*     */       }
/*  69 */       else if ("verticalmenulist".equals(action))
/*     */       {
/*  71 */         String vertical_id = request.getParameter("config_id");
/*  72 */         menuHash = getVerticalMenus(vertical_id, loginName);
/*  73 */         jsonString = gson.toJson(menuHash, new TypeToken() {}.getType());
/*     */       }
/*  75 */       else if ("horizontalmenulist".equals(action))
/*     */       {
/*  77 */         String horizontal_id = request.getParameter("vertical_id");
/*  78 */         menuHash = getHorizontalMenu(horizontal_id, loginName);
/*  79 */         jsonString = gson.toJson(menuHash, new TypeToken() {}.getType());
/*     */       }
/*  81 */       else if ("getNextLink".equals(action))
/*     */       {
/*  83 */         ArrayList<ArrayList<String>> menulink = new ArrayList();
/*  84 */         String h_id = request.getParameter("h_id");
/*  85 */         menulink = getMenuLink(h_id);
/*  86 */         jsonString = gson.toJson(menulink);
/*     */ 
/*     */       }
/*  89 */       else if ("flowmenulist".equals(action))
/*     */       {
/*  91 */         String config_id = request.getParameter("config_id");
/*  92 */         String fromQCWizard = request.getParameter("fromQCWizard");
/*  93 */         boolean fromQCWizardFlag = false;
/*  94 */         if (fromQCWizard != null)
/*     */         {
/*  96 */           fromQCWizardFlag = Boolean.parseBoolean(fromQCWizard);
/*     */         }
/*  98 */         menuHash = getFlowMenus(config_id, loginName, fromQCWizardFlag);
/*  99 */         jsonString = gson.toJson(menuHash, new TypeToken() {}.getType());
/*     */ 
/*     */       }
/* 102 */       else if ("enabledMenuList".equals(action))
/*     */       {
/* 104 */         ArrayList<String> EnableMenuList = new ArrayList();
/* 105 */         String config_id = request.getParameter("config_id");
/* 106 */         int menuType = Integer.parseInt(request.getParameter("menuType"));
/* 107 */         EnableMenuList = getEnabledList(config_id, menuType);
/* 108 */         jsonString = gson.toJson(EnableMenuList);
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 113 */         AMLog.debug("[MenuHandlerServlet-doPost] Invalid action");
/*     */       }
/* 115 */       out.println(jsonString);
/* 116 */       AMLog.info("[MenuHandlerServlet-doPost] gson String : " + jsonString);
/*     */     }
/*     */     else
/*     */     {
/* 120 */       AMLog.debug("[MenuHandlerServlet-doPost] action missing");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private LinkedHashMap getAdminAllowedConfigs(String loginName)
/*     */   {
/* 128 */     adminConfigs = new LinkedHashMap();
/* 129 */     ResultSet rs = null;
/*     */     try
/*     */     {
/* 132 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/* 133 */       String allowedCond = "";
/* 134 */       if (DBUtil.isDelegatedAdmin(loginName))
/*     */       {
/* 136 */         allowedCond = " and D_ADMIN=1";
/*     */       }
/*     */       
/* 139 */       String adminConfigQuery = "select Admin_Config.CONFIG_ID, CONFIG_NAME, V_ID, V_NAME, TYPE from Admin_Config join Admin_VMenu on Admin_Config.CONFIG_ID=Admin_VMenu.CONFIG_ID and TYPE not in ('2', '0')" + allowedCond + " order by CONFIG_ID, V_ID";
/* 140 */       AMLog.debug("[MenuHandlerServlet-doPut] Config Query : " + adminConfigQuery);
/* 141 */       rs = AMConnectionPool.executeQueryStmt(adminConfigQuery);
/*     */       
/*     */ 
/* 144 */       ArrayList<ArrayList<String>> verticleConfigs = new ArrayList();
/* 145 */       while (rs.next())
/*     */       {
/* 147 */         String configId = rs.getString("CONFIG_ID");
/*     */         
/* 149 */         if ((configId != null) && (!configId.equals("")))
/*     */         {
/* 151 */           if ((!"am.admin.config.tools.upload".equalsIgnoreCase(rs.getString("V_NAME"))) || (!DBUtil.hasServerConfigValue("am.upload.enabled")) || (DBUtil.getServerConfigValueasBoolean("am.upload.enabled")))
/*     */           {
/*     */ 
/* 154 */             ArrayList<String> eachConfigs = new ArrayList();
/* 155 */             eachConfigs.add(0, rs.getString("V_ID"));
/* 156 */             eachConfigs.add(1, FormatUtil.getString(rs.getString("V_NAME")));
/* 157 */             if (adminConfigs.containsKey(configId))
/*     */             {
/* 159 */               ArrayList<ArrayList<String>> configWrapperList = (ArrayList)adminConfigs.get(configId);
/* 160 */               AMLog.info("[ShowIT360Tile] oldlist for config : " + adminConfigs);
/* 161 */               configWrapperList.add(configWrapperList.size(), (ArrayList)eachConfigs.clone());
/* 162 */               adminConfigs.put(configId, configWrapperList);
/* 163 */               AMLog.info("[ShowIT360Tile] newlist for config : " + adminConfigs);
/*     */             }
/*     */             else
/*     */             {
/* 167 */               ArrayList<ArrayList<String>> configWrapperList = new ArrayList();
/* 168 */               configWrapperList.add(0, (ArrayList)eachConfigs.clone());
/* 169 */               adminConfigs.put(configId, configWrapperList);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 174 */       AMLog.info("[MenuHandlerServlet-doPut] Complete admin config list : " + adminConfigs);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 195 */       return adminConfigs;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 178 */       e.printStackTrace();
/*     */ 
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 185 */         if (rs != null)
/*     */         {
/* 187 */           AMConnectionPool.closeStatement(rs);
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 192 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private LinkedHashMap getQuickLinks(String loginName)
/*     */   {
/* 200 */     qLinkHash = new LinkedHashMap();
/*     */     
/* 202 */     ResultSet rs = null;
/*     */     try
/*     */     {
/* 205 */       String allowedConfigs = getAllowedConfigs(loginName, "horizontalmenulist");
/* 206 */       String allowedCond = "";
/* 207 */       if (!"".equals(allowedConfigs))
/*     */       {
/* 209 */         allowedCond = " and H_ID in (" + allowedConfigs.substring(1, allowedConfigs.length() - 1) + ")";
/*     */       }
/* 211 */       String query = "select Admin_Config.CONFIG_ID, CONFIG_NAME, Admin_VMenu.V_ID,Admin_VMenu.V_NAME, H_ID, H_NAME from Admin_Config join Admin_VMenu on Admin_Config.CONFIG_ID=Admin_VMenu.CONFIG_ID and Admin_VMenu.TYPE not in ('2', '0') join Admin_HMenu on Admin_VMenu.V_ID=Admin_HMenu.V_ID and Admin_HMenu.TYPE <> '2'" + allowedCond + " order by CONFIG_ID, Admin_VMenu.V_ID";
/*     */       
/* 213 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/* 214 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 215 */       while (rs.next())
/*     */       {
/* 217 */         String cId = rs.getString("CONFIG_ID");
/* 218 */         String cName = rs.getString("CONFIG_NAME");
/*     */         
/* 220 */         String vId = rs.getString("V_ID");
/* 221 */         String vName = rs.getString("V_NAME");
/*     */         
/* 223 */         String hId = rs.getString("H_ID");
/* 224 */         String hName = rs.getString("H_NAME");
/*     */         
/* 226 */         LinkedList<String> linkedList = new LinkedList();
/* 227 */         linkedList.add(hId);
/* 228 */         linkedList.add(FormatUtil.getString(hName));
/* 229 */         linkedList.add(FormatUtil.getString(cName));
/* 230 */         linkedList.add(FormatUtil.getString(vName));
/*     */         
/* 232 */         if (qLinkHash.containsKey(cId))
/*     */         {
/* 234 */           LinkedHashMap qLinkVHash = (LinkedHashMap)qLinkHash.get(cId);
/* 235 */           if (qLinkVHash.containsKey(vId))
/*     */           {
/* 237 */             LinkedList tempVlist = (LinkedList)qLinkVHash.get(vId);
/* 238 */             tempVlist.add(linkedList);
/*     */           }
/*     */           else
/*     */           {
/* 242 */             LinkedList<LinkedList<String>> tempLList = new LinkedList();
/* 243 */             tempLList.add(linkedList);
/* 244 */             qLinkVHash.put(vId, tempLList);
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 249 */           LinkedHashMap<String, LinkedList<LinkedList<String>>> tempLinkedHash = new LinkedHashMap();
/*     */           
/* 251 */           LinkedList<LinkedList<String>> tempLList = new LinkedList();
/* 252 */           tempLList.add(linkedList);
/*     */           
/* 254 */           tempLinkedHash.put(vId, tempLList);
/*     */           
/* 256 */           qLinkHash.put(cId, tempLinkedHash);
/*     */         }
/*     */       }
/* 259 */       AMLog.info("[MenuHandlerServlet-doPut] Quick Links : " + qLinkHash);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 279 */       return qLinkHash;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 263 */       e.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 269 */         if (rs != null)
/*     */         {
/* 271 */           AMConnectionPool.closeStatement(rs);
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 276 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getAllowedConfigs(String loginName, String combType)
/*     */     throws Exception
/*     */   {
/* 287 */     ArrayList<String> combinedList = new ArrayList();
/*     */     
/* 289 */     if ("verticalmenulist".equals(combType))
/*     */     {
/* 291 */       if (combinedList.isEmpty())
/*     */       {
/* 293 */         return "";
/*     */       }
/* 295 */       return combinedList.toString();
/*     */     }
/* 297 */     if ("horizontalmenulist".equals(combType))
/*     */     {
/* 299 */       AMLog.info("[MenuHandlerServlet-getAllowedConfigs] Allowed Horizontal menus : " + combinedList.toString());
/* 300 */       if (combinedList.isEmpty())
/*     */       {
/* 302 */         return "";
/*     */       }
/* 304 */       return combinedList.toString();
/*     */     }
/* 306 */     return "";
/*     */   }
/*     */   
/*     */   public HashMap getFlowMenus(String config_id, String loginName, boolean fromQCWizard)
/*     */   {
/* 311 */     hmenuHash = new HashMap();
/* 312 */     ResultSet rs = null;
/* 313 */     Integer vid = null;
/*     */     try
/*     */     {
/* 316 */       String allowedCond = "";
/* 317 */       String allowedConfigs = getAllowedConfigs(loginName, "horizontalmenulist");
/* 318 */       if (!"".equals(allowedConfigs))
/*     */       {
/* 320 */         allowedConfigs = allowedConfigs.substring(1, allowedConfigs.length() - 1);
/* 321 */         allowedCond = "and HMenu.H_ID in (" + allowedConfigs + ")";
/*     */       }
/* 323 */       String typeCond = "and  HMenu.TYPE not in ('1', '0') ";
/* 324 */       if (fromQCWizard)
/*     */       {
/* 326 */         typeCond = "and  HMenu.TYPE in ('5', '6', '7') ";
/*     */       }
/* 328 */       AMLog.debug("[MenuHandlerServlet-getHorizontalMenu] Will get Horizontal menus for the vertical config : " + config_id);
/* 329 */       String query = "select HMenu.H_ID, HMenu.H_NAME, HMenu.IS_MULTIPAGE,HMenu.ACT_METHOD,HMenu.LINK,HMenu.HELP_LINK,HMenu.V_ID,l.NEXT_ID,l.PREV_ID from Admin_HMenu HMenu inner join Admin_HMenuLinks l on HMenu.H_ID=l.H_ID where cast(HMenu.V_ID as CHAR) like '" + config_id + "%' " + typeCond + allowedCond + " order by HMenu.H_ID ";
/* 330 */       AMLog.debug("getFlowMenus query" + query);
/* 331 */       ArrayList<ArrayList<String>> hmenuList = new ArrayList();
/*     */       
/* 333 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/* 334 */       rs = AMConnectionPool.executeQueryStmt(query);
/*     */       
/* 336 */       int i = 0;
/* 337 */       while (rs.next())
/*     */       {
/* 339 */         ArrayList<String> hmenu = new ArrayList();
/*     */         
/* 341 */         Integer hid = Integer.valueOf(rs.getInt("H_ID"));
/* 342 */         String hmenuName = rs.getString("H_NAME");
/* 343 */         vid = Integer.valueOf(rs.getInt("V_ID"));
/* 344 */         String isMultiPage = String.valueOf(rs.getBoolean("IS_MULTIPAGE"));
/* 345 */         String actMethod = rs.getString("ACT_METHOD");
/* 346 */         String link = rs.getString("LINK");
/* 347 */         link = getOrignalLink(link);
/* 348 */         String helplink = rs.getString("HELP_LINK");
/* 349 */         Integer nextId = Integer.valueOf(rs.getInt("NEXT_ID"));
/* 350 */         Integer prevId = Integer.valueOf(rs.getInt("PREV_ID"));
/*     */         
/* 352 */         hmenu.add(0, hid.toString());
/* 353 */         hmenu.add(1, FormatUtil.getString(hmenuName));
/* 354 */         hmenu.add(2, isMultiPage);
/* 355 */         hmenu.add(3, actMethod);
/* 356 */         hmenu.add(4, link);
/* 357 */         hmenu.add(5, nextId.toString());
/* 358 */         hmenu.add(6, prevId.toString());
/* 359 */         hmenu.add(7, helplink);
/*     */         
/* 361 */         hmenuList.add(i, hmenu);
/* 362 */         i++;
/*     */       }
/* 364 */       hmenuHash.put(config_id, hmenuList);
/* 365 */       AMLog.info("[IT360-DEBUG] hmenuHash : " + hmenuHash);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 385 */       return hmenuHash;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 369 */       e.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 375 */         if (rs != null)
/*     */         {
/* 377 */           AMConnectionPool.closeStatement(rs);
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 382 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public HashMap getHorizontalMenu(String vertical_id, String loginName)
/*     */   {
/* 390 */     hmenuHash = new HashMap();
/*     */     
/* 392 */     AMLog.debug("[MenuHandlerServlet-getHorizontalMenu] Will get Horizontal menus for the vertical config : " + vertical_id);
/*     */     
/* 394 */     ArrayList<ArrayList<String>> hmenuList = new ArrayList();
/*     */     
/* 396 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 397 */     ResultSet rs = null;
/* 398 */     Integer vid = null;
/* 399 */     String allowedCond = "";
/* 400 */     ArrayList<String> serviceList = new ArrayList();
/* 401 */     serviceList.add("'AppManager'");
/*     */     
/*     */     try
/*     */     {
/* 405 */       if ((ExtProdUtil.isServiceConfigured("OpManager")) && (isOpmCentral()))
/*     */       {
/* 407 */         serviceList.add("'OpManager_Central'");
/*     */       }
/* 409 */       if ((ExtProdUtil.isServiceConfigured("OpManager")) && (!isOpmCentral()))
/*     */       {
/*     */ 
/* 412 */         serviceList.add("'OpManager'");
/* 413 */         serviceList.add("'OpManager_Central'");
/*     */       }
/* 415 */       if (ExtProdUtil.isServiceConfigured("OpStor"))
/*     */       {
/* 417 */         serviceList.add("'OpStor'");
/*     */       }
/* 419 */       String serviceCond = serviceList.toString();
/* 420 */       serviceCond = " and HMenu.SERVICE in (" + serviceCond.substring(1, serviceCond.length() - 1) + ")";
/*     */       
/*     */ 
/* 423 */       if (DBUtil.isDelegatedAdmin(loginName))
/*     */       {
/*     */ 
/* 426 */         allowedCond = " and HMenu.D_ADMIN=1 ";
/*     */       }
/*     */       
/*     */ 
/* 430 */       String query = "select HMenu.H_ID, HMenu.H_NAME, HMenu.IS_MULTIPAGE, HMenu.ACT_METHOD, HMenu.LINK,HMenu.HELP_LINK,HMenu.V_ID,l.NEXT_ID,l.PREV_ID from Admin_HMenu HMenu inner join Admin_HMenuLinks l on HMenu.H_ID=l.H_ID where HMenu.V_ID=" + vertical_id + allowedCond + serviceCond + " and TYPE not in ('0') order by HMenu.H_ID";
/* 431 */       AMLog.debug("[MenuHandlerServlet-getHorizontalMenu] query : " + query);
/* 432 */       rs = AMConnectionPool.executeQueryStmt(query);
/*     */       
/* 434 */       int i = 0;
/* 435 */       while (rs.next())
/*     */       {
/* 437 */         ArrayList<String> hmenu = new ArrayList();
/*     */         
/* 439 */         Integer hid = Integer.valueOf(rs.getInt("H_ID"));
/* 440 */         String hmenuName = rs.getString("H_NAME");
/* 441 */         vid = Integer.valueOf(rs.getInt("V_ID"));
/* 442 */         String isMultiPage = String.valueOf(rs.getBoolean("IS_MULTIPAGE"));
/* 443 */         String actMethod = rs.getString("ACT_METHOD");
/* 444 */         String link = rs.getString("LINK");
/* 445 */         link = getOrignalLink(link);
/* 446 */         String helplink = rs.getString("HELP_LINK");
/* 447 */         Integer nextId = Integer.valueOf(rs.getInt("NEXT_ID"));
/* 448 */         Integer prevId = Integer.valueOf(rs.getInt("PREV_ID"));
/*     */         
/* 450 */         hmenu.add(0, hid.toString());
/* 451 */         hmenu.add(1, FormatUtil.getString(hmenuName));
/* 452 */         hmenu.add(2, isMultiPage);
/* 453 */         hmenu.add(3, actMethod);
/* 454 */         hmenu.add(4, link);
/* 455 */         hmenu.add(5, nextId.toString());
/* 456 */         hmenu.add(6, prevId.toString());
/* 457 */         hmenu.add(7, helplink);
/*     */         
/* 459 */         hmenuList.add(i, hmenu);
/* 460 */         i++;
/*     */       }
/* 462 */       hmenuHash.put(vid.toString(), hmenuList);
/* 463 */       AMLog.info("[IT360-DEBUG] hmenuHash : " + hmenuHash);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 483 */       return hmenuHash;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 467 */       e.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 473 */         if (rs != null)
/*     */         {
/* 475 */           AMConnectionPool.closeStatement(rs);
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 480 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private String getOrignalLink(String link)
/*     */   {
/* 487 */     String toReturn = link;
/* 488 */     if (link.indexOf("service") != -1)
/*     */     {
/* 490 */       int serviceHomeIndex = link.indexOf("service") + 8;
/* 491 */       String serviceName = link.substring(serviceHomeIndex, link.indexOf("&", serviceHomeIndex));
/* 492 */       if (serviceName.equals("OpManager"))
/*     */       {
/* 494 */         link = link.substring(0, link.indexOf("service") - 1);
/*     */       }
/*     */       
/* 497 */       if (!serviceName.equals("AppManager"))
/*     */       {
/* 499 */         toReturn = ExtProdUtil.getServiceUrl(serviceName) + link;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 504 */     return toReturn;
/*     */   }
/*     */   
/*     */   public ArrayList getEnabledList(String conf_id, int menuType) {
/* 508 */     AMLog.debug("[MenuHandlerServlet-getHorizontalMenu] Will get sequence menu id  for the  config id : " + conf_id);
/* 509 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 510 */     ResultSet rs = null;
/* 511 */     String menubinary = Integer.toBinaryString(menuType);
/* 512 */     ArrayList<String> EnableMenuList = new ArrayList();
/*     */     
/* 514 */     for (int i = 0; i <= menubinary.length();)
/*     */     {
/* 516 */       int num = menuType >> i;
/* 517 */       String movedBinary = Integer.toBinaryString(num);
/* 518 */       if (movedBinary.charAt(movedBinary.length() - 1) != '0')
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/* 523 */         String query1 = "select MENU_TYPE,H_ID from Admin_HMenu where cast(V_ID as CHAR) like '" + conf_id + "%' and TYPE not in ('0') order by H_ID";
/*     */         
/*     */         try
/*     */         {
/* 527 */           rs = AMConnectionPool.executeQueryStmt(query1);
/* 528 */           int j = 0;
/* 529 */           while (rs.next())
/*     */           {
/* 531 */             String menuString = rs.getString("menu_type");
/* 532 */             if ((!menuString.equals("")) && (menuString.charAt(menuString.length() - (i + 1)) == '1'))
/*     */             {
/* 534 */               AMLog.info("NEED TO ADD THIS MENUS" + rs.getInt("H_ID"));
/* 535 */               Integer hid = Integer.valueOf(rs.getInt("H_ID"));
/* 536 */               if (!EnableMenuList.contains(hid.toString()))
/*     */               {
/* 538 */                 EnableMenuList.add(j, hid.toString());
/* 539 */                 j++;
/*     */               }
/* 541 */               AMLog.info("[MenuHandlerServlet-getHorizontalMenu] Horizontal menus to be sent : " + EnableMenuList);
/*     */             }
/*     */           }
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           try
/*     */           {
/* 554 */             if (rs != null)
/*     */             {
/* 556 */               AMConnectionPool.closeStatement(rs);
/*     */             }
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/* 561 */             e.printStackTrace();
/*     */           }
/* 514 */           i++;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 548 */           e.printStackTrace();
/*     */         }
/*     */         finally
/*     */         {
/*     */           try
/*     */           {
/* 554 */             if (rs != null)
/*     */             {
/* 556 */               AMConnectionPool.closeStatement(rs);
/*     */             }
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/* 561 */             e.printStackTrace();
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 566 */     return EnableMenuList;
/*     */   }
/*     */   
/*     */ 
/*     */   public ArrayList getMenuLink(String h_id)
/*     */   {
/* 572 */     AMLog.debug("[MenuHandlerServlet-getHorizontalMenu] Will get sequence menu id  for the  config id : " + h_id);
/* 573 */     String query = "select HMenu.LINK, HMenu.IS_MULTIPAGE,HMenu.ACT_METHOD,HMenu.HELP_LINK,l.NEXT_ID,l.PREV_ID from Admin_HMenuLinks l inner join Admin_HMenu HMenu on HMenu.H_ID=l.H_ID  where l.H_ID = '" + h_id + "' ";
/* 574 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 575 */     ResultSet rs = null;
/* 576 */     hmenu = new ArrayList();
/*     */     
/*     */     try
/*     */     {
/* 580 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 581 */       while (rs.next())
/*     */       {
/* 583 */         String link = rs.getString("LINK");
/* 584 */         link = getOrignalLink(link);
/* 585 */         String isMultiPage = String.valueOf(rs.getBoolean("IS_MULTIPAGE"));
/* 586 */         String actMethod = rs.getString("ACT_METHOD");
/* 587 */         String hlink = rs.getString("HELP_LINK");
/* 588 */         Integer n_id = Integer.valueOf(rs.getInt("NEXT_ID"));
/* 589 */         Integer p_id = Integer.valueOf(rs.getInt("PREV_ID"));
/*     */         
/* 591 */         hmenu.add(0, link);
/* 592 */         hmenu.add(1, isMultiPage);
/* 593 */         hmenu.add(2, actMethod);
/* 594 */         hmenu.add(3, n_id.toString());
/* 595 */         hmenu.add(4, p_id.toString());
/* 596 */         hmenu.add(5, hlink);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 619 */       return hmenu;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 603 */       e.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 609 */         if (rs != null)
/*     */         {
/* 611 */           AMConnectionPool.closeStatement(rs);
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 616 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public HashMap getVerticalMenus(String config_id, String loginName)
/*     */   {
/* 624 */     vmenuHash = new HashMap();
/*     */     
/* 626 */     AMLog.debug("[MenuHandlerServlet-getVerticalMenus] Will get Vertical menus for the config : " + config_id);
/* 627 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 628 */     ArrayList<ArrayList<String>> vmenuList = new ArrayList();
/* 629 */     int i = 0;
/* 630 */     Integer cid = null;
/* 631 */     ResultSet rs = null;
/*     */     try
/*     */     {
/* 634 */       String query = "select V_ID, V_NAME, CONFIG_ID from Admin_VMenu where CONFIG_ID=" + config_id + " and TYPE not in ('2', '0')";
/* 635 */       if (DBUtil.isDelegatedAdmin(loginName))
/*     */       {
/* 637 */         query = query + " and D_ADMIN=1 ";
/*     */       }
/*     */       
/*     */ 
/* 641 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 642 */       while (rs.next())
/*     */       {
/*     */ 
/* 645 */         ArrayList<String> vmenu = new ArrayList();
/*     */         
/* 647 */         Integer vid = Integer.valueOf(rs.getInt("V_ID"));
/* 648 */         String vmenuName = rs.getString("V_NAME");
/* 649 */         cid = Integer.valueOf(rs.getInt("CONFIG_ID"));
/*     */         
/* 651 */         vmenu.add(0, vid.toString());
/* 652 */         vmenu.add(1, FormatUtil.getString(vmenuName));
/* 653 */         vmenuList.add(i, (ArrayList)vmenu.clone());
/* 654 */         i++;
/*     */       }
/* 656 */       vmenuHash.put(cid.toString(), (ArrayList)vmenuList.clone());
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 676 */       return vmenuHash;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 660 */       e.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 666 */         if (rs != null)
/*     */         {
/* 668 */           AMConnectionPool.closeStatement(rs);
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 673 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isValidConfig(String configName)
/*     */   {
/* 681 */     for (int i = 0; i < ALLOWED_CONFIGS.length; i++)
/*     */     {
/* 683 */       if (ALLOWED_CONFIGS[i].equals(configName))
/*     */       {
/* 685 */         return true;
/*     */       }
/*     */     }
/* 688 */     return false;
/*     */   }
/*     */   
/*     */   private boolean isOpmCentral()
/*     */   {
/* 693 */     if (ExtProdUtil.opm_Edition == null)
/*     */     {
/* 695 */       ExtProdUtil.is_opm_Central = ExtProdUtil.isOPMCentral();
/*     */     }
/* 697 */     return ExtProdUtil.is_opm_Central;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\servlets\MenuHandlerServlet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */