package ru.job4j.design.srp;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.*;
import ru.job4j.design.srp.entity.Employee;
import ru.job4j.design.srp.report.*;
import ru.job4j.design.srp.store.MemStore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.StringJoiner;

public class ReportEngineTest {
    private static MemStore store;
    private static Employee firstEmployee;
    private static Employee secondEmployee;
    private static SimpleDateFormat formatter;
    private static Calendar calendar;

    @Before
    public void setUp() {
        store = new MemStore();
        calendar = Calendar.getInstance();
        firstEmployee = new Employee("Ivan", calendar, calendar, 99.49);
        secondEmployee = new Employee("Boris", calendar, calendar, 150);
        formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    }

    @After
    public void tearDown() {
        store = null;
        firstEmployee = null;
        secondEmployee = null;
    }

    @Test
    public void whenOldGenerated() {
        store.add(firstEmployee);
        Report engine = new ReportEngine(store);
        StringBuilder expect = new StringBuilder()
                .append("Name; Hired; Fired; Salary;")
                .append(System.lineSeparator())
                .append(firstEmployee.getName()).append(";")
                .append(firstEmployee.getHired()).append(";")
                .append(firstEmployee.getFired()).append(";")
                .append(firstEmployee.getSalary()).append(";")
                .append(System.lineSeparator());
        assertThat(engine.generate(em -> true), is(expect.toString()));
    }

    @Test
    public void whenHRReport() {
        store.add(firstEmployee);
        store.add(secondEmployee);
        Report engine = new HRReport(store);
        StringBuilder expect = new StringBuilder()
                .append("Name; Salary;")
                .append(System.lineSeparator())
                .append(secondEmployee.getName()).append(";")
                .append(secondEmployee.getHired()).append(";")
                .append(secondEmployee.getSalary()).append(";")
                .append(System.lineSeparator())
                .append(firstEmployee.getName()).append(";")
                .append(firstEmployee.getHired()).append(";")
                .append(firstEmployee.getSalary()).append(";")
                .append(System.lineSeparator());
        assertThat(engine.generate(em -> true), is(expect.toString()));
    }

    @Test
    public void reportGeneratedForAccounting() {
        store.add(firstEmployee);
        Report engine = new AccountantReport(store);
        StringBuilder expect = new StringBuilder()
                .append("Name; Hired; Fired; Salary;")
                .append(System.lineSeparator())
                .append(firstEmployee.getName()).append(";")
                .append(firstEmployee.getHired()).append(";")
                .append(firstEmployee.getFired()).append(";")
                .append(99).append(";")
                .append(System.lineSeparator());
        assertThat(engine.generate(em -> true), is(expect.toString()));
    }

    @Test
    public void whenDeveloperReport() {
        store.add(firstEmployee);
        Report engine = new DeveloperReport(store);
        StringJoiner text = new StringJoiner(System.lineSeparator());
        text.add("<html>");
        text.add("<head>");
        text.add("<title>Employees</title>");
        text.add("</head>");
        text.add("<body>");
        text.add("<table>");
        text.add("<tr>");
        text.add("<th>Name</th>");
        text.add("<th>Hired</th>");
        text.add("<th>Fired</th>");
        text.add("<th>Salary</th>");
        text.add("</tr>");
        text.add("<tr>");
        text.add(String.format("<td>%s</td>", firstEmployee.getName()));
        text.add(String.format("<td>%s</td>", firstEmployee.getHired()));
        text.add(String.format("<td>%s</td>", firstEmployee.getFired()));
        text.add(String.format("<td>%s</td>", firstEmployee.getSalary()));
        text.add("</tr>");
        text.add("</table>");
        text.add("</body>");
        text.add("</html>");
        assertThat(engine.generate(em -> true), is(text.toString()));
    }

    @Test
    public void whenXmlReport() {
        store.add(firstEmployee);
        store.add(secondEmployee);
        Report xmlReport = new XmlReport(store);
        String text = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" + "\n"
                + "<xmlReport>" + "\n"
                + "    <employees>" + "\n"
                + String.format("        <fired>%s</fired>",
                formatter.format(firstEmployee.getFired().getTime())) + "\n"
                + String.format("        <hired>%s</hired>",
                formatter.format(firstEmployee.getHired().getTime())) + "\n"
                + "        <name>Ivan</name>" + "\n"
                + "        <salary>99.49</salary>" + "\n"
                + "    </employees>" + "\n"
                + "    <employees>" + "\n"
                + String.format("        <fired>%s</fired>",
                formatter.format(secondEmployee.getFired().getTime())) + "\n"
                + String.format("        <hired>%s</hired>",
                formatter.format(secondEmployee.getHired().getTime())) + "\n"
                + "        <name>Boris</name>" + "\n"
                + "        <salary>150.0</salary>" + "\n"
                + "    </employees>" + "\n"
                + "</xmlReport>" + "\n"
                + "";
        assertThat(xmlReport.generate(em -> true), is(text));
    }

    @Test
    public void whenJsonReport() {
        store.add(firstEmployee);
        store.add(secondEmployee);
        Report jsonReport = new JsonReport(store);
        String text = "[{"
                + String.format("\"name\":\"%s\","
                        + "\"hired\":{\"year\":%d,\"month\":%d,\"dayOfMonth\":%d,"
                        + "\"hourOfDay\":%d,\"minute\":%d,\"second\":%d},"
                        + "\"fired\":{\"year\":%d,\"month\":%d,\"dayOfMonth\":%d,"
                        + "\"hourOfDay\":%d,\"minute\":%d,\"second\":%d},"
                        + "\"salary\":%s",
                firstEmployee.getName(),
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND),
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND),
                firstEmployee.getSalary())
                + "},{"
                + String.format("\"name\":\"%s\","
                        + "\"hired\":{\"year\":%d,\"month\":%d,\"dayOfMonth\":%d,"
                        + "\"hourOfDay\":%d,\"minute\":%d,\"second\":%d},"
                        + "\"fired\":{\"year\":%d,\"month\":%d,\"dayOfMonth\":%d,"
                        + "\"hourOfDay\":%d,\"minute\":%d,\"second\":%d},"
                        + "\"salary\":%s",
                secondEmployee.getName(),
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND),
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND),
                secondEmployee.getSalary())
                + "}]";
        assertThat(jsonReport.generate(em -> true), is(text));
    }
}