$(function () {
    $("#jqGrid").jqGrid({
        url: 'sys/generator/list',
        datatype: "json",
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
            password: ''
        }
	},
	methods: {
		query: function () {
			$("#jqGrid").jqGrid('setGridParam',{
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
        config: function() {
            $.ajax({
                type: "post",
                url: this.serverUrl() + "/sys/generator/config/get",
                data: JSON.stringify({}),
                dataType: "json",
                success: function(data){
                    vm.dataSource = data.data;
                }
            });
        },
        configSave: function () {
            $.ajax({
                type: "post",
                url: this.serverUrl() + "/sys/generator/config/save",
                data: JSON.stringify(vm.dataSource),
                dataType: "json",
                success: function(data){
                    $('#dataSource').modal('hide');
                }
            });
        },
        serverUrl: function () {
            return location.protocol + "//" + location.host;
        }
	}
});

