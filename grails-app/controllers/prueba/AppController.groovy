package prueba

import com.mercadolibre.sdk.Meli

import com.ning.http.client.FluentStringsMap
import com.ning.http.client.Response

import com.google.gson.JsonParser
import com.google.gson.JsonObject
import com.google.gson.JsonArray
import com.google.gson.JsonSyntaxException

import main.groovy.AnswerCommand
import main.groovy.PreguntaCommand

class AppController {
    def index() {
        if(session['token'] != null){
            Meli m = new Meli(6763081477574907, 'V0vCVJXWFDnV3laQ1Eo2CKyrAChWCklk', session['token'])
            FluentStringsMap params = new FluentStringsMap()
            params.add("access_token", m.getAccessToken())
            int offset = 0
            if(this.params.offset != null) {
                offset = Integer.parseInt(this.params.offset)
                params.add("offset", "" + offset)
            }
            params.add("status", "" + 'UNANSWERED')
            Response response = m.get("/my/received_questions/search", params)
            String responseBody = response.getResponseBody()

    		if (response.getStatusCode() == 200) {
                try{
                    JsonParser parser = new JsonParser()
                    JsonObject obj = parser.parse(responseBody).getAsJsonObject()
                    JsonArray questions = obj.getAsJsonArray('questions')
                    questions.each{
                    	Response responseItem = m.get("/items/"+it.get('item_id').getAsString(), params)
            			String responseBodyItem = responseItem.getResponseBody()
            			def item = parser.parse(responseBodyItem).getAsJsonObject()
            			it.addProperty('item_title', responseBodyItem)
            			it.addProperty('item_title',item.get('title').getAsString())
            			it.addProperty('item_url',item.get('permalink').getAsString())
                    }
                    [questions: questions,
                    total: q.size(),
                    offset: offset]
                } catch(JsonSyntaxException e){
                    redirect(action: 'error', params: [error: 'Error, vuelva a intentar [json]'])
                }
    		} else {
                session['token'] = null
                redirect(controller: 'login', action: 'index', params: [msg: 'La sesion expiro, por favor vuelva a ingresar'])
    		}
        } else{
            redirect(controller: 'login', action: 'index' )
        }
    }
    def pregunta(PreguntaCommand cmd) {
        if(session['token'] != null){
            if(!cmd.hasErrors()){
                Meli m = new Meli(6763081477574907, 'V0vCVJXWFDnV3laQ1Eo2CKyrAChWCklk', session['token'])
                FluentStringsMap params = new FluentStringsMap()
                params.add("access_token", m.getAccessToken())
                Response response = m.get("/questions/" + cmd.id, params)
                String responseBody = response.getResponseBody()

        		if (response.getStatusCode() == 200) {
                    try{
                        JsonParser parser = new JsonParser()
                        JsonObject question = parser.parse(responseBody).getAsJsonObject()
                        if(question.get('status').getAsString() == 'UNANSWERED'){
                            [question: question]
                        } else{
                            redirect(action: 'error', params: [error: 'La pregunta ya ha sido contestada'])
                        }
                    } catch(JsonSyntaxException e){
                        redirect(action: 'error', params: [error: 'Error, vuelva a intentar [json]'])
                    }
        		} else {
                    //TODO: diferenciar los status codes
                    session['token'] = null
                    redirect(controller: 'login', action: 'index', params: [msg: 'La sesion expiro, por favor vuelva a ingresar'])
        		}
            } else{
                redirect(action: 'error', params: [error: 'Error, vuelva a intentar [parametros incorrectos]'])
            }
        } else{
            redirect(controller: 'login', action: 'index' )
        }
    }

    def error() {
        [error: params.error]
    }

    def answer(AnswerCommand cmd) {
        if(!cmd.hasErrors()){
            Meli m = new Meli(6763081477574907, 'V0vCVJXWFDnV3laQ1Eo2CKyrAChWCklk', session['token'])
            FluentStringsMap params = new FluentStringsMap()
            params.add("access_token", m.getAccessToken())
            Response r = m.post("/answers", params, "{question_id:" + cmd.id+",text:'"+cmd.answer+"'}")
            if(r.getStatusCode()==200){
                redirect(action:'fin')
            } else{
                redirect(action: 'error', params: [error: 'Disculpe, ocurrio un problema al intentar responder la pregunta'])
            }
        } else{
            redirect(action: 'error', params: [error: 'Error, vuelva a intentar [parametros incorrectos]'])
        }
    }

    def fin() {
    }
}
