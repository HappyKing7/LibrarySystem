package com.Config;

import com.Config.ApiJsonModel;
import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Optional;
import io.swagger.annotations.ApiModelProperty;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.Modifier;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.BooleanMemberValue;
import javassist.bytecode.annotation.IntegerMemberValue;
import javassist.bytecode.annotation.StringMemberValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.ParameterContext;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * ApiJsonParameterBuilder
 * <p>
 * 将map入参匹配到swagger文档的工具类
 * plugin加载顺序，默认是最后加载
 *
 */
@Component
@Order
public class ApiJsonModelBuilder implements ParameterBuilderPlugin {
	private static final Logger logger = LoggerFactory.getLogger(ApiJsonModelBuilder.class);
	@Autowired
	private TypeResolver typeResolver;
	/**
	 * 动态生成的Class的包路径 自由定义
	 */
	private final static String BASE_PACKAGE = "com.platform.entity.";
	/**
	 * 默认类名
	 */
	private final static String DEFAULT_CLASS_NAME = "RequestData";
	/**
	 * 序号 防止重名
	 */
	private static Integer i = 0;

	@Override
	public void apply(ParameterContext context) {
		try {
			// 从方法或参数上获取指定注解的Optional
			Optional<ApiJsonModel> optional = context.getOperationContext().findAnnotation(ApiJsonModel.class);
			if (!optional.isPresent()) {
				optional = context.resolvedMethodParameter().findAnnotation(ApiJsonModel.class);
			}
			if (optional.isPresent()) {
				String key = DEFAULT_CLASS_NAME + i;
				i = i + 1;
				ApiJsonModel apiAnno = optional.get();
				try {
					Class.forName(BASE_PACKAGE + key);
				} catch (ClassNotFoundException e) {
					ApiModelProperty[] properties = apiAnno.value();
					// 将生成的Class添加到SwaggerModels
					context.getDocumentationContext().getAdditionalModels()
							.add(typeResolver.resolve(createRefModel(properties, key)));
					// 修改Json参数的ModelRef为动态生成的class
					context.parameterBuilder()
							.parameterType("body").modelRef(new ModelRef(key)).name("body").description("body");
				}
			}
		} catch (Exception e) {
			logger.error("@ApiJsonModel Error", e);
		}
	}

	/**
	 * MapReaderForApi
	 * 根据propertys中的值动态生成含有Swagger注解的javaBeen
	 *
	 * @author chengz
	 * @date 2020/10/14
	 */
	private Class<?> createRefModel(ApiModelProperty[] propertys, String key) throws Exception {
		ClassPool pool = ClassPool.getDefault();
		CtClass ctClass = pool.makeClass(BASE_PACKAGE + key);
		ctClass.setModifiers(Modifier.ABSTRACT);
		for (ApiModelProperty property : propertys) {
			ctClass.addField(createField(property, ctClass));
		}
		return ctClass.toClass();
	}

	/**
	 * 根据property的值生成含有swagger apiModelProperty注解的属性
	 */
	private CtField createField(ApiModelProperty property, CtClass ctClass) throws Exception {
		//此处默认字段类型为String 如果不是 swagger也是取注解的dataType 字段类型就不重要了
		CtField ctField = new CtField(ClassPool.getDefault().get(String.class.getName()), property.name(), ctClass);
		ctField.setModifiers(Modifier.PUBLIC);
		ConstPool constPool = ctClass.getClassFile().getConstPool();
		AnnotationsAttribute attr = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);
		//不知道是否有直接把property转为对应Annotation的方法 它们本质是一样的
		Annotation anno = new Annotation(ApiModelProperty.class.getTypeName(), constPool);
		Method[] members = ApiModelProperty.class.getDeclaredMethods();
		for (int j = 0; j < members.length; j++) {
			Method member = members[j];
			Object value = member.invoke(property);
			//使用默认值的就不重复赋值了
			if (!value.equals(member.getDefaultValue())) {
				//由于只拷贝了String,int,boolean等返回值的属性,所以诸如accessMode,extensions这样的属性设置将无效
				Type type = member.getReturnType();
				if (type == String.class) {
					anno.addMemberValue(member.getName(), new StringMemberValue(String.valueOf(member.invoke(property)), constPool));
				} else if (type == int.class) {
					anno.addMemberValue(member.getName(), new IntegerMemberValue((Integer) member.invoke(property), constPool));
				} else if (type == boolean.class) {
					anno.addMemberValue(member.getName(), new BooleanMemberValue((Boolean) member.invoke(property), constPool));
				}
			}
		}
		attr.addAnnotation(anno);
		ctField.getFieldInfo().addAttribute(attr);
		return ctField;
	}

	@Override
	public boolean supports(DocumentationType delimiter) {
		return true;
	}
}
