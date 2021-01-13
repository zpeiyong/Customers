package com.dataint.cloud.common.dim;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 前端显示不同：[300,400)警告-黄色提示；[400,..)错误-红色提示
 * [500,600)操作错误；[400,500)其它错误；[300,400)数据校验警告
 */
@Getter
public enum BaseExceptionEnum implements ExceptionType {

    QITA("其他", 9),

    // [300,400)数据校验警告
    ARGUMENT_NOT_VALID("请求参数校验不通过", 321),
    UPLOAD_FILE_SIZE_LIMIT("上传文件大小超过限制", 322),
    FILE_VER_NOT_FOUND("文件不存在", 323),
    DUPLICATE_PRIMARY_KEY("唯一键冲突", 324),
    ES_FAILED("数据索引操作失败", 325),
    ES_ERROR("数据索引报错", 326),
    DATE_PARSE_ERROR("日期格式化解析错误", 327),

    DATA_VER_NOT_EXIST("该数据不存在", 330),
    DATA_VER_ALREADY_EXIST("该数据已存在", 331),
    DATA_VER_CHILD_EXIST("该数据存在下级关联数据, 请先删除下级关联数据。", 332),
    DATA_VER_USER_NOT_EXIST("该用户不存在!", 333),
    DATA_VER_USER_EXIST("用户名已存在!", 334),
    DATA_VER_ROLE_NOT_EXIST("该角色不存在!", 335),
    DATA_VER_PASSWORD_NULL("密码不能为空！", 336),
    DATA_VER_USERNAME_PASSWORD_ERROR("用户名或密码不正确！", 337),
    DATA_VER_USER_NOT_ENABLED("当前用户不可用, 请联系管理员!", 338),


    DB_CONNECT_FAILED("数据库连接失败!", 350),
    DB_TYPE_NOT_SUPPORT("暂不支持对应的数据库类型", 351),

    // [400,500)其它错误
    ENCODING_UNSUPPORTED("编码错误!", 420),

    SYSTEM_ERROR("系统异常!", 410),
    SYSTEM_BUSY("系统繁忙,请稍候再试!", 411),

    GATEWAY_ERROR("网关异常!", 501),
    GATEWAY_NOT_FOUND_SERVICE("网关未找到服务!", 503),
    GATEWAY_CONNECT_TIME_OUT("网关超时!", 504),

