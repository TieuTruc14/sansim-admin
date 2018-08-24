package com.osp.web.dao.msisdn;

import com.osp.common.PagingResult;
import com.osp.modelCustomer.ConfigPackage;

import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 1/3/2018.
 */
public interface ConfigPackageDao {
    Optional<PagingResult> page(String packageCode,String packageName,Long from,Long to, PagingResult page);
    Optional<Long> add(ConfigPackage item,String ipClient) throws Exception;
    Optional<Boolean> edit(ConfigPackage item) throws Exception;
    Optional<Boolean> delete(Long id) throws Exception;
    Optional<ConfigPackage> get(Long id);
    Optional<ConfigPackage> getByPackageCode(String name);
    Optional<List<ConfigPackage>> list();
}
