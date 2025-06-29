package com.HRM_Project.HRM_Project.validation;

import jakarta.validation.GroupSequence;
import jakarta.validation.groups.Default;

public interface ValidationGroups {

    interface NotEmptyCheck{}

    interface SizeCheck {}

    interface PatternCheck {}

    @GroupSequence({ValidationGroups.NotEmptyCheck.class,
            ValidationGroups.SizeCheck.class,
            ValidationGroups.PatternCheck.class,
            Default.class})
    interface ValidationSequence {}

}
