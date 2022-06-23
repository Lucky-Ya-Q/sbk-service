package com.ruoyi.service.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class SbkLibraryReader extends BaseEntity {
    // ID
    private Long id;
    // 读者证号（如果有值采用传过来的值作为证号，如果没有则按系统设置规则生成）
//    private String rdid;
    // 姓名
    private String rdname;
    // 密码(至少6位)
    private String rdpasswd;
    // 身份证号码
    private String rdcertify;
    // 办证人员
    private String operator;
    // 开户馆
    private String rdlib;
    // 读者类型
    private String rdtype;
    // 手机号码
    private String rdloginid;
    // email
    private String rdemail;
    // 出生日期 格式：yyyy-MM-dd
//    private String rdborndate;
    // 性别 1 男 0女 不填默认1
    private String rdsex;
    // 住址
    private String rdaddress;
    // 邮编
//    private String rdpostcode;
    // 电话号码
//    private String rdphone;
    // 单位
//    private String rdunit;
    // 专业
//    private String rdsort1;
    // 职业
    private String rdsort2;
    // 职务
//    private String rdsort3;
    // 职称
//    private String rdsort4;
    // 文化
//    private String rdsort5;
    // 备注
//    private String rdremark;
    // 民族
//    private String rdnation;
    // 籍贯
//    private String rdnative;
    // 读者状态1 有效2验证3 挂失4 暂停（不填默认）5 注销10信用有效（V3.0.9.17060110及后续版本增加）
//    private String rdcfstate;
    // 押金
//    private String deposit;
    // 押金支付方式，押金有值的时候传入1 现金 2 IC卡 5 支付宝 6 微信支付 7 银行卡支付
//    private String paytype;
    // 订单流水号
//    private String serialno;
    // 附属卡卡号
//    private String cardid;
    // 附属卡类型
//    private String type;
    // 启用时间 格式：yyyy-MM-dd（不传取当前时间）
//    private String rdstartdate;
    // 终止时间 格式：yyyy-MM-dd（不传取当前时间+读者类型有效天数）
//    private String rdenddate;
    // 办证时间 格式：yyyy-MM-dd （不传取当前时间）
//    private String rdintime;
    // 系别
//    private String departmant;
    // 年级
//    private String grade;
    // 读者照片（ base64编码的二进制图片数据 ）
//    private String baseimg64;
    // 传1表示读者图片不进行压缩
//    private String nocompress;
    // 是否开通区块链
//    private String ischain;
    // 其他编号
//    private String othercardno;
    // 工作/学生证号
//    private String workcardno;
    // 班级
//    private String classnum;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
