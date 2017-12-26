package com.steve.router.processor;

import com.google.auto.service.AutoService;
import com.steve.router.annotation.Route;
import com.steve.router.base.ComponentInfo;

import java.util.HashMap;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
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
    private HashMap<String, ComponentInfo> mAnnotatedClassMap = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mElementsUtil = processingEnvironment.getElementUtils();
        mMessager = processingEnvironment.getMessager();
        mFiler = processingEnvironment.getFiler();
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

        }
        return false;
    }

    private void handleElement(Element element) {
        Name name = element.getSimpleName();


    }


}
