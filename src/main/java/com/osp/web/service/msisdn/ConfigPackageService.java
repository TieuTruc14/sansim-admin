package com.osp.web.service.msisdn;

import com.osp.common.PagingResult;
import com.osp.modelCustomer.ConfigPackage;

import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 1/3/2018.
 */
public interface ConfigPackageService {
    Optional<PagingResult> page(String packageCode,String packageName,Long from,Long to, PagingResult page);
    Optional<Boolean> add(ConfigPackage item,String ipClient) throws Exception;
    Optional<Boolean> edit(ConfigPackage item,String ipClient) throws Exception;
    Optional<Integer> delete(Long id,String ipClient) throws Exception;
    Optional<Boolean> disable(Long id,String ipClient) throws Exception;
    Optional<Boolean> enable(Long id,String ipClient) throws Exception;
    Optional<ConfigPackage> get(Long id);
    Optional<ConfigPackage> getByPackageCode(String name);
    Optional<List<ConfigPackage>> list();

}
