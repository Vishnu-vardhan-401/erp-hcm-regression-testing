package Utilities;

public final class ScenarioContext {
    private static final ThreadLocal<String> TEST_CASE_KEY = new ThreadLocal<>();

    private ScenarioContext() {}

    public static void setTestCaseKey(String key) {
        TEST_CASE_KEY.set(key);
    }

    public static String getTestCaseKey() {
        return TEST_CASE_KEY.get();
    }

    public static void clear() {
        TEST_CASE_KEY.remove();
    }
}