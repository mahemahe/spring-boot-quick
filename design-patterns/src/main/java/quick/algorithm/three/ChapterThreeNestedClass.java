package quick.algorithm.three;

/**
 * 嵌套类
 * @author mahe <mahe@maihaoche.com>
 * @date 2020/2/12
 */
public class ChapterThreeNestedClass {

    public static void main(String[] args) {
        Outer outer1 = new Outer("out", "in");
        // 经
        Outer.NestedClass nested = outer1.generateNestedClass();
        Outer.InnerClass inner = outer1.new InnerClass();
    }
}
class Outer {
    private String outerName;

    public Outer(String outerName, String nestedName) {
        this.outerName = outerName;

    }

    public NestedClass generateNestedClass() {
        NestedClass nestedClass = new NestedClass();
        return nestedClass;
    }

    static class NestedClass {
        private String nestedName;
    }

    class InnerClass {
        private String innerName;
    }
}