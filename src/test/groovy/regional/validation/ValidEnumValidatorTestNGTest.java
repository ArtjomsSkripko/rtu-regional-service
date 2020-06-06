package regional.validation;

import org.hibernate.validator.internal.util.annotationfactory.AnnotationDescriptor;
import org.hibernate.validator.internal.util.annotationfactory.AnnotationFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import regional.enumeration.PassengerTypeEnum;

public class ValidEnumValidatorTestNGTest {
    private static ValidEnumValidator validator;
    @BeforeClass
    public static void initialize() {
        validator = new ValidEnumValidator();
    }
    @Test(dataProvider = "parallelTest")
    public void testValidEnum(String enumType, boolean expectedResult) {
        validator.initialize(createAnnotation(PassengerTypeEnum.class, null));
        Assert.assertEquals(validator.isValid(enumType, null), expectedResult);
    }
    @DataProvider(name = "parallelTest")
    public static Object[][] enumTypes() {
        return new Object[][] {
                { "ADULT", true },
                { "SCHOLAR", true },
                { "RANDOM", false },
                { "STUDINT", false }
        };
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
