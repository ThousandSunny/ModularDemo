package com.steve.router.processor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.steve.router.annotation.Route;
import com.steve.router.base.ComponentInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * Created by SteveYan on 2017/12/25.
 */
// 注册注解处理器
@AutoService(Process.class)
@SupportedAnnotationTypes({})
public class RouteProcessor extends AbstractProcessor {
    // 文件相关的辅助类
    private Filer mFiler;
    // 元素相关的辅助类
    private Elements mElementsUtil;
    // 日志相关的辅助类
    private Messager mMessager;
    // 解析的目标注解集合
    private HashMap<String, List<ComponentInfo>> mAnnotatedClassMap = new HashMap<>();

    private String moduleName;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mElementsUtil = processingEnvironment.getElementUtils();
        mMessager = processingEnvironment.getMessager();
        mFiler = processingEnvironment.getFiler();


        Map<String, String> options = processingEnvironment.getOptions();
        if (options != null) {
            moduleName = options.get(Constant.OptionModule);
        }
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        mAnnotatedClassMap.clear();
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Route.class);
        for (Element element : elements) {
            handleElement(mAnnotatedClassMap, element);
        }
        return true;
    }

    private void handleElement(HashMap<String, List<ComponentInfo>> map, Element element) {
        Element pkgElement = element.getEnclosingElement();
        Route route = element.getAnnotation(Route.class);
        if (route == null) {
            return;
        }
        String group = route.group();
        String name = route.name();
        String pkg = pkgElement.getSimpleName().toString();
        if (StringUtil.isEmpty(name)) {
            name = element.getSimpleName().toString();
        }
        if (StringUtil.isEmpty(group)) {
            group = moduleName;
        }
        ComponentInfo info = new ComponentInfo(ComponentInfo.TypeActivity, group, pkg, name);
        List<ComponentInfo> list = mAnnotatedClassMap.get(Constant.AnnotatedKey);
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(info);
    }


    private void writeToJavaFile(HashMap<String, List<ComponentInfo>> map) {

        TypeName mComponentInfoClassName = ClassName.get(ComponentInfo.class);

        // 声明方法参数的类型
        ParameterizedTypeName paramListComponent = ParameterizedTypeName.get(ClassName.get(List.class), mComponentInfoClassName);
        ParameterizedTypeName moduleLoaderParameter = ParameterizedTypeName.get(
                ClassName.get(Map.class),
                ClassName.get(String.class),
                paramListComponent
        );

        // 声明方法的参数
        ParameterSpec parameterSpec = ParameterSpec.builder(moduleLoaderParameter, "targetMap", Modifier.FINAL).build();

        MethodSpec.Builder builder = MethodSpec.methodBuilder("inject");
        builder.addModifiers(Modifier.PUBLIC)
                .returns(TypeName.VOID)
                .addParameter(parameterSpec);
        for (String s : map.keySet()) {
            List<ComponentInfo> infos = map.get(s);
            for (ComponentInfo info : infos) {
                // 方法的内部语句

            }
        }



    }

}
