package com.fzy.admin.fp.invoice.feign.req;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("商品编码查询")
public class QueryGoodsRequest {
    @ApiModelProperty("为空返回最新版本，不为空则返回传入 版本（如果传入为最新版本，则不返回编码信息）")
    @JacksonXmlProperty(localName = "bbh")
    private String version;

    public String toString() {
        return "QueryGoodsRequest(version=" + getVersion() + ")";
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $version = getVersion();
        return result * 59 + (($version == null) ? 43 : $version.hashCode());
    }

    protected boolean canEqual(Object other) {
        return other instanceof QueryGoodsRequest;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof QueryGoodsRequest)) return false;
        QueryGoodsRequest other = (QueryGoodsRequest) o;
        if (!other.canEqual(this)) return false;
        Object this$version = getVersion(), other$version = other.getVersion();
        return !((this$version == null) ? (other$version != null) : !this$version.equals(other$version));
    }

    public QueryGoodsRequest setVersion(String version) {
        this.version = version;
        return this;
    }


    public String getVersion() {
        return this.version;
    }
}
