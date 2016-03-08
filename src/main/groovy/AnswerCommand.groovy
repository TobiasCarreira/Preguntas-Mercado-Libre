package main.groovy

public class AnswerCommand implements grails.validation.Validateable {
    String id
    String answer
    static constraints = {
        id(blank: false)
        answer(blank: false)
    }
}
