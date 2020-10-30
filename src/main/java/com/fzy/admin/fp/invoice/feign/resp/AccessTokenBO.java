package com.fzy.admin.fp.invoice.feign.resp;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fzy.admin.fp.invoice.feign.jackson.FamilyBaseBO;
import io.swagger.annotations.ApiModelProperty;

public class AccessTokenBO extends FamilyBaseBO {
    @ApiModelProperty(notes = "token")
    @JacksonXmlProperty(localName = "access_token")
    private String accessToken;
    @ApiModelProperty(notes = "过期时间")
    @JacksonXmlProperty
    private Integer expires;

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setExpires(Integer expires) {
        this.expires = expires;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof AccessTokenBO)) return false;
        AccessTokenBO other = (AccessTokenBO) o;
        if (!other.canEqual(this)) return false;
        Object this$accessToken = getAccessToken(), other$accessToken = other.getAccessToken();
        if ((this$accessToken == null) ? (other$accessToken != null) : !this$accessToken.equals(other$accessToken))
            return false;
        Object this$expires = getExpires(), other$expires = other.getExpires();
        return !((this$expires == null) ? (other$expires != null) : !this$expires.equals(other$expires));
    }

    protected boolean canEqual(Object other) {
        return other instanceof AccessTokenBO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $accessToken = getAccessToken();
        result = result * 59 + (($accessToken == null) ? 43 : $accessToken.hashCode());
        Object $expires = getExpires();
        return result * 59 + (($expires == null) ? 43 : $expires.hashCode());
    }

    public String toString() {
        return "AccessTokenBO(super=" + super.toString() + ", accessToken=" + getAccessToken() + ", expires=" + getExpires() + ")";
    }


    public String getAccessToken() {
        return this.accessToken;
    }


    public Integer getExpires() {
        return this.expires;
    }
}
