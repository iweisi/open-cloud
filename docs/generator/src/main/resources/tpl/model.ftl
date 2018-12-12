package ${package};

import ${tableClass.fullClassName};

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "${tableClass.tableName}")
public class ${tableClass.shortClassName?replace(prefix,'')} implements Serializable {
    private static final long serialVersionUID = 1L;

<#if tableClass.pkFields??>
    <#list tableClass.pkFields as field>
        <#if field.remarks??>
   /**
     * ${field.remarks}
   */
        </#if>
   @Id
        <#if field.sequenceColumn??>
   @GeneratedValue(strategy = GenerationType.IDENTITY)
        </#if>
        <#if field.columnName?contains("_")>
   @Column(name = "${field.columnName}")
        </#if>
    private ${field.shortTypeName} ${field.fieldName};
    </#list>
</#if>

<#if tableClass.baseFields??>
    <#list tableClass.baseFields as field>
        <#if field.remarks??>
    /**
     * ${field.remarks}
     */
        </#if>
        <#if field.columnName?contains("_")>
    @Column(name = "${field.columnName}")
        </#if>
     private  ${field.shortTypeName} ${field.fieldName};
    </#list>
</#if>

<#if tableClass.allFields??>
    <#list tableClass.allFields as field>

    public ${field.shortTypeName} get${field.fieldName?cap_first}() {
        return ${field.fieldName};
    }

    public ${tableClass.shortClassName?replace(prefix,'')} set${field.fieldName?cap_first}(${field.shortTypeName} ${field.fieldName}) {
        this.${field.fieldName} = ${field.fieldName};
        return  this;
    }
    </#list>
</#if>

}