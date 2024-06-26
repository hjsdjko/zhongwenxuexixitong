
package com.controller;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import com.alibaba.fastjson.JSONObject;
import java.util.*;
import org.springframework.beans.BeanUtils;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.ContextLoader;
import javax.servlet.ServletContext;
import com.service.TokenService;
import com.utils.*;
import java.lang.reflect.InvocationTargetException;

import com.service.DictionaryService;
import org.apache.commons.lang3.StringUtils;
import com.annotation.IgnoreAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.*;
import com.entity.view.*;
import com.service.*;
import com.utils.PageUtils;
import com.utils.R;
import com.alibaba.fastjson.*;

/**
 * 情景学习
 * 后端接口
 * @author
 * @email
*/
@RestController
@Controller
@RequestMapping("/qingjingxuexi")
public class QingjingxuexiController {
    private static final Logger logger = LoggerFactory.getLogger(QingjingxuexiController.class);

    @Autowired
    private QingjingxuexiService qingjingxuexiService;


    @Autowired
    private TokenService tokenService;
    @Autowired
    private DictionaryService dictionaryService;

    //级联表service

    @Autowired
    private YonghuService yonghuService;


    /**
    * 后端列表
    */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("page方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));
        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永不会进入");
        else if("用户".equals(role))
            params.put("yonghuId",request.getSession().getAttribute("userId"));
        params.put("qingjingxuexiDeleteStart",1);params.put("qingjingxuexiDeleteEnd",1);
        if(params.get("orderBy")==null || params.get("orderBy")==""){
            params.put("orderBy","id");
        }
        PageUtils page = qingjingxuexiService.queryPage(params);

        //字典表数据转换
        List<QingjingxuexiView> list =(List<QingjingxuexiView>)page.getList();
        for(QingjingxuexiView c:list){
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(c, request);
        }
        return R.ok().put("data", page);
    }

