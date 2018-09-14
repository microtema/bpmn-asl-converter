package de.seven.converter.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

@UtilityClass
public class ListUtil {

    public static <T> T last(List<T> list){
        if(CollectionUtils.isEmpty(list)){
            return null;
        }

        return list.get(list.size()-1);
    }
}
