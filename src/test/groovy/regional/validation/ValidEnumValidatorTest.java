package regional.validation;

import java.util.Arrays;
import java.util.Collection;

import org.hibernate.validator.internal.util.annotationfactory.AnnotationDescriptor;
import org.hibernate.validator.internal.util.annotationfactory.AnnotationFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import regional.enumeration.PassengerTypeEnum;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ValidEnumValidatorTest {
    private static ValidEnumValidator validator;
    private String enumType;
    private Boolean expectedResult;
    @BeforeClass
    public static void initialize() {
        validator = new ValidEnumValidator();
    }
    public ValidEnumValidatorTest(String enumType, Boolean expectedResult) {
        this.enumType = enumType;
        this.expectedResult = expectedResult;
    }
    @Test
    public void testValidEnum() {
        validator.initialize(createAnnotation(PassengerTypeEnum.class, null));
        assertEquals(validator.isValid(enumType, null), expectedResult);
    }
    @Parameterized.Parameters
    public static Collection enumTypes() {
        return Arrays.asList(new Object[][] {
                { "ADULT", true },
                { "SCHOLAR", true },
                { "RANDOM", false },
                { "STUDINT", false }
        });
    }
    private ValidEnum createAnnotation(Class<? extends Enum<?>> enumeration, Boolean required) {
        AnnotationDescriptor<ValidEnum> descriptor =
                new AnnotationDescriptor<ValidEnum>(ValidEnum.class);
        if(enumeration != null){
            descriptor.setValue("value", enumeration);
        }
        if(required != null){
            descriptor.setValue("required", required);
        }
        return AnnotationFactory.create(descriptor);
    }
}