    /**
    * 后端详情
    */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id, HttpServletRequest request){
        logger.debug("info方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        QingjingxuexiEntity qingjingxuexi = qingjingxuexiService.selectById(id);
        if(qingjingxuexi !=null){
            //entity转view
            QingjingxuexiView view = new QingjingxuexiView();
            BeanUtils.copyProperties( qingjingxuexi , view );//把实体数据重构到view中

            //修改对应字典表字段
            dictionaryService.dictionaryConvert(view, request);
            return R.ok().put("data", view);
        }else {
            return R.error(511,"查不到数据");
        }

    }

    /**
    * 后端保存
    */
    @RequestMapping("/save")
    public R save(@RequestBody QingjingxuexiEntity qingjingxuexi, HttpServletRequest request){
        logger.debug("save方法:,,Controller:{},,qingjingxuexi:{}",this.getClass().getName(),qingjingxuexi.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永远不会进入");

        Wrapper<QingjingxuexiEntity> queryWrapper = new EntityWrapper<QingjingxuexiEntity>()
            .eq("qingjingxuexi_uuid_number", qingjingxuexi.getQingjingxuexiUuidNumber())
            .eq("qingjingxuexi_name", qingjingxuexi.getQingjingxuexiName())
            .eq("qingjingxuexi_video", qingjingxuexi.getQingjingxuexiVideo())
            .eq("qingjingxuexi_types", qingjingxuexi.getQingjingxuexiTypes())
            .eq("qingjingxuexi_clicknum", qingjingxuexi.getQingjingxuexiClicknum())
            .eq("shangxia_types", qingjingxuexi.getShangxiaTypes())
            .eq("qingjingxuexi_delete", qingjingxuexi.getQingjingxuexiDelete())
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        QingjingxuexiEntity qingjingxuexiEntity = qingjingxuexiService.selectOne(queryWrapper);
        if(qingjingxuexiEntity==null){
            qingjingxuexi.setQingjingxuexiClicknum(1);
            qingjingxuexi.setShangxiaTypes(1);
            qingjingxuexi.setQingjingxuexiDelete(1);
            qingjingxuexi.setCreateTime(new Date());
            qingjingxuexiService.insert(qingjingxuexi);
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

    /**
    * 后端修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody QingjingxuexiEntity qingjingxuexi, HttpServletRequest request){
        logger.debug("update方法:,,Controller:{},,qingjingxuexi:{}",this.getClass().getName(),qingjingxuexi.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
//        if(false)
//            return R.error(511,"永远不会进入");
        //根据字段查询是否有相同数据
        Wrapper<QingjingxuexiEntity> queryWrapper = new EntityWrapper<QingjingxuexiEntity>()
            .notIn("id",qingjingxuexi.getId())
            .andNew()
            .eq("qingjingxuexi_uuid_number", qingjingxuexi.getQingjingxuexiUuidNumber())
            .eq("qingjingxuexi_name", qingjingxuexi.getQingjingxuexiName())
            .eq("qingjingxuexi_video", qingjingxuexi.getQingjingxuexiVideo())
            .eq("qingjingxuexi_types", qingjingxuexi.getQingjingxuexiTypes())
            .eq("qingjingxuexi_clicknum", qingjingxuexi.getQingjingxuexiClicknum())
            .eq("shangxia_types", qingjingxuexi.getShangxiaTypes())
            .eq("qingjingxuexi_delete", qingjingxuexi.getQingjingxuexiDelete())
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        QingjingxuexiEntity qingjingxuexiEntity = qingjingxuexiService.selectOne(queryWrapper);
        if("".equals(qingjingxuexi.getQingjingxuexiPhoto()) || "null".equals(qingjingxuexi.getQingjingxuexiPhoto())){
                qingjingxuexi.setQingjingxuexiPhoto(null);
        }
        if("".equals(qingjingxuexi.getQingjingxuexiVideo()) || "null".equals(qingjingxuexi.getQingjingxuexiVideo())){
                qingjingxuexi.setQingjingxuexiVideo(null);
        }
        if("".equals(qingjingxuexi.getQingjingxuexiFile()) || "null".equals(qingjingxuexi.getQingjingxuexiFile())){
                qingjingxuexi.setQingjingxuexiFile(null);
        }
        if(qingjingxuexiEntity==null){
            qingjingxuexiService.updateById(qingjingxuexi);//根据id更新
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

    /**
    * 删除
    */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
        logger.debug("delete:,,Controller:{},,ids:{}",this.getClass().getName(),ids.toString());
        ArrayList<QingjingxuexiEntity> list = new ArrayList<>();
        for(Integer id:ids){
            QingjingxuexiEntity qingjingxuexiEntity = new QingjingxuexiEntity();
            qingjingxuexiEntity.setId(id);
            qingjingxuexiEntity.setQingjingxuexiDelete(2);
            list.add(qingjingxuexiEntity);
        }
        if(list != null && list.size() >0){
            qingjingxuexiService.updateBatchById(list);
        }
        return R.ok();
    }


    /**
     * 批量上传
     */
    @RequestMapping("/batchInsert")
    public R save( String fileName){
        logger.debug("batchInsert方法:,,Controller:{},,fileName:{}",this.getClass().getName(),fileName);
        try {
            List<QingjingxuexiEntity> qingjingxuexiList = new ArrayList<>();//上传的东西
            Map<String, List<String>> seachFields= new HashMap<>();//要查询的字段
            Date date = new Date();
            int lastIndexOf = fileName.lastIndexOf(".");
            if(lastIndexOf == -1){
                return R.error(511,"该文件没有后缀");
            }else{
                String suffix = fileName.substring(lastIndexOf);
                if(!".xls".equals(suffix)){
                    return R.error(511,"只支持后缀为xls的excel文件");
                }else{
                    URL resource = this.getClass().getClassLoader().getResource("static/upload/" + fileName);//获取文件路径
                    File file = new File(resource.getFile());
                    if(!file.exists()){
                        return R.error(511,"找不到上传文件，请联系管理员");
                    }else{
                        List<List<String>> dataList = PoiUtil.poiImport(file.getPath());//读取xls文件
                        dataList.remove(0);//删除第一行，因为第一行是提示
                        for(List<String> data:dataList){
                            //循环
                            QingjingxuexiEntity qingjingxuexiEntity = new QingjingxuexiEntity();
//                            qingjingxuexiEntity.setQingjingxuexiUuidNumber(data.get(0));                    //情景学习编号 要改的
//                            qingjingxuexiEntity.setQingjingxuexiName(data.get(0));                    //情景学习名称 要改的
//                            qingjingxuexiEntity.setQingjingxuexiPhoto("");//照片
//                            qingjingxuexiEntity.setQingjingxuexiVideo(data.get(0));                    //视频 要改的
//                            qingjingxuexiEntity.setQingjingxuexiFile(data.get(0));                    //相关文件 要改的
//                            qingjingxuexiEntity.setQingjingxuexiTypes(Integer.valueOf(data.get(0)));   //情景学习类型 要改的
//                            qingjingxuexiEntity.setQingjingxuexiClicknum(Integer.valueOf(data.get(0)));   //点击次数 要改的
//                            qingjingxuexiEntity.setQingjingxuexiContent("");//照片
//                            qingjingxuexiEntity.setShangxiaTypes(Integer.valueOf(data.get(0)));   //是否上架 要改的
//                            qingjingxuexiEntity.setQingjingxuexiDelete(1);//逻辑删除字段
//                            qingjingxuexiEntity.setCreateTime(date);//时间
                            qingjingxuexiList.add(qingjingxuexiEntity);


                            //把要查询是否重复的字段放入map中
                                //情景学习编号
                                if(seachFields.containsKey("qingjingxuexiUuidNumber")){
                                    List<String> qingjingxuexiUuidNumber = seachFields.get("qingjingxuexiUuidNumber");
                                    qingjingxuexiUuidNumber.add(data.get(0));//要改的
                                }else{
                                    List<String> qingjingxuexiUuidNumber = new ArrayList<>();
                                    qingjingxuexiUuidNumber.add(data.get(0));//要改的
                                    seachFields.put("qingjingxuexiUuidNumber",qingjingxuexiUuidNumber);
                                }
                        }

                        //查询是否重复
                         //情景学习编号
                        List<QingjingxuexiEntity> qingjingxuexiEntities_qingjingxuexiUuidNumber = qingjingxuexiService.selectList(new EntityWrapper<QingjingxuexiEntity>().in("qingjingxuexi_uuid_number", seachFields.get("qingjingxuexiUuidNumber")).eq("qingjingxuexi_delete", 1));
                        if(qingjingxuexiEntities_qingjingxuexiUuidNumber.size() >0 ){
                            ArrayList<String> repeatFields = new ArrayList<>();
                            for(QingjingxuexiEntity s:qingjingxuexiEntities_qingjingxuexiUuidNumber){
                                repeatFields.add(s.getQingjingxuexiUuidNumber());
                            }
                            return R.error(511,"数据库的该表中的 [情景学习编号] 字段已经存在 存在数据为:"+repeatFields.toString());
                        }
                        qingjingxuexiService.insertBatch(qingjingxuexiList);
                        return R.ok();
                    }
                }
            }
        }catch (Exception e){
            return R.error(511,"批量插入数据异常，请联系管理员");
        }
    }





    /**
    * 前端列表
    */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("list方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));

        // 没有指定排序字段就默认id倒序
        if(StringUtil.isEmpty(String.valueOf(params.get("orderBy")))){
            params.put("orderBy","id");
        }
        PageUtils page = qingjingxuexiService.queryPage(params);

        //字典表数据转换
        List<QingjingxuexiView> list =(List<QingjingxuexiView>)page.getList();
        for(QingjingxuexiView c:list)
            dictionaryService.dictionaryConvert(c, request); //修改对应字典表字段
        return R.ok().put("data", page);
    }

    /**
    * 前端详情
    */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id, HttpServletRequest request){
        logger.debug("detail方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        QingjingxuexiEntity qingjingxuexi = qingjingxuexiService.selectById(id);
            if(qingjingxuexi !=null){

                //点击数量加1
                qingjingxuexi.setQingjingxuexiClicknum(qingjingxuexi.getQingjingxuexiClicknum()+1);
                qingjingxuexiService.updateById(qingjingxuexi);

                //entity转view
                QingjingxuexiView view = new QingjingxuexiView();
                BeanUtils.copyProperties( qingjingxuexi , view );//把实体数据重构到view中

                //修改对应字典表字段
                dictionaryService.dictionaryConvert(view, request);
                return R.ok().put("data", view);
            }else {
                return R.error(511,"查不到数据");
            }
    }


    /**
    * 前端保存
    */
    @RequestMapping("/add")
    public R add(@RequestBody QingjingxuexiEntity qingjingxuexi, HttpServletRequest request){
        logger.debug("add方法:,,Controller:{},,qingjingxuexi:{}",this.getClass().getName(),qingjingxuexi.toString());
        Wrapper<QingjingxuexiEntity> queryWrapper = new EntityWrapper<QingjingxuexiEntity>()
            .eq("qingjingxuexi_uuid_number", qingjingxuexi.getQingjingxuexiUuidNumber())
            .eq("qingjingxuexi_name", qingjingxuexi.getQingjingxuexiName())
            .eq("qingjingxuexi_video", qingjingxuexi.getQingjingxuexiVideo())
            .eq("qingjingxuexi_types", qingjingxuexi.getQingjingxuexiTypes())
            .eq("qingjingxuexi_clicknum", qingjingxuexi.getQingjingxuexiClicknum())
            .eq("shangxia_types", qingjingxuexi.getShangxiaTypes())
            .eq("qingjingxuexi_delete", qingjingxuexi.getQingjingxuexiDelete())
            ;
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        QingjingxuexiEntity qingjingxuexiEntity = qingjingxuexiService.selectOne(queryWrapper);
        if(qingjingxuexiEntity==null){
            qingjingxuexi.setQingjingxuexiDelete(1);
            qingjingxuexi.setCreateTime(new Date());
        qingjingxuexiService.insert(qingjingxuexi);
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }


}
