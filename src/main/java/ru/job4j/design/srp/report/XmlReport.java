package ru.job4j.design.srp.report;

import ru.job4j.design.srp.entity.Employee;
import ru.job4j.design.srp.store.Store;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.function.Predicate;

public class XmlReport implements Report {
    private Store store;
    private JAXBContext jaxbContext;
    private Marshaller marshaller;
    private String xmlReport;
    private List<Employee> employees;

    public XmlReport(Store store) {
        this.store = store;
    }

    @Override
    public String generate(Predicate<Employee> filter) {
        employees = store.findBy(filter);
        try (StringWriter writer = new StringWriter()) {
            jaxbContext = JAXBContext.newInstance(Employees.class);
            marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(new Employees(employees), writer);
            xmlReport = writer.getBuffer().toString();
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        }
        return xmlReport;
    }

    @XmlRootElement(name = "xmlReport")
    private static class Employees {
        private List<Employee> employees;

        public Employees() {
        }

        public Employees(List<Employee> employees) {
            this.employees = employees;
        }

        public List<Employee> getEmployees() {
            return employees;
        }

        public void setEmployees(List<Employee> employees) {
            this.employees = employees;
        }
    }
}
