package com.robertakcs.controller;

import com.google.appengine.api.datastore.Entity;
import com.robertakcs.databaseConnection.DBSingleton;
import com.robertakcs.domain.Item;
import com.robertakcs.domain.ToDoList;
import com.robertakcs.service.ToDoListServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.*;

@Controller
@RequestMapping("/")
public class HomeController {
    ToDoListServiceInterface toDoListService;

    @Autowired
    public HomeController(ToDoListServiceInterface todoListService){
        this.toDoListService = todoListService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "Home/login";
    }

//    @RequestMapping(value = "/userModel", method = RequestMethod.GET)
//    public ModelAndView userModel() {
//        return new ModelAndView("userModel", "command", new UserModel());
//    }

    /*
    * Returns main page after login
    * */
    @RequestMapping(value = "/loadPage", method = RequestMethod.GET)
    public String loadPage(ModelMap map){
        return "Home/index";
    }

    /*
    * Returns index page after logout
    * */
    @RequestMapping(value = "/loadPage_index", method = RequestMethod.GET)
    public String loadPage_index(ModelMap map){
        return "Home/login";
    }

    /*
    * Returns the DBPage
    * */
    @RequestMapping(value = "/dbAccessPage", method = RequestMethod.GET)
    public String dbAccessPage(ModelMap map) { return "Home/dbAccess"; }

    /*
    * Fake Mapping to test controller from AngularJS http request
    * */
    @RequestMapping(value = "/getMessages", method = RequestMethod.GET)
    public @ResponseBody ArrayList<ToDoList> getMessages(@ModelAttribute("TodoList") ToDoList tDL){
        return toDoListService.getToDoListArrayEntity(tDL);
    }


    /*
    * Get all the lists that the owner created
    * */
    @RequestMapping(value = "/getMyLists", method = RequestMethod.GET)
    public @ResponseBody ArrayList<ToDoList> getMyLists(@ModelAttribute("TodoList") ToDoList user){
        return toDoListService.getToDoListArrayByEmail(user);
    }

    /*
   * Get all the viewable lists
   * */
    @RequestMapping(value = "/getViewableLists", method = RequestMethod.GET)
    public @ResponseBody ArrayList<ToDoList> getViewableLists(@ModelAttribute("TodoList") ToDoList user){
        return toDoListService.getViewableToDoListArray(user);
    }

    /*
    * Get all the lists that the owner created and all public lists
    * */
    @RequestMapping(value = "/getAllLists", method = RequestMethod.GET)
    public @ResponseBody ArrayList<ToDoList> getAllLists(@ModelAttribute("TodoList") ToDoList user){
        return toDoListService.getToDoListArrayEntity(user);
    }

    /*
    * Get all the items based on the listId
    * */
    @RequestMapping(value = "/getItems", method = RequestMethod.GET)
    public @ResponseBody ArrayList<Entity> getItemsEntity(@ModelAttribute("TodoList") Item item){
        //int listId = list.();
        ArrayList<Entity> ie = toDoListService.getItemByListID(item);
        return ie;
    }

    /*
    *  Add all list
     */
    @RequestMapping(value = "/addAllList", method = RequestMethod.GET)
    public @ResponseBody boolean addAllList(@ModelAttribute("TodoListModel") ArrayList<ToDoList> TDL ){
        return true;//toDoListService.updateAllDoListEntity(listList);
    }



    /*
    * Add a new list returns the list that it saved
    * */
    @RequestMapping(value = "/addList", method = RequestMethod.GET)
    public @ResponseBody ToDoList addList(@ModelAttribute("ToDoListModel") ToDoList list){
         //   boolean success = new ToDoListDAO().addToDoList(list);

        return new ToDoList(toDoListService.saveDoListEntity(list.getEmail(), list.getPrivate(), list.getListName(), list.getID()));
    }

    /*
*  Add all item
 */
    @RequestMapping(value = "/addAllItem", method = RequestMethod.POST)
    public @ResponseBody boolean addAllItem(@RequestBody List<Item> itemList){
    //    return toDoListService.updateAllItemEntity(itemList);
        for(Item i : itemList) {
            addItem(i);
        }
        return true;
    }

    @RequestMapping(value = "/updateAllItem", method = RequestMethod.POST)
    public @ResponseBody boolean updateAllItem(@RequestBody List<Item> itemList){
        for(Item i : itemList) {
            updateItem(i);
        }
        return true;
    }


    /*
    * Add a new item, returns entity
    * */
    @RequestMapping(value = "/addItem", method = RequestMethod.GET)
    public @ResponseBody Entity addItem(@ModelAttribute("ItemModel") Item item){
        return toDoListService.saveItemEntity(item.getListId(),item.getCategory(),item.getDescription(),item.getStartDate(),item.getEndDate(),item.getCompleted(),item.getPositionInList(),item.getID());
    }

    /*
    * Update an existing list with new data
    * */
    @RequestMapping(value = "/updateList", method = RequestMethod.GET)
    public @ResponseBody Entity updateList(@ModelAttribute("TodoListModel") ToDoList list){
        //    list = new ToDoList("email", false, "name");
        return toDoListService.updateDoListEntity(list.getEntity());
    }

    /*
    * Update an existing item with new data
    * */
    @RequestMapping(value = "/updateItem", method = RequestMethod.GET)
    public @ResponseBody Entity updateItem(@ModelAttribute("ItemModel") Item item){
        return toDoListService.updateItemEntity(item.getEntity());
    }

