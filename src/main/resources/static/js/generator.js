$(function () {
    $("#jqGrid").jqGrid({
        url: 'sys/generator/list',
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
			tableName: null
		},
        dataSource: {
		    ip: '',
            port: '',
            database: '',
            userName: '',
            password: '',
            mainPath:''
        },
        param: {
		    author: '',
            module: '',
            javaPackage:'',
            tablePrefix:''
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
		generator: function() {
			var tableNames = getSelectedRows();
			if(tableNames == null){
				return ;
			}
			location.href = "sys/generator/code?tables=" + JSON.stringify(tableNames);
		},
        getDataSourceConfig: function() {
            $.ajax({
                type: "post",
                url: this.serverUrl() + "/sys/generator/datasource/get",
                data: JSON.stringify({}),
                dataType: "json",
                success: function(data){
                    vm.dataSource = data.data;
                }
            });
        },
        saveDataSourceConfig: function () {
            $.ajax({
                type: "post",
                url: this.serverUrl() + "/sys/generator/datasource/save",
                data: JSON.stringify(vm.dataSource),
                dataType: "json",
                success: function(data){
                    $('#dataSource').modal('hide');
                }
            });
        },
        serverUrl: function () {
            return location.protocol + "//" + location.host;
        },
        connectTest: function () {
            $.ajax({
                type: "post",
                url: this.serverUrl() + "/sys/generator/datasource/connect/test",
                data: JSON.stringify(vm.dataSource),
                dataType: "json",
                success: function(data){
                    alert(data.data);
                }
            });
        },
        getParam: function() {
            $.ajax({
                type: "post",
                url: this.serverUrl() + "/sys/generator/param/get",
                data: JSON.stringify({}),
                dataType: "json",
                success: function(data){
                    vm.param = data.data;
                }
            });
        },
        saveParam: function () {
            $.ajax({
                type: "post",
                url: this.serverUrl() + "/sys/generator/param/save",
                data: JSON.stringify(vm.param),
                dataType: "json",
                success: function(data){
                    $('#param').modal('hide');
                }
            });
        },
	}
});

