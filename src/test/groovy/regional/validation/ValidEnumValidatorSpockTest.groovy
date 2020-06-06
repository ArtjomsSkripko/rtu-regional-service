package regional.validation

import org.hibernate.validator.internal.util.annotationfactory.AnnotationDescriptor
import org.hibernate.validator.internal.util.annotationfactory.AnnotationFactory
import regional.enumeration.PassengerTypeEnum
import spock.lang.Specification
import spock.lang.Unroll

class ValidEnumValidatorSpockTest extends Specification {
    ValidEnumValidator validator
    def setup() {
        validator = new ValidEnumValidator()
        validator.initialize(createAnnotation(PassengerTypeEnum.class, null));
    }
    @Unroll
    def "validate enum #enumType"() {
        when: "validator check enum"
        def isEnumValid = validator.isValid(enumType, null)
        then: "assert that enum is valid"
        isEnumValid == expectedResult
        where: 'sample enums are:'
        enumType  || expectedResult
        'ADULT'   || true
        'SCHOLAR' || true
        'RANDOM'  || false
        'STUDINT' || false
    }
    private static ValidEnum createAnnotation(Class<? extends Enum<PassengerTypeEnum>> enumeration, Boolean required) {
        def descriptor =
                new AnnotationDescriptor<>(ValidEnum.class)
        if (enumeration != null) {
            descriptor.setValue("value", enumeration)
        }
        if (required != null) {
            descriptor.setValue("required", required)
        }
        return AnnotationFactory.create(descriptor) as ValidEnum
    }
}