    /*
    * Delete an existing list (NOT IN SPECS. COMPLETE LATER)
    * */
    @RequestMapping(value = "/deleteList", method = RequestMethod.GET)
    public @ResponseBody boolean deleteList(@ModelAttribute("TodoListModel") ToDoList list){
        return false;
    }

    /*
    * Delete an existing item
    * */
    @RequestMapping(value = "/deleteItem", method = RequestMethod.GET)
    public @ResponseBody boolean deleteItem(@ModelAttribute("ItemModel") Item item){
        return toDoListService.deleteItemEntity(item.getEntity()) ;
    }

    /*
     Delete all items
     */
    @RequestMapping(value = "/deleteItems", method = RequestMethod.POST)
    public @ResponseBody boolean deleteItems(@RequestBody List<Item> itemList){
        for(Item i : itemList) {
            if(i.getID() != 0) deleteItem(i);
        }
        return true;
    }

     /*
     Test
     */

    @RequestMapping(value = "/requestToDB", method = RequestMethod.POST)
    public @ResponseBody ArrayList requestToDB(@RequestBody String sqlQuery) {
        String lowerCaseString = sqlQuery.toLowerCase();
        if(lowerCaseString.contains("create") || lowerCaseString.contains("insert") || lowerCaseString.contains("drop")){
            return null;
        }
        DBSingleton dbs = DBSingleton.getSingleton();
        ArrayList<Map<String, Object>> al = dbs.getJdbcTemplate().query(sqlQuery, new ResultSetExtractor<ArrayList<Map<String, Object>>>() {
            @Override
            public ArrayList<Map<String, Object>> extractData(ResultSet rs) throws SQLException, DataAccessException {
                return resultSetToArrayList(rs);
            }
        });
        return al;
    }

    private ArrayList<Map<String, Object>> resultSetToArrayList(ResultSet rs) throws SQLException {
        ArrayList<Map<String,Object>> al = new ArrayList<Map<String, Object>>();
        while(rs.next()) {
            ResultSetMetaData rsmd = rs.getMetaData();
            int numColumns = rsmd.getColumnCount();
            Map<String, Object> obj = new HashMap<String, Object>();

            for( int i=1; i<numColumns+1; i++) {
                String column_name = rsmd.getColumnName(i);

                switch( rsmd.getColumnType( i ) ) {
                    case java.sql.Types.ARRAY:
                        obj.put(column_name, rs.getArray(column_name));     break;
                    case java.sql.Types.BIGINT:
                        obj.put(column_name, rs.getInt(column_name));       break;
                    case java.sql.Types.BOOLEAN:
                        obj.put(column_name, rs.getBoolean(column_name));   break;
                    case java.sql.Types.BLOB:
                        obj.put(column_name, rs.getBlob(column_name));      break;
                    case java.sql.Types.DOUBLE:
                        obj.put(column_name, rs.getDouble(column_name));    break;
                    case java.sql.Types.FLOAT:
                        obj.put(column_name, rs.getFloat(column_name));     break;
                    case java.sql.Types.INTEGER:
                        obj.put(column_name, rs.getInt(column_name));       break;
                    case java.sql.Types.NVARCHAR:
                        obj.put(column_name, rs.getNString(column_name));   break;
                    case java.sql.Types.VARCHAR:
                        obj.put(column_name, rs.getString(column_name));    break;
                    case java.sql.Types.TINYINT:
                        obj.put(column_name, rs.getInt(column_name));       break;
                    case java.sql.Types.SMALLINT:
                        obj.put(column_name, rs.getInt(column_name));       break;
                    case java.sql.Types.DATE:
                        obj.put(column_name, rs.getDate(column_name));      break;
                    case java.sql.Types.TIMESTAMP:
                        obj.put(column_name, rs.getTimestamp(column_name)); break;
                    default:
                        obj.put(column_name, rs.getObject(column_name));    break;
                }
            }
            al.add(obj);
        }
        return al;
    }

    @RequestMapping(value = "/printFromDB", method = RequestMethod.GET, produces="text/plain")
    public @ResponseBody String printFromDB()  {
        String url;
        if (System.getProperty("com.google.appengine.runtime.version").startsWith("Google App Engine/")) {
            // Check the System properties to determine if we are running on appengine or not
            // Google App Engine sets a few system properties that will reliably be present on a remote
            // instance.
            url = System.getProperty("ae-cloudsql.cloudsql-database-url");
            try {
                // Load the class that provides the new "jdbc:google:mysql://" prefix.
                Class.forName("com.mysql.jdbc.GoogleDriver");
            } catch (ClassNotFoundException e) {
                return "failed";
            }
        } else {
            // Set the url with the local MySQL database connection url when running locally
            url = System.getProperty("ae-cloudsql.local-database-url");
        }

        try {
            Properties properties = new Properties();
            properties.setProperty("user", "root");
            properties.setProperty("password", "RedRobins");
            properties.setProperty("useSSL", "false");
            properties.setProperty("autoReconnect", "true");
            Connection conn = DriverManager.getConnection(url, properties);
            conn.createStatement().executeQuery("use RedRobins");
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM ToDoLists");
            rs.next();
            return rs.getString("Email") + "\n";
        } catch (SQLException e) {
            return url + " " + e.toString();
        }
    }
}
