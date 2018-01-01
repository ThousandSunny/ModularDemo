package com.steve.router.processor;

import com.google.common.base.Strings;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;

/**
 * Simplify the messager.
 *
 * @author Alex <a href="mailto:zhilong.liu@aliyun.com">Contact me.</a>
 * @version 1.0
 * @since 16/8/22 上午11:48
 */
public class Logger {
    private Messager msg;

    public Logger(Messager messager) {
        msg = messager;
    }

    /**
     * Print info log.
     */
    public void info(String info) {
        if (!Strings.isNullOrEmpty(info)) {
            msg.printMessage(Diagnostic.Kind.NOTE, Constant.PREFIX_OF_LOGGER + info);
        }
    }

    public void error(String error) {
        if (!Strings.isNullOrEmpty(error)) {
            msg.printMessage(Diagnostic.Kind.ERROR, Constant.PREFIX_OF_LOGGER + "An exception is encountered, [" + error + "]");
        }
    }

    public void error(Throwable error) {
        if (null != error) {
            msg.printMessage(Diagnostic.Kind.ERROR, Constant.PREFIX_OF_LOGGER + "An exception is encountered, [" + error.getMessage() + "]" + "\n" + formatStackTrace(error.getStackTrace()));
        }
    }

    public void warning(String warning) {
        if (!Strings.isNullOrEmpty(warning)) {
            msg.printMessage(Diagnostic.Kind.WARNING, Constant.PREFIX_OF_LOGGER + warning);
        }
    }

    private String formatStackTrace(StackTraceElement[] stackTrace) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : stackTrace) {
            sb.append("    at ").append(element.toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}
