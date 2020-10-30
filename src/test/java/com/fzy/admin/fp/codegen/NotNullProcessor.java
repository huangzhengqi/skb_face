/*
package com.fzy.admin.fp.codegen;

import com.fzy.admin.fp.common.validation.annotation.NotNull;
import com.wuxp.codegen.annotation.processor.AbstractAnnotationProcessor;
import com.wuxp.codegen.annotation.processor.AnnotationMate;

import java.lang.reflect.Field;
import java.text.MessageFormat;

*/
/**
 * 自定义 验证注解处理
 *
 * @see NotNull
 *//*

public class NotNullProcessor extends AbstractAnnotationProcessor<NotNull, NotNullProcessor.NotNullMate> {


    @Override
    public NotNullMate process(NotNull annotation) {

        return super.newProxyMate(annotation, NotNullMate.class);
    }


    public abstract static class NotNullMate implements AnnotationMate<NotNull>, NotNull {

        public NotNullMate() {
        }

        @Override
        public String toComment(Field annotationOwner) {

            return MessageFormat.format("属性：{0}为必填项，不能为空", annotationOwner.getName());
        }
    }
}
*/
