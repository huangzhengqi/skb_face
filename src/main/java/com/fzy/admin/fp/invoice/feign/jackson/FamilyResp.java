package com.fzy.admin.fp.invoice.feign.jackson;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.swagger.annotations.ApiModel;

@ApiModel("数族接口统一响应")
@JacksonXmlRootElement(localName = "business")
public class FamilyResp<T extends FamilyBaseResultBO> extends Object {
    @JacksonXmlProperty(isAttribute = true)
    protected String id;

    public void setId(String id) {
        this.id = id;
    }

    @JacksonXmlProperty
    protected T body;

    public void setBody(T body) {
        this.body = body;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof FamilyResp)) return false;
        FamilyResp<?> other = (FamilyResp) o;
        if (!other.canEqual(this)) return false;
        Object this$id = getId(), other$id = other.getId();
        if ((this$id == null) ? (other$id != null) : !this$id.equals(other$id)) return false;
        Object this$body = getBody(), other$body = other.getBody();
        return !((this$body == null) ? (other$body != null) : !this$body.equals(other$body));
    }

    protected boolean canEqual(Object other) {
        return other instanceof FamilyResp;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $id = getId();
        result = result * 59 + (($id == null) ? 43 : $id.hashCode());
        Object $body = getBody();
        return result * 59 + (($body == null) ? 43 : $body.hashCode());
    }

    public String toString() {
        return "FamilyResp(id=" + getId() + ", body=" + getBody() + ")";
    }


    public String getId() {
        return this.id;
    }


    public T getBody() {
        return (T) this.body;
    }


    public boolean isSuccess() {
        T body = (T) getBody();
        if (body == null) {
            return false;
        }
        String returnCode = body.getReturnCode();
        return ("0".equals(returnCode) || "00".equals(returnCode));
    }
}
