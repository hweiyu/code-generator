$(function () {
    $("#jqGrid").jqGrid({
        url: 'code/generator/list',
        datatype: "local",
        colModel: [
			{ label: '表名', name: 'tableName', width: 100, key: true },
			{ label: 'Engine', name: 'engine', width: 70},
			{ label: '表备注', name: 'tableComment', width: 100 },
			{ label: '创建时间', name: 'createTime', width: 100 }
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,20,50,100,200],
        rownumbers: true,
        rownumWidth: 35,
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "data.list",
            page: "data.currPage",
            total: "data.totalPage",
            records: "data.totalCount"
        },
        prmNames : {
            page:"page",
            rows:"limit",
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        },
        loadComplete:function(data) {
            if ("false" === data.result) {
                alert(data.message);
            }
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
		    sourceId: 0,
			tableName: null
		},
        dataSourceList: [],
        groupList: [],
        genForm: {
		    groupId: 0
        }
	},
	methods: {
		query: function () {
			$("#jqGrid").jqGrid('setGridParam',{
                datatype: "json",
                postData:{'tableName': vm.q.tableName},
                page:1 
            }).trigger("reloadGrid");
		},
        preGen: function () {
            var tableNames = getSelectedRows();
            if(tableNames == null){
                return ;
            }
            $('#genForm').modal('show');
        },
		generator: function() {
			var tableNames = getSelectedRows();
			if(tableNames == null){
				return ;
			}
			if (vm.genForm.groupId <= 0) {
			    alert("请选择模板组");
			    return;
            }
            $('#genForm').modal('hide');
			location.href = "code/generator/code?tables=" + JSON.stringify(tableNames) + "&groupId=" + vm.genForm.groupId;
		},
        getDataSource: function () {
            $.ajax({
                type: "post",
                url: this.serverUrl() + "/datasource/list/all",
                data: JSON.stringify({}),
                dataType: "json",
                success: function(data){
                    vm.dataSourceList = [{id: 0, dataSourceName: '请选择数据源'}];
                    for (var i = 0; i < data.data.length; i++) {
                        vm.dataSourceList.push(data.data[i]);
                    }
                }
            });
        },
        serverUrl: function () {
            return location.protocol + "//" + location.host;
        },
        getGroup: function() {
            $.ajax({
                type: "post",
                url: this.serverUrl() + "/group/list/all",
                data: JSON.stringify({}),
                dataType: "json",
                success: function(data){
                    vm.groupList = [{id: 0, groupName: '请选择模板组'}];
                    for (var i = 0; i < data.data.length; i++) {
                        vm.groupList.push(data.data[i]);
                    }
                }
            });
        }
	}
});

vm.getDataSource();
vm.getGroup();

