/*
package com.fzy.admin.fp.codegen;

import com.fzy.admin.fp.common.validation.annotation.Length;
import com.wuxp.codegen.annotation.processor.AbstractAnnotationProcessor;
import com.wuxp.codegen.annotation.processor.AnnotationMate;

import java.lang.reflect.Field;
import java.text.MessageFormat;

*/
/**
 * 自定义 验证注解处理
 *
 * @see Length
 *//*

public class LengthProcessor extends AbstractAnnotationProcessor<Length, LengthProcessor.LengthMate> {


    @Override
    public LengthMate process(Length annotation) {

        return super.newProxyMate(annotation, LengthMate.class);
    }


    public abstract static class LengthMate implements AnnotationMate<Length>, Length {

        public LengthMate() {
        }

        @Override
        public String toComment(Field annotationOwner) {

            return MessageFormat.format("属性：{0}最小长度{1}，最大长度{2}",
                    annotationOwner.getName(),this.min(),this.max());
        }
    }
}
*/
