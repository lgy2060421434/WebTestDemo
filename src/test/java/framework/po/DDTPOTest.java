package framework.po;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

/*
利用junit5参数化拿到yaml数据进行数据驱动
 */
public class DDTPOTest {
    @ParameterizedTest
    @MethodSource("Data")
    public void CaseRun(POTestCase potestCase) {
        POCaseUtil.run(potestCase);
    }

    static Stream<Object> Data() throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        POTestCase potestCase = mapper.readValue(new File("src/test/java/framework/po/PoSearchTest.yaml"), POTestCase.class);
        return Stream.of(potestCase);

    }
}
