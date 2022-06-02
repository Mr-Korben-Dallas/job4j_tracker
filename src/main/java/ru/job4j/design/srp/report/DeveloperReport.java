package ru.job4j.design.srp.report;

import ru.job4j.design.srp.entity.Employee;
import ru.job4j.design.srp.store.Store;
import java.util.StringJoiner;
import java.util.function.Predicate;

public class DeveloperReport implements Report {
    private Store store;

    public DeveloperReport(Store store) {
        this.store = store;
    }

    @Override
    public String generate(Predicate<Employee> filter) {
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
        for (Employee employee : store.findBy(filter)) {
            text.add("<tr>");
            text.add(String.format("<td>%s</td>", employee.getName()));
            text.add(String.format("<td>%s</td>", employee.getHired()));
            text.add(String.format("<td>%s</td>", employee.getFired()));
            text.add(String.format("<td>%s</td>", employee.getSalary()));
            text.add("</tr>");
        }
        text.add("</table>");
        text.add("</body>");
        text.add("</html>");
        return text.toString();
    }
}
