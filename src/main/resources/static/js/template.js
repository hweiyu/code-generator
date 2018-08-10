$(function () {
    $("#jqGrid").jqGrid({
        url: '/template/list',
        datatype: "json",
        mtype: 'POST',
        colModel: [
            { label: 'id', name: 'id', width: 50, key: true, hidden: true},
            {
                label: '操作', name: 'operate', width: 70,
                formatter: function (cellvalue, options, rowObject) {
                    return '<a href="javascript:void(0);" class="btn btn-primary" onclick="vm.get(' + options.rowId +')">编辑</a>&nbsp;&nbsp;<a href="javascript:void(0);" class="btn btn-primary" onclick="vm.getContext(' + options.rowId +')">编辑模板内容</a>&nbsp;&nbsp;<a href="javascript:void(0);" class="btn btn-primary" onclick="vm.delete(' + options.rowId +')">删除</a>';
                }
            },
			{ label: '模板名', name: 'templateName', width: 50},
			{ label: '模板类型', name: 'templateType', width: 50 },
            { label: '生成包名', name: 'packagePath', width: 100 },
            { label: '文件名', name: 'fileName', width: 100 }
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,20,50,100,200],
        rownumbers: true,
        rownumWidth: 35,
        autowidth:true,
        multiselect: false,
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
        },
        serializeGridData: function (postData) {
            return JSON.stringify(postData);
        },
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			name: null
		},
        addForm: {
            templateName:'',
            templateType: '',
            packagePath: '',
            fileName: ''
        },
        editForm: {
		    id: '',
            templateName:'',
            templateType: '',
            packagePath: '',
            fileName: ''
        },
        editContextForm: {
		    id: '',
            templateName:'',
            context:''
        }
	},
	methods: {
		query: function () {
			$("#jqGrid").jqGrid('setGridParam',{
                datatype: "json",
                postData:{'template': vm.q.name},
                page:1 
            }).trigger("reloadGrid");
		},
        serverUrl: function () {
            return location.protocol + "//" + location.host;
        },
        get: function(id) {
            $.ajax({
                type: "post",
                url: this.serverUrl() + "/template/get",
                data: JSON.stringify({id: id}),
                dataType: "json",
                success: function(data){
                    vm.editForm = data.data;
                    $('#editForm').modal('show');
                }
            });
        },
        edit: function() {
            $.ajax({
                type: "post",
                url: this.serverUrl() + "/template/update",
                data: JSON.stringify(vm.editForm),
                dataType: "json",
                success: function(data){
                    if ("false" === data.result) {
                        alert(data.message);
                    } else {
                        $('#editForm').modal('hide');
                        vm.query();
                    }
                }
            });
        },
        getContext: function(id) {
            $.ajax({
                type: "post",
                url: this.serverUrl() + "/template/get",
                data: JSON.stringify({id: id}),
                dataType: "json",
                success: function(data){
                    vm.editContextForm = data.data;
                    $('#editContextForm').modal('show');
                }
            });
        },
        editContext: function() {
            $.ajax({
                type: "post",
                url: this.serverUrl() + "/template/update",
                data: JSON.stringify(vm.editContextForm),
                dataType: "json",
                success: function(data){
                    if ("false" === data.result) {
                        alert(data.message);
                    } else {
                        $('#editContextForm').modal('hide');
                        vm.query();
                    }
                }
            });
        },
        delete: function (id) {
            confirm("确认删除？", function(){
                $.ajax({
                    type: "post",
                    url: vm.serverUrl() + "/template/delete",
                    data: JSON.stringify({id: id}),
                    dataType: "json",
                    success: function(data){
                        if ("false" === data.result) {
                            alert(data.message);
                        } else {
                            vm.query();
                        }
                    }
                });
            });
        },
        insert: function () {
            $.ajax({
                type: "post",
                url: this.serverUrl() + "/template/insert",
                data: JSON.stringify(vm.addForm),
                dataType: "json",
                success: function(data){
                    $('#addForm').modal('hide');
                    vm.query();
                    vm.addForm = {
                        templateName:'',
                        moduleName:'',
                        author:'',
                        tablePrefix: '',
                        templateType: '',
                        packagePath: '',
                        fileName: ''
                    };
                }
            });
        }
	}
});

