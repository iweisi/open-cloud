package com.github.lyd.rbac.producer.mapper;


import com.github.lyd.common.mapper.CrudMapper;
import com.github.lyd.rbac.client.entity.UserAccount;
import org.springframework.stereotype.Repository;

/**
 * @author liuyadu
 */
@Repository
public interface UserAccountMapper extends CrudMapper<UserAccount> {
}