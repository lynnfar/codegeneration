package ${package.Other};

<#list table.importPackages as pkg>
    import ${pkg};
</#list>
<#if entityLombokModel>
    import lombok.Data;
    <#if chainModel>
        import lombok.experimental.Accessors;
    </#if>
</#if>

/**
* <p>
    * ${table.comment!}
    * </p>
*
* @author ${author}
* @since ${date}
*/
<#if entityLombokModel>
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
</#if>
<#if superEntityClass??>
    public class ${entity}DTO extends ${superEntityClass}<#if activeRecord><${entity}></#if> implements Serializable {
<#elseif activeRecord>
    public class ${entity}DTO extends Model<${entity}> implements Serializable {
<#else>
    public class ${entity}DTO implements Serializable {
</#if>

<#if entitySerialVersionUID>
    private static final long serialVersionUID = 1L;
</#if>

<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    <#if field.comment!?length gt 0>

        /**
        * ${field.comment}
        */
    </#if>
    private ${field.propertyType} ${field.propertyName};
</#list>
<#------------  END 字段循环遍历  ---------->
}