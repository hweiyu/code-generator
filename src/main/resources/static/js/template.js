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
            { label: '模板组', name: 'groupName', width: 50},
			{ label: '模板类型', name: 'templateTypeName', width: 50 },
            { label: '生成路径', name: 'packagePath', width: 100 },
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
        }
    });
});

var vm = new Vue({
    el:'#rrapp',
    data:{
        q:{
            groupId: 0,
            type: 0,
            name: null,
            groupList:[
                {id: 0, groupName: '请选择模板组'}
            ],
            typeList:[
                {id: 0, name: '请选择模板类型'},
                {id: 1, name: 'java'},
                {id: 2, name: 'xml'},
                {id: 3, name: 'html'},
                {id: 4, name: 'javascript'}
            ]
        },
        addForm: {
            templateName:'',
            groupId: 0,
            templateType: 1,
            packagePath: '',
            fileName: ''
        },
        editForm: {
            id: '',
            templateName:'',
            groupId: '',
            templateType: '',
            packagePath: '',
            fileName: ''
        },
        editContextForm: {
            id: '',
            templateName:'',
            context:''
        },
        typeList:[
            {id: 1, name: 'java'},
            {id: 2, name: 'xml'},
            {id: 3, name: 'html'},
            {id: 4, name: 'javascript'}
        ],
        groupList:[],
        helpForm: {
            id: 0
        },
        helpInfo: {}
    },
    methods: {
        query: function () {
            $("#jqGrid").jqGrid('setGridParam',{
                datatype: "json",
                postData:{'type': vm.q.type, 'template': vm.q.name, 'groupId': vm.q.groupId},
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
            vm.helpForm.id = id;
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
                        groupId: 0,
                        templateType: 1,
                        packagePath: '',
                        fileName: ''
                    };
                }
            });
        },
        getGroup: function() {
            $.ajax({
                type: "post",
                url: this.serverUrl() + "/group/list/all",
                data: JSON.stringify({}),
                dataType: "json",
                success: function(data){
                    vm.q.groupList = [{id: 0, groupName: '请选择模板组'}];
                    for (var i = 0; i < data.data.length; i++) {
                        vm.q.groupList.push(data.data[i]);
                    }
                    vm.groupList = data.data;
                }
            });
        },
        getHelp: function() {
            // $.ajax({
            //     type: "post",
            //     url: this.serverUrl() + "/template/help/get",
            //     data: JSON.stringify({id: vm.helpForm.id}),
            //     dataType: "json",
            //     success: function(data){
            //         vm.helpInfo = data.data;
            //     }
            // });
            $('#helpForm').modal('show');
        }
    }
});

vm.getGroup();


