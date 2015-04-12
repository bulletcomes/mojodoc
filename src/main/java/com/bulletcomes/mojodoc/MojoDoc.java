package com.bulletcomes.mojodoc;

import com.bulletcomes.mojodoc.annotations.*;
import com.bulletcomes.mojodoc.logic.MojoComparator;
import com.bulletcomes.mojodoc.logic.MojoItem;
import com.bulletcomes.mojodoc.logic.MojoMethod;
import com.bulletcomes.mojodoc.logic.MojoWrapper;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collections;

public class MojoDoc {
    public static final MojoWrapper generateDocumentation(Class aClass) {
        Method[] methods = aClass.getDeclaredMethods();
        ArrayList<MojoMethod> mojoMethods = new ArrayList<MojoMethod>();
        for (Method method : methods) {
            if (method.getAnnotation(NotDocumented.class) == null) {
                mojoMethods.add(createMojoMethod(method));
            }
        }
        Collections.sort(mojoMethods, new MojoComparator());
        MojoWrapper mojoWrapper = new MojoWrapper();
        mojoWrapper.setkMethods(mojoMethods);
        return mojoWrapper;
    }

    private static MojoMethod createMojoMethod(Method method) {
        MojoMethod mojoMethod = new MojoMethod();
        mojoMethod.setName(method.getName());

        RequestMapping requestMapping = method.getDeclaredAnnotation(RequestMapping.class);
        if (requestMapping != null && requestMapping.method() != null && requestMapping.method().length > 0) {
            mojoMethod.setRequestMethod(requestMapping.method()[0]);
        }

        Note noteMapping = method.getDeclaredAnnotation(Note.class);
        if (noteMapping != null) {
            mojoMethod.setNote(noteMapping.value());
        }

        Description methodDescription = method.getDeclaredAnnotation(Description.class);
        if (methodDescription != null) {
            mojoMethod.setDescription(methodDescription.value());
        }

        Params methodParamDescriptions = method.getDeclaredAnnotation(Params.class);
        String[] paramDescriptions = {};
        if (methodParamDescriptions != null) {
            paramDescriptions = methodParamDescriptions.values();
        }
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            if(parameters[i].getAnnotationsByType(RequestBody.class).length == 0) {

                MojoItem kItem = new MojoItem();
                try {
                    kItem.setName(paramDescriptions[i]);
                    kItem.setDescription(paramDescriptions[i + 1]);
                } catch (IndexOutOfBoundsException e) {
                    kItem.setName(parameters[i].toString());
                    kItem.setDescription("N/A");
                }
                mojoMethod.addParam(kItem);
            }
        }

        ResponseList methodResponseList = method.getDeclaredAnnotation(ResponseList.class);
        if (methodResponseList != null) {
            for (int i = 0; i < methodResponseList.values().length; i = i + 2) {
                MojoItem mojoItem = new MojoItem();
                try {
                    mojoItem.setName(methodResponseList.values()[i]);
                    mojoItem.setDescription(methodResponseList.values()[i + 1]);
                } catch (IndexOutOfBoundsException e) {
                    mojoItem.setDescription("N/A");
                }
                mojoMethod.addResponse(mojoItem);
            }
        }

        RequestContent methodRequestContent = method.getDeclaredAnnotation(RequestContent.class);
        if (methodRequestContent != null) {
            mojoMethod.setRequestContent(methodRequestContent.value());
        }
        return mojoMethod;
    }
}
