<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${mapperPackage}.${tableClass.shortClassName?replace(prefix,'')}${mapperSuffix}">
    <resultMap id="BaseResultMap" type="${modelPackage}.${tableClass.shortClassName?replace(prefix,'')}">
         <#if tableClass.pkFields??>
             <#list tableClass.pkFields as field>
        <id column="${field.columnName}" jdbcType="${field.jdbcType}" property="${field.fieldName}"/>
             </#list>
         </#if>
                <#if tableClass.baseFields??>
                    <#list tableClass.baseFields as field>
        <result column="${field.columnName}" jdbcType="${field.jdbcType}" property="${field.fieldName}"/>
                    </#list>
                </#if>
    </resultMap>
</mapper>
