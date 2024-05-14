package com.entity.model;

import com.entity.JichuEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;


/**
 * 基础
 * 接收传参的实体类
 *（实际开发中配合移动端接口开发手动去掉些没用的字段， 后端一般用entity就够用了）
 * 取自ModelAndView 的model名称
 */
public class JichuModel implements Serializable {
    private static final long serialVersionUID = 1L;




    /**
     * 主键
     */
    private Integer id;


    /**
     * 基础编号
     */
    private String jichuUuidNumber;


    /**
     * 基础名称
     */
    private String jichuName;


    /**
     * 基础照片
     */
    private String jichuPhoto;


    /**
     * 视频
     */
    private String jichuVideo;


    /**
     * 相关文件
     */
    private String jichuFile;


    /**
     * 基础类型
     */
    private Integer jichuTypes;


    /**
     * 点击次数
     */
    private Integer jichuClicknum;


    /**
     * 基础详情
     */
    private String jichuContent;


    /**
     * 是否上架
     */
    private Integer shangxiaTypes;


    /**
     * 逻辑删除
     */
    private Integer jichuDelete;


    /**
     * 创建时间  show1 show2 photoShow
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    private Date createTime;


    /**
	 * 获取：主键
	 */
    public Integer getId() {
        return id;
    }


    /**
	 * 设置：主键
	 */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
	 * 获取：基础编号
	 */
    public String getJichuUuidNumber() {
        return jichuUuidNumber;
    }


    /**
	 * 设置：基础编号
	 */
    public void setJichuUuidNumber(String jichuUuidNumber) {
        this.jichuUuidNumber = jichuUuidNumber;
    }
    /**
	 * 获取：基础名称
	 */
    public String getJichuName() {
        return jichuName;
    }


    /**
	 * 设置：基础名称
	 */
    public void setJichuName(String jichuName) {
        this.jichuName = jichuName;
    }
    /**
	 * 获取：基础照片
	 */
    public String getJichuPhoto() {
        return jichuPhoto;
    }


    /**
	 * 设置：基础照片
	 */
    public void setJichuPhoto(String jichuPhoto) {
        this.jichuPhoto = jichuPhoto;
    }
    /**
	 * 获取：视频
	 */
    public String getJichuVideo() {
        return jichuVideo;
    }


    /**
	 * 设置：视频
	 */
    public void setJichuVideo(String jichuVideo) {
        this.jichuVideo = jichuVideo;
    }
    /**
	 * 获取：相关文件
	 */
    public String getJichuFile() {
        return jichuFile;
    }


    /**
	 * 设置：相关文件
	 */
    public void setJichuFile(String jichuFile) {
        this.jichuFile = jichuFile;
    }
    /**
	 * 获取：基础类型
	 */
    public Integer getJichuTypes() {
        return jichuTypes;
    }


    /**
	 * 设置：基础类型
	 */
    public void setJichuTypes(Integer jichuTypes) {
        this.jichuTypes = jichuTypes;
    }
    /**
	 * 获取：点击次数
	 */
    public Integer getJichuClicknum() {
        return jichuClicknum;
    }


    /**
	 * 设置：点击次数
	 */
    public void setJichuClicknum(Integer jichuClicknum) {
        this.jichuClicknum = jichuClicknum;
    }
    /**
	 * 获取：基础详情
	 */
    public String getJichuContent() {
        return jichuContent;
    }


    /**
	 * 设置：基础详情
	 */
    public void setJichuContent(String jichuContent) {
        this.jichuContent = jichuContent;
    }
    /**
	 * 获取：是否上架
	 */
    public Integer getShangxiaTypes() {
        return shangxiaTypes;
    }


    /**
	 * 设置：是否上架
	 */
    public void setShangxiaTypes(Integer shangxiaTypes) {
        this.shangxiaTypes = shangxiaTypes;
    }
    /**
	 * 获取：逻辑删除
	 */
    public Integer getJichuDelete() {
        return jichuDelete;
    }


    /**
	 * 设置：逻辑删除
	 */
    public void setJichuDelete(Integer jichuDelete) {
        this.jichuDelete = jichuDelete;
    }
    /**
	 * 获取：创建时间  show1 show2 photoShow
	 */
    public Date getCreateTime() {
        return createTime;
    }


    /**
	 * 设置：创建时间  show1 show2 photoShow
	 */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    }
