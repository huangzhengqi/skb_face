/*
package com.fzy.admin.fp.codegen;

import com.fzy.admin.fp.common.validation.annotation.NotBlank;
import com.wuxp.codegen.annotation.processor.AbstractAnnotationProcessor;
import com.wuxp.codegen.annotation.processor.AnnotationMate;

import java.lang.reflect.Field;
import java.text.MessageFormat;

*/
/**
 * 自定义 验证注解处理
 *
 * @see com.fzy.admin.fp.common.validation.annotation.NotBlank
 *//*

public class NotBlankProcessor extends AbstractAnnotationProcessor<NotBlank, NotBlankProcessor.NotBlankMate> {


    @Override
    public NotBlankMate process(NotBlank annotation) {

        return super.newProxyMate(annotation, NotBlankMate.class);
    }


    public abstract static class NotBlankMate implements AnnotationMate<NotBlank>, NotBlank {

        public NotBlankMate() {
        }

        @Override
        public String toComment(Field annotationOwner) {

            return MessageFormat.format("属性：{0}为必填项，不能为空", annotationOwner.getName());
        }
    }
}
*/
