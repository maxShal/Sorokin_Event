package com.example.Sorokin_Event;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FieldChange<T> {
    private T oldField;
    private T newField;

}
