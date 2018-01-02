package com.steve.router.processor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.thousandsunny.router_annotation.annotation.annotaion.Route;
import com.thousandsunny.router_annotation.annotation.base.ComponentInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * Created by SteveYan on 2017/12/25.
 */
// 注册注解处理器
@AutoService(Processor.class)
@SupportedOptions(Constant.OptionModule)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes("com.thousandsunny.router_annotation.annotation.annotaion.Route")
public class RouteProcessor extends AbstractProcessor {


    // 文件相关的辅助类
    private Filer mFiler;
    // 元素相关的辅助类
    private Elements mElementsUtil;
    // 解析的目标注解集合
    private HashMap<String, List<ComponentInfo>> mAnnotatedClassMap = new HashMap<>();

    private String moduleName;

    private Logger logger;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        logger = new Logger(processingEnvironment.getMessager());   // Package the log utils.
        logger.info("进行初始化");

        mElementsUtil = processingEnvironment.getElementUtils();
        mFiler = processingEnvironment.getFiler();

        Map<String, String> options = processingEnvironment.getOptions();
        if (options != null) {
            moduleName = options.get(Constant.OptionModule);
        }
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_7;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        logger.info("开始执行注解解释器");

        mAnnotatedClassMap.clear();
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Route.class);
        for (Element element : elements) {
            handleElement(mAnnotatedClassMap, element);
        }
        writeToJavaFile(mAnnotatedClassMap);

        return true;
    }

    private void handleElement(HashMap<String, List<ComponentInfo>> map, Element element) {
        if (element == null) {
            return;
        }
        // 强转成  PackageElement
        PackageElement pkgElement = (PackageElement) element.getEnclosingElement();
        Route route = element.getAnnotation(Route.class);
        if (route == null) {
            return;
        }

        String group = route.group();
        String name = route.name();
        String pkg = pkgElement.getQualifiedName().toString();
        if (StringUtil.isEmpty(name)) {
            name = element.getSimpleName().toString();
        }
        if (StringUtil.isEmpty(group)) {
            group = moduleName;
        }

        ComponentInfo info = new ComponentInfo(ComponentInfo.TypeActivity, group, pkg, name);
        // 获取 Activity 的集合
        List<ComponentInfo> list = mAnnotatedClassMap.get(Constant.AnnotatedKey);
        if (list == null) {
            list = new ArrayList<>();
            mAnnotatedClassMap.put(Constant.AnnotatedKey, list);
        }
        list.add(info);
    }


    private void writeToJavaFile(HashMap<String, List<ComponentInfo>> map) {

        TypeName mComponentInfoClassName = ClassName.get(ComponentInfo.class);

        // 声明 inject 方法参数的类型
        ParameterizedTypeName paramListComponent = ParameterizedTypeName.get(ClassName.get(List.class), mComponentInfoClassName);
        ParameterizedTypeName moduleLoaderParameter = ParameterizedTypeName.get(
                ClassName.get(Map.class),
                ClassName.get(String.class),
                paramListComponent
        );

        // 声明inject方法的参数
        ParameterSpec injectParameterSpec = ParameterSpec.builder(moduleLoaderParameter, "targetMap", Modifier.FINAL).build();

        // 声明 injectElement 方法的构造器
        MethodSpec.Builder injectElementBuilder = MethodSpec.methodBuilder(Constant.MethodInjectElement)
                .addModifiers(Modifier.PRIVATE)
                .addParameter(injectParameterSpec)
                .addParameter(String.class, "group")
                .addParameter(String.class, "pkg")
                .addParameter(String.class, "name")
                .addParameter(int.class, "type");

        injectElementBuilder.addStatement("List<$T> list = targetMap.get(name)", ComponentInfo.class)
                .beginControlFlow("if( list == null )")
                .addStatement("list = new $T<>()", ArrayList.class)
                .addStatement("targetMap.put(group, list)")
                .endControlFlow()
                .addStatement("ComponentInfo info = new ComponentInfo(type, group, pkg, name)")
                .beginControlFlow("try")
                .addStatement(" info.setClazz(Class.forName(pkg + name))")
                .nextControlFlow("catch(Exception e)")
                .addStatement("e.printStackTrace()")
                .endControlFlow()
                .addStatement("list.add(info)");


        MethodSpec injectElementMethod = injectElementBuilder.build();


        // 声明 inject 方法构造器
        MethodSpec.Builder injectMethodBuilder = MethodSpec.methodBuilder(Constant.MethodInject);
        injectMethodBuilder.addModifiers(Modifier.PUBLIC)
                .returns(TypeName.VOID)
                .addParameter(injectParameterSpec);

        // 取出所有的 activity
        List<ComponentInfo> activityList = map.get(Constant.AnnotatedKey);

        if (activityList == null) {
            return;
        }

        for (ComponentInfo info : activityList) {
            injectMethodBuilder.addStatement("injectElement(targetMap, $S, $S, $S,$L)", info.getGroup(), info.getPkg(), info.getName(), info.getType());
        }

        MethodSpec injectMethodSpec = injectMethodBuilder.build();

        TypeSpec typeSpec = TypeSpec.classBuilder(moduleName + Constant.ModuleSuffix)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(injectElementMethod)
                .addMethod(injectMethodSpec)
                .build();

        JavaFile javaFile = JavaFile.builder(Constant.GeneratePkg, typeSpec).build();

        try {
            javaFile.writeTo(mFiler);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
