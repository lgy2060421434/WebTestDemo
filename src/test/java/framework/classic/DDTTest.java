package framework.classic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;

public class DDTTest {
    @ParameterizedTest
    @MethodSource("Data")
    public void CaseRun(TestCase testCase) {
        CaseUtil.run(testCase);
    }

    static Stream<Object> Data() throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        TestCase testCase = mapper.readValue(new File("src/test/java/framework/classic/SearchTest.yaml"), TestCase.class);
        return Stream.of(testCase);

    }
}
