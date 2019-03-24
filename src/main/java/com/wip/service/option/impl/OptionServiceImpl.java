/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * DateTime: 2018/7/27 21:55
 **/
package com.wip.service.option.impl;

import com.wip.constant.ErrorConstant;
import com.wip.dao.OptionDao;
import com.wip.exception.BusinessException;
import com.wip.model.OptionsDomain;
import com.wip.service.option.OptionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class OptionServiceImpl implements OptionService {

    @Autowired
    private OptionDao optionDao;

    @Override
    @Cacheable(value = "optionsCache", key = "'options_'")
    public List<Map<String,Object>> getOptions() {
        return optionDao.getOptions();
    }

    @Override
    @Transactional
    @CacheEvict(value = {"optionsCache", "optionCache"}, allEntries = true, beforeInvocation = true)
    public void saveOptions(Map<String, String> options) {
        if (null != options && !options.isEmpty()) {
            optionDao.updateOptionByName(options);
        }
    }


    @Override
    @Cacheable(value = "optionCache", key = "'optionByname+' + #p0")
    public OptionsDomain getOptionByName(String name) {
        if (StringUtils.isBlank(name))
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        return optionDao.getOptionByName(name);
    }
}
