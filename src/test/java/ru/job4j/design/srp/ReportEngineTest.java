package ru.job4j.design.srp;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.*;
import ru.job4j.design.srp.entity.Employee;
import ru.job4j.design.srp.report.*;
import ru.job4j.design.srp.store.MemStore;

import java.util.Calendar;
import java.util.StringJoiner;

public class ReportEngineTest {
    private static MemStore store;
    private static Employee firstEmployee;
    private static Employee secondEmployee;

    @Before
    public void setUp() {
        store = new MemStore();
        firstEmployee = new Employee("Ivan", Calendar.getInstance(), Calendar.getInstance(), 99.49);
        secondEmployee = new Employee("Boris", Calendar.getInstance(), Calendar.getInstance(), 150);
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
}