package preguntas

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/" {
            controller = "login"
            action = "index"
        }
        "/login" {
            controller = "login"
            action = "meliRedirect"
        }
        "/code" {
            controller = "login"
            action = "gotCode"
        }
        "/app" {
            controller = "app"
            action = "index"
        }
        "/question" {
            controller = "app"
            action = "pregunta"
        }
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
