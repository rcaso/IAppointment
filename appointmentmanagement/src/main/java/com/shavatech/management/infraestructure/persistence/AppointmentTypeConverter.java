package com.shavatech.management.infraestructure.persistence;

import com.shavatech.management.domain.entity.AppointmentType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AppointmentTypeConverter implements AttributeConverter<AppointmentType,String> {

    /**
     * @param appointmentType 
     * @return
     */
    @Override
    public String convertToDatabaseColumn(AppointmentType appointmentType) {
        if(appointmentType == null){
            return null;
        }
        return appointmentType.getValue();
    }

    /**
     * @param value
     * @return
     */
    @Override
    public AppointmentType convertToEntityAttribute(String value) {
        if(value == null){
            return null;
        }
        return AppointmentType.of(value);
    }
}
