package framework;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
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
        TestCase testCase = mapper.readValue(new File("src/test/java/framework/SearchTest.yaml"), TestCase.class);
        return Stream.of(testCase);

    }
}
