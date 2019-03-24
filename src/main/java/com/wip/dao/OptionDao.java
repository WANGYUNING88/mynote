/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * DateTime: 2018/7/27 21:56
 **/
package com.wip.dao;

import com.wip.model.OptionsDomain;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 网站选项相关Dao接口
 */
@Mapper
public interface OptionDao {

    /**
     * 获取网站全部信息
     * @return
     */
    List<Map<String, Object>> getOptions();

    /**
     * 更新网站配置
     * @param options
     */
    void updateOptionByName(Map<String,String> options);

    /**
     * 通过名称网站配置
     * @param name
     * @return
     */
    OptionsDomain getOptionByName(String name);
}
