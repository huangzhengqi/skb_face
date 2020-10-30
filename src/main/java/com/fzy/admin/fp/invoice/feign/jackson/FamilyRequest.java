package com.fzy.admin.fp.invoice.feign.jackson;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("数族接口统一请求")
@JacksonXmlRootElement(localName = "business")
@Data
public class FamilyRequest<T> extends Object {
    @JacksonXmlProperty(isAttribute = true)
    private String id;
    @JacksonXmlProperty
    private Body<T> body;

    public FamilyRequest() {
    }

    public FamilyRequest(String id, T input) {
        this.id = id;
        this.body = new Body(input);
    }

    public static class Body<T> extends Object {
        public Body(T input) {
            this.input = input;
        }

        @JacksonXmlProperty
        private T input;

        private Body() {
        }

        public void setInput(T input) {
            this.input = input;
        }

        public boolean equals(Object o) {
            if (o == this) return true;
            if (!(o instanceof Body)) return false;
            Body<?> other = (Body) o;
            if (!other.canEqual(this)) return false;
            Object this$input = getInput(), other$input = other.getInput();
            return !((this$input == null) ? (other$input != null) : !this$input.equals(other$input));
        }

        protected boolean canEqual(Object other) {
            return other instanceof Body;
        }

        public int hashCode() {
            int PRIME = 59;
            int result = 1;
            Object $input = getInput();
            return result * 59 + (($input == null) ? 43 : $input.hashCode());
        }

        public String toString() {
            return "FamilyRequest.Body(input=" + getInput() + ")";
        }


        public T getInput() {
            return (T) this.input;
        }
    }

}
