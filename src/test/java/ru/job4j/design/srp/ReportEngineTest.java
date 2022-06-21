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
        StringBuilder expect = new StringBuilder()
                .append("<html>").append(System.lineSeparator())
                .append("<head>").append(System.lineSeparator())
                .append("<title>Employees</title>").append(System.lineSeparator())
                .append("</head>").append(System.lineSeparator())
                .append("<body>").append(System.lineSeparator())
                .append("<table>").append(System.lineSeparator())
                .append("<tr>").append(System.lineSeparator())
                .append("<th>Name</th>").append(System.lineSeparator())
                .append("<th>Hired</th>").append(System.lineSeparator())
                .append("<th>Fired</th>").append(System.lineSeparator())
                .append("<th>Salary</th>").append(System.lineSeparator())
                .append("</tr>").append(System.lineSeparator())
                .append("<tr>").append(System.lineSeparator())
                .append(String.format("<td>%s</td>", firstEmployee.getName())).append(System.lineSeparator())
                .append(String.format("<td>%s</td>", firstEmployee.getHired())).append(System.lineSeparator())
                .append(String.format("<td>%s</td>", firstEmployee.getFired())).append(System.lineSeparator())
                .append(String.format("<td>%s</td>", firstEmployee.getSalary())).append(System.lineSeparator())
                .append("</tr>").append(System.lineSeparator())
                .append("</table>").append(System.lineSeparator())
                .append("</body>").append(System.lineSeparator())
                .append("</html>");
        assertThat(engine.generate(em -> true), is(expect.toString()));
    }

    @Test
    public void whenXmlReport() {
        store.add(firstEmployee);
        store.add(secondEmployee);
        Report xmlReport = new XmlReport(store);
        StringBuilder expect = new StringBuilder()
                .append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")
                .append("\n")
                .append("<xmlReport>")
                .append("\n")
                .append("    <employees>")
                .append("\n")
                .append(String.format("        <fired>%s</fired>", formatter.format(firstEmployee.getFired().getTime())))
                .append("\n")
                .append(String.format("        <hired>%s</hired>", formatter.format(firstEmployee.getHired().getTime())))
                .append("\n")
                .append("        <name>Ivan</name>")
                .append("\n")
                .append("        <salary>99.49</salary>")
                .append("\n")
                .append("    </employees>")
                .append("\n")
                .append("    <employees>")
                .append("\n")
                .append(String.format("        <fired>%s</fired>", formatter.format(secondEmployee.getFired().getTime())))
                .append("\n")
                .append(String.format("        <hired>%s</hired>", formatter.format(secondEmployee.getHired().getTime())))
                .append("\n")
                .append("        <name>Boris</name>")
                .append("\n")
                .append("        <salary>150.0</salary>")
                .append("\n")
                .append("    </employees>")
                .append("\n")
                .append("</xmlReport>")
                .append("\n");
        assertThat(xmlReport.generate(em -> true), is(expect.toString()));
    }

    @Test
    public void whenJsonReport() {
        store.add(firstEmployee);
        store.add(secondEmployee);
        Report jsonReport = new JsonReport(store);
        StringBuilder expect = new StringBuilder()
                .append("[{")
                .append(String.format("\"name\":\"%s\","
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
                )
                .append("},{")
                .append(String.format("\"name\":\"%s\","
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
                        secondEmployee.getSalary()))
                .append("}]");
        assertThat(jsonReport.generate(em -> true), is(expect.toString()));
    }
}