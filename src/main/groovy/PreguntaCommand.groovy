package main.groovy

public class PreguntaCommand implements grails.validation.Validateable {
    String id
    static constraints = {
        id(blank: false)
    }
}
