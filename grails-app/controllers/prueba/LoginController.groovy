package prueba

import com.mercadolibre.sdk.Meli

import main.groovy.CodeCommand

class LoginController {

    Meli m = new Meli(6763081477574907, 'V0vCVJXWFDnV3laQ1Eo2CKyrAChWCklk')

    def index() {
        if(session['token'] != null){
            redirect(controller: 'app', action: 'index' )
        } else{
            if(params.msg != null)
            {
                [msg: params.msg]
            }
        }
    }

    def meliRedirect() {
        String redirectUrl = m.getAuthUrl('https://serene-island-81816.herokuapp.com/code')
        redirect(url: redirectUrl)
    }

    def gotCode(CodeCommand cmd) {
        if(!cmd.hasErrors()){
            m.authorize(params.code, 'https://serene-island-81816.herokuapp.com/code')
            session['token'] = m.getAccessToken()
            redirect(controller: 'app', action: 'index' )
        } else{
            redirect(action: 'index' )
        }
    }
}
