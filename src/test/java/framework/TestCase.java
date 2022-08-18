package framework;

import java.util.HashMap;
import java.util.List;

public class TestCase {
    private String name;
    private List<HashMap<String, Object>> steps;
//    private List<HashMap<String,Object>> after_all;
//    private List<HashMap<String,Object>> before_all;
//    private List<HashMap<String,Object>> before;
//    private List<HashMap<String,Object>> after;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<HashMap<String, Object>> getSteps() {
        return steps;
    }

    public void setSteps(List<HashMap<String, Object>> steps) {
        this.steps = steps;
    }

    @Override
    public String toString() {
        return "TestCase{" +
                "name='" + name + '\'' +
                ", steps=" + steps +
                '}';
    }
}