    // [500,600)操作错误
    DATA_REPETITION("数据重复错误！", 511),
    ;

//    DATA_NOTEXSIT("该数据不存在。", 300),
//    DATA_VER_FAULSE("输入的数据校验未通过。", 310),
//    DATA_VER_CHILD_EXSIT("该数据存在下级关联数据，请先删除下级关联数据。", 311),
//    DATA_VER_DEP_CHILD_EXSIT("该部门下存在子部门，请先删除子部门。", 312),
//    DATA_VER_USER_PAS_FAULSE("输入的密码不正确，请重新输入密码。", 313),
//    DATA_VER_DEP_POS_CHILD_EXSIT("该部门下存在岗位，请先删除岗位。", 314),
//    DATA_VER_COM_DEP_CHILD_EXSIT("该单位下存在部门，请先删除部门。", 315),
//    DATA_VER_POS_USER_CHILD_EXSIT("该岗位下存在人员，请先删除人员。", 316),
//    DATA_VER_EQUIPTYPE_EQUIP_CHILD_EXSIT("该设备类型下存在设备，请先删除设备。", 317),
//    DATA_VER_AREA_EQUIPMENT_CHILD_EXSIT("该区域下存在设备，请先删除设备。", 318),
//    DATA_VER_AREA_EXSIT("区域名已存在！", 319),
//    DATA_VER_CONFIG_EXSIT("参数名已存在！", 320),
//    DATA_VER_IDNUMBER_EXSIT("证件号已存在！", 321),
//    DATA_VER_AREA_EQUIPMENT_CHILD_STATE("该区域下存在设备，不可修改状态为禁用！", 322),
//    DATA_VER_EQUIPTYPENAME_EXIT("该设备类型已存在！", 323),
//    DATA_USERNAME_INFO_EXIT("设备名称或设备编号已存在！", 324),
//    DATA_VER_SERIALNUMBER_EXIST("该编号已存在！", 325),
//    DATA_VER_POS_EXIST("该岗位已存在！", 326),
//    DATA_VER_USERLOGINNAME_EXIST("用户名已存在！", 327),
//    DATA_VER_DEP_DEPTNAME_EXSIT("部门名称已存在，请重新输入！", 328),
//    DATA_VER_COM_COM_CHILD_EXSIT("父级单位选择有误，请重新选择！", 329),
//    DATA_VER_COM_COMNAME_EXSIT("单位名称重复，请重新填写！", 330),
//    DATA_VER_ROLE_ROLENAME_EXSIT("角色名称重复，请重新填写！", 331),
//    DATA_VER_VEHCILE_CODE_EXSIT("车牌号已存在！", 332),
//    DATA_VER_COM_COMPANY_CHILD_EXSIT("该单位下存在子单位，请先删除子单位。", 333),
//    DATA_VER_CARDNUM_EXIST("该门禁卡号已存在", 334),
//    DATA_VER_WARNINGSIGNAL_EXIST("该警号已存在", 335),
//    DATA_VER_COMMUNICATION_EXIST("该通讯号已存在", 336),
//    DATA_EQUIPTHIRDID_EXIT("设备第三方编号已存在！", 337),
//    DATA_VER_EQUIPMENT_USING("该定位设备正在被其他人使用中", 338),
//    DATA_VER_RQUIREDENT_PERSON("该人员在同个时间段被其他区域选择", 339),
//    UNAUTHORIZED(HttpStatus.UNAUTHORIZED),
//    DATA_VER_PARENT_NOTEXSIT("该数据父级关联数据不存在！", 450),
//    DATA_VER_MOU_PARENT_NOTEXSIT("该模块/菜单/功能父级菜单不存在！", 451),
//    DATA_VER_PASSWORD_NULL("密码不能为空！", 452),
//    DATA_VER_USERNAME_PASSWORD_ERROR("用户名或密码不正确！", 453),
//    DATA_VER_OLD_PASSWORD_NULL("原密码不能为空！", 454),
//    DATA_VER_NEW_PASSWORD_NULL("新密码不能为空！", 455),
//    DATA_VER_OLE_NEW_PASSWORD_EQUAL("和原密码不能相同！", 456),
//    DATA_VER_OLD_PASSWORD_ERROR("原密码不正确！", 457),
//    DATA_VER_ALREADY_EXIST_ERROR("用户已存在！", 458),
//    DATA_VER_EVENT_ALARM_TIME_ERROR("设备策略告警时间解析失败", 459),
//    DATA_VER_EVENT_NOTIFY_TIME_ERROR("消息推送时段解析失败", 460),
//    DATA_VER_EVENT_JOIN_ERROR("事件对接字段错误", 461),
//    DATA_VER_CAMERA_EXSIT("由于非正常操作，存在摄像机被删除，请检查！", 499),
//    DATA_ACCESS_FAILURE("数据存取失败！", 500),
//    OPT_FAULSE("操作失败！", 501),
//    OPT_QUERY_FAULSE("查询失败！", 502),
//    OPT_SAVE_FAULSE("保存失败！", 503),
//    OPT_UPDATE_FAULSE("修改失败！", 504),
//    OPT_DEL_FAULSE("删除失败！", 505),
//    DATA_VER_RELOAD_ERROR("请重新登录！", 506),
//    FILE_NOT_FOUND("文件上传目录不存在！", 507),
//    FILE_STORE_FAULSE("文件保存失败！", 508),
//    FILE_UPLOAD_FALSE("文件上传失败！", 509),
//    DATA_ANALYSIS_USER_KEY_ERROR("解析用户失败！", 510),
//    DATA_REPETITION("数据重复错误！", 511),
//    DATA_IMPORTNULL("导入数据为空！", 512),
//    DATA_FORMAT_ERROR("数据格式错误！", 513),
//    DATA_VER_KEY_ERROR("密钥生成失败！", 514),
//    KA_DISPATCH_HANDLER_ERROR("加载kafka消息分发处理器错误！", 515),
//    FILE_NOT_EXIST("文件不存在！", 516),
//    FILE_TOO_LARGE("文件过大！", 517),
//    XML_PARSE_ERROR("XML 格式转换失败", 518),
//    DATA_CHANGE_EXCEPTION("数据更新异常", 519),
//    OPT_DEL_CAN_NOT_DELETE("该区域存在子区域,不能删除！", 520),
//    DATA_VER_WATERWAY_RFID_CHILD_EXSIT("该航道关联了RFID，请先删除RFID关联数据。", 601),
//    DATA_VER_WATERWAY_WHARF_CHILD_EXSIT("该航道关联了码头，请先删除码头关联数据。", 602),
//    DATA_VER_WATERWAY_BRIDGE_CHILD_EXSIT("该航道关联了桥梁，请先删除桥梁关联数据。", 603),
//    DATA_VER_WATERWAY_MONITOR_CHILD_EXSIT("该航道关联了监控点，请先删除监控点关联数据。", 604),
//    DATA_VER_INSPECTION_RECORD_CHILD_EXSIT("该计划关联了巡检记录，请先删除巡检记录关联数据。", 609),
//    DATA_VER_OPS_PLAN_CHILD_EXSIT("该运维人员关联了巡检计划，请先删除巡检计划关联数据！", 610),
//    DATA_VER_OPS_RECORD_CHILD_EXSIT("该运维人员关联了巡检记录，请先删除巡检记录关联数据！", 611),
//    DATA_VER_ASSETS_PLAN_CHILD_EXSIT("该资产关联了巡检计划!", 612);


    private String name;
    private int index;

    BaseExceptionEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }

    BaseExceptionEnum(HttpStatus httpStatus) {
        this.name = httpStatus.getReasonPhrase();
        this.index = httpStatus.value();
    }

}