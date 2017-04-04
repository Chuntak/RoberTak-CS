/**
 * Created by rvtru on 2/12/2017.
 */

$(document).ready(function(){
    $('[data-toggle="tooltip"]').tooltip();
});

var app = angular.module('myApp', ["kendo.directives"]);
app.controller('myCtrl', function ($scope, $http) {

    /*TEST DB*/
    $scope.printDB = "";

    $scope.printFromDB = function() {
        debugger;
        var y = $http({
            method: 'GET',
            url: '/printFromDB',
            params: {}
        }).then(function (response) {
            debugger;
            $scope.printDB = response.data;
        }, function errorCallBack(response) {
            debugger;
            alert("print from DB error\n");
        });
    };

    /* INIT SOME SCOPE VARIABLES */
    $scope.nameOfList = "";
    /* ARRAY OF ITEMS TO DELETE */
    $scope.toDelete = [];
    /* USER CREDENTIALS */
    $scope.email = sessionStorage.getItem("email");
    $scope.userName = sessionStorage.getItem("userName");
    /* ARRAY OF ITEMS */
    $scope.items = [];
    $scope.newItems = [];
    $scope.editedItems = [];
    $scope.mainGridOptions = {};
    $scope.gridData = {};
    /* SELECTED LIST */
    $scope.selectedList = {};
    $scope.deletedItems = [];
    /*boolean to check if list is new*/
    $scope.isNewList = false;

    $scope.private = false;
    $scope.nameOfList = "";
    $scope.newListMode = true;
    $scope.notsaveable = true;
    /* SHOW CONTENT AFTER 'CREATE' BUTTON IS CLICKED */
    /* LIST WILL ALWAYS SHOW NOW*/
    $scope.show = true;
    $scope.newList = function(){
        $scope.resetView();
        $scope.ownerOfList = $scope.email;
        // $scope.show = true;// !$scope.show;
        $scope.isNewList = true;
        $scope.newListMode = true;
        $scope.notsaveable = false;
    };


    $scope.loadListBTN = function() {
        $scope.newListMode = false;
    };

    $scope.viewMyList = true;
    $scope.currentList = null;

    $scope.tempSavedRecords = null;




    /* DATA FOR KENDO GRID. THESE SHOULD BE THE ITEM OBJECTS RECEIVED FROM DATASTORE */
    $scope.initGrid = new function() {
        $scope.gridData = new kendo.data.DataSource({
            transport: {
                read: function(e){
                    e.success($scope.items);
                },
                update:
                    function(e){
                        console.log("update");
                        alert("updated");
                    }
            },

            schema: {
                model: {
                    id: "GridID",
                    fields: {
                        iD: {
                            type: "number",
                            //this field will not be editable (default value is true)
                          //  editable: false,
                            // a defaultValue will not be assigned (default value is false)
                            default: 0
                        },
                        listId: {
                            type: "number",
                           // editable: false,
                            default: 0
                        },
                        positionInList: {
                            //data type of the field {Number|String|Boolean} default is String
                            type: "number",
                            // used when new model is created
                            editable: true
                        },
                        category:{
                            type: "string"
                        },
                        description:{
                            type: "string"
                        },
                        startDate:{
                            type: "date"
                        },
                        endDate:{
                            type: "date"
                        },
                        completed:{
                            type: "boolean",
                            default: false
                        },
                        isOld:{
                            type: "boolean",
                            default: false
                        }
                    }
                }
            },
            batch: true
        });
        /* INIT ITEM TABLE WITH KENDO GRID */
        $scope.mainGridOptions = {
            dataSource: $scope.gridData,
            toolbar: ["create",
                {
                    type: "button",
                    id: "moveUp",
                    text: "Move Up",
                    click: $scope.moveUp
                },
                {
                    type: "button",
                    id: "moveDown",
                    text: "Move Down",
                    click: $scope.moveDown
                }],
            //"destroy"],
            selectable: "row",
            columns: [
                // { field: "listId", title: "List ID",  hidden: true },
                // { field: "iD", title: "Item ID",  hidden: true },
                // { field: "positionInList", title: "Position", hidden: true },
                { field: "category", title: "Category" },
                { field: "description", title: "Description" },
                { field: "startDate", title: "Start Date", format: "{0:MM/dd/yyyy}" },
                { field: "endDate", title: "End Date", format: "{0:MM/dd/yyyy}" },
                { field: "completed", title: "Completed" },

                { command: ["edit"], title: "&nbsp;" },
                { command: ["delete"], title: "&nbsp;" }
            ],
            save: function(e){
                $scope.updateTempRecords();
                var dataSource = $("#grid").data("kendoGrid").dataSource;
                $("#grid").data('kendoGrid').refresh();
                debugger;
            },
            remove: function (e) {
                if(e.model.iD != 0) {
                    var deletedItem = {
                        "id":e.model.iD
                    };
                    $scope.deletedItems.push(deletedItem);
                }
            },
            update: function(e){
                //alert("update");
            },
            cancel: function(e) {
                console.log("cancel fired");
                if($scope.tempSavedRecords != null){
                    $('#grid').data('kendoGrid').dataSource.data($scope.tempSavedRecords);
                }
                else{
                    $('#grid').data('kendoGrid').dataSource.cancelChanges();
                }
            },
            sortable: true,
            pageable: true,
            editable: "popup"
        };
    };



    $scope.moveUp = function(e) {
        e.preventDefault();
        var dataItem = this.dataItem($(e.currentTarget).closest("tr"));
        moveRow(this, dataItem, -1);
    };
    $scope.moveDown = function(e) {
        e.preventDefault();
        var dataItem = this.dataItem($(e.currentTarget).closest("tr"));
        moveRow(this, dataItem, 1);
    };
    $scope.swap = function(a,b,propertyName){
        var temp=a[propertyName];
        a[propertyName]=b[propertyName];
        b[propertyName]=temp;
    };
    $scope.moveRow = function(grid, dataItem,direction) {
        var record = dataItem;
        if (!record) {
            return;
        }
        var newIndex = index = grid.dataSource.indexOf(record);
        direction < 0?newIndex--:newIndex++;
        if (newIndex < 0 || newIndex >= grid.dataSource.total()) {
            return;
        }
        swap(grid.dataSource._data[newIndex],grid.dataSource._data[index],'position');
        grid.dataSource.remove(record);
        grid.dataSource.insert(newIndex, record);

    };



    $scope.updateTempRecords = function() {
        $scope.tempSavedRecords = $('#grid').data('kendoGrid').dataSource.data();
        $scope.tempSavedRecords = $scope.tempSavedRecords.toJSON();
    };


    $scope.resetView = function() {
        $scope.nameOfList = "";
        $scope.ownerOfList= "";
        $scope.private = false;
        $scope.itemEntityList = [];
        $scope.editedItems = [];
        $scope.newItems = [];
        $("#grid").data('kendoGrid').dataSource.data([]);
        $scope.loadItemsToGrid();
    };

    /*on the list selected */
    $scope.onListSelected = function() {
        $scope.nameOfList = $scope.selectedList.listName;
        $scope.ownerOfList = $scope.selectedList.email;
        $scope.private = $scope.selectedList.private;
        $scope.currentList = $scope.selectedList;
        $scope.isNewList = false;
        $scope.refreshItems();
    };

    $scope.refreshItems = function() {
        debugger;
        $scope.getItems();
    };

    $scope.loadItemsToGrid = function() {
        var dataSource = $("#grid").data("kendoGrid").dataSource;
        for(var i = 0; i < $scope.itemEntityList.length; i++){
            var e = $scope.itemEntityList[i];
            var item = {
                "iD":e.properties.real_ID,
                "listId": e.properties.listId,
                "positionInList": e.properties.positionInList,
                "category":e.properties.category,
                "description":e.properties.description,
                "startDate": new Date(e.properties.startDate),
                "endDate": new Date (e.properties.endDate),
                "completed":e.properties.completed,
                "isOld":true
            };
            dataSource.add(item);
        }
        $scope.updateTempRecords();
    };




    $scope.loadLists = function() {
        $scope.isNewList = false;
        if($scope.viewMyList == "true"){
            var y = $http({
                method: 'GET',
                url: '/getMyLists',
                params: { "email":$scope.email }
            }).then(function (response) {
                $scope.toDoListList = response.data;
                console.log("it worked!");
                //$scope.posts[index]["comments"].splice(commentIndex, 1);
            }, function errorCallBack(response) {
                alert("get messages error\n");
            });
            $scope.notsaveable = false;
        } else {
            var y = $http({
                method: 'GET',
                url: '/getViewableLists',
                params: { "email":$scope.email }
            }).then(function (response) {
                $scope.toDoListList = response.data;
                console.log("it worked!");
                //$scope.posts[index]["comments"].splice(commentIndex, 1);
            }, function errorCallBack(response) {
                alert("get messages error\n");
            });
            $scope.notsaveable = true;
        }
        $scope.resetView();
    };

    $scope.save = function(){
        if($scope.isNewList === true) {
            var y = $http({
                method: 'GET',
                url: '/addList',
                params: {"email": $scope.email, "private":$scope.private, "listName":$scope.nameOfList, "iD": -1}
            }).then(function (response) {
                var realID = response.data.entity.properties.real_ID;
                $scope.currentList = response.data;
                $scope.isNewList = false;
                $scope.saveItemList(realID);

                console.log("Saved List");
                /*TODO saveall items*/
            }, function errorCallBack(response) {
                alert("get messages error\n");
            });
        } else {
            var y = $http({
                method: 'GET',
                url: '/updateList',
                params: {"iD":$scope.currentList.entity.properties.real_ID, "private":$scope.private, "email":$scope.email, "listName":$scope.nameOfList}
            }).then(function (response) {
                var realID = response.data.properties.real_ID;
                console.log("Saved List");
                  $scope.saveItemList(realID);
                /*TODO saveall items*/
                //$scope.posts[index]["comments"].splice(commentIndex, 1);
            }, function errorCallBack(response) {
                alert("get messages error\n");
            });
        }
    };

    $scope.saveItemList = function(id) {
        $scope.buildJsonArrays(id);
        var y = $http.post('addAllItem', $scope.newItems).then(function(response) {
            $scope.newItems = [];
            var z = $http.post('updateAllItem', $scope.editedItems).then(function(response) {
                $scope.editedItems = [];
                $scope.deleteItems();
            }, function errorCallBack(response) {
                alert("update item error");
            });
        }, function(response) {
           alert("add all item error");
        });
    };

    $scope.deleteItems = function() {
        var y = $http.post('deleteItems', $scope.deletedItems).then(function(response) {
            $scope.deletedItems = [];
            alert("SUCCESS SAVING");
            $scope.refreshItems();
        }, function(response) {
            alert("delete Item error");
        });
    };


    /*BUILD JSON ARRAY */
    $scope.buildJsonArrays = function(listId) {
        debugger;
        var gridData = $("#grid").data('kendoGrid').dataSource.data();
        for (var i = 0; i < gridData.length; i++) {
            gridData[i].listId = listId;
            var newItem = {
                "id":gridData[i].iD,
                "listId": gridData[i].listId,
                "positionInList": gridData[i].positionInList,
                "category":gridData[i].category,
                "description":gridData[i].description,
                "startDate":gridData[i].startDate,
                "endDate":gridData[i].endDate,
                "completed":gridData[i].completed
            };
            if(gridData[i].isOld == true){
                $scope.editedItems.push(newItem);
            } else {
                $scope.newItems.push(newItem);
                gridData[i].isOld = true;
            }
        }
    };

    $scope.reloadItems = function() {
        $scope.getItems();
        $scope.initGrid();
    };




    $scope.addList = function(){
        var y = $http({
            method: 'GET',
            url: '/addList',
            params: {"email": $scope.email, "private":$scope.private, "listName":$scope.nameOfList, "iD": -1}
        }).then(function (response) {
            var realID = reponse.data.iD;
            console.log("Saved List")
        }, function errorCallBack(response) {
            alert("get messages error\n");
        });
    };




    $scope.close = function(result) {
        close(result, 500); // close, but give 500ms for bootstrap to animate
    };



    /*GET USER TODOLIST*/
    $scope.getMyList = function(){
        var y = $http({
            method: 'GET',
            url: '/getMyLists',
            params: {}
        }).then(function (response) {
            $scope.toDoList = response.data;
            $scope.resetView();
            console.log("it worked!");
            //$scope.posts[index]["comments"].splice(commentIndex, 1);
        }, function errorCallBack(response) {
            alert("get messages error\n");
        });
    };

    /*GET ALL VIEWABLE LISTS, INCLUDING OWNER'S LIST AND OTHER USERS' PUBLIC LISTS*/
    $scope.getViewableLists = function(){
        var y = $http({
            method: 'GET',
            url: '/getViewableLists',
            params: {}
        }).then(function (response) {
            $scope.toDoList = response.data;
            $scope.resetView();
            console.log("it worked!");
            //$scope.posts[index]["comments"].splice(commentIndex, 1);
        }, function errorCallBack(response) {
            alert("get messages error\n");
        });
    };

    /*GETS ITEM GIVEN THE SELECTED LIST*/
    $scope.getItems = function () {
        var y = $http({
            method: 'GET',
            url: '/getItems',
            params: {"listId": $scope.selectedList.entity.properties.real_ID}
        }).then(function (response) {
            $scope.itemEntityList = response.data;
            console.log("get items worked!");
            $("#grid").data('kendoGrid').dataSource.data([]);
            $scope.loadItemsToGrid();
            //$scope.posts[index]["comments"].splice(commentIndex, 1);
        }, function errorCallBack(response) {
            alert("get messages error\n");
        });
    };



    $scope.deleteList = function () {
        var y = $http({
            method: 'GET',
            url: '/deleteList',
            params: {"iD" : ""}
        }).then(function (response) {
            $scope.responseData = response.data;
            alert("it worked!");
            //$scope.posts[index]["comments"].splice(commentIndex, 1);
        }, function errorCallBack(response) {
            alert("get messages error\n");
        });
    }
    $scope.deleteItem = function () {
        var y = $http({
            method: 'GET',
            url: '/deleteItem',
            params: {"iD" : $scope.selectedItemEntity.properties.real_ID}
        }).then(function (response) {
            $scope.responseData = response.data;
            $scope.getItems();
            alert("it worked!");
            //$scope.posts[index]["comments"].splice(commentIndex, 1);
        }, function errorCallBack(response) {
            alert("get messages error\n");
        });
    };

    /* SAVE ITEMS THAT HAVE AN ID TO DELETE FROM DATASTORE WHEN SAVE */
    $scope.collectDelete = function (item) {
        /* CHECK IF ITEM HAS ID, IF IT DOES PUSH IT IN DELETE_ARRAY */
        if(item.hasID()){
            $scope.toDelete.push(item);
        }
    };
});