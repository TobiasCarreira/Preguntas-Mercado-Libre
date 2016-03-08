package main.groovy

public class CodeCommand implements grails.validation.Validateable {
    String code
    static constraints = {
        code(blank: false)
    }
}
