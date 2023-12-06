package com.shavatech.management.infraestructure.persistence;

import com.shavatech.management.domain.entity.YesNoType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class YesNoTypeConverter implements AttributeConverter<YesNoType, String> {

    /**
     * @param yesNoType 
     * @return
     */
    @Override
    public String convertToDatabaseColumn(YesNoType yesNoType) {
        if(yesNoType == null){
            return null;
        }
        return yesNoType.getValue();
    }

    /**
     * @param s 
     * @return
     */
    @Override
    public YesNoType convertToEntityAttribute(String s) {
        if(s == null){
            return null;
        }
        return YesNoType.of(s);
    }
}
